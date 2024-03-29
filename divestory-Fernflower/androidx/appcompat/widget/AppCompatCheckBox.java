package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.TintableCompoundButton;

public class AppCompatCheckBox extends CheckBox implements TintableCompoundButton, TintableBackgroundView {
   private final AppCompatBackgroundHelper mBackgroundTintHelper;
   private final AppCompatCompoundButtonHelper mCompoundButtonHelper;
   private final AppCompatTextHelper mTextHelper;

   public AppCompatCheckBox(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public AppCompatCheckBox(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.checkboxStyle);
   }

   public AppCompatCheckBox(Context var1, AttributeSet var2, int var3) {
      super(TintContextWrapper.wrap(var1), var2, var3);
      ThemeUtils.checkAppCompatTheme(this, this.getContext());
      AppCompatCompoundButtonHelper var4 = new AppCompatCompoundButtonHelper(this);
      this.mCompoundButtonHelper = var4;
      var4.loadFromAttributes(var2, var3);
      AppCompatBackgroundHelper var5 = new AppCompatBackgroundHelper(this);
      this.mBackgroundTintHelper = var5;
      var5.loadFromAttributes(var2, var3);
      AppCompatTextHelper var6 = new AppCompatTextHelper(this);
      this.mTextHelper = var6;
      var6.loadFromAttributes(var2, var3);
   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      AppCompatBackgroundHelper var1 = this.mBackgroundTintHelper;
      if (var1 != null) {
         var1.applySupportBackgroundTint();
      }

      AppCompatTextHelper var2 = this.mTextHelper;
      if (var2 != null) {
         var2.applyCompoundDrawablesTints();
      }

   }

   public int getCompoundPaddingLeft() {
      int var1 = super.getCompoundPaddingLeft();
      AppCompatCompoundButtonHelper var2 = this.mCompoundButtonHelper;
      int var3 = var1;
      if (var2 != null) {
         var3 = var2.getCompoundPaddingLeft(var1);
      }

      return var3;
   }

   public ColorStateList getSupportBackgroundTintList() {
      AppCompatBackgroundHelper var1 = this.mBackgroundTintHelper;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getSupportBackgroundTintList();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Mode getSupportBackgroundTintMode() {
      AppCompatBackgroundHelper var1 = this.mBackgroundTintHelper;
      Mode var2;
      if (var1 != null) {
         var2 = var1.getSupportBackgroundTintMode();
      } else {
         var2 = null;
      }

      return var2;
   }

   public ColorStateList getSupportButtonTintList() {
      AppCompatCompoundButtonHelper var1 = this.mCompoundButtonHelper;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getSupportButtonTintList();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Mode getSupportButtonTintMode() {
      AppCompatCompoundButtonHelper var1 = this.mCompoundButtonHelper;
      Mode var2;
      if (var1 != null) {
         var2 = var1.getSupportButtonTintMode();
      } else {
         var2 = null;
      }

      return var2;
   }

   public void setBackgroundDrawable(Drawable var1) {
      super.setBackgroundDrawable(var1);
      AppCompatBackgroundHelper var2 = this.mBackgroundTintHelper;
      if (var2 != null) {
         var2.onSetBackgroundDrawable(var1);
      }

   }

   public void setBackgroundResource(int var1) {
      super.setBackgroundResource(var1);
      AppCompatBackgroundHelper var2 = this.mBackgroundTintHelper;
      if (var2 != null) {
         var2.onSetBackgroundResource(var1);
      }

   }

   public void setButtonDrawable(int var1) {
      this.setButtonDrawable(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setButtonDrawable(Drawable var1) {
      super.setButtonDrawable(var1);
      AppCompatCompoundButtonHelper var2 = this.mCompoundButtonHelper;
      if (var2 != null) {
         var2.onSetButtonDrawable();
      }

   }

   public void setSupportBackgroundTintList(ColorStateList var1) {
      AppCompatBackgroundHelper var2 = this.mBackgroundTintHelper;
      if (var2 != null) {
         var2.setSupportBackgroundTintList(var1);
      }

   }

   public void setSupportBackgroundTintMode(Mode var1) {
      AppCompatBackgroundHelper var2 = this.mBackgroundTintHelper;
      if (var2 != null) {
         var2.setSupportBackgroundTintMode(var1);
      }

   }

   public void setSupportButtonTintList(ColorStateList var1) {
      AppCompatCompoundButtonHelper var2 = this.mCompoundButtonHelper;
      if (var2 != null) {
         var2.setSupportButtonTintList(var1);
      }

   }

   public void setSupportButtonTintMode(Mode var1) {
      AppCompatCompoundButtonHelper var2 = this.mCompoundButtonHelper;
      if (var2 != null) {
         var2.setSupportButtonTintMode(var1);
      }

   }
}
