/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.IOException;

public class CopyStreamException
extends IOException {
    private static final long serialVersionUID = -2602899129433221532L;
    private final long totalBytesTransferred;

    public CopyStreamException(String string2, long l, IOException iOException) {
        super(string2);
        this.initCause(iOException);
        this.totalBytesTransferred = l;
    }

    public IOException getIOException() {
        return (IOException)this.getCause();
    }

    public long getTotalBytesTransferred() {
        return this.totalBytesTransferred;
    }
}

