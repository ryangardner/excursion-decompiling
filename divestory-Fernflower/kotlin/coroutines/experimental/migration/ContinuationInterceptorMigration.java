package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
   d2 = {"Lkotlin/coroutines/experimental/migration/ContinuationInterceptorMigration;", "Lkotlin/coroutines/ContinuationInterceptor;", "interceptor", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "(Lkotlin/coroutines/experimental/ContinuationInterceptor;)V", "getInterceptor", "()Lkotlin/coroutines/experimental/ContinuationInterceptor;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "kotlin-stdlib-coroutines"},
   k = 1,
   mv = {1, 1, 16}
)
final class ContinuationInterceptorMigration implements ContinuationInterceptor {
   private final kotlin.coroutines.experimental.ContinuationInterceptor interceptor;

   public ContinuationInterceptorMigration(kotlin.coroutines.experimental.ContinuationInterceptor var1) {
      Intrinsics.checkParameterIsNotNull(var1, "interceptor");
      super();
      this.interceptor = var1;
   }

   public <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      return ContinuationInterceptor.DefaultImpls.fold(this, var1, var2);
   }

   public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return ContinuationInterceptor.DefaultImpls.get(this, var1);
   }

   public final kotlin.coroutines.experimental.ContinuationInterceptor getInterceptor() {
      return this.interceptor;
   }

   public CoroutineContext.Key<?> getKey() {
      return (CoroutineContext.Key)ContinuationInterceptor.Key;
   }

   public <T> Continuation<T> interceptContinuation(Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "continuation");
      return CoroutinesMigrationKt.toContinuation(this.interceptor.interceptContinuation(CoroutinesMigrationKt.toExperimentalContinuation(var1)));
   }

   public CoroutineContext minusKey(CoroutineContext.Key<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return ContinuationInterceptor.DefaultImpls.minusKey(this, var1);
   }

   public CoroutineContext plus(CoroutineContext var1) {
      Intrinsics.checkParameterIsNotNull(var1, "context");
      return ContinuationInterceptor.DefaultImpls.plus(this, var1);
   }

   public void releaseInterceptedContinuation(Continuation<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "continuation");
      ContinuationInterceptor.DefaultImpls.releaseInterceptedContinuation(this, var1);
   }
}
