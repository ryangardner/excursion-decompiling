/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressFailedException
extends SendFailedException {
    private static final long serialVersionUID = 804831199768630097L;
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;

    public SMTPAddressFailedException(InternetAddress internetAddress, String string2, int n, String string3) {
        super(string3);
        this.addr = internetAddress;
        this.cmd = string2;
        this.rc = n;
    }

    public InternetAddress getAddress() {
        return this.addr;
    }

    public String getCommand() {
        return this.cmd;
    }

    public int getReturnCode() {
        return this.rc;
    }
}

