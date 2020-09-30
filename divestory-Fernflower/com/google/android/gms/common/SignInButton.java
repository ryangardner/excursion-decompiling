package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zaw;
import com.google.android.gms.common.internal.zay;
import com.google.android.gms.dynamic.RemoteCreator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {
   public static final int COLOR_AUTO = 2;
   public static final int COLOR_DARK = 0;
   public static final int COLOR_LIGHT = 1;
   public static final int SIZE_ICON_ONLY = 2;
   public static final int SIZE_STANDARD = 0;
   public static final int SIZE_WIDE = 1;
   private int zaa;
   private int zab;
   private View zac;
   private OnClickListener zad;

   public SignInButton(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public SignInButton(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public SignInButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.zad = null;
      TypedArray var6 = var1.getTheme().obtainStyledAttributes(var2, com.google.android.gms.base.R.styleable.SignInButton, 0, 0);

      try {
         this.zaa = var6.getInt(com.google.android.gms.base.R.styleable.SignInButton_buttonSize, 0);
         this.zab = var6.getInt(com.google.android.gms.base.R.styleable.SignInButton_colorScheme, 2);
      } finally {
         var6.recycle();
      }

      this.setStyle(this.zaa, this.zab);
   }

   public final void onClick(View var1) {
      OnClickListener var2 = this.zad;
      if (var2 != null && var1 == this.zac) {
         var2.onClick(this);
      }

   }

   public final void setColorScheme(int var1) {
      this.setStyle(this.zaa, var1);
   }

   public final void setEnabled(boolean var1) {
      super.setEnabled(var1);
      this.zac.setEnabled(var1);
   }

   public final void setOnClickListener(OnClickListener var1) {
      this.zad = var1;
      View var2 = this.zac;
      if (var2 != null) {
         var2.setOnClickListener(this);
      }

   }

   @Deprecated
   public final void setScopes(Scope[] var1) {
      this.setStyle(this.zaa, this.zab);
   }

   public final void setSize(int var1) {
      this.setStyle(var1, this.zab);
   }

   public final void setStyle(int var1, int var2) {
      this.zaa = var1;
      this.zab = var2;
      Context var3 = this.getContext();
      View var4 = this.zac;
      if (var4 != null) {
         this.removeView(var4);
      }

      try {
         this.zac = zaw.zaa(var3, this.zaa, this.zab);
      } catch (RemoteCreator.RemoteCreatorException var5) {
         Log.w("SignInButton", "Sign in button not found, using placeholder instead");
         var2 = this.zaa;
         var1 = this.zab;
         zay var6 = new zay(var3);
         var6.zaa(var3.getResources(), var2, var1);
         this.zac = var6;
      }

      this.addView(this.zac);
      this.zac.setEnabled(this.isEnabled());
      this.zac.setOnClickListener(this);
   }

   @Deprecated
   public final void setStyle(int var1, int var2, Scope[] var3) {
      this.setStyle(var1, var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ButtonSize {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ColorScheme {
   }
}
