package com.google.android.gms.internal.drive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzky extends zziw<String> implements zzkz, RandomAccess {
   private static final zzky zztk;
   private static final zzkz zztl;
   private final List<Object> zziu;

   static {
      zzky var0 = new zzky();
      zztk = var0;
      var0.zzbp();
      zztl = zztk;
   }

   public zzky() {
      this(10);
   }

   public zzky(int var1) {
      this(new ArrayList(var1));
   }

   private zzky(ArrayList<Object> var1) {
      this.zziu = var1;
   }

   private static String zzf(Object var0) {
      if (var0 instanceof String) {
         return (String)var0;
      } else {
         return var0 instanceof zzjc ? ((zzjc)var0).zzbt() : zzkm.zze((byte[])var0);
      }
   }

   // $FF: synthetic method
   public final void add(int var1, Object var2) {
      String var3 = (String)var2;
      this.zzbq();
      this.zziu.add(var1, var3);
      ++this.modCount;
   }

   public final boolean addAll(int var1, Collection<? extends String> var2) {
      this.zzbq();
      Object var3 = var2;
      if (var2 instanceof zzkz) {
         var3 = ((zzkz)var2).zzdr();
      }

      boolean var4 = this.zziu.addAll(var1, (Collection)var3);
      ++this.modCount;
      return var4;
   }

   public final boolean addAll(Collection<? extends String> var1) {
      return this.addAll(this.size(), var1);
   }

   public final void clear() {
      this.zzbq();
      this.zziu.clear();
      ++this.modCount;
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      Object var2 = this.zziu.get(var1);
      if (var2 instanceof String) {
         return (String)var2;
      } else if (var2 instanceof zzjc) {
         zzjc var6 = (zzjc)var2;
         String var5 = var6.zzbt();
         if (var6.zzbu()) {
            this.zziu.set(var1, var5);
         }

         return var5;
      } else {
         byte[] var4 = (byte[])var2;
         String var3 = zzkm.zze(var4);
         if (zzkm.zzd(var4)) {
            this.zziu.set(var1, var3);
         }

         return var3;
      }
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      Object var2 = this.zziu.remove(var1);
      ++this.modCount;
      return zzf(var2);
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      String var3 = (String)var2;
      this.zzbq();
      return zzf(this.zziu.set(var1, var3));
   }

   public final int size() {
      return this.zziu.size();
   }

   public final Object zzao(int var1) {
      return this.zziu.get(var1);
   }

   public final List<?> zzdr() {
      return Collections.unmodifiableList(this.zziu);
   }

   public final zzkz zzds() {
      return (zzkz)(this.zzbo() ? new zzna(this) : this);
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size()) {
         ArrayList var2 = new ArrayList(var1);
         var2.addAll(this.zziu);
         return new zzky(var2);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
