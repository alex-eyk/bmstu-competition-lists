package com.ximand.bot.mgtulists.model;

import lombok.Getter;

import java.util.Date;
import java.util.LinkedList;

public class CompetitionList extends LinkedList<Participant> {

    @Getter
    private final int places;
    @Getter
    private final Date created;

    public CompetitionList(int totalPlaces) {
        this.places = totalPlaces;
        this.created = new Date();
    }

    public CompetitionList(int places, Date created) {
        this.places = places;
        this.created = created;
    }

}
