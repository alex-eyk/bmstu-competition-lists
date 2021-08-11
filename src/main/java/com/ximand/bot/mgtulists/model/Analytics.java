package com.ximand.bot.mgtulists.model;

import lombok.Data;

@Data
public class Analytics {

    private int gaveConsent = 0;
    private int gaveConsentAnotherDirections = 0;
    private boolean consent = false;
    private int position;
    private int places;

    public void incrementGaveConsent() {
        gaveConsent++;
    }

    public void incrementConsentAnotherDirections() {
        gaveConsentAnotherDirections++;
    }

}