package com.google.android.gms.common.api.internal;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zaz extends GoogleApiClient {
   private final String zaa;

   public zaz(String var1) {
      this.zaa = var1;
   }

   public ConnectionResult blockingConnect() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public ConnectionResult blockingConnect(long var1, TimeUnit var3) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public PendingResult<Status> clearDefaultAccountAndReconnect() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void connect() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void disconnect() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public ConnectionResult getConnectionResult(Api<?> var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public boolean hasConnectedApi(Api<?> var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public boolean isConnected() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public boolean isConnecting() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void reconnect() {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void stopAutoManage(FragmentActivity var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      throw new UnsupportedOperationException(this.zaa);
   }

   public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      throw new UnsupportedOperationException(this.zaa);
   }
}
