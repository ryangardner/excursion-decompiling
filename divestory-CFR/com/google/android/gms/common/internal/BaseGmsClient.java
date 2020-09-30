/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
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
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.IGmsCallbacks;
import com.google.android.gms.common.internal.IGmsServiceBroker;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.internal.common.zzi;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseGmsClient<T extends IInterface> {
    public static final int CONNECT_STATE_CONNECTED = 4;
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES;
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    private static final Feature[] zzd;
    final Handler zza;
    private ConnectionResult zzaa = null;
    private boolean zzab = false;
    private volatile com.google.android.gms.common.internal.zzc zzac = null;
    protected ConnectionProgressReportCallbacks zzb;
    protected AtomicInteger zzc = new AtomicInteger(0);
    private int zze;
    private long zzf;
    private long zzg;
    private int zzh;
    private long zzi;
    private volatile String zzj = null;
    private zzl zzk;
    private final Context zzl;
    private final Looper zzm;
    private final GmsClientSupervisor zzn;
    private final GoogleApiAvailabilityLight zzo;
    private final Object zzp = new Object();
    private final Object zzq = new Object();
    private IGmsServiceBroker zzr;
    private T zzs;
    private final ArrayList<zzc<?>> zzt = new ArrayList();
    private zzd zzu;
    private int zzv = 1;
    private final BaseConnectionCallbacks zzw;
    private final BaseOnConnectionFailedListener zzx;
    private final int zzy;
    private final String zzz;

    static {
        zzd = new Feature[0];
        GOOGLE_PLUS_REQUIRED_FEATURES = new String[]{"service_esmobile", "service_googleme"};
    }

    protected BaseGmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int n, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.zzl = Preconditions.checkNotNull(context, "Context must not be null");
        this.zza = Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzm = handler.getLooper();
        this.zzn = Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzo = Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzy = n;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = null;
    }

    protected BaseGmsClient(Context context, Looper looper, int n, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String string2) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailabilityLight.getInstance(), n, Preconditions.checkNotNull(baseConnectionCallbacks), Preconditions.checkNotNull(baseOnConnectionFailedListener), string2);
    }

    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int n, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String string2) {
        this.zzl = Preconditions.checkNotNull(context, "Context must not be null");
        this.zzm = Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzn = Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzo = Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zza = new zzb(looper);
        this.zzy = n;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = string2;
    }

    private final String zza() {
        String string2;
        String string3 = string2 = this.zzz;
        if (string2 != null) return string3;
        return this.zzl.getClass().getName();
    }

    private final void zza(int n) {
        if (this.zzb()) {
            n = 5;
            this.zzab = true;
        } else {
            n = 4;
        }
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(n, this.zzc.get(), 16));
    }

    private final void zza(int n, T object) {
        int n2;
        boolean bl = false;
        int n3 = n == 4 ? 1 : 0;
        if (n3 == (n2 = object != null)) {
            bl = true;
        }
        Preconditions.checkArgument(bl);
        Object object2 = this.zzp;
        synchronized (object2) {
            this.zzv = n;
            this.zzs = object;
            if (n != 1) {
                if (n != 2 && n != 3) {
                    if (n == 4) {
                        this.onConnectedLocked((T)((IInterface)Preconditions.checkNotNull(object)));
                    }
                } else {
                    String string2;
                    Object object3;
                    Object object4;
                    object = this.zzu;
                    if (object != null && this.zzk != null) {
                        object3 = this.zzk.zza();
                        string2 = this.zzk.zzb();
                        n3 = String.valueOf(object3).length();
                        n = String.valueOf(string2).length();
                        object4 = new StringBuilder(n3 + 70 + n);
                        ((StringBuilder)object4).append("Calling connect() while still connected, missing disconnect() for ");
                        ((StringBuilder)object4).append((String)object3);
                        ((StringBuilder)object4).append(" on ");
                        ((StringBuilder)object4).append(string2);
                        Log.e((String)"GmsClient", (String)((StringBuilder)object4).toString());
                        this.zzn.zza(Preconditions.checkNotNull(this.zzk.zza()), this.zzk.zzb(), this.zzk.zzc(), (ServiceConnection)object, this.zza(), this.zzk.zzd());
                        this.zzc.incrementAndGet();
                    }
                    this.zzu = object3 = new zzd(this.zzc.get());
                    object = this.zzv == 3 && this.getLocalStartServiceAction() != null ? new zzl(this.getContext().getPackageName(), this.getLocalStartServiceAction(), true, GmsClientSupervisor.getDefaultBindFlags(), false) : new zzl(this.getStartServicePackage(), this.getStartServiceAction(), false, GmsClientSupervisor.getDefaultBindFlags(), this.getUseDynamicLookup());
                    this.zzk = object;
                    if (((zzl)object).zzd() && this.getMinApkVersion() < 17895000) {
                        object = String.valueOf(this.zzk.zza());
                        object = ((String)object).length() != 0 ? "Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat((String)object) : new String("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ");
                        object3 = new IllegalStateException((String)object);
                        throw object3;
                    }
                    GmsClientSupervisor gmsClientSupervisor = this.zzn;
                    object = Preconditions.checkNotNull(this.zzk.zza());
                    String string3 = this.zzk.zzb();
                    n = this.zzk.zzc();
                    string2 = this.zza();
                    bl = this.zzk.zzd();
                    object4 = new GmsClientSupervisor.zza((String)object, string3, n, bl);
                    if (gmsClientSupervisor.zza((GmsClientSupervisor.zza)object4, (ServiceConnection)object3, string2)) return;
                    object4 = this.zzk.zza();
                    object = this.zzk.zzb();
                    n = String.valueOf(object4).length();
                    n3 = String.valueOf(object).length();
                    object3 = new StringBuilder(n + 34 + n3);
                    ((StringBuilder)object3).append("unable to connect to service: ");
                    ((StringBuilder)object3).append((String)object4);
                    ((StringBuilder)object3).append(" on ");
                    ((StringBuilder)object3).append((String)object);
                    Log.e((String)"GmsClient", (String)((StringBuilder)object3).toString());
                    this.zza(16, null, this.zzc.get());
                }
            } else {
                object = this.zzu;
                if (object == null) return;
                this.zzn.zza(Preconditions.checkNotNull(this.zzk.zza()), this.zzk.zzb(), this.zzk.zzc(), (ServiceConnection)object, this.zza(), this.zzk.zzd());
                this.zzu = null;
            }
            return;
        }
    }

    private final void zza(com.google.android.gms.common.internal.zzc zzc2) {
        this.zzac = zzc2;
    }

    private final boolean zza(int n, int n2, T t) {
        Object object = this.zzp;
        synchronized (object) {
            if (this.zzv != n) {
                return false;
            }
            this.zza(n2, t);
            return true;
        }
    }

    private final boolean zzb() {
        Object object = this.zzp;
        synchronized (object) {
            if (this.zzv != 3) return false;
            return true;
        }
    }

    private final boolean zzc() {
        if (this.zzab) {
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)this.getServiceDescriptor())) {
            return false;
        }
        if (TextUtils.isEmpty((CharSequence)this.getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(this.getServiceDescriptor());
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public void checkAvailabilityAndConnect() {
        int n = this.zzo.isGooglePlayServicesAvailable(this.zzl, this.getMinApkVersion());
        if (n != 0) {
            this.zza(1, null);
            this.triggerNotAvailable(new LegacyClientCallbackAdapter(), n, null);
            return;
        }
        this.connect(new LegacyClientCallbackAdapter());
    }

    protected final void checkConnected() {
        if (!this.isConnected()) throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
    }

    public void connect(ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzb = Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zza(2, null);
    }

    protected abstract T createServiceInterface(IBinder var1);

    /*
     * Enabled unnecessary exception pruning
     */
    public void disconnect() {
        this.zzc.incrementAndGet();
        Object object = this.zzt;
        synchronized (object) {
            int n = this.zzt.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.zzt.clear();
                    // MONITOREXIT [5, 7, 8] lbl10 : MonitorExitStatement: MONITOREXIT : var1_1
                    object = this.zzq;
                    synchronized (object) {
                        this.zzr = null;
                    }
                    this.zza(1, null);
                    return;
                }
                this.zzt.get(n2).zze();
                ++n2;
            } while (true);
        }
    }

    public void disconnect(String string2) {
        this.zzj = string2;
        this.disconnect();
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public void dump(String object, FileDescriptor object2, PrintWriter object3, String[] object4) {
        long l;
        Object object5;
        Object object6;
        int n;
        object2 = this.zzp;
        synchronized (object2) {
            n = this.zzv;
            object4 = this.zzs;
        }
        object2 = this.zzq;
        synchronized (object2) {
            object6 = this.zzr;
        }
        ((PrintWriter)object3).append((CharSequence)object).append("mConnectState=");
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            ((PrintWriter)object3).print("UNKNOWN");
                        } else {
                            ((PrintWriter)object3).print("DISCONNECTING");
                        }
                    } else {
                        ((PrintWriter)object3).print("CONNECTED");
                    }
                } else {
                    ((PrintWriter)object3).print("LOCAL_CONNECTING");
                }
            } else {
                ((PrintWriter)object3).print("REMOTE_CONNECTING");
            }
        } else {
            ((PrintWriter)object3).print("DISCONNECTED");
        }
        ((PrintWriter)object3).append(" mService=");
        if (object4 == null) {
            ((PrintWriter)object3).append("null");
        } else {
            ((PrintWriter)object3).append(this.getServiceDescriptor()).append("@").append(Integer.toHexString(System.identityHashCode((Object)object4.asBinder())));
        }
        ((PrintWriter)object3).append(" mServiceBroker=");
        if (object6 == null) {
            ((PrintWriter)object3).println("null");
        } else {
            ((PrintWriter)object3).append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode((Object)object6.asBinder())));
        }
        object2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzg > 0L) {
            object5 = ((PrintWriter)object3).append((CharSequence)object).append("lastConnectedTime=");
            l = this.zzg;
            object4 = ((DateFormat)object2).format(new Date(this.zzg));
            object6 = new StringBuilder(String.valueOf(object4).length() + 21);
            ((StringBuilder)object6).append(l);
            ((StringBuilder)object6).append(" ");
            ((StringBuilder)object6).append((String)object4);
            ((PrintWriter)object5).println(((StringBuilder)object6).toString());
        }
        if (this.zzf > 0L) {
            ((PrintWriter)object3).append((CharSequence)object).append("lastSuspendedCause=");
            n = this.zze;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        ((PrintWriter)object3).append(String.valueOf(n));
                    } else {
                        ((PrintWriter)object3).append("CAUSE_DEAD_OBJECT_EXCEPTION");
                    }
                } else {
                    ((PrintWriter)object3).append("CAUSE_NETWORK_LOST");
                }
            } else {
                ((PrintWriter)object3).append("CAUSE_SERVICE_DISCONNECTED");
            }
            object4 = ((PrintWriter)object3).append(" lastSuspendedTime=");
            l = this.zzf;
            object5 = ((DateFormat)object2).format(new Date(this.zzf));
            object6 = new StringBuilder(String.valueOf(object5).length() + 21);
            ((StringBuilder)object6).append(l);
            ((StringBuilder)object6).append(" ");
            ((StringBuilder)object6).append((String)object5);
            ((PrintWriter)object4).println(((StringBuilder)object6).toString());
        }
        if (this.zzi <= 0L) return;
        ((PrintWriter)object3).append((CharSequence)object).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzh));
        object = ((PrintWriter)object3).append(" lastFailedTime=");
        l = this.zzi;
        object3 = ((DateFormat)object2).format(new Date(this.zzi));
        object2 = new StringBuilder(String.valueOf(object3).length() + 21);
        ((StringBuilder)object2).append(l);
        ((StringBuilder)object2).append(" ");
        ((StringBuilder)object2).append((String)object3);
        ((PrintWriter)object).println(((StringBuilder)object2).toString());
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
        com.google.android.gms.common.internal.zzc zzc2 = this.zzac;
        if (zzc2 != null) return zzc2.zzb;
        return null;
    }

    public Bundle getConnectionHint() {
        return null;
    }

    public final Context getContext() {
        return this.zzl;
    }

    public String getEndpointPackageName() {
        if (!this.isConnected()) throw new RuntimeException("Failed to connect when checking package");
        zzl zzl2 = this.zzk;
        if (zzl2 == null) throw new RuntimeException("Failed to connect when checking package");
        return zzl2.zzb();
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

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void getRemoteService(IAccountAccessor object, Set<Scope> object2) {
        void var1_4;
        block15 : {
            Object object3 = this.getGetServiceRequestExtraArgs();
            GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzy);
            getServiceRequest.zza = this.zzl.getPackageName();
            getServiceRequest.zzd = object3;
            if (object2 != null) {
                getServiceRequest.zzc = object2.toArray(new Scope[object2.size()]);
            }
            if (this.requiresSignIn()) {
                void var2_10;
                Account account = this.getAccount();
                if (account == null) {
                    Account account2 = new Account("<<default account>>", "com.google");
                }
                getServiceRequest.zze = var2_10;
                if (object != null) {
                    getServiceRequest.zzb = object.asBinder();
                }
            } else if (this.requiresAccount()) {
                getServiceRequest.zze = this.getAccount();
            }
            getServiceRequest.zzf = zzd;
            getServiceRequest.zzg = this.getApiFeatures();
            if (this.usesClientTelemetry()) {
                getServiceRequest.zzh = true;
            }
            try {
                object = this.zzq;
                // MONITORENTER : object
                if (this.zzr == null) break block15;
                object3 = this.zzr;
            }
            catch (RuntimeException runtimeException) {
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            zze zze2 = new zze(this, this.zzc.get());
            object3.getService(zze2, getServiceRequest);
            return;
        }
        Log.w((String)"GmsClient", (String)"mServiceBroker is null, client disconnected");
        // MONITOREXIT : object
        return;
        Log.w((String)"GmsClient", (String)"IGmsServiceBroker.getService failed", (Throwable)var1_4);
        this.onPostInitHandler(8, null, null, this.zzc.get());
        return;
        catch (SecurityException securityException) {
            throw securityException;
        }
        catch (DeadObjectException deadObjectException) {
            Log.w((String)"GmsClient", (String)"IGmsServiceBroker.getService failed", (Throwable)deadObjectException);
            this.triggerConnectionSuspended(3);
            return;
        }
    }

    protected Set<Scope> getScopes() {
        return Collections.emptySet();
    }

    public final T getService() throws DeadObjectException {
        Object object = this.zzp;
        synchronized (object) {
            if (this.zzv != 5) {
                this.checkConnected();
                IInterface iInterface = (IInterface)Preconditions.checkNotNull(this.zzs, "Client is connected but service is null");
                return (T)iInterface;
            }
            DeadObjectException deadObjectException = new DeadObjectException();
            throw deadObjectException;
        }
    }

    public IBinder getServiceBrokerBinder() {
        Object object = this.zzq;
        synchronized (object) {
            if (this.zzr != null) return this.zzr.asBinder();
            return null;
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
        if (this.zzac != null) return null;
        return null;
    }

    protected boolean getUseDynamicLookup() {
        return false;
    }

    public boolean isConnected() {
        Object object = this.zzp;
        synchronized (object) {
            if (this.zzv != 4) return false;
            return true;
        }
    }

    public boolean isConnecting() {
        Object object = this.zzp;
        synchronized (object) {
            if (this.zzv == 2) return true;
            if (this.zzv != 3) return false;
            return true;
        }
    }

    protected void onConnectedLocked(T t) {
        this.zzg = System.currentTimeMillis();
    }

    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzh = connectionResult.getErrorCode();
        this.zzi = System.currentTimeMillis();
    }

    protected void onConnectionSuspended(int n) {
        this.zze = n;
        this.zzf = System.currentTimeMillis();
    }

    protected void onPostInitHandler(int n, IBinder iBinder, Bundle bundle, int n2) {
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(1, n2, -1, (Object)new zzf(n, iBinder, bundle)));
    }

    public void onUserSignOut(SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
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

    public void triggerConnectionSuspended(int n) {
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(6, this.zzc.get(), n));
    }

    protected void triggerNotAvailable(ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int n, PendingIntent pendingIntent) {
        this.zzb = Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        connectionProgressReportCallbacks = this.zza;
        connectionProgressReportCallbacks.sendMessage(connectionProgressReportCallbacks.obtainMessage(3, this.zzc.get(), n, (Object)pendingIntent));
    }

    public boolean usesClientTelemetry() {
        return false;
    }

    protected final void zza(int n, Bundle bundle, int n2) {
        bundle = this.zza;
        bundle.sendMessage(bundle.obtainMessage(7, n2, -1, (Object)new zzg(n, null)));
    }

    public static interface BaseConnectionCallbacks {
        public static final int CAUSE_DEAD_OBJECT_EXCEPTION = 3;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        public void onConnected(Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface BaseOnConnectionFailedListener {
        public void onConnectionFailed(ConnectionResult var1);
    }

    public static interface ConnectionProgressReportCallbacks {
        public void onReportServiceBinding(ConnectionResult var1);
    }

    protected class LegacyClientCallbackAdapter
    implements ConnectionProgressReportCallbacks {
        @Override
        public void onReportServiceBinding(ConnectionResult object) {
            if (((ConnectionResult)object).isSuccess()) {
                object = BaseGmsClient.this;
                ((BaseGmsClient)object).getRemoteService(null, ((BaseGmsClient)object).getScopes());
                return;
            }
            if (BaseGmsClient.this.zzx == null) return;
            BaseGmsClient.this.zzx.onConnectionFailed((ConnectionResult)object);
        }
    }

    public static interface SignOutCallbacks {
        public void onSignOutComplete();
    }

    private abstract class zza
    extends zzc<Boolean> {
        private final int zza;
        private final Bundle zzb;

        protected zza(int n, Bundle bundle) {
            super(true);
            this.zza = n;
            this.zzb = bundle;
        }

        @Override
        protected abstract void zza(ConnectionResult var1);

        @Override
        protected final /* synthetic */ void zza(Object object) {
            Boolean bl = (Boolean)object;
            object = null;
            if (bl == null) {
                BaseGmsClient.this.zza(1, null);
                return;
            }
            if (this.zza == 0) {
                if (this.zza()) return;
                BaseGmsClient.this.zza(1, null);
                this.zza(new ConnectionResult(8, null));
                return;
            }
            BaseGmsClient.this.zza(1, null);
            bl = this.zzb;
            if (bl != null) {
                object = (PendingIntent)bl.getParcelable("pendingIntent");
            }
            this.zza(new ConnectionResult(this.zza, (PendingIntent)object));
        }

        protected abstract boolean zza();

        @Override
        protected final void zzb() {
        }
    }

    final class zzb
    extends zzi {
        public zzb(Looper looper) {
            super(looper);
        }

        private static void zza(Message object) {
            object = (zzc)((Message)object).obj;
            ((zzc)object).zzb();
            ((zzc)object).zzd();
        }

        private static boolean zzb(Message message) {
            if (message.what == 2) return true;
            if (message.what == 1) return true;
            if (message.what != 7) return false;
            return true;
        }

        public final void handleMessage(Message object) {
            if (BaseGmsClient.this.zzc.get() != ((Message)object).arg1) {
                if (!zzb.zzb((Message)object)) return;
                zzb.zza((Message)object);
                return;
            }
            if ((((Message)object).what == 1 || ((Message)object).what == 7 || ((Message)object).what == 4 && !BaseGmsClient.this.enableLocalFallback() || ((Message)object).what == 5) && !BaseGmsClient.this.isConnecting()) {
                zzb.zza((Message)object);
                return;
            }
            int n = ((Message)object).what;
            PendingIntent pendingIntent = null;
            if (n == 4) {
                BaseGmsClient.this.zzaa = new ConnectionResult(((Message)object).arg2);
                if (BaseGmsClient.this.zzc() && !BaseGmsClient.this.zzab) {
                    BaseGmsClient.this.zza(3, null);
                    return;
                }
                object = BaseGmsClient.this.zzaa != null ? BaseGmsClient.this.zzaa : new ConnectionResult(8);
                BaseGmsClient.this.zzb.onReportServiceBinding((ConnectionResult)object);
                BaseGmsClient.this.onConnectionFailed((ConnectionResult)object);
                return;
            }
            if (((Message)object).what == 5) {
                object = BaseGmsClient.this.zzaa != null ? BaseGmsClient.this.zzaa : new ConnectionResult(8);
                BaseGmsClient.this.zzb.onReportServiceBinding((ConnectionResult)object);
                BaseGmsClient.this.onConnectionFailed((ConnectionResult)object);
                return;
            }
            if (((Message)object).what == 3) {
                if (((Message)object).obj instanceof PendingIntent) {
                    pendingIntent = (PendingIntent)((Message)object).obj;
                }
                object = new ConnectionResult(((Message)object).arg2, pendingIntent);
                BaseGmsClient.this.zzb.onReportServiceBinding((ConnectionResult)object);
                BaseGmsClient.this.onConnectionFailed((ConnectionResult)object);
                return;
            }
            if (((Message)object).what == 6) {
                BaseGmsClient.this.zza(5, null);
                if (BaseGmsClient.this.zzw != null) {
                    BaseGmsClient.this.zzw.onConnectionSuspended(((Message)object).arg2);
                }
                BaseGmsClient.this.onConnectionSuspended(((Message)object).arg2);
                BaseGmsClient.this.zza(5, 1, (T)null);
                return;
            }
            if (((Message)object).what == 2 && !BaseGmsClient.this.isConnected()) {
                zzb.zza((Message)object);
                return;
            }
            if (zzb.zzb((Message)object)) {
                ((zzc)((Message)object).obj).zzc();
                return;
            }
            n = ((Message)object).what;
            object = new StringBuilder(45);
            ((StringBuilder)object).append("Don't know how to handle message: ");
            ((StringBuilder)object).append(n);
            Log.wtf((String)"GmsClient", (String)((StringBuilder)object).toString(), (Throwable)new Exception());
        }
    }

    protected abstract class zzc<TListener> {
        private TListener zza;
        private boolean zzb;

        public zzc(TListener TListener) {
            this.zza = TListener;
            this.zzb = false;
        }

        protected abstract void zza(TListener var1);

        protected abstract void zzb();

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        public final void zzc() {
            // MONITORENTER : this
            TListener TListener = this.zza;
            if (this.zzb) {
                String string2 = String.valueOf(this);
                int n = String.valueOf(string2).length();
                StringBuilder stringBuilder = new StringBuilder(n + 47);
                stringBuilder.append("Callback proxy ");
                stringBuilder.append(string2);
                stringBuilder.append(" being reused. This is not safe.");
                Log.w((String)"GmsClient", (String)stringBuilder.toString());
            }
            // MONITOREXIT : this
            if (TListener != null) {
                try {
                    this.zza(TListener);
                }
                catch (RuntimeException runtimeException) {
                    this.zzb();
                    throw runtimeException;
                }
            } else {
                this.zzb();
            }
            // MONITORENTER : this
            this.zzb = true;
            // MONITOREXIT : this
            this.zzd();
        }

        public final void zzd() {
            this.zze();
            ArrayList arrayList = BaseGmsClient.this.zzt;
            synchronized (arrayList) {
                BaseGmsClient.this.zzt.remove(this);
                return;
            }
        }

        public final void zze() {
            synchronized (this) {
                this.zza = null;
                return;
            }
        }
    }

    public final class zzd
    implements ServiceConnection {
        private final int zza;

        public zzd(int n) {
            this.zza = n;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        public final void onServiceConnected(ComponentName object, IBinder iBinder) {
            if (iBinder == null) {
                BaseGmsClient.this.zza(16);
                return;
            }
            Object object2 = BaseGmsClient.this.zzq;
            synchronized (object2) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                object = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                object = object != null && object instanceof IGmsServiceBroker ? (IGmsServiceBroker)object : new IGmsServiceBroker.Stub.zza(iBinder);
                baseGmsClient.zzr = (IGmsServiceBroker)object;
            }
            BaseGmsClient.this.zza(0, null, this.zza);
        }

        /*
         * Enabled unnecessary exception pruning
         */
        public final void onServiceDisconnected(ComponentName object) {
            object = BaseGmsClient.this.zzq;
            synchronized (object) {
                BaseGmsClient.this.zzr = null;
            }
            BaseGmsClient.this.zza.sendMessage(BaseGmsClient.this.zza.obtainMessage(6, this.zza, 1));
        }
    }

    public static final class zze
    extends IGmsCallbacks.zza {
        private BaseGmsClient zza;
        private final int zzb;

        public zze(BaseGmsClient baseGmsClient, int n) {
            this.zza = baseGmsClient;
            this.zzb = n;
        }

        @Override
        public final void onPostInitComplete(int n, IBinder iBinder, Bundle bundle) {
            Preconditions.checkNotNull(this.zza, "onPostInitComplete can be called only once per call to getRemoteService");
            this.zza.onPostInitHandler(n, iBinder, bundle, this.zzb);
            this.zza = null;
        }

        @Override
        public final void zza(int n, Bundle bundle) {
            Log.wtf((String)"GmsClient", (String)"received deprecated onAccountValidationComplete callback, ignoring", (Throwable)new Exception());
        }

        @Override
        public final void zza(int n, IBinder iBinder, com.google.android.gms.common.internal.zzc zzc2) {
            BaseGmsClient baseGmsClient = this.zza;
            Preconditions.checkNotNull(baseGmsClient, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
            Preconditions.checkNotNull(zzc2);
            baseGmsClient.zza(zzc2);
            this.onPostInitComplete(n, iBinder, zzc2.zza);
        }
    }

    protected final class zzf
    extends zza {
        private final IBinder zza;

        public zzf(int n, IBinder iBinder, Bundle bundle) {
            super(n, bundle);
            this.zza = iBinder;
        }

        @Override
        protected final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        @Override
        protected final boolean zza() {
            String string2;
            block3 : {
                StringBuilder stringBuilder;
                String string3;
                try {
                    string2 = Preconditions.checkNotNull(this.zza).getInterfaceDescriptor();
                    if (BaseGmsClient.this.getServiceDescriptor().equals(string2)) break block3;
                    string3 = BaseGmsClient.this.getServiceDescriptor();
                    stringBuilder = new StringBuilder(String.valueOf(string3).length() + 34 + String.valueOf(string2).length());
                    stringBuilder.append("service descriptor mismatch: ");
                }
                catch (RemoteException remoteException) {
                    Log.w((String)"GmsClient", (String)"service probably died");
                    return false;
                }
                stringBuilder.append(string3);
                stringBuilder.append(" vs. ");
                stringBuilder.append(string2);
                Log.e((String)"GmsClient", (String)stringBuilder.toString());
                return false;
            }
            string2 = BaseGmsClient.this.createServiceInterface(this.zza);
            if (string2 == null) return false;
            if (!BaseGmsClient.this.zza(2, 4, (T)((IInterface)string2))) {
                if (!BaseGmsClient.this.zza(3, 4, (T)((IInterface)string2))) return false;
            }
            BaseGmsClient.this.zzaa = null;
            string2 = BaseGmsClient.this.getConnectionHint();
            if (BaseGmsClient.this.zzw == null) return true;
            BaseGmsClient.this.zzw.onConnected((Bundle)string2);
            return true;
        }
    }

    protected final class zzg
    extends zza {
        public zzg(int n, Bundle bundle) {
            super(n, null);
        }

        @Override
        protected final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.enableLocalFallback() && BaseGmsClient.this.zzc()) {
                BaseGmsClient.this.zza(16);
                return;
            }
            BaseGmsClient.this.zzb.onReportServiceBinding(connectionResult);
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        @Override
        protected final boolean zza() {
            BaseGmsClient.this.zzb.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
            return true;
        }
    }

}

