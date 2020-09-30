/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import java.util.Date;
import javax.mail.Message;
import javax.mail.search.DateTerm;

public final class SentDateTerm
extends DateTerm {
    private static final long serialVersionUID = 5647755030530907263L;

    public SentDateTerm(int n, Date date) {
        super(n, date);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SentDateTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message object) {
        try {
            object = ((Message)object).getSentDate();
            if (object != null) return super.match((Date)object);
            return false;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

