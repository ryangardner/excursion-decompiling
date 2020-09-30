package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
   private static final Object PRED = new Object();
   private final Map<N, Object> adjacentNodeValues;
   @NullableDecl
   private final List<DirectedGraphConnections.NodeConnection<N>> orderedNodeConnections;
   private int predecessorCount;
   private int successorCount;

   private DirectedGraphConnections(Map<N, Object> var1, @NullableDecl List<DirectedGraphConnections.NodeConnection<N>> var2, int var3, int var4) {
      this.adjacentNodeValues = (Map)Preconditions.checkNotNull(var1);
      this.orderedNodeConnections = var2;
      this.predecessorCount = Graphs.checkNonNegative(var3);
      this.successorCount = Graphs.checkNonNegative(var4);
      boolean var5;
      if (var3 <= var1.size() && var4 <= var1.size()) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5);
   }

   private static boolean isPredecessor(@NullableDecl Object var0) {
      boolean var1;
      if (var0 != PRED && !(var0 instanceof DirectedGraphConnections.PredAndSucc)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static boolean isSuccessor(@NullableDecl Object var0) {
      boolean var1;
      if (var0 != PRED && var0 != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static <N, V> DirectedGraphConnections<N, V> of(ElementOrder<N> var0) {
      int var1 = null.$SwitchMap$com$google$common$graph$ElementOrder$Type[var0.type().ordinal()];
      ArrayList var2;
      if (var1 != 1) {
         if (var1 != 2) {
            throw new AssertionError(var0.type());
         }

         var2 = new ArrayList();
      } else {
         var2 = null;
      }

      return new DirectedGraphConnections(new HashMap(4, 1.0F), var2, 0, 0);
   }

   static <N, V> DirectedGraphConnections<N, V> ofImmutable(N var0, Iterable<EndpointPair<N>> var1, Function<N, V> var2) {
      HashMap var3 = new HashMap();
      ImmutableList.Builder var4 = ImmutableList.builder();
      Iterator var11 = var1.iterator();
      int var5 = 0;
      int var6 = 0;

      while(true) {
         while(var11.hasNext()) {
            EndpointPair var7 = (EndpointPair)var11.next();
            if (var7.nodeU().equals(var0) && var7.nodeV().equals(var0)) {
               var3.put(var0, new DirectedGraphConnections.PredAndSucc(var2.apply(var0)));
               var4.add((Object)(new DirectedGraphConnections.NodeConnection.Pred(var0)));
               var4.add((Object)(new DirectedGraphConnections.NodeConnection.Succ(var0)));
               ++var5;
            } else {
               Object var8;
               Object var12;
               if (var7.nodeV().equals(var0)) {
                  var8 = var7.nodeU();
                  var12 = var3.put(var8, PRED);
                  if (var12 != null) {
                     var3.put(var8, new DirectedGraphConnections.PredAndSucc(var12));
                  }

                  var4.add((Object)(new DirectedGraphConnections.NodeConnection.Pred(var8)));
                  ++var5;
                  continue;
               }

               Preconditions.checkArgument(var7.nodeU().equals(var0));
               Object var9 = var7.nodeV();
               var12 = var2.apply(var9);
               var8 = var3.put(var9, var12);
               if (var8 != null) {
                  boolean var10;
                  if (var8 == PRED) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  Preconditions.checkArgument(var10);
                  var3.put(var9, new DirectedGraphConnections.PredAndSucc(var12));
               }

               var4.add((Object)(new DirectedGraphConnections.NodeConnection.Succ(var9)));
            }

            ++var6;
         }

         return new DirectedGraphConnections(var3, var4.build(), var5, var6);
      }
   }

   public void addPredecessor(N var1, V var2) {
      boolean var3;
      label23: {
         var2 = this.adjacentNodeValues.put(var1, PRED);
         var3 = false;
         if (var2 != null) {
            if (var2 instanceof DirectedGraphConnections.PredAndSucc) {
               this.adjacentNodeValues.put(var1, var2);
               break label23;
            }

            if (var2 == PRED) {
               break label23;
            }

            this.adjacentNodeValues.put(var1, new DirectedGraphConnections.PredAndSucc(var2));
         }

         var3 = true;
      }

      if (var3) {
         int var5 = this.predecessorCount + 1;
         this.predecessorCount = var5;
         Graphs.checkPositive(var5);
         List var4 = this.orderedNodeConnections;
         if (var4 != null) {
            var4.add(new DirectedGraphConnections.NodeConnection.Pred(var1));
         }
      }

   }

   public V addSuccessor(N var1, V var2) {
      Object var4;
      label23: {
         Object var3 = this.adjacentNodeValues.put(var1, var2);
         if (var3 != null) {
            if (var3 instanceof DirectedGraphConnections.PredAndSucc) {
               this.adjacentNodeValues.put(var1, new DirectedGraphConnections.PredAndSucc(var2));
               var4 = ((DirectedGraphConnections.PredAndSucc)var3).successorValue;
               break label23;
            }

            var4 = var3;
            if (var3 != PRED) {
               break label23;
            }

            this.adjacentNodeValues.put(var1, new DirectedGraphConnections.PredAndSucc(var2));
         }

         var4 = null;
      }

      if (var4 == null) {
         int var5 = this.successorCount + 1;
         this.successorCount = var5;
         Graphs.checkPositive(var5);
         List var6 = this.orderedNodeConnections;
         if (var6 != null) {
            var6.add(new DirectedGraphConnections.NodeConnection.Succ(var1));
         }
      }

      return var4;
   }

   public Set<N> adjacentNodes() {
      return (Set)(this.orderedNodeConnections == null ? Collections.unmodifiableSet(this.adjacentNodeValues.keySet()) : new AbstractSet<N>() {
         public boolean contains(@NullableDecl Object var1) {
            return DirectedGraphConnections.this.adjacentNodeValues.containsKey(var1);
         }

         public UnmodifiableIterator<N> iterator() {
            return new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator(), new HashSet()) {
               // $FF: synthetic field
               final Iterator val$nodeConnections;
               // $FF: synthetic field
               final Set val$seenNodes;

               {
                  this.val$nodeConnections = var2;
                  this.val$seenNodes = var3;
               }

               protected N computeNext() {
                  while(true) {
                     if (this.val$nodeConnections.hasNext()) {
                        DirectedGraphConnections.NodeConnection var1 = (DirectedGraphConnections.NodeConnection)this.val$nodeConnections.next();
                        if (!this.val$seenNodes.add(var1.node)) {
                           continue;
                        }

                        return var1.node;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            return DirectedGraphConnections.this.adjacentNodeValues.size();
         }
      });
   }

   public Iterator<EndpointPair<N>> incidentEdgeIterator(final N var1) {
      List var2 = this.orderedNodeConnections;
      final Iterator var3;
      if (var2 == null) {
         var3 = Iterators.concat(Iterators.transform(this.predecessors().iterator(), new Function<N, EndpointPair<N>>() {
            public EndpointPair<N> apply(N var1x) {
               return EndpointPair.ordered(var1x, var1);
            }
         }), Iterators.transform(this.successors().iterator(), new Function<N, EndpointPair<N>>() {
            public EndpointPair<N> apply(N var1x) {
               return EndpointPair.ordered(var1, var1x);
            }
         }));
      } else {
         var3 = Iterators.transform(var2.iterator(), new Function<DirectedGraphConnections.NodeConnection<N>, EndpointPair<N>>() {
            public EndpointPair<N> apply(DirectedGraphConnections.NodeConnection<N> var1x) {
               return var1x instanceof DirectedGraphConnections.NodeConnection.Succ ? EndpointPair.ordered(var1, var1x.node) : EndpointPair.ordered(var1x.node, var1);
            }
         });
      }

      return new AbstractIterator<EndpointPair<N>>(new AtomicBoolean(false)) {
         // $FF: synthetic field
         final AtomicBoolean val$alreadySeenSelfLoop;

         {
            this.val$alreadySeenSelfLoop = var3x;
         }

         protected EndpointPair<N> computeNext() {
            while(true) {
               if (var3.hasNext()) {
                  EndpointPair var1 = (EndpointPair)var3.next();
                  if (var1.nodeU().equals(var1.nodeV()) && this.val$alreadySeenSelfLoop.getAndSet(true)) {
                     continue;
                  }

                  return var1;
               }

               return (EndpointPair)this.endOfData();
            }
         }
      };
   }

   public Set<N> predecessors() {
      return new AbstractSet<N>() {
         public boolean contains(@NullableDecl Object var1) {
            return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(var1));
         }

         public UnmodifiableIterator<N> iterator() {
            return DirectedGraphConnections.this.orderedNodeConnections == null ? new AbstractIterator<N>(DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$entries;

               {
                  this.val$entries = var2;
               }

               protected N computeNext() {
                  while(true) {
                     if (this.val$entries.hasNext()) {
                        Entry var1 = (Entry)this.val$entries.next();
                        if (!DirectedGraphConnections.isPredecessor(var1.getValue())) {
                           continue;
                        }

                        return var1.getKey();
                     }

                     return this.endOfData();
                  }
               }
            } : new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator()) {
               // $FF: synthetic field
               final Iterator val$nodeConnections;

               {
                  this.val$nodeConnections = var2;
               }

               protected N computeNext() {
                  while(true) {
                     if (this.val$nodeConnections.hasNext()) {
                        DirectedGraphConnections.NodeConnection var1 = (DirectedGraphConnections.NodeConnection)this.val$nodeConnections.next();
                        if (!(var1 instanceof DirectedGraphConnections.NodeConnection.Pred)) {
                           continue;
                        }

                        return var1.node;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            return DirectedGraphConnections.this.predecessorCount;
         }
      };
   }

   public void removePredecessor(N var1) {
      boolean var3;
      label21: {
         Object var2 = this.adjacentNodeValues.get(var1);
         if (var2 == PRED) {
            this.adjacentNodeValues.remove(var1);
         } else {
            if (!(var2 instanceof DirectedGraphConnections.PredAndSucc)) {
               var3 = false;
               break label21;
            }

            this.adjacentNodeValues.put(var1, ((DirectedGraphConnections.PredAndSucc)var2).successorValue);
         }

         var3 = true;
      }

      if (var3) {
         int var5 = this.predecessorCount - 1;
         this.predecessorCount = var5;
         Graphs.checkNonNegative(var5);
         List var4 = this.orderedNodeConnections;
         if (var4 != null) {
            var4.remove(new DirectedGraphConnections.NodeConnection.Pred(var1));
         }
      }

   }

   public V removeSuccessor(Object var1) {
      Object var2;
      label22: {
         var2 = this.adjacentNodeValues.get(var1);
         if (var2 != null) {
            Object var3 = PRED;
            if (var2 != var3) {
               if (var2 instanceof DirectedGraphConnections.PredAndSucc) {
                  this.adjacentNodeValues.put(var1, var3);
                  var2 = ((DirectedGraphConnections.PredAndSucc)var2).successorValue;
               } else {
                  this.adjacentNodeValues.remove(var1);
               }
               break label22;
            }
         }

         var2 = null;
      }

      if (var2 != null) {
         int var4 = this.successorCount - 1;
         this.successorCount = var4;
         Graphs.checkNonNegative(var4);
         List var5 = this.orderedNodeConnections;
         if (var5 != null) {
            var5.remove(new DirectedGraphConnections.NodeConnection.Succ(var1));
         }
      }

      return var2;
   }

   public Set<N> successors() {
      return new AbstractSet<N>() {
         public boolean contains(@NullableDecl Object var1) {
            return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(var1));
         }

         public UnmodifiableIterator<N> iterator() {
            return DirectedGraphConnections.this.orderedNodeConnections == null ? new AbstractIterator<N>(DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$entries;

               {
                  this.val$entries = var2;
               }

               protected N computeNext() {
                  while(true) {
                     if (this.val$entries.hasNext()) {
                        Entry var1 = (Entry)this.val$entries.next();
                        if (!DirectedGraphConnections.isSuccessor(var1.getValue())) {
                           continue;
                        }

                        return var1.getKey();
                     }

                     return this.endOfData();
                  }
               }
            } : new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator()) {
               // $FF: synthetic field
               final Iterator val$nodeConnections;

               {
                  this.val$nodeConnections = var2;
               }

               protected N computeNext() {
                  while(true) {
                     if (this.val$nodeConnections.hasNext()) {
                        DirectedGraphConnections.NodeConnection var1 = (DirectedGraphConnections.NodeConnection)this.val$nodeConnections.next();
                        if (!(var1 instanceof DirectedGraphConnections.NodeConnection.Succ)) {
                           continue;
                        }

                        return var1.node;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            return DirectedGraphConnections.this.successorCount;
         }
      };
   }

   public V value(N var1) {
      Object var2 = this.adjacentNodeValues.get(var1);
      if (var2 == PRED) {
         return null;
      } else {
         var1 = var2;
         if (var2 instanceof DirectedGraphConnections.PredAndSucc) {
            var1 = ((DirectedGraphConnections.PredAndSucc)var2).successorValue;
         }

         return var1;
      }
   }

   private abstract static class NodeConnection<N> {
      final N node;

      NodeConnection(N var1) {
         this.node = Preconditions.checkNotNull(var1);
      }

      static final class Pred<N> extends DirectedGraphConnections.NodeConnection<N> {
         Pred(N var1) {
            super(var1);
         }

         public boolean equals(Object var1) {
            return var1 instanceof DirectedGraphConnections.NodeConnection.Pred ? this.node.equals(((DirectedGraphConnections.NodeConnection.Pred)var1).node) : false;
         }

         public int hashCode() {
            return DirectedGraphConnections.NodeConnection.Pred.class.hashCode() + this.node.hashCode();
         }
      }

      static final class Succ<N> extends DirectedGraphConnections.NodeConnection<N> {
         Succ(N var1) {
            super(var1);
         }

         public boolean equals(Object var1) {
            return var1 instanceof DirectedGraphConnections.NodeConnection.Succ ? this.node.equals(((DirectedGraphConnections.NodeConnection.Succ)var1).node) : false;
         }

         public int hashCode() {
            return DirectedGraphConnections.NodeConnection.Succ.class.hashCode() + this.node.hashCode();
         }
      }
   }

   private static final class PredAndSucc {
      private final Object successorValue;

      PredAndSucc(Object var1) {
         this.successorValue = var1;
      }
   }
}
