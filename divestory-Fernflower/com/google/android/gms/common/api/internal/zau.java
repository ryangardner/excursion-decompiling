package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

final class zau implements zabm {
   // $FF: synthetic field
   private final zaq zaa;

   private zau(zaq var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   zau(zaq var1, zat var2) {
      this(var1);
   }

   public final void zaa(int var1, boolean var2) {
      zaq.zaa(this.zaa).lock();

      try {
         if (zaq.zac(this.zaa)) {
            zaq.zaa(this.zaa, false);
            zaq.zaa(this.zaa, var1, var2);
            return;
         }

         zaq.zaa(this.zaa, true);
         zaq.zaf(this.zaa).onConnectionSuspended(var1);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }

   public final void zaa(Bundle var1) {
      zaq.zaa(this.zaa).lock();

      try {
         zaq.zab(this.zaa, ConnectionResult.RESULT_SUCCESS);
         zaq.zab(this.zaa);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }

   public final void zaa(ConnectionResult var1) {
      zaq.zaa(this.zaa).lock();

      try {
         zaq.zab(this.zaa, var1);
         zaq.zab(this.zaa);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }
}
