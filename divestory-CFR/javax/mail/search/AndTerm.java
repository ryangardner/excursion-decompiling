/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class AndTerm
extends SearchTerm {
    private static final long serialVersionUID = -3583274505380989582L;
    protected SearchTerm[] terms;

    public AndTerm(SearchTerm searchTerm, SearchTerm searchTerm2) {
        SearchTerm[] arrsearchTerm = new SearchTerm[2];
        this.terms = arrsearchTerm;
        arrsearchTerm[0] = searchTerm;
        arrsearchTerm[1] = searchTerm2;
    }

    public AndTerm(SearchTerm[] arrsearchTerm) {
        this.terms = new SearchTerm[arrsearchTerm.length];
        int n = 0;
        while (n < arrsearchTerm.length) {
            this.terms[n] = arrsearchTerm[n];
            ++n;
        }
        return;
    }

    public boolean equals(Object object) {
        if (!(object instanceof AndTerm)) {
            return false;
        }
        object = (AndTerm)object;
        if (((AndTerm)object).terms.length != this.terms.length) {
            return false;
        }
        int n = 0;
        SearchTerm[] arrsearchTerm;
        while (n < (arrsearchTerm = this.terms).length) {
            if (!arrsearchTerm[n].equals(((AndTerm)object).terms[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public SearchTerm[] getTerms() {
        return (SearchTerm[])this.terms.clone();
    }

    public int hashCode() {
        int n = 0;
        int n2 = 0;
        SearchTerm[] arrsearchTerm;
        while (n < (arrsearchTerm = this.terms).length) {
            n2 += arrsearchTerm[n].hashCode();
            ++n;
        }
        return n2;
    }

    @Override
    public boolean match(Message message) {
        int n = 0;
        SearchTerm[] arrsearchTerm;
        while (n < (arrsearchTerm = this.terms).length) {
            if (!arrsearchTerm[n].match(message)) {
                return false;
            }
            ++n;
        }
        return true;
    }
}

