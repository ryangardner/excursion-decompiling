package kotlin;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0005\n\u0002\u0010\n\n\u0002\b\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0003H\u0087\b\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0003H\u0087\b\u001a\u0014\u0010\u0006\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\b\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\r\u0010\t\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\t\u001a\u00020\u0003*\u00020\u0003H\u0087\b\u001a\r\u0010\n\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\n\u001a\u00020\u0003*\u00020\u0003H\u0087\b¨\u0006\u000b"},
   d2 = {"countLeadingZeroBits", "", "", "", "countOneBits", "countTrailingZeroBits", "rotateLeft", "bitCount", "rotateRight", "takeHighestOneBit", "takeLowestOneBit", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/NumbersKt"
)
class NumbersKt__NumbersKt extends NumbersKt__NumbersJVMKt {
   public NumbersKt__NumbersKt() {
   }

   private static final int countLeadingZeroBits(byte var0) {
      return Integer.numberOfLeadingZeros(var0 & 255) - 24;
   }

   private static final int countLeadingZeroBits(short var0) {
      return Integer.numberOfLeadingZeros(var0 & '\uffff') - 16;
   }

   private static final int countOneBits(byte var0) {
      return Integer.bitCount(var0 & 255);
   }

   private static final int countOneBits(short var0) {
      return Integer.bitCount(var0 & '\uffff');
   }

   private static final int countTrailingZeroBits(byte var0) {
      return Integer.numberOfTrailingZeros(var0 | 256);
   }

   private static final int countTrailingZeroBits(short var0) {
      return Integer.numberOfTrailingZeros(var0 | 65536);
   }

   public static final byte rotateLeft(byte var0, int var1) {
      var1 &= 7;
      return (byte)((var0 & 255) >>> 8 - var1 | var0 << var1);
   }

   public static final short rotateLeft(short var0, int var1) {
      var1 &= 15;
      return (short)((var0 & '\uffff') >>> 16 - var1 | var0 << var1);
   }

   public static final byte rotateRight(byte var0, int var1) {
      var1 &= 7;
      return (byte)((var0 & 255) >>> var1 | var0 << 8 - var1);
   }

   public static final short rotateRight(short var0, int var1) {
      var1 &= 15;
      return (short)((var0 & '\uffff') >>> var1 | var0 << 16 - var1);
   }

   private static final byte takeHighestOneBit(byte var0) {
      return (byte)Integer.highestOneBit(var0 & 255);
   }

   private static final short takeHighestOneBit(short var0) {
      return (short)Integer.highestOneBit(var0 & '\uffff');
   }

   private static final byte takeLowestOneBit(byte var0) {
      return (byte)Integer.lowestOneBit(var0);
   }

   private static final short takeLowestOneBit(short var0) {
      return (short)Integer.lowestOneBit(var0);
   }
}
