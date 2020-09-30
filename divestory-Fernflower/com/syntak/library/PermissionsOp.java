package com.syntak.library;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PermissionsOp {
   public static final int ASK_ALL_PERMISSIONS = 9999;
   Activity activity;
   public HashMap<String, Boolean> permissions;
   private int permissions_to_request = 0;

   public PermissionsOp(Activity var1) {
      this.activity = var1;
      this.permissions = new HashMap();
   }

   public PermissionsOp(Activity var1, List<String> var2) {
      this.activity = var1;
      this.permissions = new HashMap();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         this.permissions.put(var4, false);
      }

   }

   public static boolean check_permission(Activity var0, String var1) {
      boolean var2;
      if (ContextCompat.checkSelfPermission(var0, var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void check_permissions(Activity var1) {
      this.permissions_to_request = 0;
      Iterator var2 = this.permissions.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if (ContextCompat.checkSelfPermission(var1, var3) == -1) {
            this.permissions.put(var3, false);
            ++this.permissions_to_request;
         } else {
            this.permissions.put(var3, true);
         }
      }

   }

   public static boolean isCameraAvailable(Context var0) {
      return var0.getPackageManager().hasSystemFeature("android.hardware.camera");
   }

   public static boolean isTelephonyAvailable(Context var0) {
      return var0.getPackageManager().hasSystemFeature("android.hardware.telephony");
   }

   public static void permissions_init() {
   }

   public static void request_permission(Activity var0, String var1) {
      ActivityCompat.requestPermissions(var0, new String[]{var1}, 9999);
   }

   private void request_permissions(Activity var1) {
      String[] var2 = new String[this.permissions_to_request];
      Iterator var3 = this.permissions.keySet().iterator();
      int var4 = 0;

      while(var3.hasNext()) {
         String var5 = (String)var3.next();
         if (!(Boolean)this.permissions.get(var5)) {
            var2[var4] = var5;
            ++var4;
         }
      }

      ActivityCompat.requestPermissions(var1, var2, 9999);
   }

   public boolean get_permission(String var1) {
      return (Boolean)this.permissions.get(var1);
   }

   public void put_permission(String var1, boolean var2) {
      this.permissions.put(var1, var2);
   }

   public boolean start() {
      this.check_permissions(this.activity);
      boolean var1;
      if (this.permissions_to_request > 0) {
         this.request_permissions(this.activity);
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
