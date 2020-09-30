/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 *  android.view.KeyEvent
 */
package androidx.media.session;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver
extends BroadcastReceiver {
    private static final String TAG = "MediaButtonReceiver";

    public static PendingIntent buildMediaButtonPendingIntent(Context context, long l) {
        ComponentName componentName = MediaButtonReceiver.getMediaButtonReceiverComponent(context);
        if (componentName != null) return MediaButtonReceiver.buildMediaButtonPendingIntent(context, componentName, l);
        Log.w((String)TAG, (String)"A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
        return null;
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context object, ComponentName componentName, long l) {
        if (componentName == null) {
            Log.w((String)TAG, (String)"The component name of media button receiver should be provided.");
            return null;
        }
        int n = PlaybackStateCompat.toKeyCode(l);
        if (n == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot build a media button pending intent with the given action: ");
            ((StringBuilder)object).append(l);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)new KeyEvent(0, n));
        return PendingIntent.getBroadcast((Context)object, (int)n, (Intent)intent, (int)0);
    }

    public static ComponentName getMediaButtonReceiverComponent(Context object) {
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.setPackage(object.getPackageName());
        object = object.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (object.size() == 1) {
            object = (ResolveInfo)object.get(0);
            return new ComponentName(object.activityInfo.packageName, object.activityInfo.name);
        }
        if (object.size() <= 1) return null;
        Log.w((String)TAG, (String)"More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
        return null;
    }

    private static ComponentName getServiceComponentByAction(Context object, String string2) {
        Object object2 = object.getPackageManager();
        Intent intent = new Intent(string2);
        intent.setPackage(object.getPackageName());
        object2 = object2.queryIntentServices(intent, 0);
        if (object2.size() == 1) {
            object = (ResolveInfo)object2.get(0);
            return new ComponentName(object.serviceInfo.packageName, object.serviceInfo.name);
        }
        if (object2.isEmpty()) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected 1 service that handles ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(", found ");
        ((StringBuilder)object).append(object2.size());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static KeyEvent handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        if (mediaSessionCompat == null) return null;
        if (intent == null) return null;
        if (!"android.intent.action.MEDIA_BUTTON".equals(intent.getAction())) return null;
        if (!intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            return null;
        }
        intent = (KeyEvent)intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        mediaSessionCompat.getController().dispatchMediaButtonEvent((KeyEvent)intent);
        return intent;
    }

    private static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
            return;
        }
        context.startService(intent);
    }

    public void onReceive(Context object, Intent object2) {
        if (object2 != null && "android.intent.action.MEDIA_BUTTON".equals(object2.getAction()) && object2.hasExtra("android.intent.extra.KEY_EVENT")) {
            ComponentName componentName = MediaButtonReceiver.getServiceComponentByAction((Context)object, "android.intent.action.MEDIA_BUTTON");
            if (componentName != null) {
                object2.setComponent(componentName);
                MediaButtonReceiver.startForegroundService((Context)object, (Intent)object2);
                return;
            }
            componentName = MediaButtonReceiver.getServiceComponentByAction((Context)object, "android.media.browse.MediaBrowserService");
            if (componentName == null) throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
            BroadcastReceiver.PendingResult pendingResult = this.goAsync();
            object = object.getApplicationContext();
            object2 = new MediaButtonConnectionCallback((Context)object, (Intent)object2, pendingResult);
            object = new MediaBrowserCompat((Context)object, componentName, (MediaBrowserCompat.ConnectionCallback)object2, null);
            ((MediaButtonConnectionCallback)object2).setMediaBrowser((MediaBrowserCompat)object);
            ((MediaBrowserCompat)object).connect();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Ignore unsupported intent: ");
        ((StringBuilder)object).append(object2);
        Log.d((String)TAG, (String)((StringBuilder)object).toString());
    }

    private static class MediaButtonConnectionCallback
    extends MediaBrowserCompat.ConnectionCallback {
        private final Context mContext;
        private final Intent mIntent;
        private MediaBrowserCompat mMediaBrowser;
        private final BroadcastReceiver.PendingResult mPendingResult;

        MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
            this.mContext = context;
            this.mIntent = intent;
            this.mPendingResult = pendingResult;
        }

        private void finish() {
            this.mMediaBrowser.disconnect();
            this.mPendingResult.finish();
        }

        @Override
        public void onConnected() {
            try {
                MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken());
                mediaControllerCompat.dispatchMediaButtonEvent((KeyEvent)this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaButtonReceiver.TAG, (String)"Failed to create a media controller", (Throwable)remoteException);
            }
            this.finish();
        }

        @Override
        public void onConnectionFailed() {
            this.finish();
        }

        @Override
        public void onConnectionSuspended() {
            this.finish();
        }

        void setMediaBrowser(MediaBrowserCompat mediaBrowserCompat) {
            this.mMediaBrowser = mediaBrowserCompat;
        }
    }

}

