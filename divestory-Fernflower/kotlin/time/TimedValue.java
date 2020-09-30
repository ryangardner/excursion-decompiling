package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0018\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u0011\u0010\u000e\u001a\u00020\u0005HÆ\u0003ø\u0001\u0000¢\u0006\u0002\u0010\bJ-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u0005ø\u0001\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"},
   d2 = {"Lkotlin/time/TimedValue;", "T", "", "value", "duration", "Lkotlin/time/Duration;", "(Ljava/lang/Object;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getDuration", "()D", "D", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "copy-RFiDyg4", "(Ljava/lang/Object;D)Lkotlin/time/TimedValue;", "equals", "", "other", "hashCode", "", "toString", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TimedValue<T> {
   private final double duration;
   private final T value;

   private TimedValue(T var1, double var2) {
      this.value = var1;
      this.duration = var2;
   }

   // $FF: synthetic method
   public TimedValue(Object var1, double var2, DefaultConstructorMarker var4) {
      this(var1, var2);
   }

   // $FF: synthetic method
   public static TimedValue copy_RFiDyg4$default/* $FF was: copy-RFiDyg4$default*/(TimedValue var0, Object var1, double var2, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.value;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.duration;
      }

      return var0.copy-RFiDyg4(var1, var2);
   }

   public final T component1() {
      return this.value;
   }

   public final double component2() {
      return this.duration;
   }

   public final TimedValue<T> copy_RFiDyg4/* $FF was: copy-RFiDyg4*/(T var1, double var2) {
      return new TimedValue(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof TimedValue)) {
            return false;
         }

         TimedValue var2 = (TimedValue)var1;
         if (!Intrinsics.areEqual(this.value, var2.value) || Double.compare(this.duration, var2.duration) != 0) {
            return false;
         }
      }

      return true;
   }

   public final double getDuration() {
      return this.duration;
   }

   public final T getValue() {
      return this.value;
   }

   public int hashCode() {
      Object var1 = this.value;
      int var2;
      if (var1 != null) {
         var2 = var1.hashCode();
      } else {
         var2 = 0;
      }

      long var3 = Double.doubleToLongBits(this.duration);
      return var2 * 31 + (int)(var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TimedValue(value=");
      var1.append(this.value);
      var1.append(", duration=");
      var1.append(Duration.toString-impl(this.duration));
      var1.append(")");
      return var1.toString();
   }
}
