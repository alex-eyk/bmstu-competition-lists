package com.ximand.bot.mgtulists.telegram.handler.impl.command;

import com.ximand.bot.mgtulists.telegram.handler.CommandHandler;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ShowRegCommandHandler extends CommandHandler {

    private static final String NAME = "show_reg";

    private final TelegramUserRepository userRepository;

    @Autowired
    public ShowRegCommandHandler(ReplyMessageProvider replyProvider, TelegramUserRepository userRepository) {
        super(NAME, replyProvider);
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(Message message) {
        val id = message.getChatId();
        val user = userRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        val replyText = getReplyProvider().getMessage("your_reg_number_is") + user.getRegistrationNumber();
        return getSimpleSendMessage(id, replyText);
    }
}
