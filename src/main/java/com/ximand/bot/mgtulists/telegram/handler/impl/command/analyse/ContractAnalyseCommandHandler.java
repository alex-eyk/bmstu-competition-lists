package com.ximand.bot.mgtulists.telegram.handler.impl.command.analyse;

import com.ximand.bot.mgtulists.telegram.handler.impl.command.analyse.AbstractAnalyseCommandHandler;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ContractAnalyseCommandHandler extends AbstractAnalyseCommandHandler {

    private static final String NAME = "analyse_contract";

    @Autowired
    public ContractAnalyseCommandHandler(TelegramUserRepository userRepository,
                                         ReplyMessageProvider replyMessageProvider) {
        super(NAME, userRepository, replyMessageProvider);
    }

    @Override
    public SendMessage handle(Message message) {
        return super.handle(message, UserActivity.INPUT_DIRECTION_CONTRACT);
    }
}
