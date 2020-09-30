package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

public class BaseImplementation {
   public abstract static class ApiMethodImpl<R extends Result, A extends Api.AnyClient> extends BasePendingResult<R> implements BaseImplementation.ResultHolder<R> {
      private final Api<?> mApi;
      private final Api.AnyClientKey<A> mClientKey;

      @Deprecated
      protected ApiMethodImpl(Api.AnyClientKey<A> var1, GoogleApiClient var2) {
         super((GoogleApiClient)Preconditions.checkNotNull(var2, "GoogleApiClient must not be null"));
         this.mClientKey = (Api.AnyClientKey)Preconditions.checkNotNull(var1);
         this.mApi = null;
      }

      protected ApiMethodImpl(Api<?> var1, GoogleApiClient var2) {
         super((GoogleApiClient)Preconditions.checkNotNull(var2, "GoogleApiClient must not be null"));
         Preconditions.checkNotNull(var1, "Api must not be null");
         this.mClientKey = var1.zac();
         this.mApi = var1;
      }

      protected ApiMethodImpl(BasePendingResult.CallbackHandler<R> var1) {
         super(var1);
         this.mClientKey = new Api.AnyClientKey();
         this.mApi = null;
      }

      private void setFailedResult(RemoteException var1) {
         this.setFailedResult(new Status(8, var1.getLocalizedMessage(), (PendingIntent)null));
      }

      protected abstract void doExecute(A var1) throws RemoteException;

      public final Api<?> getApi() {
         return this.mApi;
      }

      public final Api.AnyClientKey<A> getClientKey() {
         return this.mClientKey;
      }

      protected void onSetFailedResult(R var1) {
      }

      public final void run(A var1) throws DeadObjectException {
         try {
            this.doExecute(var1);
         } catch (DeadObjectException var2) {
            this.setFailedResult((RemoteException)var2);
            throw var2;
         } catch (RemoteException var3) {
            this.setFailedResult(var3);
         }
      }

      public final void setFailedResult(Status var1) {
         Preconditions.checkArgument(var1.isSuccess() ^ true, "Failed result must not be success");
         Result var2 = this.createFailedResult(var1);
         this.setResult(var2);
         this.onSetFailedResult(var2);
      }
   }

   public interface ResultHolder<R> {
      void setFailedResult(Status var1);

      void setResult(R var1);
   }
}
