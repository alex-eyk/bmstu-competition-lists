package com.ximand.bot.mgtulists.telegram.xml;

import com.ximand.bot.mgtulists.telegram.xml.exception.MalformedXmlException;
import lombok.val;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public abstract class AbstractXmlParser<T> implements XmlParser<T> {

    /**
     * @param xmlPath Полный путь к XML-файлу
     * @throws MalformedXmlException Будет брошено в случае ошибки в XML-файле
     * @throws IllegalStateException Будет брошено в случае ошибки с конфигурацией SAX-парсера или в случае ошибки при
     *                               чтении файла
     */
    @Override
    public T parseFile(String xmlPath) throws MalformedXmlException {
        val saxParserFactory = SAXParserFactory.newInstance();
        try {
            val saxParser = saxParserFactory.newSAXParser();
            val handler = createSaxEventHandler();
            saxParser.parse(new File(xmlPath), handler);
            return handler.getResult();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Exception with parser configurator while parsing xml-file: " + xmlPath, e);
        } catch (IOException e) {
            throw new IllegalStateException("Exception while read file: " + xmlPath, e);
        } catch (SAXException e) {
            throw new MalformedXmlException(e);
        }
    }

    protected abstract BaseSaxEventHandler<T> createSaxEventHandler();

    public static abstract class BaseSaxEventHandler<T> extends DefaultHandler {

        private T result;

        /**
         * @return Резлультат парсинга XML-файла в объект
         * @throws IllegalStateException Будет брошено в случае, если результат будет равняться null. Это может
         *                               произойти в случае, если парсинг прошел некоректно или метод
         *                               setResult(T result) не был вызван.
         */
        public T getResult() {
            if (result != null) {
                return result;
            } else {
                throw new IllegalStateException("Result of parsing: null, make sure that method setResult(T result) was " +
                        "called and parsing has been completed by the time of the call");
            }
        }

        protected void setResult(T result) {
            this.result = result;
        }
    }

}
