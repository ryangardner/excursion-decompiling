/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPMultipartDataSource;
import com.sun.mail.imap.IMAPNestedMessage;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParameterList;

public class IMAPBodyPart
extends MimeBodyPart {
    private BODYSTRUCTURE bs;
    private String description;
    private boolean headersLoaded = false;
    private IMAPMessage message;
    private String sectionId;
    private String type;

    protected IMAPBodyPart(BODYSTRUCTURE bODYSTRUCTURE, String string2, IMAPMessage iMAPMessage) {
        this.bs = bODYSTRUCTURE;
        this.sectionId = string2;
        this.message = iMAPMessage;
        this.type = new ContentType(bODYSTRUCTURE.type, bODYSTRUCTURE.subtype, bODYSTRUCTURE.cParams).toString();
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

    @Override
    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public Enumeration getAllHeaderLines() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaderLines();
    }

    @Override
    public Enumeration getAllHeaders() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaders();
    }

    @Override
    public String getContentID() throws MessagingException {
        return this.bs.id;
    }

    @Override
    public String getContentMD5() throws MessagingException {
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
        return this.type;
    }

    @Override
    public DataHandler getDataHandler() throws MessagingException {
        synchronized (this) {
            Object object;
            if (this.dh != null) return super.getDataHandler();
            if (this.bs.isMulti()) {
                DataHandler dataHandler;
                object = new IMAPMultipartDataSource(this, this.bs.bodies, this.sectionId, this.message);
                this.dh = dataHandler = new DataHandler((DataSource)object);
                return super.getDataHandler();
            } else {
                if (!this.bs.isNested()) return super.getDataHandler();
                if (!this.message.isREV1()) return super.getDataHandler();
                IMAPNestedMessage iMAPNestedMessage = new IMAPNestedMessage(this.message, this.bs.bodies[0], this.bs.envelope, this.sectionId);
                this.dh = object = new DataHandler(iMAPNestedMessage, this.type);
            }
            return super.getDataHandler();
        }
    }

    @Override
    public String getDescription() throws MessagingException {
        String string2 = this.description;
        if (string2 != null) {
            return string2;
        }
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
        return this.bs.disposition;
    }

    @Override
    public String getEncoding() throws MessagingException {
        return this.bs.encoding;
    }

    @Override
    public String getFileName() throws MessagingException {
        String string2 = this.bs.dParams != null ? this.bs.dParams.get("filename") : null;
        String string3 = string2;
        if (string2 != null) return string3;
        string3 = string2;
        if (this.bs.cParams == null) return string3;
        return this.bs.cParams.get("name");
    }

    @Override
    public String[] getHeader(String string2) throws MessagingException {
        this.loadHeaders();
        return super.getHeader(string2);
    }

    @Override
    public int getLineCount() throws MessagingException {
        return this.bs.lines;
    }

    @Override
    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaders(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaderLines(arrstring);
    }

    @Override
    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaders(arrstring);
    }

    @Override
    public int getSize() throws MessagingException {
        return this.bs.size;
    }

    @Override
    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setContent(Object object, String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setContent(Multipart multipart) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setContentMD5(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setDescription(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setDisposition(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setFileName(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    @Override
    protected void updateHeaders() {
    }
}

