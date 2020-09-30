/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class EmailAddressParsedResult
extends ParsedResult {
    private final String[] bccs;
    private final String body;
    private final String[] ccs;
    private final String subject;
    private final String[] tos;

    EmailAddressParsedResult(String string2) {
        this(new String[]{string2}, null, null, null, null);
    }

    EmailAddressParsedResult(String[] arrstring, String[] arrstring2, String[] arrstring3, String string2, String string3) {
        super(ParsedResultType.EMAIL_ADDRESS);
        this.tos = arrstring;
        this.ccs = arrstring2;
        this.bccs = arrstring3;
        this.subject = string2;
        this.body = string3;
    }

    public String[] getBCCs() {
        return this.bccs;
    }

    public String getBody() {
        return this.body;
    }

    public String[] getCCs() {
        return this.ccs;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(30);
        EmailAddressParsedResult.maybeAppend(this.tos, stringBuilder);
        EmailAddressParsedResult.maybeAppend(this.ccs, stringBuilder);
        EmailAddressParsedResult.maybeAppend(this.bccs, stringBuilder);
        EmailAddressParsedResult.maybeAppend(this.subject, stringBuilder);
        EmailAddressParsedResult.maybeAppend(this.body, stringBuilder);
        return stringBuilder.toString();
    }

    @Deprecated
    public String getEmailAddress() {
        Object object = this.tos;
        if (object == null) return null;
        if (((String[])object).length == 0) return null;
        return object[0];
    }

    @Deprecated
    public String getMailtoURI() {
        return "mailto:";
    }

    public String getSubject() {
        return this.subject;
    }

    public String[] getTos() {
        return this.tos;
    }
}

