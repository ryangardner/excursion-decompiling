/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.util.TestableByteArrayInputStream;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MockLowLevelHttpResponse
extends LowLevelHttpResponse {
    private InputStream content;
    private String contentEncoding;
    private long contentLength = -1L;
    private String contentType;
    private List<String> headerNames = new ArrayList<String>();
    private List<String> headerValues = new ArrayList<String>();
    private boolean isDisconnected;
    private String reasonPhrase;
    private int statusCode = 200;

    public MockLowLevelHttpResponse addHeader(String string2, String string3) {
        this.headerNames.add(Preconditions.checkNotNull(string2));
        this.headerValues.add(Preconditions.checkNotNull(string3));
        return this;
    }

    @Override
    public void disconnect() throws IOException {
        this.isDisconnected = true;
        super.disconnect();
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.content;
    }

    @Override
    public String getContentEncoding() {
        return this.contentEncoding;
    }

    @Override
    public long getContentLength() {
        return this.contentLength;
    }

    @Override
    public final String getContentType() {
        return this.contentType;
    }

    @Override
    public int getHeaderCount() {
        return this.headerNames.size();
    }

    @Override
    public String getHeaderName(int n) {
        return this.headerNames.get(n);
    }

    public final List<String> getHeaderNames() {
        return this.headerNames;
    }

    @Override
    public String getHeaderValue(int n) {
        return this.headerValues.get(n);
    }

    public final List<String> getHeaderValues() {
        return this.headerValues;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getStatusLine() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.statusCode);
        String string2 = this.reasonPhrase;
        if (string2 == null) return stringBuilder.toString();
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public boolean isDisconnected() {
        return this.isDisconnected;
    }

    public MockLowLevelHttpResponse setContent(InputStream inputStream2) {
        this.content = inputStream2;
        return this;
    }

    public MockLowLevelHttpResponse setContent(String object) {
        if (object != null) return this.setContent(StringUtils.getBytesUtf8((String)object));
        return this.setZeroContent();
    }

    public MockLowLevelHttpResponse setContent(byte[] arrby) {
        if (arrby == null) {
            return this.setZeroContent();
        }
        this.content = new TestableByteArrayInputStream(arrby);
        this.setContentLength(arrby.length);
        return this;
    }

    public MockLowLevelHttpResponse setContentEncoding(String string2) {
        this.contentEncoding = string2;
        return this;
    }

    public MockLowLevelHttpResponse setContentLength(long l) {
        this.contentLength = l;
        boolean bl = l >= -1L;
        Preconditions.checkArgument(bl);
        return this;
    }

    public MockLowLevelHttpResponse setContentType(String string2) {
        this.contentType = string2;
        return this;
    }

    public MockLowLevelHttpResponse setHeaderNames(List<String> list) {
        this.headerNames = Preconditions.checkNotNull(list);
        return this;
    }

    public MockLowLevelHttpResponse setHeaderValues(List<String> list) {
        this.headerValues = Preconditions.checkNotNull(list);
        return this;
    }

    public MockLowLevelHttpResponse setReasonPhrase(String string2) {
        this.reasonPhrase = string2;
        return this;
    }

    public MockLowLevelHttpResponse setStatusCode(int n) {
        this.statusCode = n;
        return this;
    }

    public MockLowLevelHttpResponse setZeroContent() {
        this.content = null;
        this.setContentLength(0L);
        return this;
    }
}

