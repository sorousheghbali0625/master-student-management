
package org.soroush.masterstudentmanagement.normalization;

import java.text.Normalizer;

public final class PersianTextNormalizer {

    private PersianTextNormalizer() {
    }

    public static String normalize(String input) {

        if (input == null) {
            return null;
        }

        String result = normalizeArabicLetters(input);
        result = normalizeDigits(result);
        result = normalizeSpaces(result);

        return Normalizer.normalize(result, Normalizer.Form.NFC);
    }

    private static String normalizeArabicLetters(String input) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);

            // Convert Arabic presentation forms to regular letters
            String expanded = String.valueOf(ch);

            if ((ch >= '\uFB50' && ch <= '\uFDFF')
                    || (ch >= '\uFE70' && ch <= '\uFEFF')) {

                expanded = Normalizer.normalize(
                        expanded,
                        Normalizer.Form.NFKC
                );
            }

            for (int j = 0; j < expanded.length(); j++) {

                char current = expanded.charAt(j);

                switch (current) {

                    // Arabic Kaf -> Persian Kaf
                    case '\u0643':
                        result.append('\u06A9');
                        break;

                    // Arabic Yeh / Alef Maksura -> Persian Yeh
                    case '\u064A':
                    case '\u0649':
                        result.append('\u06CC');
                        break;

                    // Remove Tatweel (Kashida)
                    case '\u0640':
                        break;

                    // Remove common Arabic vowel marks
                    case '\u064B':
                    case '\u064C':
                    case '\u064D':
                    case '\u064E':
                    case '\u064F':
                    case '\u0650':
                    case '\u0651':
                    case '\u0652':
                    case '\u0670':
                        break;

                    default:
                        result.append(current);
                }
            }
        }

        return result.toString();
    }

    private static String normalizeDigits(String input) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);

            if (ch >= '0' && ch <= '9') {
                ch = (char) ('\u06F0' + (ch - '0'));
            }
            else if (ch >= '\u0660' && ch <= '\u0669') {
                ch = (char) ('\u06F0' + (ch - '\u0660'));
            }

            result.append(ch);
        }

        return result.toString();
    }

    private static String normalizeSpaces(String input) {

        return input
                .replaceAll("[\\p{Zs}\\t\\n\\r\\f\\x0B]+", " ")
                .strip();
    }
}
