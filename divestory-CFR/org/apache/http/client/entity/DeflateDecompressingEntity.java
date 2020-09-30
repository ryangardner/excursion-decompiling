/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.entity;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.DecompressingEntity;

public class DeflateDecompressingEntity
extends DecompressingEntity {
    public DeflateDecompressingEntity(HttpEntity httpEntity) {
        super(httpEntity);
    }

    @Override
    public Header getContentEncoding() {
        return null;
    }

    @Override
    public long getContentLength() {
        return -1L;
    }

    @Override
    InputStream getDecompressingInputStream(InputStream inputStream2) throws IOException {
        byte[] arrby = new byte[6];
        int n = ((FilterInputStream)(inputStream2 = new PushbackInputStream(inputStream2, 6))).read(arrby);
        if (n == -1) throw new IOException("Unable to read the response");
        byte[] arrby2 = new byte[1];
        Object object = new Inflater();
        try {
            int n2;
            while ((n2 = ((Inflater)object).inflate(arrby2)) == 0) {
                if (((Inflater)object).finished()) {
                    object = new IOException("Unable to read the response");
                    throw object;
                }
                if (((Inflater)object).needsDictionary()) break;
                if (!((Inflater)object).needsInput()) continue;
                ((Inflater)object).setInput(arrby);
            }
            if (n2 != -1) {
                ((PushbackInputStream)inputStream2).unread(arrby, 0, n);
                return new InflaterInputStream(inputStream2);
            }
            object = new IOException("Unable to read the response");
            throw object;
        }
        catch (DataFormatException dataFormatException) {
            ((PushbackInputStream)inputStream2).unread(arrby, 0, n);
            return new InflaterInputStream(inputStream2, new Inflater(true));
        }
    }
}

