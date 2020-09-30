package kotlin.ranges;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\t¨\u0006\u0019"},
   d2 = {"Lkotlin/ranges/ClosedDoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(DD)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Double;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class ClosedDoubleRange implements ClosedFloatingPointRange<Double> {
   private final double _endInclusive;
   private final double _start;

   public ClosedDoubleRange(double var1, double var3) {
      this._start = var1;
      this._endInclusive = var3;
   }

   public boolean contains(double var1) {
      boolean var3;
      if (var1 >= this._start && var1 <= this._endInclusive) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var4;
      label27: {
         if (var1 instanceof ClosedDoubleRange) {
            if (this.isEmpty() && ((ClosedDoubleRange)var1).isEmpty()) {
               break label27;
            }

            double var2 = this._start;
            ClosedDoubleRange var5 = (ClosedDoubleRange)var1;
            if (var2 == var5._start && this._endInclusive == var5._endInclusive) {
               break label27;
            }
         }

         var4 = false;
         return var4;
      }

      var4 = true;
      return var4;
   }

   public Double getEndInclusive() {
      return this._endInclusive;
   }

   public Double getStart() {
      return this._start;
   }

   public int hashCode() {
      int var1;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         var1 = Double.valueOf(this._start).hashCode() * 31 + Double.valueOf(this._endInclusive).hashCode();
      }

      return var1;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this._start > this._endInclusive) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean lessThanOrEquals(double var1, double var3) {
      boolean var5;
      if (var1 <= var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._start);
      var1.append("..");
      var1.append(this._endInclusive);
      return var1.toString();
   }
}
