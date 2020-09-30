package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Escapers {
   private static final Escaper NULL_ESCAPER = new CharEscaper() {
      public String escape(String var1) {
         return (String)Preconditions.checkNotNull(var1);
      }

      protected char[] escape(char var1) {
         return null;
      }
   };

   private Escapers() {
   }

   static UnicodeEscaper asUnicodeEscaper(Escaper var0) {
      Preconditions.checkNotNull(var0);
      if (var0 instanceof UnicodeEscaper) {
         return (UnicodeEscaper)var0;
      } else if (var0 instanceof CharEscaper) {
         return wrap((CharEscaper)var0);
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Cannot create a UnicodeEscaper from: ");
         var1.append(var0.getClass().getName());
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public static Escapers.Builder builder() {
      return new Escapers.Builder();
   }

   public static String computeReplacement(CharEscaper var0, char var1) {
      return stringOrNull(var0.escape(var1));
   }

   public static String computeReplacement(UnicodeEscaper var0, int var1) {
      return stringOrNull(var0.escape(var1));
   }

   public static Escaper nullEscaper() {
      return NULL_ESCAPER;
   }

   private static String stringOrNull(char[] var0) {
      String var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = new String(var0);
      }

      return var1;
   }

   private static UnicodeEscaper wrap(final CharEscaper var0) {
      return new UnicodeEscaper() {
         protected char[] escape(int var1) {
            if (var1 < 65536) {
               return var0.escape((char)var1);
            } else {
               char[] var2 = new char[2];
               byte var3 = 0;
               Character.toChars(var1, var2, 0);
               char[] var4 = var0.escape(var2[0]);
               char[] var5 = var0.escape(var2[1]);
               if (var4 == null && var5 == null) {
                  return null;
               } else {
                  if (var4 != null) {
                     var1 = var4.length;
                  } else {
                     var1 = 1;
                  }

                  int var6;
                  if (var5 != null) {
                     var6 = var5.length;
                  } else {
                     var6 = 1;
                  }

                  char[] var7 = new char[var6 + var1];
                  if (var4 != null) {
                     for(var6 = 0; var6 < var4.length; ++var6) {
                        var7[var6] = (char)var4[var6];
                     }
                  } else {
                     var7[0] = var2[0];
                  }

                  if (var5 != null) {
                     for(var6 = var3; var6 < var5.length; ++var6) {
                        var7[var1 + var6] = (char)var5[var6];
                     }
                  } else {
                     var7[var1] = var2[1];
                  }

                  return var7;
               }
            }
         }
      };
   }

   public static final class Builder {
      private final Map<Character, String> replacementMap;
      private char safeMax;
      private char safeMin;
      private String unsafeReplacement;

      private Builder() {
         this.replacementMap = new HashMap();
         this.safeMin = (char)0;
         this.safeMax = (char)'\uffff';
         this.unsafeReplacement = null;
      }

      // $FF: synthetic method
      Builder(Object var1) {
         this();
      }

      public Escapers.Builder addEscape(char var1, String var2) {
         Preconditions.checkNotNull(var2);
         this.replacementMap.put(var1, var2);
         return this;
      }

      public Escaper build() {
         return new ArrayBasedCharEscaper(this.replacementMap, this.safeMin, this.safeMax) {
            private final char[] replacementChars;

            {
               char[] var5;
               if (Builder.this.unsafeReplacement != null) {
                  var5 = Builder.this.unsafeReplacement.toCharArray();
               } else {
                  var5 = null;
               }

               this.replacementChars = var5;
            }

            protected char[] escapeUnsafe(char var1) {
               return this.replacementChars;
            }
         };
      }

      public Escapers.Builder setSafeRange(char var1, char var2) {
         this.safeMin = (char)var1;
         this.safeMax = (char)var2;
         return this;
      }

      public Escapers.Builder setUnsafeReplacement(@NullableDecl String var1) {
         this.unsafeReplacement = var1;
         return this;
      }
   }
}
