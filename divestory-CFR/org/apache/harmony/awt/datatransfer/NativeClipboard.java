/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.Clipboard;

public abstract class NativeClipboard
extends Clipboard {
    protected static final int OPS_TIMEOUT = 10000;

    public NativeClipboard(String string2) {
        super(string2);
    }

    public void onRestart() {
    }

    public void onShutdown() {
    }
}

