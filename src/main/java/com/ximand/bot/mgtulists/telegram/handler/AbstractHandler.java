package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.util.ReplyUtils;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Ximand931
 */
public abstract class AbstractHandler {

    public Runnable getRunnable(Message message, ResultListener<SendMessage> resultListener) {
        return () -> {
            val result = handle(message);
            resultListener.onResult(result);
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

    protected SendMessage getSimpleSendMessage(long chatId, String text, boolean markdown) {
        val sendMessage = getSimpleSendMessage(chatId, text);
        sendMessage.enableMarkdown(markdown);
        return sendMessage;
    }

}
