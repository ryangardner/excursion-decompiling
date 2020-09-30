/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public final class EntityUtils {
    private EntityUtils() {
    }

    public static void consume(HttpEntity object) throws IOException {
        if (object == null) {
            return;
        }
        if (!object.isStreaming()) return;
        if ((object = object.getContent()) == null) return;
        ((InputStream)object).close();
    }

    public static String getContentCharSet(HttpEntity object) throws ParseException {
        String string2;
        if (object == null) throw new IllegalArgumentException("HTTP entity may not be null");
        String string3 = string2 = null;
        if (object.getContentType() == null) return string3;
        object = object.getContentType().getElements();
        string3 = string2;
        if (((HeaderElement[])object).length <= 0) return string3;
        object = object[0].getParameterByName("charset");
        string3 = string2;
        if (object == null) return string3;
        return object.getValue();
    }

    public static String getContentMimeType(HttpEntity arrheaderElement) throws ParseException {
        String string2;
        if (arrheaderElement == null) throw new IllegalArgumentException("HTTP entity may not be null");
        String string3 = string2 = null;
        if (arrheaderElement.getContentType() == null) return string3;
        arrheaderElement = arrheaderElement.getContentType().getElements();
        string3 = string2;
        if (arrheaderElement.length <= 0) return string3;
        return arrheaderElement[0].getName();
    }

    public static byte[] toByteArray(HttpEntity arrby) throws IOException {
        if (arrby == null) throw new IllegalArgumentException("HTTP entity may not be null");
        InputStream inputStream2 = arrby.getContent();
        if (inputStream2 == null) {
            return null;
        }
        try {
            if (arrby.getContentLength() <= Integer.MAX_VALUE) {
                int n;
                int n2 = n = (int)arrby.getContentLength();
                if (n < 0) {
                    n2 = 4096;
                }
                arrby = new ByteArrayBuffer(n2);
                byte[] arrby2 = new byte[4096];
                do {
                    if ((n2 = inputStream2.read(arrby2)) == -1) {
                        arrby = arrby.toByteArray();
                        return arrby;
                    }
                    arrby.append(arrby2, 0, n2);
                } while (true);
            }
            arrby = new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            throw arrby;
        }
        finally {
            inputStream2.close();
        }
    }

    public static String toString(HttpEntity httpEntity) throws IOException, ParseException {
        return EntityUtils.toString(httpEntity, null);
    }

    public static String toString(HttpEntity object, String object2) throws IOException, ParseException {
        if (object == null) throw new IllegalArgumentException("HTTP entity may not be null");
        InputStream inputStream2 = object.getContent();
        if (inputStream2 == null) {
            return null;
        }
        try {
            if (object.getContentLength() <= Integer.MAX_VALUE) {
                int n;
                int n2 = n = (int)object.getContentLength();
                if (n < 0) {
                    n2 = 4096;
                }
                if ((object = EntityUtils.getContentCharSet((HttpEntity)object)) != null) {
                    object2 = object;
                }
                object = object2;
                if (object2 == null) {
                    object = "ISO-8859-1";
                }
                object2 = new InputStreamReader(inputStream2, (String)object);
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(n2);
                object = new char[1024];
                do {
                    if ((n2 = ((Reader)object2).read((char[])object)) == -1) {
                        object = charArrayBuffer.toString();
                        return object;
                    }
                    charArrayBuffer.append((char[])object, 0, n2);
                } while (true);
            }
            object = new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            throw object;
        }
        finally {
            inputStream2.close();
        }
    }
}

