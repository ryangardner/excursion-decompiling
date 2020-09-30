/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPStore;
import javax.mail.Session;
import javax.mail.URLName;

public class IMAPSSLStore
extends IMAPStore {
    public IMAPSSLStore(Session session, URLName uRLName) {
        super(session, uRLName, "imaps", 993, true);
    }
}

