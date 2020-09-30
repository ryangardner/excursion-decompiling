/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.util.Vector;
import javax.mail.event.MailEvent;

class EventQueue
implements Runnable {
    private QueueElement head = null;
    private Thread qThread;
    private QueueElement tail = null;

    public EventQueue() {
        Thread thread2;
        this.qThread = thread2 = new Thread((Runnable)this, "JavaMail-EventQueue");
        thread2.setDaemon(true);
        this.qThread.start();
    }

    private QueueElement dequeue() throws InterruptedException {
        synchronized (this) {
            do {
                if (this.tail != null) {
                    QueueElement queueElement;
                    QueueElement queueElement2 = this.tail;
                    this.tail = queueElement = queueElement2.prev;
                    if (queueElement == null) {
                        this.head = null;
                    } else {
                        queueElement.next = null;
                    }
                    queueElement2.next = null;
                    queueElement2.prev = null;
                    return queueElement2;
                }
                this.wait();
            } while (true);
        }
    }

    public void enqueue(MailEvent mailEvent, Vector vector) {
        synchronized (this) {
            QueueElement queueElement = new QueueElement(mailEvent, vector);
            if (this.head == null) {
                this.head = queueElement;
                this.tail = queueElement;
            } else {
                queueElement.next = this.head;
                this.head.prev = queueElement;
                this.head = queueElement;
            }
            this.notifyAll();
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[DOLOOP], 0[TRYBLOCK]], but top level block is 6[UNCONDITIONALDOLOOP]
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

    void stop() {
        Thread thread2 = this.qThread;
        if (thread2 == null) return;
        thread2.interrupt();
        this.qThread = null;
    }

    static class QueueElement {
        MailEvent event = null;
        QueueElement next = null;
        QueueElement prev = null;
        Vector vector = null;

        QueueElement(MailEvent mailEvent, Vector vector) {
            this.event = mailEvent;
            this.vector = vector;
        }
    }

}

