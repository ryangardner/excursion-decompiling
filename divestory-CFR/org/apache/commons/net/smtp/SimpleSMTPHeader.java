/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleSMTPHeader {
    private StringBuffer __cc;
    private final String __from;
    private final StringBuffer __headerFields;
    private final String __subject;
    private final String __to;
    private boolean hasHeaderDate;

    public SimpleSMTPHeader(String string2, String string3, String string4) {
        if (string2 == null) throw new IllegalArgumentException("From cannot be null");
        this.__to = string3;
        this.__from = string2;
        this.__subject = string4;
        this.__headerFields = new StringBuffer();
        this.__cc = null;
    }

    public void addCC(String string2) {
        StringBuffer stringBuffer = this.__cc;
        if (stringBuffer == null) {
            this.__cc = new StringBuffer();
        } else {
            stringBuffer.append(", ");
        }
        this.__cc.append(string2);
    }

    public void addHeaderField(String string2, String string3) {
        if (!this.hasHeaderDate && "Date".equals(string2)) {
            this.hasHeaderDate = true;
        }
        this.__headerFields.append(string2);
        this.__headerFields.append(": ");
        this.__headerFields.append(string3);
        this.__headerFields.append('\n');
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        if (!this.hasHeaderDate) {
            this.addHeaderField("Date", simpleDateFormat.format(new Date()));
        }
        if (this.__headerFields.length() > 0) {
            stringBuilder.append(this.__headerFields.toString());
        }
        stringBuilder.append("From: ");
        stringBuilder.append(this.__from);
        stringBuilder.append("\n");
        if (this.__to != null) {
            stringBuilder.append("To: ");
            stringBuilder.append(this.__to);
            stringBuilder.append("\n");
        }
        if (this.__cc != null) {
            stringBuilder.append("Cc: ");
            stringBuilder.append(this.__cc.toString());
            stringBuilder.append("\n");
        }
        if (this.__subject != null) {
            stringBuilder.append("Subject: ");
            stringBuilder.append(this.__subject);
            stringBuilder.append("\n");
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }
}

