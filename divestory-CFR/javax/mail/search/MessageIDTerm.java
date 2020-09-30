/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.StringTerm;

public final class MessageIDTerm
extends StringTerm {
    private static final long serialVersionUID = -2121096296454691963L;

    public MessageIDTerm(String string2) {
        super(string2);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof MessageIDTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message arrstring) {
        int n;
        try {
            arrstring = arrstring.getHeader("Message-ID");
            if (arrstring == null) {
                return false;
            }
            n = 0;
        }
        catch (Exception exception) {
            return false;
        }
        while (n < arrstring.length) {
            if (super.match(arrstring[n])) {
                return true;
            }
            ++n;
        }
        return false;
    }
}

