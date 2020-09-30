package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\u0000\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\u0000\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\u0000\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\u0000¨\u0006\u0015"},
   d2 = {"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib-coroutines"},
   k = 2,
   mv = {1, 1, 16}
)
public final class CoroutinesMigrationKt {
   public static final <T> Continuation<T> toContinuation(kotlin.coroutines.experimental.Continuation<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toContinuation");
      kotlin.coroutines.experimental.Continuation var1;
      if (!(var0 instanceof ExperimentalContinuationMigration)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      ExperimentalContinuationMigration var3 = (ExperimentalContinuationMigration)var1;
      Continuation var2;
      if (var3 != null) {
         Continuation var4 = var3.getContinuation();
         if (var4 != null) {
            var2 = var4;
            return var2;
         }
      }

      var2 = (Continuation)(new ContinuationMigration(var0));
      return var2;
   }

   public static final ContinuationInterceptor toContinuationInterceptor(kotlin.coroutines.experimental.ContinuationInterceptor var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toContinuationInterceptor");
      kotlin.coroutines.experimental.ContinuationInterceptor var1;
      if (!(var0 instanceof ExperimentalContinuationInterceptorMigration)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      ExperimentalContinuationInterceptorMigration var3 = (ExperimentalContinuationInterceptorMigration)var1;
      ContinuationInterceptor var2;
      if (var3 != null) {
         ContinuationInterceptor var4 = var3.getInterceptor();
         if (var4 != null) {
            var2 = var4;
            return var2;
         }
      }

      var2 = (ContinuationInterceptor)(new ContinuationInterceptorMigration(var0));
      return var2;
   }

   public static final CoroutineContext toCoroutineContext(kotlin.coroutines.experimental.CoroutineContext var0) {
      kotlin.coroutines.experimental.ContinuationInterceptor var1;
      kotlin.coroutines.experimental.CoroutineContext var3;
      CoroutineContext var4;
      label22: {
         Intrinsics.checkParameterIsNotNull(var0, "$this$toCoroutineContext");
         var1 = (kotlin.coroutines.experimental.ContinuationInterceptor)var0.get((kotlin.coroutines.experimental.CoroutineContext.Key)kotlin.coroutines.experimental.ContinuationInterceptor.Key);
         ExperimentalContextMigration var2 = (ExperimentalContextMigration)var0.get((kotlin.coroutines.experimental.CoroutineContext.Key)ExperimentalContextMigration.Key);
         var3 = var0.minusKey((kotlin.coroutines.experimental.CoroutineContext.Key)kotlin.coroutines.experimental.ContinuationInterceptor.Key).minusKey((kotlin.coroutines.experimental.CoroutineContext.Key)ExperimentalContextMigration.Key);
         if (var2 != null) {
            var4 = var2.getContext();
            if (var4 != null) {
               break label22;
            }
         }

         var4 = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
      }

      if (var3 != kotlin.coroutines.experimental.EmptyCoroutineContext.INSTANCE) {
         var4 = var4.plus((CoroutineContext)(new ContextMigration(var3)));
      }

      if (var1 != null) {
         var4 = var4.plus((CoroutineContext)toContinuationInterceptor(var1));
      }

      return var4;
   }

   public static final <T> kotlin.coroutines.experimental.Continuation<T> toExperimentalContinuation(Continuation<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalContinuation");
      Continuation var1;
      if (!(var0 instanceof ContinuationMigration)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      ContinuationMigration var3 = (ContinuationMigration)var1;
      kotlin.coroutines.experimental.Continuation var2;
      if (var3 != null) {
         kotlin.coroutines.experimental.Continuation var4 = var3.getContinuation();
         if (var4 != null) {
            var2 = var4;
            return var2;
         }
      }

      var2 = (kotlin.coroutines.experimental.Continuation)(new ExperimentalContinuationMigration(var0));
      return var2;
   }

   public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(ContinuationInterceptor var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalContinuationInterceptor");
      ContinuationInterceptor var1;
      if (!(var0 instanceof ContinuationInterceptorMigration)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      ContinuationInterceptorMigration var3 = (ContinuationInterceptorMigration)var1;
      kotlin.coroutines.experimental.ContinuationInterceptor var2;
      if (var3 != null) {
         kotlin.coroutines.experimental.ContinuationInterceptor var4 = var3.getInterceptor();
         if (var4 != null) {
            var2 = var4;
            return var2;
         }
      }

      var2 = (kotlin.coroutines.experimental.ContinuationInterceptor)(new ExperimentalContinuationInterceptorMigration(var0));
      return var2;
   }

   public static final kotlin.coroutines.experimental.CoroutineContext toExperimentalCoroutineContext(CoroutineContext var0) {
      ContinuationInterceptor var1;
      CoroutineContext var3;
      kotlin.coroutines.experimental.CoroutineContext var4;
      label22: {
         Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalCoroutineContext");
         var1 = (ContinuationInterceptor)var0.get((CoroutineContext.Key)ContinuationInterceptor.Key);
         ContextMigration var2 = (ContextMigration)var0.get((CoroutineContext.Key)ContextMigration.Key);
         var3 = var0.minusKey((CoroutineContext.Key)ContinuationInterceptor.Key).minusKey((CoroutineContext.Key)ContextMigration.Key);
         if (var2 != null) {
            var4 = var2.getContext();
            if (var4 != null) {
               break label22;
            }
         }

         var4 = (kotlin.coroutines.experimental.CoroutineContext)kotlin.coroutines.experimental.EmptyCoroutineContext.INSTANCE;
      }

      if (var3 != EmptyCoroutineContext.INSTANCE) {
         var4 = var4.plus((kotlin.coroutines.experimental.CoroutineContext)(new ExperimentalContextMigration(var3)));
      }

      if (var1 != null) {
         var4 = var4.plus((kotlin.coroutines.experimental.CoroutineContext)toExperimentalContinuationInterceptor(var1));
      }

      return var4;
   }

   public static final <R> Function1<kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function1<? super Continuation<? super R>, ? extends Object> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalSuspendFunction");
      return (Function1)(new ExperimentalSuspendFunction0Migration(var0));
   }

   public static final <T1, R> Function2<T1, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function2<? super T1, ? super Continuation<? super R>, ? extends Object> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalSuspendFunction");
      return (Function2)(new ExperimentalSuspendFunction1Migration(var0));
   }

   public static final <T1, T2, R> Function3<T1, T2, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toExperimentalSuspendFunction");
      return (Function3)(new ExperimentalSuspendFunction2Migration(var0));
   }
}
