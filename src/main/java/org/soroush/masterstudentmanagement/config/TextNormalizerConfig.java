
package org.soroush.masterstudentmanagement.config;

import org.soroush.masterstudentmanagement.normalization.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextNormalizerConfig {

    @Bean
    public TextNormalizer textNormalizer() {

        TextNormalizer normalizer =
                new IdentityTextNormalizer();

        normalizer =
                new ArabicLettersDecorator(normalizer);

        normalizer =
                new DigitsNormalizerDecorator(normalizer);

        normalizer =
                new SpacesNormalizerDecorator(normalizer);

        normalizer =
                new UnicodeNfcDecorator(normalizer);

        return normalizer;
    }
}
