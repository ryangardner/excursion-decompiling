/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import org.apache.http.impl.conn.tsccm.WaitingThread;

public class WaitingThreadAborter {
    private boolean aborted;
    private WaitingThread waitingThread;

    public void abort() {
        this.aborted = true;
        WaitingThread waitingThread = this.waitingThread;
        if (waitingThread == null) return;
        waitingThread.interrupt();
    }

    public void setWaitingThread(WaitingThread waitingThread) {
        this.waitingThread = waitingThread;
        if (!this.aborted) return;
        waitingThread.interrupt();
    }
}

