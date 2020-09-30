/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.IntegerComparisonTerm;

public final class SizeTerm
extends IntegerComparisonTerm {
    private static final long serialVersionUID = -2556219451005103709L;

    public SizeTerm(int n, int n2) {
        super(n, n2);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SizeTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message message) {
        try {
            int n = message.getSize();
            if (n != -1) return super.match(n);
            return false;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

