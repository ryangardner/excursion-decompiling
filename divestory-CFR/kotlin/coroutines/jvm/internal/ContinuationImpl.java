/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.CompletedContinuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b!\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005B!\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J\b\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "completion", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "_context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "intercepted", "releaseIntercepted", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class ContinuationImpl
extends BaseContinuationImpl {
    private final CoroutineContext _context;
    private transient Continuation<Object> intercepted;

    public ContinuationImpl(Continuation<Object> continuation2) {
        CoroutineContext coroutineContext = continuation2 != null ? continuation2.getContext() : null;
        this(continuation2, coroutineContext);
    }

    public ContinuationImpl(Continuation<Object> continuation2, CoroutineContext coroutineContext) {
        super(continuation2);
        this._context = coroutineContext;
    }

    @Override
    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        if (coroutineContext != null) return coroutineContext;
        Intrinsics.throwNpe();
        return coroutineContext;
    }

    public final Continuation<Object> intercepted() {
        Object object = this.intercepted;
        if (object != null) {
            return object;
        }
        object = (ContinuationInterceptor)this.getContext().get(ContinuationInterceptor.Key);
        if (object == null || (object = object.interceptContinuation(this)) == null) {
            object = this;
        }
        this.intercepted = object;
        return object;
    }

    @Override
    protected void releaseIntercepted() {
        Continuation<Object> continuation2 = this.intercepted;
        if (continuation2 != null && continuation2 != this) {
            Object e = this.getContext().get(ContinuationInterceptor.Key);
            if (e == null) {
                Intrinsics.throwNpe();
            }
            ((ContinuationInterceptor)e).releaseInterceptedContinuation(continuation2);
        }
        this.intercepted = CompletedContinuation.INSTANCE;
    }
}

