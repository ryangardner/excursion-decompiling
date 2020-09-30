/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zar;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zae;
import com.google.android.gms.signin.internal.zah;
import com.google.android.gms.signin.internal.zak;
import com.google.android.gms.signin.internal.zam;
import com.google.android.gms.signin.zad;

public class SignInClientImpl
extends GmsClient<zae>
implements zad {
    private final boolean zaa;
    private final ClientSettings zab;
    private final Bundle zac;
    private final Integer zad;

    public SignInClientImpl(Context context, Looper looper, boolean bl, ClientSettings clientSettings, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zaa = bl;
        this.zab = clientSettings;
        this.zac = bundle;
        this.zad = clientSettings.zad();
    }

    public SignInClientImpl(Context context, Looper looper, boolean bl, ClientSettings clientSettings, SignInOptions signInOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, clientSettings, SignInClientImpl.createBundleFromClientSettings(clientSettings), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle createBundleFromClientSettings(ClientSettings clientSettings) {
        SignInOptions signInOptions = clientSettings.zac();
        Integer n = clientSettings.zad();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", (Parcelable)clientSettings.getAccount());
        if (n != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", n.intValue());
        }
        if (signInOptions == null) return bundle;
        bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", false);
        bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", false);
        bundle.putString("com.google.android.gms.signin.internal.serverClientId", null);
        bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
        bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", false);
        bundle.putString("com.google.android.gms.signin.internal.hostedDomain", null);
        bundle.putString("com.google.android.gms.signin.internal.logSessionId", null);
        bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", false);
        return bundle;
    }

    @Override
    protected /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        if (!(iInterface instanceof zae)) return new zah(iBinder);
        return (zae)iInterface;
    }

    @Override
    protected Bundle getGetServiceRequestExtraArgs() {
        String string2 = this.zab.getRealClientPackageName();
        if (this.getContext().getPackageName().equals(string2)) return this.zac;
        this.zac.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zab.getRealClientPackageName());
        return this.zac;
    }

    @Override
    public int getMinApkVersion() {
        return 12451000;
    }

    @Override
    protected String getServiceDescriptor() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    @Override
    protected String getStartServiceAction() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override
    public boolean requiresSignIn() {
        return this.zaa;
    }

    @Override
    public final void zaa() {
        try {
            ((zae)this.getService()).zaa(Preconditions.checkNotNull(this.zad));
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when clearAccountFromSessionStore is called");
            return;
        }
    }

    @Override
    public final void zaa(IAccountAccessor iAccountAccessor, boolean bl) {
        try {
            ((zae)this.getService()).zaa(iAccountAccessor, Preconditions.checkNotNull(this.zad), bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when saveDefaultAccount is called");
            return;
        }
    }

    @Override
    public final void zaa(zac zac2) {
        Preconditions.checkNotNull(zac2, "Expecting a valid ISignInCallbacks");
        try {
            Object object = this.zab.getAccountOrDefault();
            AbstractSafeParcelable abstractSafeParcelable = null;
            if ("<<default account>>".equals(((Account)object).name)) {
                abstractSafeParcelable = Storage.getInstance(this.getContext()).getSavedDefaultGoogleSignInAccount();
            }
            zar zar2 = new zar((Account)object, Preconditions.checkNotNull(this.zad), (GoogleSignInAccount)abstractSafeParcelable);
            object = (zae)this.getService();
            abstractSafeParcelable = new zak(zar2);
            object.zaa((zak)abstractSafeParcelable, zac2);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when signIn is called");
            try {
                zam zam2 = new zam(8);
                zac2.zaa(zam2);
                return;
            }
            catch (RemoteException remoteException2) {
                Log.wtf((String)"SignInClientImpl", (String)"ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", (Throwable)remoteException);
                return;
            }
        }
    }

    @Override
    public final void zab() {
        this.connect(new BaseGmsClient.LegacyClientCallbackAdapter((BaseGmsClient)this));
    }
}

