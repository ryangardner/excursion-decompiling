/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.Network;
import com.google.common.graph.SuccessorsFunction;
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

    public static <N> Traverser<N> forGraph(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        return new GraphTraverser<N>(successorsFunction);
    }

    public static <N> Traverser<N> forTree(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        if (successorsFunction instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph)successorsFunction).isDirected(), "Undirected graphs can never be trees.");
        }
        if (!(successorsFunction instanceof Network)) return new TreeTraverser<N>(successorsFunction);
        Preconditions.checkArgument(((Network)successorsFunction).isDirected(), "Undirected networks can never be trees.");
        return new TreeTraverser<N>(successorsFunction);
    }

    public abstract Iterable<N> breadthFirst(Iterable<? extends N> var1);

    public abstract Iterable<N> breadthFirst(N var1);

    public abstract Iterable<N> depthFirstPostOrder(Iterable<? extends N> var1);

    public abstract Iterable<N> depthFirstPostOrder(N var1);

    public abstract Iterable<N> depthFirstPreOrder(Iterable<? extends N> var1);

    public abstract Iterable<N> depthFirstPreOrder(N var1);

    private static final class GraphTraverser<N>
    extends Traverser<N> {
        private final SuccessorsFunction<N> graph;

        GraphTraverser(SuccessorsFunction<N> successorsFunction) {
            this.graph = Preconditions.checkNotNull(successorsFunction);
        }

        private void checkThatNodeIsInGraph(N n) {
            this.graph.successors(n);
        }

        @Override
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInGraph(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(iterable);
                }
            };
        }

        @Override
        public Iterable<N> breadthFirst(N n) {
            Preconditions.checkNotNull(n);
            return this.breadthFirst((N)ImmutableSet.of(n));
        }

        @Override
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInGraph(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(iterable, Order.POSTORDER);
                }
            };
        }

        @Override
        public Iterable<N> depthFirstPostOrder(N n) {
            Preconditions.checkNotNull(n);
            return this.depthFirstPostOrder((N)ImmutableSet.of(n));
        }

        @Override
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInGraph(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(iterable, Order.PREORDER);
                }
            };
        }

        @Override
        public Iterable<N> depthFirstPreOrder(N n) {
            Preconditions.checkNotNull(n);
            return this.depthFirstPreOrder((N)ImmutableSet.of(n));
        }

        private final class BreadthFirstIterator
        extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque<N>();
            private final Set<N> visited = new HashSet<N>();

            BreadthFirstIterator(Iterable<? extends N> object) {
                object = object.iterator();
                while (object.hasNext()) {
                    GraphTraverser.this = object.next();
                    if (!this.visited.add(GraphTraverser.this)) continue;
                    this.queue.add(GraphTraverser.this);
                }
            }

            @Override
            public boolean hasNext() {
                return this.queue.isEmpty() ^ true;
            }

            @Override
            public N next() {
                N n = this.queue.remove();
                Iterator<N> iterator2 = GraphTraverser.this.graph.successors(n).iterator();
                while (iterator2.hasNext()) {
                    N n2 = iterator2.next();
                    if (!this.visited.add(n2)) continue;
                    this.queue.add(n2);
                }
                return n;
            }
        }

        private final class DepthFirstIterator
        extends AbstractIterator<N> {
            private final Order order;
            private final Deque<GraphTraverser<N>> stack = new ArrayDeque<GraphTraverser<N>>();
            private final Set<N> visited = new HashSet<N>();

            DepthFirstIterator(Iterable<? extends N> iterable, Order order) {
                this.stack.push((GraphTraverser<N>)((Object)new NodeAndSuccessors(null, iterable)));
                this.order = order;
            }

            @Override
            protected N computeNext() {
                NodeAndSuccessors nodeAndSuccessors;
                boolean bl;
                do {
                    boolean bl2;
                    block7 : {
                        boolean bl3;
                        block6 : {
                            if (this.stack.isEmpty()) {
                                return (N)this.endOfData();
                            }
                            nodeAndSuccessors = (NodeAndSuccessors)((Object)this.stack.getFirst());
                            boolean bl4 = this.visited.add(nodeAndSuccessors.node);
                            boolean bl5 = nodeAndSuccessors.successorIterator.hasNext();
                            bl3 = true;
                            bl2 = bl5 ^ true;
                            if (!bl4) break block6;
                            bl = bl3;
                            if (this.order == Order.PREORDER) break block7;
                        }
                        bl = bl2 && this.order == Order.POSTORDER ? bl3 : false;
                    }
                    if (bl2) {
                        this.stack.pop();
                        continue;
                    }
                    Object n = nodeAndSuccessors.successorIterator.next();
                    if (this.visited.contains(n)) continue;
                    this.stack.push((GraphTraverser<N>)((Object)this.withSuccessors(n)));
                } while (!bl || nodeAndSuccessors.node == null);
                return nodeAndSuccessors.node;
            }

            GraphTraverser<N> withSuccessors(N n) {
                return new NodeAndSuccessors(n, GraphTraverser.this.graph.successors(n));
            }

            private final class NodeAndSuccessors {
                @NullableDecl
                final N node;
                final Iterator<? extends N> successorIterator;

                NodeAndSuccessors(N n, Iterable<? extends N> iterable) {
                    this.node = n;
                    this.successorIterator = iterable.iterator();
                }
            }

        }

    }

    private static final class Order
    extends Enum<Order> {
        private static final /* synthetic */ Order[] $VALUES;
        public static final /* enum */ Order POSTORDER;
        public static final /* enum */ Order PREORDER;

        static {
            Order order;
            PREORDER = new Order();
            POSTORDER = order = new Order();
            $VALUES = new Order[]{PREORDER, order};
        }

        public static Order valueOf(String string2) {
            return Enum.valueOf(Order.class, string2);
        }

        public static Order[] values() {
            return (Order[])$VALUES.clone();
        }
    }

    private static final class TreeTraverser<N>
    extends Traverser<N> {
        private final SuccessorsFunction<N> tree;

        TreeTraverser(SuccessorsFunction<N> successorsFunction) {
            this.tree = Preconditions.checkNotNull(successorsFunction);
        }

        private void checkThatNodeIsInTree(N n) {
            this.tree.successors(n);
        }

        @Override
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInTree(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(iterable);
                }
            };
        }

        @Override
        public Iterable<N> breadthFirst(N n) {
            Preconditions.checkNotNull(n);
            return this.breadthFirst((N)ImmutableSet.of(n));
        }

        @Override
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInTree(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new DepthFirstPostOrderIterator(iterable);
                }
            };
        }

        @Override
        public Iterable<N> depthFirstPostOrder(N n) {
            Preconditions.checkNotNull(n);
            return this.depthFirstPostOrder((N)ImmutableSet.of(n));
        }

        @Override
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<N> iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                this.checkThatNodeIsInTree(iterator2.next());
            }
            return new Iterable<N>(){

                @Override
                public Iterator<N> iterator() {
                    return new DepthFirstPreOrderIterator(iterable);
                }
            };
        }

        @Override
        public Iterable<N> depthFirstPreOrder(N n) {
            Preconditions.checkNotNull(n);
            return this.depthFirstPreOrder((N)ImmutableSet.of(n));
        }

        private final class BreadthFirstIterator
        extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque<N>();

            BreadthFirstIterator(Iterable<? extends N> iterable) {
                TreeTraverser.this = iterable.iterator();
                while (TreeTraverser.this.hasNext()) {
                    iterable = TreeTraverser.this.next();
                    this.queue.add(iterable);
                }
            }

            @Override
            public boolean hasNext() {
                return this.queue.isEmpty() ^ true;
            }

            @Override
            public N next() {
                N n = this.queue.remove();
                Iterables.addAll(this.queue, TreeTraverser.this.tree.successors(n));
                return n;
            }
        }

        private final class DepthFirstPostOrderIterator
        extends AbstractIterator<N> {
            private final ArrayDeque<TreeTraverser<N>> stack;

            DepthFirstPostOrderIterator(Iterable<? extends N> iterable) {
                this.stack = TreeTraverser.this = new ArrayDeque();
                ((ArrayDeque)TreeTraverser.this).addLast(new NodeAndChildren(null, iterable));
            }

            @Override
            protected N computeNext() {
                while (!this.stack.isEmpty()) {
                    NodeAndChildren nodeAndChildren = (NodeAndChildren)((Object)this.stack.getLast());
                    if (nodeAndChildren.childIterator.hasNext()) {
                        nodeAndChildren = nodeAndChildren.childIterator.next();
                        this.stack.addLast((TreeTraverser<N>)((Object)this.withChildren((Object)nodeAndChildren)));
                        continue;
                    }
                    this.stack.removeLast();
                    if (nodeAndChildren.node != null) return nodeAndChildren.node;
                }
                return (N)this.endOfData();
            }

            TreeTraverser<N> withChildren(N n) {
                return new NodeAndChildren(n, TreeTraverser.this.tree.successors(n));
            }

            private final class NodeAndChildren {
                final Iterator<? extends N> childIterator;
                @NullableDecl
                final N node;

                NodeAndChildren(N n, Iterable<? extends N> iterable) {
                    this.node = n;
                    this.childIterator = iterable.iterator();
                }
            }

        }

        private final class DepthFirstPreOrderIterator
        extends UnmodifiableIterator<N> {
            private final Deque<Iterator<? extends N>> stack;

            DepthFirstPreOrderIterator(Iterable<? extends N> iterable) {
                this.stack = TreeTraverser.this = new ArrayDeque();
                TreeTraverser.this.addLast(iterable.iterator());
            }

            @Override
            public boolean hasNext() {
                return this.stack.isEmpty() ^ true;
            }

            @Override
            public N next() {
                Iterator<Object> iterator2 = this.stack.getLast();
                N n = Preconditions.checkNotNull(iterator2.next());
                if (!iterator2.hasNext()) {
                    this.stack.removeLast();
                }
                if (!(iterator2 = TreeTraverser.this.tree.successors(n).iterator()).hasNext()) return n;
                this.stack.addLast(iterator2);
                return n;
            }
        }

    }

}

