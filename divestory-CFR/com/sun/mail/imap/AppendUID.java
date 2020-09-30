/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

public class AppendUID {
    public long uid = -1L;
    public long uidvalidity = -1L;

    public AppendUID(long l, long l2) {
        this.uidvalidity = l;
        this.uid = l2;
    }
}

