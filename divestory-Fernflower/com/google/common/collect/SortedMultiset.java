package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E> {
   Comparator<? super E> comparator();

   SortedMultiset<E> descendingMultiset();

   NavigableSet<E> elementSet();

   Set<Multiset.Entry<E>> entrySet();

   Multiset.Entry<E> firstEntry();

   SortedMultiset<E> headMultiset(E var1, BoundType var2);

   Iterator<E> iterator();

   Multiset.Entry<E> lastEntry();

   Multiset.Entry<E> pollFirstEntry();

   Multiset.Entry<E> pollLastEntry();

   SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4);

   SortedMultiset<E> tailMultiset(E var1, BoundType var2);
}
