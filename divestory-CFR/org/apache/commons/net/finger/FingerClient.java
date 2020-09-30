/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.finger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.util.Charsets;

public class FingerClient
extends SocketClient {
    public static final int DEFAULT_PORT = 79;
    private static final String __LONG_FLAG = "/W ";
    private transient char[] __buffer = new char[1024];

    public FingerClient() {
        this.setDefaultPort(79);
    }

    public InputStream getInputStream(boolean bl) throws IOException {
        return this.getInputStream(bl, "");
    }

    public InputStream getInputStream(boolean bl, String string2) throws IOException {
        return this.getInputStream(bl, string2, null);
    }

    public InputStream getInputStream(boolean bl, String object, String arrby) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(64);
        if (bl) {
            stringBuilder.append(__LONG_FLAG);
        }
        stringBuilder.append((String)object);
        stringBuilder.append("\r\n");
        arrby = stringBuilder.toString().getBytes(Charsets.toCharset((String)arrby).name());
        object = new DataOutputStream(new BufferedOutputStream(this._output_, 1024));
        ((DataOutputStream)object).write(arrby, 0, arrby.length);
        ((DataOutputStream)object).flush();
        return this._input_;
    }

    public String query(boolean bl) throws IOException {
        return this.query(bl, "");
    }

    public String query(boolean bl, String object) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(this.__buffer.length);
        object = new BufferedReader(new InputStreamReader(this.getInputStream(bl, (String)object), this.getCharsetName()));
        try {
            do {
                int n;
                if ((n = ((BufferedReader)object).read(this.__buffer, 0, this.__buffer.length)) <= 0) {
                    return stringBuilder.toString();
                }
                stringBuilder.append(this.__buffer, 0, n);
            } while (true);
        }
        finally {
            ((BufferedReader)object).close();
        }
    }
}

