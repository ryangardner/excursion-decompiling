/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

public class InvalidTelnetOptionException
extends Exception {
    private static final long serialVersionUID = -2516777155928793597L;
    private final String msg;
    private final int optionCode;

    public InvalidTelnetOptionException(String string2, int n) {
        this.optionCode = n;
        this.msg = string2;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.msg);
        stringBuilder.append(": ");
        stringBuilder.append(this.optionCode);
        return stringBuilder.toString();
    }
}

