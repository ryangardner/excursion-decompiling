/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.params.HttpParams;

public class LaxContentLengthStrategy
implements ContentLengthStrategy {
    @Override
    public long determineLength(HttpMessage object) throws HttpException {
        long l;
        block11 : {
            if (object == null) throw new IllegalArgumentException("HTTP message may not be null");
            boolean bl = object.getParams().isParameterTrue("http.protocol.strict-transfer-encoding");
            Object object2 = object.getFirstHeader("Transfer-Encoding");
            HeaderElement[] arrheaderElement = object.getFirstHeader("Content-Length");
            if (object2 != null) {
                int n;
                block10 : {
                    try {
                        arrheaderElement = object2.getElements();
                        if (!bl) break block10;
                    }
                    catch (ParseException parseException) {
                        object = new StringBuffer();
                        ((StringBuffer)object).append("Invalid Transfer-Encoding header value: ");
                        ((StringBuffer)object).append(object2);
                        throw new ProtocolException(((StringBuffer)object).toString(), parseException);
                    }
                    for (n = 0; n < arrheaderElement.length; ++n) {
                        object = arrheaderElement[n].getName();
                        if (object == null || ((String)object).length() <= 0 || ((String)object).equalsIgnoreCase("chunked") || ((String)object).equalsIgnoreCase("identity")) continue;
                        object2 = new StringBuffer();
                        ((StringBuffer)object2).append("Unsupported transfer encoding: ");
                        ((StringBuffer)object2).append((String)object);
                        throw new ProtocolException(((StringBuffer)object2).toString());
                    }
                }
                n = arrheaderElement.length;
                if ("identity".equalsIgnoreCase(object2.getValue())) {
                    return -1L;
                }
                if (n > 0 && "chunked".equalsIgnoreCase(arrheaderElement[n - 1].getName())) {
                    return -2L;
                }
                if (bl) throw new ProtocolException("Chunk-encoding must be the last one applied");
                return -1L;
            }
            if (arrheaderElement == null) return -1L;
            object2 = object.getHeaders("Content-Length");
            if (bl) {
                if (((Header[])object2).length > 1) throw new ProtocolException("Multiple content length headers");
            }
            for (int i = ((Header[])object2).length - 1; i >= 0; --i) {
                object = object2[i];
                try {
                    l = Long.parseLong(object.getValue());
                    break block11;
                }
                catch (NumberFormatException numberFormatException) {
                    if (!bl) continue;
                    object2 = new StringBuffer();
                    ((StringBuffer)object2).append("Invalid content length: ");
                    ((StringBuffer)object2).append(object.getValue());
                    throw new ProtocolException(((StringBuffer)object2).toString());
                }
            }
            l = -1L;
        }
        if (l < 0L) return -1L;
        return l;
    }
}

