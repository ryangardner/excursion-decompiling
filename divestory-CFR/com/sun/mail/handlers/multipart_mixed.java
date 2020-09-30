/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_mixed
implements DataContentHandler {
    private ActivationDataFlavor myDF = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");

    @Override
    public Object getContent(DataSource object) throws IOException {
        try {
            return new MimeMultipart((DataSource)object);
        }
        catch (MessagingException messagingException) {
            object = new IOException("Exception while constructing MimeMultipart");
            ((Throwable)object).initCause(messagingException);
            throw object;
        }
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (!this.myDF.equals(dataFlavor)) return null;
        return this.getContent(dataSource);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.myDF};
    }

    @Override
    public void writeTo(Object object, String string2, OutputStream outputStream2) throws IOException {
        if (!(object instanceof MimeMultipart)) return;
        try {
            ((MimeMultipart)object).writeTo(outputStream2);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.toString());
        }
    }
}

