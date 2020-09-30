/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.Notification
 *  android.app.NotificationChannel
 *  android.app.NotificationChannelGroup
 *  android.app.NotificationManager
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 */
package androidx.core.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    private static Set<String> sEnabledNotificationListenerPackages;
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock;
    private static final Object sLock;
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager;

    static {
        sEnabledNotificationListenersLock = new Object();
        sEnabledNotificationListenerPackages = new HashSet<String>();
        sLock = new Object();
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager)context.getSystemService("notification");
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }

    public static Set<String> getEnabledListenerPackages(Context object) {
        String string2 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)SETTING_ENABLED_NOTIFICATION_LISTENERS);
        object = sEnabledNotificationListenersLock;
        synchronized (object) {
            if (string2 == null) return sEnabledNotificationListenerPackages;
            if (string2.equals(sEnabledNotificationListeners)) return sEnabledNotificationListenerPackages;
            String[] arrstring = string2.split(":", -1);
            HashSet<String> hashSet = new HashSet<String>(arrstring.length);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                ComponentName componentName = ComponentName.unflattenFromString((String)arrstring[i]);
                if (componentName == null) continue;
                hashSet.add(componentName.getPackageName());
            }
            sEnabledNotificationListenerPackages = hashSet;
            sEnabledNotificationListeners = string2;
            return sEnabledNotificationListenerPackages;
        }
    }

    private void pushSideChannelQueue(Task task) {
        Object object = sLock;
        synchronized (object) {
            if (sSideChannelManager == null) {
                SideChannelManager sideChannelManager;
                sSideChannelManager = sideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.queueTask(task);
            return;
        }
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        if ((notification = NotificationCompat.getExtras(notification)) == null) return false;
        if (!notification.getBoolean(EXTRA_USE_SIDE_CHANNEL)) return false;
        return true;
    }

    public boolean areNotificationsEnabled() {
        boolean bl;
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.areNotificationsEnabled();
        }
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = bl = true;
        if (n < 19) return bl2;
        AppOpsManager appOpsManager = (AppOpsManager)this.mContext.getSystemService("appops");
        Object object = this.mContext.getApplicationInfo();
        String string2 = this.mContext.getApplicationContext().getPackageName();
        n = ((ApplicationInfo)object).uid;
        try {
            object = Class.forName(AppOpsManager.class.getName());
            n = (Integer)((Class)object).getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class).invoke((Object)appOpsManager, (int)((Integer)((Class)object).getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)), n, string2);
            if (n != 0) return false;
            return bl;
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException exception) {
            return bl;
        }
    }

    public void cancel(int n) {
        this.cancel(null, n);
    }

    public void cancel(String string2, int n) {
        this.mNotificationManager.cancel(string2, n);
        if (Build.VERSION.SDK_INT > 19) return;
        this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), n, string2));
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (Build.VERSION.SDK_INT > 19) return;
        this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
    }

    public void createNotificationChannel(NotificationChannel notificationChannel) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.createNotificationChannel(notificationChannel);
    }

    public void createNotificationChannelGroup(NotificationChannelGroup notificationChannelGroup) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.createNotificationChannelGroup(notificationChannelGroup);
    }

    public void createNotificationChannelGroups(List<NotificationChannelGroup> list) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.createNotificationChannelGroups(list);
    }

    public void createNotificationChannels(List<NotificationChannel> list) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.createNotificationChannels(list);
    }

    public void deleteNotificationChannel(String string2) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.deleteNotificationChannel(string2);
    }

    public void deleteNotificationChannelGroup(String string2) {
        if (Build.VERSION.SDK_INT < 26) return;
        this.mNotificationManager.deleteNotificationChannelGroup(string2);
    }

    public int getImportance() {
        if (Build.VERSION.SDK_INT < 24) return -1000;
        return this.mNotificationManager.getImportance();
    }

    public NotificationChannel getNotificationChannel(String string2) {
        if (Build.VERSION.SDK_INT < 26) return null;
        return this.mNotificationManager.getNotificationChannel(string2);
    }

    public NotificationChannelGroup getNotificationChannelGroup(String string2) {
        NotificationChannelGroup notificationChannelGroup;
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mNotificationManager.getNotificationChannelGroup(string2);
        }
        if (Build.VERSION.SDK_INT < 26) return null;
        Iterator<NotificationChannelGroup> iterator2 = this.getNotificationChannelGroups().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!(notificationChannelGroup = iterator2.next()).getId().equals(string2));
        return notificationChannelGroup;
    }

    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        if (Build.VERSION.SDK_INT < 26) return Collections.emptyList();
        return this.mNotificationManager.getNotificationChannelGroups();
    }

    public List<NotificationChannel> getNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) return Collections.emptyList();
        return this.mNotificationManager.getNotificationChannels();
    }

    public void notify(int n, Notification notification) {
        this.notify(null, n, notification);
    }

    public void notify(String string2, int n, Notification notification) {
        if (NotificationManagerCompat.useSideChannelForNotification(notification)) {
            this.pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), n, string2, notification));
            this.mNotificationManager.cancel(string2, n);
            return;
        }
        this.mNotificationManager.notify(string2, n, notification);
    }

    private static class CancelTask
    implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        CancelTask(String string2) {
            this.packageName = string2;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        CancelTask(String string2, int n, String string3) {
            this.packageName = string2;
            this.id = n;
            this.tag = string3;
            this.all = false;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.all) {
                iNotificationSideChannel.cancelAll(this.packageName);
                return;
            }
            iNotificationSideChannel.cancel(this.packageName, this.id, this.tag);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CancelTask[");
            stringBuilder.append("packageName:");
            stringBuilder.append(this.packageName);
            stringBuilder.append(", id:");
            stringBuilder.append(this.id);
            stringBuilder.append(", tag:");
            stringBuilder.append(this.tag);
            stringBuilder.append(", all:");
            stringBuilder.append(this.all);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class NotifyTask
    implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        NotifyTask(String string2, int n, String string3, Notification notification) {
            this.packageName = string2;
            this.id = n;
            this.tag = string3;
            this.notif = notification;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:");
            stringBuilder.append(this.packageName);
            stringBuilder.append(", id:");
            stringBuilder.append(this.id);
            stringBuilder.append(", tag:");
            stringBuilder.append(this.tag);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    private static class SideChannelManager
    implements Handler.Callback,
    ServiceConnection {
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages = new HashSet<String>();
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap<ComponentName, ListenerRecord>();

        SideChannelManager(Context context) {
            this.mContext = context;
            context = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread = context;
            context.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), (Handler.Callback)this);
        }

        private boolean ensureServiceBound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                return true;
            }
            Object object = new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName);
            listenerRecord.bound = this.mContext.bindService((Intent)object, (ServiceConnection)this, 33);
            if (listenerRecord.bound) {
                listenerRecord.retryCount = 0;
                return listenerRecord.bound;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to bind to listener ");
            ((StringBuilder)object).append((Object)listenerRecord.componentName);
            Log.w((String)NotificationManagerCompat.TAG, (String)((StringBuilder)object).toString());
            this.mContext.unbindService((ServiceConnection)this);
            return listenerRecord.bound;
        }

        private void ensureServiceUnbound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                this.mContext.unbindService((ServiceConnection)this);
                listenerRecord.bound = false;
            }
            listenerRecord.service = null;
        }

        private void handleQueueTask(Task task) {
            this.updateListenerMap();
            Iterator<ListenerRecord> iterator2 = this.mRecordMap.values().iterator();
            while (iterator2.hasNext()) {
                ListenerRecord listenerRecord = iterator2.next();
                listenerRecord.taskQueue.add(task);
                this.processListenerQueue(listenerRecord);
            }
        }

        private void handleRetryListenerQueue(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) == null) return;
            this.processListenerQueue((ListenerRecord)object);
        }

        private void handleServiceConnected(ComponentName object, IBinder iBinder) {
            if ((object = this.mRecordMap.get(object)) == null) return;
            object.service = INotificationSideChannel.Stub.asInterface(iBinder);
            object.retryCount = 0;
            this.processListenerQueue((ListenerRecord)object);
        }

        private void handleServiceDisconnected(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) == null) return;
            this.ensureServiceUnbound((ListenerRecord)object);
        }

        private void processListenerQueue(ListenerRecord listenerRecord) {
            Object object;
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Processing component ");
                ((StringBuilder)object).append((Object)listenerRecord.componentName);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(listenerRecord.taskQueue.size());
                ((StringBuilder)object).append(" queued tasks");
                Log.d((String)NotificationManagerCompat.TAG, (String)((StringBuilder)object).toString());
            }
            if (listenerRecord.taskQueue.isEmpty()) {
                return;
            }
            if (!this.ensureServiceBound(listenerRecord) || listenerRecord.service == null) {
                this.scheduleListenerRetry(listenerRecord);
                return;
            }
            while ((object = listenerRecord.taskQueue.peek()) != null) {
                block8 : {
                    try {
                        if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Sending task ");
                            stringBuilder.append(object);
                            Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                        }
                        object.send(listenerRecord.service);
                        listenerRecord.taskQueue.remove();
                        continue;
                    }
                    catch (RemoteException remoteException) {
                    }
                    catch (DeadObjectException deadObjectException) {
                        break block8;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("RemoteException communicating with ");
                    ((StringBuilder)object).append((Object)listenerRecord.componentName);
                    Log.w((String)NotificationManagerCompat.TAG, (String)((StringBuilder)object).toString(), (Throwable)remoteException);
                    break;
                }
                if (!Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) break;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote service has died: ");
                stringBuilder.append((Object)listenerRecord.componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                break;
            }
            if (listenerRecord.taskQueue.isEmpty()) return;
            this.scheduleListenerRetry(listenerRecord);
        }

        private void scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (this.mHandler.hasMessages(3, (Object)listenerRecord.componentName)) {
                return;
            }
            ++listenerRecord.retryCount;
            if (listenerRecord.retryCount > 6) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Giving up on delivering ");
                stringBuilder.append(listenerRecord.taskQueue.size());
                stringBuilder.append(" tasks to ");
                stringBuilder.append((Object)listenerRecord.componentName);
                stringBuilder.append(" after ");
                stringBuilder.append(listenerRecord.retryCount);
                stringBuilder.append(" retries");
                Log.w((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                listenerRecord.taskQueue.clear();
                return;
            }
            int n = (1 << listenerRecord.retryCount - 1) * 1000;
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Scheduling retry for ");
                stringBuilder.append(n);
                stringBuilder.append(" ms");
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            listenerRecord = this.mHandler.obtainMessage(3, (Object)listenerRecord.componentName);
            this.mHandler.sendMessageDelayed((Message)listenerRecord, (long)n);
        }

        private void updateListenerMap() {
            Object object;
            Set<String> set = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (set.equals(this.mCachedEnabledPackages)) {
                return;
            }
            this.mCachedEnabledPackages = set;
            Object object22 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
            HashSet<Object> hashSet = new HashSet<Object>();
            object22 = object22.iterator();
            while (object22.hasNext()) {
                Object object3 = (ResolveInfo)object22.next();
                if (!set.contains(object3.serviceInfo.packageName)) continue;
                object = new ComponentName(object3.serviceInfo.packageName, object3.serviceInfo.name);
                if (object3.serviceInfo.permission != null) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Permission present on component ");
                    ((StringBuilder)object3).append(object);
                    ((StringBuilder)object3).append(", not adding listener record.");
                    Log.w((String)NotificationManagerCompat.TAG, (String)((StringBuilder)object3).toString());
                    continue;
                }
                hashSet.add(object);
            }
            for (Object object22 : hashSet) {
                if (this.mRecordMap.containsKey(object22)) continue;
                if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                    set = new StringBuilder();
                    ((StringBuilder)((Object)set)).append("Adding listener record for ");
                    ((StringBuilder)((Object)set)).append(object22);
                    Log.d((String)NotificationManagerCompat.TAG, (String)((StringBuilder)((Object)set)).toString());
                }
                this.mRecordMap.put((ComponentName)object22, new ListenerRecord((ComponentName)object22));
            }
            set = this.mRecordMap.entrySet().iterator();
            while (set.hasNext()) {
                object = (Map.Entry)set.next();
                if (hashSet.contains(object.getKey())) continue;
                if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                    object22 = new StringBuilder();
                    ((StringBuilder)object22).append("Removing listener record for ");
                    ((StringBuilder)object22).append(object.getKey());
                    Log.d((String)NotificationManagerCompat.TAG, (String)((StringBuilder)object22).toString());
                }
                this.ensureServiceUnbound((ListenerRecord)object.getValue());
                set.remove();
            }
        }

        public boolean handleMessage(Message object) {
            int n = object.what;
            if (n == 0) {
                this.handleQueueTask((Task)object.obj);
                return true;
            }
            if (n == 1) {
                object = (ServiceConnectedEvent)object.obj;
                this.handleServiceConnected(object.componentName, object.iBinder);
                return true;
            }
            if (n == 2) {
                this.handleServiceDisconnected((ComponentName)object.obj);
                return true;
            }
            if (n != 3) {
                return false;
            }
            this.handleRetryListenerQueue((ComponentName)object.obj);
            return true;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connected to service ");
                stringBuilder.append((Object)componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            this.mHandler.obtainMessage(1, (Object)new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Disconnected from service ");
                stringBuilder.append((Object)componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            this.mHandler.obtainMessage(2, (Object)componentName).sendToTarget();
        }

        public void queueTask(Task task) {
            this.mHandler.obtainMessage(0, (Object)task).sendToTarget();
        }

        private static class ListenerRecord {
            boolean bound = false;
            final ComponentName componentName;
            int retryCount = 0;
            INotificationSideChannel service;
            ArrayDeque<Task> taskQueue = new ArrayDeque();

            ListenerRecord(ComponentName componentName) {
                this.componentName = componentName;
            }
        }

    }

    private static interface Task {
        public void send(INotificationSideChannel var1) throws RemoteException;
    }

}

