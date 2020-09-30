/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import javax.mail.util.SharedByteArrayInputStream;

public class ByteArrayDataSource
implements DataSource {
    private byte[] data;
    private int len = -1;
    private String name = "";
    private String type;

    public ByteArrayDataSource(InputStream arrby, String string2) throws IOException {
        DSByteArrayOutputStream dSByteArrayOutputStream = new DSByteArrayOutputStream();
        byte[] arrby2 = new byte[8192];
        do {
            int n;
            if ((n = arrby.read(arrby2)) <= 0) {
                this.data = dSByteArrayOutputStream.getBuf();
                this.len = n = dSByteArrayOutputStream.getCount();
                if (this.data.length - n > 262144) {
                    arrby = dSByteArrayOutputStream.toByteArray();
                    this.data = arrby;
                    this.len = arrby.length;
                }
                this.type = string2;
                return;
            }
            dSByteArrayOutputStream.write(arrby2, 0, n);
        } while (true);
    }

    public ByteArrayDataSource(String string2, String string3) throws IOException {
        Object object;
        try {
            object = new ContentType(string3);
            object = ((ContentType)object).getParameter("charset");
        }
        catch (ParseException parseException) {
            object = null;
        }
        Object object2 = object;
        if (object == null) {
            object2 = MimeUtility.getDefaultJavaCharset();
        }
        this.data = string2.getBytes((String)object2);
        this.type = string3;
    }

    public ByteArrayDataSource(byte[] arrby, String string2) {
        this.data = arrby;
        this.type = string2;
    }

    @Override
    public String getContentType() {
        return this.type;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        byte[] arrby = this.data;
        if (arrby == null) throw new IOException("no data");
        if (this.len >= 0) return new SharedByteArrayInputStream(this.data, 0, this.len);
        this.len = arrby.length;
        return new SharedByteArrayInputStream(this.data, 0, this.len);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("cannot do this");
    }

    public void setName(String string2) {
        this.name = string2;
    }

    static class DSByteArrayOutputStream
    extends ByteArrayOutputStream {
        DSByteArrayOutputStream() {
        }

        public byte[] getBuf() {
            return this.buf;
        }

        public int getCount() {
            return this.count;
        }
    }

}

