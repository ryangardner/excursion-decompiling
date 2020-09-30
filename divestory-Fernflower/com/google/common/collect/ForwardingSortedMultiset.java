package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;

public abstract class ForwardingSortedMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
   protected ForwardingSortedMultiset() {
   }

   public Comparator<? super E> comparator() {
      return this.delegate().comparator();
   }

   protected abstract SortedMultiset<E> delegate();

   public SortedMultiset<E> descendingMultiset() {
      return this.delegate().descendingMultiset();
   }

   public NavigableSet<E> elementSet() {
      return this.delegate().elementSet();
   }

   public Multiset.Entry<E> firstEntry() {
      return this.delegate().firstEntry();
   }

   public SortedMultiset<E> headMultiset(E var1, BoundType var2) {
      return this.delegate().headMultiset(var1, var2);
   }

   public Multiset.Entry<E> lastEntry() {
      return this.delegate().lastEntry();
   }

   public Multiset.Entry<E> pollFirstEntry() {
      return this.delegate().pollFirstEntry();
   }

   public Multiset.Entry<E> pollLastEntry() {
      return this.delegate().pollLastEntry();
   }

   protected Multiset.Entry<E> standardFirstEntry() {
      Iterator var1 = this.entrySet().iterator();
      if (!var1.hasNext()) {
         return null;
      } else {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         return Multisets.immutableEntry(var2.getElement(), var2.getCount());
      }
   }

   protected Multiset.Entry<E> standardLastEntry() {
      Iterator var1 = this.descendingMultiset().entrySet().iterator();
      if (!var1.hasNext()) {
         return null;
      } else {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         return Multisets.immutableEntry(var2.getElement(), var2.getCount());
      }
   }

   protected Multiset.Entry<E> standardPollFirstEntry() {
      Iterator var1 = this.entrySet().iterator();
      if (!var1.hasNext()) {
         return null;
      } else {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         var2 = Multisets.immutableEntry(var2.getElement(), var2.getCount());
         var1.remove();
         return var2;
      }
   }

   protected Multiset.Entry<E> standardPollLastEntry() {
      Iterator var1 = this.descendingMultiset().entrySet().iterator();
      if (!var1.hasNext()) {
         return null;
      } else {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         var2 = Multisets.immutableEntry(var2.getElement(), var2.getCount());
         var1.remove();
         return var2;
      }
   }

   protected SortedMultiset<E> standardSubMultiset(E var1, BoundType var2, E var3, BoundType var4) {
      return this.tailMultiset(var1, var2).headMultiset(var3, var4);
   }

   public SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4) {
      return this.delegate().subMultiset(var1, var2, var3, var4);
   }

   public SortedMultiset<E> tailMultiset(E var1, BoundType var2) {
      return this.delegate().tailMultiset(var1, var2);
   }

   protected abstract class StandardDescendingMultiset extends DescendingMultiset<E> {
      public StandardDescendingMultiset() {
      }

      SortedMultiset<E> forwardMultiset() {
         return ForwardingSortedMultiset.this;
      }
   }

   protected class StandardElementSet extends SortedMultisets.NavigableElementSet<E> {
      public StandardElementSet() {
         super(ForwardingSortedMultiset.this);
      }
   }
}
