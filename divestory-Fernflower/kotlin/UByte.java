package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003J\t\u0010$\u001a\u00020\rHÖ\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b*\u0010\u000fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u0012J\u001b\u0010)\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u000fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u0012J\u001b\u00100\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b:\u0010\u000fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u0012J\u001b\u00109\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u000fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0012J\u001b\u0010>\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bD\u0010\u0005J\u0010\u0010E\u001a\u00020FH\u0087\b¢\u0006\u0004\bG\u0010HJ\u0010\u0010I\u001a\u00020JH\u0087\b¢\u0006\u0004\bK\u0010LJ\u0010\u0010M\u001a\u00020\rH\u0087\b¢\u0006\u0004\bN\u0010OJ\u0010\u0010P\u001a\u00020QH\u0087\b¢\u0006\u0004\bR\u0010SJ\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u000f\u0010X\u001a\u00020YH\u0016¢\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b]\u0010\u0005J\u0013\u0010^\u001a\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0004\b_\u0010OJ\u0013\u0010`\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\ba\u0010SJ\u0013\u0010b\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\bc\u0010WJ\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006g"},
   d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "data$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "(B)I", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class UByte implements Comparable<UByte> {
   public static final UByte.Companion Companion = new UByte.Companion((DefaultConstructorMarker)null);
   public static final byte MAX_VALUE = -1;
   public static final byte MIN_VALUE = 0;
   public static final int SIZE_BITS = 8;
   public static final int SIZE_BYTES = 1;
   private final byte data;

   // $FF: synthetic method
   private UByte(byte var1) {
      this.data = (byte)var1;
   }

   private static final byte and_7apg3OU/* $FF was: and-7apg3OU*/(byte var0, byte var1) {
      return constructor-impl((byte)(var0 & var1));
   }

   // $FF: synthetic method
   public static final UByte box_impl/* $FF was: box-impl*/(byte var0) {
      return new UByte(var0);
   }

   private int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(byte var1) {
      return compareTo-7apg3OU(this.data, var1);
   }

   private static int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(byte var0, byte var1) {
      return Intrinsics.compare(var0 & 255, var1 & 255);
   }

   private static final int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(byte var0, long var1) {
      return UnsignedKt.ulongCompare(ULong.constructor-impl((long)var0 & 255L), var1);
   }

   private static final int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(byte var0, int var1) {
      return UnsignedKt.uintCompare(UInt.constructor-impl(var0 & 255), var1);
   }

   private static final int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(byte var0, short var1) {
      return Intrinsics.compare(var0 & 255, var1 & '\uffff');
   }

   public static byte constructor_impl/* $FF was: constructor-impl*/(byte var0) {
      return var0;
   }

   // $FF: synthetic method
   public static void data$annotations() {
   }

   private static final byte dec_impl/* $FF was: dec-impl*/(byte var0) {
      return constructor-impl((byte)(var0 - 1));
   }

   private static final int div_7apg3OU/* $FF was: div-7apg3OU*/(byte var0, byte var1) {
      return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & 255));
   }

   private static final long div_VKZWuLQ/* $FF was: div-VKZWuLQ*/(byte var0, long var1) {
      return UnsignedKt.ulongDivide-eb3DHEI(ULong.constructor-impl((long)var0 & 255L), var1);
   }

   private static final int div_WZ4Q5Ns/* $FF was: div-WZ4Q5Ns*/(byte var0, int var1) {
      return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(var0 & 255), var1);
   }

   private static final int div_xj2QHRw/* $FF was: div-xj2QHRw*/(byte var0, short var1) {
      return UnsignedKt.uintDivide-J1ME1BU(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & '\uffff'));
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(byte var0, Object var1) {
      return var1 instanceof UByte && var0 == ((UByte)var1).unbox-impl();
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(byte var0, byte var1) {
      boolean var2;
      if (var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(byte var0) {
      return var0;
   }

   private static final byte inc_impl/* $FF was: inc-impl*/(byte var0) {
      return constructor-impl((byte)(var0 + 1));
   }

   private static final byte inv_impl/* $FF was: inv-impl*/(byte var0) {
      return constructor-impl((byte)var0);
   }

   private static final int minus_7apg3OU/* $FF was: minus-7apg3OU*/(byte var0, byte var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) - UInt.constructor-impl(var1 & 255));
   }

   private static final long minus_VKZWuLQ/* $FF was: minus-VKZWuLQ*/(byte var0, long var1) {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 & 255L) - var1);
   }

   private static final int minus_WZ4Q5Ns/* $FF was: minus-WZ4Q5Ns*/(byte var0, int var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) - var1);
   }

   private static final int minus_xj2QHRw/* $FF was: minus-xj2QHRw*/(byte var0, short var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) - UInt.constructor-impl(var1 & '\uffff'));
   }

   private static final byte or_7apg3OU/* $FF was: or-7apg3OU*/(byte var0, byte var1) {
      return constructor-impl((byte)(var0 | var1));
   }

   private static final int plus_7apg3OU/* $FF was: plus-7apg3OU*/(byte var0, byte var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) + UInt.constructor-impl(var1 & 255));
   }

   private static final long plus_VKZWuLQ/* $FF was: plus-VKZWuLQ*/(byte var0, long var1) {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 & 255L) + var1);
   }

   private static final int plus_WZ4Q5Ns/* $FF was: plus-WZ4Q5Ns*/(byte var0, int var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) + var1);
   }

   private static final int plus_xj2QHRw/* $FF was: plus-xj2QHRw*/(byte var0, short var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) + UInt.constructor-impl(var1 & '\uffff'));
   }

   private static final UIntRange rangeTo_7apg3OU/* $FF was: rangeTo-7apg3OU*/(byte var0, byte var1) {
      return new UIntRange(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & 255), (DefaultConstructorMarker)null);
   }

   private static final int rem_7apg3OU/* $FF was: rem-7apg3OU*/(byte var0, byte var1) {
      return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & 255));
   }

   private static final long rem_VKZWuLQ/* $FF was: rem-VKZWuLQ*/(byte var0, long var1) {
      return UnsignedKt.ulongRemainder-eb3DHEI(ULong.constructor-impl((long)var0 & 255L), var1);
   }

   private static final int rem_WZ4Q5Ns/* $FF was: rem-WZ4Q5Ns*/(byte var0, int var1) {
      return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(var0 & 255), var1);
   }

   private static final int rem_xj2QHRw/* $FF was: rem-xj2QHRw*/(byte var0, short var1) {
      return UnsignedKt.uintRemainder-J1ME1BU(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & '\uffff'));
   }

   private static final int times_7apg3OU/* $FF was: times-7apg3OU*/(byte var0, byte var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) * UInt.constructor-impl(var1 & 255));
   }

   private static final long times_VKZWuLQ/* $FF was: times-VKZWuLQ*/(byte var0, long var1) {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 & 255L) * var1);
   }

   private static final int times_WZ4Q5Ns/* $FF was: times-WZ4Q5Ns*/(byte var0, int var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) * var1);
   }

   private static final int times_xj2QHRw/* $FF was: times-xj2QHRw*/(byte var0, short var1) {
      return UInt.constructor-impl(UInt.constructor-impl(var0 & 255) * UInt.constructor-impl(var1 & '\uffff'));
   }

   private static final byte toByte_impl/* $FF was: toByte-impl*/(byte var0) {
      return var0;
   }

   private static final double toDouble_impl/* $FF was: toDouble-impl*/(byte var0) {
      return (double)(var0 & 255);
   }

   private static final float toFloat_impl/* $FF was: toFloat-impl*/(byte var0) {
      return (float)(var0 & 255);
   }

   private static final int toInt_impl/* $FF was: toInt-impl*/(byte var0) {
      return var0 & 255;
   }

   private static final long toLong_impl/* $FF was: toLong-impl*/(byte var0) {
      return (long)var0 & 255L;
   }

   private static final short toShort_impl/* $FF was: toShort-impl*/(byte var0) {
      return (short)((short)var0 & 255);
   }

   public static String toString_impl/* $FF was: toString-impl*/(byte var0) {
      return String.valueOf(var0 & 255);
   }

   private static final byte toUByte_impl/* $FF was: toUByte-impl*/(byte var0) {
      return var0;
   }

   private static final int toUInt_impl/* $FF was: toUInt-impl*/(byte var0) {
      return UInt.constructor-impl(var0 & 255);
   }

   private static final long toULong_impl/* $FF was: toULong-impl*/(byte var0) {
      return ULong.constructor-impl((long)var0 & 255L);
   }

   private static final short toUShort_impl/* $FF was: toUShort-impl*/(byte var0) {
      return UShort.constructor-impl((short)((short)var0 & 255));
   }

   private static final byte xor_7apg3OU/* $FF was: xor-7apg3OU*/(byte var0, byte var1) {
      return constructor-impl((byte)(var0 ^ var1));
   }

   public boolean equals(Object var1) {
      return equals-impl(this.data, var1);
   }

   public int hashCode() {
      return hashCode-impl(this.data);
   }

   public String toString() {
      return toString-impl(this.data);
   }

   // $FF: synthetic method
   public final byte unbox_impl/* $FF was: unbox-impl*/() {
      return this.data;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\n"},
      d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
