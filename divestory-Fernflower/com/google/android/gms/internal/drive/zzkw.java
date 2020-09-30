package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzkw<K> implements Iterator<Entry<K, Object>> {
   private Iterator<Entry<K, Object>> zztg;

   public zzkw(Iterator<Entry<K, Object>> var1) {
      this.zztg = var1;
   }

   public final boolean hasNext() {
      return this.zztg.hasNext();
   }

   // $FF: synthetic method
   public final Object next() {
      Entry var1 = (Entry)this.zztg.next();
      return var1.getValue() instanceof zzkt ? new zzkv(var1, (zzku)null) : var1;
   }

   public final void remove() {
      this.zztg.remove();
   }
}
