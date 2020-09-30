package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0087\b\u001a\n\u0010)\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00105\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020(\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0087\b\u001a4\u0010B\u001a\u00020#*\u00020(2\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010B\u001a\u00020#*\u00020\u00022\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010E\u001a\u00020\u0002*\u00020(2\u0006\u0010F\u001a\u00020\u0011\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00020N*\u00020(2\u0006\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020\u0011\u001a\u001c\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010U\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010V\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010V\u001a\u00020\u0014*\u00020\u00022\u0006\u0010W\u001a\u00020\u00142\b\b\u0002\u0010X\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010V\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010Z\u001a\u00020P*\u00020\u00022\b\b\u0002\u0010[\u001a\u00020\u0011H\u0087\b\u001a\r\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006]"},
   d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "charSequence", "", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
   public StringsKt__StringsJVMKt() {
   }

   private static final String String(StringBuffer var0) {
      return new String(var0);
   }

   private static final String String(StringBuilder var0) {
      return new String(var0);
   }

   private static final String String(byte[] var0) {
      return new String(var0, Charsets.UTF_8);
   }

   private static final String String(byte[] var0, int var1, int var2) {
      return new String(var0, var1, var2, Charsets.UTF_8);
   }

   private static final String String(byte[] var0, int var1, int var2, Charset var3) {
      return new String(var0, var1, var2, var3);
   }

   private static final String String(byte[] var0, Charset var1) {
      return new String(var0, var1);
   }

   private static final String String(char[] var0) {
      return new String(var0);
   }

   private static final String String(char[] var0, int var1, int var2) {
      return new String(var0, var1, var2);
   }

   private static final String String(int[] var0, int var1, int var2) {
      return new String(var0, var1, var2);
   }

   public static final String capitalize(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$capitalize");
      boolean var1;
      if (((CharSequence)var0).length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      String var2 = var0;
      if (var1) {
         var2 = var0;
         if (Character.isLowerCase(var0.charAt(0))) {
            StringBuilder var4 = new StringBuilder();
            String var3 = var0.substring(0, 1);
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var3 = var3.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).toUpperCase()");
            var4.append(var3);
            var0 = var0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            var4.append(var0);
            var2 = var4.toString();
         }
      }

      return var2;
   }

   public static final String capitalize(String var0, Locale var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$capitalize");
      Intrinsics.checkParameterIsNotNull(var1, "locale");
      boolean var2;
      if (((CharSequence)var0).length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         char var3 = var0.charAt(0);
         if (Character.isLowerCase(var3)) {
            StringBuilder var4 = new StringBuilder();
            char var5 = Character.toTitleCase(var3);
            if (var5 != Character.toUpperCase(var3)) {
               var4.append(var5);
            } else {
               String var6 = var0.substring(0, 1);
               Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               if (var6 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               String var7 = var6.toUpperCase(var1);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.String).toUpperCase(locale)");
               var4.append(var7);
            }

            var0 = var0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            var4.append(var0);
            var0 = var4.toString();
            Intrinsics.checkExpressionValueIsNotNull(var0, "StringBuilder().apply(builderAction).toString()");
            return var0;
         }
      }

      return var0;
   }

   private static final int codePointAt(String var0, int var1) {
      if (var0 != null) {
         return var0.codePointAt(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int codePointBefore(String var0, int var1) {
      if (var0 != null) {
         return var0.codePointBefore(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int codePointCount(String var0, int var1, int var2) {
      if (var0 != null) {
         return var0.codePointCount(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final int compareTo(String var0, String var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$compareTo");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return var2 ? var0.compareToIgnoreCase(var1) : var0.compareTo(var1);
   }

   // $FF: synthetic method
   public static int compareTo$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.compareTo(var0, var1, var2);
   }

   public static final String concatToString(char[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$concatToString");
      return new String(var0);
   }

   public static final String concatToString(char[] var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$concatToString");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      return new String(var0, var1, var2 - var1);
   }

   // $FF: synthetic method
   public static String concatToString$default(char[] var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length;
      }

      return StringsKt.concatToString(var0, var1, var2);
   }

   private static final boolean contentEquals(String var0, CharSequence var1) {
      if (var0 != null) {
         return var0.contentEquals(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final boolean contentEquals(String var0, StringBuffer var1) {
      if (var0 != null) {
         return var0.contentEquals(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final String decapitalize(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$decapitalize");
      boolean var1;
      if (((CharSequence)var0).length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      String var2 = var0;
      if (var1) {
         var2 = var0;
         if (Character.isUpperCase(var0.charAt(0))) {
            StringBuilder var4 = new StringBuilder();
            String var3 = var0.substring(0, 1);
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var3 = var3.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).toLowerCase()");
            var4.append(var3);
            var0 = var0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            var4.append(var0);
            var2 = var4.toString();
         }
      }

      return var2;
   }

   public static final String decapitalize(String var0, Locale var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$decapitalize");
      Intrinsics.checkParameterIsNotNull(var1, "locale");
      boolean var2;
      if (((CharSequence)var0).length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      String var3 = var0;
      if (var2) {
         var3 = var0;
         if (!Character.isLowerCase(var0.charAt(0))) {
            StringBuilder var6 = new StringBuilder();
            String var4 = var0.substring(0, 1);
            Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (var4 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String var5 = var4.toLowerCase(var1);
            Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.String).toLowerCase(locale)");
            var6.append(var5);
            var0 = var0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            var6.append(var0);
            var3 = var6.toString();
         }
      }

      return var3;
   }

   public static final String decodeToString(byte[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$decodeToString");
      return new String(var0, Charsets.UTF_8);
   }

   public static final String decodeToString(byte[] var0, int var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$decodeToString");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      if (!var3) {
         return new String(var0, var1, var2 - var1, Charsets.UTF_8);
      } else {
         String var4 = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(var0, var1, var2 - var1)).toString();
         Intrinsics.checkExpressionValueIsNotNull(var4, "decoder.decode(ByteBuffe…- startIndex)).toString()");
         return var4;
      }
   }

   // $FF: synthetic method
   public static String decodeToString$default(byte[] var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.decodeToString(var0, var1, var2, var3);
   }

   public static final byte[] encodeToByteArray(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$encodeToByteArray");
      byte[] var1 = var0.getBytes(Charsets.UTF_8);
      Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).getBytes(charset)");
      return var1;
   }

   public static final byte[] encodeToByteArray(String var0, int var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$encodeToByteArray");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      byte[] var5;
      if (!var3) {
         String var7 = var0.substring(var1, var2);
         Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         Charset var6 = Charsets.UTF_8;
         if (var7 != null) {
            var5 = var7.getBytes(var6);
            Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.String).getBytes(charset)");
            return var5;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      } else {
         ByteBuffer var4 = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap((CharSequence)var0, var1, var2));
         if (var4.hasArray() && var4.arrayOffset() == 0) {
            var1 = var4.remaining();
            var5 = var4.array();
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            if (var1 == var5.length) {
               var5 = var4.array();
               Intrinsics.checkExpressionValueIsNotNull(var5, "byteBuffer.array()");
               return var5;
            }
         }

         var5 = new byte[var4.remaining()];
         var4.get(var5);
         return var5;
      }
   }

   // $FF: synthetic method
   public static byte[] encodeToByteArray$default(String var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length();
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.encodeToByteArray(var0, var1, var2, var3);
   }

   public static final boolean endsWith(String var0, String var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return !var2 ? var0.endsWith(var1) : StringsKt.regionMatches(var0, var0.length() - var1.length(), var1, 0, var1.length(), true);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   public static final boolean equals(String var0, String var1, boolean var2) {
      if (var0 == null) {
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         if (!var2) {
            var2 = var0.equals(var1);
         } else {
            var2 = var0.equalsIgnoreCase(var1);
         }

         return var2;
      }
   }

   // $FF: synthetic method
   public static boolean equals$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.equals(var0, var1, var2);
   }

   private static final String format(String var0, Locale var1, Object... var2) {
      var0 = String.format(var1, var0, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkExpressionValueIsNotNull(var0, "java.lang.String.format(locale, this, *args)");
      return var0;
   }

   private static final String format(String var0, Object... var1) {
      var0 = String.format(var0, Arrays.copyOf(var1, var1.length));
      Intrinsics.checkExpressionValueIsNotNull(var0, "java.lang.String.format(this, *args)");
      return var0;
   }

   private static final String format(StringCompanionObject var0, String var1, Object... var2) {
      String var3 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkExpressionValueIsNotNull(var3, "java.lang.String.format(format, *args)");
      return var3;
   }

   private static final String format(StringCompanionObject var0, Locale var1, String var2, Object... var3) {
      String var4 = String.format(var1, var2, Arrays.copyOf(var3, var3.length));
      Intrinsics.checkExpressionValueIsNotNull(var4, "java.lang.String.format(locale, format, *args)");
      return var4;
   }

   public static final Comparator<String> getCASE_INSENSITIVE_ORDER(StringCompanionObject var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$CASE_INSENSITIVE_ORDER");
      Comparator var1 = String.CASE_INSENSITIVE_ORDER;
      Intrinsics.checkExpressionValueIsNotNull(var1, "java.lang.String.CASE_INSENSITIVE_ORDER");
      return var1;
   }

   private static final String intern(String var0) {
      if (var0 != null) {
         var0 = var0.intern();
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).intern()");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final boolean isBlank(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$isBlank");
      int var1 = var0.length();
      boolean var2 = false;
      if (var1 != 0) {
         boolean var4;
         label35: {
            Iterable var3 = (Iterable)StringsKt.getIndices(var0);
            if (!(var3 instanceof Collection) || !((Collection)var3).isEmpty()) {
               Iterator var5 = var3.iterator();

               while(var5.hasNext()) {
                  if (!CharsKt.isWhitespace(var0.charAt(((IntIterator)var5).nextInt()))) {
                     var4 = false;
                     break label35;
                  }
               }
            }

            var4 = true;
         }

         if (!var4) {
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   private static final int nativeIndexOf(String var0, char var1, int var2) {
      if (var0 != null) {
         return var0.indexOf(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int nativeIndexOf(String var0, String var1, int var2) {
      if (var0 != null) {
         return var0.indexOf(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int nativeLastIndexOf(String var0, char var1, int var2) {
      if (var0 != null) {
         return var0.lastIndexOf(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int nativeLastIndexOf(String var0, String var1, int var2) {
      if (var0 != null) {
         return var0.lastIndexOf(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final int offsetByCodePoints(String var0, int var1, int var2) {
      if (var0 != null) {
         return var0.offsetByCodePoints(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final boolean regionMatches(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$regionMatches");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      return var0 instanceof String && var2 instanceof String ? StringsKt.regionMatches((String)var0, var1, (String)var2, var3, var4, var5) : StringsKt.regionMatchesImpl(var0, var1, var2, var3, var4, var5);
   }

   public static final boolean regionMatches(String var0, int var1, String var2, int var3, int var4, boolean var5) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$regionMatches");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      if (!var5) {
         var5 = var0.regionMatches(var1, var2, var3, var4);
      } else {
         var5 = var0.regionMatches(var5, var1, var2, var3, var4);
      }

      return var5;
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(String var0, int var1, String var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   public static final String repeat(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$repeat");
      byte var2 = 0;
      byte var3 = 1;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         StringBuilder var8 = new StringBuilder();
         var8.append("Count 'n' must be non-negative, but was ");
         var8.append(var1);
         var8.append('.');
         throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
      } else {
         String var5 = "";
         String var6 = var5;
         if (var1 != 0) {
            if (var1 != 1) {
               int var10 = var0.length();
               var6 = var5;
               if (var10 != 0) {
                  if (var10 != 1) {
                     StringBuilder var11 = new StringBuilder(var0.length() * var1);
                     if (1 <= var1) {
                        var10 = var3;

                        while(true) {
                           var11.append(var0);
                           if (var10 == var1) {
                              break;
                           }

                           ++var10;
                        }
                     }

                     var6 = var11.toString();
                     Intrinsics.checkExpressionValueIsNotNull(var6, "sb.toString()");
                  } else {
                     char var9 = var0.charAt(0);
                     char[] var7 = new char[var1];

                     for(var10 = var2; var10 < var1; ++var10) {
                        var7[var10] = (char)var9;
                     }

                     var6 = new String(var7);
                  }
               }
            } else {
               var6 = var0.toString();
            }
         }

         return var6;
      }
   }

   public static final String replace(String var0, char var1, char var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replace");
      if (!var3) {
         var0 = var0.replace(var1, var2);
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…replace(oldChar, newChar)");
         return var0;
      } else {
         return SequencesKt.joinToString$default(StringsKt.splitToSequence$default((CharSequence)var0, new char[]{var1}, var3, 0, 4, (Object)null), (CharSequence)String.valueOf(var2), (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
      }
   }

   public static final String replace(String var0, String var1, String var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replace");
      Intrinsics.checkParameterIsNotNull(var1, "oldValue");
      Intrinsics.checkParameterIsNotNull(var2, "newValue");
      return SequencesKt.joinToString$default(StringsKt.splitToSequence$default((CharSequence)var0, new String[]{var1}, var3, 0, 4, (Object)null), (CharSequence)var2, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   // $FF: synthetic method
   public static String replace$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replace$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   public static final String replaceFirst(String var0, char var1, char var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceFirst");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.indexOf$default(var4, var1, 0, var3, 2, (Object)null);
      if (var5 >= 0) {
         var0 = StringsKt.replaceRange(var4, var5, var5 + 1, (CharSequence)String.valueOf(var2)).toString();
      }

      return var0;
   }

   public static final String replaceFirst(String var0, String var1, String var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceFirst");
      Intrinsics.checkParameterIsNotNull(var1, "oldValue");
      Intrinsics.checkParameterIsNotNull(var2, "newValue");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.indexOf$default(var4, var1, 0, var3, 2, (Object)null);
      if (var5 >= 0) {
         var0 = StringsKt.replaceRange(var4, var5, var1.length() + var5, (CharSequence)var2).toString();
      }

      return var0;
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   public static final List<String> split(CharSequence var0, Pattern var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$split");
      Intrinsics.checkParameterIsNotNull(var1, "regex");
      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         int var6 = var2;
         if (var2 == 0) {
            var6 = -1;
         }

         String[] var5 = var1.split(var0, var6);
         Intrinsics.checkExpressionValueIsNotNull(var5, "regex.split(this, if (limit == 0) -1 else limit)");
         return ArraysKt.asList(var5);
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Limit must be non-negative, but was ");
         var4.append(var2);
         var4.append('.');
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, Pattern var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return StringsKt.split(var0, var1, var2);
   }

   public static final boolean startsWith(String var0, String var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return !var3 ? var0.startsWith(var1, var2) : StringsKt.regionMatches(var0, var2, var1, 0, var1.length(), var3);
   }

   public static final boolean startsWith(String var0, String var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return !var2 ? var0.startsWith(var1) : StringsKt.regionMatches(var0, 0, var1, 0, var1.length(), var2);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   private static final String substring(String var0, int var1) {
      if (var0 != null) {
         var0 = var0.substring(var1);
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final String substring(String var0, int var1, int var2) {
      if (var0 != null) {
         var0 = var0.substring(var1, var2);
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final byte[] toByteArray(String var0, Charset var1) {
      if (var0 != null) {
         byte[] var2 = var0.getBytes(var1);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).getBytes(charset)");
         return var2;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   // $FF: synthetic method
   static byte[] toByteArray$default(String var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if (var0 != null) {
         byte[] var4 = var0.getBytes(var1);
         Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.String).getBytes(charset)");
         return var4;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final char[] toCharArray(String var0) {
      if (var0 != null) {
         char[] var1 = var0.toCharArray();
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toCharArray()");
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final char[] toCharArray(String var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toCharArray");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      char[] var3 = new char[var2 - var1];
      var0.getChars(var1, var2, var3, 0);
      return var3;
   }

   private static final char[] toCharArray(String var0, char[] var1, int var2, int var3, int var4) {
      if (var0 != null) {
         var0.getChars(var3, var4, var1, var2);
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   // $FF: synthetic method
   public static char[] toCharArray$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return StringsKt.toCharArray(var0, var1, var2);
   }

   // $FF: synthetic method
   static char[] toCharArray$default(String var0, char[] var1, int var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.length();
      }

      if (var0 != null) {
         var0.getChars(var3, var4, var1, var2);
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final String toLowerCase(String var0) {
      if (var0 != null) {
         var0 = var0.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toLowerCase()");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final String toLowerCase(String var0, Locale var1) {
      if (var0 != null) {
         var0 = var0.toLowerCase(var1);
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toLowerCase(locale)");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final Pattern toPattern(String var0, int var1) {
      Pattern var2 = Pattern.compile(var0, var1);
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.util.regex.Pattern.compile(this, flags)");
      return var2;
   }

   // $FF: synthetic method
   static Pattern toPattern$default(String var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 0;
      }

      Pattern var4 = Pattern.compile(var0, var1);
      Intrinsics.checkExpressionValueIsNotNull(var4, "java.util.regex.Pattern.compile(this, flags)");
      return var4;
   }

   private static final String toUpperCase(String var0) {
      if (var0 != null) {
         var0 = var0.toUpperCase();
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toUpperCase()");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   private static final String toUpperCase(String var0, Locale var1) {
      if (var0 != null) {
         var0 = var0.toUpperCase(var1);
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toUpperCase(locale)");
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }
}
