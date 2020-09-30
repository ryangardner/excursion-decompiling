package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzml extends zzmr {
   // $FF: synthetic field
   private final zzmi zzvk;

   private zzml(zzmi var1) {
      super(var1, (zzmj)null);
      this.zzvk = var1;
   }

   // $FF: synthetic method
   zzml(zzmi var1, zzmj var2) {
      this(var1);
   }

   public final Iterator<Entry<K, V>> iterator() {
      return new zzmk(this.zzvk, (zzmj)null);
   }
}
