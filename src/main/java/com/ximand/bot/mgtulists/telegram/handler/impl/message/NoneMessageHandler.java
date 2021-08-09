package com.ximand.bot.mgtulists.telegram.handler.impl.message;

import com.ximand.bot.mgtulists.telegram.handler.MessageHandler;
import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class NoneMessageHandler extends MessageHandler {

    public static final UserActivity ACTIVITY = UserActivity.NONE;

    @Autowired
    public NoneMessageHandler(ReplyMessageProvider replyMessageProvider) {
        super(ACTIVITY, replyMessageProvider);
    }

    @Override
    public SendMessage handle(Message message) {
        return getSimpleSendMessage(
                message.getChatId(), getReplyProvider().getMessage("none_activity")
        );
    }
}
