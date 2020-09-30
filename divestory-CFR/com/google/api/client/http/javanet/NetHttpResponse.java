/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.javanet;

import com.google.api.client.http.LowLevelHttpResponse;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class NetHttpResponse
extends LowLevelHttpResponse {
    private final HttpURLConnection connection;
    private final ArrayList<String> headerNames = new ArrayList<E>();
    private final ArrayList<String> headerValues = new ArrayList<E>();
    private final int responseCode;
    private final String responseMessage;

    /*
     * Unable to fully structure code
     */
    NetHttpResponse(HttpURLConnection var1_1) throws IOException {
        super();
        this.connection = var1_1;
        var3_3 = var2_2 = var1_1.getResponseCode();
        if (var2_2 == -1) {
            var3_3 = 0;
        }
        this.responseCode = var3_3;
        this.responseMessage = var1_1.getResponseMessage();
        var4_4 = this.headerNames;
        var5_5 = this.headerValues;
        var1_1 = var1_1.getHeaderFields().entrySet().iterator();
        block0 : do lbl-1000: // 3 sources:
        {
            if (var1_1.hasNext() == false) return;
            var6_6 = var1_1.next();
            var7_7 = var6_6.getKey();
            if (var7_7 == null) ** GOTO lbl-1000
            var6_6 = var6_6.getValue().iterator();
            do {
                if (!var6_6.hasNext()) continue block0;
                var8_8 = (String)var6_6.next();
                if (var8_8 == null) continue;
                var4_4.add(var7_7);
                var5_5.add(var8_8);
            } while (true);
            break;
        } while (true);
    }

    @Override
    public void disconnect() {
        this.connection.disconnect();
    }

    @Override
    public InputStream getContent() throws IOException {
        InputStream inputStream2;
        try {
            inputStream2 = this.connection.getInputStream();
        }
        catch (IOException iOException) {
            inputStream2 = this.connection.getErrorStream();
        }
        if (inputStream2 != null) return new SizeValidatingInputStream(inputStream2);
        return null;
    }

    @Override
    public String getContentEncoding() {
        return this.connection.getContentEncoding();
    }

    @Override
    public long getContentLength() {
        String string2 = this.connection.getHeaderField("Content-Length");
        if (string2 != null) return Long.parseLong(string2);
        return -1L;
    }

    @Override
    public String getContentType() {
        return this.connection.getHeaderField("Content-Type");
    }

    @Override
    public int getHeaderCount() {
        return this.headerNames.size();
    }

    @Override
    public String getHeaderName(int n) {
        return this.headerNames.get(n);
    }

    @Override
    public String getHeaderValue(int n) {
        return this.headerValues.get(n);
    }

    @Override
    public String getReasonPhrase() {
        return this.responseMessage;
    }

    @Override
    public int getStatusCode() {
        return this.responseCode;
    }

    @Override
    public String getStatusLine() {
        String string2 = this.connection.getHeaderField(0);
        if (string2 == null) return null;
        if (!string2.startsWith("HTTP/1.")) return null;
        return string2;
    }

    private final class SizeValidatingInputStream
    extends FilterInputStream {
        private long bytesRead;

        public SizeValidatingInputStream(InputStream inputStream2) {
            super(inputStream2);
            this.bytesRead = 0L;
        }

        private void throwIfFalseEOF() throws IOException {
            long l = NetHttpResponse.this.getContentLength();
            if (l == -1L) {
                return;
            }
            long l2 = this.bytesRead;
            if (l2 == 0L) return;
            if (l2 >= l) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Connection closed prematurely: bytesRead = ");
            stringBuilder.append(this.bytesRead);
            stringBuilder.append(", Content-Length = ");
            stringBuilder.append(l);
            throw new IOException(stringBuilder.toString());
        }

        @Override
        public int read() throws IOException {
            int n = this.in.read();
            if (n == -1) {
                this.throwIfFalseEOF();
                return n;
            }
            ++this.bytesRead;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if ((n = this.in.read(arrby, n, n2)) == -1) {
                this.throwIfFalseEOF();
                return n;
            }
            this.bytesRead += (long)n;
            return n;
        }

        @Override
        public long skip(long l) throws IOException {
            l = this.in.skip(l);
            this.bytesRead += l;
            return l;
        }
    }

}

