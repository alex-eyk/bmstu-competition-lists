package com.ximand.bot.mgtulists.service.analyse;

import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.model.Analytics;

public interface AnalyseService {

    Analytics analyse(String registerNumber, CompetitionList competitionList);

}
