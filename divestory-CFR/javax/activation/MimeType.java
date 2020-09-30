/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Locale;
import javax.activation.MimeTypeParameterList;
import javax.activation.MimeTypeParseException;

public class MimeType
implements Externalizable {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
    private MimeTypeParameterList parameters;
    private String primaryType;
    private String subType;

    public MimeType() {
        this.primaryType = "application";
        this.subType = "*";
        this.parameters = new MimeTypeParameterList();
    }

    public MimeType(String string2) throws MimeTypeParseException {
        this.parse(string2);
    }

    public MimeType(String string2, String string3) throws MimeTypeParseException {
        if (!this.isValidToken(string2)) throw new MimeTypeParseException("Primary type is invalid.");
        this.primaryType = string2.toLowerCase(Locale.ENGLISH);
        if (!this.isValidToken(string3)) throw new MimeTypeParseException("Sub type is invalid.");
        this.subType = string3.toLowerCase(Locale.ENGLISH);
        this.parameters = new MimeTypeParameterList();
    }

    private static boolean isTokenChar(char c) {
        if (c <= ' ') return false;
        if (c >= '') return false;
        if (TSPECIALS.indexOf(c) >= 0) return false;
        return true;
    }

    private boolean isValidToken(String string2) {
        int n = string2.length();
        if (n <= 0) return false;
        int n2 = 0;
        while (n2 < n) {
            if (!MimeType.isTokenChar(string2.charAt(n2))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    private void parse(String string2) throws MimeTypeParseException {
        int n = string2.indexOf(47);
        int n2 = string2.indexOf(59);
        if (n < 0) {
            if (n2 < 0) throw new MimeTypeParseException("Unable to find a sub type.");
        }
        if (n < 0) {
            if (n2 >= 0) throw new MimeTypeParseException("Unable to find a sub type.");
        }
        if (n >= 0 && n2 < 0) {
            this.primaryType = string2.substring(0, n).trim().toLowerCase(Locale.ENGLISH);
            this.subType = string2.substring(n + 1).trim().toLowerCase(Locale.ENGLISH);
            this.parameters = new MimeTypeParameterList();
        } else {
            if (n >= n2) throw new MimeTypeParseException("Unable to find a sub type.");
            this.primaryType = string2.substring(0, n).trim().toLowerCase(Locale.ENGLISH);
            this.subType = string2.substring(n + 1, n2).trim().toLowerCase(Locale.ENGLISH);
            this.parameters = new MimeTypeParameterList(string2.substring(n2));
        }
        if (!this.isValidToken(this.primaryType)) throw new MimeTypeParseException("Primary type is invalid.");
        if (!this.isValidToken(this.subType)) throw new MimeTypeParseException("Sub type is invalid.");
    }

    public String getBaseType() {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.primaryType));
        stringBuilder.append("/");
        stringBuilder.append(this.subType);
        return stringBuilder.toString();
    }

    public String getParameter(String string2) {
        return this.parameters.get(string2);
    }

    public MimeTypeParameterList getParameters() {
        return this.parameters;
    }

    public String getPrimaryType() {
        return this.primaryType;
    }

    public String getSubType() {
        return this.subType;
    }

    public boolean match(String string2) throws MimeTypeParseException {
        return this.match(new MimeType(string2));
    }

    public boolean match(MimeType mimeType) {
        if (!this.primaryType.equals(mimeType.getPrimaryType())) return false;
        if (this.subType.equals("*")) return true;
        if (mimeType.getSubType().equals("*")) return true;
        if (!this.subType.equals(mimeType.getSubType())) return false;
        return true;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        try {
            this.parse(objectInput.readUTF());
            return;
        }
        catch (MimeTypeParseException mimeTypeParseException) {
            throw new IOException(mimeTypeParseException.toString());
        }
    }

    public void removeParameter(String string2) {
        this.parameters.remove(string2);
    }

    public void setParameter(String string2, String string3) {
        this.parameters.set(string2, string3);
    }

    public void setPrimaryType(String string2) throws MimeTypeParseException {
        if (!this.isValidToken(this.primaryType)) throw new MimeTypeParseException("Primary type is invalid.");
        this.primaryType = string2.toLowerCase(Locale.ENGLISH);
    }

    public void setSubType(String string2) throws MimeTypeParseException {
        if (!this.isValidToken(this.subType)) throw new MimeTypeParseException("Sub type is invalid.");
        this.subType = string2.toLowerCase(Locale.ENGLISH);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.getBaseType()));
        stringBuilder.append(this.parameters.toString());
        return stringBuilder.toString();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(this.toString());
        objectOutput.flush();
    }
}

