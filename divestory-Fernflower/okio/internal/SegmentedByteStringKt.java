package okio.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio._Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000R\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a\u0017\u0010\u0006\u001a\u00020\u0007*\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0080\b\u001a\r\u0010\u000b\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\r\u0010\f\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0015*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u0019\u001a\u00020\u0012*\u00020\bH\u0080\b\u001a%\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001aZ\u0010\u001e\u001a\u00020\u001b*\u00020\b2K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0080\b\u001aj\u0010\u001e\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00012K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0082\b\u001a\u0014\u0010$\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0000¨\u0006%"},
   d2 = {"binarySearch", "", "", "value", "fromIndex", "toIndex", "commonEquals", "", "Lokio/SegmentedByteString;", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "offset", "", "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class SegmentedByteStringKt {
   // $FF: synthetic method
   public static final void access$forEachSegment(SegmentedByteString var0, int var1, int var2, Function3 var3) {
      forEachSegment(var0, var1, var2, var3);
   }

   public static final int binarySearch(int[] var0, int var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearch");
      --var3;

      while(var2 <= var3) {
         int var4 = var2 + var3 >>> 1;
         int var5 = var0[var4];
         if (var5 < var1) {
            var2 = var4 + 1;
         } else {
            if (var5 <= var1) {
               return var4;
            }

            var3 = var4 - 1;
         }
      }

      return -var2 - 1;
   }

   public static final boolean commonEquals(SegmentedByteString var0, Object var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEquals");
      boolean var2 = true;
      if (var1 != var0) {
         if (var1 instanceof ByteString) {
            ByteString var3 = (ByteString)var1;
            if (var3.size() == var0.size() && var0.rangeEquals(0, (ByteString)var3, 0, var0.size())) {
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public static final int commonGetSize(SegmentedByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonGetSize");
      return var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length - 1];
   }

   public static final int commonHashCode(SegmentedByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonHashCode");
      int var1 = var0.getHashCode$okio();
      if (var1 != 0) {
         return var1;
      } else {
         int var2 = ((Object[])var0.getSegments$okio()).length;
         int var3 = 0;
         int var4 = 0;

         int var5;
         int var7;
         for(var5 = 1; var3 < var2; var4 = var7) {
            int var6 = var0.getDirectory$okio()[var2 + var3];
            var7 = var0.getDirectory$okio()[var3];
            byte[] var8 = var0.getSegments$okio()[var3];

            for(var1 = var6; var1 < var7 - var4 + var6; ++var1) {
               var5 = var5 * 31 + var8[var1];
            }

            ++var3;
         }

         var0.setHashCode$okio(var5);
         return var5;
      }
   }

   public static final byte commonInternalGet(SegmentedByteString var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonInternalGet");
      _Util.checkOffsetAndCount((long)var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length - 1], (long)var1, 1L);
      int var2 = segment(var0, var1);
      int var3;
      if (var2 == 0) {
         var3 = 0;
      } else {
         var3 = var0.getDirectory$okio()[var2 - 1];
      }

      int var4 = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var2];
      return var0.getSegments$okio()[var2][var1 - var3 + var4];
   }

   public static final boolean commonRangeEquals(SegmentedByteString var0, int var1, ByteString var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      if (var1 >= 0 && var1 <= var0.size() - var4) {
         int var5 = var4 + var1;

         for(var4 = segment(var0, var1); var1 < var5; ++var4) {
            int var6;
            if (var4 == 0) {
               var6 = 0;
            } else {
               var6 = var0.getDirectory$okio()[var4 - 1];
            }

            int var7 = var0.getDirectory$okio()[var4];
            int var8 = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var4];
            var7 = Math.min(var5, var7 - var6 + var6) - var1;
            if (!var2.rangeEquals(var3, var0.getSegments$okio()[var4], var8 + (var1 - var6), var7)) {
               return false;
            }

            var3 += var7;
            var1 += var7;
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean commonRangeEquals(SegmentedByteString var0, int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      if (var1 >= 0 && var1 <= var0.size() - var4 && var3 >= 0 && var3 <= var2.length - var4) {
         int var5 = var4 + var1;

         for(var4 = segment(var0, var1); var1 < var5; ++var4) {
            int var6;
            if (var4 == 0) {
               var6 = 0;
            } else {
               var6 = var0.getDirectory$okio()[var4 - 1];
            }

            int var7 = var0.getDirectory$okio()[var4];
            int var8 = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var4];
            var7 = Math.min(var5, var7 - var6 + var6) - var1;
            if (!_Util.arrayRangeEquals(var0.getSegments$okio()[var4], var8 + (var1 - var6), var2, var3, var7)) {
               return false;
            }

            var3 += var7;
            var1 += var7;
         }

         return true;
      } else {
         return false;
      }
   }

   public static final ByteString commonSubstring(SegmentedByteString var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSubstring");
      byte var3 = 0;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var11;
      if (var4) {
         if (var2 <= var0.size()) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            int var5 = var2 - var1;
            if (var5 >= 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var11 = new StringBuilder();
               var11.append("endIndex=");
               var11.append(var2);
               var11.append(" < beginIndex=");
               var11.append(var1);
               throw (Throwable)(new IllegalArgumentException(var11.toString().toString()));
            } else if (var1 == 0 && var2 == var0.size()) {
               return (ByteString)var0;
            } else if (var1 == var2) {
               return ByteString.EMPTY;
            } else {
               int var6 = segment(var0, var1);
               int var7 = segment(var0, var2 - 1);
               byte[][] var8 = (byte[][])ArraysKt.copyOfRange((Object[])var0.getSegments$okio(), var6, var7 + 1);
               Object[] var9 = (Object[])var8;
               int[] var13 = new int[var9.length * 2];
               int var12;
               if (var6 <= var7) {
                  var2 = var6;
                  var12 = 0;

                  while(true) {
                     var13[var12] = Math.min(var0.getDirectory$okio()[var2] - var1, var5);
                     var13[var12 + var9.length] = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var2];
                     if (var2 == var7) {
                        break;
                     }

                     ++var2;
                     ++var12;
                  }
               }

               if (var6 == 0) {
                  var2 = var3;
               } else {
                  var2 = var0.getDirectory$okio()[var6 - 1];
               }

               var12 = var9.length;
               var13[var12] += var1 - var2;
               return (ByteString)(new SegmentedByteString(var8, var13));
            }
         } else {
            StringBuilder var10 = new StringBuilder();
            var10.append("endIndex=");
            var10.append(var2);
            var10.append(" > length(");
            var10.append(var0.size());
            var10.append(')');
            throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
         }
      } else {
         var11 = new StringBuilder();
         var11.append("beginIndex=");
         var11.append(var1);
         var11.append(" < 0");
         throw (Throwable)(new IllegalArgumentException(var11.toString().toString()));
      }
   }

   public static final byte[] commonToByteArray(SegmentedByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToByteArray");
      byte[] var1 = new byte[var0.size()];
      int var2 = ((Object[])var0.getSegments$okio()).length;
      int var3 = 0;
      int var4 = 0;

      int var7;
      for(int var5 = 0; var3 < var2; var4 = var7) {
         int var6 = var0.getDirectory$okio()[var2 + var3];
         var7 = var0.getDirectory$okio()[var3];
         byte[] var8 = var0.getSegments$okio()[var3];
         var4 = var7 - var4;
         ArraysKt.copyInto(var8, var1, var5, var6, var6 + var4);
         var5 += var4;
         ++var3;
      }

      return var1;
   }

   public static final void commonWrite(SegmentedByteString var0, Buffer var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      int var4 = var3 + var2;

      for(var3 = segment(var0, var2); var2 < var4; ++var3) {
         int var5;
         if (var3 == 0) {
            var5 = 0;
         } else {
            var5 = var0.getDirectory$okio()[var3 - 1];
         }

         int var6 = var0.getDirectory$okio()[var3];
         int var7 = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var3];
         var6 = Math.min(var4, var6 - var5 + var5) - var2;
         var5 = var7 + (var2 - var5);
         Segment var8 = new Segment(var0.getSegments$okio()[var3], var5, var5 + var6, true, false);
         if (var1.head == null) {
            var8.prev = var8;
            var8.next = var8.prev;
            var1.head = var8.next;
         } else {
            Segment var9 = var1.head;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var9 = var9.prev;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var9.push(var8);
         }

         var2 += var6;
      }

      var1.setSize$okio(var1.size() + (long)var0.size());
   }

   private static final void forEachSegment(SegmentedByteString var0, int var1, int var2, Function3<? super byte[], ? super Integer, ? super Integer, Unit> var3) {
      for(int var4 = segment(var0, var1); var1 < var2; ++var4) {
         int var5;
         if (var4 == 0) {
            var5 = 0;
         } else {
            var5 = var0.getDirectory$okio()[var4 - 1];
         }

         int var6 = var0.getDirectory$okio()[var4];
         int var7 = var0.getDirectory$okio()[((Object[])var0.getSegments$okio()).length + var4];
         var6 = Math.min(var2, var6 - var5 + var5) - var1;
         var3.invoke(var0.getSegments$okio()[var4], var7 + (var1 - var5), var6);
         var1 += var6;
      }

   }

   public static final void forEachSegment(SegmentedByteString var0, Function3<? super byte[], ? super Integer, ? super Integer, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachSegment");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      int var2 = ((Object[])var0.getSegments$okio()).length;
      int var3 = 0;

      int var6;
      for(int var4 = 0; var3 < var2; var4 = var6) {
         int var5 = var0.getDirectory$okio()[var2 + var3];
         var6 = var0.getDirectory$okio()[var3];
         var1.invoke(var0.getSegments$okio()[var3], var5, var6 - var4);
         ++var3;
      }

   }

   public static final int segment(SegmentedByteString var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$segment");
      var1 = binarySearch(var0.getDirectory$okio(), var1 + 1, 0, ((Object[])var0.getSegments$okio()).length);
      if (var1 < 0) {
         var1 = var1;
      }

      return var1;
   }
}
