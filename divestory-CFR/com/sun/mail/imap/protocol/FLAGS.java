/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import javax.mail.Flags;

public class FLAGS
extends Flags
implements Item {
    static final char[] name = new char[]{'F', 'L', 'A', 'G', 'S'};
    private static final long serialVersionUID = 439049847053756670L;
    public int msgno;

    public FLAGS(IMAPResponse arrstring) throws ParsingException {
        this.msgno = arrstring.getNumber();
        arrstring.skipSpaces();
        arrstring = arrstring.readSimpleList();
        if (arrstring == null) return;
        int n = 0;
        while (n < arrstring.length) {
            String string2 = arrstring[n];
            if (string2.length() >= 2 && string2.charAt(0) == '\\') {
                char c = Character.toUpperCase(string2.charAt(1));
                if (c != '*') {
                    if (c != 'A') {
                        if (c != 'D') {
                            if (c != 'F') {
                                if (c != 'R') {
                                    if (c != 'S') {
                                        this.add(string2);
                                    } else {
                                        this.add(Flags.Flag.SEEN);
                                    }
                                } else {
                                    this.add(Flags.Flag.RECENT);
                                }
                            } else {
                                this.add(Flags.Flag.FLAGGED);
                            }
                        } else if (string2.length() >= 3) {
                            c = string2.charAt(2);
                            if (c != 'e' && c != 'E') {
                                if (c == 'r' || c == 'R') {
                                    this.add(Flags.Flag.DRAFT);
                                }
                            } else {
                                this.add(Flags.Flag.DELETED);
                            }
                        } else {
                            this.add(string2);
                        }
                    } else {
                        this.add(Flags.Flag.ANSWERED);
                    }
                } else {
                    this.add(Flags.Flag.USER);
                }
            } else {
                this.add(string2);
            }
            ++n;
        }
        return;
    }
}

