package com.ximand.bot.mgtulists.model;

import lombok.Builder;
import lombok.Data;

/**
 * POJO-объект, описывающий участника конкурса
 */
@Data
@Builder
public class Participant {

    private int position;

    /**
     * Регистрационный номер. В большинстве случаев является номером СНИЛС (формата nnn-nnn-nnn nn), однако может быть
     * набором, состоящим из одной буквы и четырех цифр (xnnnn)
     */
    private String registrationNumber;

    /**
     * Необходимость в общежитии
     */
    private boolean needForHostel;

    /**
     * Сумма баллов за все 3 экзамена включая индивидуальные достижения
     */
    private int pointsSum;

    /**
     * Сумма баллов за все 3 экзамена не включая индивидуальные достижения
     */
    private int pointsSumWithoutIndividualAchievements;

    private int firstExamPoints;
    private int secondExamPoints;
    private int thirdExamPoints;

    /**
     * Баллы за индивидуальные достижения (В документе обозначается как ИД)
     */
    private int individualAchievementsPoints;

    /**
     * Наличие у абитуриента особых прав при поступлении
     */
    private boolean specialRights;

    /**
     * Было ли подано согласие на зачисление
     */
    private boolean consentToEnrollment;

    private String forecast;

}
