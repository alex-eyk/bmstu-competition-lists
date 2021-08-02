package com.ximand.bot.mgtulists.telegram.handler.impl.command;

import com.ximand.bot.mgtulists.telegram.handler.CommandHandler;
import com.ximand.bot.mgtulists.telegram.object.TelegramUser;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class StartCommandHandler extends CommandHandler {

    private static final String NAME = "start";

    private final ReplyMessageProvider replyMessageProvider;
    private final TelegramUserRepository userRepository;

    @Autowired
    public StartCommandHandler(TelegramUserRepository userRepository, ReplyMessageProvider replyMessageProvider) {
        super(NAME);
        this.replyMessageProvider = replyMessageProvider;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(Message message) {
        val id = message.getChatId();
        String replyKey;
        if (userRepository.existsById(id)) {
            replyKey = "start";
        } else {
            userRepository.save(new TelegramUser(id, UserActivity.REGISTER));
            replyKey = "start_first_time";
        }
        return createReply(id, replyKey);
    }

    private SendMessage createReply(long id, String replyKey) {
        val text = replyMessageProvider.getMessage(replyKey);
        return getSimpleSendMessage(id, text);
    }
}
