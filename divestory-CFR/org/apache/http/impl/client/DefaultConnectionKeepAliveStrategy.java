/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;

public class DefaultConnectionKeepAliveStrategy
implements ConnectionKeepAliveStrategy {
    @Override
    public long getKeepAliveDuration(HttpResponse object, HttpContext object2) {
        if (object == null) throw new IllegalArgumentException("HTTP response may not be null");
        object = new BasicHeaderElementIterator(object.headerIterator("Keep-Alive"));
        while (object.hasNext()) {
            Object object3 = object.nextElement();
            object2 = object3.getName();
            if ((object3 = object3.getValue()) == null || !((String)object2).equalsIgnoreCase("timeout")) continue;
            try {
                long l = Long.parseLong((String)object3);
                return l * 1000L;
            }
            catch (NumberFormatException numberFormatException) {
            }
        }
        return -1L;
    }
}

