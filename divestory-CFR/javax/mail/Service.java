/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.mail.AuthenticationFailedException;
import javax.mail.EventQueue;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

public abstract class Service {
    private boolean connected = false;
    private Vector connectionListeners = null;
    protected boolean debug = false;
    private EventQueue q;
    private Object qLock = new Object();
    protected Session session;
    protected URLName url = null;

    protected Service(Session session, URLName uRLName) {
        this.session = session;
        this.url = uRLName;
        this.debug = session.getDebug();
    }

    private void terminateQueue() {
        Object object = this.qLock;
        synchronized (object) {
            if (this.q == null) return;
            Vector vector = new Vector();
            vector.setSize(1);
            EventQueue eventQueue = this.q;
            TerminatorEvent terminatorEvent = new TerminatorEvent();
            eventQueue.enqueue(terminatorEvent, vector);
            this.q = null;
            return;
        }
    }

    public void addConnectionListener(ConnectionListener connectionListener) {
        synchronized (this) {
            if (this.connectionListeners == null) {
                Vector vector;
                this.connectionListeners = vector = new Vector();
            }
            this.connectionListeners.addElement(connectionListener);
            return;
        }
    }

    public void close() throws MessagingException {
        synchronized (this) {
            this.setConnected(false);
            this.notifyConnectionListeners(3);
            return;
        }
    }

    public void connect() throws MessagingException {
        this.connect(null, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void connect(String var1_1, int var2_3, String var3_4, String var4_5) throws MessagingException {
        block34 : {
            block33 : {
                block31 : {
                    block32 : {
                        block26 : {
                            block30 : {
                                block27 : {
                                    block29 : {
                                        block28 : {
                                            // MONITORENTER : this
                                            if (this.isConnected()) {
                                                var1_1 = new IllegalStateException("already connected");
                                                throw var1_1;
                                            }
                                            if (this.url == null) break block27;
                                            var5_6 = this.url.getProtocol();
                                            var6_7 = var1_1 == null ? this.url.getHost() : var1_1;
                                            if (var2_2 == -1) {
                                                var2_2 = this.url.getPort();
                                            }
                                            if (var3_3 != null) break block28;
                                            var7_10 = var3_3 = this.url.getUsername();
                                            if (var4_4 != null) ** GOTO lbl-1000
                                            var1_1 = this.url.getPassword();
                                            break block29;
                                        }
                                        var7_10 = var3_3;
                                        if (var4_4 != null) ** GOTO lbl-1000
                                        var7_10 = var3_3;
                                        if (var3_3.equals(this.url.getUsername())) {
                                            var1_1 = this.url.getPassword();
                                        } else lbl-1000: // 3 sources:
                                        {
                                            var1_1 = var4_4;
                                            var3_3 = var7_10;
                                        }
                                    }
                                    var8_11 = this.url.getFile();
                                    var4_4 = var6_7;
                                    break block30;
                                }
                                var6_7 = var1_1;
                                var1_1 = var4_4;
                                var5_6 = null;
                                var8_11 = null;
                                var4_4 = var6_7;
                            }
                            var6_7 = var3_3;
                            var9_12 = var4_4;
                            if (var5_6 != null) {
                                var7_10 = var4_4;
                                if (var4_4 == null) {
                                    var4_4 = this.session;
                                    var6_7 = new StringBuilder("mail.");
                                    var6_7.append(var5_6);
                                    var6_7.append(".host");
                                    var7_10 = var4_4.getProperty(var6_7.toString());
                                }
                                var6_7 = var3_3;
                                var9_12 = var7_10;
                                if (var3_3 == null) {
                                    var4_4 = this.session;
                                    var3_3 = new StringBuilder("mail.");
                                    var3_3.append(var5_6);
                                    var3_3.append(".user");
                                    var6_7 = var4_4.getProperty(var3_3.toString());
                                    var9_12 = var7_10;
                                }
                            }
                            var7_10 = var9_12;
                            if (var9_12 == null) {
                                var7_10 = this.session.getProperty("mail.host");
                            }
                            var3_3 = var6_7;
                            if (var6_7 == null) {
                                var3_3 = this.session.getProperty("mail.user");
                            }
                            var3_3 = var4_4 = var3_3;
                            if (var4_4 == null) {
                                try {
                                    var3_3 = System.getProperty("user.name");
                                }
                                catch (SecurityException var6_8) {
                                    var3_3 = var4_4;
                                    if (!this.debug) break block26;
                                    var6_8.printStackTrace(this.session.getDebugOut());
                                    var3_3 = var4_4;
                                }
                            }
                        }
                        if (var1_1 != null || this.url == null) break block31;
                        var4_4 = new URLName(var5_6, (String)var7_10, var2_2, var8_11, (String)var3_3, null);
                        this.setURLName((URLName)var4_4);
                        var6_7 = this.session.getPasswordAuthentication(this.getURLName());
                        if (var6_7 == null) break block32;
                        if (var3_3 == null) {
                            var4_4 = var6_7.getUserName();
                            var1_1 = var6_7.getPassword();
                        } else {
                            var4_4 = var3_3;
                            if (var3_3.equals(var6_7.getUserName())) {
                                var1_1 = var6_7.getPassword();
                                var4_4 = var3_3;
                            }
                        }
                        break block33;
                    }
                    var4_4 = var1_1;
                    var10_13 = true;
                    var1_1 = var3_3;
                    var3_3 = var4_4;
                    break block34;
                }
                var4_4 = var3_3;
            }
            var10_13 = false;
            var3_3 = var1_1;
            var1_1 = var4_4;
        }
        try {
            var11_14 = this.protocolConnect((String)var7_10, var2_2, (String)var1_1, (String)var3_3);
            var4_4 = null;
        }
        catch (AuthenticationFailedException var4_5) {
            var11_14 = false;
        }
        var12_15 = var11_14;
        var9_12 = var1_1;
        var6_7 = var3_3;
        if (!var11_14) {
            try {
                var6_7 = InetAddress.getByName((String)var7_10);
            }
            catch (UnknownHostException var6_9) {
                var6_7 = null;
            }
            var13_16 = this.session.requestPasswordAuthentication((InetAddress)var6_7, var2_2, var5_6, null, (String)var1_1);
            var12_15 = var11_14;
            var9_12 = var1_1;
            var6_7 = var3_3;
            if (var13_16 != null) {
                var9_12 = var13_16.getUserName();
                var6_7 = var13_16.getPassword();
                var12_15 = this.protocolConnect((String)var7_10, var2_2, (String)var9_12, (String)var6_7);
            }
        }
        if (!var12_15) {
            if (var4_4 != null) {
                throw var4_4;
            }
            var1_1 = new AuthenticationFailedException();
            throw var1_1;
        }
        var1_1 = new URLName(var5_6, (String)var7_10, var2_2, var8_11, (String)var9_12, (String)var6_7);
        this.setURLName((URLName)var1_1);
        if (var10_13) {
            var4_4 = this.session;
            var1_1 = this.getURLName();
            var3_3 = new PasswordAuthentication((String)var9_12, (String)var6_7);
            var4_4.setPasswordAuthentication((URLName)var1_1, (PasswordAuthentication)var3_3);
        }
        this.setConnected(true);
        this.notifyConnectionListeners(1);
        // MONITOREXIT : this
    }

    public void connect(String string2, String string3) throws MessagingException {
        this.connect(null, string2, string3);
    }

    public void connect(String string2, String string3, String string4) throws MessagingException {
        this.connect(string2, -1, string3, string4);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.terminateQueue();
    }

    public URLName getURLName() {
        synchronized (this) {
            if (this.url == null) return this.url;
            if (this.url.getPassword() != null) return new URLName(this.url.getProtocol(), this.url.getHost(), this.url.getPort(), null, this.url.getUsername(), null);
            if (this.url.getFile() == null) return this.url;
            return new URLName(this.url.getProtocol(), this.url.getHost(), this.url.getPort(), null, this.url.getUsername(), null);
        }
    }

    public boolean isConnected() {
        synchronized (this) {
            return this.connected;
        }
    }

    protected void notifyConnectionListeners(int n) {
        synchronized (this) {
            if (this.connectionListeners != null) {
                ConnectionEvent connectionEvent = new ConnectionEvent(this, n);
                this.queueEvent(connectionEvent, this.connectionListeners);
            }
            if (n != 3) return;
            this.terminateQueue();
            return;
        }
    }

    protected boolean protocolConnect(String string2, int n, String string3, String string4) throws MessagingException {
        return false;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    protected void queueEvent(MailEvent mailEvent, Vector vector) {
        Object object = this.qLock;
        synchronized (object) {
            if (this.q == null) {
                EventQueue eventQueue;
                this.q = eventQueue = new EventQueue();
            }
        }
        vector = (Vector)vector.clone();
        this.q.enqueue(mailEvent, vector);
    }

    public void removeConnectionListener(ConnectionListener connectionListener) {
        synchronized (this) {
            if (this.connectionListeners == null) return;
            this.connectionListeners.removeElement(connectionListener);
            return;
        }
    }

    protected void setConnected(boolean bl) {
        synchronized (this) {
            this.connected = bl;
            return;
        }
    }

    protected void setURLName(URLName uRLName) {
        synchronized (this) {
            this.url = uRLName;
            return;
        }
    }

    public String toString() {
        URLName uRLName = this.getURLName();
        if (uRLName == null) return super.toString();
        return uRLName.toString();
    }

    static class TerminatorEvent
    extends MailEvent {
        private static final long serialVersionUID = 5542172141759168416L;

        TerminatorEvent() {
            super(new Object());
        }

        @Override
        public void dispatch(Object object) {
            Thread.currentThread().interrupt();
        }
    }

}

