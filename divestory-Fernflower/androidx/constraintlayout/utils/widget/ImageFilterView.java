package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

public class ImageFilterView extends AppCompatImageView {
   private float mCrossfade = 0.0F;
   private ImageFilterView.ImageMatrix mImageMatrix = new ImageFilterView.ImageMatrix();
   LayerDrawable mLayer;
   Drawable[] mLayers;
   private boolean mOverlay = true;
   private Path mPath;
   RectF mRect;
   private float mRound = Float.NaN;
   private float mRoundPercent = 0.0F;
   ViewOutlineProvider mViewOutlineProvider;

   public ImageFilterView(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public ImageFilterView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   public ImageFilterView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1, var2);
   }

   private void init(Context var1, AttributeSet var2) {
      if (var2 != null) {
         TypedArray var8 = this.getContext().obtainStyledAttributes(var2, R.styleable.ImageFilterView);
         int var3 = var8.getIndexCount();
         Drawable var6 = var8.getDrawable(R.styleable.ImageFilterView_altSrc);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var8.getIndex(var4);
            if (var5 == R.styleable.ImageFilterView_crossfade) {
               this.mCrossfade = var8.getFloat(var5, 0.0F);
            } else if (var5 == R.styleable.ImageFilterView_warmth) {
               this.setWarmth(var8.getFloat(var5, 0.0F));
            } else if (var5 == R.styleable.ImageFilterView_saturation) {
               this.setSaturation(var8.getFloat(var5, 0.0F));
            } else if (var5 == R.styleable.ImageFilterView_contrast) {
               this.setContrast(var8.getFloat(var5, 0.0F));
            } else if (var5 == R.styleable.ImageFilterView_round) {
               this.setRound(var8.getDimension(var5, 0.0F));
            } else if (var5 == R.styleable.ImageFilterView_roundPercent) {
               this.setRoundPercent(var8.getFloat(var5, 0.0F));
            } else if (var5 == R.styleable.ImageFilterView_overlay) {
               this.setOverlay(var8.getBoolean(var5, this.mOverlay));
            }
         }

         var8.recycle();
         if (var6 != null) {
            Drawable[] var9 = new Drawable[2];
            this.mLayers = var9;
            var9[0] = this.getDrawable();
            this.mLayers[1] = var6;
            LayerDrawable var7 = new LayerDrawable(this.mLayers);
            this.mLayer = var7;
            var7.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
            super.setImageDrawable(this.mLayer);
         }
      }

   }

   private void setOverlay(boolean var1) {
      this.mOverlay = var1;
   }

   public void draw(Canvas var1) {
      boolean var2;
      if (VERSION.SDK_INT < 21 && this.mRoundPercent != 0.0F && this.mPath != null) {
         var2 = true;
         var1.save();
         var1.clipPath(this.mPath);
      } else {
         var2 = false;
      }

      super.draw(var1);
      if (var2) {
         var1.restore();
      }

   }

   public float getBrightness() {
      return this.mImageMatrix.mBrightness;
   }

   public float getContrast() {
      return this.mImageMatrix.mContrast;
   }

   public float getCrossfade() {
      return this.mCrossfade;
   }

   public float getRound() {
      return this.mRound;
   }

   public float getRoundPercent() {
      return this.mRoundPercent;
   }

   public float getSaturation() {
      return this.mImageMatrix.mSaturation;
   }

   public float getWarmth() {
      return this.mImageMatrix.mWarmth;
   }

   public void setBrightness(float var1) {
      this.mImageMatrix.mBrightness = var1;
      this.mImageMatrix.updateMatrix(this);
   }

   public void setContrast(float var1) {
      this.mImageMatrix.mContrast = var1;
      this.mImageMatrix.updateMatrix(this);
   }

   public void setCrossfade(float var1) {
      this.mCrossfade = var1;
      if (this.mLayers != null) {
         if (!this.mOverlay) {
            this.mLayer.getDrawable(0).setAlpha((int)((1.0F - this.mCrossfade) * 255.0F));
         }

         this.mLayer.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
         super.setImageDrawable(this.mLayer);
      }

   }

   public void setRound(float var1) {
      if (Float.isNaN(var1)) {
         this.mRound = var1;
         var1 = this.mRoundPercent;
         this.mRoundPercent = -1.0F;
         this.setRoundPercent(var1);
      } else {
         boolean var2;
         if (this.mRound != var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.mRound = var1;
         if (var1 != 0.0F) {
            if (this.mPath == null) {
               this.mPath = new Path();
            }

            if (this.mRect == null) {
               this.mRect = new RectF();
            }

            if (VERSION.SDK_INT >= 21) {
               if (this.mViewOutlineProvider == null) {
                  ViewOutlineProvider var3 = new ViewOutlineProvider() {
                     public void getOutline(View var1, Outline var2) {
                        var2.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
                     }
                  };
                  this.mViewOutlineProvider = var3;
                  this.setOutlineProvider(var3);
               }

               this.setClipToOutline(true);
            }

            int var4 = this.getWidth();
            int var5 = this.getHeight();
            this.mRect.set(0.0F, 0.0F, (float)var4, (float)var5);
            this.mPath.reset();
            Path var7 = this.mPath;
            RectF var6 = this.mRect;
            var1 = this.mRound;
            var7.addRoundRect(var6, var1, var1, Direction.CW);
         } else if (VERSION.SDK_INT >= 21) {
            this.setClipToOutline(false);
         }

         if (var2 && VERSION.SDK_INT >= 21) {
            this.invalidateOutline();
         }

      }
   }

   public void setRoundPercent(float var1) {
      boolean var2;
      if (this.mRoundPercent != var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.mRoundPercent = var1;
      if (var1 != 0.0F) {
         if (this.mPath == null) {
            this.mPath = new Path();
         }

         if (this.mRect == null) {
            this.mRect = new RectF();
         }

         if (VERSION.SDK_INT >= 21) {
            if (this.mViewOutlineProvider == null) {
               ViewOutlineProvider var3 = new ViewOutlineProvider() {
                  public void getOutline(View var1, Outline var2) {
                     int var3 = ImageFilterView.this.getWidth();
                     int var4 = ImageFilterView.this.getHeight();
                     var2.setRoundRect(0, 0, var3, var4, (float)Math.min(var3, var4) * ImageFilterView.this.mRoundPercent / 2.0F);
                  }
               };
               this.mViewOutlineProvider = var3;
               this.setOutlineProvider(var3);
            }

            this.setClipToOutline(true);
         }

         int var4 = this.getWidth();
         int var5 = this.getHeight();
         var1 = (float)Math.min(var4, var5) * this.mRoundPercent / 2.0F;
         this.mRect.set(0.0F, 0.0F, (float)var4, (float)var5);
         this.mPath.reset();
         this.mPath.addRoundRect(this.mRect, var1, var1, Direction.CW);
      } else if (VERSION.SDK_INT >= 21) {
         this.setClipToOutline(false);
      }

      if (var2 && VERSION.SDK_INT >= 21) {
         this.invalidateOutline();
      }

   }

   public void setSaturation(float var1) {
      this.mImageMatrix.mSaturation = var1;
      this.mImageMatrix.updateMatrix(this);
   }

   public void setWarmth(float var1) {
      this.mImageMatrix.mWarmth = var1;
      this.mImageMatrix.updateMatrix(this);
   }

   static class ImageMatrix {
      float[] m = new float[20];
      float mBrightness = 1.0F;
      ColorMatrix mColorMatrix = new ColorMatrix();
      float mContrast = 1.0F;
      float mSaturation = 1.0F;
      ColorMatrix mTmpColorMatrix = new ColorMatrix();
      float mWarmth = 1.0F;

      private void brightness(float var1) {
         float[] var2 = this.m;
         var2[0] = var1;
         var2[1] = 0.0F;
         var2[2] = 0.0F;
         var2[3] = 0.0F;
         var2[4] = 0.0F;
         var2[5] = 0.0F;
         var2[6] = var1;
         var2[7] = 0.0F;
         var2[8] = 0.0F;
         var2[9] = 0.0F;
         var2[10] = 0.0F;
         var2[11] = 0.0F;
         var2[12] = var1;
         var2[13] = 0.0F;
         var2[14] = 0.0F;
         var2[15] = 0.0F;
         var2[16] = 0.0F;
         var2[17] = 0.0F;
         var2[18] = 1.0F;
         var2[19] = 0.0F;
      }

      private void saturation(float var1) {
         float var2 = 1.0F - var1;
         float var3 = 0.2999F * var2;
         float var4 = 0.587F * var2;
         var2 *= 0.114F;
         float[] var5 = this.m;
         var5[0] = var3 + var1;
         var5[1] = var4;
         var5[2] = var2;
         var5[3] = 0.0F;
         var5[4] = 0.0F;
         var5[5] = var3;
         var5[6] = var4 + var1;
         var5[7] = var2;
         var5[8] = 0.0F;
         var5[9] = 0.0F;
         var5[10] = var3;
         var5[11] = var4;
         var5[12] = var2 + var1;
         var5[13] = 0.0F;
         var5[14] = 0.0F;
         var5[15] = 0.0F;
         var5[16] = 0.0F;
         var5[17] = 0.0F;
         var5[18] = 1.0F;
         var5[19] = 0.0F;
      }

      private void warmth(float var1) {
         float var2 = var1;
         if (var1 <= 0.0F) {
            var2 = 0.01F;
         }

         var1 = 5000.0F / var2 / 100.0F;
         float var5;
         if (var1 > 66.0F) {
            double var3 = (double)(var1 - 60.0F);
            var5 = (float)Math.pow(var3, -0.13320475816726685D) * 329.69873F;
            var2 = (float)Math.pow(var3, 0.07551484555006027D) * 288.12216F;
         } else {
            var2 = (float)Math.log((double)var1) * 99.4708F - 161.11957F;
            var5 = 255.0F;
         }

         if (var1 < 66.0F) {
            if (var1 > 19.0F) {
               var1 = (float)Math.log((double)(var1 - 10.0F)) * 138.51773F - 305.0448F;
            } else {
               var1 = 0.0F;
            }
         } else {
            var1 = 255.0F;
         }

         var5 = Math.min(255.0F, Math.max(var5, 0.0F));
         var2 = Math.min(255.0F, Math.max(var2, 0.0F));
         var1 = Math.min(255.0F, Math.max(var1, 0.0F));
         float var6 = (float)Math.log((double)50.0F);
         float var7 = (float)Math.log((double)40.0F);
         float var8 = Math.min(255.0F, Math.max(255.0F, 0.0F));
         var6 = Math.min(255.0F, Math.max(var6 * 99.4708F - 161.11957F, 0.0F));
         var7 = Math.min(255.0F, Math.max(var7 * 138.51773F - 305.0448F, 0.0F));
         var5 /= var8;
         var2 /= var6;
         var1 /= var7;
         float[] var9 = this.m;
         var9[0] = var5;
         var9[1] = 0.0F;
         var9[2] = 0.0F;
         var9[3] = 0.0F;
         var9[4] = 0.0F;
         var9[5] = 0.0F;
         var9[6] = var2;
         var9[7] = 0.0F;
         var9[8] = 0.0F;
         var9[9] = 0.0F;
         var9[10] = 0.0F;
         var9[11] = 0.0F;
         var9[12] = var1;
         var9[13] = 0.0F;
         var9[14] = 0.0F;
         var9[15] = 0.0F;
         var9[16] = 0.0F;
         var9[17] = 0.0F;
         var9[18] = 1.0F;
         var9[19] = 0.0F;
      }

      void updateMatrix(ImageView var1) {
         this.mColorMatrix.reset();
         float var2 = this.mSaturation;
         boolean var3 = true;
         boolean var4;
         if (var2 != 1.0F) {
            this.saturation(var2);
            this.mColorMatrix.set(this.m);
            var4 = true;
         } else {
            var4 = false;
         }

         var2 = this.mContrast;
         if (var2 != 1.0F) {
            this.mTmpColorMatrix.setScale(var2, var2, var2, 1.0F);
            this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            var4 = true;
         }

         var2 = this.mWarmth;
         if (var2 != 1.0F) {
            this.warmth(var2);
            this.mTmpColorMatrix.set(this.m);
            this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            var4 = true;
         }

         var2 = this.mBrightness;
         if (var2 != 1.0F) {
            this.brightness(var2);
            this.mTmpColorMatrix.set(this.m);
            this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            var4 = var3;
         }

         if (var4) {
            var1.setColorFilter(new ColorMatrixColorFilter(this.mColorMatrix));
         } else {
            var1.clearColorFilter();
         }

      }
   }
}
