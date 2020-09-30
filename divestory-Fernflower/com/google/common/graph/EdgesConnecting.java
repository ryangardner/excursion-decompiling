package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class EdgesConnecting<E> extends AbstractSet<E> {
   private final Map<?, E> nodeToOutEdge;
   private final Object targetNode;

   EdgesConnecting(Map<?, E> var1, Object var2) {
      this.nodeToOutEdge = (Map)Preconditions.checkNotNull(var1);
      this.targetNode = Preconditions.checkNotNull(var2);
   }

   @NullableDecl
   private E getConnectingEdge() {
      return this.nodeToOutEdge.get(this.targetNode);
   }

   public boolean contains(@NullableDecl Object var1) {
      Object var2 = this.getConnectingEdge();
      boolean var3;
      if (var2 != null && var2.equals(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public UnmodifiableIterator<E> iterator() {
      Object var1 = this.getConnectingEdge();
      UnmodifiableIterator var2;
      if (var1 == null) {
         var2 = ImmutableSet.of().iterator();
      } else {
         var2 = Iterators.singletonIterator(var1);
      }

      return var2;
   }

   public int size() {
      byte var1;
      if (this.getConnectingEdge() == null) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      return var1;
   }
}
