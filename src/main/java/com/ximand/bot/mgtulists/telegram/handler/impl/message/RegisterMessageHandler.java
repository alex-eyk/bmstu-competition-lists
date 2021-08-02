package com.ximand.bot.mgtulists.telegram.handler.impl.message;

import com.ximand.bot.mgtulists.telegram.handler.MessageHandler;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RegisterMessageHandler extends MessageHandler {

    private static final UserActivity ACTIVITY = UserActivity.REGISTER;

    private static final String REG_NUMBER_PATTERN = "^(.\\d{4})|(\\d{3}-\\d{3}-\\d{3} \\d{2})$";

    private final TelegramUserRepository userRepository;
    private final ReplyMessageProvider replyMessageProvider;

    public RegisterMessageHandler(TelegramUserRepository userRepository, ReplyMessageProvider replyMessageProvider) {
        super(ACTIVITY);
        this.userRepository = userRepository;
        this.replyMessageProvider = replyMessageProvider;
    }

    @Override
    public SendMessage handle(Message message) {
        val id = message.getChatId();
        val reg = message.getText();
        if (reg != null && reg.matches(REG_NUMBER_PATTERN)) {
            val user = userRepository.findById(id)
                    .orElseThrow(IllegalStateException::new);
            user.setActivity(UserActivity.NONE);
            user.setRegistrationNumber(reg);
            userRepository.save(user);
            return getSimpleSendMessage(id, replyMessageProvider.getMessage("registration_number_saved"));
        } else {
            return getSimpleSendMessage(id, replyMessageProvider.getMessage("wrong_registration_number"));
        }
    }
}
