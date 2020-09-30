/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressTerm;

public final class FromTerm
extends AddressTerm {
    private static final long serialVersionUID = 5214730291502658665L;

    public FromTerm(Address address) {
        super(address);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof FromTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message arraddress) {
        int n;
        try {
            arraddress = arraddress.getFrom();
            if (arraddress == null) {
                return false;
            }
            n = 0;
        }
        catch (Exception exception) {
            return false;
        }
        while (n < arraddress.length) {
            if (super.match(arraddress[n])) {
                return true;
            }
            ++n;
        }
        return false;
    }
}

