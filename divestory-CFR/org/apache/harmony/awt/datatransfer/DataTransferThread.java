/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import org.apache.harmony.awt.datatransfer.DTK;

public class DataTransferThread
extends Thread {
    private final DTK dtk;

    public DataTransferThread(DTK dTK) {
        super("AWT-DataTransferThread");
        this.setDaemon(true);
        this.dtk = dTK;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void run() {
        synchronized (this) {
            try {
                this.dtk.initDragAndDrop();
                // MONITOREXIT [0, 3] lbl4 : MonitorExitStatement: MONITOREXIT : this
                this.dtk.runEventLoop();
                return;
            }
            finally {
                this.notifyAll();
            }
        }
    }

    @Override
    public void start() {
        synchronized (this) {
            super.start();
            try {
                this.wait();
                return;
            }
            catch (InterruptedException interruptedException) {
                RuntimeException runtimeException = new RuntimeException(interruptedException);
                throw runtimeException;
            }
        }
    }
}

