package com.google.android.gms.common.internal;

import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;

public final class zzj {
   private static final Uri zza;
   private static final Uri zzb;

   static {
      Uri var0 = Uri.parse("https://plus.google.com/");
      zza = var0;
      zzb = var0.buildUpon().appendPath("circles").appendPath("find").build();
   }

   public static Intent zza() {
      Intent var0 = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
      var0.setPackage("com.google.android.wearable.app");
      return var0;
   }

   public static Intent zza(String var0) {
      Uri var1 = Uri.fromParts("package", var0, (String)null);
      Intent var2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
      var2.setData(var1);
      return var2;
   }

   public static Intent zza(String var0, String var1) {
      Intent var2 = new Intent("android.intent.action.VIEW");
      Builder var3 = Uri.parse("market://details").buildUpon().appendQueryParameter("id", var0);
      if (!TextUtils.isEmpty(var1)) {
         var3.appendQueryParameter("pcampaignid", var1);
      }

      var2.setData(var3.build());
      var2.setPackage("com.android.vending");
      var2.addFlags(524288);
      return var2;
   }
}
