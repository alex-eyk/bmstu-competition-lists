package com.ximand.bot.mgtulists.telegram.reply.object;

import lombok.Builder;
import lombok.val;

import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
public class AnalyticsReply extends FormatableReply {

    private static final String UPDATED_PATTERN = "HH:mm:ss";

    private final String format;
    private final int position;
    private final int places;
    private final int gaveConsent;
    private final int gaveConsentAnotherDirections;
    private final Date updated;
    private final String updatedDayOfWeek;

    @Override
    public String toString() {
        val consentRate = calculateConsentRate();
        val created = new SimpleDateFormat(UPDATED_PATTERN).format(updated);
        return String.format(
                format, position, places, gaveConsent, consentRate,
                gaveConsentAnotherDirections, updatedDayOfWeek, created
        );
    }

    private float calculateConsentRate() {
        return ((float) gaveConsent / places) * 100f;
    }
}
