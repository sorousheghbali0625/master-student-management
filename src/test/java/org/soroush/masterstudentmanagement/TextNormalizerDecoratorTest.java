
package org.soroush.masterstudentmanagement.normalization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextNormalizerDecoratorTest {

    private TextNormalizer createNormalizer() {

        TextNormalizer normalizer =
                new IdentityTextNormalizer();

        normalizer =
                new ArabicLettersDecorator(normalizer);

        normalizer =
                new DigitsNormalizerDecorator(normalizer);

        normalizer =
                new SpacesNormalizerDecorator(normalizer);

        return new UnicodeNfcDecorator(normalizer);
    }

    @Test
    void shouldNormalizeAllFeatures() {

        TextNormalizer normalizer = createNormalizer();

        String result = normalizer.normalize(
                "  رياضي   كاربردي  ١ و 2  "
        );

        assertEquals("ریاضی کاربردی ۱ و ۲", result);
    }

    @Test
    void shouldNormalizeOnlyDigits() {

        TextNormalizer normalizer =
                new DigitsNormalizerDecorator(
                        new IdentityTextNormalizer()
                );

        assertEquals(
                "Math ۱۲۳",
                normalizer.normalize("Math 123")
        );
    }

    @Test
    void shouldNormalizeOnlySpaces() {

        TextNormalizer normalizer =
                new SpacesNormalizerDecorator(
                        new IdentityTextNormalizer()
                );

        assertEquals(
                "ریاضی پیشرفته",
                normalizer.normalize("  ریاضی    پیشرفته  ")
        );
    }

    @Test
    void shouldPreserveHalfSpace() {

        TextNormalizer normalizer = createNormalizer();

        assertEquals(
                "نرم\u200Cافزار",
                normalizer.normalize("نرم\u200Cافزار")
        );
    }

    @Test
    void shouldHandleNullInput() {

        TextNormalizer normalizer = createNormalizer();

        assertNull(normalizer.normalize(null));
    }

    @Test
    void shouldBeIdempotent() {

        TextNormalizer normalizer = createNormalizer();

        String once = normalizer.normalize("  رياضي  ١ ");

        String twice = normalizer.normalize(once);

        assertEquals(once, twice);
    }

    @Test
    void shouldMatchOldNormalizer() {

        TextNormalizer normalizer = createNormalizer();

        String input = "  رياضي   كاربردي  ١ و 2  ";

        assertEquals(
                PersianTextNormalizer.normalize(input),
                normalizer.normalize(input)
        );
    }
}
