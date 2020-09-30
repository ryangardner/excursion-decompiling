/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.tags.AutoValue_TagValue;

public abstract class TagValue {
    public static final int MAX_LENGTH = 255;

    TagValue() {
    }

    public static TagValue create(String string2) {
        Utils.checkArgument(TagValue.isValid(string2), "Invalid TagValue: %s", string2);
        return new AutoValue_TagValue(string2);
    }

    private static boolean isValid(String string2) {
        if (string2.length() > 255) return false;
        if (!StringUtils.isPrintableString(string2)) return false;
        return true;
    }

    public abstract String asString();
}

