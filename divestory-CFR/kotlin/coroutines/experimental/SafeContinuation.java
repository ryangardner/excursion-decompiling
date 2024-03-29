/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.experimental;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0001\u0018\u0000 \u0015*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002\u0015\u0016B\u0015\b\u0011\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u00a2\u0006\u0002\u0010\u0004B\u001f\b\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\n\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0001J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lkotlin/coroutines/experimental/SafeContinuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "delegate", "(Lkotlin/coroutines/experimental/Continuation;)V", "initialResult", "", "(Lkotlin/coroutines/experimental/Continuation;Ljava/lang/Object;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "result", "getResult", "resume", "", "value", "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "Companion", "Fail", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
public final class SafeContinuation<T>
implements Continuation<T> {
    public static final Companion Companion = new Companion(null);
    private static final AtomicReferenceFieldUpdater<SafeContinuation<?>, Object> RESULT;
    private static final Object RESUMED;
    private static final Object UNDECIDED;
    private final Continuation<T> delegate;
    private volatile Object result;

    static {
        UNDECIDED = new Object();
        RESUMED = new Object();
        RESULT = AtomicReferenceFieldUpdater.newUpdater(SafeContinuation.class, Object.class, "result");
    }

    public SafeContinuation(Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(continuation2, "delegate");
        this(continuation2, UNDECIDED);
    }

    public SafeContinuation(Continuation<? super T> continuation2, Object object) {
        Intrinsics.checkParameterIsNotNull(continuation2, "delegate");
        this.delegate = continuation2;
        this.result = object;
    }

    @Override
    public CoroutineContext getContext() {
        return this.delegate.getContext();
    }

    public final Object getResult() {
        Object object = this.result;
        Object object2 = UNDECIDED;
        Object object3 = object;
        if (object == object2) {
            if (RESULT.compareAndSet(this, object2, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            object3 = this.result;
        }
        if (object3 == RESUMED) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (object3 instanceof Fail) throw ((Fail)object3).getException();
        return object3;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void resume(T var1_1) {
        do lbl-1000: // 3 sources:
        {
            block1 : {
                if ((var2_2 = this.result) != (var3_3 = SafeContinuation.UNDECIDED)) break block1;
                if (!SafeContinuation.RESULT.compareAndSet(this, var3_3, var1_1)) ** GOTO lbl-1000
                return;
            }
            if (var2_2 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) throw (Throwable)new IllegalStateException("Already resumed");
        } while (!SafeContinuation.RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SafeContinuation.RESUMED));
        this.delegate.resume(var1_1);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void resumeWithException(Throwable var1_1) {
        Intrinsics.checkParameterIsNotNull(var1_1, "exception");
        do lbl-1000: // 3 sources:
        {
            block1 : {
                if ((var2_2 = this.result) != (var3_3 = SafeContinuation.UNDECIDED)) break block1;
                if (!SafeContinuation.RESULT.compareAndSet(this, var3_3, new Fail(var1_1))) ** GOTO lbl-1000
                return;
            }
            if (var2_2 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) throw (Throwable)new IllegalStateException("Already resumed");
        } while (!SafeContinuation.RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SafeContinuation.RESUMED));
        this.delegate.resumeWithException(var1_1);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002RZ\u0010\u0003\u001aF\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0001 \u0006*\"\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u00040\u00048\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lkotlin/coroutines/experimental/SafeContinuation$Companion;", "", "()V", "RESULT", "Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;", "Lkotlin/coroutines/experimental/SafeContinuation;", "kotlin.jvm.PlatformType", "RESULT$annotations", "RESUMED", "UNDECIDED", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void RESULT$annotations() {
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/coroutines/experimental/SafeContinuation$Fail;", "", "exception", "", "(Ljava/lang/Throwable;)V", "getException", "()Ljava/lang/Throwable;", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
    private static final class Fail {
        private final Throwable exception;

        public Fail(Throwable throwable) {
            Intrinsics.checkParameterIsNotNull(throwable, "exception");
            this.exception = throwable;
        }

        public final Throwable getException() {
            return this.exception;
        }
    }

}

