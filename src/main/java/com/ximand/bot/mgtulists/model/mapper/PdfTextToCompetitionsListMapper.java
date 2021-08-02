package com.ximand.bot.mgtulists.model.mapper;

import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.model.Participant;
import lombok.val;

public class PdfTextToCompetitionsListMapper implements Mapper<String, CompetitionList> {

    private final Mapper<String, Participant> pdfLineToParticipant = new PdfLineToParticipantMapper();

    @Override
    public CompetitionList map(String from) {
        val competitionList = new CompetitionList(1);
        val lines = from.split("\n");
        for (val line : lines) {

        }
        return null;
    }

}
