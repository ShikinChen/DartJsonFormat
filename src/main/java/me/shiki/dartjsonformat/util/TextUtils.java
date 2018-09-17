package me.shiki.dartjsonformat.util;

import java.util.regex.Pattern;

/**
 * @author shiki
 */
public final class TextUtils {
    public final static String SYMBOL = "((?=[\\x21-\\x7e]+)[^A-Za-z0-9])";

    public final static String INT = "^[+-]?[0-9]+$";

    public final static String DOUBLE = "^[+-]?[0-9.]+$";

    private TextUtils() {
    }

    public final static String toUpperFirstCase(String text) {
        if (isEmpty(text)) {
            return null;
        }
        text = text.trim();
        if (text.length() > 1) {
            return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
        } else {
            return text.substring(0, 1).toUpperCase();
        }

    }


    public final static String toLowerFirstCase(String text) {
        if (isEmpty(text)) {
            return null;
        }
        text = text.trim();
        if (text.length() > 1) {
            return text.substring(0, 1).toLowerCase() + text.substring(1, text.length());
        } else {
            return text.substring(0, 1).toLowerCase();
        }

    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public final static String formatClassName(String text) {
        return toUpperFirstCase(formatNotSymbolText(text));
    }

    public final static String formatPropertyName(String text) {
        return toLowerFirstCase(formatNotSymbolText(text));
    }

    public final static String formatNotSymbolText(String text) {
        if (isEmpty(text)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        String charText = null;
        boolean isMatch = false;
        boolean isUpperCase = false;
        text = text.trim();

        for (char c : text.toCharArray()) {
            charText = String.valueOf(c);
            isMatch = Pattern.matches(SYMBOL, charText);
            if (isMatch) {
                isUpperCase = true;
                charText = null;
            } else {
                if (isUpperCase) {
                    charText = charText.toUpperCase();
                }
                isUpperCase = false;
            }
            if (charText != null) {
                buffer.append(charText);
            }
        }
        return toUpperFirstCase(buffer.toString());
    }

    public final static boolean isInt(String text) {
        return Pattern.matches(INT, text);
    }

    public final static boolean isDouble(String text) {
        return Pattern.matches(DOUBLE, text);
    }
}
