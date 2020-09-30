/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Session;
import javax.mail.URLName;

public class SMTPSSLTransport
extends SMTPTransport {
    public SMTPSSLTransport(Session session, URLName uRLName) {
        super(session, uRLName, "smtps", 465, true);
    }
}

