/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.metrics.AutoValue_LabelValue;
import javax.annotation.Nullable;

public abstract class LabelValue {
    LabelValue() {
    }

    public static LabelValue create(@Nullable String string2) {
        return new AutoValue_LabelValue(string2);
    }

    @Nullable
    public abstract String getValue();
}

