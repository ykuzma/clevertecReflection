package ru.clevertec.util;

import java.util.UUID;

public class ReplacerComma {

    public String[] replaceComma(String json) {
        json = json.trim();
        json = json.substring(1, json.length() - 1);
        int countQuotes = 0;
        int squareBracket = 0;
        int curlyBracket = 0;
        StringBuilder sb = new StringBuilder(json);
        String uuid = UUID.randomUUID().toString();
        int  offsetAfterReplace = 0;
        char[] chars = json.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            if (chars[i] == '"' && (i == 0 || json.codePointBefore(i) != '\\')) {
                countQuotes++;
            } else if (chars[i] == '[' && countQuotes % 2 == 0) {
                squareBracket++;
            } else if (chars[i] == '{' && countQuotes % 2 == 0) {
                curlyBracket++;
            } else if (chars[i] == ']' && countQuotes % 2 == 0) {
                squareBracket--;
            } else if (chars[i] == '}' && countQuotes % 2 == 0) {
                curlyBracket--;
            } else if (chars[i] == ',' && countQuotes % 2 == 0
                    && squareBracket == 0
                    && curlyBracket == 0) {
                sb.replace(i + offsetAfterReplace, i + offsetAfterReplace + 1, uuid);
                offsetAfterReplace += uuid.length() - 1;
            }
        }
        return sb.toString().split(uuid);
    }
}
