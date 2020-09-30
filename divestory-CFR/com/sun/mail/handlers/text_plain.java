/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import myjava.awt.datatransfer.DataFlavor;

public class text_plain
implements DataContentHandler {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");

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

    /*
     * Exception decompiling
     */
    @Override
    public Object getContent(DataSource var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 9[UNCONDITIONALDOLOOP]
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

    protected ActivationDataFlavor getDF() {
        return myDF;
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (!this.getDF().equals(dataFlavor)) return null;
        return this.getContent(dataSource);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{this.getDF()};
    }

    @Override
    public void writeTo(Object object, String object2, OutputStream outputStream2) throws IOException {
        if (!(object instanceof String)) {
            object2 = new StringBuilder("\"");
            ((StringBuilder)object2).append(this.getDF().getMimeType());
            ((StringBuilder)object2).append("\" DataContentHandler requires String object, ");
            ((StringBuilder)object2).append("was given object of type ");
            ((StringBuilder)object2).append(object.getClass().toString());
            throw new IOException(((StringBuilder)object2).toString());
        }
        CharSequence charSequence = null;
        try {
            object2 = this.getCharset((String)object2);
            charSequence = object2;
            object2 = new OutputStreamWriter(outputStream2, (String)object2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException((String)charSequence);
        }
        object = (String)object;
        ((OutputStreamWriter)object2).write((String)object, 0, ((String)object).length());
        ((OutputStreamWriter)object2).flush();
    }
}

