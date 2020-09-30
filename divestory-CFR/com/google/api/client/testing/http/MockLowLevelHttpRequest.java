/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.StreamingContent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class MockLowLevelHttpRequest
extends LowLevelHttpRequest {
    private final Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
    private MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
    private String url;

    public MockLowLevelHttpRequest() {
    }

    public MockLowLevelHttpRequest(String string2) {
        this.url = string2;
    }

    @Override
    public void addHeader(String arrayList, String string2) throws IOException {
        String string3 = ((String)((Object)arrayList)).toLowerCase(Locale.US);
        List<String> list = this.headersMap.get(string3);
        arrayList = list;
        if (list == null) {
            arrayList = new ArrayList<String>();
            this.headersMap.put(string3, arrayList);
        }
        arrayList.add(string2);
    }

    @Override
    public LowLevelHttpResponse execute() throws IOException {
        return this.response;
    }

    public String getContentAsString() throws IOException {
        if (this.getStreamingContent() == null) {
            return "";
        }
        Object object = new ByteArrayOutputStream();
        this.getStreamingContent().writeTo((OutputStream)object);
        String string2 = this.getContentEncoding();
        ByteArrayOutputStream byteArrayOutputStream = object;
        if (string2 != null) {
            byteArrayOutputStream = object;
            if (string2.contains("gzip")) {
                object = new GZIPInputStream(new ByteArrayInputStream(((ByteArrayOutputStream)object).toByteArray()));
                byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy((InputStream)object, byteArrayOutputStream);
            }
        }
        if ((object = (object = this.getContentType()) != null ? new HttpMediaType((String)object) : null) != null && ((HttpMediaType)object).getCharsetParameter() != null) {
            object = ((HttpMediaType)object).getCharsetParameter();
            return byteArrayOutputStream.toString(((Charset)object).name());
        }
        object = Charsets.ISO_8859_1;
        return byteArrayOutputStream.toString(((Charset)object).name());
    }

    public String getFirstHeaderValue(String object) {
        if ((object = this.headersMap.get(((String)object).toLowerCase(Locale.US))) != null) return (String)object.get(0);
        return null;
    }

    public List<String> getHeaderValues(String list) {
        if ((list = this.headersMap.get(((String)((Object)list)).toLowerCase(Locale.US))) != null) return Collections.unmodifiableList(list);
        return Collections.emptyList();
    }

    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(this.headersMap);
    }

    public MockLowLevelHttpResponse getResponse() {
        return this.response;
    }

    public String getUrl() {
        return this.url;
    }

    public MockLowLevelHttpRequest setResponse(MockLowLevelHttpResponse mockLowLevelHttpResponse) {
        this.response = mockLowLevelHttpResponse;
        return this;
    }

    public MockLowLevelHttpRequest setUrl(String string2) {
        this.url = string2;
        return this;
    }
}

