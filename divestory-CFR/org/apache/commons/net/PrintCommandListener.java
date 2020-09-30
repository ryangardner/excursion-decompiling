/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

public class PrintCommandListener
implements ProtocolCommandListener {
    private final boolean __directionMarker;
    private final char __eolMarker;
    private final boolean __nologin;
    private final PrintWriter __writer;

    public PrintCommandListener(PrintStream printStream) {
        this(new PrintWriter(printStream));
    }

    public PrintCommandListener(PrintStream printStream, boolean bl) {
        this(new PrintWriter(printStream), bl);
    }

    public PrintCommandListener(PrintStream printStream, boolean bl, char c) {
        this(new PrintWriter(printStream), bl, c);
    }

    public PrintCommandListener(PrintStream printStream, boolean bl, char c, boolean bl2) {
        this(new PrintWriter(printStream), bl, c, bl2);
    }

    public PrintCommandListener(PrintWriter printWriter) {
        this(printWriter, false);
    }

    public PrintCommandListener(PrintWriter printWriter, boolean bl) {
        this(printWriter, bl, '\u0000');
    }

    public PrintCommandListener(PrintWriter printWriter, boolean bl, char c) {
        this(printWriter, bl, c, false);
    }

    public PrintCommandListener(PrintWriter printWriter, boolean bl, char c, boolean bl2) {
        this.__writer = printWriter;
        this.__nologin = bl;
        this.__eolMarker = c;
        this.__directionMarker = bl2;
    }

    private String getPrintableString(String string2) {
        if (this.__eolMarker == '\u0000') {
            return string2;
        }
        int n = string2.indexOf("\r\n");
        CharSequence charSequence = string2;
        if (n <= 0) return charSequence;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2.substring(0, n));
        ((StringBuilder)charSequence).append(this.__eolMarker);
        ((StringBuilder)charSequence).append(string2.substring(n));
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public void protocolCommandSent(ProtocolCommandEvent object) {
        if (this.__directionMarker) {
            this.__writer.print("> ");
        }
        if (this.__nologin) {
            String string2 = ((ProtocolCommandEvent)object).getCommand();
            if (!"PASS".equalsIgnoreCase(string2) && !"USER".equalsIgnoreCase(string2)) {
                if ("LOGIN".equalsIgnoreCase(string2)) {
                    object = ((ProtocolCommandEvent)object).getMessage();
                    object = ((String)object).substring(0, ((String)object).indexOf("LOGIN") + 5);
                    this.__writer.print((String)object);
                    this.__writer.println(" *******");
                } else {
                    this.__writer.print(this.getPrintableString(((ProtocolCommandEvent)object).getMessage()));
                }
            } else {
                this.__writer.print(string2);
                this.__writer.println(" *******");
            }
        } else {
            this.__writer.print(this.getPrintableString(((ProtocolCommandEvent)object).getMessage()));
        }
        this.__writer.flush();
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
        if (this.__directionMarker) {
            this.__writer.print("< ");
        }
        this.__writer.print(protocolCommandEvent.getMessage());
        this.__writer.flush();
    }
}

