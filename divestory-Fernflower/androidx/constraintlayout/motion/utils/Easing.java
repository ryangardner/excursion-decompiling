package androidx.constraintlayout.motion.utils;

import android.util.Log;
import java.util.Arrays;

public class Easing {
   private static final String ACCELERATE = "cubic(0.4, 0.05, 0.8, 0.7)";
   private static final String ACCELERATE_NAME = "accelerate";
   private static final String DECELERATE = "cubic(0.0, 0.0, 0.2, 0.95)";
   private static final String DECELERATE_NAME = "decelerate";
   private static final String LINEAR = "cubic(1, 1, 0, 0)";
   private static final String LINEAR_NAME = "linear";
   public static String[] NAMED_EASING = new String[]{"standard", "accelerate", "decelerate", "linear"};
   private static final String STANDARD = "cubic(0.4, 0.0, 0.2, 1)";
   private static final String STANDARD_NAME = "standard";
   static Easing sDefault = new Easing();
   String str = "identity";

   public static Easing getInterpolator(String var0) {
      if (var0 == null) {
         return null;
      } else if (var0.startsWith("cubic")) {
         return new Easing.CubicEasing(var0);
      } else {
         byte var1 = -1;
         switch(var0.hashCode()) {
         case -1354466595:
            if (var0.equals("accelerate")) {
               var1 = 1;
            }
            break;
         case -1263948740:
            if (var0.equals("decelerate")) {
               var1 = 2;
            }
            break;
         case -1102672091:
            if (var0.equals("linear")) {
               var1 = 3;
            }
            break;
         case 1312628413:
            if (var0.equals("standard")) {
               var1 = 0;
            }
         }

         if (var1 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  if (var1 != 3) {
                     StringBuilder var2 = new StringBuilder();
                     var2.append("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or ");
                     var2.append(Arrays.toString(NAMED_EASING));
                     Log.e("ConstraintSet", var2.toString());
                     return sDefault;
                  } else {
                     return new Easing.CubicEasing("cubic(1, 1, 0, 0)");
                  }
               } else {
                  return new Easing.CubicEasing("cubic(0.0, 0.0, 0.2, 0.95)");
               }
            } else {
               return new Easing.CubicEasing("cubic(0.4, 0.05, 0.8, 0.7)");
            }
         } else {
            return new Easing.CubicEasing("cubic(0.4, 0.0, 0.2, 1)");
         }
      }
   }

   public double get(double var1) {
      return var1;
   }

   public double getDiff(double var1) {
      return 1.0D;
   }

   public String toString() {
      return this.str;
   }

   static class CubicEasing extends Easing {
      private static double d_error;
      private static double error;
      double x1;
      double x2;
      double y1;
      double y2;

      public CubicEasing(double var1, double var3, double var5, double var7) {
         this.setup(var1, var3, var5, var7);
      }

      CubicEasing(String var1) {
         this.str = var1;
         int var2 = var1.indexOf(40);
         int var3 = var1.indexOf(44, var2);
         this.x1 = Double.parseDouble(var1.substring(var2 + 1, var3).trim());
         ++var3;
         var2 = var1.indexOf(44, var3);
         this.y1 = Double.parseDouble(var1.substring(var3, var2).trim());
         ++var2;
         var3 = var1.indexOf(44, var2);
         this.x2 = Double.parseDouble(var1.substring(var2, var3).trim());
         var2 = var3 + 1;
         this.y2 = Double.parseDouble(var1.substring(var2, var1.indexOf(41, var2)).trim());
      }

      private double getDiffX(double var1) {
         double var3 = 1.0D - var1;
         double var5 = this.x1;
         double var7 = this.x2;
         return var3 * 3.0D * var3 * var5 + var3 * 6.0D * var1 * (var7 - var5) + 3.0D * var1 * var1 * (1.0D - var7);
      }

      private double getDiffY(double var1) {
         double var3 = 1.0D - var1;
         double var5 = this.y1;
         double var7 = this.y2;
         return var3 * 3.0D * var3 * var5 + var3 * 6.0D * var1 * (var7 - var5) + 3.0D * var1 * var1 * (1.0D - var7);
      }

      private double getX(double var1) {
         double var3 = 1.0D - var1;
         double var5 = 3.0D * var3;
         return this.x1 * var3 * var5 * var1 + this.x2 * var5 * var1 * var1 + var1 * var1 * var1;
      }

      private double getY(double var1) {
         double var3 = 1.0D - var1;
         double var5 = 3.0D * var3;
         return this.y1 * var3 * var5 * var1 + this.y2 * var5 * var1 * var1 + var1 * var1 * var1;
      }

      public double get(double var1) {
         if (var1 <= 0.0D) {
            return 0.0D;
         } else if (var1 >= 1.0D) {
            return 1.0D;
         } else {
            double var3 = 0.5D;
            double var5 = var3;

            double var7;
            while(var3 > error) {
               var7 = this.getX(var5);
               var3 *= 0.5D;
               if (var7 < var1) {
                  var5 += var3;
               } else {
                  var5 -= var3;
               }
            }

            double var9 = var5 - var3;
            var7 = this.getX(var9);
            var5 += var3;
            var3 = this.getX(var5);
            var9 = this.getY(var9);
            return (this.getY(var5) - var9) * (var1 - var7) / (var3 - var7) + var9;
         }
      }

      public double getDiff(double var1) {
         double var3 = 0.5D;
         double var5 = var3;

         double var7;
         while(var3 > d_error) {
            var7 = this.getX(var5);
            var3 *= 0.5D;
            if (var7 < var1) {
               var5 += var3;
            } else {
               var5 -= var3;
            }
         }

         var7 = var5 - var3;
         var1 = this.getX(var7);
         var5 += var3;
         var3 = this.getX(var5);
         var7 = this.getY(var7);
         return (this.getY(var5) - var7) / (var3 - var1);
      }

      void setup(double var1, double var3, double var5, double var7) {
         this.x1 = var1;
         this.y1 = var3;
         this.x2 = var5;
         this.y2 = var7;
      }
   }
}
