package com.ximand.bot.mgtulists.service.pdf;

import com.ximand.properties.JarUtils;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import lombok.var;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log4j2
public class PdfServiceImpl implements PdfService {

    private static final String CREATE_DATE_PATTERN = "yyyyMMddHHmmss";

    /**
     * Для создания конкурсных списком МГТУ используют библиотеку от Apache, которая сохраняет в файле следующие
     * данные:
     * <pre>
     *     /Creator (Apache FOP Version 1.0)
     *     /Producer (Apache FOP Version 1.0)
     *     /CreationDate (D:20210730203626+04'00')
     * </pre>
     * Отсюда появляется возможно получить дату создания pdf-файла.
     *
     * @param url Полная ссылка на pdf-файл
     * @return Дата создания pdf-документа
     */
    @Override
    public Date loadCreateDate(String url) throws IOException {
        val fileBufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        var line = fileBufferedReader.readLine();
        while (line != null) {
            if (line.startsWith("/CreationDate")) {
                return getDateFromLine(line);
            }
            line = fileBufferedReader.readLine();
        }
        throw new IllegalStateException("Unable to find create data line, url: " + url);
    }

    private Date getDateFromLine(String line) {
        val partWithDate = line.substring(17, 31);
        try {
            val createDateUtc4 = new SimpleDateFormat(CREATE_DATE_PATTERN).parse(partWithDate);
            return new Date(createDateUtc4.getTime() - 60 * 60 * 1000);
        } catch (ParseException e) {
            throw new IllegalStateException("Unable to load create data, pattern: " + CREATE_DATE_PATTERN
                    + "; date: " + partWithDate, e);
        }
    }

    @Override
    public String loadText(String url) throws IOException, EncryptedDocumentException {
        val pdfFile = downloadFile(url);
        try (val document = PDDocument.load(pdfFile)) {
            val successDelete = pdfFile.delete();
            if (successDelete == false) {
                log.error("Unable to remove file: " + pdfFile);
            }
            if (document.isEncrypted() == false) {
                val stripper = new PDFTextStripper();
                return stripper.getText(document);
            } else {
                throw new EncryptedDocumentException();
            }
        }
    }

    private File downloadFile(String url) throws IOException {
        val path = Paths.get(getDownloadedFilePath(url));
        try (val inputStream = new URL(url).openStream()) {
            log.info("Downloading file from url: " + url);
            try {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (SecurityException | FileAlreadyExistsException | DirectoryNotEmptyException e) {
                log.error("Unable to download file", e);
            }
            return path.toFile();
        }
    }

    private String getDownloadedFilePath(String url) {
        val filename = url.substring(url.lastIndexOf("/") + 1);
        return JarUtils.getFileFromJarDirectoryPath(filename, getClass());
    }

}
