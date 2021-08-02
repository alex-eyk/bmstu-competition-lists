package com.ximand.bot.mgtulists.telegram.handler;

@FunctionalInterface
public interface ResultListener<T> {

    void onResult(T result);

}
