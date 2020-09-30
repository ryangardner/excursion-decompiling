/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public final class DotTerminatedMessageReader
extends BufferedReader {
    private static final char CR = '\r';
    private static final int DOT = 46;
    private static final char LF = '\n';
    private boolean atBeginning = true;
    private boolean eof = false;
    private boolean seenCR;

    public DotTerminatedMessageReader(Reader reader) {
        super(reader);
    }

    @Override
    public void close() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (!this.eof) {
                while (this.read() != -1) {
                }
            }
            this.eof = true;
            this.atBeginning = false;
            return;
        }
    }

    @Override
    public int read() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.eof) {
                return -1;
            }
            int n = super.read();
            if (n == -1) {
                this.eof = true;
                return -1;
            }
            if (this.atBeginning) {
                this.atBeginning = false;
                if (n == 46) {
                    this.mark(2);
                    n = super.read();
                    if (n == -1) {
                        this.eof = true;
                        return 46;
                    }
                    if (n == 46) {
                        return n;
                    }
                    if (n == 13) {
                        n = super.read();
                        if (n == -1) {
                            this.reset();
                            return 46;
                        }
                        if (n == 10) {
                            this.atBeginning = true;
                            this.eof = true;
                            return -1;
                        }
                    }
                    this.reset();
                    return 46;
                }
            }
            if (this.seenCR) {
                this.seenCR = false;
                if (n == 10) {
                    this.atBeginning = true;
                }
            }
            if (n != 13) return n;
            this.seenCR = true;
            return n;
        }
    }

    @Override
    public int read(char[] arrc) throws IOException {
        return this.read(arrc, 0, arrc.length);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public int read(char[] arrc, int n, int n2) throws IOException {
        if (n2 < 1) {
            return 0;
        }
        Object object = this.lock;
        synchronized (object) {
            int n3 = this.read();
            if (n3 == -1) {
                return -1;
            }
            int n4 = n;
            int n5 = n2;
            n2 = n3;
            do {
                n3 = n4 + 1;
                arrc[n4] = (char)n2;
                if (--n5 <= 0) return n3 - n;
                n2 = this.read();
                if (n2 == -1) {
                    return n3 - n;
                }
                n4 = n3;
            } while (true);
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public String readLine() throws IOException {
        CharSequence charSequence = new StringBuilder();
        Object object = this.lock;
        synchronized (object) {
            do {
                int n;
                if ((n = this.read()) == -1) {
                    // MONITOREXIT [2, 3, 5] lbl6 : MonitorExitStatement: MONITOREXIT : var2_2
                    charSequence = ((StringBuilder)charSequence).toString();
                    object = charSequence;
                    if (((String)charSequence).length() != 0) return object;
                    return null;
                }
                if (n == 10 && this.atBeginning) {
                    return ((StringBuilder)charSequence).substring(0, ((StringBuilder)charSequence).length() - 1);
                }
                ((StringBuilder)charSequence).append((char)n);
            } while (true);
        }
    }
}

