package com.github.mikephil.charting.animation;

import android.animation.TimeInterpolator;

public class Easing {
   private static final float DOUBLE_PI = 6.2831855F;
   public static final Easing.EasingFunction EaseInBack = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return var1 * var1 * (var1 * 2.70158F - 1.70158F);
      }
   };
   public static final Easing.EasingFunction EaseInBounce = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return 1.0F - Easing.EaseOutBounce.getInterpolation(1.0F - var1);
      }
   };
   public static final Easing.EasingFunction EaseInCirc = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return -((float)Math.sqrt((double)(1.0F - var1 * var1)) - 1.0F);
      }
   };
   public static final Easing.EasingFunction EaseInCubic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return (float)Math.pow((double)var1, 3.0D);
      }
   };
   public static final Easing.EasingFunction EaseInElastic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         if (var1 == 0.0F) {
            return 0.0F;
         } else if (var1 == 1.0F) {
            return 1.0F;
         } else {
            float var2 = (float)Math.asin(1.0D);
            --var1;
            return -((float)Math.pow(2.0D, (double)(10.0F * var1)) * (float)Math.sin((double)((var1 - 0.047746483F * var2) * 6.2831855F / 0.3F)));
         }
      }
   };
   public static final Easing.EasingFunction EaseInExpo = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         float var2 = 0.0F;
         if (var1 == 0.0F) {
            var1 = var2;
         } else {
            var1 = (float)Math.pow(2.0D, (double)((var1 - 1.0F) * 10.0F));
         }

         return var1;
      }
   };
   public static final Easing.EasingFunction EaseInOutBack = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         var1 *= 2.0F;
         if (var1 < 1.0F) {
            return var1 * var1 * (3.5949094F * var1 - 2.5949094F) * 0.5F;
         } else {
            var1 -= 2.0F;
            return (var1 * var1 * (3.5949094F * var1 + 2.5949094F) + 2.0F) * 0.5F;
         }
      }
   };
   public static final Easing.EasingFunction EaseInOutBounce = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return var1 < 0.5F ? Easing.EaseInBounce.getInterpolation(var1 * 2.0F) * 0.5F : Easing.EaseOutBounce.getInterpolation(var1 * 2.0F - 1.0F) * 0.5F + 0.5F;
      }
   };
   public static final Easing.EasingFunction EaseInOutCirc = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         var1 *= 2.0F;
         if (var1 < 1.0F) {
            return ((float)Math.sqrt((double)(1.0F - var1 * var1)) - 1.0F) * -0.5F;
         } else {
            var1 -= 2.0F;
            return ((float)Math.sqrt((double)(1.0F - var1 * var1)) + 1.0F) * 0.5F;
         }
      }
   };
   public static final Easing.EasingFunction EaseInOutCubic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         var1 *= 2.0F;
         if (var1 < 1.0F) {
            var1 = (float)Math.pow((double)var1, 3.0D);
         } else {
            var1 = (float)Math.pow((double)(var1 - 2.0F), 3.0D) + 2.0F;
         }

         return var1 * 0.5F;
      }
   };
   public static final Easing.EasingFunction EaseInOutElastic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         if (var1 == 0.0F) {
            return 0.0F;
         } else {
            float var2 = var1 * 2.0F;
            if (var2 == 2.0F) {
               return 1.0F;
            } else {
               var1 = (float)Math.asin(1.0D) * 0.07161972F;
               if (var2 < 1.0F) {
                  --var2;
                  return (float)Math.pow(2.0D, (double)(10.0F * var2)) * (float)Math.sin((double)((var2 * 1.0F - var1) * 6.2831855F * 2.2222223F)) * -0.5F;
               } else {
                  --var2;
                  return (float)Math.pow(2.0D, (double)(-10.0F * var2)) * 0.5F * (float)Math.sin((double)((var2 * 1.0F - var1) * 6.2831855F * 2.2222223F)) + 1.0F;
               }
            }
         }
      }
   };
   public static final Easing.EasingFunction EaseInOutExpo = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         if (var1 == 0.0F) {
            return 0.0F;
         } else if (var1 == 1.0F) {
            return 1.0F;
         } else {
            var1 *= 2.0F;
            if (var1 < 1.0F) {
               var1 = (float)Math.pow(2.0D, (double)((var1 - 1.0F) * 10.0F));
            } else {
               var1 = -((float)Math.pow(2.0D, (double)((var1 - 1.0F) * -10.0F))) + 2.0F;
            }

            return var1 * 0.5F;
         }
      }
   };
   public static final Easing.EasingFunction EaseInOutQuad = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         var1 *= 2.0F;
         if (var1 < 1.0F) {
            return 0.5F * var1 * var1;
         } else {
            --var1;
            return (var1 * (var1 - 2.0F) - 1.0F) * -0.5F;
         }
      }
   };
   public static final Easing.EasingFunction EaseInOutQuart = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         var1 *= 2.0F;
         return var1 < 1.0F ? (float)Math.pow((double)var1, 4.0D) * 0.5F : ((float)Math.pow((double)(var1 - 2.0F), 4.0D) - 2.0F) * -0.5F;
      }
   };
   public static final Easing.EasingFunction EaseInOutSine = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return ((float)Math.cos((double)var1 * 3.141592653589793D) - 1.0F) * -0.5F;
      }
   };
   public static final Easing.EasingFunction EaseInQuad = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return var1 * var1;
      }
   };
   public static final Easing.EasingFunction EaseInQuart = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return (float)Math.pow((double)var1, 4.0D);
      }
   };
   public static final Easing.EasingFunction EaseInSine = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return -((float)Math.cos((double)var1 * 1.5707963267948966D)) + 1.0F;
      }
   };
   public static final Easing.EasingFunction EaseOutBack = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         --var1;
         return var1 * var1 * (var1 * 2.70158F + 1.70158F) + 1.0F;
      }
   };
   public static final Easing.EasingFunction EaseOutBounce = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         if (var1 < 0.36363637F) {
            return 7.5625F * var1 * var1;
         } else if (var1 < 0.72727275F) {
            var1 -= 0.54545456F;
            return 7.5625F * var1 * var1 + 0.75F;
         } else if (var1 < 0.90909094F) {
            var1 -= 0.8181818F;
            return 7.5625F * var1 * var1 + 0.9375F;
         } else {
            var1 -= 0.95454544F;
            return 7.5625F * var1 * var1 + 0.984375F;
         }
      }
   };
   public static final Easing.EasingFunction EaseOutCirc = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         --var1;
         return (float)Math.sqrt((double)(1.0F - var1 * var1));
      }
   };
   public static final Easing.EasingFunction EaseOutCubic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return (float)Math.pow((double)(var1 - 1.0F), 3.0D) + 1.0F;
      }
   };
   public static final Easing.EasingFunction EaseOutElastic = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         if (var1 == 0.0F) {
            return 0.0F;
         } else if (var1 == 1.0F) {
            return 1.0F;
         } else {
            float var2 = (float)Math.asin(1.0D);
            return (float)Math.pow(2.0D, (double)(-10.0F * var1)) * (float)Math.sin((double)((var1 - 0.047746483F * var2) * 6.2831855F / 0.3F)) + 1.0F;
         }
      }
   };
   public static final Easing.EasingFunction EaseOutExpo = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         float var2 = 1.0F;
         if (var1 == 1.0F) {
            var1 = var2;
         } else {
            var1 = -((float)Math.pow(2.0D, (double)((var1 + 1.0F) * -10.0F)));
         }

         return var1;
      }
   };
   public static final Easing.EasingFunction EaseOutQuad = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return -var1 * (var1 - 2.0F);
      }
   };
   public static final Easing.EasingFunction EaseOutQuart = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return -((float)Math.pow((double)(var1 - 1.0F), 4.0D) - 1.0F);
      }
   };
   public static final Easing.EasingFunction EaseOutSine = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return (float)Math.sin((double)var1 * 1.5707963267948966D);
      }
   };
   public static final Easing.EasingFunction Linear = new Easing.EasingFunction() {
      public float getInterpolation(float var1) {
         return var1;
      }
   };

   public interface EasingFunction extends TimeInterpolator {
      float getInterpolation(float var1);
   }
}
