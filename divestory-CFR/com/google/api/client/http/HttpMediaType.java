/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpMediaType {
    private static final Pattern FULL_MEDIA_TYPE_REGEX;
    private static final Pattern PARAMETER_REGEX;
    private static final Pattern TOKEN_REGEX;
    private static final Pattern TYPE_REGEX;
    private String cachedBuildResult;
    private final SortedMap<String, String> parameters = new TreeMap<String, String>();
    private String subType = "octet-stream";
    private String type = "application";

    static {
        TYPE_REGEX = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
        TOKEN_REGEX = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\s*(");
        stringBuilder.append("[^\\s/=;\"]+");
        stringBuilder.append(")/(");
        stringBuilder.append("[^\\s/=;\"]+");
        stringBuilder.append(")\\s*(");
        stringBuilder.append(";.*");
        stringBuilder.append(")?");
        FULL_MEDIA_TYPE_REGEX = Pattern.compile(stringBuilder.toString(), 32);
        stringBuilder = new StringBuilder();
        stringBuilder.append("\\s*;\\s*(");
        stringBuilder.append("[^\\s/=;\"]+");
        stringBuilder.append(")=(");
        stringBuilder.append("\"([^\"]*)\"|[^\\s;\"]*");
        stringBuilder.append(")");
        PARAMETER_REGEX = Pattern.compile(stringBuilder.toString());
    }

    public HttpMediaType(String string2) {
        this.fromString(string2);
    }

    public HttpMediaType(String string2, String string3) {
        this.setType(string2);
        this.setSubType(string3);
    }

    public static boolean equalsIgnoreParameters(String string2, String string3) {
        if (string2 == null) {
            if (string3 == null) return true;
        }
        if (string2 == null) return false;
        if (string3 == null) return false;
        if (!new HttpMediaType(string2).equalsIgnoreParameters(new HttpMediaType(string3))) return false;
        return true;
    }

    private HttpMediaType fromString(String object) {
        object = FULL_MEDIA_TYPE_REGEX.matcher((CharSequence)object);
        Preconditions.checkArgument(((Matcher)object).matches(), "Type must be in the 'maintype/subtype; parameter=value' format");
        this.setType(((Matcher)object).group(1));
        this.setSubType(((Matcher)object).group(2));
        object = ((Matcher)object).group(3);
        if (object == null) return this;
        Matcher matcher = PARAMETER_REGEX.matcher((CharSequence)object);
        while (matcher.find()) {
            String string2 = matcher.group(1);
            String string3 = matcher.group(3);
            object = string3;
            if (string3 == null) {
                object = matcher.group(2);
            }
            this.setParameter(string2, (String)object);
        }
        return this;
    }

    static boolean matchesToken(String string2) {
        return TOKEN_REGEX.matcher(string2).matches();
    }

    private static String quoteString(String string2) {
        string2 = string2.replace("\\", "\\\\").replace("\"", "\\\"");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }

    /*
     * WARNING - void declaration
     */
    public String build() {
        String string2;
        String object2 = this.cachedBuildResult;
        if (object2 != null) {
            return object2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.type);
        stringBuilder.append('/');
        stringBuilder.append(this.subType);
        SortedMap<String, String> sortedMap = this.parameters;
        if (sortedMap != null) {
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                void var1_7;
                String string3 = entry.getValue();
                stringBuilder.append("; ");
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                String string4 = string3;
                if (!HttpMediaType.matchesToken(string3)) {
                    String string5 = HttpMediaType.quoteString(string3);
                }
                stringBuilder.append((String)var1_7);
            }
        }
        this.cachedBuildResult = string2 = stringBuilder.toString();
        return string2;
    }

    public void clearParameters() {
        this.cachedBuildResult = null;
        this.parameters.clear();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof HttpMediaType;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (HttpMediaType)object;
        bl = bl2;
        if (!this.equalsIgnoreParameters((HttpMediaType)object)) return bl;
        bl = bl2;
        if (!this.parameters.equals(((HttpMediaType)object).parameters)) return bl;
        return true;
    }

    public boolean equalsIgnoreParameters(HttpMediaType httpMediaType) {
        if (httpMediaType == null) return false;
        if (!this.getType().equalsIgnoreCase(httpMediaType.getType())) return false;
        if (!this.getSubType().equalsIgnoreCase(httpMediaType.getSubType())) return false;
        return true;
    }

    public Charset getCharsetParameter() {
        String string2 = this.getParameter("charset");
        if (string2 != null) return Charset.forName(string2);
        return null;
    }

    public String getParameter(String string2) {
        return (String)this.parameters.get(string2.toLowerCase(Locale.US));
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public String getSubType() {
        return this.subType;
    }

    public String getType() {
        return this.type;
    }

    public int hashCode() {
        return this.build().hashCode();
    }

    public HttpMediaType removeParameter(String string2) {
        this.cachedBuildResult = null;
        this.parameters.remove(string2.toLowerCase(Locale.US));
        return this;
    }

    public HttpMediaType setCharsetParameter(Charset object) {
        object = object == null ? null : ((Charset)object).name();
        this.setParameter("charset", (String)object);
        return this;
    }

    public HttpMediaType setParameter(String string2, String string3) {
        if (string3 == null) {
            this.removeParameter(string2);
            return this;
        }
        Preconditions.checkArgument(TOKEN_REGEX.matcher(string2).matches(), "Name contains reserved characters");
        this.cachedBuildResult = null;
        this.parameters.put(string2.toLowerCase(Locale.US), string3);
        return this;
    }

    public HttpMediaType setSubType(String string2) {
        Preconditions.checkArgument(TYPE_REGEX.matcher(string2).matches(), "Subtype contains reserved characters");
        this.subType = string2;
        this.cachedBuildResult = null;
        return this;
    }

    public HttpMediaType setType(String string2) {
        Preconditions.checkArgument(TYPE_REGEX.matcher(string2).matches(), "Type contains reserved characters");
        this.type = string2;
        this.cachedBuildResult = null;
        return this;
    }

    public String toString() {
        return this.build();
    }
}

