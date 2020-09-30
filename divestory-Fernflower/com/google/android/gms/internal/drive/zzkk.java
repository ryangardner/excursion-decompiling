package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzkk<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zzkk.zza<MessageType, BuilderType>> extends zzit<MessageType, BuilderType> {
   private static Map<Object, zzkk<?, ?>> zzrs = new ConcurrentHashMap();
   protected zzmy zzrq = zzmy.zzfa();
   private int zzrr = -1;

   private static <T extends zzkk<T, ?>> T zza(T var0, byte[] var1, int var2, int var3, zzjx var4) throws zzkq {
      var0 = (zzkk)var0.zza(zzkk.zze.zzsa, (Object)null, (Object)null);

      try {
         zzmf var5 = zzmd.zzej().zzq(var0);
         zziz var6 = new zziz(var4);
         var5.zza(var0, var1, 0, var3, var6);
         var0.zzbp();
         if (var0.zzne == 0) {
            return var0;
         } else {
            RuntimeException var9 = new RuntimeException();
            throw var9;
         }
      } catch (IOException var7) {
         if (var7.getCause() instanceof zzkq) {
            throw (zzkq)var7.getCause();
         } else {
            throw (new zzkq(var7.getMessage())).zzg(var0);
         }
      } catch (IndexOutOfBoundsException var8) {
         throw zzkq.zzdi().zzg(var0);
      }
   }

   protected static <T extends zzkk<T, ?>> T zza(T var0, byte[] var1, zzjx var2) throws zzkq {
      var0 = zza(var0, var1, 0, var1.length, var2);
      if (var0 != null && !var0.isInitialized()) {
         throw (new zzkq((new zzmw(var0)).getMessage())).zzg(var0);
      } else {
         return var0;
      }
   }

   protected static Object zza(zzlq var0, String var1, Object[] var2) {
      return new zzme(var0, var1, var2);
   }

   static Object zza(Method var0, Object var1, Object... var2) {
      try {
         Object var6 = var0.invoke(var1, var2);
         return var6;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", var3);
      } catch (InvocationTargetException var4) {
         Throwable var5 = var4.getCause();
         if (!(var5 instanceof RuntimeException)) {
            if (var5 instanceof Error) {
               throw (Error)var5;
            } else {
               throw new RuntimeException("Unexpected exception thrown by generated accessor method.", var5);
            }
         } else {
            throw (RuntimeException)var5;
         }
      }
   }

   protected static <T extends zzkk<?, ?>> void zza(Class<T> var0, T var1) {
      zzrs.put(var0, var1);
   }

   protected static final <T extends zzkk<T, ?>> boolean zza(T var0, boolean var1) {
      byte var2 = (Byte)var0.zza(zzkk.zze.zzrx, (Object)null, (Object)null);
      if (var2 == 1) {
         return true;
      } else if (var2 == 0) {
         return false;
      } else {
         boolean var3 = zzmd.zzej().zzq(var0).zzp(var0);
         if (var1) {
            int var5 = zzkk.zze.zzry;
            zzkk var4;
            if (var3) {
               var4 = var0;
            } else {
               var4 = null;
            }

            var0.zza(var5, var4, (Object)null);
         }

         return var3;
      }
   }

   static <T extends zzkk<?, ?>> T zzd(Class<T> var0) {
      zzkk var1 = (zzkk)zzrs.get(var0);
      zzkk var2 = var1;
      if (var1 == null) {
         try {
            Class.forName(var0.getName(), true, var0.getClassLoader());
         } catch (ClassNotFoundException var3) {
            throw new IllegalStateException("Class initialization cannot fail.", var3);
         }

         var2 = (zzkk)zzrs.get(var0);
      }

      var1 = var2;
      if (var2 == null) {
         var1 = (zzkk)((zzkk)zznd.zzh(var0)).zza(zzkk.zze.zzsc, (Object)null, (Object)null);
         if (var1 == null) {
            throw new IllegalStateException();
         }

         zzrs.put(var0, var1);
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !((zzkk)this.zza(zzkk.zze.zzsc, (Object)null, (Object)null)).getClass().isInstance(var1) ? false : zzmd.zzej().zzq(this).equals(this, (zzkk)var1);
      }
   }

   public int hashCode() {
      if (this.zzne != 0) {
         return this.zzne;
      } else {
         this.zzne = zzmd.zzej().zzq(this).hashCode(this);
         return this.zzne;
      }
   }

   public final boolean isInitialized() {
      return zza(this, Boolean.TRUE);
   }

   public String toString() {
      return zzlt.zza(this, super.toString());
   }

   protected abstract Object zza(int var1, Object var2, Object var3);

   public final void zzb(zzjr var1) throws IOException {
      zzmd.zzej().zzf(this.getClass()).zza(this, zzjt.zza(var1));
   }

   final int zzbm() {
      return this.zzrr;
   }

   protected final void zzbp() {
      zzmd.zzej().zzq(this).zzd(this);
   }

   protected final <MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zzkk.zza<MessageType, BuilderType>> BuilderType zzcw() {
      return (zzkk.zza)this.zza(zzkk.zze.zzsb, (Object)null, (Object)null);
   }

   public final int zzcx() {
      if (this.zzrr == -1) {
         this.zzrr = zzmd.zzej().zzq(this).zzn(this);
      }

      return this.zzrr;
   }

   // $FF: synthetic method
   public final zzlr zzcy() {
      zzkk.zza var1 = (zzkk.zza)this.zza(zzkk.zze.zzsb, (Object)null, (Object)null);
      var1.zza(this);
      return var1;
   }

   // $FF: synthetic method
   public final zzlr zzcz() {
      return (zzkk.zza)this.zza(zzkk.zze.zzsb, (Object)null, (Object)null);
   }

   // $FF: synthetic method
   public final zzlq zzda() {
      return (zzkk)this.zza(zzkk.zze.zzsc, (Object)null, (Object)null);
   }

   final void zzo(int var1) {
      this.zzrr = var1;
   }

   public static class zza<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zzkk.zza<MessageType, BuilderType>> extends zziu<MessageType, BuilderType> {
      private final MessageType zzrt;
      protected MessageType zzru;
      private boolean zzrv;

      protected zza(MessageType var1) {
         this.zzrt = var1;
         this.zzru = (zzkk)var1.zza(zzkk.zze.zzsa, (Object)null, (Object)null);
         this.zzrv = false;
      }

      private static void zza(MessageType var0, MessageType var1) {
         zzmd.zzej().zzq(var0).zzc(var0, var1);
      }

      // $FF: synthetic method
      public Object clone() throws CloneNotSupportedException {
         zzkk.zza var1 = (zzkk.zza)((zzkk)this.zzrt).zza(zzkk.zze.zzsb, (Object)null, (Object)null);
         var1.zza((zzkk)this.zzde());
         return var1;
      }

      public final boolean isInitialized() {
         return zzkk.zza(this.zzru, false);
      }

      // $FF: synthetic method
      protected final zziu zza(zzit var1) {
         return this.zza((zzkk)var1);
      }

      public final BuilderType zza(MessageType var1) {
         this.zzdb();
         zza(this.zzru, var1);
         return this;
      }

      // $FF: synthetic method
      public final zziu zzbn() {
         return (zzkk.zza)this.clone();
      }

      // $FF: synthetic method
      public final zzlq zzda() {
         return this.zzrt;
      }

      protected final void zzdb() {
         if (this.zzrv) {
            zzkk var1 = (zzkk)this.zzru.zza(zzkk.zze.zzsa, (Object)null, (Object)null);
            zza(var1, this.zzru);
            this.zzru = var1;
            this.zzrv = false;
         }

      }

      public MessageType zzdc() {
         if (this.zzrv) {
            return this.zzru;
         } else {
            this.zzru.zzbp();
            this.zzrv = true;
            return this.zzru;
         }
      }

      public final MessageType zzdd() {
         zzkk var1 = (zzkk)this.zzde();
         if (var1.isInitialized()) {
            return var1;
         } else {
            throw new zzmw(var1);
         }
      }

      // $FF: synthetic method
      public zzlq zzde() {
         return this.zzdc();
      }

      // $FF: synthetic method
      public zzlq zzdf() {
         return this.zzdd();
      }
   }

   public static final class zzb<T extends zzkk<T, ?>> extends zziv<T> {
      private final T zzrt;

      public zzb(T var1) {
         this.zzrt = var1;
      }
   }

   public abstract static class zzc<MessageType extends zzkk.zzc<MessageType, BuilderType>, BuilderType> extends zzkk<MessageType, BuilderType> implements zzls {
      protected zzkb<Object> zzrw = zzkb.zzcn();

      final zzkb<Object> zzdg() {
         if (this.zzrw.isImmutable()) {
            this.zzrw = (zzkb)this.zzrw.clone();
         }

         return this.zzrw;
      }
   }

   public static final class zzd<ContainingType extends zzlq, Type> extends zzjv<ContainingType, Type> {
   }

   public static enum zze {
      zzrx = 1,
      zzry = 2,
      zzrz = 3,
      zzsa = 4,
      zzsb = 5,
      zzsc = 6,
      zzsd = 7;

      // $FF: synthetic field
      private static final int[] zzse = new int[]{1, 2, 3, 4, 5, 6, 7};
      zzsf = 1,
      zzsg = 2;

      // $FF: synthetic field
      private static final int[] zzsh = new int[]{1, 2};
      zzsi = 1,
      zzsj = 2;

      // $FF: synthetic field
      private static final int[] zzsk = new int[]{1, 2};

      public static int[] zzdh() {
         return (int[])zzse.clone();
      }
   }
}
