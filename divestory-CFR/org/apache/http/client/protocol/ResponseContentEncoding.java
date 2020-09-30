/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;

public class ResponseContentEncoding
implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse object, HttpContext object2) throws HttpException, IOException {
        object2 = object.getEntity();
        if (object2 == null) return;
        if ((object2 = object2.getContentEncoding()) == null) return;
        if (((HeaderElement[])(object2 = object2.getElements())).length <= 0) return;
        String string2 = (object2 = object2[0]).getName().toLowerCase(Locale.US);
        if (!"gzip".equals(string2) && !"x-gzip".equals(string2)) {
            if ("deflate".equals(string2)) {
                object.setEntity(new DeflateDecompressingEntity(object.getEntity()));
                return;
            }
            if ("identity".equals(string2)) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported Content-Coding: ");
            ((StringBuilder)object).append(object2.getName());
            throw new HttpException(((StringBuilder)object).toString());
        }
        object.setEntity(new GzipDecompressingEntity(object.getEntity()));
    }
}

