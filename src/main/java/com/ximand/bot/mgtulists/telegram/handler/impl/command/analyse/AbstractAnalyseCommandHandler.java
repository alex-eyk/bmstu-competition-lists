package com.ximand.bot.mgtulists.telegram.handler.impl.command.analyse;

import com.ximand.bot.mgtulists.telegram.handler.CommandHandler;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import com.ximand.bot.mgtulists.util.ReplyUtils;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractAnalyseCommandHandler extends CommandHandler {

    private final TelegramUserRepository userRepository;
    private final ReplyMessageProvider replyMessageProvider;


    public AbstractAnalyseCommandHandler(String command, TelegramUserRepository userRepository,
                                         ReplyMessageProvider replyMessageProvider) {
        super(command);
        this.userRepository = userRepository;
        this.replyMessageProvider = replyMessageProvider;
    }

    protected SendMessage handle(Message message, UserActivity activity) {
        val id = message.getChatId();
        val user = userRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
        user.setActivity(activity);
        userRepository.save(user);
        val replyText = replyMessageProvider.getMessage("input_direction");
        return SendMessage.builder()
                .chatId(String.valueOf(id))
                .replyMarkup(ReplyUtils.getReplyKeyboardForSet(user.getLastRequests()))
                .text(replyText)
                .build();
    }
}
