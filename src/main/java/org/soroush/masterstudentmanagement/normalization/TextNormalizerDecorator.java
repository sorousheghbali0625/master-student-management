package org.soroush.masterstudentmanagement.normalization;

import java.util.Objects;

public abstract class TextNormalizerDecorator
        implements TextNormalizer {

    protected final TextNormalizer wrapped;

    protected TextNormalizerDecorator(TextNormalizer wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped);
    }

    @Override
    public String normalize(String input) {
        return wrapped.normalize(input);
    }
}