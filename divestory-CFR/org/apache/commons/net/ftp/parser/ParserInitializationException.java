/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

public class ParserInitializationException
extends RuntimeException {
    private static final long serialVersionUID = 5563335279583210658L;

    public ParserInitializationException(String string2) {
        super(string2);
    }

    public ParserInitializationException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    @Deprecated
    public Throwable getRootCause() {
        return super.getCause();
    }
}

