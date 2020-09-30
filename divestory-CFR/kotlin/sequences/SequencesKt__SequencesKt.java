/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.sequences.SequencesKt__SequencesKt$Sequence
 *  kotlin.sequences.SequencesKt__SequencesKt$asSequence$
 *  kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined
 *  kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence
 *  kotlin.sequences.SequencesKt__SequencesKt$flatten
 *  kotlin.sequences.SequencesKt__SequencesKt$generateSequence
 *  kotlin.sequences.SequencesKt__SequencesKt$ifEmpty
 */
package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.ConstrainedOnceSequence;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.GeneratorSequence;
import kotlin.sequences.SequencesKt;
import kotlin.sequences.SequencesKt__SequencesJVMKt;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt__SequencesKt$asSequence$;
import kotlin.sequences.TransformingSequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000b\u001a=\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000bH\u0007\u00a2\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010\"\u0002H\u0002\u00a2\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00050\u000bH\u0002\u00a2\u0006\u0002\b\u0016\u001a)\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00170\u0001H\u0007\u00a2\u0006\u0002\b\u0018\u001a\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a@\u0010\u001c\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001e0\u001d\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00150\u001d0\u0001\u00a8\u0006\u001f"}, d2={"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/sequences/SequencesKt")
class SequencesKt__SequencesKt
extends SequencesKt__SequencesJVMKt {
    private static final <T> kotlin.sequences.Sequence<T> Sequence(Function0<? extends Iterator<? extends T>> function0) {
        return new kotlin.sequences.Sequence<T>(function0){
            final /* synthetic */ Function0 $iterator;
            {
                this.$iterator = function0;
            }

            public Iterator<T> iterator() {
                return (Iterator)this.$iterator.invoke();
            }
        };
    }

    public static final <T> kotlin.sequences.Sequence<T> asSequence(Iterator<? extends T> iterator2) {
        Intrinsics.checkParameterIsNotNull(iterator2, "$this$asSequence");
        return SequencesKt.constrainOnce(new kotlin.sequences.Sequence<T>(iterator2){
            final /* synthetic */ Iterator $this_asSequence$inlined;
            {
                this.$this_asSequence$inlined = iterator2;
            }

            public Iterator<T> iterator() {
                return this.$this_asSequence$inlined;
            }
        });
    }

    public static final <T> kotlin.sequences.Sequence<T> constrainOnce(kotlin.sequences.Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "$this$constrainOnce");
        if (!(sequence instanceof ConstrainedOnceSequence)) return new ConstrainedOnceSequence<T>(sequence);
        return sequence;
    }

    public static final <T> kotlin.sequences.Sequence<T> emptySequence() {
        return EmptySequence.INSTANCE;
    }

    public static final <T> kotlin.sequences.Sequence<T> flatten(kotlin.sequences.Sequence<? extends kotlin.sequences.Sequence<? extends T>> sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "$this$flatten");
        return SequencesKt__SequencesKt.flatten$SequencesKt__SequencesKt(sequence, flatten.1.INSTANCE);
    }

    private static final <T, R> kotlin.sequences.Sequence<R> flatten$SequencesKt__SequencesKt(kotlin.sequences.Sequence<? extends T> sequence, Function1<? super T, ? extends Iterator<? extends R>> function1) {
        if (!(sequence instanceof TransformingSequence)) return new FlatteningSequence(sequence, flatten.3.INSTANCE, function1);
        return ((TransformingSequence)sequence).flatten$kotlin_stdlib(function1);
    }

    public static final <T> kotlin.sequences.Sequence<T> flattenSequenceOfIterable(kotlin.sequences.Sequence<? extends Iterable<? extends T>> sequence) {
        Intrinsics.checkParameterIsNotNull(sequence, "$this$flatten");
        return SequencesKt__SequencesKt.flatten$SequencesKt__SequencesKt(sequence, flatten.2.INSTANCE);
    }

    public static final <T> kotlin.sequences.Sequence<T> generateSequence(T object, Function1<? super T, ? extends T> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "nextFunction");
        if (object != null) return new GeneratorSequence<T>(new Function0<T>(object){
            final /* synthetic */ Object $seed;
            {
                this.$seed = object;
                super(0);
            }

            public final T invoke() {
                return (T)this.$seed;
            }
        }, function1);
        return EmptySequence.INSTANCE;
    }

    public static final <T> kotlin.sequences.Sequence<T> generateSequence(Function0<? extends T> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "nextFunction");
        return SequencesKt.constrainOnce((kotlin.sequences.Sequence)new GeneratorSequence<T>(function0, new Function1<T, T>(function0){
            final /* synthetic */ Function0 $nextFunction;
            {
                this.$nextFunction = function0;
                super(1);
            }

            public final T invoke(T t) {
                Intrinsics.checkParameterIsNotNull(t, "it");
                return (T)this.$nextFunction.invoke();
            }
        }));
    }

    public static final <T> kotlin.sequences.Sequence<T> generateSequence(Function0<? extends T> function0, Function1<? super T, ? extends T> function1) {
        Intrinsics.checkParameterIsNotNull(function0, "seedFunction");
        Intrinsics.checkParameterIsNotNull(function1, "nextFunction");
        return new GeneratorSequence<T>(function0, function1);
    }

    public static final <T> kotlin.sequences.Sequence<T> ifEmpty(kotlin.sequences.Sequence<? extends T> sequence, Function0<? extends kotlin.sequences.Sequence<? extends T>> function0) {
        Intrinsics.checkParameterIsNotNull(sequence, "$this$ifEmpty");
        Intrinsics.checkParameterIsNotNull(function0, "defaultValue");
        return SequencesKt.sequence(new Function2<kotlin.sequences.SequenceScope<? super T>, Continuation<? super kotlin.Unit>, Object>(sequence, function0, null){
            final /* synthetic */ Function0 $defaultValue;
            final /* synthetic */ kotlin.sequences.Sequence $this_ifEmpty;
            Object L$0;
            Object L$1;
            int label;
            private kotlin.sequences.SequenceScope p$;
            {
                this.$this_ifEmpty = sequence;
                this.$defaultValue = function0;
                super(2, continuation2);
            }

            public final Continuation<kotlin.Unit> create(Object object, Continuation<?> function2) {
                Intrinsics.checkParameterIsNotNull(function2, "completion");
                function2 = new /* invalid duplicate definition of identical inner class */;
                function2.p$ = (kotlin.sequences.SequenceScope)object;
                return function2;
            }

            public final Object invoke(Object object, Object object2) {
                return (this.create(object, (Continuation)object2)).invokeSuspend(kotlin.Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object iterator2) {
                Object object = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int n = this.label;
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) throw new java.lang.IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    object = (Iterator)this.L$1;
                    object = (kotlin.sequences.SequenceScope)this.L$0;
                    kotlin.ResultKt.throwOnFailure(iterator2);
                    return kotlin.Unit.INSTANCE;
                }
                kotlin.ResultKt.throwOnFailure(iterator2);
                kotlin.sequences.SequenceScope sequenceScope = this.p$;
                iterator2 = this.$this_ifEmpty.iterator();
                if (iterator2.hasNext()) {
                    this.L$0 = sequenceScope;
                    this.L$1 = iterator2;
                    this.label = 1;
                    if (sequenceScope.yieldAll(iterator2, (Continuation<kotlin.Unit>)this) != object) return kotlin.Unit.INSTANCE;
                    return object;
                }
                kotlin.sequences.Sequence sequence = (kotlin.sequences.Sequence)this.$defaultValue.invoke();
                this.L$0 = sequenceScope;
                this.L$1 = iterator2;
                this.label = 2;
                if (sequenceScope.yieldAll(sequence, (Continuation<kotlin.Unit>)this) != object) return kotlin.Unit.INSTANCE;
                return object;
            }
        });
    }

    private static final <T> kotlin.sequences.Sequence<T> orEmpty(kotlin.sequences.Sequence<? extends T> sequence) {
        if (sequence == null) return SequencesKt.emptySequence();
        return sequence;
    }

    public static final <T> kotlin.sequences.Sequence<T> sequenceOf(T ... object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        boolean bl = ((T[])object).length == 0;
        if (!bl) return ArraysKt.asSequence(object);
        return SequencesKt.emptySequence();
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(kotlin.sequences.Sequence<? extends Pair<? extends T, ? extends R>> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$unzip");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<Pair<T, R>> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            arrayList.add(((Pair)object).getFirst());
            arrayList2.add(((Pair)object).getSecond());
        }
        return TuplesKt.to(arrayList, arrayList2);
    }
}

