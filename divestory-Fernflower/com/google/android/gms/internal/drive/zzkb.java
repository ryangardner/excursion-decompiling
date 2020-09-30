package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzkb<FieldDescriptorType extends zzkd<FieldDescriptorType>> {
   private static final zzkb zzov = new zzkb(true);
   final zzmi<FieldDescriptorType, Object> zzos;
   private boolean zzot;
   private boolean zzou = false;

   private zzkb() {
      this.zzos = zzmi.zzav(16);
   }

   private zzkb(boolean var1) {
      this.zzos = zzmi.zzav(0);
      this.zzbp();
   }

   static int zza(zznm var0, int var1, Object var2) {
      int var3 = zzjr.zzab(var1);
      var1 = var3;
      if (var0 == zznm.zzxd) {
         zzkm.zzf((zzlq)var2);
         var1 = var3 << 1;
      }

      return var1 + zzb(var0, var2);
   }

   private final Object zza(FieldDescriptorType var1) {
      Object var2 = this.zzos.get(var1);
      Object var3 = var2;
      if (var2 instanceof zzkt) {
         var3 = zzkt.zzdp();
      }

      return var3;
   }

   static void zza(zzjr var0, zznm var1, int var2, Object var3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private final void zza(FieldDescriptorType var1, Object var2) {
      if (var1.zzcs()) {
         if (!(var2 instanceof List)) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
         }

         ArrayList var3 = new ArrayList();
         var3.addAll((List)var2);
         ArrayList var7 = (ArrayList)var3;
         int var4 = var7.size();
         int var5 = 0;

         while(var5 < var4) {
            Object var6 = var7.get(var5);
            ++var5;
            zza(var1.zzcq(), var6);
         }

         var2 = var3;
      } else {
         zza(var1.zzcq(), var2);
      }

      if (var2 instanceof zzkt) {
         this.zzou = true;
      }

      this.zzos.zza(var1, var2);
   }

   private static void zza(zznm var0, Object var1) {
      boolean var4;
      label40: {
         zzkm.checkNotNull(var1);
         int var2 = zzkc.zzow[var0.zzfj().ordinal()];
         boolean var3 = true;
         var4 = false;
         switch(var2) {
         case 1:
            var4 = var1 instanceof Integer;
            break label40;
         case 2:
            var4 = var1 instanceof Long;
            break label40;
         case 3:
            var4 = var1 instanceof Float;
            break label40;
         case 4:
            var4 = var1 instanceof Double;
            break label40;
         case 5:
            var4 = var1 instanceof Boolean;
            break label40;
         case 6:
            var4 = var1 instanceof String;
            break label40;
         case 7:
            var4 = var3;
            if (var1 instanceof zzjc) {
               break label40;
            }

            if (var1 instanceof byte[]) {
               var4 = var3;
               break label40;
            }
            break;
         case 8:
            var4 = var3;
            if (var1 instanceof Integer) {
               break label40;
            }

            if (var1 instanceof zzkn) {
               var4 = var3;
               break label40;
            }
            break;
         case 9:
            var4 = var3;
            if (!(var1 instanceof zzlq)) {
               if (var1 instanceof zzkt) {
                  var4 = var3;
                  break label40;
               }
               break;
            }
         default:
            break label40;
         }

         var4 = false;
      }

      if (!var4) {
         throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      }
   }

   public static int zzb(zzkd<?> var0, Object var1) {
      zznm var2 = var0.zzcq();
      int var3 = var0.zzcp();
      if (!var0.zzcs()) {
         return zza(var2, var3, var1);
      } else {
         boolean var4 = var0.zzct();
         byte var5 = 0;
         int var6 = 0;
         Iterator var7;
         if (var4) {
            for(var7 = ((List)var1).iterator(); var7.hasNext(); var6 += zzb(var2, var7.next())) {
            }

            return zzjr.zzab(var3) + var6 + zzjr.zzaj(var6);
         } else {
            var7 = ((List)var1).iterator();

            for(var6 = var5; var7.hasNext(); var6 += zza(var2, var3, var7.next())) {
            }

            return var6;
         }
      }
   }

   private static int zzb(zznm var0, Object var1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean zzb(Entry<FieldDescriptorType, Object> var0) {
      zzkd var1 = (zzkd)var0.getKey();
      if (var1.zzcr() == zznr.zzxx) {
         if (var1.zzcs()) {
            Iterator var2 = ((List)var0.getValue()).iterator();

            while(var2.hasNext()) {
               if (!((zzlq)var2.next()).isInitialized()) {
                  return false;
               }
            }
         } else {
            Object var3 = var0.getValue();
            if (!(var3 instanceof zzlq)) {
               if (var3 instanceof zzkt) {
                  return true;
               }

               throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }

            if (!((zzlq)var3).isInitialized()) {
               return false;
            }
         }
      }

      return true;
   }

   private final void zzc(Entry<FieldDescriptorType, Object> var1) {
      zzkd var2 = (zzkd)var1.getKey();
      Object var3 = var1.getValue();
      Object var5 = var3;
      if (var3 instanceof zzkt) {
         var5 = zzkt.zzdp();
      }

      if (!var2.zzcs()) {
         if (var2.zzcr() == zznr.zzxx) {
            var3 = this.zza(var2);
            if (var3 == null) {
               this.zzos.zza(var2, zze(var5));
            } else {
               if (var3 instanceof zzlx) {
                  var5 = var2.zza((zzlx)var3, (zzlx)var5);
               } else {
                  var5 = var2.zza(((zzlq)var3).zzcy(), (zzlq)var5).zzdf();
               }

               this.zzos.zza(var2, var5);
            }
         } else {
            this.zzos.zza(var2, zze(var5));
         }
      } else {
         Object var4 = this.zza(var2);
         var3 = var4;
         if (var4 == null) {
            var3 = new ArrayList();
         }

         Iterator var6 = ((List)var5).iterator();

         while(var6.hasNext()) {
            var4 = var6.next();
            ((List)var3).add(zze(var4));
         }

         this.zzos.zza(var2, var3);
      }
   }

   public static <T extends zzkd<T>> zzkb<T> zzcn() {
      return zzov;
   }

   private static int zzd(Entry<FieldDescriptorType, Object> var0) {
      zzkd var1 = (zzkd)var0.getKey();
      Object var2 = var0.getValue();
      if (var1.zzcr() == zznr.zzxx && !var1.zzcs() && !var1.zzct()) {
         return var2 instanceof zzkt ? zzjr.zzb(((zzkd)var0.getKey()).zzcp(), (zzkx)((zzkt)var2)) : zzjr.zzb(((zzkd)var0.getKey()).zzcp(), (zzlq)var2);
      } else {
         return zzb(var1, var2);
      }
   }

   private static Object zze(Object var0) {
      if (var0 instanceof zzlx) {
         return ((zzlx)var0).zzef();
      } else if (var0 instanceof byte[]) {
         byte[] var2 = (byte[])var0;
         byte[] var1 = new byte[var2.length];
         System.arraycopy(var2, 0, var1, 0, var2.length);
         return var1;
      } else {
         return var0;
      }
   }

   // $FF: synthetic method
   public final Object clone() throws CloneNotSupportedException {
      zzkb var1 = new zzkb();

      Entry var3;
      for(int var2 = 0; var2 < this.zzos.zzer(); ++var2) {
         var3 = this.zzos.zzaw(var2);
         var1.zza((zzkd)var3.getKey(), var3.getValue());
      }

      Iterator var4 = this.zzos.zzes().iterator();

      while(var4.hasNext()) {
         var3 = (Entry)var4.next();
         var1.zza((zzkd)var3.getKey(), var3.getValue());
      }

      var1.zzou = this.zzou;
      return var1;
   }

   final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
      return (Iterator)(this.zzou ? new zzkw(this.zzos.zzet().iterator()) : this.zzos.zzet().iterator());
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzkb)) {
         return false;
      } else {
         zzkb var2 = (zzkb)var1;
         return this.zzos.equals(var2.zzos);
      }
   }

   public final int hashCode() {
      return this.zzos.hashCode();
   }

   public final boolean isImmutable() {
      return this.zzot;
   }

   public final boolean isInitialized() {
      for(int var1 = 0; var1 < this.zzos.zzer(); ++var1) {
         if (!zzb(this.zzos.zzaw(var1))) {
            return false;
         }
      }

      Iterator var2 = this.zzos.zzes().iterator();

      do {
         if (!var2.hasNext()) {
            return true;
         }
      } while(zzb((Entry)var2.next()));

      return false;
   }

   public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
      return (Iterator)(this.zzou ? new zzkw(this.zzos.entrySet().iterator()) : this.zzos.entrySet().iterator());
   }

   public final void zza(zzkb<FieldDescriptorType> var1) {
      for(int var2 = 0; var2 < var1.zzos.zzer(); ++var2) {
         this.zzc(var1.zzos.zzaw(var2));
      }

      Iterator var3 = var1.zzos.zzes().iterator();

      while(var3.hasNext()) {
         this.zzc((Entry)var3.next());
      }

   }

   public final void zzbp() {
      if (!this.zzot) {
         this.zzos.zzbp();
         this.zzot = true;
      }
   }

   public final int zzco() {
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < this.zzos.zzer(); ++var1) {
         var2 += zzd(this.zzos.zzaw(var1));
      }

      for(Iterator var3 = this.zzos.zzes().iterator(); var3.hasNext(); var2 += zzd((Entry)var3.next())) {
      }

      return var2;
   }
}
