package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zabj;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zabo;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zac;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
   private static final Object zaa = new Object();
   private static final GoogleApiAvailability zab = new GoogleApiAvailability();
   private String zac;

   static {
      GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
   }

   public static GoogleApiAvailability getInstance() {
      return zab;
   }

   public static Dialog zaa(Activity var0, OnCancelListener var1) {
      ProgressBar var2 = new ProgressBar(var0, (AttributeSet)null, 16842874);
      var2.setIndeterminate(true);
      var2.setVisibility(0);
      Builder var3 = new Builder(var0);
      var3.setView(var2);
      var3.setMessage(com.google.android.gms.common.internal.zac.zac(var0, 18));
      var3.setPositiveButton("", (OnClickListener)null);
      AlertDialog var4 = var3.create();
      zaa(var0, var4, "GooglePlayServicesUpdatingDialog", var1);
      return var4;
   }

   static Dialog zaa(Context var0, int var1, com.google.android.gms.common.internal.zab var2, OnCancelListener var3) {
      Builder var4 = null;
      if (var1 == 0) {
         return null;
      } else {
         TypedValue var5 = new TypedValue();
         var0.getTheme().resolveAttribute(16843529, var5, true);
         if ("Theme.Dialog.Alert".equals(var0.getResources().getResourceEntryName(var5.resourceId))) {
            var4 = new Builder(var0, 5);
         }

         Builder var8 = var4;
         if (var4 == null) {
            var8 = new Builder(var0);
         }

         var8.setMessage(com.google.android.gms.common.internal.zac.zac(var0, var1));
         if (var3 != null) {
            var8.setOnCancelListener(var3);
         }

         String var7 = com.google.android.gms.common.internal.zac.zae(var0, var1);
         if (var7 != null) {
            var8.setPositiveButton(var7, var2);
         }

         String var6 = com.google.android.gms.common.internal.zac.zaa(var0, var1);
         if (var6 != null) {
            var8.setTitle(var6);
         }

         Log.w("GoogleApiAvailability", String.format("Creating dialog for Google Play services availability issue. ConnectionResult=%s", var1), new IllegalArgumentException());
         return var8.create();
      }
   }

   private static Task<Map<ApiKey<?>, String>> zaa(HasApiKey<?> var0, HasApiKey<?>... var1) {
      Preconditions.checkNotNull(var0, "Requested API must not be null.");
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Preconditions.checkNotNull(var1[var3], "Requested API must not be null.");
      }

      ArrayList var4 = new ArrayList(var1.length + 1);
      var4.add(var0);
      var4.addAll(Arrays.asList(var1));
      return GoogleApiManager.zaa().zaa((Iterable)var4);
   }

   // $FF: synthetic method
   static final Task zaa(Map var0) throws Exception {
      return Tasks.forResult((Object)null);
   }

   private final String zaa() {
      // $FF: Couldn't be decompiled
   }

   static void zaa(Activity var0, Dialog var1, String var2, OnCancelListener var3) {
      if (var0 instanceof FragmentActivity) {
         FragmentManager var5 = ((FragmentActivity)var0).getSupportFragmentManager();
         SupportErrorDialogFragment.newInstance(var1, var3).show(var5, var2);
      } else {
         android.app.FragmentManager var4 = var0.getFragmentManager();
         ErrorDialogFragment.newInstance(var1, var3).show(var4, var2);
      }
   }

   private final void zaa(Context var1, int var2, String var3, PendingIntent var4) {
      Log.w("GoogleApiAvailability", String.format("GMS core API Availability. ConnectionResult=%s, tag=%s", var2, null), new IllegalArgumentException());
      if (var2 == 18) {
         this.zaa(var1);
      } else if (var4 == null) {
         if (var2 == 6) {
            Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
         }

      } else {
         String var5 = com.google.android.gms.common.internal.zac.zab(var1, var2);
         var3 = com.google.android.gms.common.internal.zac.zad(var1, var2);
         Resources var6 = var1.getResources();
         NotificationManager var7 = (NotificationManager)Preconditions.checkNotNull(var1.getSystemService("notification"));
         NotificationCompat.Builder var12 = (new NotificationCompat.Builder(var1)).setLocalOnly(true).setAutoCancel(true).setContentTitle(var5).setStyle((new NotificationCompat.BigTextStyle()).bigText(var3));
         if (DeviceProperties.isWearable(var1)) {
            Preconditions.checkState(PlatformVersion.isAtLeastKitKatWatch());
            var12.setSmallIcon(var1.getApplicationInfo().icon).setPriority(2);
            if (DeviceProperties.isWearableWithoutPlayStore(var1)) {
               var12.addAction(com.google.android.gms.base.R.drawable.common_full_open_on_phone, var6.getString(com.google.android.gms.base.R.string.common_open_on_phone), var4);
            } else {
               var12.setContentIntent(var4);
            }
         } else {
            var12.setSmallIcon(17301642).setTicker(var6.getString(com.google.android.gms.base.R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setContentIntent(var4).setContentText(var3);
         }

         if (PlatformVersion.isAtLeastO()) {
            Preconditions.checkState(PlatformVersion.isAtLeastO());
            String var11 = this.zaa();
            var3 = var11;
            if (var11 == null) {
               var11 = "com.google.android.gms.availability";
               NotificationChannel var13 = var7.getNotificationChannel("com.google.android.gms.availability");
               String var8 = com.google.android.gms.common.internal.zac.zaa(var1);
               if (var13 == null) {
                  var7.createNotificationChannel(new NotificationChannel("com.google.android.gms.availability", var8, 4));
                  var3 = var11;
               } else {
                  var3 = var11;
                  if (!var8.contentEquals(var13.getName())) {
                     var13.setName(var8);
                     var7.createNotificationChannel(var13);
                     var3 = var11;
                  }
               }
            }

            var12.setChannelId(var3);
         }

         Notification var9 = var12.build();
         char var10;
         if (var2 != 1 && var2 != 2 && var2 != 3) {
            var10 = '魭';
         } else {
            var10 = 10436;
            GooglePlayServicesUtilLight.sCanceledAvailabilityNotification.set(false);
         }

         var7.notify(var10, var9);
      }
   }

   // $FF: synthetic method
   static final Task zab(Map var0) throws Exception {
      return Tasks.forResult((Object)null);
   }

   public Task<Void> checkApiAvailability(GoogleApi<?> var1, GoogleApi<?>... var2) {
      return zaa((HasApiKey)var1, (HasApiKey[])var2).onSuccessTask(com.google.android.gms.common.zaa.zaa);
   }

   public Task<Void> checkApiAvailability(HasApiKey<?> var1, HasApiKey<?>... var2) {
      return zaa(var1, var2).onSuccessTask(com.google.android.gms.common.zab.zaa);
   }

   public int getClientVersion(Context var1) {
      return super.getClientVersion(var1);
   }

   public Dialog getErrorDialog(Activity var1, int var2, int var3) {
      return this.getErrorDialog((Activity)var1, var2, var3, (OnCancelListener)null);
   }

   public Dialog getErrorDialog(Activity var1, int var2, int var3, OnCancelListener var4) {
      return zaa(var1, var2, (com.google.android.gms.common.internal.zab)com.google.android.gms.common.internal.zab.zaa(var1, this.getErrorResolutionIntent(var1, var2, "d"), var3), (OnCancelListener)var4);
   }

   public Dialog getErrorDialog(Fragment var1, int var2, int var3) {
      return this.getErrorDialog((Fragment)var1, var2, var3, (OnCancelListener)null);
   }

   public Dialog getErrorDialog(Fragment var1, int var2, int var3, OnCancelListener var4) {
      Intent var5 = this.getErrorResolutionIntent(var1.requireContext(), var2, "d");
      return zaa(var1.requireContext(), var2, com.google.android.gms.common.internal.zab.zaa(var1, var5, var3), var4);
   }

   public Intent getErrorResolutionIntent(Context var1, int var2, String var3) {
      return super.getErrorResolutionIntent(var1, var2, var3);
   }

   public PendingIntent getErrorResolutionPendingIntent(Context var1, int var2, int var3) {
      return super.getErrorResolutionPendingIntent(var1, var2, var3);
   }

   public PendingIntent getErrorResolutionPendingIntent(Context var1, ConnectionResult var2) {
      return var2.hasResolution() ? var2.getResolution() : this.getErrorResolutionPendingIntent(var1, var2.getErrorCode(), 0);
   }

   public final String getErrorString(int var1) {
      return super.getErrorString(var1);
   }

   public int isGooglePlayServicesAvailable(Context var1) {
      return super.isGooglePlayServicesAvailable(var1);
   }

   public int isGooglePlayServicesAvailable(Context var1, int var2) {
      return super.isGooglePlayServicesAvailable(var1, var2);
   }

   public final boolean isUserResolvableError(int var1) {
      return super.isUserResolvableError(var1);
   }

   public Task<Void> makeGooglePlayServicesAvailable(Activity var1) {
      int var2 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
      Preconditions.checkMainThread("makeGooglePlayServicesAvailable must be called from the main thread");
      var2 = this.isGooglePlayServicesAvailable(var1, var2);
      if (var2 == 0) {
         return Tasks.forResult((Object)null);
      } else {
         zabo var3 = zabo.zaa(var1);
         var3.zab(new ConnectionResult(var2, (PendingIntent)null), 0);
         return var3.zac();
      }
   }

   public void setDefaultNotificationChannelId(Context param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean showErrorDialogFragment(Activity var1, int var2, int var3) {
      return this.showErrorDialogFragment(var1, var2, var3, (OnCancelListener)null);
   }

   public boolean showErrorDialogFragment(Activity var1, int var2, int var3, OnCancelListener var4) {
      Dialog var5 = this.getErrorDialog(var1, var2, var3, var4);
      if (var5 == null) {
         return false;
      } else {
         zaa(var1, var5, "GooglePlayServicesErrorDialog", var4);
         return true;
      }
   }

   public void showErrorNotification(Context var1, int var2) {
      this.zaa(var1, var2, (String)null, (PendingIntent)this.getErrorResolutionPendingIntent(var1, var2, 0, "n"));
   }

   public void showErrorNotification(Context var1, ConnectionResult var2) {
      PendingIntent var3 = this.getErrorResolutionPendingIntent(var1, var2);
      this.zaa(var1, var2.getErrorCode(), (String)null, (PendingIntent)var3);
   }

   public final zabj zaa(Context var1, zabl var2) {
      IntentFilter var3 = new IntentFilter("android.intent.action.PACKAGE_ADDED");
      var3.addDataScheme("package");
      zabj var4 = new zabj(var2);
      var1.registerReceiver(var4, var3);
      var4.zaa(var1);
      if (!this.isUninstalledAppPossiblyUpdating(var1, "com.google.android.gms")) {
         var2.zaa();
         var4.zaa();
         return null;
      } else {
         return var4;
      }
   }

   final void zaa(Context var1) {
      (new GoogleApiAvailability.zaa(var1)).sendEmptyMessageDelayed(1, 120000L);
   }

   public final boolean zaa(Activity var1, LifecycleFragment var2, int var3, int var4, OnCancelListener var5) {
      Dialog var6 = zaa(var1, var3, (com.google.android.gms.common.internal.zab)com.google.android.gms.common.internal.zab.zaa((LifecycleFragment)var2, this.getErrorResolutionIntent(var1, var3, "d"), 2), (OnCancelListener)var5);
      if (var6 == null) {
         return false;
      } else {
         zaa(var1, var6, "GooglePlayServicesErrorDialog", var5);
         return true;
      }
   }

   public final boolean zaa(Context var1, ConnectionResult var2, int var3) {
      PendingIntent var4 = this.getErrorResolutionPendingIntent(var1, var2);
      if (var4 != null) {
         this.zaa(var1, var2.getErrorCode(), (String)null, (PendingIntent)GoogleApiActivity.zaa(var1, var4, var3));
         return true;
      } else {
         return false;
      }
   }

   private final class zaa extends zap {
      private final Context zaa;

      public zaa(Context var2) {
         Looper var3;
         if (Looper.myLooper() == null) {
            var3 = Looper.getMainLooper();
         } else {
            var3 = Looper.myLooper();
         }

         super(var3);
         this.zaa = var2.getApplicationContext();
      }

      public final void handleMessage(Message var1) {
         int var2;
         if (var1.what != 1) {
            var2 = var1.what;
            StringBuilder var3 = new StringBuilder(50);
            var3.append("Don't know how to handle this message: ");
            var3.append(var2);
            Log.w("GoogleApiAvailability", var3.toString());
         } else {
            var2 = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.zaa);
            if (GoogleApiAvailability.this.isUserResolvableError(var2)) {
               GoogleApiAvailability.this.showErrorNotification(this.zaa, var2);
            }
         }

      }
   }
}
