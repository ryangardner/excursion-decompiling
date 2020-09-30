/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import org.apache.http.client.utils.Idn;
import org.apache.http.client.utils.JdkIdn;
import org.apache.http.client.utils.Rfc3492Idn;

public class Punycode {
    private static final Idn impl;

    static {
        Idn idn;
        try {
            idn = new JdkIdn();
        }
        catch (Exception exception) {
            idn = new Rfc3492Idn();
        }
        impl = idn;
    }

    public static String toUnicode(String string2) {
        return impl.toUnicode(string2);
    }
}

