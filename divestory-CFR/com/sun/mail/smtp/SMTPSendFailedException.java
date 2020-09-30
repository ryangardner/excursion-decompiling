/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPSendFailedException
extends SendFailedException {
    private static final long serialVersionUID = 8049122628728932894L;
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;

    public SMTPSendFailedException(String string2, int n, String string3, Exception exception, Address[] arraddress, Address[] arraddress2, Address[] arraddress3) {
        super(string3, exception, arraddress, arraddress2, arraddress3);
        this.cmd = string2;
        this.rc = n;
    }

    public String getCommand() {
        return this.cmd;
    }

    public int getReturnCode() {
        return this.rc;
    }
}

