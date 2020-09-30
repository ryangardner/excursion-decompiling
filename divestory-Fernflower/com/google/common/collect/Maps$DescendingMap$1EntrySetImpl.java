package com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class Maps$DescendingMap$1EntrySetImpl extends Maps.EntrySet<K, V> {
   // $FF: synthetic field
   final Maps.DescendingMap this$0;

   Maps$DescendingMap$1EntrySetImpl(Maps.DescendingMap var1) {
      this.this$0 = var1;
   }

   public Iterator<Entry<K, V>> iterator() {
      return this.this$0.entryIterator();
   }

   Map<K, V> map() {
      return this.this$0;
   }
}
