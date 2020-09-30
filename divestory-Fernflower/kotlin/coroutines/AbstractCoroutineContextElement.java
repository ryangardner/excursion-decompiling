package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class AbstractCoroutineContextElement implements CoroutineContext.Element {
   private final CoroutineContext.Key<?> key;

   public AbstractCoroutineContextElement(CoroutineContext.Key<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      super();
      this.key = var1;
   }

   public <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      return CoroutineContext.Element.DefaultImpls.fold(this, var1, var2);
   }

   public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return CoroutineContext.Element.DefaultImpls.get(this, var1);
   }

   public CoroutineContext.Key<?> getKey() {
      return this.key;
   }

   public CoroutineContext minusKey(CoroutineContext.Key<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return CoroutineContext.Element.DefaultImpls.minusKey(this, var1);
   }

   public CoroutineContext plus(CoroutineContext var1) {
      Intrinsics.checkParameterIsNotNull(var1, "context");
      return CoroutineContext.Element.DefaultImpls.plus(this, var1);
   }
}
