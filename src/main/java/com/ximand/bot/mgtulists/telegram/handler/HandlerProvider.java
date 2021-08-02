package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.repository.TelegramUserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public final class HandlerProvider {

    private final MessageHandlerProvider messageHandlerProvider;
    private final CommandHandlerProvider commandHandlerProvider;

    private final TelegramUserRepository userRepository;

    @Autowired
    public HandlerProvider(MessageHandlerProvider messageHandlerProvider,
                           CommandHandlerProvider commandHandlerProvider, TelegramUserRepository userRepository) {
        this.messageHandlerProvider = messageHandlerProvider;
        this.commandHandlerProvider = commandHandlerProvider;
        this.userRepository = userRepository;
    }

    public AbstractHandler getHandlerForMessage(Message message) {
        if (isCommand(message)) {
            return commandHandlerProvider.getHandlerForCommand(message.getText());
        } else {
            val user = userRepository
                    .findById(message.getChatId())
                    .orElseThrow(IllegalStateException::new);
            return messageHandlerProvider.getMessageHandlerForActivity(user.getActivity());
        }
    }

    private boolean isCommand(Message message) {
        return message.getText() != null && message.getText().startsWith("/");
    }

}
