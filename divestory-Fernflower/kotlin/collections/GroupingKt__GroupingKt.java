package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009b\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u001a´\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b¢\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0016\u001a¼\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u001a|\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001c\u001aÕ\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b¢\u0006\u0002\u0010\u001e\u001a\u0090\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001f\u001a\u0088\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u001a¡\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b¢\u0006\u0002\u0010#¨\u0006$"},
   d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
   public GroupingKt__GroupingKt() {
   }

   public static final <T, K, R> Map<K, R> aggregate(Grouping<T, ? extends K> var0, Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$aggregate");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      Map var2 = (Map)(new LinkedHashMap());

      Object var4;
      Object var5;
      Object var6;
      boolean var7;
      for(Iterator var3 = var0.sourceIterator(); var3.hasNext(); var2.put(var5, var1.invoke(var5, var6, var4, var7))) {
         var4 = var3.next();
         var5 = var0.keyOf(var4);
         var6 = var2.get(var5);
         if (var6 == null && !var2.containsKey(var5)) {
            var7 = true;
         } else {
            var7 = false;
         }
      }

      return var2;
   }

   public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(Grouping<T, ? extends K> var0, M var1, Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$aggregateTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "operation");

      Object var4;
      Object var5;
      Object var6;
      boolean var7;
      for(Iterator var3 = var0.sourceIterator(); var3.hasNext(); var1.put(var5, var2.invoke(var5, var6, var4, var7))) {
         var4 = var3.next();
         var5 = var0.keyOf(var4);
         var6 = var1.get(var5);
         if (var6 == null && !var1.containsKey(var5)) {
            var7 = true;
         } else {
            var7 = false;
         }
      }

      return var1;
   }

   public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(Grouping<T, ? extends K> var0, M var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$eachCountTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");

      Object var3;
      Object var4;
      for(Iterator var2 = var0.sourceIterator(); var2.hasNext(); var1.put(var3, ((Number)var4).intValue() + 1)) {
         var3 = var0.keyOf(var2.next());
         var4 = var1.get(var3);
         boolean var5;
         if (var4 == null && !var1.containsKey(var3)) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            var4 = 0;
         }
      }

      return var1;
   }

   public static final <T, K, R> Map<K, R> fold(Grouping<T, ? extends K> var0, R var1, Function2<? super R, ? super T, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$fold");
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      Map var3 = (Map)(new LinkedHashMap());

      Object var5;
      Object var6;
      Object var7;
      for(Iterator var4 = var0.sourceIterator(); var4.hasNext(); var3.put(var6, var2.invoke(var7, var5))) {
         var5 = var4.next();
         var6 = var0.keyOf(var5);
         var7 = var3.get(var6);
         boolean var8;
         if (var7 == null && !var3.containsKey(var6)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = var1;
         }
      }

      return var3;
   }

   public static final <T, K, R> Map<K, R> fold(Grouping<T, ? extends K> var0, Function2<? super K, ? super T, ? extends R> var1, Function3<? super K, ? super R, ? super T, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$fold");
      Intrinsics.checkParameterIsNotNull(var1, "initialValueSelector");
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      Map var3 = (Map)(new LinkedHashMap());

      Object var5;
      Object var6;
      Object var7;
      for(Iterator var4 = var0.sourceIterator(); var4.hasNext(); var3.put(var6, var2.invoke(var6, var7, var5))) {
         var5 = var4.next();
         var6 = var0.keyOf(var5);
         var7 = var3.get(var6);
         boolean var8;
         if (var7 == null && !var3.containsKey(var6)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = var1.invoke(var6, var5);
         }
      }

      return var3;
   }

   public static final <T, K, R, M extends Map<? super K, R>> M foldTo(Grouping<T, ? extends K> var0, M var1, R var2, Function2<? super R, ? super T, ? extends R> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$foldTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var3, "operation");

      Object var5;
      Object var6;
      Object var7;
      for(Iterator var4 = var0.sourceIterator(); var4.hasNext(); var1.put(var6, var3.invoke(var7, var5))) {
         var5 = var4.next();
         var6 = var0.keyOf(var5);
         var7 = var1.get(var6);
         boolean var8;
         if (var7 == null && !var1.containsKey(var6)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = var2;
         }
      }

      return var1;
   }

   public static final <T, K, R, M extends Map<? super K, R>> M foldTo(Grouping<T, ? extends K> var0, M var1, Function2<? super K, ? super T, ? extends R> var2, Function3<? super K, ? super R, ? super T, ? extends R> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$foldTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "initialValueSelector");
      Intrinsics.checkParameterIsNotNull(var3, "operation");

      Object var5;
      Object var6;
      Object var7;
      for(Iterator var4 = var0.sourceIterator(); var4.hasNext(); var1.put(var6, var3.invoke(var6, var7, var5))) {
         var5 = var4.next();
         var6 = var0.keyOf(var5);
         var7 = var1.get(var6);
         boolean var8;
         if (var7 == null && !var1.containsKey(var6)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = var2.invoke(var6, var5);
         }
      }

      return var1;
   }

   public static final <S, T extends S, K> Map<K, S> reduce(Grouping<T, ? extends K> var0, Function3<? super K, ? super S, ? super T, ? extends S> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduce");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      Map var2 = (Map)(new LinkedHashMap());

      Object var4;
      Object var5;
      for(Iterator var3 = var0.sourceIterator(); var3.hasNext(); var2.put(var5, var4)) {
         var4 = var3.next();
         var5 = var0.keyOf(var4);
         Object var6 = var2.get(var5);
         boolean var7;
         if (var6 == null && !var2.containsKey(var5)) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (!var7) {
            var4 = var1.invoke(var5, var6, var4);
         }
      }

      return var2;
   }

   public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(Grouping<T, ? extends K> var0, M var1, Function3<? super K, ? super S, ? super T, ? extends S> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "operation");

      Object var4;
      Object var5;
      for(Iterator var3 = var0.sourceIterator(); var3.hasNext(); var1.put(var5, var4)) {
         var4 = var3.next();
         var5 = var0.keyOf(var4);
         Object var6 = var1.get(var5);
         boolean var7;
         if (var6 == null && !var1.containsKey(var5)) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (!var7) {
            var4 = var2.invoke(var5, var6, var4);
         }
      }

      return var1;
   }
}
