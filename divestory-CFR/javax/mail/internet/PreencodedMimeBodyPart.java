/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import com.sun.mail.util.LineOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class PreencodedMimeBodyPart
extends MimeBodyPart {
    private String encoding;

    public PreencodedMimeBodyPart(String string2) {
        this.encoding = string2;
    }

    @Override
    public String getEncoding() throws MessagingException {
        return this.encoding;
    }

    @Override
    protected void updateHeaders() throws MessagingException {
        super.updateHeaders();
        MimeBodyPart.setEncoding(this, this.encoding);
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException, MessagingException {
        LineOutputStream lineOutputStream = outputStream2 instanceof LineOutputStream ? (LineOutputStream)outputStream2 : new LineOutputStream(outputStream2);
        Enumeration enumeration = this.getAllHeaderLines();
        do {
            if (!enumeration.hasMoreElements()) {
                lineOutputStream.writeln();
                this.getDataHandler().writeTo(outputStream2);
                outputStream2.flush();
                return;
            }
            lineOutputStream.writeln((String)enumeration.nextElement());
        } while (true);
    }
}

