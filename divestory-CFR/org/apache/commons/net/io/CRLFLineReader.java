/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public final class CRLFLineReader
extends BufferedReader {
    private static final char CR = '\r';
    private static final char LF = '\n';

    public CRLFLineReader(Reader reader) {
        super(reader);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public String readLine() throws IOException {
        CharSequence charSequence = new StringBuilder();
        Object object = this.lock;
        synchronized (object) {
            boolean bl = false;
            do {
                int n;
                if ((n = this.read()) == -1) {
                    // MONITOREXIT [2, 3, 5] lbl7 : MonitorExitStatement: MONITOREXIT : var2_2
                    charSequence = ((StringBuilder)charSequence).toString();
                    object = charSequence;
                    if (((String)charSequence).length() != 0) return object;
                    return null;
                }
                if (bl && n == 10) {
                    return ((StringBuilder)charSequence).substring(0, ((StringBuilder)charSequence).length() - 1);
                }
                bl = n == 13;
                ((StringBuilder)charSequence).append((char)n);
            } while (true);
        }
    }
}

