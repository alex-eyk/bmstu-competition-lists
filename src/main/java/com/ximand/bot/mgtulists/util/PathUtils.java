package com.ximand.bot.mgtulists.util;

public final class PathUtils {

    private PathUtils() {

    }

    /**
     * При использовании файлов, находящихся в каталоге с Jar-файлов при запуске программы из терминала путем к
     * таким конфигурационным файлам будет являться путь в терминале на момет запуска. Поэтому перед запуском необходимо
     * перейти в каталог с jar-файлом.
     * <pre>
     *     cd 'jar_file_location'
     *     sudo java -jar /TelegramBot.jar
     * </pre>
     * @return Абсолютный путь к исполняемому Jar-файлу.
     */
    public static String getJarLocation() {
        return PathUtils.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
    }

}
