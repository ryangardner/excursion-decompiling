/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import java.util.Date;
import javax.mail.Message;
import javax.mail.search.DateTerm;

public final class ReceivedDateTerm
extends DateTerm {
    private static final long serialVersionUID = -2756695246195503170L;

    public ReceivedDateTerm(int n, Date date) {
        super(n, date);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ReceivedDateTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message object) {
        try {
            object = ((Message)object).getReceivedDate();
            if (object != null) return super.match((Date)object);
            return false;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

