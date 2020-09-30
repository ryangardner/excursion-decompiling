/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.net.SocketFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.util.Base64;

public class FTPHTTPClient
extends FTPClient {
    private static final byte[] CRLF = new byte[]{13, 10};
    private final Base64 base64 = new Base64();
    private final String proxyHost;
    private final String proxyPassword;
    private final int proxyPort;
    private final String proxyUsername;
    private String tunnelHost;

    public FTPHTTPClient(String string2, int n) {
        this(string2, n, null, null);
    }

    public FTPHTTPClient(String string2, int n, String string3, String string4) {
        this.proxyHost = string2;
        this.proxyPort = n;
        this.proxyUsername = string3;
        this.proxyPassword = string4;
        this.tunnelHost = null;
    }

    private BufferedReader tunnelHandshake(String charSequence, int n, InputStream iterator2, OutputStream object) throws IOException, UnsupportedEncodingException {
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("CONNECT ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(":");
        ((StringBuilder)charSequence2).append(n);
        ((StringBuilder)charSequence2).append(" HTTP/1.1");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        CharSequence charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append("Host: ");
        ((StringBuilder)charSequence3).append((String)charSequence);
        ((StringBuilder)charSequence3).append(":");
        ((StringBuilder)charSequence3).append(n);
        charSequence3 = ((StringBuilder)charSequence3).toString();
        this.tunnelHost = charSequence;
        ((OutputStream)object).write(((String)charSequence2).getBytes("UTF-8"));
        ((OutputStream)object).write(CRLF);
        ((OutputStream)object).write(((String)charSequence3).getBytes("UTF-8"));
        ((OutputStream)object).write(CRLF);
        if (this.proxyUsername != null && this.proxyPassword != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.proxyUsername);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(this.proxyPassword);
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Proxy-Authorization: Basic ");
            ((StringBuilder)charSequence2).append(this.base64.encodeToString(((String)charSequence).getBytes("UTF-8")));
            ((OutputStream)object).write(((StringBuilder)charSequence2).toString().getBytes("UTF-8"));
        }
        ((OutputStream)object).write(CRLF);
        object = new ArrayList();
        iterator2 = new BufferedReader(new InputStreamReader((InputStream)((Object)iterator2), this.getCharsetName()));
        charSequence = ((BufferedReader)((Object)iterator2)).readLine();
        while (charSequence != null && ((String)charSequence).length() > 0) {
            object.add(charSequence);
            charSequence = ((BufferedReader)((Object)iterator2)).readLine();
        }
        if (object.size() == 0) throw new IOException("No response from proxy");
        charSequence = (String)object.get(0);
        if (((String)charSequence).startsWith("HTTP/") && ((String)charSequence).length() >= 12) {
            if ("200".equals(((String)charSequence).substring(9, 12))) return iterator2;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("HTTPTunnelConnector: connection failed\r\n");
            ((StringBuilder)charSequence).append("Response received from the proxy:\r\n");
            iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                ((StringBuilder)charSequence).append((String)iterator2.next());
                ((StringBuilder)charSequence).append("\r\n");
            }
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        iterator2 = new StringBuilder();
        ((StringBuilder)((Object)iterator2)).append("Invalid response from proxy: ");
        ((StringBuilder)((Object)iterator2)).append((String)charSequence);
        throw new IOException(((StringBuilder)((Object)iterator2)).toString());
    }

    @Deprecated
    @Override
    protected Socket _openDataConnection_(int n, String string2) throws IOException {
        return super._openDataConnection_(n, string2);
    }

    @Override
    protected Socket _openDataConnection_(String string2, String string3) throws IOException {
        String string4;
        if (this.getDataConnectionMode() != 2) throw new IllegalStateException("Only passive connection mode supported");
        boolean bl = this.getRemoteAddress() instanceof Inet6Address;
        boolean bl2 = this.isUseEPSVwithIPv4() || bl;
        if (bl2 && this.epsv() == 229) {
            this._parseExtendedPassiveModeReply((String)this._replyLines.get(0));
            string4 = this.tunnelHost;
        } else {
            if (bl) {
                return null;
            }
            if (this.pasv() != 227) {
                return null;
            }
            this._parsePassiveModeReply((String)this._replyLines.get(0));
            string4 = this.getPassiveHost();
        }
        Socket socket = this._socketFactory_.createSocket(this.proxyHost, this.proxyPort);
        InputStream inputStream2 = socket.getInputStream();
        OutputStream outputStream2 = socket.getOutputStream();
        this.tunnelHandshake(string4, this.getPassivePort(), inputStream2, outputStream2);
        if (this.getRestartOffset() > 0L && !this.restart(this.getRestartOffset())) {
            socket.close();
            return null;
        }
        if (FTPReply.isPositivePreliminary(this.sendCommand(string2, string3))) return socket;
        socket.close();
        return null;
    }

    @Override
    public void connect(String object, int n) throws SocketException, IOException {
        BufferedReader bufferedReader;
        this._socket_ = this._socketFactory_.createSocket(this.proxyHost, this.proxyPort);
        this._input_ = this._socket_.getInputStream();
        this._output_ = this._socket_.getOutputStream();
        try {
            bufferedReader = this.tunnelHandshake((String)object, n, this._input_, this._output_);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not connect to ");
            stringBuilder.append((String)object);
            stringBuilder.append(" using port ");
            stringBuilder.append(n);
            object = new IOException(stringBuilder.toString());
            ((Throwable)object).initCause(exception);
            throw object;
        }
        super._connectAction_(bufferedReader);
    }
}

