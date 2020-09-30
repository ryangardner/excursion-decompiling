/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.search.StringTerm;

public final class BodyTerm
extends StringTerm {
    private static final long serialVersionUID = -4888862527916911385L;

    public BodyTerm(String string2) {
        super(string2);
    }

    private boolean matchPart(Part object) {
        try {
            if (object.isMimeType("text/*")) {
                if ((object = (String)object.getContent()) != null) return super.match((String)object);
                return false;
            }
            if (!object.isMimeType("multipart/*")) {
                if (!object.isMimeType("message/rfc822")) return false;
                return this.matchPart((Part)object.getContent());
            }
            object = (Multipart)object.getContent();
            int n = ((Multipart)object).getCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return false;
                }
                if (this.matchPart(((Multipart)object).getBodyPart(n2))) {
                    return true;
                }
                ++n2;
            } while (true);
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BodyTerm) return super.equals(object);
        return false;
    }

    @Override
    public boolean match(Message message) {
        return this.matchPart(message);
    }
}

