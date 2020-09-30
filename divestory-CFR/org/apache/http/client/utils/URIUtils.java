/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import org.apache.http.HttpHost;

public class URIUtils {
    private URIUtils() {
    }

    public static URI createURI(String string2, String string3, int n, String string4, String string5, String string6) throws URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder();
        if (string3 != null) {
            if (string2 != null) {
                stringBuilder.append(string2);
                stringBuilder.append("://");
            }
            stringBuilder.append(string3);
            if (n > 0) {
                stringBuilder.append(':');
                stringBuilder.append(n);
            }
        }
        if (string4 == null || !string4.startsWith("/")) {
            stringBuilder.append('/');
        }
        if (string4 != null) {
            stringBuilder.append(string4);
        }
        if (string5 != null) {
            stringBuilder.append('?');
            stringBuilder.append(string5);
        }
        if (string6 == null) return new URI(stringBuilder.toString());
        stringBuilder.append('#');
        stringBuilder.append(string6);
        return new URI(stringBuilder.toString());
    }

    public static HttpHost extractHost(URI object) {
        String string2 = null;
        if (object == null) {
            return null;
        }
        Object object2 = string2;
        if (!((URI)object).isAbsolute()) return object2;
        int n = ((URI)object).getPort();
        object2 = ((URI)object).getHost();
        int n2 = n;
        Object object3 = object2;
        if (object2 == null) {
            String string3 = ((URI)object).getAuthority();
            n2 = n;
            object3 = string3;
            if (string3 != null) {
                int n3 = string3.indexOf(64);
                object2 = string3;
                if (n3 >= 0) {
                    n2 = string3.length();
                    object2 = n2 > ++n3 ? string3.substring(n3) : null;
                }
                n2 = n;
                object3 = object2;
                if (object2 != null) {
                    n3 = ((String)object2).indexOf(58);
                    n2 = n;
                    object3 = object2;
                    if (n3 >= 0) {
                        n2 = n3 + 1;
                        if (n2 < ((String)object2).length()) {
                            n = Integer.parseInt(((String)object2).substring(n2));
                        }
                        object3 = ((String)object2).substring(0, n3);
                        n2 = n;
                    }
                }
            }
        }
        object = ((URI)object).getScheme();
        object2 = string2;
        if (object3 == null) return object2;
        return new HttpHost((String)object3, n2, (String)object);
    }

    private static String normalizePath(String string2) {
        int n;
        if (string2 == null) {
            return null;
        }
        for (n = 0; n < string2.length() && string2.charAt(n) == '/'; ++n) {
        }
        String string3 = string2;
        if (n <= 1) return string3;
        return string2.substring(n - 1);
    }

    private static URI removeDotSegments(URI uRI) {
        Object object = uRI.getPath();
        if (object == null) return uRI;
        if (((String)object).indexOf("/.") == -1) {
            return uRI;
        }
        object = ((String)object).split("/");
        Object object2 = new Stack();
        for (int i = 0; i < ((String[])object).length; ++i) {
            if (object[i].length() == 0 || ".".equals(object[i])) continue;
            if ("..".equals(object[i])) {
                if (((Vector)object2).isEmpty()) continue;
                ((Stack)object2).pop();
                continue;
            }
            ((Stack)object2).push(object[i]);
        }
        object = new StringBuilder();
        Iterator iterator2 = ((Vector)object2).iterator();
        while (iterator2.hasNext()) {
            object2 = (String)iterator2.next();
            ((StringBuilder)object).append('/');
            ((StringBuilder)object).append((String)object2);
        }
        try {
            return new URI(uRI.getScheme(), uRI.getAuthority(), ((StringBuilder)object).toString(), uRI.getQuery(), uRI.getFragment());
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException(uRISyntaxException);
        }
    }

    public static URI resolve(URI uRI, String string2) {
        return URIUtils.resolve(uRI, URI.create(string2));
    }

    public static URI resolve(URI object, URI uRI) {
        if (object == null) throw new IllegalArgumentException("Base URI may nor be null");
        if (uRI == null) throw new IllegalArgumentException("Reference URI may nor be null");
        String string2 = uRI.toString();
        if (string2.startsWith("?")) {
            return URIUtils.resolveReferenceStartingWithQueryString((URI)object, uRI);
        }
        boolean bl = string2.length() == 0;
        if (bl) {
            uRI = URI.create("#");
        }
        uRI = ((URI)object).resolve(uRI);
        object = uRI;
        if (!bl) return URIUtils.removeDotSegments((URI)object);
        object = uRI.toString();
        object = URI.create(((String)object).substring(0, ((String)object).indexOf(35)));
        return URIUtils.removeDotSegments((URI)object);
    }

    private static URI resolveReferenceStartingWithQueryString(URI object, URI uRI) {
        CharSequence charSequence = ((URI)object).toString();
        object = charSequence;
        if (((String)charSequence).indexOf(63) > -1) {
            object = ((String)charSequence).substring(0, ((String)charSequence).indexOf(63));
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(uRI.toString());
        return URI.create(((StringBuilder)charSequence).toString());
    }

    public static URI rewriteURI(URI uRI, HttpHost httpHost) throws URISyntaxException {
        return URIUtils.rewriteURI(uRI, httpHost, false);
    }

    public static URI rewriteURI(URI object, HttpHost object2, boolean bl) throws URISyntaxException {
        if (object == null) throw new IllegalArgumentException("URI may nor be null");
        String string2 = null;
        String string3 = null;
        if (object2 != null) {
            String string4 = ((HttpHost)object2).getSchemeName();
            string2 = ((HttpHost)object2).getHostName();
            int n = ((HttpHost)object2).getPort();
            String string5 = URIUtils.normalizePath(((URI)object).getRawPath());
            object2 = ((URI)object).getRawQuery();
            if (bl) {
                object = string3;
                return URIUtils.createURI(string4, string2, n, string5, (String)object2, (String)object);
            }
            object = ((URI)object).getRawFragment();
            return URIUtils.createURI(string4, string2, n, string5, (String)object2, (String)object);
        }
        object2 = URIUtils.normalizePath(((URI)object).getRawPath());
        string3 = ((URI)object).getRawQuery();
        if (bl) {
            object = string2;
            return URIUtils.createURI(null, null, -1, (String)object2, string3, (String)object);
        }
        object = ((URI)object).getRawFragment();
        return URIUtils.createURI(null, null, -1, (String)object2, string3, (String)object);
    }
}

