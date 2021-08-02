package com.ximand.bot.mgtulists.util;

import lombok.val;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ReplyUtils {

    public static ReplyKeyboard getRemoveReplyKeyboard() {
        val keyboardRemove = new ReplyKeyboardRemove();
        keyboardRemove.setRemoveKeyboard(true);
        return keyboardRemove;
    }

    public static ReplyKeyboard getReplyKeyboardForSet(Set<String> set) {
        val keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(convertLinesToRows(set));
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    private static List<KeyboardRow> convertLinesToRows(Set<String> lines) {
        val rowsList = new ArrayList<KeyboardRow>();
        for (String line : lines) {
            val keyboardRow = new KeyboardRow();
            keyboardRow.add(line);
            rowsList.add(keyboardRow);
        }
        return rowsList;
    }

}
