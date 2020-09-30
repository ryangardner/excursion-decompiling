/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface Multiset<E>
extends Collection<E> {
    public int add(@NullableDecl E var1, int var2);

    @Override
    public boolean add(E var1);

    @Override
    public boolean contains(@NullableDecl Object var1);

    @Override
    public boolean containsAll(Collection<?> var1);

    public int count(@NullableDecl Object var1);

    public Set<E> elementSet();

    public Set<Entry<E>> entrySet();

    @Override
    public boolean equals(@NullableDecl Object var1);

    @Override
    public int hashCode();

    @Override
    public Iterator<E> iterator();

    public int remove(@NullableDecl Object var1, int var2);

    @Override
    public boolean remove(@NullableDecl Object var1);

    @Override
    public boolean removeAll(Collection<?> var1);

    @Override
    public boolean retainAll(Collection<?> var1);

    public int setCount(E var1, int var2);

    public boolean setCount(E var1, int var2, int var3);

    @Override
    public int size();

    public String toString();

    public static interface Entry<E> {
        public boolean equals(Object var1);

        public int getCount();

        public E getElement();

        public int hashCode();

        public String toString();
    }

}

