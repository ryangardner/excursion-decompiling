package com.google.common.collect;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.Map.Entry;

class LinkedListMultimap$1EntriesImpl extends AbstractSequentialList<Entry<K, V>> {
   // $FF: synthetic field
   final LinkedListMultimap this$0;

   LinkedListMultimap$1EntriesImpl(LinkedListMultimap var1) {
      this.this$0 = var1;
   }

   public ListIterator<Entry<K, V>> listIterator(int var1) {
      return this.this$0.new NodeIterator(var1);
   }

   public int size() {
      return LinkedListMultimap.access$900(this.this$0);
   }
}
