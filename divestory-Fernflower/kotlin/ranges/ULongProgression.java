package kotlin.ranges;

import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.collections.ULongIterator;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\t\u0010\u0016\u001a\u00020\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\b\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"},
   d2 = {"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst", "()J", "J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/ULongIterator;", "toString", "", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public class ULongProgression implements Iterable<ULong>, KMappedMarker {
   public static final ULongProgression.Companion Companion = new ULongProgression.Companion((DefaultConstructorMarker)null);
   private final long first;
   private final long last;
   private final long step;

   private ULongProgression(long var1, long var3, long var5) {
      if (var5 != 0L) {
         if (var5 != Long.MIN_VALUE) {
            this.first = var1;
            this.last = UProgressionUtilKt.getProgressionLastElement-7ftBX0g(var1, var3, var5);
            this.step = var5;
         } else {
            throw (Throwable)(new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation."));
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      }
   }

   // $FF: synthetic method
   public ULongProgression(long var1, long var3, long var5, DefaultConstructorMarker var7) {
      this(var1, var3, var5);
   }

   public boolean equals(Object var1) {
      boolean var4;
      label29: {
         if (var1 instanceof ULongProgression) {
            if (this.isEmpty() && ((ULongProgression)var1).isEmpty()) {
               break label29;
            }

            long var2 = this.first;
            ULongProgression var5 = (ULongProgression)var1;
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
         long var2 = this.first;
         var1 = (int)ULong.constructor-impl(var2 ^ ULong.constructor-impl(var2 >>> 32));
         var2 = this.last;
         int var4 = (int)ULong.constructor-impl(var2 ^ ULong.constructor-impl(var2 >>> 32));
         var2 = this.step;
         var1 = (int)(var2 ^ var2 >>> 32) + (var1 * 31 + var4) * 31;
      }

      return var1;
   }

   public boolean isEmpty() {
      long var1 = this.step;
      boolean var3 = true;
      int var4 = UnsignedKt.ulongCompare(this.first, this.last);
      if (var1 > 0L) {
         if (var4 > 0) {
            return var3;
         }
      } else if (var4 < 0) {
         return var3;
      }

      var3 = false;
      return var3;
   }

   public ULongIterator iterator() {
      return (ULongIterator)(new ULongProgressionIterator(this.first, this.last, this.step, (DefaultConstructorMarker)null));
   }

   public String toString() {
      long var1 = this.step;
      StringBuilder var3 = new StringBuilder;
      if (var1 > 0L) {
         var3.<init>();
         var3.append(ULong.toString-impl(this.first));
         var3.append("..");
         var3.append(ULong.toString-impl(this.last));
         var3.append(" step ");
         var1 = this.step;
      } else {
         var3.<init>();
         var3.append(ULong.toString-impl(this.first));
         var3.append(" downTo ");
         var3.append(ULong.toString-impl(this.last));
         var3.append(" step ");
         var1 = -this.step;
      }

      var3.append(var1);
      return var3.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
      d2 = {"Lkotlin/ranges/ULongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/ULongProgression;", "rangeStart", "Lkotlin/ULong;", "rangeEnd", "step", "", "fromClosedRange-7ftBX0g", "(JJJ)Lkotlin/ranges/ULongProgression;", "kotlin-stdlib"},
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

      public final ULongProgression fromClosedRange_7ftBX0g/* $FF was: fromClosedRange-7ftBX0g*/(long var1, long var3, long var5) {
         return new ULongProgression(var1, var3, var5, (DefaultConstructorMarker)null);
      }
   }
}
