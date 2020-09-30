package kotlin.ranges;

import kotlin.Metadata;
import kotlin.collections.LongIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\t\u0010\u0014\u001a\u00020\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"},
   d2 = {"Lkotlin/ranges/LongProgression;", "", "", "start", "endInclusive", "step", "(JJJ)V", "first", "getFirst", "()J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/LongIterator;", "toString", "", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public class LongProgression implements Iterable<Long>, KMappedMarker {
   public static final LongProgression.Companion Companion = new LongProgression.Companion((DefaultConstructorMarker)null);
   private final long first;
   private final long last;
   private final long step;

   public LongProgression(long var1, long var3, long var5) {
      if (var5 != 0L) {
         if (var5 != Long.MIN_VALUE) {
            this.first = var1;
            this.last = ProgressionUtilKt.getProgressionLastElement(var1, var3, var5);
            this.step = var5;
         } else {
            throw (Throwable)(new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation."));
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      }
   }

   public boolean equals(Object var1) {
      boolean var4;
      label29: {
         if (var1 instanceof LongProgression) {
            if (this.isEmpty() && ((LongProgression)var1).isEmpty()) {
               break label29;
            }

            long var2 = this.first;
            LongProgression var5 = (LongProgression)var1;
            if (var2 == var5.first && this.last == var5.last && this.step == var5.step) {
               break label29;
            }
         }

         var4 = false;
         return var4;
      }

      var4 = true;
      return var4;
   }

   public final long getFirst() {
      return this.first;
   }

   public final long getLast() {
      return this.last;
   }

   public final long getStep() {
      return this.step;
   }

   public int hashCode() {
      int var1;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         long var2 = (long)31;
         long var4 = this.first;
         long var6 = this.last;
         long var8 = this.step;
         var1 = (int)(var2 * ((var4 ^ var4 >>> 32) * var2 + (var6 ^ var6 >>> 32)) + (var8 ^ var8 >>> 32));
      }

      return var1;
   }

   public boolean isEmpty() {
      long var1 = this.step;
      boolean var3 = true;
      long var4 = this.first;
      long var6 = this.last;
      if (var1 > 0L) {
         if (var4 > var6) {
            return var3;
         }
      } else if (var4 < var6) {
         return var3;
      }

      var3 = false;
      return var3;
   }

   public LongIterator iterator() {
      return (LongIterator)(new LongProgressionIterator(this.first, this.last, this.step));
   }

   public String toString() {
      long var1 = this.step;
      StringBuilder var3 = new StringBuilder;
      if (var1 > 0L) {
         var3.<init>();
         var3.append(this.first);
         var3.append("..");
         var3.append(this.last);
         var3.append(" step ");
         var1 = this.step;
      } else {
         var3.<init>();
         var3.append(this.first);
         var3.append(" downTo ");
         var3.append(this.last);
         var3.append(" step ");
         var1 = -this.step;
      }

      var3.append(var1);
      return var3.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006¨\u0006\t"},
      d2 = {"Lkotlin/ranges/LongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/LongProgression;", "rangeStart", "", "rangeEnd", "step", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final LongProgression fromClosedRange(long var1, long var3, long var5) {
         return new LongProgression(var1, var3, var5);
      }
   }
}
