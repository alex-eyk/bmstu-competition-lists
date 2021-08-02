package com.ximand.bot.mgtulists.telegram.handler;

import com.ximand.bot.mgtulists.telegram.object.UserActivity;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ThreadSafe
public final class MessageHandlerProvider {

    private final Map<UserActivity, MessageHandler> activityToMessageHandlerMap = new ConcurrentHashMap<>();

    @Autowired
    public MessageHandlerProvider(List<MessageHandler> messageHandlers) {
        for (val handler : messageHandlers) {
            activityToMessageHandlerMap.put(handler.getUserActivity(), handler);
        }
    }

    public MessageHandler getMessageHandlerForActivity(UserActivity activity) {
        val handler = activityToMessageHandlerMap.get(activity);
        if (handler != null) {
            return handler;
        } else {
            throw new IllegalStateException("No handler found for activity: " + activity);
        }
    }
}
