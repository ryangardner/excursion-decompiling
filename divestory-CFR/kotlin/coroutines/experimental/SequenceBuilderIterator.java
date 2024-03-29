/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.experimental;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.coroutines.experimental.SequenceBuilder;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0002J\u000e\u0010\u001a\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u001bJ\r\u0010\u001c\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u0010\u001bJ\u0015\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0017H\u0016J\u0019\u0010\"\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00028\u0000H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u001f\u0010$\u001a\u00020\u00052\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u0004\u0018\u00018\u0000X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0012R\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\t\u00a8\u0006'"}, d2={"Lkotlin/coroutines/experimental/SequenceBuilderIterator;", "T", "Lkotlin/coroutines/experimental/SequenceBuilder;", "", "Lkotlin/coroutines/experimental/Continuation;", "", "()V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "nextIterator", "nextStep", "getNextStep", "()Lkotlin/coroutines/experimental/Continuation;", "setNextStep", "(Lkotlin/coroutines/experimental/Continuation;)V", "nextValue", "Ljava/lang/Object;", "state", "", "Lkotlin/coroutines/experimental/State;", "exceptionalState", "", "hasNext", "", "next", "()Ljava/lang/Object;", "nextNotReady", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "yield", "(Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "yieldAll", "iterator", "(Ljava/util/Iterator;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
final class SequenceBuilderIterator<T>
extends SequenceBuilder<T>
implements Iterator<T>,
Continuation<Unit>,
KMappedMarker {
    private Iterator<? extends T> nextIterator;
    private Continuation<? super Unit> nextStep;
    private T nextValue;
    private int state;

    private final Throwable exceptionalState() {
        int n = this.state;
        if (n == 4) {
            return new NoSuchElementException();
        }
        if (n == 5) return new IllegalStateException("Iterator has failed.");
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unexpected state of the iterator: ");
        ((StringBuilder)serializable).append(this.state);
        return new IllegalStateException(((StringBuilder)serializable).toString());
    }

    private final T nextNotReady() {
        if (!this.hasNext()) throw (Throwable)new NoSuchElementException();
        return this.next();
    }

    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    public final Continuation<Unit> getNextStep() {
        return this.nextStep;
    }

    @Override
    public boolean hasNext() {
        do {
            int n;
            Object object;
            if ((n = this.state) != 0) {
                if (n != 1) {
                    if (n == 2) return true;
                    if (n == 3) return true;
                    if (n != 4) throw this.exceptionalState();
                    return false;
                }
                object = this.nextIterator;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                if (object.hasNext()) {
                    this.state = 2;
                    return true;
                }
                this.nextIterator = null;
            }
            this.state = 5;
            object = this.nextStep;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            this.nextStep = null;
            object.resume(Unit.INSTANCE);
        } while (true);
    }

    @Override
    public T next() {
        int n = this.state;
        if (n == 0) return this.nextNotReady();
        if (n == 1) return this.nextNotReady();
        if (n != 2) {
            if (n != 3) throw this.exceptionalState();
            this.state = 0;
            T t = this.nextValue;
            this.nextValue = null;
            return t;
        }
        this.state = 1;
        Iterator<T> iterator2 = this.nextIterator;
        if (iterator2 != null) return iterator2.next();
        Intrinsics.throwNpe();
        return iterator2.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void resume(Unit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "value");
        this.state = 4;
    }

    @Override
    public void resumeWithException(Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(throwable, "exception");
        throw throwable;
    }

    public final void setNextStep(Continuation<? super Unit> continuation2) {
        this.nextStep = continuation2;
    }

    @Override
    public Object yield(T t, Continuation<? super Unit> continuation2) {
        this.nextValue = t;
        this.state = 3;
        this.setNextStep(CoroutineIntrinsics.normalizeContinuation(continuation2));
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    @Override
    public Object yieldAll(Iterator<? extends T> iterator2, Continuation<? super Unit> continuation2) {
        if (!iterator2.hasNext()) {
            return Unit.INSTANCE;
        }
        this.nextIterator = iterator2;
        this.state = 2;
        this.setNextStep(CoroutineIntrinsics.normalizeContinuation(continuation2));
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

