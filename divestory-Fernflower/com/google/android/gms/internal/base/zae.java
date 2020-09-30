package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;

public final class zae extends Drawable implements Callback {
   private int zaa;
   private long zab;
   private int zac;
   private int zad;
   private int zae;
   private int zaf;
   private int zag;
   private boolean zah;
   private boolean zai;
   private zah zaj;
   private Drawable zak;
   private Drawable zal;
   private boolean zam;
   private boolean zan;
   private boolean zao;
   private int zap;

   public zae(Drawable var1, Drawable var2) {
      this((zah)null);
      Object var3 = var1;
      if (var1 == null) {
         var3 = com.google.android.gms.internal.base.zaf.zaa();
      }

      this.zak = (Drawable)var3;
      ((Drawable)var3).setCallback(this);
      zah var5 = this.zaj;
      int var4 = var5.zab;
      var5.zab = ((Drawable)var3).getChangingConfigurations() | var4;
      Object var6 = var2;
      if (var2 == null) {
         var6 = com.google.android.gms.internal.base.zaf.zaa();
      }

      this.zal = (Drawable)var6;
      ((Drawable)var6).setCallback(this);
      zah var7 = this.zaj;
      var4 = var7.zab;
      var7.zab = ((Drawable)var6).getChangingConfigurations() | var4;
   }

   zae(zah var1) {
      this.zaa = 0;
      this.zae = 255;
      this.zag = 0;
      this.zah = true;
      this.zaj = new zah(var1);
   }

   private final boolean zab() {
      if (!this.zam) {
         boolean var1;
         if (this.zak.getConstantState() != null && this.zal.getConstantState() != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         this.zan = var1;
         this.zam = true;
      }

      return this.zan;
   }

   public final void draw(Canvas var1) {
      int var2 = this.zaa;
      boolean var3 = false;
      boolean var4 = true;
      if (var2 != 1) {
         if (var2 == 2 && this.zab >= 0L) {
            float var5 = (float)(SystemClock.uptimeMillis() - this.zab) / (float)this.zaf;
            if (var5 >= 1.0F) {
               var3 = var4;
            } else {
               var3 = false;
            }

            if (var3) {
               this.zaa = 0;
            }

            var5 = Math.min(var5, 1.0F);
            this.zag = (int)((float)this.zad * var5 + 0.0F);
         } else {
            var3 = true;
         }
      } else {
         this.zab = SystemClock.uptimeMillis();
         this.zaa = 2;
      }

      int var9 = this.zag;
      boolean var6 = this.zah;
      Drawable var7 = this.zak;
      Drawable var8 = this.zal;
      if (!var3) {
         if (var6) {
            var7.setAlpha(this.zae - var9);
         }

         var7.draw(var1);
         if (var6) {
            var7.setAlpha(this.zae);
         }

         if (var9 > 0) {
            var8.setAlpha(var9);
            var8.draw(var1);
            var8.setAlpha(this.zae);
         }

         this.invalidateSelf();
      } else {
         if (!var6 || var9 == 0) {
            var7.draw(var1);
         }

         int var10 = this.zae;
         if (var9 == var10) {
            var8.setAlpha(var10);
            var8.draw(var1);
         }

      }
   }

   public final int getChangingConfigurations() {
      return super.getChangingConfigurations() | this.zaj.zaa | this.zaj.zab;
   }

   public final ConstantState getConstantState() {
      if (this.zab()) {
         this.zaj.zaa = this.getChangingConfigurations();
         return this.zaj;
      } else {
         return null;
      }
   }

   public final int getIntrinsicHeight() {
      return Math.max(this.zak.getIntrinsicHeight(), this.zal.getIntrinsicHeight());
   }

   public final int getIntrinsicWidth() {
      return Math.max(this.zak.getIntrinsicWidth(), this.zal.getIntrinsicWidth());
   }

   public final int getOpacity() {
      if (!this.zao) {
         this.zap = Drawable.resolveOpacity(this.zak.getOpacity(), this.zal.getOpacity());
         this.zao = true;
      }

      return this.zap;
   }

   public final void invalidateDrawable(Drawable var1) {
      Callback var2 = this.getCallback();
      if (var2 != null) {
         var2.invalidateDrawable(this);
      }

   }

   public final Drawable mutate() {
      if (!this.zai && super.mutate() == this) {
         if (!this.zab()) {
            throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
         }

         this.zak.mutate();
         this.zal.mutate();
         this.zai = true;
      }

      return this;
   }

   protected final void onBoundsChange(Rect var1) {
      this.zak.setBounds(var1);
      this.zal.setBounds(var1);
   }

   public final void scheduleDrawable(Drawable var1, Runnable var2, long var3) {
      Callback var5 = this.getCallback();
      if (var5 != null) {
         var5.scheduleDrawable(this, var2, var3);
      }

   }

   public final void setAlpha(int var1) {
      if (this.zag == this.zae) {
         this.zag = var1;
      }

      this.zae = var1;
      this.invalidateSelf();
   }

   public final void setColorFilter(ColorFilter var1) {
      this.zak.setColorFilter(var1);
      this.zal.setColorFilter(var1);
   }

   public final void unscheduleDrawable(Drawable var1, Runnable var2) {
      Callback var3 = this.getCallback();
      if (var3 != null) {
         var3.unscheduleDrawable(this, var2);
      }

   }

   public final Drawable zaa() {
      return this.zal;
   }

   public final void zaa(int var1) {
      this.zac = 0;
      this.zad = this.zae;
      this.zag = 0;
      this.zaf = 250;
      this.zaa = 1;
      this.invalidateSelf();
   }
}
