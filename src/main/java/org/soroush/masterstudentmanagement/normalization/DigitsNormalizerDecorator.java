
package org.soroush.masterstudentmanagement.normalization;

public class DigitsNormalizerDecorator
        extends TextNormalizerDecorator {

    public DigitsNormalizerDecorator(TextNormalizer wrapped) {
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

            if (ch >= '0' && ch <= '9') {

                ch = (char) ('\u06F0' + (ch - '0'));

            } else if (ch >= '\u0660' && ch <= '\u0669') {

                ch = (char) ('\u06F0' + (ch - '\u0660'));
            }

            output.append(ch);
        }

        return output.toString();
    }
}
