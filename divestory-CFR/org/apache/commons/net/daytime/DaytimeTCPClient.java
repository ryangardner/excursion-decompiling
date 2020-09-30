/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.daytime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.net.SocketClient;

public final class DaytimeTCPClient
extends SocketClient {
    public static final int DEFAULT_PORT = 13;
    private final char[] __buffer = new char[64];

    public DaytimeTCPClient() {
        this.setDefaultPort(13);
    }

    public String getTime() throws IOException {
        StringBuilder stringBuilder = new StringBuilder(this.__buffer.length);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this._input_, this.getCharsetName()));
        int n;
        char[] arrc;
        while ((n = bufferedReader.read(arrc = this.__buffer, 0, arrc.length)) > 0) {
            stringBuilder.append(this.__buffer, 0, n);
        }
        return stringBuilder.toString();
    }
}

