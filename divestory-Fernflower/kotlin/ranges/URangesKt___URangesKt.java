package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\nø\u0001\u0000¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\nø\u0001\u0000¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%H\u0087\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\b*\u00020/H\u0087\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\f\u0010J\u001a\u000208*\u000208H\u0007\u001a\f\u0010J\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010K\u001a\u000208*\u0002082\u0006\u0010K\u001a\u00020LH\u0087\u0004\u001a\u0015\u0010K\u001a\u00020>*\u00020>2\u0006\u0010K\u001a\u00020MH\u0087\u0004\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bO\u0010P\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bQ\u0010R\u001a\u001f\u0010N\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bS\u0010T\u001a\u001f\u0010N\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bU\u0010V\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006W"},
   d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/ranges/URangesKt"
)
class URangesKt___URangesKt {
   public URangesKt___URangesKt() {
   }

   public static final short coerceAtLeast_5PvTz6A/* $FF was: coerceAtLeast-5PvTz6A*/(short var0, short var1) {
      short var2 = var0;
      if (Intrinsics.compare(var0 & '\uffff', '\uffff' & var1) < 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final int coerceAtLeast_J1ME1BU/* $FF was: coerceAtLeast-J1ME1BU*/(int var0, int var1) {
      int var2 = var0;
      if (UnsignedKt.uintCompare(var0, var1) < 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final byte coerceAtLeast_Kr8caGY/* $FF was: coerceAtLeast-Kr8caGY*/(byte var0, byte var1) {
      byte var2 = var0;
      if (Intrinsics.compare(var0 & 255, var1 & 255) < 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final long coerceAtLeast_eb3DHEI/* $FF was: coerceAtLeast-eb3DHEI*/(long var0, long var2) {
      long var4 = var0;
      if (UnsignedKt.ulongCompare(var0, var2) < 0) {
         var4 = var2;
      }

      return var4;
   }

   public static final short coerceAtMost_5PvTz6A/* $FF was: coerceAtMost-5PvTz6A*/(short var0, short var1) {
      short var2 = var0;
      if (Intrinsics.compare(var0 & '\uffff', '\uffff' & var1) > 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final int coerceAtMost_J1ME1BU/* $FF was: coerceAtMost-J1ME1BU*/(int var0, int var1) {
      int var2 = var0;
      if (UnsignedKt.uintCompare(var0, var1) > 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final byte coerceAtMost_Kr8caGY/* $FF was: coerceAtMost-Kr8caGY*/(byte var0, byte var1) {
      byte var2 = var0;
      if (Intrinsics.compare(var0 & 255, var1 & 255) > 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final long coerceAtMost_eb3DHEI/* $FF was: coerceAtMost-eb3DHEI*/(long var0, long var2) {
      long var4 = var0;
      if (UnsignedKt.ulongCompare(var0, var2) > 0) {
         var4 = var2;
      }

      return var4;
   }

   public static final long coerceIn_JPwROB0/* $FF was: coerceIn-JPwROB0*/(long var0, ClosedRange<ULong> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "range");
      if (var2 instanceof ClosedFloatingPointRange) {
         return ((ULong)RangesKt.coerceIn(ULong.box-impl(var0), (ClosedFloatingPointRange)var2)).unbox-impl();
      } else if (!var2.isEmpty()) {
         long var3;
         if (UnsignedKt.ulongCompare(var0, ((ULong)var2.getStart()).unbox-impl()) < 0) {
            var3 = ((ULong)var2.getStart()).unbox-impl();
         } else {
            var3 = var0;
            if (UnsignedKt.ulongCompare(var0, ((ULong)var2.getEndInclusive()).unbox-impl()) > 0) {
               var3 = ((ULong)var2.getEndInclusive()).unbox-impl();
            }
         }

         return var3;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Cannot coerce value to an empty range: ");
         var5.append(var2);
         var5.append('.');
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   public static final short coerceIn_VKSA0NQ/* $FF was: coerceIn-VKSA0NQ*/(short var0, short var1, short var2) {
      int var3 = var1 & '\uffff';
      int var4 = var2 & '\uffff';
      if (Intrinsics.compare(var3, var4) <= 0) {
         int var5 = '\uffff' & var0;
         if (Intrinsics.compare(var5, var3) < 0) {
            return var1;
         } else {
            return Intrinsics.compare(var5, var4) > 0 ? var2 : var0;
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Cannot coerce value to an empty range: maximum ");
         var6.append(UShort.toString-impl(var2));
         var6.append(" is less than minimum ");
         var6.append(UShort.toString-impl(var1));
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final int coerceIn_WZ9TVnA/* $FF was: coerceIn-WZ9TVnA*/(int var0, int var1, int var2) {
      if (UnsignedKt.uintCompare(var1, var2) <= 0) {
         if (UnsignedKt.uintCompare(var0, var1) < 0) {
            return var1;
         } else {
            return UnsignedKt.uintCompare(var0, var2) > 0 ? var2 : var0;
         }
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: maximum ");
         var3.append(UInt.toString-impl(var2));
         var3.append(" is less than minimum ");
         var3.append(UInt.toString-impl(var1));
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final byte coerceIn_b33U2AM/* $FF was: coerceIn-b33U2AM*/(byte var0, byte var1, byte var2) {
      int var3 = var1 & 255;
      int var4 = var2 & 255;
      if (Intrinsics.compare(var3, var4) <= 0) {
         int var5 = var0 & 255;
         if (Intrinsics.compare(var5, var3) < 0) {
            return var1;
         } else {
            return Intrinsics.compare(var5, var4) > 0 ? var2 : var0;
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Cannot coerce value to an empty range: maximum ");
         var6.append(UByte.toString-impl(var2));
         var6.append(" is less than minimum ");
         var6.append(UByte.toString-impl(var1));
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final long coerceIn_sambcqE/* $FF was: coerceIn-sambcqE*/(long var0, long var2, long var4) {
      if (UnsignedKt.ulongCompare(var2, var4) <= 0) {
         if (UnsignedKt.ulongCompare(var0, var2) < 0) {
            return var2;
         } else {
            return UnsignedKt.ulongCompare(var0, var4) > 0 ? var4 : var0;
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Cannot coerce value to an empty range: maximum ");
         var6.append(ULong.toString-impl(var4));
         var6.append(" is less than minimum ");
         var6.append(ULong.toString-impl(var2));
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final int coerceIn_wuiCnnA/* $FF was: coerceIn-wuiCnnA*/(int var0, ClosedRange<UInt> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "range");
      if (var1 instanceof ClosedFloatingPointRange) {
         return ((UInt)RangesKt.coerceIn(UInt.box-impl(var0), (ClosedFloatingPointRange)var1)).unbox-impl();
      } else if (!var1.isEmpty()) {
         int var2;
         if (UnsignedKt.uintCompare(var0, ((UInt)var1.getStart()).unbox-impl()) < 0) {
            var2 = ((UInt)var1.getStart()).unbox-impl();
         } else {
            var2 = var0;
            if (UnsignedKt.uintCompare(var0, ((UInt)var1.getEndInclusive()).unbox-impl()) > 0) {
               var2 = ((UInt)var1.getEndInclusive()).unbox-impl();
            }
         }

         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final boolean contains_68kG9v0/* $FF was: contains-68kG9v0*/(UIntRange var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains-WZ4Q5Ns(UInt.constructor-impl(var1 & 255));
   }

   private static final boolean contains_GYNo2lE/* $FF was: contains-GYNo2lE*/(ULongRange var0, ULong var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var2;
      if (var1 != null && var0.contains-VKZWuLQ(var1.unbox-impl())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final boolean contains_Gab390E/* $FF was: contains-Gab390E*/(ULongRange var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 & 4294967295L));
   }

   public static final boolean contains_ULb_yJY/* $FF was: contains-ULb-yJY*/(ULongRange var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 & 255L));
   }

   public static final boolean contains_ZsK3CEQ/* $FF was: contains-ZsK3CEQ*/(UIntRange var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains-WZ4Q5Ns(UInt.constructor-impl(var1 & '\uffff'));
   }

   private static final boolean contains_biwQdVI/* $FF was: contains-biwQdVI*/(UIntRange var0, UInt var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var2;
      if (var1 != null && var0.contains-WZ4Q5Ns(var1.unbox-impl())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final boolean contains_fz5IDCE/* $FF was: contains-fz5IDCE*/(UIntRange var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var3;
      if (ULong.constructor-impl(var1 >>> 32) == 0L && var0.contains-WZ4Q5Ns(UInt.constructor-impl((int)var1))) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean contains_uhHAxoY/* $FF was: contains-uhHAxoY*/(ULongRange var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 & 65535L));
   }

   public static final UIntProgression downTo_5PvTz6A/* $FF was: downTo-5PvTz6A*/(short var0, short var1) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(var0 & '\uffff'), UInt.constructor-impl(var1 & '\uffff'), -1);
   }

   public static final UIntProgression downTo_J1ME1BU/* $FF was: downTo-J1ME1BU*/(int var0, int var1) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0, var1, -1);
   }

   public static final UIntProgression downTo_Kr8caGY/* $FF was: downTo-Kr8caGY*/(byte var0, byte var1) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(var1 & 255), -1);
   }

   public static final ULongProgression downTo_eb3DHEI/* $FF was: downTo-eb3DHEI*/(long var0, long var2) {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0, var2, -1L);
   }

   private static final int random(UIntRange var0) {
      return URangesKt.random(var0, (Random)Random.Default);
   }

   public static final int random(UIntRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      try {
         int var2 = URandomKt.nextUInt(var1, var0);
         return var2;
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   private static final long random(ULongRange var0) {
      return URangesKt.random(var0, (Random)Random.Default);
   }

   public static final long random(ULongRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      try {
         long var2 = URandomKt.nextULong(var1, var0);
         return var2;
      } catch (IllegalArgumentException var4) {
         throw (Throwable)(new NoSuchElementException(var4.getMessage()));
      }
   }

   private static final UInt randomOrNull(UIntRange var0) {
      return URangesKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final UInt randomOrNull(UIntRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      return var0.isEmpty() ? null : UInt.box-impl(URandomKt.nextUInt(var1, var0));
   }

   private static final ULong randomOrNull(ULongRange var0) {
      return URangesKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final ULong randomOrNull(ULongRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      return var0.isEmpty() ? null : ULong.box-impl(URandomKt.nextULong(var1, var0));
   }

   public static final UIntProgression reversed(UIntProgression var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   public static final ULongProgression reversed(ULongProgression var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   public static final UIntProgression step(UIntProgression var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$step");
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      RangesKt.checkStepIsPositive(var2, (Number)var1);
      UIntProgression.Companion var3 = UIntProgression.Companion;
      int var4 = var0.getFirst();
      int var5 = var0.getLast();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var3.fromClosedRange-Nkh28Cs(var4, var5, var1);
   }

   public static final ULongProgression step(ULongProgression var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$step");
      boolean var3;
      if (var1 > 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      RangesKt.checkStepIsPositive(var3, (Number)var1);
      ULongProgression.Companion var4 = ULongProgression.Companion;
      long var5 = var0.getFirst();
      long var7 = var0.getLast();
      if (var0.getStep() <= 0L) {
         var1 = -var1;
      }

      return var4.fromClosedRange-7ftBX0g(var5, var7, var1);
   }

   public static final UIntRange until_5PvTz6A/* $FF was: until-5PvTz6A*/(short var0, short var1) {
      int var2 = var1 & '\uffff';
      return Intrinsics.compare(var2, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.constructor-impl(var0 & '\uffff'), UInt.constructor-impl(UInt.constructor-impl(var2) - 1), (DefaultConstructorMarker)null);
   }

   public static final UIntRange until_J1ME1BU/* $FF was: until-J1ME1BU*/(int var0, int var1) {
      return UnsignedKt.uintCompare(var1, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(var0, UInt.constructor-impl(var1 - 1), (DefaultConstructorMarker)null);
   }

   public static final UIntRange until_Kr8caGY/* $FF was: until-Kr8caGY*/(byte var0, byte var1) {
      int var2 = var1 & 255;
      return Intrinsics.compare(var2, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.constructor-impl(var0 & 255), UInt.constructor-impl(UInt.constructor-impl(var2) - 1), (DefaultConstructorMarker)null);
   }

   public static final ULongRange until_eb3DHEI/* $FF was: until-eb3DHEI*/(long var0, long var2) {
      return UnsignedKt.ulongCompare(var2, 0L) <= 0 ? ULongRange.Companion.getEMPTY() : new ULongRange(var0, ULong.constructor-impl(var2 - ULong.constructor-impl((long)1 & 4294967295L)), (DefaultConstructorMarker)null);
   }
}
