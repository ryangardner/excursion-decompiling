/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.IOException;
import java.io.Writer;

public final class DotTerminatedMessageWriter
extends Writer {
    private static final int __LAST_WAS_CR_STATE = 1;
    private static final int __LAST_WAS_NL_STATE = 2;
    private static final int __NOTHING_SPECIAL_STATE = 0;
    private Writer __output;
    private int __state;

    public DotTerminatedMessageWriter(Writer writer) {
        super(writer);
        this.__output = writer;
        this.__state = 0;
    }

    @Override
    public void close() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.__output == null) {
                return;
            }
            if (this.__state == 1) {
                this.__output.write(10);
            } else if (this.__state != 2) {
                this.__output.write("\r\n");
            }
            this.__output.write(".\r\n");
            this.__output.flush();
            this.__output = null;
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.__output.flush();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void write(int var1_1) throws IOException {
        var2_2 = this.lock;
        // MONITORENTER : var2_2
        if (var1_1 == 10) ** GOTO lbl17
        if (var1_1 == 13) ** GOTO lbl13
        if (var1_1 != 46) ** GOTO lbl9
        if (this.__state == 2) {
            this.__output.write(46);
        }
lbl9: // 4 sources:
        this.__state = 0;
        this.__output.write(var1_1);
        // MONITOREXIT : var2_2
        return;
lbl13: // 1 sources:
        this.__state = 1;
        this.__output.write(13);
        // MONITOREXIT : var2_2
        return;
lbl17: // 1 sources:
        if (this.__state != 1) {
            this.__output.write(13);
        }
        this.__output.write(10);
        this.__state = 2;
        // MONITOREXIT : var2_2
        return;
    }

    @Override
    public void write(String string2) throws IOException {
        this.write(string2.toCharArray());
    }

    @Override
    public void write(String string2, int n, int n2) throws IOException {
        this.write(string2.toCharArray(), n, n2);
    }

    @Override
    public void write(char[] arrc) throws IOException {
        this.write(arrc, 0, arrc.length);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            void var3_3;
            while (--var3_3 > 0) {
                void var2_2;
                this.write(arrc[var2_2]);
                ++var2_2;
            }
            return;
        }
    }
}

