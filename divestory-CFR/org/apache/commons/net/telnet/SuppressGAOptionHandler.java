/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import org.apache.commons.net.telnet.TelnetOptionHandler;

public class SuppressGAOptionHandler
extends TelnetOptionHandler {
    public SuppressGAOptionHandler() {
        super(3, false, false, false, false);
    }

    public SuppressGAOptionHandler(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(3, bl, bl2, bl3, bl4);
    }
}

