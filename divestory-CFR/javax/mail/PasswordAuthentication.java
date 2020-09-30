/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

public final class PasswordAuthentication {
    private String password;
    private String userName;

    public PasswordAuthentication(String string2, String string3) {
        this.userName = string2;
        this.password = string3;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.userName;
    }
}

