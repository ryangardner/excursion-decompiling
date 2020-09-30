package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseGmsClient<T extends IInterface> {
   public static final int CONNECT_STATE_CONNECTED = 4;
   public static final int CONNECT_STATE_DISCONNECTED = 1;
   public static final int CONNECT_STATE_DISCONNECTING = 5;
   public static final String DEFAULT_ACCOUNT = "<<default account>>";
   public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = new String[]{"service_esmobile", "service_googleme"};
   public static final String KEY_PENDING_INTENT = "pendingIntent";
   private static final Feature[] zzd = new Feature[0];
   final Handler zza;
   private ConnectionResult zzaa;
   private boolean zzab;
   private volatile com.google.android.gms.common.internal.zzc zzac;
   protected BaseGmsClient.ConnectionProgressReportCallbacks zzb;
   protected AtomicInteger zzc;
   private int zze;
   private long zzf;
   private long zzg;
   private int zzh;
   private long zzi;
   private volatile String zzj;
   private zzl zzk;
   private final Context zzl;
   private final Looper zzm;
   private final GmsClientSupervisor zzn;
   private final GoogleApiAvailabilityLight zzo;
   private final Object zzp;
   private final Object zzq;
   private IGmsServiceBroker zzr;
   private T zzs;
   private final ArrayList<BaseGmsClient.zzc<?>> zzt;
   private BaseGmsClient.zzd zzu;
   private int zzv;
   private final BaseGmsClient.BaseConnectionCallbacks zzw;
   private final BaseGmsClient.BaseOnConnectionFailedListener zzx;
   private final int zzy;
   private final String zzz;

   protected BaseGmsClient(Context var1, Handler var2, GmsClientSupervisor var3, GoogleApiAvailabilityLight var4, int var5, BaseGmsClient.BaseConnectionCallbacks var6, BaseGmsClient.BaseOnConnectionFailedListener var7) {
      this.zzj = null;
      this.zzp = new Object();
      this.zzq = new Object();
      this.zzt = new ArrayList();
      this.zzv = 1;
      this.zzaa = null;
      this.zzab = false;
      this.zzac = null;
      this.zzc = new AtomicInteger(0);
      this.zzl = (Context)Preconditions.checkNotNull(var1, "Context must not be null");
      this.zza = (Handler)Preconditions.checkNotNull(var2, "Handler must not be null");
      this.zzm = var2.getLooper();
      this.zzn = (GmsClientSupervisor)Preconditions.checkNotNull(var3, "Supervisor must not be null");
      this.zzo = (GoogleApiAvailabilityLight)Preconditions.checkNotNull(var4, "API availability must not be null");
      this.zzy = var5;
      this.zzw = var6;
      this.zzx = var7;
      this.zzz = null;
   }

   protected BaseGmsClient(Context var1, Looper var2, int var3, BaseGmsClient.BaseConnectionCallbacks var4, BaseGmsClient.BaseOnConnectionFailedListener var5, String var6) {
      this(var1, var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailabilityLight.getInstance(), var3, (BaseGmsClient.BaseConnectionCallbacks)Preconditions.checkNotNull(var4), (BaseGmsClient.BaseOnConnectionFailedListener)Preconditions.checkNotNull(var5), var6);
   }

   protected BaseGmsClient(Context var1, Looper var2, GmsClientSupervisor var3, GoogleApiAvailabilityLight var4, int var5, BaseGmsClient.BaseConnectionCallbacks var6, BaseGmsClient.BaseOnConnectionFailedListener var7, String var8) {
      this.zzj = null;
      this.zzp = new Object();
      this.zzq = new Object();
      this.zzt = new ArrayList();
      this.zzv = 1;
      this.zzaa = null;
      this.zzab = false;
      this.zzac = null;
      this.zzc = new AtomicInteger(0);
      this.zzl = (Context)Preconditions.checkNotNull(var1, "Context must not be null");
      this.zzm = (Looper)Preconditions.checkNotNull(var2, "Looper must not be null");
      this.zzn = (GmsClientSupervisor)Preconditions.checkNotNull(var3, "Supervisor must not be null");
      this.zzo = (GoogleApiAvailabilityLight)Preconditions.checkNotNull(var4, "API availability must not be null");
      this.zza = new BaseGmsClient.zzb(var2);
      this.zzy = var5;
      this.zzw = var6;
      this.zzx = var7;
      this.zzz = var8;
   }

   private final String zza() {
      String var1 = this.zzz;
      String var2 = var1;
      if (var1 == null) {
         var2 = this.zzl.getClass().getName();
      }

      return var2;
   }

   private final void zza(int var1) {
      byte var3;
      if (this.zzb()) {
         var3 = 5;
         this.zzab = true;
      } else {
         var3 = 4;
      }

      Handler var2 = this.zza;
      var2.sendMessage(var2.obtainMessage(var3, this.zzc.get(), 16));
   }

   private final void zza(int var1, T var2) {
      boolean var3 = false;
      boolean var4;
      if (var1 == 4) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if (var2 != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var4 == var5) {
         var3 = true;
      }

      Preconditions.checkArgument(var3);
      Object var6 = this.zzp;
      synchronized(var6){}

      Throwable var10000;
      boolean var10001;
      label2093: {
         try {
            this.zzv = var1;
            this.zzs = var2;
         } catch (Throwable var221) {
            var10000 = var221;
            var10001 = false;
            break label2093;
         }

         String var224;
         IllegalStateException var228;
         label2099: {
            BaseGmsClient.zzd var222;
            if (var1 == 1) {
               try {
                  var222 = this.zzu;
               } catch (Throwable var213) {
                  var10000 = var213;
                  var10001 = false;
                  break label2093;
               }

               if (var222 != null) {
                  try {
                     this.zzn.zza((String)Preconditions.checkNotNull(this.zzk.zza()), this.zzk.zzb(), this.zzk.zzc(), var222, this.zza(), this.zzk.zzd());
                     this.zzu = null;
                  } catch (Throwable var212) {
                     var10000 = var212;
                     var10001 = false;
                     break label2093;
                  }
               }
            } else if (var1 != 2 && var1 != 3) {
               if (var1 == 4) {
                  try {
                     this.onConnectedLocked((IInterface)Preconditions.checkNotNull(var2));
                  } catch (Throwable var217) {
                     var10000 = var217;
                     var10001 = false;
                     break label2093;
                  }
               }
            } else {
               label2100: {
                  try {
                     var222 = this.zzu;
                  } catch (Throwable var216) {
                     var10000 = var216;
                     var10001 = false;
                     break label2093;
                  }

                  String var8;
                  int var225;
                  if (var222 != null) {
                     try {
                        if (this.zzk != null) {
                           String var7 = this.zzk.zza();
                           var8 = this.zzk.zzb();
                           var225 = String.valueOf(var7).length();
                           var1 = String.valueOf(var8).length();
                           StringBuilder var9 = new StringBuilder(var225 + 70 + var1);
                           var9.append("Calling connect() while still connected, missing disconnect() for ");
                           var9.append(var7);
                           var9.append(" on ");
                           var9.append(var8);
                           Log.e("GmsClient", var9.toString());
                           this.zzn.zza((String)Preconditions.checkNotNull(this.zzk.zza()), this.zzk.zzb(), this.zzk.zzc(), var222, this.zza(), this.zzk.zzd());
                           this.zzc.incrementAndGet();
                        }
                     } catch (Throwable var215) {
                        var10000 = var215;
                        var10001 = false;
                        break label2093;
                     }
                  }

                  zzl var223;
                  BaseGmsClient.zzd var227;
                  label2101: {
                     try {
                        var227 = new BaseGmsClient.zzd(this.zzc.get());
                        this.zzu = var227;
                        if (this.zzv == 3 && this.getLocalStartServiceAction() != null) {
                           var223 = new zzl(this.getContext().getPackageName(), this.getLocalStartServiceAction(), true, GmsClientSupervisor.getDefaultBindFlags(), false);
                           break label2101;
                        }
                     } catch (Throwable var220) {
                        var10000 = var220;
                        var10001 = false;
                        break label2093;
                     }

                     try {
                        var223 = new zzl(this.getStartServicePackage(), this.getStartServiceAction(), false, GmsClientSupervisor.getDefaultBindFlags(), this.getUseDynamicLookup());
                     } catch (Throwable var214) {
                        var10000 = var214;
                        var10001 = false;
                        break label2093;
                     }
                  }

                  label2102: {
                     try {
                        this.zzk = var223;
                        if (var223.zzd() && this.getMinApkVersion() < 17895000) {
                           var228 = new IllegalStateException;
                           var224 = String.valueOf(this.zzk.zza());
                           if (var224.length() == 0) {
                              break label2102;
                           }

                           var224 = "Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(var224);
                           break label2099;
                        }
                     } catch (Throwable var219) {
                        var10000 = var219;
                        var10001 = false;
                        break label2093;
                     }

                     try {
                        GmsClientSupervisor var10 = this.zzn;
                        var224 = (String)Preconditions.checkNotNull(this.zzk.zza());
                        String var11 = this.zzk.zzb();
                        var1 = this.zzk.zzc();
                        var8 = this.zza();
                        var3 = this.zzk.zzd();
                        GmsClientSupervisor.zza var230 = new GmsClientSupervisor.zza(var224, var11, var1, var3);
                        if (!var10.zza(var230, var227, var8)) {
                           String var231 = this.zzk.zza();
                           var224 = this.zzk.zzb();
                           var1 = String.valueOf(var231).length();
                           var225 = String.valueOf(var224).length();
                           StringBuilder var229 = new StringBuilder(var1 + 34 + var225);
                           var229.append("unable to connect to service: ");
                           var229.append(var231);
                           var229.append(" on ");
                           var229.append(var224);
                           Log.e("GmsClient", var229.toString());
                           this.zza(16, (Bundle)null, this.zzc.get());
                        }
                        break label2100;
                     } catch (Throwable var218) {
                        var10000 = var218;
                        var10001 = false;
                        break label2093;
                     }
                  }

                  try {
                     var224 = new String("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ");
                     break label2099;
                  } catch (Throwable var211) {
                     var10000 = var211;
                     var10001 = false;
                     break label2093;
                  }
               }
            }

            try {
               return;
            } catch (Throwable var210) {
               var10000 = var210;
               var10001 = false;
               break label2093;
            }
         }

         label2029:
         try {
            var228.<init>(var224);
            throw var228;
         } catch (Throwable var209) {
            var10000 = var209;
            var10001 = false;
            break label2029;
         }
      }

      while(true) {
         Throwable var226 = var10000;

         try {
            throw var226;
         } catch (Throwable var208) {
            var10000 = var208;
            var10001 = false;
            continue;
         }
      }
   }

   // $FF: synthetic method
   static void zza(BaseGmsClient var0, int var1) {
      var0.zza(16);
   }

   // $FF: synthetic method
   static void zza(BaseGmsClient var0, int var1, IInterface var2) {
      var0.zza(var1, (IInterface)null);
   }

   private final void zza(com.google.android.gms.common.internal.zzc var1) {
      this.zzac = var1;
   }

   private final boolean zza(int var1, int var2, T var3) {
      Object var4 = this.zzp;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label123: {
         try {
            if (this.zzv != var1) {
               return false;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label123;
         }

         label117:
         try {
            this.zza(var2, var3);
            return true;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label117;
         }
      }

      while(true) {
         Throwable var17 = var10000;

         try {
            throw var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }

   private final boolean zzb() {
      Object var1 = this.zzp;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label134: {
         boolean var2;
         label133: {
            label132: {
               try {
                  if (this.zzv == 3) {
                     break label132;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label134;
               }

               var2 = false;
               break label133;
            }

            var2 = true;
         }

         label126:
         try {
            return var2;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label126;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   private final boolean zzc() {
      if (this.zzab) {
         return false;
      } else if (TextUtils.isEmpty(this.getServiceDescriptor())) {
         return false;
      } else if (TextUtils.isEmpty(this.getLocalStartServiceAction())) {
         return false;
      } else {
         try {
            Class.forName(this.getServiceDescriptor());
            return true;
         } catch (ClassNotFoundException var2) {
            return false;
         }
      }
   }

   // $FF: synthetic method
   static ArrayList zzf(BaseGmsClient var0) {
      return var0.zzt;
   }

   public void checkAvailabilityAndConnect() {
      int var1 = this.zzo.isGooglePlayServicesAvailable(this.zzl, this.getMinApkVersion());
      if (var1 != 0) {
         this.zza(1, (IInterface)null);
         this.triggerNotAvailable(new BaseGmsClient.LegacyClientCallbackAdapter(), var1, (PendingIntent)null);
      } else {
         this.connect(new BaseGmsClient.LegacyClientCallbackAdapter());
      }
   }

   protected final void checkConnected() {
      if (!this.isConnected()) {
         throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
      }
   }

   public void connect(BaseGmsClient.ConnectionProgressReportCallbacks var1) {
      this.zzb = (BaseGmsClient.ConnectionProgressReportCallbacks)Preconditions.checkNotNull(var1, "Connection progress callbacks cannot be null.");
      this.zza(2, (IInterface)null);
   }

   protected abstract T createServiceInterface(IBinder var1);

   public void disconnect() {
      // $FF: Couldn't be decompiled
   }

   public void disconnect(String var1) {
      this.zzj = var1;
      this.disconnect();
   }

   public void dump(String param1, FileDescriptor param2, PrintWriter param3, String[] param4) {
      // $FF: Couldn't be decompiled
   }

   protected boolean enableLocalFallback() {
      return false;
   }

   public Account getAccount() {
      return null;
   }

   public Feature[] getApiFeatures() {
      return zzd;
   }

   public final Feature[] getAvailableFeatures() {
      com.google.android.gms.common.internal.zzc var1 = this.zzac;
      return var1 == null ? null : var1.zzb;
   }

   public Bundle getConnectionHint() {
      return null;
   }

   public final Context getContext() {
      return this.zzl;
   }

   public String getEndpointPackageName() {
      if (this.isConnected()) {
         zzl var1 = this.zzk;
         if (var1 != null) {
            return var1.zzb();
         }
      }

      throw new RuntimeException("Failed to connect when checking package");
   }

   protected Bundle getGetServiceRequestExtraArgs() {
      return new Bundle();
   }

   public String getLastDisconnectMessage() {
      return this.zzj;
   }

   protected String getLocalStartServiceAction() {
      return null;
   }

   public final Looper getLooper() {
      return this.zzm;
   }

   public int getMinApkVersion() {
      return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
   }

   public void getRemoteService(IAccountAccessor param1, Set<Scope> param2) {
      // $FF: Couldn't be decompiled
   }

   protected Set<Scope> getScopes() {
      return Collections.emptySet();
   }

   public final T getService() throws DeadObjectException {
      Object var1 = this.zzp;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.zzv != 5) {
               this.checkConnected();
               IInterface var16 = (IInterface)Preconditions.checkNotNull(this.zzs, "Client is connected but service is null");
               return var16;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label116:
         try {
            DeadObjectException var15 = new DeadObjectException();
            throw var15;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label116;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public IBinder getServiceBrokerBinder() {
      Object var1 = this.zzq;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label123: {
         try {
            if (this.zzr == null) {
               return null;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label123;
         }

         label117:
         try {
            IBinder var15 = this.zzr.asBinder();
            return var15;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label117;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   protected abstract String getServiceDescriptor();

   public Intent getSignInIntent() {
      throw new UnsupportedOperationException("Not a sign in API");
   }

   protected abstract String getStartServiceAction();

   protected String getStartServicePackage() {
      return "com.google.android.gms";
   }

   public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
      if (this.zzac == null) {
      }

      return null;
   }

   protected boolean getUseDynamicLookup() {
      return false;
   }

   public boolean isConnected() {
      Object var1 = this.zzp;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label134: {
         boolean var2;
         label133: {
            label132: {
               try {
                  if (this.zzv == 4) {
                     break label132;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label134;
               }

               var2 = false;
               break label133;
            }

            var2 = true;
         }

         label126:
         try {
            return var2;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label126;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   public boolean isConnecting() {
      Object var1 = this.zzp;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label151: {
         boolean var2;
         label150: {
            label149: {
               try {
                  if (this.zzv != 2 && this.zzv != 3) {
                     break label149;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label151;
               }

               var2 = true;
               break label150;
            }

            var2 = false;
         }

         label140:
         try {
            return var2;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label140;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   protected void onConnectedLocked(T var1) {
      this.zzg = System.currentTimeMillis();
   }

   protected void onConnectionFailed(ConnectionResult var1) {
      this.zzh = var1.getErrorCode();
      this.zzi = System.currentTimeMillis();
   }

   protected void onConnectionSuspended(int var1) {
      this.zze = var1;
      this.zzf = System.currentTimeMillis();
   }

   protected void onPostInitHandler(int var1, IBinder var2, Bundle var3, int var4) {
      Handler var5 = this.zza;
      var5.sendMessage(var5.obtainMessage(1, var4, -1, new BaseGmsClient.zzf(var1, var2, var3)));
   }

   public void onUserSignOut(BaseGmsClient.SignOutCallbacks var1) {
      var1.onSignOutComplete();
   }

   public boolean providesSignIn() {
      return false;
   }

   public boolean requiresAccount() {
      return false;
   }

   public boolean requiresGooglePlayServices() {
      return true;
   }

   public boolean requiresSignIn() {
      return false;
   }

   public void triggerConnectionSuspended(int var1) {
      Handler var2 = this.zza;
      var2.sendMessage(var2.obtainMessage(6, this.zzc.get(), var1));
   }

   protected void triggerNotAvailable(BaseGmsClient.ConnectionProgressReportCallbacks var1, int var2, PendingIntent var3) {
      this.zzb = (BaseGmsClient.ConnectionProgressReportCallbacks)Preconditions.checkNotNull(var1, "Connection progress callbacks cannot be null.");
      Handler var4 = this.zza;
      var4.sendMessage(var4.obtainMessage(3, this.zzc.get(), var2, var3));
   }

   public boolean usesClientTelemetry() {
      return false;
   }

   protected final void zza(int var1, Bundle var2, int var3) {
      Handler var4 = this.zza;
      var4.sendMessage(var4.obtainMessage(7, var3, -1, new BaseGmsClient.zzg(var1, (Bundle)null)));
   }

   public interface BaseConnectionCallbacks {
      int CAUSE_DEAD_OBJECT_EXCEPTION = 3;
      int CAUSE_SERVICE_DISCONNECTED = 1;

      void onConnected(Bundle var1);

      void onConnectionSuspended(int var1);
   }

   public interface BaseOnConnectionFailedListener {
      void onConnectionFailed(ConnectionResult var1);
   }

   public interface ConnectionProgressReportCallbacks {
      void onReportServiceBinding(ConnectionResult var1);
   }

   protected class LegacyClientCallbackAdapter implements BaseGmsClient.ConnectionProgressReportCallbacks {
      public LegacyClientCallbackAdapter() {
      }

      public void onReportServiceBinding(ConnectionResult var1) {
         if (var1.isSuccess()) {
            BaseGmsClient var2 = BaseGmsClient.this;
            var2.getRemoteService((IAccountAccessor)null, var2.getScopes());
         } else {
            if (BaseGmsClient.this.zzx != null) {
               BaseGmsClient.this.zzx.onConnectionFailed(var1);
            }

         }
      }
   }

   public interface SignOutCallbacks {
      void onSignOutComplete();
   }

   private abstract class zza extends BaseGmsClient.zzc<Boolean> {
      private final int zza;
      private final Bundle zzb;

      protected zza(int var2, Bundle var3) {
         super(true);
         this.zza = var2;
         this.zzb = var3;
      }

      protected abstract void zza(ConnectionResult var1);

      // $FF: synthetic method
      protected final void zza(Object var1) {
         Boolean var2 = (Boolean)var1;
         PendingIntent var3 = null;
         if (var2 == null) {
            BaseGmsClient.zza(BaseGmsClient.this, 1, (IInterface)null);
         } else {
            if (this.zza == 0) {
               if (!this.zza()) {
                  BaseGmsClient.zza(BaseGmsClient.this, 1, (IInterface)null);
                  this.zza(new ConnectionResult(8, (PendingIntent)null));
                  return;
               }
            } else {
               BaseGmsClient.zza(BaseGmsClient.this, 1, (IInterface)null);
               Bundle var4 = this.zzb;
               if (var4 != null) {
                  var3 = (PendingIntent)var4.getParcelable("pendingIntent");
               }

               this.zza(new ConnectionResult(this.zza, var3));
            }

         }
      }

      protected abstract boolean zza();

      protected final void zzb() {
      }
   }

   final class zzb extends com.google.android.gms.internal.common.zzi {
      public zzb(Looper var2) {
         super(var2);
      }

      private static void zza(Message var0) {
         BaseGmsClient.zzc var1 = (BaseGmsClient.zzc)var0.obj;
         var1.zzb();
         var1.zzd();
      }

      private static boolean zzb(Message var0) {
         return var0.what == 2 || var0.what == 1 || var0.what == 7;
      }

      public final void handleMessage(Message var1) {
         if (BaseGmsClient.this.zzc.get() != var1.arg1) {
            if (zzb(var1)) {
               zza(var1);
            }

         } else if ((var1.what == 1 || var1.what == 7 || var1.what == 4 && !BaseGmsClient.this.enableLocalFallback() || var1.what == 5) && !BaseGmsClient.this.isConnecting()) {
            zza(var1);
         } else {
            int var2 = var1.what;
            PendingIntent var3 = null;
            ConnectionResult var5;
            if (var2 == 4) {
               BaseGmsClient.this.zzaa = new ConnectionResult(var1.arg2);
               if (BaseGmsClient.this.zzc() && !BaseGmsClient.this.zzab) {
                  BaseGmsClient.zza(BaseGmsClient.this, 3, (IInterface)null);
               } else {
                  if (BaseGmsClient.this.zzaa != null) {
                     var5 = BaseGmsClient.this.zzaa;
                  } else {
                     var5 = new ConnectionResult(8);
                  }

                  BaseGmsClient.this.zzb.onReportServiceBinding(var5);
                  BaseGmsClient.this.onConnectionFailed(var5);
               }
            } else if (var1.what == 5) {
               if (BaseGmsClient.this.zzaa != null) {
                  var5 = BaseGmsClient.this.zzaa;
               } else {
                  var5 = new ConnectionResult(8);
               }

               BaseGmsClient.this.zzb.onReportServiceBinding(var5);
               BaseGmsClient.this.onConnectionFailed(var5);
            } else if (var1.what == 3) {
               if (var1.obj instanceof PendingIntent) {
                  var3 = (PendingIntent)var1.obj;
               }

               var5 = new ConnectionResult(var1.arg2, var3);
               BaseGmsClient.this.zzb.onReportServiceBinding(var5);
               BaseGmsClient.this.onConnectionFailed(var5);
            } else if (var1.what == 6) {
               BaseGmsClient.zza(BaseGmsClient.this, 5, (IInterface)null);
               if (BaseGmsClient.this.zzw != null) {
                  BaseGmsClient.this.zzw.onConnectionSuspended(var1.arg2);
               }

               BaseGmsClient.this.onConnectionSuspended(var1.arg2);
               BaseGmsClient.this.zza(5, 1, (IInterface)null);
            } else if (var1.what == 2 && !BaseGmsClient.this.isConnected()) {
               zza(var1);
            } else if (zzb(var1)) {
               ((BaseGmsClient.zzc)var1.obj).zzc();
            } else {
               var2 = var1.what;
               StringBuilder var4 = new StringBuilder(45);
               var4.append("Don't know how to handle message: ");
               var4.append(var2);
               Log.wtf("GmsClient", var4.toString(), new Exception());
            }
         }
      }
   }

   protected abstract class zzc<TListener> {
      private TListener zza;
      private boolean zzb;

      public zzc(TListener var2) {
         this.zza = var2;
         this.zzb = false;
      }

      protected abstract void zza(TListener var1);

      protected abstract void zzb();

      public final void zzc() {
         // $FF: Couldn't be decompiled
      }

      public final void zzd() {
         // $FF: Couldn't be decompiled
      }

      public final void zze() {
         // $FF: Couldn't be decompiled
      }
   }

   public final class zzd implements ServiceConnection {
      private final int zza;

      public zzd(int var2) {
         this.zza = var2;
      }

      public final void onServiceConnected(ComponentName var1, IBinder var2) {
         if (var2 == null) {
            BaseGmsClient.zza(BaseGmsClient.this, 16);
         } else {
            Object var3 = BaseGmsClient.this.zzq;
            synchronized(var3){}

            label320: {
               Throwable var10000;
               boolean var10001;
               label322: {
                  IInterface var35;
                  BaseGmsClient var4;
                  try {
                     var4 = BaseGmsClient.this;
                     var35 = var2.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label322;
                  }

                  Object var36;
                  label308: {
                     if (var35 != null) {
                        try {
                           if (var35 instanceof IGmsServiceBroker) {
                              var36 = (IGmsServiceBroker)var35;
                              break label308;
                           }
                        } catch (Throwable var33) {
                           var10000 = var33;
                           var10001 = false;
                           break label322;
                        }
                     }

                     try {
                        var36 = new IGmsServiceBroker.Stub.zza(var2);
                     } catch (Throwable var32) {
                        var10000 = var32;
                        var10001 = false;
                        break label322;
                     }
                  }

                  label300:
                  try {
                     var4.zzr = (IGmsServiceBroker)var36;
                     break label320;
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label300;
                  }
               }

               while(true) {
                  Throwable var37 = var10000;

                  try {
                     throw var37;
                  } catch (Throwable var30) {
                     var10000 = var30;
                     var10001 = false;
                     continue;
                  }
               }
            }

            BaseGmsClient.this.zza(0, (Bundle)null, this.zza);
         }
      }

      public final void onServiceDisconnected(ComponentName param1) {
         // $FF: Couldn't be decompiled
      }
   }

   public static final class zze extends IGmsCallbacks.zza {
      private BaseGmsClient zza;
      private final int zzb;

      public zze(BaseGmsClient var1, int var2) {
         this.zza = var1;
         this.zzb = var2;
      }

      public final void onPostInitComplete(int var1, IBinder var2, Bundle var3) {
         Preconditions.checkNotNull(this.zza, "onPostInitComplete can be called only once per call to getRemoteService");
         this.zza.onPostInitHandler(var1, var2, var3, this.zzb);
         this.zza = null;
      }

      public final void zza(int var1, Bundle var2) {
         Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
      }

      public final void zza(int var1, IBinder var2, com.google.android.gms.common.internal.zzc var3) {
         BaseGmsClient var4 = this.zza;
         Preconditions.checkNotNull(var4, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
         Preconditions.checkNotNull(var3);
         var4.zza(var3);
         this.onPostInitComplete(var1, var2, var3.zza);
      }
   }

   protected final class zzf extends BaseGmsClient.zza {
      private final IBinder zza;

      public zzf(int var2, IBinder var3, Bundle var4) {
         super(var2, var4);
         this.zza = var3;
      }

      protected final void zza(ConnectionResult var1) {
         if (BaseGmsClient.this.zzx != null) {
            BaseGmsClient.this.zzx.onConnectionFailed(var1);
         }

         BaseGmsClient.this.onConnectionFailed(var1);
      }

      protected final boolean zza() {
         String var1;
         try {
            var1 = ((IBinder)Preconditions.checkNotNull(this.zza)).getInterfaceDescriptor();
         } catch (RemoteException var4) {
            Log.w("GmsClient", "service probably died");
            return false;
         }

         if (!BaseGmsClient.this.getServiceDescriptor().equals(var1)) {
            String var2 = BaseGmsClient.this.getServiceDescriptor();
            StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 34 + String.valueOf(var1).length());
            var3.append("service descriptor mismatch: ");
            var3.append(var2);
            var3.append(" vs. ");
            var3.append(var1);
            Log.e("GmsClient", var3.toString());
            return false;
         } else {
            IInterface var5 = BaseGmsClient.this.createServiceInterface(this.zza);
            if (var5 == null || !BaseGmsClient.this.zza(2, 4, var5) && !BaseGmsClient.this.zza(3, 4, var5)) {
               return false;
            } else {
               BaseGmsClient.this.zzaa = null;
               Bundle var6 = BaseGmsClient.this.getConnectionHint();
               if (BaseGmsClient.this.zzw != null) {
                  BaseGmsClient.this.zzw.onConnected(var6);
               }

               return true;
            }
         }
      }
   }

   protected final class zzg extends BaseGmsClient.zza {
      public zzg(int var2, Bundle var3) {
         super(var2, (Bundle)null);
      }

      protected final void zza(ConnectionResult var1) {
         if (BaseGmsClient.this.enableLocalFallback() && BaseGmsClient.this.zzc()) {
            BaseGmsClient.zza(BaseGmsClient.this, 16);
         } else {
            BaseGmsClient.this.zzb.onReportServiceBinding(var1);
            BaseGmsClient.this.onConnectionFailed(var1);
         }
      }

      protected final boolean zza() {
         BaseGmsClient.this.zzb.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
         return true;
      }
   }
}
