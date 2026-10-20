package org.soroush.masterstudentmanagement.normalization;

public class SpacesNormalizerDecorator
        extends TextNormalizerDecorator {

    public SpacesNormalizerDecorator(TextNormalizer wrapped) {
        super(wrapped);
    }

    @Override
    public String normalize(String input) {

        String result = super.normalize(input);

        if (result == null) {
            return null;
        }

        return result
                .replaceAll("[\\p{Zs}\\t\\n\\r\\f\\x0B]+", " ")
                .strip();
    }
}