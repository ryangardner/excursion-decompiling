/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationUnitKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\b2\u0006\u0010&\u001a\u00020\u0007H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b'\u0010(\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\r2\u0006\u0010&\u001a\u00020\u0007H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*\u001a \u0010+\u001a\u00020\u0007*\u00020\b2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.\u001a \u0010+\u001a\u00020\u0007*\u00020\r2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010/\u001a \u0010+\u001a\u00020\u0007*\u00020\u00102\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100\"\u001b\u0010\u0000\u001a\u00020\u00018\u00c2\u0002X\u0082\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\u000e\u001a\u0004\b\u000b\u0010\u000f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\u0011\u001a\u0004\b\u000b\u0010\u0012\"!\u0010\u0013\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\n\u001a\u0004\b\u0015\u0010\f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0015\u0010\u000f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0012\"!\u0010\u0016\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\n\u001a\u0004\b\u0018\u0010\f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0018\u0010\u000f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0012\"!\u0010\u0019\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001a\u0010\n\u001a\u0004\b\u001b\u0010\f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u000f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001a\u0010\u0011\u001a\u0004\b\u001b\u0010\u0012\"!\u0010\u001c\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\n\u001a\u0004\b\u001e\u0010\f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001e\u0010\u000f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u0011\u001a\u0004\b\u001e\u0010\u0012\"!\u0010\u001f\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b \u0010\n\u001a\u0004\b!\u0010\f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b \u0010\u000e\u001a\u0004\b!\u0010\u000f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b \u0010\u0011\u001a\u0004\b!\u0010\u0012\"!\u0010\"\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b#\u0010\n\u001a\u0004\b$\u0010\f\"!\u0010\"\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b#\u0010\u000e\u001a\u0004\b$\u0010\u000f\"!\u0010\"\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b#\u0010\u0011\u001a\u0004\b$\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00061"}, d2={"storageUnit", "Ljava/util/concurrent/TimeUnit;", "storageUnit$annotations", "()V", "getStorageUnit", "()Ljava/util/concurrent/TimeUnit;", "days", "Lkotlin/time/Duration;", "", "days$annotations", "(D)V", "getDays", "(D)D", "", "(I)V", "(I)D", "", "(J)V", "(J)D", "hours", "hours$annotations", "getHours", "microseconds", "microseconds$annotations", "getMicroseconds", "milliseconds", "milliseconds$annotations", "getMilliseconds", "minutes", "minutes$annotations", "getMinutes", "nanoseconds", "nanoseconds$annotations", "getNanoseconds", "seconds", "seconds$annotations", "getSeconds", "times", "duration", "times-kIfJnKk", "(DD)D", "times-mvk6XK0", "(ID)D", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)D", "(ILjava/util/concurrent/TimeUnit;)D", "(JLjava/util/concurrent/TimeUnit;)D", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class DurationKt {
    public static final /* synthetic */ TimeUnit access$getStorageUnit$p() {
        return DurationKt.getStorageUnit();
    }

    public static /* synthetic */ void days$annotations(double d) {
    }

    public static /* synthetic */ void days$annotations(int n) {
    }

    public static /* synthetic */ void days$annotations(long l) {
    }

    public static final double getDays(double d) {
        return DurationKt.toDuration(d, TimeUnit.DAYS);
    }

    public static final double getDays(int n) {
        return DurationKt.toDuration(n, TimeUnit.DAYS);
    }

    public static final double getDays(long l) {
        return DurationKt.toDuration(l, TimeUnit.DAYS);
    }

    public static final double getHours(double d) {
        return DurationKt.toDuration(d, TimeUnit.HOURS);
    }

    public static final double getHours(int n) {
        return DurationKt.toDuration(n, TimeUnit.HOURS);
    }

    public static final double getHours(long l) {
        return DurationKt.toDuration(l, TimeUnit.HOURS);
    }

    public static final double getMicroseconds(double d) {
        return DurationKt.toDuration(d, TimeUnit.MICROSECONDS);
    }

    public static final double getMicroseconds(int n) {
        return DurationKt.toDuration(n, TimeUnit.MICROSECONDS);
    }

    public static final double getMicroseconds(long l) {
        return DurationKt.toDuration(l, TimeUnit.MICROSECONDS);
    }

    public static final double getMilliseconds(double d) {
        return DurationKt.toDuration(d, TimeUnit.MILLISECONDS);
    }

    public static final double getMilliseconds(int n) {
        return DurationKt.toDuration(n, TimeUnit.MILLISECONDS);
    }

    public static final double getMilliseconds(long l) {
        return DurationKt.toDuration(l, TimeUnit.MILLISECONDS);
    }

    public static final double getMinutes(double d) {
        return DurationKt.toDuration(d, TimeUnit.MINUTES);
    }

    public static final double getMinutes(int n) {
        return DurationKt.toDuration(n, TimeUnit.MINUTES);
    }

    public static final double getMinutes(long l) {
        return DurationKt.toDuration(l, TimeUnit.MINUTES);
    }

    public static final double getNanoseconds(double d) {
        return DurationKt.toDuration(d, TimeUnit.NANOSECONDS);
    }

    public static final double getNanoseconds(int n) {
        return DurationKt.toDuration(n, TimeUnit.NANOSECONDS);
    }

    public static final double getNanoseconds(long l) {
        return DurationKt.toDuration(l, TimeUnit.NANOSECONDS);
    }

    public static final double getSeconds(double d) {
        return DurationKt.toDuration(d, TimeUnit.SECONDS);
    }

    public static final double getSeconds(int n) {
        return DurationKt.toDuration(n, TimeUnit.SECONDS);
    }

    public static final double getSeconds(long l) {
        return DurationKt.toDuration(l, TimeUnit.SECONDS);
    }

    private static final TimeUnit getStorageUnit() {
        return TimeUnit.NANOSECONDS;
    }

    public static /* synthetic */ void hours$annotations(double d) {
    }

    public static /* synthetic */ void hours$annotations(int n) {
    }

    public static /* synthetic */ void hours$annotations(long l) {
    }

    public static /* synthetic */ void microseconds$annotations(double d) {
    }

    public static /* synthetic */ void microseconds$annotations(int n) {
    }

    public static /* synthetic */ void microseconds$annotations(long l) {
    }

    public static /* synthetic */ void milliseconds$annotations(double d) {
    }

    public static /* synthetic */ void milliseconds$annotations(int n) {
    }

    public static /* synthetic */ void milliseconds$annotations(long l) {
    }

    public static /* synthetic */ void minutes$annotations(double d) {
    }

    public static /* synthetic */ void minutes$annotations(int n) {
    }

    public static /* synthetic */ void minutes$annotations(long l) {
    }

    public static /* synthetic */ void nanoseconds$annotations(double d) {
    }

    public static /* synthetic */ void nanoseconds$annotations(int n) {
    }

    public static /* synthetic */ void nanoseconds$annotations(long l) {
    }

    public static /* synthetic */ void seconds$annotations(double d) {
    }

    public static /* synthetic */ void seconds$annotations(int n) {
    }

    public static /* synthetic */ void seconds$annotations(long l) {
    }

    private static /* synthetic */ void storageUnit$annotations() {
    }

    private static final double times-kIfJnKk(double d, double d2) {
        return Duration.times-impl(d2, d);
    }

    private static final double times-mvk6XK0(int n, double d) {
        return Duration.times-impl(d, n);
    }

    public static final double toDuration(double d, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return Duration.constructor-impl(DurationUnitKt.convertDurationUnit(d, timeUnit, TimeUnit.NANOSECONDS));
    }

    public static final double toDuration(int n, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return DurationKt.toDuration((double)n, timeUnit);
    }

    public static final double toDuration(long l, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return DurationKt.toDuration((double)l, timeUnit);
    }
}

