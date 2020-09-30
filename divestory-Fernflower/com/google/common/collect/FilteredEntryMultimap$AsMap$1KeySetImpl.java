package com.google.common.collect;

import com.google.common.base.Predicates;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class FilteredEntryMultimap$AsMap$1KeySetImpl extends Maps.KeySet<K, Collection<V>> {
   // $FF: synthetic field
   final FilteredEntryMultimap.AsMap this$1;

   FilteredEntryMultimap$AsMap$1KeySetImpl(FilteredEntryMultimap.AsMap var1) {
      super(var1);
      this.this$1 = var1;
   }

   public boolean remove(@NullableDecl Object var1) {
      boolean var2;
      if (this.this$1.remove(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean removeAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in(var1)));
   }

   public boolean retainAll(Collection<?> var1) {
      return this.this$1.this$0.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(var1))));
   }
}
