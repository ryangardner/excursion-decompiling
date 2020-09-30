/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.MLSxEntryParser;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.FromNetASCIIInputStream;
import org.apache.commons.net.io.SocketInputStream;
import org.apache.commons.net.io.SocketOutputStream;
import org.apache.commons.net.io.ToNetASCIIOutputStream;
import org.apache.commons.net.io.Util;

public class FTPClient
extends FTP
implements Configurable {
    public static final int ACTIVE_LOCAL_DATA_CONNECTION_MODE = 0;
    public static final int ACTIVE_REMOTE_DATA_CONNECTION_MODE = 1;
    public static final String FTP_SYSTEM_TYPE = "org.apache.commons.net.ftp.systemType";
    public static final String FTP_SYSTEM_TYPE_DEFAULT = "org.apache.commons.net.ftp.systemType.default";
    public static final int PASSIVE_LOCAL_DATA_CONNECTION_MODE = 2;
    public static final int PASSIVE_REMOTE_DATA_CONNECTION_MODE = 3;
    public static final String SYSTEM_TYPE_PROPERTIES = "/systemType.properties";
    private static final Pattern __PARMS_PAT = Pattern.compile("(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})");
    private InetAddress __activeExternalHost;
    private int __activeMaxPort;
    private int __activeMinPort;
    private boolean __autodetectEncoding = false;
    private int __bufferSize;
    private FTPClientConfig __configuration;
    private int __controlKeepAliveReplyTimeout = 1000;
    private long __controlKeepAliveTimeout;
    private CopyStreamListener __copyStreamListener;
    private int __dataConnectionMode;
    private int __dataTimeout;
    private FTPFileEntryParser __entryParser;
    private String __entryParserKey;
    private HashMap<String, Set<String>> __featuresMap;
    private int __fileFormat;
    private int __fileStructure;
    private int __fileTransferMode;
    private int __fileType;
    private boolean __listHiddenFiles;
    private FTPFileEntryParserFactory __parserFactory;
    private String __passiveHost;
    private InetAddress __passiveLocalHost;
    private boolean __passiveNatWorkaround = true;
    private int __passivePort;
    private final Random __random;
    private int __receiveDataSocketBufferSize;
    private boolean __remoteVerificationEnabled;
    private InetAddress __reportActiveExternalHost;
    private long __restartOffset;
    private int __sendDataSocketBufferSize;
    private String __systemName;
    private boolean __useEPSVwithIPv4;

    public FTPClient() {
        this.__initDefaults();
        this.__dataTimeout = -1;
        this.__remoteVerificationEnabled = true;
        this.__parserFactory = new DefaultFTPFileEntryParserFactory();
        this.__configuration = null;
        this.__listHiddenFiles = false;
        this.__useEPSVwithIPv4 = false;
        this.__random = new Random();
        this.__passiveLocalHost = null;
    }

    private void __initDefaults() {
        this.__dataConnectionMode = 0;
        this.__passiveHost = null;
        this.__passivePort = -1;
        this.__activeExternalHost = null;
        this.__reportActiveExternalHost = null;
        this.__activeMinPort = 0;
        this.__activeMaxPort = 0;
        this.__fileType = 0;
        this.__fileStructure = 7;
        this.__fileFormat = 4;
        this.__fileTransferMode = 10;
        this.__restartOffset = 0L;
        this.__systemName = null;
        this.__entryParser = null;
        this.__entryParserKey = "";
        this.__featuresMap = null;
    }

    private CopyStreamListener __mergeListeners(CopyStreamListener copyStreamListener) {
        if (copyStreamListener == null) {
            return this.__copyStreamListener;
        }
        if (this.__copyStreamListener == null) {
            return copyStreamListener;
        }
        CopyStreamAdapter copyStreamAdapter = new CopyStreamAdapter();
        copyStreamAdapter.addCopyStreamListener(copyStreamListener);
        copyStreamAdapter.addCopyStreamListener(this.__copyStreamListener);
        return copyStreamAdapter;
    }

    static String __parsePathname(String string2) {
        String string3;
        string2 = string3 = string2.substring(4);
        if (!string3.startsWith("\"")) return string2;
        StringBuilder stringBuilder = new StringBuilder();
        int n = 1;
        boolean bl = false;
        do {
            if (n >= string3.length()) {
                string2 = string3;
                if (!bl) return string2;
                return stringBuilder.toString();
            }
            char c = string3.charAt(n);
            if (c == '\"') {
                if (bl) {
                    stringBuilder.append(c);
                    bl = false;
                } else {
                    bl = true;
                }
            } else {
                if (bl) {
                    return stringBuilder.toString();
                }
                stringBuilder.append(c);
            }
            ++n;
        } while (true);
    }

    private boolean __storeFile(FTPCmd fTPCmd, String string2, InputStream inputStream2) throws IOException {
        return this._storeFile(fTPCmd.getCommand(), string2, inputStream2);
    }

    private OutputStream __storeFileStream(FTPCmd fTPCmd, String string2) throws IOException {
        return this._storeFileStream(fTPCmd.getCommand(), string2);
    }

    private int getActivePort() {
        int n = this.__activeMinPort;
        if (n <= 0) return 0;
        int n2 = this.__activeMaxPort;
        if (n2 < n) return 0;
        if (n2 != n) return this.__random.nextInt(n2 - n + 1) + this.__activeMinPort;
        return n2;
    }

    private InputStream getBufferedInputStream(InputStream inputStream2) {
        if (this.__bufferSize <= 0) return new BufferedInputStream(inputStream2);
        return new BufferedInputStream(inputStream2, this.__bufferSize);
    }

    private OutputStream getBufferedOutputStream(OutputStream outputStream2) {
        if (this.__bufferSize <= 0) return new BufferedOutputStream(outputStream2);
        return new BufferedOutputStream(outputStream2, this.__bufferSize);
    }

    private InetAddress getHostAddress() {
        InetAddress inetAddress = this.__activeExternalHost;
        if (inetAddress == null) return this.getLocalAddress();
        return inetAddress;
    }

    private static Properties getOverrideProperties() {
        return PropertiesSingleton.PROPERTIES;
    }

    private InetAddress getReportHostAddress() {
        InetAddress inetAddress = this.__reportActiveExternalHost;
        if (inetAddress == null) return this.getHostAddress();
        return inetAddress;
    }

    private boolean initFeatureMap() throws IOException {
        if (this.__featuresMap != null) return true;
        int n = this.feat();
        int n2 = 0;
        if (n == 530) {
            return false;
        }
        boolean bl = FTPReply.isPositiveCompletion(n);
        this.__featuresMap = new HashMap();
        if (!bl) {
            return false;
        }
        String[] arrstring = this.getReplyStrings();
        n = arrstring.length;
        while (n2 < n) {
            String string2 = arrstring[n2];
            if (string2.startsWith(" ")) {
                HashSet<String> hashSet;
                int n3 = string2.indexOf(32, 1);
                if (n3 > 0) {
                    hashSet = string2.substring(1, n3);
                    string2 = string2.substring(n3 + 1);
                } else {
                    hashSet = string2.substring(1);
                    string2 = "";
                }
                String string3 = ((String)((Object)hashSet)).toUpperCase(Locale.ENGLISH);
                Set<String> set = this.__featuresMap.get(string3);
                hashSet = set;
                if (set == null) {
                    hashSet = new HashSet<String>();
                    this.__featuresMap.put(string3, hashSet);
                }
                hashSet.add(string2);
            }
            ++n2;
        }
        return true;
    }

    private FTPListParseEngine initiateListParsing(FTPFileEntryParser object, String object2) throws IOException {
        object2 = this._openDataConnection_(FTPCmd.LIST, this.getListArguments((String)object2));
        object = new FTPListParseEngine((FTPFileEntryParser)object, this.__configuration);
        if (object2 == null) {
            return object;
        }
        ((FTPListParseEngine)object).readServerList(((Socket)object2).getInputStream(), this.getControlEncoding());
        this.completePendingCommand();
        return object;
        finally {
            Util.closeQuietly((Socket)object2);
        }
    }

    private FTPListParseEngine initiateMListParsing(String object) throws IOException {
        object = this._openDataConnection_(FTPCmd.MLSD, (String)object);
        FTPListParseEngine fTPListParseEngine = new FTPListParseEngine(MLSxEntryParser.getInstance(), this.__configuration);
        if (object == null) {
            return fTPListParseEngine;
        }
        try {
            fTPListParseEngine.readServerList(((Socket)object).getInputStream(), this.getControlEncoding());
            return fTPListParseEngine;
        }
        finally {
            Util.closeQuietly((Socket)object);
            this.completePendingCommand();
        }
    }

    void __createParser(String object) throws IOException {
        if (this.__entryParser != null) {
            if (object == null) return;
            if (this.__entryParserKey.equals(object)) return;
        }
        if (object != null) {
            this.__entryParser = this.__parserFactory.createFileEntryParser((String)object);
            this.__entryParserKey = object;
            return;
        }
        object = this.__configuration;
        if (object != null && ((FTPClientConfig)object).getServerSystemKey().length() > 0) {
            this.__entryParser = this.__parserFactory.createFileEntryParser(this.__configuration);
            this.__entryParserKey = this.__configuration.getServerSystemKey();
            return;
        }
        String string2 = System.getProperty(FTP_SYSTEM_TYPE);
        object = string2;
        if (string2 == null) {
            string2 = this.getSystemType();
            Object object2 = FTPClient.getOverrideProperties();
            object = string2;
            if (object2 != null) {
                object2 = ((Properties)object2).getProperty(string2);
                object = string2;
                if (object2 != null) {
                    object = object2;
                }
            }
        }
        this.__entryParser = this.__configuration != null ? this.__parserFactory.createFileEntryParser(new FTPClientConfig((String)object, this.__configuration)) : this.__parserFactory.createFileEntryParser((String)object);
        this.__entryParserKey = object;
    }

    @Override
    protected void _connectAction_() throws IOException {
        this._connectAction_(null);
    }

    @Override
    protected void _connectAction_(Reader object) throws IOException {
        super._connectAction_((Reader)object);
        this.__initDefaults();
        if (!this.__autodetectEncoding) return;
        object = new ArrayList(this._replyLines);
        int n = this._replyCode;
        if (this.hasFeature("UTF8") || this.hasFeature("UTF-8")) {
            this.setControlEncoding("UTF-8");
            this._controlInput_ = new CRLFLineReader(new InputStreamReader(this._input_, this.getControlEncoding()));
            this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._output_, this.getControlEncoding()));
        }
        this._replyLines.clear();
        this._replyLines.addAll(object);
        this._replyCode = n;
    }

    @Deprecated
    protected Socket _openDataConnection_(int n, String string2) throws IOException {
        return this._openDataConnection_(FTPCommand.getCommand(n), string2);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    protected Socket _openDataConnection_(String var1_1, String var2_3) throws IOException {
        var3_4 = this.__dataConnectionMode;
        if (var3_4 != 0 && var3_4 != 2) {
            return null;
        }
        var4_5 = this.getRemoteAddress() instanceof Inet6Address;
        var3_4 = this.__dataConnectionMode;
        var5_6 = 1;
        if (var3_4 == 0) {
            block29 : {
                block28 : {
                    block27 : {
                        var6_7 = this._serverSocketFactory_.createServerSocket(this.getActivePort(), 1, this.getHostAddress());
                        if (var4_5) {
                            var4_5 = FTPReply.isPositiveCompletion(this.eprt(this.getReportHostAddress(), var6_7.getLocalPort()));
                            ** if (var4_5) goto lbl-1000
lbl-1000: // 1 sources:
                            {
                                var6_7.close();
                                return null;
                            }
lbl-1000: // 1 sources:
                            {
                                break block27;
                            }
                        }
                        var4_5 = FTPReply.isPositiveCompletion(this.port(this.getReportHostAddress(), var6_7.getLocalPort()));
                        if (var4_5) break block27;
                        var6_7.close();
                        return null;
                    }
                    if (this.__restartOffset <= 0L || (var4_5 = this.restart(this.__restartOffset))) break block28;
                    var6_7.close();
                    return null;
                }
                var4_5 = FTPReply.isPositivePreliminary(this.sendCommand((String)var1_1, (String)var2_3));
                if (var4_5) break block29;
                var6_7.close();
                return null;
            }
            try {
                if (this.__dataTimeout >= 0) {
                    var6_7.setSoTimeout(this.__dataTimeout);
                }
                var1_1 = var6_7.accept();
                if (this.__dataTimeout >= 0) {
                    var1_1.setSoTimeout(this.__dataTimeout);
                }
                if (this.__receiveDataSocketBufferSize > 0) {
                    var1_1.setReceiveBufferSize(this.__receiveDataSocketBufferSize);
                }
                if (this.__sendDataSocketBufferSize <= 0) ** GOTO lbl79
                var1_1.setSendBufferSize(this.__sendDataSocketBufferSize);
            }
            finally {
                var6_7.close();
            }
        } else {
            var3_4 = var5_6;
            if (!this.isUseEPSVwithIPv4()) {
                var3_4 = var4_5 != false ? var5_6 : 0;
            }
            if (var3_4 != 0 && this.epsv() == 229) {
                this._parseExtendedPassiveModeReply((String)this._replyLines.get(0));
            } else {
                if (var4_5) {
                    return null;
                }
                if (this.pasv() != 227) {
                    return null;
                }
                this._parsePassiveModeReply((String)this._replyLines.get(0));
            }
            var6_8 = this._socketFactory_.createSocket();
            var3_4 = this.__receiveDataSocketBufferSize;
            if (var3_4 > 0) {
                var6_8.setReceiveBufferSize(var3_4);
            }
            if ((var3_4 = this.__sendDataSocketBufferSize) > 0) {
                var6_8.setSendBufferSize(var3_4);
            }
            if (this.__passiveLocalHost != null) {
                var6_8.bind(new InetSocketAddress(this.__passiveLocalHost, 0));
            }
            if ((var3_4 = this.__dataTimeout) >= 0) {
                var6_8.setSoTimeout(var3_4);
            }
            var6_8.connect(new InetSocketAddress(this.__passiveHost, this.__passivePort), this.connectTimeout);
            var7_9 = this.__restartOffset;
            if (var7_9 > 0L && !this.restart(var7_9)) {
                var6_8.close();
                return null;
            }
            if (!FTPReply.isPositivePreliminary(this.sendCommand((String)var1_1, (String)var2_3))) {
                var6_8.close();
                return null;
            }
            var1_1 = var6_8;
        }
lbl79: // 3 sources:
        if (this.__remoteVerificationEnabled == false) return var1_1;
        if (this.verifyRemote((Socket)var1_1)) {
            return var1_1;
        }
        var1_1.close();
        var2_3 = new StringBuilder();
        var2_3.append("Host attempting data connection ");
        var2_3.append(var1_1.getInetAddress().getHostAddress());
        var2_3.append(" is not same as server ");
        var2_3.append(this.getRemoteAddress().getHostAddress());
        throw new IOException(var2_3.toString());
    }

    protected Socket _openDataConnection_(FTPCmd fTPCmd, String string2) throws IOException {
        return this._openDataConnection_(fTPCmd.getCommand(), string2);
    }

    protected void _parseExtendedPassiveModeReply(String string2) throws MalformedServerReplyException {
        string2 = string2.substring(string2.indexOf(40) + 1, string2.indexOf(41)).trim();
        int n = string2.charAt(0);
        char c = string2.charAt(1);
        char c2 = string2.charAt(2);
        char c3 = string2.charAt(string2.length() - 1);
        if (n == c && c == c2 && c2 == c3) {
            try {
                n = Integer.parseInt(string2.substring(3, string2.length() - 1));
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not parse extended passive host information.\nServer Reply: ");
                stringBuilder.append(string2);
                throw new MalformedServerReplyException(stringBuilder.toString());
            }
            this.__passiveHost = this.getRemoteAddress().getHostAddress();
            this.__passivePort = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not parse extended passive host information.\nServer Reply: ");
        stringBuilder.append(string2);
        throw new MalformedServerReplyException(stringBuilder.toString());
    }

    protected void _parsePassiveModeReply(String string2) throws MalformedServerReplyException {
        Object object = __PARMS_PAT.matcher(string2);
        if (!((Matcher)object).find()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not parse passive host information.\nServer Reply: ");
            ((StringBuilder)object).append(string2);
            throw new MalformedServerReplyException(((StringBuilder)object).toString());
        }
        this.__passiveHost = ((Matcher)object).group(1).replace(',', '.');
        try {
            int n = Integer.parseInt(((Matcher)object).group(2));
            this.__passivePort = Integer.parseInt(((Matcher)object).group(3)) | n << 8;
            if (!this.__passiveNatWorkaround) return;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse passive port information.\nServer Reply: ");
            stringBuilder.append(string2);
            throw new MalformedServerReplyException(stringBuilder.toString());
        }
        try {
            if (!InetAddress.getByName(this.__passiveHost).isSiteLocalAddress()) return;
            object = this.getRemoteAddress();
            if (((InetAddress)object).isSiteLocalAddress()) return;
            object = ((InetAddress)object).getHostAddress();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[Replacing site local address ");
            stringBuilder.append(this.__passiveHost);
            stringBuilder.append(" with ");
            stringBuilder.append((String)object);
            stringBuilder.append("]\n");
            this.fireReplyReceived(0, stringBuilder.toString());
            this.__passiveHost = object;
            return;
        }
        catch (UnknownHostException unknownHostException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse passive host information.\nServer Reply: ");
            stringBuilder.append(string2);
            throw new MalformedServerReplyException(stringBuilder.toString());
        }
    }

    protected boolean _retrieveFile(String object, String object2, OutputStream outputStream2) throws IOException {
        Socket socket = this._openDataConnection_((String)object, (String)object2);
        if (socket == null) {
            return false;
        }
        object = this.__fileType == 0 ? new FromNetASCIIInputStream(this.getBufferedInputStream(socket.getInputStream())) : this.getBufferedInputStream(socket.getInputStream());
        object2 = null;
        if (this.__controlKeepAliveTimeout > 0L) {
            object2 = new CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
        }
        try {
            Util.copyStream((InputStream)object, outputStream2, this.getBufferSize(), -1L, this.__mergeListeners((CopyStreamListener)object2), false);
            return this.completePendingCommand();
        }
        finally {
            Util.closeQuietly((Closeable)object);
            Util.closeQuietly(socket);
            if (object2 != null) {
                ((CSL)object2).cleanUp();
            }
        }
    }

    protected InputStream _retrieveFileStream(String object, String object2) throws IOException {
        if ((object2 = this._openDataConnection_((String)object, (String)object2)) == null) {
            return null;
        }
        if (this.__fileType == 0) {
            object = new FromNetASCIIInputStream(this.getBufferedInputStream(((Socket)object2).getInputStream()));
            return new SocketInputStream((Socket)object2, (InputStream)object);
        }
        object = ((Socket)object2).getInputStream();
        return new SocketInputStream((Socket)object2, (InputStream)object);
    }

    protected boolean _storeFile(String object, String object2, InputStream inputStream2) throws IOException {
        Socket socket = this._openDataConnection_((String)object, (String)object2);
        if (socket == null) {
            return false;
        }
        object = this.__fileType == 0 ? new ToNetASCIIOutputStream(this.getBufferedOutputStream(socket.getOutputStream())) : this.getBufferedOutputStream(socket.getOutputStream());
        object2 = null;
        if (this.__controlKeepAliveTimeout > 0L) {
            object2 = new CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
        }
        try {
            Util.copyStream(inputStream2, (OutputStream)object, this.getBufferSize(), -1L, this.__mergeListeners((CopyStreamListener)object2), false);
        }
        catch (IOException iOException) {
            Util.closeQuietly(socket);
            if (object2 == null) throw iOException;
            ((CSL)object2).cleanUp();
            throw iOException;
        }
        ((OutputStream)object).close();
        socket.close();
        if (object2 == null) return this.completePendingCommand();
        ((CSL)object2).cleanUp();
        return this.completePendingCommand();
    }

    protected OutputStream _storeFileStream(String object, String object2) throws IOException {
        if ((object2 = this._openDataConnection_((String)object, (String)object2)) == null) {
            return null;
        }
        if (this.__fileType == 0) {
            object = new ToNetASCIIOutputStream(this.getBufferedOutputStream(((Socket)object2).getOutputStream()));
            return new SocketOutputStream((Socket)object2, (OutputStream)object);
        }
        object = ((Socket)object2).getOutputStream();
        return new SocketOutputStream((Socket)object2, (OutputStream)object);
    }

    public boolean abort() throws IOException {
        return FTPReply.isPositiveCompletion(this.abor());
    }

    public boolean allocate(int n) throws IOException {
        return FTPReply.isPositiveCompletion(this.allo(n));
    }

    public boolean allocate(int n, int n2) throws IOException {
        return FTPReply.isPositiveCompletion(this.allo(n, n2));
    }

    public boolean appendFile(String string2, InputStream inputStream2) throws IOException {
        return this.__storeFile(FTPCmd.APPE, string2, inputStream2);
    }

    public OutputStream appendFileStream(String string2) throws IOException {
        return this.__storeFileStream(FTPCmd.APPE, string2);
    }

    public boolean changeToParentDirectory() throws IOException {
        return FTPReply.isPositiveCompletion(this.cdup());
    }

    public boolean changeWorkingDirectory(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.cwd(string2));
    }

    public boolean completePendingCommand() throws IOException {
        return FTPReply.isPositiveCompletion(this.getReply());
    }

    @Override
    public void configure(FTPClientConfig fTPClientConfig) {
        this.__configuration = fTPClientConfig;
    }

    public boolean deleteFile(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.dele(string2));
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this.__initDefaults();
    }

    public boolean doCommand(String string2, String string3) throws IOException {
        return FTPReply.isPositiveCompletion(this.sendCommand(string2, string3));
    }

    public String[] doCommandAsStrings(String string2, String string3) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.sendCommand(string2, string3))) return null;
        return this.getReplyStrings();
    }

    public void enterLocalActiveMode() {
        this.__dataConnectionMode = 0;
        this.__passiveHost = null;
        this.__passivePort = -1;
    }

    public void enterLocalPassiveMode() {
        this.__dataConnectionMode = 2;
        this.__passiveHost = null;
        this.__passivePort = -1;
    }

    public boolean enterRemoteActiveMode(InetAddress inetAddress, int n) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.port(inetAddress, n))) return false;
        this.__dataConnectionMode = 1;
        this.__passiveHost = null;
        this.__passivePort = -1;
        return true;
    }

    public boolean enterRemotePassiveMode() throws IOException {
        if (this.pasv() != 227) {
            return false;
        }
        this.__dataConnectionMode = 3;
        this._parsePassiveModeReply((String)this._replyLines.get(0));
        return true;
    }

    public String featureValue(String arrstring) throws IOException {
        if ((arrstring = this.featureValues((String)arrstring)) == null) return null;
        return arrstring[0];
    }

    public String[] featureValues(String object) throws IOException {
        if (!this.initFeatureMap()) {
            return null;
        }
        if ((object = this.__featuresMap.get(((String)object).toUpperCase(Locale.ENGLISH))) == null) return null;
        return object.toArray(new String[object.size()]);
    }

    public boolean features() throws IOException {
        return FTPReply.isPositiveCompletion(this.feat());
    }

    public boolean getAutodetectUTF8() {
        return this.__autodetectEncoding;
    }

    public int getBufferSize() {
        return this.__bufferSize;
    }

    public int getControlKeepAliveReplyTimeout() {
        return this.__controlKeepAliveReplyTimeout;
    }

    public long getControlKeepAliveTimeout() {
        return this.__controlKeepAliveTimeout / 1000L;
    }

    public CopyStreamListener getCopyStreamListener() {
        return this.__copyStreamListener;
    }

    public int getDataConnectionMode() {
        return this.__dataConnectionMode;
    }

    FTPFileEntryParser getEntryParser() {
        return this.__entryParser;
    }

    protected String getListArguments(String string2) {
        CharSequence charSequence = string2;
        if (!this.getListHiddenFiles()) return charSequence;
        if (string2 == null) return "-a";
        charSequence = new StringBuilder(string2.length() + 3);
        ((StringBuilder)charSequence).append("-a ");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    public boolean getListHiddenFiles() {
        return this.__listHiddenFiles;
    }

    public String getModificationTime(String string2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.mdtm(string2))) return null;
        return this.getReplyStrings()[0].substring(4);
    }

    public String getPassiveHost() {
        return this.__passiveHost;
    }

    public InetAddress getPassiveLocalIPAddress() {
        return this.__passiveLocalHost;
    }

    public int getPassivePort() {
        return this.__passivePort;
    }

    public int getReceiveDataSocketBufferSize() {
        return this.__receiveDataSocketBufferSize;
    }

    public long getRestartOffset() {
        return this.__restartOffset;
    }

    public int getSendDataSocketBufferSize() {
        return this.__sendDataSocketBufferSize;
    }

    public String getStatus() throws IOException {
        if (!FTPReply.isPositiveCompletion(this.stat())) return null;
        return this.getReplyString();
    }

    public String getStatus(String string2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.stat(string2))) return null;
        return this.getReplyString();
    }

    @Deprecated
    public String getSystemName() throws IOException {
        if (this.__systemName != null) return this.__systemName;
        if (!FTPReply.isPositiveCompletion(this.syst())) return this.__systemName;
        this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
        return this.__systemName;
    }

    public String getSystemType() throws IOException {
        if (this.__systemName != null) return this.__systemName;
        if (FTPReply.isPositiveCompletion(this.syst())) {
            this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
            return this.__systemName;
        }
        CharSequence charSequence = System.getProperty(FTP_SYSTEM_TYPE_DEFAULT);
        if (charSequence != null) {
            this.__systemName = charSequence;
            return this.__systemName;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to determine system type - response: ");
        ((StringBuilder)charSequence).append(this.getReplyString());
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public boolean hasFeature(String string2) throws IOException {
        if (this.initFeatureMap()) return this.__featuresMap.containsKey(string2.toUpperCase(Locale.ENGLISH));
        return false;
    }

    public boolean hasFeature(String object, String string2) throws IOException {
        if (!this.initFeatureMap()) {
            return false;
        }
        if ((object = this.__featuresMap.get(((String)object).toUpperCase(Locale.ENGLISH))) == null) return false;
        return object.contains(string2);
    }

    public FTPListParseEngine initiateListParsing() throws IOException {
        return this.initiateListParsing(null);
    }

    public FTPListParseEngine initiateListParsing(String string2) throws IOException {
        return this.initiateListParsing((String)null, string2);
    }

    public FTPListParseEngine initiateListParsing(String string2, String string3) throws IOException {
        this.__createParser(string2);
        return this.initiateListParsing(this.__entryParser, string3);
    }

    public boolean isRemoteVerificationEnabled() {
        return this.__remoteVerificationEnabled;
    }

    public boolean isUseEPSVwithIPv4() {
        return this.__useEPSVwithIPv4;
    }

    public FTPFile[] listDirectories() throws IOException {
        return this.listDirectories(null);
    }

    public FTPFile[] listDirectories(String string2) throws IOException {
        return this.listFiles(string2, FTPFileFilters.DIRECTORIES);
    }

    public FTPFile[] listFiles() throws IOException {
        return this.listFiles(null);
    }

    public FTPFile[] listFiles(String string2) throws IOException {
        return this.initiateListParsing((String)null, string2).getFiles();
    }

    public FTPFile[] listFiles(String string2, FTPFileFilter fTPFileFilter) throws IOException {
        return this.initiateListParsing((String)null, string2).getFiles(fTPFileFilter);
    }

    public String listHelp() throws IOException {
        if (!FTPReply.isPositiveCompletion(this.help())) return null;
        return this.getReplyString();
    }

    public String listHelp(String string2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.help(string2))) return null;
        return this.getReplyString();
    }

    public String[] listNames() throws IOException {
        return this.listNames(null);
    }

    public String[] listNames(String object) throws IOException {
        if ((object = this._openDataConnection_(FTPCmd.NLST, this.getListArguments((String)object))) == null) {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((Socket)object).getInputStream(), this.getControlEncoding()));
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            String string2;
            if ((string2 = bufferedReader.readLine()) == null) {
                bufferedReader.close();
                ((Socket)object).close();
                if (!this.completePendingCommand()) return null;
                return arrayList.toArray(new String[arrayList.size()]);
            }
            arrayList.add(string2);
        } while (true);
    }

    public boolean login(String string2, String string3) throws IOException {
        this.user(string2);
        if (FTPReply.isPositiveCompletion(this._replyCode)) {
            return true;
        }
        if (FTPReply.isPositiveIntermediate(this._replyCode)) return FTPReply.isPositiveCompletion(this.pass(string3));
        return false;
    }

    public boolean login(String string2, String string3, String string4) throws IOException {
        this.user(string2);
        if (FTPReply.isPositiveCompletion(this._replyCode)) {
            return true;
        }
        if (!FTPReply.isPositiveIntermediate(this._replyCode)) {
            return false;
        }
        this.pass(string3);
        if (FTPReply.isPositiveCompletion(this._replyCode)) {
            return true;
        }
        if (FTPReply.isPositiveIntermediate(this._replyCode)) return FTPReply.isPositiveCompletion(this.acct(string4));
        return false;
    }

    public boolean logout() throws IOException {
        return FTPReply.isPositiveCompletion(this.quit());
    }

    public boolean makeDirectory(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.mkd(string2));
    }

    public FTPFile mdtmFile(String string2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.mdtm(string2))) return null;
        String string3 = this.getReplyStrings()[0].substring(4);
        FTPFile fTPFile = new FTPFile();
        fTPFile.setName(string2);
        fTPFile.setRawListing(string3);
        fTPFile.setTimestamp(MLSxEntryParser.parseGMTdateTime(string3));
        return fTPFile;
    }

    public FTPFile[] mlistDir() throws IOException {
        return this.mlistDir(null);
    }

    public FTPFile[] mlistDir(String string2) throws IOException {
        return this.initiateMListParsing(string2).getFiles();
    }

    public FTPFile[] mlistDir(String string2, FTPFileFilter fTPFileFilter) throws IOException {
        return this.initiateMListParsing(string2).getFiles(fTPFileFilter);
    }

    public FTPFile mlistFile(String string2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.sendCommand(FTPCmd.MLST, string2))) return null;
        return MLSxEntryParser.parseEntry(this.getReplyStrings()[1].substring(1));
    }

    public String printWorkingDirectory() throws IOException {
        if (this.pwd() == 257) return FTPClient.__parsePathname((String)this._replyLines.get(this._replyLines.size() - 1));
        return null;
    }

    public boolean reinitialize() throws IOException {
        this.rein();
        if (!FTPReply.isPositiveCompletion(this._replyCode)) {
            if (!FTPReply.isPositivePreliminary(this._replyCode)) return false;
            if (!FTPReply.isPositiveCompletion(this.getReply())) return false;
        }
        this.__initDefaults();
        return true;
    }

    public boolean remoteAppend(String string2) throws IOException {
        int n = this.__dataConnectionMode;
        if (n == 1) return FTPReply.isPositivePreliminary(this.appe(string2));
        if (n != 3) return false;
        return FTPReply.isPositivePreliminary(this.appe(string2));
    }

    public boolean remoteRetrieve(String string2) throws IOException {
        int n = this.__dataConnectionMode;
        if (n == 1) return FTPReply.isPositivePreliminary(this.retr(string2));
        if (n != 3) return false;
        return FTPReply.isPositivePreliminary(this.retr(string2));
    }

    public boolean remoteStore(String string2) throws IOException {
        int n = this.__dataConnectionMode;
        if (n == 1) return FTPReply.isPositivePreliminary(this.stor(string2));
        if (n != 3) return false;
        return FTPReply.isPositivePreliminary(this.stor(string2));
    }

    public boolean remoteStoreUnique() throws IOException {
        int n = this.__dataConnectionMode;
        if (n == 1) return FTPReply.isPositivePreliminary(this.stou());
        if (n != 3) return false;
        return FTPReply.isPositivePreliminary(this.stou());
    }

    public boolean remoteStoreUnique(String string2) throws IOException {
        int n = this.__dataConnectionMode;
        if (n == 1) return FTPReply.isPositivePreliminary(this.stou(string2));
        if (n != 3) return false;
        return FTPReply.isPositivePreliminary(this.stou(string2));
    }

    public boolean removeDirectory(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.rmd(string2));
    }

    public boolean rename(String string2, String string3) throws IOException {
        if (FTPReply.isPositiveIntermediate(this.rnfr(string2))) return FTPReply.isPositiveCompletion(this.rnto(string3));
        return false;
    }

    protected boolean restart(long l) throws IOException {
        this.__restartOffset = 0L;
        return FTPReply.isPositiveIntermediate(this.rest(Long.toString(l)));
    }

    public boolean retrieveFile(String string2, OutputStream outputStream2) throws IOException {
        return this._retrieveFile(FTPCmd.RETR.getCommand(), string2, outputStream2);
    }

    public InputStream retrieveFileStream(String string2) throws IOException {
        return this._retrieveFileStream(FTPCmd.RETR.getCommand(), string2);
    }

    public boolean sendNoOp() throws IOException {
        return FTPReply.isPositiveCompletion(this.noop());
    }

    public boolean sendSiteCommand(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.site(string2));
    }

    public void setActiveExternalIPAddress(String string2) throws UnknownHostException {
        this.__activeExternalHost = InetAddress.getByName(string2);
    }

    public void setActivePortRange(int n, int n2) {
        this.__activeMinPort = n;
        this.__activeMaxPort = n2;
    }

    public void setAutodetectUTF8(boolean bl) {
        this.__autodetectEncoding = bl;
    }

    public void setBufferSize(int n) {
        this.__bufferSize = n;
    }

    public void setControlKeepAliveReplyTimeout(int n) {
        this.__controlKeepAliveReplyTimeout = n;
    }

    public void setControlKeepAliveTimeout(long l) {
        this.__controlKeepAliveTimeout = l * 1000L;
    }

    public void setCopyStreamListener(CopyStreamListener copyStreamListener) {
        this.__copyStreamListener = copyStreamListener;
    }

    public void setDataTimeout(int n) {
        this.__dataTimeout = n;
    }

    public boolean setFileStructure(int n) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.stru(n))) return false;
        this.__fileStructure = n;
        return true;
    }

    public boolean setFileTransferMode(int n) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.mode(n))) return false;
        this.__fileTransferMode = n;
        return true;
    }

    public boolean setFileType(int n) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.type(n))) return false;
        this.__fileType = n;
        this.__fileFormat = 4;
        return true;
    }

    public boolean setFileType(int n, int n2) throws IOException {
        if (!FTPReply.isPositiveCompletion(this.type(n, n2))) return false;
        this.__fileType = n;
        this.__fileFormat = n2;
        return true;
    }

    public void setListHiddenFiles(boolean bl) {
        this.__listHiddenFiles = bl;
    }

    public boolean setModificationTime(String string2, String string3) throws IOException {
        return FTPReply.isPositiveCompletion(this.mfmt(string2, string3));
    }

    public void setParserFactory(FTPFileEntryParserFactory fTPFileEntryParserFactory) {
        this.__parserFactory = fTPFileEntryParserFactory;
    }

    public void setPassiveLocalIPAddress(String string2) throws UnknownHostException {
        this.__passiveLocalHost = InetAddress.getByName(string2);
    }

    public void setPassiveLocalIPAddress(InetAddress inetAddress) {
        this.__passiveLocalHost = inetAddress;
    }

    public void setPassiveNatWorkaround(boolean bl) {
        this.__passiveNatWorkaround = bl;
    }

    public void setReceieveDataSocketBufferSize(int n) {
        this.__receiveDataSocketBufferSize = n;
    }

    public void setRemoteVerificationEnabled(boolean bl) {
        this.__remoteVerificationEnabled = bl;
    }

    public void setReportActiveExternalIPAddress(String string2) throws UnknownHostException {
        this.__reportActiveExternalHost = InetAddress.getByName(string2);
    }

    public void setRestartOffset(long l) {
        if (l < 0L) return;
        this.__restartOffset = l;
    }

    public void setSendDataSocketBufferSize(int n) {
        this.__sendDataSocketBufferSize = n;
    }

    public void setUseEPSVwithIPv4(boolean bl) {
        this.__useEPSVwithIPv4 = bl;
    }

    public boolean storeFile(String string2, InputStream inputStream2) throws IOException {
        return this.__storeFile(FTPCmd.STOR, string2, inputStream2);
    }

    public OutputStream storeFileStream(String string2) throws IOException {
        return this.__storeFileStream(FTPCmd.STOR, string2);
    }

    public boolean storeUniqueFile(InputStream inputStream2) throws IOException {
        return this.__storeFile(FTPCmd.STOU, null, inputStream2);
    }

    public boolean storeUniqueFile(String string2, InputStream inputStream2) throws IOException {
        return this.__storeFile(FTPCmd.STOU, string2, inputStream2);
    }

    public OutputStream storeUniqueFileStream() throws IOException {
        return this.__storeFileStream(FTPCmd.STOU, null);
    }

    public OutputStream storeUniqueFileStream(String string2) throws IOException {
        return this.__storeFileStream(FTPCmd.STOU, string2);
    }

    public boolean structureMount(String string2) throws IOException {
        return FTPReply.isPositiveCompletion(this.smnt(string2));
    }

    private static class CSL
    implements CopyStreamListener {
        private final int currentSoTimeout;
        private final long idle;
        private int notAcked;
        private final FTPClient parent;
        private long time = System.currentTimeMillis();

        CSL(FTPClient fTPClient, long l, int n) throws SocketException {
            this.idle = l;
            this.parent = fTPClient;
            this.currentSoTimeout = fTPClient.getSoTimeout();
            fTPClient.setSoTimeout(n);
        }

        @Override
        public void bytesTransferred(long l, int n, long l2) {
            l = System.currentTimeMillis();
            if (l - this.time <= this.idle) return;
            try {
                this.parent.__noop();
            }
            catch (SocketTimeoutException socketTimeoutException) {
                ++this.notAcked;
            }
            catch (IOException iOException) {}
            this.time = l;
        }

        @Override
        public void bytesTransferred(CopyStreamEvent copyStreamEvent) {
            this.bytesTransferred(copyStreamEvent.getTotalBytesTransferred(), copyStreamEvent.getBytesTransferred(), copyStreamEvent.getStreamSize());
        }

        void cleanUp() throws IOException {
            try {
                do {
                    int n = this.notAcked;
                    this.notAcked = n - 1;
                    if (n <= 0) return;
                    this.parent.__getReplyNoReport();
                } while (true);
            }
            finally {
                this.parent.setSoTimeout(this.currentSoTimeout);
            }
        }
    }

    private static class PropertiesSingleton {
        static final Properties PROPERTIES;

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        static {
            var0 = FTPClient.class.getResourceAsStream("/systemType.properties");
            if (var0 == null) ** GOTO lbl13
            var1_3 = new Properties();
            try {
                var1_3.load(var0);
                ** GOTO lbl16
            }
            catch (Throwable var1_4) {
                block7 : {
                    try {
                        var0.close();
                    }
                    catch (IOException var0_2) {
                        throw var1_4;
                    }
                    throw var1_4;
lbl13: // 1 sources:
                    var1_3 = null;
                    break block7;
                    catch (IOException var2_5) {}
lbl16: // 2 sources:
                    try {
                        var0.close();
                    }
                    catch (IOException var0_1) {}
                }
                PropertiesSingleton.PROPERTIES = var1_3;
                return;
            }
        }

        private PropertiesSingleton() {
        }
    }

}

