/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.DeliveryStatus;
import com.sun.mail.dsn.MessageHeaders;
import java.io.IOException;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MultipartReport
extends MimeMultipart {
    protected boolean constructed;

    public MultipartReport() throws MessagingException {
        super("report");
        this.setBodyPart(new MimeBodyPart(), 0);
        this.setBodyPart(new MimeBodyPart(), 1);
        this.constructed = true;
    }

    public MultipartReport(String object, DeliveryStatus deliveryStatus) throws MessagingException {
        super("report");
        Object object2 = new ContentType(this.contentType);
        ((ContentType)object2).setParameter("report-type", "delivery-status");
        this.contentType = ((ContentType)object2).toString();
        object2 = new MimeBodyPart();
        ((MimeBodyPart)object2).setText((String)object);
        this.setBodyPart((BodyPart)object2, 0);
        object = new MimeBodyPart();
        ((MimeBodyPart)object).setContent(deliveryStatus, "message/delivery-status");
        this.setBodyPart((BodyPart)object, 1);
        this.constructed = true;
    }

    public MultipartReport(String object, DeliveryStatus deliveryStatus, InternetHeaders internetHeaders) throws MessagingException {
        this((String)object, deliveryStatus);
        if (internetHeaders == null) return;
        object = new MimeBodyPart();
        ((MimeBodyPart)object).setContent(new MessageHeaders(internetHeaders), "text/rfc822-headers");
        this.setBodyPart((BodyPart)object, 2);
    }

    public MultipartReport(String object, DeliveryStatus deliveryStatus, MimeMessage mimeMessage) throws MessagingException {
        this((String)object, deliveryStatus);
        if (mimeMessage == null) return;
        object = new MimeBodyPart();
        ((MimeBodyPart)object).setContent(mimeMessage, "message/rfc822");
        this.setBodyPart((BodyPart)object, 2);
    }

    public MultipartReport(DataSource dataSource) throws MessagingException {
        super(dataSource);
        this.parse();
        this.constructed = true;
    }

    private void setBodyPart(BodyPart bodyPart, int n) throws MessagingException {
        synchronized (this) {
            if (this.parts == null) {
                Vector vector;
                this.parts = vector = new Vector();
            }
            if (n < this.parts.size()) {
                super.removeBodyPart(n);
            }
            super.addBodyPart(bodyPart, n);
            return;
        }
    }

    @Override
    public void addBodyPart(BodyPart object) throws MessagingException {
        synchronized (this) {
            if (!this.constructed) {
                super.addBodyPart((BodyPart)object);
                return;
            }
            object = new MessagingException("Can't add body parts to multipart/report 1");
            throw object;
        }
    }

    @Override
    public void addBodyPart(BodyPart object, int n) throws MessagingException {
        synchronized (this) {
            object = new MessagingException("Can't add body parts to multipart/report 2");
            throw object;
        }
    }

    public DeliveryStatus getDeliveryStatus() throws MessagingException {
        synchronized (this) {
            int n = this.getCount();
            if (n < 2) {
                return null;
            }
            Object object = this.getBodyPart(1);
            boolean bl = object.isMimeType("message/delivery-status");
            if (!bl) {
                return null;
            }
            try {
                object = (DeliveryStatus)object.getContent();
                return object;
            }
            catch (IOException iOException) {
                object = new MessagingException("IOException getting DeliveryStatus", iOException);
                throw object;
            }
        }
    }

    public MimeMessage getReturnedMessage() throws MessagingException {
        synchronized (this) {
            boolean bl;
            int n = this.getCount();
            if (n < 3) {
                return null;
            }
            Part part = this.getBodyPart(2);
            if (!part.isMimeType("message/rfc822") && !(bl = part.isMimeType("text/rfc822-headers"))) {
                return null;
            }
            try {
                part = (MimeMessage)part.getContent();
                return part;
            }
            catch (IOException iOException) {
                MessagingException messagingException = new MessagingException("IOException getting ReturnedMessage", iOException);
                throw messagingException;
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public String getText() throws MessagingException {
        // MONITORENTER : this
        int i = 0;
        Object object = this.getBodyPart(0);
        if (object.isMimeType("text/plain")) {
            object = (String)object.getContent();
            // MONITOREXIT : this
            return object;
        }
        if (!object.isMimeType("multipart/alternative")) {
            // MONITOREXIT : this
            return null;
        }
        Multipart multipart = (Multipart)object.getContent();
        do {
            if (i >= multipart.getCount()) {
                return null;
            }
            object = multipart.getBodyPart(i);
            if (object.isMimeType("text/plain")) {
                object = (String)object.getContent();
                // MONITOREXIT : this
                return object;
            }
            ++i;
        } while (true);
        {
            catch (Throwable throwable22) {
                throw throwable22;
            }
            catch (IOException iOException) {}
            {
                MessagingException messagingException = new MessagingException("Exception getting text content", iOException);
                throw messagingException;
            }
        }
    }

    public MimeBodyPart getTextBodyPart() throws MessagingException {
        synchronized (this) {
            return (MimeBodyPart)this.getBodyPart(0);
        }
    }

    @Override
    public void removeBodyPart(int n) throws MessagingException {
        throw new MessagingException("Can't remove body parts from multipart/report");
    }

    @Override
    public boolean removeBodyPart(BodyPart bodyPart) throws MessagingException {
        throw new MessagingException("Can't remove body parts from multipart/report");
    }

    public void setDeliveryStatus(DeliveryStatus object) throws MessagingException {
        synchronized (this) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(object, "message/delivery-status");
            this.setBodyPart(mimeBodyPart, 2);
            object = new ContentType(this.contentType);
            ((ContentType)object).setParameter("report-type", "delivery-status");
            this.contentType = ((ContentType)object).toString();
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public void setReturnedMessage(MimeMessage part) throws MessagingException {
        synchronized (this) {
            if (part == null) {
                BodyPart bodyPart = (BodyPart)this.parts.elementAt(2);
                super.removeBodyPart(2);
                return;
            }
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            if (part instanceof MessageHeaders) {
                mimeBodyPart.setContent(part, "text/rfc822-headers");
            } else {
                mimeBodyPart.setContent(part, "message/rfc822");
            }
            this.setBodyPart(mimeBodyPart, 2);
            return;
        }
    }

    @Override
    public void setSubType(String object) throws MessagingException {
        synchronized (this) {
            object = new MessagingException("Can't change subtype of MultipartReport");
            throw object;
        }
    }

    public void setText(String string2) throws MessagingException {
        synchronized (this) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(string2);
            this.setBodyPart(mimeBodyPart, 0);
            return;
        }
    }

    public void setTextBodyPart(MimeBodyPart mimeBodyPart) throws MessagingException {
        synchronized (this) {
            this.setBodyPart(mimeBodyPart, 0);
            return;
        }
    }
}

