package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BY\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012:\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t¢\u0006\u0002\b\u000e¢\u0006\u0002\u0010\u000fJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011H\u0096\u0002RB\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t¢\u0006\u0002\b\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lkotlin/text/DelimitedRangesSequence;", "Lkotlin/sequences/Sequence;", "Lkotlin/ranges/IntRange;", "input", "", "startIndex", "", "limit", "getNextMatch", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "currentIndex", "Lkotlin/Pair;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/CharSequence;IILkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class DelimitedRangesSequence implements Sequence<IntRange> {
   private final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;
   private final CharSequence input;
   private final int limit;
   private final int startIndex;

   public DelimitedRangesSequence(CharSequence var1, int var2, int var3, Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>> var4) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Intrinsics.checkParameterIsNotNull(var4, "getNextMatch");
      super();
      this.input = var1;
      this.startIndex = var2;
      this.limit = var3;
      this.getNextMatch = var4;
   }

   public Iterator<IntRange> iterator() {
      return (Iterator)(new Iterator<IntRange>() {
         private int counter;
         private int currentStartIndex;
         private IntRange nextItem;
         private int nextSearchIndex;
         private int nextState = -1;

         {
            int var2 = RangesKt.coerceIn(DelimitedRangesSequence.this.startIndex, 0, DelimitedRangesSequence.this.input.length());
            this.currentStartIndex = var2;
            this.nextSearchIndex = var2;
         }

         private final void calcNext() {
            int var1 = this.nextSearchIndex;
            byte var2 = 0;
            if (var1 < 0) {
               this.nextState = 0;
               this.nextItem = (IntRange)null;
            } else {
               label26: {
                  label25: {
                     if (DelimitedRangesSequence.this.limit > 0) {
                        var1 = this.counter + 1;
                        this.counter = var1;
                        if (var1 >= DelimitedRangesSequence.this.limit) {
                           break label25;
                        }
                     }

                     if (this.nextSearchIndex <= DelimitedRangesSequence.this.input.length()) {
                        Pair var3 = (Pair)DelimitedRangesSequence.this.getNextMatch.invoke(DelimitedRangesSequence.this.input, this.nextSearchIndex);
                        if (var3 == null) {
                           this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                           this.nextSearchIndex = -1;
                        } else {
                           var1 = ((Number)var3.component1()).intValue();
                           int var4 = ((Number)var3.component2()).intValue();
                           this.nextItem = RangesKt.until(this.currentStartIndex, var1);
                           var1 += var4;
                           this.currentStartIndex = var1;
                           if (var4 == 0) {
                              var2 = 1;
                           }

                           this.nextSearchIndex = var1 + var2;
                        }
                        break label26;
                     }
                  }

                  this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                  this.nextSearchIndex = -1;
               }

               this.nextState = 1;
            }

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

            int var1 = this.nextState;
            boolean var2 = true;
            if (var1 != 1) {
               var2 = false;
            }

            return var2;
         }

         public IntRange next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               IntRange var1 = this.nextItem;
               if (var1 != null) {
                  this.nextItem = (IntRange)null;
                  this.nextState = -1;
                  return var1;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
               }
            } else {
               throw (Throwable)(new NoSuchElementException());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setCounter(int var1) {
            this.counter = var1;
         }

         public final void setCurrentStartIndex(int var1) {
            this.currentStartIndex = var1;
         }

         public final void setNextItem(IntRange var1) {
            this.nextItem = var1;
         }

         public final void setNextSearchIndex(int var1) {
            this.nextSearchIndex = var1;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }
      });
   }
}
