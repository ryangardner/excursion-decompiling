/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Vector;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class IMAPAddress
extends InternetAddress {
    private static final long serialVersionUID = -3835822029483122232L;
    private boolean group = false;
    private InternetAddress[] grouplist;
    private String groupname;

    IMAPAddress(Response arrobject) throws ParsingException {
        IMAPAddress iMAPAddress;
        arrobject.skipSpaces();
        if (arrobject.readByte() != 40) throw new ParsingException("ADDRESS parse error");
        this.encodedPersonal = arrobject.readString();
        arrobject.readString();
        CharSequence charSequence = arrobject.readString();
        Object object = arrobject.readString();
        if (arrobject.readByte() != 41) throw new ParsingException("ADDRESS parse error");
        if (object == null) {
            this.group = true;
            this.groupname = charSequence;
            if (charSequence == null) {
                return;
            }
            charSequence = new StringBuffer();
            ((StringBuffer)charSequence).append(this.groupname);
            ((StringBuffer)charSequence).append(':');
            object = new Vector();
        } else {
            if (charSequence != null && ((String)charSequence).length() != 0) {
                if (((String)object).length() == 0) {
                    this.address = charSequence;
                    return;
                }
                arrobject = new StringBuilder(String.valueOf(charSequence));
                arrobject.append("@");
                arrobject.append((String)object);
                this.address = arrobject.toString();
                return;
            }
            this.address = object;
            return;
        }
        while (arrobject.peekByte() != 41 && !(iMAPAddress = new IMAPAddress((Response)arrobject)).isEndOfGroup()) {
            if (((Vector)object).size() != 0) {
                ((StringBuffer)charSequence).append(',');
            }
            ((StringBuffer)charSequence).append(iMAPAddress.toString());
            ((Vector)object).addElement(iMAPAddress);
        }
        ((StringBuffer)charSequence).append(';');
        this.address = ((StringBuffer)charSequence).toString();
        arrobject = new IMAPAddress[((Vector)object).size()];
        this.grouplist = arrobject;
        ((Vector)object).copyInto(arrobject);
    }

    @Override
    public InternetAddress[] getGroup(boolean bl) throws AddressException {
        InternetAddress[] arrinternetAddress = this.grouplist;
        if (arrinternetAddress != null) return (InternetAddress[])arrinternetAddress.clone();
        return null;
    }

    boolean isEndOfGroup() {
        if (!this.group) return false;
        if (this.groupname != null) return false;
        return true;
    }

    @Override
    public boolean isGroup() {
        return this.group;
    }
}

