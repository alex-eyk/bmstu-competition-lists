package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import com.ximand.bot.mgtulists.util.ReplyUtils;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Ximand931
 */
public abstract class AbstractHandler {

    private final ReplyMessageProvider replyProvider;

    protected AbstractHandler(ReplyMessageProvider replyProvider) {
        this.replyProvider = replyProvider;
    }

    protected ReplyMessageProvider getReplyProvider() {
        return replyProvider;
    }

    public Runnable getRunnable(Message message, ResultListener<SendMessage> resultListener) {
        return () -> {
            try {
                val result = handle(message);
                resultListener.onResult(result);
            } catch (RuntimeException e) {
                resultListener.onResult(getSimpleErrorMessage(message.getChatId()));
            }
        };
    }

    public abstract SendMessage handle(Message message);

    protected SendMessage getSimpleSendMessage(long chatId, String text) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .replyMarkup(ReplyUtils.getRemoveReplyKeyboard())
                .text(text)
                .build();
    }

    protected SendMessage getSimpleErrorMessage(long chatId) {
        return getSimpleSendMessage(chatId, replyProvider.getMessage("error"));
    }

    protected SendMessage getSimpleSendMessage(long chatId, String text, boolean markdown) {
        val sendMessage = getSimpleSendMessage(chatId, text);
        sendMessage.enableMarkdown(markdown);
        return sendMessage;
    }

}
