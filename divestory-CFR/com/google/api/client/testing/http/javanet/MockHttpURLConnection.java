/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http.javanet;

import com.google.api.client.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockHttpURLConnection
extends HttpURLConnection {
    @Deprecated
    public static final byte[] ERROR_BUF;
    @Deprecated
    public static final byte[] INPUT_BUF;
    private boolean doOutputCalled;
    private InputStream errorStream = null;
    private Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
    private InputStream inputStream = null;
    private OutputStream outputStream = new ByteArrayOutputStream(0);

    static {
        INPUT_BUF = new byte[1];
        ERROR_BUF = new byte[5];
    }

    public MockHttpURLConnection(URL uRL) {
        super(uRL);
    }

    public MockHttpURLConnection addHeader(String string2, String string3) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(string3);
        if (this.headers.containsKey(string2)) {
            this.headers.get(string2).add(string3);
            return this;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string3);
        this.headers.put(string2, arrayList);
        return this;
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public void disconnect() {
    }

    public final boolean doOutputCalled() {
        return this.doOutputCalled;
    }

    public int getChunkLength() {
        return this.chunkLength;
    }

    @Override
    public InputStream getErrorStream() {
        return this.errorStream;
    }

    @Override
    public String getHeaderField(String object) {
        if ((object = this.headers.get(object)) != null) return (String)object.get(0);
        return null;
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.headers;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.responseCode >= 400) throw new IOException();
        return this.inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 == null) return super.getOutputStream();
        return outputStream2;
    }

    @Override
    public int getResponseCode() throws IOException {
        return this.responseCode;
    }

    @Override
    public void setDoOutput(boolean bl) {
        this.doOutputCalled = true;
    }

    public MockHttpURLConnection setErrorStream(InputStream inputStream2) {
        Preconditions.checkNotNull(inputStream2);
        if (this.errorStream != null) return this;
        this.errorStream = inputStream2;
        return this;
    }

    public MockHttpURLConnection setInputStream(InputStream inputStream2) {
        Preconditions.checkNotNull(inputStream2);
        if (this.inputStream != null) return this;
        this.inputStream = inputStream2;
        return this;
    }

    public MockHttpURLConnection setOutputStream(OutputStream outputStream2) {
        this.outputStream = outputStream2;
        return this;
    }

    public MockHttpURLConnection setResponseCode(int n) {
        boolean bl = n >= -1;
        Preconditions.checkArgument(bl);
        this.responseCode = n;
        return this;
    }

    @Override
    public boolean usingProxy() {
        return false;
    }
}

