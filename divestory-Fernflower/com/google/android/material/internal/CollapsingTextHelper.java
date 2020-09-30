package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;

public final class CollapsingTextHelper {
   private static final boolean DEBUG_DRAW = false;
   private static final Paint DEBUG_DRAW_PAINT;
   private static final String ELLIPSIS_NORMAL = "…";
   private static final String TAG = "CollapsingTextHelper";
   private static final boolean USE_SCALING_TEXTURE;
   private boolean boundsChanged;
   private final Rect collapsedBounds;
   private float collapsedDrawX;
   private float collapsedDrawY;
   private CancelableFontCallback collapsedFontCallback;
   private ColorStateList collapsedShadowColor;
   private float collapsedShadowDx;
   private float collapsedShadowDy;
   private float collapsedShadowRadius;
   private float collapsedTextBlend;
   private ColorStateList collapsedTextColor;
   private int collapsedTextGravity = 16;
   private float collapsedTextSize = 15.0F;
   private Typeface collapsedTypeface;
   private final RectF currentBounds;
   private float currentDrawX;
   private float currentDrawY;
   private float currentTextSize;
   private Typeface currentTypeface;
   private boolean drawTitle;
   private final Rect expandedBounds;
   private float expandedDrawX;
   private float expandedDrawY;
   private float expandedFirstLineDrawX;
   private CancelableFontCallback expandedFontCallback;
   private float expandedFraction;
   private ColorStateList expandedShadowColor;
   private float expandedShadowDx;
   private float expandedShadowDy;
   private float expandedShadowRadius;
   private float expandedTextBlend;
   private ColorStateList expandedTextColor;
   private int expandedTextGravity = 16;
   private float expandedTextSize = 15.0F;
   private Bitmap expandedTitleTexture;
   private Typeface expandedTypeface;
   private boolean isRtl;
   private int maxLines = 1;
   private TimeInterpolator positionInterpolator;
   private float scale;
   private int[] state;
   private CharSequence text;
   private StaticLayout textLayout;
   private final TextPaint textPaint;
   private TimeInterpolator textSizeInterpolator;
   private CharSequence textToDraw;
   private CharSequence textToDrawCollapsed;
   private Paint texturePaint;
   private final TextPaint tmpPaint;
   private boolean useTexture;
   private final View view;

   static {
      boolean var0;
      if (VERSION.SDK_INT < 18) {
         var0 = true;
      } else {
         var0 = false;
      }

      USE_SCALING_TEXTURE = var0;
      DEBUG_DRAW_PAINT = null;
      if (false) {
         throw new NullPointerException();
      }
   }

   public CollapsingTextHelper(View var1) {
      this.view = var1;
      this.textPaint = new TextPaint(129);
      this.tmpPaint = new TextPaint(this.textPaint);
      this.collapsedBounds = new Rect();
      this.expandedBounds = new Rect();
      this.currentBounds = new RectF();
   }

   private static int blendColors(int var0, int var1, float var2) {
      float var3 = 1.0F - var2;
      float var4 = (float)Color.alpha(var0);
      float var5 = (float)Color.alpha(var1);
      float var6 = (float)Color.red(var0);
      float var7 = (float)Color.red(var1);
      float var8 = (float)Color.green(var0);
      float var9 = (float)Color.green(var1);
      float var10 = (float)Color.blue(var0);
      float var11 = (float)Color.blue(var1);
      return Color.argb((int)(var4 * var3 + var5 * var2), (int)(var6 * var3 + var7 * var2), (int)(var8 * var3 + var9 * var2), (int)(var10 * var3 + var11 * var2));
   }

   private void calculateBaseOffsets() {
      float var1 = this.currentTextSize;
      this.calculateUsingTextSize(this.collapsedTextSize);
      CharSequence var2 = this.textToDraw;
      StaticLayout var3;
      if (var2 != null) {
         var3 = this.textLayout;
         if (var3 != null) {
            this.textToDrawCollapsed = TextUtils.ellipsize(var2, this.textPaint, (float)var3.getWidth(), TruncateAt.END);
         }
      }

      CharSequence var10 = this.textToDrawCollapsed;
      float var4 = 0.0F;
      float var5;
      if (var10 != null) {
         var5 = this.textPaint.measureText(var10, 0, var10.length());
      } else {
         var5 = 0.0F;
      }

      int var6 = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl);
      int var7 = var6 & 112;
      float var8;
      if (var7 != 48) {
         if (var7 != 80) {
            var8 = (this.textPaint.descent() - this.textPaint.ascent()) / 2.0F;
            this.collapsedDrawY = (float)this.collapsedBounds.centerY() - var8;
         } else {
            this.collapsedDrawY = (float)this.collapsedBounds.bottom + this.textPaint.ascent();
         }
      } else {
         this.collapsedDrawY = (float)this.collapsedBounds.top;
      }

      var6 &= 8388615;
      if (var6 != 1) {
         if (var6 != 5) {
            this.collapsedDrawX = (float)this.collapsedBounds.left;
         } else {
            this.collapsedDrawX = (float)this.collapsedBounds.right - var5;
         }
      } else {
         this.collapsedDrawX = (float)this.collapsedBounds.centerX() - var5 / 2.0F;
      }

      this.calculateUsingTextSize(this.expandedTextSize);
      var3 = this.textLayout;
      if (var3 != null) {
         var8 = (float)var3.getHeight();
      } else {
         var8 = 0.0F;
      }

      var10 = this.textToDraw;
      if (var10 != null) {
         var5 = this.textPaint.measureText(var10, 0, var10.length());
      } else {
         var5 = 0.0F;
      }

      var3 = this.textLayout;
      float var9 = var5;
      if (var3 != null) {
         var9 = var5;
         if (this.maxLines > 1) {
            var9 = var5;
            if (!this.isRtl) {
               var9 = (float)var3.getWidth();
            }
         }
      }

      var3 = this.textLayout;
      var5 = var4;
      if (var3 != null) {
         var5 = var3.getLineLeft(0);
      }

      this.expandedFirstLineDrawX = var5;
      var7 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl);
      var6 = var7 & 112;
      if (var6 != 48) {
         if (var6 != 80) {
            var5 = var8 / 2.0F;
            this.expandedDrawY = (float)this.expandedBounds.centerY() - var5;
         } else {
            this.expandedDrawY = (float)this.expandedBounds.bottom - var8 + this.textPaint.descent();
         }
      } else {
         this.expandedDrawY = (float)this.expandedBounds.top;
      }

      var6 = var7 & 8388615;
      if (var6 != 1) {
         if (var6 != 5) {
            this.expandedDrawX = (float)this.expandedBounds.left;
         } else {
            this.expandedDrawX = (float)this.expandedBounds.right - var9;
         }
      } else {
         this.expandedDrawX = (float)this.expandedBounds.centerX() - var9 / 2.0F;
      }

      this.clearTexture();
      this.setInterpolatedTextSize(var1);
   }

   private void calculateCurrentOffsets() {
      this.calculateOffsets(this.expandedFraction);
   }

   private boolean calculateIsRtl(CharSequence var1) {
      TextDirectionHeuristicCompat var2;
      if (this.isDefaultIsRtl()) {
         var2 = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
      } else {
         var2 = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
      }

      return var2.isRtl((CharSequence)var1, 0, var1.length());
   }

   private void calculateOffsets(float var1) {
      this.interpolateBounds(var1);
      this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, var1, this.positionInterpolator);
      this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, var1, this.positionInterpolator);
      this.setInterpolatedTextSize(lerp(this.expandedTextSize, this.collapsedTextSize, var1, this.textSizeInterpolator));
      this.setCollapsedTextBlend(1.0F - lerp(0.0F, 1.0F, 1.0F - var1, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
      this.setExpandedTextBlend(lerp(1.0F, 0.0F, var1, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
      if (this.collapsedTextColor != this.expandedTextColor) {
         this.textPaint.setColor(blendColors(this.getCurrentExpandedTextColor(), this.getCurrentCollapsedTextColor(), var1));
      } else {
         this.textPaint.setColor(this.getCurrentCollapsedTextColor());
      }

      this.textPaint.setShadowLayer(lerp(this.expandedShadowRadius, this.collapsedShadowRadius, var1, (TimeInterpolator)null), lerp(this.expandedShadowDx, this.collapsedShadowDx, var1, (TimeInterpolator)null), lerp(this.expandedShadowDy, this.collapsedShadowDy, var1, (TimeInterpolator)null), blendColors(this.getCurrentColor(this.expandedShadowColor), this.getCurrentColor(this.collapsedShadowColor), var1));
      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   private void calculateUsingTextSize(float var1) {
      if (this.text != null) {
         float var2 = (float)this.collapsedBounds.width();
         float var3 = (float)this.expandedBounds.width();
         boolean var4 = isClose(var1, this.collapsedTextSize);
         boolean var5 = false;
         byte var6 = 1;
         float var7;
         Typeface var8;
         Typeface var9;
         boolean var10;
         if (var4) {
            var7 = this.collapsedTextSize;
            this.scale = 1.0F;
            var8 = this.currentTypeface;
            var9 = this.collapsedTypeface;
            if (var8 != var9) {
               this.currentTypeface = var9;
               var10 = true;
               var1 = var2;
            } else {
               var10 = false;
               var1 = var2;
            }
         } else {
            var7 = this.expandedTextSize;
            var9 = this.currentTypeface;
            var8 = this.expandedTypeface;
            if (var9 != var8) {
               this.currentTypeface = var8;
               var10 = true;
            } else {
               var10 = false;
            }

            if (isClose(var1, this.expandedTextSize)) {
               this.scale = 1.0F;
            } else {
               this.scale = var1 / this.expandedTextSize;
            }

            var1 = this.collapsedTextSize / this.expandedTextSize;
            if (var3 * var1 > var2) {
               var1 = Math.min(var2 / var1, var3);
            } else {
               var1 = var3;
            }
         }

         boolean var11 = var10;
         if (var1 > 0.0F) {
            if (this.currentTextSize == var7 && !this.boundsChanged && !var10) {
               var10 = false;
            } else {
               var10 = true;
            }

            this.currentTextSize = var7;
            this.boundsChanged = false;
            var11 = var10;
         }

         if (this.textToDraw == null || var11) {
            this.textPaint.setTextSize(this.currentTextSize);
            this.textPaint.setTypeface(this.currentTypeface);
            TextPaint var12 = this.textPaint;
            if (this.scale != 1.0F) {
               var5 = true;
            }

            var12.setLinearText(var5);
            this.isRtl = this.calculateIsRtl(this.text);
            int var14 = var6;
            if (this.shouldDrawMultiline()) {
               var14 = this.maxLines;
            }

            StaticLayout var13 = this.createStaticLayout(var14, var1, this.isRtl);
            this.textLayout = var13;
            this.textToDraw = var13.getText();
         }

      }
   }

   private void clearTexture() {
      Bitmap var1 = this.expandedTitleTexture;
      if (var1 != null) {
         var1.recycle();
         this.expandedTitleTexture = null;
      }

   }

   private StaticLayout createStaticLayout(int var1, float var2, boolean var3) {
      StaticLayout var4;
      try {
         var4 = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int)var2).setEllipsize(TruncateAt.END).setIsRtl(var3).setAlignment(Alignment.ALIGN_NORMAL).setIncludePad(false).setMaxLines(var1).build();
      } catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException var5) {
         Log.e("CollapsingTextHelper", var5.getCause().getMessage(), var5);
         var4 = null;
      }

      return (StaticLayout)Preconditions.checkNotNull(var4);
   }

   private void drawMultinlineTransition(Canvas var1, float var2, float var3) {
      int var4 = this.textPaint.getAlpha();
      var1.translate(var2, var3);
      TextPaint var5 = this.textPaint;
      var3 = this.expandedTextBlend;
      var2 = (float)var4;
      var5.setAlpha((int)(var3 * var2));
      this.textLayout.draw(var1);
      this.textPaint.setAlpha((int)(this.collapsedTextBlend * var2));
      int var6 = this.textLayout.getLineBaseline(0);
      CharSequence var9 = this.textToDrawCollapsed;
      int var7 = var9.length();
      var2 = (float)var6;
      var1.drawText(var9, 0, var7, 0.0F, var2, this.textPaint);
      String var8 = this.textToDrawCollapsed.toString().trim();
      String var10 = var8;
      if (var8.endsWith("…")) {
         var10 = var8.substring(0, var8.length() - 1);
      }

      this.textPaint.setAlpha(var4);
      var1.drawText(var10, 0, Math.min(this.textLayout.getLineEnd(0), var10.length()), 0.0F, var2, this.textPaint);
   }

   private void ensureExpandedTexture() {
      if (this.expandedTitleTexture == null && !this.expandedBounds.isEmpty() && !TextUtils.isEmpty(this.textToDraw)) {
         this.calculateOffsets(0.0F);
         int var1 = this.textLayout.getWidth();
         int var2 = this.textLayout.getHeight();
         if (var1 > 0 && var2 > 0) {
            this.expandedTitleTexture = Bitmap.createBitmap(var1, var2, Config.ARGB_8888);
            Canvas var3 = new Canvas(this.expandedTitleTexture);
            this.textLayout.draw(var3);
            if (this.texturePaint == null) {
               this.texturePaint = new Paint(3);
            }
         }
      }

   }

   private float getCollapsedTextLeftBound(int var1, int var2) {
      if (var2 != 17 && (var2 & 7) != 1) {
         float var3;
         if ((var2 & 8388613) != 8388613 && (var2 & 5) != 5) {
            if (this.isRtl) {
               var3 = (float)this.collapsedBounds.right - this.calculateCollapsedTextWidth();
            } else {
               var3 = (float)this.collapsedBounds.left;
            }

            return var3;
         } else {
            if (this.isRtl) {
               var3 = (float)this.collapsedBounds.left;
            } else {
               var3 = (float)this.collapsedBounds.right - this.calculateCollapsedTextWidth();
            }

            return var3;
         }
      } else {
         return (float)var1 / 2.0F - this.calculateCollapsedTextWidth() / 2.0F;
      }
   }

   private float getCollapsedTextRightBound(RectF var1, int var2, int var3) {
      if (var3 != 17 && (var3 & 7) != 1) {
         float var4;
         if ((var3 & 8388613) != 8388613 && (var3 & 5) != 5) {
            if (this.isRtl) {
               var4 = (float)this.collapsedBounds.right;
            } else {
               var4 = var1.left + this.calculateCollapsedTextWidth();
            }

            return var4;
         } else {
            if (this.isRtl) {
               var4 = var1.left + this.calculateCollapsedTextWidth();
            } else {
               var4 = (float)this.collapsedBounds.right;
            }

            return var4;
         }
      } else {
         return (float)var2 / 2.0F + this.calculateCollapsedTextWidth() / 2.0F;
      }
   }

   private int getCurrentColor(ColorStateList var1) {
      if (var1 == null) {
         return 0;
      } else {
         int[] var2 = this.state;
         return var2 != null ? var1.getColorForState(var2, 0) : var1.getDefaultColor();
      }
   }

   private int getCurrentExpandedTextColor() {
      return this.getCurrentColor(this.expandedTextColor);
   }

   private void getTextPaintCollapsed(TextPaint var1) {
      var1.setTextSize(this.collapsedTextSize);
      var1.setTypeface(this.collapsedTypeface);
   }

   private void getTextPaintExpanded(TextPaint var1) {
      var1.setTextSize(this.expandedTextSize);
      var1.setTypeface(this.expandedTypeface);
   }

   private void interpolateBounds(float var1) {
      this.currentBounds.left = lerp((float)this.expandedBounds.left, (float)this.collapsedBounds.left, var1, this.positionInterpolator);
      this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, var1, this.positionInterpolator);
      this.currentBounds.right = lerp((float)this.expandedBounds.right, (float)this.collapsedBounds.right, var1, this.positionInterpolator);
      this.currentBounds.bottom = lerp((float)this.expandedBounds.bottom, (float)this.collapsedBounds.bottom, var1, this.positionInterpolator);
   }

   private static boolean isClose(float var0, float var1) {
      boolean var2;
      if (Math.abs(var0 - var1) < 0.001F) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isDefaultIsRtl() {
      int var1 = ViewCompat.getLayoutDirection(this.view);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   private static float lerp(float var0, float var1, float var2, TimeInterpolator var3) {
      float var4 = var2;
      if (var3 != null) {
         var4 = var3.getInterpolation(var2);
      }

      return AnimationUtils.lerp(var0, var1, var4);
   }

   private static boolean rectEquals(Rect var0, int var1, int var2, int var3, int var4) {
      boolean var5;
      if (var0.left == var1 && var0.top == var2 && var0.right == var3 && var0.bottom == var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   private void setCollapsedTextBlend(float var1) {
      this.collapsedTextBlend = var1;
      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   private boolean setCollapsedTypefaceInternal(Typeface var1) {
      CancelableFontCallback var2 = this.collapsedFontCallback;
      if (var2 != null) {
         var2.cancel();
      }

      if (this.collapsedTypeface != var1) {
         this.collapsedTypeface = var1;
         return true;
      } else {
         return false;
      }
   }

   private void setExpandedTextBlend(float var1) {
      this.expandedTextBlend = var1;
      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   private boolean setExpandedTypefaceInternal(Typeface var1) {
      CancelableFontCallback var2 = this.expandedFontCallback;
      if (var2 != null) {
         var2.cancel();
      }

      if (this.expandedTypeface != var1) {
         this.expandedTypeface = var1;
         return true;
      } else {
         return false;
      }
   }

   private void setInterpolatedTextSize(float var1) {
      this.calculateUsingTextSize(var1);
      boolean var2;
      if (USE_SCALING_TEXTURE && this.scale != 1.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.useTexture = var2;
      if (var2) {
         this.ensureExpandedTexture();
      }

      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   private boolean shouldDrawMultiline() {
      int var1 = this.maxLines;
      boolean var2 = true;
      if (var1 <= 1 || this.isRtl || this.useTexture) {
         var2 = false;
      }

      return var2;
   }

   public float calculateCollapsedTextWidth() {
      if (this.text == null) {
         return 0.0F;
      } else {
         this.getTextPaintCollapsed(this.tmpPaint);
         TextPaint var1 = this.tmpPaint;
         CharSequence var2 = this.text;
         return var1.measureText(var2, 0, var2.length());
      }
   }

   public void draw(Canvas var1) {
      int var2 = var1.save();
      if (this.textToDraw != null && this.drawTitle) {
         float var3 = this.currentDrawX;
         StaticLayout var4 = this.textLayout;
         boolean var5 = false;
         float var6 = var4.getLineLeft(0);
         float var7 = this.expandedFirstLineDrawX;
         this.textPaint.setTextSize(this.currentTextSize);
         float var8 = this.currentDrawX;
         float var9 = this.currentDrawY;
         boolean var10 = var5;
         if (this.useTexture) {
            var10 = var5;
            if (this.expandedTitleTexture != null) {
               var10 = true;
            }
         }

         float var11 = this.scale;
         if (var11 != 1.0F) {
            var1.scale(var11, var11, var8, var9);
         }

         if (var10) {
            var1.drawBitmap(this.expandedTitleTexture, var8, var9, this.texturePaint);
            var1.restoreToCount(var2);
            return;
         }

         if (this.shouldDrawMultiline()) {
            this.drawMultinlineTransition(var1, var3 + var6 - var7 * 2.0F, var9);
         } else {
            var1.translate(var8, var9);
            this.textLayout.draw(var1);
         }

         var1.restoreToCount(var2);
      }

   }

   public void getCollapsedTextActualBounds(RectF var1, int var2, int var3) {
      this.isRtl = this.calculateIsRtl(this.text);
      var1.left = this.getCollapsedTextLeftBound(var2, var3);
      var1.top = (float)this.collapsedBounds.top;
      var1.right = this.getCollapsedTextRightBound(var1, var2, var3);
      var1.bottom = (float)this.collapsedBounds.top + this.getCollapsedTextHeight();
   }

   public ColorStateList getCollapsedTextColor() {
      return this.collapsedTextColor;
   }

   public int getCollapsedTextGravity() {
      return this.collapsedTextGravity;
   }

   public float getCollapsedTextHeight() {
      this.getTextPaintCollapsed(this.tmpPaint);
      return -this.tmpPaint.ascent();
   }

   public float getCollapsedTextSize() {
      return this.collapsedTextSize;
   }

   public Typeface getCollapsedTypeface() {
      Typeface var1 = this.collapsedTypeface;
      if (var1 == null) {
         var1 = Typeface.DEFAULT;
      }

      return var1;
   }

   public int getCurrentCollapsedTextColor() {
      return this.getCurrentColor(this.collapsedTextColor);
   }

   public ColorStateList getExpandedTextColor() {
      return this.expandedTextColor;
   }

   public int getExpandedTextGravity() {
      return this.expandedTextGravity;
   }

   public float getExpandedTextHeight() {
      this.getTextPaintExpanded(this.tmpPaint);
      return -this.tmpPaint.ascent();
   }

   public float getExpandedTextSize() {
      return this.expandedTextSize;
   }

   public Typeface getExpandedTypeface() {
      Typeface var1 = this.expandedTypeface;
      if (var1 == null) {
         var1 = Typeface.DEFAULT;
      }

      return var1;
   }

   public float getExpansionFraction() {
      return this.expandedFraction;
   }

   public int getMaxLines() {
      return this.maxLines;
   }

   public CharSequence getText() {
      return this.text;
   }

   public final boolean isStateful() {
      ColorStateList var1 = this.collapsedTextColor;
      boolean var2;
      if (var1 == null || !var1.isStateful()) {
         var1 = this.expandedTextColor;
         if (var1 == null || !var1.isStateful()) {
            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   void onBoundsChanged() {
      boolean var1;
      if (this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.drawTitle = var1;
   }

   public void recalculate() {
      if (this.view.getHeight() > 0 && this.view.getWidth() > 0) {
         this.calculateBaseOffsets();
         this.calculateCurrentOffsets();
      }

   }

   public void setCollapsedBounds(int var1, int var2, int var3, int var4) {
      if (!rectEquals(this.collapsedBounds, var1, var2, var3, var4)) {
         this.collapsedBounds.set(var1, var2, var3, var4);
         this.boundsChanged = true;
         this.onBoundsChanged();
      }

   }

   public void setCollapsedBounds(Rect var1) {
      this.setCollapsedBounds(var1.left, var1.top, var1.right, var1.bottom);
   }

   public void setCollapsedTextAppearance(int var1) {
      TextAppearance var2 = new TextAppearance(this.view.getContext(), var1);
      if (var2.textColor != null) {
         this.collapsedTextColor = var2.textColor;
      }

      if (var2.textSize != 0.0F) {
         this.collapsedTextSize = var2.textSize;
      }

      if (var2.shadowColor != null) {
         this.collapsedShadowColor = var2.shadowColor;
      }

      this.collapsedShadowDx = var2.shadowDx;
      this.collapsedShadowDy = var2.shadowDy;
      this.collapsedShadowRadius = var2.shadowRadius;
      CancelableFontCallback var3 = this.collapsedFontCallback;
      if (var3 != null) {
         var3.cancel();
      }

      this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() {
         public void apply(Typeface var1) {
            CollapsingTextHelper.this.setCollapsedTypeface(var1);
         }
      }, var2.getFallbackFont());
      var2.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
      this.recalculate();
   }

   public void setCollapsedTextColor(ColorStateList var1) {
      if (this.collapsedTextColor != var1) {
         this.collapsedTextColor = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTextGravity(int var1) {
      if (this.collapsedTextGravity != var1) {
         this.collapsedTextGravity = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTextSize(float var1) {
      if (this.collapsedTextSize != var1) {
         this.collapsedTextSize = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTypeface(Typeface var1) {
      if (this.setCollapsedTypefaceInternal(var1)) {
         this.recalculate();
      }

   }

   public void setExpandedBounds(int var1, int var2, int var3, int var4) {
      if (!rectEquals(this.expandedBounds, var1, var2, var3, var4)) {
         this.expandedBounds.set(var1, var2, var3, var4);
         this.boundsChanged = true;
         this.onBoundsChanged();
      }

   }

   public void setExpandedBounds(Rect var1) {
      this.setExpandedBounds(var1.left, var1.top, var1.right, var1.bottom);
   }

   public void setExpandedTextAppearance(int var1) {
      TextAppearance var2 = new TextAppearance(this.view.getContext(), var1);
      if (var2.textColor != null) {
         this.expandedTextColor = var2.textColor;
      }

      if (var2.textSize != 0.0F) {
         this.expandedTextSize = var2.textSize;
      }

      if (var2.shadowColor != null) {
         this.expandedShadowColor = var2.shadowColor;
      }

      this.expandedShadowDx = var2.shadowDx;
      this.expandedShadowDy = var2.shadowDy;
      this.expandedShadowRadius = var2.shadowRadius;
      CancelableFontCallback var3 = this.expandedFontCallback;
      if (var3 != null) {
         var3.cancel();
      }

      this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() {
         public void apply(Typeface var1) {
            CollapsingTextHelper.this.setExpandedTypeface(var1);
         }
      }, var2.getFallbackFont());
      var2.getFontAsync(this.view.getContext(), this.expandedFontCallback);
      this.recalculate();
   }

   public void setExpandedTextColor(ColorStateList var1) {
      if (this.expandedTextColor != var1) {
         this.expandedTextColor = var1;
         this.recalculate();
      }

   }

   public void setExpandedTextGravity(int var1) {
      if (this.expandedTextGravity != var1) {
         this.expandedTextGravity = var1;
         this.recalculate();
      }

   }

   public void setExpandedTextSize(float var1) {
      if (this.expandedTextSize != var1) {
         this.expandedTextSize = var1;
         this.recalculate();
      }

   }

   public void setExpandedTypeface(Typeface var1) {
      if (this.setExpandedTypefaceInternal(var1)) {
         this.recalculate();
      }

   }

   public void setExpansionFraction(float var1) {
      var1 = MathUtils.clamp(var1, 0.0F, 1.0F);
      if (var1 != this.expandedFraction) {
         this.expandedFraction = var1;
         this.calculateCurrentOffsets();
      }

   }

   public void setMaxLines(int var1) {
      if (var1 != this.maxLines) {
         this.maxLines = var1;
         this.clearTexture();
         this.recalculate();
      }

   }

   public void setPositionInterpolator(TimeInterpolator var1) {
      this.positionInterpolator = var1;
      this.recalculate();
   }

   public final boolean setState(int[] var1) {
      this.state = var1;
      if (this.isStateful()) {
         this.recalculate();
         return true;
      } else {
         return false;
      }
   }

   public void setText(CharSequence var1) {
      if (var1 == null || !TextUtils.equals(this.text, var1)) {
         this.text = var1;
         this.textToDraw = null;
         this.clearTexture();
         this.recalculate();
      }

   }

   public void setTextSizeInterpolator(TimeInterpolator var1) {
      this.textSizeInterpolator = var1;
      this.recalculate();
   }

   public void setTypefaces(Typeface var1) {
      boolean var2 = this.setCollapsedTypefaceInternal(var1);
      boolean var3 = this.setExpandedTypefaceInternal(var1);
      if (var2 || var3) {
         this.recalculate();
      }

   }
}
