package com.ximand.bot.mgtulists.telegram.handler;

public abstract class CommandHandler extends AbstractHandler {

    private final String command;

    public CommandHandler(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
