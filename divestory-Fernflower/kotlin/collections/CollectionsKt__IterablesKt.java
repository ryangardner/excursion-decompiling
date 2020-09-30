package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a \u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\u0007H\u0001\u001a\u001f\u0010\t\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0001¢\u0006\u0002\u0010\n\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a,\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0010\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a\u001d\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\fH\u0002¢\u0006\u0002\b\u0013\u001a@\u0010\u0014\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u00100\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00160\u00150\u0001¨\u0006\u0017"},
   d2 = {"Iterable", "", "T", "iterator", "Lkotlin/Function0;", "", "collectionSizeOrDefault", "", "default", "collectionSizeOrNull", "(Ljava/lang/Iterable;)Ljava/lang/Integer;", "convertToSetForSetOperation", "", "convertToSetForSetOperationWith", "source", "flatten", "", "safeToConvertToSet", "", "safeToConvertToSet$CollectionsKt__IterablesKt", "unzip", "Lkotlin/Pair;", "R", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
   public CollectionsKt__IterablesKt() {
   }

   private static final <T> Iterable<T> Iterable(final Function0<? extends Iterator<? extends T>> var0) {
      return (Iterable)(new Iterable<T>() {
         public Iterator<T> iterator() {
            return (Iterator)var0.invoke();
         }
      });
   }

   public static final <T> int collectionSizeOrDefault(Iterable<? extends T> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$collectionSizeOrDefault");
      if (var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      }

      return var1;
   }

   public static final <T> Integer collectionSizeOrNull(Iterable<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$collectionSizeOrNull");
      Integer var1;
      if (var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      } else {
         var1 = null;
      }

      return var1;
   }

   public static final <T> Collection<T> convertToSetForSetOperation(Iterable<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$convertToSetForSetOperation");
      Collection var2;
      if (var0 instanceof Set) {
         var2 = (Collection)var0;
      } else if (var0 instanceof Collection) {
         Collection var1 = (Collection)var0;
         if (safeToConvertToSet$CollectionsKt__IterablesKt(var1)) {
            var2 = (Collection)CollectionsKt.toHashSet(var0);
         } else {
            var2 = var1;
         }
      } else {
         var2 = (Collection)CollectionsKt.toHashSet(var0);
      }

      return var2;
   }

   public static final <T> Collection<T> convertToSetForSetOperationWith(Iterable<? extends T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$convertToSetForSetOperationWith");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Collection var2;
      if (var0 instanceof Set) {
         var2 = (Collection)var0;
      } else if (var0 instanceof Collection) {
         if (var1 instanceof Collection && ((Collection)var1).size() < 2) {
            var2 = (Collection)var0;
         } else {
            Collection var3 = (Collection)var0;
            if (safeToConvertToSet$CollectionsKt__IterablesKt(var3)) {
               var2 = (Collection)CollectionsKt.toHashSet(var0);
            } else {
               var2 = var3;
            }
         }
      } else {
         var2 = (Collection)CollectionsKt.toHashSet(var0);
      }

      return var2;
   }

   public static final <T> List<T> flatten(Iterable<? extends Iterable<? extends T>> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatten");
      ArrayList var1 = new ArrayList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Iterable var2 = (Iterable)var3.next();
         CollectionsKt.addAll((Collection)var1, var2);
      }

      return (List)var1;
   }

   private static final <T> boolean safeToConvertToSet$CollectionsKt__IterablesKt(Collection<? extends T> var0) {
      boolean var1;
      if (var0.size() > 2 && var0 instanceof ArrayList) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final <T, R> Pair<List<T>, List<R>> unzip(Iterable<? extends Pair<? extends T, ? extends R>> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$unzip");
      int var1 = CollectionsKt.collectionSizeOrDefault(var0, 10);
      ArrayList var2 = new ArrayList(var1);
      ArrayList var3 = new ArrayList(var1);
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Pair var5 = (Pair)var4.next();
         var2.add(var5.getFirst());
         var3.add(var5.getSecond());
      }

      return TuplesKt.to(var2, var3);
   }
}
