/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import java.util.Locale;
import javax.mail.Message;
import javax.mail.search.StringTerm;

public final class HeaderTerm
extends StringTerm {
    private static final long serialVersionUID = 8342514650333389122L;
    protected String headerName;

    public HeaderTerm(String string2, String string3) {
        super(string3);
        this.headerName = string2;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HeaderTerm)) {
            return false;
        }
        object = (HeaderTerm)object;
        if (!((HeaderTerm)object).headerName.equalsIgnoreCase(this.headerName)) return false;
        if (!super.equals(object)) return false;
        return true;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    @Override
    public int hashCode() {
        return this.headerName.toLowerCase(Locale.ENGLISH).hashCode() + super.hashCode();
    }

    @Override
    public boolean match(Message arrstring) {
        int n;
        try {
            arrstring = arrstring.getHeader(this.headerName);
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

