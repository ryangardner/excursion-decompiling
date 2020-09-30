/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpEncoding;
import com.google.api.client.http.HttpEncodingStreamingContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;

public class MultipartContent
extends AbstractHttpContent {
    static final String NEWLINE = "\r\n";
    private static final String TWO_DASHES = "--";
    private ArrayList<Part> parts;

    public MultipartContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("__END_OF_PART__");
        stringBuilder.append(UUID.randomUUID().toString());
        stringBuilder.append("__");
        this(stringBuilder.toString());
    }

    public MultipartContent(String string2) {
        super(new HttpMediaType("multipart/related").setParameter("boundary", string2));
        this.parts = new ArrayList();
    }

    public MultipartContent addPart(Part part) {
        this.parts.add(Preconditions.checkNotNull(part));
        return this;
    }

    public final String getBoundary() {
        return this.getMediaType().getParameter("boundary");
    }

    public final Collection<Part> getParts() {
        return Collections.unmodifiableCollection(this.parts);
    }

    @Override
    public boolean retrySupported() {
        Iterator<Part> iterator2 = this.parts.iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (iterator2.next().content.retrySupported());
        return false;
    }

    public MultipartContent setBoundary(String string2) {
        this.getMediaType().setParameter("boundary", Preconditions.checkNotNull(string2));
        return this;
    }

    public MultipartContent setContentParts(Collection<? extends HttpContent> object) {
        this.parts = new ArrayList(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            this.addPart(new Part((HttpContent)object.next()));
        }
        return this;
    }

    @Override
    public MultipartContent setMediaType(HttpMediaType httpMediaType) {
        super.setMediaType(httpMediaType);
        return this;
    }

    public MultipartContent setParts(Collection<Part> collection) {
        this.parts = new ArrayList<Part>(collection);
        return this;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream2, this.getCharset());
        String string2 = this.getBoundary();
        Iterator<Part> iterator2 = this.parts.iterator();
        do {
            if (!iterator2.hasNext()) {
                outputStreamWriter.write(TWO_DASHES);
                outputStreamWriter.write(string2);
                outputStreamWriter.write(TWO_DASHES);
                outputStreamWriter.write(NEWLINE);
                ((Writer)outputStreamWriter).flush();
                return;
            }
            Object object = iterator2.next();
            HttpHeaders httpHeaders = new HttpHeaders().setAcceptEncoding(null);
            if (((Part)object).headers != null) {
                httpHeaders.fromHttpHeaders(((Part)object).headers);
            }
            httpHeaders.setContentEncoding(null).setUserAgent(null).setContentType(null).setContentLength(null).set("Content-Transfer-Encoding", null);
            Object object2 = ((Part)object).content;
            if (object2 != null) {
                long l;
                httpHeaders.set("Content-Transfer-Encoding", Arrays.asList("binary"));
                httpHeaders.setContentType(object2.getType());
                object = ((Part)object).encoding;
                if (object == null) {
                    l = object2.getLength();
                } else {
                    httpHeaders.setContentEncoding(object.getName());
                    object = new HttpEncodingStreamingContent((StreamingContent)object2, (HttpEncoding)object);
                    l = AbstractHttpContent.computeLength((HttpContent)object2);
                    object2 = object;
                }
                object = object2;
                if (l != -1L) {
                    httpHeaders.setContentLength(l);
                    object = object2;
                }
            } else {
                object = null;
            }
            outputStreamWriter.write(TWO_DASHES);
            outputStreamWriter.write(string2);
            outputStreamWriter.write(NEWLINE);
            HttpHeaders.serializeHeadersForMultipartRequests(httpHeaders, null, null, outputStreamWriter);
            if (object != null) {
                outputStreamWriter.write(NEWLINE);
                ((Writer)outputStreamWriter).flush();
                object.writeTo(outputStream2);
            }
            outputStreamWriter.write(NEWLINE);
        } while (true);
    }

    public static final class Part {
        HttpContent content;
        HttpEncoding encoding;
        HttpHeaders headers;

        public Part() {
            this(null);
        }

        public Part(HttpContent httpContent) {
            this(null, httpContent);
        }

        public Part(HttpHeaders httpHeaders, HttpContent httpContent) {
            this.setHeaders(httpHeaders);
            this.setContent(httpContent);
        }

        public HttpContent getContent() {
            return this.content;
        }

        public HttpEncoding getEncoding() {
            return this.encoding;
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public Part setContent(HttpContent httpContent) {
            this.content = httpContent;
            return this;
        }

        public Part setEncoding(HttpEncoding httpEncoding) {
            this.encoding = httpEncoding;
            return this;
        }

        public Part setHeaders(HttpHeaders httpHeaders) {
            this.headers = httpHeaders;
            return this;
        }
    }

}

