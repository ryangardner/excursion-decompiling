/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

public class ContentDisposition {
    private String disposition;
    private ParameterList list;

    public ContentDisposition() {
    }

    public ContentDisposition(String object) throws ParseException {
        object = new HeaderTokenizer((String)object, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token token = ((HeaderTokenizer)object).next();
        if (token.getType() != -1) throw new ParseException();
        this.disposition = token.getValue();
        if ((object = ((HeaderTokenizer)object).getRemainder()) == null) return;
        this.list = new ParameterList((String)object);
    }

    public ContentDisposition(String string2, ParameterList parameterList) {
        this.disposition = string2;
        this.list = parameterList;
    }

    public String getDisposition() {
        return this.disposition;
    }

    public String getParameter(String string2) {
        ParameterList parameterList = this.list;
        if (parameterList != null) return parameterList.get(string2);
        return null;
    }

    public ParameterList getParameterList() {
        return this.list;
    }

    public void setDisposition(String string2) {
        this.disposition = string2;
    }

    public void setParameter(String string2, String string3) {
        if (this.list == null) {
            this.list = new ParameterList();
        }
        this.list.set(string2, string3);
    }

    public void setParameterList(ParameterList parameterList) {
        this.list = parameterList;
    }

    public String toString() {
        CharSequence charSequence = this.disposition;
        if (charSequence == null) {
            return null;
        }
        if (this.list == null) {
            return charSequence;
        }
        charSequence = new StringBuffer(this.disposition);
        ((StringBuffer)charSequence).append(this.list.toString(((StringBuffer)charSequence).length() + 21));
        return ((StringBuffer)charSequence).toString();
    }
}

