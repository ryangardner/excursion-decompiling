package okhttp3;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B-\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\fH\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00020\u0003J\r\u0010\u0005\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0003H\u0016J\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\nR\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u0019"},
   d2 = {"Lokhttp3/MediaType;", "", "mediaType", "", "type", "subtype", "parameterNamesAndValues", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "[Ljava/lang/String;", "()Ljava/lang/String;", "charset", "Ljava/nio/charset/Charset;", "defaultValue", "equals", "", "other", "hashCode", "", "parameter", "name", "-deprecated_subtype", "toString", "-deprecated_type", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MediaType {
   public static final MediaType.Companion Companion = new MediaType.Companion((DefaultConstructorMarker)null);
   private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
   private static final String QUOTED = "\"([^\"]*)\"";
   private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
   private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
   private final String mediaType;
   private final String[] parameterNamesAndValues;
   private final String subtype;
   private final String type;

   private MediaType(String var1, String var2, String var3, String[] var4) {
      this.mediaType = var1;
      this.type = var2;
      this.subtype = var3;
      this.parameterNamesAndValues = var4;
   }

   // $FF: synthetic method
   public MediaType(String var1, String var2, String var3, String[] var4, DefaultConstructorMarker var5) {
      this(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   public static Charset charset$default(MediaType var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = (Charset)null;
      }

      return var0.charset(var1);
   }

   @JvmStatic
   public static final MediaType get(String var0) {
      return Companion.get(var0);
   }

   @JvmStatic
   public static final MediaType parse(String var0) {
      return Companion.parse(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "subtype",
   imports = {}
)
   )
   public final String _deprecated_subtype/* $FF was: -deprecated_subtype*/() {
      return this.subtype;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "type",
   imports = {}
)
   )
   public final String _deprecated_type/* $FF was: -deprecated_type*/() {
      return this.type;
   }

   public final Charset charset() {
      return charset$default(this, (Charset)null, 1, (Object)null);
   }

   public final Charset charset(Charset var1) {
      String var2 = this.parameter("charset");
      Charset var3 = var1;
      if (var2 != null) {
         try {
            var3 = Charset.forName(var2);
         } catch (IllegalArgumentException var4) {
            var3 = var1;
         }
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof MediaType && Intrinsics.areEqual((Object)((MediaType)var1).mediaType, (Object)this.mediaType)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.mediaType.hashCode();
   }

   public final String parameter(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      IntProgression var2 = RangesKt.step((IntProgression)ArraysKt.getIndices(this.parameterNamesAndValues), 2);
      int var3 = var2.getFirst();
      int var4 = var2.getLast();
      int var5 = var2.getStep();
      if (var5 >= 0) {
         if (var3 > var4) {
            return null;
         }
      } else if (var3 < var4) {
         return null;
      }

      while(!StringsKt.equals(this.parameterNamesAndValues[var3], var1, true)) {
         if (var3 == var4) {
            return null;
         }

         var3 += var5;
      }

      return this.parameterNamesAndValues[var3 + 1];
   }

   public final String subtype() {
      return this.subtype;
   }

   public String toString() {
      return this.mediaType;
   }

   public final String type() {
      return this.type;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\rJ\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u000fJ\u0011\u0010\u0010\u001a\u00020\u000b*\u00020\u0007H\u0007¢\u0006\u0002\b\nJ\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000b*\u00020\u0007H\u0007¢\u0006\u0002\b\u000eR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
      d2 = {"Lokhttp3/MediaType$Companion;", "", "()V", "PARAMETER", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "QUOTED", "", "TOKEN", "TYPE_SUBTYPE", "get", "Lokhttp3/MediaType;", "mediaType", "-deprecated_get", "parse", "-deprecated_parse", "toMediaType", "toMediaTypeOrNull", "okhttp"},
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

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "mediaType.toMediaType()",
   imports = {"okhttp3.MediaType.Companion.toMediaType"}
)
      )
      public final MediaType _deprecated_get/* $FF was: -deprecated_get*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "mediaType");
         return ((MediaType.Companion)this).get(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "mediaType.toMediaTypeOrNull()",
   imports = {"okhttp3.MediaType.Companion.toMediaTypeOrNull"}
)
      )
      public final MediaType _deprecated_parse/* $FF was: -deprecated_parse*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "mediaType");
         return ((MediaType.Companion)this).parse(var1);
      }

      @JvmStatic
      public final MediaType get(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toMediaType");
         Pattern var2 = MediaType.TYPE_SUBTYPE;
         CharSequence var3 = (CharSequence)var1;
         Matcher var10 = var2.matcher(var3);
         if (var10.lookingAt()) {
            String var4 = var10.group(1);
            Intrinsics.checkExpressionValueIsNotNull(var4, "typeSubtype.group(1)");
            Locale var5 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(var5, "Locale.US");
            if (var4 != null) {
               String var16 = var4.toLowerCase(var5);
               Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.String).toLowerCase(locale)");
               var4 = var10.group(2);
               Intrinsics.checkExpressionValueIsNotNull(var4, "typeSubtype.group(2)");
               Locale var6 = Locale.US;
               Intrinsics.checkExpressionValueIsNotNull(var6, "Locale.US");
               if (var4 != null) {
                  var4 = var4.toLowerCase(var6);
                  Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.String).toLowerCase(locale)");
                  List var18 = (List)(new ArrayList());
                  Matcher var7 = MediaType.PARAMETER.matcher(var3);
                  int var8 = var10.end();

                  while(var8 < var1.length()) {
                     var7.region(var8, var1.length());
                     String var15;
                     if (!var7.lookingAt()) {
                        StringBuilder var14 = new StringBuilder();
                        var14.append("Parameter is not formatted correctly: \"");
                        var15 = var1.substring(var8);
                        Intrinsics.checkExpressionValueIsNotNull(var15, "(this as java.lang.String).substring(startIndex)");
                        var14.append(var15);
                        var14.append("\" for: \"");
                        var14.append(var1);
                        var14.append('"');
                        throw (Throwable)(new IllegalArgumentException(var14.toString().toString()));
                     }

                     String var9 = var7.group(1);
                     if (var9 == null) {
                        var8 = var7.end();
                     } else {
                        String var11 = var7.group(2);
                        if (var11 == null) {
                           var15 = var7.group(3);
                        } else {
                           var15 = var11;
                           if (StringsKt.startsWith$default(var11, "'", false, 2, (Object)null)) {
                              var15 = var11;
                              if (StringsKt.endsWith$default(var11, "'", false, 2, (Object)null)) {
                                 var15 = var11;
                                 if (var11.length() > 2) {
                                    var15 = var11.substring(1, var11.length() - 1);
                                    Intrinsics.checkExpressionValueIsNotNull(var15, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                 }
                              }
                           }
                        }

                        Collection var12 = (Collection)var18;
                        var12.add(var9);
                        var12.add(var15);
                        var8 = var7.end();
                     }
                  }

                  Object[] var17 = ((Collection)var18).toArray(new String[0]);
                  if (var17 != null) {
                     return new MediaType(var1, var16, var4, (String[])var17, (DefaultConstructorMarker)null);
                  } else {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                  }
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         } else {
            StringBuilder var13 = new StringBuilder();
            var13.append("No subtype found for: \"");
            var13.append(var1);
            var13.append('"');
            throw (Throwable)(new IllegalArgumentException(var13.toString().toString()));
         }
      }

      @JvmStatic
      public final MediaType parse(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toMediaTypeOrNull");

         MediaType var3;
         try {
            var3 = ((MediaType.Companion)this).get(var1);
         } catch (IllegalArgumentException var2) {
            var3 = null;
         }

         return var3;
      }
   }
}
