/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 J2\u00020\u0001:\u0002IJBa\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0007\u00a2\u0006\u0002\b!J\r\u0010\u0011\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\"J\r\u0010\u0012\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b#J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007\u00a2\u0006\u0002\b$J\u000f\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0007\u00a2\u0006\u0002\b%J\r\u0010\u0016\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b&J\u0013\u0010'\u001a\u00020\u00182\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u000f\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0007\u00a2\u0006\u0002\b)J\b\u0010*\u001a\u00020\bH\u0016J\r\u0010\u0006\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b+J\u0006\u0010,\u001a\u00020-J\u0010\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0005\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b/J\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007\u00a2\u0006\u0002\b0J\r\u0010\u001a\u001a\u00020\bH\u0007\u00a2\u0006\u0002\b1J\r\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\b2J\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0007\u00a2\u0006\u0002\b3J\u0010\u00104\u001a\u0004\u0018\u00010\u00032\u0006\u00105\u001a\u00020\u0003J\u000e\u00106\u001a\u00020\u00032\u0006\u00107\u001a\u00020\bJ\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001eH\u0007\u00a2\u0006\u0002\b8J\u0010\u00109\u001a\u0004\u0018\u00010\u00032\u0006\u00107\u001a\u00020\bJ\u0016\u0010:\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n2\u0006\u00105\u001a\u00020\u0003J\r\u0010 \u001a\u00020\bH\u0007\u00a2\u0006\u0002\b;J\u0006\u0010<\u001a\u00020\u0003J\u0010\u0010=\u001a\u0004\u0018\u00010\u00002\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b>J\b\u0010?\u001a\u00020\u0003H\u0016J\r\u0010@\u001a\u00020AH\u0007\u00a2\u0006\u0002\bBJ\r\u0010C\u001a\u00020DH\u0007\u00a2\u0006\u0002\b\rJ\b\u0010E\u001a\u0004\u0018\u00010\u0003J\r\u0010B\u001a\u00020AH\u0007\u00a2\u0006\u0002\bFJ\r\u0010\r\u001a\u00020DH\u0007\u00a2\u0006\u0002\bGJ\r\u0010\u0004\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\bHR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0012\u001a\u00020\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\n8G\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0016\u001a\u00020\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u0015\u0010\f\u001a\u0004\u0018\u00010\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0010R\u0013\u0010\u0006\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u0013\u0010\u0005\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0014R\u0011\u0010\u001a\u001a\u00020\b8G\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0007\u001a\u00020\b8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001bR\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001e8G\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0011\u0010 \u001a\u00020\b8G\u00a2\u0006\u0006\u001a\u0004\b \u0010\u001bR\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0010R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010\u00a8\u0006K"}, d2={"Lokhttp3/HttpUrl;", "", "scheme", "", "username", "password", "host", "port", "", "pathSegments", "", "queryNamesAndValues", "fragment", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "encodedFragment", "()Ljava/lang/String;", "encodedPassword", "encodedPath", "encodedPathSegments", "()Ljava/util/List;", "encodedQuery", "encodedUsername", "isHttps", "", "()Z", "pathSize", "()I", "query", "queryParameterNames", "", "()Ljava/util/Set;", "querySize", "-deprecated_encodedFragment", "-deprecated_encodedPassword", "-deprecated_encodedPath", "-deprecated_encodedPathSegments", "-deprecated_encodedQuery", "-deprecated_encodedUsername", "equals", "other", "-deprecated_fragment", "hashCode", "-deprecated_host", "newBuilder", "Lokhttp3/HttpUrl$Builder;", "link", "-deprecated_password", "-deprecated_pathSegments", "-deprecated_pathSize", "-deprecated_port", "-deprecated_query", "queryParameter", "name", "queryParameterName", "index", "-deprecated_queryParameterNames", "queryParameterValue", "queryParameterValues", "-deprecated_querySize", "redact", "resolve", "-deprecated_scheme", "toString", "toUri", "Ljava/net/URI;", "uri", "toUrl", "Ljava/net/URL;", "topPrivateDomain", "-deprecated_uri", "-deprecated_url", "-deprecated_username", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class HttpUrl {
    public static final Companion Companion = new Companion(null);
    public static final String FORM_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#&!$(),~";
    public static final String FRAGMENT_ENCODE_SET = "";
    public static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
    public static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
    public static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";
    public static final String QUERY_COMPONENT_ENCODE_SET = " !\"#$&'(),/:;<=>?@[]\\^`{|}~";
    public static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";
    public static final String QUERY_COMPONENT_REENCODE_SET = " \"'<>#&=";
    public static final String QUERY_ENCODE_SET = " \"'<>#";
    public static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
    private final String fragment;
    private final String host;
    private final boolean isHttps;
    private final String password;
    private final List<String> pathSegments;
    private final int port;
    private final List<String> queryNamesAndValues;
    private final String scheme;
    private final String url;
    private final String username;

    public HttpUrl(String string2, String string3, String string4, String string5, int n, List<String> list, List<String> list2, String string6, String string7) {
        Intrinsics.checkParameterIsNotNull(string2, "scheme");
        Intrinsics.checkParameterIsNotNull(string3, "username");
        Intrinsics.checkParameterIsNotNull(string4, "password");
        Intrinsics.checkParameterIsNotNull(string5, "host");
        Intrinsics.checkParameterIsNotNull(list, "pathSegments");
        Intrinsics.checkParameterIsNotNull(string7, "url");
        this.scheme = string2;
        this.username = string3;
        this.password = string4;
        this.host = string5;
        this.port = n;
        this.pathSegments = list;
        this.queryNamesAndValues = list2;
        this.fragment = string6;
        this.url = string7;
        this.isHttps = Intrinsics.areEqual(string2, "https");
    }

    @JvmStatic
    public static final int defaultPort(String string2) {
        return Companion.defaultPort(string2);
    }

    @JvmStatic
    public static final HttpUrl get(String string2) {
        return Companion.get(string2);
    }

    @JvmStatic
    public static final HttpUrl get(URI uRI) {
        return Companion.get(uRI);
    }

    @JvmStatic
    public static final HttpUrl get(URL uRL) {
        return Companion.get(uRL);
    }

    @JvmStatic
    public static final HttpUrl parse(String string2) {
        return Companion.parse(string2);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedFragment", imports={}))
    public final String -deprecated_encodedFragment() {
        return this.encodedFragment();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPassword", imports={}))
    public final String -deprecated_encodedPassword() {
        return this.encodedPassword();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPath", imports={}))
    public final String -deprecated_encodedPath() {
        return this.encodedPath();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPathSegments", imports={}))
    public final List<String> -deprecated_encodedPathSegments() {
        return this.encodedPathSegments();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedQuery", imports={}))
    public final String -deprecated_encodedQuery() {
        return this.encodedQuery();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedUsername", imports={}))
    public final String -deprecated_encodedUsername() {
        return this.encodedUsername();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="fragment", imports={}))
    public final String -deprecated_fragment() {
        return this.fragment;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="host", imports={}))
    public final String -deprecated_host() {
        return this.host;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="password", imports={}))
    public final String -deprecated_password() {
        return this.password;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pathSegments", imports={}))
    public final List<String> -deprecated_pathSegments() {
        return this.pathSegments;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pathSize", imports={}))
    public final int -deprecated_pathSize() {
        return this.pathSize();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="port", imports={}))
    public final int -deprecated_port() {
        return this.port;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="query", imports={}))
    public final String -deprecated_query() {
        return this.query();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="queryParameterNames", imports={}))
    public final Set<String> -deprecated_queryParameterNames() {
        return this.queryParameterNames();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="querySize", imports={}))
    public final int -deprecated_querySize() {
        return this.querySize();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="scheme", imports={}))
    public final String -deprecated_scheme() {
        return this.scheme;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to toUri()", replaceWith=@ReplaceWith(expression="toUri()", imports={}))
    public final URI -deprecated_uri() {
        return this.uri();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to toUrl()", replaceWith=@ReplaceWith(expression="toUrl()", imports={}))
    public final URL -deprecated_url() {
        return this.url();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="username", imports={}))
    public final String -deprecated_username() {
        return this.username;
    }

    public final String encodedFragment() {
        if (this.fragment == null) {
            return null;
        }
        int n = StringsKt.indexOf$default((CharSequence)this.url, '#', 0, false, 6, null);
        String string2 = this.url;
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n + 1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        return string2;
    }

    public final String encodedPassword() {
        if (((CharSequence)this.password).length() == 0) {
            return FRAGMENT_ENCODE_SET;
        }
        int n = 0;
        if (n != 0) {
            return FRAGMENT_ENCODE_SET;
        }
        n = StringsKt.indexOf$default((CharSequence)this.url, ':', this.scheme.length() + 3, false, 4, null);
        int n2 = StringsKt.indexOf$default((CharSequence)this.url, '@', 0, false, 6, null);
        String string2 = this.url;
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n + 1, n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public final String encodedPath() {
        int n = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, null);
        String string2 = this.url;
        int n2 = Util.delimiterOffset(string2, "?#", n, string2.length());
        string2 = this.url;
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public final List<String> encodedPathSegments() {
        int n = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, null);
        Object object = this.url;
        int n2 = Util.delimiterOffset((String)object, "?#", n, ((String)object).length());
        object = new ArrayList();
        while (n < n2) {
            int n3 = n + 1;
            n = Util.delimiterOffset(this.url, '/', n3, n2);
            String string2 = this.url;
            if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            string2 = string2.substring(n3, n);
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            object.add(string2);
        }
        return object;
    }

    public final String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int n = StringsKt.indexOf$default((CharSequence)this.url, '?', 0, false, 6, null) + 1;
        String string2 = this.url;
        int n2 = Util.delimiterOffset(string2, '#', n, string2.length());
        string2 = this.url;
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public final String encodedUsername() {
        if (((CharSequence)this.username).length() == 0) {
            return FRAGMENT_ENCODE_SET;
        }
        int n = 0;
        if (n != 0) {
            return FRAGMENT_ENCODE_SET;
        }
        n = this.scheme.length() + 3;
        String string2 = this.url;
        int n2 = Util.delimiterOffset(string2, ":@", n, string2.length());
        string2 = this.url;
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof HttpUrl)) return false;
        if (!Intrinsics.areEqual(((HttpUrl)object).url, this.url)) return false;
        return true;
    }

    public final String fragment() {
        return this.fragment;
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    public final String host() {
        return this.host;
    }

    public final boolean isHttps() {
        return this.isHttps;
    }

    public final Builder newBuilder() {
        Builder builder = new Builder();
        builder.setScheme$okhttp(this.scheme);
        builder.setEncodedUsername$okhttp(this.encodedUsername());
        builder.setEncodedPassword$okhttp(this.encodedPassword());
        builder.setHost$okhttp(this.host);
        int n = this.port != Companion.defaultPort(this.scheme) ? this.port : -1;
        builder.setPort$okhttp(n);
        builder.getEncodedPathSegments$okhttp().clear();
        builder.getEncodedPathSegments$okhttp().addAll((Collection<String>)this.encodedPathSegments());
        builder.encodedQuery(this.encodedQuery());
        builder.setEncodedFragment$okhttp(this.encodedFragment());
        return builder;
    }

    public final Builder newBuilder(String object) {
        Intrinsics.checkParameterIsNotNull(object, "link");
        try {
            Builder builder = new Builder();
            return builder.parse$okhttp(this, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public final String password() {
        return this.password;
    }

    public final List<String> pathSegments() {
        return this.pathSegments;
    }

    public final int pathSize() {
        return this.pathSegments.size();
    }

    public final int port() {
        return this.port;
    }

    public final String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Companion.toQueryString$okhttp(this.queryNamesAndValues, stringBuilder);
        return stringBuilder.toString();
    }

    public final String queryParameter(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Iterable<String> iterable = this.queryNamesAndValues;
        if (iterable == null) {
            return null;
        }
        iterable = RangesKt.step(RangesKt.until(0, iterable.size()), 2);
        int n = ((IntProgression)iterable).getFirst();
        int n2 = ((IntProgression)iterable).getLast();
        int n3 = ((IntProgression)iterable).getStep();
        if (n3 >= 0) {
            if (n > n2) return null;
        } else if (n < n2) return null;
        while (!Intrinsics.areEqual(string2, this.queryNamesAndValues.get(n))) {
            if (n == n2) return null;
            n += n3;
        }
        return this.queryNamesAndValues.get(n + 1);
    }

    public final String queryParameterName(int n) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) throw (Throwable)new IndexOutOfBoundsException();
        if ((list = list.get(n * 2)) != null) return (String)((Object)list);
        Intrinsics.throwNpe();
        return (String)((Object)list);
    }

    public final Set<String> queryParameterNames() {
        if (this.queryNamesAndValues == null) {
            return SetsKt.emptySet();
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
        Object object = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int n = ((IntProgression)object).getFirst();
        int n2 = ((IntProgression)object).getLast();
        int n3 = ((IntProgression)object).getStep();
        if (n3 >= 0 ? n <= n2 : n >= n2) {
            do {
                if ((object = this.queryNamesAndValues.get(n)) == null) {
                    Intrinsics.throwNpe();
                }
                linkedHashSet.add((String)object);
                if (n == n2) break;
                n += n3;
            } while (true);
        }
        linkedHashSet = Collections.unmodifiableSet((Set)linkedHashSet);
        Intrinsics.checkExpressionValueIsNotNull(linkedHashSet, "Collections.unmodifiableSet(result)");
        return linkedHashSet;
    }

    public final String queryParameterValue(int n) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) throw (Throwable)new IndexOutOfBoundsException();
        return list.get(n * 2 + 1);
    }

    public final List<String> queryParameterValues(String object) {
        Intrinsics.checkParameterIsNotNull(object, "name");
        if (this.queryNamesAndValues == null) {
            return CollectionsKt.emptyList();
        }
        List list = new ArrayList();
        IntProgression intProgression = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int n = intProgression.getFirst();
        int n2 = intProgression.getLast();
        int n3 = intProgression.getStep();
        if (n3 >= 0 ? n <= n2 : n >= n2) {
            do {
                if (Intrinsics.areEqual(object, this.queryNamesAndValues.get(n))) {
                    list.add(this.queryNamesAndValues.get(n + 1));
                }
                if (n == n2) break;
                n += n3;
            } while (true);
        }
        object = Collections.unmodifiableList(list);
        Intrinsics.checkExpressionValueIsNotNull(object, "Collections.unmodifiableList(result)");
        return object;
    }

    public final int querySize() {
        List<String> list = this.queryNamesAndValues;
        if (list == null) return 0;
        return list.size() / 2;
    }

    public final String redact() {
        Builder builder = this.newBuilder("/...");
        if (builder != null) return builder.username(FRAGMENT_ENCODE_SET).password(FRAGMENT_ENCODE_SET).build().toString();
        Intrinsics.throwNpe();
        return builder.username(FRAGMENT_ENCODE_SET).password(FRAGMENT_ENCODE_SET).build().toString();
    }

    public final HttpUrl resolve(String object) {
        Intrinsics.checkParameterIsNotNull(object, "link");
        object = this.newBuilder((String)object);
        if (object == null) return null;
        return ((Builder)object).build();
    }

    public final String scheme() {
        return this.scheme;
    }

    public String toString() {
        return this.url;
    }

    public final String topPrivateDomain() {
        if (!Util.canParseAsIpAddress(this.host)) return PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(this.host);
        return null;
    }

    public final URI uri() {
        Object object = this.newBuilder().reencodeForUri$okhttp().toString();
        try {
            return new URI((String)object);
        }
        catch (URISyntaxException uRISyntaxException) {
            try {
                Object object2 = (CharSequence)object;
                object = new Regex("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]");
                object2 = URI.create(((Regex)object).replace((CharSequence)object2, FRAGMENT_ENCODE_SET));
                Intrinsics.checkExpressionValueIsNotNull(object2, "URI.create(stripped)");
                return object2;
            }
            catch (Exception exception) {
                throw (Throwable)new RuntimeException(uRISyntaxException);
            }
        }
    }

    public final URL url() {
        try {
            return new URL(this.url);
        }
        catch (MalformedURLException malformedURLException) {
            throw (Throwable)new RuntimeException(malformedURLException);
        }
    }

    public final String username() {
        return this.username;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0017\u0018\u0000 V2\u00020\u0001:\u0001VB\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0004J\u0018\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0004J\u0018\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\u0006\u00102\u001a\u000203J\b\u00104\u001a\u00020\u001bH\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0004J\u000e\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u0004J\u0010\u00106\u001a\u00020\u00002\b\u00106\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u00107\u001a\u00020\u00002\b\u00107\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0004J\u0010\u00108\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u0010\u0010:\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u001f\u0010;\u001a\u00020\u00002\b\u0010<\u001a\u0004\u0018\u0001032\u0006\u00109\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b=J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u0004J\b\u0010?\u001a\u00020@H\u0002J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ0\u0010A\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010B\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010D\u001a\u00020.2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010E\u001a\u00020\u00002\b\u0010E\u001a\u0004\u0018\u00010\u0004J\r\u0010F\u001a\u00020\u0000H\u0000\u00a2\u0006\u0002\bGJ\u0010\u0010H\u001a\u00020@2\u0006\u0010I\u001a\u00020\u0004H\u0002J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u0004J\u000e\u0010K\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0004J\u000e\u0010L\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001bJ \u0010N\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010O\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001bH\u0002J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u0004J\u0016\u0010P\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u0004J\u0018\u0010Q\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u0016\u0010R\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0004J\u0018\u0010S\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\b\u0010T\u001a\u00020\u0004H\u0016J\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\rX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\rX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u001bX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\b\u00a8\u0006W"}, d2={"Lokhttp3/HttpUrl$Builder;", "", "()V", "encodedFragment", "", "getEncodedFragment$okhttp", "()Ljava/lang/String;", "setEncodedFragment$okhttp", "(Ljava/lang/String;)V", "encodedPassword", "getEncodedPassword$okhttp", "setEncodedPassword$okhttp", "encodedPathSegments", "", "getEncodedPathSegments$okhttp", "()Ljava/util/List;", "encodedQueryNamesAndValues", "getEncodedQueryNamesAndValues$okhttp", "setEncodedQueryNamesAndValues$okhttp", "(Ljava/util/List;)V", "encodedUsername", "getEncodedUsername$okhttp", "setEncodedUsername$okhttp", "host", "getHost$okhttp", "setHost$okhttp", "port", "", "getPort$okhttp", "()I", "setPort$okhttp", "(I)V", "scheme", "getScheme$okhttp", "setScheme$okhttp", "addEncodedPathSegment", "encodedPathSegment", "addEncodedPathSegments", "addEncodedQueryParameter", "encodedName", "encodedValue", "addPathSegment", "pathSegment", "addPathSegments", "pathSegments", "alreadyEncoded", "", "addQueryParameter", "name", "value", "build", "Lokhttp3/HttpUrl;", "effectivePort", "encodedPath", "encodedQuery", "fragment", "isDot", "input", "isDotDot", "parse", "base", "parse$okhttp", "password", "pop", "", "push", "pos", "limit", "addTrailingSlash", "query", "reencodeForUri", "reencodeForUri$okhttp", "removeAllCanonicalQueryParameters", "canonicalName", "removeAllEncodedQueryParameters", "removeAllQueryParameters", "removePathSegment", "index", "resolvePath", "startPos", "setEncodedPathSegment", "setEncodedQueryParameter", "setPathSegment", "setQueryParameter", "toString", "username", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        public static final Companion Companion = new Companion(null);
        public static final String INVALID_HOST = "Invalid URL host";
        private String encodedFragment;
        private String encodedPassword = "";
        private final List<String> encodedPathSegments;
        private List<String> encodedQueryNamesAndValues;
        private String encodedUsername = "";
        private String host;
        private int port = -1;
        private String scheme;

        public Builder() {
            List list;
            this.encodedPathSegments = list = (List)new ArrayList();
            list.add(HttpUrl.FRAGMENT_ENCODE_SET);
        }

        private final Builder addPathSegments(String string2, boolean bl) {
            int n;
            Builder builder = this;
            int n2 = 0;
            do {
                boolean bl2 = (n = Util.delimiterOffset(string2, "/\\", n2, string2.length())) < string2.length();
                builder.push(string2, n2, n, bl2, bl);
                n2 = ++n;
            } while (n <= string2.length());
            return builder;
        }

        private final int effectivePort() {
            int n = this.port;
            if (n != -1) {
                return n;
            }
            okhttp3.HttpUrl$Companion companion = Companion;
            String string2 = this.scheme;
            if (string2 != null) return companion.defaultPort(string2);
            Intrinsics.throwNpe();
            return companion.defaultPort(string2);
        }

        private final boolean isDot(String string2) {
            boolean bl;
            boolean bl2 = Intrinsics.areEqual(string2, ".");
            boolean bl3 = bl = true;
            if (bl2) return bl3;
            if (!StringsKt.equals(string2, "%2e", true)) return false;
            return bl;
        }

        private final boolean isDotDot(String string2) {
            boolean bl;
            boolean bl2 = Intrinsics.areEqual(string2, "..");
            boolean bl3 = bl = true;
            if (bl2) return bl3;
            bl3 = bl;
            if (StringsKt.equals(string2, "%2e.", true)) return bl3;
            bl3 = bl;
            if (StringsKt.equals(string2, ".%2e", true)) return bl3;
            if (!StringsKt.equals(string2, "%2e%2e", true)) return false;
            return bl;
        }

        private final void pop() {
            List<String> list = this.encodedPathSegments;
            boolean bl = ((CharSequence)list.remove(list.size() - 1)).length() == 0;
            if (bl && ((Collection)this.encodedPathSegments).isEmpty() ^ true) {
                list = this.encodedPathSegments;
                list.set(list.size() - 1, HttpUrl.FRAGMENT_ENCODE_SET);
                return;
            }
            this.encodedPathSegments.add(HttpUrl.FRAGMENT_ENCODE_SET);
        }

        private final void push(String string2, int n, int n2, boolean bl, boolean bl2) {
            if (this.isDot(string2 = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n, n2, HttpUrl.PATH_SEGMENT_ENCODE_SET, bl2, false, false, false, null, 240, null))) {
                return;
            }
            if (this.isDotDot(string2)) {
                this.pop();
                return;
            }
            List<String> list = this.encodedPathSegments;
            n = ((CharSequence)list.get(list.size() - 1)).length() == 0 ? 1 : 0;
            if (n != 0) {
                list = this.encodedPathSegments;
                list.set(list.size() - 1, string2);
            } else {
                this.encodedPathSegments.add(string2);
            }
            if (!bl) return;
            this.encodedPathSegments.add(HttpUrl.FRAGMENT_ENCODE_SET);
        }

        private final void removeAllCanonicalQueryParameters(String string2) {
            Iterable<String> iterable = this.encodedQueryNamesAndValues;
            if (iterable == null) {
                Intrinsics.throwNpe();
            }
            iterable = RangesKt.step(RangesKt.downTo(iterable.size() - 2, 0), 2);
            int n = ((IntProgression)iterable).getFirst();
            int n2 = ((IntProgression)iterable).getLast();
            int n3 = ((IntProgression)iterable).getStep();
            if (n3 >= 0) {
                if (n > n2) return;
            } else if (n < n2) return;
            do {
                if ((iterable = this.encodedQueryNamesAndValues) == null) {
                    Intrinsics.throwNpe();
                }
                if (Intrinsics.areEqual(string2, (String)iterable.get(n))) {
                    iterable = this.encodedQueryNamesAndValues;
                    if (iterable == null) {
                        Intrinsics.throwNpe();
                    }
                    iterable.remove(n + 1);
                    iterable = this.encodedQueryNamesAndValues;
                    if (iterable == null) {
                        Intrinsics.throwNpe();
                    }
                    iterable.remove(n);
                    iterable = this.encodedQueryNamesAndValues;
                    if (iterable == null) {
                        Intrinsics.throwNpe();
                    }
                    if (iterable.isEmpty()) {
                        this.encodedQueryNamesAndValues = null;
                        return;
                    }
                }
                if (n == n2) return;
                n += n3;
            } while (true);
        }

        /*
         * Unable to fully structure code
         */
        private final void resolvePath(String var1_1, int var2_2, int var3_3) {
            block2 : {
                if (var2_2 == var3_3) {
                    return;
                }
                var4_4 = var1_1.charAt(var2_2);
                if (var4_4 != 47 && var4_4 != 92) break block2;
                this.encodedPathSegments.clear();
                this.encodedPathSegments.add("");
                ** GOTO lbl20
            }
            var5_5 = this.encodedPathSegments;
            var5_5.set(var5_5.size() - 1, "");
            while (var2_2 < var3_3) {
                var4_4 = Util.delimiterOffset(var1_1, "/\\", var2_2, var3_3);
                var6_6 = var4_4 < var3_3;
                this.push(var1_1, var2_2, var4_4, var6_6, true);
                var2_2 = var4_4;
                if (!var6_6) continue;
                var2_2 = var4_4;
lbl20: // 2 sources:
                ++var2_2;
            }
        }

        public final Builder addEncodedPathSegment(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedPathSegment");
            Builder builder = this;
            builder.push(string2, 0, string2.length(), false, true);
            return builder;
        }

        public final Builder addEncodedPathSegments(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedPathSegments");
            return this.addPathSegments(string2, true);
        }

        public final Builder addEncodedQueryParameter(String string2, String string3) {
            List<String> list;
            Intrinsics.checkParameterIsNotNull(string2, "encodedName");
            Builder builder = this;
            if (builder.encodedQueryNamesAndValues == null) {
                builder.encodedQueryNamesAndValues = new ArrayList();
            }
            if ((list = builder.encodedQueryNamesAndValues) == null) {
                Intrinsics.throwNpe();
            }
            list.add(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null));
            list = builder.encodedQueryNamesAndValues;
            if (list == null) {
                Intrinsics.throwNpe();
            }
            string2 = string3 != null ? okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string3, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null) : null;
            list.add(string2);
            return builder;
        }

        public final Builder addPathSegment(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "pathSegment");
            Builder builder = this;
            builder.push(string2, 0, string2.length(), false, false);
            return builder;
        }

        public final Builder addPathSegments(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "pathSegments");
            return this.addPathSegments(string2, false);
        }

        public final Builder addQueryParameter(String string2, String string3) {
            List<String> list;
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            if (builder.encodedQueryNamesAndValues == null) {
                builder.encodedQueryNamesAndValues = new ArrayList();
            }
            if ((list = builder.encodedQueryNamesAndValues) == null) {
                Intrinsics.throwNpe();
            }
            list.add(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null));
            list = builder.encodedQueryNamesAndValues;
            if (list == null) {
                Intrinsics.throwNpe();
            }
            string2 = string3 != null ? okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string3, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null) : null;
            list.add(string2);
            return builder;
        }

        public final HttpUrl build() {
            Object object;
            String string2 = this.scheme;
            if (string2 == null) throw (Throwable)new IllegalStateException("scheme == null");
            String string3 = okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, this.encodedUsername, 0, 0, false, 7, null);
            String string4 = okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, this.encodedPassword, 0, 0, false, 7, null);
            String string5 = this.host;
            if (string5 == null) throw (Throwable)new IllegalStateException("host == null");
            int n = this.effectivePort();
            Object object2 = this.encodedPathSegments;
            Object object3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(object2, 10));
            Object object4 = object2.iterator();
            while (object4.hasNext()) {
                object2 = (String)object4.next();
                object3.add((String)okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, (String)object2, 0, 0, false, 7, null));
            }
            object4 = (List)object3;
            object3 = this.encodedQueryNamesAndValues;
            object2 = null;
            if (object3 == null) {
                object3 = null;
            } else {
                object3 = (Iterable)object3;
                object = new ArrayList(CollectionsKt.collectionSizeOrDefault(object3, 10));
                Iterator<String> iterator2 = object3.iterator();
                while (iterator2.hasNext()) {
                    object3 = iterator2.next();
                    object3 = object3 != null ? okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, (String)object3, 0, 0, true, 3, null) : null;
                    object.add(object3);
                }
                object3 = (List)object;
            }
            object = this.encodedFragment;
            if (object == null) return new HttpUrl(string2, string3, string4, string5, n, (List<String>)object4, (List<String>)object3, (String)object2, this.toString());
            object2 = okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, (String)object, 0, 0, false, 7, null);
            return new HttpUrl(string2, string3, string4, string5, n, (List<String>)object4, (List<String>)object3, (String)object2, this.toString());
        }

        public final Builder encodedFragment(String string2) {
            Builder builder = this;
            string2 = string2 != null ? okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.FRAGMENT_ENCODE_SET, true, false, false, true, null, 179, null) : null;
            builder.encodedFragment = string2;
            return builder;
        }

        public final Builder encodedPassword(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedPassword");
            Builder builder = this;
            builder.encodedPassword = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
            return builder;
        }

        public final Builder encodedPath(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedPath");
            Object object = this;
            if (StringsKt.startsWith$default(string2, "/", false, 2, null)) {
                ((Builder)object).resolvePath(string2, 0, string2.length());
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected encodedPath: ");
            ((StringBuilder)object).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder encodedQuery(String list) {
            Builder builder = this;
            list = list != null && (list = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, (String)((Object)list), 0, 0, HttpUrl.QUERY_ENCODE_SET, true, false, true, false, null, 211, null)) != null ? Companion.toQueryNamesAndValues$okhttp((String)((Object)list)) : null;
            builder.encodedQueryNamesAndValues = list;
            return builder;
        }

        public final Builder encodedUsername(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedUsername");
            Builder builder = this;
            builder.encodedUsername = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
            return builder;
        }

        public final Builder fragment(String string2) {
            Builder builder = this;
            string2 = string2 != null ? okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.FRAGMENT_ENCODE_SET, false, false, false, true, null, 187, null) : null;
            builder.encodedFragment = string2;
            return builder;
        }

        public final String getEncodedFragment$okhttp() {
            return this.encodedFragment;
        }

        public final String getEncodedPassword$okhttp() {
            return this.encodedPassword;
        }

        public final List<String> getEncodedPathSegments$okhttp() {
            return this.encodedPathSegments;
        }

        public final List<String> getEncodedQueryNamesAndValues$okhttp() {
            return this.encodedQueryNamesAndValues;
        }

        public final String getEncodedUsername$okhttp() {
            return this.encodedUsername;
        }

        public final String getHost$okhttp() {
            return this.host;
        }

        public final int getPort$okhttp() {
            return this.port;
        }

        public final String getScheme$okhttp() {
            return this.scheme;
        }

        public final Builder host(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "host");
            Object object = this;
            String string3 = HostnamesKt.toCanonicalHost(okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, string2, 0, 0, false, 7, null));
            if (string3 != null) {
                ((Builder)object).host = string3;
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected host: ");
            ((StringBuilder)object).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public final Builder parse$okhttp(HttpUrl object, String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "input");
            int n = Util.indexOfFirstNonAsciiWhitespace$default(string2, 0, 0, 3, null);
            int n2 = Util.indexOfLastNonAsciiWhitespace$default(string2, n, 0, 2, null);
            int n3 = Builder.Companion.schemeDelimiterOffset(string2, n, n2);
            Object object2 = "(this as java.lang.Strin\u2026ing(startIndex, endIndex)";
            if (n3 != -1) {
                if (StringsKt.startsWith(string2, "https:", n, true)) {
                    this.scheme = "https";
                    n += 6;
                } else {
                    if (!StringsKt.startsWith(string2, "http:", n, true)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Expected URL scheme 'http' or 'https' but was '");
                        string2 = string2.substring(0, n3);
                        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append("'");
                        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    this.scheme = "http";
                    n += 5;
                }
            } else {
                if (object == null) throw (Throwable)new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
                this.scheme = ((HttpUrl)object).scheme();
            }
            n3 = Builder.Companion.slashCount(string2, n, n2);
            if (n3 < 2 && object != null && !(Intrinsics.areEqual(((HttpUrl)object).scheme(), this.scheme) ^ true)) {
                this.encodedUsername = ((HttpUrl)object).encodedUsername();
                this.encodedPassword = ((HttpUrl)object).encodedPassword();
                this.host = ((HttpUrl)object).host();
                this.port = ((HttpUrl)object).port();
                this.encodedPathSegments.clear();
                this.encodedPathSegments.addAll((Collection<String>)((HttpUrl)object).encodedPathSegments());
                if (n == n2 || string2.charAt(n) == '#') {
                    this.encodedQuery(((HttpUrl)object).encodedQuery());
                }
            } else {
                Object object3;
                int n4;
                int n5;
                int n6 = n + n3;
                n = 0;
                n3 = 0;
                object = object2;
                while ((n4 = (n5 = Util.delimiterOffset(string2, "@/\\?#", n6, n2)) != n2 ? (int)string2.charAt(n5) : -1) != -1 && n4 != 35 && n4 != 47 && n4 != 92 && n4 != 63) {
                    if (n4 != 64) continue;
                    if (n == 0) {
                        n4 = Util.delimiterOffset(string2, ':', n6, n5);
                        object2 = object3 = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n6, n4, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null);
                        if (n3 != 0) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append(this.encodedUsername);
                            ((StringBuilder)object2).append("%40");
                            ((StringBuilder)object2).append((String)object3);
                            object2 = ((StringBuilder)object2).toString();
                        }
                        this.encodedUsername = object2;
                        if (n4 != n5) {
                            this.encodedPassword = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n4 + 1, n5, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null);
                            n = 1;
                        }
                        n3 = 1;
                    } else {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(this.encodedPassword);
                        ((StringBuilder)object2).append("%40");
                        ((StringBuilder)object2).append(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n6, n5, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null));
                        this.encodedPassword = ((StringBuilder)object2).toString();
                    }
                    n6 = n5 + 1;
                }
                n3 = Builder.Companion.portColonOffset(string2, n6, n5);
                n4 = n3 + 1;
                if (n4 < n5) {
                    this.host = HostnamesKt.toCanonicalHost(okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, string2, n6, n3, false, 4, null));
                    this.port = n = Builder.Companion.parsePort(string2, n4, n5);
                    if ((n = n != -1 ? 1 : 0) == 0) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Invalid URL port: \"");
                        string2 = string2.substring(n4, n5);
                        Intrinsics.checkExpressionValueIsNotNull(string2, (String)object);
                        ((StringBuilder)object2).append(string2);
                        ((StringBuilder)object2).append('\"');
                        throw (Throwable)new IllegalArgumentException(((StringBuilder)object2).toString().toString());
                    }
                } else {
                    this.host = HostnamesKt.toCanonicalHost(okhttp3.HttpUrl$Companion.percentDecode$okhttp$default(Companion, string2, n6, n3, false, 4, null));
                    object3 = Companion;
                    object2 = this.scheme;
                    if (object2 == null) {
                        Intrinsics.throwNpe();
                    }
                    this.port = ((okhttp3.HttpUrl$Companion)object3).defaultPort((String)object2);
                }
                if ((n = this.host != null ? 1 : 0) == 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Invalid URL host: \"");
                    string2 = string2.substring(n6, n3);
                    Intrinsics.checkExpressionValueIsNotNull(string2, (String)object);
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append('\"');
                    throw (Throwable)new IllegalArgumentException(((StringBuilder)object2).toString().toString());
                }
                n = n5;
            }
            n3 = Util.delimiterOffset(string2, "?#", n, n2);
            this.resolvePath(string2, n, n3);
            n = n3;
            if (n3 < n2) {
                n = n3;
                if (string2.charAt(n3) == '?') {
                    n = Util.delimiterOffset(string2, '#', n3, n2);
                    this.encodedQueryNamesAndValues = Companion.toQueryNamesAndValues$okhttp(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n3 + 1, n, HttpUrl.QUERY_ENCODE_SET, true, false, true, false, null, 208, null));
                }
            }
            if (n >= n2) return this;
            if (string2.charAt(n) != '#') return this;
            this.encodedFragment = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, n + 1, n2, HttpUrl.FRAGMENT_ENCODE_SET, true, false, false, true, null, 176, null);
            return this;
        }

        public final Builder password(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "password");
            Builder builder = this;
            builder.encodedPassword = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
            return builder;
        }

        public final Builder port(int n) {
            Object object = this;
            boolean bl = true;
            if (1 > n || 65535 < n) {
                bl = false;
            }
            if (bl) {
                ((Builder)object).port = n;
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected port: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder query(String list) {
            Builder builder = this;
            list = list != null && (list = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, (String)((Object)list), 0, 0, HttpUrl.QUERY_ENCODE_SET, false, false, true, false, null, 219, null)) != null ? Companion.toQueryNamesAndValues$okhttp((String)((Object)list)) : null;
            builder.encodedQueryNamesAndValues = list;
            return builder;
        }

        public final Builder reencodeForUri$okhttp() {
            int n;
            Builder builder = this;
            CharSequence charSequence = builder.host;
            Object var3_3 = null;
            if (charSequence != null) {
                charSequence = charSequence;
                charSequence = new Regex("[\"<>^`{|}]").replace(charSequence, HttpUrl.FRAGMENT_ENCODE_SET);
            } else {
                charSequence = null;
            }
            builder.host = charSequence;
            int n2 = builder.encodedPathSegments.size();
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                builder.encodedPathSegments.set(n, okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, builder.encodedPathSegments.get(n), 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true, true, false, false, null, 227, null));
            }
            List<String> list = builder.encodedQueryNamesAndValues;
            if (list != null) {
                n2 = list.size();
                for (n = n3; n < n2; ++n) {
                    charSequence = (String)list.get(n);
                    charSequence = charSequence != null ? okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, charSequence, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET_URI, true, true, true, false, null, 195, null) : null;
                    list.set(n, (String)charSequence);
                }
            }
            list = builder.encodedFragment;
            charSequence = var3_3;
            if (list != null) {
                charSequence = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, (String)((Object)list), 0, 0, HttpUrl.FRAGMENT_ENCODE_SET_URI, true, true, false, true, null, 163, null);
            }
            builder.encodedFragment = charSequence;
            return builder;
        }

        public final Builder removeAllEncodedQueryParameters(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedName");
            Builder builder = this;
            if (builder.encodedQueryNamesAndValues == null) {
                return builder;
            }
            builder.removeAllCanonicalQueryParameters(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, null, 211, null));
            return builder;
        }

        public final Builder removeAllQueryParameters(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            if (builder.encodedQueryNamesAndValues == null) {
                return builder;
            }
            builder.removeAllCanonicalQueryParameters(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, null, 219, null));
            return builder;
        }

        public final Builder removePathSegment(int n) {
            Builder builder = this;
            builder.encodedPathSegments.remove(n);
            if (!builder.encodedPathSegments.isEmpty()) return builder;
            builder.encodedPathSegments.add(HttpUrl.FRAGMENT_ENCODE_SET);
            return builder;
        }

        public final Builder scheme(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "scheme");
            Object object = this;
            if (StringsKt.equals(string2, "http", true)) {
                ((Builder)object).scheme = "http";
                return object;
            }
            if (StringsKt.equals(string2, "https", true)) {
                ((Builder)object).scheme = "https";
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected scheme: ");
            ((StringBuilder)object).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public final void setEncodedFragment$okhttp(String string2) {
            this.encodedFragment = string2;
        }

        public final void setEncodedPassword$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "<set-?>");
            this.encodedPassword = string2;
        }

        public final Builder setEncodedPathSegment(int n, String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedPathSegment");
            Object object = this;
            String string3 = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET, true, false, false, false, null, 243, null);
            ((Builder)object).encodedPathSegments.set(n, string3);
            n = !((Builder)object).isDot(string3) && !((Builder)object).isDotDot(string3) ? 1 : 0;
            if (n != 0) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected path segment: ");
            ((StringBuilder)object).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final void setEncodedQueryNamesAndValues$okhttp(List<String> list) {
            this.encodedQueryNamesAndValues = list;
        }

        public final Builder setEncodedQueryParameter(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "encodedName");
            Builder builder = this;
            builder.removeAllEncodedQueryParameters(string2);
            builder.addEncodedQueryParameter(string2, string3);
            return builder;
        }

        public final void setEncodedUsername$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "<set-?>");
            this.encodedUsername = string2;
        }

        public final void setHost$okhttp(String string2) {
            this.host = string2;
        }

        public final Builder setPathSegment(int n, String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "pathSegment");
            Builder builder = this;
            CharSequence charSequence = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, HttpUrl.PATH_SEGMENT_ENCODE_SET, false, false, false, false, null, 251, null);
            boolean bl = !builder.isDot((String)charSequence) && !builder.isDotDot((String)charSequence);
            if (bl) {
                builder.encodedPathSegments.set(n, (String)charSequence);
                return builder;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("unexpected path segment: ");
            ((StringBuilder)charSequence).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }

        public final void setPort$okhttp(int n) {
            this.port = n;
        }

        public final Builder setQueryParameter(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            builder.removeAllQueryParameters(string2);
            builder.addQueryParameter(string2, string3);
            return builder;
        }

        public final void setScheme$okhttp(String string2) {
            this.scheme = string2;
        }

        public String toString() {
            CharSequence charSequence;
            Object object;
            Object object2;
            block15 : {
                int n;
                block16 : {
                    charSequence = new StringBuilder();
                    object = this.scheme;
                    if (object != null) {
                        ((StringBuilder)charSequence).append((String)object);
                        ((StringBuilder)charSequence).append("://");
                    } else {
                        ((StringBuilder)charSequence).append("//");
                    }
                    n = ((CharSequence)this.encodedUsername).length();
                    int n2 = 1;
                    n = n > 0 ? 1 : 0;
                    if (n != 0 || (n = ((CharSequence)this.encodedPassword).length() > 0 ? 1 : 0) != 0) {
                        ((StringBuilder)charSequence).append(this.encodedUsername);
                        n = ((CharSequence)this.encodedPassword).length() > 0 ? n2 : 0;
                        if (n != 0) {
                            ((StringBuilder)charSequence).append(':');
                            ((StringBuilder)charSequence).append(this.encodedPassword);
                        }
                        ((StringBuilder)charSequence).append('@');
                    }
                    if ((object = this.host) != null) {
                        if (object == null) {
                            Intrinsics.throwNpe();
                        }
                        if (StringsKt.contains$default((CharSequence)object, ':', false, 2, null)) {
                            ((StringBuilder)charSequence).append('[');
                            ((StringBuilder)charSequence).append(this.host);
                            ((StringBuilder)charSequence).append(']');
                        } else {
                            ((StringBuilder)charSequence).append(this.host);
                        }
                    }
                    if (this.port == -1 && this.scheme == null) break block15;
                    n = this.effectivePort();
                    if (this.scheme == null) break block16;
                    object2 = Companion;
                    object = this.scheme;
                    if (object == null) {
                        Intrinsics.throwNpe();
                    }
                    if (n == ((okhttp3.HttpUrl$Companion)object2).defaultPort((String)object)) break block15;
                }
                ((StringBuilder)charSequence).append(':');
                ((StringBuilder)charSequence).append(n);
            }
            Companion.toPathString$okhttp(this.encodedPathSegments, (StringBuilder)charSequence);
            if (this.encodedQueryNamesAndValues != null) {
                ((StringBuilder)charSequence).append('?');
                object = Companion;
                object2 = this.encodedQueryNamesAndValues;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                ((okhttp3.HttpUrl$Companion)object).toQueryString$okhttp((List<String>)object2, (StringBuilder)charSequence);
            }
            if (this.encodedFragment != null) {
                ((StringBuilder)charSequence).append('#');
                ((StringBuilder)charSequence).append(this.encodedFragment);
            }
            charSequence = ((StringBuilder)charSequence).toString();
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
            return charSequence;
        }

        public final Builder username(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "username");
            Builder builder = this;
            builder.encodedUsername = okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
            return builder;
        }

        @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u001c\u0010\f\u001a\u00020\u0006*\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/HttpUrl$Builder$Companion;", "", "()V", "INVALID_HOST", "", "parsePort", "", "input", "pos", "limit", "portColonOffset", "schemeDelimiterOffset", "slashCount", "okhttp"}, k=1, mv={1, 1, 16})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private final int parsePort(String string2, int n, int n2) {
                int n3 = -1;
                try {
                    n2 = Integer.parseInt(okhttp3.HttpUrl$Companion.canonicalize$okhttp$default(HttpUrl.Companion, string2, n, n2, HttpUrl.FRAGMENT_ENCODE_SET, false, false, false, false, null, 248, null));
                    if (1 > n2) {
                        return n3;
                    }
                    n = n3;
                    if (65535 < n2) return n;
                    return n2;
                }
                catch (NumberFormatException numberFormatException) {
                    return n3;
                }
            }

            private final int portColonOffset(String string2, int n, int n2) {
                while (n < n2) {
                    int n3;
                    block4 : {
                        char c = string2.charAt(n);
                        if (c == ':') return n;
                        n3 = n;
                        if (c != '[') {
                            n3 = n;
                        } else {
                            do {
                                n3 = n = n3 + 1;
                                if (n >= n2) break block4;
                                n3 = n;
                            } while (string2.charAt(n) != ']');
                            n3 = n;
                        }
                    }
                    n = n3 + 1;
                }
                return n2;
            }

            private final int schemeDelimiterOffset(String string2, int n, int n2) {
                int n3;
                int n4;
                char c;
                block8 : {
                    block7 : {
                        n3 = -1;
                        if (n2 - n < 2) {
                            return -1;
                        }
                        c = string2.charAt(n);
                        if (c < 'a') break block7;
                        n4 = n;
                        if (c <= 'z') break block8;
                    }
                    n4 = n3;
                    if (c < 'A') return n4;
                    n4 = n;
                    if (c > 'Z') {
                        return n3;
                    }
                }
                do {
                    n = n4 + 1;
                    n4 = n3;
                    if (n >= n2) return n4;
                    c = string2.charAt(n);
                    if (!('a' <= c && 'z' >= c || 'A' <= c && 'Z' >= c || '0' <= c && '9' >= c || c == '+' || c == '-' || c == '.')) {
                        n4 = n3;
                        if (c != ':') return n4;
                        return n;
                    }
                    n4 = n;
                } while (true);
            }

            private final int slashCount(String string2, int n, int n2) {
                int n3 = 0;
                while (n < n2) {
                    char c = string2.charAt(n);
                    if (c != '\\') {
                        if (c != '/') return n3;
                    }
                    ++n3;
                    ++n;
                }
                return n3;
            }
        }

    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u00a2\u0006\u0002\b\u0018J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u00a2\u0006\u0002\b\u0018J\u0015\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\b\u0018J\u0017\u0010\u001b\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\b\u001cJa\u0010\u001d\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"2\b\b\u0002\u0010$\u001a\u00020\"2\b\b\u0002\u0010%\u001a\u00020\"2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'H\u0000\u00a2\u0006\u0002\b(J\u001c\u0010)\u001a\u00020\"*\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0002J/\u0010*\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\b\b\u0002\u0010$\u001a\u00020\"H\u0000\u00a2\u0006\u0002\b+J\u0011\u0010,\u001a\u00020\u0015*\u00020\u0004H\u0007\u00a2\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0017H\u0007\u00a2\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u001aH\u0007\u00a2\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0004H\u0007\u00a2\u0006\u0002\b\u001bJ#\u0010.\u001a\u00020/*\b\u0012\u0004\u0012\u00020\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000\u00a2\u0006\u0002\b4J\u0019\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000406*\u00020\u0004H\u0000\u00a2\u0006\u0002\b7J%\u00108\u001a\u00020/*\n\u0012\u0006\u0012\u0004\u0018\u00010\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000\u00a2\u0006\u0002\b9JV\u0010:\u001a\u00020/*\u00020;2\u0006\u0010<\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\"2\b\u0010&\u001a\u0004\u0018\u00010'H\u0002J,\u0010=\u001a\u00020/*\u00020;2\u0006\u0010>\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2={"Lokhttp3/HttpUrl$Companion;", "", "()V", "FORM_ENCODE_SET", "", "FRAGMENT_ENCODE_SET", "FRAGMENT_ENCODE_SET_URI", "HEX_DIGITS", "", "PASSWORD_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET_URI", "QUERY_COMPONENT_ENCODE_SET", "QUERY_COMPONENT_ENCODE_SET_URI", "QUERY_COMPONENT_REENCODE_SET", "QUERY_ENCODE_SET", "USERNAME_ENCODE_SET", "defaultPort", "", "scheme", "get", "Lokhttp3/HttpUrl;", "uri", "Ljava/net/URI;", "-deprecated_get", "url", "Ljava/net/URL;", "parse", "-deprecated_parse", "canonicalize", "pos", "limit", "encodeSet", "alreadyEncoded", "", "strict", "plusIsSpace", "unicodeAllowed", "charset", "Ljava/nio/charset/Charset;", "canonicalize$okhttp", "isPercentEncoded", "percentDecode", "percentDecode$okhttp", "toHttpUrl", "toHttpUrlOrNull", "toPathString", "", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toPathString$okhttp", "toQueryNamesAndValues", "", "toQueryNamesAndValues$okhttp", "toQueryString", "toQueryString$okhttp", "writeCanonicalized", "Lokio/Buffer;", "input", "writePercentDecoded", "encoded", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ String canonicalize$okhttp$default(Companion companion, String string2, int n, int n2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4, Charset charset, int n3, Object object) {
            if ((n3 & 1) != 0) {
                n = 0;
            }
            if ((n3 & 2) != 0) {
                n2 = string2.length();
            }
            if ((n3 & 8) != 0) {
                bl = false;
            }
            if ((n3 & 16) != 0) {
                bl2 = false;
            }
            if ((n3 & 32) != 0) {
                bl3 = false;
            }
            if ((n3 & 64) != 0) {
                bl4 = false;
            }
            if ((n3 & 128) == 0) return companion.canonicalize$okhttp(string2, n, n2, string3, bl, bl2, bl3, bl4, charset);
            charset = null;
            return companion.canonicalize$okhttp(string2, n, n2, string3, bl, bl2, bl3, bl4, charset);
        }

        private final boolean isPercentEncoded(String string2, int n, int n2) {
            int n3 = n + 2;
            boolean bl = true;
            if (n3 >= n2) return false;
            if (string2.charAt(n) != '%') return false;
            if (Util.parseHexDigit(string2.charAt(n + 1)) == -1) return false;
            if (Util.parseHexDigit(string2.charAt(n3)) == -1) return false;
            return bl;
        }

        public static /* synthetic */ String percentDecode$okhttp$default(Companion companion, String string2, int n, int n2, boolean bl, int n3, Object object) {
            if ((n3 & 1) != 0) {
                n = 0;
            }
            if ((n3 & 2) != 0) {
                n2 = string2.length();
            }
            if ((n3 & 4) == 0) return companion.percentDecode$okhttp(string2, n, n2, bl);
            bl = false;
            return companion.percentDecode$okhttp(string2, n, n2, bl);
        }

        private final void writeCanonicalized(Buffer buffer, String string2, int n, int n2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4, Charset charset) {
            Buffer buffer2 = null;
            while (n < n2) {
                int n3;
                Object object;
                block13 : {
                    Object object2;
                    block12 : {
                        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        n3 = string2.codePointAt(n);
                        if (!bl) break block12;
                        object = buffer2;
                        if (n3 == 9) break block13;
                        object = buffer2;
                        if (n3 == 10) break block13;
                        object = buffer2;
                        if (n3 == 12) break block13;
                        object = buffer2;
                        if (n3 == 13) break block13;
                    }
                    if (n3 == 43 && bl3) {
                        object2 = bl ? "+" : "%2B";
                        buffer.writeUtf8((String)object2);
                        object = buffer2;
                    } else if (!(n3 < 32 || n3 == 127 || n3 >= 128 && !bl4 || StringsKt.contains$default((CharSequence)string3, (char)n3, false, 2, null) || n3 == 37 && (!bl || bl2 && !this.isPercentEncoded(string2, n, n2)))) {
                        buffer.writeUtf8CodePoint(n3);
                        object = buffer2;
                    } else {
                        object2 = buffer2;
                        if (buffer2 == null) {
                            object2 = new Buffer();
                        }
                        if (charset != null && !Intrinsics.areEqual(charset, StandardCharsets.UTF_8)) {
                            ((Buffer)object2).writeString(string2, n, Character.charCount(n3) + n, charset);
                        } else {
                            ((Buffer)object2).writeUtf8CodePoint(n3);
                        }
                        do {
                            object = object2;
                            if (((Buffer)object2).exhausted()) break;
                            int n4 = ((Buffer)object2).readByte() & 255;
                            buffer.writeByte(37);
                            buffer.writeByte(HEX_DIGITS[n4 >> 4 & 15]);
                            buffer.writeByte(HEX_DIGITS[n4 & 15]);
                        } while (true);
                    }
                }
                n += Character.charCount(n3);
                buffer2 = object;
            }
        }

        private final void writePercentDecoded(Buffer buffer, String string2, int n, int n2, boolean bl) {
            while (n < n2) {
                int n3;
                if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                int n4 = string2.codePointAt(n);
                if (n4 == 37 && (n3 = n + 2) < n2) {
                    int n5 = Util.parseHexDigit(string2.charAt(n + 1));
                    int n6 = Util.parseHexDigit(string2.charAt(n3));
                    if (n5 != -1 && n6 != -1) {
                        buffer.writeByte((n5 << 4) + n6);
                        n = Character.charCount(n4) + n3;
                        continue;
                    }
                } else if (n4 == 43 && bl) {
                    buffer.writeByte(32);
                    ++n;
                    continue;
                }
                buffer.writeUtf8CodePoint(n4);
                n += Character.charCount(n4);
            }
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrl()", imports={"okhttp3.HttpUrl.Companion.toHttpUrl"}))
        public final HttpUrl -deprecated_get(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "url");
            return this.get(string2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="uri.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        public final HttpUrl -deprecated_get(URI uRI) {
            Intrinsics.checkParameterIsNotNull(uRI, "uri");
            return this.get(uRI);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        public final HttpUrl -deprecated_get(URL uRL) {
            Intrinsics.checkParameterIsNotNull(uRL, "url");
            return this.get(uRL);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        public final HttpUrl -deprecated_parse(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "url");
            return this.parse(string2);
        }

        public final String canonicalize$okhttp(String string2, int n, int n2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4, Charset charset) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$canonicalize");
            Intrinsics.checkParameterIsNotNull(string3, "encodeSet");
            int n3 = n;
            do {
                if (n3 >= n2) {
                    string2 = string2.substring(n, n2);
                    Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    return string2;
                }
                int n4 = string2.codePointAt(n3);
                if (n4 < 32 || n4 == 127 || n4 >= 128 && !bl4 || StringsKt.contains$default((CharSequence)string3, (char)n4, false, 2, null) || n4 == 37 && (!bl || bl2 && !this.isPercentEncoded(string2, n3, n2)) || n4 == 43 && bl3) break;
                n3 += Character.charCount(n4);
            } while (true);
            Buffer buffer = new Buffer();
            buffer.writeUtf8(string2, n, n3);
            this.writeCanonicalized(buffer, string2, n3, n2, string3, bl, bl2, bl3, bl4, charset);
            return buffer.readUtf8();
        }

        @JvmStatic
        public final int defaultPort(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "scheme");
            int n = string2.hashCode();
            if (n != 3213448) {
                if (n != 99617003) return -1;
                if (!string2.equals("https")) return -1;
                return 443;
            }
            if (!string2.equals("http")) return -1;
            return 80;
        }

        @JvmStatic
        public final HttpUrl get(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$toHttpUrl");
            return new Builder().parse$okhttp(null, string2).build();
        }

        @JvmStatic
        public final HttpUrl get(URI object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toHttpUrlOrNull");
            Companion companion = this;
            object = ((URI)object).toString();
            Intrinsics.checkExpressionValueIsNotNull(object, "toString()");
            return companion.parse((String)object);
        }

        @JvmStatic
        public final HttpUrl get(URL object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toHttpUrlOrNull");
            Companion companion = this;
            object = ((URL)object).toString();
            Intrinsics.checkExpressionValueIsNotNull(object, "toString()");
            return companion.parse((String)object);
        }

        @JvmStatic
        public final HttpUrl parse(String object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toHttpUrlOrNull");
            try {
                return this.get((String)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }

        public final String percentDecode$okhttp(String string2, int n, int n2, boolean bl) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$percentDecode");
            int n3 = n;
            do {
                if (n3 >= n2) {
                    string2 = string2.substring(n, n2);
                    Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    return string2;
                }
                char c = string2.charAt(n3);
                if (c == '%' || c == '+' && bl) break;
                ++n3;
            } while (true);
            Buffer buffer = new Buffer();
            buffer.writeUtf8(string2, n, n3);
            this.writePercentDecoded(buffer, string2, n3, n2, bl);
            return buffer.readUtf8();
        }

        public final void toPathString$okhttp(List<String> list, StringBuilder stringBuilder) {
            Intrinsics.checkParameterIsNotNull(list, "$this$toPathString");
            Intrinsics.checkParameterIsNotNull(stringBuilder, "out");
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                stringBuilder.append('/');
                stringBuilder.append(list.get(n2));
                ++n2;
            }
        }

        public final List<String> toQueryNamesAndValues$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$toQueryNamesAndValues");
            List list = new ArrayList();
            int n = 0;
            while (n <= string2.length()) {
                int n2;
                CharSequence charSequence = string2;
                int n3 = n2 = StringsKt.indexOf$default(charSequence, '&', n, false, 4, null);
                if (n2 == -1) {
                    n3 = string2.length();
                }
                if ((n2 = StringsKt.indexOf$default(charSequence, '=', n, false, 4, null)) != -1 && n2 <= n3) {
                    charSequence = string2.substring(n, n2);
                    Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    list.add(charSequence);
                    charSequence = string2.substring(n2 + 1, n3);
                    Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    list.add(charSequence);
                } else {
                    charSequence = string2.substring(n, n3);
                    Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    list.add(charSequence);
                    list.add(null);
                }
                n = n3 + 1;
            }
            return list;
        }

        public final void toQueryString$okhttp(List<String> list, StringBuilder stringBuilder) {
            Intrinsics.checkParameterIsNotNull(list, "$this$toQueryString");
            Intrinsics.checkParameterIsNotNull(stringBuilder, "out");
            Object object = RangesKt.step(RangesKt.until(0, list.size()), 2);
            int n = ((IntProgression)object).getFirst();
            int n2 = ((IntProgression)object).getLast();
            int n3 = ((IntProgression)object).getStep();
            if (n3 >= 0) {
                if (n > n2) return;
            } else if (n < n2) return;
            do {
                String string2 = list.get(n);
                object = list.get(n + 1);
                if (n > 0) {
                    stringBuilder.append('&');
                }
                stringBuilder.append(string2);
                if (object != null) {
                    stringBuilder.append('=');
                    stringBuilder.append((String)object);
                }
                if (n == n2) return;
                n += n3;
            } while (true);
        }
    }

}

