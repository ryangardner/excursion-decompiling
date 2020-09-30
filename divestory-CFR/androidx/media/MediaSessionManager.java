/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.session.MediaSessionManager
 *  android.media.session.MediaSessionManager$RemoteUserInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.util.Log;
import androidx.media.MediaSessionManagerImplApi21;
import androidx.media.MediaSessionManagerImplApi28;
import androidx.media.MediaSessionManagerImplBase;

public final class MediaSessionManager {
    static final boolean DEBUG = Log.isLoggable((String)"MediaSessionManager", (int)3);
    static final String TAG = "MediaSessionManager";
    private static final Object sLock = new Object();
    private static volatile MediaSessionManager sSessionManager;
    MediaSessionManagerImpl mImpl;

    private MediaSessionManager(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new MediaSessionManagerImplApi28(context);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaSessionManagerImplApi21(context);
            return;
        }
        this.mImpl = new MediaSessionManagerImplBase(context);
    }

    public static MediaSessionManager getSessionManager(Context context) {
        MediaSessionManager mediaSessionManager;
        MediaSessionManager mediaSessionManager2 = mediaSessionManager = sSessionManager;
        if (mediaSessionManager != null) return mediaSessionManager2;
        Object object = sLock;
        synchronized (object) {
            mediaSessionManager2 = mediaSessionManager = sSessionManager;
            if (mediaSessionManager != null) return mediaSessionManager2;
            mediaSessionManager2 = new MediaSessionManager(context.getApplicationContext());
            sSessionManager = mediaSessionManager2;
            return sSessionManager;
        }
    }

    Context getContext() {
        return this.mImpl.getContext();
    }

    public boolean isTrustedForMediaControl(RemoteUserInfo remoteUserInfo) {
        if (remoteUserInfo == null) throw new IllegalArgumentException("userInfo should not be null");
        return this.mImpl.isTrustedForMediaControl(remoteUserInfo.mImpl);
    }

    static interface MediaSessionManagerImpl {
        public Context getContext();

        public boolean isTrustedForMediaControl(RemoteUserInfoImpl var1);
    }

    public static final class RemoteUserInfo {
        public static final String LEGACY_CONTROLLER = "android.media.session.MediaController";
        RemoteUserInfoImpl mImpl;

        public RemoteUserInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            this.mImpl = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(remoteUserInfo);
        }

        public RemoteUserInfo(String string2, int n, int n2) {
            if (Build.VERSION.SDK_INT >= 28) {
                this.mImpl = new MediaSessionManagerImplApi28.RemoteUserInfoImplApi28(string2, n, n2);
                return;
            }
            this.mImpl = new MediaSessionManagerImplBase.RemoteUserInfoImplBase(string2, n, n2);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object instanceof RemoteUserInfo) return this.mImpl.equals(((RemoteUserInfo)object).mImpl);
            return false;
        }

        public String getPackageName() {
            return this.mImpl.getPackageName();
        }

        public int getPid() {
            return this.mImpl.getPid();
        }

        public int getUid() {
            return this.mImpl.getUid();
        }

        public int hashCode() {
            return this.mImpl.hashCode();
        }
    }

    static interface RemoteUserInfoImpl {
        public String getPackageName();

        public int getPid();

        public int getUid();
    }

}

