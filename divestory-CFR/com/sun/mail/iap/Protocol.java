/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.iap.ResponseInputStream;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;

public class Protocol {
    private static final byte[] CRLF = new byte[]{13, 10};
    private boolean connected;
    protected boolean debug;
    private volatile Vector handlers;
    protected String host;
    private volatile ResponseInputStream input;
    protected PrintStream out;
    private volatile DataOutputStream output;
    protected String prefix;
    protected Properties props;
    protected boolean quote;
    private Socket socket;
    private int tagCounter;
    private volatile long timestamp;
    private TraceInputStream traceInput;
    private TraceOutputStream traceOutput;

    public Protocol(InputStream closeable, OutputStream outputStream2, boolean bl) throws IOException {
        this.connected = false;
        this.tagCounter = 0;
        this.handlers = null;
        this.host = "localhost";
        this.debug = bl;
        this.quote = false;
        this.out = System.out;
        closeable = new TraceInputStream((InputStream)closeable, System.out);
        this.traceInput = closeable;
        ((TraceInputStream)closeable).setTrace(bl);
        this.traceInput.setQuote(this.quote);
        this.input = new ResponseInputStream(this.traceInput);
        closeable = new TraceOutputStream(outputStream2, System.out);
        this.traceOutput = closeable;
        ((TraceOutputStream)closeable).setTrace(bl);
        this.traceOutput.setQuote(this.quote);
        this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
        this.timestamp = System.currentTimeMillis();
    }

    public Protocol(String string2, int n, boolean bl, PrintStream printStream, Properties properties, String string3, boolean bl2) throws IOException, ProtocolException {
        boolean bl3 = false;
        this.connected = false;
        this.tagCounter = 0;
        this.handlers = null;
        try {
            this.host = string2;
            this.debug = bl;
            this.out = printStream;
            this.props = properties;
            this.prefix = string3;
            this.socket = SocketFetcher.getSocket(string2, n, properties, string3, bl2);
            string2 = properties.getProperty("mail.debug.quote");
            bl = bl3;
            if (string2 != null) {
                bl = bl3;
                if (string2.equalsIgnoreCase("true")) {
                    bl = true;
                }
            }
            this.quote = bl;
            this.initStreams(printStream);
            this.processGreeting(this.readResponse());
            this.timestamp = System.currentTimeMillis();
            this.connected = true;
            return;
        }
        finally {
            if (!true) {
                this.disconnect();
            }
        }
    }

    private void initStreams(PrintStream filterOutputStream) throws IOException {
        TraceInputStream traceInputStream;
        this.traceInput = traceInputStream = new TraceInputStream(this.socket.getInputStream(), filterOutputStream);
        traceInputStream.setTrace(this.debug);
        this.traceInput.setQuote(this.quote);
        this.input = new ResponseInputStream(this.traceInput);
        filterOutputStream = new TraceOutputStream(this.socket.getOutputStream(), filterOutputStream);
        this.traceOutput = filterOutputStream;
        ((TraceOutputStream)filterOutputStream).setTrace(this.debug);
        this.traceOutput.setQuote(this.quote);
        this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
    }

    public void addResponseHandler(ResponseHandler responseHandler) {
        synchronized (this) {
            if (this.handlers == null) {
                Vector vector;
                this.handlers = vector = new Vector();
            }
            this.handlers.addElement(responseHandler);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public Response[] command(String var1_1, Argument var2_5) {
        // MONITORENTER : this
        var3_7 = new Vector<Response>();
        var4_8 = false;
        var5_9 = null;
        try {
            var1_1 = this.writeCommand((String)var1_1, (Argument)var2_4);
            ** GOTO lbl17
        }
        catch (Exception var1_2) {
            var3_7.addElement(Response.byeResponse(var1_2));
            var1_1 = var5_9;
        }
        catch (LiteralException var1_3) {
            var3_7.addElement(var1_3.getResponse());
            var1_1 = var5_9;
        }
        do {
            var4_8 = true;
lbl17: // 2 sources:
            do {
                if (var4_8) {
                    var1_1 = new Response[var3_7.size()];
                    var3_7.copyInto(var1_1);
                    this.timestamp = System.currentTimeMillis();
                    // MONITOREXIT : this
                    return var1_1;
                }
                try {
                    var2_4 = this.readResponse();
                }
                catch (ProtocolException var2_5) {
                    continue;
                }
                catch (IOException var2_6) {
                    var2_4 = Response.byeResponse(var2_6);
                }
                var3_7.addElement(var2_4);
                var6_10 = var4_8;
                if (var2_4.isBYE()) {
                    var6_10 = true;
                }
                var4_8 = var6_10;
                if (!var2_4.isTagged()) continue;
                var7_11 = var2_4.getTag().equals(var1_1);
                var4_8 = var6_10;
                if (var7_11) break;
            } while (true);
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    protected void disconnect() {
        // MONITORENTER : this
        var1_1 = this.socket;
        if (var1_1 != null) {
            try {
                this.socket.close();
lbl6: // 2 sources:
                do {
                    this.socket = null;
                    return;
                    break;
                } while (true);
            }
            catch (IOException var1_2) {
                ** continue;
            }
        }
        // MONITOREXIT : this
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.disconnect();
    }

    protected ResponseInputStream getInputStream() {
        return this.input;
    }

    protected OutputStream getOutputStream() {
        return this.output;
    }

    protected ByteArray getResponseBuffer() {
        return null;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void handleResult(Response response) throws ProtocolException {
        if (response.isOK()) {
            return;
        }
        if (response.isNO()) throw new CommandFailedException(response);
        if (response.isBAD()) throw new BadCommandException(response);
        if (!response.isBYE()) {
            return;
        }
        this.disconnect();
        throw new ConnectionException(this, response);
    }

    /*
     * Unable to fully structure code
     */
    public void notifyResponseHandlers(Response[] var1_1) {
        if (this.handlers == null) {
            return;
        }
        var2_2 = 0;
        block0 : do {
            if (var2_2 >= var1_1.length) {
                return;
            }
            var3_3 = var1_1[var2_2];
            if (var3_3 == null) ** GOTO lbl17
            var4_4 = this.handlers.size();
            if (var4_4 == 0) {
                return;
            }
            var5_5 = new Object[var4_4];
            this.handlers.copyInto(var5_5);
            var6_6 = 0;
            do {
                block8 : {
                    if (var6_6 < var4_4) break block8;
lbl17: // 2 sources:
                    ++var2_2;
                    continue block0;
                }
                ((ResponseHandler)var5_5[var6_6]).handleResponse(var3_3);
                ++var6_6;
            } while (true);
            break;
        } while (true);
    }

    protected void processGreeting(Response response) throws ProtocolException {
        if (response.isBYE()) throw new ConnectionException(this, response);
    }

    public Response readResponse() throws IOException, ProtocolException {
        return new Response(this);
    }

    public void removeResponseHandler(ResponseHandler responseHandler) {
        synchronized (this) {
            if (this.handlers == null) return;
            this.handlers.removeElement(responseHandler);
            return;
        }
    }

    public void simpleCommand(String arrresponse, Argument argument) throws ProtocolException {
        arrresponse = this.command((String)arrresponse, argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
    }

    public void startTLS(String string2) throws IOException, ProtocolException {
        synchronized (this) {
            this.simpleCommand(string2, null);
            this.socket = SocketFetcher.startTLS(this.socket, this.props, this.prefix);
            this.initStreams(this.out);
            return;
        }
    }

    /*
     * Converted monitor instructions to comments
     */
    protected boolean supportsNonSyncLiterals() {
        // MONITORENTER : this
        // MONITOREXIT : this
        return false;
    }

    public String writeCommand(String string2, Argument argument) throws IOException, ProtocolException {
        StringBuilder stringBuilder = new StringBuilder("A");
        int n = this.tagCounter;
        this.tagCounter = n + 1;
        stringBuilder.append(Integer.toString(n, 10));
        String string3 = stringBuilder.toString();
        DataOutputStream dataOutputStream = this.output;
        stringBuilder = new StringBuilder(String.valueOf(string3));
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        dataOutputStream.writeBytes(stringBuilder.toString());
        if (argument != null) {
            this.output.write(32);
            argument.write(this);
        }
        this.output.write(CRLF);
        this.output.flush();
        return string3;
    }
}

