/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;

public class FileEntity
extends AbstractHttpEntity
implements Cloneable {
    protected final File file;

    public FileEntity(File file, String string2) {
        if (file == null) throw new IllegalArgumentException("File may not be null");
        this.file = file;
        this.setContentType(string2);
    }

    public Object clone() throws CloneNotSupportedException {
        return Object.super.clone();
    }

    @Override
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public long getContentLength() {
        return this.file.length();
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
        FileInputStream fileInputStream = new FileInputStream(this.file);
        try {
            int n;
            byte[] arrby = new byte[4096];
            while ((n = ((InputStream)fileInputStream).read(arrby)) != -1) {
                outputStream2.write(arrby, 0, n);
            }
            outputStream2.flush();
            return;
        }
        finally {
            ((InputStream)fileInputStream).close();
        }
    }
}

