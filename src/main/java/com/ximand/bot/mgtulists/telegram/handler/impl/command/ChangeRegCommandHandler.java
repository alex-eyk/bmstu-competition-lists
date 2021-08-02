package com.ximand.bot.mgtulists.telegram.handler.impl.command;

import com.ximand.bot.mgtulists.telegram.handler.CommandHandler;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ChangeRegCommandHandler extends CommandHandler {

    private static final String NAME = "change_reg";

    private final ReplyMessageProvider replyMessageProvider;
    private final TelegramUserRepository userRepository;

    @Autowired
    public ChangeRegCommandHandler(ReplyMessageProvider replyMessageProvider, TelegramUserRepository userRepository) {
        super(NAME);
        this.replyMessageProvider = replyMessageProvider;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(Message message) {
        val id = message.getChatId();
        val user = userRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);
        user.setActivity(UserActivity.REGISTER);
        userRepository.save(user);
        return getSimpleSendMessage(id, replyMessageProvider.getMessage("input_new_registration_number"));
    }
}
