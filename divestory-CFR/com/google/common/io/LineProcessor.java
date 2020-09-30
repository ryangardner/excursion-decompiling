/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import java.io.IOException;

public interface LineProcessor<T> {
    public T getResult();

    public boolean processLine(String var1) throws IOException;
}

