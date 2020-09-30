package io.opencensus.tags.propagation;

import io.opencensus.tags.TagContext;

public abstract class TagContextBinarySerializer {
   public abstract TagContext fromByteArray(byte[] var1) throws TagContextDeserializationException;

   public abstract byte[] toByteArray(TagContext var1) throws TagContextSerializationException;
}
