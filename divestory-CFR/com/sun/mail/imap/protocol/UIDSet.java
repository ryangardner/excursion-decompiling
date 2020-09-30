/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import java.util.Vector;

public class UIDSet {
    public long end;
    public long start;

    public UIDSet() {
    }

    public UIDSet(long l, long l2) {
        this.start = l;
        this.end = l2;
    }

    public static UIDSet[] createUIDSets(long[] arrobject) {
        Vector<UIDSet> vector = new Vector<UIDSet>();
        int n = 0;
        do {
            int n2;
            if (n >= arrobject.length) {
                arrobject = new UIDSet[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            UIDSet uIDSet = new UIDSet();
            uIDSet.start = arrobject[n];
            while ((n2 = n + 1) < arrobject.length) {
                n = n2;
                if (arrobject[n2] == arrobject[n2 - 1] + 1L) continue;
            }
            n = n2 - 1;
            uIDSet.end = arrobject[n];
            vector.addElement(uIDSet);
            ++n;
        } while (true);
    }

    public static long size(UIDSet[] arruIDSet) {
        long l = 0L;
        if (arruIDSet == null) {
            return 0L;
        }
        int n = 0;
        while (n < arruIDSet.length) {
            l += arruIDSet[n].size();
            ++n;
        }
        return l;
    }

    public static String toString(UIDSet[] arruIDSet) {
        if (arruIDSet == null) return null;
        if (arruIDSet.length == 0) {
            return null;
        }
        int n = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = arruIDSet.length;
        do {
            long l;
            long l2;
            if ((l = arruIDSet[n].end) > (l2 = arruIDSet[n].start)) {
                stringBuffer.append(l2);
                stringBuffer.append(':');
                stringBuffer.append(l);
            } else {
                stringBuffer.append(l2);
            }
            if (++n >= n2) {
                return stringBuffer.toString();
            }
            stringBuffer.append(',');
        } while (true);
    }

    public long size() {
        return this.end - this.start + 1L;
    }
}

