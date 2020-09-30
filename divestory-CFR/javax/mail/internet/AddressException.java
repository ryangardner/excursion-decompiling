/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import javax.mail.internet.ParseException;

public class AddressException
extends ParseException {
    private static final long serialVersionUID = 9134583443539323120L;
    protected int pos = -1;
    protected String ref = null;

    public AddressException() {
    }

    public AddressException(String string2) {
        super(string2);
    }

    public AddressException(String string2, String string3) {
        super(string2);
        this.ref = string3;
    }

    public AddressException(String string2, String string3, int n) {
        super(string2);
        this.ref = string3;
        this.pos = n;
    }

    public int getPos() {
        return this.pos;
    }

    public String getRef() {
        return this.ref;
    }

    @Override
    public String toString() {
        CharSequence charSequence = super.toString();
        if (this.ref == null) {
            return charSequence;
        }
        charSequence = new StringBuilder(String.valueOf(charSequence));
        ((StringBuilder)charSequence).append(" in string ``");
        ((StringBuilder)charSequence).append(this.ref);
        ((StringBuilder)charSequence).append("''");
        charSequence = ((StringBuilder)charSequence).toString();
        if (this.pos < 0) {
            return charSequence;
        }
        charSequence = new StringBuilder(String.valueOf(charSequence));
        ((StringBuilder)charSequence).append(" at position ");
        ((StringBuilder)charSequence).append(this.pos);
        return ((StringBuilder)charSequence).toString();
    }
}

