/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class MimePartDataSource
implements DataSource,
MessageAware {
    private static boolean ignoreMultipartEncoding = true;
    private MessageContext context;
    protected MimePart part;

    static {
        try {
            String string2 = System.getProperty("mail.mime.ignoremultipartencoding");
            boolean bl = string2 == null || !string2.equalsIgnoreCase("false");
            ignoreMultipartEncoding = bl;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public MimePartDataSource(MimePart mimePart) {
        this.part = mimePart;
    }

    private static String restrictEncoding(String string2, MimePart object) throws MessagingException {
        Object object2 = string2;
        if (!ignoreMultipartEncoding) return object2;
        if (string2 == null) {
            return string2;
        }
        object2 = string2;
        if (string2.equalsIgnoreCase("7bit")) return object2;
        object2 = string2;
        if (string2.equalsIgnoreCase("8bit")) return object2;
        if (string2.equalsIgnoreCase("binary")) {
            return string2;
        }
        if ((object = object.getContentType()) == null) {
            return string2;
        }
        try {
            object2 = new ContentType((String)object);
            if (((ContentType)object2).match("multipart/*")) return null;
            boolean bl = ((ContentType)object2).match("message/*");
            object2 = string2;
            if (!bl) return object2;
            return null;
        }
        catch (ParseException parseException) {
            return string2;
        }
    }

    @Override
    public String getContentType() {
        try {
            return this.part.getContentType();
        }
        catch (MessagingException messagingException) {
            return "application/octet-stream";
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            InputStream inputStream2;
            if (this.part instanceof MimeBodyPart) {
                inputStream2 = ((MimeBodyPart)this.part).getContentStream();
            } else {
                if (!(this.part instanceof MimeMessage)) {
                    MessagingException messagingException = new MessagingException("Unknown part");
                    throw messagingException;
                }
                inputStream2 = ((MimeMessage)this.part).getContentStream();
            }
            String string2 = MimePartDataSource.restrictEncoding(this.part.getEncoding(), this.part);
            InputStream inputStream3 = inputStream2;
            if (string2 == null) return inputStream3;
            return MimeUtility.decode(inputStream2, string2);
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.getMessage());
        }
    }

    @Override
    public MessageContext getMessageContext() {
        synchronized (this) {
            MessageContext messageContext;
            if (this.context != null) return this.context;
            this.context = messageContext = new MessageContext(this.part);
            return this.context;
        }
    }

    @Override
    public String getName() {
        try {
            if (!(this.part instanceof MimeBodyPart)) return "";
            return ((MimeBodyPart)this.part).getFileName();
        }
        catch (MessagingException messagingException) {
            return "";
        }
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException();
    }
}

