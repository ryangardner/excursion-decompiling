package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.events.DriveEvent;
import java.util.ArrayList;
import java.util.List;

public final class zzee extends zzet {
   private static final GmsLogger zzbz = new GmsLogger("EventCallback", "");
   private final int zzda = 1;
   private final com.google.android.gms.drive.events.zzi zzgt;
   private final zzeg zzgu;
   private final List<Integer> zzgv = new ArrayList();

   public zzee(Looper var1, Context var2, int var3, com.google.android.gms.drive.events.zzi var4) {
      this.zzgt = var4;
      this.zzgu = new zzeg(var1, var2, (zzef)null);
   }

   // $FF: synthetic method
   static GmsLogger zzai() {
      return zzbz;
   }

   public final void zzc(zzfp var1) throws RemoteException {
      DriveEvent var2 = var1.zzat();
      boolean var3;
      if (this.zzda == var2.getType()) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
      Preconditions.checkState(this.zzgv.contains(var2.getType()));
      zzeg var4 = this.zzgu;
      var4.sendMessage(var4.obtainMessage(1, new Pair(this.zzgt, var2)));
   }

   public final void zzf(int var1) {
      this.zzgv.add(1);
   }

   public final boolean zzg(int var1) {
      return this.zzgv.contains(1);
   }
}
