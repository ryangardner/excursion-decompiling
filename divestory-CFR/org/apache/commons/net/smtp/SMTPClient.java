/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import org.apache.commons.net.io.DotTerminatedMessageWriter;
import org.apache.commons.net.smtp.RelayPath;
import org.apache.commons.net.smtp.SMTP;
import org.apache.commons.net.smtp.SMTPReply;

public class SMTPClient
extends SMTP {
    public SMTPClient() {
    }

    public SMTPClient(String string2) {
        super(string2);
    }

    public boolean addRecipient(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(string2);
        stringBuilder.append(">");
        return SMTPReply.isPositiveCompletion(this.rcpt(stringBuilder.toString()));
    }

    public boolean addRecipient(RelayPath relayPath) throws IOException {
        return SMTPReply.isPositiveCompletion(this.rcpt(relayPath.toString()));
    }

    public boolean completePendingCommand() throws IOException {
        return SMTPReply.isPositiveCompletion(this.getReply());
    }

    public String listHelp() throws IOException {
        if (!SMTPReply.isPositiveCompletion(this.help())) return null;
        return this.getReplyString();
    }

    public String listHelp(String string2) throws IOException {
        if (!SMTPReply.isPositiveCompletion(this.help(string2))) return null;
        return this.getReplyString();
    }

    public boolean login() throws IOException {
        String string2 = this.getLocalAddress().getHostName();
        if (string2 != null) return SMTPReply.isPositiveCompletion(this.helo(string2));
        return false;
    }

    public boolean login(String string2) throws IOException {
        return SMTPReply.isPositiveCompletion(this.helo(string2));
    }

    public boolean logout() throws IOException {
        return SMTPReply.isPositiveCompletion(this.quit());
    }

    public boolean reset() throws IOException {
        return SMTPReply.isPositiveCompletion(this.rset());
    }

    public Writer sendMessageData() throws IOException {
        if (SMTPReply.isPositiveIntermediate(this.data())) return new DotTerminatedMessageWriter(this._writer);
        return null;
    }

    public boolean sendNoOp() throws IOException {
        return SMTPReply.isPositiveCompletion(this.noop());
    }

    public boolean sendShortMessageData(String string2) throws IOException {
        Writer writer = this.sendMessageData();
        if (writer == null) {
            return false;
        }
        writer.write(string2);
        writer.close();
        return this.completePendingCommand();
    }

    public boolean sendSimpleMessage(String string2, String string3, String string4) throws IOException {
        if (!this.setSender(string2)) {
            return false;
        }
        if (this.addRecipient(string3)) return this.sendShortMessageData(string4);
        return false;
    }

    public boolean sendSimpleMessage(String string2, String[] arrstring, String string3) throws IOException {
        if (!this.setSender(string2)) {
            return false;
        }
        int n = 0;
        boolean bl = false;
        do {
            if (n >= arrstring.length) {
                if (bl) return this.sendShortMessageData(string3);
                return false;
            }
            if (this.addRecipient(arrstring[n])) {
                bl = true;
            }
            ++n;
        } while (true);
    }

    public boolean setSender(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(string2);
        stringBuilder.append(">");
        return SMTPReply.isPositiveCompletion(this.mail(stringBuilder.toString()));
    }

    public boolean setSender(RelayPath relayPath) throws IOException {
        return SMTPReply.isPositiveCompletion(this.mail(relayPath.toString()));
    }

    public boolean verify(String string2) throws IOException {
        int n = this.vrfy(string2);
        if (n == 250) return true;
        if (n == 251) return true;
        return false;
    }
}

