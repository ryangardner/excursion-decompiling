package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0000H\u0002J\u0013\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u0013\u001a\u0002H\u00122\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00120\u0015H\u0016¢\u0006\u0002\u0010\u0016J(\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00042\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0002¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0014\u0010\u001e\u001a\u00020\u00012\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\""},
   d2 = {"Lkotlin/coroutines/experimental/CombinedContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "left", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Lkotlin/coroutines/experimental/CoroutineContext;Lkotlin/coroutines/experimental/CoroutineContext$Element;)V", "getElement", "()Lkotlin/coroutines/experimental/CoroutineContext$Element;", "getLeft", "()Lkotlin/coroutines/experimental/CoroutineContext;", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "kotlin-stdlib-coroutines"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CombinedContext implements CoroutineContext {
   private final CoroutineContext.Element element;
   private final CoroutineContext left;

   public CombinedContext(CoroutineContext var1, CoroutineContext.Element var2) {
      Intrinsics.checkParameterIsNotNull(var1, "left");
      Intrinsics.checkParameterIsNotNull(var2, "element");
      super();
      this.left = var1;
      this.element = var2;
   }

   private final boolean contains(CoroutineContext.Element var1) {
      return Intrinsics.areEqual((Object)this.get(var1.getKey()), (Object)var1);
   }

   private final boolean containsAll(CombinedContext var1) {
      while(this.contains(var1.element)) {
         CoroutineContext var2 = var1.left;
         if (!(var2 instanceof CombinedContext)) {
            if (var2 != null) {
               return this.contains((CoroutineContext.Element)var2);
            }

            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.CoroutineContext.Element");
         }

         var1 = (CombinedContext)var2;
      }

      return false;
   }

   private final int size() {
      CoroutineContext var1 = this.left;
      int var2;
      if (var1 instanceof CombinedContext) {
         var2 = ((CombinedContext)var1).size() + 1;
      } else {
         var2 = 2;
      }

      return var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      label28: {
         if ((CombinedContext)this != var1) {
            if (!(var1 instanceof CombinedContext)) {
               break label28;
            }

            CombinedContext var3 = (CombinedContext)var1;
            if (var3.size() != this.size() || !var3.containsAll(this)) {
               break label28;
            }
         }

         var2 = true;
         return var2;
      }

      var2 = false;
      return var2;
   }

   public <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      return var2.invoke(this.left.fold(var1, var2), this.element);
   }

   public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      CombinedContext var2 = (CombinedContext)this;

      while(true) {
         CoroutineContext.Element var3 = var2.element.get(var1);
         if (var3 != null) {
            return var3;
         }

         CoroutineContext var4 = var2.left;
         if (!(var4 instanceof CombinedContext)) {
            return var4.get(var1);
         }

         var2 = (CombinedContext)var4;
      }
   }

   public final CoroutineContext.Element getElement() {
      return this.element;
   }

   public final CoroutineContext getLeft() {
      return this.left;
   }

   public int hashCode() {
      return this.left.hashCode() + this.element.hashCode();
   }

   public CoroutineContext minusKey(CoroutineContext.Key<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      if (this.element.get(var1) != null) {
         return this.left;
      } else {
         CoroutineContext var2 = this.left.minusKey(var1);
         if (var2 == this.left) {
            var2 = (CoroutineContext)this;
         } else if (var2 == EmptyCoroutineContext.INSTANCE) {
            var2 = (CoroutineContext)this.element;
         } else {
            var2 = (CoroutineContext)(new CombinedContext(var2, this.element));
         }

         return var2;
      }
   }

   public CoroutineContext plus(CoroutineContext var1) {
      Intrinsics.checkParameterIsNotNull(var1, "context");
      return CoroutineContext.DefaultImpls.plus(this, var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[");
      var1.append((String)this.fold("", (Function2)null.INSTANCE));
      var1.append("]");
      return var1.toString();
   }
}
