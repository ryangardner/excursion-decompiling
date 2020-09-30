/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

public class Header {
    protected String name;
    protected String value;

    public Header(String string2, String string3) {
        this.name = string2;
        this.value = string3;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}

