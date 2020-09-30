/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import org.apache.commons.net.telnet.TelnetOptionHandler;

public class SimpleOptionHandler
extends TelnetOptionHandler {
    public SimpleOptionHandler(int n) {
        super(n, false, false, false, false);
    }

    public SimpleOptionHandler(int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(n, bl, bl2, bl3, bl4);
    }
}

