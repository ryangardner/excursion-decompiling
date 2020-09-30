package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.internal.ByteStringKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0000\u001a \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\fH\u0000\u001a\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\fH\u0080\b\u001a\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010\u000f\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\u0080\f\u001a\u0015\u0010\u000f\u001a\u00020\f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0080\f\u001a\u0015\u0010\u000f\u001a\u00020\f*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fH\u0080\f\u001a\f\u0010\u0012\u001a\u00020\u0005*\u00020\u0005H\u0000\u001a\f\u0010\u0012\u001a\u00020\f*\u00020\fH\u0000\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0013H\u0000\u001a\u0015\u0010\u0014\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\u0080\f\u001a\u0015\u0010\u0015\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\u0080\f\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0010H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0005H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\fH\u0000¨\u0006\u0018"},
   d2 = {"arrayRangeEquals", "", "a", "", "aOffset", "", "b", "bOffset", "byteCount", "checkOffsetAndCount", "", "size", "", "offset", "minOf", "and", "", "other", "reverseBytes", "", "shl", "shr", "toHexString", "", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _Util {
   public static final int and(byte var0, int var1) {
      return var0 & var1;
   }

   public static final long and(byte var0, long var1) {
      return (long)var0 & var1;
   }

   public static final long and(int var0, long var1) {
      return (long)var0 & var1;
   }

   public static final boolean arrayRangeEquals(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "a");
      Intrinsics.checkParameterIsNotNull(var2, "b");

      for(int var5 = 0; var5 < var4; ++var5) {
         if (var0[var5 + var1] != var2[var5 + var3]) {
            return false;
         }
      }

      return true;
   }

   public static final void checkOffsetAndCount(long var0, long var2, long var4) {
      if ((var2 | var4) < 0L || var2 > var0 || var0 - var2 < var4) {
         StringBuilder var6 = new StringBuilder();
         var6.append("size=");
         var6.append(var0);
         var6.append(" offset=");
         var6.append(var2);
         var6.append(" byteCount=");
         var6.append(var4);
         throw (Throwable)(new ArrayIndexOutOfBoundsException(var6.toString()));
      }
   }

   public static final long minOf(int var0, long var1) {
      return Math.min((long)var0, var1);
   }

   public static final long minOf(long var0, int var2) {
      return Math.min(var0, (long)var2);
   }

   public static final int reverseBytes(int var0) {
      return (var0 & 255) << 24 | (-16777216 & var0) >>> 24 | (16711680 & var0) >>> 8 | ('\uff00' & var0) << 8;
   }

   public static final long reverseBytes(long var0) {
      return (var0 & 255L) << 56 | (-72057594037927936L & var0) >>> 56 | (71776119061217280L & var0) >>> 40 | (280375465082880L & var0) >>> 24 | (1095216660480L & var0) >>> 8 | (4278190080L & var0) << 8 | (16711680L & var0) << 24 | (65280L & var0) << 40;
   }

   public static final short reverseBytes(short var0) {
      int var1 = var0 & '\uffff';
      return (short)((var1 & 255) << 8 | ('\uff00' & var1) >>> 8);
   }

   public static final int shl(byte var0, int var1) {
      return var0 << var1;
   }

   public static final int shr(byte var0, int var1) {
      return var0 >> var1;
   }

   public static final String toHexString(byte var0) {
      return new String(new char[]{ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 4 & 15], ByteStringKt.getHEX_DIGIT_CHARS()[var0 & 15]});
   }

   public static final String toHexString(int var0) {
      if (var0 == 0) {
         return "0";
      } else {
         char[] var1 = new char[8];
         char var2 = ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 28 & 15];
         byte var3 = 0;
         var1[0] = (char)var2;
         var1[1] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 24 & 15];
         var1[2] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 20 & 15];
         var1[3] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 16 & 15];
         var1[4] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 12 & 15];
         var1[5] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 8 & 15];
         var1[6] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 >> 4 & 15];
         var1[7] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var0 & 15];

         for(var0 = var3; var0 < 8 && var1[var0] == '0'; ++var0) {
         }

         return new String(var1, var0, 8 - var0);
      }
   }

   public static final String toHexString(long var0) {
      if (var0 == 0L) {
         return "0";
      } else {
         char[] var2 = new char[16];
         char var3 = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 60 & 15L)];
         int var4 = 0;
         var2[0] = (char)var3;
         var2[1] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 56 & 15L)];
         var2[2] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 52 & 15L)];
         var2[3] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 48 & 15L)];
         var2[4] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 44 & 15L)];
         var2[5] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 40 & 15L)];
         var2[6] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 36 & 15L)];
         var2[7] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 32 & 15L)];
         var2[8] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 28 & 15L)];
         var2[9] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 24 & 15L)];
         var2[10] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 20 & 15L)];
         var2[11] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 16 & 15L)];
         var2[12] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 12 & 15L)];
         var2[13] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 8 & 15L)];
         var2[14] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 >> 4 & 15L)];

         for(var2[15] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[(int)(var0 & 15L)]; var4 < 16 && var2[var4] == '0'; ++var4) {
         }

         return new String(var2, var4, 16 - var4);
      }
   }
}
