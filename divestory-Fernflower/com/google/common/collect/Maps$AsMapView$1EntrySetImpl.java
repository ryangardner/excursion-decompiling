package com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class Maps$AsMapView$1EntrySetImpl extends Maps.EntrySet<K, V> {
   // $FF: synthetic field
   final Maps.AsMapView this$0;

   Maps$AsMapView$1EntrySetImpl(Maps.AsMapView var1) {
      this.this$0 = var1;
   }

   public Iterator<Entry<K, V>> iterator() {
      return Maps.asMapEntryIterator(this.this$0.backingSet(), this.this$0.function);
   }

   Map<K, V> map() {
      return this.this$0;
   }
}
