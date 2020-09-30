package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.collections.BooleanIterator;
import kotlin.collections.ByteIterator;
import kotlin.collections.CharIterator;
import kotlin.collections.DoubleIterator;
import kotlin.collections.FloatIterator;
import kotlin.collections.IntIterator;
import kotlin.collections.LongIterator;
import kotlin.collections.ShortIterator;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0019\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\u0010\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0017\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0005\u001a\u000e\u0010\u0000\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0007\u001a\u000e\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\t\u001a\u000e\u0010\u0000\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u000b\u001a\u000e\u0010\u0000\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\r\u001a\u000e\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000f\u001a\u000e\u0010\u0000\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0011¨\u0006\u0012"},
   d2 = {"iterator", "Lkotlin/collections/BooleanIterator;", "array", "", "Lkotlin/collections/ByteIterator;", "", "Lkotlin/collections/CharIterator;", "", "Lkotlin/collections/DoubleIterator;", "", "Lkotlin/collections/FloatIterator;", "", "Lkotlin/collections/IntIterator;", "", "Lkotlin/collections/LongIterator;", "", "Lkotlin/collections/ShortIterator;", "", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ArrayIteratorsKt {
   public static final BooleanIterator iterator(boolean[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (BooleanIterator)(new ArrayBooleanIterator(var0));
   }

   public static final ByteIterator iterator(byte[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (ByteIterator)(new ArrayByteIterator(var0));
   }

   public static final CharIterator iterator(char[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (CharIterator)(new ArrayCharIterator(var0));
   }

   public static final DoubleIterator iterator(double[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (DoubleIterator)(new ArrayDoubleIterator(var0));
   }

   public static final FloatIterator iterator(float[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (FloatIterator)(new ArrayFloatIterator(var0));
   }

   public static final IntIterator iterator(int[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (IntIterator)(new ArrayIntIterator(var0));
   }

   public static final LongIterator iterator(long[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (LongIterator)(new ArrayLongIterator(var0));
   }

   public static final ShortIterator iterator(short[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "array");
      return (ShortIterator)(new ArrayShortIterator(var0));
   }
}
