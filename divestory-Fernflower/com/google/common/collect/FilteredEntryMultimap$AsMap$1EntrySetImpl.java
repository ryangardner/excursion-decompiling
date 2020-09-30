package com.google.common.collect;

import com.google.common.base.Predicates;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class FilteredEntryMultimap$AsMap$1EntrySetImpl extends Maps.EntrySet<K, Collection<V>> {
   // $FF: synthetic field
   final FilteredEntryMultimap.AsMap this$1;

   FilteredEntryMultimap$AsMap$1EntrySetImpl(FilteredEntryMultimap.AsMap var1) {
      this.this$1 = var1;
   }

   public Iterator<Entry<K, Collection<V>>> iterator() {
      return new AbstractIterator<Entry<K, Collection<V>>>() {
         final Iterator<Entry<K, Collection<V>>> backingIterator;

         {
            this.backingIterator = FilteredEntryMultimap$AsMap$1EntrySetImpl.this.this$1.this$0.unfiltered.asMap().entrySet().iterator();
         }

         protected Entry<K, Collection<V>> computeNext() {
            while(true) {
               if (this.backingIterator.hasNext()) {
                  Entry var1 = (Entry)this.backingIterator.next();
                  Object var2 = var1.getKey();
                  Collection var3 = FilteredEntryMultimap.filterCollection((Collection)var1.getValue(), FilteredEntryMultimap$AsMap$1EntrySetImpl.this.this$1.this$0.new ValuePredicate(var2));
                  if (var3.isEmpty()) {
                     continue;
                  }

                  return Maps.immutableEntry(var2, var3);
               }

               return (Entry)this.endOfData();
            }
         }
      };
   }

   Map<K, Collection<V>> map() {
      return this.this$1;
   }

   public boolean removeAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Predicates.in(var1));
   }

   public boolean retainAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Predicates.not(Predicates.in(var1)));
   }

   public int size() {
      return Iterators.size(this.iterator());
   }
}
