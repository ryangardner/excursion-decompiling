/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPBodyPart;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.util.Vector;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

public class IMAPMultipartDataSource
extends MimePartDataSource
implements MultipartDataSource {
    private Vector parts;

    protected IMAPMultipartDataSource(MimePart object, BODYSTRUCTURE[] arrbODYSTRUCTURE, String string2, IMAPMessage iMAPMessage) {
        super((MimePart)object);
        this.parts = new Vector(arrbODYSTRUCTURE.length);
        int n = 0;
        while (n < arrbODYSTRUCTURE.length) {
            Vector vector = this.parts;
            BODYSTRUCTURE bODYSTRUCTURE = arrbODYSTRUCTURE[n];
            if (string2 == null) {
                object = Integer.toString(n + 1);
            } else {
                object = new StringBuilder(String.valueOf(string2));
                ((StringBuilder)object).append(".");
                ((StringBuilder)object).append(Integer.toString(n + 1));
                object = ((StringBuilder)object).toString();
            }
            vector.addElement(new IMAPBodyPart(bODYSTRUCTURE, (String)object, iMAPMessage));
            ++n;
        }
        return;
    }

    @Override
    public BodyPart getBodyPart(int n) throws MessagingException {
        return (BodyPart)this.parts.elementAt(n);
    }

    @Override
    public int getCount() {
        return this.parts.size();
    }
}

