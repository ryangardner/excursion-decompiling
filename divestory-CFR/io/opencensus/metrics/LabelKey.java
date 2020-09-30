/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.metrics.AutoValue_LabelKey;

public abstract class LabelKey {
    LabelKey() {
    }

    public static LabelKey create(String string2, String string3) {
        return new AutoValue_LabelKey(string2, string3);
    }

    public abstract String getDescription();

    public abstract String getKey();
}

