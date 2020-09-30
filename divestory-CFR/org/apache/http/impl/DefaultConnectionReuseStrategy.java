/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.TokenIterator;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.HttpContext;

public class DefaultConnectionReuseStrategy
implements ConnectionReuseStrategy {
    protected TokenIterator createTokenIterator(HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }

    @Override
    public boolean keepAlive(HttpResponse object, HttpContext object2) {
        if (object == null) throw new IllegalArgumentException("HTTP response may not be null.");
        if (object2 == null) throw new IllegalArgumentException("HTTP context may not be null.");
        if ((object2 = (HttpConnection)object2.getAttribute("http.connection")) != null && !object2.isOpen()) {
            return false;
        }
        object2 = object.getEntity();
        ProtocolVersion protocolVersion = object.getStatusLine().getProtocolVersion();
        if (object2 != null && object2.getContentLength() < 0L) {
            if (!object2.isChunked()) return false;
            if (protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                return false;
            }
        }
        HeaderIterator headerIterator = object.headerIterator("Connection");
        object2 = headerIterator;
        if (!headerIterator.hasNext()) {
            object2 = object.headerIterator("Proxy-Connection");
        }
        if (!object2.hasNext()) return protocolVersion.lessEquals(HttpVersion.HTTP_1_0) ^ true;
        try {
            object2 = this.createTokenIterator((HeaderIterator)object2);
            boolean bl = false;
            do {
                if (!object2.hasNext()) {
                    if (!bl) return protocolVersion.lessEquals(HttpVersion.HTTP_1_0) ^ true;
                    return true;
                }
                object = object2.nextToken();
                if ("Close".equalsIgnoreCase((String)object)) {
                    return false;
                }
                boolean bl2 = "Keep-Alive".equalsIgnoreCase((String)object);
                if (!bl2) continue;
                bl = true;
            } while (true);
        }
        catch (ParseException parseException) {
            return false;
        }
    }
}

