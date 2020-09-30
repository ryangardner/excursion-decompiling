package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzmq implements Iterator<Entry<K, V>> {
   private int pos;
   private Iterator<Entry<K, V>> zzvj;
   // $FF: synthetic field
   private final zzmi zzvk;
   private boolean zzvo;

   private zzmq(zzmi var1) {
      this.zzvk = var1;
      this.pos = -1;
   }

   // $FF: synthetic method
   zzmq(zzmi var1, zzmj var2) {
      this(var1);
   }

   private final Iterator<Entry<K, V>> zzew() {
      if (this.zzvj == null) {
         this.zzvj = zzmi.zzc(this.zzvk).entrySet().iterator();
      }

      return this.zzvj;
   }

   public final boolean hasNext() {
      return this.pos + 1 < zzmi.zzb(this.zzvk).size() || !zzmi.zzc(this.zzvk).isEmpty() && this.zzew().hasNext();
   }

   // $FF: synthetic method
   public final Object next() {
      this.zzvo = true;
      int var1 = this.pos + 1;
      this.pos = var1;
      return var1 < zzmi.zzb(this.zzvk).size() ? (Entry)zzmi.zzb(this.zzvk).get(this.pos) : (Entry)this.zzew().next();
   }

   public final void remove() {
      if (this.zzvo) {
         this.zzvo = false;
         zzmi.zza(this.zzvk);
         if (this.pos < zzmi.zzb(this.zzvk).size()) {
            zzmi var1 = this.zzvk;
            int var2 = this.pos--;
            zzmi.zza(var1, var2);
         } else {
            this.zzew().remove();
         }
      } else {
         throw new IllegalStateException("remove() was called before next()");
      }
   }
}
