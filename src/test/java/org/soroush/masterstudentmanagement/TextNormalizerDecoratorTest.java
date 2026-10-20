
package org.soroush.masterstudentmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soroush.masterstudentmanagement.normalization.*;

import static org.junit.jupiter.api.Assertions.*;

class TextNormalizerDecoratorTest {

    private TextNormalizer textNormalizer;

    @BeforeEach
    void setUp() {

        TextNormalizer normalizer =
                new IdentityTextNormalizer();

        normalizer =
                new ArabicLettersDecorator(normalizer);

        normalizer =
                new DigitsNormalizerDecorator(normalizer);

        normalizer =
                new SpacesNormalizerDecorator(normalizer);

        textNormalizer =
                new UnicodeNfcDecorator(normalizer);
    }

    @Test
    void shouldNormalizeAllFeatures() {

        String result = textNormalizer.normalize(
                "  رياضي   كاربردي  ١ و 2  "
        );

        assertEquals(
                "ریاضی کاربردی ۱ و ۲",
                result
        );
    }

    @Test
    void shouldNormalizeOnlyDigits() {

        TextNormalizer digitsNormalizer =
                new DigitsNormalizerDecorator(
                        new IdentityTextNormalizer()
                );

        assertEquals(
                "Math ۱۲۳",
                digitsNormalizer.normalize("Math 123")
        );
    }

    @Test
    void shouldNormalizeOnlySpaces() {

        TextNormalizer spacesNormalizer =
                new SpacesNormalizerDecorator(
                        new IdentityTextNormalizer()
                );

        assertEquals(
                "ریاضی پیشرفته",
                spacesNormalizer.normalize(
                        "  ریاضی    پیشرفته  "
                )
        );
    }

    @Test
    void shouldNormalizeArabicLetters() {

        TextNormalizer lettersNormalizer =
                new ArabicLettersDecorator(
                        new IdentityTextNormalizer()
                );

        assertEquals(
                "ریاضی کاربردی",
                lettersNormalizer.normalize("رياضي كاربردي")
        );
    }

    @Test
    void shouldPreserveHalfSpace() {

        assertEquals(
                "نرم\u200Cافزار",
                textNormalizer.normalize("نرم\u200Cافزار")
        );
    }

    @Test
    void shouldHandleNullInput() {

        assertNull(textNormalizer.normalize(null));
    }

    @Test
    void shouldHandleEmptyString() {

        assertEquals(
                "",
                textNormalizer.normalize("")
        );
    }

    @Test
    void shouldBeIdempotent() {

        String once = textNormalizer.normalize(
                "  رياضي  ١ "
        );

        String twice = textNormalizer.normalize(once);

        assertEquals(once, twice);
    }

    
}
