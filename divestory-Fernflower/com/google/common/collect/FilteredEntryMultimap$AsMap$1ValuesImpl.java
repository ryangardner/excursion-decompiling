package com.google.common.collect;

import com.google.common.base.Predicates;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class FilteredEntryMultimap$AsMap$1ValuesImpl extends Maps.Values<K, Collection<V>> {
   // $FF: synthetic field
   final FilteredEntryMultimap.AsMap this$1;

   FilteredEntryMultimap$AsMap$1ValuesImpl(FilteredEntryMultimap.AsMap var1) {
      super(var1);
      this.this$1 = var1;
   }

   public boolean remove(@NullableDecl Object var1) {
      if (var1 instanceof Collection) {
         Collection var2 = (Collection)var1;
         Iterator var5 = this.this$1.this$0.unfiltered.asMap().entrySet().iterator();

         while(var5.hasNext()) {
            Entry var3 = (Entry)var5.next();
            Object var4 = var3.getKey();
            Collection var6 = FilteredEntryMultimap.filterCollection((Collection)var3.getValue(), this.this$1.this$0.new ValuePredicate(var4));
            if (!var6.isEmpty() && var2.equals(var6)) {
               if (var6.size() == ((Collection)var3.getValue()).size()) {
                  var5.remove();
               } else {
                  var6.clear();
               }

               return true;
            }
         }
      }

      return false;
   }

   public boolean removeAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in(var1)));
   }

   public boolean retainAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(var1))));
   }
}
