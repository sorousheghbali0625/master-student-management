package org.soroush.masterstudentmanagement.normalization;

import java.text.Normalizer;

public class UnicodeNfcDecorator
        extends TextNormalizerDecorator {

    public UnicodeNfcDecorator(TextNormalizer wrapped) {
        super(wrapped);
    }

    @Override
    public String normalize(String input) {

        String result = super.normalize(input);

        if (result == null) {
            return null;
        }

        return Normalizer.normalize(
                result,
                Normalizer.Form.NFC
        );
    }
}