package kotlin.collections;

import java.util.Enumeration;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\u0002¨\u0006\u0004"},
   d2 = {"iterator", "", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__IteratorsJVMKt extends CollectionsKt__IterablesKt {
   public CollectionsKt__IteratorsJVMKt() {
   }

   public static final <T> Iterator<T> iterator(final Enumeration<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$iterator");
      return (Iterator)(new Iterator<T>() {
         public boolean hasNext() {
            return var0.hasMoreElements();
         }

         public T next() {
            return var0.nextElement();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }
}
