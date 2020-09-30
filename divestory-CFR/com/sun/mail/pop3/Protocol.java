/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.Response;
import com.sun.mail.pop3.SharedByteArrayOutputStream;
import com.sun.mail.pop3.Status;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.StringTokenizer;

class Protocol {
    private static final String CRLF = "\r\n";
    private static final int POP3_PORT = 110;
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private String apopChallenge;
    private boolean debug;
    private DataInputStream input;
    private PrintStream out;
    private PrintWriter output;
    private Socket socket;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    Protocol(String var1_1, int var2_4, boolean var3_5, PrintStream var4_6, Properties var5_8, String var6_9, boolean var7_10) throws IOException {
        block12 : {
            super();
            var8_11 = 0;
            this.debug = false;
            this.apopChallenge = null;
            this.debug = var3_5;
            this.out = var4_6;
            var9_12 = new StringBuilder(String.valueOf(var6_9));
            var9_12.append(".apop.enable");
            var9_12 = var5_8.getProperty(var9_12.toString());
            var10_13 = var8_11;
            if (var9_12 != null) {
                var10_13 = var8_11;
                if (var9_12.equalsIgnoreCase("true")) {
                    var10_13 = 1;
                }
            }
            var8_11 = var2_4;
            if (var2_4 == -1) {
                var8_11 = 110;
            }
            if (!var3_5) ** GOTO lbl33
            super("DEBUG POP3: connecting to host \"");
            var9_12.append((String)var1_1);
            var9_12.append("\", port ");
            var9_12.append(var8_11);
            var9_12.append(", isSSL ");
            var9_12.append(var7_10);
            var4_6.println(var9_12.toString());
lbl33: // 2 sources:
            this.socket = SocketFetcher.getSocket((String)var1_1, var8_11, (Properties)var5_8, (String)var6_9, var7_10);
            var1_1 = new BufferedInputStream(this.socket.getInputStream());
            this.input = var5_8 = new DataInputStream((InputStream)var1_1);
            var1_1 = new OutputStreamWriter(this.socket.getOutputStream(), "iso-8859-1");
            var6_9 = new BufferedWriter((Writer)var1_1);
            this.output = var5_8 = new PrintWriter((Writer)var6_9);
            var1_1 = this.simpleCommand(null);
            if (!var1_1.ok) break block12;
            if (var10_13 == 0) return;
            var10_13 = var1_1.data.indexOf(60);
            var2_4 = var1_1.data.indexOf(62, var10_13);
            if (var10_13 != -1 && var2_4 != -1) {
                this.apopChallenge = var1_1.data.substring(var10_13, var2_4 + 1);
            }
            if (var3_5 == false) return;
            var1_1 = new StringBuilder("DEBUG POP3: APOP challenge: ");
            var1_1.append(this.apopChallenge);
            var4_6.println(var1_1.toString());
            return;
        }
        this.socket.close();
        catch (IOException var1_2) {
            this.socket.close();
        }
        finally {
            throw new IOException("Connect failed");
        }
        {
            finally {
                throw var1_2;
            }
        }
    }

    private String getDigest(String arrby) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.apopChallenge));
        stringBuilder.append((String)arrby);
        arrby = stringBuilder.toString();
        try {
            arrby = MessageDigest.getInstance("MD5").digest(arrby.getBytes("iso-8859-1"));
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
            return null;
        }
        return Protocol.toHex(arrby);
    }

    private Response multilineCommand(String object, int n) throws IOException {
        Response response = this.simpleCommand((String)object);
        if (!response.ok) {
            return response;
        }
        object = new SharedByteArrayOutputStream(n);
        int n2 = 10;
        while ((n = this.input.read()) >= 0) {
            if (n2 == 10 && n == 46) {
                if (this.debug) {
                    this.out.write(n);
                }
                n = n2 = this.input.read();
                if (n2 == 13) {
                    if (this.debug) {
                        this.out.write(n2);
                    }
                    n = n2 = this.input.read();
                    if (!this.debug) break;
                    this.out.write(n2);
                    n = n2;
                    break;
                }
            }
            ((ByteArrayOutputStream)object).write(n);
            n2 = n;
            if (!this.debug) continue;
            this.out.write(n);
            n2 = n;
        }
        if (n < 0) throw new EOFException("EOF on socket");
        response.bytes = ((SharedByteArrayOutputStream)object).toStream();
        return response;
    }

    private Response simpleCommand(String charSequence) throws IOException {
        PrintStream printStream;
        Object object;
        if (this.socket == null) throw new IOException("Folder is closed");
        if (charSequence != null) {
            if (this.debug) {
                printStream = this.out;
                object = new StringBuilder("C: ");
                ((StringBuilder)object).append((String)charSequence);
                printStream.println(((StringBuilder)object).toString());
            }
            charSequence = new StringBuilder(String.valueOf(charSequence));
            ((StringBuilder)charSequence).append(CRLF);
            charSequence = ((StringBuilder)charSequence).toString();
            this.output.print((String)charSequence);
            this.output.flush();
        }
        if ((charSequence = this.input.readLine()) == null) {
            if (!this.debug) throw new EOFException("EOF on socket");
            this.out.println("S: EOF");
            throw new EOFException("EOF on socket");
        }
        if (this.debug) {
            printStream = this.out;
            object = new StringBuilder("S: ");
            ((StringBuilder)object).append((String)charSequence);
            printStream.println(((StringBuilder)object).toString());
        }
        object = new Response();
        if (((String)charSequence).startsWith("+OK")) {
            ((Response)object).ok = true;
        } else {
            if (!((String)charSequence).startsWith("-ERR")) {
                object = new StringBuilder("Unexpected response: ");
                ((StringBuilder)object).append((String)charSequence);
                throw new IOException(((StringBuilder)object).toString());
            }
            ((Response)object).ok = false;
        }
        int n = ((String)charSequence).indexOf(32);
        if (n < 0) return object;
        ((Response)object).data = ((String)charSequence).substring(n + 1);
        return object;
    }

    private static String toHex(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = arrby[n] & 255;
            int n4 = n2 + 1;
            char[] arrc2 = digits;
            arrc[n2] = arrc2[n3 >> 4];
            n2 = n4 + 1;
            arrc[n4] = arrc2[n3 & 15];
            ++n;
        }
        return new String(arrc);
    }

    boolean dele(int n) throws IOException {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder("DELE ");
            stringBuilder.append(n);
            return this.simpleCommand((String)stringBuilder.toString()).ok;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.socket == null) return;
        this.quit();
    }

    int list(int n) throws IOException {
        synchronized (this) {
            int n2;
            Object object = new StringBuilder("LIST ");
            ((StringBuilder)object).append(n);
            object = this.simpleCommand(((StringBuilder)object).toString());
            n = n2 = -1;
            if (!((Response)object).ok) return n;
            Object object2 = ((Response)object).data;
            n = n2;
            if (object2 == null) return n;
            try {
                object2 = new StringTokenizer(((Response)object).data);
                ((StringTokenizer)object2).nextToken();
                return Integer.parseInt(((StringTokenizer)object2).nextToken());
            }
            catch (Exception exception) {
                return n2;
            }
        }
    }

    InputStream list() throws IOException {
        synchronized (this) {
            return this.multilineCommand((String)"LIST", (int)128).bytes;
        }
    }

    String login(String object, String charSequence) throws IOException {
        synchronized (this) {
            CharSequence charSequence2 = this.apopChallenge != null ? this.getDigest((String)charSequence) : null;
            if (this.apopChallenge != null && charSequence2 != null) {
                charSequence = new StringBuilder("APOP ");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append((String)charSequence2);
                object = this.simpleCommand(((StringBuilder)charSequence).toString());
            } else {
                charSequence2 = new StringBuilder("USER ");
                ((StringBuilder)charSequence2).append((String)object);
                object = this.simpleCommand(((StringBuilder)charSequence2).toString());
                if (!((Response)object).ok) {
                    object = ((Response)object).data != null ? ((Response)object).data : "USER command failed";
                    return object;
                }
                object = new StringBuilder("PASS ");
                ((StringBuilder)object).append((String)charSequence);
                object = this.simpleCommand(((StringBuilder)object).toString());
            }
            if (((Response)object).ok) return null;
            object = ((Response)object).data != null ? ((Response)object).data : "login failed";
            return object;
        }
    }

    boolean noop() throws IOException {
        synchronized (this) {
            return this.simpleCommand((String)"NOOP").ok;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    boolean quit() throws IOException {
        synchronized (this) {
            boolean bl;
            try {
                bl = this.simpleCommand((String)"QUIT").ok;
            }
            catch (Throwable throwable) {
                try {
                    this.socket.close();
                    throw throwable;
                }
                finally {
                    this.socket = null;
                    this.input = null;
                    this.output = null;
                }
            }
            try {
                this.socket.close();
                return bl;
            }
            finally {
                this.socket = null;
                this.input = null;
                this.output = null;
            }
        }
    }

    InputStream retr(int n, int n2) throws IOException {
        synchronized (this) {
            Object object = new StringBuilder("RETR ");
            ((StringBuilder)object).append(n);
            return this.multilineCommand((String)object.toString(), (int)n2).bytes;
        }
    }

    boolean rset() throws IOException {
        synchronized (this) {
            return this.simpleCommand((String)"RSET").ok;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    Status stat() throws IOException {
        // MONITORENTER : this
        Response response = this.simpleCommand("STAT");
        Status status = new Status();
        if (!response.ok) return status;
        Object object = response.data;
        if (object == null) return status;
        try {
            object = new StringTokenizer(response.data);
            status.total = Integer.parseInt(((StringTokenizer)object).nextToken());
            status.size = Integer.parseInt(((StringTokenizer)object).nextToken());
            // MONITOREXIT : this
            return status;
        }
        catch (Exception exception) {
            return status;
        }
    }

    InputStream top(int n, int n2) throws IOException {
        synchronized (this) {
            Object object = new StringBuilder("TOP ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(n2);
            return this.multilineCommand((String)object.toString(), (int)0).bytes;
        }
    }

    String uidl(int n) throws IOException {
        synchronized (this) {
            Object object;
            block5 : {
                object = new StringBuilder("UIDL ");
                ((StringBuilder)object).append(n);
                object = this.simpleCommand(((StringBuilder)object).toString());
                boolean bl = ((Response)object).ok;
                if (bl) break block5;
                return null;
            }
            n = ((Response)object).data.indexOf(32);
            if (n <= 0) return null;
            object = ((Response)object).data.substring(n + 1);
            return object;
        }
    }

    boolean uidl(String[] arrstring) throws IOException {
        synchronized (this) {
            Object object = this.multilineCommand("UIDL", arrstring.length * 15);
            boolean bl = ((Response)object).ok;
            if (!bl) {
                return false;
            }
            LineInputStream lineInputStream = new LineInputStream(((Response)object).bytes);
            while ((object = lineInputStream.readLine()) != null) {
                int n;
                int n2 = ((String)object).indexOf(32);
                if (n2 < 1 || n2 >= ((String)object).length() || (n = Integer.parseInt(((String)object).substring(0, n2))) <= 0 || n > arrstring.length) continue;
                arrstring[n - 1] = ((String)object).substring(n2 + 1);
            }
            return true;
        }
    }
}

