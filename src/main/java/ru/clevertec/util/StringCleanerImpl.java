package ru.clevertec.util;

public class StringCleanerImpl implements StringCleaner{
    @Override
    public String clean(String string) {
        string  = string.trim();
        if(string.matches("^\".*\"$")) {
            string = string.substring(1, string.length() - 1);
        }

        return deleteBackSlash(string);
    }

    private String deleteBackSlash(String string) {
       return string.replace("\\\"", "\"");
    }
}
