package com.ximand.bot.mgtulists.model.mapper;

import com.ximand.bot.mgtulists.model.Participant;
import com.ximand.bot.mgtulists.util.TextUtils;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;

@Component
public class PdfLineToParticipantMapper implements Mapper<String, Participant> {

    private static final String INSURANCE_PATTERN = "\\d{3}-\\d{3}-\\d{3} \\d{2}";
    private static final String REG_NUMBER_PATTERN = ".\\d{4}";

    @Override
    public Participant map(String line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException();
        }
        val regNumber = findRegNumber(line);
        val lineWithoutRegNumber = line.replace(regNumber + " ", "");
        val participantParams = lineWithoutRegNumber.split(" ");
        return Participant.builder()
                .position(parseInt(participantParams[0]))
                .registrationNumber(regNumber)
                .needForHostel(convertToBoolean(participantParams[1]))
                .pointsSum(parseInt(participantParams[2]))
                .pointsSumWithoutIndividualAchievements(parseInt(participantParams[3]))
                .firstExamPoints(parseInt(participantParams[4].replace("*", "")))
                .secondExamPoints(parseInt(participantParams[5].replace("*", "")))
                .thirdExamPoints(parseInt(participantParams[6].replace("*", "")))
                .individualAchievementsPoints(parseInt(participantParams[7]))
                .specialRights(convertToBoolean(participantParams[8]))
                .consentToEnrollment(convertToBoolean(participantParams[9]))
                .forecast(getForecastByParams(participantParams))
                .build();
    }

    private String findRegNumber(String line) {
        var regNumber = TextUtils.findAnyByRegex(line, INSURANCE_PATTERN, REG_NUMBER_PATTERN);
        if (regNumber != null) {
            return regNumber;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean convertToBoolean(String text) {
        if (text.equalsIgnoreCase("Да")) {
            return true;
        } else if (text.equalsIgnoreCase("Нет")) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String getForecastByParams(String[] params) {
        try {
            return params[10];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
