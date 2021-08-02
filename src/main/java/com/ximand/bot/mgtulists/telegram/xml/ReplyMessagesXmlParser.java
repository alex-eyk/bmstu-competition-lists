package com.ximand.bot.mgtulists.telegram.xml;

import lombok.val;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReplyMessagesXmlParser extends AbstractXmlParser<Map<String, String>> {

    @Override
    protected BaseSaxEventHandler<Map<String, String>> createSaxEventHandler() {
        return new ReplyMessageSaxEventHandler();
    }

    static class ReplyMessageSaxEventHandler extends BaseSaxEventHandler<Map<String, String>> {

        private Map<String, String> keyToMessageMap;

        /**
         * Текущий ключ для получения сообщения. Может быть равен null в случае, если в данный момент времени не
         * рассматривается необходимый элемент `reply`
         */
        private String key;

        /**
         * Текущее сообщение для ключа. Может быть равен null в случае, если в данный момент времени не
         * рассматривается необходимый элемент `reply`
         */
        private String value;

        @Override
        public void startDocument() {
            this.keyToMessageMap = new HashMap<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("reply")) {
                val key = attributes.getValue("key");
                if (key != null) {
                    this.key = key;
                } else {
                    throw new SAXException("No value for param 'key'");
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (this.key != null) {
                val read = new String(ch, start, length);
                if (value != null) {
                    this.value += read;
                } else {
                    this.value = read;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("reply")) {
                this.keyToMessageMap.put(key, value);
                this.key = null;
                this.value = null;
            }
        }

        @Override
        public void endDocument() {
            super.setResult(keyToMessageMap);
        }
    }
}
