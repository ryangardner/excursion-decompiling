/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;

public class DefaultHttpRequestFactory
implements HttpRequestFactory {
    private static final String[] RFC2616_COMMON_METHODS = new String[]{"GET"};
    private static final String[] RFC2616_ENTITY_ENC_METHODS = new String[]{"POST", "PUT"};
    private static final String[] RFC2616_SPECIAL_METHODS = new String[]{"HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT"};

    private static boolean isOneOf(String[] arrstring, String string2) {
        int n = 0;
        while (n < arrstring.length) {
            if (arrstring[n].equalsIgnoreCase(string2)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    public HttpRequest newHttpRequest(String string2, String charSequence) throws MethodNotSupportedException {
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_COMMON_METHODS, string2)) {
            return new BasicHttpRequest(string2, (String)charSequence);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_ENTITY_ENC_METHODS, string2)) {
            return new BasicHttpEntityEnclosingRequest(string2, (String)charSequence);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_SPECIAL_METHODS, string2)) {
            return new BasicHttpRequest(string2, (String)charSequence);
        }
        charSequence = new StringBuffer();
        ((StringBuffer)charSequence).append(string2);
        ((StringBuffer)charSequence).append(" method not supported");
        throw new MethodNotSupportedException(((StringBuffer)charSequence).toString());
    }

    @Override
    public HttpRequest newHttpRequest(RequestLine object) throws MethodNotSupportedException {
        if (object == null) throw new IllegalArgumentException("Request line may not be null");
        String string2 = object.getMethod();
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_COMMON_METHODS, string2)) {
            return new BasicHttpRequest((RequestLine)object);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_ENTITY_ENC_METHODS, string2)) {
            return new BasicHttpEntityEnclosingRequest((RequestLine)object);
        }
        if (DefaultHttpRequestFactory.isOneOf(RFC2616_SPECIAL_METHODS, string2)) {
            return new BasicHttpRequest((RequestLine)object);
        }
        object = new StringBuffer();
        ((StringBuffer)object).append(string2);
        ((StringBuffer)object).append(" method not supported");
        throw new MethodNotSupportedException(((StringBuffer)object).toString());
    }
}

