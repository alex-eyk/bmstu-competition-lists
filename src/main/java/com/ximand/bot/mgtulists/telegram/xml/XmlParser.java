package com.ximand.bot.mgtulists.telegram.xml;

import com.ximand.bot.mgtulists.telegram.xml.exception.MalformedXmlException;

import java.io.InputStream;

/**
 * Цель парсера - быстро и эффективно конвертировать небольшой файл в объект определенного типа
 *
 * @param <T> Объект, который нужно получить на выходе
 */
public interface XmlParser<T> {

    T parseFile(InputStream xmlInputStream) throws MalformedXmlException;

}
