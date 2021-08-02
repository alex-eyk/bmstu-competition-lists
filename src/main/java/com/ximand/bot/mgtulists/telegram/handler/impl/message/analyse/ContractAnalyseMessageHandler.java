package com.ximand.bot.mgtulists.telegram.handler.impl.message.analyse;

import com.ximand.bot.mgtulists.service.analyse.AnalyseService;
import com.ximand.bot.mgtulists.service.lists.impl.ContractCompetitionsListService;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractAnalyseMessageHandler extends AbstractAnalyseMessageHandler {

    private static final UserActivity ACTIVITY = UserActivity.INPUT_DIRECTION_CONTRACT;

    @Autowired
    public ContractAnalyseMessageHandler(ReplyMessageProvider replyMessageProvider, TelegramUserRepository userRepository,
                                         ContractCompetitionsListService competitionListsService, AnalyseService analyseService) {
        super(ACTIVITY, replyMessageProvider, userRepository, competitionListsService, analyseService);
    }
}
