
package org.soroush.masterstudentmanagement.normalization;

import java.text.Normalizer;

public class ArabicLettersDecorator
        extends TextNormalizerDecorator {

    public ArabicLettersDecorator(TextNormalizer wrapped) {
        super(wrapped);
    }

    @Override
    public String normalize(String input) {

        String result = super.normalize(input);

        if (result == null) {
            return null;
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < result.length(); i++) {

            char ch = result.charAt(i);

            String expanded = String.valueOf(ch);

            // Arabic Presentation Forms
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

                    // Arabic Kaf → Persian Kaf
                    case '\u0643':
                        output.append('\u06A9');
                        break;

                    // Arabic Yeh and Alef Maksura → Persian Yeh
                    case '\u064A':
                    case '\u0649':
                        output.append('\u06CC');
                        break;

                    // Remove Tatweel
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
                        output.append(current);
                }
            }
        }

        return output.toString();
    }
}
