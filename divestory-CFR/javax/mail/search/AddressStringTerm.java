/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.search.StringTerm;

public abstract class AddressStringTerm
extends StringTerm {
    private static final long serialVersionUID = 3086821234204980368L;

    protected AddressStringTerm(String string2) {
        super(string2, true);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AddressStringTerm) return super.equals(object);
        return false;
    }

    protected boolean match(Address address) {
        if (!(address instanceof InternetAddress)) return super.match(address.toString());
        return super.match(((InternetAddress)address).toUnicodeString());
    }
}

