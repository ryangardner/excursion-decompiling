/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.nio.charset.Charset;

public class Charsets {
    public static Charset toCharset(String object) {
        if (object != null) return Charset.forName((String)object);
        return Charset.defaultCharset();
    }

    public static Charset toCharset(String object, String string2) {
        if (object != null) return Charset.forName((String)object);
        return Charset.forName(string2);
    }
}

