/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020\rH\u00d6\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010\u000fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010\u0012J\u001b\u0010)\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u000fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u0012J\u001b\u00100\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b:\u0010\u000fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b;\u0010\u0012J\u001b\u00109\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u000fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b@\u0010\u0012J\u001b\u0010>\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bD\u0010\u0005J\u0010\u0010E\u001a\u00020FH\u0087\b\u00a2\u0006\u0004\bG\u0010HJ\u0010\u0010I\u001a\u00020JH\u0087\b\u00a2\u0006\u0004\bK\u0010LJ\u0010\u0010M\u001a\u00020\rH\u0087\b\u00a2\u0006\u0004\bN\u0010OJ\u0010\u0010P\u001a\u00020QH\u0087\b\u00a2\u0006\u0004\bR\u0010SJ\u0010\u0010T\u001a\u00020UH\u0087\b\u00a2\u0006\u0004\bV\u0010WJ\u000f\u0010X\u001a\u00020YH\u0016\u00a2\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b]\u0010\u0005J\u0013\u0010^\u001a\u00020\u0010H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b_\u0010OJ\u0013\u0010`\u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\ba\u0010SJ\u0013\u0010b\u001a\u00020\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\bc\u0010WJ\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006g"}, d2={"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "data$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "(B)I", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UByte
implements Comparable<UByte> {
    public static final Companion Companion = new Companion(null);
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    private /* synthetic */ UByte(byte by) {
        this.data = by;
    }

    private static final byte and-7apg3OU(byte by, byte by2) {
        return UByte.constructor-impl((byte)(by & by2));
    }

    public static final /* synthetic */ UByte box-impl(byte by) {
        return new UByte(by);
    }

    private int compareTo-7apg3OU(byte by) {
        return UByte.compareTo-7apg3OU(this.data, by);
    }

    private static int compareTo-7apg3OU(byte by, byte by2) {
        return Intrinsics.compare(by & 255, by2 & 255);
    }

    private static final int compareTo-VKZWuLQ(byte by, long l) {
        return UnsignedKt.ulongCompare(ULong.constructor-impl((long)by & 255L), l);
    }

    private static final int compareTo-WZ4Q5Ns(byte by, int n) {
        return UnsignedKt.uintCompare(UInt.constructor-impl(by & 255), n);
    }

    private static final int compareTo-xj2QHRw(byte by, short s) {
        return Intrinsics.compare(by & 255, s & 65535);
    }

    public static byte constructor-impl(byte by) {
        return by;
    }

    public static /* synthetic */ void data$annotations() {
    }

    private static final byte dec-impl(byte by) {
        return UByte.constructor-impl((byte)(by - 1));
    }

    private static final int div-7apg3OU(byte by, byte by2) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(by & 255), UInt.constructor-impl(by2 & 255));
    }

    private static final long div-VKZWuLQ(byte by, long l) {
        return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl((long)by & 255L), l);
    }

    private static final int div-WZ4Q5Ns(byte by, int n) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(by & 255), n);
    }

    private static final int div-xj2QHRw(byte by, short s) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(by & 255), UInt.constructor-impl(s & 65535));
    }

    public static boolean equals-impl(byte by, Object object) {
        if (!(object instanceof UByte)) return false;
        if (by != ((UByte)object).unbox-impl()) return false;
        return true;
    }

    public static final boolean equals-impl0(byte by, byte by2) {
        if (by != by2) return false;
        return true;
    }

    public static int hashCode-impl(byte by) {
        return by;
    }

    private static final byte inc-impl(byte by) {
        return UByte.constructor-impl((byte)(by + 1));
    }

    private static final byte inv-impl(byte by) {
        return UByte.constructor-impl(by);
    }

    private static final int minus-7apg3OU(byte by, byte by2) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) - UInt.constructor-impl(by2 & 255));
    }

    private static final long minus-VKZWuLQ(byte by, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)by & 255L) - l);
    }

    private static final int minus-WZ4Q5Ns(byte by, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) - n);
    }

    private static final int minus-xj2QHRw(byte by, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) - UInt.constructor-impl(s & 65535));
    }

    private static final byte or-7apg3OU(byte by, byte by2) {
        return UByte.constructor-impl((byte)(by | by2));
    }

    private static final int plus-7apg3OU(byte by, byte by2) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) + UInt.constructor-impl(by2 & 255));
    }

    private static final long plus-VKZWuLQ(byte by, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)by & 255L) + l);
    }

    private static final int plus-WZ4Q5Ns(byte by, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) + n);
    }

    private static final int plus-xj2QHRw(byte by, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) + UInt.constructor-impl(s & 65535));
    }

    private static final UIntRange rangeTo-7apg3OU(byte by, byte by2) {
        return new UIntRange(UInt.constructor-impl(by & 255), UInt.constructor-impl(by2 & 255), null);
    }

    private static final int rem-7apg3OU(byte by, byte by2) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(by & 255), UInt.constructor-impl(by2 & 255));
    }

    private static final long rem-VKZWuLQ(byte by, long l) {
        return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl((long)by & 255L), l);
    }

    private static final int rem-WZ4Q5Ns(byte by, int n) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(by & 255), n);
    }

    private static final int rem-xj2QHRw(byte by, short s) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(by & 255), UInt.constructor-impl(s & 65535));
    }

    private static final int times-7apg3OU(byte by, byte by2) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) * UInt.constructor-impl(by2 & 255));
    }

    private static final long times-VKZWuLQ(byte by, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)by & 255L) * l);
    }

    private static final int times-WZ4Q5Ns(byte by, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) * n);
    }

    private static final int times-xj2QHRw(byte by, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(by & 255) * UInt.constructor-impl(s & 65535));
    }

    private static final byte toByte-impl(byte by) {
        return by;
    }

    private static final double toDouble-impl(byte by) {
        return by & 255;
    }

    private static final float toFloat-impl(byte by) {
        return by & 255;
    }

    private static final int toInt-impl(byte by) {
        return by & 255;
    }

    private static final long toLong-impl(byte by) {
        return (long)by & 255L;
    }

    private static final short toShort-impl(byte by) {
        return (short)((short)by & 255);
    }

    public static String toString-impl(byte by) {
        return String.valueOf(by & 255);
    }

    private static final byte toUByte-impl(byte by) {
        return by;
    }

    private static final int toUInt-impl(byte by) {
        return UInt.constructor-impl(by & 255);
    }

    private static final long toULong-impl(byte by) {
        return ULong.constructor-impl((long)by & 255L);
    }

    private static final short toUShort-impl(byte by) {
        return UShort.constructor-impl((short)((short)by & 255));
    }

    private static final byte xor-7apg3OU(byte by, byte by2) {
        return UByte.constructor-impl((byte)(by ^ by2));
    }

    public boolean equals(Object object) {
        return UByte.equals-impl(this.data, object);
    }

    public int hashCode() {
        return UByte.hashCode-impl(this.data);
    }

    public String toString() {
        return UByte.toString-impl(this.data);
    }

    public final /* synthetic */ byte unbox-impl() {
        return this.data;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2={"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

