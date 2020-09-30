/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.async;

public interface NonBlockingInputFeeder {
    public void endOfInput();

    public boolean needMoreInput();
}

