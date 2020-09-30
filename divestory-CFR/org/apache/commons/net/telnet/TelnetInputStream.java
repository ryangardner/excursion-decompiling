/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import org.apache.commons.net.telnet.TelnetClient;

final class TelnetInputStream
extends BufferedInputStream
implements Runnable {
    private static final int EOF = -1;
    private static final int WOULD_BLOCK = -2;
    static final int _STATE_CR = 8;
    static final int _STATE_DATA = 0;
    static final int _STATE_DO = 4;
    static final int _STATE_DONT = 5;
    static final int _STATE_IAC = 1;
    static final int _STATE_IAC_SB = 9;
    static final int _STATE_SB = 6;
    static final int _STATE_SE = 7;
    static final int _STATE_WILL = 2;
    static final int _STATE_WONT = 3;
    private int __bytesAvailable;
    private final TelnetClient __client;
    private boolean __hasReachedEOF;
    private IOException __ioException;
    private volatile boolean __isClosed;
    private final int[] __queue;
    private int __queueHead;
    private int __queueTail;
    private boolean __readIsWaiting;
    private int __receiveState;
    private final int[] __suboption = new int[512];
    private int __suboption_count = 0;
    private final Thread __thread;
    private volatile boolean __threaded;

    TelnetInputStream(InputStream inputStream2, TelnetClient telnetClient) {
        this(inputStream2, telnetClient, true);
    }

    TelnetInputStream(InputStream inputStream2, TelnetClient telnetClient, boolean bl) {
        super(inputStream2);
        this.__client = telnetClient;
        this.__receiveState = 0;
        this.__isClosed = true;
        this.__hasReachedEOF = false;
        this.__queue = new int[2049];
        this.__queueHead = 0;
        this.__queueTail = 0;
        this.__bytesAvailable = 0;
        this.__ioException = null;
        this.__readIsWaiting = false;
        this.__threaded = false;
        if (bl) {
            this.__thread = new Thread(this);
            return;
        }
        this.__thread = null;
    }

    private boolean __processChar(int n) throws InterruptedException {
        int[] arrn = this.__queue;
        synchronized (arrn) {
            boolean bl = this.__bytesAvailable == 0;
            while (this.__bytesAvailable >= this.__queue.length - 1) {
                if (!this.__threaded) {
                    IllegalStateException illegalStateException = new IllegalStateException("Queue is full! Cannot process another character.");
                    throw illegalStateException;
                }
                this.__queue.notify();
                this.__queue.wait();
            }
            if (this.__readIsWaiting && this.__threaded) {
                this.__queue.notify();
            }
            this.__queue[this.__queueTail] = n;
            ++this.__bytesAvailable;
            this.__queueTail = n = this.__queueTail + 1;
            if (n < this.__queue.length) return bl;
            this.__queueTail = 0;
            return bl;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private int __read(boolean bl) throws IOException {
        int n;
        block39 : do {
            if (!bl && super.available() == 0) {
                return -2;
            }
            n = super.read();
            if (n < 0) {
                return -1;
            }
            int[] arrn = this.__client;
            synchronized (arrn) {
                this.__client._processAYTResponse();
            }
            this.__client._spyRead(n &= 255);
            switch (this.__receiveState) {
                default: {
                    return n;
                }
                case 9: {
                    int n2;
                    if (n != 240) {
                        if (n == 255 && (n2 = this.__suboption_count) < (arrn = this.__suboption).length) {
                            this.__suboption_count = n2 + 1;
                            arrn[n2] = n;
                        }
                        this.__receiveState = 6;
                        continue block39;
                    }
                    arrn = this.__client;
                    synchronized (arrn) {
                        this.__client._processSuboption(this.__suboption, this.__suboption_count);
                        this.__client._flushOutputStream();
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 8: {
                    if (n != 0) break;
                    continue block39;
                }
                case 6: {
                    int n2;
                    if (n != 255) {
                        n2 = this.__suboption_count;
                        arrn = this.__suboption;
                        if (n2 < arrn.length) {
                            this.__suboption_count = n2 + 1;
                            arrn[n2] = n;
                        }
                        this.__receiveState = 6;
                        continue block39;
                    }
                    this.__receiveState = 9;
                    continue block39;
                }
                case 5: {
                    arrn = this.__client;
                    synchronized (arrn) {
                        this.__client._processDont(n);
                        this.__client._flushOutputStream();
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 4: {
                    arrn = this.__client;
                    synchronized (arrn) {
                        this.__client._processDo(n);
                        this.__client._flushOutputStream();
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 3: {
                    arrn = this.__client;
                    synchronized (arrn) {
                        this.__client._processWont(n);
                        this.__client._flushOutputStream();
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 2: {
                    arrn = this.__client;
                    synchronized (arrn) {
                        this.__client._processWill(n);
                        this.__client._flushOutputStream();
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 1: {
                    if (n != 240) {
                        switch (n) {
                            default: {
                                this.__receiveState = 0;
                                this.__client._processCommand(n);
                                continue block39;
                            }
                            case 255: {
                                this.__receiveState = 0;
                                return n;
                            }
                            case 254: {
                                this.__receiveState = 5;
                                continue block39;
                            }
                            case 253: {
                                this.__receiveState = 4;
                                continue block39;
                            }
                            case 252: {
                                this.__receiveState = 3;
                                continue block39;
                            }
                            case 251: {
                                this.__receiveState = 2;
                                continue block39;
                            }
                            case 250: 
                        }
                        this.__suboption_count = 0;
                        this.__receiveState = 6;
                        continue block39;
                    }
                    this.__receiveState = 0;
                    continue block39;
                }
                case 0: 
            }
            if (n != 255) break;
            this.__receiveState = 1;
        } while (true);
        if (n == 13) {
            TelnetClient telnetClient = this.__client;
            synchronized (telnetClient) {
                this.__receiveState = this.__client._requestedDont(0) ? 8 : 0;
                return n;
            }
        }
        this.__receiveState = 0;
        return n;
    }

    void _start() {
        int n;
        if (this.__thread == null) {
            return;
        }
        this.__isClosed = false;
        int n2 = n = Thread.currentThread().getPriority() + 1;
        if (n > 10) {
            n2 = 10;
        }
        this.__thread.setPriority(n2);
        this.__thread.setDaemon(true);
        this.__thread.start();
        this.__threaded = true;
    }

    @Override
    public int available() throws IOException {
        int[] arrn = this.__queue;
        synchronized (arrn) {
            if (this.__threaded) {
                return this.__bytesAvailable;
            }
            int n = this.__bytesAvailable;
            int n2 = super.available();
            return n + n2;
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        int[] arrn = this.__queue;
        synchronized (arrn) {
            this.__hasReachedEOF = true;
            this.__isClosed = true;
            if (this.__thread != null && this.__thread.isAlive()) {
                this.__thread.interrupt();
            }
            this.__queue.notifyAll();
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public int read() throws IOException {
        int n;
        int n2;
        block21 : {
            int[] arrn = this.__queue;
            // MONITORENTER : arrn
            do {
                if (this.__ioException != null) {
                    IOException iOException = this.__ioException;
                    this.__ioException = null;
                    throw iOException;
                }
                if (this.__bytesAvailable != 0) break block21;
                if (this.__hasReachedEOF) {
                    // MONITOREXIT : arrn
                    return -1;
                }
                if (this.__threaded) {
                    this.__queue.notify();
                    try {
                        this.__readIsWaiting = true;
                        this.__queue.wait();
                        this.__readIsWaiting = false;
                    }
                    catch (InterruptedException interruptedException) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("Fatal thread interruption during read.");
                        throw interruptedIOException;
                    }
                }
                this.__readIsWaiting = true;
                boolean bl = true;
                do {
                    block23 : {
                        block22 : {
                            n2 = this.__read(bl);
                            if (n2 >= 0 || n2 == -2) break block22;
                            return n2;
                        }
                        if (n2 != -2) {
                            try {
                                this.__processChar(n2);
                            }
                            catch (InterruptedException interruptedException) {
                                if (!this.__isClosed) break block23;
                                // MONITOREXIT : arrn
                                return -1;
                            }
                        }
                    }
                    if (super.available() <= 0 || this.__bytesAvailable >= this.__queue.length - 1) break;
                    bl = false;
                } while (true);
                this.__readIsWaiting = false;
            } while (true);
            catch (InterruptedIOException interruptedIOException) {
                int[] arrn2 = this.__queue;
                // MONITORENTER : arrn2
                this.__ioException = interruptedIOException;
                this.__queue.notifyAll();
                try {
                    this.__queue.wait(100L);
                    // MONITOREXIT : arrn2
                    // MONITOREXIT : arrn
                    return -1;
                }
                catch (InterruptedException interruptedException) {
                    return -1;
                }
            }
        }
        n2 = this.__queue[this.__queueHead];
        this.__queueHead = n = this.__queueHead + 1;
        if (n >= this.__queue.length) {
            this.__queueHead = 0;
        }
        this.__bytesAvailable = n = this.__bytesAvailable - 1;
        if (n == 0 && this.__threaded) {
            this.__queue.notify();
        }
        // MONITOREXIT : arrn
        return n2;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        if (n2 < 1) {
            return 0;
        }
        int[] arrn = this.__queue;
        synchronized (arrn) {
            n3 = n2;
            if (n2 > this.__bytesAvailable) {
                n3 = this.__bytesAvailable;
            }
        }
        int n4 = this.read();
        if (n4 == -1) {
            return -1;
        }
        n2 = n;
        int n5 = n3;
        n3 = n2;
        n2 = n4;
        do {
            n4 = n3 + 1;
            arrby[n3] = (byte)n2;
            if (--n5 <= 0) return n4 - n;
            n2 = this.read();
            if (n2 == -1) {
                return n4 - n;
            }
            n3 = n4;
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void run() {
        int[] arrn;
        try {
            boolean bl;
            while (!(bl = this.__isClosed)) {
                int n = this.__read(true);
                if (n < 0) break;
                try {
                    bl = this.__processChar(n);
                }
                catch (InterruptedException interruptedException) {
                    if (this.__isClosed) break;
                    bl = false;
                }
                if (!bl) continue;
                this.__client.notifyInputListener();
                continue;
                catch (RuntimeException runtimeException) {
                    super.close();
                    break;
                }
                catch (InterruptedIOException interruptedIOException) {
                    block21 : {
                        arrn = this.__queue;
                        // MONITORENTER : arrn
                        this.__ioException = interruptedIOException;
                        this.__queue.notifyAll();
                        try {
                            this.__queue.wait(100L);
                        }
                        catch (InterruptedException interruptedException) {
                            if (!this.__isClosed) break block21;
                            // MONITOREXIT : arrn
                            break;
                        }
                    }
                    // MONITOREXIT : arrn
                }
            }
        }
        catch (IOException iOException) {
            arrn = this.__queue;
            // MONITORENTER : arrn
            this.__ioException = iOException;
            // MONITOREXIT : arrn
            this.__client.notifyInputListener();
        }
        arrn = this.__queue;
        // MONITORENTER : arrn
        this.__isClosed = true;
        this.__hasReachedEOF = true;
        this.__queue.notify();
        // MONITOREXIT : arrn
        this.__threaded = false;
    }
}

