package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Traverser<N> {
   private Traverser() {
   }

   // $FF: synthetic method
   Traverser(Object var1) {
      this();
   }

   public static <N> Traverser<N> forGraph(SuccessorsFunction<N> var0) {
      Preconditions.checkNotNull(var0);
      return new Traverser.GraphTraverser(var0);
   }

   public static <N> Traverser<N> forTree(SuccessorsFunction<N> var0) {
      Preconditions.checkNotNull(var0);
      if (var0 instanceof BaseGraph) {
         Preconditions.checkArgument(((BaseGraph)var0).isDirected(), "Undirected graphs can never be trees.");
      }

      if (var0 instanceof Network) {
         Preconditions.checkArgument(((Network)var0).isDirected(), "Undirected networks can never be trees.");
      }

      return new Traverser.TreeTraverser(var0);
   }

   public abstract Iterable<N> breadthFirst(Iterable<? extends N> var1);

   public abstract Iterable<N> breadthFirst(N var1);

   public abstract Iterable<N> depthFirstPostOrder(Iterable<? extends N> var1);

   public abstract Iterable<N> depthFirstPostOrder(N var1);

   public abstract Iterable<N> depthFirstPreOrder(Iterable<? extends N> var1);

   public abstract Iterable<N> depthFirstPreOrder(N var1);

   private static final class GraphTraverser<N> extends Traverser<N> {
      private final SuccessorsFunction<N> graph;

      GraphTraverser(SuccessorsFunction<N> var1) {
         super(null);
         this.graph = (SuccessorsFunction)Preconditions.checkNotNull(var1);
      }

      private void checkThatNodeIsInGraph(N var1) {
         this.graph.successors(var1);
      }

      public Iterable<N> breadthFirst(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInGraph(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return GraphTraverser.this.new BreadthFirstIterator(var1);
               }
            };
         }
      }

      public Iterable<N> breadthFirst(N var1) {
         Preconditions.checkNotNull(var1);
         return this.breadthFirst((Iterable)ImmutableSet.of(var1));
      }

      public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInGraph(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return GraphTraverser.this.new DepthFirstIterator(var1, Traverser.Order.POSTORDER);
               }
            };
         }
      }

      public Iterable<N> depthFirstPostOrder(N var1) {
         Preconditions.checkNotNull(var1);
         return this.depthFirstPostOrder((Iterable)ImmutableSet.of(var1));
      }

      public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInGraph(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return GraphTraverser.this.new DepthFirstIterator(var1, Traverser.Order.PREORDER);
               }
            };
         }
      }

      public Iterable<N> depthFirstPreOrder(N var1) {
         Preconditions.checkNotNull(var1);
         return this.depthFirstPreOrder((Iterable)ImmutableSet.of(var1));
      }

      private final class BreadthFirstIterator extends UnmodifiableIterator<N> {
         private final Queue<N> queue = new ArrayDeque();
         private final Set<N> visited = new HashSet();

         BreadthFirstIterator(Iterable<? extends N> var2) {
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               Object var3 = var4.next();
               if (this.visited.add(var3)) {
                  this.queue.add(var3);
               }
            }

         }

         public boolean hasNext() {
            return this.queue.isEmpty() ^ true;
         }

         public N next() {
            Object var1 = this.queue.remove();
            Iterator var2 = GraphTraverser.this.graph.successors(var1).iterator();

            while(var2.hasNext()) {
               Object var3 = var2.next();
               if (this.visited.add(var3)) {
                  this.queue.add(var3);
               }
            }

            return var1;
         }
      }

      private final class DepthFirstIterator extends AbstractIterator<N> {
         private final Traverser.Order order;
         private final Deque<Traverser.GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors> stack = new ArrayDeque();
         private final Set<N> visited = new HashSet();

         DepthFirstIterator(Iterable<? extends N> var2, Traverser.Order var3) {
            this.stack.push(new Traverser.GraphTraverser.DepthFirstIterator.NodeAndSuccessors((Object)null, var2));
            this.order = var3;
         }

         protected N computeNext() {
            Traverser.GraphTraverser.DepthFirstIterator.NodeAndSuccessors var1;
            boolean var6;
            do {
               if (this.stack.isEmpty()) {
                  return this.endOfData();
               }

               boolean var5;
               label39: {
                  var1 = (Traverser.GraphTraverser.DepthFirstIterator.NodeAndSuccessors)this.stack.getFirst();
                  boolean var2 = this.visited.add(var1.node);
                  boolean var3 = var1.successorIterator.hasNext();
                  boolean var4 = true;
                  var5 = var3 ^ true;
                  if (var2) {
                     var6 = var4;
                     if (this.order == Traverser.Order.PREORDER) {
                        break label39;
                     }
                  }

                  if (var5 && this.order == Traverser.Order.POSTORDER) {
                     var6 = var4;
                  } else {
                     var6 = false;
                  }
               }

               if (var5) {
                  this.stack.pop();
               } else {
                  Object var7 = var1.successorIterator.next();
                  if (!this.visited.contains(var7)) {
                     this.stack.push(this.withSuccessors(var7));
                  }
               }
            } while(!var6 || var1.node == null);

            return var1.node;
         }

         Traverser.GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors withSuccessors(N var1) {
            return new Traverser.GraphTraverser.DepthFirstIterator.NodeAndSuccessors(var1, GraphTraverser.this.graph.successors(var1));
         }

         private final class NodeAndSuccessors {
            @NullableDecl
            final N node;
            final Iterator<? extends N> successorIterator;

            NodeAndSuccessors(@NullableDecl N var2, Iterable<? extends N> var3) {
               this.node = var2;
               this.successorIterator = var3.iterator();
            }
         }
      }
   }

   private static enum Order {
      POSTORDER,
      PREORDER;

      static {
         Traverser.Order var0 = new Traverser.Order("POSTORDER", 1);
         POSTORDER = var0;
      }
   }

   private static final class TreeTraverser<N> extends Traverser<N> {
      private final SuccessorsFunction<N> tree;

      TreeTraverser(SuccessorsFunction<N> var1) {
         super(null);
         this.tree = (SuccessorsFunction)Preconditions.checkNotNull(var1);
      }

      private void checkThatNodeIsInTree(N var1) {
         this.tree.successors(var1);
      }

      public Iterable<N> breadthFirst(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInTree(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return TreeTraverser.this.new BreadthFirstIterator(var1);
               }
            };
         }
      }

      public Iterable<N> breadthFirst(N var1) {
         Preconditions.checkNotNull(var1);
         return this.breadthFirst((Iterable)ImmutableSet.of(var1));
      }

      public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInTree(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return TreeTraverser.this.new DepthFirstPostOrderIterator(var1);
               }
            };
         }
      }

      public Iterable<N> depthFirstPostOrder(N var1) {
         Preconditions.checkNotNull(var1);
         return this.depthFirstPostOrder((Iterable)ImmutableSet.of(var1));
      }

      public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> var1) {
         Preconditions.checkNotNull(var1);
         if (Iterables.isEmpty(var1)) {
            return ImmutableSet.of();
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.checkThatNodeIsInTree(var2.next());
            }

            return new Iterable<N>() {
               public Iterator<N> iterator() {
                  return TreeTraverser.this.new DepthFirstPreOrderIterator(var1);
               }
            };
         }
      }

      public Iterable<N> depthFirstPreOrder(N var1) {
         Preconditions.checkNotNull(var1);
         return this.depthFirstPreOrder((Iterable)ImmutableSet.of(var1));
      }

      private final class BreadthFirstIterator extends UnmodifiableIterator<N> {
         private final Queue<N> queue = new ArrayDeque();

         BreadthFirstIterator(Iterable<? extends N> var2) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               Object var4 = var3.next();
               this.queue.add(var4);
            }

         }

         public boolean hasNext() {
            return this.queue.isEmpty() ^ true;
         }

         public N next() {
            Object var1 = this.queue.remove();
            Iterables.addAll(this.queue, TreeTraverser.this.tree.successors(var1));
            return var1;
         }
      }

      private final class DepthFirstPostOrderIterator extends AbstractIterator<N> {
         private final ArrayDeque<Traverser.TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren> stack;

         DepthFirstPostOrderIterator(Iterable<? extends N> var2) {
            ArrayDeque var3 = new ArrayDeque();
            this.stack = var3;
            var3.addLast(new Traverser.TreeTraverser.DepthFirstPostOrderIterator.NodeAndChildren((Object)null, var2));
         }

         protected N computeNext() {
            while(true) {
               if (!this.stack.isEmpty()) {
                  Traverser.TreeTraverser.DepthFirstPostOrderIterator.NodeAndChildren var1 = (Traverser.TreeTraverser.DepthFirstPostOrderIterator.NodeAndChildren)this.stack.getLast();
                  if (var1.childIterator.hasNext()) {
                     Object var2 = var1.childIterator.next();
                     this.stack.addLast(this.withChildren(var2));
                     continue;
                  }

                  this.stack.removeLast();
                  if (var1.node == null) {
                     continue;
                  }

                  return var1.node;
               }

               return this.endOfData();
            }
         }

         Traverser.TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren withChildren(N var1) {
            return new Traverser.TreeTraverser.DepthFirstPostOrderIterator.NodeAndChildren(var1, TreeTraverser.this.tree.successors(var1));
         }

         private final class NodeAndChildren {
            final Iterator<? extends N> childIterator;
            @NullableDecl
            final N node;

            NodeAndChildren(@NullableDecl N var2, Iterable<? extends N> var3) {
               this.node = var2;
               this.childIterator = var3.iterator();
            }
         }
      }

      private final class DepthFirstPreOrderIterator extends UnmodifiableIterator<N> {
         private final Deque<Iterator<? extends N>> stack;

         DepthFirstPreOrderIterator(Iterable<? extends N> var2) {
            ArrayDeque var3 = new ArrayDeque();
            this.stack = var3;
            var3.addLast(var2.iterator());
         }

         public boolean hasNext() {
            return this.stack.isEmpty() ^ true;
         }

         public N next() {
            Iterator var1 = (Iterator)this.stack.getLast();
            Object var2 = Preconditions.checkNotNull(var1.next());
            if (!var1.hasNext()) {
               this.stack.removeLast();
            }

            var1 = TreeTraverser.this.tree.successors(var2).iterator();
            if (var1.hasNext()) {
               this.stack.addLast(var1);
            }

            return var2;
         }
      }
   }
}
