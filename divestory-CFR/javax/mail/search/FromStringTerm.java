/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressStringTerm;

public final class FromStringTerm
extends AddressStringTerm {
    private static final long serialVersionUID = 5801127523826772788L;

    public FromStringTerm(String string2) {
        super(string2);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof FromStringTerm) return super.equals(object);
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

