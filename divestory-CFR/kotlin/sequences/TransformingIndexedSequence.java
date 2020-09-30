/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.sequences.TransformingIndexedSequence$iterator
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.TransformingIndexedSequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B-\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\nH\u0096\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lkotlin/sequences/TransformingIndexedSequence;", "T", "R", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function2;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class TransformingIndexedSequence<T, R>
implements Sequence<R> {
    private final Sequence<T> sequence;
    private final Function2<Integer, T, R> transformer;

    public TransformingIndexedSequence(Sequence<? extends T> sequence, Function2<? super Integer, ? super T, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(function2, "transformer");
        this.sequence = sequence;
        this.transformer = function2;
    }

    public static final /* synthetic */ Sequence access$getSequence$p(TransformingIndexedSequence transformingIndexedSequence) {
        return transformingIndexedSequence.sequence;
    }

    public static final /* synthetic */ Function2 access$getTransformer$p(TransformingIndexedSequence transformingIndexedSequence) {
        return transformingIndexedSequence.transformer;
    }

    @Override
    public Iterator<R> iterator() {
        return new Iterator<R>(this){
            private int index;
            private final Iterator<T> iterator;
            final /* synthetic */ TransformingIndexedSequence this$0;
            {
                this.this$0 = transformingIndexedSequence;
                this.iterator = TransformingIndexedSequence.access$getSequence$p(transformingIndexedSequence).iterator();
            }

            public final int getIndex() {
                return this.index;
            }

            public final Iterator<T> getIterator() {
                return this.iterator;
            }

            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            public R next() {
                Function2 function2 = TransformingIndexedSequence.access$getTransformer$p(this.this$0);
                int n = this.index;
                this.index = n + 1;
                if (n >= 0) return function2.invoke(n, this.iterator.next());
                kotlin.collections.CollectionsKt.throwIndexOverflow();
                return function2.invoke(n, this.iterator.next());
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public final void setIndex(int n) {
                this.index = n;
            }
        };
    }
}

