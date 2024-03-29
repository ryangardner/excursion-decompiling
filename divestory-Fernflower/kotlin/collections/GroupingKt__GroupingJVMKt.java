package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aW\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b¨\u0006\r"},
   d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingJVMKt {
   public GroupingKt__GroupingJVMKt() {
   }

   public static final <T, K> Map<K, Integer> eachCount(Grouping<T, ? extends K> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$eachCount");
      Map var1 = (Map)(new LinkedHashMap());
      Iterator var2 = var0.sourceIterator();

      while(var2.hasNext()) {
         Object var3 = var0.keyOf(var2.next());
         Object var4 = var1.get(var3);
         boolean var5;
         if (var4 == null && !var1.containsKey(var3)) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            var4 = new Ref.IntRef();
         }

         Ref.IntRef var7 = (Ref.IntRef)var4;
         ++var7.element;
         var1.put(var3, var7);
      }

      Iterator var8 = ((Iterable)var1.entrySet()).iterator();

      while(var8.hasNext()) {
         Entry var6 = (Entry)var8.next();
         if (var6 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         TypeIntrinsics.asMutableMapEntry(var6).setValue(((Ref.IntRef)var6.getValue()).element);
      }

      return TypeIntrinsics.asMutableMap(var1);
   }

   private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Iterator var2 = ((Iterable)var0.entrySet()).iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         TypeIntrinsics.asMutableMapEntry(var3).setValue(var1.invoke(var3));
      }

      if (var0 != null) {
         return TypeIntrinsics.asMutableMap(var0);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
      }
   }
}
