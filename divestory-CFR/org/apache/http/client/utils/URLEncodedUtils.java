/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class URLEncodedUtils {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";

    private static String decode(String string2, String string3) {
        if (string3 == null) {
            string3 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(string2, string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException(unsupportedEncodingException);
        }
    }

    private static String encode(String string2, String string3) {
        if (string3 == null) {
            string3 = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(string2, string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException(unsupportedEncodingException);
        }
    }

    public static String format(List<? extends NameValuePair> object, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<? extends NameValuePair> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            String string3 = URLEncodedUtils.encode(object.getName(), string2);
            object = (object = object.getValue()) != null ? URLEncodedUtils.encode((String)object, string2) : "";
            if (stringBuilder.length() > 0) {
                stringBuilder.append(PARAMETER_SEPARATOR);
            }
            stringBuilder.append(string3);
            stringBuilder.append(NAME_VALUE_SEPARATOR);
            stringBuilder.append((String)object);
        }
        return stringBuilder.toString();
    }

    public static boolean isEncoded(HttpEntity arrheaderElement) {
        if ((arrheaderElement = arrheaderElement.getContentType()) == null) return false;
        if ((arrheaderElement = arrheaderElement.getElements()).length <= 0) return false;
        return arrheaderElement[0].getName().equalsIgnoreCase(CONTENT_TYPE);
    }

    public static List<NameValuePair> parse(URI list, String string2) {
        List<NameValuePair> list2 = Collections.emptyList();
        String string3 = ((URI)((Object)list)).getRawQuery();
        list = list2;
        if (string3 == null) return list;
        list = list2;
        if (string3.length() <= 0) return list;
        list = new ArrayList<NameValuePair>();
        URLEncodedUtils.parse(list, new Scanner(string3), string2);
        return list;
    }

    public static List<NameValuePair> parse(HttpEntity object) throws IOException {
        List list = Collections.emptyList();
        Object object2 = object.getContentType();
        String string2 = null;
        String string3 = null;
        if (object2 != null && ((HeaderElement[])(object2 = object2.getElements())).length > 0) {
            object2 = object2[0];
            string2 = object2.getName();
            if ((object2 = object2.getParameterByName("charset")) != null) {
                string3 = object2.getValue();
            }
        } else {
            string3 = null;
        }
        object2 = list;
        if (string2 == null) return object2;
        object2 = list;
        if (!string2.equalsIgnoreCase(CONTENT_TYPE)) return object2;
        object = EntityUtils.toString((HttpEntity)object, "ASCII");
        object2 = list;
        if (object == null) return object2;
        object2 = list;
        if (((String)object).length() <= 0) return object2;
        object2 = new ArrayList();
        URLEncodedUtils.parse((List<NameValuePair>)object2, new Scanner((String)object), string3);
        return object2;
    }

    public static void parse(List<NameValuePair> list, Scanner scanner, String string2) {
        scanner.useDelimiter(PARAMETER_SEPARATOR);
        while (scanner.hasNext()) {
            String[] arrstring = scanner.next().split(NAME_VALUE_SEPARATOR);
            if (arrstring.length == 0) throw new IllegalArgumentException("bad parameter");
            if (arrstring.length > 2) throw new IllegalArgumentException("bad parameter");
            String string3 = URLEncodedUtils.decode(arrstring[0], string2);
            String string4 = null;
            if (arrstring.length == 2) {
                string4 = URLEncodedUtils.decode(arrstring[1], string2);
            }
            list.add(new BasicNameValuePair(string3, string4));
        }
    }
}

