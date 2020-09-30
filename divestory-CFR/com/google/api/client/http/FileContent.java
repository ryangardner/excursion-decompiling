/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.util.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class FileContent
extends AbstractInputStreamContent {
    private final File file;

    public FileContent(String string2, File file) {
        super(string2);
        this.file = Preconditions.checkNotNull(file);
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }

    @Override
    public long getLength() {
        return this.file.length();
    }

    @Override
    public boolean retrySupported() {
        return true;
    }

    @Override
    public FileContent setCloseInputStream(boolean bl) {
        return (FileContent)super.setCloseInputStream(bl);
    }

    @Override
    public FileContent setType(String string2) {
        return (FileContent)super.setType(string2);
    }
}

