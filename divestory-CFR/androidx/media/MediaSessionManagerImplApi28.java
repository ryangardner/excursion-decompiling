/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.session.MediaSessionManager
 *  android.media.session.MediaSessionManager$RemoteUserInfo
 */
package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import androidx.core.util.ObjectsCompat;
import androidx.media.MediaSessionManager;
import androidx.media.MediaSessionManagerImplApi21;

class MediaSessionManagerImplApi28
extends MediaSessionManagerImplApi21 {
    android.media.session.MediaSessionManager mObject;

    MediaSessionManagerImplApi28(Context context) {
        super(context);
        this.mObject = (android.media.session.MediaSessionManager)context.getSystemService("media_session");
    }

    @Override
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl remoteUserInfoImpl) {
        if (!(remoteUserInfoImpl instanceof RemoteUserInfoImplApi28)) return false;
        return this.mObject.isTrustedForMediaControl(((RemoteUserInfoImplApi28)remoteUserInfoImpl).mObject);
    }

    static final class RemoteUserInfoImplApi28
    implements MediaSessionManager.RemoteUserInfoImpl {
        final MediaSessionManager.RemoteUserInfo mObject;

        RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            this.mObject = remoteUserInfo;
        }

        RemoteUserInfoImplApi28(String string2, int n, int n2) {
            this.mObject = new MediaSessionManager.RemoteUserInfo(string2, n, n2);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof RemoteUserInfoImplApi28)) {
                return false;
            }
            object = (RemoteUserInfoImplApi28)object;
            return this.mObject.equals((Object)((RemoteUserInfoImplApi28)object).mObject);
        }

        @Override
        public String getPackageName() {
            return this.mObject.getPackageName();
        }

        @Override
        public int getPid() {
            return this.mObject.getPid();
        }

        @Override
        public int getUid() {
            return this.mObject.getUid();
        }

        public int hashCode() {
            return ObjectsCompat.hash(new Object[]{this.mObject});
        }
    }

}

