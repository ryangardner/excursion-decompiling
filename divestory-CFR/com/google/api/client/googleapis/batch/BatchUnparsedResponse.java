/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.batch;

import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.ObjectParser;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class BatchUnparsedResponse {
    private final String boundary;
    private int contentId = 0;
    boolean hasNext = true;
    private final InputStream inputStream;
    private final List<BatchRequest.RequestInfo<?, ?>> requestInfos;
    private final boolean retryAllowed;
    List<BatchRequest.RequestInfo<?, ?>> unsuccessfulRequestInfos = new ArrayList();

    BatchUnparsedResponse(InputStream inputStream2, String string2, List<BatchRequest.RequestInfo<?, ?>> list, boolean bl) throws IOException {
        this.boundary = string2;
        this.requestInfos = list;
        this.retryAllowed = bl;
        this.inputStream = inputStream2;
        this.checkForFinalBoundary(this.readLine());
    }

    private void checkForFinalBoundary(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.boundary);
        stringBuilder.append("--");
        if (!string2.equals(stringBuilder.toString())) return;
        this.hasNext = false;
        this.inputStream.close();
    }

    private HttpResponse getFakeResponse(int n, InputStream object, List<String> list, List<String> list2) throws IOException {
        object = new FakeResponseHttpTransport(n, (InputStream)object, list, list2).createRequestFactory().buildPostRequest(new GenericUrl("http://google.com/"), null);
        ((HttpRequest)object).setLoggingEnabled(false);
        ((HttpRequest)object).setThrowExceptionOnExecuteError(false);
        return ((HttpRequest)object).execute();
    }

    private <A, T, E> A getParsedDataClass(Class<A> class_, HttpResponse httpResponse, BatchRequest.RequestInfo<T, E> requestInfo) throws IOException {
        if (class_ != Void.class) return requestInfo.request.getParser().parseAndClose(httpResponse.getContent(), httpResponse.getContentCharset(), class_);
        return null;
    }

    private <T, E> void parseAndCallback(BatchRequest.RequestInfo<T, E> requestInfo, int n, HttpResponse httpResponse) throws IOException {
        BatchCallback batchCallback = requestInfo.callback;
        HttpHeaders httpHeaders = httpResponse.getHeaders();
        HttpUnsuccessfulResponseHandler httpUnsuccessfulResponseHandler = requestInfo.request.getUnsuccessfulResponseHandler();
        if (HttpStatusCodes.isSuccess(n)) {
            if (batchCallback == null) {
                return;
            }
            batchCallback.onSuccess(this.getParsedDataClass(requestInfo.dataClass, httpResponse, requestInfo), httpHeaders);
            return;
        }
        HttpContent httpContent = requestInfo.request.getContent();
        boolean bl = this.retryAllowed;
        n = 1;
        bl = bl && (httpContent == null || httpContent.retrySupported());
        boolean bl2 = httpUnsuccessfulResponseHandler != null ? httpUnsuccessfulResponseHandler.handleResponse(requestInfo.request, httpResponse, bl) : false;
        if (bl2 || !requestInfo.request.handleRedirect(httpResponse.getStatusCode(), httpResponse.getHeaders())) {
            n = 0;
        }
        if (bl && (bl2 || n != 0)) {
            this.unsuccessfulRequestInfos.add(requestInfo);
            return;
        }
        if (batchCallback == null) {
            return;
        }
        batchCallback.onFailure(this.getParsedDataClass(requestInfo.errorClass, httpResponse, requestInfo), httpHeaders);
    }

    private String readLine() throws IOException {
        return BatchUnparsedResponse.trimCrlf(this.readRawLine());
    }

    private String readRawLine() throws IOException {
        int n = this.inputStream.read();
        if (n == -1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (n != -1) {
            stringBuilder.append((char)n);
            if (n == 10) {
                return stringBuilder.toString();
            }
            n = this.inputStream.read();
        }
        return stringBuilder.toString();
    }

    private static InputStream trimCrlf(byte[] arrby) {
        int n;
        int n2 = n = arrby.length;
        if (n > 0) {
            n2 = n;
            if (arrby[n - 1] == 10) {
                n2 = n - 1;
            }
        }
        n = n2;
        if (n2 <= 0) return new ByteArrayInputStream(arrby, 0, n);
        n = n2;
        if (arrby[n2 - 1] != 13) return new ByteArrayInputStream(arrby, 0, n);
        n = n2 - 1;
        return new ByteArrayInputStream(arrby, 0, n);
    }

    private static String trimCrlf(String string2) {
        if (string2.endsWith("\r\n")) {
            return string2.substring(0, string2.length() - 2);
        }
        String string3 = string2;
        if (!string2.endsWith("\n")) return string3;
        return string2.substring(0, string2.length() - 1);
    }

    void parseNextResponse() throws IOException {
        String string2;
        Object object;
        ++this.contentId;
        while ((string2 = this.readLine()) != null && !string2.equals("")) {
        }
        int n = Integer.parseInt(this.readLine().split(" ")[1]);
        Object object2 = new ArrayList<String>();
        ArrayList<String> arrayList = new ArrayList<String>();
        long l = -1L;
        while ((string2 = this.readLine()) != null && !string2.equals("")) {
            object = string2.split(": ", 2);
            string2 = object[0];
            object = object[1];
            object2.add(string2);
            arrayList.add((String)object);
            if (!"Content-Length".equalsIgnoreCase(string2.trim())) continue;
            l = Long.parseLong((String)object);
        }
        long l2 = l LCMP -1L;
        if (l2 == false) {
            object = new ByteArrayOutputStream();
            while ((string2 = this.readRawLine()) != null && !string2.startsWith(this.boundary)) {
                ((OutputStream)object).write(string2.getBytes("ISO-8859-1"));
            }
            object = BatchUnparsedResponse.trimCrlf(((ByteArrayOutputStream)object).toByteArray());
            string2 = BatchUnparsedResponse.trimCrlf(string2);
        } else {
            object = new FilterInputStream(ByteStreams.limit(this.inputStream, l)){

                @Override
                public void close() {
                }
            };
        }
        object2 = this.getFakeResponse(n, (InputStream)object, (List<String>)object2, (List<String>)arrayList);
        this.parseAndCallback(this.requestInfos.get(this.contentId - 1), n, (HttpResponse)object2);
        while (((InputStream)object).skip(l) > 0L || ((InputStream)object).read() != -1) {
        }
        if (l2 != false) {
            string2 = this.readLine();
        }
        while (string2 != null && string2.length() == 0) {
            string2 = this.readLine();
        }
        this.checkForFinalBoundary(string2);
    }

    private static class FakeLowLevelHttpRequest
    extends LowLevelHttpRequest {
        private List<String> headerNames;
        private List<String> headerValues;
        private InputStream partContent;
        private int statusCode;

        FakeLowLevelHttpRequest(InputStream inputStream2, int n, List<String> list, List<String> list2) {
            this.partContent = inputStream2;
            this.statusCode = n;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override
        public void addHeader(String string2, String string3) {
        }

        @Override
        public LowLevelHttpResponse execute() {
            return new FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        }
    }

    private static class FakeLowLevelHttpResponse
    extends LowLevelHttpResponse {
        private List<String> headerNames = new ArrayList<String>();
        private List<String> headerValues = new ArrayList<String>();
        private InputStream partContent;
        private int statusCode;

        FakeLowLevelHttpResponse(InputStream inputStream2, int n, List<String> list, List<String> list2) {
            this.partContent = inputStream2;
            this.statusCode = n;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override
        public InputStream getContent() {
            return this.partContent;
        }

        @Override
        public String getContentEncoding() {
            return null;
        }

        @Override
        public long getContentLength() {
            return 0L;
        }

        @Override
        public String getContentType() {
            return null;
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
            return null;
        }

        @Override
        public int getStatusCode() {
            return this.statusCode;
        }

        @Override
        public String getStatusLine() {
            return null;
        }
    }

    private static class FakeResponseHttpTransport
    extends HttpTransport {
        private List<String> headerNames;
        private List<String> headerValues;
        private InputStream partContent;
        private int statusCode;

        FakeResponseHttpTransport(int n, InputStream inputStream2, List<String> list, List<String> list2) {
            this.statusCode = n;
            this.partContent = inputStream2;
            this.headerNames = list;
            this.headerValues = list2;
        }

        @Override
        protected LowLevelHttpRequest buildRequest(String string2, String string3) {
            return new FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        }
    }

}

