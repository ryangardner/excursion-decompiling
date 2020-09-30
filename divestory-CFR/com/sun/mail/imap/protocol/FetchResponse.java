/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.RFC822SIZE;
import com.sun.mail.imap.protocol.UID;
import java.io.IOException;
import java.util.Vector;

public class FetchResponse
extends IMAPResponse {
    private static final char[] HEADER = new char[]{'.', 'H', 'E', 'A', 'D', 'E', 'R'};
    private static final char[] TEXT = new char[]{'.', 'T', 'E', 'X', 'T'};
    private Item[] items;

    public FetchResponse(Protocol protocol) throws IOException, ProtocolException {
        super(protocol);
        this.parse();
    }

    public FetchResponse(IMAPResponse iMAPResponse) throws IOException, ProtocolException {
        super(iMAPResponse);
        this.parse();
    }

    public static Item getItem(Response[] arrresponse, int n, Class class_) {
        if (arrresponse == null) {
            return null;
        }
        int n2 = 0;
        while (n2 < arrresponse.length) {
            if (arrresponse[n2] != null && arrresponse[n2] instanceof FetchResponse && ((FetchResponse)arrresponse[n2]).getNumber() == n) {
                Item[] arritem;
                FetchResponse fetchResponse = (FetchResponse)arrresponse[n2];
                for (int i = 0; i < (arritem = fetchResponse.items).length; ++i) {
                    if (!class_.isInstance(arritem[i])) continue;
                    return fetchResponse.items[i];
                }
            }
            ++n2;
        }
        return null;
    }

    private boolean match(char[] arrc) {
        int n = arrc.length;
        int n2 = this.index;
        int n3 = 0;
        while (n3 < n) {
            if (Character.toUpperCase((char)this.buffer[n2]) != arrc[n3]) {
                return false;
            }
            ++n3;
            ++n2;
        }
        return true;
    }

    private void parse() throws ParsingException {
        this.skipSpaces();
        if (this.buffer[this.index] != 40) {
            StringBuilder stringBuilder = new StringBuilder("error in FETCH parsing, missing '(' at index ");
            stringBuilder.append(this.index);
            throw new ParsingException(stringBuilder.toString());
        }
        Vector<StringBuilder> vector = new Vector<StringBuilder>();
        Object object = null;
        do {
            ++this.index;
            if (this.index >= this.size) {
                object = new StringBuilder("error in FETCH parsing, ran off end of buffer, size ");
                object.append(this.size);
                throw new ParsingException(object.toString());
            }
            byte by = this.buffer[this.index];
            if (by != 66) {
                if (by != 73) {
                    if (by != 82) {
                        if (by != 85) {
                            if (by != 69) {
                                if (by == 70 && this.match(FLAGS.name)) {
                                    this.index += FLAGS.name.length;
                                    object = new FLAGS(this);
                                }
                            } else if (this.match(ENVELOPE.name)) {
                                this.index += ENVELOPE.name.length;
                                object = new ENVELOPE(this);
                            }
                        } else if (this.match(UID.name)) {
                            this.index += UID.name.length;
                            object = new UID(this);
                        }
                    } else if (this.match(RFC822SIZE.name)) {
                        this.index += RFC822SIZE.name.length;
                        object = new RFC822SIZE(this);
                    } else if (this.match(RFC822DATA.name)) {
                        this.index += RFC822DATA.name.length;
                        if (this.match(HEADER)) {
                            this.index += HEADER.length;
                        } else if (this.match(TEXT)) {
                            this.index += TEXT.length;
                        }
                        object = new RFC822DATA(this);
                    }
                } else if (this.match(INTERNALDATE.name)) {
                    this.index += INTERNALDATE.name.length;
                    object = new INTERNALDATE(this);
                }
            } else if (this.match(BODY.name)) {
                if (this.buffer[this.index + 4] == 91) {
                    this.index += BODY.name.length;
                    object = new BODY(this);
                } else {
                    this.index = this.match(BODYSTRUCTURE.name) ? (this.index += BODYSTRUCTURE.name.length) : (this.index += BODY.name.length);
                    object = new BODYSTRUCTURE(this);
                }
            }
            if (object == null) continue;
            vector.addElement((StringBuilder)object);
        } while (this.buffer[this.index] != 41);
        ++this.index;
        object = new Item[vector.size()];
        this.items = object;
        vector.copyInto((Object[])object);
    }

    public Item getItem(int n) {
        return this.items[n];
    }

    public Item getItem(Class class_) {
        int n = 0;
        Item[] arritem;
        while (n < (arritem = this.items).length) {
            if (class_.isInstance(arritem[n])) {
                return this.items[n];
            }
            ++n;
        }
        return null;
    }

    public int getItemCount() {
        return this.items.length;
    }
}

