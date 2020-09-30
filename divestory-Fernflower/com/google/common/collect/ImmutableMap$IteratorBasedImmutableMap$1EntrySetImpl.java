package com.google.common.collect;

import java.util.Map.Entry;

class ImmutableMap$IteratorBasedImmutableMap$1EntrySetImpl extends ImmutableMapEntrySet<K, V> {
   // $FF: synthetic field
   final ImmutableMap.IteratorBasedImmutableMap this$0;

   ImmutableMap$IteratorBasedImmutableMap$1EntrySetImpl(ImmutableMap.IteratorBasedImmutableMap var1) {
      this.this$0 = var1;
   }

   public UnmodifiableIterator<Entry<K, V>> iterator() {
      return this.this$0.entryIterator();
   }

   ImmutableMap<K, V> map() {
      return this.this$0;
   }
}
