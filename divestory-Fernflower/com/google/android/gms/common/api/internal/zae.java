package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zae<T> extends zab {
   protected final TaskCompletionSource<T> zab;

   public zae(int var1, TaskCompletionSource<T> var2) {
      super(var1);
      this.zab = var2;
   }

   public void zaa(Status var1) {
      this.zab.trySetException(new ApiException(var1));
   }

   public void zaa(zaw var1, boolean var2) {
   }

   public void zaa(Exception var1) {
      this.zab.trySetException(var1);
   }

   public final void zac(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zad(var1);
      } catch (DeadObjectException var2) {
         this.zaa(zac.zaa((RemoteException)var2));
         throw var2;
      } catch (RemoteException var3) {
         this.zaa(zac.zaa(var3));
      } catch (RuntimeException var4) {
         this.zaa((Exception)var4);
      }
   }

   protected abstract void zad(GoogleApiManager.zaa<?> var1) throws RemoteException;
}
