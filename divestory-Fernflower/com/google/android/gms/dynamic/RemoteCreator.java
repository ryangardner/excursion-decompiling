package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;

public abstract class RemoteCreator<T> {
   private final String zza;
   private T zzb;

   protected RemoteCreator(String var1) {
      this.zza = var1;
   }

   protected abstract T getRemoteCreator(IBinder var1);

   protected final T getRemoteCreatorInstance(Context var1) throws RemoteCreator.RemoteCreatorException {
      if (this.zzb == null) {
         Preconditions.checkNotNull(var1);
         var1 = GooglePlayServicesUtilLight.getRemoteContext(var1);
         if (var1 == null) {
            throw new RemoteCreator.RemoteCreatorException("Could not get remote context.");
         }

         ClassLoader var5 = var1.getClassLoader();

         try {
            this.zzb = this.getRemoteCreator((IBinder)var5.loadClass(this.zza).newInstance());
         } catch (ClassNotFoundException var2) {
            throw new RemoteCreator.RemoteCreatorException("Could not load creator class.", var2);
         } catch (InstantiationException var3) {
            throw new RemoteCreator.RemoteCreatorException("Could not instantiate creator.", var3);
         } catch (IllegalAccessException var4) {
            throw new RemoteCreator.RemoteCreatorException("Could not access creator.", var4);
         }
      }

      return this.zzb;
   }

   public static class RemoteCreatorException extends Exception {
      public RemoteCreatorException(String var1) {
         super(var1);
      }

      public RemoteCreatorException(String var1, Throwable var2) {
         super(var1, var2);
      }
   }
}
