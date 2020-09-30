/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

public final class ParsedResultType
extends Enum<ParsedResultType> {
    private static final /* synthetic */ ParsedResultType[] $VALUES;
    public static final /* enum */ ParsedResultType ADDRESSBOOK;
    public static final /* enum */ ParsedResultType CALENDAR;
    public static final /* enum */ ParsedResultType EMAIL_ADDRESS;
    public static final /* enum */ ParsedResultType GEO;
    public static final /* enum */ ParsedResultType ISBN;
    public static final /* enum */ ParsedResultType PRODUCT;
    public static final /* enum */ ParsedResultType SMS;
    public static final /* enum */ ParsedResultType TEL;
    public static final /* enum */ ParsedResultType TEXT;
    public static final /* enum */ ParsedResultType URI;
    public static final /* enum */ ParsedResultType VIN;
    public static final /* enum */ ParsedResultType WIFI;

    static {
        ParsedResultType parsedResultType;
        ADDRESSBOOK = new ParsedResultType();
        EMAIL_ADDRESS = new ParsedResultType();
        PRODUCT = new ParsedResultType();
        URI = new ParsedResultType();
        TEXT = new ParsedResultType();
        GEO = new ParsedResultType();
        TEL = new ParsedResultType();
        SMS = new ParsedResultType();
        CALENDAR = new ParsedResultType();
        WIFI = new ParsedResultType();
        ISBN = new ParsedResultType();
        VIN = parsedResultType = new ParsedResultType();
        $VALUES = new ParsedResultType[]{ADDRESSBOOK, EMAIL_ADDRESS, PRODUCT, URI, TEXT, GEO, TEL, SMS, CALENDAR, WIFI, ISBN, parsedResultType};
    }

    public static ParsedResultType valueOf(String string2) {
        return Enum.valueOf(ParsedResultType.class, string2);
    }

    public static ParsedResultType[] values() {
        return (ParsedResultType[])$VALUES.clone();
    }
}

