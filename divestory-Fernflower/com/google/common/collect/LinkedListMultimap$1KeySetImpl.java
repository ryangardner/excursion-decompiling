package com.google.common.collect;

import java.util.Iterator;

class LinkedListMultimap$1KeySetImpl extends Sets.ImprovedAbstractSet<K> {
   // $FF: synthetic field
   final LinkedListMultimap this$0;

   LinkedListMultimap$1KeySetImpl(LinkedListMultimap var1) {
      this.this$0 = var1;
   }

   public boolean contains(Object var1) {
      return this.this$0.containsKey(var1);
   }

   public Iterator<K> iterator() {
      return this.this$0.new DistinctKeyIterator();
   }

   public boolean remove(Object var1) {
      return this.this$0.removeAll(var1).isEmpty() ^ true;
   }

   public int size() {
      return LinkedListMultimap.access$600(this.this$0).size();
   }
}
