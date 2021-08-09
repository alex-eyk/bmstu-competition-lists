package com.ximand.bot.mgtulists.telegram.handler.impl.command;

import com.ximand.bot.mgtulists.telegram.handler.CommandHandler;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UnknownCommandHandler extends CommandHandler {

    public static final String NAME = "unknown";

    @Autowired
    public UnknownCommandHandler(ReplyMessageProvider replyMessageProvider) {
        super(NAME, replyMessageProvider);
    }

    @Override
    public SendMessage handle(Message message) {
        val chatId = message.getChatId();
        val reply = getReplyProvider().getMessage("unknown_command");
        return getSimpleSendMessage(chatId, reply);
    }
}
