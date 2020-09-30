package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

public final class zac extends zab {
   private WeakReference<ImageManager.OnImageLoadedListener> zac;

   public zac(ImageManager.OnImageLoadedListener var1, Uri var2) {
      super(var2, 0);
      Asserts.checkNotNull(var1);
      this.zac = new WeakReference(var1);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zac)) {
         return false;
      } else {
         zac var2 = (zac)var1;
         ImageManager.OnImageLoadedListener var4 = (ImageManager.OnImageLoadedListener)this.zac.get();
         ImageManager.OnImageLoadedListener var3 = (ImageManager.OnImageLoadedListener)var2.zac.get();
         return var3 != null && var4 != null && Objects.equal(var3, var4) && Objects.equal(var2.zaa, this.zaa);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zaa);
   }

   protected final void zaa(Drawable var1, boolean var2, boolean var3, boolean var4) {
      if (!var3) {
         ImageManager.OnImageLoadedListener var5 = (ImageManager.OnImageLoadedListener)this.zac.get();
         if (var5 != null) {
            var5.onImageLoaded(this.zaa.zaa, var1, var4);
         }
      }

   }
}
