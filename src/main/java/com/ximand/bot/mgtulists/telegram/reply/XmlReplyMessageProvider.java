package com.ximand.bot.mgtulists.telegram.reply;

import com.ximand.bot.mgtulists.telegram.xml.XmlParser;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Чтобы не хранить сообщения для ответа прямо в коде приложения, а также упростить их редактирование, они будут
 * храниться в отдельном файле, храня ключи и значения. Такая реализация хранения ответов дает возможность также
 * реализовать поддержку нескольких языков.
 *
 * Данная реализация считывает все ключи и ответы из XML-файла replies.xml. XML-файл состоит из корневого
 * элемента - replies, в котором находятся элементы reply. Каждый такой элемент обязательно должен иметь параметр
 * `key`, а так же значение.
 *
 * @author Ximand931
 */
@Service
public class XmlReplyMessageProvider implements ReplyMessageProvider {

    private static final String FILE_NAME = "replies.xml";
    private final Map<String, String> keyToMessageMap;

    @Autowired
    public XmlReplyMessageProvider(XmlParser<Map<String, String>> toRepliesParser) {
        val resource = getClass().getClassLoader().getResource(FILE_NAME);
        if (resource != null) {
            val replies = toRepliesParser.parseFile(resource.getPath());
            keyToMessageMap = new ConcurrentHashMap<>(replies);
        } else {
            throw new IllegalStateException("File not found, name: " + FILE_NAME);
        }
    }

    @Override
    public String getMessage(String key) throws IllegalArgumentException {
        val message = keyToMessageMap.get(key);
        if (message != null) {
            return message;
        } else {
            throw new IllegalArgumentException("No message with key: " + key);
        }
    }

}
