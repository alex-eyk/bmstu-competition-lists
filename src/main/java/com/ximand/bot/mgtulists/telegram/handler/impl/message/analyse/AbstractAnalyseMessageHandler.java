package com.ximand.bot.mgtulists.telegram.handler.impl.message.analyse;

import com.ximand.bot.mgtulists.model.Analytics;
import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.service.analyse.AnalyseService;
import com.ximand.bot.mgtulists.service.analyse.ParticipantNotFoundException;
import com.ximand.bot.mgtulists.service.lists.CompetitionListsService;
import com.ximand.bot.mgtulists.telegram.handler.MessageHandler;
import com.ximand.bot.mgtulists.telegram.object.TelegramUser;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.reply.object.AnalyticsReply;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import lombok.var;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.Calendar;

public abstract class AbstractAnalyseMessageHandler extends MessageHandler {

    private static final String DIRECTION_PATTERN = "^\\d{2}.\\d{2}.\\d{2}$";

    private final TelegramUserRepository userRepository;
    private final CompetitionListsService competitionListsService;
    private final AnalyseService analyseService;

    public AbstractAnalyseMessageHandler(UserActivity userActivity, ReplyMessageProvider replyMessageProvider,
                                         TelegramUserRepository userRepository, CompetitionListsService competitionListsService, AnalyseService analyseService) {
        super(userActivity, replyMessageProvider);
        this.userRepository = userRepository;
        this.competitionListsService = competitionListsService;
        this.analyseService = analyseService;
    }

    @Override
    public SendMessage handle(Message message) {
        val id = message.getChatId();
        val direction = message.getText();
        if (direction != null && direction.matches(DIRECTION_PATTERN)) {
            val user = userRepository.findById(id)
                    .orElseThrow(IllegalStateException::new);
            val regNum = user.getRegistrationNumber();
            try {
                val list = competitionListsService.getListForDirection(direction);
                val analytics = analyseService.analyse(regNum, list);
                updateUser(user, direction);
                return getSimpleSendMessage(id, getReplyText(analytics, list), true);
            } catch (IOException e) {
                val replyText = getReplyProvider().getMessage("load_list_error");
                return getSimpleSendMessage(id, replyText);
            } catch (ParticipantNotFoundException e) {
                val replyText = getReplyProvider().getMessage("participant_not_found");
                return getSimpleSendMessage(id, replyText);
            }
        } else {
            val replyText = getReplyProvider().getMessage("wrong_direction");
            return getSimpleSendMessage(id, replyText);
        }
    }

    private String getReplyText(Analytics analytics, CompetitionList list) {
        val formatReply = getReplyProvider().getMessage("analyse_format");
        val dayOfWeek = getDayOfWeek(list);
        var replyText = analytics.isConsent() ? getReplyProvider().getMessage("consent_gave") : "";
        return replyText + AnalyticsReply.builder()
                .format(formatReply)
                .position(analytics.getPosition())
                .places(analytics.getPlaces())
                .gaveConsent(analytics.getGaveConsent())
                .gaveConsentAnotherDirections(analytics.getGaveConsentAnotherDirections())
                .updated(list.getUpdated())
                .updatedDayOfWeek(dayOfWeek)
                .build()
                .toString();
    }

    private String getDayOfWeek(CompetitionList competitionList) {
        val calendar = Calendar.getInstance();
        calendar.setTime(competitionList.getUpdated());
        return getReplyProvider().getMessage(
                "dow_accusative_" + calendar.get(Calendar.DAY_OF_WEEK)
        );
    }

    private void updateUser(TelegramUser user, String direction) {
        user.setActivity(UserActivity.NONE);
        user.getLastRequests().add(direction);
        userRepository.save(user);
    }
}
