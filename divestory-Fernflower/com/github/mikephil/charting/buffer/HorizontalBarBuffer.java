package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer extends BarBuffer {
   public HorizontalBarBuffer(int var1, int var2, boolean var3) {
      super(var1, var2, var3);
   }

   public void feed(IBarDataSet var1) {
      float var2 = (float)var1.getEntryCount();
      float var3 = this.phaseX;
      float var4 = this.mBarWidth / 2.0F;

      for(int var5 = 0; (float)var5 < var2 * var3; ++var5) {
         BarEntry var6 = (BarEntry)var1.getEntryForIndex(var5);
         if (var6 != null) {
            float var7 = var6.getX();
            float var8 = var6.getY();
            float[] var9 = var6.getYVals();
            float var12;
            float var13;
            if (this.mContainsStacks && var9 != null) {
               var8 = -var6.getNegativeSum();
               int var10 = 0;

               float var14;
               for(float var11 = 0.0F; var10 < var9.length; var11 = var14) {
                  var12 = var9[var10];
                  if (var12 >= 0.0F) {
                     var13 = var12 + var11;
                     var12 = var8;
                     var8 = var11;
                     var14 = var13;
                  } else {
                     var13 = Math.abs(var12) + var8;
                     var12 = Math.abs(var12) + var8;
                     var14 = var11;
                  }

                  if (this.mInverted) {
                     if (var8 >= var13) {
                        var11 = var8;
                     } else {
                        var11 = var13;
                     }

                     if (var8 > var13) {
                        var8 = var13;
                     }
                  } else {
                     if (var8 >= var13) {
                        var11 = var8;
                     } else {
                        var11 = var13;
                     }

                     if (var8 > var13) {
                        var8 = var13;
                     }

                     var13 = var8;
                     var8 = var11;
                     var11 = var13;
                  }

                  var13 = this.phaseY;
                  this.addBar(var11 * this.phaseY, var7 + var4, var8 * var13, var7 - var4);
                  ++var10;
                  var8 = var12;
               }
            } else {
               if (this.mInverted) {
                  if (var8 >= 0.0F) {
                     var13 = var8;
                  } else {
                     var13 = 0.0F;
                  }

                  if (var8 > 0.0F) {
                     var8 = 0.0F;
                  }
               } else {
                  if (var8 >= 0.0F) {
                     var13 = var8;
                  } else {
                     var13 = 0.0F;
                  }

                  if (var8 > 0.0F) {
                     var8 = 0.0F;
                  }

                  var12 = var8;
                  var8 = var13;
                  var13 = var12;
               }

               if (var8 > 0.0F) {
                  var8 *= this.phaseY;
               } else {
                  var13 *= this.phaseY;
               }

               this.addBar(var13, var7 + var4, var8, var7 - var4);
            }
         }
      }

      this.reset();
   }
}
