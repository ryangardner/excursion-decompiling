/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import javax.mail.MessagingException;

public class LineOutputStream
extends FilterOutputStream {
    private static byte[] newline;

    static {
        byte[] arrby = new byte[2];
        newline = arrby;
        arrby[0] = (byte)13;
        arrby[1] = (byte)10;
    }

    public LineOutputStream(OutputStream outputStream2) {
        super(outputStream2);
    }

    public void writeln() throws MessagingException {
        try {
            this.out.write(newline);
            return;
        }
        catch (Exception exception) {
            throw new MessagingException("IOException", exception);
        }
    }

    public void writeln(String arrby) throws MessagingException {
        try {
            arrby = ASCIIUtility.getBytes((String)arrby);
            this.out.write(arrby);
            this.out.write(newline);
            return;
        }
        catch (Exception exception) {
            throw new MessagingException("IOException", exception);
        }
    }
}

