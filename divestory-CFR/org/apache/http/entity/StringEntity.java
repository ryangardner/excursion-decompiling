/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.AbstractHttpEntity;

public class StringEntity
extends AbstractHttpEntity
implements Cloneable {
    protected final byte[] content;

    public StringEntity(String string2) throws UnsupportedEncodingException {
        this(string2, null);
    }

    public StringEntity(String string2, String string3) throws UnsupportedEncodingException {
        this(string2, null, string3);
    }

    public StringEntity(String charSequence, String string2, String string3) throws UnsupportedEncodingException {
        if (charSequence == null) throw new IllegalArgumentException("Source string may not be null");
        String string4 = string2;
        if (string2 == null) {
            string4 = "text/plain";
        }
        string2 = string3;
        if (string3 == null) {
            string2 = "ISO-8859-1";
        }
        this.content = ((String)charSequence).getBytes(string2);
        charSequence = new StringBuffer();
        ((StringBuffer)charSequence).append(string4);
        ((StringBuffer)charSequence).append("; charset=");
        ((StringBuffer)charSequence).append(string2);
        this.setContentType(((StringBuffer)charSequence).toString());
    }

    public Object clone() throws CloneNotSupportedException {
        return Object.super.clone();
    }

    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public long getContentLength() {
        return this.content.length;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        outputStream2.write(this.content);
        outputStream2.flush();
    }
}

