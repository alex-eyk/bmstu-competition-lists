package com.ximand.bot.mgtulists.telegram;

import com.ximand.properties.PropertiesPath;
import com.ximand.properties.Property;
import lombok.Data;

@Data
@PropertiesPath("jarpath:/app.properties")
public final class Config {

    @Property(name = "server.threads", defaultValue = "4")
    private int threads;

}
