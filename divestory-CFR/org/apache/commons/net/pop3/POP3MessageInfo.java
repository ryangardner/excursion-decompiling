/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.pop3;

public final class POP3MessageInfo {
    public String identifier;
    public int number;
    public int size;

    public POP3MessageInfo() {
        this(0, null, 0);
    }

    public POP3MessageInfo(int n, int n2) {
        this(n, null, n2);
    }

    public POP3MessageInfo(int n, String string2) {
        this(n, string2, -1);
    }

    private POP3MessageInfo(int n, String string2, int n2) {
        this.number = n;
        this.size = n2;
        this.identifier = string2;
    }
}

