package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.IntentSender.SendIntentException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.Preconditions;

public class GoogleApiActivity extends Activity implements OnCancelListener {
   private int zaa = 0;

   public static PendingIntent zaa(Context var0, PendingIntent var1, int var2) {
      return PendingIntent.getActivity(var0, 0, zaa(var0, var1, var2, true), 134217728);
   }

   public static Intent zaa(Context var0, PendingIntent var1, int var2, boolean var3) {
      Intent var4 = new Intent(var0, GoogleApiActivity.class);
      var4.putExtra("pending_intent", var1);
      var4.putExtra("failing_client_id", var2);
      var4.putExtra("notify_manager", var3);
      return var4;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if (var1 == 1) {
         boolean var4 = this.getIntent().getBooleanExtra("notify_manager", true);
         this.zaa = 0;
         this.setResult(var2, var3);
         if (var4) {
            GoogleApiManager var5 = GoogleApiManager.zaa((Context)this);
            if (var2 != -1) {
               if (var2 == 0) {
                  var5.zab(new ConnectionResult(13, (PendingIntent)null), this.getIntent().getIntExtra("failing_client_id", -1));
               }
            } else {
               var5.zac();
            }
         }
      } else if (var1 == 2) {
         this.zaa = 0;
         this.setResult(var2, var3);
      }

      this.finish();
   }

   public void onCancel(DialogInterface var1) {
      this.zaa = 0;
      this.setResult(0);
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (var1 != null) {
         this.zaa = var1.getInt("resolution");
      }

      if (this.zaa != 1) {
         var1 = this.getIntent().getExtras();
         if (var1 == null) {
            Log.e("GoogleApiActivity", "Activity started without extras");
            this.finish();
            return;
         }

         PendingIntent var2 = (PendingIntent)var1.get("pending_intent");
         Integer var3 = (Integer)var1.get("error_code");
         if (var2 == null && var3 == null) {
            Log.e("GoogleApiActivity", "Activity started without resolution");
            this.finish();
            return;
         }

         if (var2 != null) {
            try {
               this.startIntentSenderForResult(var2.getIntentSender(), 1, (Intent)null, 0, 0, 0);
               this.zaa = 1;
               return;
            } catch (ActivityNotFoundException var5) {
               if (var1.getBoolean("notify_manager", true)) {
                  GoogleApiManager.zaa((Context)this).zab(new ConnectionResult(22, (PendingIntent)null), this.getIntent().getIntExtra("failing_client_id", -1));
               } else {
                  String var9 = String.valueOf(var2);
                  StringBuilder var7 = new StringBuilder(String.valueOf(var9).length() + 36);
                  var7.append("Activity not found while launching ");
                  var7.append(var9);
                  var7.append(".");
                  var9 = var7.toString();
                  String var8 = var9;
                  if (Build.FINGERPRINT.contains("generic")) {
                     var8 = String.valueOf(var9).concat(" This may occur when resolving Google Play services connection issues on emulators with Google APIs but not Google Play Store.");
                  }

                  Log.e("GoogleApiActivity", var8, var5);
               }

               this.zaa = 1;
               this.finish();
               return;
            } catch (SendIntentException var6) {
               Log.e("GoogleApiActivity", "Failed to launch pendingIntent", var6);
               this.finish();
               return;
            }
         }

         int var4 = (Integer)Preconditions.checkNotNull(var3);
         GoogleApiAvailability.getInstance().showErrorDialogFragment(this, var4, 2, this);
         this.zaa = 1;
      }

   }

   protected void onSaveInstanceState(Bundle var1) {
      var1.putInt("resolution", this.zaa);
      super.onSaveInstanceState(var1);
   }
}
