/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressSucceededException
extends MessagingException {
    private static final long serialVersionUID = -1168335848623096749L;
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;

    public SMTPAddressSucceededException(InternetAddress internetAddress, String string2, int n, String string3) {
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

