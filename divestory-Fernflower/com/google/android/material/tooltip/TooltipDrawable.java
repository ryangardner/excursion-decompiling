package com.google.android.material.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;

public class TooltipDrawable extends MaterialShapeDrawable implements TextDrawableHelper.TextDrawableDelegate {
   private static final int DEFAULT_STYLE;
   private static final int DEFAULT_THEME_ATTR;
   private int arrowSize;
   private final OnLayoutChangeListener attachedViewLayoutChangeListener = new OnLayoutChangeListener() {
      public void onLayoutChange(View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
         TooltipDrawable.this.updateLocationOnScreen(var1);
      }
   };
   private final Context context;
   private final Rect displayFrame = new Rect();
   private final FontMetrics fontMetrics = new FontMetrics();
   private int layoutMargin;
   private int locationOnScreenX;
   private int minHeight;
   private int minWidth;
   private int padding;
   private CharSequence text;
   private final TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);

   static {
      DEFAULT_STYLE = R.style.Widget_MaterialComponents_Tooltip;
      DEFAULT_THEME_ATTR = R.attr.tooltipStyle;
   }

   private TooltipDrawable(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.context = var1;
      this.textDrawableHelper.getTextPaint().density = var1.getResources().getDisplayMetrics().density;
      this.textDrawableHelper.getTextPaint().setTextAlign(Align.CENTER);
   }

   private float calculatePointerOffset() {
      int var1;
      float var2;
      if (this.displayFrame.right - this.getBounds().right - this.locationOnScreenX - this.layoutMargin < 0) {
         var1 = this.displayFrame.right - this.getBounds().right - this.locationOnScreenX - this.layoutMargin;
      } else {
         if (this.displayFrame.left - this.getBounds().left - this.locationOnScreenX + this.layoutMargin <= 0) {
            var2 = 0.0F;
            return var2;
         }

         var1 = this.displayFrame.left - this.getBounds().left - this.locationOnScreenX + this.layoutMargin;
      }

      var2 = (float)var1;
      return var2;
   }

   private float calculateTextCenterFromBaseline() {
      this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
      return (this.fontMetrics.descent + this.fontMetrics.ascent) / 2.0F;
   }

   private float calculateTextOriginAndAlignment(Rect var1) {
      return (float)var1.centerY() - this.calculateTextCenterFromBaseline();
   }

   public static TooltipDrawable create(Context var0) {
      return createFromAttributes(var0, (AttributeSet)null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
   }

   public static TooltipDrawable createFromAttributes(Context var0, AttributeSet var1) {
      return createFromAttributes(var0, var1, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
   }

   public static TooltipDrawable createFromAttributes(Context var0, AttributeSet var1, int var2, int var3) {
      TooltipDrawable var4 = new TooltipDrawable(var0, var1, var2, var3);
      var4.loadFromAttributes(var1, var2, var3);
      return var4;
   }

   private EdgeTreatment createMarkerEdge() {
      float var1 = -this.calculatePointerOffset();
      float var2 = (float)((double)this.getBounds().width() - (double)this.arrowSize * Math.sqrt(2.0D)) / 2.0F;
      var2 = Math.min(Math.max(var1, -var2), var2);
      return new OffsetEdgeTreatment(new MarkerEdgeTreatment((float)this.arrowSize), var2);
   }

   private void drawText(Canvas var1) {
      if (this.text != null) {
         Rect var2 = this.getBounds();
         int var3 = (int)this.calculateTextOriginAndAlignment(var2);
         if (this.textDrawableHelper.getTextAppearance() != null) {
            this.textDrawableHelper.getTextPaint().drawableState = this.getState();
            this.textDrawableHelper.updateTextPaintDrawState(this.context);
         }

         CharSequence var4 = this.text;
         var1.drawText(var4, 0, var4.length(), (float)var2.centerX(), (float)var3, this.textDrawableHelper.getTextPaint());
      }
   }

   private float getTextWidth() {
      CharSequence var1 = this.text;
      return var1 == null ? 0.0F : this.textDrawableHelper.getTextWidth(var1.toString());
   }

   private void loadFromAttributes(AttributeSet var1, int var2, int var3) {
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(this.context, var1, R.styleable.Tooltip, var2, var3);
      this.arrowSize = this.context.getResources().getDimensionPixelSize(R.dimen.mtrl_tooltip_arrowSize);
      this.setShapeAppearanceModel(this.getShapeAppearanceModel().toBuilder().setBottomEdge(this.createMarkerEdge()).build());
      this.setText(var4.getText(R.styleable.Tooltip_android_text));
      this.setTextAppearance(MaterialResources.getTextAppearance(this.context, var4, R.styleable.Tooltip_android_textAppearance));
      var2 = MaterialColors.getColor(this.context, R.attr.colorOnBackground, TooltipDrawable.class.getCanonicalName());
      var2 = MaterialColors.layer(ColorUtils.setAlphaComponent(MaterialColors.getColor(this.context, 16842801, TooltipDrawable.class.getCanonicalName()), 229), ColorUtils.setAlphaComponent(var2, 153));
      this.setFillColor(ColorStateList.valueOf(var4.getColor(R.styleable.Tooltip_backgroundTint, var2)));
      this.setStrokeColor(ColorStateList.valueOf(MaterialColors.getColor(this.context, R.attr.colorSurface, TooltipDrawable.class.getCanonicalName())));
      this.padding = var4.getDimensionPixelSize(R.styleable.Tooltip_android_padding, 0);
      this.minWidth = var4.getDimensionPixelSize(R.styleable.Tooltip_android_minWidth, 0);
      this.minHeight = var4.getDimensionPixelSize(R.styleable.Tooltip_android_minHeight, 0);
      this.layoutMargin = var4.getDimensionPixelSize(R.styleable.Tooltip_android_layout_margin, 0);
      var4.recycle();
   }

   private void updateLocationOnScreen(View var1) {
      int[] var2 = new int[2];
      var1.getLocationOnScreen(var2);
      this.locationOnScreenX = var2[0];
      var1.getWindowVisibleDisplayFrame(this.displayFrame);
   }

   public void detachView(View var1) {
      if (var1 != null) {
         var1.removeOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
      }
   }

   public void draw(Canvas var1) {
      var1.save();
      var1.translate(this.calculatePointerOffset(), (float)(-((double)this.arrowSize * Math.sqrt(2.0D) - (double)this.arrowSize)));
      super.draw(var1);
      this.drawText(var1);
      var1.restore();
   }

   public int getIntrinsicHeight() {
      return (int)Math.max(this.textDrawableHelper.getTextPaint().getTextSize(), (float)this.minHeight);
   }

   public int getIntrinsicWidth() {
      return (int)Math.max((float)(this.padding * 2) + this.getTextWidth(), (float)this.minWidth);
   }

   public int getLayoutMargin() {
      return this.layoutMargin;
   }

   public int getMinHeight() {
      return this.minHeight;
   }

   public int getMinWidth() {
      return this.minWidth;
   }

   public CharSequence getText() {
      return this.text;
   }

   public TextAppearance getTextAppearance() {
      return this.textDrawableHelper.getTextAppearance();
   }

   public int getTextPadding() {
      return this.padding;
   }

   protected void onBoundsChange(Rect var1) {
      super.onBoundsChange(var1);
      this.setShapeAppearanceModel(this.getShapeAppearanceModel().toBuilder().setBottomEdge(this.createMarkerEdge()).build());
   }

   public boolean onStateChange(int[] var1) {
      return super.onStateChange(var1);
   }

   public void onTextSizeChange() {
      this.invalidateSelf();
   }

   public void setLayoutMargin(int var1) {
      this.layoutMargin = var1;
      this.invalidateSelf();
   }

   public void setMinHeight(int var1) {
      this.minHeight = var1;
      this.invalidateSelf();
   }

   public void setMinWidth(int var1) {
      this.minWidth = var1;
      this.invalidateSelf();
   }

   public void setRelativeToView(View var1) {
      if (var1 != null) {
         this.updateLocationOnScreen(var1);
         var1.addOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
      }
   }

   public void setText(CharSequence var1) {
      if (!TextUtils.equals(this.text, var1)) {
         this.text = var1;
         this.textDrawableHelper.setTextWidthDirty(true);
         this.invalidateSelf();
      }

   }

   public void setTextAppearance(TextAppearance var1) {
      this.textDrawableHelper.setTextAppearance(var1, this.context);
   }

   public void setTextAppearanceResource(int var1) {
      this.setTextAppearance(new TextAppearance(this.context, var1));
   }

   public void setTextPadding(int var1) {
      this.padding = var1;
      this.invalidateSelf();
   }

   public void setTextResource(int var1) {
      this.setText(this.context.getResources().getString(var1));
   }
}
