/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

public class ContentType {
    private ParameterList list;
    private String primaryType;
    private String subType;

    public ContentType() {
    }

    public ContentType(String object) throws ParseException {
        object = new HeaderTokenizer((String)object, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token token = ((HeaderTokenizer)object).next();
        if (token.getType() != -1) throw new ParseException();
        this.primaryType = token.getValue();
        if ((char)((HeaderTokenizer)object).next().getType() != '/') throw new ParseException();
        token = ((HeaderTokenizer)object).next();
        if (token.getType() != -1) throw new ParseException();
        this.subType = token.getValue();
        if ((object = ((HeaderTokenizer)object).getRemainder()) == null) return;
        this.list = new ParameterList((String)object);
    }

    public ContentType(String string2, String string3, ParameterList parameterList) {
        this.primaryType = string2;
        this.subType = string3;
        this.list = parameterList;
    }

    public String getBaseType() {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.primaryType));
        stringBuilder.append('/');
        stringBuilder.append(this.subType);
        return stringBuilder.toString();
    }

    public String getParameter(String string2) {
        ParameterList parameterList = this.list;
        if (parameterList != null) return parameterList.get(string2);
        return null;
    }

    public ParameterList getParameterList() {
        return this.list;
    }

    public String getPrimaryType() {
        return this.primaryType;
    }

    public String getSubType() {
        return this.subType;
    }

    public boolean match(String string2) {
        try {
            ContentType contentType = new ContentType(string2);
            return this.match(contentType);
        }
        catch (ParseException parseException) {
            return false;
        }
    }

    public boolean match(ContentType object) {
        if (!this.primaryType.equalsIgnoreCase(((ContentType)object).getPrimaryType())) {
            return false;
        }
        object = ((ContentType)object).getSubType();
        if (this.subType.charAt(0) == '*') return true;
        if (((String)object).charAt(0) == '*') {
            return true;
        }
        if (this.subType.equalsIgnoreCase((String)object)) return true;
        return false;
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

    public void setPrimaryType(String string2) {
        this.primaryType = string2;
    }

    public void setSubType(String string2) {
        this.subType = string2;
    }

    public String toString() {
        if (this.primaryType == null) return null;
        if (this.subType == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.primaryType);
        stringBuffer.append('/');
        stringBuffer.append(this.subType);
        ParameterList parameterList = this.list;
        if (parameterList == null) return stringBuffer.toString();
        stringBuffer.append(parameterList.toString(stringBuffer.length() + 14));
        return stringBuffer.toString();
    }
}

