package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcu extends RegisterListenerMethod<zzaw, OpenFileCallback> {
   // $FF: synthetic field
   private final DriveFile zzfs;
   // $FF: synthetic field
   private final int zzft;
   // $FF: synthetic field
   private final zzg zzfu;
   // $FF: synthetic field
   private final ListenerHolder zzfv;
   // $FF: synthetic field
   private final zzch zzfw;

   zzcu(zzch var1, ListenerHolder var2, DriveFile var3, int var4, zzg var5, ListenerHolder var6) {
      super(var2);
      this.zzfw = var1;
      this.zzfs = var3;
      this.zzft = var4;
      this.zzfu = var5;
      this.zzfv = var6;
   }

   // $FF: synthetic method
   protected final void registerListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var4 = (zzaw)var1;
      zzgj var3 = new zzgj(this.zzfs.getDriveId(), this.zzft, 0);
      zzec var5 = ((zzeo)var4.getService()).zza((zzgj)var3, new zzdk(this.zzfw, this.zzfu, this.zzfv));
      this.zzfu.setCancelToken(ICancelToken.Stub.asInterface(var5.zzgs));
      var2.setResult((Object)null);
   }
}
