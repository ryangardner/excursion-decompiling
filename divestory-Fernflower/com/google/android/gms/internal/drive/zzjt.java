package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzjt implements zzns {
   private final zzjr zznx;

   private zzjt(zzjr var1) {
      var1 = (zzjr)zzkm.zza(var1, (String)"output");
      this.zznx = var1;
      var1.zzoh = this;
   }

   public static zzjt zza(zzjr var0) {
      return var0.zzoh != null ? var0.zzoh : new zzjt(var0);
   }

   public final void zza(int var1, double var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final void zza(int var1, float var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final void zza(int var1, long var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final void zza(int var1, zzjc var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final <K, V> void zza(int var1, zzlj<K, V> var2, Map<K, V> var3) throws IOException {
      Iterator var5 = var3.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var4 = (Entry)var5.next();
         this.zznx.zzb(var1, 2);
         this.zznx.zzy(zzli.zza(var2, var4.getKey(), var4.getValue()));
         zzli.zza(this.zznx, var2, var4.getKey(), var4.getValue());
      }

   }

   public final void zza(int var1, Object var2) throws IOException {
      if (var2 instanceof zzjc) {
         this.zznx.zzb(var1, (zzjc)var2);
      } else {
         this.zznx.zza(var1, (zzlq)var2);
      }
   }

   public final void zza(int var1, Object var2, zzmf var3) throws IOException {
      this.zznx.zza(var1, (zzlq)var2, var3);
   }

   public final void zza(int var1, String var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final void zza(int var1, List<String> var2) throws IOException {
      boolean var3 = var2 instanceof zzkz;
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         zzkz var6 = (zzkz)var2;

         for(var4 = var5; var4 < var2.size(); ++var4) {
            Object var7 = var6.zzao(var4);
            if (var7 instanceof String) {
               this.zznx.zza(var1, (String)var7);
            } else {
               this.zznx.zza(var1, (zzjc)var7);
            }
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zza(var1, (String)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zza(int var1, List<?> var2, zzmf var3) throws IOException {
      for(int var4 = 0; var4 < var2.size(); ++var4) {
         this.zza(var1, var2.get(var4), var3);
      }

   }

   public final void zza(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzac((Integer)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzx((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzc(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzak(int var1) throws IOException {
      this.zznx.zzb(var1, 3);
   }

   public final void zzal(int var1) throws IOException {
      this.zznx.zzb(var1, 4);
   }

   public final void zzb(int var1, long var2) throws IOException {
      this.zznx.zzb(var1, var2);
   }

   public final void zzb(int var1, Object var2, zzmf var3) throws IOException {
      zzjr var4 = this.zznx;
      zzlq var5 = (zzlq)var2;
      var4.zzb(var1, 3);
      var3.zza(var5, var4.zzoh);
      var4.zzb(var1, 4);
   }

   public final void zzb(int var1, List<zzjc> var2) throws IOException {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.zznx.zza(var1, (zzjc)var2.get(var3));
      }

   }

   public final void zzb(int var1, List<?> var2, zzmf var3) throws IOException {
      for(int var4 = 0; var4 < var2.size(); ++var4) {
         this.zzb(var1, var2.get(var4), var3);
      }

   }

   public final void zzb(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzaf((Integer)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzaa((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzf(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzb(int var1, boolean var2) throws IOException {
      this.zznx.zzb(var1, var2);
   }

   public final void zzc(int var1, int var2) throws IOException {
      this.zznx.zzc(var1, var2);
   }

   public final void zzc(int var1, long var2) throws IOException {
      this.zznx.zzc(var1, var2);
   }

   public final void zzc(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzo((Long)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzl((Long)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zza(var1, (Long)var2.get(var4));
            ++var4;
         }

      }
   }

   public final int zzcd() {
      return zzkk.zze.zzsi;
   }

   public final void zzd(int var1, int var2) throws IOException {
      this.zznx.zzd(var1, var2);
   }

   public final void zzd(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzp((Long)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzl((Long)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zza(var1, (Long)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zze(int var1, int var2) throws IOException {
      this.zznx.zze(var1, var2);
   }

   public final void zze(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzr((Long)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzn((Long)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzc(var1, (Long)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzf(int var1, int var2) throws IOException {
      this.zznx.zzf(var1, var2);
   }

   public final void zzf(int var1, List<Float> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzb((Float)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zza((Float)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zza(var1, (Float)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzg(int var1, List<Double> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzb((Double)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zza((Double)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zza(var1, (Double)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzh(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzah((Integer)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzx((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzc(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzi(int var1, long var2) throws IOException {
      this.zznx.zza(var1, var2);
   }

   public final void zzi(int var1, List<Boolean> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzd((Boolean)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzc((Boolean)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzb(var1, (Boolean)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzj(int var1, long var2) throws IOException {
      this.zznx.zzc(var1, var2);
   }

   public final void zzj(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzad((Integer)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzy((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzd(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzk(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzag((Integer)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzaa((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzf(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzl(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzs((Long)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzn((Long)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzc(var1, (Long)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzm(int var1, int var2) throws IOException {
      this.zznx.zzf(var1, var2);
   }

   public final void zzm(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var1 = 0;

         for(var4 = 0; var1 < var2.size(); ++var1) {
            var4 += zzjr.zzae((Integer)var2.get(var1));
         }

         this.zznx.zzy(var4);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzz((Integer)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zze(var1, (Integer)var2.get(var4));
            ++var4;
         }

      }
   }

   public final void zzn(int var1, int var2) throws IOException {
      this.zznx.zzc(var1, var2);
   }

   public final void zzn(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.zznx.zzb(var1, 2);
         var4 = 0;

         for(var1 = 0; var4 < var2.size(); ++var4) {
            var1 += zzjr.zzq((Long)var2.get(var4));
         }

         this.zznx.zzy(var1);

         for(var1 = var5; var1 < var2.size(); ++var1) {
            this.zznx.zzm((Long)var2.get(var1));
         }

      } else {
         while(var4 < var2.size()) {
            this.zznx.zzb(var1, (Long)var2.get(var4));
            ++var4;
         }

      }
   }
}
