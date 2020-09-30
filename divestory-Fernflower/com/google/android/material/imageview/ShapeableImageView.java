package com.google.android.material.imageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class ShapeableImageView extends AppCompatImageView implements Shapeable {
   private static final int DEF_STYLE_RES;
   private final Paint borderPaint;
   private final Paint clearPaint;
   private final RectF destination;
   private Path maskPath;
   private final RectF maskRect;
   private final Path path;
   private final ShapeAppearancePathProvider pathProvider;
   private final MaterialShapeDrawable shadowDrawable;
   private ShapeAppearanceModel shapeAppearanceModel;
   private ColorStateList strokeColor;
   private float strokeWidth;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_ShapeableImageView;
   }

   public ShapeableImageView(Context var1) {
      this(var1, (AttributeSet)null, 0);
   }

   public ShapeableImageView(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public ShapeableImageView(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.pathProvider = new ShapeAppearancePathProvider();
      this.path = new Path();
      var1 = this.getContext();
      Paint var4 = new Paint();
      this.clearPaint = var4;
      var4.setAntiAlias(true);
      this.clearPaint.setColor(-1);
      this.clearPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
      this.destination = new RectF();
      this.maskRect = new RectF();
      this.maskPath = new Path();
      TypedArray var5 = var1.obtainStyledAttributes(var2, R.styleable.ShapeableImageView, var3, DEF_STYLE_RES);
      this.strokeColor = MaterialResources.getColorStateList(var1, var5, R.styleable.ShapeableImageView_strokeColor);
      this.strokeWidth = (float)var5.getDimensionPixelSize(R.styleable.ShapeableImageView_strokeWidth, 0);
      var4 = new Paint();
      this.borderPaint = var4;
      var4.setStyle(Style.STROKE);
      this.borderPaint.setAntiAlias(true);
      this.shapeAppearanceModel = ShapeAppearanceModel.builder(var1, var2, var3, DEF_STYLE_RES).build();
      this.shadowDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
      if (VERSION.SDK_INT >= 21) {
         this.setOutlineProvider(new ShapeableImageView.OutlineProvider());
      }

   }

   private void drawStroke(Canvas var1) {
      if (this.strokeColor != null) {
         this.borderPaint.setStrokeWidth(this.strokeWidth);
         int var2 = this.strokeColor.getColorForState(this.getDrawableState(), this.strokeColor.getDefaultColor());
         if (this.strokeWidth > 0.0F && var2 != 0) {
            this.borderPaint.setColor(var2);
            var1.drawPath(this.path, this.borderPaint);
         }

      }
   }

   private void updateShapeMask(int var1, int var2) {
      this.destination.set((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(var1 - this.getPaddingRight()), (float)(var2 - this.getPaddingBottom()));
      this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0F, this.destination, this.path);
      this.maskPath.rewind();
      this.maskPath.addPath(this.path);
      this.maskRect.set(0.0F, 0.0F, (float)var1, (float)var2);
      this.maskPath.addRect(this.maskRect, Direction.CCW);
   }

   public ShapeAppearanceModel getShapeAppearanceModel() {
      return this.shapeAppearanceModel;
   }

   public ColorStateList getStrokeColor() {
      return this.strokeColor;
   }

   public float getStrokeWidth() {
      return this.strokeWidth;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.setLayerType(2, (Paint)null);
   }

   protected void onDetachedFromWindow() {
      this.setLayerType(0, (Paint)null);
      super.onDetachedFromWindow();
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      var1.drawPath(this.maskPath, this.clearPaint);
      this.drawStroke(var1);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      this.updateShapeMask(var1, var2);
   }

   public void setShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.shapeAppearanceModel = var1;
      this.shadowDrawable.setShapeAppearanceModel(var1);
      this.updateShapeMask(this.getWidth(), this.getHeight());
      this.invalidate();
   }

   public void setStrokeColor(ColorStateList var1) {
      this.strokeColor = var1;
      this.invalidate();
   }

   public void setStrokeColorResource(int var1) {
      this.setStrokeColor(AppCompatResources.getColorStateList(this.getContext(), var1));
   }

   public void setStrokeWidth(float var1) {
      if (this.strokeWidth != var1) {
         this.strokeWidth = var1;
         this.invalidate();
      }

   }

   public void setStrokeWidthResource(int var1) {
      this.setStrokeWidth((float)this.getResources().getDimensionPixelSize(var1));
   }

   class OutlineProvider extends ViewOutlineProvider {
      private final Rect rect = new Rect();

      public void getOutline(View var1, Outline var2) {
         if (ShapeableImageView.this.shapeAppearanceModel != null) {
            ShapeableImageView.this.destination.round(this.rect);
            ShapeableImageView.this.shadowDrawable.setBounds(this.rect);
            ShapeableImageView.this.shadowDrawable.getOutline(var2);
         }
      }
   }
}
