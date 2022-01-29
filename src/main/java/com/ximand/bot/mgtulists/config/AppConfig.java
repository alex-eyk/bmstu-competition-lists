package com.ximand.bot.mgtulists.config;

import com.ximand.bot.mgtulists.telegram.ServerConfig;
import com.ximand.properties.PropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ServerConfig config() {
        return new PropertiesProvider().createInstance(ServerConfig.class);
    }

    @Bean
    public WebConfig webConfig() {
        return new PropertiesProvider().createInstance(WebConfig.class);
    }

}
