package com.syntak.library;

import java.util.Random;

public class MathOp {
   public static MathOp.Matrix add(MathOp.Matrix var0, MathOp.Matrix var1) throws MathOp.IllegalDimensionException {
      if (var0.getNcols() == var1.getNcols() && var0.getNrows() == var1.getNrows()) {
         MathOp.Matrix var2 = new MathOp.Matrix(var0.getNrows(), var0.getNcols());

         for(int var3 = 0; var3 < var0.getNrows(); ++var3) {
            for(int var4 = 0; var4 < var0.getNcols(); ++var4) {
               var2.setValueAt(var3, var4, var0.getValueAt(var3, var4) + var1.getValueAt(var3, var4));
            }
         }

         return var2;
      } else {
         throw new MathOp.IllegalDimensionException("Two matrices should be the same dimension.");
      }
   }

   private static int changeSign(int var0) {
      return var0 % 2 == 0 ? 1 : -1;
   }

   public static MathOp.Matrix cofactor(MathOp.Matrix var0) throws MathOp.NoSquareException {
      MathOp.Matrix var1 = new MathOp.Matrix(var0.getNrows(), var0.getNcols());

      for(int var2 = 0; var2 < var0.getNrows(); ++var2) {
         for(int var3 = 0; var3 < var0.getNcols(); ++var3) {
            var1.setValueAt(var2, var3, (double)(changeSign(var2) * changeSign(var3)) * determinant(createSubMatrix(var0, var2, var3)));
         }
      }

      return var1;
   }

   public static MathOp.Matrix createSubMatrix(MathOp.Matrix var0, int var1, int var2) {
      MathOp.Matrix var3 = new MathOp.Matrix(var0.getNrows() - 1, var0.getNcols() - 1);
      int var4 = 0;

      for(int var5 = -1; var4 < var0.getNrows(); ++var4) {
         if (var4 != var1) {
            int var6 = var5 + 1;
            int var7 = 0;
            int var8 = -1;

            while(true) {
               var5 = var6;
               if (var7 >= var0.getNcols()) {
                  break;
               }

               if (var7 != var2) {
                  ++var8;
                  var3.setValueAt(var6, var8, var0.getValueAt(var4, var7));
               }

               ++var7;
            }
         }
      }

      return var3;
   }

   public static double determinant(MathOp.Matrix var0) throws MathOp.NoSquareException {
      if (!var0.isSquare()) {
         throw new MathOp.NoSquareException("matrix need to be square.");
      } else if (var0.size() == 1) {
         return var0.getValueAt(0, 0);
      } else if (var0.size() == 2) {
         return var0.getValueAt(0, 0) * var0.getValueAt(1, 1) - var0.getValueAt(0, 1) * var0.getValueAt(1, 0);
      } else {
         double var1 = 0.0D;

         for(int var3 = 0; var3 < var0.getNcols(); ++var3) {
            var1 += (double)changeSign(var3) * var0.getValueAt(0, var3) * determinant(createSubMatrix(var0, 0, var3));
         }

         return var1;
      }
   }

   public static MathOp.Matrix inverse(MathOp.Matrix var0) throws MathOp.NoSquareException {
      return transpose(cofactor(var0)).multiplyByConstant(1.0D / determinant(var0));
   }

   public static boolean isInteger(double var0) {
      boolean var2;
      if ((double)((int)var0) == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isInteger(float var0) {
      boolean var1;
      if ((float)((int)var0) == var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static float lengthFoot2Meter(float var0) {
      return (float)((double)var0 * 0.3048D);
   }

   public static float lengthMeter2Foot(float var0) {
      return (float)((double)var0 / 0.3048D);
   }

   public static int mod(int var0, int var1) {
      int var2 = var0 % var1;
      var0 = var2;
      if (var2 < 0) {
         var0 = var2 + var1;
      }

      return var0;
   }

   public static MathOp.Matrix multiply(MathOp.Matrix var0, MathOp.Matrix var1) {
      MathOp.Matrix var2 = new MathOp.Matrix(var0.getNrows(), var1.getNcols());

      for(int var3 = 0; var3 < var2.getNrows(); ++var3) {
         for(int var4 = 0; var4 < var2.getNcols(); ++var4) {
            double var5 = 0.0D;

            for(int var7 = 0; var7 < var0.getNcols(); ++var7) {
               var5 += var0.getValueAt(var3, var7) * var1.getValueAt(var7, var4);
            }

            var2.setValueAt(var3, var4, var5);
         }
      }

      return var2;
   }

   public static int randomInteger(int var0, int var1) {
      return (new Random()).nextInt(var1 - var0) + var0;
   }

   public static float round_to_digits(float var0, int var1) {
      int var2 = 1;

      int var3;
      for(var3 = 10; var2 < var1; ++var2) {
         var3 *= 10;
      }

      float var4 = (float)var3;
      float var5 = var0 * var4;
      var0 = var5;
      if (var5 - (float)((int)var5) >= 0.5F) {
         var0 = var5 + 1.0F;
      }

      return (float)((int)var0) / var4;
   }

   public static double square(double var0) {
      return var0 * var0;
   }

   public static MathOp.Matrix subtract(MathOp.Matrix var0, MathOp.Matrix var1) throws MathOp.IllegalDimensionException {
      return add(var0, var1.multiplyByConstant(-1.0D));
   }

   public static float temperatureC2F(float var0) {
      return (float)((double)var0 * 1.8D + 32.0D);
   }

   public static float temperatureF2C(float var0) {
      return (float)((double)(var0 - 32.0F) / 1.8D);
   }

   public static MathOp.Matrix transpose(MathOp.Matrix var0) {
      MathOp.Matrix var1 = new MathOp.Matrix(var0.getNcols(), var0.getNrows());

      for(int var2 = 0; var2 < var0.getNrows(); ++var2) {
         for(int var3 = 0; var3 < var0.getNcols(); ++var3) {
            var1.setValueAt(var3, var2, var0.getValueAt(var2, var3));
         }
      }

      return var1;
   }

   public static float weightKg2Pound(float var0) {
      return (float)((double)var0 / 0.454D);
   }

   public static float weightPound2Kg(float var0) {
      return (float)((double)var0 * 0.454D);
   }

   public static class IllegalDimensionException extends Exception {
      public IllegalDimensionException() {
      }

      public IllegalDimensionException(String var1) {
         super(var1);
      }
   }

   public static class Matrix {
      private double[][] data;
      private int ncols;
      private int nrows;

      public Matrix(int var1, int var2) {
         this.nrows = var1;
         this.ncols = var2;
         this.data = new double[var1][var2];
      }

      public Matrix(double[][] var1) {
         this.data = var1;
         this.nrows = var1.length;
         this.ncols = var1[0].length;
      }

      public int getNcols() {
         return this.ncols;
      }

      public int getNrows() {
         return this.nrows;
      }

      public double getValueAt(int var1, int var2) {
         return this.data[var1][var2];
      }

      public double[][] getValues() {
         return this.data;
      }

      public MathOp.Matrix insertColumnWithValue1() {
         MathOp.Matrix var1 = new MathOp.Matrix(this.getNrows(), this.getNcols() + 1);

         for(int var2 = 0; var2 < var1.getNrows(); ++var2) {
            for(int var3 = 0; var3 < var1.getNcols(); ++var3) {
               if (var3 == 0) {
                  var1.setValueAt(var2, var3, 1.0D);
               } else {
                  var1.setValueAt(var2, var3, this.getValueAt(var2, var3 - 1));
               }
            }
         }

         return var1;
      }

      public boolean isSquare() {
         boolean var1;
         if (this.nrows == this.ncols) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public MathOp.Matrix multiplyByConstant(double var1) {
         MathOp.Matrix var3 = new MathOp.Matrix(this.nrows, this.ncols);

         for(int var4 = 0; var4 < this.nrows; ++var4) {
            for(int var5 = 0; var5 < this.ncols; ++var5) {
               var3.setValueAt(var4, var5, this.data[var4][var5] * var1);
            }
         }

         return var3;
      }

      public void setNcols(int var1) {
         this.ncols = var1;
      }

      public void setNrows(int var1) {
         this.nrows = var1;
      }

      public void setValueAt(int var1, int var2, double var3) {
         this.data[var1][var2] = var3;
      }

      public void setValues(double[][] var1) {
         this.data = var1;
      }

      public int size() {
         return this.isSquare() ? this.nrows : -1;
      }
   }

   public static class NoSquareException extends Exception {
      public NoSquareException() {
      }

      public NoSquareException(String var1) {
         super(var1);
      }
   }

   public static class Vector3D {
      double x;
      double y;
      double z;

      public Vector3D() {
      }

      public Vector3D(double var1, double var3, double var5) {
         this.x = var1;
         this.y = var3;
         this.z = var5;
      }
   }
}
