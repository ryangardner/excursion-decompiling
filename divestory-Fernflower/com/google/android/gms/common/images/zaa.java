package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.internal.Objects;

final class zaa {
   public final Uri zaa;

   public zaa(Uri var1) {
      this.zaa = var1;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof zaa) ? false : Objects.equal(((zaa)var1).zaa, this.zaa);
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zaa);
   }
}
