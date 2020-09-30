/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractIterator<T>
extends UnmodifiableIterator<T> {
    @NullableDecl
    private T next;
    private State state = State.NOT_READY;

    protected AbstractIterator() {
    }

    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = this.computeNext();
        if (this.state == State.DONE) return false;
        this.state = State.READY;
        return true;
    }

    protected abstract T computeNext();

    protected final T endOfData() {
        this.state = State.DONE;
        return null;
    }

    @Override
    public final boolean hasNext() {
        boolean bl = this.state != State.FAILED;
        Preconditions.checkState(bl);
        int n = 1.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()];
        if (n == 1) return false;
        if (n == 2) return true;
        return this.tryToComputeNext();
    }

    @Override
    public final T next() {
        if (!this.hasNext()) throw new NoSuchElementException();
        this.state = State.NOT_READY;
        T t = this.next;
        this.next = null;
        return t;
    }

    public final T peek() {
        if (!this.hasNext()) throw new NoSuchElementException();
        return this.next;
    }

    private static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State DONE;
        public static final /* enum */ State FAILED;
        public static final /* enum */ State NOT_READY;
        public static final /* enum */ State READY;

        static {
            State state;
            READY = new State();
            NOT_READY = new State();
            DONE = new State();
            FAILED = state = new State();
            $VALUES = new State[]{READY, NOT_READY, DONE, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }
    }

}

