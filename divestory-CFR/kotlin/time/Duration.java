/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnitKt;
import kotlin.time.FormatToDecimalsKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0000H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101H\u00d6\u0003J\t\u00102\u001a\u00020\tH\u00d6\u0001J\r\u00103\u001a\u000200\u00a2\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200\u00a2\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200\u00a2\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200\u00a2\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b\u00a2\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b\u00a2\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b\u00a2\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b\u00a2\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b\u00a2\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[\u00a2\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V\u00a2\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V\u00a2\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016\u00a2\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t\u00a2\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00008F\u00f8\u0001\u0000\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\u0002\n\u0000\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006t"}, d2={"Lkotlin/time/Duration;", "", "value", "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class Duration
implements Comparable<Duration> {
    public static final Companion Companion = new Companion(null);
    private static final double INFINITE;
    private static final double ZERO;
    private final double value;

    static {
        ZERO = Duration.constructor-impl(0.0);
        INFINITE = Duration.constructor-impl(DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY());
    }

    private /* synthetic */ Duration(double d) {
        this.value = d;
    }

    public static final /* synthetic */ Duration box-impl(double d) {
        return new Duration(d);
    }

    public static int compareTo-LRDsOJo(double d, double d2) {
        return Double.compare(d, d2);
    }

    public static double constructor-impl(double d) {
        return d;
    }

    public static final double div-LRDsOJo(double d, double d2) {
        return d / d2;
    }

    public static final double div-impl(double d, double d2) {
        return Duration.constructor-impl(d / d2);
    }

    public static final double div-impl(double d, int n) {
        return Duration.constructor-impl(d / (double)n);
    }

    public static boolean equals-impl(double d, Object object) {
        if (!(object instanceof Duration)) return false;
        if (Double.compare(d, ((Duration)object).unbox-impl()) != 0) return false;
        return true;
    }

    public static final boolean equals-impl0(double d, double d2) {
        if (Double.compare(d, d2) != 0) return false;
        return true;
    }

    public static final double getAbsoluteValue-impl(double d) {
        double d2 = d;
        if (!Duration.isNegative-impl(d)) return d2;
        return Duration.unaryMinus-impl(d);
    }

    public static final int getHoursComponent-impl(double d) {
        return (int)(Duration.getInHours-impl(d) % (double)24);
    }

    public static final double getInDays-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.DAYS);
    }

    public static final double getInHours-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.HOURS);
    }

    public static final double getInMicroseconds-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.MICROSECONDS);
    }

    public static final double getInMilliseconds-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.MILLISECONDS);
    }

    public static final double getInMinutes-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.MINUTES);
    }

    public static final double getInNanoseconds-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.NANOSECONDS);
    }

    public static final double getInSeconds-impl(double d) {
        return Duration.toDouble-impl(d, TimeUnit.SECONDS);
    }

    public static final int getMinutesComponent-impl(double d) {
        return (int)(Duration.getInMinutes-impl(d) % (double)60);
    }

    public static final int getNanosecondsComponent-impl(double d) {
        return (int)(Duration.getInNanoseconds-impl(d) % 1.0E9);
    }

    public static final int getSecondsComponent-impl(double d) {
        return (int)(Duration.getInSeconds-impl(d) % (double)60);
    }

    public static int hashCode-impl(double d) {
        long l = Double.doubleToLongBits(d);
        return (int)(l ^ l >>> 32);
    }

    public static /* synthetic */ void hoursComponent$annotations() {
    }

    public static final boolean isFinite-impl(double d) {
        if (Double.isInfinite(d)) return false;
        if (Double.isNaN(d)) return false;
        return true;
    }

    public static final boolean isInfinite-impl(double d) {
        return Double.isInfinite(d);
    }

    public static final boolean isNegative-impl(double d) {
        boolean bl = false;
        if (!(d < (double)false)) return bl;
        return true;
    }

    public static final boolean isPositive-impl(double d) {
        boolean bl = false;
        if (!(d > (double)false)) return bl;
        return true;
    }

    public static final double minus-LRDsOJo(double d, double d2) {
        return Duration.constructor-impl(d - d2);
    }

    public static /* synthetic */ void minutesComponent$annotations() {
    }

    public static /* synthetic */ void nanosecondsComponent$annotations() {
    }

    public static final double plus-LRDsOJo(double d, double d2) {
        return Duration.constructor-impl(d + d2);
    }

    private static final int precision-impl(double d, double d2) {
        int n = 1;
        if (d2 < (double)true) {
            return 3;
        }
        if (d2 < (double)10) {
            return 2;
        }
        if (!(d2 < (double)100)) return 0;
        return n;
    }

    public static /* synthetic */ void secondsComponent$annotations() {
    }

    public static final double times-impl(double d, double d2) {
        return Duration.constructor-impl(d * d2);
    }

    public static final double times-impl(double d, int n) {
        return Duration.constructor-impl(d * (double)n);
    }

    public static final <T> T toComponents-impl(double d, Function2<? super Long, ? super Integer, ? extends T> function2) {
        Intrinsics.checkParameterIsNotNull(function2, "action");
        return function2.invoke((Long)((long)Duration.getInSeconds-impl(d)), (Integer)Duration.getNanosecondsComponent-impl(d));
    }

    public static final <T> T toComponents-impl(double d, Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> function3) {
        Intrinsics.checkParameterIsNotNull(function3, "action");
        return function3.invoke((Integer)((int)Duration.getInMinutes-impl(d)), (Integer)Duration.getSecondsComponent-impl(d), (Integer)Duration.getNanosecondsComponent-impl(d));
    }

    public static final <T> T toComponents-impl(double d, Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> function4) {
        Intrinsics.checkParameterIsNotNull(function4, "action");
        return function4.invoke((Integer)((int)Duration.getInHours-impl(d)), (Integer)Duration.getMinutesComponent-impl(d), (Integer)Duration.getSecondsComponent-impl(d), (Integer)Duration.getNanosecondsComponent-impl(d));
    }

    public static final <T> T toComponents-impl(double d, Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> function5) {
        Intrinsics.checkParameterIsNotNull(function5, "action");
        return function5.invoke((Integer)((int)Duration.getInDays-impl(d)), (Integer)Duration.getHoursComponent-impl(d), (Integer)Duration.getMinutesComponent-impl(d), (Integer)Duration.getSecondsComponent-impl(d), (Integer)Duration.getNanosecondsComponent-impl(d));
    }

    public static final double toDouble-impl(double d, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return DurationUnitKt.convertDurationUnit(d, DurationKt.access$getStorageUnit$p(), timeUnit);
    }

    public static final int toInt-impl(double d, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return (int)Duration.toDouble-impl(d, timeUnit);
    }

    public static final String toIsoString-impl(double d) {
        CharSequence charSequence = new StringBuilder();
        if (Duration.isNegative-impl(d)) {
            ((StringBuilder)charSequence).append('-');
        }
        ((StringBuilder)charSequence).append("PT");
        d = Duration.getAbsoluteValue-impl(d);
        int n = (int)Duration.getInHours-impl(d);
        int n2 = Duration.getMinutesComponent-impl(d);
        int n3 = Duration.getSecondsComponent-impl(d);
        int n4 = Duration.getNanosecondsComponent-impl(d);
        boolean bl = true;
        boolean bl2 = n != 0;
        boolean bl3 = n3 != 0 || n4 != 0;
        boolean bl4 = bl;
        if (n2 == 0) {
            bl4 = bl3 && bl2 ? bl : false;
        }
        if (bl2) {
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append('H');
        }
        if (bl4) {
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append('M');
        }
        if (bl3 || !bl2 && !bl4) {
            ((StringBuilder)charSequence).append(n3);
            if (n4 != 0) {
                ((StringBuilder)charSequence).append('.');
                String string2 = StringsKt.padStart(String.valueOf(n4), 9, '0');
                if (n4 % 1000000 == 0) {
                    ((StringBuilder)charSequence).append(string2, 0, 3);
                    Intrinsics.checkExpressionValueIsNotNull(charSequence, "this.append(value, startIndex, endIndex)");
                } else if (n4 % 1000 == 0) {
                    ((StringBuilder)charSequence).append(string2, 0, 6);
                    Intrinsics.checkExpressionValueIsNotNull(charSequence, "this.append(value, startIndex, endIndex)");
                } else {
                    ((StringBuilder)charSequence).append(string2);
                }
            }
            ((StringBuilder)charSequence).append('S');
        }
        charSequence = ((StringBuilder)charSequence).toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
        return charSequence;
    }

    public static final long toLong-impl(double d, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        return (long)Duration.toDouble-impl(d, timeUnit);
    }

    public static final long toLongMilliseconds-impl(double d) {
        return Duration.toLong-impl(d, TimeUnit.MILLISECONDS);
    }

    public static final long toLongNanoseconds-impl(double d) {
        return Duration.toLong-impl(d, TimeUnit.NANOSECONDS);
    }

    /*
     * Unable to fully structure code
     */
    public static String toString-impl(double var0) {
        block6 : {
            block12 : {
                block11 : {
                    block10 : {
                        block9 : {
                            block8 : {
                                block7 : {
                                    block5 : {
                                        block4 : {
                                            if (Duration.isInfinite-impl(var0)) {
                                                var2_1 = String.valueOf(var0);
                                                return var2_17;
                                            }
                                            if (var0 == 0.0) {
                                                return var2_17;
                                            }
                                            var3_18 = Duration.getInNanoseconds-impl(Duration.getAbsoluteValue-impl(var0));
                                            var5_19 = false;
                                            if (!(var3_18 < 1.0E-6)) break block4;
                                            var2_3 = TimeUnit.SECONDS;
                                            ** GOTO lbl46
                                        }
                                        if (!(var3_18 < (double)true)) break block5;
                                        var2_5 = TimeUnit.NANOSECONDS;
                                        var6_20 = 7;
                                        break block6;
                                    }
                                    if (!(var3_18 < 1000.0)) break block7;
                                    var2_6 = TimeUnit.NANOSECONDS;
                                    ** GOTO lbl43
                                }
                                if (!(var3_18 < 1000000.0)) break block8;
                                var2_8 = TimeUnit.MICROSECONDS;
                                ** GOTO lbl43
                            }
                            if (!(var3_18 < 1.0E9)) break block9;
                            var2_9 = TimeUnit.MILLISECONDS;
                            ** GOTO lbl43
                        }
                        if (!(var3_18 < 1.0E12)) break block10;
                        var2_10 = TimeUnit.SECONDS;
                        ** GOTO lbl43
                    }
                    if (!(var3_18 < 6.0E13)) break block11;
                    var2_11 = TimeUnit.MINUTES;
                    ** GOTO lbl43
                }
                if (!(var3_18 < 3.6E15)) break block12;
                var2_12 = TimeUnit.HOURS;
                ** GOTO lbl43
            }
            if (var3_18 < 8.64E20) {
                var2_13 = TimeUnit.DAYS;
lbl43: // 7 sources:
                var6_20 = 0;
            } else {
                var2_14 = TimeUnit.DAYS;
lbl46: // 2 sources:
                var6_20 = 0;
                var5_19 = true;
            }
        }
        var3_18 = Duration.toDouble-impl(var0, (TimeUnit)var2_15);
        var7_21 = new StringBuilder();
        var8_22 = var5_19 != false ? FormatToDecimalsKt.formatScientific(var3_18) : (var6_20 > 0 ? FormatToDecimalsKt.formatUpToDecimals(var3_18, var6_20) : FormatToDecimalsKt.formatToExactDecimals(var3_18, Duration.precision-impl(var0, Math.abs(var3_18))));
        var7_21.append(var8_22);
        var7_21.append(DurationUnitKt.shortName((TimeUnit)var2_15));
        var2_16 = var7_21.toString();
        return var2_17;
    }

    public static final String toString-impl(double d, TimeUnit object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "unit");
        boolean bl = n >= 0;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("decimals must be not negative, but was ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (Duration.isInfinite-impl(d)) {
            return String.valueOf(d);
        }
        d = Duration.toDouble-impl(d, (TimeUnit)((Object)object));
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = Math.abs(d) < 1.0E14 ? FormatToDecimalsKt.formatToExactDecimals(d, RangesKt.coerceAtMost(n, 12)) : FormatToDecimalsKt.formatScientific(d);
        stringBuilder.append(string2);
        stringBuilder.append(DurationUnitKt.shortName((TimeUnit)((Object)object)));
        return stringBuilder.toString();
    }

    public static /* synthetic */ String toString-impl$default(double d, TimeUnit timeUnit, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return Duration.toString-impl(d, timeUnit, n);
        n = 0;
        return Duration.toString-impl(d, timeUnit, n);
    }

    public static final double unaryMinus-impl(double d) {
        return Duration.constructor-impl(-d);
    }

    public int compareTo-LRDsOJo(double d) {
        return Duration.compareTo-LRDsOJo(this.value, d);
    }

    public boolean equals(Object object) {
        return Duration.equals-impl(this.value, object);
    }

    public int hashCode() {
        return Duration.hashCode-impl(this.value);
    }

    public String toString() {
        return Duration.toString-impl(this.value);
    }

    public final /* synthetic */ double unbox-impl() {
        return this.value;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2={"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final double convert(double d, TimeUnit timeUnit, TimeUnit timeUnit2) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "sourceUnit");
            Intrinsics.checkParameterIsNotNull((Object)timeUnit2, "targetUnit");
            return DurationUnitKt.convertDurationUnit(d, timeUnit, timeUnit2);
        }

        public final double getINFINITE() {
            return INFINITE;
        }

        public final double getZERO() {
            return ZERO;
        }
    }

}

