package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzmk implements Iterator<Entry<K, V>> {
   private int pos;
   private Iterator<Entry<K, V>> zzvj;
   // $FF: synthetic field
   private final zzmi zzvk;

   private zzmk(zzmi var1) {
      this.zzvk = var1;
      this.pos = zzmi.zzb(this.zzvk).size();
   }

   // $FF: synthetic method
   zzmk(zzmi var1, zzmj var2) {
      this(var1);
   }

   private final Iterator<Entry<K, V>> zzew() {
      if (this.zzvj == null) {
         this.zzvj = zzmi.zzd(this.zzvk).entrySet().iterator();
      }

      return this.zzvj;
   }

   public final boolean hasNext() {
      int var1 = this.pos;
      return var1 > 0 && var1 <= zzmi.zzb(this.zzvk).size() || this.zzew().hasNext();
   }

   // $FF: synthetic method
   public final Object next() {
      if (this.zzew().hasNext()) {
         return (Entry)this.zzew().next();
      } else {
         List var1 = zzmi.zzb(this.zzvk);
         int var2 = this.pos - 1;
         this.pos = var2;
         return (Entry)var1.get(var2);
      }
   }

   public final void remove() {
      throw new UnsupportedOperationException();
   }
}
