package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class DynamiteModule {
   public static final DynamiteModule.VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzf();
   public static final DynamiteModule.VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zze();
   public static final DynamiteModule.VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzh();
   public static final DynamiteModule.VersionPolicy PREFER_LOCAL = new zzd();
   public static final DynamiteModule.VersionPolicy PREFER_REMOTE = new com.google.android.gms.dynamite.zza();
   public static final DynamiteModule.VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzc();
   private static Boolean zza;
   private static zzk zzb;
   private static zzm zzc;
   private static String zzd;
   private static int zze;
   private static final ThreadLocal<DynamiteModule.zza> zzf = new ThreadLocal();
   private static final DynamiteModule.VersionPolicy.zzb zzg = new com.google.android.gms.dynamite.zzb();
   private static final DynamiteModule.VersionPolicy zzh = new zzg();
   private final Context zzi;

   private DynamiteModule(Context var1) {
      this.zzi = (Context)Preconditions.checkNotNull(var1);
   }

   public static int getLocalVersion(Context var0, String var1) {
      String var7;
      StringBuilder var8;
      try {
         ClassLoader var2 = var0.getApplicationContext().getClassLoader();
         int var3 = String.valueOf(var1).length();
         var8 = new StringBuilder(var3 + 61);
         var8.append("com.google.android.gms.dynamite.descriptors.");
         var8.append(var1);
         var8.append(".ModuleDescriptor");
         Class var9 = var2.loadClass(var8.toString());
         Field var10 = var9.getDeclaredField("MODULE_ID");
         Field var11 = var9.getDeclaredField("MODULE_VERSION");
         if (!Objects.equal(var10.get((Object)null), var1)) {
            var7 = String.valueOf(var10.get((Object)null));
            int var4 = String.valueOf(var7).length();
            var3 = String.valueOf(var1).length();
            StringBuilder var12 = new StringBuilder(var4 + 51 + var3);
            var12.append("Module descriptor id '");
            var12.append(var7);
            var12.append("' didn't match expected id '");
            var12.append(var1);
            var12.append("'");
            Log.e("DynamiteModule", var12.toString());
            return 0;
         }

         var3 = var11.getInt((Object)null);
         return var3;
      } catch (ClassNotFoundException var5) {
         var8 = new StringBuilder(String.valueOf(var1).length() + 45);
         var8.append("Local module descriptor class for ");
         var8.append(var1);
         var8.append(" not found.");
         Log.w("DynamiteModule", var8.toString());
      } catch (Exception var6) {
         var7 = String.valueOf(var6.getMessage());
         if (var7.length() != 0) {
            var7 = "Failed to load module descriptor class: ".concat(var7);
         } else {
            var7 = new String("Failed to load module descriptor class: ");
         }

         Log.e("DynamiteModule", var7);
      }

      return 0;
   }

   public static int getRemoteVersion(Context var0, String var1) {
      return zza(var0, var1, false);
   }

   public static DynamiteModule load(Context param0, DynamiteModule.VersionPolicy param1, String param2) throws DynamiteModule.LoadingException {
      // $FF: Couldn't be decompiled
   }

   public static int zza(Context param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private static DynamiteModule zza(Context var0, String var1) {
      var1 = String.valueOf(var1);
      if (var1.length() != 0) {
         var1 = "Selected local version of ".concat(var1);
      } else {
         var1 = new String("Selected local version of ");
      }

      Log.i("DynamiteModule", var1);
      return new DynamiteModule(var0.getApplicationContext());
   }

   private static DynamiteModule zza(Context param0, String param1, int param2) throws DynamiteModule.LoadingException {
      // $FF: Couldn't be decompiled
   }

   private static zzk zza(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static Boolean zza() {
      synchronized(DynamiteModule.class){}

      Throwable var10000;
      boolean var10001;
      label133: {
         boolean var0;
         label132: {
            label131: {
               try {
                  if (zze >= 2) {
                     break label131;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label133;
               }

               var0 = false;
               break label132;
            }

            var0 = true;
         }

         label125:
         try {
            return var0;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label125;
         }
      }

      while(true) {
         Throwable var1 = var10000;

         try {
            throw var1;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            continue;
         }
      }
   }

   private static void zza(ClassLoader var0) throws DynamiteModule.LoadingException {
      Object var23;
      ClassNotFoundException var27;
      label102: {
         IllegalAccessException var26;
         label103: {
            InstantiationException var25;
            label104: {
               InvocationTargetException var24;
               label105: {
                  NoSuchMethodException var10000;
                  label87: {
                     boolean var10001;
                     IBinder var22;
                     try {
                        var22 = (IBinder)var0.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor().newInstance();
                     } catch (ClassNotFoundException var17) {
                        var27 = var17;
                        var10001 = false;
                        break label102;
                     } catch (IllegalAccessException var18) {
                        var26 = var18;
                        var10001 = false;
                        break label103;
                     } catch (InstantiationException var19) {
                        var25 = var19;
                        var10001 = false;
                        break label104;
                     } catch (InvocationTargetException var20) {
                        var24 = var20;
                        var10001 = false;
                        break label105;
                     } catch (NoSuchMethodException var21) {
                        var10000 = var21;
                        var10001 = false;
                        break label87;
                     }

                     if (var22 == null) {
                        var23 = null;
                     } else {
                        label88: {
                           try {
                              IInterface var1 = var22.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                              if (var1 instanceof zzm) {
                                 var23 = (zzm)var1;
                                 break label88;
                              }
                           } catch (ClassNotFoundException var12) {
                              var27 = var12;
                              var10001 = false;
                              break label102;
                           } catch (IllegalAccessException var13) {
                              var26 = var13;
                              var10001 = false;
                              break label103;
                           } catch (InstantiationException var14) {
                              var25 = var14;
                              var10001 = false;
                              break label104;
                           } catch (InvocationTargetException var15) {
                              var24 = var15;
                              var10001 = false;
                              break label105;
                           } catch (NoSuchMethodException var16) {
                              var10000 = var16;
                              var10001 = false;
                              break label87;
                           }

                           try {
                              var23 = new zzl(var22);
                           } catch (ClassNotFoundException var7) {
                              var27 = var7;
                              var10001 = false;
                              break label102;
                           } catch (IllegalAccessException var8) {
                              var26 = var8;
                              var10001 = false;
                              break label103;
                           } catch (InstantiationException var9) {
                              var25 = var9;
                              var10001 = false;
                              break label104;
                           } catch (InvocationTargetException var10) {
                              var24 = var10;
                              var10001 = false;
                              break label105;
                           } catch (NoSuchMethodException var11) {
                              var10000 = var11;
                              var10001 = false;
                              break label87;
                           }
                        }
                     }

                     try {
                        zzc = (zzm)var23;
                        return;
                     } catch (ClassNotFoundException var2) {
                        var27 = var2;
                        var10001 = false;
                        break label102;
                     } catch (IllegalAccessException var3) {
                        var26 = var3;
                        var10001 = false;
                        break label103;
                     } catch (InstantiationException var4) {
                        var25 = var4;
                        var10001 = false;
                        break label104;
                     } catch (InvocationTargetException var5) {
                        var24 = var5;
                        var10001 = false;
                        break label105;
                     } catch (NoSuchMethodException var6) {
                        var10000 = var6;
                        var10001 = false;
                     }
                  }

                  var23 = var10000;
                  throw new DynamiteModule.LoadingException("Failed to instantiate dynamite loader", (Throwable)var23, (com.google.android.gms.dynamite.zzb)null);
               }

               var23 = var24;
               throw new DynamiteModule.LoadingException("Failed to instantiate dynamite loader", (Throwable)var23, (com.google.android.gms.dynamite.zzb)null);
            }

            var23 = var25;
            throw new DynamiteModule.LoadingException("Failed to instantiate dynamite loader", (Throwable)var23, (com.google.android.gms.dynamite.zzb)null);
         }

         var23 = var26;
         throw new DynamiteModule.LoadingException("Failed to instantiate dynamite loader", (Throwable)var23, (com.google.android.gms.dynamite.zzb)null);
      }

      var23 = var27;
      throw new DynamiteModule.LoadingException("Failed to instantiate dynamite loader", (Throwable)var23, (com.google.android.gms.dynamite.zzb)null);
   }

   private static boolean zza(Cursor var0) {
      DynamiteModule.zza var1 = (DynamiteModule.zza)zzf.get();
      if (var1 != null && var1.zza == null) {
         var1.zza = var0;
         return true;
      } else {
         return false;
      }
   }

   private static int zzb(Context param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private static DynamiteModule zzb(Context param0, String param1, int param2) throws DynamiteModule.LoadingException, RemoteException {
      // $FF: Couldn't be decompiled
   }

   private static int zzc(Context param0, String param1, boolean param2) throws DynamiteModule.LoadingException {
      // $FF: Couldn't be decompiled
   }

   public final Context getModuleContext() {
      return this.zzi;
   }

   public final IBinder instantiate(String var1) throws DynamiteModule.LoadingException {
      Object var2;
      try {
         IBinder var6 = (IBinder)this.zzi.getClassLoader().loadClass(var1).newInstance();
         return var6;
      } catch (ClassNotFoundException var3) {
         var2 = var3;
      } catch (InstantiationException var4) {
         var2 = var4;
      } catch (IllegalAccessException var5) {
         var2 = var5;
      }

      var1 = String.valueOf(var1);
      if (var1.length() != 0) {
         var1 = "Failed to instantiate module class: ".concat(var1);
      } else {
         var1 = new String("Failed to instantiate module class: ");
      }

      throw new DynamiteModule.LoadingException(var1, (Throwable)var2, (com.google.android.gms.dynamite.zzb)null);
   }

   public static class DynamiteLoaderClassLoader {
      public static ClassLoader sClassLoader;
   }

   public static class LoadingException extends Exception {
      private LoadingException(String var1) {
         super(var1);
      }

      // $FF: synthetic method
      LoadingException(String var1, com.google.android.gms.dynamite.zzb var2) {
         this(var1);
      }

      private LoadingException(String var1, Throwable var2) {
         super(var1, var2);
      }

      // $FF: synthetic method
      LoadingException(String var1, Throwable var2, com.google.android.gms.dynamite.zzb var3) {
         this(var1, var2);
      }
   }

   public interface VersionPolicy {
      DynamiteModule.VersionPolicy.zza zza(Context var1, String var2, DynamiteModule.VersionPolicy.zzb var3) throws DynamiteModule.LoadingException;

      public static final class zza {
         public int zza = 0;
         public int zzb = 0;
         public int zzc = 0;
      }

      public interface zzb {
         int zza(Context var1, String var2);

         int zza(Context var1, String var2, boolean var3) throws DynamiteModule.LoadingException;
      }
   }

   private static final class zza {
      public Cursor zza;

      private zza() {
      }

      // $FF: synthetic method
      zza(com.google.android.gms.dynamite.zzb var1) {
         this();
      }
   }

   private static final class zzb implements DynamiteModule.VersionPolicy.zzb {
      private final int zza;
      private final int zzb;

      public zzb(int var1, int var2) {
         this.zza = var1;
         this.zzb = 0;
      }

      public final int zza(Context var1, String var2) {
         return this.zza;
      }

      public final int zza(Context var1, String var2, boolean var3) {
         return 0;
      }
   }
}
