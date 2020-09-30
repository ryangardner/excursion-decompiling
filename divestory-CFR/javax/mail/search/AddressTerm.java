/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.search.SearchTerm;

public abstract class AddressTerm
extends SearchTerm {
    private static final long serialVersionUID = 2005405551929769980L;
    protected Address address;

    protected AddressTerm(Address address) {
        this.address = address;
    }

    public boolean equals(Object object) {
        if (object instanceof AddressTerm) return ((AddressTerm)object).address.equals(this.address);
        return false;
    }

    public Address getAddress() {
        return this.address;
    }

    public int hashCode() {
        return this.address.hashCode();
    }

    protected boolean match(Address address) {
        return address.equals(this.address);
    }
}

