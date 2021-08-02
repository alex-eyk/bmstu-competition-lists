package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.object.UserActivity;

public abstract class MessageHandler extends AbstractHandler {

    private final UserActivity userActivity;

    public MessageHandler(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }
}
