package com.google.common.base;

import java.util.Arrays;
import java.util.BitSet;

public abstract class CharMatcher implements Predicate<Character> {
   private static final int DISTINCT_CHARS = 65536;

   protected CharMatcher() {
   }

   public static CharMatcher any() {
      return CharMatcher.Any.INSTANCE;
   }

   public static CharMatcher anyOf(CharSequence var0) {
      int var1 = var0.length();
      if (var1 != 0) {
         if (var1 != 1) {
            return (CharMatcher)(var1 != 2 ? new CharMatcher.AnyOf(var0) : isEither(var0.charAt(0), var0.charAt(1)));
         } else {
            return is(var0.charAt(0));
         }
      } else {
         return none();
      }
   }

   public static CharMatcher ascii() {
      return CharMatcher.Ascii.INSTANCE;
   }

   public static CharMatcher breakingWhitespace() {
      return CharMatcher.BreakingWhitespace.INSTANCE;
   }

   @Deprecated
   public static CharMatcher digit() {
      return CharMatcher.Digit.INSTANCE;
   }

   private String finishCollapseFrom(CharSequence var1, int var2, int var3, char var4, StringBuilder var5, boolean var6) {
      for(boolean var7 = var6; var2 < var3; var7 = var6) {
         char var8 = var1.charAt(var2);
         if (this.matches(var8)) {
            var6 = var7;
            if (!var7) {
               var5.append(var4);
               var6 = true;
            }
         } else {
            var5.append(var8);
            var6 = false;
         }

         ++var2;
      }

      return var5.toString();
   }

   public static CharMatcher forPredicate(Predicate<? super Character> var0) {
      Object var1;
      if (var0 instanceof CharMatcher) {
         var1 = (CharMatcher)var0;
      } else {
         var1 = new CharMatcher.ForPredicate(var0);
      }

      return (CharMatcher)var1;
   }

   public static CharMatcher inRange(char var0, char var1) {
      return new CharMatcher.InRange(var0, var1);
   }

   @Deprecated
   public static CharMatcher invisible() {
      return CharMatcher.Invisible.INSTANCE;
   }

   public static CharMatcher is(char var0) {
      return new CharMatcher.Is(var0);
   }

   private static CharMatcher.IsEither isEither(char var0, char var1) {
      return new CharMatcher.IsEither(var0, var1);
   }

   public static CharMatcher isNot(char var0) {
      return new CharMatcher.IsNot(var0);
   }

   private static boolean isSmall(int var0, int var1) {
      boolean var2;
      if (var0 <= 1023 && var1 > var0 * 4 * 16) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public static CharMatcher javaDigit() {
      return CharMatcher.JavaDigit.INSTANCE;
   }

   public static CharMatcher javaIsoControl() {
      return CharMatcher.JavaIsoControl.INSTANCE;
   }

   @Deprecated
   public static CharMatcher javaLetter() {
      return CharMatcher.JavaLetter.INSTANCE;
   }

   @Deprecated
   public static CharMatcher javaLetterOrDigit() {
      return CharMatcher.JavaLetterOrDigit.INSTANCE;
   }

   @Deprecated
   public static CharMatcher javaLowerCase() {
      return CharMatcher.JavaLowerCase.INSTANCE;
   }

   @Deprecated
   public static CharMatcher javaUpperCase() {
      return CharMatcher.JavaUpperCase.INSTANCE;
   }

   public static CharMatcher none() {
      return CharMatcher.None.INSTANCE;
   }

   public static CharMatcher noneOf(CharSequence var0) {
      return anyOf(var0).negate();
   }

   private static CharMatcher precomputedPositive(int var0, BitSet var1, String var2) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               Object var4;
               if (isSmall(var0, var1.length())) {
                  var4 = SmallCharMatcher.from(var1, var2);
               } else {
                  var4 = new CharMatcher.BitSetMatcher(var1, var2);
               }

               return (CharMatcher)var4;
            } else {
               char var3 = (char)var1.nextSetBit(0);
               return isEither(var3, (char)var1.nextSetBit(var3 + 1));
            }
         } else {
            return is((char)var1.nextSetBit(0));
         }
      } else {
         return none();
      }
   }

   private static String showCharacter(char var0) {
      char[] var1 = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
      byte var2 = 0;
      char var3 = var0;

      for(int var4 = var2; var4 < 4; ++var4) {
         var1[5 - var4] = "0123456789ABCDEF".charAt(var3 & 15);
         var3 = (char)(var3 >> 4);
      }

      return String.copyValueOf(var1);
   }

   @Deprecated
   public static CharMatcher singleWidth() {
      return CharMatcher.SingleWidth.INSTANCE;
   }

   public static CharMatcher whitespace() {
      return CharMatcher.Whitespace.INSTANCE;
   }

   public CharMatcher and(CharMatcher var1) {
      return new CharMatcher.And(this, var1);
   }

   @Deprecated
   public boolean apply(Character var1) {
      return this.matches(var1);
   }

   public String collapseFrom(CharSequence var1, char var2) {
      int var3 = var1.length();

      int var6;
      for(int var4 = 0; var4 < var3; var4 = var6 + 1) {
         char var5 = var1.charAt(var4);
         var6 = var4;
         if (this.matches(var5)) {
            if (var5 != var2 || var4 != var3 - 1 && this.matches(var1.charAt(var4 + 1))) {
               StringBuilder var7 = new StringBuilder(var3);
               var7.append(var1, 0, var4);
               var7.append(var2);
               return this.finishCollapseFrom(var1, var4 + 1, var3, var2, var7, true);
            }

            var6 = var4 + 1;
         }
      }

      return var1.toString();
   }

   public int countIn(CharSequence var1) {
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var1.length(); var3 = var4) {
         var4 = var3;
         if (this.matches(var1.charAt(var2))) {
            var4 = var3 + 1;
         }

         ++var2;
      }

      return var3;
   }

   public int indexIn(CharSequence var1) {
      return this.indexIn(var1, 0);
   }

   public int indexIn(CharSequence var1, int var2) {
      int var3 = var1.length();
      Preconditions.checkPositionIndex(var2, var3);

      while(var2 < var3) {
         if (this.matches(var1.charAt(var2))) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public int lastIndexIn(CharSequence var1) {
      for(int var2 = var1.length() - 1; var2 >= 0; --var2) {
         if (this.matches(var1.charAt(var2))) {
            return var2;
         }
      }

      return -1;
   }

   public abstract boolean matches(char var1);

   public boolean matchesAllOf(CharSequence var1) {
      for(int var2 = var1.length() - 1; var2 >= 0; --var2) {
         if (!this.matches(var1.charAt(var2))) {
            return false;
         }
      }

      return true;
   }

   public boolean matchesAnyOf(CharSequence var1) {
      return this.matchesNoneOf(var1) ^ true;
   }

   public boolean matchesNoneOf(CharSequence var1) {
      boolean var2;
      if (this.indexIn(var1) == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public CharMatcher negate() {
      return new CharMatcher.Negated(this);
   }

   public CharMatcher or(CharMatcher var1) {
      return new CharMatcher.Or(this, var1);
   }

   public CharMatcher precomputed() {
      return Platform.precomputeCharMatcher(this);
   }

   CharMatcher precomputedInternal() {
      BitSet var1 = new BitSet();
      this.setBits(var1);
      int var2 = var1.cardinality();
      if (var2 * 2 <= 65536) {
         return precomputedPositive(var2, var1, this.toString());
      } else {
         var1.flip(0, 65536);
         final String var3 = this.toString();
         String var4;
         if (var3.endsWith(".negate()")) {
            var4 = var3.substring(0, var3.length() - 9);
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append(var3);
            var5.append(".negate()");
            var4 = var5.toString();
         }

         return new CharMatcher.NegatedFastMatcher(precomputedPositive(65536 - var2, var1, var4)) {
            public String toString() {
               return var3;
            }
         };
      }
   }

   public String removeFrom(CharSequence var1) {
      String var4 = var1.toString();
      int var2 = this.indexIn(var4);
      if (var2 == -1) {
         return var4;
      } else {
         char[] var5 = var4.toCharArray();
         int var3 = 1;

         label23:
         while(true) {
            ++var2;

            while(var2 != var5.length) {
               if (this.matches(var5[var2])) {
                  ++var3;
                  continue label23;
               }

               var5[var2 - var3] = var5[var2];
               ++var2;
            }

            return new String(var5, 0, var2 - var3);
         }
      }
   }

   public String replaceFrom(CharSequence var1, char var2) {
      String var5 = var1.toString();
      int var3 = this.indexIn(var5);
      if (var3 == -1) {
         return var5;
      } else {
         char[] var6 = var5.toCharArray();
         var6[var3] = (char)var2;

         while(true) {
            int var4 = var3 + 1;
            if (var4 >= var6.length) {
               return new String(var6);
            }

            var3 = var4;
            if (this.matches(var6[var4])) {
               var6[var4] = (char)var2;
               var3 = var4;
            }
         }
      }
   }

   public String replaceFrom(CharSequence var1, CharSequence var2) {
      int var3 = var2.length();
      if (var3 == 0) {
         return this.removeFrom(var1);
      } else {
         int var4 = 0;
         if (var3 == 1) {
            return this.replaceFrom(var1, var2.charAt(0));
         } else {
            String var5 = var1.toString();
            var3 = this.indexIn(var5);
            if (var3 == -1) {
               return var5;
            } else {
               int var6 = var5.length();
               StringBuilder var9 = new StringBuilder(var6 * 3 / 2 + 16);

               int var7;
               int var8;
               do {
                  var9.append(var5, var4, var3);
                  var9.append(var2);
                  var7 = var3 + 1;
                  var8 = this.indexIn(var5, var7);
                  var3 = var8;
                  var4 = var7;
               } while(var8 != -1);

               var9.append(var5, var7, var6);
               return var9.toString();
            }
         }
      }
   }

   public String retainFrom(CharSequence var1) {
      return this.negate().removeFrom(var1);
   }

   void setBits(BitSet var1) {
      for(int var2 = 65535; var2 >= 0; --var2) {
         if (this.matches((char)var2)) {
            var1.set(var2);
         }
      }

   }

   public String toString() {
      return super.toString();
   }

   public String trimAndCollapseFrom(CharSequence var1, char var2) {
      int var3 = var1.length();
      int var4 = var3 - 1;

      int var5;
      for(var5 = 0; var5 < var3 && this.matches(var1.charAt(var5)); ++var5) {
      }

      for(var3 = var4; var3 > var5 && this.matches(var1.charAt(var3)); --var3) {
      }

      String var6;
      if (var5 == 0 && var3 == var4) {
         var6 = this.collapseFrom(var1, var2);
      } else {
         ++var3;
         var6 = this.finishCollapseFrom(var1, var5, var3, var2, new StringBuilder(var3 - var5), false);
      }

      return var6;
   }

   public String trimFrom(CharSequence var1) {
      int var2 = var1.length();

      int var3;
      for(var3 = 0; var3 < var2 && this.matches(var1.charAt(var3)); ++var3) {
      }

      --var2;

      while(var2 > var3 && this.matches(var1.charAt(var2))) {
         --var2;
      }

      return var1.subSequence(var3, var2 + 1).toString();
   }

   public String trimLeadingFrom(CharSequence var1) {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!this.matches(var1.charAt(var3))) {
            return var1.subSequence(var3, var2).toString();
         }
      }

      return "";
   }

   public String trimTrailingFrom(CharSequence var1) {
      for(int var2 = var1.length() - 1; var2 >= 0; --var2) {
         if (!this.matches(var1.charAt(var2))) {
            return var1.subSequence(0, var2 + 1).toString();
         }
      }

      return "";
   }

   private static final class And extends CharMatcher {
      final CharMatcher first;
      final CharMatcher second;

      And(CharMatcher var1, CharMatcher var2) {
         this.first = (CharMatcher)Preconditions.checkNotNull(var1);
         this.second = (CharMatcher)Preconditions.checkNotNull(var2);
      }

      public boolean matches(char var1) {
         boolean var2;
         if (this.first.matches(var1) && this.second.matches(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         BitSet var2 = new BitSet();
         this.first.setBits(var2);
         BitSet var3 = new BitSet();
         this.second.setBits(var3);
         var2.and(var3);
         var1.or(var2);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.and(");
         var1.append(this.first);
         var1.append(", ");
         var1.append(this.second);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class Any extends CharMatcher.NamedFastMatcher {
      static final CharMatcher.Any INSTANCE = new CharMatcher.Any();

      private Any() {
         super("CharMatcher.any()");
      }

      public CharMatcher and(CharMatcher var1) {
         return (CharMatcher)Preconditions.checkNotNull(var1);
      }

      public String collapseFrom(CharSequence var1, char var2) {
         String var3;
         if (var1.length() == 0) {
            var3 = "";
         } else {
            var3 = String.valueOf(var2);
         }

         return var3;
      }

      public int countIn(CharSequence var1) {
         return var1.length();
      }

      public int indexIn(CharSequence var1) {
         byte var2;
         if (var1.length() == 0) {
            var2 = -1;
         } else {
            var2 = 0;
         }

         return var2;
      }

      public int indexIn(CharSequence var1, int var2) {
         int var3 = var1.length();
         Preconditions.checkPositionIndex(var2, var3);
         int var4 = var2;
         if (var2 == var3) {
            var4 = -1;
         }

         return var4;
      }

      public int lastIndexIn(CharSequence var1) {
         return var1.length() - 1;
      }

      public boolean matches(char var1) {
         return true;
      }

      public boolean matchesAllOf(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return true;
      }

      public boolean matchesNoneOf(CharSequence var1) {
         boolean var2;
         if (var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public CharMatcher negate() {
         return none();
      }

      public CharMatcher or(CharMatcher var1) {
         Preconditions.checkNotNull(var1);
         return this;
      }

      public String removeFrom(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return "";
      }

      public String replaceFrom(CharSequence var1, char var2) {
         char[] var3 = new char[var1.length()];
         Arrays.fill(var3, var2);
         return new String(var3);
      }

      public String replaceFrom(CharSequence var1, CharSequence var2) {
         StringBuilder var3 = new StringBuilder(var1.length() * var2.length());

         for(int var4 = 0; var4 < var1.length(); ++var4) {
            var3.append(var2);
         }

         return var3.toString();
      }

      public String trimFrom(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return "";
      }
   }

   private static final class AnyOf extends CharMatcher {
      private final char[] chars;

      public AnyOf(CharSequence var1) {
         char[] var2 = var1.toString().toCharArray();
         this.chars = var2;
         Arrays.sort(var2);
      }

      public boolean matches(char var1) {
         boolean var2;
         if (Arrays.binarySearch(this.chars, var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         char[] var2 = this.chars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1.set(var2[var4]);
         }

      }

      public String toString() {
         StringBuilder var1 = new StringBuilder("CharMatcher.anyOf(\"");
         char[] var2 = this.chars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1.append(CharMatcher.showCharacter(var2[var4]));
         }

         var1.append("\")");
         return var1.toString();
      }
   }

   private static final class Ascii extends CharMatcher.NamedFastMatcher {
      static final CharMatcher.Ascii INSTANCE = new CharMatcher.Ascii();

      Ascii() {
         super("CharMatcher.ascii()");
      }

      public boolean matches(char var1) {
         boolean var2;
         if (var1 <= 127) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   private static final class BitSetMatcher extends CharMatcher.NamedFastMatcher {
      private final BitSet table;

      private BitSetMatcher(BitSet var1, String var2) {
         super(var2);
         BitSet var3 = var1;
         if (var1.length() + 64 < var1.size()) {
            var3 = (BitSet)var1.clone();
         }

         this.table = var3;
      }

      // $FF: synthetic method
      BitSetMatcher(BitSet var1, String var2, Object var3) {
         this(var1, var2);
      }

      public boolean matches(char var1) {
         return this.table.get(var1);
      }

      void setBits(BitSet var1) {
         var1.or(this.table);
      }
   }

   private static final class BreakingWhitespace extends CharMatcher {
      static final CharMatcher INSTANCE = new CharMatcher.BreakingWhitespace();

      public boolean matches(char var1) {
         boolean var2 = true;
         if (var1 != ' ' && var1 != 133 && var1 != 5760) {
            if (var1 == 8199) {
               return false;
            }

            if (var1 != 8287 && var1 != 12288 && var1 != 8232 && var1 != 8233) {
               switch(var1) {
               case '\t':
               case '\n':
               case '\u000b':
               case '\f':
               case '\r':
                  break;
               default:
                  if (var1 < 8192 || var1 > 8202) {
                     var2 = false;
                  }

                  return var2;
               }
            }
         }

         return true;
      }

      public String toString() {
         return "CharMatcher.breakingWhitespace()";
      }
   }

   private static final class Digit extends CharMatcher.RangesMatcher {
      static final CharMatcher.Digit INSTANCE = new CharMatcher.Digit();
      private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０";

      private Digit() {
         super("CharMatcher.digit()", zeroes(), nines());
      }

      private static char[] nines() {
         char[] var0 = new char[37];

         for(int var1 = 0; var1 < 37; ++var1) {
            var0[var1] = (char)((char)("0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０".charAt(var1) + 9));
         }

         return var0;
      }

      private static char[] zeroes() {
         return "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０".toCharArray();
      }
   }

   abstract static class FastMatcher extends CharMatcher {
      public CharMatcher negate() {
         return new CharMatcher.NegatedFastMatcher(this);
      }

      public final CharMatcher precomputed() {
         return this;
      }
   }

   private static final class ForPredicate extends CharMatcher {
      private final Predicate<? super Character> predicate;

      ForPredicate(Predicate<? super Character> var1) {
         this.predicate = (Predicate)Preconditions.checkNotNull(var1);
      }

      public boolean apply(Character var1) {
         return this.predicate.apply(Preconditions.checkNotNull(var1));
      }

      public boolean matches(char var1) {
         return this.predicate.apply(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.forPredicate(");
         var1.append(this.predicate);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class InRange extends CharMatcher.FastMatcher {
      private final char endInclusive;
      private final char startInclusive;

      InRange(char var1, char var2) {
         boolean var3;
         if (var2 >= var1) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         this.startInclusive = (char)var1;
         this.endInclusive = (char)var2;
      }

      public boolean matches(char var1) {
         boolean var2;
         if (this.startInclusive <= var1 && var1 <= this.endInclusive) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         var1.set(this.startInclusive, this.endInclusive + 1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.inRange('");
         var1.append(CharMatcher.showCharacter(this.startInclusive));
         var1.append("', '");
         var1.append(CharMatcher.showCharacter(this.endInclusive));
         var1.append("')");
         return var1.toString();
      }
   }

   private static final class Invisible extends CharMatcher.RangesMatcher {
      static final CharMatcher.Invisible INSTANCE = new CharMatcher.Invisible();
      private static final String RANGE_ENDS = "  \u00ad\u0605\u061c\u06dd\u070f\u08e2 \u180e\u200f \u2064\u206f　\uf8ff\ufeff\ufffb";
      private static final String RANGE_STARTS = "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u08e2 \u180e \u2028 \u2066　\ud800\ufeff\ufff9";

      private Invisible() {
         super("CharMatcher.invisible()", "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u08e2 \u180e \u2028 \u2066　\ud800\ufeff\ufff9".toCharArray(), "  \u00ad\u0605\u061c\u06dd\u070f\u08e2 \u180e\u200f \u2064\u206f　\uf8ff\ufeff\ufffb".toCharArray());
      }
   }

   private static final class Is extends CharMatcher.FastMatcher {
      private final char match;

      Is(char var1) {
         this.match = (char)var1;
      }

      public CharMatcher and(CharMatcher var1) {
         Object var2;
         if (var1.matches(this.match)) {
            var2 = this;
         } else {
            var2 = none();
         }

         return (CharMatcher)var2;
      }

      public boolean matches(char var1) {
         boolean var2;
         if (var1 == this.match) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public CharMatcher negate() {
         return isNot(this.match);
      }

      public CharMatcher or(CharMatcher var1) {
         if (!var1.matches(this.match)) {
            var1 = super.or(var1);
         }

         return var1;
      }

      public String replaceFrom(CharSequence var1, char var2) {
         return var1.toString().replace(this.match, var2);
      }

      void setBits(BitSet var1) {
         var1.set(this.match);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.is('");
         var1.append(CharMatcher.showCharacter(this.match));
         var1.append("')");
         return var1.toString();
      }
   }

   private static final class IsEither extends CharMatcher.FastMatcher {
      private final char match1;
      private final char match2;

      IsEither(char var1, char var2) {
         this.match1 = (char)var1;
         this.match2 = (char)var2;
      }

      public boolean matches(char var1) {
         boolean var2;
         if (var1 != this.match1 && var1 != this.match2) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         var1.set(this.match1);
         var1.set(this.match2);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.anyOf(\"");
         var1.append(CharMatcher.showCharacter(this.match1));
         var1.append(CharMatcher.showCharacter(this.match2));
         var1.append("\")");
         return var1.toString();
      }
   }

   private static final class IsNot extends CharMatcher.FastMatcher {
      private final char match;

      IsNot(char var1) {
         this.match = (char)var1;
      }

      public CharMatcher and(CharMatcher var1) {
         CharMatcher var2 = var1;
         if (var1.matches(this.match)) {
            var2 = super.and(var1);
         }

         return var2;
      }

      public boolean matches(char var1) {
         boolean var2;
         if (var1 != this.match) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public CharMatcher negate() {
         return is(this.match);
      }

      public CharMatcher or(CharMatcher var1) {
         Object var2;
         if (var1.matches(this.match)) {
            var2 = any();
         } else {
            var2 = this;
         }

         return (CharMatcher)var2;
      }

      void setBits(BitSet var1) {
         var1.set(0, this.match);
         var1.set(this.match + 1, 65536);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.isNot('");
         var1.append(CharMatcher.showCharacter(this.match));
         var1.append("')");
         return var1.toString();
      }
   }

   private static final class JavaDigit extends CharMatcher {
      static final CharMatcher.JavaDigit INSTANCE = new CharMatcher.JavaDigit();

      public boolean matches(char var1) {
         return Character.isDigit(var1);
      }

      public String toString() {
         return "CharMatcher.javaDigit()";
      }
   }

   private static final class JavaIsoControl extends CharMatcher.NamedFastMatcher {
      static final CharMatcher.JavaIsoControl INSTANCE = new CharMatcher.JavaIsoControl();

      private JavaIsoControl() {
         super("CharMatcher.javaIsoControl()");
      }

      public boolean matches(char var1) {
         boolean var2;
         if (var1 <= 31 || var1 >= 127 && var1 <= 159) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   private static final class JavaLetter extends CharMatcher {
      static final CharMatcher.JavaLetter INSTANCE = new CharMatcher.JavaLetter();

      public boolean matches(char var1) {
         return Character.isLetter(var1);
      }

      public String toString() {
         return "CharMatcher.javaLetter()";
      }
   }

   private static final class JavaLetterOrDigit extends CharMatcher {
      static final CharMatcher.JavaLetterOrDigit INSTANCE = new CharMatcher.JavaLetterOrDigit();

      public boolean matches(char var1) {
         return Character.isLetterOrDigit(var1);
      }

      public String toString() {
         return "CharMatcher.javaLetterOrDigit()";
      }
   }

   private static final class JavaLowerCase extends CharMatcher {
      static final CharMatcher.JavaLowerCase INSTANCE = new CharMatcher.JavaLowerCase();

      public boolean matches(char var1) {
         return Character.isLowerCase(var1);
      }

      public String toString() {
         return "CharMatcher.javaLowerCase()";
      }
   }

   private static final class JavaUpperCase extends CharMatcher {
      static final CharMatcher.JavaUpperCase INSTANCE = new CharMatcher.JavaUpperCase();

      public boolean matches(char var1) {
         return Character.isUpperCase(var1);
      }

      public String toString() {
         return "CharMatcher.javaUpperCase()";
      }
   }

   abstract static class NamedFastMatcher extends CharMatcher.FastMatcher {
      private final String description;

      NamedFastMatcher(String var1) {
         this.description = (String)Preconditions.checkNotNull(var1);
      }

      public final String toString() {
         return this.description;
      }
   }

   private static class Negated extends CharMatcher {
      final CharMatcher original;

      Negated(CharMatcher var1) {
         this.original = (CharMatcher)Preconditions.checkNotNull(var1);
      }

      public int countIn(CharSequence var1) {
         return var1.length() - this.original.countIn(var1);
      }

      public boolean matches(char var1) {
         return this.original.matches(var1) ^ true;
      }

      public boolean matchesAllOf(CharSequence var1) {
         return this.original.matchesNoneOf(var1);
      }

      public boolean matchesNoneOf(CharSequence var1) {
         return this.original.matchesAllOf(var1);
      }

      public CharMatcher negate() {
         return this.original;
      }

      void setBits(BitSet var1) {
         BitSet var2 = new BitSet();
         this.original.setBits(var2);
         var2.flip(0, 65536);
         var1.or(var2);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.original);
         var1.append(".negate()");
         return var1.toString();
      }
   }

   static class NegatedFastMatcher extends CharMatcher.Negated {
      NegatedFastMatcher(CharMatcher var1) {
         super(var1);
      }

      public final CharMatcher precomputed() {
         return this;
      }
   }

   private static final class None extends CharMatcher.NamedFastMatcher {
      static final CharMatcher.None INSTANCE = new CharMatcher.None();

      private None() {
         super("CharMatcher.none()");
      }

      public CharMatcher and(CharMatcher var1) {
         Preconditions.checkNotNull(var1);
         return this;
      }

      public String collapseFrom(CharSequence var1, char var2) {
         return var1.toString();
      }

      public int countIn(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return 0;
      }

      public int indexIn(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return -1;
      }

      public int indexIn(CharSequence var1, int var2) {
         Preconditions.checkPositionIndex(var2, var1.length());
         return -1;
      }

      public int lastIndexIn(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return -1;
      }

      public boolean matches(char var1) {
         return false;
      }

      public boolean matchesAllOf(CharSequence var1) {
         boolean var2;
         if (var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean matchesNoneOf(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return true;
      }

      public CharMatcher negate() {
         return any();
      }

      public CharMatcher or(CharMatcher var1) {
         return (CharMatcher)Preconditions.checkNotNull(var1);
      }

      public String removeFrom(CharSequence var1) {
         return var1.toString();
      }

      public String replaceFrom(CharSequence var1, char var2) {
         return var1.toString();
      }

      public String replaceFrom(CharSequence var1, CharSequence var2) {
         Preconditions.checkNotNull(var2);
         return var1.toString();
      }

      public String trimFrom(CharSequence var1) {
         return var1.toString();
      }

      public String trimLeadingFrom(CharSequence var1) {
         return var1.toString();
      }

      public String trimTrailingFrom(CharSequence var1) {
         return var1.toString();
      }
   }

   private static final class Or extends CharMatcher {
      final CharMatcher first;
      final CharMatcher second;

      Or(CharMatcher var1, CharMatcher var2) {
         this.first = (CharMatcher)Preconditions.checkNotNull(var1);
         this.second = (CharMatcher)Preconditions.checkNotNull(var2);
      }

      public boolean matches(char var1) {
         boolean var2;
         if (!this.first.matches(var1) && !this.second.matches(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         this.first.setBits(var1);
         this.second.setBits(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharMatcher.or(");
         var1.append(this.first);
         var1.append(", ");
         var1.append(this.second);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class RangesMatcher extends CharMatcher {
      private final String description;
      private final char[] rangeEnds;
      private final char[] rangeStarts;

      RangesMatcher(String var1, char[] var2, char[] var3) {
         this.description = var1;
         this.rangeStarts = var2;
         this.rangeEnds = var3;
         boolean var4;
         if (var2.length == var3.length) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);

         int var6;
         for(int var5 = 0; var5 < var2.length; var5 = var6) {
            if (var2[var5] <= var3[var5]) {
               var4 = true;
            } else {
               var4 = false;
            }

            Preconditions.checkArgument(var4);
            var6 = var5 + 1;
            if (var6 < var2.length) {
               if (var3[var5] < var2[var6]) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               Preconditions.checkArgument(var4);
            }
         }

      }

      public boolean matches(char var1) {
         int var2 = Arrays.binarySearch(this.rangeStarts, var1);
         boolean var3 = true;
         if (var2 >= 0) {
            return true;
         } else {
            --var2;
            if (var2 < 0 || var1 > this.rangeEnds[var2]) {
               var3 = false;
            }

            return var3;
         }
      }

      public String toString() {
         return this.description;
      }
   }

   private static final class SingleWidth extends CharMatcher.RangesMatcher {
      static final CharMatcher.SingleWidth INSTANCE = new CharMatcher.SingleWidth();

      private SingleWidth() {
         super("CharMatcher.singleWidth()", "\u0000־א׳\u0600ݐ\u0e00Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ\u0e7f₯℺\ufdff\ufeffￜ".toCharArray());
      }
   }

   static final class Whitespace extends CharMatcher.NamedFastMatcher {
      static final CharMatcher.Whitespace INSTANCE = new CharMatcher.Whitespace();
      static final int MULTIPLIER = 1682554634;
      static final int SHIFT = Integer.numberOfLeadingZeros(31);
      static final String TABLE = " 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　";

      Whitespace() {
         super("CharMatcher.whitespace()");
      }

      public boolean matches(char var1) {
         boolean var2;
         if (" 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".charAt(1682554634 * var1 >>> SHIFT) == var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      void setBits(BitSet var1) {
         for(int var2 = 0; var2 < 32; ++var2) {
            var1.set(" 　\r\u0085   　\u2029\u000b　   　 \t     \f 　 　　\u2028\n 　".charAt(var2));
         }

      }
   }
}
