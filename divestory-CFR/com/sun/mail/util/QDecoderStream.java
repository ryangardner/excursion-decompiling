/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.QPDecoderStream;
import java.io.IOException;
import java.io.InputStream;

public class QDecoderStream
extends QPDecoderStream {
    public QDecoderStream(InputStream inputStream2) {
        super(inputStream2);
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n == 95) {
            return 32;
        }
        if (n != 61) return n;
        this.ba[0] = (byte)this.in.read();
        this.ba[1] = (byte)this.in.read();
        try {
            return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder("Error in QP stream ");
            stringBuilder.append(numberFormatException.getMessage());
            throw new IOException(stringBuilder.toString());
        }
    }
}

