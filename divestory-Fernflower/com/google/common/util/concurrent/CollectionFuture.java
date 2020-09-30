package com.google.common.util.concurrent;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {
   private List<Optional<V>> values;

   CollectionFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> var1, boolean var2) {
      super(var1, var2, true);
      Object var3;
      if (var1.isEmpty()) {
         var3 = ImmutableList.of();
      } else {
         var3 = Lists.newArrayListWithCapacity(var1.size());
      }

      this.values = (List)var3;

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         this.values.add((Object)null);
      }

   }

   final void collectOneValue(int var1, @NullableDecl V var2) {
      List var3 = this.values;
      if (var3 != null) {
         var3.set(var1, Optional.fromNullable(var2));
      }

   }

   abstract C combine(List<Optional<V>> var1);

   final void handleAllCompleted() {
      List var1 = this.values;
      if (var1 != null) {
         this.set(this.combine(var1));
      }

   }

   void releaseResources(AggregateFuture.ReleaseResourcesReason var1) {
      super.releaseResources(var1);
      this.values = null;
   }

   static final class ListFuture<V> extends CollectionFuture<V, List<V>> {
      ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> var1, boolean var2) {
         super(var1, var2);
         this.init();
      }

      public List<V> combine(List<Optional<V>> var1) {
         ArrayList var2 = Lists.newArrayListWithCapacity(var1.size());

         Object var5;
         for(Iterator var3 = var1.iterator(); var3.hasNext(); var2.add(var5)) {
            Optional var4 = (Optional)var3.next();
            if (var4 != null) {
               var5 = var4.orNull();
            } else {
               var5 = null;
            }
         }

         return Collections.unmodifiableList(var2);
      }
   }
}
