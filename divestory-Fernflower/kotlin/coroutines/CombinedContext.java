package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0096\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CombinedContext implements CoroutineContext, Serializable {
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

            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
         }

         var1 = (CombinedContext)var2;
      }

      return false;
   }

   private final int size() {
      CombinedContext var1 = (CombinedContext)this;
      int var2 = 2;

      while(true) {
         CoroutineContext var3 = var1.left;
         CoroutineContext var4 = var3;
         if (!(var3 instanceof CombinedContext)) {
            var4 = null;
         }

         var1 = (CombinedContext)var4;
         if (var1 == null) {
            return var2;
         }

         ++var2;
      }
   }

   private final Object writeReplace() {
      int var1 = this.size();
      final CoroutineContext[] var2 = new CoroutineContext[var1];
      final Ref.IntRef var3 = new Ref.IntRef();
      boolean var4 = false;
      var3.element = 0;
      this.fold(Unit.INSTANCE, (Function2)(new Function2<Unit, CoroutineContext.Element, Unit>() {
         public final void invoke(Unit var1, CoroutineContext.Element var2x) {
            Intrinsics.checkParameterIsNotNull(var1, "<anonymous parameter 0>");
            Intrinsics.checkParameterIsNotNull(var2x, "element");
            CoroutineContext[] var3x = var2;
            Ref.IntRef var5 = var3;
            int var4 = var5.element++;
            var3x[var4] = (CoroutineContext)var2x;
         }
      }));
      if (var3.element == var1) {
         var4 = true;
      }

      if (var4) {
         return new CombinedContext.Serialized(var2);
      } else {
         throw (Throwable)(new IllegalStateException("Check failed.".toString()));
      }
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

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Serialized implements Serializable {
      public static final CombinedContext.Serialized.Companion Companion = new CombinedContext.Serialized.Companion((DefaultConstructorMarker)null);
      private static final long serialVersionUID = 0L;
      private final CoroutineContext[] elements;

      public Serialized(CoroutineContext[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "elements");
         super();
         this.elements = var1;
      }

      private final Object readResolve() {
         CoroutineContext[] var1 = this.elements;
         Object var2 = EmptyCoroutineContext.INSTANCE;
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CoroutineContext var5 = var1[var4];
            var2 = ((CoroutineContext)var2).plus(var5);
         }

         return var2;
      }

      public final CoroutineContext[] getElements() {
         return this.elements;
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
         d2 = {"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"},
         k = 1,
         mv = {1, 1, 16}
      )
      public static final class Companion {
         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }
      }
   }
}
