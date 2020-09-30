package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\r"},
   d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/SetsKt"
)
class SetsKt___SetsKt extends SetsKt__SetsKt {
   public SetsKt___SetsKt() {
   }

   public static final <T> Set<T> minus(Set<? extends T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Iterable var2 = (Iterable)var0;
      Collection var6 = CollectionsKt.convertToSetForSetOperationWith(var1, var2);
      if (var6.isEmpty()) {
         return CollectionsKt.toSet(var2);
      } else if (var6 instanceof Set) {
         Collection var5 = (Collection)(new LinkedHashSet());
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Object var7 = var3.next();
            if (!var6.contains(var7)) {
               var5.add(var7);
            }
         }

         return (Set)var5;
      } else {
         LinkedHashSet var4 = new LinkedHashSet((Collection)var0);
         var4.removeAll(var6);
         return (Set)var4;
      }
   }

   public static final <T> Set<T> minus(Set<? extends T> var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size()));
      Iterator var8 = ((Iterable)var0).iterator();
      boolean var3 = false;

      while(var8.hasNext()) {
         Object var4 = var8.next();
         boolean var5 = true;
         boolean var6 = var3;
         boolean var7 = var5;
         if (!var3) {
            var6 = var3;
            var7 = var5;
            if (Intrinsics.areEqual(var4, var1)) {
               var6 = true;
               var7 = false;
            }
         }

         var3 = var6;
         if (var7) {
            ((Collection)var2).add(var4);
            var3 = var6;
         }
      }

      return (Set)((Collection)var2);
   }

   public static final <T> Set<T> minus(Set<? extends T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      LinkedHashSet var2 = new LinkedHashSet((Collection)var0);
      CollectionsKt.removeAll((Collection)var2, var1);
      return (Set)var2;
   }

   public static final <T> Set<T> minus(Set<? extends T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      LinkedHashSet var2 = new LinkedHashSet((Collection)var0);
      CollectionsKt.removeAll((Collection)var2, var1);
      return (Set)var2;
   }

   private static final <T> Set<T> minusElement(Set<? extends T> var0, T var1) {
      return SetsKt.minus(var0, var1);
   }

   public static final <T> Set<T> plus(Set<? extends T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Integer var2 = CollectionsKt.collectionSizeOrNull(var1);
      int var3;
      if (var2 != null) {
         var3 = ((Number)var2).intValue();
         var3 += var0.size();
      } else {
         var3 = var0.size() * 2;
      }

      LinkedHashSet var4 = new LinkedHashSet(MapsKt.mapCapacity(var3));
      var4.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var4, var1);
      return (Set)var4;
   }

   public static final <T> Set<T> plus(Set<? extends T> var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + 1));
      var2.addAll((Collection)var0);
      var2.add(var1);
      return (Set)var2;
   }

   public static final <T> Set<T> plus(Set<? extends T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() * 2));
      var2.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var2, var1);
      return (Set)var2;
   }

   public static final <T> Set<T> plus(Set<? extends T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + var1.length));
      var2.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var2, var1);
      return (Set)var2;
   }

   private static final <T> Set<T> plusElement(Set<? extends T> var0, T var1) {
      return SetsKt.plus(var0, var1);
   }
}
