/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.sequences.DropWhileSequence$iterator
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.DropWhileSequence;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lkotlin/sequences/DropWhileSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "predicate", "Lkotlin/Function1;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class DropWhileSequence<T>
implements Sequence<T> {
    private final Function1<T, Boolean> predicate;
    private final Sequence<T> sequence;

    public DropWhileSequence(Sequence<? extends T> sequence, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        this.sequence = sequence;
        this.predicate = function1;
    }

    public static final /* synthetic */ Function1 access$getPredicate$p(DropWhileSequence dropWhileSequence) {
        return dropWhileSequence.predicate;
    }

    public static final /* synthetic */ Sequence access$getSequence$p(DropWhileSequence dropWhileSequence) {
        return dropWhileSequence.sequence;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(this){
            private int dropState;
            private final Iterator<T> iterator;
            private T nextItem;
            final /* synthetic */ DropWhileSequence this$0;
            {
                this.this$0 = dropWhileSequence;
                this.iterator = DropWhileSequence.access$getSequence$p(dropWhileSequence).iterator();
                this.dropState = -1;
            }

            private final void drop() {
                T t;
                do {
                    if (!this.iterator.hasNext()) {
                        this.dropState = 0;
                        return;
                    }
                    t = this.iterator.next();
                } while (((Boolean)DropWhileSequence.access$getPredicate$p(this.this$0).invoke(t)).booleanValue());
                this.nextItem = t;
                this.dropState = 1;
            }

            public final int getDropState() {
                return this.dropState;
            }

            public final Iterator<T> getIterator() {
                return this.iterator;
            }

            public final T getNextItem() {
                return this.nextItem;
            }

            public boolean hasNext() {
                boolean bl;
                if (this.dropState == -1) {
                    this.drop();
                }
                int n = this.dropState;
                boolean bl2 = bl = true;
                if (n == 1) return bl2;
                if (!this.iterator.hasNext()) return false;
                return bl;
            }

            public T next() {
                if (this.dropState == -1) {
                    this.drop();
                }
                if (this.dropState != 1) return this.iterator.next();
                T t = this.nextItem;
                this.nextItem = null;
                this.dropState = 0;
                return t;
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public final void setDropState(int n) {
                this.dropState = n;
            }

            public final void setNextItem(T t) {
                this.nextItem = t;
            }
        };
    }
}

