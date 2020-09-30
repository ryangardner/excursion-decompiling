/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.io.IOException;

public final class SMTPConnectionClosedException
extends IOException {
    private static final long serialVersionUID = 626520434326660627L;

    public SMTPConnectionClosedException() {
    }

    public SMTPConnectionClosedException(String string2) {
        super(string2);
    }
}

