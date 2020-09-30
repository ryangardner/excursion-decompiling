package com.google.zxing.common;

import java.util.Arrays;

public final class BitMatrix implements Cloneable {
   private final int[] bits;
   private final int height;
   private final int rowSize;
   private final int width;

   public BitMatrix(int var1) {
      this(var1, var1);
   }

   public BitMatrix(int var1, int var2) {
      if (var1 >= 1 && var2 >= 1) {
         this.width = var1;
         this.height = var2;
         var1 = (var1 + 31) / 32;
         this.rowSize = var1;
         this.bits = new int[var1 * var2];
      } else {
         throw new IllegalArgumentException("Both dimensions must be greater than 0");
      }
   }

   private BitMatrix(int var1, int var2, int var3, int[] var4) {
      this.width = var1;
      this.height = var2;
      this.rowSize = var3;
      this.bits = var4;
   }

   public static BitMatrix parse(String var0, String var1, String var2) {
      if (var0 == null) {
         throw new IllegalArgumentException();
      } else {
         boolean[] var3 = new boolean[var0.length()];
         byte var4 = 0;
         int var5 = 0;
         int var6 = 0;
         int var7 = 0;
         int var8 = -1;
         int var9 = 0;

         while(true) {
            int var10;
            while(var5 < var0.length()) {
               if (var0.charAt(var5) != '\n' && var0.charAt(var5) != '\r') {
                  if (var0.substring(var5, var1.length() + var5).equals(var1)) {
                     var5 += var1.length();
                     var3[var6] = true;
                  } else {
                     if (!var0.substring(var5, var2.length() + var5).equals(var2)) {
                        StringBuilder var14 = new StringBuilder();
                        var14.append("illegal character encountered: ");
                        var14.append(var0.substring(var5));
                        throw new IllegalArgumentException(var14.toString());
                     }

                     var5 += var2.length();
                     var3[var6] = false;
                  }

                  ++var6;
               } else {
                  var10 = var7;
                  int var11 = var8;
                  int var12 = var9;
                  if (var6 > var7) {
                     if (var8 == -1) {
                        var8 = var6 - var7;
                     } else if (var6 - var7 != var8) {
                        throw new IllegalArgumentException("row lengths do not match");
                     }

                     var12 = var9 + 1;
                     var10 = var6;
                     var11 = var8;
                  }

                  ++var5;
                  var7 = var10;
                  var8 = var11;
                  var9 = var12;
               }
            }

            var5 = var8;
            var10 = var9;
            if (var6 > var7) {
               if (var8 == -1) {
                  var8 = var6 - var7;
               } else if (var6 - var7 != var8) {
                  throw new IllegalArgumentException("row lengths do not match");
               }

               var10 = var9 + 1;
               var5 = var8;
            }

            BitMatrix var13 = new BitMatrix(var5, var10);

            for(var8 = var4; var8 < var6; ++var8) {
               if (var3[var8]) {
                  var13.set(var8 % var5, var8 / var5);
               }
            }

            return var13;
         }
      }
   }

   public void clear() {
      int var1 = this.bits.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         this.bits[var2] = 0;
      }

   }

   public BitMatrix clone() {
      return new BitMatrix(this.width, this.height, this.rowSize, (int[])this.bits.clone());
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof BitMatrix;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         BitMatrix var4 = (BitMatrix)var1;
         var2 = var3;
         if (this.width == var4.width) {
            var2 = var3;
            if (this.height == var4.height) {
               var2 = var3;
               if (this.rowSize == var4.rowSize) {
                  var2 = var3;
                  if (Arrays.equals(this.bits, var4.bits)) {
                     var2 = true;
                  }
               }
            }
         }

         return var2;
      }
   }

   public void flip(int var1, int var2) {
      var2 = var2 * this.rowSize + var1 / 32;
      int[] var3 = this.bits;
      var3[var2] ^= 1 << (var1 & 31);
   }

   public boolean get(int var1, int var2) {
      int var3 = this.rowSize;
      int var4 = var1 / 32;
      var2 = this.bits[var2 * var3 + var4];
      boolean var5 = true;
      if ((var2 >>> (var1 & 31) & 1) == 0) {
         var5 = false;
      }

      return var5;
   }

   public int[] getBottomRightOnBit() {
      int var1;
      for(var1 = this.bits.length - 1; var1 >= 0 && this.bits[var1] == 0; --var1) {
      }

      if (var1 < 0) {
         return null;
      } else {
         int var2 = this.rowSize;
         int var3 = var1 / var2;
         int var4 = this.bits[var1];

         int var5;
         for(var5 = 31; var4 >>> var5 == 0; --var5) {
         }

         return new int[]{var1 % var2 * 32 + var5, var3};
      }
   }

   public int[] getEnclosingRectangle() {
      int var1 = this.width;
      int var2 = this.height;
      int var3 = -1;
      int var4 = -1;

      int var5;
      int var7;
      for(var5 = 0; var5 < this.height; ++var5) {
         int var6 = 0;

         while(true) {
            var7 = this.rowSize;
            if (var6 >= var7) {
               break;
            }

            int var8 = this.bits[var7 * var5 + var6];
            int var9 = var1;
            int var10 = var2;
            int var11 = var3;
            int var12 = var4;
            if (var8 != 0) {
               var7 = var2;
               if (var5 < var2) {
                  var7 = var5;
               }

               var2 = var4;
               if (var5 > var4) {
                  var2 = var5;
               }

               int var13 = var6 * 32;
               var4 = var1;
               if (var13 < var1) {
                  for(var4 = 0; var8 << 31 - var4 == 0; ++var4) {
                  }

                  var12 = var4 + var13;
                  var4 = var1;
                  if (var12 < var1) {
                     var4 = var12;
                  }
               }

               var9 = var4;
               var10 = var7;
               var11 = var3;
               var12 = var2;
               if (var13 + 31 > var3) {
                  for(var1 = 31; var8 >>> var1 == 0; --var1) {
                  }

                  var1 += var13;
                  var9 = var4;
                  var10 = var7;
                  var11 = var3;
                  var12 = var2;
                  if (var1 > var3) {
                     var11 = var1;
                     var12 = var2;
                     var10 = var7;
                     var9 = var4;
                  }
               }
            }

            ++var6;
            var1 = var9;
            var2 = var10;
            var3 = var11;
            var4 = var12;
         }
      }

      var5 = var3 - var1;
      var7 = var4 - var2;
      if (var5 >= 0 && var7 >= 0) {
         return new int[]{var1, var2, var5, var7};
      } else {
         return null;
      }
   }

   public int getHeight() {
      return this.height;
   }

   public BitArray getRow(int var1, BitArray var2) {
      if (var2 != null && var2.getSize() >= this.width) {
         var2.clear();
      } else {
         var2 = new BitArray(this.width);
      }

      int var3 = this.rowSize;

      for(int var4 = 0; var4 < this.rowSize; ++var4) {
         var2.setBulk(var4 * 32, this.bits[var1 * var3 + var4]);
      }

      return var2;
   }

   public int getRowSize() {
      return this.rowSize;
   }

   public int[] getTopLeftOnBit() {
      int var1 = 0;

      while(true) {
         int[] var2 = this.bits;
         if (var1 >= var2.length || var2[var1] != 0) {
            var2 = this.bits;
            if (var1 == var2.length) {
               return null;
            } else {
               int var3 = this.rowSize;
               int var4 = var1 / var3;
               int var5 = var2[var1];

               int var6;
               for(var6 = 0; var5 << 31 - var6 == 0; ++var6) {
               }

               return new int[]{var1 % var3 * 32 + var6, var4};
            }
         }

         ++var1;
      }
   }

   public int getWidth() {
      return this.width;
   }

   public int hashCode() {
      int var1 = this.width;
      return (((var1 * 31 + var1) * 31 + this.height) * 31 + this.rowSize) * 31 + Arrays.hashCode(this.bits);
   }

   public void rotate180() {
      int var1 = this.getWidth();
      int var2 = this.getHeight();
      BitArray var3 = new BitArray(var1);
      BitArray var4 = new BitArray(var1);

      for(var1 = 0; var1 < (var2 + 1) / 2; ++var1) {
         var3 = this.getRow(var1, var3);
         int var5 = var2 - 1 - var1;
         var4 = this.getRow(var5, var4);
         var3.reverse();
         var4.reverse();
         this.setRow(var1, var4);
         this.setRow(var5, var3);
      }

   }

   public void set(int var1, int var2) {
      var2 = var2 * this.rowSize + var1 / 32;
      int[] var3 = this.bits;
      var3[var2] |= 1 << (var1 & 31);
   }

   public void setRegion(int var1, int var2, int var3, int var4) {
      if (var2 >= 0 && var1 >= 0) {
         if (var4 >= 1 && var3 >= 1) {
            int var5 = var3 + var1;
            var4 += var2;
            if (var4 <= this.height && var5 <= this.width) {
               while(var2 < var4) {
                  int var6 = this.rowSize;

                  for(var3 = var1; var3 < var5; ++var3) {
                     int[] var7 = this.bits;
                     int var8 = var3 / 32 + var6 * var2;
                     var7[var8] |= 1 << (var3 & 31);
                  }

                  ++var2;
               }

            } else {
               throw new IllegalArgumentException("The region must fit inside the matrix");
            }
         } else {
            throw new IllegalArgumentException("Height and width must be at least 1");
         }
      } else {
         throw new IllegalArgumentException("Left and top must be nonnegative");
      }
   }

   public void setRow(int var1, BitArray var2) {
      int[] var3 = var2.getBitArray();
      int[] var5 = this.bits;
      int var4 = this.rowSize;
      System.arraycopy(var3, 0, var5, var1 * var4, var4);
   }

   public String toString() {
      return this.toString("X ", "  ");
   }

   public String toString(String var1, String var2) {
      return this.toString(var1, var2, "\n");
   }

   @Deprecated
   public String toString(String var1, String var2, String var3) {
      StringBuilder var4 = new StringBuilder(this.height * (this.width + 1));

      for(int var5 = 0; var5 < this.height; ++var5) {
         for(int var6 = 0; var6 < this.width; ++var6) {
            String var7;
            if (this.get(var6, var5)) {
               var7 = var1;
            } else {
               var7 = var2;
            }

            var4.append(var7);
         }

         var4.append(var3);
      }

      return var4.toString();
   }

   public void unset(int var1, int var2) {
      var2 = var2 * this.rowSize + var1 / 32;
      int[] var3 = this.bits;
      var3[var2] &= 1 << (var1 & 31);
   }

   public void xor(BitMatrix var1) {
      if (this.width == var1.getWidth() && this.height == var1.getHeight() && this.rowSize == var1.getRowSize()) {
         BitArray var2 = new BitArray(this.width / 32 + 1);

         for(int var3 = 0; var3 < this.height; ++var3) {
            int var4 = this.rowSize;
            int[] var5 = var1.getRow(var3, var2).getBitArray();

            for(int var6 = 0; var6 < this.rowSize; ++var6) {
               int[] var7 = this.bits;
               int var8 = var4 * var3 + var6;
               var7[var8] ^= var5[var6];
            }
         }

      } else {
         throw new IllegalArgumentException("input matrix dimensions do not match");
      }
   }
}
