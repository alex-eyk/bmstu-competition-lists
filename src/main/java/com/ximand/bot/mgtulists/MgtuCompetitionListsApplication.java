package com.ximand.bot.mgtulists;

import com.ximand.bot.mgtulists.config.WebConfig;
import com.ximand.bot.mgtulists.telegram.Config;
import com.ximand.properties.PropertiesProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * При использовании файлов, находящихся в каталоге с Jar-файлов при запуске программы из терминала путем к
 * таким конфигурационным файлам будет являться путь в терминале на момет запуска. Поэтому перед запуском необходимо
 * перейти в каталог с jar-файлом.
 * <pre>
 *     cd 'jar_file_location'
 *     sudo java -jar /TelegramBot.jar
 * </pre>
 *
 * @author Ximand931
 */
@SpringBootApplication
public class MgtuCompetitionListsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgtuCompetitionListsApplication.class, args);
    }

    @Bean
    public Config getConfig() {
        return new PropertiesProvider().createInstance(Config.class);
    }

    @Bean
    public WebConfig webConfig() {
        return new PropertiesProvider().createInstance(WebConfig.class);
    }

}
