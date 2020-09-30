package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.R;

public class ImageFilterButton extends AppCompatImageButton {
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

   public ImageFilterButton(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public ImageFilterButton(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   public ImageFilterButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1, var2);
   }

   private void init(Context var1, AttributeSet var2) {
      this.setPadding(0, 0, 0, 0);
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
      if (VERSION.SDK_INT < 21 && this.mRound != 0.0F && this.mPath != null) {
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
                        var2.setRoundRect(0, 0, ImageFilterButton.this.getWidth(), ImageFilterButton.this.getHeight(), ImageFilterButton.this.mRound);
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
            Path var6 = this.mPath;
            RectF var7 = this.mRect;
            var1 = this.mRound;
            var6.addRoundRect(var7, var1, var1, Direction.CW);
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
                     int var3 = ImageFilterButton.this.getWidth();
                     int var4 = ImageFilterButton.this.getHeight();
                     var2.setRoundRect(0, 0, var3, var4, (float)Math.min(var3, var4) * ImageFilterButton.this.mRoundPercent / 2.0F);
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
}
