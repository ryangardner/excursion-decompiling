/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.entity;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.impl.io.ContentLengthOutputStream;
import org.apache.http.impl.io.IdentityOutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class EntitySerializer {
    private final ContentLengthStrategy lenStrategy;

    public EntitySerializer(ContentLengthStrategy contentLengthStrategy) {
        if (contentLengthStrategy == null) throw new IllegalArgumentException("Content length strategy may not be null");
        this.lenStrategy = contentLengthStrategy;
    }

    protected OutputStream doSerialize(SessionOutputBuffer sessionOutputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        long l = this.lenStrategy.determineLength(httpMessage);
        if (l == -2L) {
            return new ChunkedOutputStream(sessionOutputBuffer);
        }
        if (l != -1L) return new ContentLengthOutputStream(sessionOutputBuffer, l);
        return new IdentityOutputStream(sessionOutputBuffer);
    }

    public void serialize(SessionOutputBuffer object, HttpMessage httpMessage, HttpEntity httpEntity) throws HttpException, IOException {
        if (object == null) throw new IllegalArgumentException("Session output buffer may not be null");
        if (httpMessage == null) throw new IllegalArgumentException("HTTP message may not be null");
        if (httpEntity == null) throw new IllegalArgumentException("HTTP entity may not be null");
        object = this.doSerialize((SessionOutputBuffer)object, httpMessage);
        httpEntity.writeTo((OutputStream)object);
        ((OutputStream)object).close();
    }
}

