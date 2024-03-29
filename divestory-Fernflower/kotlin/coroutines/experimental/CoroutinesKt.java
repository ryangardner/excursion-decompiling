package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a%\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0081\b\u001a3\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\u001a\b\u0004\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\t\u0012\u0004\u0012\u00020\u00070\u000fH\u0087Hø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aD\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\t\"\u0004\b\u0000\u0010\u000e*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\t\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000f2\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u000e0\tH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a]\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\t\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u000e*#\b\u0001\u0012\u0004\u0012\u0002H\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\t\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\u00152\u0006\u0010\u0016\u001a\u0002H\u00132\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u000e0\tH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a>\u0010\u0018\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u000e*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\t\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000f2\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u000e0\tH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001aW\u0010\u0018\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u000e*#\b\u0001\u0012\u0004\u0012\u0002H\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\t\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\u00152\u0006\u0010\u0016\u001a\u0002H\u00132\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u000e0\tH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\"\u001b\u0010\u0000\u001a\u00020\u00018Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\t¨\u0006\u001b"},
   d2 = {"coroutineContext", "Lkotlin/coroutines/experimental/CoroutineContext;", "coroutineContext$annotations", "()V", "getCoroutineContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "processBareContinuationResume", "", "completion", "Lkotlin/coroutines/experimental/Continuation;", "block", "Lkotlin/Function0;", "", "suspendCoroutine", "T", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "createCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)V", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)V", "kotlin-stdlib-coroutines"},
   k = 2,
   mv = {1, 1, 16}
)
public final class CoroutinesKt {
   // $FF: synthetic method
   public static void coroutineContext$annotations() {
   }

   public static final <T> Continuation<Unit> createCoroutine(Function1<? super Continuation<? super T>, ? extends Object> var0, Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutine");
      Intrinsics.checkParameterIsNotNull(var1, "completion");
      return (Continuation)(new SafeContinuation(IntrinsicsKt.createCoroutineUnchecked(var0, var1), IntrinsicsKt.getCOROUTINE_SUSPENDED()));
   }

   public static final <R, T> Continuation<Unit> createCoroutine(Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, R var1, Continuation<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutine");
      Intrinsics.checkParameterIsNotNull(var2, "completion");
      return (Continuation)(new SafeContinuation(IntrinsicsKt.createCoroutineUnchecked(var0, var1, var2), IntrinsicsKt.getCOROUTINE_SUSPENDED()));
   }

   private static final CoroutineContext getCoroutineContext() {
      throw (Throwable)(new NotImplementedError("Implemented as intrinsic"));
   }

   private static final void processBareContinuationResume(Continuation<?> var0, Function0<? extends Object> var1) {
      Throwable var10000;
      label99: {
         boolean var10001;
         Object var14;
         try {
            var14 = var1.invoke();
            if (var14 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               return;
            }
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label99;
         }

         if (var0 != null) {
            label95:
            try {
               var0.resume(var14);
               return;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label95;
            }
         } else {
            label91:
            try {
               TypeCastException var16 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
               throw var16;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               break label91;
            }
         }
      }

      Throwable var15 = var10000;
      var0.resumeWithException(var15);
   }

   public static final <T> void startCoroutine(Function1<? super Continuation<? super T>, ? extends Object> var0, Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startCoroutine");
      Intrinsics.checkParameterIsNotNull(var1, "completion");
      IntrinsicsKt.createCoroutineUnchecked(var0, var1).resume(Unit.INSTANCE);
   }

   public static final <R, T> void startCoroutine(Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, R var1, Continuation<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startCoroutine");
      Intrinsics.checkParameterIsNotNull(var2, "completion");
      IntrinsicsKt.createCoroutineUnchecked(var0, var1, var2).resume(Unit.INSTANCE);
   }

   public static final <T> Object suspendCoroutine(Function1<? super Continuation<? super T>, Unit> var0, Continuation<? super T> var1) {
      SafeContinuation var2 = new SafeContinuation(CoroutineIntrinsics.normalizeContinuation(var1));
      var0.invoke(var2);
      return var2.getResult();
   }

   private static final Object suspendCoroutine$$forInline(Function1 var0, Continuation var1) {
      InlineMarker.mark(0);
      SafeContinuation var3 = new SafeContinuation(CoroutineIntrinsics.normalizeContinuation(var1));
      var0.invoke(var3);
      Object var2 = var3.getResult();
      InlineMarker.mark(1);
      return var2;
   }
}
