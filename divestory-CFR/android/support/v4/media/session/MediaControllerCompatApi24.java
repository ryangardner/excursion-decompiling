/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.media.session.MediaController
 *  android.media.session.MediaController$TransportControls
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;

class MediaControllerCompatApi24 {
    private MediaControllerCompatApi24() {
    }

    public static class TransportControls {
        private TransportControls() {
        }

        public static void prepare(Object object) {
            ((MediaController.TransportControls)object).prepare();
        }

        public static void prepareFromMediaId(Object object, String string2, Bundle bundle) {
            ((MediaController.TransportControls)object).prepareFromMediaId(string2, bundle);
        }

        public static void prepareFromSearch(Object object, String string2, Bundle bundle) {
            ((MediaController.TransportControls)object).prepareFromSearch(string2, bundle);
        }

        public static void prepareFromUri(Object object, Uri uri, Bundle bundle) {
            ((MediaController.TransportControls)object).prepareFromUri(uri, bundle);
        }
    }

}

