/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@Deprecated
public abstract class TreeTraverser<T> {
    @Deprecated
    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>(){

            @Override
            public Iterable<T> children(T t) {
                return (Iterable)function.apply(t);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> breadthFirstTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(){

            @Override
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(t);
            }
        };
    }

    public abstract Iterable<T> children(T var1);

    UnmodifiableIterator<T> postOrderIterator(T t) {
        return new PostOrderIterator(t);
    }

    @Deprecated
    public final FluentIterable<T> postOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(){

            @Override
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.postOrderIterator(t);
            }
        };
    }

    UnmodifiableIterator<T> preOrderIterator(T t) {
        return new PreOrderIterator(t);
    }

    @Deprecated
    public final FluentIterable<T> preOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(){

            @Override
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.preOrderIterator(t);
            }
        };
    }

    private final class BreadthFirstIterator
    extends UnmodifiableIterator<T>
    implements PeekingIterator<T> {
        private final Queue<T> queue;

        BreadthFirstIterator(T t) {
            this.queue = TreeTraverser.this = new ArrayDeque();
            TreeTraverser.this.add(t);
        }

        @Override
        public boolean hasNext() {
            return this.queue.isEmpty() ^ true;
        }

        @Override
        public T next() {
            T t = this.queue.remove();
            Iterables.addAll(this.queue, TreeTraverser.this.children(t));
            return t;
        }

        @Override
        public T peek() {
            return this.queue.element();
        }
    }

    private final class PostOrderIterator
    extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> stack;

        PostOrderIterator(T t) {
            this.stack = TreeTraverser.this = new ArrayDeque();
            ((ArrayDeque)TreeTraverser.this).addLast(this.expand(t));
        }

        private PostOrderNode<T> expand(T t) {
            return new PostOrderNode<T>(t, TreeTraverser.this.children(t).iterator());
        }

        @Override
        protected T computeNext() {
            while (!this.stack.isEmpty()) {
                PostOrderNode<T> postOrderNode = this.stack.getLast();
                if (!postOrderNode.childIterator.hasNext()) {
                    this.stack.removeLast();
                    return postOrderNode.root;
                }
                postOrderNode = postOrderNode.childIterator.next();
                this.stack.addLast(this.expand(postOrderNode));
            }
            return this.endOfData();
        }
    }

    private static final class PostOrderNode<T> {
        final Iterator<T> childIterator;
        final T root;

        PostOrderNode(T t, Iterator<T> iterator2) {
            this.root = Preconditions.checkNotNull(t);
            this.childIterator = Preconditions.checkNotNull(iterator2);
        }
    }

    private final class PreOrderIterator
    extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> stack;

        PreOrderIterator(T t) {
            this.stack = TreeTraverser.this = new ArrayDeque();
            TreeTraverser.this.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        @Override
        public boolean hasNext() {
            return this.stack.isEmpty() ^ true;
        }

        @Override
        public T next() {
            Iterator<T> iterator2 = this.stack.getLast();
            T t = Preconditions.checkNotNull(iterator2.next());
            if (!iterator2.hasNext()) {
                this.stack.removeLast();
            }
            if (!(iterator2 = TreeTraverser.this.children(t).iterator()).hasNext()) return t;
            this.stack.addLast(iterator2);
            return t;
        }
    }

}

