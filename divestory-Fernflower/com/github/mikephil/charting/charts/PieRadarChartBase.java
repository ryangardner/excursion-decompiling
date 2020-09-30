package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>> extends Chart<T> {
   protected float mMinOffset = 0.0F;
   private float mRawRotationAngle = 270.0F;
   protected boolean mRotateEnabled = true;
   private float mRotationAngle = 270.0F;

   public PieRadarChartBase(Context var1) {
      super(var1);
   }

   public PieRadarChartBase(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public PieRadarChartBase(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void calcMinMax() {
   }

   public void calculateOffsets() {
      Legend var1 = this.mLegend;
      float var2 = 0.0F;
      float var3 = 0.0F;
      float var4 = 0.0F;
      float var5 = 0.0F;
      float var6 = 0.0F;
      float var7;
      float var9;
      float var10;
      if (var1 != null && this.mLegend.isEnabled() && !this.mLegend.isDrawInsideEnabled()) {
         label103: {
            label102: {
               label101: {
                  label100: {
                     label99: {
                        var7 = Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent());
                        int var8 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mLegend.getOrientation().ordinal()];
                        if (var8 != 1) {
                           if (var8 != 2) {
                              var2 = var6;
                           } else {
                              label112: {
                                 if (this.mLegend.getVerticalAlignment() != Legend.LegendVerticalAlignment.TOP) {
                                    var2 = var6;
                                    if (this.mLegend.getVerticalAlignment() != Legend.LegendVerticalAlignment.BOTTOM) {
                                       break label112;
                                    }
                                 }

                                 var7 = this.getRequiredLegendOffset();
                                 var2 = Math.min(this.mLegend.mNeededHeight + var7, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                 var8 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
                                 var7 = var2;
                                 if (var8 == 1) {
                                    break label102;
                                 }

                                 var7 = var2;
                                 if (var8 == 2) {
                                    break label99;
                                 }

                                 var2 = var6;
                              }
                           }
                        } else {
                           if (this.mLegend.getHorizontalAlignment() != Legend.LegendHorizontalAlignment.LEFT && this.mLegend.getHorizontalAlignment() != Legend.LegendHorizontalAlignment.RIGHT) {
                              var7 = 0.0F;
                           } else if (this.mLegend.getVerticalAlignment() == Legend.LegendVerticalAlignment.CENTER) {
                              var7 += Utils.convertDpToPixel(13.0F);
                           } else {
                              var5 = var7 + Utils.convertDpToPixel(8.0F);
                              var9 = this.mLegend.mNeededHeight;
                              var10 = this.mLegend.mTextHeightMax;
                              MPPointF var13 = this.getCenter();
                              if (this.mLegend.getHorizontalAlignment() == Legend.LegendHorizontalAlignment.RIGHT) {
                                 var7 = (float)this.getWidth() - var5 + 15.0F;
                              } else {
                                 var7 = var5 - 15.0F;
                              }

                              var10 = var9 + var10 + 15.0F;
                              var9 = this.distanceToCenter(var7, var10);
                              MPPointF var11 = this.getPosition(var13, this.getRadius(), this.getAngleForPoint(var7, var10));
                              float var12 = this.distanceToCenter(var11.x, var11.y);
                              var7 = Utils.convertDpToPixel(5.0F);
                              if (var10 >= var13.y && (float)this.getHeight() - var5 > (float)this.getWidth()) {
                                 var7 = var5;
                              } else if (var9 < var12) {
                                 var7 += var12 - var9;
                              } else {
                                 var7 = 0.0F;
                              }

                              MPPointF.recycleInstance(var13);
                              MPPointF.recycleInstance(var11);
                           }

                           var8 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[this.mLegend.getHorizontalAlignment().ordinal()];
                           if (var8 != 1) {
                              if (var8 == 2) {
                                 break label100;
                              }

                              if (var8 == 3) {
                                 var8 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
                                 if (var8 == 1) {
                                    var7 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    break label102;
                                 }

                                 if (var8 == 2) {
                                    var7 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    break label99;
                                 }
                              }

                              var2 = var6;
                           } else {
                              var2 = var7;
                           }
                        }

                        var7 = 0.0F;
                        break label100;
                     }

                     var4 = var7;
                     var7 = 0.0F;
                     var2 = var3;
                     break label101;
                  }

                  var4 = 0.0F;
               }

               var3 = 0.0F;
               break label103;
            }

            var3 = var7;
            var7 = 0.0F;
            var6 = 0.0F;
            var2 = var4;
            var4 = var6;
         }

         var6 = var2 + this.getRequiredBaseOffset();
         var2 = var7 + this.getRequiredBaseOffset();
         var5 = var3 + this.getRequiredBaseOffset();
         var7 = var4 + this.getRequiredBaseOffset();
         var3 = var6;
         var4 = var5;
      } else {
         var2 = 0.0F;
         var7 = 0.0F;
         var4 = 0.0F;
         var3 = var5;
      }

      var5 = Utils.convertDpToPixel(this.mMinOffset);
      var6 = var5;
      if (this instanceof RadarChart) {
         XAxis var14 = this.getXAxis();
         var6 = var5;
         if (var14.isEnabled()) {
            var6 = var5;
            if (var14.isDrawLabelsEnabled()) {
               var6 = Math.max(var5, (float)var14.mLabelRotatedWidth);
            }
         }
      }

      var10 = this.getExtraTopOffset();
      var9 = this.getExtraRightOffset();
      var5 = this.getExtraBottomOffset();
      var3 = Math.max(var6, var3 + this.getExtraLeftOffset());
      var4 = Math.max(var6, var4 + var10);
      var2 = Math.max(var6, var2 + var9);
      var7 = Math.max(var6, Math.max(this.getRequiredBaseOffset(), var7 + var5));
      this.mViewPortHandler.restrainViewPort(var3, var4, var2, var7);
      if (this.mLogEnabled) {
         StringBuilder var15 = new StringBuilder();
         var15.append("offsetLeft: ");
         var15.append(var3);
         var15.append(", offsetTop: ");
         var15.append(var4);
         var15.append(", offsetRight: ");
         var15.append(var2);
         var15.append(", offsetBottom: ");
         var15.append(var7);
         Log.i("MPAndroidChart", var15.toString());
      }

   }

   public void computeScroll() {
      if (this.mChartTouchListener instanceof PieRadarChartTouchListener) {
         ((PieRadarChartTouchListener)this.mChartTouchListener).computeScroll();
      }

   }

   public float distanceToCenter(float var1, float var2) {
      MPPointF var3 = this.getCenterOffsets();
      if (var1 > var3.x) {
         var1 -= var3.x;
      } else {
         var1 = var3.x - var1;
      }

      if (var2 > var3.y) {
         var2 -= var3.y;
      } else {
         var2 = var3.y - var2;
      }

      var1 = (float)Math.sqrt(Math.pow((double)var1, 2.0D) + Math.pow((double)var2, 2.0D));
      MPPointF.recycleInstance(var3);
      return var1;
   }

   public float getAngleForPoint(float var1, float var2) {
      MPPointF var3 = this.getCenterOffsets();
      double var4 = (double)(var1 - var3.x);
      double var6 = (double)(var2 - var3.y);
      float var8 = (float)Math.toDegrees(Math.acos(var6 / Math.sqrt(var4 * var4 + var6 * var6)));
      var2 = var8;
      if (var1 > var3.x) {
         var2 = 360.0F - var8;
      }

      var2 += 90.0F;
      var1 = var2;
      if (var2 > 360.0F) {
         var1 = var2 - 360.0F;
      }

      MPPointF.recycleInstance(var3);
      return var1;
   }

   public float getDiameter() {
      RectF var1 = this.mViewPortHandler.getContentRect();
      var1.left += this.getExtraLeftOffset();
      var1.top += this.getExtraTopOffset();
      var1.right -= this.getExtraRightOffset();
      var1.bottom -= this.getExtraBottomOffset();
      return Math.min(var1.width(), var1.height());
   }

   public abstract int getIndexForAngle(float var1);

   public int getMaxVisibleCount() {
      return this.mData.getEntryCount();
   }

   public float getMinOffset() {
      return this.mMinOffset;
   }

   public MPPointF getPosition(MPPointF var1, float var2, float var3) {
      MPPointF var4 = MPPointF.getInstance(0.0F, 0.0F);
      this.getPosition(var1, var2, var3, var4);
      return var4;
   }

   public void getPosition(MPPointF var1, float var2, float var3, MPPointF var4) {
      double var5 = (double)var1.x;
      double var7 = (double)var2;
      double var9 = (double)var3;
      var4.x = (float)(var5 + Math.cos(Math.toRadians(var9)) * var7);
      var4.y = (float)((double)var1.y + var7 * Math.sin(Math.toRadians(var9)));
   }

   public abstract float getRadius();

   public float getRawRotationAngle() {
      return this.mRawRotationAngle;
   }

   protected abstract float getRequiredBaseOffset();

   protected abstract float getRequiredLegendOffset();

   public float getRotationAngle() {
      return this.mRotationAngle;
   }

   public float getYChartMax() {
      return 0.0F;
   }

   public float getYChartMin() {
      return 0.0F;
   }

   protected void init() {
      super.init();
      this.mChartTouchListener = new PieRadarChartTouchListener(this);
   }

   public boolean isRotationEnabled() {
      return this.mRotateEnabled;
   }

   public void notifyDataSetChanged() {
      if (this.mData != null) {
         this.calcMinMax();
         if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
         }

         this.calculateOffsets();
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      return this.mTouchEnabled && this.mChartTouchListener != null ? this.mChartTouchListener.onTouch(this, var1) : super.onTouchEvent(var1);
   }

   public void setMinOffset(float var1) {
      this.mMinOffset = var1;
   }

   public void setRotationAngle(float var1) {
      this.mRawRotationAngle = var1;
      this.mRotationAngle = Utils.getNormalizedAngle(var1);
   }

   public void setRotationEnabled(boolean var1) {
      this.mRotateEnabled = var1;
   }

   public void spin(int var1, float var2, float var3, Easing.EasingFunction var4) {
      this.setRotationAngle(var2);
      ObjectAnimator var5 = ObjectAnimator.ofFloat(this, "rotationAngle", new float[]{var2, var3});
      var5.setDuration((long)var1);
      var5.setInterpolator(var4);
      var5.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1) {
            PieRadarChartBase.this.postInvalidate();
         }
      });
      var5.start();
   }
}
