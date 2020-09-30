package io.opencensus.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public final class Utils {
   private Utils() {
   }

   public static void checkArgument(boolean var0, @Nullable Object var1) {
      if (!var0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkArgument(boolean var0, String var1, @Nullable Object... var2) {
      if (!var0) {
         throw new IllegalArgumentException(format(var1, var2));
      }
   }

   public static void checkIndex(int var0, int var1) {
      StringBuilder var2;
      if (var1 >= 0) {
         if (var0 < 0 || var0 >= var1) {
            var2 = new StringBuilder();
            var2.append("Index out of bounds: size=");
            var2.append(var1);
            var2.append(", index=");
            var2.append(var0);
            throw new IndexOutOfBoundsException(var2.toString());
         }
      } else {
         var2 = new StringBuilder();
         var2.append("Negative size: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static <T> void checkListElementNotNull(List<T> var0, @Nullable Object var1) {
      Iterator var2 = var0.iterator();

      do {
         if (!var2.hasNext()) {
            return;
         }
      } while(var2.next() != null);

      throw new NullPointerException(String.valueOf(var1));
   }

   public static <K, V> void checkMapElementNotNull(Map<K, V> var0, @Nullable Object var1) {
      Iterator var2 = var0.entrySet().iterator();

      Entry var3;
      do {
         if (!var2.hasNext()) {
            return;
         }

         var3 = (Entry)var2.next();
      } while(var3.getKey() != null && var3.getValue() != null);

      throw new NullPointerException(String.valueOf(var1));
   }

   public static <T> T checkNotNull(T var0, @Nullable Object var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(String.valueOf(var1));
      }
   }

   public static void checkState(boolean var0, @Nullable Object var1) {
      if (!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }

   public static boolean equalsObjects(@Nullable Object var0, @Nullable Object var1) {
      boolean var2;
      if (var0 == null) {
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return var2;
   }

   private static String format(String var0, @Nullable Object... var1) {
      if (var1 == null) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder(var0.length() + var1.length * 16);
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < var1.length; ++var3) {
            int var5 = var0.indexOf("%s", var4);
            if (var5 == -1) {
               break;
            }

            var2.append(var0, var4, var5);
            var2.append(var1[var3]);
            var4 = var5 + 2;
         }

         var2.append(var0, var4, var0.length());
         if (var3 < var1.length) {
            var2.append(" [");
            var4 = var3 + 1;
            var2.append(var1[var3]);

            for(var3 = var4; var3 < var1.length; ++var3) {
               var2.append(", ");
               var2.append(var1[var3]);
            }

            var2.append(']');
         }

         return var2.toString();
      }
   }
}
