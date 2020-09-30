/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.QPEncoderStream;
import java.io.IOException;
import java.io.OutputStream;

public class QEncoderStream
extends QPEncoderStream {
    private static String TEXT_SPECIALS = "=_?";
    private static String WORD_SPECIALS = "=_?\"#$%&'(),.:;<>@[\\]^`{|}~";
    private String specials;

    public QEncoderStream(OutputStream object, boolean bl) {
        super((OutputStream)object, Integer.MAX_VALUE);
        object = bl ? WORD_SPECIALS : TEXT_SPECIALS;
        this.specials = object;
    }

    public static int encodedLength(byte[] arrby, boolean bl) {
        String string2 = bl ? WORD_SPECIALS : TEXT_SPECIALS;
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = arrby[n] & 255;
            n2 = n3 >= 32 && n3 < 127 && string2.indexOf(n3) < 0 ? ++n2 : (n2 += 3);
            ++n;
        }
        return n2;
    }

    @Override
    public void write(int n) throws IOException {
        if ((n &= 255) == 32) {
            this.output(95, false);
            return;
        }
        if (n >= 32 && n < 127 && this.specials.indexOf(n) < 0) {
            this.output(n, false);
            return;
        }
        this.output(n, true);
    }
}

