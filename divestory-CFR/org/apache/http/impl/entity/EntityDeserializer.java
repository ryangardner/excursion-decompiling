/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.entity;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.io.SessionInputBuffer;

public class EntityDeserializer {
    private final ContentLengthStrategy lenStrategy;

    public EntityDeserializer(ContentLengthStrategy contentLengthStrategy) {
        if (contentLengthStrategy == null) throw new IllegalArgumentException("Content length strategy may not be null");
        this.lenStrategy = contentLengthStrategy;
    }

    public HttpEntity deserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Session input buffer may not be null");
        if (httpMessage == null) throw new IllegalArgumentException("HTTP message may not be null");
        return this.doDeserialize(sessionInputBuffer, httpMessage);
    }

    protected BasicHttpEntity doDeserialize(SessionInputBuffer object, HttpMessage httpMessage) throws HttpException, IOException {
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        long l = this.lenStrategy.determineLength(httpMessage);
        if (l == -2L) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new ChunkedInputStream((SessionInputBuffer)object));
        } else if (l == -1L) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new IdentityInputStream((SessionInputBuffer)object));
        } else {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(l);
            basicHttpEntity.setContent(new ContentLengthInputStream((SessionInputBuffer)object, l));
        }
        object = httpMessage.getFirstHeader("Content-Type");
        if (object != null) {
            basicHttpEntity.setContentType((Header)object);
        }
        if ((object = httpMessage.getFirstHeader("Content-Encoding")) == null) return basicHttpEntity;
        basicHttpEntity.setContentEncoding((Header)object);
        return basicHttpEntity;
    }
}

