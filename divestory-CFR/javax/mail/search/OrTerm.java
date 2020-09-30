/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class OrTerm
extends SearchTerm {
    private static final long serialVersionUID = 5380534067523646936L;
    protected SearchTerm[] terms;

    public OrTerm(SearchTerm searchTerm, SearchTerm searchTerm2) {
        SearchTerm[] arrsearchTerm = new SearchTerm[2];
        this.terms = arrsearchTerm;
        arrsearchTerm[0] = searchTerm;
        arrsearchTerm[1] = searchTerm2;
    }

    public OrTerm(SearchTerm[] arrsearchTerm) {
        this.terms = new SearchTerm[arrsearchTerm.length];
        int n = 0;
        while (n < arrsearchTerm.length) {
            this.terms[n] = arrsearchTerm[n];
            ++n;
        }
        return;
    }

    public boolean equals(Object object) {
        if (!(object instanceof OrTerm)) {
            return false;
        }
        object = (OrTerm)object;
        if (((OrTerm)object).terms.length != this.terms.length) {
            return false;
        }
        int n = 0;
        SearchTerm[] arrsearchTerm;
        while (n < (arrsearchTerm = this.terms).length) {
            if (!arrsearchTerm[n].equals(((OrTerm)object).terms[n])) {
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
            if (arrsearchTerm[n].match(message)) {
                return true;
            }
            ++n;
        }
        return false;
    }
}

