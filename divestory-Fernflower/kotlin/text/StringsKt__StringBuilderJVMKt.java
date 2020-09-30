package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a-\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a/\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b\u001a\u001d\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b2\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001f\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u0012\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\rH\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u000eH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0010H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0012H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0013H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0014H\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0015H\u0087\b\u001a%\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a\u0014\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001d\u0010\u0017\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u0006H\u0087\b\u001a%\u0010\u0019\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a5\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a7\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a!\u0010\u001b\u001a\u00020\u001c*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\fH\u0087\n\u001a-\u0010\u001d\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0015H\u0087\b\u001a7\u0010\u001e\u001a\u00020\u001c*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u00062\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0087\b¨\u0006!"},
   d2 = {"appendRange", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "", "startIndex", "", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringBuilderJVMKt extends StringsKt__RegexExtensionsKt {
   public StringsKt__StringBuilderJVMKt() {
   }

   private static final StringBuilder appendRange(StringBuilder var0, CharSequence var1, int var2, int var3) {
      var0.append(var1, var2, var3);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.append(value, startIndex, endIndex)");
      return var0;
   }

   private static final StringBuilder appendRange(StringBuilder var0, char[] var1, int var2, int var3) {
      var0.append(var1, var2, var3 - var2);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.append(value, start…x, endIndex - startIndex)");
      return var0;
   }

   public static final Appendable appendln(Appendable var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendln");
      var0 = var0.append((CharSequence)SystemProperties.LINE_SEPARATOR);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(SystemProperties.LINE_SEPARATOR)");
      return var0;
   }

   private static final Appendable appendln(Appendable var0, char var1) {
      var0 = var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final Appendable appendln(Appendable var0, CharSequence var1) {
      var0 = var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   public static final StringBuilder appendln(StringBuilder var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendln");
      var0.append(SystemProperties.LINE_SEPARATOR);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(SystemProperties.LINE_SEPARATOR)");
      return var0;
   }

   private static final StringBuilder appendln(StringBuilder var0, byte var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value.toInt())");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, char var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, double var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, float var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, int var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, long var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, CharSequence var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, Object var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, String var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, StringBuffer var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, StringBuilder var1) {
      var0.append((CharSequence)var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, short var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value.toInt())");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, boolean var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   private static final StringBuilder appendln(StringBuilder var0, char[] var1) {
      var0.append(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "append(value)");
      return StringsKt.appendln(var0);
   }

   public static final StringBuilder clear(StringBuilder var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$clear");
      var0.setLength(0);
      return var0;
   }

   private static final StringBuilder deleteAt(StringBuilder var0, int var1) {
      var0 = var0.deleteCharAt(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.deleteCharAt(index)");
      return var0;
   }

   private static final StringBuilder deleteRange(StringBuilder var0, int var1, int var2) {
      var0 = var0.delete(var1, var2);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.delete(startIndex, endIndex)");
      return var0;
   }

   private static final StringBuilder insertRange(StringBuilder var0, int var1, CharSequence var2, int var3, int var4) {
      var0 = var0.insert(var1, var2, var3, var4);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.insert(index, value, startIndex, endIndex)");
      return var0;
   }

   private static final StringBuilder insertRange(StringBuilder var0, int var1, char[] var2, int var3, int var4) {
      var0 = var0.insert(var1, var2, var3, var4 - var3);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.insert(index, value…x, endIndex - startIndex)");
      return var0;
   }

   private static final void set(StringBuilder var0, int var1, char var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$set");
      var0.setCharAt(var1, var2);
   }

   private static final StringBuilder setRange(StringBuilder var0, int var1, int var2, String var3) {
      var0 = var0.replace(var1, var2, var3);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.replace(startIndex, endIndex, value)");
      return var0;
   }

   private static final void toCharArray(StringBuilder var0, char[] var1, int var2, int var3, int var4) {
      var0.getChars(var3, var4, var1, var2);
   }

   // $FF: synthetic method
   static void toCharArray$default(StringBuilder var0, char[] var1, int var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.length();
      }

      var0.getChars(var3, var4, var1, var2);
   }
}
