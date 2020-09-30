/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

public class BasicListHeaderIterator
implements HeaderIterator {
    protected final List allHeaders;
    protected int currentIndex;
    protected String headerName;
    protected int lastIndex;

    public BasicListHeaderIterator(List list, String string2) {
        if (list == null) throw new IllegalArgumentException("Header list must not be null.");
        this.allHeaders = list;
        this.headerName = string2;
        this.currentIndex = this.findNext(-1);
        this.lastIndex = -1;
    }

    protected boolean filterHeader(int n) {
        if (this.headerName == null) {
            return true;
        }
        String string2 = ((Header)this.allHeaders.get(n)).getName();
        return this.headerName.equalsIgnoreCase(string2);
    }

    protected int findNext(int n) {
        int n2 = -1;
        if (n < -1) {
            return -1;
        }
        int n3 = this.allHeaders.size();
        boolean bl = false;
        while (!bl && n < n3 - 1) {
            bl = this.filterHeader(++n);
        }
        if (!bl) return n2;
        return n;
    }

    @Override
    public boolean hasNext() {
        if (this.currentIndex < 0) return false;
        return true;
    }

    public final Object next() throws NoSuchElementException {
        return this.nextHeader();
    }

    @Override
    public Header nextHeader() throws NoSuchElementException {
        int n = this.currentIndex;
        if (n < 0) throw new NoSuchElementException("Iteration already finished.");
        this.lastIndex = n;
        this.currentIndex = this.findNext(n);
        return (Header)this.allHeaders.get(n);
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        int n = this.lastIndex;
        if (n < 0) throw new IllegalStateException("No header to remove.");
        this.allHeaders.remove(n);
        this.lastIndex = -1;
        --this.currentIndex;
    }
}

