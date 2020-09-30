/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.POP3Store;
import javax.mail.Session;
import javax.mail.URLName;

public class POP3SSLStore
extends POP3Store {
    public POP3SSLStore(Session session, URLName uRLName) {
        super(session, uRLName, "pop3s", 995, true);
    }
}

