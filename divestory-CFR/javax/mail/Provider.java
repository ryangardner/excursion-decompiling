/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

public class Provider {
    private String className;
    private String protocol;
    private Type type;
    private String vendor;
    private String version;

    public Provider(Type type, String string2, String string3, String string4, String string5) {
        this.type = type;
        this.protocol = string2;
        this.className = string3;
        this.vendor = string4;
        this.version = string5;
    }

    public String getClassName() {
        return this.className;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public Type getType() {
        return this.type;
    }

    public String getVendor() {
        return this.vendor;
    }

    public String getVersion() {
        return this.version;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder("javax.mail.Provider[");
        ((StringBuilder)charSequence).append(this.type);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(this.protocol);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(this.className);
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.vendor != null) {
            charSequence = new StringBuilder(String.valueOf(charSequence2));
            ((StringBuilder)charSequence).append(",");
            ((StringBuilder)charSequence).append(this.vendor);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.version != null) {
            charSequence = new StringBuilder(String.valueOf(charSequence));
            ((StringBuilder)charSequence).append(",");
            ((StringBuilder)charSequence).append(this.version);
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder(String.valueOf(charSequence2));
        ((StringBuilder)charSequence).append("]");
        return ((StringBuilder)charSequence).toString();
    }

    public static class Type {
        public static final Type STORE = new Type("STORE");
        public static final Type TRANSPORT = new Type("TRANSPORT");
        private String type;

        private Type(String string2) {
            this.type = string2;
        }

        public String toString() {
            return this.type;
        }
    }

}

