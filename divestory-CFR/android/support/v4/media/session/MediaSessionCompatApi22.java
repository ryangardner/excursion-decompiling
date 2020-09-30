/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.media.session.MediaSession
 */
package android.support.v4.media.session;

import android.media.session.MediaSession;

class MediaSessionCompatApi22 {
    private MediaSessionCompatApi22() {
    }

    public static void setRatingType(Object object, int n) {
        ((MediaSession)object).setRatingType(n);
    }
}

