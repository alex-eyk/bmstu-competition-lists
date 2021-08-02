package com.ximand.bot.mgtulists.service.pdf;

import java.io.IOException;
import java.util.Date;

public interface PdfService {

    Date loadCreateDate(String url) throws IOException;

    String loadText(String url) throws IOException, EncryptedDocumentException;

}
