package com.google.common.collect;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.Map.Entry;

class LinkedListMultimap$1ValuesImpl extends AbstractSequentialList<V> {
   // $FF: synthetic field
   final LinkedListMultimap this$0;

   LinkedListMultimap$1ValuesImpl(LinkedListMultimap var1) {
      this.this$0 = var1;
   }

   public ListIterator<V> listIterator(int var1) {
      final LinkedListMultimap.NodeIterator var2 = this.this$0.new NodeIterator(var1);
      return new TransformedListIterator<Entry<K, V>, V>(var2) {
         public void set(V var1) {
            var2.setValue(var1);
         }

         V transform(Entry<K, V> var1) {
            return var1.getValue();
         }
      };
   }

   public int size() {
      return LinkedListMultimap.access$900(this.this$0);
   }
}
