/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.AutoValue_Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Annotation {
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.unmodifiableMap(Collections.emptyMap());

    Annotation() {
    }

    public static Annotation fromDescription(String string2) {
        return new AutoValue_Annotation(string2, EMPTY_ATTRIBUTES);
    }

    public static Annotation fromDescriptionAndAttributes(String string2, Map<String, AttributeValue> map) {
        return new AutoValue_Annotation(string2, Collections.unmodifiableMap(new HashMap<String, AttributeValue>(Utils.checkNotNull(map, "attributes"))));
    }

    public abstract Map<String, AttributeValue> getAttributes();

    public abstract String getDescription();
}

