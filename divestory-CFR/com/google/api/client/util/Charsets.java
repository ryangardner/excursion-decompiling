/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.nio.charset.Charset;

public final class Charsets {
    public static final Charset ISO_8859_1;
    public static final Charset UTF_8;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
    }

    private Charsets() {
    }
}

