/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpContent;
import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractInputStreamContent
implements HttpContent {
    private boolean closeInputStream = true;
    private String type;

    public AbstractInputStreamContent(String string2) {
        this.setType(string2);
    }

    public final boolean getCloseInputStream() {
        return this.closeInputStream;
    }

    public abstract InputStream getInputStream() throws IOException;

    @Override
    public String getType() {
        return this.type;
    }

    public AbstractInputStreamContent setCloseInputStream(boolean bl) {
        this.closeInputStream = bl;
        return this;
    }

    public AbstractInputStreamContent setType(String string2) {
        this.type = string2;
        return this;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        IOUtils.copy(this.getInputStream(), outputStream2, this.closeInputStream);
        outputStream2.flush();
    }
}

