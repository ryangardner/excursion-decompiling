/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;

public class Status {
    static final String[] standardItems = new String[]{"MESSAGES", "RECENT", "UNSEEN", "UIDNEXT", "UIDVALIDITY"};
    public String mbox = null;
    public int recent = -1;
    public int total = -1;
    public long uidnext = -1L;
    public long uidvalidity = -1L;
    public int unseen = -1;

    public Status(Response response) throws ParsingException {
        this.mbox = response.readAtomString();
        response.skipSpaces();
        if (response.readByte() != 40) throw new ParsingException("parse error in STATUS");
        do {
            String string2;
            if ((string2 = response.readAtom()).equalsIgnoreCase("MESSAGES")) {
                this.total = response.readNumber();
                continue;
            }
            if (string2.equalsIgnoreCase("RECENT")) {
                this.recent = response.readNumber();
                continue;
            }
            if (string2.equalsIgnoreCase("UIDNEXT")) {
                this.uidnext = response.readLong();
                continue;
            }
            if (string2.equalsIgnoreCase("UIDVALIDITY")) {
                this.uidvalidity = response.readLong();
                continue;
            }
            if (!string2.equalsIgnoreCase("UNSEEN")) continue;
            this.unseen = response.readNumber();
        } while (response.readByte() != 41);
    }

    public static void add(Status status, Status status2) {
        long l;
        int n = status2.total;
        if (n != -1) {
            status.total = n;
        }
        if ((n = status2.recent) != -1) {
            status.recent = n;
        }
        if ((l = status2.uidnext) != -1L) {
            status.uidnext = l;
        }
        if ((l = status2.uidvalidity) != -1L) {
            status.uidvalidity = l;
        }
        if ((n = status2.unseen) == -1) return;
        status.unseen = n;
    }
}

