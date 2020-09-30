/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;
import java.io.ByteArrayInputStream;

public class BODY
implements Item {
    static final char[] name = new char[]{'B', 'O', 'D', 'Y'};
    public ByteArray data;
    public int msgno;
    public int origin = 0;
    public String section;

    public BODY(FetchResponse fetchResponse) throws ParsingException {
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        do {
            byte by;
            if ((by = fetchResponse.readByte()) == 93) {
                if (fetchResponse.readByte() == 60) {
                    this.origin = fetchResponse.readNumber();
                    fetchResponse.skip(1);
                }
                this.data = fetchResponse.readByteArray();
                return;
            }
            if (by == 0) throw new ParsingException("BODY parse error: missing ``]'' at section end");
        } while (true);
    }

    public ByteArray getByteArray() {
        return this.data;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        ByteArray byteArray = this.data;
        if (byteArray == null) return null;
        return byteArray.toByteArrayInputStream();
    }
}

