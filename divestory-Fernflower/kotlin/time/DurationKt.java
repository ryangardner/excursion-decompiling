package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\b2\u0006\u0010&\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\r2\u0006\u0010&\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a \u0010+\u001a\u00020\u0007*\u00020\b2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010.\u001a \u0010+\u001a\u00020\u0007*\u00020\r2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010/\u001a \u0010+\u001a\u00020\u0007*\u00020\u00102\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u00100\"\u001b\u0010\u0000\u001a\u00020\u00018Â\u0002X\u0082\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000e\u001a\u0004\b\u000b\u0010\u000f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u0011\u001a\u0004\b\u000b\u0010\u0012\"!\u0010\u0013\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\n\u001a\u0004\b\u0015\u0010\f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0015\u0010\u000f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0012\"!\u0010\u0016\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\n\u001a\u0004\b\u0018\u0010\f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0018\u0010\u000f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0012\"!\u0010\u0019\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\n\u001a\u0004\b\u001b\u0010\f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u000f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u0011\u001a\u0004\b\u001b\u0010\u0012\"!\u0010\u001c\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\n\u001a\u0004\b\u001e\u0010\f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001e\u0010\u000f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u0011\u001a\u0004\b\u001e\u0010\u0012\"!\u0010\u001f\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\n\u001a\u0004\b!\u0010\f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u000e\u001a\u0004\b!\u0010\u000f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u0011\u001a\u0004\b!\u0010\u0012\"!\u0010\"\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\n\u001a\u0004\b$\u0010\f\"!\u0010\"\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u000e\u001a\u0004\b$\u0010\u000f\"!\u0010\"\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u0011\u001a\u0004\b$\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019¨\u00061"},
   d2 = {"storageUnit", "Ljava/util/concurrent/TimeUnit;", "storageUnit$annotations", "()V", "getStorageUnit", "()Ljava/util/concurrent/TimeUnit;", "days", "Lkotlin/time/Duration;", "", "days$annotations", "(D)V", "getDays", "(D)D", "", "(I)V", "(I)D", "", "(J)V", "(J)D", "hours", "hours$annotations", "getHours", "microseconds", "microseconds$annotations", "getMicroseconds", "milliseconds", "milliseconds$annotations", "getMilliseconds", "minutes", "minutes$annotations", "getMinutes", "nanoseconds", "nanoseconds$annotations", "getNanoseconds", "seconds", "seconds$annotations", "getSeconds", "times", "duration", "times-kIfJnKk", "(DD)D", "times-mvk6XK0", "(ID)D", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)D", "(ILjava/util/concurrent/TimeUnit;)D", "(JLjava/util/concurrent/TimeUnit;)D", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class DurationKt {
   // $FF: synthetic method
   public static final TimeUnit access$getStorageUnit$p() {
      return getStorageUnit();
   }

   // $FF: synthetic method
   public static void days$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void days$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void days$annotations(long var0) {
   }

   public static final double getDays(double var0) {
      return toDuration(var0, TimeUnit.DAYS);
   }

   public static final double getDays(int var0) {
      return toDuration(var0, TimeUnit.DAYS);
   }

   public static final double getDays(long var0) {
      return toDuration(var0, TimeUnit.DAYS);
   }

   public static final double getHours(double var0) {
      return toDuration(var0, TimeUnit.HOURS);
   }

   public static final double getHours(int var0) {
      return toDuration(var0, TimeUnit.HOURS);
   }

   public static final double getHours(long var0) {
      return toDuration(var0, TimeUnit.HOURS);
   }

   public static final double getMicroseconds(double var0) {
      return toDuration(var0, TimeUnit.MICROSECONDS);
   }

   public static final double getMicroseconds(int var0) {
      return toDuration(var0, TimeUnit.MICROSECONDS);
   }

   public static final double getMicroseconds(long var0) {
      return toDuration(var0, TimeUnit.MICROSECONDS);
   }

   public static final double getMilliseconds(double var0) {
      return toDuration(var0, TimeUnit.MILLISECONDS);
   }

   public static final double getMilliseconds(int var0) {
      return toDuration(var0, TimeUnit.MILLISECONDS);
   }

   public static final double getMilliseconds(long var0) {
      return toDuration(var0, TimeUnit.MILLISECONDS);
   }

   public static final double getMinutes(double var0) {
      return toDuration(var0, TimeUnit.MINUTES);
   }

   public static final double getMinutes(int var0) {
      return toDuration(var0, TimeUnit.MINUTES);
   }

   public static final double getMinutes(long var0) {
      return toDuration(var0, TimeUnit.MINUTES);
   }

   public static final double getNanoseconds(double var0) {
      return toDuration(var0, TimeUnit.NANOSECONDS);
   }

   public static final double getNanoseconds(int var0) {
      return toDuration(var0, TimeUnit.NANOSECONDS);
   }

   public static final double getNanoseconds(long var0) {
      return toDuration(var0, TimeUnit.NANOSECONDS);
   }

   public static final double getSeconds(double var0) {
      return toDuration(var0, TimeUnit.SECONDS);
   }

   public static final double getSeconds(int var0) {
      return toDuration(var0, TimeUnit.SECONDS);
   }

   public static final double getSeconds(long var0) {
      return toDuration(var0, TimeUnit.SECONDS);
   }

   private static final TimeUnit getStorageUnit() {
      return TimeUnit.NANOSECONDS;
   }

   // $FF: synthetic method
   public static void hours$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void hours$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void hours$annotations(long var0) {
   }

   // $FF: synthetic method
   public static void microseconds$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void microseconds$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void microseconds$annotations(long var0) {
   }

   // $FF: synthetic method
   public static void milliseconds$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void milliseconds$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void milliseconds$annotations(long var0) {
   }

   // $FF: synthetic method
   public static void minutes$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void minutes$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void minutes$annotations(long var0) {
   }

   // $FF: synthetic method
   public static void nanoseconds$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void nanoseconds$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void nanoseconds$annotations(long var0) {
   }

   // $FF: synthetic method
   public static void seconds$annotations(double var0) {
   }

   // $FF: synthetic method
   public static void seconds$annotations(int var0) {
   }

   // $FF: synthetic method
   public static void seconds$annotations(long var0) {
   }

   // $FF: synthetic method
   private static void storageUnit$annotations() {
   }

   private static final double times_kIfJnKk/* $FF was: times-kIfJnKk*/(double var0, double var2) {
      return Duration.times-impl(var2, var0);
   }

   private static final double times_mvk6XK0/* $FF was: times-mvk6XK0*/(int var0, double var1) {
      return Duration.times-impl(var1, var0);
   }

   public static final double toDuration(double var0, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      return Duration.constructor-impl(DurationUnitKt.convertDurationUnit(var0, var2, TimeUnit.NANOSECONDS));
   }

   public static final double toDuration(int var0, TimeUnit var1) {
      Intrinsics.checkParameterIsNotNull(var1, "unit");
      return toDuration((double)var0, var1);
   }

   public static final double toDuration(long var0, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      return toDuration((double)var0, var2);
   }
}
