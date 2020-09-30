/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.FragmentManager
 *  android.app.Notification
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Looper
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.base.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zabj;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zabo;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zab;
import com.google.android.gms.common.internal.zac;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class GoogleApiAvailability
extends GoogleApiAvailabilityLight {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object zaa;
    private static final GoogleApiAvailability zab;
    private String zac;

    static {
        zaa = new Object();
        zab = new GoogleApiAvailability();
        GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    public static GoogleApiAvailability getInstance() {
        return zab;
    }

    public static Dialog zaa(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar((Context)activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)activity);
        builder.setView((View)progressBar);
        builder.setMessage((CharSequence)zac.zac((Context)activity, 18));
        builder.setPositiveButton((CharSequence)"", null);
        builder = builder.create();
        GoogleApiAvailability.zaa(activity, (Dialog)builder, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return builder;
    }

    static Dialog zaa(Context object, int n, zab zab2, DialogInterface.OnCancelListener object2) {
        AlertDialog.Builder builder = null;
        if (n == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        object.getTheme().resolveAttribute(16843529, typedValue, true);
        if ("Theme.Dialog.Alert".equals(object.getResources().getResourceEntryName(typedValue.resourceId))) {
            builder = new AlertDialog.Builder(object, 5);
        }
        typedValue = builder;
        if (builder == null) {
            typedValue = new AlertDialog.Builder(object);
        }
        typedValue.setMessage((CharSequence)zac.zac(object, n));
        if (object2 != null) {
            typedValue.setOnCancelListener(object2);
        }
        if ((object2 = zac.zae(object, n)) != null) {
            typedValue.setPositiveButton((CharSequence)object2, (DialogInterface.OnClickListener)zab2);
        }
        if ((object = zac.zaa(object, n)) != null) {
            typedValue.setTitle((CharSequence)object);
        }
        Log.w((String)"GoogleApiAvailability", (String)String.format("Creating dialog for Google Play services availability issue. ConnectionResult=%s", n), (Throwable)new IllegalArgumentException());
        return typedValue.create();
    }

    private static Task<Map<ApiKey<?>, String>> zaa(HasApiKey<?> hasApiKey, HasApiKey<?> ... arrhasApiKey) {
        Preconditions.checkNotNull(hasApiKey, "Requested API must not be null.");
        int n = arrhasApiKey.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                ArrayList arrayList = new ArrayList(arrhasApiKey.length + 1);
                arrayList.add(hasApiKey);
                arrayList.addAll(Arrays.asList(arrhasApiKey));
                return GoogleApiManager.zaa().zaa(arrayList);
            }
            Preconditions.checkNotNull(arrhasApiKey[n2], "Requested API must not be null.");
            ++n2;
        } while (true);
    }

    static final /* synthetic */ Task zaa(Map map) throws Exception {
        return Tasks.forResult(null);
    }

    private final String zaa() {
        Object object = zaa;
        synchronized (object) {
            return this.zac;
        }
    }

    static void zaa(Activity object, Dialog dialog, String string2, DialogInterface.OnCancelListener onCancelListener) {
        if (object instanceof FragmentActivity) {
            object = ((FragmentActivity)object).getSupportFragmentManager();
            SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show((androidx.fragment.app.FragmentManager)object, string2);
            return;
        }
        object = object.getFragmentManager();
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show((FragmentManager)object, string2);
    }

    private final void zaa(Context object, int n, String string2, PendingIntent object2) {
        Log.w((String)"GoogleApiAvailability", (String)String.format("GMS core API Availability. ConnectionResult=%s, tag=%s", n, null), (Throwable)new IllegalArgumentException());
        if (n == 18) {
            this.zaa((Context)object);
            return;
        }
        if (object2 == null) {
            if (n != 6) return;
            Log.w((String)"GoogleApiAvailability", (String)"Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
            return;
        }
        Object object3 = zac.zab((Context)object, n);
        string2 = zac.zad((Context)object, n);
        Resources resources = object.getResources();
        NotificationManager notificationManager = (NotificationManager)Preconditions.checkNotNull(object.getSystemService("notification"));
        object3 = new NotificationCompat.Builder((Context)object).setLocalOnly(true).setAutoCancel(true).setContentTitle((CharSequence)object3).setStyle(new NotificationCompat.BigTextStyle().bigText(string2));
        if (DeviceProperties.isWearable((Context)object)) {
            Preconditions.checkState(PlatformVersion.isAtLeastKitKatWatch());
            ((NotificationCompat.Builder)object3).setSmallIcon(object.getApplicationInfo().icon).setPriority(2);
            if (DeviceProperties.isWearableWithoutPlayStore((Context)object)) {
                ((NotificationCompat.Builder)object3).addAction(R.drawable.common_full_open_on_phone, resources.getString(R.string.common_open_on_phone), (PendingIntent)object2);
            } else {
                ((NotificationCompat.Builder)object3).setContentIntent((PendingIntent)object2);
            }
        } else {
            ((NotificationCompat.Builder)object3).setSmallIcon(17301642).setTicker(resources.getString(R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setContentIntent((PendingIntent)object2).setContentText(string2);
        }
        if (PlatformVersion.isAtLeastO()) {
            Preconditions.checkState(PlatformVersion.isAtLeastO());
            object2 = this.zaa();
            string2 = object2;
            if (object2 == null) {
                object2 = "com.google.android.gms.availability";
                resources = notificationManager.getNotificationChannel("com.google.android.gms.availability");
                object = zac.zaa((Context)object);
                if (resources == null) {
                    notificationManager.createNotificationChannel(new NotificationChannel("com.google.android.gms.availability", (CharSequence)object, 4));
                    string2 = object2;
                } else {
                    string2 = object2;
                    if (!((String)object).contentEquals(resources.getName())) {
                        resources.setName((CharSequence)object);
                        notificationManager.createNotificationChannel((NotificationChannel)resources);
                        string2 = object2;
                    }
                }
            }
            ((NotificationCompat.Builder)object3).setChannelId(string2);
        }
        object = ((NotificationCompat.Builder)object3).build();
        if (n != 1 && n != 2 && n != 3) {
            n = 39789;
        } else {
            n = 10436;
            GooglePlayServicesUtilLight.sCanceledAvailabilityNotification.set(false);
        }
        notificationManager.notify(n, (Notification)object);
    }

    static final /* synthetic */ Task zab(Map map) throws Exception {
        return Tasks.forResult(null);
    }

    public Task<Void> checkApiAvailability(GoogleApi<?> googleApi, GoogleApi<?> ... arrgoogleApi) {
        return GoogleApiAvailability.zaa(googleApi, arrgoogleApi).onSuccessTask(com.google.android.gms.common.zaa.zaa);
    }

    public Task<Void> checkApiAvailability(HasApiKey<?> hasApiKey, HasApiKey<?> ... arrhasApiKey) {
        return GoogleApiAvailability.zaa(hasApiKey, arrhasApiKey).onSuccessTask(com.google.android.gms.common.zab.zaa);
    }

    @Override
    public int getClientVersion(Context context) {
        return super.getClientVersion(context);
    }

    public Dialog getErrorDialog(Activity activity, int n, int n2) {
        return this.getErrorDialog(activity, n, n2, null);
    }

    public Dialog getErrorDialog(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        return GoogleApiAvailability.zaa((Context)activity, n, zab.zaa(activity, this.getErrorResolutionIntent((Context)activity, n, "d"), n2), onCancelListener);
    }

    public Dialog getErrorDialog(Fragment fragment, int n, int n2) {
        return this.getErrorDialog(fragment, n, n2, null);
    }

    public Dialog getErrorDialog(Fragment fragment, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        Intent intent = this.getErrorResolutionIntent(fragment.requireContext(), n, "d");
        return GoogleApiAvailability.zaa(fragment.requireContext(), n, zab.zaa(fragment, intent, n2), onCancelListener);
    }

    @Override
    public Intent getErrorResolutionIntent(Context context, int n, String string2) {
        return super.getErrorResolutionIntent(context, n, string2);
    }

    @Override
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n, int n2) {
        return super.getErrorResolutionPendingIntent(context, n, n2);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) return this.getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
        return connectionResult.getResolution();
    }

    @Override
    public final String getErrorString(int n) {
        return super.getErrorString(n);
    }

    @Override
    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    @Override
    public int isGooglePlayServicesAvailable(Context context, int n) {
        return super.isGooglePlayServicesAvailable(context, n);
    }

    @Override
    public final boolean isUserResolvableError(int n) {
        return super.isUserResolvableError(n);
    }

    public Task<Void> makeGooglePlayServicesAvailable(Activity object) {
        int n = GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Preconditions.checkMainThread("makeGooglePlayServicesAvailable must be called from the main thread");
        n = this.isGooglePlayServicesAvailable((Context)object, n);
        if (n == 0) {
            return Tasks.forResult(null);
        }
        object = zabo.zaa((Activity)object);
        ((zak)object).zab(new ConnectionResult(n, null), 0);
        return ((zabo)object).zac();
    }

    public void setDefaultNotificationChannelId(Context object, String string2) {
        if (PlatformVersion.isAtLeastO()) {
            Preconditions.checkNotNull(((NotificationManager)Preconditions.checkNotNull(object.getSystemService("notification"))).getNotificationChannel(string2));
        }
        object = zaa;
        synchronized (object) {
            this.zac = string2;
            return;
        }
    }

    public boolean showErrorDialogFragment(Activity activity, int n, int n2) {
        return this.showErrorDialogFragment(activity, n, n2, null);
    }

    public boolean showErrorDialogFragment(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog dialog = this.getErrorDialog(activity, n, n2, onCancelListener);
        if (dialog == null) {
            return false;
        }
        GoogleApiAvailability.zaa(activity, dialog, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int n) {
        this.zaa(context, n, null, this.getErrorResolutionPendingIntent(context, n, 0, "n"));
    }

    public void showErrorNotification(Context context, ConnectionResult connectionResult) {
        PendingIntent pendingIntent = this.getErrorResolutionPendingIntent(context, connectionResult);
        this.zaa(context, connectionResult.getErrorCode(), null, pendingIntent);
    }

    public final zabj zaa(Context context, zabl zabl2) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        zabj zabj2 = new zabj(zabl2);
        context.registerReceiver((BroadcastReceiver)zabj2, intentFilter);
        zabj2.zaa(context);
        if (this.isUninstalledAppPossiblyUpdating(context, GOOGLE_PLAY_SERVICES_PACKAGE)) return zabj2;
        zabl2.zaa();
        zabj2.zaa();
        return null;
    }

    final void zaa(Context context) {
        new zaa(context).sendEmptyMessageDelayed(1, 120000L);
    }

    public final boolean zaa(Activity activity, LifecycleFragment lifecycleFragment, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        if ((lifecycleFragment = GoogleApiAvailability.zaa((Context)activity, n, zab.zaa(lifecycleFragment, this.getErrorResolutionIntent((Context)activity, n, "d"), 2), onCancelListener)) == null) {
            return false;
        }
        GoogleApiAvailability.zaa(activity, (Dialog)lifecycleFragment, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    public final boolean zaa(Context context, ConnectionResult connectionResult, int n) {
        PendingIntent pendingIntent = this.getErrorResolutionPendingIntent(context, connectionResult);
        if (pendingIntent == null) return false;
        this.zaa(context, connectionResult.getErrorCode(), null, GoogleApiActivity.zaa(context, pendingIntent, n));
        return true;
    }

    private final class zaa
    extends zap {
        private final Context zaa;

        public zaa(Context context) {
            GoogleApiAvailability.this = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
            super((Looper)GoogleApiAvailability.this);
            this.zaa = context.getApplicationContext();
        }

        public final void handleMessage(Message object) {
            if (((Message)object).what != 1) {
                int n = ((Message)object).what;
                object = new StringBuilder(50);
                ((StringBuilder)object).append("Don't know how to handle this message: ");
                ((StringBuilder)object).append(n);
                Log.w((String)"GoogleApiAvailability", (String)((StringBuilder)object).toString());
                return;
            }
            int n = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.zaa);
            if (!GoogleApiAvailability.this.isUserResolvableError(n)) return;
            GoogleApiAvailability.this.showErrorNotification(this.zaa, n);
        }
    }

}

