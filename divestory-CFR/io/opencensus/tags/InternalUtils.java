/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.Tag;
import io.opencensus.tags.TagContext;
import java.util.Iterator;

public final class InternalUtils {
    private InternalUtils() {
    }

    public static Iterator<Tag> getTags(TagContext tagContext) {
        return tagContext.getIterator();
    }
}

