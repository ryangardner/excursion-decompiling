package kotlin.text;

import kotlin.Metadata;
import kotlin.ranges.IntRange;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0001\u001a\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0000\u001a\r\u0010\u000e\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0010\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0011\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0012\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0013\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0018\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u001a\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\r\u0010\u001b\u001a\u00020\u000f*\u00020\u0002H\u0087\b\u001a\n\u0010\u001c\u001a\u00020\u000f*\u00020\u0002\u001a\r\u0010\u001d\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\u001e\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\u001f\u001a\u00020\u0002*\u00020\u0002H\u0087\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006 "},
   d2 = {"category", "Lkotlin/text/CharCategory;", "", "getCategory", "(C)Lkotlin/text/CharCategory;", "directionality", "Lkotlin/text/CharDirectionality;", "getDirectionality", "(C)Lkotlin/text/CharDirectionality;", "checkRadix", "", "radix", "digitOf", "char", "isDefined", "", "isDigit", "isHighSurrogate", "isISOControl", "isIdentifierIgnorable", "isJavaIdentifierPart", "isJavaIdentifierStart", "isLetter", "isLetterOrDigit", "isLowSurrogate", "isLowerCase", "isTitleCase", "isUpperCase", "isWhitespace", "toLowerCase", "toTitleCase", "toUpperCase", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/CharsKt"
)
class CharsKt__CharJVMKt {
   public CharsKt__CharJVMKt() {
   }

   public static final int checkRadix(int var0) {
      if (2 <= var0 && 36 >= var0) {
         return var0;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("radix ");
         var1.append(var0);
         var1.append(" was not in valid range ");
         var1.append(new IntRange(2, 36));
         throw (Throwable)(new IllegalArgumentException(var1.toString()));
      }
   }

   public static final int digitOf(char var0, int var1) {
      return Character.digit(var0, var1);
   }

   public static final CharCategory getCategory(char var0) {
      return CharCategory.Companion.valueOf(Character.getType(var0));
   }

   public static final CharDirectionality getDirectionality(char var0) {
      return CharDirectionality.Companion.valueOf(Character.getDirectionality(var0));
   }

   private static final boolean isDefined(char var0) {
      return Character.isDefined(var0);
   }

   private static final boolean isDigit(char var0) {
      return Character.isDigit(var0);
   }

   private static final boolean isHighSurrogate(char var0) {
      return Character.isHighSurrogate(var0);
   }

   private static final boolean isISOControl(char var0) {
      return Character.isISOControl(var0);
   }

   private static final boolean isIdentifierIgnorable(char var0) {
      return Character.isIdentifierIgnorable(var0);
   }

   private static final boolean isJavaIdentifierPart(char var0) {
      return Character.isJavaIdentifierPart(var0);
   }

   private static final boolean isJavaIdentifierStart(char var0) {
      return Character.isJavaIdentifierStart(var0);
   }

   private static final boolean isLetter(char var0) {
      return Character.isLetter(var0);
   }

   private static final boolean isLetterOrDigit(char var0) {
      return Character.isLetterOrDigit(var0);
   }

   private static final boolean isLowSurrogate(char var0) {
      return Character.isLowSurrogate(var0);
   }

   private static final boolean isLowerCase(char var0) {
      return Character.isLowerCase(var0);
   }

   private static final boolean isTitleCase(char var0) {
      return Character.isTitleCase(var0);
   }

   private static final boolean isUpperCase(char var0) {
      return Character.isUpperCase(var0);
   }

   public static final boolean isWhitespace(char var0) {
      boolean var1;
      if (!Character.isWhitespace(var0) && !Character.isSpaceChar(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static final char toLowerCase(char var0) {
      return Character.toLowerCase(var0);
   }

   private static final char toTitleCase(char var0) {
      return Character.toTitleCase(var0);
   }

   private static final char toUpperCase(char var0) {
      return Character.toUpperCase(var0);
   }
}
