/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.coroutines.ContinuationKt$Continuation
 */
package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0002\b\u0004\u001a<\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\u0006\u0010\t\u001a\u00020\u00012\u001a\b\u0004\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\f\u0012\u0004\u0012\u00020\r0\u000bH\u0087\b\u00f8\u0001\u0000\u001a3\u0010\u000e\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\u001a\b\u0004\u0010\u000f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0004\u0012\u00020\r0\u000bH\u0087H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001aD\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\u0000\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001a]\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016\u00a2\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u001a&\u0010\u001a\u001a\u00020\r\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001b\u001a\u0002H\bH\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a!\u0010\u001d\u001a\u00020\r\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001e\u001a\u00020\u001fH\u0087\b\u001a>\u0010 \u001a\u00020\r\"\u0004\b\u0000\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!\u001aW\u0010 \u001a\u00020\r\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016\u00a2\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"\"\u001b\u0010\u0000\u001a\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2={"coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "coroutineContext$annotations", "()V", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Continuation", "Lkotlin/coroutines/Continuation;", "T", "context", "resumeWith", "Lkotlin/Function1;", "Lkotlin/Result;", "", "suspendCoroutine", "block", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCoroutine", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resume", "value", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "resumeWithException", "exception", "", "startCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ContinuationKt {
    private static final <T> kotlin.coroutines.Continuation<T> Continuation(CoroutineContext coroutineContext, Function1<? super Result<? extends T>, Unit> function1) {
        return new kotlin.coroutines.Continuation<T>(coroutineContext, function1){
            final /* synthetic */ CoroutineContext $context;
            final /* synthetic */ Function1 $resumeWith;
            {
                this.$context = coroutineContext;
                this.$resumeWith = function1;
            }

            public CoroutineContext getContext() {
                return this.$context;
            }

            public void resumeWith(Object object) {
                this.$resumeWith.invoke(Result.box-impl(object));
            }
        };
    }

    public static /* synthetic */ void coroutineContext$annotations() {
    }

    public static final <T> kotlin.coroutines.Continuation<Unit> createCoroutine(Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends Object> function1, kotlin.coroutines.Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(function1, "$this$createCoroutine");
        Intrinsics.checkParameterIsNotNull(continuation2, "completion");
        return new SafeContinuation<Unit>(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(function1, continuation2)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
    }

    public static final <R, T> kotlin.coroutines.Continuation<Unit> createCoroutine(Function2<? super R, ? super kotlin.coroutines.Continuation<? super T>, ? extends Object> function2, R r, kotlin.coroutines.Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(function2, "$this$createCoroutine");
        Intrinsics.checkParameterIsNotNull(continuation2, "completion");
        return new SafeContinuation<Unit>(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(function2, r, continuation2)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
    }

    private static final CoroutineContext getCoroutineContext() {
        throw (Throwable)new NotImplementedError("Implemented as intrinsic");
    }

    private static final <T> void resume(kotlin.coroutines.Continuation<? super T> continuation2, T t) {
        Result.Companion companion = Result.Companion;
        continuation2.resumeWith(Result.constructor-impl(t));
    }

    private static final <T> void resumeWithException(kotlin.coroutines.Continuation<? super T> continuation2, Throwable throwable) {
        Result.Companion companion = Result.Companion;
        continuation2.resumeWith(Result.constructor-impl(ResultKt.createFailure(throwable)));
    }

    public static final <T> void startCoroutine(Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends Object> object, kotlin.coroutines.Continuation<? super T> object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$startCoroutine");
        Intrinsics.checkParameterIsNotNull(object2, "completion");
        object = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(object, object2));
        object2 = Unit.INSTANCE;
        Result.Companion companion = Result.Companion;
        object.resumeWith(Result.constructor-impl(object2));
    }

    public static final <R, T> void startCoroutine(Function2<? super R, ? super kotlin.coroutines.Continuation<? super T>, ? extends Object> object, R object2, kotlin.coroutines.Continuation<? super T> object3) {
        Intrinsics.checkParameterIsNotNull(object, "$this$startCoroutine");
        Intrinsics.checkParameterIsNotNull(object3, "completion");
        object = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(object, object2, object3));
        object2 = Unit.INSTANCE;
        object3 = Result.Companion;
        object.resumeWith(Result.constructor-impl(object2));
    }

    private static final <T> Object suspendCoroutine(Function1<? super kotlin.coroutines.Continuation<? super T>, Unit> object, kotlin.coroutines.Continuation<? super T> continuation2) {
        InlineMarker.mark(0);
        SafeContinuation<T> safeContinuation = new SafeContinuation<T>(IntrinsicsKt.intercepted(continuation2));
        object.invoke(safeContinuation);
        object = safeContinuation.getOrThrow();
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation2);
        }
        InlineMarker.mark(1);
        return object;
    }
}

