package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;

public abstract class MessageHandler extends AbstractHandler {

    private final UserActivity userActivity;

    public MessageHandler(UserActivity userActivity, ReplyMessageProvider replyProvider) {
        super(replyProvider);
        this.userActivity = userActivity;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }
}
