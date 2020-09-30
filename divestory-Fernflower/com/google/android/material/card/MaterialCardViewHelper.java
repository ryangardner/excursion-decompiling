package com.google.android.material.card;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

class MaterialCardViewHelper {
   private static final float CARD_VIEW_SHADOW_MULTIPLIER = 1.5F;
   private static final int CHECKED_ICON_LAYER_INDEX = 2;
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final double COS_45 = Math.cos(Math.toRadians(45.0D));
   private static final int DEFAULT_STROKE_VALUE = -1;
   private final MaterialShapeDrawable bgDrawable;
   private boolean checkable;
   private Drawable checkedIcon;
   private final int checkedIconMargin;
   private final int checkedIconSize;
   private ColorStateList checkedIconTint;
   private LayerDrawable clickableForegroundDrawable;
   private MaterialShapeDrawable compatRippleDrawable;
   private Drawable fgDrawable;
   private final MaterialShapeDrawable foregroundContentDrawable;
   private MaterialShapeDrawable foregroundShapeDrawable;
   private boolean isBackgroundOverwritten = false;
   private final MaterialCardView materialCardView;
   private ColorStateList rippleColor;
   private Drawable rippleDrawable;
   private ShapeAppearanceModel shapeAppearanceModel;
   private ColorStateList strokeColor;
   private int strokeWidth;
   private final Rect userContentPadding = new Rect();

   public MaterialCardViewHelper(MaterialCardView var1, AttributeSet var2, int var3, int var4) {
      this.materialCardView = var1;
      MaterialShapeDrawable var5 = new MaterialShapeDrawable(var1.getContext(), var2, var3, var4);
      this.bgDrawable = var5;
      var5.initializeElevationOverlay(var1.getContext());
      this.bgDrawable.setShadowColor(-12303292);
      ShapeAppearanceModel.Builder var6 = this.bgDrawable.getShapeAppearanceModel().toBuilder();
      TypedArray var8 = var1.getContext().obtainStyledAttributes(var2, R.styleable.CardView, var3, R.style.CardView);
      if (var8.hasValue(R.styleable.CardView_cardCornerRadius)) {
         var6.setAllCornerSizes(var8.getDimension(R.styleable.CardView_cardCornerRadius, 0.0F));
      }

      this.foregroundContentDrawable = new MaterialShapeDrawable();
      this.setShapeAppearanceModel(var6.build());
      Resources var7 = var1.getResources();
      this.checkedIconMargin = var7.getDimensionPixelSize(R.dimen.mtrl_card_checked_icon_margin);
      this.checkedIconSize = var7.getDimensionPixelSize(R.dimen.mtrl_card_checked_icon_size);
      var8.recycle();
   }

   private float calculateActualCornerPadding() {
      return Math.max(Math.max(this.calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopLeftCorner(), this.bgDrawable.getTopLeftCornerResolvedSize()), this.calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopRightCorner(), this.bgDrawable.getTopRightCornerResolvedSize())), Math.max(this.calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomRightCorner(), this.bgDrawable.getBottomRightCornerResolvedSize()), this.calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomLeftCorner(), this.bgDrawable.getBottomLeftCornerResolvedSize())));
   }

   private float calculateCornerPaddingForCornerTreatment(CornerTreatment var1, float var2) {
      if (var1 instanceof RoundedCornerTreatment) {
         return (float)((1.0D - COS_45) * (double)var2);
      } else {
         return var1 instanceof CutCornerTreatment ? var2 / 2.0F : 0.0F;
      }
   }

   private float calculateHorizontalBackgroundPadding() {
      float var1 = this.materialCardView.getMaxCardElevation();
      float var2;
      if (this.shouldAddCornerPaddingOutsideCardBackground()) {
         var2 = this.calculateActualCornerPadding();
      } else {
         var2 = 0.0F;
      }

      return var1 + var2;
   }

   private float calculateVerticalBackgroundPadding() {
      float var1 = this.materialCardView.getMaxCardElevation();
      float var2;
      if (this.shouldAddCornerPaddingOutsideCardBackground()) {
         var2 = this.calculateActualCornerPadding();
      } else {
         var2 = 0.0F;
      }

      return var1 * 1.5F + var2;
   }

   private boolean canClipToOutline() {
      boolean var1;
      if (VERSION.SDK_INT >= 21 && this.bgDrawable.isRoundRect()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private Drawable createCheckedIconLayer() {
      StateListDrawable var1 = new StateListDrawable();
      Drawable var2 = this.checkedIcon;
      if (var2 != null) {
         var1.addState(CHECKED_STATE_SET, var2);
      }

      return var1;
   }

   private Drawable createCompatRippleDrawable() {
      StateListDrawable var1 = new StateListDrawable();
      MaterialShapeDrawable var2 = this.createForegroundShapeDrawable();
      this.compatRippleDrawable = var2;
      var2.setFillColor(this.rippleColor);
      var2 = this.compatRippleDrawable;
      var1.addState(new int[]{16842919}, var2);
      return var1;
   }

   private Drawable createForegroundRippleDrawable() {
      if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
         this.foregroundShapeDrawable = this.createForegroundShapeDrawable();
         return new RippleDrawable(this.rippleColor, (Drawable)null, this.foregroundShapeDrawable);
      } else {
         return this.createCompatRippleDrawable();
      }
   }

   private MaterialShapeDrawable createForegroundShapeDrawable() {
      return new MaterialShapeDrawable(this.shapeAppearanceModel);
   }

   private Drawable getClickableForeground() {
      if (this.rippleDrawable == null) {
         this.rippleDrawable = this.createForegroundRippleDrawable();
      }

      if (this.clickableForegroundDrawable == null) {
         Drawable var1 = this.createCheckedIconLayer();
         LayerDrawable var2 = new LayerDrawable(new Drawable[]{this.rippleDrawable, this.foregroundContentDrawable, var1});
         this.clickableForegroundDrawable = var2;
         var2.setId(2, R.id.mtrl_card_checked_layer_id);
      }

      return this.clickableForegroundDrawable;
   }

   private float getParentCardViewCalculatedCornerPadding() {
      return !this.materialCardView.getPreventCornerOverlap() || VERSION.SDK_INT >= 21 && !this.materialCardView.getUseCompatPadding() ? 0.0F : (float)((1.0D - COS_45) * (double)this.materialCardView.getCardViewRadius());
   }

   private Drawable insetDrawable(Drawable var1) {
      boolean var2;
      if (VERSION.SDK_INT < 21) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3;
      int var4;
      if (!var2 && !this.materialCardView.getUseCompatPadding()) {
         var3 = 0;
         var4 = 0;
      } else {
         var4 = (int)Math.ceil((double)this.calculateVerticalBackgroundPadding());
         var3 = (int)Math.ceil((double)this.calculateHorizontalBackgroundPadding());
      }

      return new InsetDrawable(var1, var3, var4, var3, var4) {
         public int getMinimumHeight() {
            return -1;
         }

         public int getMinimumWidth() {
            return -1;
         }

         public boolean getPadding(Rect var1) {
            return false;
         }
      };
   }

   private boolean shouldAddCornerPaddingInsideCardBackground() {
      boolean var1;
      if (this.materialCardView.getPreventCornerOverlap() && !this.canClipToOutline()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean shouldAddCornerPaddingOutsideCardBackground() {
      boolean var1;
      if (this.materialCardView.getPreventCornerOverlap() && this.canClipToOutline() && this.materialCardView.getUseCompatPadding()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void updateInsetForeground(Drawable var1) {
      if (VERSION.SDK_INT >= 23 && this.materialCardView.getForeground() instanceof InsetDrawable) {
         ((InsetDrawable)this.materialCardView.getForeground()).setDrawable(var1);
      } else {
         this.materialCardView.setForeground(this.insetDrawable(var1));
      }

   }

   private void updateRippleColor() {
      if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
         Drawable var1 = this.rippleDrawable;
         if (var1 != null) {
            ((RippleDrawable)var1).setColor(this.rippleColor);
            return;
         }
      }

      MaterialShapeDrawable var2 = this.compatRippleDrawable;
      if (var2 != null) {
         var2.setFillColor(this.rippleColor);
      }

   }

   void forceRippleRedraw() {
      Drawable var1 = this.rippleDrawable;
      if (var1 != null) {
         Rect var3 = var1.getBounds();
         int var2 = var3.bottom;
         this.rippleDrawable.setBounds(var3.left, var3.top, var3.right, var2 - 1);
         this.rippleDrawable.setBounds(var3.left, var3.top, var3.right, var2);
      }

   }

   MaterialShapeDrawable getBackground() {
      return this.bgDrawable;
   }

   ColorStateList getCardBackgroundColor() {
      return this.bgDrawable.getFillColor();
   }

   ColorStateList getCardForegroundColor() {
      return this.foregroundContentDrawable.getFillColor();
   }

   Drawable getCheckedIcon() {
      return this.checkedIcon;
   }

   ColorStateList getCheckedIconTint() {
      return this.checkedIconTint;
   }

   float getCornerRadius() {
      return this.bgDrawable.getTopLeftCornerResolvedSize();
   }

   float getProgress() {
      return this.bgDrawable.getInterpolation();
   }

   ColorStateList getRippleColor() {
      return this.rippleColor;
   }

   ShapeAppearanceModel getShapeAppearanceModel() {
      return this.shapeAppearanceModel;
   }

   int getStrokeColor() {
      ColorStateList var1 = this.strokeColor;
      int var2;
      if (var1 == null) {
         var2 = -1;
      } else {
         var2 = var1.getDefaultColor();
      }

      return var2;
   }

   ColorStateList getStrokeColorStateList() {
      return this.strokeColor;
   }

   int getStrokeWidth() {
      return this.strokeWidth;
   }

   Rect getUserContentPadding() {
      return this.userContentPadding;
   }

   boolean isBackgroundOverwritten() {
      return this.isBackgroundOverwritten;
   }

   boolean isCheckable() {
      return this.checkable;
   }

   void loadFromAttributes(TypedArray var1) {
      ColorStateList var2 = MaterialResources.getColorStateList(this.materialCardView.getContext(), var1, R.styleable.MaterialCardView_strokeColor);
      this.strokeColor = var2;
      if (var2 == null) {
         this.strokeColor = ColorStateList.valueOf(-1);
      }

      this.strokeWidth = var1.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
      boolean var3 = var1.getBoolean(R.styleable.MaterialCardView_android_checkable, false);
      this.checkable = var3;
      this.materialCardView.setLongClickable(var3);
      this.checkedIconTint = MaterialResources.getColorStateList(this.materialCardView.getContext(), var1, R.styleable.MaterialCardView_checkedIconTint);
      this.setCheckedIcon(MaterialResources.getDrawable(this.materialCardView.getContext(), var1, R.styleable.MaterialCardView_checkedIcon));
      var2 = MaterialResources.getColorStateList(this.materialCardView.getContext(), var1, R.styleable.MaterialCardView_rippleColor);
      this.rippleColor = var2;
      if (var2 == null) {
         this.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(this.materialCardView, R.attr.colorControlHighlight));
      }

      this.setCardForegroundColor(MaterialResources.getColorStateList(this.materialCardView.getContext(), var1, R.styleable.MaterialCardView_cardForegroundColor));
      this.updateRippleColor();
      this.updateElevation();
      this.updateStroke();
      this.materialCardView.setBackgroundInternal(this.insetDrawable(this.bgDrawable));
      Object var4;
      if (this.materialCardView.isClickable()) {
         var4 = this.getClickableForeground();
      } else {
         var4 = this.foregroundContentDrawable;
      }

      this.fgDrawable = (Drawable)var4;
      this.materialCardView.setForeground(this.insetDrawable((Drawable)var4));
   }

   void onMeasure(int var1, int var2) {
      if (this.clickableForegroundDrawable != null) {
         int var3 = this.checkedIconMargin;
         int var4 = this.checkedIconSize;
         int var5 = var1 - var3 - var4;
         var3 = var2 - var3 - var4;
         boolean var6;
         if (VERSION.SDK_INT < 21) {
            var6 = true;
         } else {
            var6 = false;
         }

         label22: {
            if (!var6) {
               var1 = var5;
               var2 = var3;
               if (!this.materialCardView.getUseCompatPadding()) {
                  break label22;
               }
            }

            var2 = var3 - (int)Math.ceil((double)(this.calculateVerticalBackgroundPadding() * 2.0F));
            var1 = var5 - (int)Math.ceil((double)(this.calculateHorizontalBackgroundPadding() * 2.0F));
         }

         var5 = this.checkedIconMargin;
         if (ViewCompat.getLayoutDirection(this.materialCardView) == 1) {
            var3 = var1;
         } else {
            var3 = var5;
            var5 = var1;
         }

         this.clickableForegroundDrawable.setLayerInset(2, var5, this.checkedIconMargin, var3, var2);
      }

   }

   void setBackgroundOverwritten(boolean var1) {
      this.isBackgroundOverwritten = var1;
   }

   void setCardBackgroundColor(ColorStateList var1) {
      this.bgDrawable.setFillColor(var1);
   }

   void setCardForegroundColor(ColorStateList var1) {
      MaterialShapeDrawable var2 = this.foregroundContentDrawable;
      ColorStateList var3 = var1;
      if (var1 == null) {
         var3 = ColorStateList.valueOf(0);
      }

      var2.setFillColor(var3);
   }

   void setCheckable(boolean var1) {
      this.checkable = var1;
   }

   void setCheckedIcon(Drawable var1) {
      this.checkedIcon = var1;
      if (var1 != null) {
         var1 = DrawableCompat.wrap(var1.mutate());
         this.checkedIcon = var1;
         DrawableCompat.setTintList(var1, this.checkedIconTint);
      }

      if (this.clickableForegroundDrawable != null) {
         var1 = this.createCheckedIconLayer();
         this.clickableForegroundDrawable.setDrawableByLayerId(R.id.mtrl_card_checked_layer_id, var1);
      }

   }

   void setCheckedIconTint(ColorStateList var1) {
      this.checkedIconTint = var1;
      Drawable var2 = this.checkedIcon;
      if (var2 != null) {
         DrawableCompat.setTintList(var2, var1);
      }

   }

   void setCornerRadius(float var1) {
      this.setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(var1));
      this.fgDrawable.invalidateSelf();
      if (this.shouldAddCornerPaddingOutsideCardBackground() || this.shouldAddCornerPaddingInsideCardBackground()) {
         this.updateContentPadding();
      }

      if (this.shouldAddCornerPaddingOutsideCardBackground()) {
         this.updateInsets();
      }

   }

   void setProgress(float var1) {
      this.bgDrawable.setInterpolation(var1);
      MaterialShapeDrawable var2 = this.foregroundContentDrawable;
      if (var2 != null) {
         var2.setInterpolation(var1);
      }

      var2 = this.foregroundShapeDrawable;
      if (var2 != null) {
         var2.setInterpolation(var1);
      }

   }

   void setRippleColor(ColorStateList var1) {
      this.rippleColor = var1;
      this.updateRippleColor();
   }

   void setShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.shapeAppearanceModel = var1;
      this.bgDrawable.setShapeAppearanceModel(var1);
      MaterialShapeDrawable var2 = this.bgDrawable;
      var2.setShadowBitmapDrawingEnable(var2.isRoundRect() ^ true);
      var2 = this.foregroundContentDrawable;
      if (var2 != null) {
         var2.setShapeAppearanceModel(var1);
      }

      var2 = this.foregroundShapeDrawable;
      if (var2 != null) {
         var2.setShapeAppearanceModel(var1);
      }

      var2 = this.compatRippleDrawable;
      if (var2 != null) {
         var2.setShapeAppearanceModel(var1);
      }

   }

   void setStrokeColor(ColorStateList var1) {
      if (this.strokeColor != var1) {
         this.strokeColor = var1;
         this.updateStroke();
      }
   }

   void setStrokeWidth(int var1) {
      if (var1 != this.strokeWidth) {
         this.strokeWidth = var1;
         this.updateStroke();
      }
   }

   void setUserContentPadding(int var1, int var2, int var3, int var4) {
      this.userContentPadding.set(var1, var2, var3, var4);
      this.updateContentPadding();
   }

   void updateClickable() {
      Drawable var1 = this.fgDrawable;
      Object var2;
      if (this.materialCardView.isClickable()) {
         var2 = this.getClickableForeground();
      } else {
         var2 = this.foregroundContentDrawable;
      }

      this.fgDrawable = (Drawable)var2;
      if (var1 != var2) {
         this.updateInsetForeground((Drawable)var2);
      }

   }

   void updateContentPadding() {
      boolean var1;
      if (!this.shouldAddCornerPaddingInsideCardBackground() && !this.shouldAddCornerPaddingOutsideCardBackground()) {
         var1 = false;
      } else {
         var1 = true;
      }

      float var2;
      if (var1) {
         var2 = this.calculateActualCornerPadding();
      } else {
         var2 = 0.0F;
      }

      int var3 = (int)(var2 - this.getParentCardViewCalculatedCornerPadding());
      this.materialCardView.setAncestorContentPadding(this.userContentPadding.left + var3, this.userContentPadding.top + var3, this.userContentPadding.right + var3, this.userContentPadding.bottom + var3);
   }

   void updateElevation() {
      this.bgDrawable.setElevation(this.materialCardView.getCardElevation());
   }

   void updateInsets() {
      if (!this.isBackgroundOverwritten()) {
         this.materialCardView.setBackgroundInternal(this.insetDrawable(this.bgDrawable));
      }

      this.materialCardView.setForeground(this.insetDrawable(this.fgDrawable));
   }

   void updateStroke() {
      this.foregroundContentDrawable.setStroke((float)this.strokeWidth, this.strokeColor);
   }
}
