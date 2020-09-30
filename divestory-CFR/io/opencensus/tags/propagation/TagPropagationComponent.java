/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags.propagation;

import io.opencensus.tags.propagation.TagContextBinarySerializer;
import io.opencensus.tags.propagation.TagContextTextFormat;

public abstract class TagPropagationComponent {
    public abstract TagContextBinarySerializer getBinarySerializer();

    public abstract TagContextTextFormat getCorrelationContextFormat();
}

