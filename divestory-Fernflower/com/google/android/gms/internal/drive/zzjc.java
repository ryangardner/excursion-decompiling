package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzjc implements Serializable, Iterable<Byte> {
   public static final zzjc zznq;
   private static final zzji zznr;
   private static final Comparator<zzjc> zznt;
   private int zzns = 0;

   static {
      zznq = new zzjm(zzkm.zzsn);
      Object var0;
      if (zzix.zzbr()) {
         var0 = new zzjn((zzjd)null);
      } else {
         var0 = new zzjg((zzjd)null);
      }

      zznr = (zzji)var0;
      zznt = new zzje();
   }

   zzjc() {
   }

   private static int zza(byte var0) {
      return var0 & 255;
   }

   // $FF: synthetic method
   static int zzb(byte var0) {
      return zza(var0);
   }

   static int zzb(int var0, int var1, int var2) {
      int var3 = var1 - var0;
      if ((var0 | var1 | var3 | var2 - var1) < 0) {
         StringBuilder var4;
         if (var0 >= 0) {
            if (var1 < var0) {
               var4 = new StringBuilder(66);
               var4.append("Beginning index larger than ending index: ");
               var4.append(var0);
               var4.append(", ");
               var4.append(var1);
               throw new IndexOutOfBoundsException(var4.toString());
            } else {
               var4 = new StringBuilder(37);
               var4.append("End index: ");
               var4.append(var1);
               var4.append(" >= ");
               var4.append(var2);
               throw new IndexOutOfBoundsException(var4.toString());
            }
         } else {
            var4 = new StringBuilder(32);
            var4.append("Beginning index: ");
            var4.append(var0);
            var4.append(" < 0");
            throw new IndexOutOfBoundsException(var4.toString());
         }
      } else {
         return var3;
      }
   }

   public static zzjc zzb(byte[] var0, int var1, int var2) {
      zzb(var1, var1 + var2, var0.length);
      return new zzjm(zznr.zzc(var0, var1, var2));
   }

   public static zzjc zzk(String var0) {
      return new zzjm(var0.getBytes(zzkm.UTF_8));
   }

   static zzjk zzu(int var0) {
      return new zzjk(var0, (zzjd)null);
   }

   public abstract boolean equals(Object var1);

   public final int hashCode() {
      int var1 = this.zzns;
      int var2 = var1;
      if (var1 == 0) {
         var2 = this.size();
         var1 = this.zza(var2, 0, var2);
         var2 = var1;
         if (var1 == 0) {
            var2 = 1;
         }

         this.zzns = var2;
      }

      return var2;
   }

   // $FF: synthetic method
   public Iterator iterator() {
      return new zzjd(this);
   }

   public abstract int size();

   public final String toString() {
      return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
   }

   protected abstract int zza(int var1, int var2, int var3);

   public abstract zzjc zza(int var1, int var2);

   protected abstract String zza(Charset var1);

   abstract void zza(zzjb var1) throws IOException;

   public final String zzbt() {
      Charset var1 = zzkm.UTF_8;
      return this.size() == 0 ? "" : this.zza(var1);
   }

   public abstract boolean zzbu();

   protected final int zzbv() {
      return this.zzns;
   }

   public abstract byte zzs(int var1);

   abstract byte zzt(int var1);
}
