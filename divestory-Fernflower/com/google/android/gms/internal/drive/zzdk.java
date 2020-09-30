package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OpenFileCallback;

final class zzdk extends zzl {
   // $FF: synthetic field
   private final zzch zzfw;
   private final ListenerToken zzgj;
   private final ListenerHolder<OpenFileCallback> zzgk;

   zzdk(zzch var1, ListenerToken var2, ListenerHolder var3) {
      this.zzfw = var1;
      this.zzgj = var2;
      this.zzgk = var3;
   }

   private final void zza(zzdg<OpenFileCallback> var1) {
      this.zzgk.notifyListener(new zzdo(this, var1));
   }

   public final void zza(Status var1) throws RemoteException {
      this.zza((zzdg)(new zzdl(this, var1)));
   }

   // $FF: synthetic method
   final void zza(Status var1, OpenFileCallback var2) {
      var2.onError(ApiExceptionUtil.fromStatus(var1));
      this.zzfw.cancelOpenFileCallback(this.zzgj);
   }

   public final void zza(zzfh var1) throws RemoteException {
      this.zza((zzdg)(new zzdn(this, var1)));
   }

   // $FF: synthetic method
   final void zza(zzfh var1, OpenFileCallback var2) {
      var2.onContents(new zzbi(var1.zzes));
      this.zzfw.cancelOpenFileCallback(this.zzgj);
   }

   public final void zza(zzfl var1) throws RemoteException {
      this.zza((zzdg)(new zzdm(var1)));
   }
}
