package com.ximand.bot.mgtulists.service.lists.impl;

import com.ximand.bot.mgtulists.config.WebConfig;
import com.ximand.bot.mgtulists.model.CompetitionType;
import com.ximand.bot.mgtulists.service.lists.AbstractCompetitionListsService;
import com.ximand.bot.mgtulists.service.pdf.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractCompetitionsListService extends AbstractCompetitionListsService {

    private static final CompetitionType COMPETITION_TYPE = CompetitionType.CONTRACT;

    private final String url;

    @Autowired
    protected ContractCompetitionsListService(WebConfig webConfig, PdfService pdfService) {
        super(webConfig, pdfService);
        this.url = webConfig.getContractUrl();
    }

    @Override
    protected String getBaseUrl() {
        return url;
    }

    @Override
    public CompetitionType getCompetitionType() {
        return COMPETITION_TYPE;
    }
}
