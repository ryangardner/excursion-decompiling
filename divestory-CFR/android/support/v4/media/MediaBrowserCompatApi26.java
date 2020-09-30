/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.media.browse.MediaBrowser$SubscriptionCallback
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.List;

class MediaBrowserCompatApi26 {
    private MediaBrowserCompatApi26() {
    }

    static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy<SubscriptionCallback>(subscriptionCallback);
    }

    public static void subscribe(Object object, String string2, Bundle bundle, Object object2) {
        ((MediaBrowser)object).subscribe(string2, bundle, (MediaBrowser.SubscriptionCallback)object2);
    }

    public static void unsubscribe(Object object, String string2, Object object2) {
        ((MediaBrowser)object).unsubscribe(string2, (MediaBrowser.SubscriptionCallback)object2);
    }

    static interface SubscriptionCallback
    extends MediaBrowserCompatApi21.SubscriptionCallback {
        public void onChildrenLoaded(String var1, List<?> var2, Bundle var3);

        public void onError(String var1, Bundle var2);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback>
    extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
        SubscriptionCallbackProxy(T t) {
            super(t);
        }

        public void onChildrenLoaded(String string2, List<MediaBrowser.MediaItem> list, Bundle bundle) {
            MediaSessionCompat.ensureClassLoader(bundle);
            ((SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(string2, list, bundle);
        }

        public void onError(String string2, Bundle bundle) {
            MediaSessionCompat.ensureClassLoader(bundle);
            ((SubscriptionCallback)this.mSubscriptionCallback).onError(string2, bundle);
        }
    }

}

