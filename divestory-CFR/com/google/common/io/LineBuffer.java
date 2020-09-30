/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import java.io.IOException;

abstract class LineBuffer {
    private StringBuilder line = new StringBuilder();
    private boolean sawReturn;

    LineBuffer() {
    }

    private boolean finishLine(boolean bl) throws IOException {
        String string2 = this.sawReturn ? (bl ? "\r\n" : "\r") : (bl ? "\n" : "");
        this.handleLine(this.line.toString(), string2);
        this.line = new StringBuilder();
        this.sawReturn = false;
        return bl;
    }

    protected void add(char[] arrc, int n, int n2) throws IOException {
        boolean bl;
        int n3 = this.sawReturn && n2 > 0 && this.finishLine(bl = arrc[n] == '\n') ? n + 1 : n;
        int n4 = n + n2;
        n2 = n3;
        n = n3;
        do {
            block6 : {
                block7 : {
                    block4 : {
                        block5 : {
                            if (n >= n4) {
                                this.line.append(arrc, n2, n4 - n2);
                                return;
                            }
                            n3 = arrc[n];
                            if (n3 == 10) break block4;
                            if (n3 == 13) break block5;
                            n3 = n2;
                            break block6;
                        }
                        this.line.append(arrc, n2, n - n2);
                        this.sawReturn = true;
                        n3 = n + 1;
                        n2 = n;
                        if (n3 < n4) {
                            bl = arrc[n3] == '\n';
                            n2 = n;
                            if (this.finishLine(bl)) {
                                n2 = n3;
                            }
                        }
                        break block7;
                    }
                    this.line.append(arrc, n2, n - n2);
                    this.finishLine(true);
                    n2 = n;
                }
                n3 = n2 + 1;
                n = n2;
            }
            ++n;
            n2 = n3;
        } while (true);
    }

    protected void finish() throws IOException {
        if (!this.sawReturn) {
            if (this.line.length() <= 0) return;
        }
        this.finishLine(false);
    }

    protected abstract void handleLine(String var1, String var2) throws IOException;
}

