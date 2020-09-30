package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGmsServiceBroker extends IInterface {
   void getService(IGmsCallbacks var1, GetServiceRequest var2) throws RemoteException;

   public abstract static class Stub extends Binder implements IGmsServiceBroker {
      public Stub() {
         this.attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         if (var1 > 16777215) {
            return super.onTransact(var1, var2, var3, var4);
         } else {
            var2.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            IBinder var5 = var2.readStrongBinder();
            GetServiceRequest var6 = null;
            Object var7;
            if (var5 == null) {
               var7 = null;
            } else {
               IInterface var10 = var5.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
               if (var10 instanceof IGmsCallbacks) {
                  var7 = (IGmsCallbacks)var10;
               } else {
                  var7 = new zzp(var5);
               }
            }

            if (var1 == 46) {
               if (var2.readInt() != 0) {
                  var6 = (GetServiceRequest)GetServiceRequest.CREATOR.createFromParcel(var2);
               }

               this.getService((IGmsCallbacks)var7, var6);
               ((Parcel)Preconditions.checkNotNull(var3)).writeNoException();
               return true;
            } else if (var1 == 47) {
               if (var2.readInt() != 0) {
                  zzx var9 = (zzx)zzx.CREATOR.createFromParcel(var2);
               }

               throw new UnsupportedOperationException();
            } else {
               var2.readInt();
               if (var1 != 4) {
                  var2.readString();
               }

               Bundle var8;
               if (var1 != 1) {
                  if (var1 != 2 && var1 != 23 && var1 != 25 && var1 != 27) {
                     label134: {
                        if (var1 != 30) {
                           if (var1 == 34) {
                              var2.readString();
                              throw new UnsupportedOperationException();
                           }

                           if (var1 == 41 || var1 == 43 || var1 == 37 || var1 == 38) {
                              break label134;
                           }

                           switch(var1) {
                           case 5:
                           case 6:
                           case 7:
                           case 8:
                           case 11:
                           case 12:
                           case 13:
                           case 14:
                           case 15:
                           case 16:
                           case 17:
                           case 18:
                              break label134;
                           case 9:
                              var2.readString();
                              var2.createStringArray();
                              var2.readString();
                              var2.readStrongBinder();
                              var2.readString();
                              if (var2.readInt() != 0) {
                                 var8 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                              }

                              throw new UnsupportedOperationException();
                           case 10:
                              var2.readString();
                              var2.createStringArray();
                              throw new UnsupportedOperationException();
                           case 19:
                              var2.readStrongBinder();
                              if (var2.readInt() != 0) {
                                 var8 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                              }

                              throw new UnsupportedOperationException();
                           case 20:
                              break;
                           default:
                              throw new UnsupportedOperationException();
                           }
                        }

                        var2.createStringArray();
                        var2.readString();
                        if (var2.readInt() != 0) {
                           var8 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                        }

                        throw new UnsupportedOperationException();
                     }
                  }

                  if (var2.readInt() != 0) {
                     var8 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                  }
               } else {
                  var2.readString();
                  var2.createStringArray();
                  var2.readString();
                  if (var2.readInt() != 0) {
                     var8 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                  }
               }

               throw new UnsupportedOperationException();
            }
         }
      }

      private static final class zza implements IGmsServiceBroker {
         private final IBinder zza;

         zza(IBinder var1) {
            this.zza = var1;
         }

         public final IBinder asBinder() {
            return this.zza;
         }

         public final void getService(IGmsCallbacks var1, GetServiceRequest var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            label339: {
               Throwable var10000;
               label343: {
                  boolean var10001;
                  try {
                     var3.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label343;
                  }

                  IBinder var47;
                  if (var1 != null) {
                     try {
                        var47 = var1.asBinder();
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label343;
                     }
                  } else {
                     var47 = null;
                  }

                  try {
                     var3.writeStrongBinder(var47);
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break label343;
                  }

                  if (var2 != null) {
                     try {
                        var3.writeInt(1);
                        var2.writeToParcel(var3, 0);
                     } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break label343;
                     }
                  } else {
                     try {
                        var3.writeInt(0);
                     } catch (Throwable var42) {
                        var10000 = var42;
                        var10001 = false;
                        break label343;
                     }
                  }

                  label321:
                  try {
                     this.zza.transact(46, var3, var4, 0);
                     var4.readException();
                     break label339;
                  } catch (Throwable var41) {
                     var10000 = var41;
                     var10001 = false;
                     break label321;
                  }
               }

               Throwable var48 = var10000;
               var4.recycle();
               var3.recycle();
               throw var48;
            }

            var4.recycle();
            var3.recycle();
         }
      }
   }
}
