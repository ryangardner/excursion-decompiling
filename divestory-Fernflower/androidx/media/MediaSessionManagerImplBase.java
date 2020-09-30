package androidx.media;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.util.ObjectsCompat;

class MediaSessionManagerImplBase implements MediaSessionManager.MediaSessionManagerImpl {
   private static final boolean DEBUG;
   private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
   private static final String PERMISSION_MEDIA_CONTENT_CONTROL = "android.permission.MEDIA_CONTENT_CONTROL";
   private static final String PERMISSION_STATUS_BAR_SERVICE = "android.permission.STATUS_BAR_SERVICE";
   private static final String TAG = "MediaSessionManager";
   ContentResolver mContentResolver;
   Context mContext;

   static {
      DEBUG = MediaSessionManager.DEBUG;
   }

   MediaSessionManagerImplBase(Context var1) {
      this.mContext = var1;
      this.mContentResolver = var1.getContentResolver();
   }

   private boolean isPermissionGranted(MediaSessionManager.RemoteUserInfoImpl var1, String var2) {
      int var3 = var1.getPid();
      boolean var4 = true;
      boolean var5 = true;
      if (var3 < 0) {
         if (this.mContext.getPackageManager().checkPermission(var2, var1.getPackageName()) != 0) {
            var5 = false;
         }

         return var5;
      } else {
         if (this.mContext.checkPermission(var2, var1.getPid(), var1.getUid()) == 0) {
            var5 = var4;
         } else {
            var5 = false;
         }

         return var5;
      }
   }

   public Context getContext() {
      return this.mContext;
   }

   boolean isEnabledNotificationListener(MediaSessionManager.RemoteUserInfoImpl var1) {
      String var2 = Secure.getString(this.mContentResolver, "enabled_notification_listeners");
      if (var2 != null) {
         String[] var3 = var2.split(":");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ComponentName var5 = ComponentName.unflattenFromString(var3[var4]);
            if (var5 != null && var5.getPackageName().equals(var1.getPackageName())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl var1) {
      boolean var2 = false;

      StringBuilder var3;
      ApplicationInfo var5;
      try {
         var5 = this.mContext.getPackageManager().getApplicationInfo(var1.getPackageName(), 0);
      } catch (NameNotFoundException var4) {
         if (DEBUG) {
            var3 = new StringBuilder();
            var3.append("Package ");
            var3.append(var1.getPackageName());
            var3.append(" doesn't exist");
            Log.d("MediaSessionManager", var3.toString());
         }

         return false;
      }

      if (var5.uid != var1.getUid()) {
         if (DEBUG) {
            var3 = new StringBuilder();
            var3.append("Package name ");
            var3.append(var1.getPackageName());
            var3.append(" doesn't match with the uid ");
            var3.append(var1.getUid());
            Log.d("MediaSessionManager", var3.toString());
         }

         return false;
      } else {
         if (this.isPermissionGranted(var1, "android.permission.STATUS_BAR_SERVICE") || this.isPermissionGranted(var1, "android.permission.MEDIA_CONTENT_CONTROL") || var1.getUid() == 1000 || this.isEnabledNotificationListener(var1)) {
            var2 = true;
         }

         return var2;
      }
   }

   static class RemoteUserInfoImplBase implements MediaSessionManager.RemoteUserInfoImpl {
      private String mPackageName;
      private int mPid;
      private int mUid;

      RemoteUserInfoImplBase(String var1, int var2, int var3) {
         this.mPackageName = var1;
         this.mPid = var2;
         this.mUid = var3;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof MediaSessionManagerImplBase.RemoteUserInfoImplBase)) {
            return false;
         } else {
            MediaSessionManagerImplBase.RemoteUserInfoImplBase var3 = (MediaSessionManagerImplBase.RemoteUserInfoImplBase)var1;
            if (!TextUtils.equals(this.mPackageName, var3.mPackageName) || this.mPid != var3.mPid || this.mUid != var3.mUid) {
               var2 = false;
            }

            return var2;
         }
      }

      public String getPackageName() {
         return this.mPackageName;
      }

      public int getPid() {
         return this.mPid;
      }

      public int getUid() {
         return this.mUid;
      }

      public int hashCode() {
         return ObjectsCompat.hash(this.mPackageName, this.mPid, this.mUid);
      }
   }
}
