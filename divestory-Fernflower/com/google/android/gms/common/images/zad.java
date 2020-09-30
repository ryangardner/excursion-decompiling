package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.base.zak;
import java.lang.ref.WeakReference;

public final class zad extends zab {
   private WeakReference<ImageView> zac;

   public zad(ImageView var1, int var2) {
      super(Uri.EMPTY, var2);
      Asserts.checkNotNull(var1);
      this.zac = new WeakReference(var1);
   }

   public zad(ImageView var1, Uri var2) {
      super(var2, 0);
      Asserts.checkNotNull(var1);
      this.zac = new WeakReference(var1);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zad)) {
         return false;
      } else {
         zad var2 = (zad)var1;
         ImageView var3 = (ImageView)this.zac.get();
         ImageView var4 = (ImageView)var2.zac.get();
         return var4 != null && var3 != null && Objects.equal(var4, var3);
      }
   }

   public final int hashCode() {
      return 0;
   }

   protected final void zaa(Drawable var1, boolean var2, boolean var3, boolean var4) {
      ImageView var5 = (ImageView)this.zac.get();
      if (var5 != null) {
         int var6 = 0;
         boolean var7;
         if (!var3 && !var4) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7 && var5 instanceof zak) {
            zak var8 = (zak)var5;
            int var9 = zak.zaa();
            if (this.zab != 0 && var9 == this.zab) {
               return;
            }
         }

         var2 = this.zaa(var2, var3);
         Object var13 = var1;
         if (var2) {
            Drawable var10 = var5.getDrawable();
            Drawable var14;
            if (var10 != null) {
               var14 = var10;
               if (var10 instanceof com.google.android.gms.internal.base.zae) {
                  var14 = ((com.google.android.gms.internal.base.zae)var10).zaa();
               }
            } else {
               var14 = null;
            }

            var13 = new com.google.android.gms.internal.base.zae(var14, var1);
         }

         var5.setImageDrawable((Drawable)var13);
         if (var5 instanceof zak) {
            zak var11 = (zak)var5;
            Uri var12;
            if (var4) {
               var12 = this.zaa.zaa;
            } else {
               var12 = Uri.EMPTY;
            }

            zak.zaa(var12);
            if (var7) {
               var6 = this.zab;
            }

            zak.zaa(var6);
         }

         if (var13 != null && var2) {
            ((com.google.android.gms.internal.base.zae)var13).zaa(250);
         }
      }

   }
}
