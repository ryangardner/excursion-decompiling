package okhttp3.internal.http2;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u001e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lokhttp3/internal/http2/Huffman;", "", "()V", "CODES", "", "CODE_BIT_COUNTS", "", "root", "Lokhttp3/internal/http2/Huffman$Node;", "addCode", "", "symbol", "", "code", "codeBitCount", "decode", "source", "Lokio/BufferedSource;", "byteCount", "", "sink", "Lokio/BufferedSink;", "encode", "Lokio/ByteString;", "encodedLength", "bytes", "Node", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Huffman {
   private static final int[] CODES;
   private static final byte[] CODE_BIT_COUNTS;
   public static final Huffman INSTANCE;
   private static final Huffman.Node root;

   static {
      Huffman var0 = new Huffman();
      INSTANCE = var0;
      CODES = new int[]{8184, 8388568, 268435426, 268435427, 268435428, 268435429, 268435430, 268435431, 268435432, 16777194, 1073741820, 268435433, 268435434, 1073741821, 268435435, 268435436, 268435437, 268435438, 268435439, 268435440, 268435441, 268435442, 1073741822, 268435443, 268435444, 268435445, 268435446, 268435447, 268435448, 268435449, 268435450, 268435451, 20, 1016, 1017, 4090, 8185, 21, 248, 2042, 1018, 1019, 249, 2043, 250, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, 251, 32764, 32, 4091, 1020, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 252, 115, 253, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, 121, 122, 123, 32766, 2044, 16381, 8189, 268435452, 1048550, 4194258, 1048551, 1048552, 4194259, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 8388573, 8388574, 16777195, 8388575, 16777196, 16777197, 4194263, 8388576, 16777198, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 8388583, 16777199, 4194266, 2097117, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 4194269, 4194270, 16777200, 2097119, 4194271, 8388587, 8388588, 2097120, 2097121, 4194272, 2097122, 8388589, 4194273, 8388590, 8388591, 1048554, 4194274, 4194275, 4194276, 8388592, 4194277, 4194278, 8388593, 67108832, 67108833, 1048555, 524273, 4194279, 8388594, 4194280, 33554412, 67108834, 67108835, 67108836, 134217694, 134217695, 67108837, 16777201, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 16777202, 2097124, 2097125, 67108840, 67108841, 268435453, 134217699, 134217700, 134217701, 1048556, 16777203, 1048557, 2097126, 4194281, 2097127, 2097128, 8388595, 4194282, 4194283, 33554414, 33554415, 16777204, 16777205, 67108842, 8388596, 67108843, 134217702, 67108844, 67108845, 134217703, 134217704, 134217705, 134217706, 134217707, 268435454, 134217708, 134217709, 134217710, 134217711, 134217712, 67108846};
      CODE_BIT_COUNTS = new byte[]{13, 23, 28, 28, 28, 28, 28, 28, 28, 24, 30, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 28, 6, 10, 10, 12, 13, 6, 8, 11, 10, 10, 8, 11, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 15, 6, 12, 10, 13, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, 13, 19, 13, 14, 6, 15, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, 15, 11, 14, 13, 28, 20, 22, 20, 20, 22, 22, 22, 23, 22, 23, 23, 23, 23, 23, 24, 23, 24, 24, 22, 23, 24, 23, 23, 23, 23, 21, 22, 23, 22, 23, 23, 24, 22, 21, 20, 22, 22, 23, 23, 21, 23, 22, 22, 24, 21, 22, 23, 23, 21, 21, 22, 21, 23, 22, 23, 23, 20, 22, 22, 22, 23, 22, 22, 23, 26, 26, 20, 19, 22, 23, 22, 25, 26, 26, 26, 27, 27, 26, 24, 25, 19, 21, 26, 27, 27, 26, 27, 24, 21, 21, 26, 26, 28, 27, 27, 27, 20, 24, 20, 21, 22, 21, 21, 23, 22, 22, 25, 25, 24, 24, 26, 23, 26, 27, 26, 26, 27, 27, 27, 27, 27, 28, 27, 27, 27, 27, 27, 26};
      root = new Huffman.Node();
      int var1 = CODE_BIT_COUNTS.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         var0.addCode(var2, CODES[var2], CODE_BIT_COUNTS[var2]);
      }

   }

   private Huffman() {
   }

   private final void addCode(int var1, int var2, int var3) {
      Huffman.Node var4 = new Huffman.Node(var1, var3);
      Huffman.Node var5 = root;

      while(var3 > 8) {
         var3 -= 8;
         var1 = var2 >>> var3 & 255;
         Huffman.Node[] var6 = var5.getChildren();
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         Huffman.Node var7 = var6[var1];
         var5 = var7;
         if (var7 == null) {
            var5 = new Huffman.Node();
            var6[var1] = var5;
         }
      }

      var1 = 8 - var3;
      var2 = var2 << var1 & 255;
      Huffman.Node[] var8 = var5.getChildren();
      if (var8 == null) {
         Intrinsics.throwNpe();
      }

      ArraysKt.fill(var8, var4, var2, (1 << var1) + var2);
   }

   public final void decode(BufferedSource var1, long var2, BufferedSink var4) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var4, "sink");
      Huffman.Node var5 = root;
      int var6 = 0;
      long var7 = 0L;
      int var9 = 0;

      while(true) {
         Huffman.Node var10 = var5;
         int var11 = var9;
         if (var7 >= var2) {
            while(var11 > 0) {
               Huffman.Node[] var12 = var10.getChildren();
               if (var12 == null) {
                  Intrinsics.throwNpe();
               }

               Huffman.Node var13 = var12[var6 << 8 - var11 & 255];
               if (var13 == null) {
                  Intrinsics.throwNpe();
               }

               if (var13.getChildren() != null || var13.getTerminalBitCount() > var11) {
                  break;
               }

               var4.writeByte(var13.getSymbol());
               var11 -= var13.getTerminalBitCount();
               var10 = root;
            }

            return;
         }

         var6 = var6 << 8 | Util.and((byte)var1.readByte(), 255);
         var9 += 8;

         while(var9 >= 8) {
            var11 = var9 - 8;
            Huffman.Node[] var14 = var5.getChildren();
            if (var14 == null) {
               Intrinsics.throwNpe();
            }

            var5 = var14[var6 >>> var11 & 255];
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            if (var5.getChildren() == null) {
               var4.writeByte(var5.getSymbol());
               var9 -= var5.getTerminalBitCount();
               var5 = root;
            } else {
               var9 = var11;
            }
         }

         ++var7;
      }
   }

   public final void encode(ByteString var1, BufferedSink var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "sink");
      int var3 = var1.size();
      int var4 = 0;
      long var5 = 0L;

      int var7;
      for(var7 = 0; var4 < var3; ++var4) {
         int var8 = Util.and((byte)var1.getByte(var4), 255);
         int var9 = CODES[var8];
         byte var10 = CODE_BIT_COUNTS[var8];
         var5 = var5 << var10 | (long)var9;
         var7 += var10;

         while(var7 >= 8) {
            var7 -= 8;
            var2.writeByte((int)(var5 >> var7));
         }
      }

      if (var7 > 0) {
         var2.writeByte((int)(var5 << 8 - var7 | 255L >>> var7));
      }

   }

   public final int encodedLength(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      int var2 = var1.size();
      long var3 = 0L;

      for(int var5 = 0; var5 < var2; ++var5) {
         int var6 = Util.and((byte)var1.getByte(var5), 255);
         var3 += (long)CODE_BIT_COUNTS[var6];
      }

      return (int)(var3 + (long)7 >> 3);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0000\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r¨\u0006\u0010"},
      d2 = {"Lokhttp3/internal/http2/Huffman$Node;", "", "()V", "symbol", "", "bits", "(II)V", "children", "", "getChildren", "()[Lokhttp3/internal/http2/Huffman$Node;", "[Lokhttp3/internal/http2/Huffman$Node;", "getSymbol", "()I", "terminalBitCount", "getTerminalBitCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Node {
      private final Huffman.Node[] children;
      private final int symbol;
      private final int terminalBitCount;

      public Node() {
         this.children = new Huffman.Node[256];
         this.symbol = 0;
         this.terminalBitCount = 0;
      }

      public Node(int var1, int var2) {
         this.children = (Huffman.Node[])null;
         this.symbol = var1;
         var2 &= 7;
         var1 = var2;
         if (var2 == 0) {
            var1 = 8;
         }

         this.terminalBitCount = var1;
      }

      public final Huffman.Node[] getChildren() {
         return this.children;
      }

      public final int getSymbol() {
         return this.symbol;
      }

      public final int getTerminalBitCount() {
         return this.terminalBitCount;
      }
   }
}
