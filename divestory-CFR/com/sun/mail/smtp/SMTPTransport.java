/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import com.sun.mail.smtp.DigestMD5;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPOutputStream;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.ParseException;

public class SMTPTransport
extends Transport {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] CRLF;
    private static final String UNKNOWN = "UNKNOWN";
    private static char[] hexchar;
    private static final String[] ignoreList;
    private Address[] addresses;
    private SMTPOutputStream dataStream;
    private int defaultPort = 25;
    private MessagingException exception;
    private Hashtable extMap;
    private Address[] invalidAddr;
    private boolean isSSL;
    private int lastReturnCode;
    private String lastServerResponse;
    private LineInputStream lineInputStream;
    private String localHostName;
    private DigestMD5 md5support;
    private MimeMessage message;
    private String name = "smtp";
    private PrintStream out;
    private boolean quitWait;
    private boolean reportSuccess;
    private String saslRealm;
    private boolean sendPartiallyFailed;
    private BufferedInputStream serverInput;
    private OutputStream serverOutput;
    private Socket serverSocket;
    private boolean useRset;
    private boolean useStartTLS;
    private Address[] validSentAddr;
    private Address[] validUnsentAddr;

    static {
        ignoreList = new String[]{"Bcc", "Content-Length"};
        CRLF = new byte[]{13, 10};
        hexchar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public SMTPTransport(Session session, URLName uRLName) {
        this(session, uRLName, "smtp", 25, false);
    }

    protected SMTPTransport(Session object, URLName object2, String string2, int n, boolean bl) {
        super((Session)object, (URLName)object2);
        boolean bl2 = false;
        this.isSSL = false;
        this.sendPartiallyFailed = false;
        this.quitWait = false;
        this.saslRealm = UNKNOWN;
        if (object2 != null) {
            string2 = ((URLName)object2).getProtocol();
        }
        this.name = string2;
        this.defaultPort = n;
        this.isSSL = bl;
        this.out = ((Session)object).getDebugOut();
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".quitwait");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        bl = object2 == null || ((String)object2).equalsIgnoreCase("true");
        this.quitWait = bl;
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".reportsuccess");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        bl = object2 != null && ((String)object2).equalsIgnoreCase("true");
        this.reportSuccess = bl;
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".starttls.enable");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        bl = object2 != null && ((String)object2).equalsIgnoreCase("true");
        this.useStartTLS = bl;
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".userset");
        object = ((Session)object).getProperty(((StringBuilder)object2).toString());
        bl = bl2;
        if (object != null) {
            bl = bl2;
            if (((String)object).equalsIgnoreCase("true")) {
                bl = true;
            }
        }
        this.useRset = bl;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private void closeConnection() throws MessagingException {
        Throwable throwable2222;
        if (this.serverSocket != null) {
            this.serverSocket.close();
        }
        this.serverSocket = null;
        this.serverOutput = null;
        this.serverInput = null;
        this.lineInputStream = null;
        if (!super.isConnected()) return;
        super.close();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                MessagingException messagingException = new MessagingException("Server Close Failed", iOException);
                throw messagingException;
            }
        }
        this.serverSocket = null;
        this.serverOutput = null;
        this.serverInput = null;
        this.lineInputStream = null;
        if (!super.isConnected()) throw throwable2222;
        super.close();
        throw throwable2222;
    }

    private boolean convertTo8Bit(MimePart object) {
        boolean bl;
        int n;
        boolean bl2 = false;
        int n2 = 0;
        try {
            if (object.isMimeType("text/*")) {
                String string2 = object.getEncoding();
                bl = bl2;
                if (string2 == null) return bl;
                if (!string2.equalsIgnoreCase("quoted-printable")) {
                    bl = bl2;
                    if (!string2.equalsIgnoreCase("base64")) return bl;
                }
                bl = bl2;
                if (!this.is8Bit(object.getInputStream())) return bl;
                object.setContent(object.getContent(), object.getContentType());
                object.setHeader("Content-Transfer-Encoding", "8bit");
                return true;
            }
            bl = bl2;
            if (!object.isMimeType("multipart/*")) return bl;
            object = (MimeMultipart)object.getContent();
            n = ((MimeMultipart)object).getCount();
            bl = false;
        }
        catch (IOException | MessagingException exception) {
            return bl2;
        }
        while (n2 < n) {
            try {
                bl2 = this.convertTo8Bit((MimePart)((Object)((MimeMultipart)object).getBodyPart(n2)));
                if (bl2) {
                    bl = true;
                }
                ++n2;
            }
            catch (IOException | MessagingException exception) {
                return bl;
            }
        }
        return bl;
    }

    private void expandGroups() {
        Object object = null;
        int n = 0;
        do {
            Object object2;
            block13 : {
                InternetAddress internetAddress;
                int n2;
                block11 : {
                    block12 : {
                        block10 : {
                            if (n >= ((Address[])(object2 = this.addresses)).length) {
                                if (object == null) return;
                                object2 = new InternetAddress[object.size()];
                                object.copyInto((Object[])object2);
                                this.addresses = object2;
                                return;
                            }
                            internetAddress = (InternetAddress)object2[n];
                            if (!internetAddress.isGroup()) break block10;
                            object2 = object;
                            if (object != null) break block11;
                            object2 = new Vector();
                            break block12;
                        }
                        object2 = object;
                        if (object != null) {
                            object.addElement(internetAddress);
                            object2 = object;
                        }
                        break block13;
                    }
                    for (n2 = 0; n2 < n; ++n2) {
                        ((Vector)object2).addElement(this.addresses[n2]);
                    }
                }
                try {
                    object = internetAddress.getGroup(true);
                    if (object != null) {
                        for (n2 = 0; n2 < ((Object[])object).length; ++n2) {
                            ((Vector)object2).addElement(object[n2]);
                        }
                    } else {
                        ((Vector)object2).addElement(internetAddress);
                    }
                }
                catch (ParseException parseException) {
                    ((Vector)object2).addElement(internetAddress);
                }
            }
            ++n;
            object = object2;
        } while (true);
    }

    private DigestMD5 getMD5() {
        synchronized (this) {
            DigestMD5 digestMD5;
            if (this.md5support != null) return this.md5support;
            PrintStream printStream = this.debug ? this.out : null;
            this.md5support = digestMD5 = new DigestMD5(printStream);
            return this.md5support;
        }
    }

    private void initStreams() throws IOException {
        Object object = this.session.getProperties();
        FilterOutputStream filterOutputStream = this.session.getDebugOut();
        boolean bl = this.session.getDebug();
        boolean bl2 = (object = ((Properties)object).getProperty("mail.debug.quote")) != null && ((String)object).equalsIgnoreCase("true");
        object = new TraceInputStream(this.serverSocket.getInputStream(), filterOutputStream);
        ((TraceInputStream)object).setTrace(bl);
        ((TraceInputStream)object).setQuote(bl2);
        filterOutputStream = new TraceOutputStream(this.serverSocket.getOutputStream(), filterOutputStream);
        ((TraceOutputStream)filterOutputStream).setTrace(bl);
        ((TraceOutputStream)filterOutputStream).setQuote(bl2);
        this.serverOutput = new BufferedOutputStream(filterOutputStream);
        this.serverInput = new BufferedInputStream((InputStream)object);
        this.lineInputStream = new LineInputStream(this.serverInput);
    }

    private boolean is8Bit(InputStream inputStream2) {
        boolean bl = false;
        int n = 0;
        try {
            do {
                int n2;
                if ((n2 = inputStream2.read()) < 0) {
                    if (!this.debug) return bl;
                    if (!bl) return bl;
                    this.out.println("DEBUG SMTP: found an 8bit part");
                    return bl;
                }
                int n3 = n2 & 255;
                if (n3 != 13 && n3 != 10) {
                    if (n3 == 0) {
                        return false;
                    }
                    n2 = ++n;
                    if (n > 998) {
                        return false;
                    }
                } else {
                    n2 = 0;
                }
                n = n2;
                if (n3 <= 127) continue;
                bl = true;
                n = n2;
            } while (true);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    private boolean isNotLastLine(String string2) {
        if (string2 == null) return false;
        if (string2.length() < 4) return false;
        if (string2.charAt(3) != '-') return false;
        return true;
    }

    private void issueSendCommand(String string2, int n) throws MessagingException {
        this.sendCommand(string2);
        int n2 = this.readServerResponse();
        if (n2 == n) return;
        Object object = this.validSentAddr;
        n = object == null ? 0 : ((Address[])object).length;
        object = this.validUnsentAddr;
        int n3 = object == null ? 0 : ((Address[])object).length;
        object = new Address[n + n3];
        if (n > 0) {
            System.arraycopy(this.validSentAddr, 0, object, 0, n);
        }
        if (n3 > 0) {
            System.arraycopy(this.validUnsentAddr, 0, object, n, n3);
        }
        this.validSentAddr = null;
        this.validUnsentAddr = object;
        if (this.debug) {
            object = this.out;
            StringBuilder stringBuilder = new StringBuilder("DEBUG SMTP: got response code ");
            stringBuilder.append(n2);
            stringBuilder.append(", with response: ");
            stringBuilder.append(this.lastServerResponse);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        object = this.lastServerResponse;
        n = this.lastReturnCode;
        if (this.serverSocket != null) {
            this.issueCommand("RSET", 250);
        }
        this.lastServerResponse = object;
        this.lastReturnCode = n;
        throw new SMTPSendFailedException(string2, n2, this.lastServerResponse, this.exception, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
    }

    private String normalizeAddress(String string2) {
        CharSequence charSequence = string2;
        if (string2.startsWith("<")) return charSequence;
        charSequence = string2;
        if (string2.endsWith(">")) return charSequence;
        charSequence = new StringBuilder("<");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(">");
        return ((StringBuilder)charSequence).toString();
    }

    private void openServer() throws MessagingException {
        String string2 = UNKNOWN;
        int n = -1;
        String string3 = string2;
        try {
            Serializable serializable;
            Appendable appendable;
            int n2 = this.serverSocket.getPort();
            string3 = string2;
            n = n2;
            string3 = string2 = this.serverSocket.getInetAddress().getHostName();
            n = n2;
            if (this.debug) {
                string3 = string2;
                n = n2;
                appendable = this.out;
                string3 = string2;
                n = n2;
                string3 = string2;
                n = n2;
                serializable = new StringBuilder("DEBUG SMTP: starting protocol to host \"");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(string2);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append("\", port ");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(n2);
                string3 = string2;
                n = n2;
                ((PrintStream)appendable).println(((StringBuilder)serializable).toString());
            }
            string3 = string2;
            n = n2;
            this.initStreams();
            string3 = string2;
            n = n2;
            int n3 = this.readServerResponse();
            if (n3 == 220) {
                string3 = string2;
                n = n2;
                if (!this.debug) return;
                string3 = string2;
                n = n2;
                appendable = this.out;
                string3 = string2;
                n = n2;
                string3 = string2;
                n = n2;
                serializable = new StringBuilder("DEBUG SMTP: protocol started to host \"");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(string2);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append("\", port: ");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(n2);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append("\n");
                string3 = string2;
                n = n2;
                ((PrintStream)appendable).println(((StringBuilder)serializable).toString());
                return;
            }
            string3 = string2;
            n = n2;
            this.serverSocket.close();
            string3 = string2;
            n = n2;
            this.serverSocket = null;
            string3 = string2;
            n = n2;
            this.serverOutput = null;
            string3 = string2;
            n = n2;
            this.serverInput = null;
            string3 = string2;
            n = n2;
            this.lineInputStream = null;
            string3 = string2;
            n = n2;
            boolean bl = this.debug;
            if (bl) {
                string3 = string2;
                n = n2;
                appendable = this.out;
                string3 = string2;
                n = n2;
                string3 = string2;
                n = n2;
                serializable = new StringBuilder("DEBUG SMTP: got bad greeting from host \"");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(string2);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append("\", port: ");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(n2);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(", response: ");
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append(n3);
                string3 = string2;
                n = n2;
                ((StringBuilder)serializable).append("\n");
                string3 = string2;
                n = n2;
                ((PrintStream)appendable).println(((StringBuilder)serializable).toString());
            }
            string3 = string2;
            n = n2;
            string3 = string2;
            n = n2;
            string3 = string2;
            n = n2;
            appendable = new StringBuilder("Got bad greeting from SMTP host: ");
            string3 = string2;
            n = n2;
            ((StringBuilder)appendable).append(string2);
            string3 = string2;
            n = n2;
            ((StringBuilder)appendable).append(", port: ");
            string3 = string2;
            n = n2;
            ((StringBuilder)appendable).append(n2);
            string3 = string2;
            n = n2;
            ((StringBuilder)appendable).append(", response: ");
            string3 = string2;
            n = n2;
            ((StringBuilder)appendable).append(n3);
            string3 = string2;
            n = n2;
            serializable = new MessagingException(((StringBuilder)appendable).toString());
            string3 = string2;
            n = n2;
            throw serializable;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder("Could not start protocol to SMTP host: ");
            stringBuilder.append(string3);
            stringBuilder.append(", port: ");
            stringBuilder.append(n);
            throw new MessagingException(stringBuilder.toString(), iOException);
        }
    }

    private void openServer(String string2, int n) throws MessagingException {
        Serializable serializable;
        Object object;
        if (this.debug) {
            object = this.out;
            serializable = new StringBuilder("DEBUG SMTP: trying to connect to host \"");
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append("\", port ");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append(", isSSL ");
            ((StringBuilder)serializable).append(this.isSSL);
            ((PrintStream)object).println(((StringBuilder)serializable).toString());
        }
        int n2 = n;
        try {
            serializable = this.session.getProperties();
            n2 = n;
            n2 = n;
            object = new StringBuilder("mail.");
            n2 = n;
            ((StringBuilder)object).append(this.name);
            n2 = n;
            object = SocketFetcher.getSocket(string2, n, (Properties)serializable, ((StringBuilder)object).toString(), this.isSSL);
            n2 = n;
            this.serverSocket = object;
            n2 = n;
            n2 = n = ((Socket)object).getPort();
            this.initStreams();
            n2 = n;
            int n3 = this.readServerResponse();
            if (n3 == 220) {
                n2 = n;
                if (!this.debug) return;
                n2 = n;
                object = this.out;
                n2 = n;
                n2 = n;
                serializable = new StringBuilder("DEBUG SMTP: connected to host \"");
                n2 = n;
                ((StringBuilder)serializable).append(string2);
                n2 = n;
                ((StringBuilder)serializable).append("\", port: ");
                n2 = n;
                ((StringBuilder)serializable).append(n);
                n2 = n;
                ((StringBuilder)serializable).append("\n");
                n2 = n;
                ((PrintStream)object).println(((StringBuilder)serializable).toString());
                return;
            }
            n2 = n;
            this.serverSocket.close();
            n2 = n;
            this.serverSocket = null;
            n2 = n;
            this.serverOutput = null;
            n2 = n;
            this.serverInput = null;
            n2 = n;
            this.lineInputStream = null;
            n2 = n;
            boolean bl = this.debug;
            if (bl) {
                n2 = n;
                object = this.out;
                n2 = n;
                n2 = n;
                serializable = new StringBuilder("DEBUG SMTP: could not connect to host \"");
                n2 = n;
                ((StringBuilder)serializable).append(string2);
                n2 = n;
                ((StringBuilder)serializable).append("\", port: ");
                n2 = n;
                ((StringBuilder)serializable).append(n);
                n2 = n;
                ((StringBuilder)serializable).append(", response: ");
                n2 = n;
                ((StringBuilder)serializable).append(n3);
                n2 = n;
                ((StringBuilder)serializable).append("\n");
                n2 = n;
                ((PrintStream)object).println(((StringBuilder)serializable).toString());
            }
            n2 = n;
            n2 = n;
            n2 = n;
            object = new StringBuilder("Could not connect to SMTP host: ");
            n2 = n;
            ((StringBuilder)object).append(string2);
            n2 = n;
            ((StringBuilder)object).append(", port: ");
            n2 = n;
            ((StringBuilder)object).append(n);
            n2 = n;
            ((StringBuilder)object).append(", response: ");
            n2 = n;
            ((StringBuilder)object).append(n3);
            n2 = n;
            serializable = new MessagingException(((StringBuilder)object).toString());
            n2 = n;
            throw serializable;
        }
        catch (IOException iOException) {
            serializable = new StringBuilder("Could not connect to SMTP host: ");
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append(", port: ");
            ((StringBuilder)serializable).append(n2);
            throw new MessagingException(((StringBuilder)serializable).toString(), iOException);
        }
        catch (UnknownHostException unknownHostException) {
            serializable = new StringBuilder("Unknown SMTP host: ");
            ((StringBuilder)serializable).append(string2);
            throw new MessagingException(((StringBuilder)serializable).toString(), unknownHostException);
        }
    }

    private void sendCommand(byte[] arrby) throws MessagingException {
        try {
            this.serverOutput.write(arrby);
            this.serverOutput.write(CRLF);
            this.serverOutput.flush();
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Can't send command to SMTP host", iOException);
        }
    }

    protected static String xtext(String string2) {
        AbstractStringBuilder abstractStringBuilder = null;
        int n = 0;
        do {
            AbstractStringBuilder abstractStringBuilder2;
            if (n >= string2.length()) {
                if (abstractStringBuilder == null) return string2;
                return ((StringBuffer)abstractStringBuilder).toString();
            }
            char c = string2.charAt(n);
            if (c >= '') {
                abstractStringBuilder = new StringBuilder("Non-ASCII character in SMTP submitter: ");
                ((StringBuilder)abstractStringBuilder).append(string2);
                throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
            }
            if (c >= '!' && c <= '~' && c != '+' && c != '=') {
                abstractStringBuilder2 = abstractStringBuilder;
                if (abstractStringBuilder != null) {
                    ((StringBuffer)abstractStringBuilder).append(c);
                    abstractStringBuilder2 = abstractStringBuilder;
                }
            } else {
                abstractStringBuilder2 = abstractStringBuilder;
                if (abstractStringBuilder == null) {
                    abstractStringBuilder2 = new StringBuffer(string2.length() + 4);
                    ((StringBuffer)abstractStringBuilder2).append(string2.substring(0, n));
                }
                ((StringBuffer)abstractStringBuilder2).append('+');
                ((StringBuffer)abstractStringBuilder2).append(hexchar[(c & 240) >> 4]);
                ((StringBuffer)abstractStringBuilder2).append(hexchar[c & 15]);
            }
            ++n;
            abstractStringBuilder = abstractStringBuilder2;
        } while (true);
    }

    protected void checkConnected() {
        if (!super.isConnected()) throw new IllegalStateException("Not connected");
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void close() throws MessagingException {
        synchronized (this) {
            boolean bl = super.isConnected();
            if (!bl) {
                return;
            }
            try {
                if (this.serverSocket == null) return;
                this.sendCommand("QUIT");
                if (!this.quitWait) return;
                int n = this.readServerResponse();
                if (n == 221) return;
                if (n == -1) return;
                PrintStream printStream = this.out;
                StringBuilder stringBuilder = new StringBuilder("DEBUG SMTP: QUIT failed with ");
                stringBuilder.append(n);
                printStream.println(stringBuilder.toString());
                return;
            }
            finally {
                this.closeConnection();
            }
        }
    }

    public void connect(Socket socket) throws MessagingException {
        synchronized (this) {
            this.serverSocket = socket;
            super.connect();
            return;
        }
    }

    protected OutputStream data() throws MessagingException {
        SMTPOutputStream sMTPOutputStream;
        this.issueSendCommand("DATA", 354);
        this.dataStream = sMTPOutputStream = new SMTPOutputStream(this.serverOutput);
        return sMTPOutputStream;
    }

    protected boolean ehlo(String string2) throws MessagingException {
        CharSequence charSequence;
        if (string2 != null) {
            charSequence = new StringBuilder("EHLO ");
            charSequence.append(string2);
            string2 = charSequence.toString();
        } else {
            string2 = "EHLO";
        }
        this.sendCommand(string2);
        int n = this.readServerResponse();
        if (n == 250) {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(this.lastServerResponse));
            this.extMap = new Hashtable();
            boolean bl = true;
            try {
                while ((string2 = bufferedReader.readLine()) != null) {
                    if (bl) {
                        bl = false;
                        continue;
                    }
                    if (string2.length() < 5) continue;
                    CharSequence charSequence2 = string2.substring(4);
                    int n2 = ((String)charSequence2).indexOf(32);
                    charSequence = "";
                    string2 = charSequence2;
                    if (n2 > 0) {
                        charSequence = ((String)charSequence2).substring(n2 + 1);
                        string2 = ((String)charSequence2).substring(0, n2);
                    }
                    if (this.debug) {
                        PrintStream printStream = this.out;
                        charSequence2 = new StringBuilder("DEBUG SMTP: Found extension \"");
                        ((StringBuilder)charSequence2).append(string2);
                        ((StringBuilder)charSequence2).append("\", arg \"");
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        ((StringBuilder)charSequence2).append("\"");
                        printStream.println(((StringBuilder)charSequence2).toString());
                    }
                    this.extMap.put(string2.toUpperCase(Locale.ENGLISH), charSequence);
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (n != 250) return false;
        return true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            this.closeConnection();
            return;
        }
        catch (MessagingException messagingException) {
            return;
        }
    }

    protected void finishData() throws IOException, MessagingException {
        this.dataStream.ensureAtBOL();
        this.issueSendCommand(".", 250);
    }

    public String getExtensionParameter(String string2) {
        Hashtable hashtable = this.extMap;
        if (hashtable != null) return (String)hashtable.get(string2.toUpperCase(Locale.ENGLISH));
        return null;
    }

    public int getLastReturnCode() {
        synchronized (this) {
            return this.lastReturnCode;
        }
    }

    public String getLastServerResponse() {
        synchronized (this) {
            return this.lastServerResponse;
        }
    }

    public String getLocalHost() {
        synchronized (this) {
            try {
                try {
                    Object object;
                    Object object2;
                    if (this.localHostName == null || this.localHostName.length() <= 0) {
                        object = this.session;
                        object2 = new StringBuilder("mail.");
                        ((StringBuilder)object2).append(this.name);
                        ((StringBuilder)object2).append(".localhost");
                        this.localHostName = ((Session)object).getProperty(((StringBuilder)object2).toString());
                    }
                    if (this.localHostName == null || this.localHostName.length() <= 0) {
                        object2 = this.session;
                        object = new StringBuilder("mail.");
                        ((StringBuilder)object).append(this.name);
                        ((StringBuilder)object).append(".localaddress");
                        this.localHostName = ((Session)object2).getProperty(((StringBuilder)object).toString());
                    }
                    if (this.localHostName != null) {
                        if (this.localHostName.length() > 0) return this.localHostName;
                    }
                    object2 = InetAddress.getLocalHost();
                    this.localHostName = object = ((InetAddress)object2).getHostName();
                    if (object != null) return this.localHostName;
                    object = new StringBuilder("[");
                    ((StringBuilder)object).append(((InetAddress)object2).getHostAddress());
                    ((StringBuilder)object).append("]");
                    this.localHostName = ((StringBuilder)object).toString();
                    return this.localHostName;
                }
                catch (UnknownHostException unknownHostException) {}
                return this.localHostName;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public boolean getReportSuccess() {
        synchronized (this) {
            return this.reportSuccess;
        }
    }

    public String getSASLRealm() {
        synchronized (this) {
            if (this.saslRealm != UNKNOWN) return this.saslRealm;
            Object object = this.session;
            Object object2 = new StringBuilder("mail.");
            ((StringBuilder)object2).append(this.name);
            ((StringBuilder)object2).append(".sasl.realm");
            this.saslRealm = object = ((Session)object).getProperty(((StringBuilder)object2).toString());
            if (object != null) return this.saslRealm;
            object2 = this.session;
            object = new StringBuilder("mail.");
            ((StringBuilder)object).append(this.name);
            ((StringBuilder)object).append(".saslrealm");
            this.saslRealm = ((Session)object2).getProperty(((StringBuilder)object).toString());
            return this.saslRealm;
        }
    }

    public boolean getStartTLS() {
        synchronized (this) {
            return this.useStartTLS;
        }
    }

    public boolean getUseRset() {
        synchronized (this) {
            return this.useRset;
        }
    }

    protected void helo(String string2) throws MessagingException {
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder("HELO ");
            stringBuilder.append(string2);
            this.issueCommand(stringBuilder.toString(), 250);
            return;
        }
        this.issueCommand("HELO", 250);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public boolean isConnected() {
        block12 : {
            // MONITORENTER : this
            boolean bl = super.isConnected();
            if (!bl) {
                // MONITOREXIT : this
                return false;
            }
            if (this.useRset) {
                this.sendCommand("RSET");
            } else {
                this.sendCommand("NOOP");
            }
            int n = this.readServerResponse();
            if (n < 0 || n == 421) break block12;
            // MONITOREXIT : this
            return true;
        }
        try {
            this.closeConnection();
            // MONITOREXIT : this
            return false;
        }
        catch (Exception exception) {
            try {
                this.closeConnection();
                // MONITOREXIT : this
                return false;
            }
            catch (MessagingException messagingException) {
                return false;
            }
            catch (MessagingException messagingException) {
                return false;
            }
        }
    }

    public void issueCommand(String object, int n) throws MessagingException {
        synchronized (this) {
            this.sendCommand((String)object);
            int n2 = this.readServerResponse();
            if (n2 == n) {
                return;
            }
            object = new MessagingException(this.lastServerResponse);
            throw object;
        }
    }

    protected void mailFrom() throws MessagingException {
        Object object;
        Object object2;
        Object object3;
        Object var3_3;
        block20 : {
            block24 : {
                block23 : {
                    block22 : {
                        block21 : {
                            object2 = this.message;
                            boolean bl = object2 instanceof SMTPMessage;
                            var3_3 = null;
                            object = bl ? ((SMTPMessage)object2).getEnvelopeFrom() : null;
                            if (object == null) break block21;
                            object2 = object;
                            if (((String)object).length() > 0) break block22;
                        }
                        object2 = this.session;
                        object = new StringBuilder("mail.");
                        ((StringBuilder)object).append(this.name);
                        ((StringBuilder)object).append(".from");
                        object2 = ((Session)object2).getProperty(((StringBuilder)object).toString());
                    }
                    if (object2 == null) break block23;
                    object = object2;
                    if (((String)object2).length() > 0) break block24;
                }
                object2 = (object2 = this.message) != null && (object2 = ((MimeMessage)object2).getFrom()) != null && ((Address[])object2).length > 0 ? object2[0] : InternetAddress.getLocalAddress(this.session);
                if (object2 == null) throw new MessagingException("can't determine local email address");
                object = ((InternetAddress)object2).getAddress();
            }
            object2 = new StringBuilder("MAIL FROM:");
            ((StringBuilder)object2).append(this.normalizeAddress((String)object));
            object2 = object3 = ((StringBuilder)object2).toString();
            if (this.supportsExtension("DSN")) {
                object2 = this.message;
                object2 = object2 instanceof SMTPMessage ? ((SMTPMessage)object2).getDSNRet() : null;
                object = object2;
                if (object2 == null) {
                    object = this.session;
                    object2 = new StringBuilder("mail.");
                    ((StringBuilder)object2).append(this.name);
                    ((StringBuilder)object2).append(".dsn.ret");
                    object = ((Session)object).getProperty(((StringBuilder)object2).toString());
                }
                object2 = object3;
                if (object != null) {
                    object2 = new StringBuilder(String.valueOf(object3));
                    ((StringBuilder)object2).append(" RET=");
                    ((StringBuilder)object2).append((String)object);
                    object2 = ((StringBuilder)object2).toString();
                }
            }
            object = object2;
            if (this.supportsExtension("AUTH")) {
                object = this.message;
                object = object instanceof SMTPMessage ? ((SMTPMessage)object).getSubmitter() : null;
                object3 = object;
                if (object == null) {
                    object = this.session;
                    object3 = new StringBuilder("mail.");
                    ((StringBuilder)object3).append(this.name);
                    ((StringBuilder)object3).append(".submitter");
                    object3 = ((Session)object).getProperty(((StringBuilder)object3).toString());
                }
                object = object2;
                if (object3 != null) {
                    try {
                        object = SMTPTransport.xtext((String)object3);
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(object2));
                        stringBuilder.append(" AUTH=");
                        stringBuilder.append((String)object);
                        object = stringBuilder.toString();
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        object = object2;
                        if (!this.debug) break block20;
                        PrintStream printStream = this.out;
                        object = new StringBuilder("DEBUG SMTP: ignoring invalid submitter: ");
                        ((StringBuilder)object).append((String)object3);
                        ((StringBuilder)object).append(", Exception: ");
                        ((StringBuilder)object).append(illegalArgumentException);
                        printStream.println(((StringBuilder)object).toString());
                        object = object2;
                    }
                }
            }
        }
        object3 = this.message;
        object2 = var3_3;
        if (object3 instanceof SMTPMessage) {
            object2 = ((SMTPMessage)object3).getMailExtension();
        }
        object3 = object2;
        if (object2 == null) {
            object2 = this.session;
            object3 = new StringBuilder("mail.");
            ((StringBuilder)object3).append(this.name);
            ((StringBuilder)object3).append(".mailextension");
            object3 = ((Session)object2).getProperty(((StringBuilder)object3).toString());
        }
        object2 = object;
        if (object3 != null) {
            object2 = object;
            if (((String)object3).length() > 0) {
                object2 = new StringBuilder(String.valueOf(object));
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append((String)object3);
                object2 = ((StringBuilder)object2).toString();
            }
        }
        this.issueSendCommand((String)object2, 250);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    protected boolean protocolConnect(String object, int n, String charSequence, String string2) throws MessagingException {
        Object object2 = this.session;
        Appendable appendable = new StringBuilder("mail.");
        ((StringBuilder)appendable).append(this.name);
        ((StringBuilder)appendable).append(".ehlo");
        object2 = ((Session)object2).getProperty(((StringBuilder)appendable).toString());
        boolean bl = object2 == null || !((String)object2).equalsIgnoreCase("false");
        object2 = this.session;
        appendable = new StringBuilder("mail.");
        ((StringBuilder)appendable).append(this.name);
        ((StringBuilder)appendable).append(".auth");
        object2 = ((Session)object2).getProperty(((StringBuilder)appendable).toString());
        boolean bl2 = object2 != null && ((String)object2).equalsIgnoreCase("true");
        if (this.debug) {
            appendable = this.out;
            object2 = new StringBuilder("DEBUG SMTP: useEhlo ");
            ((StringBuilder)object2).append(bl);
            ((StringBuilder)object2).append(", useAuth ");
            ((StringBuilder)object2).append(bl2);
            ((PrintStream)appendable).println(((StringBuilder)object2).toString());
        }
        if (bl2) {
            if (charSequence == null) return false;
            if (string2 == null) {
                return false;
            }
        }
        int n2 = -1;
        if (n == -1) {
            object2 = this.session;
            appendable = new StringBuilder("mail.");
            ((StringBuilder)appendable).append(this.name);
            ((StringBuilder)appendable).append(".port");
            object2 = ((Session)object2).getProperty(((StringBuilder)appendable).toString());
            n = object2 != null ? Integer.parseInt((String)object2) : this.defaultPort;
        }
        if (object == null || ((String)object).length() == 0) {
            object = "localhost";
        }
        if (this.serverSocket != null) {
            this.openServer();
        } else {
            this.openServer((String)object, n);
        }
        bl = bl ? this.ehlo(this.getLocalHost()) : false;
        if (!bl) {
            this.helo(this.getLocalHost());
        }
        if (this.useStartTLS && this.supportsExtension("STARTTLS")) {
            this.startTLS();
            this.ehlo(this.getLocalHost());
        }
        if (!bl2) {
            if (charSequence == null) return true;
            if (string2 == null) return true;
        }
        if (!this.supportsExtension("AUTH")) {
            if (!this.supportsExtension("AUTH=LOGIN")) return true;
        }
        if (this.debug) {
            this.out.println("DEBUG SMTP: Attempt to authenticate");
            if (!this.supportsAuthentication("LOGIN") && this.supportsExtension("AUTH=LOGIN")) {
                this.out.println("DEBUG SMTP: use AUTH=LOGIN hack");
            }
        }
        if (!this.supportsAuthentication("LOGIN") && !this.supportsExtension("AUTH=LOGIN")) {
            int n3;
            if (this.supportsAuthentication("PLAIN")) {
                int n4 = this.simpleCommand("AUTH PLAIN");
                try {
                    object2 = new ByteArrayOutputStream();
                    object = new BASE64EncoderStream((OutputStream)object2, Integer.MAX_VALUE);
                    n = n4;
                    if (n4 != 334) return true;
                    ((OutputStream)object).write(0);
                    ((OutputStream)object).write(ASCIIUtility.getBytes((String)charSequence));
                    ((OutputStream)object).write(0);
                    ((OutputStream)object).write(ASCIIUtility.getBytes(string2));
                    ((OutputStream)object).flush();
                    n = this.simpleCommand(((ByteArrayOutputStream)object2).toByteArray());
                    return true;
                }
                catch (IOException iOException) {
                    if (n4 == 235) return true;
                    this.closeConnection();
                    return false;
                }
                finally {
                    if (n != 235) {
                        this.closeConnection();
                        return false;
                    }
                }
            }
            if (!this.supportsAuthentication("DIGEST-MD5")) return true;
            object2 = this.getMD5();
            if (object2 == null) return true;
            n = n3 = this.simpleCommand("AUTH DIGEST-MD5");
            if (n3 == 334) {
                n = n3;
                try {
                    int n5;
                    n = n5 = this.simpleCommand(((DigestMD5)object2).authClient((String)object, (String)charSequence, string2, this.getSASLRealm(), this.lastServerResponse));
                    if (n5 == 334) {
                        n = n5;
                        n3 = n5;
                        if (!((DigestMD5)object2).authServer(this.lastServerResponse)) {
                            n = n2;
                        } else {
                            n = n5;
                            n3 = n5;
                            n = n5 = this.simpleCommand(new byte[0]);
                        }
                    }
                }
                catch (Exception exception) {
                    n = n3;
                    if (this.debug) {
                        n = n3;
                        object = this.out;
                        n = n3;
                        n = n3;
                        charSequence = new StringBuilder("DEBUG SMTP: DIGEST-MD5: ");
                        n = n3;
                        ((StringBuilder)charSequence).append(exception);
                        n = n3;
                        ((PrintStream)object).println(((StringBuilder)charSequence).toString());
                    }
                    if (n3 == 235) return true;
                    this.closeConnection();
                    return false;
                }
            }
            if (n == 235) return true;
            this.closeConnection();
            return false;
        }
        int n6 = n = this.simpleCommand("AUTH LOGIN");
        if (n == 530) {
            this.startTLS();
            n6 = this.simpleCommand("AUTH LOGIN");
        }
        int n7 = n6;
        n2 = n6;
        try {
            n7 = n6;
            n2 = n6;
            object = new ByteArrayOutputStream();
            n7 = n6;
            n2 = n6;
            n7 = n6;
            n2 = n6;
            object2 = new BASE64EncoderStream((OutputStream)object, Integer.MAX_VALUE);
            n = n6;
            if (n6 == 334) {
                n7 = n6;
                n2 = n6;
                ((OutputStream)object2).write(ASCIIUtility.getBytes((String)charSequence));
                n7 = n6;
                n2 = n6;
                ((OutputStream)object2).flush();
                n7 = n6;
                n2 = n6;
                n7 = n = this.simpleCommand(((ByteArrayOutputStream)object).toByteArray());
                n2 = n;
                ((ByteArrayOutputStream)object).reset();
            }
            n6 = n;
            if (n == 334) {
                n7 = n;
                n2 = n;
                ((OutputStream)object2).write(ASCIIUtility.getBytes(string2));
                n7 = n;
                n2 = n;
                ((OutputStream)object2).flush();
                n7 = n;
                n2 = n;
                n7 = n = this.simpleCommand(((ByteArrayOutputStream)object).toByteArray());
                n2 = n;
                ((ByteArrayOutputStream)object).reset();
                n6 = n;
            }
            if (n6 == 235) return true;
        }
        catch (Throwable throwable) {
            if (n7 == 235) throw throwable;
            this.closeConnection();
            return false;
        }
        catch (IOException iOException) {
            if (n2 == 235) return true;
            this.closeConnection();
            return false;
        }
        this.closeConnection();
        return false;
        catch (Throwable throwable) {}
        if (n == 235) throw throwable;
        this.closeConnection();
        return false;
    }

    /*
     * Exception decompiling
     */
    protected void rcptTo() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CASE]], but top level block is 6[SWITCH]
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

    protected int readServerResponse() throws MessagingException {
        CharSequence charSequence;
        int n;
        Object object;
        block16 : {
            block15 : {
                object = new StringBuffer(100);
                try {
                    do {
                        if ((charSequence = this.lineInputStream.readLine()) == null) {
                            object = ((StringBuffer)object).toString();
                            charSequence = object;
                            if (((String)object).length() == 0) {
                                charSequence = "[EOF]";
                            }
                            this.lastServerResponse = charSequence;
                            this.lastReturnCode = -1;
                            if (!this.debug) return -1;
                            object = this.out;
                            StringBuilder stringBuilder = new StringBuilder("DEBUG SMTP: EOF: ");
                            stringBuilder.append((String)charSequence);
                            ((PrintStream)object).println(stringBuilder.toString());
                            return -1;
                        }
                        ((StringBuffer)object).append((String)charSequence);
                        ((StringBuffer)object).append("\n");
                    } while (this.isNotLastLine((String)charSequence));
                    charSequence = ((StringBuffer)object).toString();
                    if (charSequence == null || ((String)charSequence).length() < 3) break block15;
                }
                catch (IOException iOException) {
                    if (this.debug) {
                        PrintStream printStream = this.out;
                        object = new StringBuilder("DEBUG SMTP: exception reading response: ");
                        ((StringBuilder)object).append(iOException);
                        printStream.println(((StringBuilder)object).toString());
                    }
                    this.lastServerResponse = "";
                    this.lastReturnCode = 0;
                    throw new MessagingException("Exception reading response", iOException);
                }
                try {
                    n = Integer.parseInt(((String)charSequence).substring(0, 3));
                    break block16;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    try {
                        this.close();
                    }
                    catch (MessagingException messagingException) {
                        if (this.debug) {
                            messagingException.printStackTrace(this.out);
                        }
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    try {
                        this.close();
                    }
                    catch (MessagingException messagingException) {
                        if (!this.debug) break block15;
                        messagingException.printStackTrace(this.out);
                    }
                }
            }
            n = -1;
        }
        if (n == -1 && this.debug) {
            object = this.out;
            StringBuilder stringBuilder = new StringBuilder("DEBUG SMTP: bad server response: ");
            stringBuilder.append((String)charSequence);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        this.lastServerResponse = charSequence;
        this.lastReturnCode = n;
        return n;
    }

    protected void sendCommand(String string2) throws MessagingException {
        this.sendCommand(ASCIIUtility.getBytes(string2));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void sendMessage(Message var1_1, Address[] var2_6) throws MessagingException, SendFailedException {
        block25 : {
            // MONITORENTER : this
            this.checkConnected();
            if (!(var1_1 instanceof MimeMessage)) {
                if (this.debug) {
                    this.out.println("DEBUG SMTP: Can only send RFC822 msgs");
                }
                var1_1 = new MessagingException("SMTP can only send RFC822 messages");
                throw var1_1;
            }
            var3_8 = 0;
            do {
                if (var3_8 >= ((Object)var2_6).length) {
                    this.message = (MimeMessage)var1_1;
                    this.addresses = var2_6;
                    this.validUnsentAddr = var2_6;
                    this.expandGroups();
                    var4_9 = var1_1 instanceof SMTPMessage != false ? ((SMTPMessage)var1_1).getAllow8bitMIME() : false;
                    var5_10 = var4_9;
                    if (!var4_9) {
                        var2_6 = this.session;
                        var1_1 = new StringBuilder("mail.");
                        var1_1.append(this.name);
                        var1_1.append(".allow8bitmime");
                        var1_1 = var2_6.getProperty(var1_1.toString());
                        var5_10 = var1_1 != null && var1_1.equalsIgnoreCase("true") != false;
                    }
                    if (this.debug) {
                        var2_6 = this.out;
                        var1_1 = new StringBuilder("DEBUG SMTP: use8bit ");
                        var1_1.append(var5_10);
                        var2_6.println(var1_1.toString());
                    }
                    if (!var5_10 || !this.supportsExtension("8BITMIME") || !(var4_9 = this.convertTo8Bit(this.message))) break block25;
                    this.message.saveChanges();
                    break block25;
                }
                if (!(var2_6[var3_8] instanceof InternetAddress)) {
                    var6_11 = new StringBuilder();
                    var6_11.append(var2_6[var3_8]);
                    var6_11.append(" is not an InternetAddress");
                    var1_1 = new MessagingException(var6_11.toString());
                    throw var1_1;
                }
                ++var3_8;
            } while (true);
            catch (MessagingException var1_5) {}
        }
        this.mailFrom();
        this.rcptTo();
        this.message.writeTo(this.data(), SMTPTransport.ignoreList);
        this.finishData();
        if (this.sendPartiallyFailed) {
            if (this.debug) {
                this.out.println("DEBUG SMTP: Sending partially failed because of invalid destination addresses");
            }
            this.notifyTransportListeners(3, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
            var1_1 = new SMTPSendFailedException(".", this.lastReturnCode, this.lastServerResponse, this.exception, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
            throw var1_1;
        }
        this.notifyTransportListeners(1, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
        this.invalidAddr = null;
        this.validUnsentAddr = null;
        this.validSentAddr = null;
        this.addresses = null;
        this.message = null;
        this.exception = null;
        this.sendPartiallyFailed = false;
        return;
        {
            catch (Throwable var1_2) {
                ** GOTO lbl83
            }
            catch (IOException var1_3) {}
            {
                block26 : {
                    if (this.debug) {
                        var1_3.printStackTrace(this.out);
                    }
                    try {
                        this.closeConnection();
                        break block26;
                    }
                    catch (MessagingException var2_7) {}
                    catch (MessagingException var1_4) {
                        if (this.debug) {
                            var1_4.printStackTrace(this.out);
                        }
                        this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                        throw var1_4;
                    }
lbl83: // 1 sources:
                    this.invalidAddr = null;
                    this.validUnsentAddr = null;
                    this.validSentAddr = null;
                    this.addresses = null;
                    this.message = null;
                    this.exception = null;
                    this.sendPartiallyFailed = false;
                    throw var1_2;
                }
                this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                var2_6 = new MessagingException("IOException while sending message", var1_3);
                throw var2_6;
            }
        }
    }

    public void setLocalHost(String string2) {
        synchronized (this) {
            this.localHostName = string2;
            return;
        }
    }

    public void setReportSuccess(boolean bl) {
        synchronized (this) {
            this.reportSuccess = bl;
            return;
        }
    }

    public void setSASLRealm(String string2) {
        synchronized (this) {
            this.saslRealm = string2;
            return;
        }
    }

    public void setStartTLS(boolean bl) {
        synchronized (this) {
            this.useStartTLS = bl;
            return;
        }
    }

    public void setUseRset(boolean bl) {
        synchronized (this) {
            this.useRset = bl;
            return;
        }
    }

    public int simpleCommand(String string2) throws MessagingException {
        synchronized (this) {
            this.sendCommand(string2);
            return this.readServerResponse();
        }
    }

    protected int simpleCommand(byte[] arrby) throws MessagingException {
        this.sendCommand(arrby);
        return this.readServerResponse();
    }

    protected void startTLS() throws MessagingException {
        this.issueCommand("STARTTLS", 220);
        try {
            Socket socket = this.serverSocket;
            Properties properties = this.session.getProperties();
            StringBuilder stringBuilder = new StringBuilder("mail.");
            stringBuilder.append(this.name);
            this.serverSocket = SocketFetcher.startTLS(socket, properties, stringBuilder.toString());
            this.initStreams();
            return;
        }
        catch (IOException iOException) {
            this.closeConnection();
            throw new MessagingException("Could not convert socket to TLS", iOException);
        }
    }

    protected boolean supportsAuthentication(String string2) {
        Object object = this.extMap;
        if (object == null) {
            return false;
        }
        if ((object = (String)((Hashtable)object).get("AUTH")) == null) {
            return false;
        }
        object = new StringTokenizer((String)object);
        do {
            if (((StringTokenizer)object).hasMoreTokens()) continue;
            return false;
        } while (!((StringTokenizer)object).nextToken().equalsIgnoreCase(string2));
        return true;
    }

    public boolean supportsExtension(String string2) {
        Hashtable hashtable = this.extMap;
        if (hashtable == null) return false;
        if (hashtable.get(string2.toUpperCase(Locale.ENGLISH)) == null) return false;
        return true;
    }
}

