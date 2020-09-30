/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.MessageHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import myjava.awt.datatransfer.DataFlavor;

public class text_rfc822headers
implements DataContentHandler {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(MessageHeaders.class, "text/rfc822-headers", "RFC822 headers");
    private static ActivationDataFlavor myDFs = new ActivationDataFlavor(String.class, "text/rfc822-headers", "RFC822 headers");

    private String getCharset(String object) {
        try {
            Object object2 = new ContentType((String)object);
            object = object2 = ((ContentType)object2).getParameter("charset");
            if (object2 != null) return MimeUtility.javaCharset((String)object);
            object = "us-ascii";
            return MimeUtility.javaCharset((String)object);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private Object getStringContent(DataSource arrc) throws IOException {
        Object object;
        char[] arrc2 = null;
        try {
            object = this.getCharset(arrc.getContentType());
            arrc2 = object;
            object = new InputStreamReader(arrc.getInputStream(), (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException((String)arrc2);
        }
        arrc = new char[1024];
        int n = 0;
        int n2;
        while ((n2 = ((InputStreamReader)object).read(arrc, n, arrc.length - n)) != -1) {
            n = n2 = n + n2;
            if (n2 < arrc.length) continue;
            n = arrc.length;
            n = n < 262144 ? (n += n) : (n += 262144);
            arrc2 = new char[n];
            System.arraycopy(arrc, 0, arrc2, 0, n2);
            arrc = arrc2;
            n = n2;
        }
        return new String(arrc, 0, n);
    }

    @Override
    public Object getContent(DataSource object) throws IOException {
        try {
            return new MessageHeaders(object.getInputStream());
        }
        catch (MessagingException messagingException) {
            StringBuilder stringBuilder = new StringBuilder("Exception creating MessageHeaders: ");
            stringBuilder.append(messagingException);
            throw new IOException(stringBuilder.toString());
        }
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (myDF.equals(dataFlavor)) {
            return this.getContent(dataSource);
        }
        if (!myDFs.equals(dataFlavor)) return null;
        return this.getStringContent(dataSource);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{myDF, myDFs};
    }

    @Override
    public void writeTo(Object object, String object2, OutputStream outputStream2) throws IOException {
        if (object instanceof MessageHeaders) {
            object = (MessageHeaders)object;
            try {
                ((MimeMessage)object).writeTo(outputStream2);
                return;
            }
            catch (MessagingException messagingException) {
                object2 = messagingException.getNextException();
                if (object2 instanceof IOException) {
                    throw (IOException)object2;
                }
                object2 = new StringBuilder("Exception writing headers: ");
                ((StringBuilder)object2).append(messagingException);
                throw new IOException(((StringBuilder)object2).toString());
            }
        }
        if (!(object instanceof String)) {
            object2 = new StringBuilder("\"");
            ((StringBuilder)object2).append(myDFs.getMimeType());
            ((StringBuilder)object2).append("\" DataContentHandler requires String object, ");
            ((StringBuilder)object2).append("was given object of type ");
            ((StringBuilder)object2).append(object.getClass().toString());
            throw new IOException(((StringBuilder)object2).toString());
        }
        Object object3 = null;
        try {
            object3 = object2 = this.getCharset((String)object2);
            object2 = new OutputStreamWriter(outputStream2, (String)object2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException((String)object3);
        }
        object = (String)object;
        ((OutputStreamWriter)object2).write((String)object, 0, ((String)object).length());
        ((OutputStreamWriter)object2).flush();
    }
}

