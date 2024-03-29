/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$Token
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.service.media.MediaBrowserService
 *  android.service.media.MediaBrowserService$BrowserRoot
 *  android.service.media.MediaBrowserService$Result
 */
package androidx.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaBrowserServiceCompatApi21 {
    private MediaBrowserServiceCompatApi21() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceCompatProxy);
    }

    public static void notifyChildrenChanged(Object object, String string2) {
        ((MediaBrowserService)object).notifyChildrenChanged(string2);
    }

    public static IBinder onBind(Object object, Intent intent) {
        return ((MediaBrowserService)object).onBind(intent);
    }

    public static void onCreate(Object object) {
        ((MediaBrowserService)object).onCreate();
    }

    public static void setSessionToken(Object object, Object object2) {
        ((MediaBrowserService)object).setSessionToken((MediaSession.Token)object2);
    }

    static class BrowserRoot {
        final Bundle mExtras;
        final String mRootId;

        BrowserRoot(String string2, Bundle bundle) {
            this.mRootId = string2;
            this.mExtras = bundle;
        }
    }

    static class MediaBrowserServiceAdaptor
    extends MediaBrowserService {
        final ServiceCompatProxy mServiceProxy;

        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            this.attachBaseContext(context);
            this.mServiceProxy = serviceCompatProxy;
        }

        public MediaBrowserService.BrowserRoot onGetRoot(String object, int n, Bundle object2) {
            MediaSessionCompat.ensureClassLoader(object2);
            ServiceCompatProxy serviceCompatProxy = this.mServiceProxy;
            Object var5_5 = null;
            object2 = object2 == null ? null : new Bundle(object2);
            object = serviceCompatProxy.onGetRoot((String)object, n, (Bundle)object2);
            if (object != null) return new MediaBrowserService.BrowserRoot(((BrowserRoot)object).mRootId, ((BrowserRoot)object).mExtras);
            return var5_5;
        }

        public void onLoadChildren(String string2, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
            this.mServiceProxy.onLoadChildren(string2, new ResultWrapper<List<Parcel>>(result));
        }
    }

    static class ResultWrapper<T> {
        MediaBrowserService.Result mResultObj;

        ResultWrapper(MediaBrowserService.Result result) {
            this.mResultObj = result;
        }

        public void detach() {
            this.mResultObj.detach();
        }

        List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> parcel) {
            if (parcel == null) {
                return null;
            }
            ArrayList<MediaBrowser.MediaItem> arrayList = new ArrayList<MediaBrowser.MediaItem>();
            Iterator<Parcel> iterator2 = parcel.iterator();
            while (iterator2.hasNext()) {
                parcel = iterator2.next();
                parcel.setDataPosition(0);
                arrayList.add((MediaBrowser.MediaItem)MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }

        public void sendResult(T object) {
            if (object instanceof List) {
                this.mResultObj.sendResult(this.parcelListToItemList((List)object));
                return;
            }
            if (object instanceof Parcel) {
                object = (Parcel)object;
                object.setDataPosition(0);
                this.mResultObj.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(object));
                object.recycle();
                return;
            }
            this.mResultObj.sendResult(null);
        }
    }

    public static interface ServiceCompatProxy {
        public BrowserRoot onGetRoot(String var1, int var2, Bundle var3);

        public void onLoadChildren(String var1, ResultWrapper<List<Parcel>> var2);
    }

}

