/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags.propagation;

import io.opencensus.tags.TagContext;
import io.opencensus.tags.propagation.TagContextDeserializationException;
import io.opencensus.tags.propagation.TagContextSerializationException;

public abstract class TagContextBinarySerializer {
    public abstract TagContext fromByteArray(byte[] var1) throws TagContextDeserializationException;

    public abstract byte[] toByteArray(TagContext var1) throws TagContextSerializationException;
}

