/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.experimental.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\u00a8\u0006\u0007"}, d2={"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"}, k=2, mv={1, 1, 16})
public final class CoroutineIntrinsics {
    public static final <T> Continuation<T> interceptContinuationIfNeeded(CoroutineContext object, Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(object, "context");
        Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
        Object object2 = (ContinuationInterceptor)object.get(ContinuationInterceptor.Key);
        object = continuation2;
        if (object2 == null) return object;
        object2 = object2.interceptContinuation(continuation2);
        object = continuation2;
        if (object2 == null) return object;
        return object2;
    }

    public static final <T> Continuation<T> normalizeContinuation(Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
        Continuation<Object> continuation3 = !(continuation2 instanceof CoroutineImpl) ? null : continuation2;
        Continuation<Object> continuation4 = (CoroutineImpl)continuation3;
        continuation3 = continuation2;
        if (continuation4 == null) return continuation3;
        continuation4 = ((CoroutineImpl)continuation4).getFacade();
        continuation3 = continuation2;
        if (continuation4 == null) return continuation3;
        return continuation4;
    }
}

