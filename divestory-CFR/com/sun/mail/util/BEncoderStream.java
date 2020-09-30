/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.BASE64EncoderStream;
import java.io.OutputStream;

public class BEncoderStream
extends BASE64EncoderStream {
    public BEncoderStream(OutputStream outputStream2) {
        super(outputStream2, Integer.MAX_VALUE);
    }

    public static int encodedLength(byte[] arrby) {
        return (arrby.length + 2) / 3 * 4;
    }
}

