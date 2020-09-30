package kotlin.time;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a0\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a0\u0010\u0000\u001a\u00020\u0001*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\n\u001a4\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"},
   d2 = {"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)D", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/TimeSource;", "(Lkotlin/time/TimeSource;Lkotlin/jvm/functions/Function0;)D", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class MeasureTimeKt {
   public static final double measureTime(Function0<Unit> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "block");
      TimeMark var1 = ((TimeSource)TimeSource.Monotonic.INSTANCE).markNow();
      var0.invoke();
      return var1.elapsedNow();
   }

   public static final double measureTime(TimeSource var0, Function0<Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$measureTime");
      Intrinsics.checkParameterIsNotNull(var1, "block");
      TimeMark var2 = var0.markNow();
      var1.invoke();
      return var2.elapsedNow();
   }

   public static final <T> TimedValue<T> measureTimedValue(Function0<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "block");
      TimeMark var1 = ((TimeSource)TimeSource.Monotonic.INSTANCE).markNow();
      return new TimedValue(var0.invoke(), var1.elapsedNow(), (DefaultConstructorMarker)null);
   }

   public static final <T> TimedValue<T> measureTimedValue(TimeSource var0, Function0<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$measureTimedValue");
      Intrinsics.checkParameterIsNotNull(var1, "block");
      TimeMark var2 = var0.markNow();
      return new TimedValue(var1.invoke(), var2.elapsedNow(), (DefaultConstructorMarker)null);
   }
}
