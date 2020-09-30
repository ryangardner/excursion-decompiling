/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.mail.util.SharedByteArrayInputStream;

class SharedByteArrayOutputStream
extends ByteArrayOutputStream {
    public SharedByteArrayOutputStream(int n) {
        super(n);
    }

    public InputStream toStream() {
        return new SharedByteArrayInputStream(this.buf, 0, this.count);
    }
}

