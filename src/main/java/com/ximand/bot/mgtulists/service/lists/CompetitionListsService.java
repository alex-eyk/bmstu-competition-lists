package com.ximand.bot.mgtulists.service.lists;

import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.model.CompetitionType;

import java.io.IOException;

public interface CompetitionListsService {

    CompetitionList getListForDirection(String direction) throws IllegalArgumentException, IOException;

    CompetitionType getCompetitionType();

}
