package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0003\u0018\u00002\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\u0004H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0004H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"},
   d2 = {"Lkotlin/time/AdjustedTimeMark;", "Lkotlin/time/TimeMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/TimeMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/TimeMark;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class AdjustedTimeMark extends TimeMark {
   private final double adjustment;
   private final TimeMark mark;

   private AdjustedTimeMark(TimeMark var1, double var2) {
      this.mark = var1;
      this.adjustment = var2;
   }

   // $FF: synthetic method
   public AdjustedTimeMark(TimeMark var1, double var2, DefaultConstructorMarker var4) {
      this(var1, var2);
   }

   public double elapsedNow() {
      return Duration.minus-LRDsOJo(this.mark.elapsedNow(), this.adjustment);
   }

   public final double getAdjustment() {
      return this.adjustment;
   }

   public final TimeMark getMark() {
      return this.mark;
   }

   public TimeMark plus_LRDsOJo/* $FF was: plus-LRDsOJo*/(double var1) {
      return (TimeMark)(new AdjustedTimeMark(this.mark, Duration.plus-LRDsOJo(this.adjustment, var1), (DefaultConstructorMarker)null));
   }
}
