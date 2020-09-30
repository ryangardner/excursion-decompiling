/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.escape.CharEscapers;
import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.PercentEscaper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GenericUrl
extends GenericData {
    private static final Escaper URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:");
    private String fragment;
    private String host;
    private List<String> pathParts;
    private int port = -1;
    private String scheme;
    private String userInfo;
    private boolean verbatim;

    public GenericUrl() {
    }

    public GenericUrl(String string2) {
        this(string2, false);
    }

    private GenericUrl(String string2, String string3, int n, String string4, String string5, String string6, String string7, boolean bl) {
        this.scheme = string2.toLowerCase(Locale.US);
        this.host = string3;
        this.port = n;
        this.pathParts = GenericUrl.toPathParts(string4, bl);
        this.verbatim = bl;
        if (bl) {
            this.fragment = string5;
            if (string6 != null) {
                UrlEncodedParser.parse(string6, (Object)this, false);
            }
            this.userInfo = string7;
            return;
        }
        string3 = null;
        string2 = string5 != null ? CharEscapers.decodeUri(string5) : null;
        this.fragment = string2;
        if (string6 != null) {
            UrlEncodedParser.parse(string6, (Object)this);
        }
        string2 = string3;
        if (string7 != null) {
            string2 = CharEscapers.decodeUri(string7);
        }
        this.userInfo = string2;
    }

    public GenericUrl(String string2, boolean bl) {
        this(GenericUrl.parseURL(string2), bl);
    }

    public GenericUrl(URI uRI) {
        this(uRI, false);
    }

    public GenericUrl(URI uRI, boolean bl) {
        this(uRI.getScheme(), uRI.getHost(), uRI.getPort(), uRI.getRawPath(), uRI.getRawFragment(), uRI.getRawQuery(), uRI.getRawUserInfo(), bl);
    }

    public GenericUrl(URL uRL) {
        this(uRL, false);
    }

    public GenericUrl(URL uRL, boolean bl) {
        this(uRL.getProtocol(), uRL.getHost(), uRL.getPort(), uRL.getPath(), uRL.getRef(), uRL.getQuery(), uRL.getUserInfo(), bl);
    }

    static void addQueryParams(Set<Map.Entry<String, Object>> entry, StringBuilder stringBuilder, boolean bl) {
        Iterator<Map.Entry<String, Object>> iterator2 = entry.iterator();
        boolean bl2 = true;
        block0 : while (iterator2.hasNext()) {
            entry = iterator2.next();
            Object object = entry.getValue();
            if (object == null) continue;
            entry = bl ? (String)entry.getKey() : CharEscapers.escapeUriQuery((String)entry.getKey());
            if (object instanceof Collection) {
                object = ((Collection)object).iterator();
                boolean bl3 = bl2;
                do {
                    bl2 = bl3;
                    if (!object.hasNext()) continue block0;
                    bl3 = GenericUrl.appendParam(bl3, stringBuilder, (String)((Object)entry), object.next(), bl);
                } while (true);
            }
            bl2 = GenericUrl.appendParam(bl2, stringBuilder, (String)((Object)entry), object, bl);
        }
    }

    private static boolean appendParam(boolean bl, StringBuilder stringBuilder, String string2, Object object, boolean bl2) {
        if (bl) {
            bl = false;
            stringBuilder.append('?');
        } else {
            stringBuilder.append('&');
        }
        stringBuilder.append(string2);
        string2 = bl2 ? object.toString() : CharEscapers.escapeUriQuery(object.toString());
        if (string2.length() == 0) return bl;
        stringBuilder.append('=');
        stringBuilder.append(string2);
        return bl;
    }

    private void appendRawPathFromParts(StringBuilder stringBuilder) {
        int n = this.pathParts.size();
        int n2 = 0;
        while (n2 < n) {
            String string2 = this.pathParts.get(n2);
            if (n2 != 0) {
                stringBuilder.append('/');
            }
            if (string2.length() != 0) {
                if (!this.verbatim) {
                    string2 = CharEscapers.escapeUriPath(string2);
                }
                stringBuilder.append(string2);
            }
            ++n2;
        }
    }

    private static URL parseURL(String object) {
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            throw new IllegalArgumentException(malformedURLException);
        }
    }

    public static List<String> toPathParts(String string2) {
        return GenericUrl.toPathParts(string2, false);
    }

    public static List<String> toPathParts(String string2, boolean bl) {
        if (string2 == null) return null;
        if (string2.length() == 0) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        boolean bl2 = true;
        int n = 0;
        while (bl2) {
            int n2 = string2.indexOf(47, n);
            bl2 = n2 != -1;
            String string3 = bl2 ? string2.substring(n, n2) : string2.substring(n);
            if (!bl) {
                string3 = CharEscapers.decodeUriPath(string3);
            }
            arrayList.add(string3);
            n = n2 + 1;
        }
        return arrayList;
    }

    private static URI toURI(String object) {
        try {
            return new URI((String)object);
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException(uRISyntaxException);
        }
    }

    public void appendRawPath(String object) {
        if (object == null) return;
        if (((String)object).length() == 0) return;
        object = GenericUrl.toPathParts((String)object, this.verbatim);
        List<String> list = this.pathParts;
        if (list != null && !list.isEmpty()) {
            int n = this.pathParts.size();
            list = this.pathParts;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.pathParts.get(--n));
            stringBuilder.append((String)object.get(0));
            list.set(n, stringBuilder.toString());
            this.pathParts.addAll(object.subList(1, object.size()));
            return;
        }
        this.pathParts = object;
    }

    public final String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.buildAuthority());
        stringBuilder.append(this.buildRelativeUrl());
        return stringBuilder.toString();
    }

    public final String buildAuthority() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Preconditions.checkNotNull(this.scheme));
        stringBuilder.append("://");
        String string2 = this.userInfo;
        if (string2 != null) {
            if (!this.verbatim) {
                string2 = CharEscapers.escapeUriUserInfo(string2);
            }
            stringBuilder.append(string2);
            stringBuilder.append('@');
        }
        stringBuilder.append(Preconditions.checkNotNull(this.host));
        int n = this.port;
        if (n == -1) return stringBuilder.toString();
        stringBuilder.append(':');
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    public final String buildRelativeUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.pathParts != null) {
            this.appendRawPathFromParts(stringBuilder);
        }
        GenericUrl.addQueryParams(this.entrySet(), stringBuilder, this.verbatim);
        String string2 = this.fragment;
        if (string2 == null) return stringBuilder.toString();
        stringBuilder.append('#');
        if (!this.verbatim) {
            string2 = URI_FRAGMENT_ESCAPER.escape(string2);
        }
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    @Override
    public GenericUrl clone() {
        GenericUrl genericUrl = (GenericUrl)super.clone();
        if (this.pathParts == null) return genericUrl;
        genericUrl.pathParts = new ArrayList<String>(this.pathParts);
        return genericUrl;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!super.equals(object)) return false;
        if (!(object instanceof GenericUrl)) {
            return false;
        }
        object = (GenericUrl)object;
        return this.build().equals(((GenericUrl)object).build());
    }

    public Collection<Object> getAll(String object) {
        if ((object = this.get(object)) == null) {
            return Collections.emptySet();
        }
        if (!(object instanceof Collection)) return Collections.singleton(object);
        return Collections.unmodifiableCollection((Collection)object);
    }

    public Object getFirst(String iterator2) {
        Object object = this.get(iterator2);
        iterator2 = object;
        if (!(object instanceof Collection)) return iterator2;
        iterator2 = ((Collection)object).iterator();
        if (!iterator2.hasNext()) return null;
        return iterator2.next();
    }

    public String getFragment() {
        return this.fragment;
    }

    public String getHost() {
        return this.host;
    }

    public List<String> getPathParts() {
        return this.pathParts;
    }

    public int getPort() {
        return this.port;
    }

    public String getRawPath() {
        if (this.pathParts == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.appendRawPathFromParts(stringBuilder);
        return stringBuilder.toString();
    }

    public final String getScheme() {
        return this.scheme;
    }

    public final String getUserInfo() {
        return this.userInfo;
    }

    @Override
    public int hashCode() {
        return this.build().hashCode();
    }

    @Override
    public GenericUrl set(String string2, Object object) {
        return (GenericUrl)super.set(string2, object);
    }

    public final void setFragment(String string2) {
        this.fragment = string2;
    }

    public final void setHost(String string2) {
        this.host = Preconditions.checkNotNull(string2);
    }

    public void setPathParts(List<String> list) {
        this.pathParts = list;
    }

    public final void setPort(int n) {
        boolean bl = n >= -1;
        Preconditions.checkArgument(bl, "expected port >= -1");
        this.port = n;
    }

    public void setRawPath(String string2) {
        this.pathParts = GenericUrl.toPathParts(string2, this.verbatim);
    }

    public final void setScheme(String string2) {
        this.scheme = Preconditions.checkNotNull(string2);
    }

    public final void setUserInfo(String string2) {
        this.userInfo = string2;
    }

    @Override
    public String toString() {
        return this.build();
    }

    public final URI toURI() {
        return GenericUrl.toURI(this.build());
    }

    public final URL toURL() {
        return GenericUrl.parseURL(this.build());
    }

    public final URL toURL(String object) {
        try {
            return new URL(this.toURL(), (String)object);
        }
        catch (MalformedURLException malformedURLException) {
            throw new IllegalArgumentException(malformedURLException);
        }
    }
}

