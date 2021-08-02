package com.ximand.bot.mgtulists.telegram.reply;

/**
 * Чтобы не хранить сообщения для ответа прямо в коде приложения, а также упростить их редактирование, они будут
 * храниться в отдельном файле, имея ключи и значения. Такая реализация хранения ответов дает возможность также
 * реализовать поддержку нескольких языков.
 * @author Ximand931
 */
public interface ReplyMessageProvider {

    /**
     * @return Ответное сообщение для текущего ключа
     * @throws IllegalArgumentException В случае неверно предоставленного ключа будет брошено исключение
     */
    String getMessage(String key) throws IllegalArgumentException;

}
