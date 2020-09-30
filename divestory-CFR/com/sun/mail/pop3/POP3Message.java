/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.pop3.Protocol;
import java.io.InputStream;
import java.util.Enumeration;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.SharedInputStream;

public class POP3Message
extends MimeMessage {
    static final String UNKNOWN = "UNKNOWN";
    private POP3Folder folder;
    private int hdrSize = -1;
    private int msgSize = -1;
    String uid = "UNKNOWN";

    public POP3Message(Folder folder, int n) throws MessagingException {
        super(folder, n);
        this.folder = (POP3Folder)folder;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private void loadHeaders() throws MessagingException {
        InputStream inputStream2;
        if (this.headers != null) {
            // MONITOREXIT : this
            return;
        }
        if (!((POP3Store)this.folder.getStore()).disableTop && (inputStream2 = this.folder.getProtocol().top(this.msgnum, 0)) != null) {
            InternetHeaders internetHeaders;
            this.hdrSize = inputStream2.available();
            this.headers = internetHeaders = new InternetHeaders(inputStream2);
            return;
        }
        this.getContentStream().close();
        // MONITOREXIT : this
    }

    @Override
    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override
    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override
    public Enumeration getAllHeaderLines() throws MessagingException {
        if (this.headers != null) return this.headers.getAllHeaderLines();
        this.loadHeaders();
        return this.headers.getAllHeaderLines();
    }

    @Override
    public Enumeration getAllHeaders() throws MessagingException {
        if (this.headers != null) return this.headers.getAllHeaders();
        this.loadHeaders();
        return this.headers.getAllHeaders();
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    protected InputStream getContentStream() throws MessagingException {
        Object object;
        block18 : {
            int n;
            int n2;
            block17 : {
                InternetHeaders internetHeaders;
                if (this.contentStream != null) {
                    // MONITOREXIT : this
                    return super.getContentStream();
                }
                object = this.folder.getProtocol();
                n2 = this.msgnum;
                n = this.msgSize > 0 ? this.msgSize + this.hdrSize : 0;
                if ((object = ((Protocol)object).retr(n2, n)) == null) {
                    this.expunged = true;
                    object = new MessageRemovedException();
                    throw object;
                }
                if (this.headers != null && !((POP3Store)this.folder.getStore()).forgetTopHeaders) break block17;
                this.headers = internetHeaders = new InternetHeaders((InputStream)object);
                this.hdrSize = (int)((SharedInputStream)object).getPosition();
                break block18;
            }
            block8 : do {
                n = 0;
                do {
                    block20 : {
                        block19 : {
                            if ((n2 = ((InputStream)object).read()) < 0 || n2 == 10) break block19;
                            if (n2 != 13) break block20;
                            if (((InputStream)object).available() > 0) {
                                ((InputStream)object).mark(1);
                                if (((InputStream)object).read() != 10) {
                                    ((InputStream)object).reset();
                                }
                            }
                        }
                        if (((InputStream)object).available() != 0 && n != 0) continue block8;
                        this.hdrSize = (int)((SharedInputStream)object).getPosition();
                        break block8;
                    }
                    ++n;
                } while (true);
                break;
            } while (true);
        }
        this.contentStream = ((SharedInputStream)object).newStream(this.hdrSize, -1L);
        return super.getContentStream();
    }

    @Override
    public String getHeader(String string2, String string3) throws MessagingException {
        if (this.headers != null) return this.headers.getHeader(string2, string3);
        this.loadHeaders();
        return this.headers.getHeader(string2, string3);
    }

    @Override
    public String[] getHeader(String string2) throws MessagingException {
        if (this.headers != null) return this.headers.getHeader(string2);
        this.loadHeaders();
        return this.headers.getHeader(string2);
    }

    @Override
    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        if (this.headers != null) return this.headers.getMatchingHeaderLines(arrstring);
        this.loadHeaders();
        return this.headers.getMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        if (this.headers != null) return this.headers.getMatchingHeaders(arrstring);
        this.loadHeaders();
        return this.headers.getMatchingHeaders(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        if (this.headers != null) return this.headers.getNonMatchingHeaderLines(arrstring);
        this.loadHeaders();
        return this.headers.getNonMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        if (this.headers != null) return this.headers.getNonMatchingHeaders(arrstring);
        this.loadHeaders();
        return this.headers.getNonMatchingHeaders(arrstring);
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public int getSize() throws MessagingException {
        if (this.msgSize >= 0) {
            int n = this.msgSize;
            // MONITOREXIT : this
            return n;
        }
        if (this.msgSize < 0) {
            if (this.headers == null) {
                this.loadHeaders();
            }
            this.msgSize = this.contentStream != null ? this.contentStream.available() : this.folder.getProtocol().list(this.msgnum) - this.hdrSize;
        }
        int n = this.msgSize;
        // MONITOREXIT : this
        return n;
    }

    public void invalidate(boolean bl) {
        synchronized (this) {
            this.content = null;
            this.contentStream = null;
            this.msgSize = -1;
            if (!bl) return;
            this.headers = null;
            this.hdrSize = -1;
            return;
        }
    }

    @Override
    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override
    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override
    public void setFlags(Flags flags, boolean bl) throws MessagingException {
        Flags flags2 = (Flags)this.flags.clone();
        super.setFlags(flags, bl);
        if (this.flags.equals(flags2)) return;
        this.folder.notifyMessageChangedListeners(1, this);
    }

    @Override
    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public InputStream top(int n) throws MessagingException {
        InputStream inputStream2 = this.folder.getProtocol().top(this.msgnum, n);
        // MONITOREXIT : this
        return inputStream2;
    }
}

