package androidx.core.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.Build.VERSION;
import android.os.Handler.Callback;
import android.provider.Settings.Secure;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
   private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
   private static String sEnabledNotificationListeners;
   private static final Object sEnabledNotificationListenersLock = new Object();
   private static final Object sLock = new Object();
   private static NotificationManagerCompat.SideChannelManager sSideChannelManager;
   private final Context mContext;
   private final NotificationManager mNotificationManager;

   private NotificationManagerCompat(Context var1) {
      this.mContext = var1;
      this.mNotificationManager = (NotificationManager)var1.getSystemService("notification");
   }

   public static NotificationManagerCompat from(Context var0) {
      return new NotificationManagerCompat(var0);
   }

   public static Set<String> getEnabledListenerPackages(Context var0) {
      Throwable var10000;
      boolean var10001;
      label388: {
         String var1 = Secure.getString(var0.getContentResolver(), "enabled_notification_listeners");
         Object var49 = sEnabledNotificationListenersLock;
         synchronized(var49){}
         if (var1 != null) {
            label387: {
               String[] var2;
               HashSet var3;
               int var4;
               try {
                  if (var1.equals(sEnabledNotificationListeners)) {
                     break label387;
                  }

                  var2 = var1.split(":", -1);
                  var3 = new HashSet(var2.length);
                  var4 = var2.length;
               } catch (Throwable var48) {
                  var10000 = var48;
                  var10001 = false;
                  break label388;
               }

               int var5 = 0;

               while(true) {
                  if (var5 >= var4) {
                     try {
                        sEnabledNotificationListenerPackages = var3;
                        sEnabledNotificationListeners = var1;
                        break;
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label388;
                     }
                  }

                  ComponentName var6;
                  try {
                     var6 = ComponentName.unflattenFromString(var2[var5]);
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break label388;
                  }

                  if (var6 != null) {
                     try {
                        var3.add(var6.getPackageName());
                     } catch (Throwable var46) {
                        var10000 = var46;
                        var10001 = false;
                        break label388;
                     }
                  }

                  ++var5;
               }
            }
         }

         label362:
         try {
            Set var51 = sEnabledNotificationListenerPackages;
            return var51;
         } catch (Throwable var44) {
            var10000 = var44;
            var10001 = false;
            break label362;
         }
      }

      while(true) {
         Throwable var50 = var10000;

         try {
            throw var50;
         } catch (Throwable var43) {
            var10000 = var43;
            var10001 = false;
            continue;
         }
      }
   }

   private void pushSideChannelQueue(NotificationManagerCompat.Task var1) {
      Object var2 = sLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (sSideChannelManager == null) {
               NotificationManagerCompat.SideChannelManager var3 = new NotificationManagerCompat.SideChannelManager(this.mContext.getApplicationContext());
               sSideChannelManager = var3;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            sSideChannelManager.queueTask(var1);
            return;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var16 = var10000;

         try {
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   private static boolean useSideChannelForNotification(Notification var0) {
      Bundle var2 = NotificationCompat.getExtras(var0);
      boolean var1;
      if (var2 != null && var2.getBoolean("android.support.useSideChannel")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean areNotificationsEnabled() {
      if (VERSION.SDK_INT >= 24) {
         return this.mNotificationManager.areNotificationsEnabled();
      } else {
         int var1 = VERSION.SDK_INT;
         boolean var2 = true;
         boolean var3 = var2;
         if (var1 >= 19) {
            AppOpsManager var4 = (AppOpsManager)this.mContext.getSystemService("appops");
            ApplicationInfo var5 = this.mContext.getApplicationInfo();
            String var6 = this.mContext.getApplicationContext().getPackageName();
            var1 = var5.uid;

            try {
               Class var8 = Class.forName(AppOpsManager.class.getName());
               var1 = (Integer)var8.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(var4, (Integer)var8.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class), var1, var6);
            } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | RuntimeException | ClassNotFoundException var7) {
               var3 = var2;
               return var3;
            }

            if (var1 == 0) {
               var3 = var2;
            } else {
               var3 = false;
            }
         }

         return var3;
      }
   }

   public void cancel(int var1) {
      this.cancel((String)null, var1);
   }

   public void cancel(String var1, int var2) {
      this.mNotificationManager.cancel(var1, var2);
      if (VERSION.SDK_INT <= 19) {
         this.pushSideChannelQueue(new NotificationManagerCompat.CancelTask(this.mContext.getPackageName(), var2, var1));
      }

   }

   public void cancelAll() {
      this.mNotificationManager.cancelAll();
      if (VERSION.SDK_INT <= 19) {
         this.pushSideChannelQueue(new NotificationManagerCompat.CancelTask(this.mContext.getPackageName()));
      }

   }

   public void createNotificationChannel(NotificationChannel var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.createNotificationChannel(var1);
      }

   }

   public void createNotificationChannelGroup(NotificationChannelGroup var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.createNotificationChannelGroup(var1);
      }

   }

   public void createNotificationChannelGroups(List<NotificationChannelGroup> var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.createNotificationChannelGroups(var1);
      }

   }

   public void createNotificationChannels(List<NotificationChannel> var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.createNotificationChannels(var1);
      }

   }

   public void deleteNotificationChannel(String var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.deleteNotificationChannel(var1);
      }

   }

   public void deleteNotificationChannelGroup(String var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mNotificationManager.deleteNotificationChannelGroup(var1);
      }

   }

   public int getImportance() {
      return VERSION.SDK_INT >= 24 ? this.mNotificationManager.getImportance() : -1000;
   }

   public NotificationChannel getNotificationChannel(String var1) {
      return VERSION.SDK_INT >= 26 ? this.mNotificationManager.getNotificationChannel(var1) : null;
   }

   public NotificationChannelGroup getNotificationChannelGroup(String var1) {
      if (VERSION.SDK_INT >= 28) {
         return this.mNotificationManager.getNotificationChannelGroup(var1);
      } else {
         if (VERSION.SDK_INT >= 26) {
            Iterator var2 = this.getNotificationChannelGroups().iterator();

            while(var2.hasNext()) {
               NotificationChannelGroup var3 = (NotificationChannelGroup)var2.next();
               if (var3.getId().equals(var1)) {
                  return var3;
               }
            }
         }

         return null;
      }
   }

   public List<NotificationChannelGroup> getNotificationChannelGroups() {
      return VERSION.SDK_INT >= 26 ? this.mNotificationManager.getNotificationChannelGroups() : Collections.emptyList();
   }

   public List<NotificationChannel> getNotificationChannels() {
      return VERSION.SDK_INT >= 26 ? this.mNotificationManager.getNotificationChannels() : Collections.emptyList();
   }

   public void notify(int var1, Notification var2) {
      this.notify((String)null, var1, var2);
   }

   public void notify(String var1, int var2, Notification var3) {
      if (useSideChannelForNotification(var3)) {
         this.pushSideChannelQueue(new NotificationManagerCompat.NotifyTask(this.mContext.getPackageName(), var2, var1, var3));
         this.mNotificationManager.cancel(var1, var2);
      } else {
         this.mNotificationManager.notify(var1, var2, var3);
      }

   }

   private static class CancelTask implements NotificationManagerCompat.Task {
      final boolean all;
      final int id;
      final String packageName;
      final String tag;

      CancelTask(String var1) {
         this.packageName = var1;
         this.id = 0;
         this.tag = null;
         this.all = true;
      }

      CancelTask(String var1, int var2, String var3) {
         this.packageName = var1;
         this.id = var2;
         this.tag = var3;
         this.all = false;
      }

      public void send(INotificationSideChannel var1) throws RemoteException {
         if (this.all) {
            var1.cancelAll(this.packageName);
         } else {
            var1.cancel(this.packageName, this.id, this.tag);
         }

      }

      public String toString() {
         StringBuilder var1 = new StringBuilder("CancelTask[");
         var1.append("packageName:");
         var1.append(this.packageName);
         var1.append(", id:");
         var1.append(this.id);
         var1.append(", tag:");
         var1.append(this.tag);
         var1.append(", all:");
         var1.append(this.all);
         var1.append("]");
         return var1.toString();
      }
   }

   private static class NotifyTask implements NotificationManagerCompat.Task {
      final int id;
      final Notification notif;
      final String packageName;
      final String tag;

      NotifyTask(String var1, int var2, String var3, Notification var4) {
         this.packageName = var1;
         this.id = var2;
         this.tag = var3;
         this.notif = var4;
      }

      public void send(INotificationSideChannel var1) throws RemoteException {
         var1.notify(this.packageName, this.id, this.tag, this.notif);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder("NotifyTask[");
         var1.append("packageName:");
         var1.append(this.packageName);
         var1.append(", id:");
         var1.append(this.id);
         var1.append(", tag:");
         var1.append(this.tag);
         var1.append("]");
         return var1.toString();
      }
   }

   private static class ServiceConnectedEvent {
      final ComponentName componentName;
      final IBinder iBinder;

      ServiceConnectedEvent(ComponentName var1, IBinder var2) {
         this.componentName = var1;
         this.iBinder = var2;
      }
   }

   private static class SideChannelManager implements Callback, ServiceConnection {
      private static final int MSG_QUEUE_TASK = 0;
      private static final int MSG_RETRY_LISTENER_QUEUE = 3;
      private static final int MSG_SERVICE_CONNECTED = 1;
      private static final int MSG_SERVICE_DISCONNECTED = 2;
      private Set<String> mCachedEnabledPackages = new HashSet();
      private final Context mContext;
      private final Handler mHandler;
      private final HandlerThread mHandlerThread;
      private final Map<ComponentName, NotificationManagerCompat.SideChannelManager.ListenerRecord> mRecordMap = new HashMap();

      SideChannelManager(Context var1) {
         this.mContext = var1;
         HandlerThread var2 = new HandlerThread("NotificationManagerCompat");
         this.mHandlerThread = var2;
         var2.start();
         this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
      }

      private boolean ensureServiceBound(NotificationManagerCompat.SideChannelManager.ListenerRecord var1) {
         if (var1.bound) {
            return true;
         } else {
            Intent var2 = (new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL")).setComponent(var1.componentName);
            var1.bound = this.mContext.bindService(var2, this, 33);
            if (var1.bound) {
               var1.retryCount = 0;
            } else {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unable to bind to listener ");
               var3.append(var1.componentName);
               Log.w("NotifManCompat", var3.toString());
               this.mContext.unbindService(this);
            }

            return var1.bound;
         }
      }

      private void ensureServiceUnbound(NotificationManagerCompat.SideChannelManager.ListenerRecord var1) {
         if (var1.bound) {
            this.mContext.unbindService(this);
            var1.bound = false;
         }

         var1.service = null;
      }

      private void handleQueueTask(NotificationManagerCompat.Task var1) {
         this.updateListenerMap();
         Iterator var2 = this.mRecordMap.values().iterator();

         while(var2.hasNext()) {
            NotificationManagerCompat.SideChannelManager.ListenerRecord var3 = (NotificationManagerCompat.SideChannelManager.ListenerRecord)var2.next();
            var3.taskQueue.add(var1);
            this.processListenerQueue(var3);
         }

      }

      private void handleRetryListenerQueue(ComponentName var1) {
         NotificationManagerCompat.SideChannelManager.ListenerRecord var2 = (NotificationManagerCompat.SideChannelManager.ListenerRecord)this.mRecordMap.get(var1);
         if (var2 != null) {
            this.processListenerQueue(var2);
         }

      }

      private void handleServiceConnected(ComponentName var1, IBinder var2) {
         NotificationManagerCompat.SideChannelManager.ListenerRecord var3 = (NotificationManagerCompat.SideChannelManager.ListenerRecord)this.mRecordMap.get(var1);
         if (var3 != null) {
            var3.service = INotificationSideChannel.Stub.asInterface(var2);
            var3.retryCount = 0;
            this.processListenerQueue(var3);
         }

      }

      private void handleServiceDisconnected(ComponentName var1) {
         NotificationManagerCompat.SideChannelManager.ListenerRecord var2 = (NotificationManagerCompat.SideChannelManager.ListenerRecord)this.mRecordMap.get(var1);
         if (var2 != null) {
            this.ensureServiceUnbound(var2);
         }

      }

      private void processListenerQueue(NotificationManagerCompat.SideChannelManager.ListenerRecord var1) {
         StringBuilder var2;
         if (Log.isLoggable("NotifManCompat", 3)) {
            var2 = new StringBuilder();
            var2.append("Processing component ");
            var2.append(var1.componentName);
            var2.append(", ");
            var2.append(var1.taskQueue.size());
            var2.append(" queued tasks");
            Log.d("NotifManCompat", var2.toString());
         }

         if (!var1.taskQueue.isEmpty()) {
            if (this.ensureServiceBound(var1) && var1.service != null) {
               while(true) {
                  NotificationManagerCompat.Task var6 = (NotificationManagerCompat.Task)var1.taskQueue.peek();
                  if (var6 == null) {
                     break;
                  }

                  try {
                     if (Log.isLoggable("NotifManCompat", 3)) {
                        StringBuilder var3 = new StringBuilder();
                        var3.append("Sending task ");
                        var3.append(var6);
                        Log.d("NotifManCompat", var3.toString());
                     }

                     var6.send(var1.service);
                     var1.taskQueue.remove();
                  } catch (DeadObjectException var4) {
                     if (Log.isLoggable("NotifManCompat", 3)) {
                        var2 = new StringBuilder();
                        var2.append("Remote service has died: ");
                        var2.append(var1.componentName);
                        Log.d("NotifManCompat", var2.toString());
                     }
                     break;
                  } catch (RemoteException var5) {
                     var2 = new StringBuilder();
                     var2.append("RemoteException communicating with ");
                     var2.append(var1.componentName);
                     Log.w("NotifManCompat", var2.toString(), var5);
                     break;
                  }
               }

               if (!var1.taskQueue.isEmpty()) {
                  this.scheduleListenerRetry(var1);
               }

            } else {
               this.scheduleListenerRetry(var1);
            }
         }
      }

      private void scheduleListenerRetry(NotificationManagerCompat.SideChannelManager.ListenerRecord var1) {
         if (!this.mHandler.hasMessages(3, var1.componentName)) {
            ++var1.retryCount;
            StringBuilder var2;
            if (var1.retryCount > 6) {
               var2 = new StringBuilder();
               var2.append("Giving up on delivering ");
               var2.append(var1.taskQueue.size());
               var2.append(" tasks to ");
               var2.append(var1.componentName);
               var2.append(" after ");
               var2.append(var1.retryCount);
               var2.append(" retries");
               Log.w("NotifManCompat", var2.toString());
               var1.taskQueue.clear();
            } else {
               int var3 = (1 << var1.retryCount - 1) * 1000;
               if (Log.isLoggable("NotifManCompat", 3)) {
                  var2 = new StringBuilder();
                  var2.append("Scheduling retry for ");
                  var2.append(var3);
                  var2.append(" ms");
                  Log.d("NotifManCompat", var2.toString());
               }

               Message var4 = this.mHandler.obtainMessage(3, var1.componentName);
               this.mHandler.sendMessageDelayed(var4, (long)var3);
            }
         }
      }

      private void updateListenerMap() {
         Set var1 = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
         if (!var1.equals(this.mCachedEnabledPackages)) {
            this.mCachedEnabledPackages = var1;
            List var2 = this.mContext.getPackageManager().queryIntentServices((new Intent()).setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 0);
            HashSet var3 = new HashSet();
            Iterator var8 = var2.iterator();

            while(var8.hasNext()) {
               ResolveInfo var4 = (ResolveInfo)var8.next();
               if (var1.contains(var4.serviceInfo.packageName)) {
                  ComponentName var5 = new ComponentName(var4.serviceInfo.packageName, var4.serviceInfo.name);
                  if (var4.serviceInfo.permission != null) {
                     StringBuilder var11 = new StringBuilder();
                     var11.append("Permission present on component ");
                     var11.append(var5);
                     var11.append(", not adding listener record.");
                     Log.w("NotifManCompat", var11.toString());
                  } else {
                     var3.add(var5);
                  }
               }
            }

            Iterator var12 = var3.iterator();

            while(var12.hasNext()) {
               ComponentName var9 = (ComponentName)var12.next();
               if (!this.mRecordMap.containsKey(var9)) {
                  if (Log.isLoggable("NotifManCompat", 3)) {
                     StringBuilder var6 = new StringBuilder();
                     var6.append("Adding listener record for ");
                     var6.append(var9);
                     Log.d("NotifManCompat", var6.toString());
                  }

                  this.mRecordMap.put(var9, new NotificationManagerCompat.SideChannelManager.ListenerRecord(var9));
               }
            }

            Iterator var7 = this.mRecordMap.entrySet().iterator();

            while(var7.hasNext()) {
               Entry var13 = (Entry)var7.next();
               if (!var3.contains(var13.getKey())) {
                  if (Log.isLoggable("NotifManCompat", 3)) {
                     StringBuilder var10 = new StringBuilder();
                     var10.append("Removing listener record for ");
                     var10.append(var13.getKey());
                     Log.d("NotifManCompat", var10.toString());
                  }

                  this.ensureServiceUnbound((NotificationManagerCompat.SideChannelManager.ListenerRecord)var13.getValue());
                  var7.remove();
               }
            }

         }
      }

      public boolean handleMessage(Message var1) {
         int var2 = var1.what;
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     return false;
                  } else {
                     this.handleRetryListenerQueue((ComponentName)var1.obj);
                     return true;
                  }
               } else {
                  this.handleServiceDisconnected((ComponentName)var1.obj);
                  return true;
               }
            } else {
               NotificationManagerCompat.ServiceConnectedEvent var3 = (NotificationManagerCompat.ServiceConnectedEvent)var1.obj;
               this.handleServiceConnected(var3.componentName, var3.iBinder);
               return true;
            }
         } else {
            this.handleQueueTask((NotificationManagerCompat.Task)var1.obj);
            return true;
         }
      }

      public void onServiceConnected(ComponentName var1, IBinder var2) {
         if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Connected to service ");
            var3.append(var1);
            Log.d("NotifManCompat", var3.toString());
         }

         this.mHandler.obtainMessage(1, new NotificationManagerCompat.ServiceConnectedEvent(var1, var2)).sendToTarget();
      }

      public void onServiceDisconnected(ComponentName var1) {
         if (Log.isLoggable("NotifManCompat", 3)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Disconnected from service ");
            var2.append(var1);
            Log.d("NotifManCompat", var2.toString());
         }

         this.mHandler.obtainMessage(2, var1).sendToTarget();
      }

      public void queueTask(NotificationManagerCompat.Task var1) {
         this.mHandler.obtainMessage(0, var1).sendToTarget();
      }

      private static class ListenerRecord {
         boolean bound = false;
         final ComponentName componentName;
         int retryCount = 0;
         INotificationSideChannel service;
         ArrayDeque<NotificationManagerCompat.Task> taskQueue = new ArrayDeque();

         ListenerRecord(ComponentName var1) {
            this.componentName = var1;
         }
      }
   }

   private interface Task {
      void send(INotificationSideChannel var1) throws RemoteException;
   }
}
