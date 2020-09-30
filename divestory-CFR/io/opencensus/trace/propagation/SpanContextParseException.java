/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.propagation;

public final class SpanContextParseException
extends Exception {
    private static final long serialVersionUID = 0L;

    public SpanContextParseException(String string2) {
        super(string2);
    }

    public SpanContextParseException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

