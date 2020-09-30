/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.util.Vector;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.MailEvent;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public abstract class Transport
extends Service {
    private Vector transportListeners = null;

    public Transport(Session session, URLName uRLName) {
        super(session, uRLName);
    }

    public static void send(Message message) throws MessagingException {
        message.saveChanges();
        Transport.send0(message, message.getAllRecipients());
    }

    public static void send(Message message, Address[] arraddress) throws MessagingException {
        message.saveChanges();
        Transport.send0(message, arraddress);
    }

    /*
     * Exception decompiling
     */
    private static void send0(Message var0, Address[] var1_3) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public void addTransportListener(TransportListener transportListener) {
        synchronized (this) {
            if (this.transportListeners == null) {
                Vector vector;
                this.transportListeners = vector = new Vector();
            }
            this.transportListeners.addElement(transportListener);
            return;
        }
    }

    protected void notifyTransportListeners(int n, Address[] arraddress, Address[] arraddress2, Address[] arraddress3, Message message) {
        if (this.transportListeners == null) {
            return;
        }
        this.queueEvent(new TransportEvent(this, n, arraddress, arraddress2, arraddress3, message), this.transportListeners);
    }

    public void removeTransportListener(TransportListener transportListener) {
        synchronized (this) {
            if (this.transportListeners == null) return;
            this.transportListeners.removeElement(transportListener);
            return;
        }
    }

    public abstract void sendMessage(Message var1, Address[] var2) throws MessagingException;
}

