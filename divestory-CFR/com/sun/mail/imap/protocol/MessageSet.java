/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import java.util.Vector;

public class MessageSet {
    public int end;
    public int start;

    public MessageSet() {
    }

    public MessageSet(int n, int n2) {
        this.start = n;
        this.end = n2;
    }

    public static MessageSet[] createMessageSets(int[] arrobject) {
        Vector<MessageSet> vector = new Vector<MessageSet>();
        int n = 0;
        do {
            int n2;
            if (n >= arrobject.length) {
                arrobject = new MessageSet[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            MessageSet messageSet = new MessageSet();
            messageSet.start = arrobject[n];
            while ((n2 = n + 1) < arrobject.length) {
                n = n2;
                if (arrobject[n2] == arrobject[n2 - 1] + 1) continue;
            }
            n = n2 - 1;
            messageSet.end = arrobject[n];
            vector.addElement(messageSet);
            ++n;
        } while (true);
    }

    public static int size(MessageSet[] arrmessageSet) {
        int n = 0;
        if (arrmessageSet == null) {
            return 0;
        }
        int n2 = 0;
        while (n < arrmessageSet.length) {
            n2 += arrmessageSet[n].size();
            ++n;
        }
        return n2;
    }

    public static String toString(MessageSet[] arrmessageSet) {
        if (arrmessageSet == null) return null;
        if (arrmessageSet.length == 0) {
            return null;
        }
        int n = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = arrmessageSet.length;
        do {
            int n3;
            int n4;
            if ((n4 = arrmessageSet[n].end) > (n3 = arrmessageSet[n].start)) {
                stringBuffer.append(n3);
                stringBuffer.append(':');
                stringBuffer.append(n4);
            } else {
                stringBuffer.append(n3);
            }
            if (++n >= n2) {
                return stringBuffer.toString();
            }
            stringBuffer.append(',');
        } while (true);
    }

    public int size() {
        return this.end - this.start + 1;
    }
}

