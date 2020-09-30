/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.DefaultFolder;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.Namespaces;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Quota;
import javax.mail.QuotaAwareStore;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class IMAPStore
extends Store
implements QuotaAwareStore,
ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int RESPONSE = 1000;
    private int appendBufferSize = -1;
    private String authorizationID;
    private int blksize = 16384;
    private volatile boolean connected = false;
    private int defaultPort = 143;
    private boolean disableAuthLogin = false;
    private boolean disableAuthPlain = false;
    private boolean enableImapEvents = false;
    private boolean enableSASL = false;
    private boolean enableStartTLS = false;
    private boolean forcePasswordRefresh = false;
    private String host;
    private boolean isSSL = false;
    private int minIdleTime = 10;
    private String name = "imap";
    private Namespaces namespaces;
    private PrintStream out;
    private String password;
    private ConnectionPool pool = new ConnectionPool();
    private int port = -1;
    private String proxyAuthUser;
    private String[] saslMechanisms;
    private String saslRealm;
    private int statusCacheTimeout = 1000;
    private String user;

    public IMAPStore(Session session, URLName uRLName) {
        this(session, uRLName, "imap", 143, false);
    }

    protected IMAPStore(Session object, URLName object2, String string2, int n, boolean bl) {
        super((Session)object, (URLName)object2);
        Object object3;
        if (object2 != null) {
            string2 = ((URLName)object2).getProtocol();
        }
        this.name = string2;
        this.defaultPort = n;
        this.isSSL = bl;
        ConnectionPool.access$0(this.pool, System.currentTimeMillis());
        this.debug = ((Session)object).getDebug();
        this.out = object2 = ((Session)object).getDebugOut();
        if (object2 == null) {
            this.out = System.out;
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".connectionpool.debug");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            ConnectionPool.access$1(this.pool, true);
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".partialfetch");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("false")) {
            this.blksize = -1;
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.partialfetch: false");
            }
        } else {
            object2 = new StringBuilder("mail.");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(".fetchsize");
            object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
            if (object2 != null) {
                this.blksize = Integer.parseInt((String)object2);
            }
            if (this.debug) {
                object3 = this.out;
                object2 = new StringBuilder("DEBUG: mail.imap.fetchsize: ");
                ((StringBuilder)object2).append(this.blksize);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".statuscachetimeout");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.statusCacheTimeout = Integer.parseInt((String)object2);
            if (this.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.statuscachetimeout: ");
                ((StringBuilder)object3).append(this.statusCacheTimeout);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".appendbuffersize");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.appendBufferSize = Integer.parseInt((String)object2);
            if (this.debug) {
                object3 = this.out;
                object2 = new StringBuilder("DEBUG: mail.imap.appendbuffersize: ");
                ((StringBuilder)object2).append(this.appendBufferSize);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".minidletime");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.minIdleTime = Integer.parseInt((String)object2);
            if (this.debug) {
                object3 = this.out;
                object2 = new StringBuilder("DEBUG: mail.imap.minidletime: ");
                ((StringBuilder)object2).append(this.minIdleTime);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".connectionpoolsize");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            try {
                n = Integer.parseInt((String)object2);
                if (n > 0) {
                    ConnectionPool.access$2(this.pool, n);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (this.pool.debug) {
                object3 = this.out;
                object2 = new StringBuilder("DEBUG: mail.imap.connectionpoolsize: ");
                ((StringBuilder)object2).append(this.pool.poolSize);
                ((PrintStream)object3).println(((StringBuilder)object2).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".connectionpooltimeout");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            try {
                n = Integer.parseInt((String)object2);
                if (n > 0) {
                    ConnectionPool.access$5(this.pool, n);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (this.pool.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.connectionpooltimeout: ");
                ((StringBuilder)object3).append(this.pool.clientTimeoutInterval);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".servertimeout");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            try {
                n = Integer.parseInt((String)object2);
                if (n > 0) {
                    ConnectionPool.access$7(this.pool, n);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (this.pool.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.servertimeout: ");
                ((StringBuilder)object3).append(this.pool.serverTimeoutInterval);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".separatestoreconnection");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.pool.debug) {
                this.out.println("DEBUG: dedicate a store connection");
            }
            ConnectionPool.access$9(this.pool, true);
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".proxyauth.user");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.proxyAuthUser = object2;
            if (this.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.proxyauth.user: ");
                ((StringBuilder)object3).append(this.proxyAuthUser);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".auth.login.disable");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: disable AUTH=LOGIN");
            }
            this.disableAuthLogin = true;
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".auth.plain.disable");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: disable AUTH=PLAIN");
            }
            this.disableAuthPlain = true;
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".starttls.enable");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable STARTTLS");
            }
            this.enableStartTLS = true;
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".sasl.enable");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable SASL");
            }
            this.enableSASL = true;
        }
        if (this.enableSASL) {
            object2 = new StringBuilder("mail.");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(".sasl.mechanisms");
            object3 = ((Session)object).getProperty(((StringBuilder)object2).toString());
            if (object3 != null && ((String)object3).length() > 0) {
                CharSequence charSequence;
                if (this.debug) {
                    object2 = this.out;
                    charSequence = new StringBuilder("DEBUG: SASL mechanisms allowed: ");
                    ((StringBuilder)charSequence).append((String)object3);
                    ((PrintStream)object2).println(((StringBuilder)charSequence).toString());
                }
                object2 = new Vector(5);
                object3 = new StringTokenizer((String)object3, " ,");
                do {
                    if (!((StringTokenizer)object3).hasMoreTokens()) {
                        object3 = new String[((Vector)object2).size()];
                        this.saslMechanisms = object3;
                        ((Vector)object2).copyInto((Object[])object3);
                        break;
                    }
                    charSequence = ((StringTokenizer)object3).nextToken();
                    if (((String)charSequence).length() <= 0) continue;
                    ((Vector)object2).addElement(charSequence);
                } while (true);
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".sasl.authorizationid");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.authorizationID = object2;
            if (this.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.sasl.authorizationid: ");
                ((StringBuilder)object3).append(this.authorizationID);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".sasl.realm");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null) {
            this.saslRealm = object2;
            if (this.debug) {
                object2 = this.out;
                object3 = new StringBuilder("DEBUG: mail.imap.sasl.realm: ");
                ((StringBuilder)object3).append(this.saslRealm);
                ((PrintStream)object2).println(((StringBuilder)object3).toString());
            }
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".forcepasswordrefresh");
        object2 = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object2 != null && ((String)object2).equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable forcePasswordRefresh");
            }
            this.forcePasswordRefresh = true;
        }
        object2 = new StringBuilder("mail.");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".enableimapevents");
        object = ((Session)object).getProperty(((StringBuilder)object2).toString());
        if (object == null) return;
        if (!((String)object).equalsIgnoreCase("true")) return;
        if (this.debug) {
            this.out.println("DEBUG: enable IMAP events");
        }
        this.enableImapEvents = true;
    }

    private void checkConnected() {
        if (this.connected) {
            return;
        }
        super.setConnected(false);
        throw new IllegalStateException("Not connected");
    }

    private void cleanup() {
        this.cleanup(false);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private void cleanup(boolean var1_1) {
        if (this.debug) {
            var2_2 = this.out;
            var3_3 = new StringBuilder("DEBUG: IMAPStore cleanup, force ");
            var3_3.append(var1_1);
            var2_2.println(var3_3.toString());
        }
        var2_2 = null;
        block8 : do {
            var3_3 = this.pool;
            // MONITORENTER : var3_3
            if (ConnectionPool.access$13(this.pool) != null) {
                var2_2 = ConnectionPool.access$13(this.pool);
                ConnectionPool.access$14(this.pool, null);
                var4_5 = 0;
            } else {
                var4_5 = 1;
            }
            // MONITOREXIT : var3_3
            if (var4_5 != 0) {
                var2_2 = this.pool;
                // MONITORENTER : var2_2
                this.emptyConnectionPool(var1_1);
                // MONITOREXIT : var2_2
                this.connected = false;
                this.notifyConnectionListeners(3);
                if (this.debug == false) return;
                this.out.println("DEBUG: IMAPStore cleanup done");
                return;
            }
            var5_6 = var2_2.size();
            var4_5 = 0;
            block9 : do {
                if (var4_5 >= var5_6) continue block8;
                var3_3 = (IMAPFolder)var2_2.elementAt(var4_5);
                if (!var1_1) ** GOTO lbl39
                try {
                    block17 : {
                        if (this.debug) {
                            this.out.println("DEBUG: force folder to close");
                        }
                        var3_3.forceClose();
                        break block17;
lbl39: // 1 sources:
                        if (this.debug) {
                            this.out.println("DEBUG: close folder");
                        }
                        var3_3.close(false);
                    }
lbl43: // 2 sources:
                    do {
                        ++var4_5;
                        continue block9;
                        break;
                    } while (true);
                }
                catch (IllegalStateException | MessagingException var3_4) {
                    ** continue;
                }
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private void emptyConnectionPool(boolean var1_1) {
        var2_2 = this.pool;
        // MONITORENTER : var2_2
        var3_3 = ConnectionPool.access$10(this.pool).size() - 1;
        block5 : do {
            if (var3_3 < 0) {
                ConnectionPool.access$10(this.pool).removeAllElements();
                // MONITOREXIT : var2_2
                if (ConnectionPool.access$3(this.pool) == false) return;
                this.out.println("DEBUG: removed all authenticated connections");
                return;
            }
            try {
                var4_4 = (IMAPProtocol)ConnectionPool.access$10(this.pool).elementAt(var3_3);
                var4_4.removeResponseHandler(this);
                if (var1_1) {
                    var4_4.disconnect();
                } else {
                    var4_4.logout();
                }
lbl18: // 3 sources:
                do {
                    --var3_3;
                    continue block5;
                    break;
                } while (true);
            }
            catch (ProtocolException var4_5) {
                ** continue;
            }
        } while (true);
    }

    /*
     * Exception decompiling
     */
    private Namespaces getNamespaces() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
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

    private void login(IMAPProtocol iMAPProtocol, String string2, String string3) throws ProtocolException {
        if (this.enableStartTLS && iMAPProtocol.hasCapability("STARTTLS")) {
            iMAPProtocol.startTLS();
            iMAPProtocol.capability();
        }
        if (iMAPProtocol.isAuthenticated()) {
            return;
        }
        iMAPProtocol.getCapabilities().put("__PRELOGIN__", "");
        String string4 = this.authorizationID;
        if (string4 == null && (string4 = this.proxyAuthUser) == null) {
            string4 = string2;
        }
        if (this.enableSASL) {
            iMAPProtocol.sasllogin(this.saslMechanisms, this.saslRealm, string4, string2, string3);
        }
        if (!iMAPProtocol.isAuthenticated()) {
            if (iMAPProtocol.hasCapability("AUTH=PLAIN") && !this.disableAuthPlain) {
                iMAPProtocol.authplain(string4, string2, string3);
            } else if ((iMAPProtocol.hasCapability("AUTH-LOGIN") || iMAPProtocol.hasCapability("AUTH=LOGIN")) && !this.disableAuthLogin) {
                iMAPProtocol.authlogin(string2, string3);
            } else {
                if (iMAPProtocol.hasCapability("LOGINDISABLED")) throw new ProtocolException("No login methods supported!");
                iMAPProtocol.login(string2, string3);
            }
        }
        if ((string2 = this.proxyAuthUser) != null) {
            iMAPProtocol.proxyauth(string2);
        }
        if (!iMAPProtocol.hasCapability("__PRELOGIN__")) return;
        try {
            iMAPProtocol.capability();
            return;
        }
        catch (ConnectionException connectionException) {
            throw connectionException;
        }
        catch (ProtocolException protocolException) {
            return;
        }
    }

    private Folder[] namespaceToFolders(Namespaces.Namespace[] arrnamespace, String string2) {
        int n = arrnamespace.length;
        Folder[] arrfolder = new Folder[n];
        int n2 = 0;
        while (n2 < n) {
            CharSequence charSequence;
            String string3 = arrnamespace[n2].prefix;
            if (string2 == null) {
                int n3 = string3.length();
                charSequence = string3;
                if (n3 > 0) {
                    charSequence = string3;
                    if (string3.charAt(--n3) == arrnamespace[n2].delimiter) {
                        charSequence = string3.substring(0, n3);
                    }
                }
            } else {
                charSequence = new StringBuilder(String.valueOf(string3));
                ((StringBuilder)charSequence).append(string2);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            char c = arrnamespace[n2].delimiter;
            boolean bl = string2 == null;
            arrfolder[n2] = new IMAPFolder((String)charSequence, c, this, bl);
            ++n2;
        }
        return arrfolder;
    }

    private void timeoutConnections() {
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            Appendable appendable;
            Object object;
            if (System.currentTimeMillis() - this.pool.lastTimePruned <= this.pool.pruningInterval) return;
            if (this.pool.authenticatedConnections.size() <= 1) return;
            if (this.pool.debug) {
                object = this.out;
                appendable = new StringBuilder("DEBUG: checking for connections to prune: ");
                ((StringBuilder)appendable).append(System.currentTimeMillis() - this.pool.lastTimePruned);
                ((PrintStream)object).println(((StringBuilder)appendable).toString());
                object = this.out;
                appendable = new StringBuilder("DEBUG: clientTimeoutInterval: ");
                ((StringBuilder)appendable).append(this.pool.clientTimeoutInterval);
                ((PrintStream)object).println(((StringBuilder)appendable).toString());
            }
            int n = this.pool.authenticatedConnections.size() - 1;
            do {
                block10 : {
                    block11 : {
                        if (n <= 0) break block11;
                        object = (IMAPProtocol)this.pool.authenticatedConnections.elementAt(n);
                        if (this.pool.debug) {
                            appendable = this.out;
                            StringBuilder stringBuilder = new StringBuilder("DEBUG: protocol last used: ");
                            stringBuilder.append(System.currentTimeMillis() - ((Protocol)object).getTimestamp());
                            ((PrintStream)appendable).println(stringBuilder.toString());
                        }
                        if (System.currentTimeMillis() - ((Protocol)object).getTimestamp() <= this.pool.clientTimeoutInterval) break block10;
                        if (this.pool.debug) {
                            this.out.println("DEBUG: authenticated connection timed out");
                            this.out.println("DEBUG: logging out the connection");
                        }
                        ((Protocol)object).removeResponseHandler(this);
                        this.pool.authenticatedConnections.removeElementAt(n);
                        try {
                            ((IMAPProtocol)object).logout();
                            break block10;
                        }
                        catch (ProtocolException protocolException) {}
                    }
                    ConnectionPool.access$0(this.pool, System.currentTimeMillis());
                    return;
                }
                --n;
            } while (true);
        }
    }

    private void waitIfIdle() throws ProtocolException {
        while (this.pool.idleState != 0) {
            if (this.pool.idleState == 1) {
                this.pool.idleProtocol.idleAbort();
                ConnectionPool.access$20(this.pool, 2);
            }
            try {
                this.pool.wait();
            }
            catch (InterruptedException interruptedException) {
            }
        }
        return;
    }

    boolean allowReadOnlySelect() {
        Session session = this.session;
        CharSequence charSequence = new StringBuilder("mail.");
        ((StringBuilder)charSequence).append(this.name);
        ((StringBuilder)charSequence).append(".allowreadonlyselect");
        charSequence = session.getProperty(((StringBuilder)charSequence).toString());
        if (charSequence == null) return false;
        if (!((String)charSequence).equalsIgnoreCase("true")) return false;
        return true;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void close() throws MessagingException {
        Object object;
        Throwable throwable42;
        block25 : {
            Object object3;
            Object object2;
            MessagingException messagingException;
            block24 : {
                // MONITORENTER : this
                boolean bl = super.isConnected();
                if (!bl) {
                    // MONITOREXIT : this
                    return;
                }
                messagingException = null;
                object = object3 = null;
                object2 = messagingException;
                ConnectionPool connectionPool = this.pool;
                object = object3;
                object2 = messagingException;
                // MONITORENTER : connectionPool
                bl = this.pool.authenticatedConnections.isEmpty();
                // MONITOREXIT : connectionPool
                if (!bl) break block24;
                object = object3;
                object2 = messagingException;
                if (this.pool.debug) {
                    object = object3;
                    object2 = messagingException;
                    this.out.println("DEBUG: close() - no connections ");
                }
                object = object3;
                object2 = messagingException;
                this.cleanup();
                this.releaseStoreProtocol(null);
                // MONITOREXIT : this
                return;
            }
            object = object3;
            object2 = messagingException;
            object = object3 = this.getStoreProtocol();
            object2 = object3;
            ConnectionPool connectionPool = this.pool;
            object = object3;
            object2 = object3;
            // MONITORENTER : connectionPool
            this.pool.authenticatedConnections.removeElement(object3);
            // MONITOREXIT : connectionPool
            object = object3;
            object2 = object3;
            ((IMAPProtocol)object3).logout();
            this.releaseStoreProtocol((IMAPProtocol)object3);
            return;
            {
                catch (Throwable throwable2) {}
                object = object3;
                object2 = object3;
                throw throwable2;
                {
                    catch (Throwable throwable3) {}
                    object = object3;
                    object2 = messagingException;
                    try {
                        throw throwable3;
                    }
                    catch (Throwable throwable42) {
                        break block25;
                    }
                    catch (ProtocolException protocolException) {
                        object = object2;
                        this.cleanup();
                        object = object2;
                        object = object2;
                        messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                        object = object2;
                        throw messagingException;
                    }
                }
            }
        }
        this.releaseStoreProtocol((IMAPProtocol)object);
        throw throwable42;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    int getAppendBufferSize() {
        return this.appendBufferSize;
    }

    boolean getConnectionPoolDebug() {
        return this.pool.debug;
    }

    @Override
    public Folder getDefaultFolder() throws MessagingException {
        synchronized (this) {
            this.checkConnected();
            return new DefaultFolder(this);
        }
    }

    int getFetchBlockSize() {
        return this.blksize;
    }

    @Override
    public Folder getFolder(String object) throws MessagingException {
        synchronized (this) {
            this.checkConnected();
            return new IMAPFolder((String)object, '\uffff', this);
        }
    }

    @Override
    public Folder getFolder(URLName object) throws MessagingException {
        synchronized (this) {
            this.checkConnected();
            return new IMAPFolder(((URLName)object).getFile(), '\uffff', this);
        }
    }

    int getMinIdleTime() {
        return this.minIdleTime;
    }

    @Override
    public Folder[] getPersonalNamespaces() throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null) return super.getPersonalNamespaces();
        if (namespaces.personal != null) return this.namespaceToFolders(namespaces.personal, null);
        return super.getPersonalNamespaces();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    IMAPProtocol getProtocol(IMAPFolder var1_1) throws MessagingException {
        block15 : do {
            var2_2 = null;
            do {
                if (var2_2 != null) {
                    return var2_2;
                }
                var3_6 = this.pool;
                // MONITORENTER : var3_6
                if (ConnectionPool.access$10(this.pool).isEmpty() || ConnectionPool.access$10(this.pool).size() == 1 && (ConnectionPool.access$11(this.pool) || ConnectionPool.access$12(this.pool))) ** GOTO lbl32
                if (this.debug) {
                    var2_2 = this.out;
                    var4_8 = new Vector<E>("DEBUG: connection available -- size: ");
                    var4_8.append(ConnectionPool.access$10(this.pool).size());
                    var2_2.println(var4_8.toString());
                }
                var2_2 = (IMAPProtocol)ConnectionPool.access$10(this.pool).lastElement();
                ConnectionPool.access$10(this.pool).removeElement(var2_2);
                var5_11 = System.currentTimeMillis();
                var7_12 = var2_2.getTimestamp();
                var9_13 = ConnectionPool.access$8(this.pool);
                if (var5_11 - var7_12 <= var9_13) ** GOTO lbl30
                try {
                    var2_2.noop();
                    ** GOTO lbl30
                }
                catch (ProtocolException var4_7) {
                    try {
                        block30 : {
                            block31 : {
                                block27 : {
                                    block28 : {
                                        var2_2.removeResponseHandler((ResponseHandler)this);
                                        var2_2.disconnect();
                                        break block30;
lbl30: // 2 sources:
                                        var2_2.removeResponseHandler((ResponseHandler)this);
                                        break block31;
lbl32: // 1 sources:
                                        if (this.debug) {
                                            this.out.println("DEBUG: no connections in the pool, creating a new one");
                                        }
                                        var11_14 = this.forcePasswordRefresh;
                                        if (var11_14) {
                                            try {
                                                var4_8 = InetAddress.getByName(this.host);
                                            }
                                            catch (UnknownHostException var4_9) {
                                                var4_8 = null;
                                            }
                                            var4_8 = this.session.requestPasswordAuthentication((InetAddress)var4_8, this.port, this.name, null, this.user);
                                            if (var4_8 != null) {
                                                this.user = var4_8.getUserName();
                                                this.password = var4_8.getPassword();
                                            }
                                        }
                                        var4_8 = new Vector<E>(this.name, this.host, this.port, this.session.getDebug(), this.session.getDebugOut(), this.session.getProperties(), this.isSSL);
                                        try {
                                            this.login((IMAPProtocol)var4_8, this.user, this.password);
                                            var2_2 = var4_8;
                                            break block27;
                                        }
                                        catch (Exception var2_3) {
                                            var2_2 = var4_8;
                                            break block28;
                                        }
                                        catch (Exception var4_10) {
                                            // empty catch block
                                        }
                                    }
                                    if (var2_2 != null) {
                                        try {
                                            var2_2.disconnect();
                                        }
                                        catch (Exception var2_5) {}
                                    }
                                    var2_2 = null;
                                }
                                if (var2_2 == null) {
                                    var1_1 = new MessagingException("connection failure");
                                    throw var1_1;
                                }
                            }
                            this.timeoutConnections();
                            if (var1_1 != null) {
                                if (ConnectionPool.access$13(this.pool) == null) {
                                    var12_15 = this.pool;
                                    var4_8 = new Vector<E>();
                                    ConnectionPool.access$14(var12_15, var4_8);
                                }
                                ConnectionPool.access$13(this.pool).addElement(var1_1);
                            }
                            // MONITOREXIT : var3_6
                            continue;
                        }
lbl79: // 2 sources:
                        continue block15;
                    }
                    catch (Throwable var2_4) {
                        ** continue;
                    }
                }
                break;
            } while (true);
        } while (true);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Quota[] getQuota(String var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
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

    Session getSession() {
        return this.session;
    }

    @Override
    public Folder[] getSharedNamespaces() throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null) return super.getSharedNamespaces();
        if (namespaces.shared != null) return this.namespaceToFolders(namespaces.shared, null);
        return super.getSharedNamespaces();
    }

    int getStatusCacheTimeout() {
        return this.statusCacheTimeout;
    }

    /*
     * Exception decompiling
     */
    IMAPProtocol getStoreProtocol() throws ProtocolException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 11[UNCONDITIONALDOLOOP]
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

    @Override
    public Folder[] getUserNamespaces(String string2) throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null) return super.getUserNamespaces(string2);
        if (namespaces.otherUsers != null) return this.namespaceToFolders(namespaces.otherUsers, string2);
        return super.getUserNamespaces(string2);
    }

    @Override
    public void handleResponse(Response response) {
        if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
            this.handleResponseCode(response);
        }
        if (!response.isBYE()) return;
        if (this.debug) {
            this.out.println("DEBUG: IMAPStore connection dead");
        }
        if (!this.connected) return;
        this.cleanup(response.isSynthetic());
    }

    void handleResponseCode(Response response) {
        String string2 = response.getRest();
        boolean bl = string2.startsWith("[");
        boolean bl2 = false;
        boolean bl3 = false;
        String string3 = string2;
        if (bl) {
            int n = string2.indexOf(93);
            bl2 = bl3;
            if (n > 0) {
                bl2 = bl3;
                if (string2.substring(0, n + 1).equalsIgnoreCase("[ALERT]")) {
                    bl2 = true;
                }
            }
            string3 = string2.substring(n + 1).trim();
        }
        if (bl2) {
            this.notifyStoreListeners(1, string3);
            return;
        }
        if (!response.isUnTagged()) return;
        if (string3.length() <= 0) return;
        this.notifyStoreListeners(2, string3);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public boolean hasCapability(String var1_1) throws MessagingException {
        // MONITORENTER : this
        var2_3 = null;
        var3_4 = null;
        var3_4 = var4_5 = this.getStoreProtocol();
        var2_3 = var4_5;
        var5_7 = var4_5.hasCapability((String)var1_1);
        this.releaseStoreProtocol(var4_5);
        return var5_7;
        {
            catch (Throwable var1_2) {
            }
            catch (ProtocolException var4_6) {}
            if (var2_3 != null) ** GOTO lbl17
            var3_4 = var2_3;
            {
                this.cleanup();
lbl17: // 2 sources:
                var3_4 = var2_3;
                var3_4 = var2_3;
                var1_1 = new MessagingException(var4_6.getMessage(), var4_6);
                var3_4 = var2_3;
                throw var1_1;
            }
        }
        this.releaseStoreProtocol(var3_4);
        throw var1_2;
    }

    boolean hasSeparateStoreConnection() {
        return this.pool.separateStoreConnection;
    }

    /*
     * Exception decompiling
     */
    public void idle() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public boolean isConnected() {
        block10 : {
            // MONITORENTER : this
            if (!this.connected) {
                super.setConnected(false);
                // MONITOREXIT : this
                return false;
            }
            var1_1 = null;
            var2_3 = null;
            var2_3 = var3_5 = this.getStoreProtocol();
            var1_1 = var3_5;
            var3_5.noop();
lbl12: // 3 sources:
            do {
                this.releaseStoreProtocol(var3_5);
                break block10;
                break;
            } while (true);
            {
                catch (Throwable var1_2) {
                }
                catch (ProtocolException var2_4) {}
                var3_5 = var1_1;
                if (var1_1 != null) ** GOTO lbl12
                var2_3 = var1_1;
                {
                    this.cleanup();
                    var3_5 = var1_1;
                    ** continue;
                }
            }
            this.releaseStoreProtocol(var2_3);
            throw var1_2;
        }
        var4_6 = super.isConnected();
        // MONITOREXIT : this
        return var4_6;
    }

    boolean isConnectionPoolFull() {
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            if (this.pool.debug) {
                PrintStream printStream = this.out;
                StringBuilder stringBuilder = new StringBuilder("DEBUG: current size: ");
                stringBuilder.append(this.pool.authenticatedConnections.size());
                stringBuilder.append("   pool size: ");
                stringBuilder.append(this.pool.poolSize);
                printStream.println(stringBuilder.toString());
            }
            if (this.pool.authenticatedConnections.size() < this.pool.poolSize) return false;
            return true;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    protected boolean protocolConnect(String object, int n, String object2, String string2) throws MessagingException {
        void var1_4;
        block29 : {
            // MONITORENTER : this
            Appendable appendable = null;
            if (object != null && string2 != null && object2 != null) {
                Appendable appendable2;
                Object object3;
                if (n != -1) {
                    this.port = n;
                } else {
                    object3 = this.session;
                    appendable2 = new StringBuilder("mail.");
                    ((StringBuilder)appendable2).append(this.name);
                    ((StringBuilder)appendable2).append(".port");
                    object3 = ((Session)object3).getProperty(((StringBuilder)appendable2).toString());
                    if (object3 != null) {
                        this.port = Integer.parseInt((String)object3);
                    }
                }
                if (this.port == -1) {
                    this.port = this.defaultPort;
                }
                object3 = this.pool;
                // MONITORENTER : object3
                boolean bl = this.pool.authenticatedConnections.isEmpty();
                if (bl) {
                    object3 = new IMAPProtocol(this.name, (String)object, this.port, this.session.getDebug(), this.session.getDebugOut(), this.session.getProperties(), this.isSSL);
                    try {
                        if (this.debug) {
                            appendable2 = this.out;
                            appendable = new StringBuilder("DEBUG: protocolConnect login, host=");
                            ((StringBuilder)appendable).append((String)object);
                            ((StringBuilder)appendable).append(", user=");
                            ((StringBuilder)appendable).append((String)object2);
                            ((StringBuilder)appendable).append(", password=<non-null>");
                            ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
                        }
                        this.login((IMAPProtocol)object3, (String)object2, string2);
                        ((Protocol)object3).addResponseHandler(this);
                        this.host = object;
                        this.user = object2;
                        this.password = string2;
                        object2 = this.pool;
                        // MONITORENTER : object2
                        this.pool.authenticatedConnections.addElement(object3);
                    }
                    catch (CommandFailedException commandFailedException) {
                        object2 = object3;
                        break block29;
                    }
                }
                this.connected = true;
                // MONITOREXIT : this
                return true;
            }
            if (this.debug) {
                appendable = this.out;
                StringBuilder stringBuilder = new StringBuilder("DEBUG: protocolConnect returning false, host=");
                stringBuilder.append((String)object);
                stringBuilder.append(", user=");
                stringBuilder.append((String)object2);
                stringBuilder.append(", password=");
                object = string2 != null ? "<non-null>" : "<null>";
                stringBuilder.append((String)object);
                ((PrintStream)appendable).println(stringBuilder.toString());
            }
            // MONITOREXIT : this
            return false;
            catch (IOException iOException) {
                object = new MessagingException(iOException.getMessage(), iOException);
                throw object;
            }
            catch (ProtocolException protocolException) {
                object = new MessagingException(protocolException.getMessage(), protocolException);
                throw object;
            }
            catch (CommandFailedException commandFailedException) {
                object2 = appendable;
            }
        }
        if (object2 != null) {
            ((IMAPProtocol)object2).disconnect();
        }
        object2 = new AuthenticationFailedException(var1_4.getResponse().getRest());
        throw object2;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void releaseProtocol(IMAPFolder iMAPFolder, IMAPProtocol object2) {
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            PrintStream printStream;
            if (printStream != null) {
                if (!this.isConnectionPoolFull()) {
                    ((Protocol)((Object)printStream)).addResponseHandler(this);
                    this.pool.authenticatedConnections.addElement(printStream);
                    if (this.debug) {
                        printStream = this.out;
                        StringBuilder stringBuilder = new StringBuilder("DEBUG: added an Authenticated connection -- size: ");
                        stringBuilder.append(this.pool.authenticatedConnections.size());
                        printStream.println(stringBuilder.toString());
                    }
                } else {
                    if (this.debug) {
                        this.out.println("DEBUG: pool is full, not adding an Authenticated connection");
                    }
                    try {
                        ((IMAPProtocol)((Object)printStream)).logout();
                    }
                    catch (ProtocolException object2) {}
                }
            }
            if (this.pool.folders != null) {
                this.pool.folders.removeElement(iMAPFolder);
            }
            this.timeoutConnections();
            return;
        }
    }

    void releaseStoreProtocol(IMAPProtocol object) {
        if (object == null) {
            return;
        }
        object = this.pool;
        synchronized (object) {
            ConnectionPool.access$15(this.pool, false);
            this.pool.notifyAll();
            if (this.pool.debug) {
                this.out.println("DEBUG: releaseStoreProtocol()");
            }
            this.timeoutConnections();
            return;
        }
    }

    public void setPassword(String string2) {
        synchronized (this) {
            this.password = string2;
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void setQuota(Quota var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
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

    public void setUsername(String string2) {
        synchronized (this) {
            this.user = string2;
            return;
        }
    }

    static class ConnectionPool {
        private static final int ABORTING = 2;
        private static final int IDLE = 1;
        private static final int RUNNING = 0;
        private Vector authenticatedConnections = new Vector();
        private long clientTimeoutInterval = 45000L;
        private boolean debug = false;
        private Vector folders;
        private IMAPProtocol idleProtocol;
        private int idleState = 0;
        private long lastTimePruned;
        private int poolSize = 1;
        private long pruningInterval = 60000L;
        private boolean separateStoreConnection = false;
        private long serverTimeoutInterval = 1800000L;
        private boolean storeConnectionInUse = false;

        ConnectionPool() {
        }

        static /* synthetic */ void access$0(ConnectionPool connectionPool, long l) {
            connectionPool.lastTimePruned = l;
        }

        static /* synthetic */ void access$1(ConnectionPool connectionPool, boolean bl) {
            connectionPool.debug = bl;
        }

        static /* synthetic */ boolean access$12(ConnectionPool connectionPool) {
            return connectionPool.storeConnectionInUse;
        }

        static /* synthetic */ void access$14(ConnectionPool connectionPool, Vector vector) {
            connectionPool.folders = vector;
        }

        static /* synthetic */ void access$15(ConnectionPool connectionPool, boolean bl) {
            connectionPool.storeConnectionInUse = bl;
        }

        static /* synthetic */ void access$18(ConnectionPool connectionPool, IMAPProtocol iMAPProtocol) {
            connectionPool.idleProtocol = iMAPProtocol;
        }

        static /* synthetic */ void access$2(ConnectionPool connectionPool, int n) {
            connectionPool.poolSize = n;
        }

        static /* synthetic */ void access$20(ConnectionPool connectionPool, int n) {
            connectionPool.idleState = n;
        }

        static /* synthetic */ void access$5(ConnectionPool connectionPool, long l) {
            connectionPool.clientTimeoutInterval = l;
        }

        static /* synthetic */ void access$7(ConnectionPool connectionPool, long l) {
            connectionPool.serverTimeoutInterval = l;
        }

        static /* synthetic */ void access$9(ConnectionPool connectionPool, boolean bl) {
            connectionPool.separateStoreConnection = bl;
        }
    }

}

