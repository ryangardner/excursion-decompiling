package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\b\u001a\"\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u001a+\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\bø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\"\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a+\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001a\"\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a+\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\"\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u0005\u001a+\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\b\u001a\"\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u000b\u001a+\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\r\u001a\"\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0010\u001a+\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0012\u001a\"\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0015\u001a+\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b \u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"},
   d2 = {"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-5PvTz6A", "minOf-VKSA0NQ", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/comparisons/UComparisonsKt"
)
class UComparisonsKt___UComparisonsKt {
   public UComparisonsKt___UComparisonsKt() {
   }

   public static final short maxOf_5PvTz6A/* $FF was: maxOf-5PvTz6A*/(short var0, short var1) {
      short var2;
      if (Intrinsics.compare(var0 & '\uffff', '\uffff' & var1) >= 0) {
         var2 = var0;
      } else {
         var2 = var1;
      }

      return var2;
   }

   public static final int maxOf_J1ME1BU/* $FF was: maxOf-J1ME1BU*/(int var0, int var1) {
      if (UnsignedKt.uintCompare(var0, var1) < 0) {
         var0 = var1;
      }

      return var0;
   }

   public static final byte maxOf_Kr8caGY/* $FF was: maxOf-Kr8caGY*/(byte var0, byte var1) {
      byte var2;
      if (Intrinsics.compare(var0 & 255, var1 & 255) >= 0) {
         var2 = var0;
      } else {
         var2 = var1;
      }

      return var2;
   }

   private static final short maxOf_VKSA0NQ/* $FF was: maxOf-VKSA0NQ*/(short var0, short var1, short var2) {
      return UComparisonsKt.maxOf-5PvTz6A(var0, UComparisonsKt.maxOf-5PvTz6A(var1, var2));
   }

   private static final int maxOf_WZ9TVnA/* $FF was: maxOf-WZ9TVnA*/(int var0, int var1, int var2) {
      return UComparisonsKt.maxOf-J1ME1BU(var0, UComparisonsKt.maxOf-J1ME1BU(var1, var2));
   }

   private static final byte maxOf_b33U2AM/* $FF was: maxOf-b33U2AM*/(byte var0, byte var1, byte var2) {
      return UComparisonsKt.maxOf-Kr8caGY(var0, UComparisonsKt.maxOf-Kr8caGY(var1, var2));
   }

   public static final long maxOf_eb3DHEI/* $FF was: maxOf-eb3DHEI*/(long var0, long var2) {
      if (UnsignedKt.ulongCompare(var0, var2) < 0) {
         var0 = var2;
      }

      return var0;
   }

   private static final long maxOf_sambcqE/* $FF was: maxOf-sambcqE*/(long var0, long var2, long var4) {
      return UComparisonsKt.maxOf-eb3DHEI(var0, UComparisonsKt.maxOf-eb3DHEI(var2, var4));
   }

   public static final short minOf_5PvTz6A/* $FF was: minOf-5PvTz6A*/(short var0, short var1) {
      short var2;
      if (Intrinsics.compare(var0 & '\uffff', '\uffff' & var1) <= 0) {
         var2 = var0;
      } else {
         var2 = var1;
      }

      return var2;
   }

   public static final int minOf_J1ME1BU/* $FF was: minOf-J1ME1BU*/(int var0, int var1) {
      if (UnsignedKt.uintCompare(var0, var1) > 0) {
         var0 = var1;
      }

      return var0;
   }

   public static final byte minOf_Kr8caGY/* $FF was: minOf-Kr8caGY*/(byte var0, byte var1) {
      byte var2;
      if (Intrinsics.compare(var0 & 255, var1 & 255) <= 0) {
         var2 = var0;
      } else {
         var2 = var1;
      }

      return var2;
   }

   private static final short minOf_VKSA0NQ/* $FF was: minOf-VKSA0NQ*/(short var0, short var1, short var2) {
      return UComparisonsKt.minOf-5PvTz6A(var0, UComparisonsKt.minOf-5PvTz6A(var1, var2));
   }

   private static final int minOf_WZ9TVnA/* $FF was: minOf-WZ9TVnA*/(int var0, int var1, int var2) {
      return UComparisonsKt.minOf-J1ME1BU(var0, UComparisonsKt.minOf-J1ME1BU(var1, var2));
   }

   private static final byte minOf_b33U2AM/* $FF was: minOf-b33U2AM*/(byte var0, byte var1, byte var2) {
      return UComparisonsKt.minOf-Kr8caGY(var0, UComparisonsKt.minOf-Kr8caGY(var1, var2));
   }

   public static final long minOf_eb3DHEI/* $FF was: minOf-eb3DHEI*/(long var0, long var2) {
      if (UnsignedKt.ulongCompare(var0, var2) > 0) {
         var0 = var2;
      }

      return var0;
   }

   private static final long minOf_sambcqE/* $FF was: minOf-sambcqE*/(long var0, long var2, long var4) {
      return UComparisonsKt.minOf-eb3DHEI(var0, UComparisonsKt.minOf-eb3DHEI(var2, var4));
   }
}
