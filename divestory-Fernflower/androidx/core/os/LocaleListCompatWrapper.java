package androidx.core.os;

import android.os.Build.VERSION;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

final class LocaleListCompatWrapper implements LocaleListInterface {
   private static final Locale EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");
   private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
   private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
   private static final Locale[] sEmptyList = new Locale[0];
   private final Locale[] mList;
   private final String mStringRepresentation;

   LocaleListCompatWrapper(Locale... var1) {
      if (var1.length == 0) {
         this.mList = sEmptyList;
         this.mStringRepresentation = "";
      } else {
         Locale[] var2 = new Locale[var1.length];
         HashSet var3 = new HashSet();
         StringBuilder var4 = new StringBuilder();

         for(int var5 = 0; var5 < var1.length; ++var5) {
            Locale var6 = var1[var5];
            StringBuilder var7;
            if (var6 == null) {
               var7 = new StringBuilder();
               var7.append("list[");
               var7.append(var5);
               var7.append("] is null");
               throw new NullPointerException(var7.toString());
            }

            if (var3.contains(var6)) {
               var7 = new StringBuilder();
               var7.append("list[");
               var7.append(var5);
               var7.append("] is a repetition");
               throw new IllegalArgumentException(var7.toString());
            }

            var6 = (Locale)var6.clone();
            var2[var5] = var6;
            toLanguageTag(var4, var6);
            if (var5 < var1.length - 1) {
               var4.append(',');
            }

            var3.add(var6);
         }

         this.mList = var2;
         this.mStringRepresentation = var4.toString();
      }

   }

   private Locale computeFirstMatch(Collection<String> var1, boolean var2) {
      int var3 = this.computeFirstMatchIndex(var1, var2);
      Locale var4;
      if (var3 == -1) {
         var4 = null;
      } else {
         var4 = this.mList[var3];
      }

      return var4;
   }

   private int computeFirstMatchIndex(Collection<String> var1, boolean var2) {
      Locale[] var3 = this.mList;
      if (var3.length == 1) {
         return 0;
      } else if (var3.length == 0) {
         return -1;
      } else {
         int var4;
         label38: {
            if (var2) {
               var4 = this.findFirstMatchIndex(EN_LATN);
               if (var4 == 0) {
                  return 0;
               }

               if (var4 < Integer.MAX_VALUE) {
                  break label38;
               }
            }

            var4 = Integer.MAX_VALUE;
         }

         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            int var5 = this.findFirstMatchIndex(LocaleListCompat.forLanguageTagCompat((String)var6.next()));
            if (var5 == 0) {
               return 0;
            }

            if (var5 < var4) {
               var4 = var5;
            }
         }

         if (var4 == Integer.MAX_VALUE) {
            return 0;
         } else {
            return var4;
         }
      }
   }

   private int findFirstMatchIndex(Locale var1) {
      int var2 = 0;

      while(true) {
         Locale[] var3 = this.mList;
         if (var2 >= var3.length) {
            return Integer.MAX_VALUE;
         }

         if (matchScore(var1, var3[var2]) > 0) {
            return var2;
         }

         ++var2;
      }
   }

   private static String getLikelyScript(Locale var0) {
      if (VERSION.SDK_INT >= 21) {
         String var1 = var0.getScript();
         if (!var1.isEmpty()) {
            return var1;
         }
      }

      return "";
   }

   private static boolean isPseudoLocale(Locale var0) {
      boolean var1;
      if (!LOCALE_EN_XA.equals(var0) && !LOCALE_AR_XB.equals(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static int matchScore(Locale var0, Locale var1) {
      boolean var2 = var0.equals(var1);
      byte var3 = 1;
      if (var2) {
         return 1;
      } else if (!var0.getLanguage().equals(var1.getLanguage())) {
         return 0;
      } else if (!isPseudoLocale(var0) && !isPseudoLocale(var1)) {
         String var4 = getLikelyScript(var0);
         if (var4.isEmpty()) {
            String var6 = var0.getCountry();
            byte var5 = var3;
            if (!var6.isEmpty()) {
               if (var6.equals(var1.getCountry())) {
                  var5 = var3;
               } else {
                  var5 = 0;
               }
            }

            return var5;
         } else {
            return var4.equals(getLikelyScript(var1));
         }
      } else {
         return 0;
      }
   }

   static void toLanguageTag(StringBuilder var0, Locale var1) {
      var0.append(var1.getLanguage());
      String var2 = var1.getCountry();
      if (var2 != null && !var2.isEmpty()) {
         var0.append('-');
         var0.append(var1.getCountry());
      }

   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof LocaleListCompatWrapper)) {
         return false;
      } else {
         Locale[] var4 = ((LocaleListCompatWrapper)var1).mList;
         if (this.mList.length != var4.length) {
            return false;
         } else {
            int var2 = 0;

            while(true) {
               Locale[] var3 = this.mList;
               if (var2 >= var3.length) {
                  return true;
               }

               if (!var3[var2].equals(var4[var2])) {
                  return false;
               }

               ++var2;
            }
         }
      }
   }

   public Locale get(int var1) {
      Locale var3;
      if (var1 >= 0) {
         Locale[] var2 = this.mList;
         if (var1 < var2.length) {
            var3 = var2[var1];
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public Locale getFirstMatch(String[] var1) {
      return this.computeFirstMatch(Arrays.asList(var1), false);
   }

   public Object getLocaleList() {
      return null;
   }

   public int hashCode() {
      int var1 = 1;
      int var2 = 0;

      while(true) {
         Locale[] var3 = this.mList;
         if (var2 >= var3.length) {
            return var1;
         }

         var1 = var1 * 31 + var3[var2].hashCode();
         ++var2;
      }
   }

   public int indexOf(Locale var1) {
      int var2 = 0;

      while(true) {
         Locale[] var3 = this.mList;
         if (var2 >= var3.length) {
            return -1;
         }

         if (var3[var2].equals(var1)) {
            return var2;
         }

         ++var2;
      }
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.mList.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int size() {
      return this.mList.length;
   }

   public String toLanguageTags() {
      return this.mStringRepresentation;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[");
      int var2 = 0;

      while(true) {
         Locale[] var3 = this.mList;
         if (var2 >= var3.length) {
            var1.append("]");
            return var1.toString();
         }

         var1.append(var3[var2]);
         if (var2 < this.mList.length - 1) {
            var1.append(',');
         }

         ++var2;
      }
   }
}
