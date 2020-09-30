/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 &2\u00020\u0001:\u0002%&BO\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000eJ\r\u0010\u0007\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0012J\u0013\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\r\u0010\u0005\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0017J\r\u0010\r\u001a\u00020\nH\u0007\u00a2\u0006\u0002\b\u0018J\r\u0010\u000b\u001a\u00020\nH\u0007\u00a2\u0006\u0002\b\u0019J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001cJ\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001dJ\r\u0010\b\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001eJ\r\u0010\f\u001a\u00020\nH\u0007\u00a2\u0006\u0002\b\u001fJ\r\u0010\t\u001a\u00020\nH\u0007\u00a2\u0006\u0002\b J\b\u0010!\u001a\u00020\u0003H\u0016J\u0015\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\nH\u0000\u00a2\u0006\u0002\b#J\r\u0010\u0004\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b$R\u0013\u0010\u0007\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000fR\u0013\u0010\u0005\u001a\u00020\u00068\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0013\u0010\r\u001a\u00020\n8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0011R\u0013\u0010\u000b\u001a\u00020\n8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0011R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000fR\u0013\u0010\b\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000fR\u0013\u0010\f\u001a\u00020\n8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0011R\u0013\u0010\t\u001a\u00020\n8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0011R\u0013\u0010\u0004\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000f\u00a8\u0006'"}, d2={"Lokhttp3/Cookie;", "", "name", "", "value", "expiresAt", "", "domain", "path", "secure", "", "httpOnly", "persistent", "hostOnly", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;ZZZZ)V", "()Ljava/lang/String;", "()J", "()Z", "-deprecated_domain", "equals", "other", "-deprecated_expiresAt", "hashCode", "", "-deprecated_hostOnly", "-deprecated_httpOnly", "matches", "url", "Lokhttp3/HttpUrl;", "-deprecated_name", "-deprecated_path", "-deprecated_persistent", "-deprecated_secure", "toString", "forObsoleteRfc2965", "toString$okhttp", "-deprecated_value", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Cookie {
    public static final Companion Companion = new Companion(null);
    private static final Pattern DAY_OF_MONTH_PATTERN;
    private static final Pattern MONTH_PATTERN;
    private static final Pattern TIME_PATTERN;
    private static final Pattern YEAR_PATTERN;
    private final String domain;
    private final long expiresAt;
    private final boolean hostOnly;
    private final boolean httpOnly;
    private final String name;
    private final String path;
    private final boolean persistent;
    private final boolean secure;
    private final String value;

    static {
        YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
        MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
        DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
        TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    }

    private Cookie(String string2, String string3, long l, String string4, String string5, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.name = string2;
        this.value = string3;
        this.expiresAt = l;
        this.domain = string4;
        this.path = string5;
        this.secure = bl;
        this.httpOnly = bl2;
        this.persistent = bl3;
        this.hostOnly = bl4;
    }

    public /* synthetic */ Cookie(String string2, String string3, long l, String string4, String string5, boolean bl, boolean bl2, boolean bl3, boolean bl4, DefaultConstructorMarker defaultConstructorMarker) {
        this(string2, string3, l, string4, string5, bl, bl2, bl3, bl4);
    }

    @JvmStatic
    public static final Cookie parse(HttpUrl httpUrl, String string2) {
        return Companion.parse(httpUrl, string2);
    }

    @JvmStatic
    public static final List<Cookie> parseAll(HttpUrl httpUrl, Headers headers) {
        return Companion.parseAll(httpUrl, headers);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="domain", imports={}))
    public final String -deprecated_domain() {
        return this.domain;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="expiresAt", imports={}))
    public final long -deprecated_expiresAt() {
        return this.expiresAt;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hostOnly", imports={}))
    public final boolean -deprecated_hostOnly() {
        return this.hostOnly;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="httpOnly", imports={}))
    public final boolean -deprecated_httpOnly() {
        return this.httpOnly;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="name", imports={}))
    public final String -deprecated_name() {
        return this.name;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="path", imports={}))
    public final String -deprecated_path() {
        return this.path;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="persistent", imports={}))
    public final boolean -deprecated_persistent() {
        return this.persistent;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="secure", imports={}))
    public final boolean -deprecated_secure() {
        return this.secure;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="value", imports={}))
    public final String -deprecated_value() {
        return this.value;
    }

    public final String domain() {
        return this.domain;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Cookie)) return false;
        object = (Cookie)object;
        if (!Intrinsics.areEqual(((Cookie)object).name, this.name)) return false;
        if (!Intrinsics.areEqual(((Cookie)object).value, this.value)) return false;
        if (((Cookie)object).expiresAt != this.expiresAt) return false;
        if (!Intrinsics.areEqual(((Cookie)object).domain, this.domain)) return false;
        if (!Intrinsics.areEqual(((Cookie)object).path, this.path)) return false;
        if (((Cookie)object).secure != this.secure) return false;
        if (((Cookie)object).httpOnly != this.httpOnly) return false;
        if (((Cookie)object).persistent != this.persistent) return false;
        if (((Cookie)object).hostOnly != this.hostOnly) return false;
        return true;
    }

    public final long expiresAt() {
        return this.expiresAt;
    }

    public int hashCode() {
        return ((((((((527 + this.name.hashCode()) * 31 + this.value.hashCode()) * 31 + Long.hashCode(this.expiresAt)) * 31 + this.domain.hashCode()) * 31 + this.path.hashCode()) * 31 + Boolean.hashCode(this.secure)) * 31 + Boolean.hashCode(this.httpOnly)) * 31 + Boolean.hashCode(this.persistent)) * 31 + Boolean.hashCode(this.hostOnly);
    }

    public final boolean hostOnly() {
        return this.hostOnly;
    }

    public final boolean httpOnly() {
        return this.httpOnly;
    }

    public final boolean matches(HttpUrl httpUrl) {
        Intrinsics.checkParameterIsNotNull(httpUrl, "url");
        boolean bl = this.hostOnly ? Intrinsics.areEqual(httpUrl.host(), this.domain) : Cookie.Companion.domainMatch(httpUrl.host(), this.domain);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (!Cookie.Companion.pathMatch(httpUrl, this.path)) {
            return false;
        }
        if (!this.secure) return true;
        bl = bl2;
        if (!httpUrl.isHttps()) return bl;
        return true;
    }

    public final String name() {
        return this.name;
    }

    public final String path() {
        return this.path;
    }

    public final boolean persistent() {
        return this.persistent;
    }

    public final boolean secure() {
        return this.secure;
    }

    public String toString() {
        return this.toString$okhttp(false);
    }

    public final String toString$okhttp(boolean bl) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.name);
        charSequence.append('=');
        charSequence.append(this.value);
        if (this.persistent) {
            if (this.expiresAt == Long.MIN_VALUE) {
                charSequence.append("; max-age=0");
            } else {
                charSequence.append("; expires=");
                charSequence.append(DatesKt.toHttpDateString(new Date(this.expiresAt)));
            }
        }
        if (!this.hostOnly) {
            charSequence.append("; domain=");
            if (bl) {
                charSequence.append(".");
            }
            charSequence.append(this.domain);
        }
        charSequence.append("; path=");
        charSequence.append(this.path);
        if (this.secure) {
            charSequence.append("; secure");
        }
        if (this.httpOnly) {
            charSequence.append("; httponly");
        }
        charSequence = charSequence.toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "toString()");
        return charSequence;
    }

    public final String value() {
        return this.value;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0018\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\t\u001a\u00020\u0000J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004J\u0006\u0010\r\u001a\u00020\u0000J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/Cookie$Builder;", "", "()V", "domain", "", "expiresAt", "", "hostOnly", "", "httpOnly", "name", "path", "persistent", "secure", "value", "build", "Lokhttp3/Cookie;", "hostOnlyDomain", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private String domain;
        private long expiresAt = 253402300799999L;
        private boolean hostOnly;
        private boolean httpOnly;
        private String name;
        private String path = "/";
        private boolean persistent;
        private boolean secure;
        private String value;

        private final Builder domain(String string2, boolean bl) {
            Builder builder = this;
            CharSequence charSequence = HostnamesKt.toCanonicalHost(string2);
            if (charSequence != null) {
                builder.domain = charSequence;
                builder.hostOnly = bl;
                return builder;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("unexpected domain: ");
            ((StringBuilder)charSequence).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }

        public final Cookie build() {
            String string2 = this.name;
            if (string2 == null) throw (Throwable)new NullPointerException("builder.name == null");
            String string3 = this.value;
            if (string3 == null) throw (Throwable)new NullPointerException("builder.value == null");
            long l = this.expiresAt;
            String string4 = this.domain;
            if (string4 == null) throw (Throwable)new NullPointerException("builder.domain == null");
            return new Cookie(string2, string3, l, string4, this.path, this.secure, this.httpOnly, this.persistent, this.hostOnly, null);
        }

        public final Builder domain(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "domain");
            return this.domain(string2, false);
        }

        public final Builder expiresAt(long l) {
            Builder builder = this;
            long l2 = l;
            if (l <= 0L) {
                l2 = Long.MIN_VALUE;
            }
            l = l2;
            if (l2 > 253402300799999L) {
                l = 253402300799999L;
            }
            builder.expiresAt = l;
            builder.persistent = true;
            return builder;
        }

        public final Builder hostOnlyDomain(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "domain");
            return this.domain(string2, true);
        }

        public final Builder httpOnly() {
            Builder builder = this;
            builder.httpOnly = true;
            return builder;
        }

        public final Builder name(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            if (!Intrinsics.areEqual(((Object)StringsKt.trim((CharSequence)string2)).toString(), string2)) throw (Throwable)new IllegalArgumentException("name is not trimmed".toString());
            builder.name = string2;
            return builder;
        }

        public final Builder path(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "path");
            Builder builder = this;
            if (!StringsKt.startsWith$default(string2, "/", false, 2, null)) throw (Throwable)new IllegalArgumentException("path must start with '/'".toString());
            builder.path = string2;
            return builder;
        }

        public final Builder secure() {
            Builder builder = this;
            builder.secure = true;
            return builder;
        }

        public final Builder value(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "value");
            Builder builder = this;
            if (!Intrinsics.areEqual(((Object)StringsKt.trim((CharSequence)string2)).toString(), string2)) throw (Throwable)new IllegalArgumentException("value is not trimmed".toString());
            builder.value = string2;
            return builder;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\fH\u0002J'\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0000\u00a2\u0006\u0002\b\u001bJ\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0007J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001d2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\fH\u0002J \u0010\"\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\fH\u0002J\u0018\u0010$\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\fH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lokhttp3/Cookie$Companion;", "", "()V", "DAY_OF_MONTH_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "MONTH_PATTERN", "TIME_PATTERN", "YEAR_PATTERN", "dateCharacterOffset", "", "input", "", "pos", "limit", "invert", "", "domainMatch", "urlHost", "domain", "parse", "Lokhttp3/Cookie;", "currentTimeMillis", "", "url", "Lokhttp3/HttpUrl;", "setCookie", "parse$okhttp", "parseAll", "", "headers", "Lokhttp3/Headers;", "parseDomain", "s", "parseExpires", "parseMaxAge", "pathMatch", "path", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final int dateCharacterOffset(String string2, int n, int n2, boolean bl) {
            while (n < n2) {
                char c = string2.charAt(n);
                c = !(c < ' ' && c != '\t' || c >= '' || '0' <= c && '9' >= c || 'a' <= c && 'z' >= c || 'A' <= c && 'Z' >= c || c == ':') ? (char)'\u0000' : '\u0001';
                if (c == (bl ^ true)) {
                    return n;
                }
                ++n;
            }
            return n2;
        }

        private final boolean domainMatch(String string2, String string3) {
            boolean bl = Intrinsics.areEqual(string2, string3);
            boolean bl2 = true;
            if (bl) {
                return true;
            }
            if (!StringsKt.endsWith$default(string2, string3, false, 2, null)) return false;
            if (string2.charAt(string2.length() - string3.length() - 1) != '.') return false;
            if (Util.canParseAsIpAddress(string2)) return false;
            return bl2;
        }

        private final String parseDomain(String string2) {
            if (!(StringsKt.endsWith$default(string2, ".", false, 2, null) ^ true)) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            if ((string2 = HostnamesKt.toCanonicalHost(StringsKt.removePrefix(string2, (CharSequence)"."))) == null) throw (Throwable)new IllegalArgumentException();
            return string2;
        }

        private final long parseExpires(String object, int n, int n2) {
            Companion companion = this;
            int n3 = companion.dateCharacterOffset((String)object, n, n2, false);
            Matcher matcher = TIME_PATTERN.matcher((CharSequence)object);
            n = -1;
            int n4 = -1;
            int n5 = -1;
            int n6 = -1;
            int n7 = -1;
            int n8 = -1;
            while (n3 < n2) {
                int n9;
                int n10;
                int n11;
                Object object2;
                int n12;
                int n13;
                int n14;
                int n15 = companion.dateCharacterOffset((String)object, n3 + 1, n2, true);
                matcher.region(n3, n15);
                if (n4 == -1 && matcher.usePattern(TIME_PATTERN).matches()) {
                    object2 = matcher.group(1);
                    Intrinsics.checkExpressionValueIsNotNull(object2, "matcher.group(1)");
                    n9 = Integer.parseInt((String)object2);
                    object2 = matcher.group(2);
                    Intrinsics.checkExpressionValueIsNotNull(object2, "matcher.group(2)");
                    n12 = Integer.parseInt((String)object2);
                    object2 = matcher.group(3);
                    Intrinsics.checkExpressionValueIsNotNull(object2, "matcher.group(3)");
                    n13 = Integer.parseInt((String)object2);
                    n14 = n;
                    n10 = n5;
                    n11 = n6;
                } else if (n5 == -1 && matcher.usePattern(DAY_OF_MONTH_PATTERN).matches()) {
                    object2 = matcher.group(1);
                    Intrinsics.checkExpressionValueIsNotNull(object2, "matcher.group(1)");
                    n10 = Integer.parseInt((String)object2);
                    n14 = n;
                    n9 = n4;
                    n11 = n6;
                    n12 = n7;
                    n13 = n8;
                } else if (n6 == -1 && matcher.usePattern(MONTH_PATTERN).matches()) {
                    String string2 = matcher.group(1);
                    Intrinsics.checkExpressionValueIsNotNull(string2, "matcher.group(1)");
                    object2 = Locale.US;
                    Intrinsics.checkExpressionValueIsNotNull(object2, "Locale.US");
                    if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    string2 = string2.toLowerCase((Locale)object2);
                    Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase(locale)");
                    object2 = MONTH_PATTERN.pattern();
                    Intrinsics.checkExpressionValueIsNotNull(object2, "MONTH_PATTERN.pattern()");
                    n11 = StringsKt.indexOf$default((CharSequence)object2, string2, 0, false, 6, null) / 4;
                    n14 = n;
                    n9 = n4;
                    n10 = n5;
                    n12 = n7;
                    n13 = n8;
                } else {
                    n14 = n;
                    n9 = n4;
                    n10 = n5;
                    n11 = n6;
                    n12 = n7;
                    n13 = n8;
                    if (n == -1) {
                        n14 = n;
                        n9 = n4;
                        n10 = n5;
                        n11 = n6;
                        n12 = n7;
                        n13 = n8;
                        if (matcher.usePattern(YEAR_PATTERN).matches()) {
                            object2 = matcher.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(object2, "matcher.group(1)");
                            n14 = Integer.parseInt((String)object2);
                            n13 = n8;
                            n12 = n7;
                            n11 = n6;
                            n10 = n5;
                            n9 = n4;
                        }
                    }
                }
                n3 = companion.dateCharacterOffset((String)object, n15 + 1, n2, false);
                n = n14;
                n4 = n9;
                n5 = n10;
                n6 = n11;
                n7 = n12;
                n8 = n13;
            }
            if (70 > n) {
                n2 = n;
            } else {
                n2 = n;
                if (99 >= n) {
                    n2 = n + 1900;
                }
            }
            if (n2 < 0) {
                n = n2;
            } else {
                n = n2;
                if (69 >= n2) {
                    n = n2 + 2000;
                }
            }
            n2 = n >= 1601 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            n2 = n6 != -1 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            n2 = 1 <= n5 && 31 >= n5 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            n2 = n4 >= 0 && 23 >= n4 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            n2 = n7 >= 0 && 59 >= n7 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            n2 = n8 >= 0 && 59 >= n8 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            object = new GregorianCalendar(Util.UTC);
            ((Calendar)object).setLenient(false);
            ((Calendar)object).set(1, n);
            ((Calendar)object).set(2, n6 - 1);
            ((Calendar)object).set(5, n5);
            ((Calendar)object).set(11, n4);
            ((Calendar)object).set(12, n7);
            ((Calendar)object).set(13, n8);
            ((Calendar)object).set(14, 0);
            return ((Calendar)object).getTimeInMillis();
        }

        private final long parseMaxAge(String string2) {
            long l = Long.MIN_VALUE;
            try {
                long l2 = Long.parseLong(string2);
                if (l2 > 0L) return l2;
                return l;
            }
            catch (NumberFormatException numberFormatException) {
                CharSequence charSequence = string2;
                if (!new Regex("-?\\d+").matches(charSequence)) throw (Throwable)numberFormatException;
                if (!StringsKt.startsWith$default(string2, "-", false, 2, null)) return Long.MAX_VALUE;
                return l;
            }
        }

        private final boolean pathMatch(HttpUrl object, String string2) {
            if (Intrinsics.areEqual(object = ((HttpUrl)object).encodedPath(), string2)) {
                return true;
            }
            if (!StringsKt.startsWith$default((String)object, string2, false, 2, null)) return false;
            if (StringsKt.endsWith$default(string2, "/", false, 2, null)) {
                return true;
            }
            if (((String)object).charAt(string2.length()) != '/') return false;
            return true;
        }

        @JvmStatic
        public final Cookie parse(HttpUrl httpUrl, String string2) {
            Intrinsics.checkParameterIsNotNull(httpUrl, "url");
            Intrinsics.checkParameterIsNotNull(string2, "setCookie");
            return this.parse$okhttp(System.currentTimeMillis(), httpUrl, string2);
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        public final Cookie parse$okhttp(long var1_1, HttpUrl var3_2, String var4_3) {
            Intrinsics.checkParameterIsNotNull(var3_2, "url");
            Intrinsics.checkParameterIsNotNull(var4_3, "setCookie");
            var5_4 = Util.delimiterOffset$default(var4_3, ';', 0, 0, 6, null);
            var6_5 = Util.delimiterOffset$default(var4_3, '=', 0, var5_4, 2, null);
            if (var6_5 == var5_4) {
                return null;
            }
            var7_6 = Util.trimSubstring$default(var4_3, 0, var6_5, 1, null);
            var8_7 = ((CharSequence)var7_6).length() == 0 ? 1 : 0;
            if (var8_7 != 0) return null;
            if (Util.indexOfControlOrNonAscii(var7_6) != -1) {
                return null;
            }
            var9_8 = Util.trimSubstring(var4_3, var6_5 + 1, var5_4);
            if (Util.indexOfControlOrNonAscii(var9_8) != -1) {
                return null;
            }
            var10_9 = null;
            var8_7 = var5_4 + 1;
            var5_4 = var4_3.length();
            var11_10 = var10_9;
            var12_11 = -1L;
            var14_12 = false;
            var15_13 = false;
            var16_14 = false;
            var17_15 = true;
            var18_16 = 253402300799999L;
            do {
                block26 : {
                    block28 : {
                        block29 : {
                            block30 : {
                                block27 : {
                                    block24 : {
                                        block25 : {
                                            if (var8_7 >= var5_4) break block24;
                                            var6_5 = Util.delimiterOffset(var4_3, ';', var8_7, var5_4);
                                            var20_17 = Util.delimiterOffset(var4_3, '=', var8_7, var6_5);
                                            var21_18 = Util.trimSubstring(var4_3, var8_7, var20_17);
                                            var22_19 = var20_17 < var6_5 ? Util.trimSubstring(var4_3, var20_17 + 1, var6_5) : "";
                                            if (!StringsKt.equals(var21_18, "expires", true)) break block25;
                                            try {
                                                var18_16 = var23_21 = this.parseExpires(var22_19, 0, var22_19.length());
                                                ** GOTO lbl39
                                            }
                                            catch (IllegalArgumentException | NumberFormatException var22_20) {
                                                var22_19 = var10_9;
                                                var26_23 = var11_10;
                                                var27_24 = var14_12;
                                                var25_22 = var16_14;
                                                var28_25 = var17_15;
                                                var23_21 = var12_11;
                                                var29_26 = var18_16;
                                            }
                                        }
                                        if (StringsKt.equals(var21_18, "max-age", true)) {
                                            var12_11 = var23_21 = this.parseMaxAge(var22_19);
lbl39: // 2 sources:
                                            var25_22 = true;
                                            var22_19 = var10_9;
                                            var26_23 = var11_10;
                                            var27_24 = var14_12;
                                            var28_25 = var17_15;
                                            var23_21 = var12_11;
                                            var29_26 = var18_16;
                                        } else if (StringsKt.equals(var21_18, "domain", true)) {
                                            var22_19 = this.parseDomain(var22_19);
                                            var28_25 = false;
                                            var26_23 = var11_10;
                                            var27_24 = var14_12;
                                            var25_22 = var16_14;
                                            var23_21 = var12_11;
                                            var29_26 = var18_16;
                                            ** GOTO lbl139
                                        } else if (StringsKt.equals(var21_18, "path", true)) {
                                            var26_23 = var22_19;
                                            var22_19 = var10_9;
                                            var27_24 = var14_12;
                                            var25_22 = var16_14;
                                            var28_25 = var17_15;
                                            var23_21 = var12_11;
                                            var29_26 = var18_16;
                                        } else if (StringsKt.equals(var21_18, "secure", true)) {
                                            var27_24 = true;
                                            var22_19 = var10_9;
                                            var26_23 = var11_10;
                                            var25_22 = var16_14;
                                            var28_25 = var17_15;
                                            var23_21 = var12_11;
                                            var29_26 = var18_16;
                                        } else {
                                            var22_19 = var10_9;
                                            var26_23 = var11_10;
                                            var27_24 = var14_12;
                                            var25_22 = var16_14;
                                            var28_25 = var17_15;
                                            var23_21 = var12_11;
                                            var29_26 = var18_16;
                                            if (StringsKt.equals(var21_18, "httponly", true)) {
                                                var15_13 = true;
                                                var29_26 = var18_16;
                                                var23_21 = var12_11;
                                                var28_25 = var17_15;
                                                var25_22 = var16_14;
                                                var27_24 = var14_12;
                                                var26_23 = var11_10;
                                                var22_19 = var10_9;
                                            }
                                        }
                                        break block26;
                                    }
                                    var23_21 = Long.MIN_VALUE;
                                    if (var12_11 != Long.MIN_VALUE) break block27;
                                    var1_1 = var23_21;
                                    break block28;
                                }
                                if (var12_11 == -1L) break block29;
                                var18_16 = var12_11 <= 9223372036854775L ? var12_11 * (long)1000 : Long.MAX_VALUE;
                                if ((var18_16 = var1_1 + var18_16) < var1_1) break block30;
                                var1_1 = var18_16;
                                if (var18_16 <= 253402300799999L) break block28;
                            }
                            var1_1 = 253402300799999L;
                            break block28;
                        }
                        var1_1 = var18_16;
                    }
                    var22_19 = var3_2.host();
                    if (var10_9 == null) {
                        var4_3 = var22_19;
                    } else {
                        if (!this.domainMatch(var22_19, var10_9)) {
                            return null;
                        }
                        var4_3 = var10_9;
                    }
                    if (var22_19.length() != var4_3.length() && PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(var4_3) == null) {
                        return null;
                    }
                    var10_9 = "/";
                    if (var11_10 != null && StringsKt.startsWith$default(var11_10, "/", false, 2, null)) {
                        var3_2 = var11_10;
                        return new Cookie(var7_6, var9_8, var1_1, var4_3, (String)var3_2, var14_12, var15_13, var16_14, var17_15, null);
                    }
                    var11_10 = var3_2.encodedPath();
                    var8_7 = StringsKt.lastIndexOf$default((CharSequence)var11_10, '/', 0, false, 6, null);
                    var3_2 = var10_9;
                    if (var8_7 == 0) return new Cookie(var7_6, var9_8, var1_1, var4_3, (String)var3_2, var14_12, var15_13, var16_14, var17_15, null);
                    if (var11_10 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    var3_2 = var11_10.substring(0, var8_7);
                    Intrinsics.checkExpressionValueIsNotNull(var3_2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    return new Cookie(var7_6, var9_8, var1_1, var4_3, (String)var3_2, var14_12, var15_13, var16_14, var17_15, null);
                }
                var8_7 = var6_5 + 1;
                var10_9 = var22_19;
                var11_10 = var26_23;
                var14_12 = var27_24;
                var16_14 = var25_22;
                var17_15 = var28_25;
                var12_11 = var23_21;
                var18_16 = var29_26;
            } while (true);
        }

        @JvmStatic
        public final List<Cookie> parseAll(HttpUrl list, Headers iterable) {
            Intrinsics.checkParameterIsNotNull(list, "url");
            Intrinsics.checkParameterIsNotNull(iterable, "headers");
            List<String> list2 = ((Headers)iterable).values("Set-Cookie");
            iterable = null;
            int n = list2.size();
            for (int i = 0; i < n; ++i) {
                Cookie cookie = this.parse((HttpUrl)((Object)list), list2.get(i));
                Iterable<Pair<String, String>> iterable2 = iterable;
                if (cookie != null) {
                    iterable2 = iterable;
                    if (iterable == null) {
                        iterable2 = new ArrayList();
                    }
                    iterable2.add((Pair<String, String>)((Object)cookie));
                }
                iterable = iterable2;
            }
            if (iterable == null) return CollectionsKt.emptyList();
            list = Collections.unmodifiableList(iterable);
            Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiableList(cookies)");
            return list;
        }
    }

}

