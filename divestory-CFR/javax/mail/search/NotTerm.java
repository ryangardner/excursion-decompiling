/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class NotTerm
extends SearchTerm {
    private static final long serialVersionUID = 7152293214217310216L;
    protected SearchTerm term;

    public NotTerm(SearchTerm searchTerm) {
        this.term = searchTerm;
    }

    public boolean equals(Object object) {
        if (object instanceof NotTerm) return ((NotTerm)object).term.equals(this.term);
        return false;
    }

    public SearchTerm getTerm() {
        return this.term;
    }

    public int hashCode() {
        return this.term.hashCode() << 1;
    }

    @Override
    public boolean match(Message message) {
        return this.term.match(message) ^ true;
    }
}

