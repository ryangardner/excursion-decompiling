package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingServiceConnection implements ServiceConnection {
   private boolean zza = false;
   private final BlockingQueue<IBinder> zzb = new LinkedBlockingQueue();

   public IBinder getService() throws InterruptedException {
      Preconditions.checkNotMainThread("BlockingServiceConnection.getService() called on main thread");
      if (!this.zza) {
         this.zza = true;
         return (IBinder)this.zzb.take();
      } else {
         throw new IllegalStateException("Cannot call get on this connection more than once");
      }
   }

   public IBinder getServiceWithTimeout(long var1, TimeUnit var3) throws InterruptedException, TimeoutException {
      Preconditions.checkNotMainThread("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
      if (!this.zza) {
         this.zza = true;
         IBinder var4 = (IBinder)this.zzb.poll(var1, var3);
         if (var4 != null) {
            return var4;
         } else {
            throw new TimeoutException("Timed out waiting for the service connection");
         }
      } else {
         throw new IllegalStateException("Cannot call get on this connection more than once");
      }
   }

   public void onServiceConnected(ComponentName var1, IBinder var2) {
      this.zzb.add(var2);
   }

   public void onServiceDisconnected(ComponentName var1) {
   }
}
