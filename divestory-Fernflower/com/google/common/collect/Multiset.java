package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface Multiset<E> extends Collection<E> {
   int add(@NullableDecl E var1, int var2);

   boolean add(E var1);

   boolean contains(@NullableDecl Object var1);

   boolean containsAll(Collection<?> var1);

   int count(@NullableDecl Object var1);

   Set<E> elementSet();

   Set<Multiset.Entry<E>> entrySet();

   boolean equals(@NullableDecl Object var1);

   int hashCode();

   Iterator<E> iterator();

   int remove(@NullableDecl Object var1, int var2);

   boolean remove(@NullableDecl Object var1);

   boolean removeAll(Collection<?> var1);

   boolean retainAll(Collection<?> var1);

   int setCount(E var1, int var2);

   boolean setCount(E var1, int var2, int var3);

   int size();

   String toString();

   public interface Entry<E> {
      boolean equals(Object var1);

      int getCount();

      E getElement();

      int hashCode();

      String toString();
   }
}
