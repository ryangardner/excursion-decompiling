/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.mail.internet.MailDateFormat;

public class INTERNALDATE
implements Item {
    private static SimpleDateFormat df;
    private static MailDateFormat mailDateFormat;
    static final char[] name;
    protected Date date;
    public int msgno;

    static {
        name = new char[]{'I', 'N', 'T', 'E', 'R', 'N', 'A', 'L', 'D', 'A', 'T', 'E'};
        mailDateFormat = new MailDateFormat();
        df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss ", Locale.US);
    }

    public INTERNALDATE(FetchResponse object) throws ParsingException {
        this.msgno = ((IMAPResponse)object).getNumber();
        ((Response)object).skipSpaces();
        object = ((Response)object).readString();
        if (object == null) throw new ParsingException("INTERNALDATE is NIL");
        try {
            this.date = mailDateFormat.parse((String)object);
            return;
        }
        catch (ParseException parseException) {
            throw new ParsingException("INTERNALDATE parse error");
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public static String format(Date date) {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat = df;
        synchronized (simpleDateFormat) {
            SimpleDateFormat simpleDateFormat2 = df;
            FieldPosition fieldPosition = new FieldPosition(0);
            simpleDateFormat2.format(date, stringBuffer, fieldPosition);
        }
        int n = -date.getTimezoneOffset();
        if (n < 0) {
            stringBuffer.append('-');
            n = -n;
        } else {
            stringBuffer.append('+');
        }
        int n2 = n / 60;
        stringBuffer.append(Character.forDigit(n2 / 10, 10));
        stringBuffer.append(Character.forDigit(n2 % 10, 10));
        stringBuffer.append(Character.forDigit((n %= 60) / 10, 10));
        stringBuffer.append(Character.forDigit(n % 10, 10));
        return stringBuffer.toString();
    }

    public Date getDate() {
        return this.date;
    }
}

