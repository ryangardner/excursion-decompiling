/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.BadParcelableException
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi23;
import android.support.v4.media.MediaBrowserCompatApi26;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.media.MediaBrowserCompatUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MediaBrowserCompat {
    public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
    public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
    static final boolean DEBUG = Log.isLoggable((String)"MediaBrowserCompat", (int)3);
    public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
    public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    static final String TAG = "MediaBrowserCompat";
    private final MediaBrowserImpl mImpl;

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = new MediaBrowserImplApi26(context, componentName, connectionCallback, bundle);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserImplApi23(context, componentName, connectionCallback, bundle);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserImplApi21(context, componentName, connectionCallback, bundle);
            return;
        }
        this.mImpl = new MediaBrowserImplBase(context, componentName, connectionCallback, bundle);
    }

    public void connect() {
        this.mImpl.connect();
    }

    public void disconnect() {
        this.mImpl.disconnect();
    }

    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    public void getItem(String string2, ItemCallback itemCallback) {
        this.mImpl.getItem(string2, itemCallback);
    }

    public Bundle getNotifyChildrenChangedOptions() {
        return this.mImpl.getNotifyChildrenChangedOptions();
    }

    public String getRoot() {
        return this.mImpl.getRoot();
    }

    public ComponentName getServiceComponent() {
        return this.mImpl.getServiceComponent();
    }

    public MediaSessionCompat.Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public boolean isConnected() {
        return this.mImpl.isConnected();
    }

    public void search(String string2, Bundle bundle, SearchCallback searchCallback) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("query cannot be empty");
        if (searchCallback == null) throw new IllegalArgumentException("callback cannot be null");
        this.mImpl.search(string2, bundle, searchCallback);
    }

    public void sendCustomAction(String string2, Bundle bundle, CustomActionCallback customActionCallback) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("action cannot be empty");
        this.mImpl.sendCustomAction(string2, bundle, customActionCallback);
    }

    public void subscribe(String string2, Bundle bundle, SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("parentId is empty");
        if (subscriptionCallback == null) throw new IllegalArgumentException("callback is null");
        if (bundle == null) throw new IllegalArgumentException("options are null");
        this.mImpl.subscribe(string2, bundle, subscriptionCallback);
    }

    public void subscribe(String string2, SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("parentId is empty");
        if (subscriptionCallback == null) throw new IllegalArgumentException("callback is null");
        this.mImpl.subscribe(string2, null, subscriptionCallback);
    }

    public void unsubscribe(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("parentId is empty");
        this.mImpl.unsubscribe(string2, null);
    }

    public void unsubscribe(String string2, SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("parentId is empty");
        if (subscriptionCallback == null) throw new IllegalArgumentException("callback is null");
        this.mImpl.unsubscribe(string2, subscriptionCallback);
    }

    private static class CallbackHandler
    extends Handler {
        private final WeakReference<MediaBrowserServiceCallbackImpl> mCallbackImplRef;
        private WeakReference<Messenger> mCallbacksMessengerRef;

        CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            this.mCallbackImplRef = new WeakReference<MediaBrowserServiceCallbackImpl>(mediaBrowserServiceCallbackImpl);
        }

        public void handleMessage(Message message) {
            WeakReference<Messenger> weakReference = this.mCallbacksMessengerRef;
            if (weakReference == null) return;
            if (weakReference.get() == null) return;
            if (this.mCallbackImplRef.get() == null) {
                return;
            }
            Object object = message.getData();
            MediaSessionCompat.ensureClassLoader((Bundle)object);
            weakReference = (MediaBrowserServiceCallbackImpl)this.mCallbackImplRef.get();
            Messenger messenger = (Messenger)this.mCallbacksMessengerRef.get();
            try {
                int n = message.what;
                if (n == 1) {
                    Bundle bundle = object.getBundle("data_root_hints");
                    MediaSessionCompat.ensureClassLoader(bundle);
                    weakReference.onServiceConnected(messenger, object.getString("data_media_item_id"), (MediaSessionCompat.Token)object.getParcelable("data_media_session_token"), bundle);
                    return;
                }
                if (n == 2) {
                    weakReference.onConnectionFailed(messenger);
                    return;
                }
                if (n != 3) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unhandled message: ");
                    ((StringBuilder)object).append((Object)message);
                    ((StringBuilder)object).append("\n  Client version: ");
                    ((StringBuilder)object).append(1);
                    ((StringBuilder)object).append("\n  Service version: ");
                    ((StringBuilder)object).append(message.arg1);
                    Log.w((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
                    return;
                }
                Bundle bundle = object.getBundle("data_options");
                MediaSessionCompat.ensureClassLoader(bundle);
                Bundle bundle2 = object.getBundle("data_notify_children_changed_options");
                MediaSessionCompat.ensureClassLoader(bundle2);
                weakReference.onLoadChildren(messenger, object.getString("data_media_item_id"), (List)object.getParcelableArrayList("data_media_item_list"), bundle, bundle2);
                return;
            }
            catch (BadParcelableException badParcelableException) {
                Log.e((String)MediaBrowserCompat.TAG, (String)"Could not unparcel the data.");
                if (message.what != 1) return;
                weakReference.onConnectionFailed(messenger);
            }
        }

        void setCallbacksMessenger(Messenger messenger) {
            this.mCallbacksMessengerRef = new WeakReference<Messenger>(messenger);
        }
    }

    public static class ConnectionCallback {
        ConnectionCallbackInternal mConnectionCallbackInternal;
        final Object mConnectionCallbackObj;

        public ConnectionCallback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
                return;
            }
            this.mConnectionCallbackObj = null;
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        void setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
            this.mConnectionCallbackInternal = connectionCallbackInternal;
        }

        static interface ConnectionCallbackInternal {
            public void onConnected();

            public void onConnectionFailed();

            public void onConnectionSuspended();
        }

        private class StubApi21
        implements MediaBrowserCompatApi21.ConnectionCallback {
            StubApi21() {
            }

            @Override
            public void onConnected() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
                }
                ConnectionCallback.this.onConnected();
            }

            @Override
            public void onConnectionFailed() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
                }
                ConnectionCallback.this.onConnectionFailed();
            }

            @Override
            public void onConnectionSuspended() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
                }
                ConnectionCallback.this.onConnectionSuspended();
            }
        }

    }

    public static abstract class CustomActionCallback {
        public void onError(String string2, Bundle bundle, Bundle bundle2) {
        }

        public void onProgressUpdate(String string2, Bundle bundle, Bundle bundle2) {
        }

        public void onResult(String string2, Bundle bundle, Bundle bundle2) {
        }
    }

    private static class CustomActionResultReceiver
    extends ResultReceiver {
        private final String mAction;
        private final CustomActionCallback mCallback;
        private final Bundle mExtras;

        CustomActionResultReceiver(String string2, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler);
            this.mAction = string2;
            this.mExtras = bundle;
            this.mCallback = customActionCallback;
        }

        @Override
        protected void onReceiveResult(int n, Bundle bundle) {
            if (this.mCallback == null) {
                return;
            }
            MediaSessionCompat.ensureClassLoader(bundle);
            if (n == -1) {
                this.mCallback.onError(this.mAction, this.mExtras, bundle);
                return;
            }
            if (n == 0) {
                this.mCallback.onResult(this.mAction, this.mExtras, bundle);
                return;
            }
            if (n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown result code: ");
                stringBuilder.append(n);
                stringBuilder.append(" (extras=");
                stringBuilder.append((Object)this.mExtras);
                stringBuilder.append(", resultData=");
                stringBuilder.append((Object)bundle);
                stringBuilder.append(")");
                Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                return;
            }
            this.mCallback.onProgressUpdate(this.mAction, this.mExtras, bundle);
        }
    }

    public static abstract class ItemCallback {
        final Object mItemCallbackObj;

        public ItemCallback() {
            if (Build.VERSION.SDK_INT >= 23) {
                this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23());
                return;
            }
            this.mItemCallbackObj = null;
        }

        public void onError(String string2) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }

        private class StubApi23
        implements MediaBrowserCompatApi23.ItemCallback {
            StubApi23() {
            }

            @Override
            public void onError(String string2) {
                ItemCallback.this.onError(string2);
            }

            @Override
            public void onItemLoaded(Parcel parcel) {
                if (parcel == null) {
                    ItemCallback.this.onItemLoaded(null);
                    return;
                }
                parcel.setDataPosition(0);
                MediaItem mediaItem = (MediaItem)MediaItem.CREATOR.createFromParcel(parcel);
                parcel.recycle();
                ItemCallback.this.onItemLoaded(mediaItem);
            }
        }

    }

    private static class ItemReceiver
    extends ResultReceiver {
        private final ItemCallback mCallback;
        private final String mMediaId;

        ItemReceiver(String string2, ItemCallback itemCallback, Handler handler) {
            super(handler);
            this.mMediaId = string2;
            this.mCallback = itemCallback;
        }

        @Override
        protected void onReceiveResult(int n, Bundle bundle) {
            MediaSessionCompat.ensureClassLoader(bundle);
            if (n == 0 && bundle != null && bundle.containsKey("media_item")) {
                if ((bundle = bundle.getParcelable("media_item")) != null && !(bundle instanceof MediaItem)) {
                    this.mCallback.onError(this.mMediaId);
                    return;
                }
                this.mCallback.onItemLoaded((MediaItem)bundle);
                return;
            }
            this.mCallback.onError(this.mMediaId);
        }
    }

    static interface MediaBrowserImpl {
        public void connect();

        public void disconnect();

        public Bundle getExtras();

        public void getItem(String var1, ItemCallback var2);

        public Bundle getNotifyChildrenChangedOptions();

        public String getRoot();

        public ComponentName getServiceComponent();

        public MediaSessionCompat.Token getSessionToken();

        public boolean isConnected();

        public void search(String var1, Bundle var2, SearchCallback var3);

        public void sendCustomAction(String var1, Bundle var2, CustomActionCallback var3);

        public void subscribe(String var1, Bundle var2, SubscriptionCallback var3);

        public void unsubscribe(String var1, SubscriptionCallback var2);
    }

    static class MediaBrowserImplApi21
    implements MediaBrowserImpl,
    MediaBrowserServiceCallbackImpl,
    ConnectionCallback.ConnectionCallbackInternal {
        protected final Object mBrowserObj;
        protected Messenger mCallbacksMessenger;
        final Context mContext;
        protected final CallbackHandler mHandler = new CallbackHandler(this);
        private MediaSessionCompat.Token mMediaSessionToken;
        private Bundle mNotifyChildrenChangedOptions;
        protected final Bundle mRootHints;
        protected ServiceBinderWrapper mServiceBinderWrapper;
        protected int mServiceVersion;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.mContext = context;
            Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
            this.mRootHints = bundle2;
            bundle2.putInt("extra_client_version", 1);
            connectionCallback.setInternalConnectionCallback(this);
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context, componentName, connectionCallback.mConnectionCallbackObj, this.mRootHints);
        }

        @Override
        public void connect() {
            MediaBrowserCompatApi21.connect(this.mBrowserObj);
        }

        @Override
        public void disconnect() {
            Messenger messenger;
            ServiceBinderWrapper serviceBinderWrapper = this.mServiceBinderWrapper;
            if (serviceBinderWrapper != null && (messenger = this.mCallbacksMessenger) != null) {
                try {
                    serviceBinderWrapper.unregisterCallbackMessenger(messenger);
                }
                catch (RemoteException remoteException) {
                    Log.i((String)MediaBrowserCompat.TAG, (String)"Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
        }

        @Override
        public Bundle getExtras() {
            return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
        }

        @Override
        public void getItem(final String string2, final ItemCallback itemCallback) {
            if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("mediaId is empty");
            if (itemCallback == null) throw new IllegalArgumentException("cb is null");
            if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                Log.i((String)MediaBrowserCompat.TAG, (String)"Not connected, unable to retrieve the MediaItem.");
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        itemCallback.onError(string2);
                    }
                });
                return;
            }
            if (this.mServiceBinderWrapper == null) {
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        itemCallback.onError(string2);
                    }
                });
                return;
            }
            ItemReceiver itemReceiver = new ItemReceiver(string2, itemCallback, this.mHandler);
            try {
                this.mServiceBinderWrapper.getMediaItem(string2, itemReceiver, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error getting media item: ");
                stringBuilder.append(string2);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        itemCallback.onError(string2);
                    }
                });
            }
        }

        @Override
        public Bundle getNotifyChildrenChangedOptions() {
            return this.mNotifyChildrenChangedOptions;
        }

        @Override
        public String getRoot() {
            return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
        }

        @Override
        public ComponentName getServiceComponent() {
            return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
        }

        @Override
        public MediaSessionCompat.Token getSessionToken() {
            if (this.mMediaSessionToken != null) return this.mMediaSessionToken;
            this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
            return this.mMediaSessionToken;
        }

        @Override
        public boolean isConnected() {
            return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
        }

        @Override
        public void onConnected() {
            Object object = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
            if (object == null) {
                return;
            }
            this.mServiceVersion = object.getInt("extra_service_version", 0);
            IBinder iBinder = BundleCompat.getBinder(object, "extra_messenger");
            if (iBinder != null) {
                this.mServiceBinderWrapper = new ServiceBinderWrapper(iBinder, this.mRootHints);
                iBinder = new Messenger((Handler)this.mHandler);
                this.mCallbacksMessenger = iBinder;
                this.mHandler.setCallbacksMessenger((Messenger)iBinder);
                try {
                    this.mServiceBinderWrapper.registerCallbackMessenger(this.mContext, this.mCallbacksMessenger);
                }
                catch (RemoteException remoteException) {
                    Log.i((String)MediaBrowserCompat.TAG, (String)"Remote error registering client messenger.");
                }
            }
            if ((object = IMediaSession.Stub.asInterface(BundleCompat.getBinder(object, "extra_session_binder"))) == null) return;
            this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), (IMediaSession)object);
        }

        @Override
        public void onConnectionFailed() {
        }

        @Override
        public void onConnectionFailed(Messenger messenger) {
        }

        @Override
        public void onConnectionSuspended() {
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mMediaSessionToken = null;
            this.mHandler.setCallbacksMessenger(null);
        }

        @Override
        public void onLoadChildren(Messenger object, String string2, List list, Bundle bundle, Bundle bundle2) {
            if (this.mCallbacksMessenger != object) {
                return;
            }
            object = (Subscription)this.mSubscriptions.get(string2);
            if (object == null) {
                if (!DEBUG) return;
                object = new StringBuilder();
                ((StringBuilder)object).append("onLoadChildren for id that isn't subscribed id=");
                ((StringBuilder)object).append(string2);
                Log.d((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
                return;
            }
            if ((object = ((Subscription)object).getCallback(bundle)) == null) return;
            if (bundle == null) {
                if (list == null) {
                    ((SubscriptionCallback)object).onError(string2);
                    return;
                }
                this.mNotifyChildrenChangedOptions = bundle2;
                ((SubscriptionCallback)object).onChildrenLoaded(string2, list);
                this.mNotifyChildrenChangedOptions = null;
                return;
            }
            if (list == null) {
                ((SubscriptionCallback)object).onError(string2, bundle);
                return;
            }
            this.mNotifyChildrenChangedOptions = bundle2;
            ((SubscriptionCallback)object).onChildrenLoaded(string2, list, bundle);
            this.mNotifyChildrenChangedOptions = null;
        }

        @Override
        public void onServiceConnected(Messenger messenger, String string2, MediaSessionCompat.Token token, Bundle bundle) {
        }

        @Override
        public void search(final String string2, final Bundle bundle, final SearchCallback searchCallback) {
            if (!this.isConnected()) throw new IllegalStateException("search() called while not connected");
            if (this.mServiceBinderWrapper == null) {
                Log.i((String)MediaBrowserCompat.TAG, (String)"The connected service doesn't support search.");
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        searchCallback.onError(string2, bundle);
                    }
                });
                return;
            }
            SearchResultReceiver searchResultReceiver = new SearchResultReceiver(string2, bundle, searchCallback, this.mHandler);
            try {
                this.mServiceBinderWrapper.search(string2, bundle, searchResultReceiver, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error searching items with query: ");
                stringBuilder.append(string2);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        searchCallback.onError(string2, bundle);
                    }
                });
            }
        }

        @Override
        public void sendCustomAction(String string2, Bundle bundle, CustomActionCallback object) {
            if (!this.isConnected()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot send a custom action (");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(") with ");
                ((StringBuilder)object).append("extras ");
                ((StringBuilder)object).append((Object)bundle);
                ((StringBuilder)object).append(" because the browser is not connected to the ");
                ((StringBuilder)object).append("service.");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            if (this.mServiceBinderWrapper == null) {
                Log.i((String)MediaBrowserCompat.TAG, (String)"The connected service doesn't support sendCustomAction.");
                if (object != null) {
                    this.mHandler.post(new Runnable((CustomActionCallback)object, string2, bundle){
                        final /* synthetic */ String val$action;
                        final /* synthetic */ CustomActionCallback val$callback;
                        final /* synthetic */ Bundle val$extras;
                        {
                            this.val$callback = customActionCallback;
                            this.val$action = string2;
                            this.val$extras = bundle;
                        }

                        @Override
                        public void run() {
                            this.val$callback.onError(this.val$action, this.val$extras, null);
                        }
                    });
                }
            }
            Object object2 = new CustomActionResultReceiver(string2, bundle, (CustomActionCallback)object, this.mHandler);
            try {
                this.mServiceBinderWrapper.sendCustomAction(string2, bundle, (ResultReceiver)object2, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Remote error sending a custom action: action=");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(", extras=");
                ((StringBuilder)object2).append((Object)bundle);
                Log.i((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object2).toString(), (Throwable)remoteException);
                if (object == null) return;
                this.mHandler.post(new Runnable((CustomActionCallback)object, string2, bundle){
                    final /* synthetic */ String val$action;
                    final /* synthetic */ CustomActionCallback val$callback;
                    final /* synthetic */ Bundle val$extras;
                    {
                        this.val$callback = customActionCallback;
                        this.val$action = string2;
                        this.val$extras = bundle;
                    }

                    @Override
                    public void run() {
                        this.val$callback.onError(this.val$action, this.val$extras, null);
                    }
                });
            }
        }

        @Override
        public void subscribe(String string2, Bundle object, SubscriptionCallback subscriptionCallback) {
            Subscription subscription = (Subscription)this.mSubscriptions.get(string2);
            Object object2 = subscription;
            if (subscription == null) {
                object2 = new Subscription();
                this.mSubscriptions.put(string2, (Subscription)object2);
            }
            subscriptionCallback.setSubscription((Subscription)object2);
            object = object == null ? null : new Bundle(object);
            ((Subscription)object2).putCallback((Bundle)object, subscriptionCallback);
            object2 = this.mServiceBinderWrapper;
            if (object2 == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, string2, subscriptionCallback.mSubscriptionCallbackObj);
                return;
            }
            try {
                ((ServiceBinderWrapper)object2).addSubscription(string2, subscriptionCallback.mToken, (Bundle)object, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error subscribing media item: ");
                stringBuilder.append(string2);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            }
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public void unsubscribe(String var1_1, SubscriptionCallback var2_2) {
            block9 : {
                block10 : {
                    var3_3 = (Subscription)this.mSubscriptions.get(var1_1);
                    if (var3_3 == null) {
                        return;
                    }
                    var4_4 = this.mServiceBinderWrapper;
                    if (var4_4 != null) break block10;
                    if (var2_2 == null) {
                        MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, var1_1);
                    } else {
                        var4_4 = var3_3.getCallbacks();
                        var5_7 = var3_3.getOptionsList();
                        for (var6_9 = var4_4.size() - 1; var6_9 >= 0; --var6_9) {
                            if (var4_4.get(var6_9) != var2_2) continue;
                            var4_4.remove(var6_9);
                            var5_7.remove(var6_9);
                        }
                        if (var4_4.size() == 0) {
                            MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, var1_1);
                        }
                    }
                    break block9;
                }
                if (var2_2 != null) ** GOTO lbl26
                try {
                    var4_4.removeSubscription(var1_1, null, this.mCallbacksMessenger);
                    break block9;
lbl26: // 1 sources:
                    var4_4 = var3_3.getCallbacks();
                    var5_8 = var3_3.getOptionsList();
                    for (var6_10 = var4_4.size() - 1; var6_10 >= 0; --var6_10) {
                        if (var4_4.get(var6_10) != var2_2) continue;
                        this.mServiceBinderWrapper.removeSubscription(var1_1, var2_2.mToken, this.mCallbacksMessenger);
                        var4_4.remove(var6_10);
                        var5_8.remove(var6_10);
                    }
                }
                catch (RemoteException var4_5) {
                    var4_6 = new StringBuilder();
                    var4_6.append("removeSubscription failed with RemoteException parentId=");
                    var4_6.append(var1_1);
                    Log.d((String)"MediaBrowserCompat", (String)var4_6.toString());
                }
            }
            if (!var3_3.isEmpty()) {
                if (var2_2 != null) return;
            }
            this.mSubscriptions.remove(var1_1);
        }

    }

    static class MediaBrowserImplApi23
    extends MediaBrowserImplApi21 {
        MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override
        public void getItem(String string2, ItemCallback itemCallback) {
            if (this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi23.getItem(this.mBrowserObj, string2, itemCallback.mItemCallbackObj);
                return;
            }
            super.getItem(string2, itemCallback);
        }
    }

    static class MediaBrowserImplApi26
    extends MediaBrowserImplApi23 {
        MediaBrowserImplApi26(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override
        public void subscribe(String string2, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
                super.subscribe(string2, bundle, subscriptionCallback);
                return;
            }
            if (bundle == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, string2, subscriptionCallback.mSubscriptionCallbackObj);
                return;
            }
            MediaBrowserCompatApi26.subscribe(this.mBrowserObj, string2, bundle, subscriptionCallback.mSubscriptionCallbackObj);
        }

        @Override
        public void unsubscribe(String string2, SubscriptionCallback subscriptionCallback) {
            if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
                super.unsubscribe(string2, subscriptionCallback);
                return;
            }
            if (subscriptionCallback == null) {
                MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, string2);
                return;
            }
            MediaBrowserCompatApi26.unsubscribe(this.mBrowserObj, string2, subscriptionCallback.mSubscriptionCallbackObj);
        }
    }

    static class MediaBrowserImplBase
    implements MediaBrowserImpl,
    MediaBrowserServiceCallbackImpl {
        static final int CONNECT_STATE_CONNECTED = 3;
        static final int CONNECT_STATE_CONNECTING = 2;
        static final int CONNECT_STATE_DISCONNECTED = 1;
        static final int CONNECT_STATE_DISCONNECTING = 0;
        static final int CONNECT_STATE_SUSPENDED = 4;
        final ConnectionCallback mCallback;
        Messenger mCallbacksMessenger;
        final Context mContext;
        private Bundle mExtras;
        final CallbackHandler mHandler = new CallbackHandler(this);
        private MediaSessionCompat.Token mMediaSessionToken;
        private Bundle mNotifyChildrenChangedOptions;
        final Bundle mRootHints;
        private String mRootId;
        ServiceBinderWrapper mServiceBinderWrapper;
        final ComponentName mServiceComponent;
        MediaServiceConnection mServiceConnection;
        int mState = 1;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        public MediaBrowserImplBase(Context object, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            if (object == null) throw new IllegalArgumentException("context must not be null");
            if (componentName == null) throw new IllegalArgumentException("service component must not be null");
            if (connectionCallback == null) throw new IllegalArgumentException("connection callback must not be null");
            this.mContext = object;
            this.mServiceComponent = componentName;
            this.mCallback = connectionCallback;
            object = bundle == null ? null : new Bundle(bundle);
            this.mRootHints = object;
        }

        private static String getStateLabel(int n) {
            if (n == 0) return "CONNECT_STATE_DISCONNECTING";
            if (n == 1) return "CONNECT_STATE_DISCONNECTED";
            if (n == 2) return "CONNECT_STATE_CONNECTING";
            if (n == 3) return "CONNECT_STATE_CONNECTED";
            if (n == 4) return "CONNECT_STATE_SUSPENDED";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UNKNOWN/");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }

        private boolean isCurrent(Messenger object, String string2) {
            int n;
            if (this.mCallbacksMessenger == object && (n = this.mState) != 0) {
                if (n != 1) return true;
            }
            if ((n = this.mState) == 0) return false;
            if (n == 1) return false;
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append((Object)this.mServiceComponent);
            ((StringBuilder)object).append(" with mCallbacksMessenger=");
            ((StringBuilder)object).append((Object)this.mCallbacksMessenger);
            ((StringBuilder)object).append(" this=");
            ((StringBuilder)object).append(this);
            Log.i((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
            return false;
        }

        @Override
        public void connect() {
            int n = this.mState;
            if (n != 0 && n != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("connect() called while neigther disconnecting nor disconnected (state=");
                stringBuilder.append(MediaBrowserImplBase.getStateLabel(this.mState));
                stringBuilder.append(")");
                throw new IllegalStateException(stringBuilder.toString());
            }
            this.mState = 2;
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    if (MediaBrowserImplBase.this.mState == 0) {
                        return;
                    }
                    MediaBrowserImplBase.this.mState = 2;
                    if (DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mServiceConnection should be null. Instead it is ");
                        stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mServiceBinderWrapper should be null. Instead it is ");
                        stringBuilder.append(MediaBrowserImplBase.this.mServiceBinderWrapper);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mCallbacksMessenger should be null. Instead it is ");
                        stringBuilder.append((Object)MediaBrowserImplBase.this.mCallbacksMessenger);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    Intent intent = new Intent("android.media.browse.MediaBrowserService");
                    intent.setComponent(MediaBrowserImplBase.this.mServiceComponent);
                    MediaBrowserImplBase.this.mServiceConnection = new MediaServiceConnection();
                    boolean bl = false;
                    try {
                        boolean bl2;
                        bl = bl2 = MediaBrowserImplBase.this.mContext.bindService(intent, (ServiceConnection)MediaBrowserImplBase.this.mServiceConnection, 1);
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed binding to service ");
                        stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                        Log.e((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    }
                    if (!bl) {
                        MediaBrowserImplBase.this.forceCloseConnection();
                        MediaBrowserImplBase.this.mCallback.onConnectionFailed();
                    }
                    if (!DEBUG) return;
                    Log.d((String)MediaBrowserCompat.TAG, (String)"connect...");
                    MediaBrowserImplBase.this.dump();
                }
            });
        }

        @Override
        public void disconnect() {
            this.mState = 0;
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        try {
                            MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger);
                        }
                        catch (RemoteException remoteException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("RemoteException during connect for ");
                            stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                            Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                        }
                    }
                    int n = MediaBrowserImplBase.this.mState;
                    MediaBrowserImplBase.this.forceCloseConnection();
                    if (n != 0) {
                        MediaBrowserImplBase.this.mState = n;
                    }
                    if (!DEBUG) return;
                    Log.d((String)MediaBrowserCompat.TAG, (String)"disconnect...");
                    MediaBrowserImplBase.this.dump();
                }
            });
        }

        void dump() {
            Log.d((String)MediaBrowserCompat.TAG, (String)"MediaBrowserCompat...");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  mServiceComponent=");
            stringBuilder.append((Object)this.mServiceComponent);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mCallback=");
            stringBuilder.append(this.mCallback);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mRootHints=");
            stringBuilder.append((Object)this.mRootHints);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mState=");
            stringBuilder.append(MediaBrowserImplBase.getStateLabel(this.mState));
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mServiceConnection=");
            stringBuilder.append(this.mServiceConnection);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mServiceBinderWrapper=");
            stringBuilder.append(this.mServiceBinderWrapper);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mCallbacksMessenger=");
            stringBuilder.append((Object)this.mCallbacksMessenger);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mRootId=");
            stringBuilder.append(this.mRootId);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  mMediaSessionToken=");
            stringBuilder.append(this.mMediaSessionToken);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        }

        void forceCloseConnection() {
            MediaServiceConnection mediaServiceConnection = this.mServiceConnection;
            if (mediaServiceConnection != null) {
                this.mContext.unbindService((ServiceConnection)mediaServiceConnection);
            }
            this.mState = 1;
            this.mServiceConnection = null;
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
            this.mRootId = null;
            this.mMediaSessionToken = null;
        }

        @Override
        public Bundle getExtras() {
            if (this.isConnected()) {
                return this.mExtras;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getExtras() called while not connected (state=");
            stringBuilder.append(MediaBrowserImplBase.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public void getItem(final String string2, final ItemCallback itemCallback) {
            if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("mediaId is empty");
            if (itemCallback == null) throw new IllegalArgumentException("cb is null");
            if (!this.isConnected()) {
                Log.i((String)MediaBrowserCompat.TAG, (String)"Not connected, unable to retrieve the MediaItem.");
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        itemCallback.onError(string2);
                    }
                });
                return;
            }
            ItemReceiver itemReceiver = new ItemReceiver(string2, itemCallback, this.mHandler);
            try {
                this.mServiceBinderWrapper.getMediaItem(string2, itemReceiver, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error getting media item: ");
                stringBuilder.append(string2);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        itemCallback.onError(string2);
                    }
                });
            }
        }

        @Override
        public Bundle getNotifyChildrenChangedOptions() {
            return this.mNotifyChildrenChangedOptions;
        }

        @Override
        public String getRoot() {
            if (this.isConnected()) {
                return this.mRootId;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getRoot() called while not connected(state=");
            stringBuilder.append(MediaBrowserImplBase.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public ComponentName getServiceComponent() {
            if (this.isConnected()) {
                return this.mServiceComponent;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getServiceComponent() called while not connected (state=");
            stringBuilder.append(this.mState);
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public MediaSessionCompat.Token getSessionToken() {
            if (this.isConnected()) {
                return this.mMediaSessionToken;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSessionToken() called while not connected(state=");
            stringBuilder.append(this.mState);
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public boolean isConnected() {
            if (this.mState != 3) return false;
            return true;
        }

        @Override
        public void onConnectionFailed(Messenger object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectFailed for ");
            stringBuilder.append((Object)this.mServiceComponent);
            Log.e((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            if (!this.isCurrent((Messenger)object, "onConnectFailed")) {
                return;
            }
            if (this.mState != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onConnect from service while mState=");
                ((StringBuilder)object).append(MediaBrowserImplBase.getStateLabel(this.mState));
                ((StringBuilder)object).append("... ignoring");
                Log.w((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
                return;
            }
            this.forceCloseConnection();
            this.mCallback.onConnectionFailed();
        }

        @Override
        public void onLoadChildren(Messenger object, String string2, List list, Bundle bundle, Bundle bundle2) {
            if (!this.isCurrent((Messenger)object, "onLoadChildren")) {
                return;
            }
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onLoadChildren for ");
                ((StringBuilder)object).append((Object)this.mServiceComponent);
                ((StringBuilder)object).append(" id=");
                ((StringBuilder)object).append(string2);
                Log.d((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
            }
            if ((object = (Subscription)this.mSubscriptions.get(string2)) == null) {
                if (!DEBUG) return;
                object = new StringBuilder();
                ((StringBuilder)object).append("onLoadChildren for id that isn't subscribed id=");
                ((StringBuilder)object).append(string2);
                Log.d((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
                return;
            }
            if ((object = ((Subscription)object).getCallback(bundle)) == null) return;
            if (bundle == null) {
                if (list == null) {
                    ((SubscriptionCallback)object).onError(string2);
                    return;
                }
                this.mNotifyChildrenChangedOptions = bundle2;
                ((SubscriptionCallback)object).onChildrenLoaded(string2, list);
                this.mNotifyChildrenChangedOptions = null;
                return;
            }
            if (list == null) {
                ((SubscriptionCallback)object).onError(string2, bundle);
                return;
            }
            this.mNotifyChildrenChangedOptions = bundle2;
            ((SubscriptionCallback)object).onChildrenLoaded(string2, list, bundle);
            this.mNotifyChildrenChangedOptions = null;
        }

        @Override
        public void onServiceConnected(Messenger object, String object2, MediaSessionCompat.Token list, Bundle object3) {
            if (!this.isCurrent((Messenger)object, "onConnect")) {
                return;
            }
            if (this.mState != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onConnect from service while mState=");
                ((StringBuilder)object).append(MediaBrowserImplBase.getStateLabel(this.mState));
                ((StringBuilder)object).append("... ignoring");
                Log.w((String)MediaBrowserCompat.TAG, (String)((StringBuilder)object).toString());
                return;
            }
            this.mRootId = object2;
            this.mMediaSessionToken = list;
            this.mExtras = object3;
            this.mState = 3;
            if (DEBUG) {
                Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                this.dump();
            }
            this.mCallback.onConnected();
            try {
                object2 = this.mSubscriptions.entrySet().iterator();
                block2 : do {
                    if (!object2.hasNext()) return;
                    list = (Map.Entry)object2.next();
                    object = (String)list.getKey();
                    object3 = (Subscription)list.getValue();
                    list = ((Subscription)object3).getCallbacks();
                    object3 = ((Subscription)object3).getOptionsList();
                    int n = 0;
                    do {
                        if (n >= list.size()) continue block2;
                        this.mServiceBinderWrapper.addSubscription((String)object, list.get((int)n).mToken, (Bundle)object3.get(n), this.mCallbacksMessenger);
                        ++n;
                    } while (true);
                    break;
                } while (true);
            }
            catch (RemoteException remoteException) {
                Log.d((String)MediaBrowserCompat.TAG, (String)"addSubscription failed with RemoteException.");
            }
        }

        @Override
        public void search(String charSequence, Bundle bundle, final SearchCallback searchCallback) {
            if (!this.isConnected()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("search() called while not connected (state=");
                ((StringBuilder)charSequence).append(MediaBrowserImplBase.getStateLabel(this.mState));
                ((StringBuilder)charSequence).append(")");
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            SearchResultReceiver searchResultReceiver = new SearchResultReceiver((String)charSequence, bundle, searchCallback, this.mHandler);
            try {
                this.mServiceBinderWrapper.search((String)charSequence, bundle, searchResultReceiver, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error searching items with query: ");
                stringBuilder.append((String)charSequence);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
                this.mHandler.post(new Runnable((String)charSequence, bundle){
                    final /* synthetic */ Bundle val$extras;
                    final /* synthetic */ String val$query;
                    {
                        this.val$query = string2;
                        this.val$extras = bundle;
                    }

                    @Override
                    public void run() {
                        searchCallback.onError(this.val$query, this.val$extras);
                    }
                });
            }
        }

        @Override
        public void sendCustomAction(String string2, Bundle bundle, CustomActionCallback object) {
            if (!this.isConnected()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot send a custom action (");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(") with ");
                ((StringBuilder)object).append("extras ");
                ((StringBuilder)object).append((Object)bundle);
                ((StringBuilder)object).append(" because the browser is not connected to the ");
                ((StringBuilder)object).append("service.");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            CustomActionResultReceiver customActionResultReceiver = new CustomActionResultReceiver(string2, bundle, (CustomActionCallback)object, this.mHandler);
            try {
                this.mServiceBinderWrapper.sendCustomAction(string2, bundle, customActionResultReceiver, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote error sending a custom action: action=");
                stringBuilder.append(string2);
                stringBuilder.append(", extras=");
                stringBuilder.append((Object)bundle);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
                if (object == null) return;
                this.mHandler.post(new Runnable((CustomActionCallback)object, string2, bundle){
                    final /* synthetic */ String val$action;
                    final /* synthetic */ CustomActionCallback val$callback;
                    final /* synthetic */ Bundle val$extras;
                    {
                        this.val$callback = customActionCallback;
                        this.val$action = string2;
                        this.val$extras = bundle;
                    }

                    @Override
                    public void run() {
                        this.val$callback.onError(this.val$action, this.val$extras, null);
                    }
                });
            }
        }

        @Override
        public void subscribe(String string2, Bundle object, SubscriptionCallback subscriptionCallback) {
            Subscription subscription;
            Subscription subscription2 = subscription = (Subscription)this.mSubscriptions.get(string2);
            if (subscription == null) {
                subscription2 = new Subscription();
                this.mSubscriptions.put(string2, subscription2);
            }
            object = object == null ? null : new Bundle(object);
            subscription2.putCallback((Bundle)object, subscriptionCallback);
            if (!this.isConnected()) return;
            try {
                this.mServiceBinderWrapper.addSubscription(string2, subscriptionCallback.mToken, (Bundle)object, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addSubscription failed with RemoteException parentId=");
                stringBuilder.append(string2);
                Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            }
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public void unsubscribe(String var1_1, SubscriptionCallback var2_2) {
            block7 : {
                var3_3 = (Subscription)this.mSubscriptions.get(var1_1);
                if (var3_3 == null) {
                    return;
                }
                if (var2_2 != null) ** GOTO lbl9
                try {
                    if (this.isConnected()) {
                        this.mServiceBinderWrapper.removeSubscription(var1_1, null, this.mCallbacksMessenger);
                    }
                    break block7;
lbl9: // 1 sources:
                    var4_4 = var3_3.getCallbacks();
                    var5_7 = var3_3.getOptionsList();
                    for (var6_8 = var4_4.size() - 1; var6_8 >= 0; --var6_8) {
                        if (var4_4.get(var6_8) != var2_2) continue;
                        if (this.isConnected()) {
                            this.mServiceBinderWrapper.removeSubscription(var1_1, var2_2.mToken, this.mCallbacksMessenger);
                        }
                        var4_4.remove(var6_8);
                        var5_7.remove(var6_8);
                    }
                }
                catch (RemoteException var4_5) {
                    var4_6 = new StringBuilder();
                    var4_6.append("removeSubscription failed with RemoteException parentId=");
                    var4_6.append(var1_1);
                    Log.d((String)"MediaBrowserCompat", (String)var4_6.toString());
                }
            }
            if (!var3_3.isEmpty()) {
                if (var2_2 != null) return;
            }
            this.mSubscriptions.remove(var1_1);
        }

        private class MediaServiceConnection
        implements ServiceConnection {
            MediaServiceConnection() {
            }

            private void postOrRun(Runnable runnable2) {
                if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                    runnable2.run();
                    return;
                }
                MediaBrowserImplBase.this.mHandler.post(runnable2);
            }

            boolean isCurrent(String string2) {
                if (MediaBrowserImplBase.this.mServiceConnection == this && MediaBrowserImplBase.this.mState != 0) {
                    if (MediaBrowserImplBase.this.mState != 1) return true;
                }
                if (MediaBrowserImplBase.this.mState == 0) return false;
                if (MediaBrowserImplBase.this.mState == 1) return false;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" for ");
                stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                stringBuilder.append(" with mServiceConnection=");
                stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                stringBuilder.append(" this=");
                stringBuilder.append(this);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                return false;
            }

            public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                this.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        if (DEBUG) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("MediaServiceConnection.onServiceConnected name=");
                            stringBuilder.append((Object)componentName);
                            stringBuilder.append(" binder=");
                            stringBuilder.append((Object)iBinder);
                            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                            MediaBrowserImplBase.this.dump();
                        }
                        if (!MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                            return;
                        }
                        MediaBrowserImplBase.this.mServiceBinderWrapper = new ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.mRootHints);
                        MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger((Handler)MediaBrowserImplBase.this.mHandler);
                        MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                        MediaBrowserImplBase.this.mState = 2;
                        try {
                            if (DEBUG) {
                                Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                                MediaBrowserImplBase.this.dump();
                            }
                            MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger);
                            return;
                        }
                        catch (RemoteException remoteException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("RemoteException during connect for ");
                            stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                            Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                            if (!DEBUG) return;
                            Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                            MediaBrowserImplBase.this.dump();
                        }
                    }
                });
            }

            public void onServiceDisconnected(final ComponentName componentName) {
                this.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        if (DEBUG) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
                            stringBuilder.append((Object)componentName);
                            stringBuilder.append(" this=");
                            stringBuilder.append(this);
                            stringBuilder.append(" mServiceConnection=");
                            stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                            MediaBrowserImplBase.this.dump();
                        }
                        if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                            return;
                        }
                        MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                        MediaBrowserImplBase.this.mCallbacksMessenger = null;
                        MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                        MediaBrowserImplBase.this.mState = 4;
                        MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
                    }
                });
            }

        }

    }

    static interface MediaBrowserServiceCallbackImpl {
        public void onConnectionFailed(Messenger var1);

        public void onLoadChildren(Messenger var1, String var2, List var3, Bundle var4, Bundle var5);

        public void onServiceConnected(Messenger var1, String var2, MediaSessionCompat.Token var3, Bundle var4);
    }

    public static class MediaItem
    implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>(){

            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            public MediaItem[] newArray(int n) {
                return new MediaItem[n];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        public MediaItem(MediaDescriptionCompat mediaDescriptionCompat, int n) {
            if (mediaDescriptionCompat == null) throw new IllegalArgumentException("description cannot be null");
            if (TextUtils.isEmpty((CharSequence)mediaDescriptionCompat.getMediaId())) throw new IllegalArgumentException("description must have a non-empty media id");
            this.mFlags = n;
            this.mDescription = mediaDescriptionCompat;
        }

        public static MediaItem fromMediaItem(Object object) {
            if (object == null) return null;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            int n = MediaBrowserCompatApi21.MediaItem.getFlags(object);
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaBrowserCompatApi21.MediaItem.getDescription(object)), n);
        }

        public static List<MediaItem> fromMediaItemList(List<?> object) {
            if (object == null) return null;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList<MediaItem> arrayList = new ArrayList<MediaItem>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(MediaItem.fromMediaItem(object.next()));
            }
            return arrayList;
        }

        public int describeContents() {
            return 0;
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public String getMediaId() {
            return this.mDescription.getMediaId();
        }

        public boolean isBrowsable() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) return false;
            return bl;
        }

        public boolean isPlayable() {
            if ((this.mFlags & 2) == 0) return false;
            return true;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("MediaItem{");
            stringBuilder.append("mFlags=");
            stringBuilder.append(this.mFlags);
            stringBuilder.append(", mDescription=");
            stringBuilder.append(this.mDescription);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, n);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Flags {
        }

    }

    public static abstract class SearchCallback {
        public void onError(String string2, Bundle bundle) {
        }

        public void onSearchResult(String string2, Bundle bundle, List<MediaItem> list) {
        }
    }

    private static class SearchResultReceiver
    extends ResultReceiver {
        private final SearchCallback mCallback;
        private final Bundle mExtras;
        private final String mQuery;

        SearchResultReceiver(String string2, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler);
            this.mQuery = string2;
            this.mExtras = bundle;
            this.mCallback = searchCallback;
        }

        @Override
        protected void onReceiveResult(int n, Bundle object) {
            MediaSessionCompat.ensureClassLoader(object);
            if (n == 0 && object != null && object.containsKey("search_results")) {
                Parcelable[] arrparcelable = object.getParcelableArray("search_results");
                object = null;
                if (arrparcelable != null) {
                    ArrayList<MediaItem> arrayList = new ArrayList<MediaItem>();
                    int n2 = arrparcelable.length;
                    n = 0;
                    do {
                        object = arrayList;
                        if (n >= n2) break;
                        arrayList.add((MediaItem)arrparcelable[n]);
                        ++n;
                    } while (true);
                }
                this.mCallback.onSearchResult(this.mQuery, this.mExtras, (List<MediaItem>)object);
                return;
            }
            this.mCallback.onError(this.mQuery, this.mExtras);
        }
    }

    private static class ServiceBinderWrapper {
        private Messenger mMessenger;
        private Bundle mRootHints;

        public ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
            this.mMessenger = new Messenger(iBinder);
            this.mRootHints = bundle;
        }

        private void sendRequest(int n, Bundle bundle, Messenger messenger) throws RemoteException {
            Message message = Message.obtain();
            message.what = n;
            message.arg1 = 1;
            message.setData(bundle);
            message.replyTo = messenger;
            this.mMessenger.send(message);
        }

        void addSubscription(String string2, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", string2);
            BundleCompat.putBinder(bundle2, "data_callback_token", iBinder);
            bundle2.putBundle("data_options", bundle);
            this.sendRequest(3, bundle2, messenger);
        }

        void connect(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.mRootHints);
            this.sendRequest(1, bundle, messenger);
        }

        void disconnect(Messenger messenger) throws RemoteException {
            this.sendRequest(2, null, messenger);
        }

        void getMediaItem(String string2, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_media_item_id", string2);
            bundle.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
            this.sendRequest(5, bundle, messenger);
        }

        void registerCallbackMessenger(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.mRootHints);
            this.sendRequest(6, bundle, messenger);
        }

        void removeSubscription(String string2, IBinder iBinder, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_media_item_id", string2);
            BundleCompat.putBinder(bundle, "data_callback_token", iBinder);
            this.sendRequest(4, bundle, messenger);
        }

        void search(String string2, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_search_query", string2);
            bundle2.putBundle("data_search_extras", bundle);
            bundle2.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
            this.sendRequest(8, bundle2, messenger);
        }

        void sendCustomAction(String string2, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_custom_action", string2);
            bundle2.putBundle("data_custom_action_extras", bundle);
            bundle2.putParcelable("data_result_receiver", (Parcelable)resultReceiver);
            this.sendRequest(9, bundle2, messenger);
        }

        void unregisterCallbackMessenger(Messenger messenger) throws RemoteException {
            this.sendRequest(7, null, messenger);
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList<SubscriptionCallback>();
        private final List<Bundle> mOptionsList = new ArrayList<Bundle>();

        public SubscriptionCallback getCallback(Bundle bundle) {
            int n = 0;
            while (n < this.mOptionsList.size()) {
                if (MediaBrowserCompatUtils.areSameOptions(this.mOptionsList.get(n), bundle)) {
                    return this.mCallbacks.get(n);
                }
                ++n;
            }
            return null;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public void putCallback(Bundle bundle, SubscriptionCallback subscriptionCallback) {
            int n = 0;
            do {
                if (n >= this.mOptionsList.size()) {
                    this.mCallbacks.add(subscriptionCallback);
                    this.mOptionsList.add(bundle);
                    return;
                }
                if (MediaBrowserCompatUtils.areSameOptions(this.mOptionsList.get(n), bundle)) {
                    this.mCallbacks.set(n, subscriptionCallback);
                    return;
                }
                ++n;
            } while (true);
        }
    }

    public static abstract class SubscriptionCallback {
        final Object mSubscriptionCallbackObj;
        WeakReference<Subscription> mSubscriptionRef;
        final IBinder mToken = new Binder();

        public SubscriptionCallback() {
            if (Build.VERSION.SDK_INT >= 26) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi26.createSubscriptionCallback(new StubApi26());
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());
                return;
            }
            this.mSubscriptionCallbackObj = null;
        }

        public void onChildrenLoaded(String string2, List<MediaItem> list) {
        }

        public void onChildrenLoaded(String string2, List<MediaItem> list, Bundle bundle) {
        }

        public void onError(String string2) {
        }

        public void onError(String string2, Bundle bundle) {
        }

        void setSubscription(Subscription subscription) {
            this.mSubscriptionRef = new WeakReference<Subscription>(subscription);
        }

        private class StubApi21
        implements MediaBrowserCompatApi21.SubscriptionCallback {
            StubApi21() {
            }

            List<MediaItem> applyOptions(List<MediaItem> list, Bundle bundle) {
                if (list == null) {
                    return null;
                }
                int n = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
                int n2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
                if (n == -1 && n2 == -1) {
                    return list;
                }
                int n3 = n2 * n;
                int n4 = n3 + n2;
                if (n < 0) return Collections.emptyList();
                if (n2 < 1) return Collections.emptyList();
                if (n3 >= list.size()) {
                    return Collections.emptyList();
                }
                n2 = n4;
                if (n4 <= list.size()) return list.subList(n3, n2);
                n2 = list.size();
                return list.subList(n3, n2);
            }

            @Override
            public void onChildrenLoaded(String string2, List<?> list) {
                Subscription subscription = SubscriptionCallback.this.mSubscriptionRef == null ? null : (Subscription)SubscriptionCallback.this.mSubscriptionRef.get();
                if (subscription == null) {
                    SubscriptionCallback.this.onChildrenLoaded(string2, MediaItem.fromMediaItemList(list));
                    return;
                }
                List<MediaItem> list2 = MediaItem.fromMediaItemList(list);
                list = subscription.getCallbacks();
                List<Bundle> list3 = subscription.getOptionsList();
                int n = 0;
                while (n < list.size()) {
                    subscription = list3.get(n);
                    if (subscription == null) {
                        SubscriptionCallback.this.onChildrenLoaded(string2, list2);
                    } else {
                        SubscriptionCallback.this.onChildrenLoaded(string2, this.applyOptions(list2, (Bundle)subscription), (Bundle)subscription);
                    }
                    ++n;
                }
            }

            @Override
            public void onError(String string2) {
                SubscriptionCallback.this.onError(string2);
            }
        }

        private class StubApi26
        extends StubApi21
        implements MediaBrowserCompatApi26.SubscriptionCallback {
            StubApi26() {
            }

            @Override
            public void onChildrenLoaded(String string2, List<?> list, Bundle bundle) {
                SubscriptionCallback.this.onChildrenLoaded(string2, MediaItem.fromMediaItemList(list), bundle);
            }

            @Override
            public void onError(String string2, Bundle bundle) {
                SubscriptionCallback.this.onError(string2, bundle);
            }
        }

    }

}

