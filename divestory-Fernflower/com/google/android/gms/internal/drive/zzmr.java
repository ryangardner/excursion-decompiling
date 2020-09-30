package com.google.android.gms.internal.drive;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class zzmr extends AbstractSet<Entry<K, V>> {
   // $FF: synthetic field
   private final zzmi zzvk;

   private zzmr(zzmi var1) {
      this.zzvk = var1;
   }

   // $FF: synthetic method
   zzmr(zzmi var1, zzmj var2) {
      this(var1);
   }

   // $FF: synthetic method
   public boolean add(Object var1) {
      Entry var2 = (Entry)var1;
      if (!this.contains(var2)) {
         this.zzvk.zza((Comparable)var2.getKey(), var2.getValue());
         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      this.zzvk.clear();
   }

   public boolean contains(Object var1) {
      Entry var2 = (Entry)var1;
      var1 = this.zzvk.get(var2.getKey());
      Object var3 = var2.getValue();
      return var1 == var3 || var1 != null && var1.equals(var3);
   }

   public Iterator<Entry<K, V>> iterator() {
      return new zzmq(this.zzvk, (zzmj)null);
   }

   public boolean remove(Object var1) {
      Entry var2 = (Entry)var1;
      if (this.contains(var2)) {
         this.zzvk.remove(var2.getKey());
         return true;
      } else {
         return false;
      }
   }

   public int size() {
      return this.zzvk.size();
   }
}
