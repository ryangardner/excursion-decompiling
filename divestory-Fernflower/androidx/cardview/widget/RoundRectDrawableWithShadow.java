package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import androidx.cardview.R;

class RoundRectDrawableWithShadow extends Drawable {
   private static final double COS_45 = Math.cos(Math.toRadians(45.0D));
   private static final float SHADOW_MULTIPLIER = 1.5F;
   static RoundRectDrawableWithShadow.RoundRectHelper sRoundRectHelper;
   private boolean mAddPaddingForCorners = true;
   private ColorStateList mBackground;
   private final RectF mCardBounds;
   private float mCornerRadius;
   private Paint mCornerShadowPaint;
   private Path mCornerShadowPath;
   private boolean mDirty = true;
   private Paint mEdgeShadowPaint;
   private final int mInsetShadow;
   private Paint mPaint;
   private boolean mPrintedShadowClipWarning = false;
   private float mRawMaxShadowSize;
   private float mRawShadowSize;
   private final int mShadowEndColor;
   private float mShadowSize;
   private final int mShadowStartColor;

   RoundRectDrawableWithShadow(Resources var1, ColorStateList var2, float var3, float var4, float var5) {
      this.mShadowStartColor = var1.getColor(R.color.cardview_shadow_start_color);
      this.mShadowEndColor = var1.getColor(R.color.cardview_shadow_end_color);
      this.mInsetShadow = var1.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
      this.mPaint = new Paint(5);
      this.setBackground(var2);
      Paint var6 = new Paint(5);
      this.mCornerShadowPaint = var6;
      var6.setStyle(Style.FILL);
      this.mCornerRadius = (float)((int)(var3 + 0.5F));
      this.mCardBounds = new RectF();
      var6 = new Paint(this.mCornerShadowPaint);
      this.mEdgeShadowPaint = var6;
      var6.setAntiAlias(false);
      this.setShadowSize(var4, var5);
   }

   private void buildComponents(Rect var1) {
      float var2 = this.mRawMaxShadowSize * 1.5F;
      this.mCardBounds.set((float)var1.left + this.mRawMaxShadowSize, (float)var1.top + var2, (float)var1.right - this.mRawMaxShadowSize, (float)var1.bottom - var2);
      this.buildShadowCorners();
   }

   private void buildShadowCorners() {
      float var1 = this.mCornerRadius;
      RectF var2 = new RectF(-var1, -var1, var1, var1);
      RectF var3 = new RectF(var2);
      var1 = this.mShadowSize;
      var3.inset(-var1, -var1);
      Path var4 = this.mCornerShadowPath;
      if (var4 == null) {
         this.mCornerShadowPath = new Path();
      } else {
         var4.reset();
      }

      this.mCornerShadowPath.setFillType(FillType.EVEN_ODD);
      this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
      this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
      this.mCornerShadowPath.arcTo(var3, 180.0F, 90.0F, false);
      this.mCornerShadowPath.arcTo(var2, 270.0F, -90.0F, false);
      this.mCornerShadowPath.close();
      var1 = this.mCornerRadius;
      float var5 = var1 / (this.mShadowSize + var1);
      Paint var9 = this.mCornerShadowPaint;
      float var6 = this.mCornerRadius;
      var1 = this.mShadowSize;
      int var7 = this.mShadowStartColor;
      int var8 = this.mShadowEndColor;
      TileMode var10 = TileMode.CLAMP;
      var9.setShader(new RadialGradient(0.0F, 0.0F, var6 + var1, new int[]{var7, var7, var8}, new float[]{0.0F, var5, 1.0F}, var10));
      var9 = this.mEdgeShadowPaint;
      var5 = this.mCornerRadius;
      var1 = -var5;
      var6 = this.mShadowSize;
      var5 = -var5;
      var7 = this.mShadowStartColor;
      var8 = this.mShadowEndColor;
      var10 = TileMode.CLAMP;
      var9.setShader(new LinearGradient(0.0F, var1 + var6, 0.0F, var5 - var6, new int[]{var7, var7, var8}, new float[]{0.0F, 0.5F, 1.0F}, var10));
      this.mEdgeShadowPaint.setAntiAlias(false);
   }

   static float calculateHorizontalPadding(float var0, float var1, boolean var2) {
      float var3 = var0;
      if (var2) {
         var3 = (float)((double)var0 + (1.0D - COS_45) * (double)var1);
      }

      return var3;
   }

   static float calculateVerticalPadding(float var0, float var1, boolean var2) {
      return var2 ? (float)((double)(var0 * 1.5F) + (1.0D - COS_45) * (double)var1) : var0 * 1.5F;
   }

   private void drawShadow(Canvas var1) {
      float var2 = this.mCornerRadius;
      float var3 = -var2 - this.mShadowSize;
      float var4 = var2 + (float)this.mInsetShadow + this.mRawShadowSize / 2.0F;
      var2 = this.mCardBounds.width();
      float var5 = var4 * 2.0F;
      boolean var6;
      if (var2 - var5 > 0.0F) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if (this.mCardBounds.height() - var5 > 0.0F) {
         var7 = true;
      } else {
         var7 = false;
      }

      int var8 = var1.save();
      var1.translate(this.mCardBounds.left + var4, this.mCardBounds.top + var4);
      var1.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (var6) {
         var1.drawRect(0.0F, var3, this.mCardBounds.width() - var5, -this.mCornerRadius, this.mEdgeShadowPaint);
      }

      var1.restoreToCount(var8);
      var8 = var1.save();
      var1.translate(this.mCardBounds.right - var4, this.mCardBounds.bottom - var4);
      var1.rotate(180.0F);
      var1.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (var6) {
         var1.drawRect(0.0F, var3, this.mCardBounds.width() - var5, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
      }

      var1.restoreToCount(var8);
      int var9 = var1.save();
      var1.translate(this.mCardBounds.left + var4, this.mCardBounds.bottom - var4);
      var1.rotate(270.0F);
      var1.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (var7) {
         var1.drawRect(0.0F, var3, this.mCardBounds.height() - var5, -this.mCornerRadius, this.mEdgeShadowPaint);
      }

      var1.restoreToCount(var9);
      var9 = var1.save();
      var1.translate(this.mCardBounds.right - var4, this.mCardBounds.top + var4);
      var1.rotate(90.0F);
      var1.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (var7) {
         var1.drawRect(0.0F, var3, this.mCardBounds.height() - var5, -this.mCornerRadius, this.mEdgeShadowPaint);
      }

      var1.restoreToCount(var9);
   }

   private void setBackground(ColorStateList var1) {
      ColorStateList var2 = var1;
      if (var1 == null) {
         var2 = ColorStateList.valueOf(0);
      }

      this.mBackground = var2;
      this.mPaint.setColor(var2.getColorForState(this.getState(), this.mBackground.getDefaultColor()));
   }

   private void setShadowSize(float var1, float var2) {
      StringBuilder var4;
      if (var1 >= 0.0F) {
         if (var2 >= 0.0F) {
            float var3 = (float)this.toEven(var1);
            var2 = (float)this.toEven(var2);
            var1 = var3;
            if (var3 > var2) {
               if (!this.mPrintedShadowClipWarning) {
                  this.mPrintedShadowClipWarning = true;
               }

               var1 = var2;
            }

            if (this.mRawShadowSize != var1 || this.mRawMaxShadowSize != var2) {
               this.mRawShadowSize = var1;
               this.mRawMaxShadowSize = var2;
               this.mShadowSize = (float)((int)(var1 * 1.5F + (float)this.mInsetShadow + 0.5F));
               this.mDirty = true;
               this.invalidateSelf();
            }
         } else {
            var4 = new StringBuilder();
            var4.append("Invalid max shadow size ");
            var4.append(var2);
            var4.append(". Must be >= 0");
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         var4 = new StringBuilder();
         var4.append("Invalid shadow size ");
         var4.append(var1);
         var4.append(". Must be >= 0");
         throw new IllegalArgumentException(var4.toString());
      }
   }

   private int toEven(float var1) {
      int var2 = (int)(var1 + 0.5F);
      int var3 = var2;
      if (var2 % 2 == 1) {
         var3 = var2 - 1;
      }

      return var3;
   }

   public void draw(Canvas var1) {
      if (this.mDirty) {
         this.buildComponents(this.getBounds());
         this.mDirty = false;
      }

      var1.translate(0.0F, this.mRawShadowSize / 2.0F);
      this.drawShadow(var1);
      var1.translate(0.0F, -this.mRawShadowSize / 2.0F);
      sRoundRectHelper.drawRoundRect(var1, this.mCardBounds, this.mCornerRadius, this.mPaint);
   }

   ColorStateList getColor() {
      return this.mBackground;
   }

   float getCornerRadius() {
      return this.mCornerRadius;
   }

   void getMaxShadowAndCornerPadding(Rect var1) {
      this.getPadding(var1);
   }

   float getMaxShadowSize() {
      return this.mRawMaxShadowSize;
   }

   float getMinHeight() {
      float var1 = this.mRawMaxShadowSize;
      return Math.max(var1, this.mCornerRadius + (float)this.mInsetShadow + var1 * 1.5F / 2.0F) * 2.0F + (this.mRawMaxShadowSize * 1.5F + (float)this.mInsetShadow) * 2.0F;
   }

   float getMinWidth() {
      float var1 = this.mRawMaxShadowSize;
      return Math.max(var1, this.mCornerRadius + (float)this.mInsetShadow + var1 / 2.0F) * 2.0F + (this.mRawMaxShadowSize + (float)this.mInsetShadow) * 2.0F;
   }

   public int getOpacity() {
      return -3;
   }

   public boolean getPadding(Rect var1) {
      int var2 = (int)Math.ceil((double)calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
      int var3 = (int)Math.ceil((double)calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
      var1.set(var3, var2, var3, var2);
      return true;
   }

   float getShadowSize() {
      return this.mRawShadowSize;
   }

   public boolean isStateful() {
      ColorStateList var1 = this.mBackground;
      boolean var2;
      if ((var1 == null || !var1.isStateful()) && !super.isStateful()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected void onBoundsChange(Rect var1) {
      super.onBoundsChange(var1);
      this.mDirty = true;
   }

   protected boolean onStateChange(int[] var1) {
      ColorStateList var2 = this.mBackground;
      int var3 = var2.getColorForState(var1, var2.getDefaultColor());
      if (this.mPaint.getColor() == var3) {
         return false;
      } else {
         this.mPaint.setColor(var3);
         this.mDirty = true;
         this.invalidateSelf();
         return true;
      }
   }

   void setAddPaddingForCorners(boolean var1) {
      this.mAddPaddingForCorners = var1;
      this.invalidateSelf();
   }

   public void setAlpha(int var1) {
      this.mPaint.setAlpha(var1);
      this.mCornerShadowPaint.setAlpha(var1);
      this.mEdgeShadowPaint.setAlpha(var1);
   }

   void setColor(ColorStateList var1) {
      this.setBackground(var1);
      this.invalidateSelf();
   }

   public void setColorFilter(ColorFilter var1) {
      this.mPaint.setColorFilter(var1);
   }

   void setCornerRadius(float var1) {
      if (var1 >= 0.0F) {
         var1 = (float)((int)(var1 + 0.5F));
         if (this.mCornerRadius != var1) {
            this.mCornerRadius = var1;
            this.mDirty = true;
            this.invalidateSelf();
         }
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid radius ");
         var2.append(var1);
         var2.append(". Must be >= 0");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   void setMaxShadowSize(float var1) {
      this.setShadowSize(this.mRawShadowSize, var1);
   }

   void setShadowSize(float var1) {
      this.setShadowSize(var1, this.mRawMaxShadowSize);
   }

   interface RoundRectHelper {
      void drawRoundRect(Canvas var1, RectF var2, float var3, Paint var4);
   }
}
