package kotlin.text;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b \b\u0086\u0001\u0018\u0000 -2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001-B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,¨\u0006."},
   d2 = {"Lkotlin/text/CharCategory;", "", "value", "", "code", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getValue", "()I", "contains", "", "char", "", "UNASSIGNED", "UPPERCASE_LETTER", "LOWERCASE_LETTER", "TITLECASE_LETTER", "MODIFIER_LETTER", "OTHER_LETTER", "NON_SPACING_MARK", "ENCLOSING_MARK", "COMBINING_SPACING_MARK", "DECIMAL_DIGIT_NUMBER", "LETTER_NUMBER", "OTHER_NUMBER", "SPACE_SEPARATOR", "LINE_SEPARATOR", "PARAGRAPH_SEPARATOR", "CONTROL", "FORMAT", "PRIVATE_USE", "SURROGATE", "DASH_PUNCTUATION", "START_PUNCTUATION", "END_PUNCTUATION", "CONNECTOR_PUNCTUATION", "OTHER_PUNCTUATION", "MATH_SYMBOL", "CURRENCY_SYMBOL", "MODIFIER_SYMBOL", "OTHER_SYMBOL", "INITIAL_QUOTE_PUNCTUATION", "FINAL_QUOTE_PUNCTUATION", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum CharCategory {
   COMBINING_SPACING_MARK,
   CONNECTOR_PUNCTUATION,
   CONTROL,
   CURRENCY_SYMBOL;

   public static final CharCategory.Companion Companion;
   DASH_PUNCTUATION,
   DECIMAL_DIGIT_NUMBER,
   ENCLOSING_MARK,
   END_PUNCTUATION,
   FINAL_QUOTE_PUNCTUATION,
   FORMAT,
   INITIAL_QUOTE_PUNCTUATION,
   LETTER_NUMBER,
   LINE_SEPARATOR,
   LOWERCASE_LETTER,
   MATH_SYMBOL,
   MODIFIER_LETTER,
   MODIFIER_SYMBOL,
   NON_SPACING_MARK,
   OTHER_LETTER,
   OTHER_NUMBER,
   OTHER_PUNCTUATION,
   OTHER_SYMBOL,
   PARAGRAPH_SEPARATOR,
   PRIVATE_USE,
   SPACE_SEPARATOR,
   START_PUNCTUATION,
   SURROGATE,
   TITLECASE_LETTER,
   UNASSIGNED,
   UPPERCASE_LETTER;

   private static final Lazy categoryMap$delegate;
   private final String code;
   private final int value;

   static {
      CharCategory var0 = new CharCategory("UNASSIGNED", 0, 0, "Cn");
      UNASSIGNED = var0;
      CharCategory var1 = new CharCategory("UPPERCASE_LETTER", 1, 1, "Lu");
      UPPERCASE_LETTER = var1;
      CharCategory var2 = new CharCategory("LOWERCASE_LETTER", 2, 2, "Ll");
      LOWERCASE_LETTER = var2;
      CharCategory var3 = new CharCategory("TITLECASE_LETTER", 3, 3, "Lt");
      TITLECASE_LETTER = var3;
      CharCategory var4 = new CharCategory("MODIFIER_LETTER", 4, 4, "Lm");
      MODIFIER_LETTER = var4;
      CharCategory var5 = new CharCategory("OTHER_LETTER", 5, 5, "Lo");
      OTHER_LETTER = var5;
      CharCategory var6 = new CharCategory("NON_SPACING_MARK", 6, 6, "Mn");
      NON_SPACING_MARK = var6;
      CharCategory var7 = new CharCategory("ENCLOSING_MARK", 7, 7, "Me");
      ENCLOSING_MARK = var7;
      CharCategory var8 = new CharCategory("COMBINING_SPACING_MARK", 8, 8, "Mc");
      COMBINING_SPACING_MARK = var8;
      CharCategory var9 = new CharCategory("DECIMAL_DIGIT_NUMBER", 9, 9, "Nd");
      DECIMAL_DIGIT_NUMBER = var9;
      CharCategory var10 = new CharCategory("LETTER_NUMBER", 10, 10, "Nl");
      LETTER_NUMBER = var10;
      CharCategory var11 = new CharCategory("OTHER_NUMBER", 11, 11, "No");
      OTHER_NUMBER = var11;
      CharCategory var12 = new CharCategory("SPACE_SEPARATOR", 12, 12, "Zs");
      SPACE_SEPARATOR = var12;
      CharCategory var13 = new CharCategory("LINE_SEPARATOR", 13, 13, "Zl");
      LINE_SEPARATOR = var13;
      CharCategory var14 = new CharCategory("PARAGRAPH_SEPARATOR", 14, 14, "Zp");
      PARAGRAPH_SEPARATOR = var14;
      CharCategory var15 = new CharCategory("CONTROL", 15, 15, "Cc");
      CONTROL = var15;
      CharCategory var16 = new CharCategory("FORMAT", 16, 16, "Cf");
      FORMAT = var16;
      CharCategory var17 = new CharCategory("PRIVATE_USE", 17, 18, "Co");
      PRIVATE_USE = var17;
      CharCategory var18 = new CharCategory("SURROGATE", 18, 19, "Cs");
      SURROGATE = var18;
      CharCategory var19 = new CharCategory("DASH_PUNCTUATION", 19, 20, "Pd");
      DASH_PUNCTUATION = var19;
      CharCategory var20 = new CharCategory("START_PUNCTUATION", 20, 21, "Ps");
      START_PUNCTUATION = var20;
      CharCategory var21 = new CharCategory("END_PUNCTUATION", 21, 22, "Pe");
      END_PUNCTUATION = var21;
      CharCategory var22 = new CharCategory("CONNECTOR_PUNCTUATION", 22, 23, "Pc");
      CONNECTOR_PUNCTUATION = var22;
      CharCategory var23 = new CharCategory("OTHER_PUNCTUATION", 23, 24, "Po");
      OTHER_PUNCTUATION = var23;
      CharCategory var24 = new CharCategory("MATH_SYMBOL", 24, 25, "Sm");
      MATH_SYMBOL = var24;
      CharCategory var25 = new CharCategory("CURRENCY_SYMBOL", 25, 26, "Sc");
      CURRENCY_SYMBOL = var25;
      CharCategory var26 = new CharCategory("MODIFIER_SYMBOL", 26, 27, "Sk");
      MODIFIER_SYMBOL = var26;
      CharCategory var27 = new CharCategory("OTHER_SYMBOL", 27, 28, "So");
      OTHER_SYMBOL = var27;
      CharCategory var28 = new CharCategory("INITIAL_QUOTE_PUNCTUATION", 28, 29, "Pi");
      INITIAL_QUOTE_PUNCTUATION = var28;
      CharCategory var29 = new CharCategory("FINAL_QUOTE_PUNCTUATION", 29, 30, "Pf");
      FINAL_QUOTE_PUNCTUATION = var29;
      Companion = new CharCategory.Companion((DefaultConstructorMarker)null);
      categoryMap$delegate = LazyKt.lazy((Function0)null.INSTANCE);
   }

   private CharCategory(int var3, String var4) {
      this.value = var3;
      this.code = var4;
   }

   public final boolean contains(char var1) {
      boolean var2;
      if (Character.getType(var1) == this.value) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final String getCode() {
      return this.code;
   }

   public final int getValue() {
      return this.value;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/text/CharCategory$Companion;", "", "()V", "categoryMap", "", "", "Lkotlin/text/CharCategory;", "getCategoryMap", "()Ljava/util/Map;", "categoryMap$delegate", "Lkotlin/Lazy;", "valueOf", "category", "kotlin-stdlib"},
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

      private final Map<Integer, CharCategory> getCategoryMap() {
         Lazy var1 = CharCategory.categoryMap$delegate;
         CharCategory.Companion var2 = CharCategory.Companion;
         return (Map)var1.getValue();
      }

      public final CharCategory valueOf(int var1) {
         CharCategory var2 = (CharCategory)((CharCategory.Companion)this).getCategoryMap().get(var1);
         if (var2 != null) {
            return var2;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Category #");
            var3.append(var1);
            var3.append(" is not defined.");
            throw (Throwable)(new IllegalArgumentException(var3.toString()));
         }
      }
   }
}
