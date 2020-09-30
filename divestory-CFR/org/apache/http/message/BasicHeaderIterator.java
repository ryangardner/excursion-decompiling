/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

public class BasicHeaderIterator
implements HeaderIterator {
    protected final Header[] allHeaders;
    protected int currentIndex;
    protected String headerName;

    public BasicHeaderIterator(Header[] arrheader, String string2) {
        if (arrheader == null) throw new IllegalArgumentException("Header array must not be null.");
        this.allHeaders = arrheader;
        this.headerName = string2;
        this.currentIndex = this.findNext(-1);
    }

    protected boolean filterHeader(int n) {
        String string2 = this.headerName;
        if (string2 == null) return true;
        if (string2.equalsIgnoreCase(this.allHeaders[n].getName())) return true;
        return false;
    }

    protected int findNext(int n) {
        int n2 = -1;
        if (n < -1) {
            return -1;
        }
        int n3 = this.allHeaders.length;
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
        this.currentIndex = this.findNext(n);
        return this.allHeaders[n];
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }
}

