package org.soroush.masterstudentmanagement;




import org.junit.jupiter.api.Test;
import org.soroush.masterstudentmanagement.normalization.PersianTextNormalizer;

import static org.junit.jupiter.api.Assertions.*;

class PersianNormalizerTest {

    @Test
    void shouldNormalizeEnglishDigits() {

        String result =
                PersianTextNormalizer.normalize("Math 123");

        assertEquals("Math ۱۲۳", result);
    }

    @Test
    void shouldNormalizeArabicDigits() {

        String result =
                PersianTextNormalizer.normalize("ریاضی ١٢٣");

        assertEquals("ریاضی ۱۲۳", result);
    }

    @Test
    void shouldRemoveExtraSpaces() {

        String result =
                PersianTextNormalizer.normalize(
                        "  مهندسی   نرم افزار  "
                );

        assertEquals("مهندسی نرم افزار", result);
    }

    @Test
    void shouldNormalizeNonBreakingSpace() {

        String result =
                PersianTextNormalizer.normalize(
                        "ریاضی\u00A0پیشرفته"
                );

        assertEquals("ریاضی پیشرفته", result);
    }

    @Test
    void shouldPreserveHalfSpace() {

        String result =
                PersianTextNormalizer.normalize(
                        "نرم\u200Cافزار"
                );

        assertEquals("نرم\u200Cافزار", result);
    }

    @Test
    void shouldHandleNull() {

        assertNull(PersianTextNormalizer.normalize(null));

    }

    @Test
    void shouldConvertArabicKafToPersian() {

        String result =
                PersianTextNormalizer.normalize("برنامه نويسي كامپيوتر");

        assertEquals("برنامه نویسی کامپیوتر", result);
    }

    @Test
    void shouldConvertArabicYehToPersian() {

        String result =
                PersianTextNormalizer.normalize("رياضي");

        assertEquals("ریاضی", result);
    }

    @Test
    void shouldConvertAlefMaksuraToPersianYeh() {

        String result =
                PersianTextNormalizer.normalize("موسى");

        assertEquals("موسی", result);
    }

    @Test
    void shouldRemoveTatweel() {

        String result =
                PersianTextNormalizer.normalize("برنـامه نویسی");

        assertEquals("برنامه نویسی", result);
    }

    @Test
    void shouldRemoveArabicDiacritics() {

        String result =
                PersianTextNormalizer.normalize("رِيَاضِيّات");

        assertEquals("ریاضیات", result);
    }

    @Test
    void shouldPreserveHamzaLetters() {

        String result =
                PersianTextNormalizer.normalize("جزئيات و مؤلف");

        assertEquals("جزئیات و مؤلف", result);
    }

    @Test
    void shouldNormalizeAllFeaturesTogether() {

        String result = PersianTextNormalizer.normalize(
                "  رياضي   كاربردي  ١ و 2  "
        );

        assertEquals("ریاضی کاربردی ۱ و ۲", result);
    }

    @Test
    void shouldBeIdempotent() {

        String once =
                PersianTextNormalizer.normalize("رياضي  ١");

        String twice =
                PersianTextNormalizer.normalize(once);

        assertEquals(once, twice);
    }

}

