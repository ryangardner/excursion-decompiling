package kotlin.text;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
   d2 = {"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", "value", "Lkotlin/text/Regex;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class ScreenFloatValueRegEx {
   public static final ScreenFloatValueRegEx INSTANCE = new ScreenFloatValueRegEx();
   public static final Regex value;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append('(');
      var0.append("(\\p{Digit}+)");
      var0.append("(\\.)?(");
      var0.append("(\\p{Digit}+)");
      var0.append("?)(");
      var0.append("[eE][+-]?(\\p{Digit}+)");
      var0.append(")?)|");
      var0.append("(\\.(");
      var0.append("(\\p{Digit}+)");
      var0.append(")(");
      var0.append("[eE][+-]?(\\p{Digit}+)");
      var0.append(")?)|");
      var0.append("((");
      var0.append("(0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+))");
      var0.append(")[pP][+-]?");
      var0.append("(\\p{Digit}+)");
      var0.append(')');
      String var2 = var0.toString();
      StringBuilder var1 = new StringBuilder();
      var1.append("[\\x00-\\x20]*[+-]?(NaN|Infinity|((");
      var1.append(var2);
      var1.append(")[fFdD]?))[\\x00-\\x20]*");
      value = new Regex(var1.toString());
   }

   private ScreenFloatValueRegEx() {
   }
}
