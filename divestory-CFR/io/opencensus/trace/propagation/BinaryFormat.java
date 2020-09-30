/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.propagation;

import io.opencensus.internal.Utils;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.propagation.SpanContextParseException;
import java.text.ParseException;

public abstract class BinaryFormat {
    static final NoopBinaryFormat NOOP_BINARY_FORMAT = new NoopBinaryFormat();

    static BinaryFormat getNoopBinaryFormat() {
        return NOOP_BINARY_FORMAT;
    }

    @Deprecated
    public SpanContext fromBinaryValue(byte[] object) throws ParseException {
        try {
            return this.fromByteArray((byte[])object);
        }
        catch (SpanContextParseException spanContextParseException) {
            throw new ParseException(spanContextParseException.toString(), 0);
        }
    }

    public SpanContext fromByteArray(byte[] object) throws SpanContextParseException {
        try {
            return this.fromBinaryValue((byte[])object);
        }
        catch (ParseException parseException) {
            throw new SpanContextParseException("Error while parsing.", parseException);
        }
    }

    @Deprecated
    public byte[] toBinaryValue(SpanContext spanContext) {
        return this.toByteArray(spanContext);
    }

    public byte[] toByteArray(SpanContext spanContext) {
        return this.toBinaryValue(spanContext);
    }

    private static final class NoopBinaryFormat
    extends BinaryFormat {
        private NoopBinaryFormat() {
        }

        @Override
        public SpanContext fromByteArray(byte[] arrby) {
            Utils.checkNotNull(arrby, "bytes");
            return SpanContext.INVALID;
        }

        @Override
        public byte[] toByteArray(SpanContext spanContext) {
            Utils.checkNotNull(spanContext, "spanContext");
            return new byte[0];
        }
    }

}

