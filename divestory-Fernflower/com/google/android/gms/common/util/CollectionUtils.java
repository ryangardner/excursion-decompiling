package com.google.android.gms.common.util;

import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {
   private CollectionUtils() {
   }

   public static boolean isEmpty(Collection<?> var0) {
      return var0 == null ? true : var0.isEmpty();
   }

   @Deprecated
   public static <T> List<T> listOf() {
      return Collections.emptyList();
   }

   @Deprecated
   public static <T> List<T> listOf(T var0) {
      return Collections.singletonList(var0);
   }

   @Deprecated
   public static <T> List<T> listOf(T... var0) {
      int var1 = var0.length;
      if (var1 != 0) {
         return var1 != 1 ? Collections.unmodifiableList(Arrays.asList(var0)) : listOf(var0[0]);
      } else {
         return listOf();
      }
   }

   public static <K, V> Map<K, V> mapOf(K var0, V var1, K var2, V var3, K var4, V var5) {
      Map var6 = zzb(3, false);
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return Collections.unmodifiableMap(var6);
   }

   public static <K, V> Map<K, V> mapOf(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9, K var10, V var11) {
      Map var12 = zzb(6, false);
      var12.put(var0, var1);
      var12.put(var2, var3);
      var12.put(var4, var5);
      var12.put(var6, var7);
      var12.put(var8, var9);
      var12.put(var10, var11);
      return Collections.unmodifiableMap(var12);
   }

   public static <K, V> Map<K, V> mapOfKeyValueArrays(K[] var0, V[] var1) {
      int var2;
      int var3;
      if (var0.length != var1.length) {
         var3 = var0.length;
         var2 = var1.length;
         StringBuilder var5 = new StringBuilder(66);
         var5.append("Key and values array lengths not equal: ");
         var5.append(var3);
         var5.append(" != ");
         var5.append(var2);
         throw new IllegalArgumentException(var5.toString());
      } else {
         var2 = var0.length;
         if (var2 == 0) {
            return Collections.emptyMap();
         } else {
            var3 = 0;
            if (var2 == 1) {
               return Collections.singletonMap(var0[0], var1[0]);
            } else {
               Map var4;
               for(var4 = zzb(var0.length, false); var3 < var0.length; ++var3) {
                  var4.put(var0[var3], var1[var3]);
               }

               return Collections.unmodifiableMap(var4);
            }
         }
      }
   }

   public static <T> Set<T> mutableSetOfWithSize(int var0) {
      return (Set)(var0 == 0 ? new ArraySet() : zza(var0, true));
   }

   @Deprecated
   public static <T> Set<T> setOf(T var0, T var1, T var2) {
      Set var3 = zza(3, false);
      var3.add(var0);
      var3.add(var1);
      var3.add(var2);
      return Collections.unmodifiableSet(var3);
   }

   @Deprecated
   public static <T> Set<T> setOf(T... var0) {
      int var1 = var0.length;
      if (var1 != 0) {
         if (var1 != 1) {
            Object var2;
            Object var6;
            if (var1 != 2) {
               if (var1 != 3) {
                  if (var1 != 4) {
                     Set var7 = zza(var0.length, false);
                     Collections.addAll(var7, var0);
                     return Collections.unmodifiableSet(var7);
                  } else {
                     Object var3 = var0[0];
                     Object var8 = var0[1];
                     var2 = var0[2];
                     var6 = var0[3];
                     Set var5 = zza(4, false);
                     var5.add(var3);
                     var5.add(var8);
                     var5.add(var2);
                     var5.add(var6);
                     return Collections.unmodifiableSet(var5);
                  }
               } else {
                  return setOf(var0[0], var0[1], var0[2]);
               }
            } else {
               var2 = var0[0];
               var6 = var0[1];
               Set var4 = zza(2, false);
               var4.add(var2);
               var4.add(var6);
               return Collections.unmodifiableSet(var4);
            }
         } else {
            return Collections.singleton(var0[0]);
         }
      } else {
         return Collections.emptySet();
      }
   }

   private static <T> Set<T> zza(int var0, boolean var1) {
      float var2;
      if (var1) {
         var2 = 0.75F;
      } else {
         var2 = 1.0F;
      }

      short var3;
      if (var1) {
         var3 = 128;
      } else {
         var3 = 256;
      }

      return (Set)(var0 <= var3 ? new ArraySet(var0) : new HashSet(var0, var2));
   }

   private static <K, V> Map<K, V> zzb(int var0, boolean var1) {
      return (Map)(var0 <= 256 ? new ArrayMap(var0) : new HashMap(var0, 1.0F));
   }
}
