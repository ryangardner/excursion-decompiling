/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpEncoding;
import com.google.api.client.util.StreamingContent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipEncoding
implements HttpEncoding {
    @Override
    public void encode(StreamingContent streamingContent, OutputStream outputStream2) throws IOException {
        outputStream2 = new GZIPOutputStream(new BufferedOutputStream(outputStream2){

            @Override
            public void close() throws IOException {
                try {
                    this.flush();
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        });
        streamingContent.writeTo(outputStream2);
        ((DeflaterOutputStream)outputStream2).close();
    }

    @Override
    public String getName() {
        return "gzip";
    }

}

