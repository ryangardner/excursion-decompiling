/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.syntak.library;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class CountingRequestBody
extends RequestBody {
    protected CountingSink countingSink;
    protected RequestBody delegate;
    protected Listener listener;

    public CountingRequestBody(RequestBody requestBody, Listener listener) {
        this.delegate = requestBody;
        this.listener = listener;
    }

    @Override
    public long contentLength() {
        try {
            return this.delegate.contentLength();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return 0L;
        }
    }

    @Override
    public MediaType contentType() {
        return this.delegate.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink2) throws IOException {
        sink2 = new CountingSink(sink2);
        this.countingSink = sink2;
        sink2 = Okio.buffer(sink2);
        this.delegate.writeTo((BufferedSink)sink2);
        sink2.flush();
    }

    protected final class CountingSink
    extends ForwardingSink {
        private long bytesWritten;

        public CountingSink(Sink sink2) {
            super(sink2);
            this.bytesWritten = 0L;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            super.write(buffer, l);
            this.bytesWritten += l;
            CountingRequestBody.this.listener.onRequestProgress(this.bytesWritten, CountingRequestBody.this.contentLength());
        }
    }

    public static interface Listener {
        public void onRequestProgress(long var1, long var3);
    }

}

