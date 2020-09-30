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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import javax.mail.internet.SharedInputStream;

public class MimeBodyPart
extends BodyPart
implements MimePart {
    static boolean cacheMultipart = true;
    private static boolean decodeFileName = false;
    private static boolean encodeFileName = false;
    private static boolean setContentTypeFileName = true;
    private static boolean setDefaultTextCharset = true;
    private Object cachedContent;
    protected byte[] content;
    protected InputStream contentStream;
    protected DataHandler dh;
    protected InternetHeaders headers;

    static {
        try {
            String string2 = System.getProperty("mail.mime.setdefaulttextcharset");
            boolean bl = false;
            boolean bl2 = string2 == null || !string2.equalsIgnoreCase("false");
            setDefaultTextCharset = bl2;
            string2 = System.getProperty("mail.mime.setcontenttypefilename");
            bl2 = string2 == null || !string2.equalsIgnoreCase("false");
            setContentTypeFileName = bl2;
            string2 = System.getProperty("mail.mime.encodefilename");
            bl2 = string2 != null && !string2.equalsIgnoreCase("false");
            encodeFileName = bl2;
            string2 = System.getProperty("mail.mime.decodefilename");
            bl2 = string2 != null && !string2.equalsIgnoreCase("false");
            decodeFileName = bl2;
            string2 = System.getProperty("mail.mime.cachemultipart");
            bl2 = string2 != null && string2.equalsIgnoreCase("false") ? bl : true;
            cacheMultipart = bl2;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public MimeBodyPart() {
        this.headers = new InternetHeaders();
    }

    public MimeBodyPart(InputStream object) throws MessagingException {
        InputStream inputStream2 = object;
        if (!(object instanceof ByteArrayInputStream)) {
            inputStream2 = object;
            if (!(object instanceof BufferedInputStream)) {
                inputStream2 = object;
                if (!(object instanceof SharedInputStream)) {
                    inputStream2 = new BufferedInputStream((InputStream)object);
                }
            }
        }
        this.headers = new InternetHeaders(inputStream2);
        if (inputStream2 instanceof SharedInputStream) {
            object = (SharedInputStream)((Object)inputStream2);
            this.contentStream = object.newStream(object.getPosition(), -1L);
            return;
        }
        try {
            this.content = ASCIIUtility.getBytes(inputStream2);
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Error reading input stream", iOException);
        }
    }

    public MimeBodyPart(InternetHeaders internetHeaders, byte[] arrby) throws MessagingException {
        this.headers = internetHeaders;
        this.content = arrby;
    }

    static String[] getContentLanguage(MimePart vector) throws MessagingException {
        if ((vector = vector.getHeader("Content-Language", null)) == null) {
            return null;
        }
        Object[] arrobject = new HeaderTokenizer((String)((Object)vector), "()<>@,;:\\\"\t []/?=");
        vector = new Vector<String>();
        do {
            HeaderTokenizer.Token token;
            int n;
            if ((n = (token = arrobject.next()).getType()) == -4) {
                if (vector.size() != 0) break;
                return null;
            }
            if (n != -1) continue;
            vector.addElement(token.getValue());
        } while (true);
        arrobject = new String[vector.size()];
        vector.copyInto(arrobject);
        return arrobject;
    }

    static String getDescription(MimePart object) throws MessagingException {
        if ((object = object.getHeader("Content-Description", null)) == null) {
            return null;
        }
        try {
            String string2 = MimeUtility.decodeText(MimeUtility.unfold((String)object));
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return object;
        }
    }

    static String getDisposition(MimePart object) throws MessagingException {
        if ((object = object.getHeader("Content-Disposition", null)) != null) return new ContentDisposition((String)object).getDisposition();
        return null;
    }

    static String getEncoding(MimePart object) throws MessagingException {
        int n;
        if ((object = object.getHeader("Content-Transfer-Encoding", null)) == null) {
            return null;
        }
        String string2 = ((String)object).trim();
        object = string2;
        if (string2.equalsIgnoreCase("7bit")) return object;
        object = string2;
        if (string2.equalsIgnoreCase("8bit")) return object;
        object = string2;
        if (string2.equalsIgnoreCase("quoted-printable")) return object;
        object = string2;
        if (string2.equalsIgnoreCase("binary")) return object;
        if (string2.equalsIgnoreCase("base64")) {
            return string2;
        }
        HeaderTokenizer headerTokenizer = new HeaderTokenizer(string2, "()<>@,;:\\\"\t []/?=");
        do {
            if ((n = ((HeaderTokenizer.Token)(object = headerTokenizer.next())).getType()) != -4) continue;
            return string2;
        } while (n != -1);
        return ((HeaderTokenizer.Token)object).getValue();
    }

    static String getFileName(MimePart object) throws MessagingException {
        String string2 = object.getHeader("Content-Disposition", null);
        string2 = string2 != null ? new ContentDisposition(string2).getParameter("filename") : null;
        Object object2 = string2;
        if (string2 == null) {
            object = object.getHeader("Content-Type", null);
            object2 = string2;
            if (object != null) {
                try {
                    object2 = new ContentType((String)object);
                    object2 = ((ContentType)object2).getParameter("name");
                }
                catch (ParseException parseException) {
                    object2 = string2;
                }
            }
        }
        object = object2;
        if (!decodeFileName) return object;
        object = object2;
        if (object2 == null) return object;
        try {
            return MimeUtility.decodeText((String)object2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new MessagingException("Can't decode filename", unsupportedEncodingException);
        }
    }

    static void invalidateContentHeaders(MimePart mimePart) throws MessagingException {
        mimePart.removeHeader("Content-Type");
        mimePart.removeHeader("Content-Transfer-Encoding");
    }

    static boolean isMimeType(MimePart mimePart, String string2) throws MessagingException {
        try {
            ContentType contentType = new ContentType(mimePart.getContentType());
            return contentType.match(string2);
        }
        catch (ParseException parseException) {
            return mimePart.getContentType().equalsIgnoreCase(string2);
        }
    }

    static void setContentLanguage(MimePart mimePart, String[] arrstring) throws MessagingException {
        StringBuffer stringBuffer = new StringBuffer(arrstring[0]);
        int n = 1;
        do {
            if (n >= arrstring.length) {
                mimePart.setHeader("Content-Language", stringBuffer.toString());
                return;
            }
            stringBuffer.append(',');
            stringBuffer.append(arrstring[n]);
            ++n;
        } while (true);
    }

    static void setDescription(MimePart mimePart, String string2, String string3) throws MessagingException {
        if (string2 == null) {
            mimePart.removeHeader("Content-Description");
            return;
        }
        try {
            mimePart.setHeader("Content-Description", MimeUtility.fold(21, MimeUtility.encodeText(string2, string3, null)));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new MessagingException("Encoding error", unsupportedEncodingException);
        }
    }

    static void setDisposition(MimePart mimePart, String string2) throws MessagingException {
        if (string2 == null) {
            mimePart.removeHeader("Content-Disposition");
            return;
        }
        String string3 = mimePart.getHeader("Content-Disposition", null);
        Object object = string2;
        if (string3 != null) {
            object = new ContentDisposition(string3);
            ((ContentDisposition)object).setDisposition(string2);
            object = ((ContentDisposition)object).toString();
        }
        mimePart.setHeader("Content-Disposition", (String)object);
    }

    static void setEncoding(MimePart mimePart, String string2) throws MessagingException {
        mimePart.setHeader("Content-Transfer-Encoding", string2);
    }

    static void setFileName(MimePart mimePart, String object) throws MessagingException {
        String string2 = object;
        if (encodeFileName) {
            string2 = object;
            if (object != null) {
                try {
                    string2 = MimeUtility.encodeText((String)object);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new MessagingException("Can't encode filename", unsupportedEncodingException);
                }
            }
        }
        String string3 = mimePart.getHeader("Content-Disposition", null);
        object = string3;
        if (string3 == null) {
            object = "attachment";
        }
        object = new ContentDisposition((String)object);
        ((ContentDisposition)object).setParameter("filename", string2);
        mimePart.setHeader("Content-Disposition", ((ContentDisposition)object).toString());
        if (!setContentTypeFileName) return;
        string3 = mimePart.getHeader("Content-Type", null);
        if (string3 == null) return;
        try {
            object = new ContentType(string3);
            ((ContentType)object).setParameter("name", string2);
            mimePart.setHeader("Content-Type", ((ContentType)object).toString());
            return;
        }
        catch (ParseException parseException) {
            return;
        }
    }

    static void setText(MimePart mimePart, String string2, String charSequence, String string3) throws MessagingException {
        String string4 = charSequence;
        if (charSequence == null) {
            string4 = MimeUtility.checkAscii(string2) != 1 ? MimeUtility.getDefaultMIMECharset() : "us-ascii";
        }
        charSequence = new StringBuilder("text/");
        ((StringBuilder)charSequence).append(string3);
        ((StringBuilder)charSequence).append("; charset=");
        ((StringBuilder)charSequence).append(MimeUtility.quote(string4, "()<>@,;:\\\"\t []/?="));
        mimePart.setContent(string2, ((StringBuilder)charSequence).toString());
    }

    static void updateHeaders(MimePart object) throws MessagingException {
        Object object2 = object.getDataHandler();
        if (object2 == null) {
            return;
        }
        try {
            boolean bl;
            Object object3;
            Object object4;
            Object object5;
            boolean bl2;
            block21 : {
                block20 : {
                    block19 : {
                        object5 = ((DataHandler)object2).getContentType();
                        object4 = object.getHeader("Content-Type");
                        bl = false;
                        bl2 = object4 == null;
                        object3 = new ContentType((String)object5);
                        if (!((ContentType)object3).match("multipart/*")) break block19;
                        if (object instanceof MimeBodyPart) {
                            object4 = (MimeBodyPart)object;
                            object4 = ((MimeBodyPart)object4).cachedContent != null ? ((MimeBodyPart)object4).cachedContent : ((DataHandler)object2).getContent();
                        } else if (object instanceof MimeMessage) {
                            object4 = (MimeMessage)object;
                            object4 = ((MimeMessage)object4).cachedContent != null ? ((MimeMessage)object4).cachedContent : ((DataHandler)object2).getContent();
                        } else {
                            object4 = ((DataHandler)object2).getContent();
                        }
                        if (!(object4 instanceof MimeMultipart)) {
                            object = new StringBuilder("MIME part of type \"");
                            ((StringBuilder)object).append((String)object5);
                            ((StringBuilder)object).append("\" contains object of type ");
                            ((StringBuilder)object).append(object4.getClass().getName());
                            ((StringBuilder)object).append(" instead of MimeMultipart");
                            object3 = new MessagingException(((StringBuilder)object).toString());
                            throw object3;
                        }
                        ((MimeMultipart)object4).updateHeaders();
                        break block20;
                    }
                    if (!((ContentType)object3).match("message/rfc822")) break block21;
                }
                bl = true;
            }
            object4 = object5;
            if (!bl) {
                if (object.getHeader("Content-Transfer-Encoding") == null) {
                    MimeBodyPart.setEncoding((MimePart)object, MimeUtility.getEncoding((DataHandler)object2));
                }
                object4 = object5;
                if (bl2) {
                    object4 = object5;
                    if (setDefaultTextCharset) {
                        object4 = object5;
                        if (((ContentType)object3).match("text/*")) {
                            object4 = object5;
                            if (((ContentType)object3).getParameter("charset") == null) {
                                object4 = object.getEncoding();
                                object4 = object4 != null && ((String)object4).equalsIgnoreCase("7bit") ? "us-ascii" : MimeUtility.getDefaultMIMECharset();
                                ((ContentType)object3).setParameter("charset", (String)object4);
                                object4 = ((ContentType)object3).toString();
                            }
                        }
                    }
                }
            }
            if (!bl2) return;
            object2 = object.getHeader("Content-Disposition", null);
            object5 = object4;
            if (object2 != null) {
                object5 = new ContentDisposition((String)object2);
                object2 = ((ContentDisposition)object5).getParameter("filename");
                object5 = object4;
                if (object2 != null) {
                    ((ContentType)object3).setParameter("name", (String)object2);
                    object5 = ((ContentType)object3).toString();
                }
            }
            object.setHeader("Content-Type", (String)object5);
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("IOException updating headers", iOException);
        }
    }

    static void writeTo(MimePart mimePart, OutputStream outputStream2, String[] object) throws IOException, MessagingException {
        LineOutputStream lineOutputStream = outputStream2 instanceof LineOutputStream ? (LineOutputStream)outputStream2 : new LineOutputStream(outputStream2);
        object = mimePart.getNonMatchingHeaderLines((String[])object);
        do {
            if (!object.hasMoreElements()) {
                lineOutputStream.writeln();
                outputStream2 = MimeUtility.encode(outputStream2, mimePart.getEncoding());
                mimePart.getDataHandler().writeTo(outputStream2);
                outputStream2.flush();
                return;
            }
            lineOutputStream.writeln((String)object.nextElement());
        } while (true);
    }

    @Override
    public void addHeader(String string2, String string3) throws MessagingException {
        this.headers.addHeader(string2, string3);
    }

    @Override
    public void addHeaderLine(String string2) throws MessagingException {
        this.headers.addHeaderLine(string2);
    }

    public void attachFile(File object) throws IOException, MessagingException {
        object = new FileDataSource((File)object);
        this.setDataHandler(new DataHandler((DataSource)object));
        this.setFileName(((FileDataSource)object).getName());
    }

    public void attachFile(String string2) throws IOException, MessagingException {
        this.attachFile(new File(string2));
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
    public Object getContent() throws IOException, MessagingException {
        Object object = this.cachedContent;
        if (object != null) {
            return object;
        }
        try {
            object = this.getDataHandler().getContent();
            if (!cacheMultipart) return object;
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
        return new ByteArrayInputStream(this.content);
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
        if (this.dh != null) return this.dh;
        this.dh = new DataHandler(new MimePartDataSource(this));
        return this.dh;
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
    public boolean isMimeType(String string2) throws MessagingException {
        return MimeBodyPart.isMimeType(this, string2);
    }

    @Override
    public void removeHeader(String string2) throws MessagingException {
        this.headers.removeHeader(string2);
    }

    /*
     * Exception decompiling
     */
    public void saveFile(File var1_1) throws IOException, MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 13[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public void saveFile(String string2) throws IOException, MessagingException {
        this.saveFile(new File(string2));
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
        this.dh = dataHandler;
        this.cachedContent = null;
        MimeBodyPart.invalidateContentHeaders(this);
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

    @Override
    public void setHeader(String string2, String string3) throws MessagingException {
        this.headers.setHeader(string2, string3);
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

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException, MessagingException {
        MimeBodyPart.writeTo(this, outputStream2, null);
    }
}

