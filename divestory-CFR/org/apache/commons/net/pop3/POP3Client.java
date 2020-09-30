/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.pop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.pop3.POP3MessageInfo;

public class POP3Client
extends POP3 {
    private static POP3MessageInfo __parseStatus(String object) {
        int n;
        int n2;
        StringTokenizer stringTokenizer = new StringTokenizer((String)object);
        boolean bl = stringTokenizer.hasMoreElements();
        object = null;
        if (!bl) {
            return null;
        }
        try {
            n2 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreElements()) {
                return null;
            }
            n = Integer.parseInt(stringTokenizer.nextToken());
        }
        catch (NumberFormatException numberFormatException) {
            return object;
        }
        return new POP3MessageInfo(n2, n);
    }

    private static POP3MessageInfo __parseUID(String object) {
        int n;
        Object object2 = new StringTokenizer((String)object);
        boolean bl = ((StringTokenizer)object2).hasMoreElements();
        object = null;
        if (!bl) {
            return null;
        }
        try {
            n = Integer.parseInt(((StringTokenizer)object2).nextToken());
            if (!((StringTokenizer)object2).hasMoreElements()) {
                return null;
            }
            object2 = ((StringTokenizer)object2).nextToken();
        }
        catch (NumberFormatException numberFormatException) {
            return object;
        }
        return new POP3MessageInfo(n, (String)object2);
    }

    public boolean capa() throws IOException {
        if (this.sendCommand(12) != 0) return false;
        this.getAdditionalReply();
        return true;
    }

    public boolean deleteMessage(int n) throws IOException {
        boolean bl;
        int n2 = this.getState();
        boolean bl2 = bl = false;
        if (n2 != 1) return bl2;
        bl2 = bl;
        if (this.sendCommand(6, Integer.toString(n)) != 0) return bl2;
        return true;
    }

    public POP3MessageInfo listMessage(int n) throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(4, Integer.toString(n)) == 0) return POP3Client.__parseStatus(this._lastReplyLine.substring(3));
        return null;
    }

    public POP3MessageInfo[] listMessages() throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(4) != 0) {
            return null;
        }
        this.getAdditionalReply();
        int n = this._replyLines.size() - 2;
        POP3MessageInfo[] arrpOP3MessageInfo = new POP3MessageInfo[n];
        ListIterator listIterator = this._replyLines.listIterator(1);
        int n2 = 0;
        while (n2 < n) {
            arrpOP3MessageInfo[n2] = POP3Client.__parseStatus((String)listIterator.next());
            ++n2;
        }
        return arrpOP3MessageInfo;
    }

    public POP3MessageInfo listUniqueIdentifier(int n) throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(11, Integer.toString(n)) == 0) return POP3Client.__parseUID(this._lastReplyLine.substring(3));
        return null;
    }

    public POP3MessageInfo[] listUniqueIdentifiers() throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(11) != 0) {
            return null;
        }
        this.getAdditionalReply();
        int n = this._replyLines.size() - 2;
        POP3MessageInfo[] arrpOP3MessageInfo = new POP3MessageInfo[n];
        ListIterator listIterator = this._replyLines.listIterator(1);
        int n2 = 0;
        while (n2 < n) {
            arrpOP3MessageInfo[n2] = POP3Client.__parseUID((String)listIterator.next());
            ++n2;
        }
        return arrpOP3MessageInfo;
    }

    public boolean login(String string2, String string3) throws IOException {
        if (this.getState() != 0) {
            return false;
        }
        if (this.sendCommand(0, string2) != 0) {
            return false;
        }
        if (this.sendCommand(1, string3) != 0) {
            return false;
        }
        this.setState(1);
        return true;
    }

    public boolean login(String string2, String charSequence, String object) throws IOException, NoSuchAlgorithmException {
        if (this.getState() != 0) {
            return false;
        }
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append((String)object);
        object = messageDigest.digest(stringBuilder.toString().getBytes(this.getCharsetName()));
        charSequence = new StringBuilder(128);
        for (int i = 0; i < ((byte[])object).length; ++i) {
            int n = object[i] & 255;
            if (n <= 15) {
                ((StringBuilder)charSequence).append("0");
            }
            ((StringBuilder)charSequence).append(Integer.toHexString(n));
        }
        object = new StringBuilder(256);
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(' ');
        ((StringBuilder)object).append(((StringBuilder)charSequence).toString());
        if (this.sendCommand(9, ((StringBuilder)object).toString()) != 0) {
            return false;
        }
        this.setState(1);
        return true;
    }

    public boolean logout() throws IOException {
        int n = this.getState();
        boolean bl = true;
        if (n == 1) {
            this.setState(2);
        }
        this.sendCommand(2);
        if (this._replyCode != 0) return false;
        return bl;
    }

    public boolean noop() throws IOException {
        boolean bl;
        int n = this.getState();
        boolean bl2 = bl = false;
        if (n != 1) return bl2;
        bl2 = bl;
        if (this.sendCommand(7) != 0) return bl2;
        return true;
    }

    public boolean reset() throws IOException {
        boolean bl;
        int n = this.getState();
        boolean bl2 = bl = false;
        if (n != 1) return bl2;
        bl2 = bl;
        if (this.sendCommand(8) != 0) return bl2;
        return true;
    }

    public Reader retrieveMessage(int n) throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(5, Integer.toString(n)) == 0) return new DotTerminatedMessageReader(this._reader);
        return null;
    }

    public Reader retrieveMessageTop(int n, int n2) throws IOException {
        if (n2 < 0) return null;
        if (this.getState() != 1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(n));
        stringBuilder.append(" ");
        stringBuilder.append(Integer.toString(n2));
        if (this.sendCommand(10, stringBuilder.toString()) == 0) return new DotTerminatedMessageReader(this._reader);
        return null;
    }

    public POP3MessageInfo status() throws IOException {
        if (this.getState() != 1) {
            return null;
        }
        if (this.sendCommand(3) == 0) return POP3Client.__parseStatus(this._lastReplyLine.substring(3));
        return null;
    }
}

