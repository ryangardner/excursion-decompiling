/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public interface IFragmentWrapper
extends IInterface {
    public IObjectWrapper zza() throws RemoteException;

    public void zza(Intent var1) throws RemoteException;

    public void zza(Intent var1, int var2) throws RemoteException;

    public void zza(IObjectWrapper var1) throws RemoteException;

    public void zza(boolean var1) throws RemoteException;

    public Bundle zzb() throws RemoteException;

    public void zzb(IObjectWrapper var1) throws RemoteException;

    public void zzb(boolean var1) throws RemoteException;

    public int zzc() throws RemoteException;

    public void zzc(boolean var1) throws RemoteException;

    public IFragmentWrapper zzd() throws RemoteException;

    public void zzd(boolean var1) throws RemoteException;

    public IObjectWrapper zze() throws RemoteException;

    public boolean zzf() throws RemoteException;

    public String zzg() throws RemoteException;

    public IFragmentWrapper zzh() throws RemoteException;

    public int zzi() throws RemoteException;

    public boolean zzj() throws RemoteException;

    public IObjectWrapper zzk() throws RemoteException;

    public boolean zzl() throws RemoteException;

    public boolean zzm() throws RemoteException;

    public boolean zzn() throws RemoteException;

    public boolean zzo() throws RemoteException;

    public boolean zzp() throws RemoteException;

    public boolean zzq() throws RemoteException;

    public boolean zzr() throws RemoteException;

    public static abstract class Stub
    extends com.google.android.gms.internal.common.zza
    implements IFragmentWrapper {
        public Stub() {
            super("com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static IFragmentWrapper asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            if (!(iInterface instanceof IFragmentWrapper)) return new zza(iBinder);
            return (IFragmentWrapper)iInterface;
        }

        @Override
        protected final boolean zza(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            switch (n) {
                default: {
                    return false;
                }
                case 27: {
                    this.zzb(IObjectWrapper.Stub.asInterface(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 26: {
                    this.zza((Intent)zzd.zza(object, Intent.CREATOR), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 25: {
                    this.zza((Intent)zzd.zza(object, Intent.CREATOR));
                    parcel.writeNoException();
                    return true;
                }
                case 24: {
                    this.zzd(zzd.zza(object));
                    parcel.writeNoException();
                    return true;
                }
                case 23: {
                    this.zzc(zzd.zza(object));
                    parcel.writeNoException();
                    return true;
                }
                case 22: {
                    this.zzb(zzd.zza(object));
                    parcel.writeNoException();
                    return true;
                }
                case 21: {
                    this.zza(zzd.zza(object));
                    parcel.writeNoException();
                    return true;
                }
                case 20: {
                    this.zza(IObjectWrapper.Stub.asInterface(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 19: {
                    boolean bl = this.zzr();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 18: {
                    boolean bl = this.zzq();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 17: {
                    boolean bl = this.zzp();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 16: {
                    boolean bl = this.zzo();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 15: {
                    boolean bl = this.zzn();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 14: {
                    boolean bl = this.zzm();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 13: {
                    boolean bl = this.zzl();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 12: {
                    object = this.zzk();
                    parcel.writeNoException();
                    zzd.zza(parcel, (IInterface)object);
                    return true;
                }
                case 11: {
                    boolean bl = this.zzj();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 10: {
                    n = this.zzi();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 9: {
                    object = this.zzh();
                    parcel.writeNoException();
                    zzd.zza(parcel, (IInterface)object);
                    return true;
                }
                case 8: {
                    object = this.zzg();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 7: {
                    boolean bl = this.zzf();
                    parcel.writeNoException();
                    zzd.zza(parcel, bl);
                    return true;
                }
                case 6: {
                    object = this.zze();
                    parcel.writeNoException();
                    zzd.zza(parcel, (IInterface)object);
                    return true;
                }
                case 5: {
                    object = this.zzd();
                    parcel.writeNoException();
                    zzd.zza(parcel, (IInterface)object);
                    return true;
                }
                case 4: {
                    n = this.zzc();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 3: {
                    object = this.zzb();
                    parcel.writeNoException();
                    zzd.zzb(parcel, (Parcelable)object);
                    return true;
                }
                case 2: 
            }
            object = this.zza();
            parcel.writeNoException();
            zzd.zza(parcel, (IInterface)object);
            return true;
        }

        public static final class zza
        extends zzb
        implements IFragmentWrapper {
            zza(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.dynamic.IFragmentWrapper");
            }

            @Override
            public final IObjectWrapper zza() throws RemoteException {
                Parcel parcel = this.zza(2, this.a_());
                IObjectWrapper iObjectWrapper = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                parcel.recycle();
                return iObjectWrapper;
            }

            @Override
            public final void zza(Intent intent) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, (Parcelable)intent);
                this.zzb(25, parcel);
            }

            @Override
            public final void zza(Intent intent, int n) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, (Parcelable)intent);
                parcel.writeInt(n);
                this.zzb(26, parcel);
            }

            @Override
            public final void zza(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, iObjectWrapper);
                this.zzb(20, parcel);
            }

            @Override
            public final void zza(boolean bl) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, bl);
                this.zzb(21, parcel);
            }

            @Override
            public final Bundle zzb() throws RemoteException {
                Parcel parcel = this.zza(3, this.a_());
                Bundle bundle = (Bundle)zzd.zza(parcel, Bundle.CREATOR);
                parcel.recycle();
                return bundle;
            }

            @Override
            public final void zzb(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, iObjectWrapper);
                this.zzb(27, parcel);
            }

            @Override
            public final void zzb(boolean bl) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, bl);
                this.zzb(22, parcel);
            }

            @Override
            public final int zzc() throws RemoteException {
                Parcel parcel = this.zza(4, this.a_());
                int n = parcel.readInt();
                parcel.recycle();
                return n;
            }

            @Override
            public final void zzc(boolean bl) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, bl);
                this.zzb(23, parcel);
            }

            @Override
            public final IFragmentWrapper zzd() throws RemoteException {
                Parcel parcel = this.zza(5, this.a_());
                IFragmentWrapper iFragmentWrapper = Stub.asInterface(parcel.readStrongBinder());
                parcel.recycle();
                return iFragmentWrapper;
            }

            @Override
            public final void zzd(boolean bl) throws RemoteException {
                Parcel parcel = this.a_();
                zzd.zza(parcel, bl);
                this.zzb(24, parcel);
            }

            @Override
            public final IObjectWrapper zze() throws RemoteException {
                Parcel parcel = this.zza(6, this.a_());
                IObjectWrapper iObjectWrapper = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                parcel.recycle();
                return iObjectWrapper;
            }

            @Override
            public final boolean zzf() throws RemoteException {
                Parcel parcel = this.zza(7, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final String zzg() throws RemoteException {
                Parcel parcel = this.zza(8, this.a_());
                String string2 = parcel.readString();
                parcel.recycle();
                return string2;
            }

            @Override
            public final IFragmentWrapper zzh() throws RemoteException {
                Parcel parcel = this.zza(9, this.a_());
                IFragmentWrapper iFragmentWrapper = Stub.asInterface(parcel.readStrongBinder());
                parcel.recycle();
                return iFragmentWrapper;
            }

            @Override
            public final int zzi() throws RemoteException {
                Parcel parcel = this.zza(10, this.a_());
                int n = parcel.readInt();
                parcel.recycle();
                return n;
            }

            @Override
            public final boolean zzj() throws RemoteException {
                Parcel parcel = this.zza(11, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final IObjectWrapper zzk() throws RemoteException {
                Parcel parcel = this.zza(12, this.a_());
                IObjectWrapper iObjectWrapper = IObjectWrapper.Stub.asInterface(parcel.readStrongBinder());
                parcel.recycle();
                return iObjectWrapper;
            }

            @Override
            public final boolean zzl() throws RemoteException {
                Parcel parcel = this.zza(13, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzm() throws RemoteException {
                Parcel parcel = this.zza(14, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzn() throws RemoteException {
                Parcel parcel = this.zza(15, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzo() throws RemoteException {
                Parcel parcel = this.zza(16, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzp() throws RemoteException {
                Parcel parcel = this.zza(17, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzq() throws RemoteException {
                Parcel parcel = this.zza(18, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }

            @Override
            public final boolean zzr() throws RemoteException {
                Parcel parcel = this.zza(19, this.a_());
                boolean bl = zzd.zza(parcel);
                parcel.recycle();
                return bl;
            }
        }

    }

}

