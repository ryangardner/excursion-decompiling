/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.LineOutputStream;
import java.io.EOFException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.MultipartDataSource;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.ParameterList;
import javax.mail.internet.UniqueValue;

public class MimeMultipart
extends Multipart {
    private static boolean bmparse = true;
    private static boolean ignoreMissingBoundaryParameter = true;
    private static boolean ignoreMissingEndBoundary = true;
    private boolean complete = true;
    protected DataSource ds = null;
    protected boolean parsed = true;
    private String preamble = null;

    static {
        try {
            String string2 = System.getProperty("mail.mime.multipart.ignoremissingendboundary");
            boolean bl = false;
            boolean bl2 = string2 == null || !string2.equalsIgnoreCase("false");
            ignoreMissingEndBoundary = bl2;
            string2 = System.getProperty("mail.mime.multipart.ignoremissingboundaryparameter");
            bl2 = string2 == null || !string2.equalsIgnoreCase("false");
            ignoreMissingBoundaryParameter = bl2;
            string2 = System.getProperty("mail.mime.multipart.bmparse");
            bl2 = string2 != null && string2.equalsIgnoreCase("false") ? bl : true;
            bmparse = bl2;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public MimeMultipart() {
        this("mixed");
    }

    public MimeMultipart(String object) {
        String string2 = UniqueValue.getUniqueBoundaryValue();
        object = new ContentType("multipart", (String)object, null);
        ((ContentType)object).setParameter("boundary", string2);
        this.contentType = ((ContentType)object).toString();
    }

    public MimeMultipart(DataSource dataSource) throws MessagingException {
        if (dataSource instanceof MessageAware) {
            this.setParent(((MessageAware)((Object)dataSource)).getMessageContext().getPart());
        }
        if (dataSource instanceof MultipartDataSource) {
            this.setMultipartDataSource((MultipartDataSource)dataSource);
            return;
        }
        this.parsed = false;
        this.ds = dataSource;
        this.contentType = dataSource.getContentType();
    }

    /*
     * Exception decompiling
     */
    private void parsebm() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 26[UNCONDITIONALDOLOOP]
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

    private static int readFully(InputStream inputStream2, byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        int n4 = n;
        n = n2;
        if (n2 == 0) {
            return 0;
        }
        do {
            if (n <= 0 || (n2 = inputStream2.read(arrby, n4, n)) <= 0) {
                if (n3 <= 0) return -1;
                return n3;
            }
            n4 += n2;
            n3 += n2;
            n -= n2;
        } while (true);
    }

    private void skipFully(InputStream inputStream2, long l) throws IOException {
        while (l > 0L) {
            long l2 = inputStream2.skip(l);
            if (l2 <= 0L) throw new EOFException("can't skip");
            l -= l2;
        }
        return;
    }

    @Override
    public void addBodyPart(BodyPart bodyPart) throws MessagingException {
        synchronized (this) {
            this.parse();
            super.addBodyPart(bodyPart);
            return;
        }
    }

    @Override
    public void addBodyPart(BodyPart bodyPart, int n) throws MessagingException {
        synchronized (this) {
            this.parse();
            super.addBodyPart(bodyPart, n);
            return;
        }
    }

    protected InternetHeaders createInternetHeaders(InputStream inputStream2) throws MessagingException {
        return new InternetHeaders(inputStream2);
    }

    protected MimeBodyPart createMimeBodyPart(InputStream inputStream2) throws MessagingException {
        return new MimeBodyPart(inputStream2);
    }

    protected MimeBodyPart createMimeBodyPart(InternetHeaders internetHeaders, byte[] arrby) throws MessagingException {
        return new MimeBodyPart(internetHeaders, arrby);
    }

    @Override
    public BodyPart getBodyPart(int n) throws MessagingException {
        synchronized (this) {
            this.parse();
            return super.getBodyPart(n);
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public BodyPart getBodyPart(String string2) throws MessagingException {
        synchronized (this) {
            this.parse();
            int n = this.getCount();
            int n2 = 0;
            while (n2 < n) {
                boolean bl;
                MimeBodyPart mimeBodyPart = (MimeBodyPart)this.getBodyPart(n2);
                String string3 = mimeBodyPart.getContentID();
                if (string3 != null && (bl = string3.equals(string2))) {
                    return mimeBodyPart;
                }
                ++n2;
            }
            return null;
        }
    }

    @Override
    public int getCount() throws MessagingException {
        synchronized (this) {
            this.parse();
            return super.getCount();
        }
    }

    public String getPreamble() throws MessagingException {
        synchronized (this) {
            this.parse();
            return this.preamble;
        }
    }

    public boolean isComplete() throws MessagingException {
        synchronized (this) {
            this.parse();
            return this.complete;
        }
    }

    /*
     * Exception decompiling
     */
    protected void parse() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[TRYBLOCK]], but top level block is 6[TRYBLOCK]
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
    public void removeBodyPart(int n) throws MessagingException {
        this.parse();
        super.removeBodyPart(n);
    }

    @Override
    public boolean removeBodyPart(BodyPart bodyPart) throws MessagingException {
        this.parse();
        return super.removeBodyPart(bodyPart);
    }

    public void setPreamble(String string2) throws MessagingException {
        synchronized (this) {
            this.preamble = string2;
            return;
        }
    }

    public void setSubType(String string2) throws MessagingException {
        synchronized (this) {
            ContentType contentType = new ContentType(this.contentType);
            contentType.setSubType(string2);
            this.contentType = contentType.toString();
            return;
        }
    }

    protected void updateHeaders() throws MessagingException {
        int n = 0;
        while (n < this.parts.size()) {
            ((MimeBodyPart)this.parts.elementAt(n)).updateHeaders();
            ++n;
        }
        return;
    }

    @Override
    public void writeTo(OutputStream object) throws IOException, MessagingException {
        synchronized (this) {
            this.parse();
            CharSequence charSequence = new StringBuilder("--");
            Object object2 = new ContentType(this.contentType);
            charSequence.append(((ContentType)object2).getParameter("boundary"));
            charSequence = charSequence.toString();
            object2 = new LineOutputStream((OutputStream)object);
            if (this.preamble != null) {
                byte[] arrby = ASCIIUtility.getBytes(this.preamble);
                ((FilterOutputStream)object2).write(arrby);
                if (arrby.length > 0 && arrby[arrby.length - 1] != 13 && arrby[arrby.length - 1] != 10) {
                    ((LineOutputStream)object2).writeln();
                }
            }
            int n = 0;
            do {
                if (n >= this.parts.size()) {
                    object = new StringBuilder(String.valueOf(charSequence));
                    ((StringBuilder)object).append("--");
                    ((LineOutputStream)object2).writeln(((StringBuilder)object).toString());
                    return;
                }
                ((LineOutputStream)object2).writeln((String)charSequence);
                ((MimeBodyPart)this.parts.elementAt(n)).writeTo((OutputStream)object);
                ((LineOutputStream)object2).writeln();
                ++n;
            } while (true);
        }
    }
}

