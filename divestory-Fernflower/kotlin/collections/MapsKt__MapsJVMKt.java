package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000R\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u0081\b\u001a\u0010\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0001\u001a2\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f\u001aY\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u000e\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u000f\"\u0004\b\u0001\u0010\n2*\u0010\u0010\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f0\u0011\"\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\u0010\u0012\u001a@\u0010\u0013\u001a\u0002H\n\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u00142\u0006\u0010\u0015\u001a\u0002H\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\n0\u0017H\u0086\b¢\u0006\u0002\u0010\u0018\u001a\u0019\u0010\u0019\u001a\u00020\u001a*\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0\bH\u0087\b\u001a2\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u0010\u0012\u0006\b\u0001\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\bH\u0000\u001a1\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\bH\u0081\b\u001a:\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u000e\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u000f\"\u0004\b\u0001\u0010\n*\u0010\u0012\u0006\b\u0001\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b\u001a@\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u0010\u0012\u0006\b\u0001\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\b2\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\t0 \"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006!"},
   d2 = {"INT_MAX_POWER_OF_TWO", "", "checkBuilderCapacity", "", "capacity", "mapCapacity", "expectedSize", "mapOf", "", "K", "V", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "", "pairs", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "comparator", "Ljava/util/Comparator;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
   private static final int INT_MAX_POWER_OF_TWO = 1073741824;

   public MapsKt__MapsJVMKt() {
   }

   private static final void checkBuilderCapacity(int var0) {
   }

   public static final <K, V> V getOrPut(ConcurrentMap<K, V> var0, K var1, Function0<? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getOrPut");
      Intrinsics.checkParameterIsNotNull(var2, "defaultValue");
      Object var3 = var0.get(var1);
      Object var4;
      if (var3 != null) {
         var4 = var3;
      } else {
         Object var5 = var2.invoke();
         var1 = var0.putIfAbsent(var1, var5);
         var4 = var5;
         if (var1 != null) {
            var4 = var1;
         }
      }

      return var4;
   }

   public static final int mapCapacity(int var0) {
      if (var0 >= 0) {
         if (var0 < 3) {
            ++var0;
         } else if (var0 < 1073741824) {
            var0 = (int)((float)var0 / 0.75F + 1.0F);
         } else {
            var0 = Integer.MAX_VALUE;
         }
      }

      return var0;
   }

   public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pair");
      Map var1 = Collections.singletonMap(var0.getFirst(), var0.getSecond());
      Intrinsics.checkExpressionValueIsNotNull(var1, "java.util.Collections.si…(pair.first, pair.second)");
      return var1;
   }

   public static final <K extends Comparable<? super K>, V> SortedMap<K, V> sortedMapOf(Pair<? extends K, ? extends V>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pairs");
      TreeMap var1 = new TreeMap();
      MapsKt.putAll((Map)var1, var0);
      return (SortedMap)var1;
   }

   private static final Properties toProperties(Map<String, String> var0) {
      Properties var1 = new Properties();
      var1.putAll(var0);
      return var1;
   }

   public static final <K, V> Map<K, V> toSingletonMap(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSingletonMap");
      Entry var1 = (Entry)var0.entrySet().iterator().next();
      var0 = Collections.singletonMap(var1.getKey(), var1.getValue());
      Intrinsics.checkExpressionValueIsNotNull(var0, "java.util.Collections.singletonMap(key, value)");
      Intrinsics.checkExpressionValueIsNotNull(var0, "with(entries.iterator().…ingletonMap(key, value) }");
      return var0;
   }

   private static final <K, V> Map<K, V> toSingletonMapOrSelf(Map<K, ? extends V> var0) {
      return MapsKt.toSingletonMap(var0);
   }

   public static final <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedMap");
      return (SortedMap)(new TreeMap(var0));
   }

   public static final <K, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> var0, Comparator<? super K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedMap");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      TreeMap var2 = new TreeMap(var1);
      var2.putAll(var0);
      return (SortedMap)var2;
   }
}
