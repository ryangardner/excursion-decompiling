/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.util.Iterator;
import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;

class ArticleIterator
implements Iterator<Article>,
Iterable<Article> {
    private final Iterator<String> stringIterator;

    public ArticleIterator(Iterable<String> iterable) {
        this.stringIterator = iterable.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.stringIterator.hasNext();
    }

    @Override
    public Iterator<Article> iterator() {
        return this;
    }

    @Override
    public Article next() {
        return NNTPClient.__parseArticleEntry(this.stringIterator.next());
    }

    @Override
    public void remove() {
        this.stringIterator.remove();
    }
}

