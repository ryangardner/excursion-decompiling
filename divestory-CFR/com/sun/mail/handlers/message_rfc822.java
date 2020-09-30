/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import myjava.awt.datatransfer.DataFlavor;

public class message_rfc822
implements DataContentHandler {
    ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(Message.class, "message/rfc822", "Message");

    @Override
    public Object getContent(DataSource object) throws IOException {
        try {
            Object object2;
            if (object instanceof MessageAware) {
                object2 = ((MessageAware)object).getMessageContext().getSession();
                return new MimeMessage((Session)object2, object.getInputStream());
            } else {
                object2 = new Properties();
                object2 = Session.getDefaultInstance((Properties)object2, null);
            }
            return new MimeMessage((Session)object2, object.getInputStream());
        }
        catch (MessagingException messagingException) {
            StringBuilder stringBuilder = new StringBuilder("Exception creating MimeMessage in message/rfc822 DataContentHandler: ");
            stringBuilder.append(messagingException.toString());
            throw new IOException(stringBuilder.toString());
        }
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (!this.ourDataFlavor.equals(dataFlavor)) return null;
        return this.getContent(dataSource);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.ourDataFlavor};
    }

    @Override
    public void writeTo(Object object, String string2, OutputStream outputStream2) throws IOException {
        if (!(object instanceof Message)) throw new IOException("unsupported object");
        object = (Message)object;
        try {
            object.writeTo(outputStream2);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.toString());
        }
    }
}

