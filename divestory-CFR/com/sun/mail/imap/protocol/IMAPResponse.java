/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.util.ASCIIUtility;
import java.io.IOException;
import java.util.Vector;

public class IMAPResponse
extends Response {
    private String key;
    private int number;

    public IMAPResponse(Protocol object) throws IOException, ProtocolException {
        super((Protocol)object);
        if (!this.isUnTagged()) return;
        if (this.isOK()) return;
        if (this.isNO()) return;
        if (this.isBAD()) return;
        if (this.isBYE()) return;
        this.key = object = this.readAtom();
        try {
            this.number = Integer.parseInt((String)object);
            this.key = this.readAtom();
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    public IMAPResponse(IMAPResponse iMAPResponse) {
        super(iMAPResponse);
        this.key = iMAPResponse.key;
        this.number = iMAPResponse.number;
    }

    public static IMAPResponse readResponse(Protocol object) throws IOException, ProtocolException {
        IMAPResponse iMAPResponse = new IMAPResponse((Protocol)object);
        object = iMAPResponse;
        if (!iMAPResponse.keyEquals("FETCH")) return object;
        return new FetchResponse(iMAPResponse);
    }

    public String getKey() {
        return this.key;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean keyEquals(String string2) {
        String string3 = this.key;
        if (string3 == null) return false;
        if (!string3.equalsIgnoreCase(string2)) return false;
        return true;
    }

    public String[] readSimpleList() {
        this.skipSpaces();
        int n = this.buffer[this.index];
        Object[] arrobject = null;
        if (n != 40) {
            return null;
        }
        ++this.index;
        Vector<String> vector = new Vector<String>();
        int n2 = this.index;
        do {
            if (this.buffer[this.index] == 41) {
                if (this.index > n2) {
                    vector.addElement(ASCIIUtility.toString(this.buffer, n2, this.index));
                }
                ++this.index;
                n = vector.size();
                if (n <= 0) return arrobject;
                arrobject = new String[n];
                vector.copyInto(arrobject);
                return arrobject;
            }
            n = n2;
            if (this.buffer[this.index] == 32) {
                vector.addElement(ASCIIUtility.toString(this.buffer, n2, this.index));
                n = this.index + 1;
            }
            ++this.index;
            n2 = n;
        } while (true);
    }
}

