/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;

public class IMAPInputStream
extends InputStream {
    private static final int slop = 64;
    private int blksize;
    private byte[] buf;
    private int bufcount;
    private int bufpos;
    private int max;
    private IMAPMessage msg;
    private boolean peek;
    private int pos;
    private ByteArray readbuf;
    private String section;

    public IMAPInputStream(IMAPMessage iMAPMessage, String string2, int n, boolean bl) {
        this.msg = iMAPMessage;
        this.section = string2;
        this.max = n;
        this.peek = bl;
        this.pos = 0;
        this.blksize = iMAPMessage.getFetchBlockSize();
    }

    private void checkSeen() {
        if (this.peek) {
            return;
        }
        try {
            Folder folder = this.msg.getFolder();
            if (folder == null) return;
            if (folder.getMode() == 1) return;
            if (this.msg.isSet(Flags.Flag.SEEN)) return;
            this.msg.setFlag(Flags.Flag.SEEN, true);
            return;
        }
        catch (MessagingException messagingException) {
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private void fill() throws IOException {
        block13 : {
            var1_1 = this.max;
            if (var1_1 != -1 && (var2_2 = this.pos) >= var1_1) {
                if (var2_2 == 0) {
                    this.checkSeen();
                }
                this.readbuf = null;
                return;
            }
            if (this.readbuf == null) {
                this.readbuf = new ByteArray(this.blksize + 64);
            }
            var3_3 = this.msg.getMessageCacheLock();
            // MONITORENTER : var3_3
            var4_4 = this.msg.getProtocol();
            if (this.msg.isExpunged()) ** GOTO lbl34
            var1_1 = this.msg.getSequenceNumber();
            var2_2 = this.blksize;
            if (this.max != -1 && this.pos + this.blksize > this.max) {
                var2_2 = this.max - this.pos;
            }
            if ((var4_4 = this.peek != false ? var4_4.peekBody(var1_1, this.section, this.pos, var2_2, this.readbuf) : var4_4.fetchBody(var1_1, this.section, this.pos, var2_2, this.readbuf)) == null || (var4_4 = var4_4.getByteArray()) == null) break block13;
            // MONITOREXIT : var3_3
            if (this.pos == 0) {
                this.checkSeen();
            }
            this.buf = var4_4.getBytes();
            this.bufpos = var4_4.getStart();
            var2_2 = var4_4.getCount();
            this.bufcount = this.bufpos + var2_2;
            this.pos += var2_2;
            return;
        }
        this.forceCheckExpunged();
        var4_4 = new IOException("No content");
        throw var4_4;
lbl34: // 1 sources:
        try {
            var4_4 = new MessageRemovedIOException("No content for expunged message");
            throw var4_4;
        }
        catch (FolderClosedException var4_6) {
            var5_8 = new FolderClosedIOException(var4_6.getFolder(), var4_6.getMessage());
            throw var5_8;
        }
        catch (ProtocolException var4_7) {
            this.forceCheckExpunged();
            var5_9 = new IOException(var4_7.getMessage());
            throw var5_9;
        }
    }

    /*
     * Exception decompiling
     */
    private void forceCheckExpunged() throws MessageRemovedIOException, FolderClosedIOException {
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
    public int available() throws IOException {
        synchronized (this) {
            int n = this.bufcount;
            int n2 = this.bufpos;
            return n - n2;
        }
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            int n;
            block4 : {
                if (this.bufpos < this.bufcount) break block4;
                this.fill();
                n = this.bufpos;
                int n2 = this.bufcount;
                if (n < n2) break block4;
                return -1;
            }
            byte[] arrby = this.buf;
            n = this.bufpos;
            this.bufpos = n + 1;
            n = arrby[n];
            return n & 255;
        }
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            int n4;
            block5 : {
                n4 = n3 = this.bufcount - this.bufpos;
                if (n3 > 0) break block5;
                this.fill();
                n3 = this.bufcount;
                n4 = this.bufpos;
                n3 -= n4;
                n4 = n3;
                if (n3 > 0) break block5;
                return -1;
            }
            n3 = n2;
            if (n4 < n2) {
                n3 = n4;
            }
            System.arraycopy(this.buf, this.bufpos, arrby, n, n3);
            this.bufpos += n3;
            return n3;
        }
    }
}

