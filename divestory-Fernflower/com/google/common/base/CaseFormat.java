package com.google.common.base;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public enum CaseFormat {
   LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
      String normalizeFirstWord(String var1) {
         return Ascii.toLowerCase(var1);
      }

      String normalizeWord(String var1) {
         return CaseFormat.firstCharOnlyToUpper(var1);
      }
   },
   LOWER_HYPHEN(CharMatcher.is('-'), "-") {
      String convert(CaseFormat var1, String var2) {
         if (var1 == LOWER_UNDERSCORE) {
            return var2.replace('-', '_');
         } else {
            return var1 == UPPER_UNDERSCORE ? Ascii.toUpperCase(var2.replace('-', '_')) : super.convert(var1, var2);
         }
      }

      String normalizeWord(String var1) {
         return Ascii.toLowerCase(var1);
      }
   },
   LOWER_UNDERSCORE(CharMatcher.is('_'), "_") {
      String convert(CaseFormat var1, String var2) {
         if (var1 == LOWER_HYPHEN) {
            return var2.replace('_', '-');
         } else {
            return var1 == UPPER_UNDERSCORE ? Ascii.toUpperCase(var2) : super.convert(var1, var2);
         }
      }

      String normalizeWord(String var1) {
         return Ascii.toLowerCase(var1);
      }
   },
   UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
      String normalizeWord(String var1) {
         return CaseFormat.firstCharOnlyToUpper(var1);
      }
   },
   UPPER_UNDERSCORE;

   private final CharMatcher wordBoundary;
   private final String wordSeparator;

   static {
      CaseFormat var0 = new CaseFormat("UPPER_UNDERSCORE", 4, CharMatcher.is('_'), "_") {
         String convert(CaseFormat var1, String var2) {
            if (var1 == LOWER_HYPHEN) {
               return Ascii.toLowerCase(var2.replace('_', '-'));
            } else {
               return var1 == LOWER_UNDERSCORE ? Ascii.toLowerCase(var2) : super.convert(var1, var2);
            }
         }

         String normalizeWord(String var1) {
            return Ascii.toUpperCase(var1);
         }
      };
      UPPER_UNDERSCORE = var0;
   }

   private CaseFormat(CharMatcher var3, String var4) {
      this.wordBoundary = var3;
      this.wordSeparator = var4;
   }

   // $FF: synthetic method
   CaseFormat(CharMatcher var3, String var4, Object var5) {
      this(var3, var4);
   }

   private static String firstCharOnlyToUpper(String var0) {
      if (!var0.isEmpty()) {
         StringBuilder var1 = new StringBuilder();
         var1.append(Ascii.toUpperCase(var0.charAt(0)));
         var1.append(Ascii.toLowerCase(var0.substring(1)));
         var0 = var1.toString();
      }

      return var0;
   }

   String convert(CaseFormat var1, String var2) {
      StringBuilder var3 = null;
      int var4 = 0;
      int var5 = -1;

      while(true) {
         var5 = this.wordBoundary.indexIn(var2, var5 + 1);
         if (var5 == -1) {
            String var6;
            if (var4 == 0) {
               var6 = var1.normalizeFirstWord(var2);
            } else {
               var3.append(var1.normalizeWord(var2.substring(var4)));
               var6 = var3.toString();
            }

            return var6;
         }

         if (var4 == 0) {
            var3 = new StringBuilder(var2.length() + var1.wordSeparator.length() * 4);
            var3.append(var1.normalizeFirstWord(var2.substring(var4, var5)));
         } else {
            var3.append(var1.normalizeWord(var2.substring(var4, var5)));
         }

         var3.append(var1.wordSeparator);
         var4 = this.wordSeparator.length() + var5;
      }
   }

   public Converter<String, String> converterTo(CaseFormat var1) {
      return new CaseFormat.StringConverter(this, var1);
   }

   String normalizeFirstWord(String var1) {
      return this.normalizeWord(var1);
   }

   abstract String normalizeWord(String var1);

   public final String to(CaseFormat var1, String var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      if (var1 != this) {
         var2 = this.convert(var1, var2);
      }

      return var2;
   }

   private static final class StringConverter extends Converter<String, String> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final CaseFormat sourceFormat;
      private final CaseFormat targetFormat;

      StringConverter(CaseFormat var1, CaseFormat var2) {
         this.sourceFormat = (CaseFormat)Preconditions.checkNotNull(var1);
         this.targetFormat = (CaseFormat)Preconditions.checkNotNull(var2);
      }

      protected String doBackward(String var1) {
         return this.targetFormat.to(this.sourceFormat, var1);
      }

      protected String doForward(String var1) {
         return this.sourceFormat.to(this.targetFormat, var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof CaseFormat.StringConverter;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            CaseFormat.StringConverter var5 = (CaseFormat.StringConverter)var1;
            var4 = var3;
            if (this.sourceFormat.equals(var5.sourceFormat)) {
               var4 = var3;
               if (this.targetFormat.equals(var5.targetFormat)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.sourceFormat);
         var1.append(".converterTo(");
         var1.append(this.targetFormat);
         var1.append(")");
         return var1.toString();
      }
   }
}
