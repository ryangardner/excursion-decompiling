/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.javanet;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.http.javanet.NetHttpResponse;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class NetHttpRequest
extends LowLevelHttpRequest {
    private static final OutputWriter DEFAULT_CONNECTION_WRITER = new DefaultOutputWriter();
    private final HttpURLConnection connection;
    private int writeTimeout;

    NetHttpRequest(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
        this.writeTimeout = 0;
        httpURLConnection.setInstanceFollowRedirects(false);
    }

    private boolean hasResponse(HttpURLConnection httpURLConnection) {
        boolean bl = false;
        try {
            int n = httpURLConnection.getResponseCode();
            if (n <= 0) return bl;
            return true;
        }
        catch (IOException iOException) {
            return bl;
        }
    }

    private void writeContentToOutputStream(OutputWriter object, OutputStream future) throws IOException {
        if (this.writeTimeout == 0) {
            object.write((OutputStream)((Object)future), this.getStreamingContent());
            return;
        }
        future = new Callable<Boolean>((OutputWriter)object, (OutputStream)((Object)future), this.getStreamingContent()){
            final /* synthetic */ StreamingContent val$content;
            final /* synthetic */ OutputStream val$out;
            final /* synthetic */ OutputWriter val$outputWriter;
            {
                this.val$outputWriter = outputWriter;
                this.val$out = outputStream2;
                this.val$content = streamingContent;
            }

            @Override
            public Boolean call() throws IOException {
                this.val$outputWriter.write(this.val$out, this.val$content);
                return Boolean.TRUE;
            }
        };
        object = Executors.newSingleThreadExecutor();
        future = object.submit(new FutureTask(future), null);
        object.shutdown();
        try {
            future.get(this.writeTimeout, TimeUnit.MILLISECONDS);
            if (object.isTerminated()) return;
            object.shutdown();
            return;
        }
        catch (TimeoutException timeoutException) {
            throw new IOException("Socket write timed out", timeoutException);
        }
        catch (ExecutionException executionException) {
            throw new IOException("Exception in socket write", executionException);
        }
        catch (InterruptedException interruptedException) {
            throw new IOException("Socket write interrupted", interruptedException);
        }
    }

    @Override
    public void addHeader(String string2, String string3) {
        this.connection.addRequestProperty(string2, string3);
    }

    @Override
    public LowLevelHttpResponse execute() throws IOException {
        return this.execute(DEFAULT_CONNECTION_WRITER);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    LowLevelHttpResponse execute(OutputWriter object) throws IOException {
        Throwable throwable2222;
        HttpURLConnection httpURLConnection = this.connection;
        if (this.getStreamingContent() != null) {
            long l2;
            long l;
            Object object2 = this.getContentType();
            if (object2 != null) {
                this.addHeader("Content-Type", (String)object2);
            }
            if ((object2 = this.getContentEncoding()) != null) {
                this.addHeader("Content-Encoding", (String)object2);
            }
            if ((l = (l2 = this.getContentLength() LCMP 0L)) >= 0) {
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(l2));
            }
            if (!"POST".equals(object2 = httpURLConnection.getRequestMethod()) && !"PUT".equals(object2)) {
                boolean bl = l == false;
                Preconditions.checkArgument(bl, "%s with non-zero content length is not supported", object2);
            } else {
                httpURLConnection.setDoOutput(true);
                if (l >= 0 && l2 <= Integer.MAX_VALUE) {
                    httpURLConnection.setFixedLengthStreamingMode((int)l2);
                } else {
                    httpURLConnection.setChunkedStreamingMode(0);
                }
                object2 = httpURLConnection.getOutputStream();
                this.writeContentToOutputStream((OutputWriter)object, (OutputStream)object2);
                ((OutputStream)object2).close();
                {
                    block21 : {
                        catch (Throwable throwable2222) {
                            break block21;
                        }
                        catch (IOException iOException) {}
                        {
                            boolean bl = this.hasResponse(httpURLConnection);
                            if (!bl) throw iOException;
                        }
                        try {
                            ((OutputStream)object2).close();
                        }
                        catch (IOException iOException) {}
                    }
                    ((OutputStream)object2).close();
                    throw throwable2222;
                }
            }
        }
        try {
            httpURLConnection.connect();
            return new NetHttpResponse(httpURLConnection);
        }
        catch (Throwable throwable3) {
            httpURLConnection.disconnect();
            throw throwable3;
        }
        catch (IOException iOException) {
            throw throwable2222;
        }
    }

    String getRequestProperty(String string2) {
        return this.connection.getRequestProperty(string2);
    }

    @Override
    public void setTimeout(int n, int n2) {
        this.connection.setReadTimeout(n2);
        this.connection.setConnectTimeout(n);
    }

    @Override
    public void setWriteTimeout(int n) throws IOException {
        this.writeTimeout = n;
    }

    static class DefaultOutputWriter
    implements OutputWriter {
        DefaultOutputWriter() {
        }

        @Override
        public void write(OutputStream outputStream2, StreamingContent streamingContent) throws IOException {
            streamingContent.writeTo(outputStream2);
        }
    }

    static interface OutputWriter {
        public void write(OutputStream var1, StreamingContent var2) throws IOException;
    }

}

