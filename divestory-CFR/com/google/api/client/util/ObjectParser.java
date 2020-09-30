/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public interface ObjectParser {
    public <T> T parseAndClose(InputStream var1, Charset var2, Class<T> var3) throws IOException;

    public Object parseAndClose(InputStream var1, Charset var2, Type var3) throws IOException;

    public <T> T parseAndClose(Reader var1, Class<T> var2) throws IOException;

    public Object parseAndClose(Reader var1, Type var2) throws IOException;
}

