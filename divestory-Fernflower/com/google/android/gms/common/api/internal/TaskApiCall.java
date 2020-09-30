package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class TaskApiCall<A extends Api.AnyClient, ResultT> {
   private final Feature[] zaa;
   private final boolean zab;
   private final int zac;

   @Deprecated
   public TaskApiCall() {
      this.zaa = null;
      this.zab = false;
      this.zac = 0;
   }

   private TaskApiCall(Feature[] var1, boolean var2, int var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   // $FF: synthetic method
   TaskApiCall(Feature[] var1, boolean var2, int var3, zacf var4) {
      this(var1, var2, var3);
   }

   public static <A extends Api.AnyClient, ResultT> TaskApiCall.Builder<A, ResultT> builder() {
      return new TaskApiCall.Builder((zacf)null);
   }

   protected abstract void doExecute(A var1, TaskCompletionSource<ResultT> var2) throws RemoteException;

   public boolean shouldAutoResolveMissingFeatures() {
      return this.zab;
   }

   public final Feature[] zaa() {
      return this.zaa;
   }

   public static class Builder<A extends Api.AnyClient, ResultT> {
      private RemoteCall<A, TaskCompletionSource<ResultT>> zaa;
      private boolean zab;
      private Feature[] zac;
      private int zad;

      private Builder() {
         this.zab = true;
         this.zad = 0;
      }

      // $FF: synthetic method
      Builder(zacf var1) {
         this();
      }

      // $FF: synthetic method
      static RemoteCall zaa(TaskApiCall.Builder var0) {
         return var0.zaa;
      }

      public TaskApiCall<A, ResultT> build() {
         boolean var1;
         if (this.zaa != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkArgument(var1, "execute parameter required");
         return new zacg(this, this.zac, this.zab, this.zad);
      }

      @Deprecated
      public TaskApiCall.Builder<A, ResultT> execute(BiConsumer<A, TaskCompletionSource<ResultT>> var1) {
         this.zaa = new zach(var1);
         return this;
      }

      public TaskApiCall.Builder<A, ResultT> run(RemoteCall<A, TaskCompletionSource<ResultT>> var1) {
         this.zaa = var1;
         return this;
      }

      public TaskApiCall.Builder<A, ResultT> setAutoResolveMissingFeatures(boolean var1) {
         this.zab = var1;
         return this;
      }

      public TaskApiCall.Builder<A, ResultT> setFeatures(Feature... var1) {
         this.zac = var1;
         return this;
      }

      public TaskApiCall.Builder<A, ResultT> setMethodKey(int var1) {
         boolean var2;
         if (var1 != 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2);
         this.zad = var1;
         return this;
      }
   }
}
