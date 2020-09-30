/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPAddress;
import com.sun.mail.imap.protocol.Item;
import java.util.Date;
import java.util.Vector;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;

public class ENVELOPE
implements Item {
    private static MailDateFormat mailDateFormat;
    static final char[] name;
    public InternetAddress[] bcc;
    public InternetAddress[] cc;
    public Date date = null;
    public InternetAddress[] from;
    public String inReplyTo;
    public String messageId;
    public int msgno;
    public InternetAddress[] replyTo;
    public InternetAddress[] sender;
    public String subject;
    public InternetAddress[] to;

    static {
        name = new char[]{'E', 'N', 'V', 'E', 'L', 'O', 'P', 'E'};
        mailDateFormat = new MailDateFormat();
    }

    public ENVELOPE(FetchResponse fetchResponse) throws ParsingException {
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        if (fetchResponse.readByte() != 40) throw new ParsingException("ENVELOPE parse error");
        String string2 = fetchResponse.readString();
        if (string2 != null) {
            try {
                this.date = mailDateFormat.parse(string2);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.subject = fetchResponse.readString();
        this.from = this.parseAddressList(fetchResponse);
        this.sender = this.parseAddressList(fetchResponse);
        this.replyTo = this.parseAddressList(fetchResponse);
        this.to = this.parseAddressList(fetchResponse);
        this.cc = this.parseAddressList(fetchResponse);
        this.bcc = this.parseAddressList(fetchResponse);
        this.inReplyTo = fetchResponse.readString();
        this.messageId = fetchResponse.readString();
        if (fetchResponse.readByte() != 41) throw new ParsingException("ENVELOPE parse error");
    }

    private InternetAddress[] parseAddressList(Response arrobject) throws ParsingException {
        arrobject.skipSpaces();
        byte by = arrobject.readByte();
        if (by == 40) {
            Vector<IMAPAddress> vector = new Vector<IMAPAddress>();
            do {
                IMAPAddress iMAPAddress;
                if ((iMAPAddress = new IMAPAddress((Response)arrobject)).isEndOfGroup()) continue;
                vector.addElement(iMAPAddress);
            } while (arrobject.peekByte() != 41);
            arrobject.skip(1);
            arrobject = new InternetAddress[vector.size()];
            vector.copyInto(arrobject);
            return arrobject;
        }
        if (by != 78) {
            if (by != 110) throw new ParsingException("ADDRESS parse error");
        }
        arrobject.skip(2);
        return null;
    }
}

