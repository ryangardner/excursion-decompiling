/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.DelimitedRangesSequence$iterator
 */
package kotlin.text;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import kotlin.text.DelimitedRangesSequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BY\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012:\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\u0010\u000fJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011H\u0096\u0002RB\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t\u00a2\u0006\u0002\b\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lkotlin/text/DelimitedRangesSequence;", "Lkotlin/sequences/Sequence;", "Lkotlin/ranges/IntRange;", "input", "", "startIndex", "", "limit", "getNextMatch", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "currentIndex", "Lkotlin/Pair;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/CharSequence;IILkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
final class DelimitedRangesSequence
implements Sequence<IntRange> {
    private final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;
    private final CharSequence input;
    private final int limit;
    private final int startIndex;

    public DelimitedRangesSequence(CharSequence charSequence, int n, int n2, Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Intrinsics.checkParameterIsNotNull(function2, "getNextMatch");
        this.input = charSequence;
        this.startIndex = n;
        this.limit = n2;
        this.getNextMatch = function2;
    }

    public static final /* synthetic */ Function2 access$getGetNextMatch$p(DelimitedRangesSequence delimitedRangesSequence) {
        return delimitedRangesSequence.getNextMatch;
    }

    public static final /* synthetic */ CharSequence access$getInput$p(DelimitedRangesSequence delimitedRangesSequence) {
        return delimitedRangesSequence.input;
    }

    public static final /* synthetic */ int access$getLimit$p(DelimitedRangesSequence delimitedRangesSequence) {
        return delimitedRangesSequence.limit;
    }

    public static final /* synthetic */ int access$getStartIndex$p(DelimitedRangesSequence delimitedRangesSequence) {
        return delimitedRangesSequence.startIndex;
    }

    @Override
    public Iterator<IntRange> iterator() {
        return new Iterator<IntRange>(this){
            private int counter;
            private int currentStartIndex;
            private IntRange nextItem;
            private int nextSearchIndex;
            private int nextState;
            final /* synthetic */ DelimitedRangesSequence this$0;
            {
                int n;
                this.this$0 = delimitedRangesSequence;
                this.nextState = -1;
                this.currentStartIndex = n = kotlin.ranges.RangesKt.coerceIn(DelimitedRangesSequence.access$getStartIndex$p(delimitedRangesSequence), 0, DelimitedRangesSequence.access$getInput$p(delimitedRangesSequence).length());
                this.nextSearchIndex = n;
            }

            /*
             * Unable to fully structure code
             */
            private final void calcNext() {
                block6 : {
                    var1_1 = this.nextSearchIndex;
                    var2_2 = 0;
                    if (var1_1 < 0) {
                        this.nextState = 0;
                        this.nextItem = null;
                        return;
                    }
                    if (DelimitedRangesSequence.access$getLimit$p(this.this$0) <= 0) break block6;
                    this.counter = var1_1 = this.counter + 1;
                    if (var1_1 >= DelimitedRangesSequence.access$getLimit$p(this.this$0)) ** GOTO lbl-1000
                }
                if (this.nextSearchIndex > DelimitedRangesSequence.access$getInput$p(this.this$0).length()) lbl-1000: // 2 sources:
                {
                    this.nextItem = new IntRange(this.currentStartIndex, kotlin.text.StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
                    this.nextSearchIndex = -1;
                } else {
                    var3_3 = (Pair)DelimitedRangesSequence.access$getGetNextMatch$p(this.this$0).invoke(DelimitedRangesSequence.access$getInput$p(this.this$0), this.nextSearchIndex);
                    if (var3_3 == null) {
                        this.nextItem = new IntRange(this.currentStartIndex, kotlin.text.StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
                        this.nextSearchIndex = -1;
                    } else {
                        var1_1 = ((java.lang.Number)var3_3.component1()).intValue();
                        var4_4 = ((java.lang.Number)var3_3.component2()).intValue();
                        this.nextItem = kotlin.ranges.RangesKt.until(this.currentStartIndex, var1_1);
                        this.currentStartIndex = var1_1 += var4_4;
                        if (var4_4 == 0) {
                            var2_2 = 1;
                        }
                        this.nextSearchIndex = var1_1 + var2_2;
                    }
                }
                this.nextState = 1;
            }

            public final int getCounter() {
                return this.counter;
            }

            public final int getCurrentStartIndex() {
                return this.currentStartIndex;
            }

            public final IntRange getNextItem() {
                return this.nextItem;
            }

            public final int getNextSearchIndex() {
                return this.nextSearchIndex;
            }

            public final int getNextState() {
                return this.nextState;
            }

            public boolean hasNext() {
                if (this.nextState == -1) {
                    this.calcNext();
                }
                int n = this.nextState;
                boolean bl = true;
                if (n != 1) return false;
                return bl;
            }

            public IntRange next() {
                if (this.nextState == -1) {
                    this.calcNext();
                }
                if (this.nextState == 0) throw (java.lang.Throwable)new java.util.NoSuchElementException();
                IntRange intRange = this.nextItem;
                if (intRange == null) throw new kotlin.TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
                this.nextItem = null;
                this.nextState = -1;
                return intRange;
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public final void setCounter(int n) {
                this.counter = n;
            }

            public final void setCurrentStartIndex(int n) {
                this.currentStartIndex = n;
            }

            public final void setNextItem(IntRange intRange) {
                this.nextItem = intRange;
            }

            public final void setNextSearchIndex(int n) {
                this.nextSearchIndex = n;
            }

            public final void setNextState(int n) {
                this.nextState = n;
            }
        };
    }
}

