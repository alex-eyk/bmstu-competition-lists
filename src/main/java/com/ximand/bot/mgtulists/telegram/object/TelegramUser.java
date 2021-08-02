package com.ximand.bot.mgtulists.telegram.object;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ximand931
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {

    @Id
    @Field("_id")
    private long id;

    @Field("activity")
    private UserActivity activity;

    @Field("registration_number")
    private String registrationNumber;

    @Field("last_requests")
    private Set<String> lastRequests;

    public TelegramUser(long id, UserActivity activity) {
        this.id = id;
        this.activity = activity;
        this.registrationNumber = "";
        this.lastRequests = new HashSet<>();
    }
}
