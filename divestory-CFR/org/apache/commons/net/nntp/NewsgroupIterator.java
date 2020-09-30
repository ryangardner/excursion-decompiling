/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.util.Iterator;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;

class NewsgroupIterator
implements Iterator<NewsgroupInfo>,
Iterable<NewsgroupInfo> {
    private final Iterator<String> stringIterator;

    public NewsgroupIterator(Iterable<String> iterable) {
        this.stringIterator = iterable.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.stringIterator.hasNext();
    }

    @Override
    public Iterator<NewsgroupInfo> iterator() {
        return this;
    }

    @Override
    public NewsgroupInfo next() {
        return NNTPClient.__parseNewsgroupListEntry(this.stringIterator.next());
    }

    @Override
    public void remove() {
        this.stringIterator.remove();
    }
}

