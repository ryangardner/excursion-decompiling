/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.dsn;

import com.sun.mail.util.LineOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

public class DeliveryStatus {
    private static boolean debug = false;
    protected InternetHeaders messageDSN;
    protected InternetHeaders[] recipientDSN;

    static {
        try {
            String string2 = System.getProperty("mail.dsn.debug");
            boolean bl = string2 != null && !string2.equalsIgnoreCase("false");
            debug = bl;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public DeliveryStatus() throws MessagingException {
        this.messageDSN = new InternetHeaders();
        this.recipientDSN = new InternetHeaders[0];
    }

    public DeliveryStatus(InputStream arrobject) throws MessagingException, IOException {
        Object object;
        Vector<InternetHeaders> vector;
        block6 : {
            this.messageDSN = new InternetHeaders((InputStream)arrobject);
            if (debug) {
                System.out.println("DSN: got messageDSN");
            }
            vector = new Vector<InternetHeaders>();
            try {
                while (arrobject.available() > 0) {
                    object = new InternetHeaders((InputStream)arrobject);
                    if (debug) {
                        System.out.println("DSN: got recipientDSN");
                    }
                    vector.addElement((InternetHeaders)object);
                }
            }
            catch (EOFException eOFException) {
                if (!debug) break block6;
                System.out.println("DSN: got EOFException");
            }
        }
        if (debug) {
            arrobject = System.out;
            object = new StringBuilder("DSN: recipientDSN size ");
            ((StringBuilder)object).append(vector.size());
            arrobject.println(((StringBuilder)object).toString());
        }
        arrobject = new InternetHeaders[vector.size()];
        this.recipientDSN = arrobject;
        vector.copyInto(arrobject);
    }

    private static void writeInternetHeaders(InternetHeaders object, LineOutputStream object2) throws IOException {
        object = ((InternetHeaders)object).getAllHeaderLines();
        try {
            do {
                if (!object.hasMoreElements()) {
                    return;
                }
                ((LineOutputStream)object2).writeln((String)object.nextElement());
            } while (true);
        }
        catch (MessagingException messagingException) {
            object2 = messagingException.getNextException();
            if (object2 instanceof IOException) {
                throw (IOException)object2;
            }
            object2 = new StringBuilder("Exception writing headers: ");
            ((StringBuilder)object2).append(messagingException);
            throw new IOException(((StringBuilder)object2).toString());
        }
    }

    public void addRecipientDSN(InternetHeaders internetHeaders) {
        InternetHeaders[] arrinternetHeaders = this.recipientDSN;
        InternetHeaders[] arrinternetHeaders2 = new InternetHeaders[arrinternetHeaders.length + 1];
        System.arraycopy(arrinternetHeaders, 0, arrinternetHeaders2, 0, arrinternetHeaders.length);
        this.recipientDSN = arrinternetHeaders2;
        arrinternetHeaders2[arrinternetHeaders2.length - 1] = internetHeaders;
    }

    public InternetHeaders getMessageDSN() {
        return this.messageDSN;
    }

    public InternetHeaders getRecipientDSN(int n) {
        return this.recipientDSN[n];
    }

    public int getRecipientDSNCount() {
        return this.recipientDSN.length;
    }

    public void setMessageDSN(InternetHeaders internetHeaders) {
        this.messageDSN = internetHeaders;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DeliveryStatus: Reporting-MTA=");
        stringBuilder.append(this.messageDSN.getHeader("Reporting-MTA", null));
        stringBuilder.append(", #Recipients=");
        stringBuilder.append(this.recipientDSN.length);
        return stringBuilder.toString();
    }

    public void writeTo(OutputStream outputStream2) throws IOException, MessagingException {
        outputStream2 = outputStream2 instanceof LineOutputStream ? (LineOutputStream)outputStream2 : new LineOutputStream(outputStream2);
        DeliveryStatus.writeInternetHeaders(this.messageDSN, (LineOutputStream)outputStream2);
        ((LineOutputStream)outputStream2).writeln();
        int n = 0;
        InternetHeaders[] arrinternetHeaders;
        while (n < (arrinternetHeaders = this.recipientDSN).length) {
            DeliveryStatus.writeInternetHeaders(arrinternetHeaders[n], (LineOutputStream)outputStream2);
            ((LineOutputStream)outputStream2).writeln();
            ++n;
        }
        return;
    }
}

