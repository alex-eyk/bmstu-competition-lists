package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.handler.impl.command.UnknownCommandHandler;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ThreadSafe
public final class CommandHandlerProvider {

    private final Map<String, CommandHandler> commandHandlerMap = new ConcurrentHashMap<>();

    @Autowired
    public CommandHandlerProvider(List<CommandHandler> commandHandlers) {
        for (val handler : commandHandlers) {
            commandHandlerMap.put(handler.getCommand(), handler);
        }
    }

    public CommandHandler getHandlerForCommand(String command) {
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        val handler = commandHandlerMap.get(command);
        if (handler != null) {
            return handler;
        } else {
            return commandHandlerMap.get(UnknownCommandHandler.NAME);
        }
    }

}
