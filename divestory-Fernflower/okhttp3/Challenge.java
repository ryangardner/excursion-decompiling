package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bJ\u001b\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00030\u0007H\u0007¢\u0006\u0002\b\u000eJ\r\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\b\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u000f\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b\u0015J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0016J\b\u0010\u0017\u001a\u00020\u0003H\u0016J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bR!\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00030\u00078G¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b\u0004\u0010\rR\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\r¨\u0006\u0019"},
   d2 = {"Lokhttp3/Challenge;", "", "scheme", "", "realm", "(Ljava/lang/String;Ljava/lang/String;)V", "authParams", "", "(Ljava/lang/String;Ljava/util/Map;)V", "()Ljava/util/Map;", "charset", "Ljava/nio/charset/Charset;", "()Ljava/nio/charset/Charset;", "()Ljava/lang/String;", "-deprecated_authParams", "-deprecated_charset", "equals", "", "other", "hashCode", "", "-deprecated_realm", "-deprecated_scheme", "toString", "withCharset", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Challenge {
   private final Map<String, String> authParams;
   private final String scheme;

   public Challenge(String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "scheme");
      Intrinsics.checkParameterIsNotNull(var2, "realm");
      Map var3 = Collections.singletonMap("realm", var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "singletonMap(\"realm\", realm)");
      this(var1, var3);
   }

   public Challenge(String var1, Map<String, String> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "scheme");
      Intrinsics.checkParameterIsNotNull(var2, "authParams");
      super();
      this.scheme = var1;
      Map var3 = (Map)(new LinkedHashMap());

      String var8;
      for(Iterator var6 = var2.entrySet().iterator(); var6.hasNext(); var3.put(var1, var8)) {
         Entry var4 = (Entry)var6.next();
         var1 = (String)var4.getKey();
         var8 = (String)var4.getValue();
         if (var1 != null) {
            Locale var5 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(var5, "US");
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var1 = var1.toLowerCase(var5);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toLowerCase(locale)");
         } else {
            var1 = null;
         }
      }

      Map var7 = Collections.unmodifiableMap(var3);
      Intrinsics.checkExpressionValueIsNotNull(var7, "unmodifiableMap<String?, String>(newAuthParams)");
      this.authParams = var7;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "authParams",
   imports = {}
)
   )
   public final Map<String, String> _deprecated_authParams/* $FF was: -deprecated_authParams*/() {
      return this.authParams;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "charset",
   imports = {}
)
   )
   public final Charset _deprecated_charset/* $FF was: -deprecated_charset*/() {
      return this.charset();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "realm",
   imports = {}
)
   )
   public final String _deprecated_realm/* $FF was: -deprecated_realm*/() {
      return this.realm();
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

   public final Map<String, String> authParams() {
      return this.authParams;
   }

   public final Charset charset() {
      String var1 = (String)this.authParams.get("charset");
      Charset var3;
      if (var1 != null) {
         try {
            var3 = Charset.forName(var1);
            Intrinsics.checkExpressionValueIsNotNull(var3, "Charset.forName(charset)");
            return var3;
         } catch (Exception var2) {
         }
      }

      var3 = StandardCharsets.ISO_8859_1;
      Intrinsics.checkExpressionValueIsNotNull(var3, "ISO_8859_1");
      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Challenge) {
         Challenge var3 = (Challenge)var1;
         if (Intrinsics.areEqual((Object)var3.scheme, (Object)this.scheme) && Intrinsics.areEqual((Object)var3.authParams, (Object)this.authParams)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public int hashCode() {
      return (899 + this.scheme.hashCode()) * 31 + this.authParams.hashCode();
   }

   public final String realm() {
      return (String)this.authParams.get("realm");
   }

   public final String scheme() {
      return this.scheme;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.scheme);
      var1.append(" authParams=");
      var1.append(this.authParams);
      return var1.toString();
   }

   public final Challenge withCharset(Charset var1) {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      Map var2 = MapsKt.toMutableMap(this.authParams);
      String var3 = var1.name();
      Intrinsics.checkExpressionValueIsNotNull(var3, "charset.name()");
      var2.put("charset", var3);
      return new Challenge(this.scheme, var2);
   }
}
