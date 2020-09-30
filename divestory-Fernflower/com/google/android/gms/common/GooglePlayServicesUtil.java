package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;

public final class GooglePlayServicesUtil extends GooglePlayServicesUtilLight {
   public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
   @Deprecated
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   @Deprecated
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
   public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

   static {
      GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
   }

   private GooglePlayServicesUtil() {
   }

   @Deprecated
   public static Dialog getErrorDialog(int var0, Activity var1, int var2) {
      return getErrorDialog(var0, var1, var2, (OnCancelListener)null);
   }

   @Deprecated
   public static Dialog getErrorDialog(int var0, Activity var1, int var2, OnCancelListener var3) {
      int var4 = var0;
      if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var0)) {
         var4 = 18;
      }

      return GoogleApiAvailability.getInstance().getErrorDialog(var1, var4, var2, var3);
   }

   @Deprecated
   public static PendingIntent getErrorPendingIntent(int var0, Context var1, int var2) {
      return GooglePlayServicesUtilLight.getErrorPendingIntent(var0, var1, var2);
   }

   @Deprecated
   public static String getErrorString(int var0) {
      return GooglePlayServicesUtilLight.getErrorString(var0);
   }

   public static Context getRemoteContext(Context var0) {
      return GooglePlayServicesUtilLight.getRemoteContext(var0);
   }

   public static Resources getRemoteResource(Context var0) {
      return GooglePlayServicesUtilLight.getRemoteResource(var0);
   }

   @Deprecated
   public static int isGooglePlayServicesAvailable(Context var0) {
      return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(var0);
   }

   @Deprecated
   public static int isGooglePlayServicesAvailable(Context var0, int var1) {
      return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(var0, var1);
   }

   @Deprecated
   public static boolean isUserRecoverableError(int var0) {
      return GooglePlayServicesUtilLight.isUserRecoverableError(var0);
   }

   @Deprecated
   public static boolean showErrorDialogFragment(int var0, Activity var1, int var2) {
      return showErrorDialogFragment(var0, var1, var2, (OnCancelListener)null);
   }

   @Deprecated
   public static boolean showErrorDialogFragment(int var0, Activity var1, int var2, OnCancelListener var3) {
      return showErrorDialogFragment(var0, var1, (Fragment)null, var2, var3);
   }

   public static boolean showErrorDialogFragment(int var0, Activity var1, Fragment var2, int var3, OnCancelListener var4) {
      int var5 = var0;
      if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var0)) {
         var5 = 18;
      }

      GoogleApiAvailability var6 = GoogleApiAvailability.getInstance();
      if (var2 == null) {
         return var6.showErrorDialogFragment(var1, var5, var3, var4);
      } else {
         Dialog var7 = GoogleApiAvailability.zaa(var1, var5, (com.google.android.gms.common.internal.zab)com.google.android.gms.common.internal.zab.zaa(var2, GoogleApiAvailability.getInstance().getErrorResolutionIntent(var1, var5, "d"), var3), (OnCancelListener)var4);
         if (var7 == null) {
            return false;
         } else {
            GoogleApiAvailability.zaa(var1, var7, "GooglePlayServicesErrorDialog", var4);
            return true;
         }
      }
   }

   @Deprecated
   public static void showErrorNotification(int var0, Context var1) {
      GoogleApiAvailability var2 = GoogleApiAvailability.getInstance();
      if (!GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var0) && !GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(var1, var0)) {
         var2.showErrorNotification(var1, var0);
      } else {
         var2.zaa(var1);
      }
   }
}
