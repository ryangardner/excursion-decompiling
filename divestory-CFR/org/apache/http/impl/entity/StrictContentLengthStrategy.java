/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.ContentLengthStrategy;

public class StrictContentLengthStrategy
implements ContentLengthStrategy {
    @Override
    public long determineLength(HttpMessage object) throws HttpException {
        if (object == null) throw new IllegalArgumentException("HTTP message may not be null");
        Object object2 = object.getFirstHeader("Transfer-Encoding");
        Header header = object.getFirstHeader("Content-Length");
        if (object2 != null) {
            if ("chunked".equalsIgnoreCase((String)(object2 = object2.getValue()))) {
                if (!object.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    return -2L;
                }
                object2 = new StringBuffer();
                ((StringBuffer)object2).append("Chunked transfer encoding not allowed for ");
                ((StringBuffer)object2).append(object.getProtocolVersion());
                throw new ProtocolException(((StringBuffer)object2).toString());
            }
            if ("identity".equalsIgnoreCase((String)object2)) {
                return -1L;
            }
            object = new StringBuffer();
            ((StringBuffer)object).append("Unsupported transfer encoding: ");
            ((StringBuffer)object).append((String)object2);
            throw new ProtocolException(((StringBuffer)object).toString());
        }
        if (header == null) return -1L;
        object = header.getValue();
        try {
            return Long.parseLong((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid content length: ");
            stringBuffer.append((String)object);
            throw new ProtocolException(stringBuffer.toString());
        }
    }
}

