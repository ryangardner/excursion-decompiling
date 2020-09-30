/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.DeliveryStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class message_deliverystatus
implements DataContentHandler {
    ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(DeliveryStatus.class, "message/delivery-status", "Delivery Status");

    @Override
    public Object getContent(DataSource object) throws IOException {
        try {
            return new DeliveryStatus(object.getInputStream());
        }
        catch (MessagingException messagingException) {
            object = new StringBuilder("Exception creating DeliveryStatus in message/devliery-status DataContentHandler: ");
            ((StringBuilder)object).append(messagingException.toString());
            throw new IOException(((StringBuilder)object).toString());
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
        if (!(object instanceof DeliveryStatus)) throw new IOException("unsupported object");
        object = (DeliveryStatus)object;
        try {
            ((DeliveryStatus)object).writeTo(outputStream2);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.toString());
        }
    }
}

