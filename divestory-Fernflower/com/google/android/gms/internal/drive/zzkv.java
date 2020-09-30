package com.google.android.gms.internal.drive;

import java.util.Map.Entry;

final class zzkv<K> implements Entry<K, Object> {
   private Entry<K, zzkt> zztf;

   private zzkv(Entry<K, zzkt> var1) {
      this.zztf = var1;
   }

   // $FF: synthetic method
   zzkv(Entry var1, zzku var2) {
      this(var1);
   }

   public final K getKey() {
      return this.zztf.getKey();
   }

   public final Object getValue() {
      return (zzkt)this.zztf.getValue() == null ? null : zzkt.zzdp();
   }

   public final Object setValue(Object var1) {
      if (var1 instanceof zzlq) {
         return ((zzkt)this.zztf.getValue()).zzi((zzlq)var1);
      } else {
         throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
      }
   }

   public final zzkt zzdq() {
      return (zzkt)this.zztf.getValue();
   }
}
