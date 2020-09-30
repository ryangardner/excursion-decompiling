package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class MultiEdgesConnecting<E> extends AbstractSet<E> {
   private final Map<E, ?> outEdgeToNode;
   private final Object targetNode;

   MultiEdgesConnecting(Map<E, ?> var1, Object var2) {
      this.outEdgeToNode = (Map)Preconditions.checkNotNull(var1);
      this.targetNode = Preconditions.checkNotNull(var2);
   }

   public boolean contains(@NullableDecl Object var1) {
      return this.targetNode.equals(this.outEdgeToNode.get(var1));
   }

   public UnmodifiableIterator<E> iterator() {
      return new AbstractIterator<E>(this.outEdgeToNode.entrySet().iterator()) {
         // $FF: synthetic field
         final Iterator val$entries;

         {
            this.val$entries = var2;
         }

         protected E computeNext() {
            while(true) {
               if (this.val$entries.hasNext()) {
                  Entry var1 = (Entry)this.val$entries.next();
                  if (!MultiEdgesConnecting.this.targetNode.equals(var1.getValue())) {
                     continue;
                  }

                  return var1.getKey();
               }

               return this.endOfData();
            }
         }
      };
   }
}
