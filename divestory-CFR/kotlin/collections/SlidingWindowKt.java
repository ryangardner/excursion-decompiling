/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.collections.SlidingWindowKt$windowedIterator
 *  kotlin.collections.SlidingWindowKt$windowedSequence$
 *  kotlin.collections.SlidingWindowKt$windowedSequence$$inlined
 *  kotlin.collections.SlidingWindowKt$windowedSequence$$inlined$Sequence
 */
package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.EmptyIterator;
import kotlin.collections.SlidingWindowKt;
import kotlin.collections.SlidingWindowKt$windowedSequence$;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u00a8\u0006\u000f"}, d2={"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class SlidingWindowKt {
    public static final void checkWindowSizeStep(int n, int n2) {
        CharSequence charSequence;
        boolean bl = n > 0 && n2 > 0;
        if (bl) return;
        if (n != n2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Both size ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" and step ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" must be greater than zero.");
            charSequence = ((StringBuilder)charSequence).toString();
            throw (Throwable)new IllegalArgumentException(((Object)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("size ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" must be greater than zero.");
        charSequence = ((StringBuilder)charSequence).toString();
        throw (Throwable)new IllegalArgumentException(((Object)charSequence).toString());
    }

    public static final <T> Iterator<List<T>> windowedIterator(Iterator<? extends T> iterator2, int n, int n2, boolean bl, boolean bl2) {
        Intrinsics.checkParameterIsNotNull(iterator2, "iterator");
        if (iterator2.hasNext()) return SequencesKt.iterator(new Function2<kotlin.sequences.SequenceScope<? super List<? extends T>>, Continuation<? super kotlin.Unit>, Object>(n, n2, iterator2, bl2, bl, null){
            final /* synthetic */ Iterator $iterator;
            final /* synthetic */ boolean $partialWindows;
            final /* synthetic */ boolean $reuseBuffer;
            final /* synthetic */ int $size;
            final /* synthetic */ int $step;
            int I$0;
            int I$1;
            int I$2;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;
            private kotlin.sequences.SequenceScope p$;
            {
                this.$size = n;
                this.$step = n2;
                this.$iterator = iterator2;
                this.$reuseBuffer = bl;
                this.$partialWindows = bl2;
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

            /*
             * Unable to fully structure code
             */
            public final Object invokeSuspend(Object var1_1) {
                block18 : {
                    block19 : {
                        block23 : {
                            block20 : {
                                block21 : {
                                    block22 : {
                                        var2_2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                        var3_3 = this.label;
                                        if (var3_3 == 0) break block18;
                                        if (var3_3 == 1) break block19;
                                        if (var3_3 == 2) break block20;
                                        if (var3_3 == 3) break block21;
                                        if (var3_3 == 4) break block22;
                                        if (var3_3 != 5) throw new java.lang.IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        var4_4 = (kotlin.collections.RingBuffer)this.L$1;
                                        break block23;
                                    }
                                    var5_6 = (kotlin.collections.RingBuffer)this.L$1;
                                    var3_3 = this.I$1;
                                    var6_8 = this.I$0;
                                    var7_9 = (kotlin.sequences.SequenceScope)this.L$0;
                                    kotlin.ResultKt.throwOnFailure(var1_1);
                                    var8_11 = this;
                                    ** GOTO lbl156
                                }
                                var5_6 = (Iterator)this.L$3;
                                var4_5 = (kotlin.collections.RingBuffer)this.L$1;
                                var6_8 = this.I$1;
                                var3_3 = this.I$0;
                                var8_11 = (kotlin.sequences.SequenceScope)this.L$0;
                                kotlin.ResultKt.throwOnFailure(var1_1);
                                var9_12 = this;
                                var1_1 = var4_5;
                                ** GOTO lbl123
                            }
                            var4_4 = (java.util.ArrayList)this.L$1;
                        }
                        var4_4 = (kotlin.sequences.SequenceScope)this.L$0;
                        kotlin.ResultKt.throwOnFailure(var1_1);
                        return kotlin.Unit.INSTANCE;
                    }
                    var7_10 = (Iterator)this.L$3;
                    var10_13 = (java.util.ArrayList)this.L$1;
                    var3_3 = this.I$1;
                    var11_23 = this.I$0;
                    var4_5 = (kotlin.sequences.SequenceScope)this.L$0;
                    kotlin.ResultKt.throwOnFailure(var1_1);
                    var5_7 = this;
                    var1_1 = var10_13;
                    ** GOTO lbl81
                }
                kotlin.ResultKt.throwOnFailure(var1_1);
                var4_5 = this.p$;
                var11_23 = kotlin.ranges.RangesKt.coerceAtMost(this.$size, 1024);
                var6_8 = this.$step - this.$size;
                if (var6_8 < 0) {
                    var1_1 = new kotlin.collections.RingBuffer<T>(var11_23);
                    var5_6 = this.$iterator;
                    var7_9 = var4_5;
                    var4_5 = this;
                    var3_3 = var11_23;
                } else {
                    var1_1 = new java.util.ArrayList<E>(var11_23);
                    var12_24 = 0;
                    var7_10 = this.$iterator;
                    var5_7 = this;
                    var3_3 = var6_8;
                    var6_8 = var12_24;
                    while (var7_10.hasNext()) {
                        var10_15 = var7_10.next();
                        if (var6_8 > 0) {
                            --var6_8;
                            continue;
                        }
                        var1_1.add(var10_15);
                        if (var1_1.size() != var5_7.$size) continue;
                        var5_7.L$0 = var4_5;
                        var5_7.I$0 = var11_23;
                        var5_7.I$1 = var3_3;
                        var5_7.L$1 = var1_1;
                        var5_7.I$2 = var6_8;
                        var5_7.L$2 = var10_15;
                        var5_7.L$3 = var7_10;
                        var5_7.label = 1;
                        if (var4_5.yield(var1_1, var5_7) == var2_2) {
                            return var2_2;
                        }
lbl81: // 3 sources:
                        if (var5_7.$reuseBuffer) {
                            var1_1.clear();
                        } else {
                            var1_1 = new java.util.ArrayList<E>(var5_7.$size);
                        }
                        var12_24 = var3_3;
                        var6_8 = var3_3;
                        var3_3 = var12_24;
                    }
                    if ((((java.util.Collection)var1_1).isEmpty() ^ true) == false) return kotlin.Unit.INSTANCE;
                    if (!var5_7.$partialWindows) {
                        if (var1_1.size() != var5_7.$size) return kotlin.Unit.INSTANCE;
                    }
                    var5_7.L$0 = var4_5;
                    var5_7.I$0 = var11_23;
                    var5_7.I$1 = var3_3;
                    var5_7.L$1 = var1_1;
                    var5_7.I$2 = var6_8;
                    var5_7.label = 2;
                    if (var4_5.yield(var1_1, var5_7) != var2_2) return kotlin.Unit.INSTANCE;
                    return var2_2;
                }
                while (var5_6.hasNext()) {
                    var8_11 = var5_6.next();
                    var1_1.add(var8_11);
                    if (!var1_1.isFull()) continue;
                    var11_23 = var1_1.size();
                    if (var11_23 < (var12_25 = var4_5.$size)) {
                        var1_1 = var1_1.expanded(var12_25);
                        continue;
                    }
                    if (var4_5.$reuseBuffer) {
                        var10_17 = var1_1;
                    } else {
                        var10_18 = new java.util.ArrayList<E>((java.util.Collection)var1_1);
                    }
                    var4_5.L$0 = var7_9;
                    var4_5.I$0 = var3_3;
                    var4_5.I$1 = var6_8;
                    var4_5.L$1 = var1_1;
                    var4_5.L$2 = var8_11;
                    var4_5.L$3 = var5_6;
                    var4_5.label = 3;
                    var8_11 = var7_9;
                    var9_12 = var4_5;
                    if (var7_9.yield(var10_16, (Continuation<kotlin.Unit>)var4_5) == var2_2) {
                        return var2_2;
                    }
lbl123: // 3 sources:
                    var1_1.removeFirst(var9_12.$step);
                    var7_9 = var8_11;
                    var4_5 = var9_12;
                }
                if (var4_5.$partialWindows == false) return kotlin.Unit.INSTANCE;
                var5_6 = var1_1;
                var11_23 = var6_8;
                var1_1 = var7_9;
                var6_8 = var3_3;
                var3_3 = var11_23;
                do {
                    if (var5_6.size() <= var4_5.$step) {
                        if ((((java.util.Collection)var5_6).isEmpty() ^ true) == false) return kotlin.Unit.INSTANCE;
                        var4_5.L$0 = var1_1;
                        var4_5.I$0 = var6_8;
                        var4_5.I$1 = var3_3;
                        var4_5.L$1 = var5_6;
                        var4_5.label = 5;
                        if (var1_1.yield(var5_6, (Continuation<kotlin.Unit>)var4_5) != var2_2) return kotlin.Unit.INSTANCE;
                        return var2_2;
                    }
                    if (var4_5.$reuseBuffer) {
                        var10_20 = var5_6;
                    } else {
                        var10_21 = new java.util.ArrayList<E>(var5_6);
                    }
                    var4_5.L$0 = var1_1;
                    var4_5.I$0 = var6_8;
                    var4_5.I$1 = var3_3;
                    var4_5.L$1 = var5_6;
                    var4_5.label = 4;
                    var7_9 = var1_1;
                    var8_11 = var4_5;
                    if (var1_1.yield(var10_22, (Continuation<kotlin.Unit>)var4_5) == var2_2) {
                        return var2_2;
                    }
lbl156: // 3 sources:
                    var5_6.removeFirst(var8_11.$step);
                    var1_1 = var7_9;
                    var4_5 = var8_11;
                } while (true);
            }
        });
        return EmptyIterator.INSTANCE;
    }

    public static final <T> Sequence<List<T>> windowedSequence(Sequence<? extends T> sequence, int n, int n2, boolean bl, boolean bl2) {
        Intrinsics.checkParameterIsNotNull(sequence, "$this$windowedSequence");
        SlidingWindowKt.checkWindowSizeStep(n, n2);
        return new Sequence<List<? extends T>>(sequence, n, n2, bl, bl2){
            final /* synthetic */ boolean $partialWindows$inlined;
            final /* synthetic */ boolean $reuseBuffer$inlined;
            final /* synthetic */ int $size$inlined;
            final /* synthetic */ int $step$inlined;
            final /* synthetic */ Sequence $this_windowedSequence$inlined;
            {
                this.$this_windowedSequence$inlined = sequence;
                this.$size$inlined = n;
                this.$step$inlined = n2;
                this.$partialWindows$inlined = bl;
                this.$reuseBuffer$inlined = bl2;
            }

            public Iterator<List<? extends T>> iterator() {
                return SlidingWindowKt.windowedIterator(this.$this_windowedSequence$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined);
            }
        };
    }
}

