/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020\rH\u00d6\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010\u0010J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010\u0013J\u001b\u0010)\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u0010J\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u0013J\u001b\u00100\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b:\u0010\u0010J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b;\u0010\u0013J\u001b\u00109\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u0010J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b@\u0010\u0013J\u001b\u0010>\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020DH\u0087\b\u00a2\u0006\u0004\bE\u0010FJ\u0010\u0010G\u001a\u00020HH\u0087\b\u00a2\u0006\u0004\bI\u0010JJ\u0010\u0010K\u001a\u00020LH\u0087\b\u00a2\u0006\u0004\bM\u0010NJ\u0010\u0010O\u001a\u00020\rH\u0087\b\u00a2\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b\u00a2\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bW\u0010\u0005J\u000f\u0010X\u001a\u00020YH\u0016\u00a2\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u000eH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b]\u0010FJ\u0013\u0010^\u001a\u00020\u0011H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b_\u0010QJ\u0013\u0010`\u001a\u00020\u0014H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\ba\u0010UJ\u0013\u0010b\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\bc\u0010\u0005J\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006g"}, d2={"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "data$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "(S)I", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UShort
implements Comparable<UShort> {
    public static final Companion Companion = new Companion(null);
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    private /* synthetic */ UShort(short s) {
        this.data = s;
    }

    private static final short and-xj2QHRw(short s, short s2) {
        return UShort.constructor-impl((short)(s & s2));
    }

    public static final /* synthetic */ UShort box-impl(short s) {
        return new UShort(s);
    }

    private static final int compareTo-7apg3OU(short s, byte by) {
        return Intrinsics.compare(s & 65535, by & 255);
    }

    private static final int compareTo-VKZWuLQ(short s, long l) {
        return UnsignedKt.ulongCompare(ULong.constructor-impl((long)s & 65535L), l);
    }

    private static final int compareTo-WZ4Q5Ns(short s, int n) {
        return UnsignedKt.uintCompare(UInt.constructor-impl(s & 65535), n);
    }

    private int compareTo-xj2QHRw(short s) {
        return UShort.compareTo-xj2QHRw(this.data, s);
    }

    private static int compareTo-xj2QHRw(short s, short s2) {
        return Intrinsics.compare(s & 65535, s2 & 65535);
    }

    public static short constructor-impl(short s) {
        return s;
    }

    public static /* synthetic */ void data$annotations() {
    }

    private static final short dec-impl(short s) {
        return UShort.constructor-impl((short)(s - 1));
    }

    private static final int div-7apg3OU(short s, byte by) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(s & 65535), UInt.constructor-impl(by & 255));
    }

    private static final long div-VKZWuLQ(short s, long l) {
        return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl((long)s & 65535L), l);
    }

    private static final int div-WZ4Q5Ns(short s, int n) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(s & 65535), n);
    }

    private static final int div-xj2QHRw(short s, short s2) {
        return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(s & 65535), UInt.constructor-impl(s2 & 65535));
    }

    public static boolean equals-impl(short s, Object object) {
        if (!(object instanceof UShort)) return false;
        if (s != ((UShort)object).unbox-impl()) return false;
        return true;
    }

    public static final boolean equals-impl0(short s, short s2) {
        if (s != s2) return false;
        return true;
    }

    public static int hashCode-impl(short s) {
        return s;
    }

    private static final short inc-impl(short s) {
        return UShort.constructor-impl((short)(s + 1));
    }

    private static final short inv-impl(short s) {
        return UShort.constructor-impl(s);
    }

    private static final int minus-7apg3OU(short s, byte by) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) - UInt.constructor-impl(by & 255));
    }

    private static final long minus-VKZWuLQ(short s, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)s & 65535L) - l);
    }

    private static final int minus-WZ4Q5Ns(short s, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) - n);
    }

    private static final int minus-xj2QHRw(short s, short s2) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) - UInt.constructor-impl(s2 & 65535));
    }

    private static final short or-xj2QHRw(short s, short s2) {
        return UShort.constructor-impl((short)(s | s2));
    }

    private static final int plus-7apg3OU(short s, byte by) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) + UInt.constructor-impl(by & 255));
    }

    private static final long plus-VKZWuLQ(short s, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)s & 65535L) + l);
    }

    private static final int plus-WZ4Q5Ns(short s, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) + n);
    }

    private static final int plus-xj2QHRw(short s, short s2) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) + UInt.constructor-impl(s2 & 65535));
    }

    private static final UIntRange rangeTo-xj2QHRw(short s, short s2) {
        return new UIntRange(UInt.constructor-impl(s & 65535), UInt.constructor-impl(s2 & 65535), null);
    }

    private static final int rem-7apg3OU(short s, byte by) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(s & 65535), UInt.constructor-impl(by & 255));
    }

    private static final long rem-VKZWuLQ(short s, long l) {
        return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl((long)s & 65535L), l);
    }

    private static final int rem-WZ4Q5Ns(short s, int n) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(s & 65535), n);
    }

    private static final int rem-xj2QHRw(short s, short s2) {
        return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(s & 65535), UInt.constructor-impl(s2 & 65535));
    }

    private static final int times-7apg3OU(short s, byte by) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) * UInt.constructor-impl(by & 255));
    }

    private static final long times-VKZWuLQ(short s, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)s & 65535L) * l);
    }

    private static final int times-WZ4Q5Ns(short s, int n) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) * n);
    }

    private static final int times-xj2QHRw(short s, short s2) {
        return UInt.constructor-impl(UInt.constructor-impl(s & 65535) * UInt.constructor-impl(s2 & 65535));
    }

    private static final byte toByte-impl(short s) {
        return (byte)s;
    }

    private static final double toDouble-impl(short s) {
        return s & 65535;
    }

    private static final float toFloat-impl(short s) {
        return s & 65535;
    }

    private static final int toInt-impl(short s) {
        return s & 65535;
    }

    private static final long toLong-impl(short s) {
        return (long)s & 65535L;
    }

    private static final short toShort-impl(short s) {
        return s;
    }

    public static String toString-impl(short s) {
        return String.valueOf(s & 65535);
    }

    private static final byte toUByte-impl(short s) {
        return UByte.constructor-impl((byte)s);
    }

    private static final int toUInt-impl(short s) {
        return UInt.constructor-impl(s & 65535);
    }

    private static final long toULong-impl(short s) {
        return ULong.constructor-impl((long)s & 65535L);
    }

    private static final short toUShort-impl(short s) {
        return s;
    }

    private static final short xor-xj2QHRw(short s, short s2) {
        return UShort.constructor-impl((short)(s ^ s2));
    }

    public boolean equals(Object object) {
        return UShort.equals-impl(this.data, object);
    }

    public int hashCode() {
        return UShort.hashCode-impl(this.data);
    }

    public String toString() {
        return UShort.toString-impl(this.data);
    }

    public final /* synthetic */ short unbox-impl() {
        return this.data;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2={"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

