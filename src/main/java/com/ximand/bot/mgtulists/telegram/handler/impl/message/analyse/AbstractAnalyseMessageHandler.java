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
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractAnalyseMessageHandler extends MessageHandler {

    private static final String DIRECTION_PATTERN = "^\\d{2}.\\d{2}.\\d{2}$";
    private static final String CREATE_DATE_PATTERN = "hh:mm:ss";

    private final ReplyMessageProvider replyMessageProvider;
    private final TelegramUserRepository userRepository;
    private final CompetitionListsService competitionListsService;
    private final AnalyseService analyseService;

    public AbstractAnalyseMessageHandler(UserActivity userActivity, ReplyMessageProvider replyMessageProvider,
                                         TelegramUserRepository userRepository, CompetitionListsService competitionListsService, AnalyseService analyseService) {
        super(userActivity);
        this.replyMessageProvider = replyMessageProvider;
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
                return getSimpleSendMessage(id, getReplyText(analytics, list));
            } catch (IOException e) {
                val replyText = replyMessageProvider.getMessage("load_list_error");
                return getSimpleSendMessage(id, replyText);
            } catch (ParticipantNotFoundException e) {
                val replyText = replyMessageProvider.getMessage("participant_not_found");
                return getSimpleSendMessage(id, replyText);
            }
        } else {
            val replyText = replyMessageProvider.getMessage("wrong_direction");
            return getSimpleSendMessage(id, replyText);
        }
    }

    private String getReplyText(Analytics analytics, CompetitionList list) {
        val formatReply = replyMessageProvider.getMessage("analyse_format");
        val dayOfWeek = replyMessageProvider.getMessage(
                "dow_accusative_" + Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        );
        val created = new SimpleDateFormat(CREATE_DATE_PATTERN).format(list.getCreated());
        return String.format(formatReply, analytics.getPosition(), analytics.getNumOfPlaces(),
                analytics.getGaveConsent(), analytics.getGaveConsentAnotherDirections(), dayOfWeek, created);
    }

    private void updateUser(TelegramUser user, String direction) {
        user.setActivity(UserActivity.NONE);
        user.getLastRequests().add(direction);
        userRepository.save(user);
    }
}
