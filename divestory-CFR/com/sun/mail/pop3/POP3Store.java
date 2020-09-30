/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.DefaultFolder;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.Protocol;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class POP3Store
extends Store {
    private int defaultPort = 110;
    boolean disableTop = false;
    boolean forgetTopHeaders = false;
    private String host = null;
    private boolean isSSL = false;
    Constructor messageConstructor = null;
    private String name = "pop3";
    private String passwd = null;
    private Protocol port = null;
    private int portNum = -1;
    private POP3Folder portOwner = null;
    boolean rsetBeforeQuit = false;
    private String user = null;

    public POP3Store(Session session, URLName uRLName) {
        this(session, uRLName, "pop3", 110, false);
    }

    public POP3Store(Session object, URLName class_, String object2, int n, boolean bl) {
        super((Session)object, (URLName)((Object)class_));
        if (class_ != null) {
            object2 = ((URLName)((Object)class_)).getProtocol();
        }
        this.name = object2;
        this.defaultPort = n;
        this.isSSL = bl;
        class_ = new StringBuilder("mail.");
        ((StringBuilder)((Object)class_)).append((String)object2);
        ((StringBuilder)((Object)class_)).append(".rsetbeforequit");
        class_ = ((Session)object).getProperty(((StringBuilder)((Object)class_)).toString());
        if (class_ != null && ((String)((Object)class_)).equalsIgnoreCase("true")) {
            this.rsetBeforeQuit = true;
        }
        class_ = new StringBuilder("mail.");
        ((StringBuilder)((Object)class_)).append((String)object2);
        ((StringBuilder)((Object)class_)).append(".disabletop");
        class_ = ((Session)object).getProperty(((StringBuilder)((Object)class_)).toString());
        if (class_ != null && ((String)((Object)class_)).equalsIgnoreCase("true")) {
            this.disableTop = true;
        }
        class_ = new StringBuilder("mail.");
        ((StringBuilder)((Object)class_)).append((String)object2);
        ((StringBuilder)((Object)class_)).append(".forgettopheaders");
        class_ = ((Session)object).getProperty(((StringBuilder)((Object)class_)).toString());
        if (class_ != null && ((String)((Object)class_)).equalsIgnoreCase("true")) {
            this.forgetTopHeaders = true;
        }
        class_ = new StringBuilder("mail.");
        ((StringBuilder)((Object)class_)).append((String)object2);
        ((StringBuilder)((Object)class_)).append(".message.class");
        object2 = ((Session)object).getProperty(((StringBuilder)((Object)class_)).toString());
        if (object2 == null) return;
        if (((Session)object).getDebug()) {
            PrintStream printStream = ((Session)object).getDebugOut();
            class_ = new StringBuilder("DEBUG: POP3 message class: ");
            ((StringBuilder)((Object)class_)).append((String)object2);
            printStream.println(((StringBuilder)((Object)class_)).toString());
        }
        try {
            class_ = this.getClass().getClassLoader();
            try {
                class_ = ((ClassLoader)((Object)class_)).loadClass((String)object2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                class_ = Class.forName((String)object2);
            }
            this.messageConstructor = class_.getConstructor(Folder.class, Integer.TYPE);
            return;
        }
        catch (Exception exception) {
            if (!((Session)object).getDebug()) return;
            object2 = ((Session)object).getDebugOut();
            object = new StringBuilder("DEBUG: failed to load POP3 message class: ");
            ((StringBuilder)object).append(exception);
            ((PrintStream)object2).println(((StringBuilder)object).toString());
        }
    }

    private void checkConnected() throws MessagingException {
        if (!super.isConnected()) throw new MessagingException("Not connected");
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void close() throws MessagingException {
        block7 : {
            block6 : {
                // MONITORENTER : this
                if (this.port == null) break block6;
                this.port.quit();
            }
            this.port = null;
            break block7;
            catch (Throwable throwable) {
                this.port = null;
                super.close();
                throw throwable;
            }
            catch (IOException iOException) {
                this.port = null;
            }
        }
        super.close();
        return;
    }

    void closePort(POP3Folder pOP3Folder) {
        synchronized (this) {
            if (this.portOwner != pOP3Folder) return;
            this.port = null;
            this.portOwner = null;
            return;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.port == null) return;
        this.close();
    }

    @Override
    public Folder getDefaultFolder() throws MessagingException {
        this.checkConnected();
        return new DefaultFolder(this);
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        this.checkConnected();
        return new POP3Folder(this, string2);
    }

    @Override
    public Folder getFolder(URLName uRLName) throws MessagingException {
        this.checkConnected();
        return new POP3Folder(this, uRLName.getFile());
    }

    Protocol getPort(POP3Folder object) throws IOException {
        synchronized (this) {
            CharSequence charSequence;
            Protocol protocol;
            block8 : {
                if (this.port != null && this.portOwner == null) {
                    this.portOwner = object;
                    return this.port;
                }
                String string2 = this.host;
                int n = this.portNum;
                boolean bl = this.session.getDebug();
                PrintStream printStream = this.session.getDebugOut();
                Properties properties = this.session.getProperties();
                charSequence = new StringBuilder("mail.");
                charSequence.append(this.name);
                protocol = new Protocol(string2, n, bl, printStream, properties, charSequence.toString(), this.isSSL);
                charSequence = protocol.login(this.user, this.passwd);
                if (charSequence != null) break block8;
                if (this.port == null && object != null) {
                    this.port = protocol;
                    this.portOwner = object;
                }
                if (this.portOwner != null) return protocol;
                this.portOwner = object;
                return protocol;
            }
            try {
                protocol.quit();
            }
            catch (IOException | Throwable throwable) {}
            object = new EOFException((String)charSequence);
            throw object;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public boolean isConnected() {
        // MONITORENTER : this
        boolean bl = super.isConnected();
        if (!bl) {
            // MONITOREXIT : this
            return false;
        }
        // MONITORENTER : this
        try {
            if (this.port == null) {
                this.port = this.getPort(null);
                return true;
            }
            this.port.noop();
            // MONITOREXIT : this
            // MONITOREXIT : this
            return true;
        }
        catch (IOException iOException) {
            try {
                super.close();
                // MONITOREXIT : this
                // MONITOREXIT : this
                return false;
            }
            catch (Throwable | MessagingException throwable) {}
        }
        finally {
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    protected boolean protocolConnect(String var1_1, int var2_3, String var3_4, String var4_7) throws MessagingException {
        block9 : {
            // MONITORENTER : this
            if (var1_1 == null || var4_7 == null) break block9;
            if (var3_4 == null) {
                return false;
            }
            var5_8 = var2_3;
            if (var2_3 != -1) ** GOTO lbl18
            var6_9 = this.session;
            var7_10 = new StringBuilder("mail.");
            var7_10.append(this.name);
            var7_10.append(".port");
            var6_9 = var6_9.getProperty(var7_10.toString());
            var5_8 = var2_3;
            if (var6_9 != null) {
                var5_8 = Integer.parseInt((String)var6_9);
            }
lbl18: // 4 sources:
            var2_3 = var5_8;
            if (var5_8 == -1) {
                var2_3 = this.defaultPort;
            }
            this.host = var1_1;
            this.portNum = var2_3;
            this.user = var3_4;
            this.passwd = var4_7;
            try {
                this.port = this.getPort(null);
                // MONITOREXIT : this
                return true;
            }
            catch (IOException var3_5) {
                var1_1 = new MessagingException("Connect failed", var3_5);
                throw var1_1;
            }
            catch (EOFException var3_6) {
                var1_1 = new AuthenticationFailedException(var3_6.getMessage());
                throw var1_1;
            }
        }
        // MONITOREXIT : this
        return false;
    }
}

