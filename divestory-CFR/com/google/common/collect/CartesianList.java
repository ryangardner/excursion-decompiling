/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CartesianList<E>
extends AbstractList<List<E>>
implements RandomAccess {
    private final transient ImmutableList<List<E>> axes;
    private final transient int[] axesSizeProduct;

    CartesianList(ImmutableList<List<E>> immutableList) {
        this.axes = immutableList;
        int[] arrn = new int[immutableList.size() + 1];
        arrn[immutableList.size()] = 1;
        try {
            for (int i = immutableList.size() - 1; i >= 0; --i) {
                arrn[i] = IntMath.checkedMultiply(arrn[i + 1], ((List)immutableList.get(i)).size());
            }
        }
        catch (ArithmeticException arithmeticException) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
        this.axesSizeProduct = arrn;
    }

    static <E> List<List<E>> create(List<? extends List<? extends E>> list) {
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        Iterator<List<E>> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            list = ImmutableList.copyOf(iterator2.next());
            if (list.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add(list);
        }
        return new CartesianList<E>((ImmutableList<List<E>>)builder.build());
    }

    private int getAxisIndexForProductIndex(int n, int n2) {
        return n / this.axesSizeProduct[n2 + 1] % ((List)this.axes.get(n2)).size();
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (this.indexOf(object) == -1) return false;
        return true;
    }

    @Override
    public ImmutableList<E> get(final int n) {
        Preconditions.checkElementIndex(n, this.size());
        return new ImmutableList<E>(){

            @Override
            public E get(int n3) {
                Preconditions.checkElementIndex(n3, this.size());
                int n2 = CartesianList.this.getAxisIndexForProductIndex(n, n3);
                return ((List)CartesianList.this.axes.get(n3)).get(n2);
            }

            @Override
            boolean isPartialView() {
                return true;
            }

            @Override
            public int size() {
                return CartesianList.this.axes.size();
            }
        };
    }

    @Override
    public int indexOf(Object listIterator) {
        if (!(listIterator instanceof List)) {
            return -1;
        }
        if ((listIterator = (List)((Object)listIterator)).size() != this.axes.size()) {
            return -1;
        }
        listIterator = listIterator.listIterator();
        int n = 0;
        while (listIterator.hasNext()) {
            int n2 = listIterator.nextIndex();
            int n3 = ((List)this.axes.get(n2)).indexOf(listIterator.next());
            if (n3 == -1) {
                return -1;
            }
            n += n3 * this.axesSizeProduct[n2 + 1];
        }
        return n;
    }

    @Override
    public int size() {
        return this.axesSizeProduct[0];
    }

}

