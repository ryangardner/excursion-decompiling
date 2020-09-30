/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.StringTerm;

public final class SubjectTerm
extends StringTerm {
    private static final long serialVersionUID = 7481568618055573432L;

    public SubjectTerm(String string2) {
        super(string2);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SubjectTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message object) {
        try {
            object = ((Message)object).getSubject();
            if (object != null) return super.match((String)object);
            return false;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

