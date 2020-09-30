package com.google.zxing.common;

import java.util.Arrays;

public final class BitArray implements Cloneable {
   private int[] bits;
   private int size;

   public BitArray() {
      this.size = 0;
      this.bits = new int[1];
   }

   public BitArray(int var1) {
      this.size = var1;
      this.bits = makeArray(var1);
   }

   BitArray(int[] var1, int var2) {
      this.bits = var1;
      this.size = var2;
   }

   private void ensureCapacity(int var1) {
      if (var1 > this.bits.length * 32) {
         int[] var2 = makeArray(var1);
         int[] var3 = this.bits;
         System.arraycopy(var3, 0, var2, 0, var3.length);
         this.bits = var2;
      }

   }

   private static int[] makeArray(int var0) {
      return new int[(var0 + 31) / 32];
   }

   public void appendBit(boolean var1) {
      this.ensureCapacity(this.size + 1);
      if (var1) {
         int[] var2 = this.bits;
         int var3 = this.size;
         int var4 = var3 / 32;
         var2[var4] |= 1 << (var3 & 31);
      }

      ++this.size;
   }

   public void appendBitArray(BitArray var1) {
      int var2 = var1.size;
      this.ensureCapacity(this.size + var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         this.appendBit(var1.get(var3));
      }

   }

   public void appendBits(int var1, int var2) {
      if (var2 >= 0 && var2 <= 32) {
         this.ensureCapacity(this.size + var2);

         while(var2 > 0) {
            boolean var3 = true;
            if ((var1 >> var2 - 1 & 1) != 1) {
               var3 = false;
            }

            this.appendBit(var3);
            --var2;
         }

      } else {
         throw new IllegalArgumentException("Num bits must be between 0 and 32");
      }
   }

   public void clear() {
      int var1 = this.bits.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         this.bits[var2] = 0;
      }

   }

   public BitArray clone() {
      return new BitArray((int[])this.bits.clone(), this.size);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof BitArray;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         BitArray var4 = (BitArray)var1;
         var2 = var3;
         if (this.size == var4.size) {
            var2 = var3;
            if (Arrays.equals(this.bits, var4.bits)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public void flip(int var1) {
      int[] var2 = this.bits;
      int var3 = var1 / 32;
      var2[var3] ^= 1 << (var1 & 31);
   }

   public boolean get(int var1) {
      int var2 = this.bits[var1 / 32];
      boolean var3 = true;
      if ((1 << (var1 & 31) & var2) == 0) {
         var3 = false;
      }

      return var3;
   }

   public int[] getBitArray() {
      return this.bits;
   }

   public int getNextSet(int var1) {
      int var2 = this.size;
      if (var1 >= var2) {
         return var2;
      } else {
         int var3 = var1 / 32;
         var2 = (1 << (var1 & 31)) - 1 & this.bits[var3];

         int[] var4;
         for(var1 = var3; var2 == 0; var2 = var4[var1]) {
            ++var1;
            var4 = this.bits;
            if (var1 == var4.length) {
               return this.size;
            }
         }

         var3 = var1 * 32 + Integer.numberOfTrailingZeros(var2);
         var2 = this.size;
         var1 = var3;
         if (var3 > var2) {
            var1 = var2;
         }

         return var1;
      }
   }

   public int getNextUnset(int var1) {
      int var2 = this.size;
      if (var1 >= var2) {
         return var2;
      } else {
         var2 = var1 / 32;

         int[] var3;
         for(var1 = (1 << (var1 & 31)) - 1 & this.bits[var2]; var1 == 0; var1 = var3[var2]) {
            ++var2;
            var3 = this.bits;
            if (var2 == var3.length) {
               return this.size;
            }
         }

         int var4 = var2 * 32 + Integer.numberOfTrailingZeros(var1);
         var2 = this.size;
         var1 = var4;
         if (var4 > var2) {
            var1 = var2;
         }

         return var1;
      }
   }

   public int getSize() {
      return this.size;
   }

   public int getSizeInBytes() {
      return (this.size + 7) / 8;
   }

   public int hashCode() {
      return this.size * 31 + Arrays.hashCode(this.bits);
   }

   public boolean isRange(int var1, int var2, boolean var3) {
      if (var2 < var1) {
         throw new IllegalArgumentException();
      } else if (var2 == var1) {
         return true;
      } else {
         int var4 = var2 - 1;
         int var5 = var1 / 32;
         int var6 = var4 / 32;

         for(int var7 = var5; var7 <= var6; ++var7) {
            if (var7 > var5) {
               var2 = 0;
            } else {
               var2 = var1 & 31;
            }

            int var8;
            if (var7 < var6) {
               var8 = 31;
            } else {
               var8 = var4 & 31;
            }

            int var9;
            if (var2 == 0 && var8 == 31) {
               var2 = -1;
            } else {
               var9 = 0;
               int var10 = var2;

               while(true) {
                  var2 = var9;
                  if (var10 > var8) {
                     break;
                  }

                  var9 |= 1 << var10;
                  ++var10;
               }
            }

            var8 = this.bits[var7];
            if (var3) {
               var9 = var2;
            } else {
               var9 = 0;
            }

            if ((var8 & var2) != var9) {
               return false;
            }
         }

         return true;
      }
   }

   public void reverse() {
      int[] var1 = new int[this.bits.length];
      int var2 = (this.size - 1) / 32;
      int var3 = var2 + 1;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         long var5 = (long)this.bits[var4];
         var5 = (var5 & 1431655765L) << 1 | var5 >> 1 & 1431655765L;
         var5 = (var5 & 858993459L) << 2 | var5 >> 2 & 858993459L;
         var5 = (var5 & 252645135L) << 4 | var5 >> 4 & 252645135L;
         var5 = (var5 & 16711935L) << 8 | var5 >> 8 & 16711935L;
         var1[var2 - var4] = (int)((var5 & 65535L) << 16 | var5 >> 16 & 65535L);
      }

      var4 = this.size;
      var2 = var3 * 32;
      if (var4 != var2) {
         int var7 = var2 - var4;
         var2 = 0;

         for(var4 = 1; var2 < 31 - var7; ++var2) {
            var4 = var4 << 1 | 1;
         }

         int var8 = var1[0] >> var7 & var4;

         for(var2 = 1; var2 < var3; ++var2) {
            int var9 = var1[var2];
            var1[var2 - 1] = var8 | var9 << 32 - var7;
            var8 = var9 >> var7 & var4;
         }

         var1[var3 - 1] = var8;
      }

      this.bits = var1;
   }

   public void set(int var1) {
      int[] var2 = this.bits;
      int var3 = var1 / 32;
      var2[var3] |= 1 << (var1 & 31);
   }

   public void setBulk(int var1, int var2) {
      this.bits[var1 / 32] = var2;
   }

   public void setRange(int var1, int var2) {
      if (var2 < var1) {
         throw new IllegalArgumentException();
      } else if (var2 != var1) {
         int var3 = var2 - 1;
         int var4 = var1 / 32;
         int var5 = var3 / 32;

         for(int var6 = var4; var6 <= var5; ++var6) {
            byte var7 = 0;
            int var8;
            if (var6 > var4) {
               var8 = 0;
            } else {
               var8 = var1 & 31;
            }

            int var9;
            if (var6 < var5) {
               var9 = 31;
            } else {
               var9 = var3 & 31;
            }

            label38: {
               var2 = var7;
               int var10 = var8;
               if (var8 == 0) {
                  var2 = var7;
                  var10 = var8;
                  if (var9 == 31) {
                     var8 = -1;
                     break label38;
                  }
               }

               while(true) {
                  var8 = var2;
                  if (var10 > var9) {
                     break;
                  }

                  var2 |= 1 << var10;
                  ++var10;
               }
            }

            int[] var11 = this.bits;
            var11[var6] |= var8;
         }

      }
   }

   public void toBytes(int var1, byte[] var2, int var3, int var4) {
      byte var5 = 0;
      int var6 = var1;

      for(var1 = var5; var1 < var4; ++var1) {
         int var7 = 0;

         int var8;
         int var9;
         for(var8 = 0; var7 < 8; var8 = var9) {
            var9 = var8;
            if (this.get(var6)) {
               var9 = var8 | 1 << 7 - var7;
            }

            ++var6;
            ++var7;
         }

         var2[var3 + var1] = (byte)((byte)var8);
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(this.size);

      for(int var2 = 0; var2 < this.size; ++var2) {
         if ((var2 & 7) == 0) {
            var1.append(' ');
         }

         byte var3;
         char var4;
         if (this.get(var2)) {
            var3 = 88;
            var4 = (char)var3;
         } else {
            var3 = 46;
            var4 = (char)var3;
         }

         var1.append(var4);
      }

      return var1.toString();
   }

   public void xor(BitArray var1) {
      if (this.bits.length != var1.bits.length) {
         throw new IllegalArgumentException("Sizes don't match");
      } else {
         int var2 = 0;

         while(true) {
            int[] var3 = this.bits;
            if (var2 >= var3.length) {
               return;
            }

            var3[var2] ^= var1.bits[var2];
            ++var2;
         }
      }
   }
}
