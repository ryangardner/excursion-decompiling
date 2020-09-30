/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.sequences.MergingSequence$iterator
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.MergingSequence;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004B;\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00020\u000bH\u0096\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/sequences/MergingSequence;", "T1", "T2", "V", "Lkotlin/sequences/Sequence;", "sequence1", "sequence2", "transform", "Lkotlin/Function2;", "(Lkotlin/sequences/Sequence;Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class MergingSequence<T1, T2, V>
implements Sequence<V> {
    private final Sequence<T1> sequence1;
    private final Sequence<T2> sequence2;
    private final Function2<T1, T2, V> transform;

    public MergingSequence(Sequence<? extends T1> sequence, Sequence<? extends T2> sequence2, Function2<? super T1, ? super T2, ? extends V> function2) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence1");
        Intrinsics.checkParameterIsNotNull(sequence2, "sequence2");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        this.sequence1 = sequence;
        this.sequence2 = sequence2;
        this.transform = function2;
    }

    public static final /* synthetic */ Sequence access$getSequence1$p(MergingSequence mergingSequence) {
        return mergingSequence.sequence1;
    }

    public static final /* synthetic */ Sequence access$getSequence2$p(MergingSequence mergingSequence) {
        return mergingSequence.sequence2;
    }

    public static final /* synthetic */ Function2 access$getTransform$p(MergingSequence mergingSequence) {
        return mergingSequence.transform;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>(this){
            private final Iterator<T1> iterator1;
            private final Iterator<T2> iterator2;
            final /* synthetic */ MergingSequence this$0;
            {
                this.this$0 = mergingSequence;
                this.iterator1 = MergingSequence.access$getSequence1$p(mergingSequence).iterator();
                this.iterator2 = MergingSequence.access$getSequence2$p(mergingSequence).iterator();
            }

            public final Iterator<T1> getIterator1() {
                return this.iterator1;
            }

            public final Iterator<T2> getIterator2() {
                return this.iterator2;
            }

            public boolean hasNext() {
                if (!this.iterator1.hasNext()) return false;
                if (!this.iterator2.hasNext()) return false;
                return true;
            }

            public V next() {
                return (V)MergingSequence.access$getTransform$p(this.this$0).invoke(this.iterator1.next(), this.iterator2.next());
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }
}

