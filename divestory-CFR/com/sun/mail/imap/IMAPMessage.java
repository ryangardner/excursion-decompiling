/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMultipartDataSource;
import com.sun.mail.imap.IMAPNestedMessage;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.Utility;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParameterList;

public class IMAPMessage
extends MimeMessage {
    private static String EnvelopeCmd = "ENVELOPE INTERNALDATE RFC822.SIZE";
    protected BODYSTRUCTURE bs;
    private String description;
    protected ENVELOPE envelope;
    private boolean headersLoaded = false;
    private Hashtable loadedHeaders;
    private boolean peek;
    private Date receivedDate;
    protected String sectionId;
    private int seqnum;
    private int size = -1;
    private String subject;
    private String type;
    private long uid = -1L;

    protected IMAPMessage(IMAPFolder iMAPFolder, int n, int n2) {
        super(iMAPFolder, n);
        this.seqnum = n2;
        this.flags = null;
    }

    protected IMAPMessage(Session session) {
        super(session);
    }

    private BODYSTRUCTURE _getBodyStructure() {
        return this.bs;
    }

    private ENVELOPE _getEnvelope() {
        return this.envelope;
    }

    private Flags _getFlags() {
        return this.flags;
    }

    private InternetAddress[] aaclone(InternetAddress[] arrinternetAddress) {
        if (arrinternetAddress != null) return (InternetAddress[])arrinternetAddress.clone();
        return null;
    }

    private boolean areHeadersLoaded() {
        synchronized (this) {
            return this.headersLoaded;
        }
    }

    private static String craftHeaderCmd(IMAPProtocol iMAPProtocol, String[] arrstring) {
        StringBuffer stringBuffer = iMAPProtocol.isREV1() ? new StringBuffer("BODY.PEEK[HEADER.FIELDS (") : new StringBuffer("RFC822.HEADER.LINES (");
        int n = 0;
        do {
            if (n >= arrstring.length) {
                if (!iMAPProtocol.isREV1()) break;
                stringBuffer.append(")]");
                return stringBuffer.toString();
            }
            if (n > 0) {
                stringBuffer.append(" ");
            }
            stringBuffer.append(arrstring[n]);
            ++n;
        } while (true);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /*
     * Exception decompiling
     */
    static void fetch(IMAPFolder var0, Message[] var1_2, FetchProfile var2_5) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[UNCONDITIONALDOLOOP]], but top level block is 11[UNCONDITIONALDOLOOP]
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

    private boolean isHeaderLoaded(String string2) {
        synchronized (this) {
            boolean bl = this.headersLoaded;
            if (bl) {
                return true;
            }
            bl = this.loadedHeaders != null ? this.loadedHeaders.containsKey(string2.toUpperCase(Locale.ENGLISH)) : false;
            return bl;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private void loadBODYSTRUCTURE() throws MessagingException {
        synchronized (this) {
            Object object = this.bs;
            if (object != null) {
                return;
            }
            object = this.getMessageCacheLock();
            synchronized (object) {
                try {
                    Object object2;
                    block11 : {
                        try {
                            object2 = this.getProtocol();
                            this.checkExpunged();
                            this.bs = object2 = ((IMAPProtocol)object2).fetchBodyStructure(this.getSequenceNumber());
                            if (object2 == null) break block11;
                        }
                        catch (ProtocolException protocolException) {
                            this.forceCheckExpunged();
                            MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                            throw messagingException;
                        }
                        catch (ConnectionException connectionException) {
                            FolderClosedException folderClosedException = new FolderClosedException(this.folder, connectionException.getMessage());
                            throw folderClosedException;
                        }
                        return;
                    }
                    this.forceCheckExpunged();
                    object2 = new MessagingException("Unable to load BODYSTRUCTURE");
                    throw object2;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void loadEnvelope() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 13[UNCONDITIONALDOLOOP]
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

    /*
     * Enabled unnecessary exception pruning
     */
    private void loadFlags() throws MessagingException {
        synchronized (this) {
            Object object = this.flags;
            if (object != null) {
                return;
            }
            object = this.getMessageCacheLock();
            synchronized (object) {
                try {
                    try {
                        IMAPProtocol iMAPProtocol = this.getProtocol();
                        this.checkExpunged();
                        this.flags = iMAPProtocol.fetchFlags(this.getSequenceNumber());
                        return;
                    }
                    catch (ProtocolException protocolException) {
                        this.forceCheckExpunged();
                        MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                        throw messagingException;
                    }
                    catch (ConnectionException connectionException) {
                        FolderClosedException folderClosedException = new FolderClosedException(this.folder, connectionException.getMessage());
                        throw folderClosedException;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void loadHeaders() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CATCHBLOCK]], but top level block is 4[TRYBLOCK]
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

    private void setHeaderLoaded(String string2) {
        synchronized (this) {
            if (this.loadedHeaders == null) {
                Hashtable hashtable;
                this.loadedHeaders = hashtable = new Hashtable(1);
            }
            this.loadedHeaders.put(string2.toUpperCase(Locale.ENGLISH), string2);
            return;
        }
    }

    private void setHeadersLoaded(boolean bl) {
        synchronized (this) {
            this.headersLoaded = bl;
            return;
        }
    }

    private String toSection(String string2) {
        if (this.sectionId == null) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.sectionId));
        stringBuilder.append(".");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    Session _getSession() {
        return this.session;
    }

    void _setFlags(Flags flags) {
        this.flags = flags;
    }

    @Override
    public void addFrom(Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void addRecipients(Message.RecipientType recipientType, Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void checkExpunged() throws MessageRemovedException {
        if (this.expunged) throw new MessageRemovedException();
    }

    /*
     * Exception decompiling
     */
    protected void forceCheckExpunged() throws MessageRemovedException, FolderClosedException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
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

    @Override
    public Enumeration getAllHeaderLines() throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getAllHeaderLines();
    }

    @Override
    public Enumeration getAllHeaders() throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getAllHeaders();
    }

    @Override
    public String getContentID() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.id;
    }

    @Override
    public String[] getContentLanguage() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        if (this.bs.language == null) return null;
        return (String[])this.bs.language.clone();
    }

    @Override
    public String getContentMD5() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.md5;
    }

    /*
     * Exception decompiling
     */
    @Override
    protected InputStream getContentStream() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
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

    @Override
    public String getContentType() throws MessagingException {
        this.checkExpunged();
        if (this.type != null) return this.type;
        this.loadBODYSTRUCTURE();
        this.type = new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams).toString();
        return this.type;
    }

    @Override
    public DataHandler getDataHandler() throws MessagingException {
        synchronized (this) {
            Object object;
            this.checkExpunged();
            if (this.dh != null) return super.getDataHandler();
            this.loadBODYSTRUCTURE();
            if (this.type == null) {
                object = new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams);
                this.type = ((ContentType)object).toString();
            }
            if (this.bs.isMulti()) {
                IMAPMultipartDataSource iMAPMultipartDataSource = new IMAPMultipartDataSource(this, this.bs.bodies, this.sectionId, this);
                this.dh = object = new DataHandler(iMAPMultipartDataSource);
                return super.getDataHandler();
            } else {
                DataHandler dataHandler;
                if (!this.bs.isNested()) return super.getDataHandler();
                if (!this.isREV1()) return super.getDataHandler();
                BODYSTRUCTURE bODYSTRUCTURE = this.bs.bodies[0];
                ENVELOPE eNVELOPE = this.bs.envelope;
                if (this.sectionId == null) {
                    object = "1";
                } else {
                    object = new StringBuilder(String.valueOf(this.sectionId));
                    ((StringBuilder)object).append(".1");
                    object = ((StringBuilder)object).toString();
                }
                IMAPNestedMessage iMAPNestedMessage = new IMAPNestedMessage(this, bODYSTRUCTURE, eNVELOPE, (String)object);
                this.dh = dataHandler = new DataHandler(iMAPNestedMessage, this.type);
            }
            return super.getDataHandler();
        }
    }

    @Override
    public String getDescription() throws MessagingException {
        this.checkExpunged();
        String string2 = this.description;
        if (string2 != null) {
            return string2;
        }
        this.loadBODYSTRUCTURE();
        if (this.bs.description == null) {
            return null;
        }
        try {
            this.description = MimeUtility.decodeText(this.bs.description);
            return this.description;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.description = this.bs.description;
        }
        return this.description;
    }

    @Override
    public String getDisposition() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.disposition;
    }

    @Override
    public String getEncoding() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.encoding;
    }

    protected int getFetchBlockSize() {
        return ((IMAPStore)this.folder.getStore()).getFetchBlockSize();
    }

    @Override
    public String getFileName() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        String string2 = this.bs.dParams != null ? this.bs.dParams.get("filename") : null;
        String string3 = string2;
        if (string2 != null) return string3;
        string3 = string2;
        if (this.bs.cParams == null) return string3;
        return this.bs.cParams.get("name");
    }

    @Override
    public Flags getFlags() throws MessagingException {
        synchronized (this) {
            this.checkExpunged();
            this.loadFlags();
            return super.getFlags();
        }
    }

    @Override
    public Address[] getFrom() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.aaclone(this.envelope.from);
    }

    @Override
    public String getHeader(String string2, String string3) throws MessagingException {
        this.checkExpunged();
        if (this.getHeader(string2) != null) return this.headers.getHeader(string2, string3);
        return null;
    }

    /*
     * Exception decompiling
     */
    @Override
    public String[] getHeader(String var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
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

    public String getInReplyTo() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.envelope.inReplyTo;
    }

    @Override
    public int getLineCount() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.lines;
    }

    @Override
    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getMatchingHeaders(arrstring);
    }

    protected Object getMessageCacheLock() {
        return ((IMAPFolder)this.folder).messageCacheLock;
    }

    @Override
    public String getMessageID() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.envelope.messageId;
    }

    @Override
    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getNonMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getNonMatchingHeaders(arrstring);
    }

    public boolean getPeek() {
        synchronized (this) {
            return this.peek;
        }
    }

    protected IMAPProtocol getProtocol() throws ProtocolException, FolderClosedException {
        ((IMAPFolder)this.folder).waitIfIdle();
        IMAPProtocol iMAPProtocol = ((IMAPFolder)this.folder).protocol;
        if (iMAPProtocol == null) throw new FolderClosedException(this.folder);
        return iMAPProtocol;
    }

    @Override
    public Date getReceivedDate() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.receivedDate != null) return new Date(this.receivedDate.getTime());
        return null;
    }

    @Override
    public Address[] getRecipients(Message.RecipientType recipientType) throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (recipientType == Message.RecipientType.TO) {
            return this.aaclone(this.envelope.to);
        }
        if (recipientType == Message.RecipientType.CC) {
            return this.aaclone(this.envelope.cc);
        }
        if (recipientType != Message.RecipientType.BCC) return super.getRecipients(recipientType);
        return this.aaclone(this.envelope.bcc);
    }

    @Override
    public Address[] getReplyTo() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.aaclone(this.envelope.replyTo);
    }

    @Override
    public Address getSender() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.envelope.sender == null) return null;
        return this.envelope.sender[0];
    }

    @Override
    public Date getSentDate() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.envelope.date != null) return new Date(this.envelope.date.getTime());
        return null;
    }

    protected int getSequenceNumber() {
        return this.seqnum;
    }

    @Override
    public int getSize() throws MessagingException {
        this.checkExpunged();
        if (this.size != -1) return this.size;
        this.loadEnvelope();
        return this.size;
    }

    @Override
    public String getSubject() throws MessagingException {
        this.checkExpunged();
        String string2 = this.subject;
        if (string2 != null) {
            return string2;
        }
        this.loadEnvelope();
        if (this.envelope.subject == null) {
            return null;
        }
        try {
            this.subject = MimeUtility.decodeText(this.envelope.subject);
            return this.subject;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.subject = this.envelope.subject;
        }
        return this.subject;
    }

    protected long getUID() {
        return this.uid;
    }

    public void invalidateHeaders() {
        synchronized (this) {
            this.headersLoaded = false;
            this.loadedHeaders = null;
            this.envelope = null;
            this.bs = null;
            this.receivedDate = null;
            this.size = -1;
            this.type = null;
            this.subject = null;
            this.description = null;
            return;
        }
    }

    protected boolean isREV1() throws FolderClosedException {
        IMAPProtocol iMAPProtocol = ((IMAPFolder)this.folder).protocol;
        if (iMAPProtocol == null) throw new FolderClosedException(this.folder);
        return iMAPProtocol.isREV1();
    }

    @Override
    public boolean isSet(Flags.Flag flag) throws MessagingException {
        synchronized (this) {
            this.checkExpunged();
            this.loadFlags();
            return super.isSet(flag);
        }
    }

    @Override
    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setContentID(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setContentLanguage(String[] arrstring) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setContentMD5(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setDescription(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setDisposition(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    protected void setExpunged(boolean bl) {
        super.setExpunged(bl);
        this.seqnum = -1;
    }

    @Override
    public void setFileName(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void setFlags(Flags serializable, boolean bl) throws MessagingException {
        synchronized (this) {
            Object object = this.getMessageCacheLock();
            synchronized (object) {
                try {
                    try {
                        IMAPProtocol iMAPProtocol = this.getProtocol();
                        this.checkExpunged();
                        iMAPProtocol.storeFlags(this.getSequenceNumber(), (Flags)serializable, bl);
                        return;
                    }
                    catch (ProtocolException protocolException) {
                        MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                        throw messagingException;
                    }
                    catch (ConnectionException connectionException) {
                        FolderClosedException folderClosedException = new FolderClosedException(this.folder, connectionException.getMessage());
                        throw folderClosedException;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    @Override
    public void setFrom(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    protected void setMessageNumber(int n) {
        super.setMessageNumber(n);
    }

    public void setPeek(boolean bl) {
        synchronized (this) {
            this.peek = bl;
            return;
        }
    }

    @Override
    public void setRecipients(Message.RecipientType recipientType, Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setReplyTo(Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setSender(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    @Override
    public void setSentDate(Date date) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setSequenceNumber(int n) {
        this.seqnum = n;
    }

    @Override
    public void setSubject(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setUID(long l) {
        this.uid = l;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void writeTo(OutputStream var1_1) throws IOException, MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    class 1FetchProfileCondition
    implements Utility.Condition {
        private String[] hdrs = null;
        private boolean needBodyStructure = false;
        private boolean needEnvelope = false;
        private boolean needFlags = false;
        private boolean needHeaders = false;
        private boolean needSize = false;
        private boolean needUID = false;

        public 1FetchProfileCondition(FetchProfile fetchProfile) {
            if (fetchProfile.contains(FetchProfile.Item.ENVELOPE)) {
                this.needEnvelope = true;
            }
            if (fetchProfile.contains(FetchProfile.Item.FLAGS)) {
                this.needFlags = true;
            }
            if (fetchProfile.contains(FetchProfile.Item.CONTENT_INFO)) {
                this.needBodyStructure = true;
            }
            if (fetchProfile.contains(UIDFolder.FetchProfileItem.UID)) {
                this.needUID = true;
            }
            if (fetchProfile.contains(IMAPFolder.FetchProfileItem.HEADERS)) {
                this.needHeaders = true;
            }
            if (fetchProfile.contains(IMAPFolder.FetchProfileItem.SIZE)) {
                this.needSize = true;
            }
            this.hdrs = fetchProfile.getHeaderNames();
        }

        @Override
        public boolean test(IMAPMessage iMAPMessage) {
            if (this.needEnvelope && iMAPMessage._getEnvelope() == null) {
                return true;
            }
            if (this.needFlags && iMAPMessage._getFlags() == null) {
                return true;
            }
            if (this.needBodyStructure && iMAPMessage._getBodyStructure() == null) {
                return true;
            }
            if (this.needUID && iMAPMessage.getUID() == -1L) {
                return true;
            }
            if (this.needHeaders && !iMAPMessage.areHeadersLoaded()) {
                return true;
            }
            if (this.needSize && iMAPMessage.size == -1) {
                return true;
            }
            int n = 0;
            String[] arrstring;
            while (n < (arrstring = this.hdrs).length) {
                if (!iMAPMessage.isHeaderLoaded(arrstring[n])) {
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

}

