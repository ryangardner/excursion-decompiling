package okhttp3;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u0000 !2\u00020\u0001:\u0002 !Bq\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012J\r\u0010\u000f\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0015J\r\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0016J\r\u0010\u000b\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0017J\r\u0010\f\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0018J\r\u0010\n\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0019J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001aJ\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u000e\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001cJ\r\u0010\r\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001dJ\r\u0010\u0007\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0011H\u0016R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u000f\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0013R\u0013\u0010\u0005\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0014R\u0013\u0010\u000b\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0014R\u0013\u0010\f\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0014R\u0013\u0010\n\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0013R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0013R\u0013\u0010\u000e\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0013R\u0013\u0010\r\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0013R\u0013\u0010\u0007\u001a\u00020\u00068\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0014¨\u0006\""},
   d2 = {"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "-deprecated_immutable", "-deprecated_maxAgeSeconds", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_mustRevalidate", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_noTransform", "-deprecated_onlyIfCached", "-deprecated_sMaxAgeSeconds", "toString", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CacheControl {
   public static final CacheControl.Companion Companion = new CacheControl.Companion((DefaultConstructorMarker)null);
   public static final CacheControl FORCE_CACHE;
   public static final CacheControl FORCE_NETWORK = (new CacheControl.Builder()).noCache().build();
   private String headerValue;
   private final boolean immutable;
   private final boolean isPrivate;
   private final boolean isPublic;
   private final int maxAgeSeconds;
   private final int maxStaleSeconds;
   private final int minFreshSeconds;
   private final boolean mustRevalidate;
   private final boolean noCache;
   private final boolean noStore;
   private final boolean noTransform;
   private final boolean onlyIfCached;
   private final int sMaxAgeSeconds;

   static {
      FORCE_CACHE = (new CacheControl.Builder()).onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
   }

   private CacheControl(boolean var1, boolean var2, int var3, int var4, boolean var5, boolean var6, boolean var7, int var8, int var9, boolean var10, boolean var11, boolean var12, String var13) {
      this.noCache = var1;
      this.noStore = var2;
      this.maxAgeSeconds = var3;
      this.sMaxAgeSeconds = var4;
      this.isPrivate = var5;
      this.isPublic = var6;
      this.mustRevalidate = var7;
      this.maxStaleSeconds = var8;
      this.minFreshSeconds = var9;
      this.onlyIfCached = var10;
      this.noTransform = var11;
      this.immutable = var12;
      this.headerValue = var13;
   }

   // $FF: synthetic method
   public CacheControl(boolean var1, boolean var2, int var3, int var4, boolean var5, boolean var6, boolean var7, int var8, int var9, boolean var10, boolean var11, boolean var12, String var13, DefaultConstructorMarker var14) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   @JvmStatic
   public static final CacheControl parse(Headers var0) {
      return Companion.parse(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "immutable",
   imports = {}
)
   )
   public final boolean _deprecated_immutable/* $FF was: -deprecated_immutable*/() {
      return this.immutable;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "maxAgeSeconds",
   imports = {}
)
   )
   public final int _deprecated_maxAgeSeconds/* $FF was: -deprecated_maxAgeSeconds*/() {
      return this.maxAgeSeconds;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "maxStaleSeconds",
   imports = {}
)
   )
   public final int _deprecated_maxStaleSeconds/* $FF was: -deprecated_maxStaleSeconds*/() {
      return this.maxStaleSeconds;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "minFreshSeconds",
   imports = {}
)
   )
   public final int _deprecated_minFreshSeconds/* $FF was: -deprecated_minFreshSeconds*/() {
      return this.minFreshSeconds;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "mustRevalidate",
   imports = {}
)
   )
   public final boolean _deprecated_mustRevalidate/* $FF was: -deprecated_mustRevalidate*/() {
      return this.mustRevalidate;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "noCache",
   imports = {}
)
   )
   public final boolean _deprecated_noCache/* $FF was: -deprecated_noCache*/() {
      return this.noCache;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "noStore",
   imports = {}
)
   )
   public final boolean _deprecated_noStore/* $FF was: -deprecated_noStore*/() {
      return this.noStore;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "noTransform",
   imports = {}
)
   )
   public final boolean _deprecated_noTransform/* $FF was: -deprecated_noTransform*/() {
      return this.noTransform;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "onlyIfCached",
   imports = {}
)
   )
   public final boolean _deprecated_onlyIfCached/* $FF was: -deprecated_onlyIfCached*/() {
      return this.onlyIfCached;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "sMaxAgeSeconds",
   imports = {}
)
   )
   public final int _deprecated_sMaxAgeSeconds/* $FF was: -deprecated_sMaxAgeSeconds*/() {
      return this.sMaxAgeSeconds;
   }

   public final boolean immutable() {
      return this.immutable;
   }

   public final boolean isPrivate() {
      return this.isPrivate;
   }

   public final boolean isPublic() {
      return this.isPublic;
   }

   public final int maxAgeSeconds() {
      return this.maxAgeSeconds;
   }

   public final int maxStaleSeconds() {
      return this.maxStaleSeconds;
   }

   public final int minFreshSeconds() {
      return this.minFreshSeconds;
   }

   public final boolean mustRevalidate() {
      return this.mustRevalidate;
   }

   public final boolean noCache() {
      return this.noCache;
   }

   public final boolean noStore() {
      return this.noStore;
   }

   public final boolean noTransform() {
      return this.noTransform;
   }

   public final boolean onlyIfCached() {
      return this.onlyIfCached;
   }

   public final int sMaxAgeSeconds() {
      return this.sMaxAgeSeconds;
   }

   public String toString() {
      String var1 = this.headerValue;
      String var2 = var1;
      if (var1 == null) {
         StringBuilder var4 = new StringBuilder();
         if (this.noCache) {
            var4.append("no-cache, ");
         }

         if (this.noStore) {
            var4.append("no-store, ");
         }

         if (this.maxAgeSeconds != -1) {
            var4.append("max-age=");
            var4.append(this.maxAgeSeconds);
            var4.append(", ");
         }

         if (this.sMaxAgeSeconds != -1) {
            var4.append("s-maxage=");
            var4.append(this.sMaxAgeSeconds);
            var4.append(", ");
         }

         if (this.isPrivate) {
            var4.append("private, ");
         }

         if (this.isPublic) {
            var4.append("public, ");
         }

         if (this.mustRevalidate) {
            var4.append("must-revalidate, ");
         }

         if (this.maxStaleSeconds != -1) {
            var4.append("max-stale=");
            var4.append(this.maxStaleSeconds);
            var4.append(", ");
         }

         if (this.minFreshSeconds != -1) {
            var4.append("min-fresh=");
            var4.append(this.minFreshSeconds);
            var4.append(", ");
         }

         if (this.onlyIfCached) {
            var4.append("only-if-cached, ");
         }

         if (this.noTransform) {
            var4.append("no-transform, ");
         }

         if (this.immutable) {
            var4.append("immutable, ");
         }

         boolean var3;
         if (((CharSequence)var4).length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            return "";
         }

         var4.delete(var4.length() - 2, var4.length());
         var2 = var4.toString();
         Intrinsics.checkExpressionValueIsNotNull(var2, "StringBuilder().apply(builderAction).toString()");
         this.headerValue = var2;
      }

      return var2;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u0003\u001a\u00020\u0000J\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\t\u001a\u00020\u0000J\u0006\u0010\n\u001a\u00020\u0000J\u0006\u0010\u000b\u001a\u00020\u0000J\u0006\u0010\f\u001a\u00020\u0000J\f\u0010\u0014\u001a\u00020\u0006*\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"},
      d2 = {"Lokhttp3/CacheControl$Builder;", "", "()V", "immutable", "", "maxAgeSeconds", "", "maxStaleSeconds", "minFreshSeconds", "noCache", "noStore", "noTransform", "onlyIfCached", "build", "Lokhttp3/CacheControl;", "maxAge", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "maxStale", "minFresh", "clampToInt", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private boolean immutable;
      private int maxAgeSeconds = -1;
      private int maxStaleSeconds = -1;
      private int minFreshSeconds = -1;
      private boolean noCache;
      private boolean noStore;
      private boolean noTransform;
      private boolean onlyIfCached;

      private final int clampToInt(long var1) {
         int var3 = Integer.MAX_VALUE;
         if (var1 <= (long)Integer.MAX_VALUE) {
            var3 = (int)var1;
         }

         return var3;
      }

      public final CacheControl build() {
         return new CacheControl(this.noCache, this.noStore, this.maxAgeSeconds, -1, false, false, false, this.maxStaleSeconds, this.minFreshSeconds, this.onlyIfCached, this.noTransform, this.immutable, (String)null, (DefaultConstructorMarker)null);
      }

      public final CacheControl.Builder immutable() {
         CacheControl.Builder var1 = (CacheControl.Builder)this;
         var1.immutable = true;
         return var1;
      }

      public final CacheControl.Builder maxAge(int var1, TimeUnit var2) {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         CacheControl.Builder var3 = (CacheControl.Builder)this;
         boolean var4;
         if (var1 >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var3.maxAgeSeconds = var3.clampToInt(var2.toSeconds((long)var1));
            return var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("maxAge < 0: ");
            var5.append(var1);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      }

      public final CacheControl.Builder maxStale(int var1, TimeUnit var2) {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         CacheControl.Builder var3 = (CacheControl.Builder)this;
         boolean var4;
         if (var1 >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var3.maxStaleSeconds = var3.clampToInt(var2.toSeconds((long)var1));
            return var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("maxStale < 0: ");
            var5.append(var1);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      }

      public final CacheControl.Builder minFresh(int var1, TimeUnit var2) {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         CacheControl.Builder var3 = (CacheControl.Builder)this;
         boolean var4;
         if (var1 >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var3.minFreshSeconds = var3.clampToInt(var2.toSeconds((long)var1));
            return var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("minFresh < 0: ");
            var5.append(var1);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      }

      public final CacheControl.Builder noCache() {
         CacheControl.Builder var1 = (CacheControl.Builder)this;
         var1.noCache = true;
         return var1;
      }

      public final CacheControl.Builder noStore() {
         CacheControl.Builder var1 = (CacheControl.Builder)this;
         var1.noStore = true;
         return var1;
      }

      public final CacheControl.Builder noTransform() {
         CacheControl.Builder var1 = (CacheControl.Builder)this;
         var1.noTransform = true;
         return var1;
      }

      public final CacheControl.Builder onlyIfCached() {
         CacheControl.Builder var1 = (CacheControl.Builder)this;
         var1.onlyIfCached = true;
         return var1;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001e\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\nH\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
      d2 = {"Lokhttp3/CacheControl$Companion;", "", "()V", "FORCE_CACHE", "Lokhttp3/CacheControl;", "FORCE_NETWORK", "parse", "headers", "Lokhttp3/Headers;", "indexOfElement", "", "", "characters", "startIndex", "okhttp"},
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

      private final int indexOfElement(String var1, String var2, int var3) {
         for(int var4 = var1.length(); var3 < var4; ++var3) {
            if (StringsKt.contains$default((CharSequence)var2, var1.charAt(var3), false, 2, (Object)null)) {
               return var3;
            }
         }

         return var1.length();
      }

      // $FF: synthetic method
      static int indexOfElement$default(CacheControl.Companion var0, String var1, String var2, int var3, int var4, Object var5) {
         if ((var4 & 2) != 0) {
            var3 = 0;
         }

         return var0.indexOfElement(var1, var2, var3);
      }

      @JvmStatic
      public final CacheControl parse(Headers var1) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         String var2 = (String)null;
         int var3 = var1.size();
         boolean var4 = true;
         String var5 = var2;
         int var6 = 0;
         boolean var7 = true;
         boolean var8 = false;
         boolean var9 = false;
         int var10 = -1;
         int var11 = -1;
         boolean var12 = false;
         boolean var13 = false;
         boolean var14 = false;
         int var15 = -1;
         int var16 = -1;
         boolean var17 = false;
         boolean var18 = false;

         boolean var19;
         for(var19 = false; var6 < var3; ++var6) {
            String var21;
            String var22;
            label119: {
               var21 = var1.name(var6);
               var22 = var1.value(var6);
               if (StringsKt.equals(var21, "Cache-Control", var4)) {
                  if (var5 == null) {
                     var5 = var22;
                     break label119;
                  }
               } else if (!StringsKt.equals(var21, "Pragma", var4)) {
                  continue;
               }

               var7 = false;
            }

            byte var23 = 0;
            int var24 = var3;

            boolean var34;
            for(var3 = var23; var3 < var22.length(); var18 = var34) {
               CacheControl.Companion var20 = (CacheControl.Companion)this;
               int var38 = var20.indexOfElement(var22, "=,;", var3);
               if (var22 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var21 = var22.substring(var3, var38);
               Intrinsics.checkExpressionValueIsNotNull(var21, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               if (var21 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
               }

               var21 = StringsKt.trim((CharSequence)var21).toString();
               String var37;
               if (var38 != var22.length() && var22.charAt(var38) != ',' && var22.charAt(var38) != ';') {
                  var38 = Util.indexOfNonWhitespace(var22, var38 + 1);
                  if (var38 < var22.length() && var22.charAt(var38) == '"') {
                     var3 = var38 + 1;
                     var38 = StringsKt.indexOf$default((CharSequence)var22, '"', var3, false, 4, (Object)null);
                     if (var22 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var37 = var22.substring(var3, var38);
                     Intrinsics.checkExpressionValueIsNotNull(var37, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                     var3 = var38 + 1;
                  } else {
                     var3 = var20.indexOfElement(var22, ",;", var38);
                     if (var22 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var37 = var22.substring(var38, var3);
                     Intrinsics.checkExpressionValueIsNotNull(var37, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                     if (var37 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var37 = StringsKt.trim((CharSequence)var37).toString();
                  }
               } else {
                  var3 = var38 + 1;
                  var37 = var2;
               }

               boolean var25 = true;
               boolean var26;
               int var27;
               boolean var28;
               boolean var29;
               boolean var30;
               int var31;
               int var32;
               boolean var33;
               if (StringsKt.equals("no-cache", var21, true)) {
                  var8 = true;
                  var26 = var9;
                  var38 = var10;
                  var27 = var11;
                  var28 = var12;
                  var29 = var13;
                  var30 = var14;
                  var31 = var15;
                  var32 = var16;
                  var33 = var17;
                  var34 = var18;
               } else if (StringsKt.equals("no-store", var21, true)) {
                  var26 = true;
                  var38 = var10;
                  var27 = var11;
                  var28 = var12;
                  var29 = var13;
                  var30 = var14;
                  var31 = var15;
                  var32 = var16;
                  var33 = var17;
                  var34 = var18;
               } else {
                  label98: {
                     if (StringsKt.equals("max-age", var21, true)) {
                        var10 = Util.toNonNegativeInt(var37, -1);
                     } else if (StringsKt.equals("s-maxage", var21, true)) {
                        var11 = Util.toNonNegativeInt(var37, -1);
                     } else {
                        if (StringsKt.equals("private", var21, true)) {
                           var28 = true;
                           var26 = var9;
                           var38 = var10;
                           var27 = var11;
                           var29 = var13;
                           var30 = var14;
                           var31 = var15;
                           var32 = var16;
                           var33 = var17;
                           var34 = var18;
                           break label98;
                        }

                        if (StringsKt.equals("public", var21, true)) {
                           var29 = true;
                           var26 = var9;
                           var38 = var10;
                           var27 = var11;
                           var28 = var12;
                           var30 = var14;
                           var31 = var15;
                           var32 = var16;
                           var33 = var17;
                           var34 = var18;
                           break label98;
                        }

                        if (StringsKt.equals("must-revalidate", var21, true)) {
                           var30 = true;
                           var26 = var9;
                           var38 = var10;
                           var27 = var11;
                           var28 = var12;
                           var29 = var13;
                           var31 = var15;
                           var32 = var16;
                           var33 = var17;
                           var34 = var18;
                           break label98;
                        }

                        if (StringsKt.equals("max-stale", var21, true)) {
                           var31 = Util.toNonNegativeInt(var37, Integer.MAX_VALUE);
                           var26 = var9;
                           var38 = var10;
                           var27 = var11;
                           var28 = var12;
                           var29 = var13;
                           var30 = var14;
                           var32 = var16;
                           var33 = var17;
                           var34 = var18;
                           break label98;
                        }

                        if (!StringsKt.equals("min-fresh", var21, true)) {
                           if (StringsKt.equals("only-if-cached", var21, true)) {
                              var33 = true;
                              var26 = var9;
                              var38 = var10;
                              var27 = var11;
                              var28 = var12;
                              var29 = var13;
                              var30 = var14;
                              var31 = var15;
                              var32 = var16;
                              var34 = var18;
                           } else if (StringsKt.equals("no-transform", var21, true)) {
                              var34 = true;
                              var26 = var9;
                              var38 = var10;
                              var27 = var11;
                              var28 = var12;
                              var29 = var13;
                              var30 = var14;
                              var31 = var15;
                              var32 = var16;
                              var33 = var17;
                           } else {
                              boolean var35 = StringsKt.equals("immutable", var21, true);
                              var4 = var8;
                              var8 = var8;
                              var26 = var9;
                              var38 = var10;
                              var27 = var11;
                              var28 = var12;
                              var29 = var13;
                              var30 = var14;
                              var31 = var15;
                              var32 = var16;
                              var33 = var17;
                              var34 = var18;
                              if (var35) {
                                 var19 = true;
                                 var34 = var18;
                                 var33 = var17;
                                 var32 = var16;
                                 var31 = var15;
                                 var30 = var14;
                                 var29 = var13;
                                 var28 = var12;
                                 var27 = var11;
                                 var38 = var10;
                                 var26 = var9;
                                 var8 = var4;
                              }
                           }
                           break label98;
                        }

                        var16 = Util.toNonNegativeInt(var37, -1);
                     }

                     var26 = var9;
                     var38 = var10;
                     var27 = var11;
                     var28 = var12;
                     var29 = var13;
                     var30 = var14;
                     var31 = var15;
                     var32 = var16;
                     var33 = var17;
                     var34 = var18;
                  }
               }

               var4 = var25;
               var9 = var26;
               var10 = var38;
               var11 = var27;
               var12 = var28;
               var13 = var29;
               var14 = var30;
               var15 = var31;
               var16 = var32;
               var17 = var33;
            }

            var3 = var24;
         }

         String var36;
         if (!var7) {
            var36 = var2;
         } else {
            var36 = var5;
         }

         return new CacheControl(var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var36, (DefaultConstructorMarker)null);
      }
   }
}
