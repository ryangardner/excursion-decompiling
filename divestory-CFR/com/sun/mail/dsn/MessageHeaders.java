/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.dsn;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public class MessageHeaders
extends MimeMessage {
    public MessageHeaders() throws MessagingException {
        super((Session)null);
        this.content = new byte[0];
    }

    public MessageHeaders(InputStream inputStream2) throws MessagingException {
        super(null, inputStream2);
        this.content = new byte[0];
    }

    public MessageHeaders(InternetHeaders internetHeaders) throws MessagingException {
        super((Session)null);
        this.headers = internetHeaders;
        this.content = new byte[0];
    }

    @Override
    protected InputStream getContentStream() {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new MessagingException("Can't set content for MessageHeaders");
    }
}

