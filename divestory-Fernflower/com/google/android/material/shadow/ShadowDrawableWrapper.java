package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
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
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

@Deprecated
public class ShadowDrawableWrapper extends DrawableWrapper {
   static final double COS_45 = Math.cos(Math.toRadians(45.0D));
   static final float SHADOW_BOTTOM_SCALE = 1.0F;
   static final float SHADOW_HORIZ_SCALE = 0.5F;
   static final float SHADOW_MULTIPLIER = 1.5F;
   static final float SHADOW_TOP_SCALE = 0.25F;
   private boolean addPaddingForCorners = true;
   final RectF contentBounds;
   float cornerRadius;
   final Paint cornerShadowPaint;
   Path cornerShadowPath;
   private boolean dirty = true;
   final Paint edgeShadowPaint;
   float maxShadowSize;
   private boolean printedShadowClipWarning = false;
   float rawMaxShadowSize;
   float rawShadowSize;
   private float rotation;
   private final int shadowEndColor;
   private final int shadowMiddleColor;
   float shadowSize;
   private final int shadowStartColor;

   public ShadowDrawableWrapper(Context var1, Drawable var2, float var3, float var4, float var5) {
      super(var2);
      this.shadowStartColor = ContextCompat.getColor(var1, R.color.design_fab_shadow_start_color);
      this.shadowMiddleColor = ContextCompat.getColor(var1, R.color.design_fab_shadow_mid_color);
      this.shadowEndColor = ContextCompat.getColor(var1, R.color.design_fab_shadow_end_color);
      Paint var6 = new Paint(5);
      this.cornerShadowPaint = var6;
      var6.setStyle(Style.FILL);
      this.cornerRadius = (float)Math.round(var3);
      this.contentBounds = new RectF();
      var6 = new Paint(this.cornerShadowPaint);
      this.edgeShadowPaint = var6;
      var6.setAntiAlias(false);
      this.setShadowSize(var4, var5);
   }

   private void buildComponents(Rect var1) {
      float var2 = this.rawMaxShadowSize * 1.5F;
      this.contentBounds.set((float)var1.left + this.rawMaxShadowSize, (float)var1.top + var2, (float)var1.right - this.rawMaxShadowSize, (float)var1.bottom - var2);
      this.getWrappedDrawable().setBounds((int)this.contentBounds.left, (int)this.contentBounds.top, (int)this.contentBounds.right, (int)this.contentBounds.bottom);
      this.buildShadowCorners();
   }

   private void buildShadowCorners() {
      float var1 = this.cornerRadius;
      RectF var2 = new RectF(-var1, -var1, var1, var1);
      RectF var3 = new RectF(var2);
      var1 = this.shadowSize;
      var3.inset(-var1, -var1);
      Path var4 = this.cornerShadowPath;
      if (var4 == null) {
         this.cornerShadowPath = new Path();
      } else {
         var4.reset();
      }

      this.cornerShadowPath.setFillType(FillType.EVEN_ODD);
      this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0F);
      this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0F);
      this.cornerShadowPath.arcTo(var3, 180.0F, 90.0F, false);
      this.cornerShadowPath.arcTo(var2, 270.0F, -90.0F, false);
      this.cornerShadowPath.close();
      var1 = -var3.top;
      float var5;
      int var8;
      int var9;
      int var10;
      if (var1 > 0.0F) {
         var5 = this.cornerRadius / var1;
         float var6 = (1.0F - var5) / 2.0F;
         Paint var7 = this.cornerShadowPaint;
         var8 = this.shadowStartColor;
         var9 = this.shadowMiddleColor;
         var10 = this.shadowEndColor;
         TileMode var12 = TileMode.CLAMP;
         var7.setShader(new RadialGradient(0.0F, 0.0F, var1, new int[]{0, var8, var9, var10}, new float[]{0.0F, var5, var6 + var5, 1.0F}, var12));
      }

      Paint var13 = this.edgeShadowPaint;
      var5 = var2.top;
      var1 = var3.top;
      var9 = this.shadowStartColor;
      var8 = this.shadowMiddleColor;
      var10 = this.shadowEndColor;
      TileMode var11 = TileMode.CLAMP;
      var13.setShader(new LinearGradient(0.0F, var5, 0.0F, var1, new int[]{var9, var8, var10}, new float[]{0.0F, 0.5F, 1.0F}, var11));
      this.edgeShadowPaint.setAntiAlias(false);
   }

   public static float calculateHorizontalPadding(float var0, float var1, boolean var2) {
      float var3 = var0;
      if (var2) {
         var3 = (float)((double)var0 + (1.0D - COS_45) * (double)var1);
      }

      return var3;
   }

   public static float calculateVerticalPadding(float var0, float var1, boolean var2) {
      return var2 ? (float)((double)(var0 * 1.5F) + (1.0D - COS_45) * (double)var1) : var0 * 1.5F;
   }

   private void drawShadow(Canvas var1) {
      int var2 = var1.save();
      var1.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
      float var3 = this.cornerRadius;
      float var4 = -var3 - this.shadowSize;
      float var5 = this.contentBounds.width();
      float var6 = var3 * 2.0F;
      boolean var7;
      if (var5 - var6 > 0.0F) {
         var7 = true;
      } else {
         var7 = false;
      }

      boolean var8;
      if (this.contentBounds.height() - var6 > 0.0F) {
         var8 = true;
      } else {
         var8 = false;
      }

      var5 = this.rawShadowSize;
      float var9 = var3 / (var5 - 0.5F * var5 + var3);
      float var10 = var3 / (var5 - 0.25F * var5 + var3);
      var5 = var3 / (var5 - var5 * 1.0F + var3);
      int var11 = var1.save();
      var1.translate(this.contentBounds.left + var3, this.contentBounds.top + var3);
      var1.scale(var9, var10);
      var1.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
      if (var7) {
         var1.scale(1.0F / var9, 1.0F);
         var1.drawRect(0.0F, var4, this.contentBounds.width() - var6, -this.cornerRadius, this.edgeShadowPaint);
      }

      var1.restoreToCount(var11);
      var11 = var1.save();
      var1.translate(this.contentBounds.right - var3, this.contentBounds.bottom - var3);
      var1.scale(var9, var5);
      var1.rotate(180.0F);
      var1.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
      if (var7) {
         var1.scale(1.0F / var9, 1.0F);
         var1.drawRect(0.0F, var4, this.contentBounds.width() - var6, -this.cornerRadius + this.shadowSize, this.edgeShadowPaint);
      }

      var1.restoreToCount(var11);
      int var12 = var1.save();
      var1.translate(this.contentBounds.left + var3, this.contentBounds.bottom - var3);
      var1.scale(var9, var5);
      var1.rotate(270.0F);
      var1.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
      if (var8) {
         var1.scale(1.0F / var5, 1.0F);
         var1.drawRect(0.0F, var4, this.contentBounds.height() - var6, -this.cornerRadius, this.edgeShadowPaint);
      }

      var1.restoreToCount(var12);
      var12 = var1.save();
      var1.translate(this.contentBounds.right - var3, this.contentBounds.top + var3);
      var1.scale(var9, var10);
      var1.rotate(90.0F);
      var1.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
      if (var8) {
         var1.scale(1.0F / var10, 1.0F);
         var1.drawRect(0.0F, var4, this.contentBounds.height() - var6, -this.cornerRadius, this.edgeShadowPaint);
      }

      var1.restoreToCount(var12);
      var1.restoreToCount(var2);
   }

   private static int toEven(float var0) {
      int var1 = Math.round(var0);
      int var2 = var1;
      if (var1 % 2 == 1) {
         var2 = var1 - 1;
      }

      return var2;
   }

   public void draw(Canvas var1) {
      if (this.dirty) {
         this.buildComponents(this.getBounds());
         this.dirty = false;
      }

      this.drawShadow(var1);
      super.draw(var1);
   }

   public float getCornerRadius() {
      return this.cornerRadius;
   }

   public float getMaxShadowSize() {
      return this.rawMaxShadowSize;
   }

   public float getMinHeight() {
      float var1 = this.rawMaxShadowSize;
      return Math.max(var1, this.cornerRadius + var1 * 1.5F / 2.0F) * 2.0F + this.rawMaxShadowSize * 1.5F * 2.0F;
   }

   public float getMinWidth() {
      float var1 = this.rawMaxShadowSize;
      return Math.max(var1, this.cornerRadius + var1 / 2.0F) * 2.0F + this.rawMaxShadowSize * 2.0F;
   }

   public int getOpacity() {
      return -3;
   }

   public boolean getPadding(Rect var1) {
      int var2 = (int)Math.ceil((double)calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
      int var3 = (int)Math.ceil((double)calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
      var1.set(var3, var2, var3, var2);
      return true;
   }

   public float getShadowSize() {
      return this.rawShadowSize;
   }

   protected void onBoundsChange(Rect var1) {
      this.dirty = true;
   }

   public void setAddPaddingForCorners(boolean var1) {
      this.addPaddingForCorners = var1;
      this.invalidateSelf();
   }

   public void setAlpha(int var1) {
      super.setAlpha(var1);
      this.cornerShadowPaint.setAlpha(var1);
      this.edgeShadowPaint.setAlpha(var1);
   }

   public void setCornerRadius(float var1) {
      var1 = (float)Math.round(var1);
      if (this.cornerRadius != var1) {
         this.cornerRadius = var1;
         this.dirty = true;
         this.invalidateSelf();
      }
   }

   public void setMaxShadowSize(float var1) {
      this.setShadowSize(this.rawShadowSize, var1);
   }

   public final void setRotation(float var1) {
      if (this.rotation != var1) {
         this.rotation = var1;
         this.invalidateSelf();
      }

   }

   public void setShadowSize(float var1) {
      this.setShadowSize(var1, this.rawMaxShadowSize);
   }

   public void setShadowSize(float var1, float var2) {
      if (var1 >= 0.0F && var2 >= 0.0F) {
         float var3 = (float)toEven(var1);
         var2 = (float)toEven(var2);
         var1 = var3;
         if (var3 > var2) {
            if (!this.printedShadowClipWarning) {
               this.printedShadowClipWarning = true;
            }

            var1 = var2;
         }

         if (this.rawShadowSize != var1 || this.rawMaxShadowSize != var2) {
            this.rawShadowSize = var1;
            this.rawMaxShadowSize = var2;
            this.shadowSize = (float)Math.round(var1 * 1.5F);
            this.maxShadowSize = var2;
            this.dirty = true;
            this.invalidateSelf();
         }
      } else {
         throw new IllegalArgumentException("invalid shadow size");
      }
   }
}
