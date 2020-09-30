/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock;
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();

    static {
        mLock = new Object();
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()){

            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                LocalBroadcastManager.this.executePendingBroadcasts();
            }
        };
    }

    public static LocalBroadcastManager getInstance(Context object) {
        Object object2 = mLock;
        synchronized (object2) {
            LocalBroadcastManager localBroadcastManager;
            if (mInstance != null) return mInstance;
            mInstance = localBroadcastManager = new LocalBroadcastManager(object.getApplicationContext());
            return mInstance;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void executePendingBroadcasts() {
        block3 : do {
            BroadcastRecord[] arrbroadcastRecord;
            int n;
            Object object = this.mReceivers;
            synchronized (object) {
                n = this.mPendingBroadcasts.size();
                if (n <= 0) {
                    return;
                }
                arrbroadcastRecord = new BroadcastRecord[n];
                this.mPendingBroadcasts.toArray(arrbroadcastRecord);
                this.mPendingBroadcasts.clear();
            }
            int n2 = 0;
            do {
                if (n2 >= n) continue block3;
                BroadcastRecord broadcastRecord = arrbroadcastRecord[n2];
                int n3 = broadcastRecord.receivers.size();
                for (int i = 0; i < n3; ++i) {
                    object = broadcastRecord.receivers.get(i);
                    if (((ReceiverRecord)object).dead) continue;
                    ((ReceiverRecord)object).receiver.onReceive(this.mAppContext, broadcastRecord.intent);
                }
                ++n2;
            } while (true);
            break;
        } while (true);
    }

    public void registerReceiver(BroadcastReceiver object, IntentFilter intentFilter) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, (BroadcastReceiver)object);
            Object object2 = this.mReceivers.get(object);
            ArrayList<ReceiverRecord> arrayList = object2;
            if (object2 == null) {
                arrayList = new ArrayList<ReceiverRecord>(1);
                this.mReceivers.put((BroadcastReceiver)object, arrayList);
            }
            arrayList.add(receiverRecord);
            int n = 0;
            while (n < intentFilter.countActions()) {
                object2 = intentFilter.getAction(n);
                arrayList = this.mActions.get(object2);
                object = arrayList;
                if (arrayList == null) {
                    object = new ArrayList(1);
                    this.mActions.put((String)object2, (ArrayList<ReceiverRecord>)object);
                }
                object.add(receiverRecord);
                ++n;
            }
            return;
        }
    }

    public boolean sendBroadcast(Intent intent) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList<ReceiverRecord> arrayList;
            Object object;
            String string2 = intent.getAction();
            Object object2 = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
            Uri uri = intent.getData();
            String string3 = intent.getScheme();
            Set set = intent.getCategories();
            int n = (intent.getFlags() & 8) != 0 ? 1 : 0;
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Resolving type ");
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" scheme ");
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append(" of intent ");
                ((StringBuilder)object).append((Object)intent);
                Log.v((String)TAG, (String)((StringBuilder)object).toString());
            }
            if ((arrayList = this.mActions.get(intent.getAction())) == null) return false;
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Action list: ");
                ((StringBuilder)object).append(arrayList);
                Log.v((String)TAG, (String)((StringBuilder)object).toString());
            }
            Object object3 = null;
            for (int i = 0; i < arrayList.size(); ++i) {
                Object object4 = arrayList.get(i);
                if (n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Matching against filter ");
                    ((StringBuilder)object).append((Object)((ReceiverRecord)object4).filter);
                    Log.v((String)TAG, (String)((StringBuilder)object).toString());
                }
                if (((ReceiverRecord)object4).broadcasting) {
                    if (n == 0) continue;
                    Log.v((String)TAG, (String)"  Filter's target already added");
                    continue;
                }
                IntentFilter intentFilter = ((ReceiverRecord)object4).filter;
                object = object3;
                int n2 = intentFilter.match(string2, (String)object2, string3, uri, set, TAG);
                if (n2 >= 0) {
                    if (n != 0) {
                        object3 = new Object();
                        ((StringBuilder)object3).append("  Filter matched!  match=0x");
                        ((StringBuilder)object3).append(Integer.toHexString(n2));
                        Log.v((String)TAG, (String)((StringBuilder)object3).toString());
                    }
                    object3 = object == null ? new Object() : object;
                    ((ArrayList)object3).add(object4);
                    ((ReceiverRecord)object4).broadcasting = true;
                    continue;
                }
                if (n == 0) continue;
                object = n2 != -4 ? (n2 != -3 ? (n2 != -2 ? (n2 != -1 ? "unknown reason" : "type") : "data") : "action") : "category";
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("  Filter did not match: ");
                ((StringBuilder)object4).append((String)object);
                Log.v((String)TAG, (String)((StringBuilder)object4).toString());
            }
            if (object3 == null) return false;
            for (n = 0; n < ((ArrayList)object3).size(); ++n) {
                ((ReceiverRecord)object3.get((int)n)).broadcasting = false;
            }
            object2 = this.mPendingBroadcasts;
            object = new BroadcastRecord(intent, (ArrayList<ReceiverRecord>)object3);
            ((ArrayList)object2).add(object);
            if (this.mHandler.hasMessages(1)) return true;
            this.mHandler.sendEmptyMessage(1);
            return true;
        }
    }

    public void sendBroadcastSync(Intent intent) {
        if (!this.sendBroadcast(intent)) return;
        this.executePendingBroadcasts();
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList<ReceiverRecord> arrayList = this.mReceivers.remove((Object)broadcastReceiver);
            if (arrayList == null) {
                return;
            }
            int n = arrayList.size() - 1;
            while (n >= 0) {
                ReceiverRecord receiverRecord = arrayList.get(n);
                receiverRecord.dead = true;
                for (int i = 0; i < receiverRecord.filter.countActions(); ++i) {
                    String string2 = receiverRecord.filter.getAction(i);
                    ArrayList<ReceiverRecord> arrayList2 = this.mActions.get(string2);
                    if (arrayList2 == null) continue;
                    for (int j = arrayList2.size() - 1; j >= 0; --j) {
                        ReceiverRecord receiverRecord2 = arrayList2.get(j);
                        if (receiverRecord2.receiver != broadcastReceiver) continue;
                        receiverRecord2.dead = true;
                        arrayList2.remove(j);
                    }
                    if (arrayList2.size() > 0) continue;
                    this.mActions.remove(string2);
                }
                --n;
            }
            return;
        }
    }

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Receiver{");
            stringBuilder.append((Object)this.receiver);
            stringBuilder.append(" filter=");
            stringBuilder.append((Object)this.filter);
            if (this.dead) {
                stringBuilder.append(" DEAD");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

