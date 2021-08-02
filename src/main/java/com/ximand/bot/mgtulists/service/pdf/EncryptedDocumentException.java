package com.ximand.bot.mgtulists.service.pdf;

public class EncryptedDocumentException extends RuntimeException {

    private static final String MESSAGE = "Unable to read encrypted document";

    public EncryptedDocumentException() {
        super(MESSAGE);
    }
}
