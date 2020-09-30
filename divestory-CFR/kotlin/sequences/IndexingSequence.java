/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.sequences.IndexingSequence$iterator
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.IndexingSequence;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u00a2\u0006\u0002\u0010\u0005J\u0015\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0007H\u0096\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"Lkotlin/sequences/IndexingSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/collections/IndexedValue;", "sequence", "(Lkotlin/sequences/Sequence;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class IndexingSequence<T>
implements Sequence<IndexedValue<? extends T>> {
    private final Sequence<T> sequence;

    public IndexingSequence(Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        this.sequence = sequence;
    }

    public static final /* synthetic */ Sequence access$getSequence$p(IndexingSequence indexingSequence) {
        return indexingSequence.sequence;
    }

    @Override
    public Iterator<IndexedValue<T>> iterator() {
        return new Iterator<IndexedValue<? extends T>>(this){
            private int index;
            private final Iterator<T> iterator;
            final /* synthetic */ IndexingSequence this$0;
            {
                this.this$0 = indexingSequence;
                this.iterator = IndexingSequence.access$getSequence$p(indexingSequence).iterator();
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

            public IndexedValue<T> next() {
                int n = this.index;
                this.index = n + 1;
                if (n >= 0) return new IndexedValue<T>(n, this.iterator.next());
                kotlin.collections.CollectionsKt.throwIndexOverflow();
                return new IndexedValue<T>(n, this.iterator.next());
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

