package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.reply.ReplyMessageProvider;

public abstract class CommandHandler extends AbstractHandler {

    private final String command;

    public CommandHandler(String command, ReplyMessageProvider replyProvider) {
        super(replyProvider);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
