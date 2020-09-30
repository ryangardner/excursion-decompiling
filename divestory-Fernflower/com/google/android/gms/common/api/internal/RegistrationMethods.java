package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

public class RegistrationMethods<A extends Api.AnyClient, L> {
   public final RegisterListenerMethod<A, L> zaa;
   public final UnregisterListenerMethod<A, L> zab;
   public final Runnable zac;

   private RegistrationMethods(RegisterListenerMethod<A, L> var1, UnregisterListenerMethod<A, L> var2, Runnable var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   // $FF: synthetic method
   RegistrationMethods(RegisterListenerMethod var1, UnregisterListenerMethod var2, Runnable var3, zabt var4) {
      this(var1, var2, var3);
   }

   public static <A extends Api.AnyClient, L> RegistrationMethods.Builder<A, L> builder() {
      return new RegistrationMethods.Builder((zabt)null);
   }

   public static class Builder<A extends Api.AnyClient, L> {
      private RemoteCall<A, TaskCompletionSource<Void>> zaa;
      private RemoteCall<A, TaskCompletionSource<Boolean>> zab;
      private Runnable zac;
      private ListenerHolder<L> zad;
      private Feature[] zae;
      private boolean zaf;

      private Builder() {
         this.zac = zabv.zaa;
         this.zaf = true;
      }

      // $FF: synthetic method
      Builder(zabt var1) {
         this();
      }

      // $FF: synthetic method
      static RemoteCall zaa(RegistrationMethods.Builder var0) {
         return var0.zaa;
      }

      // $FF: synthetic method
      static RemoteCall zab(RegistrationMethods.Builder var0) {
         return var0.zab;
      }

      public RegistrationMethods<A, L> build() {
         RemoteCall var1 = this.zaa;
         boolean var2 = true;
         boolean var3;
         if (var1 != null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "Must set register function");
         if (this.zab != null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "Must set unregister function");
         if (this.zad != null) {
            var3 = var2;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "Must set holder");
         ListenerHolder.ListenerKey var4 = (ListenerHolder.ListenerKey)Preconditions.checkNotNull(this.zad.getListenerKey(), "Key must not be null");
         return new RegistrationMethods(new zabw(this, this.zad, this.zae, this.zaf), new zaby(this, var4), this.zac, (zabt)null);
      }

      public RegistrationMethods.Builder<A, L> onConnectionSuspended(Runnable var1) {
         this.zac = var1;
         return this;
      }

      public RegistrationMethods.Builder<A, L> register(RemoteCall<A, TaskCompletionSource<Void>> var1) {
         this.zaa = var1;
         return this;
      }

      @Deprecated
      public RegistrationMethods.Builder<A, L> register(BiConsumer<A, TaskCompletionSource<Void>> var1) {
         this.zaa = new zabu(var1);
         return this;
      }

      public RegistrationMethods.Builder<A, L> setAutoResolveMissingFeatures(boolean var1) {
         this.zaf = var1;
         return this;
      }

      public RegistrationMethods.Builder<A, L> setFeatures(Feature... var1) {
         this.zae = var1;
         return this;
      }

      public RegistrationMethods.Builder<A, L> unregister(RemoteCall<A, TaskCompletionSource<Boolean>> var1) {
         this.zab = var1;
         return this;
      }

      @Deprecated
      public RegistrationMethods.Builder<A, L> unregister(BiConsumer<A, TaskCompletionSource<Boolean>> var1) {
         this.zaa = new zabx(this);
         return this;
      }

      public RegistrationMethods.Builder<A, L> withHolder(ListenerHolder<L> var1) {
         this.zad = var1;
         return this;
      }

      // $FF: synthetic method
      final void zaa(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
         this.zaa.accept(var1, var2);
      }
   }
}
