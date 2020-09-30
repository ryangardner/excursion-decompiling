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

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 J2\u00020\u0001:\u0002IJBa\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eJ\u000f\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b!J\r\u0010\u0011\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\"J\r\u0010\u0012\u001a\u00020\u0003H\u0007¢\u0006\u0002\b#J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\b$J\u000f\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b%J\r\u0010\u0016\u001a\u00020\u0003H\u0007¢\u0006\u0002\b&J\u0013\u0010'\u001a\u00020\u00182\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u000f\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b)J\b\u0010*\u001a\u00020\bH\u0016J\r\u0010\u0006\u001a\u00020\u0003H\u0007¢\u0006\u0002\b+J\u0006\u0010,\u001a\u00020-J\u0010\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0005\u001a\u00020\u0003H\u0007¢\u0006\u0002\b/J\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\b0J\r\u0010\u001a\u001a\u00020\bH\u0007¢\u0006\u0002\b1J\r\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\b2J\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b3J\u0010\u00104\u001a\u0004\u0018\u00010\u00032\u0006\u00105\u001a\u00020\u0003J\u000e\u00106\u001a\u00020\u00032\u0006\u00107\u001a\u00020\bJ\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001eH\u0007¢\u0006\u0002\b8J\u0010\u00109\u001a\u0004\u0018\u00010\u00032\u0006\u00107\u001a\u00020\bJ\u0016\u0010:\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n2\u0006\u00105\u001a\u00020\u0003J\r\u0010 \u001a\u00020\bH\u0007¢\u0006\u0002\b;J\u0006\u0010<\u001a\u00020\u0003J\u0010\u0010=\u001a\u0004\u0018\u00010\u00002\u0006\u0010.\u001a\u00020\u0003J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b>J\b\u0010?\u001a\u00020\u0003H\u0016J\r\u0010@\u001a\u00020AH\u0007¢\u0006\u0002\bBJ\r\u0010C\u001a\u00020DH\u0007¢\u0006\u0002\b\rJ\b\u0010E\u001a\u0004\u0018\u00010\u0003J\r\u0010B\u001a\u00020AH\u0007¢\u0006\u0002\bFJ\r\u0010\r\u001a\u00020DH\u0007¢\u0006\u0002\bGJ\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\bHR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0012\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\n8G¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0016\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u0015\u0010\f\u001a\u0004\u0018\u00010\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0010R\u0013\u0010\u0006\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u0013\u0010\u0005\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0014R\u0011\u0010\u001a\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0007\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001bR\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u001e8G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0011\u0010 \u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b \u0010\u001bR\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0010R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010¨\u0006K"},
   d2 = {"Lokhttp3/HttpUrl;", "", "scheme", "", "username", "password", "host", "port", "", "pathSegments", "", "queryNamesAndValues", "fragment", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "encodedFragment", "()Ljava/lang/String;", "encodedPassword", "encodedPath", "encodedPathSegments", "()Ljava/util/List;", "encodedQuery", "encodedUsername", "isHttps", "", "()Z", "pathSize", "()I", "query", "queryParameterNames", "", "()Ljava/util/Set;", "querySize", "-deprecated_encodedFragment", "-deprecated_encodedPassword", "-deprecated_encodedPath", "-deprecated_encodedPathSegments", "-deprecated_encodedQuery", "-deprecated_encodedUsername", "equals", "other", "-deprecated_fragment", "hashCode", "-deprecated_host", "newBuilder", "Lokhttp3/HttpUrl$Builder;", "link", "-deprecated_password", "-deprecated_pathSegments", "-deprecated_pathSize", "-deprecated_port", "-deprecated_query", "queryParameter", "name", "queryParameterName", "index", "-deprecated_queryParameterNames", "queryParameterValue", "queryParameterValues", "-deprecated_querySize", "redact", "resolve", "-deprecated_scheme", "toString", "toUri", "Ljava/net/URI;", "uri", "toUrl", "Ljava/net/URL;", "topPrivateDomain", "-deprecated_uri", "-deprecated_url", "-deprecated_username", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class HttpUrl {
   public static final HttpUrl.Companion Companion = new HttpUrl.Companion((DefaultConstructorMarker)null);
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

   public HttpUrl(String var1, String var2, String var3, String var4, int var5, List<String> var6, List<String> var7, String var8, String var9) {
      Intrinsics.checkParameterIsNotNull(var1, "scheme");
      Intrinsics.checkParameterIsNotNull(var2, "username");
      Intrinsics.checkParameterIsNotNull(var3, "password");
      Intrinsics.checkParameterIsNotNull(var4, "host");
      Intrinsics.checkParameterIsNotNull(var6, "pathSegments");
      Intrinsics.checkParameterIsNotNull(var9, "url");
      super();
      this.scheme = var1;
      this.username = var2;
      this.password = var3;
      this.host = var4;
      this.port = var5;
      this.pathSegments = var6;
      this.queryNamesAndValues = var7;
      this.fragment = var8;
      this.url = var9;
      this.isHttps = Intrinsics.areEqual((Object)var1, (Object)"https");
   }

   @JvmStatic
   public static final int defaultPort(String var0) {
      return Companion.defaultPort(var0);
   }

   @JvmStatic
   public static final HttpUrl get(String var0) {
      return Companion.get(var0);
   }

   @JvmStatic
   public static final HttpUrl get(URI var0) {
      return Companion.get(var0);
   }

   @JvmStatic
   public static final HttpUrl get(URL var0) {
      return Companion.get(var0);
   }

   @JvmStatic
   public static final HttpUrl parse(String var0) {
      return Companion.parse(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedFragment",
   imports = {}
)
   )
   public final String _deprecated_encodedFragment/* $FF was: -deprecated_encodedFragment*/() {
      return this.encodedFragment();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedPassword",
   imports = {}
)
   )
   public final String _deprecated_encodedPassword/* $FF was: -deprecated_encodedPassword*/() {
      return this.encodedPassword();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedPath",
   imports = {}
)
   )
   public final String _deprecated_encodedPath/* $FF was: -deprecated_encodedPath*/() {
      return this.encodedPath();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedPathSegments",
   imports = {}
)
   )
   public final List<String> _deprecated_encodedPathSegments/* $FF was: -deprecated_encodedPathSegments*/() {
      return this.encodedPathSegments();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedQuery",
   imports = {}
)
   )
   public final String _deprecated_encodedQuery/* $FF was: -deprecated_encodedQuery*/() {
      return this.encodedQuery();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "encodedUsername",
   imports = {}
)
   )
   public final String _deprecated_encodedUsername/* $FF was: -deprecated_encodedUsername*/() {
      return this.encodedUsername();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "fragment",
   imports = {}
)
   )
   public final String _deprecated_fragment/* $FF was: -deprecated_fragment*/() {
      return this.fragment;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "host",
   imports = {}
)
   )
   public final String _deprecated_host/* $FF was: -deprecated_host*/() {
      return this.host;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "password",
   imports = {}
)
   )
   public final String _deprecated_password/* $FF was: -deprecated_password*/() {
      return this.password;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "pathSegments",
   imports = {}
)
   )
   public final List<String> _deprecated_pathSegments/* $FF was: -deprecated_pathSegments*/() {
      return this.pathSegments;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "pathSize",
   imports = {}
)
   )
   public final int _deprecated_pathSize/* $FF was: -deprecated_pathSize*/() {
      return this.pathSize();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "port",
   imports = {}
)
   )
   public final int _deprecated_port/* $FF was: -deprecated_port*/() {
      return this.port;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "query",
   imports = {}
)
   )
   public final String _deprecated_query/* $FF was: -deprecated_query*/() {
      return this.query();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "queryParameterNames",
   imports = {}
)
   )
   public final Set<String> _deprecated_queryParameterNames/* $FF was: -deprecated_queryParameterNames*/() {
      return this.queryParameterNames();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "querySize",
   imports = {}
)
   )
   public final int _deprecated_querySize/* $FF was: -deprecated_querySize*/() {
      return this.querySize();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "scheme",
   imports = {}
)
   )
   public final String _deprecated_scheme/* $FF was: -deprecated_scheme*/() {
      return this.scheme;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to toUri()",
      replaceWith = @ReplaceWith(
   expression = "toUri()",
   imports = {}
)
   )
   public final URI _deprecated_uri/* $FF was: -deprecated_uri*/() {
      return this.uri();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to toUrl()",
      replaceWith = @ReplaceWith(
   expression = "toUrl()",
   imports = {}
)
   )
   public final URL _deprecated_url/* $FF was: -deprecated_url*/() {
      return this.url();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "username",
   imports = {}
)
   )
   public final String _deprecated_username/* $FF was: -deprecated_username*/() {
      return this.username;
   }

   public final String encodedFragment() {
      if (this.fragment == null) {
         return null;
      } else {
         int var1 = StringsKt.indexOf$default((CharSequence)this.url, '#', 0, false, 6, (Object)null);
         String var2 = this.url;
         if (var2 != null) {
            var2 = var2.substring(var1 + 1);
            Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).substring(startIndex)");
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }
   }

   public final String encodedPassword() {
      boolean var1;
      if (((CharSequence)this.password).length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return "";
      } else {
         int var4 = StringsKt.indexOf$default((CharSequence)this.url, ':', this.scheme.length() + 3, false, 4, (Object)null);
         int var2 = StringsKt.indexOf$default((CharSequence)this.url, '@', 0, false, 6, (Object)null);
         String var3 = this.url;
         if (var3 != null) {
            var3 = var3.substring(var4 + 1, var2);
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var3;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }
   }

   public final String encodedPath() {
      int var1 = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, (Object)null);
      String var2 = this.url;
      int var3 = Util.delimiterOffset(var2, "?#", var1, var2.length());
      var2 = this.url;
      if (var2 != null) {
         var2 = var2.substring(var1, var3);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var2;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public final List<String> encodedPathSegments() {
      int var1 = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, (Object)null);
      String var2 = this.url;
      int var3 = Util.delimiterOffset(var2, "?#", var1, var2.length());
      List var6 = (List)(new ArrayList());

      while(var1 < var3) {
         int var4 = var1 + 1;
         var1 = Util.delimiterOffset(this.url, '/', var4, var3);
         String var5 = this.url;
         if (var5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var5 = var5.substring(var4, var1);
         Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         var6.add(var5);
      }

      return var6;
   }

   public final String encodedQuery() {
      if (this.queryNamesAndValues == null) {
         return null;
      } else {
         int var1 = StringsKt.indexOf$default((CharSequence)this.url, '?', 0, false, 6, (Object)null) + 1;
         String var2 = this.url;
         int var3 = Util.delimiterOffset(var2, '#', var1, var2.length());
         var2 = this.url;
         if (var2 != null) {
            var2 = var2.substring(var1, var3);
            Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }
   }

   public final String encodedUsername() {
      boolean var1;
      if (((CharSequence)this.username).length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return "";
      } else {
         int var4 = this.scheme.length() + 3;
         String var2 = this.url;
         int var3 = Util.delimiterOffset(var2, ":@", var4, var2.length());
         var2 = this.url;
         if (var2 != null) {
            var2 = var2.substring(var4, var3);
            Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof HttpUrl && Intrinsics.areEqual((Object)((HttpUrl)var1).url, (Object)this.url)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
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

   public final HttpUrl.Builder newBuilder() {
      HttpUrl.Builder var1 = new HttpUrl.Builder();
      var1.setScheme$okhttp(this.scheme);
      var1.setEncodedUsername$okhttp(this.encodedUsername());
      var1.setEncodedPassword$okhttp(this.encodedPassword());
      var1.setHost$okhttp(this.host);
      int var2;
      if (this.port != Companion.defaultPort(this.scheme)) {
         var2 = this.port;
      } else {
         var2 = -1;
      }

      var1.setPort$okhttp(var2);
      var1.getEncodedPathSegments$okhttp().clear();
      var1.getEncodedPathSegments$okhttp().addAll((Collection)this.encodedPathSegments());
      var1.encodedQuery(this.encodedQuery());
      var1.setEncodedFragment$okhttp(this.encodedFragment());
      return var1;
   }

   public final HttpUrl.Builder newBuilder(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "link");

      HttpUrl.Builder var4;
      try {
         HttpUrl.Builder var2 = new HttpUrl.Builder();
         var4 = var2.parse$okhttp(this, var1);
      } catch (IllegalArgumentException var3) {
         var4 = null;
      }

      return var4;
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
      } else {
         StringBuilder var1 = new StringBuilder();
         Companion.toQueryString$okhttp(this.queryNamesAndValues, var1);
         return var1.toString();
      }
   }

   public final String queryParameter(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      List var2 = this.queryNamesAndValues;
      if (var2 == null) {
         return null;
      } else {
         IntProgression var6 = RangesKt.step((IntProgression)RangesKt.until(0, var2.size()), 2);
         int var3 = var6.getFirst();
         int var4 = var6.getLast();
         int var5 = var6.getStep();
         if (var5 >= 0) {
            if (var3 > var4) {
               return null;
            }
         } else if (var3 < var4) {
            return null;
         }

         while(!Intrinsics.areEqual((Object)var1, (Object)((String)this.queryNamesAndValues.get(var3)))) {
            if (var3 == var4) {
               return null;
            }

            var3 += var5;
         }

         return (String)this.queryNamesAndValues.get(var3 + 1);
      }
   }

   public final String queryParameterName(int var1) {
      List var2 = this.queryNamesAndValues;
      if (var2 != null) {
         Object var3 = var2.get(var1 * 2);
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         return (String)var3;
      } else {
         throw (Throwable)(new IndexOutOfBoundsException());
      }
   }

   public final Set<String> queryParameterNames() {
      if (this.queryNamesAndValues == null) {
         return SetsKt.emptySet();
      } else {
         LinkedHashSet var1;
         label25: {
            var1 = new LinkedHashSet();
            IntProgression var2 = RangesKt.step((IntProgression)RangesKt.until(0, this.queryNamesAndValues.size()), 2);
            int var3 = var2.getFirst();
            int var4 = var2.getLast();
            int var5 = var2.getStep();
            if (var5 >= 0) {
               if (var3 > var4) {
                  break label25;
               }
            } else if (var3 < var4) {
               break label25;
            }

            while(true) {
               Object var7 = this.queryNamesAndValues.get(var3);
               if (var7 == null) {
                  Intrinsics.throwNpe();
               }

               var1.add(var7);
               if (var3 == var4) {
                  break;
               }

               var3 += var5;
            }
         }

         Set var6 = Collections.unmodifiableSet((Set)var1);
         Intrinsics.checkExpressionValueIsNotNull(var6, "Collections.unmodifiableSet(result)");
         return var6;
      }
   }

   public final String queryParameterValue(int var1) {
      List var2 = this.queryNamesAndValues;
      if (var2 != null) {
         return (String)var2.get(var1 * 2 + 1);
      } else {
         throw (Throwable)(new IndexOutOfBoundsException());
      }
   }

   public final List<String> queryParameterValues(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      if (this.queryNamesAndValues == null) {
         return CollectionsKt.emptyList();
      } else {
         List var2;
         label25: {
            var2 = (List)(new ArrayList());
            IntProgression var3 = RangesKt.step((IntProgression)RangesKt.until(0, this.queryNamesAndValues.size()), 2);
            int var4 = var3.getFirst();
            int var5 = var3.getLast();
            int var6 = var3.getStep();
            if (var6 >= 0) {
               if (var4 > var5) {
                  break label25;
               }
            } else if (var4 < var5) {
               break label25;
            }

            while(true) {
               if (Intrinsics.areEqual((Object)var1, (Object)((String)this.queryNamesAndValues.get(var4)))) {
                  var2.add(this.queryNamesAndValues.get(var4 + 1));
               }

               if (var4 == var5) {
                  break;
               }

               var4 += var6;
            }
         }

         List var7 = Collections.unmodifiableList(var2);
         Intrinsics.checkExpressionValueIsNotNull(var7, "Collections.unmodifiableList(result)");
         return var7;
      }
   }

   public final int querySize() {
      List var1 = this.queryNamesAndValues;
      int var2;
      if (var1 != null) {
         var2 = var1.size() / 2;
      } else {
         var2 = 0;
      }

      return var2;
   }

   public final String redact() {
      HttpUrl.Builder var1 = this.newBuilder("/...");
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1.username("").password("").build().toString();
   }

   public final HttpUrl resolve(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "link");
      HttpUrl.Builder var2 = this.newBuilder(var1);
      HttpUrl var3;
      if (var2 != null) {
         var3 = var2.build();
      } else {
         var3 = null;
      }

      return var3;
   }

   public final String scheme() {
      return this.scheme;
   }

   public String toString() {
      return this.url;
   }

   public final String topPrivateDomain() {
      String var1;
      if (Util.canParseAsIpAddress(this.host)) {
         var1 = null;
      } else {
         var1 = PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(this.host);
      }

      return var1;
   }

   public final URI uri() {
      String var1 = this.newBuilder().reencodeForUri$okhttp().toString();

      URI var7;
      try {
         var7 = new URI(var1);
      } catch (URISyntaxException var5) {
         try {
            CharSequence var2 = (CharSequence)var1;
            Regex var6 = new Regex("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]");
            var7 = URI.create(var6.replace(var2, ""));
            Intrinsics.checkExpressionValueIsNotNull(var7, "URI.create(stripped)");
         } catch (Exception var4) {
            throw (Throwable)(new RuntimeException((Throwable)var5));
         }
      }

      return var7;
   }

   public final URL url() {
      try {
         URL var1 = new URL(this.url);
         return var1;
      } catch (MalformedURLException var2) {
         throw (Throwable)(new RuntimeException((Throwable)var2));
      }
   }

   public final String username() {
      return this.username;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0017\u0018\u0000 V2\u00020\u0001:\u0001VB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0004J\u0018\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0004J\u0018\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\u0006\u00102\u001a\u000203J\b\u00104\u001a\u00020\u001bH\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0004J\u000e\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u0004J\u0010\u00106\u001a\u00020\u00002\b\u00106\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u00107\u001a\u00020\u00002\b\u00107\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0004J\u0010\u00108\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u0010\u0010:\u001a\u00020.2\u0006\u00109\u001a\u00020\u0004H\u0002J\u001f\u0010;\u001a\u00020\u00002\b\u0010<\u001a\u0004\u0018\u0001032\u0006\u00109\u001a\u00020\u0004H\u0000¢\u0006\u0002\b=J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\u0004J\b\u0010?\u001a\u00020@H\u0002J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ0\u0010A\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010B\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001b2\u0006\u0010D\u001a\u00020.2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010E\u001a\u00020\u00002\b\u0010E\u001a\u0004\u0018\u00010\u0004J\r\u0010F\u001a\u00020\u0000H\u0000¢\u0006\u0002\bGJ\u0010\u0010H\u001a\u00020@2\u0006\u0010I\u001a\u00020\u0004H\u0002J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u0004J\u000e\u0010K\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0004J\u000e\u0010L\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001bJ \u0010N\u001a\u00020@2\u0006\u00109\u001a\u00020\u00042\u0006\u0010O\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020\u001bH\u0002J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u0004J\u0016\u0010P\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u0004J\u0018\u0010Q\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u0004J\u0016\u0010R\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0004J\u0018\u0010S\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00042\b\u00101\u001a\u0004\u0018\u00010\u0004J\b\u0010T\u001a\u00020\u0004H\u0016J\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u001bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\b¨\u0006W"},
      d2 = {"Lokhttp3/HttpUrl$Builder;", "", "()V", "encodedFragment", "", "getEncodedFragment$okhttp", "()Ljava/lang/String;", "setEncodedFragment$okhttp", "(Ljava/lang/String;)V", "encodedPassword", "getEncodedPassword$okhttp", "setEncodedPassword$okhttp", "encodedPathSegments", "", "getEncodedPathSegments$okhttp", "()Ljava/util/List;", "encodedQueryNamesAndValues", "getEncodedQueryNamesAndValues$okhttp", "setEncodedQueryNamesAndValues$okhttp", "(Ljava/util/List;)V", "encodedUsername", "getEncodedUsername$okhttp", "setEncodedUsername$okhttp", "host", "getHost$okhttp", "setHost$okhttp", "port", "", "getPort$okhttp", "()I", "setPort$okhttp", "(I)V", "scheme", "getScheme$okhttp", "setScheme$okhttp", "addEncodedPathSegment", "encodedPathSegment", "addEncodedPathSegments", "addEncodedQueryParameter", "encodedName", "encodedValue", "addPathSegment", "pathSegment", "addPathSegments", "pathSegments", "alreadyEncoded", "", "addQueryParameter", "name", "value", "build", "Lokhttp3/HttpUrl;", "effectivePort", "encodedPath", "encodedQuery", "fragment", "isDot", "input", "isDotDot", "parse", "base", "parse$okhttp", "password", "pop", "", "push", "pos", "limit", "addTrailingSlash", "query", "reencodeForUri", "reencodeForUri$okhttp", "removeAllCanonicalQueryParameters", "canonicalName", "removeAllEncodedQueryParameters", "removeAllQueryParameters", "removePathSegment", "index", "resolvePath", "startPos", "setEncodedPathSegment", "setEncodedQueryParameter", "setPathSegment", "setQueryParameter", "toString", "username", "Companion", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      public static final HttpUrl.Builder.Companion Companion = new HttpUrl.Builder.Companion((DefaultConstructorMarker)null);
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
         List var1 = (List)(new ArrayList());
         this.encodedPathSegments = var1;
         var1.add("");
      }

      private final HttpUrl.Builder addPathSegments(String var1, boolean var2) {
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         int var4 = 0;

         int var5;
         do {
            var5 = Util.delimiterOffset(var1, "/\\", var4, var1.length());
            boolean var6;
            if (var5 < var1.length()) {
               var6 = true;
            } else {
               var6 = false;
            }

            var3.push(var1, var4, var5, var6, var2);
            ++var5;
            var4 = var5;
         } while(var5 <= var1.length());

         return var3;
      }

      private final int effectivePort() {
         int var1 = this.port;
         if (var1 == -1) {
            HttpUrl.Companion var2 = HttpUrl.Companion;
            String var3 = this.scheme;
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var1 = var2.defaultPort(var3);
         }

         return var1;
      }

      private final boolean isDot(String var1) {
         boolean var2 = Intrinsics.areEqual((Object)var1, (Object)".");
         boolean var3 = true;
         boolean var4 = var3;
         if (!var2) {
            if (StringsKt.equals(var1, "%2e", true)) {
               var4 = var3;
            } else {
               var4 = false;
            }
         }

         return var4;
      }

      private final boolean isDotDot(String var1) {
         boolean var2 = Intrinsics.areEqual((Object)var1, (Object)"..");
         boolean var3 = true;
         boolean var4 = var3;
         if (!var2) {
            var4 = var3;
            if (!StringsKt.equals(var1, "%2e.", true)) {
               var4 = var3;
               if (!StringsKt.equals(var1, ".%2e", true)) {
                  if (StringsKt.equals(var1, "%2e%2e", true)) {
                     var4 = var3;
                  } else {
                     var4 = false;
                  }
               }
            }
         }

         return var4;
      }

      private final void pop() {
         List var1 = this.encodedPathSegments;
         boolean var2;
         if (((CharSequence)((String)var1.remove(var1.size() - 1))).length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2 && ((Collection)this.encodedPathSegments).isEmpty() ^ true) {
            var1 = this.encodedPathSegments;
            var1.set(var1.size() - 1, "");
         } else {
            this.encodedPathSegments.add("");
         }

      }

      private final void push(String var1, int var2, int var3, boolean var4, boolean var5) {
         var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, var2, var3, " \"<>^`{}|/\\?#", var5, false, false, false, (Charset)null, 240, (Object)null);
         if (!this.isDot(var1)) {
            if (this.isDotDot(var1)) {
               this.pop();
            } else {
               List var6 = this.encodedPathSegments;
               boolean var7;
               if (((CharSequence)var6.get(var6.size() - 1)).length() == 0) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (var7) {
                  var6 = this.encodedPathSegments;
                  var6.set(var6.size() - 1, var1);
               } else {
                  this.encodedPathSegments.add(var1);
               }

               if (var4) {
                  this.encodedPathSegments.add("");
               }

            }
         }
      }

      private final void removeAllCanonicalQueryParameters(String var1) {
         List var2 = this.encodedQueryNamesAndValues;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         IntProgression var6 = RangesKt.step(RangesKt.downTo(var2.size() - 2, 0), 2);
         int var3 = var6.getFirst();
         int var4 = var6.getLast();
         int var5 = var6.getStep();
         if (var5 >= 0) {
            if (var3 > var4) {
               return;
            }
         } else if (var3 < var4) {
            return;
         }

         while(true) {
            var2 = this.encodedQueryNamesAndValues;
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            if (Intrinsics.areEqual((Object)var1, (Object)((String)var2.get(var3)))) {
               var2 = this.encodedQueryNamesAndValues;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               var2.remove(var3 + 1);
               var2 = this.encodedQueryNamesAndValues;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               var2.remove(var3);
               var2 = this.encodedQueryNamesAndValues;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               if (var2.isEmpty()) {
                  this.encodedQueryNamesAndValues = (List)null;
                  return;
               }
            }

            if (var3 == var4) {
               return;
            }

            var3 += var5;
         }
      }

      private final void resolvePath(String var1, int var2, int var3) {
         if (var2 != var3) {
            char var4 = var1.charAt(var2);
            if (var4 != '/' && var4 != '\\') {
               List var5 = this.encodedPathSegments;
               var5.set(var5.size() - 1, "");
            } else {
               this.encodedPathSegments.clear();
               this.encodedPathSegments.add("");
               ++var2;
            }

            while(true) {
               while(var2 < var3) {
                  int var7 = Util.delimiterOffset(var1, "/\\", var2, var3);
                  boolean var6;
                  if (var7 < var3) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  this.push(var1, var2, var7, var6, true);
                  var2 = var7;
                  if (var6) {
                     var2 = var7 + 1;
                  }
               }

               return;
            }
         }
      }

      public final HttpUrl.Builder addEncodedPathSegment(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPathSegment");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.push(var1, 0, var1.length(), false, true);
         return var2;
      }

      public final HttpUrl.Builder addEncodedPathSegments(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPathSegments");
         return this.addPathSegments(var1, true);
      }

      public final HttpUrl.Builder addEncodedQueryParameter(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         if (var3.encodedQueryNamesAndValues == null) {
            var3.encodedQueryNamesAndValues = (List)(new ArrayList());
         }

         List var4 = var3.encodedQueryNamesAndValues;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         var4.add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#&=", true, false, true, false, (Charset)null, 211, (Object)null));
         var4 = var3.encodedQueryNamesAndValues;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         if (var2 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"'<>#&=", true, false, true, false, (Charset)null, 211, (Object)null);
         } else {
            var1 = null;
         }

         var4.add(var1);
         return var3;
      }

      public final HttpUrl.Builder addPathSegment(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "pathSegment");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.push(var1, 0, var1.length(), false, false);
         return var2;
      }

      public final HttpUrl.Builder addPathSegments(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "pathSegments");
         return this.addPathSegments(var1, false);
      }

      public final HttpUrl.Builder addQueryParameter(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         if (var3.encodedQueryNamesAndValues == null) {
            var3.encodedQueryNamesAndValues = (List)(new ArrayList());
         }

         List var4 = var3.encodedQueryNamesAndValues;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         var4.add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, (Charset)null, 219, (Object)null));
         var4 = var3.encodedQueryNamesAndValues;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         if (var2 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, (Charset)null, 219, (Object)null);
         } else {
            var1 = null;
         }

         var4.add(var1);
         return var3;
      }

      public final HttpUrl build() {
         String var1 = this.scheme;
         if (var1 == null) {
            throw (Throwable)(new IllegalStateException("scheme == null"));
         } else {
            String var2 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedUsername, 0, 0, false, 7, (Object)null);
            String var3 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedPassword, 0, 0, false, 7, (Object)null);
            String var4 = this.host;
            if (var4 == null) {
               throw (Throwable)(new IllegalStateException("host == null"));
            } else {
               int var5 = this.effectivePort();
               Iterable var6 = (Iterable)this.encodedPathSegments;
               Collection var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10)));
               Iterator var8 = var6.iterator();

               String var11;
               while(var8.hasNext()) {
                  var11 = (String)var8.next();
                  var7.add(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var11, 0, 0, false, 7, (Object)null));
               }

               List var15 = (List)var7;
               List var12 = this.encodedQueryNamesAndValues;
               var11 = null;
               if (var12 != null) {
                  Iterable var13 = (Iterable)var12;
                  Collection var9 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10)));

                  String var14;
                  for(Iterator var10 = var13.iterator(); var10.hasNext(); var9.add(var14)) {
                     var14 = (String)var10.next();
                     if (var14 != null) {
                        var14 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var14, 0, 0, true, 3, (Object)null);
                     } else {
                        var14 = null;
                     }
                  }

                  var12 = (List)var9;
               } else {
                  var12 = null;
               }

               String var16 = this.encodedFragment;
               if (var16 != null) {
                  var11 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var16, 0, 0, false, 7, (Object)null);
               }

               return new HttpUrl(var1, var2, var3, var4, var5, var15, var12, var11, this.toString());
            }
         }
      }

      public final HttpUrl.Builder encodedFragment(String var1) {
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (var1 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, "", true, false, false, true, (Charset)null, 179, (Object)null);
         } else {
            var1 = null;
         }

         var2.encodedFragment = var1;
         return var2;
      }

      public final HttpUrl.Builder encodedPassword(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPassword");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset)null, 243, (Object)null);
         return var2;
      }

      public final HttpUrl.Builder encodedPath(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPath");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (StringsKt.startsWith$default(var1, "/", false, 2, (Object)null)) {
            var2.resolvePath(var1, 0, var1.length());
            return var2;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("unexpected encodedPath: ");
            var3.append(var1);
            throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
         }
      }

      public final HttpUrl.Builder encodedQuery(String var1) {
         HttpUrl.Builder var2;
         List var3;
         label12: {
            var2 = (HttpUrl.Builder)this;
            if (var1 != null) {
               var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#", true, false, true, false, (Charset)null, 211, (Object)null);
               if (var1 != null) {
                  var3 = HttpUrl.Companion.toQueryNamesAndValues$okhttp(var1);
                  break label12;
               }
            }

            var3 = null;
         }

         var2.encodedQueryNamesAndValues = var3;
         return var2;
      }

      public final HttpUrl.Builder encodedUsername(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedUsername");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.encodedUsername = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset)null, 243, (Object)null);
         return var2;
      }

      public final HttpUrl.Builder fragment(String var1) {
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (var1 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, "", false, false, false, true, (Charset)null, 187, (Object)null);
         } else {
            var1 = null;
         }

         var2.encodedFragment = var1;
         return var2;
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

      public final HttpUrl.Builder host(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "host");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         String var3 = HostnamesKt.toCanonicalHost(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var1, 0, 0, false, 7, (Object)null));
         if (var3 != null) {
            var2.host = var3;
            return var2;
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("unexpected host: ");
            var4.append(var1);
            throw (Throwable)(new IllegalArgumentException(var4.toString()));
         }
      }

      public final HttpUrl.Builder parse$okhttp(HttpUrl var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var2, "input");
         int var3 = Util.indexOfFirstNonAsciiWhitespace$default(var2, 0, 0, 3, (Object)null);
         int var4 = Util.indexOfLastNonAsciiWhitespace$default(var2, var3, 0, 2, (Object)null);
         int var5 = Companion.schemeDelimiterOffset(var2, var3, var4);
         String var6 = "(this as java.lang.Strin…ing(startIndex, endIndex)";
         if (var5 != -1) {
            if (StringsKt.startsWith(var2, "https:", var3, true)) {
               this.scheme = "https";
               var3 += 6;
            } else {
               if (!StringsKt.startsWith(var2, "http:", var3, true)) {
                  StringBuilder var11 = new StringBuilder();
                  var11.append("Expected URL scheme 'http' or 'https' but was '");
                  var2 = var2.substring(0, var5);
                  Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  var11.append(var2);
                  var11.append("'");
                  throw (Throwable)(new IllegalArgumentException(var11.toString()));
               }

               this.scheme = "http";
               var3 += 5;
            }
         } else {
            if (var1 == null) {
               throw (Throwable)(new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found"));
            }

            this.scheme = var1.scheme();
         }

         var5 = Companion.slashCount(var2, var3, var4);
         if (var5 < 2 && var1 != null && !(Intrinsics.areEqual((Object)var1.scheme(), (Object)this.scheme) ^ true)) {
            this.encodedUsername = var1.encodedUsername();
            this.encodedPassword = var1.encodedPassword();
            this.host = var1.host();
            this.port = var1.port();
            this.encodedPathSegments.clear();
            this.encodedPathSegments.addAll((Collection)var1.encodedPathSegments());
            if (var3 == var4 || var2.charAt(var3) == '#') {
               this.encodedQuery(var1.encodedQuery());
            }
         } else {
            int var7 = var3 + var5;
            boolean var13 = false;
            boolean var14 = false;
            String var12 = var6;

            while(true) {
               int var8 = Util.delimiterOffset(var2, "@/\\?#", var7, var4);
               int var9;
               if (var8 != var4) {
                  var9 = var2.charAt(var8);
               } else {
                  var9 = -1;
               }

               StringBuilder var15;
               if (var9 == -1 || var9 == 35 || var9 == 47 || var9 == 92 || var9 == 63) {
                  var5 = Companion.portColonOffset(var2, var7, var8);
                  var9 = var5 + 1;
                  if (var9 < var8) {
                     this.host = HostnamesKt.toCanonicalHost(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var2, var7, var5, false, 4, (Object)null));
                     var3 = Companion.parsePort(var2, var9, var8);
                     this.port = var3;
                     if (var3 != -1) {
                        var13 = true;
                     } else {
                        var13 = false;
                     }

                     if (!var13) {
                        var15 = new StringBuilder();
                        var15.append("Invalid URL port: \"");
                        var2 = var2.substring(var9, var8);
                        Intrinsics.checkExpressionValueIsNotNull(var2, var12);
                        var15.append(var2);
                        var15.append('"');
                        throw (Throwable)(new IllegalArgumentException(var15.toString().toString()));
                     }
                  } else {
                     this.host = HostnamesKt.toCanonicalHost(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var2, var7, var5, false, 4, (Object)null));
                     HttpUrl.Companion var16 = HttpUrl.Companion;
                     var6 = this.scheme;
                     if (var6 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.port = var16.defaultPort(var6);
                  }

                  if (this.host != null) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  if (!var13) {
                     var15 = new StringBuilder();
                     var15.append("Invalid URL host: \"");
                     var2 = var2.substring(var7, var5);
                     Intrinsics.checkExpressionValueIsNotNull(var2, var12);
                     var15.append(var2);
                     var15.append('"');
                     throw (Throwable)(new IllegalArgumentException(var15.toString().toString()));
                  }

                  var3 = var8;
                  break;
               }

               if (var9 == 64) {
                  if (!var13) {
                     var9 = Util.delimiterOffset(var2, ':', var7, var8);
                     String var10 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, var7, var9, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset)null, 240, (Object)null);
                     var6 = var10;
                     if (var14) {
                        var15 = new StringBuilder();
                        var15.append(this.encodedUsername);
                        var15.append("%40");
                        var15.append(var10);
                        var6 = var15.toString();
                     }

                     this.encodedUsername = var6;
                     if (var9 != var8) {
                        this.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, var9 + 1, var8, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset)null, 240, (Object)null);
                        var13 = true;
                     }

                     var14 = true;
                  } else {
                     var15 = new StringBuilder();
                     var15.append(this.encodedPassword);
                     var15.append("%40");
                     var15.append(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, var7, var8, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, (Charset)null, 240, (Object)null));
                     this.encodedPassword = var15.toString();
                  }

                  var7 = var8 + 1;
               }
            }
         }

         var5 = Util.delimiterOffset(var2, "?#", var3, var4);
         this.resolvePath(var2, var3, var5);
         var3 = var5;
         if (var5 < var4) {
            var3 = var5;
            if (var2.charAt(var5) == '?') {
               var3 = Util.delimiterOffset(var2, '#', var5, var4);
               this.encodedQueryNamesAndValues = HttpUrl.Companion.toQueryNamesAndValues$okhttp(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, var5 + 1, var3, " \"'<>#", true, false, true, false, (Charset)null, 208, (Object)null));
            }
         }

         if (var3 < var4 && var2.charAt(var3) == '#') {
            this.encodedFragment = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, var3 + 1, var4, "", true, false, false, true, (Charset)null, 176, (Object)null);
         }

         return this;
      }

      public final HttpUrl.Builder password(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "password");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, (Charset)null, 251, (Object)null);
         return var2;
      }

      public final HttpUrl.Builder port(int var1) {
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         boolean var3 = true;
         if (1 > var1 || 65535 < var1) {
            var3 = false;
         }

         if (var3) {
            var2.port = var1;
            return var2;
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("unexpected port: ");
            var4.append(var1);
            throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
         }
      }

      public final HttpUrl.Builder query(String var1) {
         HttpUrl.Builder var2;
         List var3;
         label12: {
            var2 = (HttpUrl.Builder)this;
            if (var1 != null) {
               var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#", false, false, true, false, (Charset)null, 219, (Object)null);
               if (var1 != null) {
                  var3 = HttpUrl.Companion.toQueryNamesAndValues$okhttp(var1);
                  break label12;
               }
            }

            var3 = null;
         }

         var2.encodedQueryNamesAndValues = var3;
         return var2;
      }

      public final HttpUrl.Builder reencodeForUri$okhttp() {
         HttpUrl.Builder var1 = (HttpUrl.Builder)this;
         String var2 = var1.host;
         Object var3 = null;
         if (var2 != null) {
            CharSequence var8 = (CharSequence)var2;
            var2 = (new Regex("[\"<>^`{|}]")).replace(var8, "");
         } else {
            var2 = null;
         }

         var1.host = var2;
         int var4 = var1.encodedPathSegments.size();
         byte var5 = 0;

         int var6;
         for(var6 = 0; var6 < var4; ++var6) {
            var1.encodedPathSegments.set(var6, HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, (String)var1.encodedPathSegments.get(var6), 0, 0, "[]", true, true, false, false, (Charset)null, 227, (Object)null));
         }

         List var7 = var1.encodedQueryNamesAndValues;
         if (var7 != null) {
            var4 = var7.size();

            for(var6 = var5; var6 < var4; ++var6) {
               var2 = (String)var7.get(var6);
               if (var2 != null) {
                  var2 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, "\\^`{|}", true, true, true, false, (Charset)null, 195, (Object)null);
               } else {
                  var2 = null;
               }

               var7.set(var6, var2);
            }
         }

         String var9 = var1.encodedFragment;
         var2 = (String)var3;
         if (var9 != null) {
            var2 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var9, 0, 0, " \"#<>\\^`{|}", true, true, false, true, (Charset)null, 163, (Object)null);
         }

         var1.encodedFragment = var2;
         return var1;
      }

      public final HttpUrl.Builder removeAllEncodedQueryParameters(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (var2.encodedQueryNamesAndValues == null) {
            return var2;
         } else {
            var2.removeAllCanonicalQueryParameters(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#&=", true, false, true, false, (Charset)null, 211, (Object)null));
            return var2;
         }
      }

      public final HttpUrl.Builder removeAllQueryParameters(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (var2.encodedQueryNamesAndValues == null) {
            return var2;
         } else {
            var2.removeAllCanonicalQueryParameters(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, (Charset)null, 219, (Object)null));
            return var2;
         }
      }

      public final HttpUrl.Builder removePathSegment(int var1) {
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.encodedPathSegments.remove(var1);
         if (var2.encodedPathSegments.isEmpty()) {
            var2.encodedPathSegments.add("");
         }

         return var2;
      }

      public final HttpUrl.Builder scheme(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "scheme");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         if (StringsKt.equals(var1, "http", true)) {
            var2.scheme = "http";
         } else {
            if (!StringsKt.equals(var1, "https", true)) {
               StringBuilder var3 = new StringBuilder();
               var3.append("unexpected scheme: ");
               var3.append(var1);
               throw (Throwable)(new IllegalArgumentException(var3.toString()));
            }

            var2.scheme = "https";
         }

         return var2;
      }

      public final void setEncodedFragment$okhttp(String var1) {
         this.encodedFragment = var1;
      }

      public final void setEncodedPassword$okhttp(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.encodedPassword = var1;
      }

      public final HttpUrl.Builder setEncodedPathSegment(int var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var2, "encodedPathSegment");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         String var4 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"<>^`{}|/\\?#", true, false, false, false, (Charset)null, 243, (Object)null);
         var3.encodedPathSegments.set(var1, var4);
         boolean var5;
         if (!var3.isDot(var4) && !var3.isDotDot(var4)) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            return var3;
         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append("unexpected path segment: ");
            var6.append(var2);
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         }
      }

      public final void setEncodedQueryNamesAndValues$okhttp(List<String> var1) {
         this.encodedQueryNamesAndValues = var1;
      }

      public final HttpUrl.Builder setEncodedQueryParameter(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         var3.removeAllEncodedQueryParameters(var1);
         var3.addEncodedQueryParameter(var1, var2);
         return var3;
      }

      public final void setEncodedUsername$okhttp(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.encodedUsername = var1;
      }

      public final void setHost$okhttp(String var1) {
         this.host = var1;
      }

      public final HttpUrl.Builder setPathSegment(int var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var2, "pathSegment");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         String var4 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"<>^`{}|/\\?#", false, false, false, false, (Charset)null, 251, (Object)null);
         boolean var5;
         if (!var3.isDot(var4) && !var3.isDotDot(var4)) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            var3.encodedPathSegments.set(var1, var4);
            return var3;
         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append("unexpected path segment: ");
            var6.append(var2);
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         }
      }

      public final void setPort$okhttp(int var1) {
         this.port = var1;
      }

      public final HttpUrl.Builder setQueryParameter(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         HttpUrl.Builder var3 = (HttpUrl.Builder)this;
         var3.removeAllQueryParameters(var1);
         var3.addQueryParameter(var1, var2);
         return var3;
      }

      public final void setScheme$okhttp(String var1) {
         this.scheme = var1;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.scheme;
         if (var2 != null) {
            var1.append(var2);
            var1.append("://");
         } else {
            var1.append("//");
         }

         int var3 = ((CharSequence)this.encodedUsername).length();
         boolean var4 = true;
         boolean var8;
         if (var3 > 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         label79: {
            if (!var8) {
               if (((CharSequence)this.encodedPassword).length() > 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (!var8) {
                  break label79;
               }
            }

            var1.append(this.encodedUsername);
            if (((CharSequence)this.encodedPassword).length() > 0) {
               var8 = var4;
            } else {
               var8 = false;
            }

            if (var8) {
               var1.append(':');
               var1.append(this.encodedPassword);
            }

            var1.append('@');
         }

         var2 = this.host;
         if (var2 != null) {
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            if (StringsKt.contains$default((CharSequence)var2, ':', false, 2, (Object)null)) {
               var1.append('[');
               var1.append(this.host);
               var1.append(']');
            } else {
               var1.append(this.host);
            }
         }

         if (this.port != -1 || this.scheme != null) {
            label81: {
               var3 = this.effectivePort();
               if (this.scheme != null) {
                  HttpUrl.Companion var5 = HttpUrl.Companion;
                  var2 = this.scheme;
                  if (var2 == null) {
                     Intrinsics.throwNpe();
                  }

                  if (var3 == var5.defaultPort(var2)) {
                     break label81;
                  }
               }

               var1.append(':');
               var1.append(var3);
            }
         }

         HttpUrl.Companion.toPathString$okhttp(this.encodedPathSegments, var1);
         if (this.encodedQueryNamesAndValues != null) {
            var1.append('?');
            HttpUrl.Companion var7 = HttpUrl.Companion;
            List var9 = this.encodedQueryNamesAndValues;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var7.toQueryString$okhttp(var9, var1);
         }

         if (this.encodedFragment != null) {
            var1.append('#');
            var1.append(this.encodedFragment);
         }

         String var6 = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var6, "StringBuilder().apply(builderAction).toString()");
         return var6;
      }

      public final HttpUrl.Builder username(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "username");
         HttpUrl.Builder var2 = (HttpUrl.Builder)this;
         var2.encodedUsername = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, (Charset)null, 251, (Object)null);
         return var2;
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J \u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u001c\u0010\f\u001a\u00020\u0006*\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\r"},
         d2 = {"Lokhttp3/HttpUrl$Builder$Companion;", "", "()V", "INVALID_HOST", "", "parsePort", "", "input", "pos", "limit", "portColonOffset", "schemeDelimiterOffset", "slashCount", "okhttp"},
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

         private final int parsePort(String var1, int var2, int var3) {
            byte var4 = -1;

            try {
               var3 = Integer.parseInt(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, var2, var3, "", false, false, false, false, (Charset)null, 248, (Object)null));
            } catch (NumberFormatException var5) {
               var2 = var4;
               return var2;
            }

            if (1 > var3) {
               var2 = var4;
            } else {
               var2 = var4;
               if (65535 >= var3) {
                  var2 = var3;
               }
            }

            return var2;
         }

         private final int portColonOffset(String var1, int var2, int var3) {
            int var5;
            label26:
            for(; var2 < var3; var2 = var5 + 1) {
               char var4 = var1.charAt(var2);
               if (var4 == ':') {
                  return var2;
               }

               var5 = var2;
               if (var4 != '[') {
                  var5 = var2;
               } else {
                  do {
                     var2 = var5 + 1;
                     var5 = var2;
                     if (var2 >= var3) {
                        continue label26;
                     }

                     var5 = var2;
                  } while(var1.charAt(var2) != ']');

                  var5 = var2;
               }
            }

            return var3;
         }

         private final int schemeDelimiterOffset(String var1, int var2, int var3) {
            byte var4 = -1;
            if (var3 - var2 < 2) {
               return -1;
            } else {
               char var5;
               int var6;
               label64: {
                  var5 = var1.charAt(var2);
                  if (var5 >= 'a') {
                     var6 = var2;
                     if (var5 <= 'z') {
                        break label64;
                     }
                  }

                  var6 = var4;
                  if (var5 < 'A') {
                     return var6;
                  }

                  var6 = var2;
                  if (var5 > 'Z') {
                     var6 = var4;
                     return var6;
                  }
               }

               while(true) {
                  var2 = var6 + 1;
                  var6 = var4;
                  if (var2 >= var3) {
                     break;
                  }

                  var5 = var1.charAt(var2);
                  if (('a' > var5 || 'z' < var5) && ('A' > var5 || 'Z' < var5) && ('0' > var5 || '9' < var5) && var5 != '+' && var5 != '-' && var5 != '.') {
                     var6 = var4;
                     if (var5 == ':') {
                        var6 = var2;
                     }
                     break;
                  }

                  var6 = var2;
               }

               return var6;
            }
         }

         private final int slashCount(String var1, int var2, int var3) {
            int var4;
            for(var4 = 0; var2 < var3; ++var2) {
               char var5 = var1.charAt(var2);
               if (var5 != '\\' && var5 != '/') {
                  break;
               }

               ++var4;
            }

            return var4;
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007¢\u0006\u0002\b\u0018J\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0007¢\u0006\u0002\b\u0018J\u0015\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u0018J\u0017\u0010\u001b\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u001cJa\u0010\u001d\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"2\b\b\u0002\u0010$\u001a\u00020\"2\b\b\u0002\u0010%\u001a\u00020\"2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'H\u0000¢\u0006\u0002\b(J\u001c\u0010)\u001a\u00020\"*\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0002J/\u0010*\u001a\u00020\u0004*\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u001f\u001a\u00020\u00122\b\b\u0002\u0010$\u001a\u00020\"H\u0000¢\u0006\u0002\b+J\u0011\u0010,\u001a\u00020\u0015*\u00020\u0004H\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0017H\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u001aH\u0007¢\u0006\u0002\b\u0014J\u0013\u0010-\u001a\u0004\u0018\u00010\u0015*\u00020\u0004H\u0007¢\u0006\u0002\b\u001bJ#\u0010.\u001a\u00020/*\b\u0012\u0004\u0012\u00020\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000¢\u0006\u0002\b4J\u0019\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000406*\u00020\u0004H\u0000¢\u0006\u0002\b7J%\u00108\u001a\u00020/*\n\u0012\u0006\u0012\u0004\u0018\u00010\u0004002\n\u00101\u001a\u000602j\u0002`3H\u0000¢\u0006\u0002\b9JV\u0010:\u001a\u00020/*\u00020;2\u0006\u0010<\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\"2\b\u0010&\u001a\u0004\u0018\u00010'H\u0002J,\u0010=\u001a\u00020/*\u00020;2\u0006\u0010>\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006?"},
      d2 = {"Lokhttp3/HttpUrl$Companion;", "", "()V", "FORM_ENCODE_SET", "", "FRAGMENT_ENCODE_SET", "FRAGMENT_ENCODE_SET_URI", "HEX_DIGITS", "", "PASSWORD_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET_URI", "QUERY_COMPONENT_ENCODE_SET", "QUERY_COMPONENT_ENCODE_SET_URI", "QUERY_COMPONENT_REENCODE_SET", "QUERY_ENCODE_SET", "USERNAME_ENCODE_SET", "defaultPort", "", "scheme", "get", "Lokhttp3/HttpUrl;", "uri", "Ljava/net/URI;", "-deprecated_get", "url", "Ljava/net/URL;", "parse", "-deprecated_parse", "canonicalize", "pos", "limit", "encodeSet", "alreadyEncoded", "", "strict", "plusIsSpace", "unicodeAllowed", "charset", "Ljava/nio/charset/Charset;", "canonicalize$okhttp", "isPercentEncoded", "percentDecode", "percentDecode$okhttp", "toHttpUrl", "toHttpUrlOrNull", "toPathString", "", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toPathString$okhttp", "toQueryNamesAndValues", "", "toQueryNamesAndValues$okhttp", "toQueryString", "toQueryString$okhttp", "writeCanonicalized", "Lokio/Buffer;", "input", "writePercentDecoded", "encoded", "okhttp"},
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

      // $FF: synthetic method
      public static String canonicalize$okhttp$default(HttpUrl.Companion var0, String var1, int var2, int var3, String var4, boolean var5, boolean var6, boolean var7, boolean var8, Charset var9, int var10, Object var11) {
         if ((var10 & 1) != 0) {
            var2 = 0;
         }

         if ((var10 & 2) != 0) {
            var3 = var1.length();
         }

         if ((var10 & 8) != 0) {
            var5 = false;
         }

         if ((var10 & 16) != 0) {
            var6 = false;
         }

         if ((var10 & 32) != 0) {
            var7 = false;
         }

         if ((var10 & 64) != 0) {
            var8 = false;
         }

         if ((var10 & 128) != 0) {
            var9 = (Charset)null;
         }

         return var0.canonicalize$okhttp(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

      private final boolean isPercentEncoded(String var1, int var2, int var3) {
         int var4 = var2 + 2;
         boolean var5 = true;
         if (var4 >= var3 || var1.charAt(var2) != '%' || Util.parseHexDigit(var1.charAt(var2 + 1)) == -1 || Util.parseHexDigit(var1.charAt(var4)) == -1) {
            var5 = false;
         }

         return var5;
      }

      // $FF: synthetic method
      public static String percentDecode$okhttp$default(HttpUrl.Companion var0, String var1, int var2, int var3, boolean var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var2 = 0;
         }

         if ((var5 & 2) != 0) {
            var3 = var1.length();
         }

         if ((var5 & 4) != 0) {
            var4 = false;
         }

         return var0.percentDecode$okhttp(var1, var2, var3, var4);
      }

      private final void writeCanonicalized(Buffer var1, String var2, int var3, int var4, String var5, boolean var6, boolean var7, boolean var8, boolean var9, Charset var10) {
         Buffer var13;
         for(Buffer var11 = (Buffer)null; var3 < var4; var11 = var13) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            int var12;
            label98: {
               var12 = var2.codePointAt(var3);
               if (var6) {
                  var13 = var11;
                  if (var12 == 9) {
                     break label98;
                  }

                  var13 = var11;
                  if (var12 == 10) {
                     break label98;
                  }

                  var13 = var11;
                  if (var12 == 12) {
                     break label98;
                  }

                  var13 = var11;
                  if (var12 == 13) {
                     break label98;
                  }
               }

               if (var12 == 43 && var8) {
                  String var16;
                  if (var6) {
                     var16 = "+";
                  } else {
                     var16 = "%2B";
                  }

                  var1.writeUtf8(var16);
                  var13 = var11;
               } else if (var12 >= 32 && var12 != 127 && (var12 < 128 || var9) && !StringsKt.contains$default((CharSequence)var5, (char)var12, false, 2, (Object)null) && (var12 != 37 || var6 && (!var7 || ((HttpUrl.Companion)this).isPercentEncoded(var2, var3, var4)))) {
                  var1.writeUtf8CodePoint(var12);
                  var13 = var11;
               } else {
                  Buffer var14 = var11;
                  if (var11 == null) {
                     var14 = new Buffer();
                  }

                  if (var10 != null && !Intrinsics.areEqual((Object)var10, (Object)StandardCharsets.UTF_8)) {
                     var14.writeString(var2, var3, Character.charCount(var12) + var3, var10);
                  } else {
                     var14.writeUtf8CodePoint(var12);
                  }

                  while(true) {
                     var13 = var14;
                     if (var14.exhausted()) {
                        break;
                     }

                     int var15 = var14.readByte() & 255;
                     var1.writeByte(37);
                     var1.writeByte(HttpUrl.HEX_DIGITS[var15 >> 4 & 15]);
                     var1.writeByte(HttpUrl.HEX_DIGITS[var15 & 15]);
                  }
               }
            }

            var3 += Character.charCount(var12);
         }

      }

      private final void writePercentDecoded(Buffer var1, String var2, int var3, int var4, boolean var5) {
         while(var3 < var4) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            int var6;
            label25: {
               var6 = var2.codePointAt(var3);
               if (var6 == 37) {
                  int var7 = var3 + 2;
                  if (var7 < var4) {
                     int var8 = Util.parseHexDigit(var2.charAt(var3 + 1));
                     int var9 = Util.parseHexDigit(var2.charAt(var7));
                     if (var8 != -1 && var9 != -1) {
                        var1.writeByte((var8 << 4) + var9);
                        var3 = Character.charCount(var6) + var7;
                        continue;
                     }
                     break label25;
                  }
               }

               if (var6 == 43 && var5) {
                  var1.writeByte(32);
                  ++var3;
                  continue;
               }
            }

            var1.writeUtf8CodePoint(var6);
            var3 += Character.charCount(var6);
         }

      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "url.toHttpUrl()",
   imports = {"okhttp3.HttpUrl.Companion.toHttpUrl"}
)
      )
      public final HttpUrl _deprecated_get/* $FF was: -deprecated_get*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         return ((HttpUrl.Companion)this).get(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "uri.toHttpUrlOrNull()",
   imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}
)
      )
      public final HttpUrl _deprecated_get/* $FF was: -deprecated_get*/(URI var1) {
         Intrinsics.checkParameterIsNotNull(var1, "uri");
         return ((HttpUrl.Companion)this).get(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "url.toHttpUrlOrNull()",
   imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}
)
      )
      public final HttpUrl _deprecated_get/* $FF was: -deprecated_get*/(URL var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         return ((HttpUrl.Companion)this).get(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "url.toHttpUrlOrNull()",
   imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}
)
      )
      public final HttpUrl _deprecated_parse/* $FF was: -deprecated_parse*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         return ((HttpUrl.Companion)this).parse(var1);
      }

      public final String canonicalize$okhttp(String var1, int var2, int var3, String var4, boolean var5, boolean var6, boolean var7, boolean var8, Charset var9) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$canonicalize");
         Intrinsics.checkParameterIsNotNull(var4, "encodeSet");

         int var11;
         for(int var10 = var2; var10 < var3; var10 += Character.charCount(var11)) {
            var11 = var1.codePointAt(var10);
            if (var11 < 32 || var11 == 127 || var11 >= 128 && !var8 || StringsKt.contains$default((CharSequence)var4, (char)var11, false, 2, (Object)null) || var11 == 37 && (!var5 || var6 && !((HttpUrl.Companion)this).isPercentEncoded(var1, var10, var3)) || var11 == 43 && var7) {
               Buffer var12 = new Buffer();
               var12.writeUtf8(var1, var2, var10);
               ((HttpUrl.Companion)this).writeCanonicalized(var12, var1, var10, var3, var4, var5, var6, var7, var8, var9);
               return var12.readUtf8();
            }
         }

         var1 = var1.substring(var2, var3);
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var1;
      }

      @JvmStatic
      public final int defaultPort(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "scheme");
         int var2 = var1.hashCode();
         short var3;
         if (var2 != 3213448) {
            if (var2 == 99617003 && var1.equals("https")) {
               var3 = 443;
               return var3;
            }
         } else if (var1.equals("http")) {
            var3 = 80;
            return var3;
         }

         var3 = -1;
         return var3;
      }

      @JvmStatic
      public final HttpUrl get(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrl");
         return (new HttpUrl.Builder()).parse$okhttp((HttpUrl)null, var1).build();
      }

      @JvmStatic
      public final HttpUrl get(URI var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");
         HttpUrl.Companion var2 = (HttpUrl.Companion)this;
         String var3 = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
         return var2.parse(var3);
      }

      @JvmStatic
      public final HttpUrl get(URL var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");
         HttpUrl.Companion var2 = (HttpUrl.Companion)this;
         String var3 = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
         return var2.parse(var3);
      }

      @JvmStatic
      public final HttpUrl parse(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");

         HttpUrl var3;
         try {
            var3 = ((HttpUrl.Companion)this).get(var1);
         } catch (IllegalArgumentException var2) {
            var3 = null;
         }

         return var3;
      }

      public final String percentDecode$okhttp(String var1, int var2, int var3, boolean var4) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$percentDecode");

         for(int var5 = var2; var5 < var3; ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 == '%' || var6 == '+' && var4) {
               Buffer var7 = new Buffer();
               var7.writeUtf8(var1, var2, var5);
               ((HttpUrl.Companion)this).writePercentDecoded(var7, var1, var5, var3, var4);
               return var7.readUtf8();
            }
         }

         var1 = var1.substring(var2, var3);
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var1;
      }

      public final void toPathString$okhttp(List<String> var1, StringBuilder var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toPathString");
         Intrinsics.checkParameterIsNotNull(var2, "out");
         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            var2.append('/');
            var2.append((String)var1.get(var4));
         }

      }

      public final List<String> toQueryNamesAndValues$okhttp(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toQueryNamesAndValues");
         List var2 = (List)(new ArrayList());

         int var6;
         for(int var3 = 0; var3 <= var1.length(); var3 = var6 + 1) {
            CharSequence var4 = (CharSequence)var1;
            int var5 = StringsKt.indexOf$default(var4, '&', var3, false, 4, (Object)null);
            var6 = var5;
            if (var5 == -1) {
               var6 = var1.length();
            }

            var5 = StringsKt.indexOf$default(var4, '=', var3, false, 4, (Object)null);
            String var7;
            if (var5 != -1 && var5 <= var6) {
               var7 = var1.substring(var3, var5);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var2.add(var7);
               var7 = var1.substring(var5 + 1, var6);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var2.add(var7);
            } else {
               var7 = var1.substring(var3, var6);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var2.add(var7);
               var2.add((Object)null);
            }
         }

         return var2;
      }

      public final void toQueryString$okhttp(List<String> var1, StringBuilder var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toQueryString");
         Intrinsics.checkParameterIsNotNull(var2, "out");
         IntProgression var3 = RangesKt.step((IntProgression)RangesKt.until(0, var1.size()), 2);
         int var4 = var3.getFirst();
         int var5 = var3.getLast();
         int var6 = var3.getStep();
         if (var6 >= 0) {
            if (var4 > var5) {
               return;
            }
         } else if (var4 < var5) {
            return;
         }

         while(true) {
            String var7 = (String)var1.get(var4);
            String var8 = (String)var1.get(var4 + 1);
            if (var4 > 0) {
               var2.append('&');
            }

            var2.append(var7);
            if (var8 != null) {
               var2.append('=');
               var2.append(var8);
            }

            if (var4 == var5) {
               return;
            }

            var4 += var6;
         }
      }
   }
}
