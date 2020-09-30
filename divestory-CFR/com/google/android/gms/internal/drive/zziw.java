/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkp;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class zziw<E>
extends AbstractList<E>
implements zzkp<E> {
    private boolean zznh = true;

    zziw() {
    }

    @Override
    public void add(int n, E e) {
        this.zzbq();
        super.add(n, e);
    }

    @Override
    public boolean add(E e) {
        this.zzbq();
        return super.add(e);
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        this.zzbq();
        return super.addAll(n, collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        this.zzbq();
        return super.addAll(collection);
    }

    @Override
    public void clear() {
        this.zzbq();
        super.clear();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        if (!(object instanceof RandomAccess)) {
            return super.equals(object);
        }
        object = (List)object;
        int n = this.size();
        if (n != object.size()) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            if (!this.get(n2).equals(object.get(n2))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int n = this.size();
        int n2 = 1;
        int n3 = 0;
        while (n3 < n) {
            n2 = n2 * 31 + this.get(n3).hashCode();
            ++n3;
        }
        return n2;
    }

    @Override
    public E remove(int n) {
        this.zzbq();
        return super.remove(n);
    }

    @Override
    public boolean remove(Object object) {
        this.zzbq();
        return super.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.zzbq();
        return super.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.zzbq();
        return super.retainAll(collection);
    }

    @Override
    public E set(int n, E e) {
        this.zzbq();
        return super.set(n, e);
    }

    @Override
    public boolean zzbo() {
        return this.zznh;
    }

    @Override
    public final void zzbp() {
        this.zznh = false;
    }

    protected final void zzbq() {
        if (!this.zznh) throw new UnsupportedOperationException();
    }
}

