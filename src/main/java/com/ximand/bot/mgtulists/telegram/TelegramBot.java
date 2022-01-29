package com.ximand.bot.mgtulists.telegram;

import com.ximand.bot.mgtulists.telegram.handler.HandlerProvider;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final ExecutorService executorService;

    private final HandlerProvider handlerProvider;

    @Value("${telegram.token}")
    private String token;
    @Value("${telegram.bot_name}")
    private String username;

    @Autowired
    public TelegramBot(ServerConfig config, HandlerProvider handlerProvider) {
        this.executorService = Executors.newFixedThreadPool(config.getThreads());
        this.handlerProvider = handlerProvider;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        val message = update.getMessage();
        val messageHandler = handlerProvider.getHandlerForMessage(message);
        val task = messageHandler.getRunnable(message, this::sendMessage);
        executorService.submit(task);
    }

    private synchronized void sendMessage(SendMessage sendMessage) {
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception while async send message", e);
        }
    }
}
