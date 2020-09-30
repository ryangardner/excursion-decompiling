/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.errorprone.annotations.DoNotMock;
import java.io.IOException;

@DoNotMock(value="Implement it normally")
public interface ByteProcessor<T> {
    public T getResult();

    public boolean processBytes(byte[] var1, int var2, int var3) throws IOException;
}

