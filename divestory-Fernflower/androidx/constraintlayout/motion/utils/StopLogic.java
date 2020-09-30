package androidx.constraintlayout.motion.utils;

import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

public class StopLogic extends MotionInterpolator {
   private boolean mBackwards = false;
   private float mLastPosition;
   private int mNumberOfStages;
   private float mStage1Duration;
   private float mStage1EndPosition;
   private float mStage1Velocity;
   private float mStage2Duration;
   private float mStage2EndPosition;
   private float mStage2Velocity;
   private float mStage3Duration;
   private float mStage3EndPosition;
   private float mStage3Velocity;
   private float mStartPosition;
   private String mType;

   private float calcY(float var1) {
      float var2 = this.mStage1Duration;
      float var3;
      if (var1 <= var2) {
         var3 = this.mStage1Velocity;
         return var3 * var1 + (this.mStage2Velocity - var3) * var1 * var1 / (var2 * 2.0F);
      } else {
         int var4 = this.mNumberOfStages;
         if (var4 == 1) {
            return this.mStage1EndPosition;
         } else {
            float var5 = var1 - var2;
            var3 = this.mStage2Duration;
            if (var5 < var3) {
               var1 = this.mStage1EndPosition;
               var2 = this.mStage2Velocity;
               return var1 + var2 * var5 + (this.mStage3Velocity - var2) * var5 * var5 / (var3 * 2.0F);
            } else if (var4 == 2) {
               return this.mStage2EndPosition;
            } else {
               var2 = var5 - var3;
               var1 = this.mStage3Duration;
               if (var2 < var1) {
                  var5 = this.mStage2EndPosition;
                  var3 = this.mStage3Velocity;
                  return var5 + var3 * var2 - var3 * var2 * var2 / (var1 * 2.0F);
               } else {
                  return this.mStage3EndPosition;
               }
            }
         }
      }
   }

   private void setup(float var1, float var2, float var3, float var4, float var5) {
      float var6 = var1;
      if (var1 == 0.0F) {
         var6 = 1.0E-4F;
      }

      this.mStage1Velocity = var6;
      var1 = var6 / var3;
      float var7 = var1 * var6 / 2.0F;
      if (var6 < 0.0F) {
         var1 = (float)Math.sqrt((double)((var2 - -var6 / var3 * var6 / 2.0F) * var3));
         if (var1 < var4) {
            this.mType = "backward accelerate, decelerate";
            this.mNumberOfStages = 2;
            this.mStage1Velocity = var6;
            this.mStage2Velocity = var1;
            this.mStage3Velocity = 0.0F;
            var4 = (var1 - var6) / var3;
            this.mStage1Duration = var4;
            this.mStage2Duration = var1 / var3;
            this.mStage1EndPosition = (var6 + var1) * var4 / 2.0F;
            this.mStage2EndPosition = var2;
            this.mStage3EndPosition = var2;
         } else {
            this.mType = "backward accelerate cruse decelerate";
            this.mNumberOfStages = 3;
            this.mStage1Velocity = var6;
            this.mStage2Velocity = var4;
            this.mStage3Velocity = var4;
            var5 = (var4 - var6) / var3;
            this.mStage1Duration = var5;
            var1 = var4 / var3;
            this.mStage3Duration = var1;
            var3 = (var6 + var4) * var5 / 2.0F;
            var1 = var1 * var4 / 2.0F;
            this.mStage2Duration = (var2 - var3 - var1) / var4;
            this.mStage1EndPosition = var3;
            this.mStage2EndPosition = var2 - var1;
            this.mStage3EndPosition = var2;
         }
      } else if (var7 >= var2) {
         this.mType = "hard stop";
         var1 = 2.0F * var2 / var6;
         this.mNumberOfStages = 1;
         this.mStage1Velocity = var6;
         this.mStage2Velocity = 0.0F;
         this.mStage1EndPosition = var2;
         this.mStage1Duration = var1;
      } else {
         float var8 = var2 - var7;
         var7 = var8 / var6;
         if (var7 + var1 < var5) {
            this.mType = "cruse decelerate";
            this.mNumberOfStages = 2;
            this.mStage1Velocity = var6;
            this.mStage2Velocity = var6;
            this.mStage3Velocity = 0.0F;
            this.mStage1EndPosition = var8;
            this.mStage2EndPosition = var2;
            this.mStage1Duration = var7;
            this.mStage2Duration = var1;
         } else {
            var7 = (float)Math.sqrt((double)(var3 * var2 + var6 * var6 / 2.0F));
            var1 = (var7 - var6) / var3;
            this.mStage1Duration = var1;
            var5 = var7 / var3;
            this.mStage2Duration = var5;
            if (var7 < var4) {
               this.mType = "accelerate decelerate";
               this.mNumberOfStages = 2;
               this.mStage1Velocity = var6;
               this.mStage2Velocity = var7;
               this.mStage3Velocity = 0.0F;
               this.mStage1Duration = var1;
               this.mStage2Duration = var5;
               this.mStage1EndPosition = (var6 + var7) * var1 / 2.0F;
               this.mStage2EndPosition = var2;
            } else {
               this.mType = "accelerate cruse decelerate";
               this.mNumberOfStages = 3;
               this.mStage1Velocity = var6;
               this.mStage2Velocity = var4;
               this.mStage3Velocity = var4;
               var1 = (var4 - var6) / var3;
               this.mStage1Duration = var1;
               var3 = var4 / var3;
               this.mStage3Duration = var3;
               var1 = (var6 + var4) * var1 / 2.0F;
               var3 = var3 * var4 / 2.0F;
               this.mStage2Duration = (var2 - var1 - var3) / var4;
               this.mStage1EndPosition = var1;
               this.mStage2EndPosition = var2 - var3;
               this.mStage3EndPosition = var2;
            }
         }
      }
   }

   public void config(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.mStartPosition = var1;
      boolean var7;
      if (var1 > var2) {
         var7 = true;
      } else {
         var7 = false;
      }

      this.mBackwards = var7;
      if (var7) {
         this.setup(-var3, var1 - var2, var5, var6, var4);
      } else {
         this.setup(var3, var2 - var1, var5, var6, var4);
      }

   }

   public void debug(String var1, String var2, float var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" ===== ");
      var4.append(this.mType);
      Log.v(var1, var4.toString());
      StringBuilder var5 = new StringBuilder();
      var5.append(var2);
      String var8;
      if (this.mBackwards) {
         var8 = "backwards";
      } else {
         var8 = "forward ";
      }

      var5.append(var8);
      var5.append(" time = ");
      var5.append(var3);
      var5.append("  stages ");
      var5.append(this.mNumberOfStages);
      Log.v(var1, var5.toString());
      var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" dur ");
      var4.append(this.mStage1Duration);
      var4.append(" vel ");
      var4.append(this.mStage1Velocity);
      var4.append(" pos ");
      var4.append(this.mStage1EndPosition);
      Log.v(var1, var4.toString());
      if (this.mNumberOfStages > 1) {
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append(" dur ");
         var4.append(this.mStage2Duration);
         var4.append(" vel ");
         var4.append(this.mStage2Velocity);
         var4.append(" pos ");
         var4.append(this.mStage2EndPosition);
         Log.v(var1, var4.toString());
      }

      if (this.mNumberOfStages > 2) {
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append(" dur ");
         var4.append(this.mStage3Duration);
         var4.append(" vel ");
         var4.append(this.mStage3Velocity);
         var4.append(" pos ");
         var4.append(this.mStage3EndPosition);
         Log.v(var1, var4.toString());
      }

      float var6 = this.mStage1Duration;
      if (var3 <= var6) {
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append("stage 0");
         Log.v(var1, var4.toString());
      } else {
         int var7 = this.mNumberOfStages;
         if (var7 == 1) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append("end stage 0");
            Log.v(var1, var4.toString());
         } else {
            var6 = var3 - var6;
            var3 = this.mStage2Duration;
            if (var6 < var3) {
               var4 = new StringBuilder();
               var4.append(var2);
               var4.append(" stage 1");
               Log.v(var1, var4.toString());
            } else if (var7 == 2) {
               var4 = new StringBuilder();
               var4.append(var2);
               var4.append("end stage 1");
               Log.v(var1, var4.toString());
            } else if (var6 - var3 < this.mStage3Duration) {
               var4 = new StringBuilder();
               var4.append(var2);
               var4.append(" stage 2");
               Log.v(var1, var4.toString());
            } else {
               var4 = new StringBuilder();
               var4.append(var2);
               var4.append(" end stage 2");
               Log.v(var1, var4.toString());
            }
         }
      }
   }

   public float getInterpolation(float var1) {
      float var2 = this.calcY(var1);
      this.mLastPosition = var1;
      if (this.mBackwards) {
         var1 = this.mStartPosition - var2;
      } else {
         var1 = this.mStartPosition + var2;
      }

      return var1;
   }

   public float getVelocity() {
      float var1;
      if (this.mBackwards) {
         var1 = -this.getVelocity(this.mLastPosition);
      } else {
         var1 = this.getVelocity(this.mLastPosition);
      }

      return var1;
   }

   public float getVelocity(float var1) {
      float var2 = this.mStage1Duration;
      float var3;
      if (var1 <= var2) {
         var3 = this.mStage1Velocity;
         return var3 + (this.mStage2Velocity - var3) * var1 / var2;
      } else {
         int var4 = this.mNumberOfStages;
         if (var4 == 1) {
            return 0.0F;
         } else {
            var2 = var1 - var2;
            var3 = this.mStage2Duration;
            if (var2 < var3) {
               var1 = this.mStage2Velocity;
               return var1 + (this.mStage3Velocity - var1) * var2 / var3;
            } else if (var4 == 2) {
               return this.mStage2EndPosition;
            } else {
               var2 -= var3;
               var1 = this.mStage3Duration;
               if (var2 < var1) {
                  var3 = this.mStage3Velocity;
                  return var3 - var2 * var3 / var1;
               } else {
                  return this.mStage3EndPosition;
               }
            }
         }
      }
   }
}
