/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.experimental.migration.CoroutinesMigrationKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0005J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0013"}, d2={"Lkotlin/coroutines/experimental/migration/ExperimentalContinuationMigration;", "T", "Lkotlin/coroutines/experimental/Continuation;", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "getContinuation", "()Lkotlin/coroutines/Continuation;", "resume", "", "value", "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
final class ExperimentalContinuationMigration<T>
implements kotlin.coroutines.experimental.Continuation<T> {
    private final kotlin.coroutines.experimental.CoroutineContext context;
    private final Continuation<T> continuation;

    public ExperimentalContinuationMigration(Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
        this.continuation = continuation2;
        this.context = CoroutinesMigrationKt.toExperimentalCoroutineContext(continuation2.getContext());
    }

    @Override
    public kotlin.coroutines.experimental.CoroutineContext getContext() {
        return this.context;
    }

    public final Continuation<T> getContinuation() {
        return this.continuation;
    }

    @Override
    public void resume(T t) {
        Continuation<T> continuation2 = this.continuation;
        Result.Companion companion = Result.Companion;
        continuation2.resumeWith(Result.constructor-impl(t));
    }

    @Override
    public void resumeWithException(Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(throwable, "exception");
        Continuation<T> continuation2 = this.continuation;
        Result.Companion companion = Result.Companion;
        continuation2.resumeWith(Result.constructor-impl(ResultKt.createFailure(throwable)));
    }
}

