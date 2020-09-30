package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
   private static final long serialVersionUID = 1L;
   private final transient TreeMultiset.AvlNode<E> header;
   private final transient GeneralRange<E> range;
   private final transient TreeMultiset.Reference<TreeMultiset.AvlNode<E>> rootReference;

   TreeMultiset(TreeMultiset.Reference<TreeMultiset.AvlNode<E>> var1, GeneralRange<E> var2, TreeMultiset.AvlNode<E> var3) {
      super(var2.comparator());
      this.rootReference = var1;
      this.range = var2;
      this.header = var3;
   }

   TreeMultiset(Comparator<? super E> var1) {
      super(var1);
      this.range = GeneralRange.all(var1);
      TreeMultiset.AvlNode var2 = new TreeMultiset.AvlNode((Object)null, 1);
      this.header = var2;
      successor(var2, var2);
      this.rootReference = new TreeMultiset.Reference();
   }

   private long aggregateAboveRange(TreeMultiset.Aggregate var1, @NullableDecl TreeMultiset.AvlNode<E> var2) {
      if (var2 == null) {
         return 0L;
      } else {
         int var3 = this.comparator().compare(this.range.getUpperEndpoint(), var2.elem);
         if (var3 > 0) {
            return this.aggregateAboveRange(var1, var2.right);
         } else {
            long var4;
            long var6;
            if (var3 == 0) {
               var3 = null.$SwitchMap$com$google$common$collect$BoundType[this.range.getUpperBoundType().ordinal()];
               if (var3 != 1) {
                  if (var3 == 2) {
                     return var1.treeAggregate(var2.right);
                  }

                  throw new AssertionError();
               }

               var4 = (long)var1.nodeAggregate(var2);
               var6 = var1.treeAggregate(var2.right);
            } else {
               var4 = var1.treeAggregate(var2.right) + (long)var1.nodeAggregate(var2);
               var6 = this.aggregateAboveRange(var1, var2.left);
            }

            return var4 + var6;
         }
      }
   }

   private long aggregateBelowRange(TreeMultiset.Aggregate var1, @NullableDecl TreeMultiset.AvlNode<E> var2) {
      if (var2 == null) {
         return 0L;
      } else {
         int var3 = this.comparator().compare(this.range.getLowerEndpoint(), var2.elem);
         if (var3 < 0) {
            return this.aggregateBelowRange(var1, var2.left);
         } else {
            long var4;
            long var6;
            if (var3 == 0) {
               var3 = null.$SwitchMap$com$google$common$collect$BoundType[this.range.getLowerBoundType().ordinal()];
               if (var3 != 1) {
                  if (var3 == 2) {
                     return var1.treeAggregate(var2.left);
                  }

                  throw new AssertionError();
               }

               var4 = (long)var1.nodeAggregate(var2);
               var6 = var1.treeAggregate(var2.left);
            } else {
               var4 = var1.treeAggregate(var2.left) + (long)var1.nodeAggregate(var2);
               var6 = this.aggregateBelowRange(var1, var2.right);
            }

            return var4 + var6;
         }
      }
   }

   private long aggregateForEntries(TreeMultiset.Aggregate var1) {
      TreeMultiset.AvlNode var2 = (TreeMultiset.AvlNode)this.rootReference.get();
      long var3 = var1.treeAggregate(var2);
      long var5 = var3;
      if (this.range.hasLowerBound()) {
         var5 = var3 - this.aggregateBelowRange(var1, var2);
      }

      var3 = var5;
      if (this.range.hasUpperBound()) {
         var3 = var5 - this.aggregateAboveRange(var1, var2);
      }

      return var3;
   }

   public static <E extends Comparable> TreeMultiset<E> create() {
      return new TreeMultiset(Ordering.natural());
   }

   public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> var0) {
      TreeMultiset var1 = create();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <E> TreeMultiset<E> create(@NullableDecl Comparator<? super E> var0) {
      TreeMultiset var1;
      if (var0 == null) {
         var1 = new TreeMultiset(Ordering.natural());
      } else {
         var1 = new TreeMultiset(var0);
      }

      return var1;
   }

   static int distinctElements(@NullableDecl TreeMultiset.AvlNode<?> var0) {
      int var1;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.distinctElements;
      }

      return var1;
   }

   @NullableDecl
   private TreeMultiset.AvlNode<E> firstNode() {
      TreeMultiset.AvlNode var1 = (TreeMultiset.AvlNode)this.rootReference.get();
      Object var2 = null;
      if (var1 == null) {
         return null;
      } else {
         TreeMultiset.AvlNode var4;
         if (this.range.hasLowerBound()) {
            Object var3 = this.range.getLowerEndpoint();
            var4 = ((TreeMultiset.AvlNode)this.rootReference.get()).ceiling(this.comparator(), var3);
            if (var4 == null) {
               return null;
            }

            var1 = var4;
            if (this.range.getLowerBoundType() == BoundType.OPEN) {
               var1 = var4;
               if (this.comparator().compare(var3, var4.getElement()) == 0) {
                  var1 = var4.succ;
               }
            }
         } else {
            var1 = this.header.succ;
         }

         var4 = (TreeMultiset.AvlNode)var2;
         if (var1 != this.header) {
            if (!this.range.contains(var1.getElement())) {
               var4 = (TreeMultiset.AvlNode)var2;
            } else {
               var4 = var1;
            }
         }

         return var4;
      }
   }

   @NullableDecl
   private TreeMultiset.AvlNode<E> lastNode() {
      TreeMultiset.AvlNode var1 = (TreeMultiset.AvlNode)this.rootReference.get();
      Object var2 = null;
      if (var1 == null) {
         return null;
      } else {
         TreeMultiset.AvlNode var4;
         if (this.range.hasUpperBound()) {
            Object var3 = this.range.getUpperEndpoint();
            var4 = ((TreeMultiset.AvlNode)this.rootReference.get()).floor(this.comparator(), var3);
            if (var4 == null) {
               return null;
            }

            var1 = var4;
            if (this.range.getUpperBoundType() == BoundType.OPEN) {
               var1 = var4;
               if (this.comparator().compare(var3, var4.getElement()) == 0) {
                  var1 = var4.pred;
               }
            }
         } else {
            var1 = this.header.pred;
         }

         var4 = (TreeMultiset.AvlNode)var2;
         if (var1 != this.header) {
            if (!this.range.contains(var1.getElement())) {
               var4 = (TreeMultiset.AvlNode)var2;
            } else {
               var4 = var1;
            }
         }

         return var4;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Comparator var2 = (Comparator)var1.readObject();
      Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set(this, var2);
      Serialization.getFieldSetter(TreeMultiset.class, "range").set(this, GeneralRange.all(var2));
      Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set(this, new TreeMultiset.Reference());
      TreeMultiset.AvlNode var3 = new TreeMultiset.AvlNode((Object)null, 1);
      Serialization.getFieldSetter(TreeMultiset.class, "header").set(this, var3);
      successor(var3, var3);
      Serialization.populateMultiset(this, var1);
   }

   private static <T> void successor(TreeMultiset.AvlNode<T> var0, TreeMultiset.AvlNode<T> var1) {
      var0.succ = var1;
      var1.pred = var0;
   }

   private static <T> void successor(TreeMultiset.AvlNode<T> var0, TreeMultiset.AvlNode<T> var1, TreeMultiset.AvlNode<T> var2) {
      successor(var0, var1);
      successor(var1, var2);
   }

   private Multiset.Entry<E> wrapEntry(final TreeMultiset.AvlNode<E> var1) {
      return new Multisets.AbstractEntry<E>() {
         public int getCount() {
            int var1x = var1.getCount();
            int var2 = var1x;
            if (var1x == 0) {
               var2 = TreeMultiset.this.count(this.getElement());
            }

            return var2;
         }

         public E getElement() {
            return var1.getElement();
         }
      };
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.elementSet().comparator());
      Serialization.writeMultiset(this, var1);
   }

   public int add(@NullableDecl E var1, int var2) {
      CollectPreconditions.checkNonnegative(var2, "occurrences");
      if (var2 == 0) {
         return this.count(var1);
      } else {
         Preconditions.checkArgument(this.range.contains(var1));
         TreeMultiset.AvlNode var3 = (TreeMultiset.AvlNode)this.rootReference.get();
         TreeMultiset.AvlNode var5;
         if (var3 == null) {
            this.comparator().compare(var1, var1);
            var5 = new TreeMultiset.AvlNode(var1, var2);
            TreeMultiset.AvlNode var6 = this.header;
            successor(var6, var5, var6);
            this.rootReference.checkAndSet(var3, var5);
            return 0;
         } else {
            int[] var4 = new int[1];
            var5 = var3.add(this.comparator(), var1, var2, var4);
            this.rootReference.checkAndSet(var3, var5);
            return var4[0];
         }
      }
   }

   public void clear() {
      if (!this.range.hasLowerBound() && !this.range.hasUpperBound()) {
         TreeMultiset.AvlNode var1 = this.header.succ;

         while(true) {
            TreeMultiset.AvlNode var2 = this.header;
            if (var1 == var2) {
               successor(var2, var2);
               this.rootReference.clear();
               break;
            }

            var2 = var1.succ;
            var1.elemCount = 0;
            var1.left = null;
            var1.right = null;
            var1.pred = null;
            var1.succ = null;
            var1 = var2;
         }
      } else {
         Iterators.clear(this.entryIterator());
      }

   }

   public int count(@NullableDecl Object param1) {
      // $FF: Couldn't be decompiled
   }

   Iterator<Multiset.Entry<E>> descendingEntryIterator() {
      return new Iterator<Multiset.Entry<E>>() {
         TreeMultiset.AvlNode<E> current = TreeMultiset.this.lastNode();
         Multiset.Entry<E> prevEntry = null;

         public boolean hasNext() {
            if (this.current == null) {
               return false;
            } else if (TreeMultiset.this.range.tooLow(this.current.getElement())) {
               this.current = null;
               return false;
            } else {
               return true;
            }
         }

         public Multiset.Entry<E> next() {
            if (this.hasNext()) {
               Multiset.Entry var1 = TreeMultiset.this.wrapEntry(this.current);
               this.prevEntry = var1;
               if (this.current.pred == TreeMultiset.this.header) {
                  this.current = null;
               } else {
                  this.current = this.current.pred;
               }

               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            boolean var1;
            if (this.prevEntry != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
            this.prevEntry = null;
         }
      };
   }

   int distinctElements() {
      return Ints.saturatedCast(this.aggregateForEntries(TreeMultiset.Aggregate.DISTINCT));
   }

   Iterator<E> elementIterator() {
      return Multisets.elementIterator(this.entryIterator());
   }

   Iterator<Multiset.Entry<E>> entryIterator() {
      return new Iterator<Multiset.Entry<E>>() {
         TreeMultiset.AvlNode<E> current = TreeMultiset.this.firstNode();
         @NullableDecl
         Multiset.Entry<E> prevEntry;

         public boolean hasNext() {
            if (this.current == null) {
               return false;
            } else if (TreeMultiset.this.range.tooHigh(this.current.getElement())) {
               this.current = null;
               return false;
            } else {
               return true;
            }
         }

         public Multiset.Entry<E> next() {
            if (this.hasNext()) {
               Multiset.Entry var1 = TreeMultiset.this.wrapEntry(this.current);
               this.prevEntry = var1;
               if (this.current.succ == TreeMultiset.this.header) {
                  this.current = null;
               } else {
                  this.current = this.current.succ;
               }

               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            boolean var1;
            if (this.prevEntry != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
            this.prevEntry = null;
         }
      };
   }

   public SortedMultiset<E> headMultiset(@NullableDecl E var1, BoundType var2) {
      return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(this.comparator(), var1, var2)), this.header);
   }

   public Iterator<E> iterator() {
      return Multisets.iteratorImpl(this);
   }

   public int remove(@NullableDecl Object var1, int var2) {
      CollectPreconditions.checkNonnegative(var2, "occurrences");
      if (var2 == 0) {
         return this.count(var1);
      } else {
         TreeMultiset.AvlNode var3 = (TreeMultiset.AvlNode)this.rootReference.get();
         int[] var4 = new int[1];

         boolean var10001;
         try {
            if (!this.range.contains(var1)) {
               return 0;
            }
         } catch (NullPointerException | ClassCastException var6) {
            var10001 = false;
            return 0;
         }

         if (var3 != null) {
            TreeMultiset.AvlNode var7;
            try {
               var7 = var3.remove(this.comparator(), var1, var2, var4);
            } catch (NullPointerException | ClassCastException var5) {
               var10001 = false;
               return 0;
            }

            this.rootReference.checkAndSet(var3, var7);
            return var4[0];
         } else {
            return 0;
         }
      }
   }

   public int setCount(@NullableDecl E var1, int var2) {
      CollectPreconditions.checkNonnegative(var2, "count");
      boolean var3 = this.range.contains(var1);
      boolean var4 = true;
      if (!var3) {
         if (var2 != 0) {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         return 0;
      } else {
         TreeMultiset.AvlNode var5 = (TreeMultiset.AvlNode)this.rootReference.get();
         if (var5 == null) {
            if (var2 > 0) {
               this.add(var1, var2);
            }

            return 0;
         } else {
            int[] var6 = new int[1];
            TreeMultiset.AvlNode var7 = var5.setCount(this.comparator(), var1, var2, var6);
            this.rootReference.checkAndSet(var5, var7);
            return var6[0];
         }
      }
   }

   public boolean setCount(@NullableDecl E var1, int var2, int var3) {
      CollectPreconditions.checkNonnegative(var3, "newCount");
      CollectPreconditions.checkNonnegative(var2, "oldCount");
      Preconditions.checkArgument(this.range.contains(var1));
      TreeMultiset.AvlNode var4 = (TreeMultiset.AvlNode)this.rootReference.get();
      boolean var5 = false;
      if (var4 == null) {
         if (var2 == 0) {
            if (var3 > 0) {
               this.add(var1, var3);
            }

            return true;
         } else {
            return false;
         }
      } else {
         int[] var6 = new int[1];
         TreeMultiset.AvlNode var7 = var4.setCount(this.comparator(), var1, var2, var3, var6);
         this.rootReference.checkAndSet(var4, var7);
         if (var6[0] == var2) {
            var5 = true;
         }

         return var5;
      }
   }

   public int size() {
      return Ints.saturatedCast(this.aggregateForEntries(TreeMultiset.Aggregate.SIZE));
   }

   public SortedMultiset<E> tailMultiset(@NullableDecl E var1, BoundType var2) {
      return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(this.comparator(), var1, var2)), this.header);
   }

   private static enum Aggregate {
      DISTINCT,
      SIZE {
         int nodeAggregate(TreeMultiset.AvlNode<?> var1) {
            return var1.elemCount;
         }

         long treeAggregate(@NullableDecl TreeMultiset.AvlNode<?> var1) {
            long var2;
            if (var1 == null) {
               var2 = 0L;
            } else {
               var2 = var1.totalCount;
            }

            return var2;
         }
      };

      static {
         TreeMultiset.Aggregate var0 = new TreeMultiset.Aggregate("DISTINCT", 1) {
            int nodeAggregate(TreeMultiset.AvlNode<?> var1) {
               return 1;
            }

            long treeAggregate(@NullableDecl TreeMultiset.AvlNode<?> var1) {
               long var2;
               if (var1 == null) {
                  var2 = 0L;
               } else {
                  var2 = (long)var1.distinctElements;
               }

               return var2;
            }
         };
         DISTINCT = var0;
      }

      private Aggregate() {
      }

      // $FF: synthetic method
      Aggregate(Object var3) {
         this();
      }

      abstract int nodeAggregate(TreeMultiset.AvlNode<?> var1);

      abstract long treeAggregate(@NullableDecl TreeMultiset.AvlNode<?> var1);
   }

   private static final class AvlNode<E> {
      private int distinctElements;
      @NullableDecl
      private final E elem;
      private int elemCount;
      private int height;
      @NullableDecl
      private TreeMultiset.AvlNode<E> left;
      @NullableDecl
      private TreeMultiset.AvlNode<E> pred;
      @NullableDecl
      private TreeMultiset.AvlNode<E> right;
      @NullableDecl
      private TreeMultiset.AvlNode<E> succ;
      private long totalCount;

      AvlNode(@NullableDecl E var1, int var2) {
         boolean var3;
         if (var2 > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         this.elem = var1;
         this.elemCount = var2;
         this.totalCount = (long)var2;
         this.distinctElements = 1;
         this.height = 1;
         this.left = null;
         this.right = null;
      }

      private TreeMultiset.AvlNode<E> addLeftChild(E var1, int var2) {
         TreeMultiset.AvlNode var3 = new TreeMultiset.AvlNode(var1, var2);
         this.left = var3;
         TreeMultiset.successor(this.pred, var3, this);
         this.height = Math.max(2, this.height);
         ++this.distinctElements;
         this.totalCount += (long)var2;
         return this;
      }

      private TreeMultiset.AvlNode<E> addRightChild(E var1, int var2) {
         TreeMultiset.AvlNode var3 = new TreeMultiset.AvlNode(var1, var2);
         this.right = var3;
         TreeMultiset.successor(this, var3, this.succ);
         this.height = Math.max(2, this.height);
         ++this.distinctElements;
         this.totalCount += (long)var2;
         return this;
      }

      private int balanceFactor() {
         return height(this.left) - height(this.right);
      }

      @NullableDecl
      private TreeMultiset.AvlNode<E> ceiling(Comparator<? super E> var1, E var2) {
         int var3 = var1.compare(var2, this.elem);
         TreeMultiset.AvlNode var4;
         TreeMultiset.AvlNode var5;
         if (var3 < 0) {
            var4 = this.left;
            if (var4 == null) {
               var5 = this;
            } else {
               var5 = (TreeMultiset.AvlNode)MoreObjects.firstNonNull(var4.ceiling(var1, var2), this);
            }

            return var5;
         } else if (var3 == 0) {
            return this;
         } else {
            var4 = this.right;
            if (var4 == null) {
               var5 = null;
            } else {
               var5 = var4.ceiling(var1, var2);
            }

            return var5;
         }
      }

      private TreeMultiset.AvlNode<E> deleteMe() {
         int var1 = this.elemCount;
         this.elemCount = 0;
         TreeMultiset.successor(this.pred, this.succ);
         TreeMultiset.AvlNode var2 = this.left;
         if (var2 == null) {
            return this.right;
         } else {
            TreeMultiset.AvlNode var3 = this.right;
            if (var3 == null) {
               return var2;
            } else if (var2.height >= var3.height) {
               var3 = this.pred;
               var3.left = var2.removeMax(var3);
               var3.right = this.right;
               var3.distinctElements = this.distinctElements - 1;
               var3.totalCount = this.totalCount - (long)var1;
               return var3.rebalance();
            } else {
               var2 = this.succ;
               var2.right = var3.removeMin(var2);
               var2.left = this.left;
               var2.distinctElements = this.distinctElements - 1;
               var2.totalCount = this.totalCount - (long)var1;
               return var2.rebalance();
            }
         }
      }

      @NullableDecl
      private TreeMultiset.AvlNode<E> floor(Comparator<? super E> var1, E var2) {
         int var3 = var1.compare(var2, this.elem);
         TreeMultiset.AvlNode var4;
         TreeMultiset.AvlNode var5;
         if (var3 > 0) {
            var4 = this.right;
            if (var4 == null) {
               var5 = this;
            } else {
               var5 = (TreeMultiset.AvlNode)MoreObjects.firstNonNull(var4.floor(var1, var2), this);
            }

            return var5;
         } else if (var3 == 0) {
            return this;
         } else {
            var4 = this.left;
            if (var4 == null) {
               var5 = null;
            } else {
               var5 = var4.floor(var1, var2);
            }

            return var5;
         }
      }

      private static int height(@NullableDecl TreeMultiset.AvlNode<?> var0) {
         int var1;
         if (var0 == null) {
            var1 = 0;
         } else {
            var1 = var0.height;
         }

         return var1;
      }

      private TreeMultiset.AvlNode<E> rebalance() {
         int var1 = this.balanceFactor();
         if (var1 != -2) {
            if (var1 != 2) {
               this.recomputeHeight();
               return this;
            } else {
               if (this.left.balanceFactor() < 0) {
                  this.left = this.left.rotateLeft();
               }

               return this.rotateRight();
            }
         } else {
            if (this.right.balanceFactor() > 0) {
               this.right = this.right.rotateRight();
            }

            return this.rotateLeft();
         }
      }

      private void recompute() {
         this.recomputeMultiset();
         this.recomputeHeight();
      }

      private void recomputeHeight() {
         this.height = Math.max(height(this.left), height(this.right)) + 1;
      }

      private void recomputeMultiset() {
         this.distinctElements = TreeMultiset.distinctElements(this.left) + 1 + TreeMultiset.distinctElements(this.right);
         this.totalCount = (long)this.elemCount + totalCount(this.left) + totalCount(this.right);
      }

      private TreeMultiset.AvlNode<E> removeMax(TreeMultiset.AvlNode<E> var1) {
         TreeMultiset.AvlNode var2 = this.right;
         if (var2 == null) {
            return this.left;
         } else {
            this.right = var2.removeMax(var1);
            --this.distinctElements;
            this.totalCount -= (long)var1.elemCount;
            return this.rebalance();
         }
      }

      private TreeMultiset.AvlNode<E> removeMin(TreeMultiset.AvlNode<E> var1) {
         TreeMultiset.AvlNode var2 = this.left;
         if (var2 == null) {
            return this.right;
         } else {
            this.left = var2.removeMin(var1);
            --this.distinctElements;
            this.totalCount -= (long)var1.elemCount;
            return this.rebalance();
         }
      }

      private TreeMultiset.AvlNode<E> rotateLeft() {
         boolean var1;
         if (this.right != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1);
         TreeMultiset.AvlNode var2 = this.right;
         this.right = var2.left;
         var2.left = this;
         var2.totalCount = this.totalCount;
         var2.distinctElements = this.distinctElements;
         this.recompute();
         var2.recomputeHeight();
         return var2;
      }

      private TreeMultiset.AvlNode<E> rotateRight() {
         boolean var1;
         if (this.left != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1);
         TreeMultiset.AvlNode var2 = this.left;
         this.left = var2.right;
         var2.right = this;
         var2.totalCount = this.totalCount;
         var2.distinctElements = this.distinctElements;
         this.recompute();
         var2.recomputeHeight();
         return var2;
      }

      private static long totalCount(@NullableDecl TreeMultiset.AvlNode<?> var0) {
         long var1;
         if (var0 == null) {
            var1 = 0L;
         } else {
            var1 = var0.totalCount;
         }

         return var1;
      }

      TreeMultiset.AvlNode<E> add(Comparator<? super E> var1, @NullableDecl E var2, int var3, int[] var4) {
         int var5 = var1.compare(var2, this.elem);
         boolean var6 = true;
         TreeMultiset.AvlNode var7;
         TreeMultiset.AvlNode var12;
         if (var5 < 0) {
            var7 = this.left;
            if (var7 == null) {
               var4[0] = 0;
               return this.addLeftChild(var2, var3);
            } else {
               var5 = var7.height;
               this.left = var7.add(var1, var2, var3, var4);
               if (var4[0] == 0) {
                  ++this.distinctElements;
               }

               this.totalCount += (long)var3;
               if (this.left.height == var5) {
                  var12 = this;
               } else {
                  var12 = this.rebalance();
               }

               return var12;
            }
         } else if (var5 > 0) {
            var7 = this.right;
            if (var7 == null) {
               var4[0] = 0;
               return this.addRightChild(var2, var3);
            } else {
               var5 = var7.height;
               this.right = var7.add(var1, var2, var3, var4);
               if (var4[0] == 0) {
                  ++this.distinctElements;
               }

               this.totalCount += (long)var3;
               if (this.right.height == var5) {
                  var12 = this;
               } else {
                  var12 = this.rebalance();
               }

               return var12;
            }
         } else {
            var5 = this.elemCount;
            var4[0] = var5;
            long var8 = (long)var5;
            long var10 = (long)var3;
            if (var8 + var10 > 2147483647L) {
               var6 = false;
            }

            Preconditions.checkArgument(var6);
            this.elemCount += var3;
            this.totalCount += var10;
            return this;
         }
      }

      public int count(Comparator<? super E> var1, E var2) {
         int var3 = var1.compare(var2, this.elem);
         byte var4 = 0;
         int var5 = 0;
         TreeMultiset.AvlNode var6;
         if (var3 < 0) {
            var6 = this.left;
            if (var6 != null) {
               var5 = var6.count(var1, var2);
            }

            return var5;
         } else if (var3 > 0) {
            var6 = this.right;
            if (var6 == null) {
               var5 = var4;
            } else {
               var5 = var6.count(var1, var2);
            }

            return var5;
         } else {
            return this.elemCount;
         }
      }

      int getCount() {
         return this.elemCount;
      }

      E getElement() {
         return this.elem;
      }

      TreeMultiset.AvlNode<E> remove(Comparator<? super E> var1, @NullableDecl E var2, int var3, int[] var4) {
         int var5 = var1.compare(var2, this.elem);
         TreeMultiset.AvlNode var6;
         if (var5 < 0) {
            var6 = this.left;
            if (var6 == null) {
               var4[0] = 0;
               return this;
            } else {
               this.left = var6.remove(var1, var2, var3, var4);
               if (var4[0] > 0) {
                  if (var3 >= var4[0]) {
                     --this.distinctElements;
                     this.totalCount -= (long)var4[0];
                  } else {
                     this.totalCount -= (long)var3;
                  }
               }

               TreeMultiset.AvlNode var7;
               if (var4[0] == 0) {
                  var7 = this;
               } else {
                  var7 = this.rebalance();
               }

               return var7;
            }
         } else if (var5 > 0) {
            var6 = this.right;
            if (var6 == null) {
               var4[0] = 0;
               return this;
            } else {
               this.right = var6.remove(var1, var2, var3, var4);
               if (var4[0] > 0) {
                  if (var3 >= var4[0]) {
                     --this.distinctElements;
                     this.totalCount -= (long)var4[0];
                  } else {
                     this.totalCount -= (long)var3;
                  }
               }

               return this.rebalance();
            }
         } else {
            var5 = this.elemCount;
            var4[0] = var5;
            if (var3 >= var5) {
               return this.deleteMe();
            } else {
               this.elemCount = var5 - var3;
               this.totalCount -= (long)var3;
               return this;
            }
         }
      }

      TreeMultiset.AvlNode<E> setCount(Comparator<? super E> var1, @NullableDecl E var2, int var3, int var4, int[] var5) {
         int var6 = var1.compare(var2, this.elem);
         TreeMultiset.AvlNode var7;
         if (var6 < 0) {
            var7 = this.left;
            if (var7 == null) {
               var5[0] = 0;
               return var3 == 0 && var4 > 0 ? this.addLeftChild(var2, var4) : this;
            } else {
               this.left = var7.setCount(var1, var2, var3, var4, var5);
               if (var5[0] == var3) {
                  if (var4 == 0 && var5[0] != 0) {
                     --this.distinctElements;
                  } else if (var4 > 0 && var5[0] == 0) {
                     ++this.distinctElements;
                  }

                  this.totalCount += (long)(var4 - var5[0]);
               }

               return this.rebalance();
            }
         } else if (var6 > 0) {
            var7 = this.right;
            if (var7 == null) {
               var5[0] = 0;
               return var3 == 0 && var4 > 0 ? this.addRightChild(var2, var4) : this;
            } else {
               this.right = var7.setCount(var1, var2, var3, var4, var5);
               if (var5[0] == var3) {
                  if (var4 == 0 && var5[0] != 0) {
                     --this.distinctElements;
                  } else if (var4 > 0 && var5[0] == 0) {
                     ++this.distinctElements;
                  }

                  this.totalCount += (long)(var4 - var5[0]);
               }

               return this.rebalance();
            }
         } else {
            var6 = this.elemCount;
            var5[0] = var6;
            if (var3 == var6) {
               if (var4 == 0) {
                  return this.deleteMe();
               }

               this.totalCount += (long)(var4 - var6);
               this.elemCount = var4;
            }

            return this;
         }
      }

      TreeMultiset.AvlNode<E> setCount(Comparator<? super E> var1, @NullableDecl E var2, int var3, int[] var4) {
         int var5 = var1.compare(var2, this.elem);
         TreeMultiset.AvlNode var6;
         TreeMultiset.AvlNode var7;
         if (var5 < 0) {
            var6 = this.left;
            if (var6 == null) {
               var4[0] = 0;
               if (var3 > 0) {
                  var7 = this.addLeftChild(var2, var3);
               } else {
                  var7 = this;
               }

               return var7;
            } else {
               this.left = var6.setCount(var1, var2, var3, var4);
               if (var3 == 0 && var4[0] != 0) {
                  --this.distinctElements;
               } else if (var3 > 0 && var4[0] == 0) {
                  ++this.distinctElements;
               }

               this.totalCount += (long)(var3 - var4[0]);
               return this.rebalance();
            }
         } else if (var5 > 0) {
            var6 = this.right;
            if (var6 == null) {
               var4[0] = 0;
               if (var3 > 0) {
                  var7 = this.addRightChild(var2, var3);
               } else {
                  var7 = this;
               }

               return var7;
            } else {
               this.right = var6.setCount(var1, var2, var3, var4);
               if (var3 == 0 && var4[0] != 0) {
                  --this.distinctElements;
               } else if (var3 > 0 && var4[0] == 0) {
                  ++this.distinctElements;
               }

               this.totalCount += (long)(var3 - var4[0]);
               return this.rebalance();
            }
         } else {
            var5 = this.elemCount;
            var4[0] = var5;
            if (var3 == 0) {
               return this.deleteMe();
            } else {
               this.totalCount += (long)(var3 - var5);
               this.elemCount = var3;
               return this;
            }
         }
      }

      public String toString() {
         return Multisets.immutableEntry(this.getElement(), this.getCount()).toString();
      }
   }

   private static final class Reference<T> {
      @NullableDecl
      private T value;

      private Reference() {
      }

      // $FF: synthetic method
      Reference(Object var1) {
         this();
      }

      public void checkAndSet(@NullableDecl T var1, T var2) {
         if (this.value == var1) {
            this.value = var2;
         } else {
            throw new ConcurrentModificationException();
         }
      }

      void clear() {
         this.value = null;
      }

      @NullableDecl
      public T get() {
         return this.value;
      }
   }
}
