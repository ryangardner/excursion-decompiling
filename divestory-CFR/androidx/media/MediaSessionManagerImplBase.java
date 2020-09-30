/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 *  android.util.Log
 */
package androidx.media;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.util.ObjectsCompat;
import androidx.media.MediaSessionManager;

class MediaSessionManagerImplBase
implements MediaSessionManager.MediaSessionManagerImpl {
    private static final boolean DEBUG = MediaSessionManager.DEBUG;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String PERMISSION_MEDIA_CONTENT_CONTROL = "android.permission.MEDIA_CONTENT_CONTROL";
    private static final String PERMISSION_STATUS_BAR_SERVICE = "android.permission.STATUS_BAR_SERVICE";
    private static final String TAG = "MediaSessionManager";
    ContentResolver mContentResolver;
    Context mContext;

    MediaSessionManagerImplBase(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
    }

    private boolean isPermissionGranted(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl, String string2) {
        int n = remoteUserInfoImpl.getPid();
        boolean bl = true;
        boolean bl2 = true;
        if (n < 0) {
            if (this.mContext.getPackageManager().checkPermission(string2, remoteUserInfoImpl.getPackageName()) != 0) return false;
            return bl2;
        }
        if (this.mContext.checkPermission(string2, remoteUserInfoImpl.getPid(), remoteUserInfoImpl.getUid()) != 0) return false;
        return bl;
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    boolean isEnabledNotificationListener(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        String string2 = Settings.Secure.getString((ContentResolver)this.mContentResolver, (String)ENABLED_NOTIFICATION_LISTENERS);
        if (string2 == null) return false;
        String[] arrstring = string2.split(":");
        int n = 0;
        while (n < arrstring.length) {
            string2 = ComponentName.unflattenFromString((String)arrstring[n]);
            if (string2 != null && string2.getPackageName().equals(remoteUserInfoImpl.getPackageName())) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        boolean bl;
        block2 : {
            Object object;
            bl = false;
            try {
                object = this.mContext.getPackageManager().getApplicationInfo(remoteUserInfoImpl.getPackageName(), 0);
                if (((ApplicationInfo)object).uid == remoteUserInfoImpl.getUid()) break block2;
                if (!DEBUG) return false;
                object = new StringBuilder();
                ((StringBuilder)object).append("Package name ");
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                if (!DEBUG) return false;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package ");
                stringBuilder.append(remoteUserInfoImpl.getPackageName());
                stringBuilder.append(" doesn't exist");
                Log.d((String)TAG, (String)stringBuilder.toString());
                return false;
            }
            ((StringBuilder)object).append(remoteUserInfoImpl.getPackageName());
            ((StringBuilder)object).append(" doesn't match with the uid ");
            ((StringBuilder)object).append(remoteUserInfoImpl.getUid());
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
            return false;
        }
        if (this.isPermissionGranted(remoteUserInfoImpl, PERMISSION_STATUS_BAR_SERVICE)) return true;
        if (this.isPermissionGranted(remoteUserInfoImpl, PERMISSION_MEDIA_CONTENT_CONTROL)) return true;
        if (remoteUserInfoImpl.getUid() == 1000) return true;
        if (!this.isEnabledNotificationListener(remoteUserInfoImpl)) return bl;
        return true;
    }

    static class RemoteUserInfoImplBase
    implements MediaSessionManager.RemoteUserInfoImpl {
        private String mPackageName;
        private int mPid;
        private int mUid;

        RemoteUserInfoImplBase(String string2, int n, int n2) {
            this.mPackageName = string2;
            this.mPid = n;
            this.mUid = n2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof RemoteUserInfoImplBase)) {
                return false;
            }
            object = (RemoteUserInfoImplBase)object;
            if (!TextUtils.equals((CharSequence)this.mPackageName, (CharSequence)((RemoteUserInfoImplBase)object).mPackageName)) return false;
            if (this.mPid != ((RemoteUserInfoImplBase)object).mPid) return false;
            if (this.mUid != ((RemoteUserInfoImplBase)object).mUid) return false;
            return bl;
        }

        @Override
        public String getPackageName() {
            return this.mPackageName;
        }

        @Override
        public int getPid() {
            return this.mPid;
        }

        @Override
        public int getUid() {
            return this.mUid;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mPackageName, this.mPid, this.mUid);
        }
    }

}

