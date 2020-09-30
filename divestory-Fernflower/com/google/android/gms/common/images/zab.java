package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zaj;

public abstract class zab {
   final zaa zaa;
   protected int zab = 0;
   private int zac = 0;
   private boolean zad = false;
   private boolean zae = true;
   private boolean zaf = false;
   private boolean zag = true;

   public zab(Uri var1, int var2) {
      this.zaa = new zaa(var1);
      this.zab = var2;
   }

   final void zaa(Context var1, Bitmap var2, boolean var3) {
      Asserts.checkNotNull(var2);
      this.zaa(new BitmapDrawable(var1.getResources(), var2), var3, false, true);
   }

   final void zaa(Context var1, zaj var2) {
      if (this.zag) {
         this.zaa((Drawable)null, false, true, false);
      }

   }

   final void zaa(Context var1, zaj var2, boolean var3) {
      int var4 = this.zab;
      Drawable var5;
      if (var4 != 0) {
         var5 = var1.getResources().getDrawable(var4);
      } else {
         var5 = null;
      }

      this.zaa(var5, var3, false, false);
   }

   protected abstract void zaa(Drawable var1, boolean var2, boolean var3, boolean var4);

   protected final boolean zaa(boolean var1, boolean var2) {
      return this.zae && !var2 && !var1;
   }
}
