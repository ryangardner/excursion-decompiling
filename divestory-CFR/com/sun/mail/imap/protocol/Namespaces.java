/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.BASE64MailboxDecoder;
import java.util.Vector;

public class Namespaces {
    public Namespace[] otherUsers;
    public Namespace[] personal;
    public Namespace[] shared;

    public Namespaces(Response response) throws ProtocolException {
        this.personal = this.getNamespaces(response);
        this.otherUsers = this.getNamespaces(response);
        this.shared = this.getNamespaces(response);
    }

    private Namespace[] getNamespaces(Response arrobject) throws ProtocolException {
        arrobject.skipSpaces();
        if (arrobject.peekByte() == 40) {
            Vector<Namespace> vector = new Vector<Namespace>();
            arrobject.readByte();
            do {
                vector.addElement(new Namespace((Response)arrobject));
            } while (arrobject.peekByte() != 41);
            arrobject.readByte();
            arrobject = new Namespace[vector.size()];
            vector.copyInto(arrobject);
            return arrobject;
        }
        if ((arrobject = arrobject.readAtom()) == null) throw new ProtocolException("Expected NIL, got null");
        if (arrobject.equalsIgnoreCase("NIL")) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected NIL, got ");
        stringBuilder.append((String)arrobject);
        throw new ProtocolException(stringBuilder.toString());
    }

    public static class Namespace {
        public char delimiter;
        public String prefix;

        public Namespace(Response object) throws ProtocolException {
            if (((Response)object).readByte() != 40) throw new ProtocolException("Missing '(' at start of Namespace");
            this.prefix = BASE64MailboxDecoder.decode(((Response)object).readString());
            ((Response)object).skipSpaces();
            if (((Response)object).peekByte() == 34) {
                ((Response)object).readByte();
                char c = (char)((Response)object).readByte();
                this.delimiter = c;
                if (c == '\\') {
                    this.delimiter = (char)((Response)object).readByte();
                }
                if (((Response)object).readByte() != 34) throw new ProtocolException("Missing '\"' at end of QUOTED_CHAR");
            } else {
                String string2 = ((Response)object).readAtom();
                if (string2 == null) throw new ProtocolException("Expected NIL, got null");
                if (!string2.equalsIgnoreCase("NIL")) {
                    object = new StringBuilder("Expected NIL, got ");
                    ((StringBuilder)object).append(string2);
                    throw new ProtocolException(((StringBuilder)object).toString());
                }
                this.delimiter = (char)(false ? 1 : 0);
            }
            if (((Response)object).peekByte() != 41) {
                ((Response)object).skipSpaces();
                ((Response)object).readString();
                ((Response)object).skipSpaces();
                ((Response)object).readStringList();
            }
            if (((Response)object).readByte() != 41) throw new ProtocolException("Missing ')' at end of Namespace");
        }
    }

}

