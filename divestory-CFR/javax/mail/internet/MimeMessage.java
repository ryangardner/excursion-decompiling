/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.LineOutputStream;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MailDateFormat;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.NewsAddress;
import javax.mail.internet.SharedInputStream;
import javax.mail.internet.UniqueValue;
import javax.mail.util.SharedByteArrayInputStream;

public class MimeMessage
extends Message
implements MimePart {
    private static final Flags answeredFlag;
    private static MailDateFormat mailDateFormat;
    Object cachedContent;
    protected byte[] content;
    protected InputStream contentStream;
    protected DataHandler dh;
    protected Flags flags;
    protected InternetHeaders headers;
    protected boolean modified = false;
    protected boolean saved = false;
    private boolean strict = true;

    static {
        mailDateFormat = new MailDateFormat();
        answeredFlag = new Flags(Flags.Flag.ANSWERED);
    }

    protected MimeMessage(Folder folder, int n) {
        super(folder, n);
        this.flags = new Flags();
        this.saved = true;
        this.initStrict();
    }

    protected MimeMessage(Folder folder, InputStream inputStream2, int n) throws MessagingException {
        this(folder, n);
        this.initStrict();
        this.parse(inputStream2);
    }

    protected MimeMessage(Folder folder, InternetHeaders internetHeaders, byte[] arrby, int n) throws MessagingException {
        this(folder, n);
        this.headers = internetHeaders;
        this.content = arrby;
        this.initStrict();
    }

    public MimeMessage(Session session) {
        super(session);
        this.modified = true;
        this.headers = new InternetHeaders();
        this.flags = new Flags();
        this.initStrict();
    }

    public MimeMessage(Session session, InputStream inputStream2) throws MessagingException {
        super(session);
        this.flags = new Flags();
        this.initStrict();
        this.parse(inputStream2);
        this.saved = true;
    }

    public MimeMessage(MimeMessage object) throws MessagingException {
        super(((MimeMessage)object).session);
        this.flags = ((MimeMessage)object).getFlags();
        int n = ((MimeMessage)object).getSize();
        ByteArrayOutputStream byteArrayOutputStream = n > 0 ? new ByteArrayOutputStream(n) : new ByteArrayOutputStream();
        try {
            this.strict = ((MimeMessage)object).strict;
            ((MimeMessage)object).writeTo(byteArrayOutputStream);
            byteArrayOutputStream.close();
            object = new SharedByteArrayInputStream(byteArrayOutputStream.toByteArray());
            this.parse((InputStream)object);
            ((ByteArrayInputStream)object).close();
            this.saved = true;
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("IOException while copying message", iOException);
        }
    }

    private void addAddressHeader(String string2, Address[] object) throws MessagingException {
        if ((object = InternetAddress.toString(object)) == null) {
            return;
        }
        this.addHeader(string2, (String)object);
    }

    private Address[] eliminateDuplicates(Vector arraddress, Address[] arraddress2) {
        if (arraddress2 == null) {
            return null;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        block0 : do {
            int n4;
            if (n2 >= arraddress2.length) {
                arraddress = arraddress2;
                if (n3 == 0) return arraddress;
                arraddress = arraddress2 instanceof InternetAddress[] ? new InternetAddress[arraddress2.length - n3] : new Address[arraddress2.length - n3];
                n3 = 0;
                n2 = n;
                do {
                    if (n2 >= arraddress2.length) {
                        return arraddress;
                    }
                    n4 = n3;
                    if (arraddress2[n2] != null) {
                        arraddress[n3] = arraddress2[n2];
                        n4 = n3 + 1;
                    }
                    ++n2;
                    n3 = n4;
                } while (true);
            }
            n4 = 0;
            do {
                block13 : {
                    block12 : {
                        block11 : {
                            if (n4 < arraddress.size()) break block11;
                            n4 = 0;
                            break block12;
                        }
                        if (!((InternetAddress)arraddress.elementAt(n4)).equals(arraddress2[n2])) break block13;
                        ++n3;
                        arraddress2[n2] = null;
                        n4 = 1;
                    }
                    if (n4 == 0) {
                        arraddress.addElement(arraddress2[n2]);
                    }
                    ++n2;
                    continue block0;
                }
                ++n4;
            } while (true);
            break;
        } while (true);
    }

    private Address[] getAddressHeader(String arrinternetAddress) throws MessagingException {
        if ((arrinternetAddress = this.getHeader((String)arrinternetAddress, ",")) != null) return InternetAddress.parseHeader((String)arrinternetAddress, this.strict);
        return null;
    }

    private String getHeaderName(Message.RecipientType object) throws MessagingException {
        if (object == Message.RecipientType.TO) {
            return "To";
        }
        if (object == Message.RecipientType.CC) {
            return "Cc";
        }
        if (object == Message.RecipientType.BCC) {
            return "Bcc";
        }
        if (object != RecipientType.NEWSGROUPS) throw new MessagingException("Invalid Recipient Type");
        return "Newsgroups";
    }

    private void initStrict() {
        if (this.session == null) return;
        String string2 = this.session.getProperty("mail.mime.address.strict");
        boolean bl = string2 == null || !string2.equalsIgnoreCase("false");
        this.strict = bl;
    }

    private void setAddressHeader(String string2, Address[] object) throws MessagingException {
        if ((object = InternetAddress.toString(object)) == null) {
            this.removeHeader(string2);
            return;
        }
        this.setHeader(string2, (String)object);
    }

    @Override
    public void addFrom(Address[] arraddress) throws MessagingException {
        this.addAddressHeader("From", arraddress);
    }

    @Override
    public void addHeader(String string2, String string3) throws MessagingException {
        this.headers.addHeader(string2, string3);
    }

    @Override
    public void addHeaderLine(String string2) throws MessagingException {
        this.headers.addHeaderLine(string2);
    }

    public void addRecipients(Message.RecipientType recipientType, String string2) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            if (string2 == null) return;
            if (string2.length() == 0) return;
            this.addHeader("Newsgroups", string2);
            return;
        }
        this.addAddressHeader(this.getHeaderName(recipientType), InternetAddress.parse(string2));
    }

    @Override
    public void addRecipients(Message.RecipientType object, Address[] arraddress) throws MessagingException {
        if (object == RecipientType.NEWSGROUPS) {
            object = NewsAddress.toString(arraddress);
            if (object == null) return;
            this.addHeader("Newsgroups", (String)object);
            return;
        }
        this.addAddressHeader(this.getHeaderName((Message.RecipientType)object), arraddress);
    }

    protected InternetHeaders createInternetHeaders(InputStream inputStream2) throws MessagingException {
        return new InternetHeaders(inputStream2);
    }

    protected MimeMessage createMimeMessage(Session session) throws MessagingException {
        return new MimeMessage(session);
    }

    @Override
    public Enumeration getAllHeaderLines() throws MessagingException {
        return this.headers.getAllHeaderLines();
    }

    @Override
    public Enumeration getAllHeaders() throws MessagingException {
        return this.headers.getAllHeaders();
    }

    @Override
    public Address[] getAllRecipients() throws MessagingException {
        Address[] arraddress = super.getAllRecipients();
        Address[] arraddress2 = this.getRecipients(RecipientType.NEWSGROUPS);
        if (arraddress2 == null) {
            return arraddress;
        }
        if (arraddress == null) {
            return arraddress2;
        }
        Address[] arraddress3 = new Address[arraddress.length + arraddress2.length];
        System.arraycopy(arraddress, 0, arraddress3, 0, arraddress.length);
        System.arraycopy(arraddress2, 0, arraddress3, arraddress.length, arraddress2.length);
        return arraddress3;
    }

    @Override
    public Object getContent() throws IOException, MessagingException {
        Object object = this.cachedContent;
        if (object != null) {
            return object;
        }
        try {
            object = this.getDataHandler().getContent();
            if (!MimeBodyPart.cacheMultipart) return object;
        }
        catch (MessageRemovedIOException messageRemovedIOException) {
            throw new MessageRemovedException(messageRemovedIOException.getMessage());
        }
        catch (FolderClosedIOException folderClosedIOException) {
            throw new FolderClosedException(folderClosedIOException.getFolder(), folderClosedIOException.getMessage());
        }
        if (!(object instanceof Multipart)) {
            if (!(object instanceof Message)) return object;
        }
        if (this.content == null) {
            if (this.contentStream == null) return object;
        }
        this.cachedContent = object;
        return object;
    }

    @Override
    public String getContentID() throws MessagingException {
        return this.getHeader("Content-Id", null);
    }

    @Override
    public String[] getContentLanguage() throws MessagingException {
        return MimeBodyPart.getContentLanguage(this);
    }

    @Override
    public String getContentMD5() throws MessagingException {
        return this.getHeader("Content-MD5", null);
    }

    protected InputStream getContentStream() throws MessagingException {
        InputStream inputStream2 = this.contentStream;
        if (inputStream2 != null) {
            return ((SharedInputStream)((Object)inputStream2)).newStream(0L, -1L);
        }
        if (this.content == null) throw new MessagingException("No content");
        return new SharedByteArrayInputStream(this.content);
    }

    @Override
    public String getContentType() throws MessagingException {
        String string2;
        String string3 = string2 = this.getHeader("Content-Type", null);
        if (string2 != null) return string3;
        return "text/plain";
    }

    @Override
    public DataHandler getDataHandler() throws MessagingException {
        synchronized (this) {
            DataHandler dataHandler;
            if (this.dh != null) return this.dh;
            Object object = new MimePartDataSource(this);
            this.dh = dataHandler = new DataHandler((DataSource)object);
            return this.dh;
        }
    }

    @Override
    public String getDescription() throws MessagingException {
        return MimeBodyPart.getDescription(this);
    }

    @Override
    public String getDisposition() throws MessagingException {
        return MimeBodyPart.getDisposition(this);
    }

    @Override
    public String getEncoding() throws MessagingException {
        return MimeBodyPart.getEncoding(this);
    }

    @Override
    public String getFileName() throws MessagingException {
        return MimeBodyPart.getFileName(this);
    }

    @Override
    public Flags getFlags() throws MessagingException {
        synchronized (this) {
            return (Flags)this.flags.clone();
        }
    }

    @Override
    public Address[] getFrom() throws MessagingException {
        Address[] arraddress;
        Address[] arraddress2 = arraddress = this.getAddressHeader("From");
        if (arraddress != null) return arraddress2;
        return this.getAddressHeader("Sender");
    }

    @Override
    public String getHeader(String string2, String string3) throws MessagingException {
        return this.headers.getHeader(string2, string3);
    }

    @Override
    public String[] getHeader(String string2) throws MessagingException {
        return this.headers.getHeader(string2);
    }

    @Override
    public InputStream getInputStream() throws IOException, MessagingException {
        return this.getDataHandler().getInputStream();
    }

    @Override
    public int getLineCount() throws MessagingException {
        return -1;
    }

    @Override
    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        return this.headers.getMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        return this.headers.getMatchingHeaders(arrstring);
    }

    public String getMessageID() throws MessagingException {
        return this.getHeader("Message-ID", null);
    }

    @Override
    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        return this.headers.getNonMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        return this.headers.getNonMatchingHeaders(arrstring);
    }

    public InputStream getRawInputStream() throws MessagingException {
        return this.getContentStream();
    }

    @Override
    public Date getReceivedDate() throws MessagingException {
        return null;
    }

    @Override
    public Address[] getRecipients(Message.RecipientType object) throws MessagingException {
        if (object != RecipientType.NEWSGROUPS) return this.getAddressHeader(this.getHeaderName((Message.RecipientType)object));
        object = this.getHeader("Newsgroups", ",");
        if (object != null) return NewsAddress.parse((String)object);
        return null;
    }

    @Override
    public Address[] getReplyTo() throws MessagingException {
        Address[] arraddress;
        Address[] arraddress2 = arraddress = this.getAddressHeader("Reply-To");
        if (arraddress != null) return arraddress2;
        return this.getFrom();
    }

    public Address getSender() throws MessagingException {
        Address[] arraddress = this.getAddressHeader("Sender");
        if (arraddress == null) return null;
        if (arraddress.length != 0) return arraddress[0];
        return null;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public Date getSentDate() throws MessagingException {
        Object object = this.getHeader("Date", null);
        if (object == null) return null;
        try {
            MailDateFormat mailDateFormat = MimeMessage.mailDateFormat;
            // MONITORENTER : mailDateFormat
        }
        catch (ParseException parseException) {
            return null;
        }
        object = mailDateFormat.parse((String)object);
        // MONITOREXIT : mailDateFormat
        return object;
    }

    @Override
    public int getSize() throws MessagingException {
        Object object = this.content;
        if (object != null) {
            return ((byte[])object).length;
        }
        object = this.contentStream;
        if (object == null) return -1;
        try {
            int n = ((InputStream)object).available();
            if (n <= 0) return -1;
            return n;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    @Override
    public String getSubject() throws MessagingException {
        String string2 = this.getHeader("Subject", null);
        if (string2 == null) {
            return null;
        }
        try {
            String string3 = MimeUtility.decodeText(MimeUtility.unfold(string2));
            return string3;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return string2;
        }
    }

    @Override
    public boolean isMimeType(String string2) throws MessagingException {
        return MimeBodyPart.isMimeType(this, string2);
    }

    @Override
    public boolean isSet(Flags.Flag flag) throws MessagingException {
        synchronized (this) {
            return this.flags.contains(flag);
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    protected void parse(InputStream object) throws MessagingException {
        Object inputStream2 = object;
        if (!(object instanceof ByteArrayInputStream)) {
            inputStream2 = object;
            if (!(object instanceof BufferedInputStream)) {
                inputStream2 = object;
                if (!(object instanceof SharedInputStream)) {
                    inputStream2 = new BufferedInputStream((InputStream)object);
                }
            }
        }
        this.headers = this.createInternetHeaders((InputStream)inputStream2);
        if (inputStream2 instanceof SharedInputStream) {
            object = (SharedInputStream)inputStream2;
            this.contentStream = object.newStream(object.getPosition(), -1L);
        } else {
            this.content = ASCIIUtility.getBytes((InputStream)inputStream2);
        }
        this.modified = false;
        return;
        catch (IOException iOException) {
            throw new MessagingException("IOException", iOException);
        }
    }

    @Override
    public void removeHeader(String string2) throws MessagingException {
        this.headers.removeHeader(string2);
    }

    @Override
    public Message reply(boolean bl) throws MessagingException {
        Object object;
        MimeMessage mimeMessage = this.createMimeMessage(this.session);
        Object object2 = this.getHeader("Subject", null);
        if (object2 != null) {
            object = object2;
            if (!object2.regionMatches(true, 0, "Re: ", 0, 4)) {
                object = new StringBuilder("Re: ");
                object.append((String)object2);
                object = object.toString();
            }
            mimeMessage.setHeader("Subject", (String)object);
        }
        Address[] arraddress = this.getReplyTo();
        mimeMessage.setRecipients(Message.RecipientType.TO, arraddress);
        if (bl) {
            object2 = new Vector();
            object = InternetAddress.getLocalAddress(this.session);
            if (object != null) {
                object2.addElement(object);
            }
            object = this.session != null ? this.session.getProperty("mail.alternates") : null;
            boolean bl2 = false;
            if (object != null) {
                this.eliminateDuplicates((Vector)object2, InternetAddress.parse((String)object, false));
            }
            object = this.session != null ? this.session.getProperty("mail.replyallcc") : null;
            boolean bl3 = bl2;
            if (object != null) {
                bl3 = bl2;
                if (object.equalsIgnoreCase("true")) {
                    bl3 = true;
                }
            }
            this.eliminateDuplicates((Vector)object2, arraddress);
            object = this.eliminateDuplicates((Vector)object2, this.getRecipients(Message.RecipientType.TO));
            if (object != null && ((Address[])object).length > 0) {
                if (bl3) {
                    mimeMessage.addRecipients(Message.RecipientType.CC, (Address[])object);
                } else {
                    mimeMessage.addRecipients(Message.RecipientType.TO, (Address[])object);
                }
            }
            if ((object = this.eliminateDuplicates((Vector)object2, this.getRecipients(Message.RecipientType.CC))) != null && ((Address[])object).length > 0) {
                mimeMessage.addRecipients(Message.RecipientType.CC, (Address[])object);
            }
            if ((object = this.getRecipients(RecipientType.NEWSGROUPS)) != null && ((Address[])object).length > 0) {
                mimeMessage.setRecipients((Message.RecipientType)RecipientType.NEWSGROUPS, (Address[])object);
            }
        }
        if ((arraddress = this.getHeader("Message-Id", null)) != null) {
            mimeMessage.setHeader("In-Reply-To", (String)arraddress);
        }
        object = object2 = this.getHeader("References", " ");
        if (object2 == null) {
            object = this.getHeader("In-Reply-To", " ");
        }
        if (arraddress != null) {
            object2 = arraddress;
            if (object != null) {
                object = new StringBuilder(String.valueOf(MimeUtility.unfold((String)object)));
                object.append(" ");
                object.append((String)arraddress);
                object2 = object.toString();
            }
        } else {
            object2 = object;
        }
        if (object2 != null) {
            mimeMessage.setHeader("References", MimeUtility.fold(12, (String)object2));
        }
        try {
            this.setFlags(answeredFlag, true);
            return mimeMessage;
        }
        catch (MessagingException messagingException) {
            return mimeMessage;
        }
    }

    @Override
    public void saveChanges() throws MessagingException {
        this.modified = true;
        this.saved = true;
        this.updateHeaders();
    }

    @Override
    public void setContent(Object object, String string2) throws MessagingException {
        if (object instanceof Multipart) {
            this.setContent((Multipart)object);
            return;
        }
        this.setDataHandler(new DataHandler(object, string2));
    }

    @Override
    public void setContent(Multipart multipart) throws MessagingException {
        this.setDataHandler(new DataHandler(multipart, multipart.getContentType()));
        multipart.setParent(this);
    }

    public void setContentID(String string2) throws MessagingException {
        if (string2 == null) {
            this.removeHeader("Content-ID");
            return;
        }
        this.setHeader("Content-ID", string2);
    }

    @Override
    public void setContentLanguage(String[] arrstring) throws MessagingException {
        MimeBodyPart.setContentLanguage(this, arrstring);
    }

    @Override
    public void setContentMD5(String string2) throws MessagingException {
        this.setHeader("Content-MD5", string2);
    }

    @Override
    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        synchronized (this) {
            this.dh = dataHandler;
            this.cachedContent = null;
            MimeBodyPart.invalidateContentHeaders(this);
            return;
        }
    }

    @Override
    public void setDescription(String string2) throws MessagingException {
        this.setDescription(string2, null);
    }

    public void setDescription(String string2, String string3) throws MessagingException {
        MimeBodyPart.setDescription(this, string2, string3);
    }

    @Override
    public void setDisposition(String string2) throws MessagingException {
        MimeBodyPart.setDisposition(this, string2);
    }

    @Override
    public void setFileName(String string2) throws MessagingException {
        MimeBodyPart.setFileName(this, string2);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public void setFlags(Flags flags, boolean bl) throws MessagingException {
        synchronized (this) {
            void var2_2;
            if (var2_2 != false) {
                this.flags.add(flags);
            } else {
                this.flags.remove(flags);
            }
            return;
        }
    }

    @Override
    public void setFrom() throws MessagingException {
        InternetAddress internetAddress = InternetAddress.getLocalAddress(this.session);
        if (internetAddress == null) throw new MessagingException("No From address");
        this.setFrom(internetAddress);
    }

    @Override
    public void setFrom(Address address) throws MessagingException {
        if (address == null) {
            this.removeHeader("From");
            return;
        }
        this.setHeader("From", address.toString());
    }

    @Override
    public void setHeader(String string2, String string3) throws MessagingException {
        this.headers.setHeader(string2, string3);
    }

    public void setRecipients(Message.RecipientType recipientType, String string2) throws MessagingException {
        if (recipientType != RecipientType.NEWSGROUPS) {
            this.setAddressHeader(this.getHeaderName(recipientType), InternetAddress.parse(string2));
            return;
        }
        if (string2 != null && string2.length() != 0) {
            this.setHeader("Newsgroups", string2);
            return;
        }
        this.removeHeader("Newsgroups");
    }

    @Override
    public void setRecipients(Message.RecipientType recipientType, Address[] arraddress) throws MessagingException {
        if (recipientType != RecipientType.NEWSGROUPS) {
            this.setAddressHeader(this.getHeaderName(recipientType), arraddress);
            return;
        }
        if (arraddress != null && arraddress.length != 0) {
            this.setHeader("Newsgroups", NewsAddress.toString(arraddress));
            return;
        }
        this.removeHeader("Newsgroups");
    }

    @Override
    public void setReplyTo(Address[] arraddress) throws MessagingException {
        this.setAddressHeader("Reply-To", arraddress);
    }

    public void setSender(Address address) throws MessagingException {
        if (address == null) {
            this.removeHeader("Sender");
            return;
        }
        this.setHeader("Sender", address.toString());
    }

    @Override
    public void setSentDate(Date date) throws MessagingException {
        if (date == null) {
            this.removeHeader("Date");
            return;
        }
        MailDateFormat mailDateFormat = MimeMessage.mailDateFormat;
        synchronized (mailDateFormat) {
            this.setHeader("Date", MimeMessage.mailDateFormat.format(date));
            return;
        }
    }

    @Override
    public void setSubject(String string2) throws MessagingException {
        this.setSubject(string2, null);
    }

    public void setSubject(String string2, String string3) throws MessagingException {
        if (string2 == null) {
            this.removeHeader("Subject");
            return;
        }
        try {
            this.setHeader("Subject", MimeUtility.fold(9, MimeUtility.encodeText(string2, string3, null)));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new MessagingException("Encoding error", unsupportedEncodingException);
        }
    }

    @Override
    public void setText(String string2) throws MessagingException {
        this.setText(string2, null);
    }

    @Override
    public void setText(String string2, String string3) throws MessagingException {
        MimeBodyPart.setText(this, string2, string3, "plain");
    }

    @Override
    public void setText(String string2, String string3, String string4) throws MessagingException {
        MimeBodyPart.setText(this, string2, string3, string4);
    }

    protected void updateHeaders() throws MessagingException {
        MimeBodyPart.updateHeaders(this);
        this.setHeader("MIME-Version", "1.0");
        this.updateMessageID();
        if (this.cachedContent == null) return;
        this.dh = new DataHandler(this.cachedContent, this.getContentType());
        this.cachedContent = null;
        this.content = null;
        InputStream inputStream2 = this.contentStream;
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            }
            catch (IOException iOException) {}
        }
        this.contentStream = null;
    }

    protected void updateMessageID() throws MessagingException {
        StringBuilder stringBuilder = new StringBuilder("<");
        stringBuilder.append(UniqueValue.getUniqueMessageIDValue(this.session));
        stringBuilder.append(">");
        this.setHeader("Message-ID", stringBuilder.toString());
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException, MessagingException {
        this.writeTo(outputStream2, null);
    }

    public void writeTo(OutputStream outputStream2, String[] object) throws IOException, MessagingException {
        block6 : {
            if (!this.saved) {
                this.saveChanges();
            }
            if (this.modified) {
                MimeBodyPart.writeTo(this, outputStream2, (String[])object);
                return;
            }
            Object object2 = this.getNonMatchingHeaderLines((String[])object);
            object = new LineOutputStream(outputStream2);
            do {
                if (!object2.hasMoreElements()) {
                    ((LineOutputStream)object).writeln();
                    object = this.content;
                    if (object == null) break;
                    outputStream2.write((byte[])object);
                    break block6;
                }
                ((LineOutputStream)object).writeln((String)object2.nextElement());
            } while (true);
            object2 = this.getContentStream();
            object = new byte[8192];
            do {
                int n;
                if ((n = ((InputStream)object2).read((byte[])object)) <= 0) {
                    ((InputStream)object2).close();
                    object = null;
                    break;
                }
                outputStream2.write((byte[])object, 0, n);
            } while (true);
        }
        outputStream2.flush();
    }

    public static class RecipientType
    extends Message.RecipientType {
        public static final RecipientType NEWSGROUPS = new RecipientType("Newsgroups");
        private static final long serialVersionUID = -5468290701714395543L;

        protected RecipientType(String string2) {
            super(string2);
        }

        @Override
        protected Object readResolve() throws ObjectStreamException {
            if (!this.type.equals("Newsgroups")) return super.readResolve();
            return NEWSGROUPS;
        }
    }

}

