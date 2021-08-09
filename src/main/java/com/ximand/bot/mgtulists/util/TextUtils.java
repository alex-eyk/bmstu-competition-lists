package com.ximand.bot.mgtulists.util;

import lombok.val;

import java.util.regex.Pattern;

public final class TextUtils {

    private TextUtils() {
    }

    public static String findAnyByRegex(String text, String... regexArray) {
        for (val regex : regexArray) {
            val found = findByRegex(text, regex);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public static String findByRegex(String text, String regex) {
        val mather = Pattern.compile(regex).matcher(text);
        if (mather.find()) {
            return mather.group();
        } else {
            return null;
        }
    }

}
