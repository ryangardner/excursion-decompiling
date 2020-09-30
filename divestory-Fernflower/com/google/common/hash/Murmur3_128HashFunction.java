package com.google.common.hash;

import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class Murmur3_128HashFunction extends AbstractHashFunction implements Serializable {
   static final HashFunction GOOD_FAST_HASH_128;
   static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
   private static final long serialVersionUID = 0L;
   private final int seed;

   static {
      GOOD_FAST_HASH_128 = new Murmur3_128HashFunction(Hashing.GOOD_FAST_HASH_SEED);
   }

   Murmur3_128HashFunction(int var1) {
      this.seed = var1;
   }

   public int bits() {
      return 128;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Murmur3_128HashFunction;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Murmur3_128HashFunction var5 = (Murmur3_128HashFunction)var1;
         var4 = var3;
         if (this.seed == var5.seed) {
            var4 = true;
         }
      }

      return var4;
   }

   public int hashCode() {
      return this.getClass().hashCode() ^ this.seed;
   }

   public Hasher newHasher() {
      return new Murmur3_128HashFunction.Murmur3_128Hasher(this.seed);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Hashing.murmur3_128(");
      var1.append(this.seed);
      var1.append(")");
      return var1.toString();
   }

   private static final class Murmur3_128Hasher extends AbstractStreamingHasher {
      private static final long C1 = -8663945395140668459L;
      private static final long C2 = 5545529020109919103L;
      private static final int CHUNK_SIZE = 16;
      private long h1;
      private long h2;
      private int length;

      Murmur3_128Hasher(int var1) {
         super(16);
         long var2 = (long)var1;
         this.h1 = var2;
         this.h2 = var2;
         this.length = 0;
      }

      private void bmix64(long var1, long var3) {
         long var5 = this.h1;
         var1 = mixK1(var1) ^ var5;
         this.h1 = var1;
         var5 = Long.rotateLeft(var1, 27);
         this.h1 = var5;
         var1 = this.h2;
         var5 += var1;
         this.h1 = var5;
         this.h1 = var5 * 5L + 1390208809L;
         var1 ^= mixK2(var3);
         this.h2 = var1;
         var1 = Long.rotateLeft(var1, 31);
         this.h2 = var1;
         var1 += this.h1;
         this.h2 = var1;
         this.h2 = var1 * 5L + 944331445L;
      }

      private static long fmix64(long var0) {
         var0 = (var0 ^ var0 >>> 33) * -49064778989728563L;
         var0 = (var0 ^ var0 >>> 33) * -4265267296055464877L;
         return var0 ^ var0 >>> 33;
      }

      private static long mixK1(long var0) {
         return Long.rotateLeft(var0 * -8663945395140668459L, 31) * 5545529020109919103L;
      }

      private static long mixK2(long var0) {
         return Long.rotateLeft(var0 * 5545529020109919103L, 33) * -8663945395140668459L;
      }

      public HashCode makeHash() {
         long var1 = this.h1;
         int var3 = this.length;
         long var4 = var1 ^ (long)var3;
         this.h1 = var4;
         var1 = this.h2 ^ (long)var3;
         this.h2 = var1;
         var4 += var1;
         this.h1 = var4;
         this.h2 = var1 + var4;
         this.h1 = fmix64(var4);
         var4 = fmix64(this.h2);
         this.h2 = var4;
         var1 = this.h1 + var4;
         this.h1 = var1;
         this.h2 = var4 + var1;
         return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
      }

      protected void process(ByteBuffer var1) {
         this.bmix64(var1.getLong(), var1.getLong());
         this.length += 16;
      }

      protected void processRemaining(ByteBuffer var1) {
         long var3;
         long var5;
         label100: {
            label114: {
               label115: {
                  label95: {
                     label116: {
                        label117: {
                           label118: {
                              label119: {
                                 label120: {
                                    label121: {
                                       label122: {
                                          label80: {
                                             label79: {
                                                this.length += var1.remaining();
                                                int var2 = var1.remaining();
                                                var3 = 0L;
                                                switch(var2) {
                                                case 1:
                                                   var5 = 0L;
                                                   break label115;
                                                case 2:
                                                   var5 = 0L;
                                                   break label95;
                                                case 3:
                                                   var5 = 0L;
                                                   break label117;
                                                case 4:
                                                   var5 = 0L;
                                                   break label119;
                                                case 5:
                                                   var5 = 0L;
                                                   break label121;
                                                case 6:
                                                   var5 = 0L;
                                                   break label80;
                                                case 7:
                                                   var5 = (long)UnsignedBytes.toInt(var1.get(6)) << 48 ^ 0L;
                                                   break label80;
                                                case 8:
                                                   var5 = 0L;
                                                   break label114;
                                                case 9:
                                                   var5 = 0L;
                                                   break label116;
                                                case 10:
                                                   var5 = 0L;
                                                   break label118;
                                                case 11:
                                                   var5 = 0L;
                                                   break label120;
                                                case 12:
                                                   var5 = 0L;
                                                   break label122;
                                                case 13:
                                                   var5 = 0L;
                                                   break label79;
                                                case 14:
                                                   var5 = 0L;
                                                   break;
                                                case 15:
                                                   var5 = (long)UnsignedBytes.toInt(var1.get(14)) << 48 ^ 0L;
                                                   break;
                                                default:
                                                   throw new AssertionError("Should never get here.");
                                                }

                                                var5 ^= (long)UnsignedBytes.toInt(var1.get(13)) << 40;
                                             }

                                             var5 ^= (long)UnsignedBytes.toInt(var1.get(12)) << 32;
                                             break label122;
                                          }

                                          var5 ^= (long)UnsignedBytes.toInt(var1.get(5)) << 40;
                                          break label121;
                                       }

                                       var5 ^= (long)UnsignedBytes.toInt(var1.get(11)) << 24;
                                       break label120;
                                    }

                                    var5 ^= (long)UnsignedBytes.toInt(var1.get(4)) << 32;
                                    break label119;
                                 }

                                 var5 ^= (long)UnsignedBytes.toInt(var1.get(10)) << 16;
                                 break label118;
                              }

                              var5 ^= (long)UnsignedBytes.toInt(var1.get(3)) << 24;
                              break label117;
                           }

                           var5 ^= (long)UnsignedBytes.toInt(var1.get(9)) << 8;
                           break label116;
                        }

                        var5 ^= (long)UnsignedBytes.toInt(var1.get(2)) << 16;
                        break label95;
                     }

                     var5 ^= (long)UnsignedBytes.toInt(var1.get(8));
                     break label114;
                  }

                  var5 ^= (long)UnsignedBytes.toInt(var1.get(1)) << 8;
               }

               var5 ^= (long)UnsignedBytes.toInt(var1.get(0));
               break label100;
            }

            long var7 = var1.getLong() ^ 0L;
            var3 = var5;
            var5 = var7;
         }

         this.h1 ^= mixK1(var5);
         this.h2 ^= mixK2(var3);
      }
   }
}
