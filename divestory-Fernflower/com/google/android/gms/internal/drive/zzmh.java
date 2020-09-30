package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzmh {
   private static final Class<?> zzuz = zzep();
   private static final zzmx<?, ?> zzva = zzf(false);
   private static final zzmx<?, ?> zzvb = zzf(true);
   private static final zzmx<?, ?> zzvc = new zzmz();

   static int zza(List<Long> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzle) {
            zzle var5 = (zzle)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzo(var5.getLong(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzo((Long)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   private static <UT, UB> UB zza(int var0, int var1, UB var2, zzmx<UT, UB> var3) {
      Object var4 = var2;
      if (var2 == null) {
         var4 = var3.zzez();
      }

      var3.zza(var4, var0, (long)var1);
      return var4;
   }

   static <UT, UB> UB zza(int var0, List<Integer> var1, zzko var2, UB var3, zzmx<UT, UB> var4) {
      if (var2 == null) {
         return var3;
      } else {
         int var6;
         Object var9;
         if (var1 instanceof RandomAccess) {
            int var5 = var1.size();
            var6 = 0;

            int var7;
            for(var7 = 0; var6 < var5; ++var6) {
               int var8 = (Integer)var1.get(var6);
               if (var2.zzan(var8)) {
                  if (var6 != var7) {
                     var1.set(var7, var8);
                  }

                  ++var7;
               } else {
                  var3 = zza(var0, var8, var3, var4);
               }
            }

            var9 = var3;
            if (var7 != var5) {
               var1.subList(var7, var5).clear();
               var9 = var3;
            }
         } else {
            Iterator var10 = var1.iterator();

            while(true) {
               var9 = var3;
               if (!var10.hasNext()) {
                  break;
               }

               var6 = (Integer)var10.next();
               if (!var2.zzan(var6)) {
                  var3 = zza(var0, var6, var3, var4);
                  var10.remove();
               }
            }
         }

         return var9;
      }
   }

   public static void zza(int var0, List<String> var1, zzns var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zza(var0, var1);
      }

   }

   public static void zza(int var0, List<?> var1, zzns var2, zzmf var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zza(var0, var1, var3);
      }

   }

   public static void zza(int var0, List<Double> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzg(var0, var1, var3);
      }

   }

   static <T, FT extends zzkd<FT>> void zza(zzjy<FT> var0, T var1, T var2) {
      zzkb var3 = var0.zzb(var2);
      if (!var3.zzos.isEmpty()) {
         var0.zzc(var1).zza(var3);
      }

   }

   static <T> void zza(zzll var0, T var1, T var2, long var3) {
      zznd.zza(var1, var3, var0.zzb(zznd.zzo(var1, var3), zznd.zzo(var2, var3)));
   }

   static <T, UT, UB> void zza(zzmx<UT, UB> var0, T var1, T var2) {
      var0.zze(var1, var0.zzg(var0.zzr(var1), var0.zzr(var2)));
   }

   static int zzb(List<Long> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzle) {
            zzle var5 = (zzle)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzp(var5.getLong(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzp((Long)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   public static void zzb(int var0, List<zzjc> var1, zzns var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzb(var0, var1);
      }

   }

   public static void zzb(int var0, List<?> var1, zzns var2, zzmf var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzb(var0, var1, var3);
      }

   }

   public static void zzb(int var0, List<Float> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzf(var0, var1, var3);
      }

   }

   static int zzc(int var0, Object var1, zzmf var2) {
      return var1 instanceof zzkx ? zzjr.zza(var0, (zzkx)var1) : zzjr.zzb(var0, (zzlq)var1, var2);
   }

   static int zzc(int var0, List<?> var1) {
      int var2 = var1.size();
      int var3 = 0;
      byte var4 = 0;
      if (var2 == 0) {
         return 0;
      } else {
         int var5 = zzjr.zzab(var0) * var2;
         var0 = var5;
         Object var6;
         if (var1 instanceof zzkz) {
            zzkz var7 = (zzkz)var1;
            var0 = var5;
            var3 = var4;

            while(true) {
               var5 = var0;
               if (var3 >= var2) {
                  break;
               }

               var6 = var7.zzao(var3);
               if (var6 instanceof zzjc) {
                  var5 = zzjr.zzb((zzjc)var6);
               } else {
                  var5 = zzjr.zzm((String)var6);
               }

               var0 += var5;
               ++var3;
            }
         } else {
            while(true) {
               var5 = var0;
               if (var3 >= var2) {
                  break;
               }

               var6 = var1.get(var3);
               if (var6 instanceof zzjc) {
                  var5 = zzjr.zzb((zzjc)var6);
               } else {
                  var5 = zzjr.zzm((String)var6);
               }

               var0 += var5;
               ++var3;
            }
         }

         return var5;
      }
   }

   static int zzc(int var0, List<?> var1, zzmf var2) {
      int var3 = var1.size();
      byte var4 = 0;
      if (var3 == 0) {
         return 0;
      } else {
         int var5 = zzjr.zzab(var0) * var3;

         for(var0 = var4; var0 < var3; ++var0) {
            Object var6 = var1.get(var0);
            int var7;
            if (var6 instanceof zzkx) {
               var7 = zzjr.zza((zzkx)var6);
            } else {
               var7 = zzjr.zza((zzlq)var6, var2);
            }

            var5 += var7;
         }

         return var5;
      }
   }

   static int zzc(List<Long> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzle) {
            zzle var5 = (zzle)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzq(var5.getLong(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzq((Long)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   public static void zzc(int var0, List<Long> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzc(var0, var1, var3);
      }

   }

   static int zzd(int var0, List<zzjc> var1) {
      int var2 = var1.size();
      byte var3 = 0;
      if (var2 == 0) {
         return 0;
      } else {
         var2 *= zzjr.zzab(var0);
         var0 = var3;

         int var4;
         for(var4 = var2; var0 < var1.size(); ++var0) {
            var4 += zzjr.zzb((zzjc)var1.get(var0));
         }

         return var4;
      }
   }

   static int zzd(int var0, List<zzlq> var1, zzmf var2) {
      int var3 = var1.size();
      int var4 = 0;
      if (var3 == 0) {
         return 0;
      } else {
         int var5;
         for(var5 = 0; var4 < var3; ++var4) {
            var5 += zzjr.zzc(var0, (zzlq)var1.get(var4), var2);
         }

         return var5;
      }
   }

   static int zzd(List<Integer> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzkl) {
            zzkl var5 = (zzkl)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzah(var5.getInt(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzah((Integer)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   public static void zzd(int var0, List<Long> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzd(var0, var1, var3);
      }

   }

   static boolean zzd(Object var0, Object var1) {
      return var0 == var1 || var0 != null && var0.equals(var1);
   }

   static int zze(List<Integer> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzkl) {
            zzkl var5 = (zzkl)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzac(var5.getInt(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzac((Integer)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   public static void zze(int var0, List<Long> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzn(var0, var1, var3);
      }

   }

   public static zzmx<?, ?> zzem() {
      return zzva;
   }

   public static zzmx<?, ?> zzen() {
      return zzvb;
   }

   public static zzmx<?, ?> zzeo() {
      return zzvc;
   }

   private static Class<?> zzep() {
      try {
         Class var0 = Class.forName("com.google.protobuf.GeneratedMessage");
         return var0;
      } finally {
         ;
      }
   }

   private static Class<?> zzeq() {
      try {
         Class var0 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
         return var0;
      } finally {
         ;
      }
   }

   static int zzf(List<Integer> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzkl) {
            zzkl var5 = (zzkl)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzad(var5.getInt(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzad((Integer)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   private static zzmx<?, ?> zzf(boolean param0) {
      // $FF: Couldn't be decompiled
   }

   public static void zzf(int var0, List<Long> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zze(var0, var1, var3);
      }

   }

   static int zzg(List<Integer> var0) {
      int var1 = var0.size();
      byte var2 = 0;
      int var3 = 0;
      if (var1 == 0) {
         return 0;
      } else {
         int var4;
         int var6;
         if (var0 instanceof zzkl) {
            zzkl var5 = (zzkl)var0;
            var4 = 0;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzae(var5.getInt(var3));
               ++var3;
            }
         } else {
            var4 = 0;
            var3 = var2;

            while(true) {
               var6 = var4;
               if (var3 >= var1) {
                  break;
               }

               var4 += zzjr.zzae((Integer)var0.get(var3));
               ++var3;
            }
         }

         return var6;
      }
   }

   public static void zzg(int var0, List<Long> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzl(var0, var1, var3);
      }

   }

   public static void zzg(Class<?> var0) {
      if (!zzkk.class.isAssignableFrom(var0)) {
         Class var1 = zzuz;
         if (var1 != null && !var1.isAssignableFrom(var0)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
         }
      }

   }

   static int zzh(List<?> var0) {
      return var0.size() << 2;
   }

   public static void zzh(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zza(var0, var1, var3);
      }

   }

   static int zzi(List<?> var0) {
      return var0.size() << 3;
   }

   public static void zzi(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzj(var0, var1, var3);
      }

   }

   static int zzj(List<?> var0) {
      return var0.size();
   }

   public static void zzj(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzm(var0, var1, var3);
      }

   }

   public static void zzk(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzb(var0, var1, var3);
      }

   }

   public static void zzl(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzk(var0, var1, var3);
      }

   }

   public static void zzm(int var0, List<Integer> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzh(var0, var1, var3);
      }

   }

   public static void zzn(int var0, List<Boolean> var1, zzns var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.zzi(var0, var1, var3);
      }

   }

   static int zzo(int var0, List<Long> var1, boolean var2) {
      return var1.size() == 0 ? 0 : zza(var1) + var1.size() * zzjr.zzab(var0);
   }

   static int zzp(int var0, List<Long> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zzb(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzq(int var0, List<Long> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zzc(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzr(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zzd(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzs(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zze(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzt(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zzf(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzu(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : zzg(var1) + var3 * zzjr.zzab(var0);
   }

   static int zzv(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : var3 * zzjr.zzj(var0, 0);
   }

   static int zzw(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : var3 * zzjr.zzg(var0, 0L);
   }

   static int zzx(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      return var3 == 0 ? 0 : var3 * zzjr.zzc(var0, true);
   }
}
