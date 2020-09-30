package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer extends AbstractBuffer<IBarDataSet> {
   protected float mBarWidth = 1.0F;
   protected boolean mContainsStacks = false;
   protected int mDataSetCount = 1;
   protected int mDataSetIndex = 0;
   protected boolean mInverted = false;

   public BarBuffer(int var1, int var2, boolean var3) {
      super(var1);
      this.mDataSetCount = var2;
      this.mContainsStacks = var3;
   }

   protected void addBar(float var1, float var2, float var3, float var4) {
      float[] var5 = this.buffer;
      int var6 = this.index++;
      var5[var6] = var1;
      var5 = this.buffer;
      var6 = this.index++;
      var5[var6] = var2;
      var5 = this.buffer;
      var6 = this.index++;
      var5[var6] = var3;
      var5 = this.buffer;
      var6 = this.index++;
      var5[var6] = var4;
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
            float var14;
            if (this.mContainsStacks && var9 != null) {
               var8 = -var6.getNegativeSum();
               int var10 = 0;

               float var15;
               for(float var11 = 0.0F; var10 < var9.length; var11 = var15) {
                  var12 = var9[var10];
                  float var16;
                  int var13 = (var16 = var12 - 0.0F) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
                  if (var13 == 0 && (var11 == 0.0F || var8 == 0.0F)) {
                     var14 = var12;
                     var12 = var8;
                     var8 = var14;
                     var15 = var11;
                  } else if (var13 >= 0) {
                     var14 = var12 + var11;
                     var12 = var8;
                     var8 = var11;
                     var15 = var14;
                  } else {
                     var14 = Math.abs(var12) + var8;
                     var12 = Math.abs(var12) + var8;
                     var15 = var11;
                  }

                  if (this.mInverted) {
                     if (var8 >= var14) {
                        var11 = var8;
                     } else {
                        var11 = var14;
                     }

                     if (var8 > var14) {
                        var8 = var14;
                     }
                  } else {
                     if (var8 >= var14) {
                        var11 = var8;
                     } else {
                        var11 = var14;
                     }

                     if (var8 > var14) {
                        var8 = var14;
                     }

                     var14 = var11;
                     var11 = var8;
                     var8 = var14;
                  }

                  this.addBar(var7 - var4, var8 * this.phaseY, var7 + var4, var11 * this.phaseY);
                  ++var10;
                  var8 = var12;
               }
            } else {
               if (this.mInverted) {
                  if (var8 >= 0.0F) {
                     var14 = var8;
                  } else {
                     var14 = 0.0F;
                  }

                  if (var8 > 0.0F) {
                     var8 = 0.0F;
                  }
               } else {
                  if (var8 >= 0.0F) {
                     var14 = var8;
                  } else {
                     var14 = 0.0F;
                  }

                  if (var8 > 0.0F) {
                     var8 = 0.0F;
                  }

                  var12 = var14;
                  var14 = var8;
                  var8 = var12;
               }

               if (var8 > 0.0F) {
                  var8 *= this.phaseY;
               } else {
                  var14 *= this.phaseY;
               }

               this.addBar(var7 - var4, var8, var7 + var4, var14);
            }
         }
      }

      this.reset();
   }

   public void setBarWidth(float var1) {
      this.mBarWidth = var1;
   }

   public void setDataSet(int var1) {
      this.mDataSetIndex = var1;
   }

   public void setInverted(boolean var1) {
      this.mInverted = var1;
   }
}
