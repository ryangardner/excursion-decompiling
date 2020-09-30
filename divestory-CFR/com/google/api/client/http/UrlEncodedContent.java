/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UrlEncodedContent
extends AbstractHttpContent {
    private Object data;

    public UrlEncodedContent(Object object) {
        super(UrlEncodedParser.MEDIA_TYPE);
        this.setData(object);
    }

    private static boolean appendParam(boolean bl, Writer writer, String string2, Object object) throws IOException {
        boolean bl2 = bl;
        if (object == null) return bl2;
        if (Data.isNull(object)) {
            return bl;
        }
        if (bl) {
            bl = false;
        } else {
            writer.write("&");
        }
        writer.write(string2);
        string2 = object instanceof Enum ? FieldInfo.of((Enum)object).getName() : object.toString();
        string2 = CharEscapers.escapeUri(string2);
        bl2 = bl;
        if (string2.length() == 0) return bl2;
        writer.write("=");
        writer.write(string2);
        return bl;
    }

    public static UrlEncodedContent getContent(HttpRequest httpRequest) {
        HttpContent httpContent = httpRequest.getContent();
        if (httpContent != null) {
            return (UrlEncodedContent)httpContent;
        }
        httpContent = new UrlEncodedContent(new HashMap());
        httpRequest.setContent(httpContent);
        return httpContent;
    }

    public final Object getData() {
        return this.data;
    }

    public UrlEncodedContent setData(Object object) {
        this.data = Preconditions.checkNotNull(object);
        return this;
    }

    @Override
    public UrlEncodedContent setMediaType(HttpMediaType httpMediaType) {
        super.setMediaType(httpMediaType);
        return this;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void writeTo(OutputStream var1_1) throws IOException {
        var1_1 = new BufferedWriter(new OutputStreamWriter((OutputStream)var1_1, this.getCharset()));
        var2_2 = Data.mapOf(this.data).entrySet().iterator();
        var3_3 = true;
        block0 : do {
            if (!var2_2.hasNext()) {
                var1_1.flush();
                return;
            }
            var4_4 = var2_2.next();
            var5_5 = var4_4.getValue();
            if (var5_5 == null) continue;
            var4_4 = CharEscapers.escapeUri(var4_4.getKey());
            var6_6 = var5_5.getClass();
            if (!(var5_5 instanceof Iterable) && !var6_6.isArray()) {
                var3_3 = UrlEncodedContent.appendParam(var3_3, (Writer)var1_1, (String)var4_4, var5_5);
                continue;
            }
            var5_5 = Types.iterableOf(var5_5).iterator();
            var7_7 = var3_3;
            do {
                var3_3 = var7_7;
                if (var5_5.hasNext()) ** break;
                continue block0;
                var7_7 = UrlEncodedContent.appendParam(var7_7, (Writer)var1_1, (String)var4_4, var5_5.next());
            } while (true);
            break;
        } while (true);
    }
}

