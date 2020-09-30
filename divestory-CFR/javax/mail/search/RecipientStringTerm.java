/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressStringTerm;

public final class RecipientStringTerm
extends AddressStringTerm {
    private static final long serialVersionUID = -8293562089611618849L;
    private Message.RecipientType type;

    public RecipientStringTerm(Message.RecipientType recipientType, String string2) {
        super(string2);
        this.type = recipientType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecipientStringTerm)) {
            return false;
        }
        if (!((RecipientStringTerm)object).type.equals(this.type)) return false;
        if (!super.equals(object)) return false;
        return true;
    }

    public Message.RecipientType getRecipientType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + super.hashCode();
    }

    @Override
    public boolean match(Message arraddress) {
        int n;
        try {
            arraddress = arraddress.getRecipients(this.type);
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

