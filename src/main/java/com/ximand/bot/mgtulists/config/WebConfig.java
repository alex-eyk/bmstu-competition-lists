package com.ximand.bot.mgtulists.config;

import com.ximand.properties.PropertiesPath;
import com.ximand.properties.Property;
import lombok.Data;

@Data
@PropertiesPath("jarpath:/web-config.properties")
public class WebConfig {

    @Property(name = "url.lists.budget", defaultValue = "https://priem.bmstu.ru/lists/upload/enrollees/first/moscow-1/")
    private String budgetUrl;

    @Property(name = "url.lists.contract", defaultValue = "https://priem.bmstu.ru/lists/upload/enrollees/first/moscow-0/")
    private String contractUrl;

    @Property(name = "web.update_lists.thread", defaultValue = "1")
    private int updateThreads;

    @Property(name = "web.update_lists.delay", defaultValue = "30")
    private int updateDelay;

}
