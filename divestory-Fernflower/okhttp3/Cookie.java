package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 &2\u00020\u0001:\u0002%&BO\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\n¢\u0006\u0002\u0010\u000eJ\r\u0010\u0007\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0012J\u0013\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\r\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0017J\r\u0010\r\u001a\u00020\nH\u0007¢\u0006\u0002\b\u0018J\r\u0010\u000b\u001a\u00020\nH\u0007¢\u0006\u0002\b\u0019J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001cJ\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001dJ\r\u0010\b\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001eJ\r\u0010\f\u001a\u00020\nH\u0007¢\u0006\u0002\b\u001fJ\r\u0010\t\u001a\u00020\nH\u0007¢\u0006\u0002\b J\b\u0010!\u001a\u00020\u0003H\u0016J\u0015\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\nH\u0000¢\u0006\u0002\b#J\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b$R\u0013\u0010\u0007\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000fR\u0013\u0010\u0005\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0013\u0010\r\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0011R\u0013\u0010\u000b\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0011R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000fR\u0013\u0010\b\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000fR\u0013\u0010\f\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0011R\u0013\u0010\t\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0011R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000f¨\u0006'"},
   d2 = {"Lokhttp3/Cookie;", "", "name", "", "value", "expiresAt", "", "domain", "path", "secure", "", "httpOnly", "persistent", "hostOnly", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;ZZZZ)V", "()Ljava/lang/String;", "()J", "()Z", "-deprecated_domain", "equals", "other", "-deprecated_expiresAt", "hashCode", "", "-deprecated_hostOnly", "-deprecated_httpOnly", "matches", "url", "Lokhttp3/HttpUrl;", "-deprecated_name", "-deprecated_path", "-deprecated_persistent", "-deprecated_secure", "toString", "forObsoleteRfc2965", "toString$okhttp", "-deprecated_value", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Cookie {
   public static final Cookie.Companion Companion = new Cookie.Companion((DefaultConstructorMarker)null);
   private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
   private static final Pattern MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
   private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
   private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
   private final String domain;
   private final long expiresAt;
   private final boolean hostOnly;
   private final boolean httpOnly;
   private final String name;
   private final String path;
   private final boolean persistent;
   private final boolean secure;
   private final String value;

   private Cookie(String var1, String var2, long var3, String var5, String var6, boolean var7, boolean var8, boolean var9, boolean var10) {
      this.name = var1;
      this.value = var2;
      this.expiresAt = var3;
      this.domain = var5;
      this.path = var6;
      this.secure = var7;
      this.httpOnly = var8;
      this.persistent = var9;
      this.hostOnly = var10;
   }

   // $FF: synthetic method
   public Cookie(String var1, String var2, long var3, String var5, String var6, boolean var7, boolean var8, boolean var9, boolean var10, DefaultConstructorMarker var11) {
      this(var1, var2, var3, var5, var6, var7, var8, var9, var10);
   }

   @JvmStatic
   public static final Cookie parse(HttpUrl var0, String var1) {
      return Companion.parse(var0, var1);
   }

   @JvmStatic
   public static final List<Cookie> parseAll(HttpUrl var0, Headers var1) {
      return Companion.parseAll(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "domain",
   imports = {}
)
   )
   public final String _deprecated_domain/* $FF was: -deprecated_domain*/() {
      return this.domain;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "expiresAt",
   imports = {}
)
   )
   public final long _deprecated_expiresAt/* $FF was: -deprecated_expiresAt*/() {
      return this.expiresAt;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "hostOnly",
   imports = {}
)
   )
   public final boolean _deprecated_hostOnly/* $FF was: -deprecated_hostOnly*/() {
      return this.hostOnly;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "httpOnly",
   imports = {}
)
   )
   public final boolean _deprecated_httpOnly/* $FF was: -deprecated_httpOnly*/() {
      return this.httpOnly;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "name",
   imports = {}
)
   )
   public final String _deprecated_name/* $FF was: -deprecated_name*/() {
      return this.name;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "path",
   imports = {}
)
   )
   public final String _deprecated_path/* $FF was: -deprecated_path*/() {
      return this.path;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "persistent",
   imports = {}
)
   )
   public final boolean _deprecated_persistent/* $FF was: -deprecated_persistent*/() {
      return this.persistent;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "secure",
   imports = {}
)
   )
   public final boolean _deprecated_secure/* $FF was: -deprecated_secure*/() {
      return this.secure;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "value",
   imports = {}
)
   )
   public final String _deprecated_value/* $FF was: -deprecated_value*/() {
      return this.value;
   }

   public final String domain() {
      return this.domain;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Cookie) {
         Cookie var3 = (Cookie)var1;
         if (Intrinsics.areEqual((Object)var3.name, (Object)this.name) && Intrinsics.areEqual((Object)var3.value, (Object)this.value) && var3.expiresAt == this.expiresAt && Intrinsics.areEqual((Object)var3.domain, (Object)this.domain) && Intrinsics.areEqual((Object)var3.path, (Object)this.path) && var3.secure == this.secure && var3.httpOnly == this.httpOnly && var3.persistent == this.persistent && var3.hostOnly == this.hostOnly) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
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

   public final boolean matches(HttpUrl var1) {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      boolean var2;
      if (this.hostOnly) {
         var2 = Intrinsics.areEqual((Object)var1.host(), (Object)this.domain);
      } else {
         var2 = Companion.domainMatch(var1.host(), this.domain);
      }

      boolean var3 = false;
      if (!var2) {
         return false;
      } else if (!Companion.pathMatch(var1, this.path)) {
         return false;
      } else {
         if (this.secure) {
            var2 = var3;
            if (!var1.isHttps()) {
               return var2;
            }
         }

         var2 = true;
         return var2;
      }
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

   public final String toString$okhttp(boolean var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.name);
      var2.append('=');
      var2.append(this.value);
      if (this.persistent) {
         if (this.expiresAt == Long.MIN_VALUE) {
            var2.append("; max-age=0");
         } else {
            var2.append("; expires=");
            var2.append(DatesKt.toHttpDateString(new Date(this.expiresAt)));
         }
      }

      if (!this.hostOnly) {
         var2.append("; domain=");
         if (var1) {
            var2.append(".");
         }

         var2.append(this.domain);
      }

      var2.append("; path=");
      var2.append(this.path);
      if (this.secure) {
         var2.append("; secure");
      }

      if (this.httpOnly) {
         var2.append("; httponly");
      }

      String var3 = var2.toString();
      Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
      return var3;
   }

   public final String value() {
      return this.value;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0018\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\t\u001a\u00020\u0000J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004J\u0006\u0010\r\u001a\u00020\u0000J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
      d2 = {"Lokhttp3/Cookie$Builder;", "", "()V", "domain", "", "expiresAt", "", "hostOnly", "", "httpOnly", "name", "path", "persistent", "secure", "value", "build", "Lokhttp3/Cookie;", "hostOnlyDomain", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
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

      private final Cookie.Builder domain(String var1, boolean var2) {
         Cookie.Builder var3 = (Cookie.Builder)this;
         String var4 = HostnamesKt.toCanonicalHost(var1);
         if (var4 != null) {
            var3.domain = var4;
            var3.hostOnly = var2;
            return var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("unexpected domain: ");
            var5.append(var1);
            throw (Throwable)(new IllegalArgumentException(var5.toString()));
         }
      }

      public final Cookie build() {
         String var1 = this.name;
         if (var1 != null) {
            String var2 = this.value;
            if (var2 != null) {
               long var3 = this.expiresAt;
               String var5 = this.domain;
               if (var5 != null) {
                  return new Cookie(var1, var2, var3, var5, this.path, this.secure, this.httpOnly, this.persistent, this.hostOnly, (DefaultConstructorMarker)null);
               } else {
                  throw (Throwable)(new NullPointerException("builder.domain == null"));
               }
            } else {
               throw (Throwable)(new NullPointerException("builder.value == null"));
            }
         } else {
            throw (Throwable)(new NullPointerException("builder.name == null"));
         }
      }

      public final Cookie.Builder domain(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "domain");
         return this.domain(var1, false);
      }

      public final Cookie.Builder expiresAt(long var1) {
         Cookie.Builder var3 = (Cookie.Builder)this;
         long var4 = var1;
         if (var1 <= 0L) {
            var4 = Long.MIN_VALUE;
         }

         var1 = var4;
         if (var4 > 253402300799999L) {
            var1 = 253402300799999L;
         }

         var3.expiresAt = var1;
         var3.persistent = true;
         return var3;
      }

      public final Cookie.Builder hostOnlyDomain(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "domain");
         return this.domain(var1, true);
      }

      public final Cookie.Builder httpOnly() {
         Cookie.Builder var1 = (Cookie.Builder)this;
         var1.httpOnly = true;
         return var1;
      }

      public final Cookie.Builder name(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Cookie.Builder var2 = (Cookie.Builder)this;
         if (Intrinsics.areEqual((Object)StringsKt.trim((CharSequence)var1).toString(), (Object)var1)) {
            var2.name = var1;
            return var2;
         } else {
            throw (Throwable)(new IllegalArgumentException("name is not trimmed".toString()));
         }
      }

      public final Cookie.Builder path(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "path");
         Cookie.Builder var2 = (Cookie.Builder)this;
         if (StringsKt.startsWith$default(var1, "/", false, 2, (Object)null)) {
            var2.path = var1;
            return var2;
         } else {
            throw (Throwable)(new IllegalArgumentException("path must start with '/'".toString()));
         }
      }

      public final Cookie.Builder secure() {
         Cookie.Builder var1 = (Cookie.Builder)this;
         var1.secure = true;
         return var1;
      }

      public final Cookie.Builder value(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "value");
         Cookie.Builder var2 = (Cookie.Builder)this;
         if (Intrinsics.areEqual((Object)StringsKt.trim((CharSequence)var1).toString(), (Object)var1)) {
            var2.value = var1;
            return var2;
         } else {
            throw (Throwable)(new IllegalArgumentException("value is not trimmed".toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\fH\u0002J'\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0000¢\u0006\u0002\b\u001bJ\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0007J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001d2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\fH\u0002J \u0010\"\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\fH\u0002J\u0018\u0010$\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\fH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"},
      d2 = {"Lokhttp3/Cookie$Companion;", "", "()V", "DAY_OF_MONTH_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "MONTH_PATTERN", "TIME_PATTERN", "YEAR_PATTERN", "dateCharacterOffset", "", "input", "", "pos", "limit", "invert", "", "domainMatch", "urlHost", "domain", "parse", "Lokhttp3/Cookie;", "currentTimeMillis", "", "url", "Lokhttp3/HttpUrl;", "setCookie", "parse$okhttp", "parseAll", "", "headers", "Lokhttp3/Headers;", "parseDomain", "s", "parseExpires", "parseMaxAge", "pathMatch", "path", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final int dateCharacterOffset(String var1, int var2, int var3, boolean var4) {
         while(var2 < var3) {
            char var5 = var1.charAt(var2);
            boolean var6;
            if ((var5 >= ' ' || var5 == '\t') && var5 < 127 && ('0' > var5 || '9' < var5) && ('a' > var5 || 'z' < var5) && ('A' > var5 || 'Z' < var5) && var5 != ':') {
               var6 = false;
            } else {
               var6 = true;
            }

            if (var6 == (var4 ^ true)) {
               return var2;
            }

            ++var2;
         }

         return var3;
      }

      private final boolean domainMatch(String var1, String var2) {
         boolean var3 = Intrinsics.areEqual((Object)var1, (Object)var2);
         boolean var4 = true;
         if (var3) {
            return true;
         } else {
            if (!StringsKt.endsWith$default(var1, var2, false, 2, (Object)null) || var1.charAt(var1.length() - var2.length() - 1) != '.' || Util.canParseAsIpAddress(var1)) {
               var4 = false;
            }

            return var4;
         }
      }

      private final String parseDomain(String var1) {
         if (StringsKt.endsWith$default(var1, ".", false, 2, (Object)null) ^ true) {
            var1 = HostnamesKt.toCanonicalHost(StringsKt.removePrefix(var1, (CharSequence)"."));
            if (var1 != null) {
               return var1;
            } else {
               throw (Throwable)(new IllegalArgumentException());
            }
         } else {
            throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
         }
      }

      private final long parseExpires(String var1, int var2, int var3) {
         Cookie.Companion var4 = (Cookie.Companion)this;
         int var5 = var4.dateCharacterOffset(var1, var2, var3, false);
         Matcher var6 = Cookie.TIME_PATTERN.matcher((CharSequence)var1);
         var2 = -1;
         int var7 = -1;
         int var8 = -1;
         int var9 = -1;
         int var10 = -1;

         int var11;
         int var16;
         for(var11 = -1; var5 < var3; var11 = var16) {
            int var12 = var4.dateCharacterOffset(var1, var5 + 1, var3, true);
            var6.region(var5, var12);
            String var13;
            int var14;
            int var15;
            int var17;
            int var18;
            int var19;
            if (var7 == -1 && var6.usePattern(Cookie.TIME_PATTERN).matches()) {
               var13 = var6.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var13, "matcher.group(1)");
               var14 = Integer.parseInt(var13);
               var13 = var6.group(2);
               Intrinsics.checkExpressionValueIsNotNull(var13, "matcher.group(2)");
               var15 = Integer.parseInt(var13);
               var13 = var6.group(3);
               Intrinsics.checkExpressionValueIsNotNull(var13, "matcher.group(3)");
               var16 = Integer.parseInt(var13);
               var17 = var2;
               var18 = var8;
               var19 = var9;
            } else if (var8 == -1 && var6.usePattern(Cookie.DAY_OF_MONTH_PATTERN).matches()) {
               var13 = var6.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var13, "matcher.group(1)");
               var18 = Integer.parseInt(var13);
               var17 = var2;
               var14 = var7;
               var19 = var9;
               var15 = var10;
               var16 = var11;
            } else if (var9 == -1 && var6.usePattern(Cookie.MONTH_PATTERN).matches()) {
               String var20 = var6.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var20, "matcher.group(1)");
               Locale var23 = Locale.US;
               Intrinsics.checkExpressionValueIsNotNull(var23, "Locale.US");
               if (var20 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var20 = var20.toLowerCase(var23);
               Intrinsics.checkExpressionValueIsNotNull(var20, "(this as java.lang.String).toLowerCase(locale)");
               var13 = Cookie.MONTH_PATTERN.pattern();
               Intrinsics.checkExpressionValueIsNotNull(var13, "MONTH_PATTERN.pattern()");
               var19 = StringsKt.indexOf$default((CharSequence)var13, var20, 0, false, 6, (Object)null) / 4;
               var17 = var2;
               var14 = var7;
               var18 = var8;
               var15 = var10;
               var16 = var11;
            } else {
               var17 = var2;
               var14 = var7;
               var18 = var8;
               var19 = var9;
               var15 = var10;
               var16 = var11;
               if (var2 == -1) {
                  var17 = var2;
                  var14 = var7;
                  var18 = var8;
                  var19 = var9;
                  var15 = var10;
                  var16 = var11;
                  if (var6.usePattern(Cookie.YEAR_PATTERN).matches()) {
                     var13 = var6.group(1);
                     Intrinsics.checkExpressionValueIsNotNull(var13, "matcher.group(1)");
                     var17 = Integer.parseInt(var13);
                     var16 = var11;
                     var15 = var10;
                     var19 = var9;
                     var18 = var8;
                     var14 = var7;
                  }
               }
            }

            var5 = var4.dateCharacterOffset(var1, var12 + 1, var3, false);
            var2 = var17;
            var7 = var14;
            var8 = var18;
            var9 = var19;
            var10 = var15;
         }

         if (70 > var2) {
            var3 = var2;
         } else {
            var3 = var2;
            if (99 >= var2) {
               var3 = var2 + 1900;
            }
         }

         if (var3 < 0) {
            var2 = var3;
         } else {
            var2 = var3;
            if (69 >= var3) {
               var2 = var3 + 2000;
            }
         }

         boolean var22;
         if (var2 >= 1601) {
            var22 = true;
         } else {
            var22 = false;
         }

         if (var22) {
            if (var9 != -1) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (!var22) {
               throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
            } else {
               if (1 <= var8 && 31 >= var8) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (!var22) {
                  throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
               } else {
                  if (var7 >= 0 && 23 >= var7) {
                     var22 = true;
                  } else {
                     var22 = false;
                  }

                  if (!var22) {
                     throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
                  } else {
                     if (var10 >= 0 && 59 >= var10) {
                        var22 = true;
                     } else {
                        var22 = false;
                     }

                     if (!var22) {
                        throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
                     } else {
                        if (var11 >= 0 && 59 >= var11) {
                           var22 = true;
                        } else {
                           var22 = false;
                        }

                        if (var22) {
                           GregorianCalendar var21 = new GregorianCalendar(Util.UTC);
                           var21.setLenient(false);
                           var21.set(1, var2);
                           var21.set(2, var9 - 1);
                           var21.set(5, var8);
                           var21.set(11, var7);
                           var21.set(12, var10);
                           var21.set(13, var11);
                           var21.set(14, 0);
                           return var21.getTimeInMillis();
                        } else {
                           throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
                        }
                     }
                  }
               }
            }
         } else {
            throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
         }
      }

      private final long parseMaxAge(String var1) {
         long var2 = Long.MIN_VALUE;

         long var4;
         try {
            var4 = Long.parseLong(var1);
         } catch (NumberFormatException var8) {
            CharSequence var7 = (CharSequence)var1;
            if ((new Regex("-?\\d+")).matches(var7)) {
               if (!StringsKt.startsWith$default(var1, "-", false, 2, (Object)null)) {
                  var2 = Long.MAX_VALUE;
               }

               return var2;
            }

            throw (Throwable)var8;
         }

         if (var4 > 0L) {
            var2 = var4;
         }

         return var2;
      }

      private final boolean pathMatch(HttpUrl var1, String var2) {
         String var3 = var1.encodedPath();
         if (Intrinsics.areEqual((Object)var3, (Object)var2)) {
            return true;
         } else {
            if (StringsKt.startsWith$default(var3, var2, false, 2, (Object)null)) {
               if (StringsKt.endsWith$default(var2, "/", false, 2, (Object)null)) {
                  return true;
               }

               if (var3.charAt(var2.length()) == '/') {
                  return true;
               }
            }

            return false;
         }
      }

      @JvmStatic
      public final Cookie parse(HttpUrl var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         Intrinsics.checkParameterIsNotNull(var2, "setCookie");
         return ((Cookie.Companion)this).parse$okhttp(System.currentTimeMillis(), var1, var2);
      }

      public final Cookie parse$okhttp(long var1, HttpUrl var3, String var4) {
         Intrinsics.checkParameterIsNotNull(var3, "url");
         Intrinsics.checkParameterIsNotNull(var4, "setCookie");
         int var5 = Util.delimiterOffset$default(var4, ';', 0, 0, 6, (Object)null);
         int var6 = Util.delimiterOffset$default(var4, '=', 0, var5, 2, (Object)null);
         if (var6 == var5) {
            return null;
         } else {
            String var7 = Util.trimSubstring$default(var4, 0, var6, 1, (Object)null);
            boolean var8;
            if (((CharSequence)var7).length() == 0) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (!var8 && Util.indexOfControlOrNonAscii(var7) == -1) {
               String var9 = Util.trimSubstring(var4, var6 + 1, var5);
               if (Util.indexOfControlOrNonAscii(var9) != -1) {
                  return null;
               } else {
                  String var10 = (String)null;
                  int var35 = var5 + 1;
                  var5 = var4.length();
                  String var11 = var10;
                  long var12 = -1L;
                  boolean var14 = false;
                  boolean var15 = false;
                  boolean var16 = false;
                  boolean var17 = true;

                  long var18;
                  String var22;
                  long var23;
                  long var29;
                  for(var18 = 253402300799999L; var35 < var5; var18 = var29) {
                     var6 = Util.delimiterOffset(var4, ';', var35, var5);
                     int var20 = Util.delimiterOffset(var4, '=', var35, var6);
                     String var21 = Util.trimSubstring(var4, var35, var20);
                     if (var20 < var6) {
                        var22 = Util.trimSubstring(var4, var20 + 1, var6);
                     } else {
                        var22 = "";
                     }

                     boolean var25;
                     String var26;
                     boolean var27;
                     boolean var28;
                     label122: {
                        label121: {
                           boolean var10001;
                           if (StringsKt.equals(var21, "expires", true)) {
                              try {
                                 var23 = ((Cookie.Companion)this).parseExpires(var22, 0, var22.length());
                              } catch (IllegalArgumentException var33) {
                                 var10001 = false;
                                 break label121;
                              }

                              var18 = var23;
                           } else {
                              if (!StringsKt.equals(var21, "max-age", true)) {
                                 if (!StringsKt.equals(var21, "domain", true)) {
                                    if (StringsKt.equals(var21, "path", true)) {
                                       var26 = var22;
                                       var22 = var10;
                                       var27 = var14;
                                       var25 = var16;
                                       var28 = var17;
                                       var23 = var12;
                                       var29 = var18;
                                    } else if (StringsKt.equals(var21, "secure", true)) {
                                       var27 = true;
                                       var22 = var10;
                                       var26 = var11;
                                       var25 = var16;
                                       var28 = var17;
                                       var23 = var12;
                                       var29 = var18;
                                    } else {
                                       var22 = var10;
                                       var26 = var11;
                                       var27 = var14;
                                       var25 = var16;
                                       var28 = var17;
                                       var23 = var12;
                                       var29 = var18;
                                       if (StringsKt.equals(var21, "httponly", true)) {
                                          var15 = true;
                                          var29 = var18;
                                          var23 = var12;
                                          var28 = var17;
                                          var25 = var16;
                                          var27 = var14;
                                          var26 = var11;
                                          var22 = var10;
                                       }
                                    }
                                    break label122;
                                 }

                                 try {
                                    var22 = ((Cookie.Companion)this).parseDomain(var22);
                                 } catch (IllegalArgumentException var31) {
                                    var10001 = false;
                                    break label121;
                                 }

                                 var28 = false;
                                 var26 = var11;
                                 var27 = var14;
                                 var25 = var16;
                                 var23 = var12;
                                 var29 = var18;
                                 break label122;
                              }

                              try {
                                 var23 = ((Cookie.Companion)this).parseMaxAge(var22);
                              } catch (NumberFormatException var32) {
                                 var10001 = false;
                                 break label121;
                              }

                              var12 = var23;
                           }

                           var25 = true;
                           var22 = var10;
                           var26 = var11;
                           var27 = var14;
                           var28 = var17;
                           var23 = var12;
                           var29 = var18;
                           break label122;
                        }

                        var22 = var10;
                        var26 = var11;
                        var27 = var14;
                        var25 = var16;
                        var28 = var17;
                        var23 = var12;
                        var29 = var18;
                     }

                     var35 = var6 + 1;
                     var10 = var22;
                     var11 = var26;
                     var14 = var27;
                     var16 = var25;
                     var17 = var28;
                     var12 = var23;
                  }

                  var23 = Long.MIN_VALUE;
                  if (var12 == Long.MIN_VALUE) {
                     var1 = var23;
                  } else if (var12 != -1L) {
                     label139: {
                        if (var12 <= 9223372036854775L) {
                           var18 = var12 * (long)1000;
                        } else {
                           var18 = Long.MAX_VALUE;
                        }

                        var18 += var1;
                        if (var18 >= var1) {
                           var1 = var18;
                           if (var18 <= 253402300799999L) {
                              break label139;
                           }
                        }

                        var1 = 253402300799999L;
                     }
                  } else {
                     var1 = var18;
                  }

                  var22 = var3.host();
                  if (var10 == null) {
                     var4 = var22;
                  } else {
                     if (!((Cookie.Companion)this).domainMatch(var22, var10)) {
                        return null;
                     }

                     var4 = var10;
                  }

                  if (var22.length() != var4.length() && PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(var4) == null) {
                     return null;
                  } else {
                     var10 = "/";
                     String var34;
                     if (var11 != null && StringsKt.startsWith$default(var11, "/", false, 2, (Object)null)) {
                        var34 = var11;
                     } else {
                        var11 = var3.encodedPath();
                        var35 = StringsKt.lastIndexOf$default((CharSequence)var11, '/', 0, false, 6, (Object)null);
                        var34 = var10;
                        if (var35 != 0) {
                           if (var11 == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var34 = var11.substring(0, var35);
                           Intrinsics.checkExpressionValueIsNotNull(var34, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        }
                     }

                     return new Cookie(var7, var9, var1, var4, var34, var14, var15, var16, var17, (DefaultConstructorMarker)null);
                  }
               }
            } else {
               return null;
            }
         }
      }

      @JvmStatic
      public final List<Cookie> parseAll(HttpUrl var1, Headers var2) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         Intrinsics.checkParameterIsNotNull(var2, "headers");
         List var3 = var2.values("Set-Cookie");
         List var9 = (List)null;
         int var4 = var3.size();

         List var7;
         for(int var5 = 0; var5 < var4; var9 = var7) {
            Cookie var6 = ((Cookie.Companion)this).parse(var1, (String)var3.get(var5));
            var7 = var9;
            if (var6 != null) {
               var7 = var9;
               if (var9 == null) {
                  var7 = (List)(new ArrayList());
               }

               var7.add(var6);
            }

            ++var5;
         }

         List var8;
         if (var9 != null) {
            var8 = Collections.unmodifiableList(var9);
            Intrinsics.checkExpressionValueIsNotNull(var8, "Collections.unmodifiableList(cookies)");
         } else {
            var8 = CollectionsKt.emptyList();
         }

         return var8;
      }
   }
}
