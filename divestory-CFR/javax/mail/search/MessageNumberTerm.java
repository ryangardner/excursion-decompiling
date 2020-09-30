/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.IntegerComparisonTerm;

public final class MessageNumberTerm
extends IntegerComparisonTerm {
    private static final long serialVersionUID = -5379625829658623812L;

    public MessageNumberTerm(int n) {
        super(3, n);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof MessageNumberTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message message) {
        try {
            int n = message.getMessageNumber();
            return super.match(n);
        }
        catch (Exception exception) {
            return false;
        }
    }
}

