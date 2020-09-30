package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\n\u0010\u0006\u001a\u00060\u0004j\u0002`\u0005H\u0001*\u001e\b\u0007\u0010\u0007\"\u00020\u00042\u00020\u0004B\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\nB\u0002\b\u000b¨\u0006\f"},
   d2 = {"convertDurationUnit", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "DurationUnit", "Lkotlin/SinceKotlin;", "version", "1.3", "Lkotlin/time/ExperimentalTime;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/time/DurationUnitKt"
)
class DurationUnitKt__DurationUnitJvmKt {
   public DurationUnitKt__DurationUnitJvmKt() {
   }

   // $FF: synthetic method
   public static void DurationUnit$annotations() {
   }

   public static final double convertDurationUnit(double var0, TimeUnit var2, TimeUnit var3) {
      Intrinsics.checkParameterIsNotNull(var2, "sourceUnit");
      Intrinsics.checkParameterIsNotNull(var3, "targetUnit");
      long var4 = var3.convert(1L, var2);
      return var4 > 0L ? var0 * (double)var4 : var0 / (double)var2.convert(1L, var3);
   }
}
