/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.Literal;
import com.sun.mail.imap.LengthCounter;
import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;

class MessageLiteral
implements Literal {
    private byte[] buf;
    private Message msg;
    private int msgSize = -1;

    public MessageLiteral(Message message, int n) throws MessagingException, IOException {
        this.msg = message;
        LengthCounter lengthCounter = new LengthCounter(n);
        CRLFOutputStream cRLFOutputStream = new CRLFOutputStream(lengthCounter);
        message.writeTo(cRLFOutputStream);
        ((OutputStream)cRLFOutputStream).flush();
        this.msgSize = lengthCounter.getSize();
        this.buf = lengthCounter.getBytes();
    }

    @Override
    public int size() {
        return this.msgSize;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        try {
            if (this.buf != null) {
                outputStream2.write(this.buf, 0, this.msgSize);
                return;
            }
            CRLFOutputStream cRLFOutputStream = new CRLFOutputStream(outputStream2);
            this.msg.writeTo(cRLFOutputStream);
            return;
        }
        catch (MessagingException messagingException) {
            StringBuilder stringBuilder = new StringBuilder("MessagingException while appending message: ");
            stringBuilder.append(messagingException);
            throw new IOException(stringBuilder.toString());
        }
    }
}

