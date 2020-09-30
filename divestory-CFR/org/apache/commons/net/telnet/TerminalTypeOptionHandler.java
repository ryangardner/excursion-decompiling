/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import org.apache.commons.net.telnet.TelnetOptionHandler;

public class TerminalTypeOptionHandler
extends TelnetOptionHandler {
    protected static final int TERMINAL_TYPE = 24;
    protected static final int TERMINAL_TYPE_IS = 0;
    protected static final int TERMINAL_TYPE_SEND = 1;
    private final String termType;

    public TerminalTypeOptionHandler(String string2) {
        super(24, false, false, false, false);
        this.termType = string2;
    }

    public TerminalTypeOptionHandler(String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(24, bl, bl2, bl3, bl4);
        this.termType = string2;
    }

    @Override
    public int[] answerSubnegotiation(int[] arrn, int n) {
        if (arrn == null) return null;
        if (n <= 1) return null;
        String string2 = this.termType;
        if (string2 == null) return null;
        n = 0;
        if (arrn[0] != 24) return null;
        if (arrn[1] != 1) return null;
        arrn = new int[string2.length() + 2];
        arrn[0] = 24;
        arrn[1] = 0;
        while (n < this.termType.length()) {
            arrn[n + 2] = this.termType.charAt(n);
            ++n;
        }
        return arrn;
    }
}

