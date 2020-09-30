/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.tags.AutoValue_TagKey;

public abstract class TagKey {
    public static final int MAX_LENGTH = 255;

    TagKey() {
    }

    public static TagKey create(String string2) {
        Utils.checkArgument(TagKey.isValid(string2), "Invalid TagKey name: %s", string2);
        return new AutoValue_TagKey(string2);
    }

    private static boolean isValid(String string2) {
        if (string2.isEmpty()) return false;
        if (string2.length() > 255) return false;
        if (!StringUtils.isPrintableString(string2)) return false;
        return true;
    }

    public abstract String getName();
}

