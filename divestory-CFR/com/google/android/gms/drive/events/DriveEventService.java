/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.events;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzd;
import com.google.android.gms.drive.events.zzh;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.drive.events.zzv;
import com.google.android.gms.internal.drive.zzet;
import com.google.android.gms.internal.drive.zzfp;
import com.google.android.gms.internal.drive.zzir;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DriveEventService
extends Service
implements ChangeListener,
CompletionListener,
zzd,
zzi {
    public static final String ACTION_HANDLE_EVENT = "com.google.android.gms.drive.events.HANDLE_EVENT";
    private static final GmsLogger zzbz = new GmsLogger("DriveEventService", "");
    private final String name;
    private CountDownLatch zzcj;
    zza zzck;
    boolean zzcl = false;
    private int zzcm = -1;

    protected DriveEventService() {
        this("DriveEventService");
    }

    protected DriveEventService(String string2) {
        this.name = string2;
    }

    private final void zza(zzfp safeParcelable) {
        safeParcelable = ((zzfp)safeParcelable).zzat();
        try {
            int n = safeParcelable.getType();
            if (n == 1) {
                this.onChange((ChangeEvent)safeParcelable);
                return;
            }
            if (n == 2) {
                this.onCompletion((CompletionEvent)safeParcelable);
                return;
            }
            if (n == 4) {
                this.zza((com.google.android.gms.drive.events.zzb)safeParcelable);
                return;
            }
            if (n != 7) {
                zzbz.wfmt("DriveEventService", "Unhandled event: %s", safeParcelable);
                return;
            }
            safeParcelable = (zzv)safeParcelable;
            zzbz.wfmt("DriveEventService", "Unhandled transfer state event in %s: %s", this.name, safeParcelable);
            return;
        }
        catch (Exception exception) {
            zzbz.e("DriveEventService", String.format("Error handling event in %s", this.name), exception);
            return;
        }
    }

    static /* synthetic */ CountDownLatch zzb(DriveEventService driveEventService) {
        return driveEventService.zzcj;
    }

    private final void zzw() throws SecurityException {
        int n = this.getCallingUid();
        if (n == this.zzcm) {
            return;
        }
        if (!UidVerifier.isGooglePlayServicesUid((Context)this, n)) throw new SecurityException("Caller is not GooglePlayServices");
        this.zzcm = n;
    }

    protected int getCallingUid() {
        return Binder.getCallingUid();
    }

    public final IBinder onBind(Intent object) {
        synchronized (this) {
            if (!ACTION_HANDLE_EVENT.equals(object.getAction())) return null;
            if (this.zzck == null && !this.zzcl) {
                this.zzcl = true;
                object = new CountDownLatch(1);
                Object object2 = new CountDownLatch(1);
                this.zzcj = object2;
                object2 = new zzh(this, (CountDownLatch)object);
                ((Thread)object2).start();
                try {
                    if (!((CountDownLatch)object).await(5000L, TimeUnit.MILLISECONDS)) {
                        zzbz.e("DriveEventService", "Failed to synchronously initialize event handler.");
                    }
                }
                catch (InterruptedException interruptedException) {
                    object = new RuntimeException("Unable to start event handler", interruptedException);
                    throw object;
                }
            }
            object = new zzb(null);
            object = ((com.google.android.gms.internal.drive.zzb)((Object)object)).asBinder();
            return object;
        }
    }

    @Override
    public void onChange(ChangeEvent changeEvent) {
        zzbz.wfmt("DriveEventService", "Unhandled change event in %s: %s", this.name, changeEvent);
    }

    @Override
    public void onCompletion(CompletionEvent completionEvent) {
        zzbz.wfmt("DriveEventService", "Unhandled completion event in %s: %s", this.name, completionEvent);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void onDestroy() {
        // MONITORENTER : this
        if (this.zzck != null) {
            var1_1 = zza.zza(this.zzck);
            this.zzck.sendMessage(var1_1);
            this.zzck = null;
            try {
                if (!this.zzcj.await(5000L, TimeUnit.MILLISECONDS)) {
                    DriveEventService.zzbz.w("DriveEventService", "Failed to synchronously quit event handler. Will quit itself");
                }
lbl10: // 4 sources:
                do {
                    this.zzcj = null;
                    break;
                } while (true);
            }
            catch (InterruptedException var1_2) {
                ** continue;
            }
        }
        super.onDestroy();
        // MONITOREXIT : this
    }

    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public final void zza(com.google.android.gms.drive.events.zzb zzb2) {
        zzbz.wfmt("DriveEventService", "Unhandled changes available event in %s: %s", this.name, zzb2);
    }

    static final class zza
    extends zzir {
        private final WeakReference<DriveEventService> zzcp;

        private zza(DriveEventService driveEventService) {
            this.zzcp = new WeakReference<DriveEventService>(driveEventService);
        }

        /* synthetic */ zza(DriveEventService driveEventService, zzh zzh2) {
            this(driveEventService);
        }

        static /* synthetic */ Message zza(zza zza2) {
            return zza2.zzy();
        }

        private final Message zzb(zzfp zzfp2) {
            return this.obtainMessage(1, (Object)zzfp2);
        }

        private final Message zzy() {
            return this.obtainMessage(2);
        }

        public final void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    zzbz.wfmt("DriveEventService", "Unexpected message type: %s", message.what);
                    return;
                }
                this.getLooper().quit();
                return;
            }
            DriveEventService driveEventService = (DriveEventService)this.zzcp.get();
            if (driveEventService != null) {
                driveEventService.zza((zzfp)message.obj);
                return;
            }
            this.getLooper().quit();
        }
    }

    final class zzb
    extends zzet {
        private zzb() {
        }

        /* synthetic */ zzb(zzh zzh2) {
            this();
        }

        @Override
        public final void zzc(zzfp zzfp2) throws RemoteException {
            DriveEventService driveEventService = DriveEventService.this;
            synchronized (driveEventService) {
                DriveEventService.this.zzw();
                if (DriveEventService.this.zzck != null) {
                    zzfp2 = DriveEventService.this.zzck.zzb(zzfp2);
                    DriveEventService.this.zzck.sendMessage((Message)zzfp2);
                } else {
                    zzbz.e("DriveEventService", "Receiving event before initialize is completed.");
                }
                return;
            }
        }
    }

}

