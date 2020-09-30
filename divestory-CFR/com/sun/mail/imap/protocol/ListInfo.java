/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.BASE64MailboxDecoder;
import com.sun.mail.imap.protocol.IMAPResponse;
import java.util.Vector;

public class ListInfo {
    public static final int CHANGED = 1;
    public static final int INDETERMINATE = 3;
    public static final int UNCHANGED = 2;
    public String[] attrs;
    public boolean canOpen = true;
    public int changeState = 3;
    public boolean hasInferiors = true;
    public String name = null;
    public char separator = (char)47;

    public ListInfo(IMAPResponse object) throws ParsingException {
        int n;
        Object[] arrobject = ((IMAPResponse)object).readSimpleList();
        Vector<Object> vector = new Vector<Object>();
        if (arrobject != null) {
            for (n = 0; n < arrobject.length; ++n) {
                if (arrobject[n].equalsIgnoreCase("\\Marked")) {
                    this.changeState = 1;
                } else if (((String)arrobject[n]).equalsIgnoreCase("\\Unmarked")) {
                    this.changeState = 2;
                } else if (((String)arrobject[n]).equalsIgnoreCase("\\Noselect")) {
                    this.canOpen = false;
                } else if (((String)arrobject[n]).equalsIgnoreCase("\\Noinferiors")) {
                    this.hasInferiors = false;
                }
                vector.addElement(arrobject[n]);
            }
        }
        arrobject = new String[vector.size()];
        this.attrs = arrobject;
        vector.copyInto(arrobject);
        ((Response)object).skipSpaces();
        if (((Response)object).readByte() == 34) {
            n = (char)((Response)object).readByte();
            this.separator = (char)n;
            if (n == 92) {
                this.separator = (char)((Response)object).readByte();
            }
            ((Response)object).skip(1);
        } else {
            ((Response)object).skip(2);
        }
        ((Response)object).skipSpaces();
        this.name = object = ((Response)object).readAtomString();
        this.name = BASE64MailboxDecoder.decode((String)object);
    }
}

