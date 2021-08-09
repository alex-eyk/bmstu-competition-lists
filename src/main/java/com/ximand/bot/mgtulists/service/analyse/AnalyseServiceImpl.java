package com.ximand.bot.mgtulists.service.analyse;

import com.ximand.bot.mgtulists.model.CompetitionList;
import com.ximand.bot.mgtulists.model.Participant;
import com.ximand.bot.mgtulists.model.Analytics;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.ThreadSafe;

@Service
@ThreadSafe
public class AnalyseServiceImpl implements AnalyseService {

    @Override
    public Analytics analyse(String registerNumber, CompetitionList competitionList)
            throws IllegalArgumentException, ParticipantNotFoundException {
        if (registerNumber == null || registerNumber.isEmpty()) {
            throw new IllegalArgumentException();
        }
        val analytics = new Analytics();
        analytics.setPlaces(competitionList.getPlaces());
        for (val participant : competitionList) {
            if (participant.getRegistrationNumber().equals(registerNumber)) {
                analytics.setPosition(participant.getPosition());
                return analytics;
            } else {
                updateAnalytics(analytics, participant);
            }
        }
        throw new ParticipantNotFoundException();
    }

    private void updateAnalytics(Analytics analytics, Participant participant) {
        if (participant.isConsentToEnrollment()) {
            analytics.incrementGaveConsent();
        } else if (isGaveConsentAnotherDirections(participant)) {
            analytics.incrementConsentAnotherDirections();
        }
    }

    /**
     * Абитуренит подал согласие на зачисление на другое направление если он не подал согласие на зачисление
     * на данное направление, но при этом прогноз приемной комиссии не является пустым.
     *
     * @return Подал ли абитуриент согласие на зачисление на другое направление.
     */
    private boolean isGaveConsentAnotherDirections(Participant participant) {
        return participant.isConsentToEnrollment() == false && participant.getForecast() != null;
    }

}
