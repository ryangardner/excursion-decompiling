/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressTerm;

public final class RecipientTerm
extends AddressTerm {
    private static final long serialVersionUID = 6548700653122680468L;
    protected Message.RecipientType type;

    public RecipientTerm(Message.RecipientType recipientType, Address address) {
        super(address);
        this.type = recipientType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecipientTerm)) {
            return false;
        }
        if (!((RecipientTerm)object).type.equals(this.type)) return false;
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

