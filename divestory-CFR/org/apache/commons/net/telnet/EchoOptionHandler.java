/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import org.apache.commons.net.telnet.TelnetOptionHandler;

public class EchoOptionHandler
extends TelnetOptionHandler {
    public EchoOptionHandler() {
        super(1, false, false, false, false);
    }

    public EchoOptionHandler(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(1, bl, bl2, bl3, bl4);
    }
}

