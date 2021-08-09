package com.ximand.bot.mgtulists.service.lists;

import com.ximand.bot.mgtulists.config.WebConfig;
import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.model.Participant;
import com.ximand.bot.mgtulists.model.mapper.Mapper;
import com.ximand.bot.mgtulists.model.mapper.PdfLineToParticipantMapper;
import com.ximand.bot.mgtulists.service.pdf.PdfService;
import com.ximand.bot.mgtulists.util.TextUtils;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
@ThreadSafe
public abstract class AbstractCompetitionListsService implements CompetitionListsService {

    private static final String DIRECTION_PATTERN = "^\\d{2}.\\d{2}.\\d{2}$";
    private static final String GENERAL_COMPETITION_DECLARATION_PATTERN = "\\d.*\\..*((общ.*конкурс.*)|(оплат.*стоимост.*)).*";

    private final Map<String, CompetitionList> directionToParticipantMap = new ConcurrentHashMap<>();
    private final Mapper<String, Participant> toParticipantMapper = new PdfLineToParticipantMapper();

    private final CompetitionListUpdater updater = new CompetitionListUpdater();
    private final ExecutorService updateExecutorService;

    private final PdfService pdfService;

    protected AbstractCompetitionListsService(WebConfig webConfig, PdfService pdfService) {
        this.pdfService = pdfService;
        this.updateExecutorService = Executors.newFixedThreadPool(webConfig.getUpdateThreads());
        val delay = webConfig.getUpdateDelay();
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(new AllListsUpdater(), delay, delay, TimeUnit.SECONDS);
    }

    @Override
    public CompetitionList getListForDirection(String direction) throws IOException {
        val competitionsList = directionToParticipantMap.get(direction);
        if (competitionsList != null) {
            return competitionsList;
        } else {
            val newCompetitionsList = loadCompetitionsList(direction);
            directionToParticipantMap.put(direction, newCompetitionsList);
            return newCompetitionsList;
        }
    }

    private CompetitionList loadCompetitionsList(String direction) throws IOException {
        val url = getFileUrl(direction);
        val created = pdfService.loadCreateDate(url);
        val pdfText = pdfService.loadText(url);

        val generalCompetitionsText = getTextOnlyForGeneralCompetitions(pdfText);
        val newCompetitionsList = new CompetitionList(getNumOfPlaces(pdfText), created);
        for (val line : generalCompetitionsText.split("\n")) {
            try {
                newCompetitionsList.add(toParticipantMapper.map(line));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return newCompetitionsList;
    }

    private int getNumOfPlaces(String text) {
        val declarationText = TextUtils.findByRegex(text, GENERAL_COMPETITION_DECLARATION_PATTERN);
        val places = TextUtils.findByRegex(declarationText, "\\( ?\\d+");
        if (places != null) {
            return Integer.parseInt(places.replace("(", "").trim());
        } else {
            return -1;
        }
    }

    private String getTextOnlyForGeneralCompetitions(String text) {
        val declarationText = TextUtils.findByRegex(text, GENERAL_COMPETITION_DECLARATION_PATTERN);
        if (declarationText != null) {
            val startIndex = text.indexOf(declarationText);
            return text.substring(startIndex);
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected abstract String getBaseUrl();

    private String getFileUrl(String direction) throws IllegalArgumentException {
        if (direction != null && direction.matches(DIRECTION_PATTERN)) {
            return getBaseUrl() + direction + ".pdf";
        } else {
            throw new IllegalArgumentException(String.format("Wrong direction: '%s'", direction));
        }
    }

    private class AllListsUpdater implements Runnable {

        @Override
        public void run() {
            directionToParticipantMap.forEach((direction, list) -> {
                try {
                    val lastDate = pdfService.loadCreateDate(getFileUrl(direction));
                    if (lastDate != null && lastDate.equals(list.getUpdated()) == false) {
                        log.info("Data for direction: " + direction + " not actual! Last date: " + lastDate
                                + " - created: " + list.getUpdated());
                        updateExecutorService.submit(
                                updater.getRunnable(direction)
                        );
                    }
                } catch (IOException e) {
                    log.error("Unable to check that list is actual, direction: " + direction, e);
                }
            });
        }
    }

    private class CompetitionListUpdater {

        public Runnable getRunnable(String direction) {
            return () -> update(direction);
        }

        private void update(String direction) {
            try {
                log.info("Start updating competition list for direction: " + direction);
                directionToParticipantMap.put(direction, loadCompetitionsList(direction));
            } catch (IOException e) {
                log.error("Unable to update competition list for direction: " + direction, e);
            }
        }

    }
}
