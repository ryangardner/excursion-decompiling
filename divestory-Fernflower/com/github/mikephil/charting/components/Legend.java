package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend extends ComponentBase {
   private List<Boolean> mCalculatedLabelBreakPoints;
   private List<FSize> mCalculatedLabelSizes;
   private List<FSize> mCalculatedLineSizes;
   private Legend.LegendDirection mDirection;
   private boolean mDrawInside;
   private LegendEntry[] mEntries;
   private LegendEntry[] mExtraEntries;
   private DashPathEffect mFormLineDashEffect;
   private float mFormLineWidth;
   private float mFormSize;
   private float mFormToTextSpace;
   private Legend.LegendHorizontalAlignment mHorizontalAlignment;
   private boolean mIsLegendCustom;
   private float mMaxSizePercent;
   public float mNeededHeight;
   public float mNeededWidth;
   private Legend.LegendOrientation mOrientation;
   private Legend.LegendForm mShape;
   private float mStackSpace;
   public float mTextHeightMax;
   public float mTextWidthMax;
   private Legend.LegendVerticalAlignment mVerticalAlignment;
   private boolean mWordWrapEnabled;
   private float mXEntrySpace;
   private float mYEntrySpace;

   public Legend() {
      this.mEntries = new LegendEntry[0];
      this.mIsLegendCustom = false;
      this.mHorizontalAlignment = Legend.LegendHorizontalAlignment.LEFT;
      this.mVerticalAlignment = Legend.LegendVerticalAlignment.BOTTOM;
      this.mOrientation = Legend.LegendOrientation.HORIZONTAL;
      this.mDrawInside = false;
      this.mDirection = Legend.LegendDirection.LEFT_TO_RIGHT;
      this.mShape = Legend.LegendForm.SQUARE;
      this.mFormSize = 8.0F;
      this.mFormLineWidth = 3.0F;
      this.mFormLineDashEffect = null;
      this.mXEntrySpace = 6.0F;
      this.mYEntrySpace = 0.0F;
      this.mFormToTextSpace = 5.0F;
      this.mStackSpace = 3.0F;
      this.mMaxSizePercent = 0.95F;
      this.mNeededWidth = 0.0F;
      this.mNeededHeight = 0.0F;
      this.mTextHeightMax = 0.0F;
      this.mTextWidthMax = 0.0F;
      this.mWordWrapEnabled = false;
      this.mCalculatedLabelSizes = new ArrayList(16);
      this.mCalculatedLabelBreakPoints = new ArrayList(16);
      this.mCalculatedLineSizes = new ArrayList(16);
      this.mTextSize = Utils.convertDpToPixel(10.0F);
      this.mXOffset = Utils.convertDpToPixel(5.0F);
      this.mYOffset = Utils.convertDpToPixel(3.0F);
   }

   public Legend(LegendEntry[] var1) {
      this();
      if (var1 != null) {
         this.mEntries = var1;
      } else {
         throw new IllegalArgumentException("entries array is NULL");
      }
   }

   public void calculateDimensions(Paint var1, ViewPortHandler var2) {
      float var3 = Utils.convertDpToPixel(this.mFormSize);
      float var4 = Utils.convertDpToPixel(this.mStackSpace);
      float var5 = Utils.convertDpToPixel(this.mFormToTextSpace);
      float var6 = Utils.convertDpToPixel(this.mXEntrySpace);
      float var7 = Utils.convertDpToPixel(this.mYEntrySpace);
      boolean var8 = this.mWordWrapEnabled;
      LegendEntry[] var9 = this.mEntries;
      int var10 = var9.length;
      this.mTextWidthMax = this.getMaximumEntryWidth(var1);
      this.mTextHeightMax = this.getMaximumEntryHeight(var1);
      int var11 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()];
      float var17;
      float var19;
      float var20;
      float var21;
      float var23;
      int var32;
      if (var11 != 1) {
         if (var11 == 2) {
            float var12 = Utils.getLineHeight(var1);
            float var13 = Utils.getLineSpacing(var1);
            float var14 = var2.contentWidth();
            float var15 = this.mMaxSizePercent;
            this.mCalculatedLabelBreakPoints.clear();
            this.mCalculatedLabelSizes.clear();
            this.mCalculatedLineSizes.clear();
            var11 = 0;
            int var16 = -1;
            var17 = 0.0F;
            float var18 = 0.0F;
            var19 = 0.0F;
            LegendEntry[] var25 = var9;
            var20 = var4;

            for(var21 = var3; var11 < var10; var19 = var4) {
               LegendEntry var28 = var25[var11];
               boolean var22;
               if (var28.form != Legend.LegendForm.NONE) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (Float.isNaN(var28.formSize)) {
                  var23 = var21;
               } else {
                  var23 = Utils.convertDpToPixel(var28.formSize);
               }

               String var29 = var28.label;
               this.mCalculatedLabelBreakPoints.add(false);
               if (var16 == -1) {
                  var3 = 0.0F;
               } else {
                  var3 = var18 + var20;
               }

               if (var29 != null) {
                  this.mCalculatedLabelSizes.add(Utils.calcTextSize(var1, var29));
                  if (var22) {
                     var23 += var5;
                  } else {
                     var23 = 0.0F;
                  }

                  var23 = var3 + var23 + ((FSize)this.mCalculatedLabelSizes.get(var11)).width;
                  var32 = var16;
               } else {
                  this.mCalculatedLabelSizes.add(FSize.getInstance(0.0F, 0.0F));
                  if (!var22) {
                     var23 = 0.0F;
                  }

                  var3 += var23;
                  var23 = var3;
                  var32 = var16;
                  if (var16 == -1) {
                     var32 = var11;
                     var23 = var3;
                  }
               }

               label167: {
                  if (var29 == null) {
                     var3 = var17;
                     var4 = var19;
                     if (var11 != var10 - 1) {
                        break label167;
                     }
                  }

                  float var33;
                  var16 = (var33 = var19 - 0.0F) == 0.0F ? 0 : (var33 < 0.0F ? -1 : 1);
                  if (var16 == 0) {
                     var18 = 0.0F;
                  } else {
                     var18 = var6;
                  }

                  if (var8 && var16 != 0 && var14 * var15 - var19 < var18 + var23) {
                     this.mCalculatedLineSizes.add(FSize.getInstance(var19, var12));
                     var3 = Math.max(var17, var19);
                     List var24 = this.mCalculatedLabelBreakPoints;
                     if (var32 > -1) {
                        var16 = var32;
                     } else {
                        var16 = var11;
                     }

                     var24.set(var16, true);
                     var17 = var23;
                  } else {
                     var3 = var17;
                     var17 = var19 + var18 + var23;
                  }

                  if (var11 == var10 - 1) {
                     this.mCalculatedLineSizes.add(FSize.getInstance(var17, var12));
                     var3 = Math.max(var3, var17);
                  }

                  var4 = var17;
               }

               if (var29 != null) {
                  var32 = -1;
               }

               ++var11;
               var16 = var32;
               var17 = var3;
               var18 = var23;
            }

            this.mNeededWidth = var17;
            var23 = (float)this.mCalculatedLineSizes.size();
            if (this.mCalculatedLineSizes.size() == 0) {
               var11 = 0;
            } else {
               var11 = this.mCalculatedLineSizes.size() - 1;
            }

            this.mNeededHeight = var12 * var23 + (var13 + var7) * (float)var11;
         }
      } else {
         var19 = Utils.getLineHeight(var1);
         var21 = 0.0F;
         var6 = 0.0F;
         var32 = 0;
         var23 = 0.0F;

         for(boolean var30 = false; var32 < var10; ++var32) {
            LegendEntry var26 = var9[var32];
            boolean var31;
            if (var26.form != Legend.LegendForm.NONE) {
               var31 = true;
            } else {
               var31 = false;
            }

            if (Float.isNaN(var26.formSize)) {
               var20 = var3;
            } else {
               var20 = Utils.convertDpToPixel(var26.formSize);
            }

            String var27 = var26.label;
            if (!var30) {
               var6 = 0.0F;
            }

            var17 = var6;
            if (var31) {
               var17 = var6;
               if (var30) {
                  var17 = var6 + var4;
               }

               var17 += var20;
            }

            if (var27 == null) {
               var17 += var20;
               var6 = var17;
               if (var32 < var10 - 1) {
                  var6 = var17 + var4;
               }

               var30 = true;
            } else {
               label166: {
                  if (var31 && !var30) {
                     var6 = var17 + var5;
                  } else {
                     var6 = var17;
                     if (var30) {
                        var23 = Math.max(var23, var17);
                        var21 += var19 + var7;
                        var17 = 0.0F;
                        var30 = false;
                        break label166;
                     }
                  }

                  var17 = var6;
               }

               var20 = (float)Utils.calcTextWidth(var1, var27);
               var6 = var21;
               if (var32 < var10 - 1) {
                  var6 = var21 + var19 + var7;
               }

               var17 += var20;
               var21 = var6;
               var6 = var17;
            }

            var23 = Math.max(var23, var6);
         }

         this.mNeededWidth = var23;
         this.mNeededHeight = var21;
      }

      this.mNeededHeight += this.mYOffset;
      this.mNeededWidth += this.mXOffset;
   }

   public List<Boolean> getCalculatedLabelBreakPoints() {
      return this.mCalculatedLabelBreakPoints;
   }

   public List<FSize> getCalculatedLabelSizes() {
      return this.mCalculatedLabelSizes;
   }

   public List<FSize> getCalculatedLineSizes() {
      return this.mCalculatedLineSizes;
   }

   public Legend.LegendDirection getDirection() {
      return this.mDirection;
   }

   public LegendEntry[] getEntries() {
      return this.mEntries;
   }

   public LegendEntry[] getExtraEntries() {
      return this.mExtraEntries;
   }

   public Legend.LegendForm getForm() {
      return this.mShape;
   }

   public DashPathEffect getFormLineDashEffect() {
      return this.mFormLineDashEffect;
   }

   public float getFormLineWidth() {
      return this.mFormLineWidth;
   }

   public float getFormSize() {
      return this.mFormSize;
   }

   public float getFormToTextSpace() {
      return this.mFormToTextSpace;
   }

   public Legend.LegendHorizontalAlignment getHorizontalAlignment() {
      return this.mHorizontalAlignment;
   }

   public float getMaxSizePercent() {
      return this.mMaxSizePercent;
   }

   public float getMaximumEntryHeight(Paint var1) {
      LegendEntry[] var2 = this.mEntries;
      int var3 = var2.length;
      float var4 = 0.0F;

      float var7;
      for(int var5 = 0; var5 < var3; var4 = var7) {
         String var6 = var2[var5].label;
         if (var6 == null) {
            var7 = var4;
         } else {
            float var8 = (float)Utils.calcTextHeight(var1, var6);
            var7 = var4;
            if (var8 > var4) {
               var7 = var8;
            }
         }

         ++var5;
      }

      return var4;
   }

   public float getMaximumEntryWidth(Paint var1) {
      float var2 = Utils.convertDpToPixel(this.mFormToTextSpace);
      LegendEntry[] var3 = this.mEntries;
      int var4 = var3.length;
      float var5 = 0.0F;
      float var6 = 0.0F;

      float var9;
      for(int var7 = 0; var7 < var4; var6 = var9) {
         LegendEntry var8 = var3[var7];
         if (Float.isNaN(var8.formSize)) {
            var9 = this.mFormSize;
         } else {
            var9 = var8.formSize;
         }

         float var10 = Utils.convertDpToPixel(var9);
         var9 = var6;
         if (var10 > var6) {
            var9 = var10;
         }

         String var11 = var8.label;
         if (var11 == null) {
            var6 = var5;
         } else {
            var10 = (float)Utils.calcTextWidth(var1, var11);
            var6 = var5;
            if (var10 > var5) {
               var6 = var10;
            }
         }

         ++var7;
         var5 = var6;
      }

      return var5 + var6 + var2;
   }

   public Legend.LegendOrientation getOrientation() {
      return this.mOrientation;
   }

   public float getStackSpace() {
      return this.mStackSpace;
   }

   public Legend.LegendVerticalAlignment getVerticalAlignment() {
      return this.mVerticalAlignment;
   }

   public float getXEntrySpace() {
      return this.mXEntrySpace;
   }

   public float getYEntrySpace() {
      return this.mYEntrySpace;
   }

   public boolean isDrawInsideEnabled() {
      return this.mDrawInside;
   }

   public boolean isLegendCustom() {
      return this.mIsLegendCustom;
   }

   public boolean isWordWrapEnabled() {
      return this.mWordWrapEnabled;
   }

   public void resetCustom() {
      this.mIsLegendCustom = false;
   }

   public void setCustom(List<LegendEntry> var1) {
      this.mEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
      this.mIsLegendCustom = true;
   }

   public void setCustom(LegendEntry[] var1) {
      this.mEntries = var1;
      this.mIsLegendCustom = true;
   }

   public void setDirection(Legend.LegendDirection var1) {
      this.mDirection = var1;
   }

   public void setDrawInside(boolean var1) {
      this.mDrawInside = var1;
   }

   public void setEntries(List<LegendEntry> var1) {
      this.mEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
   }

   public void setExtra(List<LegendEntry> var1) {
      this.mExtraEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
   }

   public void setExtra(int[] var1, String[] var2) {
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < Math.min(var1.length, var2.length); ++var4) {
         LegendEntry var5 = new LegendEntry();
         var5.formColor = var1[var4];
         var5.label = var2[var4];
         if (var5.formColor != 1122868 && var5.formColor != 0) {
            if (var5.formColor == 1122867) {
               var5.form = Legend.LegendForm.EMPTY;
            }
         } else {
            var5.form = Legend.LegendForm.NONE;
         }

         var3.add(var5);
      }

      this.mExtraEntries = (LegendEntry[])var3.toArray(new LegendEntry[var3.size()]);
   }

   public void setExtra(LegendEntry[] var1) {
      LegendEntry[] var2 = var1;
      if (var1 == null) {
         var2 = new LegendEntry[0];
      }

      this.mExtraEntries = var2;
   }

   public void setForm(Legend.LegendForm var1) {
      this.mShape = var1;
   }

   public void setFormLineDashEffect(DashPathEffect var1) {
      this.mFormLineDashEffect = var1;
   }

   public void setFormLineWidth(float var1) {
      this.mFormLineWidth = var1;
   }

   public void setFormSize(float var1) {
      this.mFormSize = var1;
   }

   public void setFormToTextSpace(float var1) {
      this.mFormToTextSpace = var1;
   }

   public void setHorizontalAlignment(Legend.LegendHorizontalAlignment var1) {
      this.mHorizontalAlignment = var1;
   }

   public void setMaxSizePercent(float var1) {
      this.mMaxSizePercent = var1;
   }

   public void setOrientation(Legend.LegendOrientation var1) {
      this.mOrientation = var1;
   }

   public void setStackSpace(float var1) {
      this.mStackSpace = var1;
   }

   public void setVerticalAlignment(Legend.LegendVerticalAlignment var1) {
      this.mVerticalAlignment = var1;
   }

   public void setWordWrapEnabled(boolean var1) {
      this.mWordWrapEnabled = var1;
   }

   public void setXEntrySpace(float var1) {
      this.mXEntrySpace = var1;
   }

   public void setYEntrySpace(float var1) {
      this.mYEntrySpace = var1;
   }

   public static enum LegendDirection {
      LEFT_TO_RIGHT,
      RIGHT_TO_LEFT;

      static {
         Legend.LegendDirection var0 = new Legend.LegendDirection("RIGHT_TO_LEFT", 1);
         RIGHT_TO_LEFT = var0;
      }
   }

   public static enum LegendForm {
      CIRCLE,
      DEFAULT,
      EMPTY,
      LINE,
      NONE,
      SQUARE;

      static {
         Legend.LegendForm var0 = new Legend.LegendForm("LINE", 5);
         LINE = var0;
      }
   }

   public static enum LegendHorizontalAlignment {
      CENTER,
      LEFT,
      RIGHT;

      static {
         Legend.LegendHorizontalAlignment var0 = new Legend.LegendHorizontalAlignment("RIGHT", 2);
         RIGHT = var0;
      }
   }

   public static enum LegendOrientation {
      HORIZONTAL,
      VERTICAL;

      static {
         Legend.LegendOrientation var0 = new Legend.LegendOrientation("VERTICAL", 1);
         VERTICAL = var0;
      }
   }

   public static enum LegendVerticalAlignment {
      BOTTOM,
      CENTER,
      TOP;

      static {
         Legend.LegendVerticalAlignment var0 = new Legend.LegendVerticalAlignment("BOTTOM", 2);
         BOTTOM = var0;
      }
   }
}
