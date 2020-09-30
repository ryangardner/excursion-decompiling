package com.google.common.collect;

import java.util.Iterator;

class AbstractSortedMultiset$1DescendingMultisetImpl extends DescendingMultiset<E> {
   // $FF: synthetic field
   final AbstractSortedMultiset this$0;

   AbstractSortedMultiset$1DescendingMultisetImpl(AbstractSortedMultiset var1) {
      this.this$0 = var1;
   }

   Iterator<Multiset.Entry<E>> entryIterator() {
      return this.this$0.descendingEntryIterator();
   }

   SortedMultiset<E> forwardMultiset() {
      return this.this$0;
   }

   public Iterator<E> iterator() {
      return this.this$0.descendingIterator();
   }
}
