/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import com.sun.mail.util.LineInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Store;
import javax.mail.StreamLoader;
import javax.mail.Transport;
import javax.mail.URLName;

public final class Session {
    private static Session defaultSession;
    private final Properties addressMap = new Properties();
    private final Hashtable authTable = new Hashtable();
    private final Authenticator authenticator;
    private boolean debug = false;
    private PrintStream out;
    private final Properties props;
    private final Vector providers = new Vector();
    private final Hashtable providersByClassName = new Hashtable();
    private final Hashtable providersByProtocol = new Hashtable();

    private Session(Properties serializable, Authenticator authenticator) {
        this.props = serializable;
        this.authenticator = authenticator;
        if (Boolean.valueOf(serializable.getProperty("mail.debug")).booleanValue()) {
            this.debug = true;
        }
        if (this.debug) {
            this.pr("DEBUG: JavaMail version 1.4.1");
        }
        serializable = authenticator != null ? authenticator.getClass() : this.getClass();
        this.loadProviders((Class)serializable);
        this.loadAddressMap((Class)serializable);
    }

    private static ClassLoader getContextClassLoader() {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                }
                catch (SecurityException securityException) {
                    return null;
                }
            }
        });
    }

    public static Session getDefaultInstance(Properties properties) {
        return Session.getDefaultInstance(properties, null);
    }

    public static Session getDefaultInstance(Properties object, Authenticator authenticator) {
        synchronized (Session.class) {
            block9 : {
                block8 : {
                    block7 : {
                        Session session;
                        if (defaultSession != null) break block7;
                        defaultSession = session = new Session((Properties)object, authenticator);
                        break block8;
                    }
                    if (Session.defaultSession.authenticator != authenticator && (Session.defaultSession.authenticator == null || authenticator == null || Session.defaultSession.authenticator.getClass().getClassLoader() != authenticator.getClass().getClassLoader())) break block9;
                }
                object = defaultSession;
                return object;
            }
            object = new SecurityException("Access to default session denied");
            throw object;
        }
    }

    public static Session getInstance(Properties properties) {
        return new Session(properties, null);
    }

    public static Session getInstance(Properties properties, Authenticator authenticator) {
        return new Session(properties, authenticator);
    }

    private static InputStream getResourceAsStream(Class object, final String string2) throws IOException {
        try {
            PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction(){

                public Object run() throws IOException {
                    return Class.this.getResourceAsStream(string2);
                }
            };
            return (InputStream)AccessController.doPrivileged(privilegedExceptionAction);
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getException();
        }
    }

    private static URL[] getResources(ClassLoader classLoader, final String string2) {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                URL[] arruRL;
                URL[] arruRL2 = arruRL = (URL[])null;
                try {
                    Object object;
                    arruRL2 = arruRL;
                    Vector<Object[]> vector = new Vector<Object[]>();
                    arruRL2 = arruRL;
                    Enumeration<URL> enumeration = ClassLoader.this.getResources(string2);
                    while (enumeration != null) {
                        arruRL2 = arruRL;
                        if (!enumeration.hasMoreElements()) break;
                        arruRL2 = arruRL;
                        object = enumeration.nextElement();
                        if (object == null) continue;
                        arruRL2 = arruRL;
                        vector.addElement((Object[])object);
                    }
                    object = arruRL;
                    arruRL2 = arruRL;
                    if (vector.size() <= 0) return object;
                    arruRL2 = arruRL;
                    object = new URL[vector.size()];
                    arruRL2 = object;
                    vector.copyInto((Object[])object);
                    return object;
                }
                catch (IOException | SecurityException exception) {
                    return arruRL2;
                }
            }
        });
    }

    private Object getService(Provider provider, URLName class_) throws NoSuchProviderException {
        if (provider == null) throw new NoSuchProviderException("null");
        URLName uRLName = class_;
        if (class_ == null) {
            uRLName = new URLName(provider.getProtocol(), null, -1, null, null, null);
        }
        ClassLoader classLoader = (class_ = this.authenticator) != null ? class_.getClass().getClassLoader() : this.getClass().getClassLoader();
        Class<?> class_2 = null;
        try {
            ClassLoader classLoader2 = Session.getContextClassLoader();
            class_ = class_2;
            if (classLoader2 != null) {
                try {
                    class_ = classLoader2.loadClass(provider.getClassName());
                }
                catch (ClassNotFoundException classNotFoundException) {
                    class_ = class_2;
                }
            }
            class_2 = class_;
            if (class_ == null) {
                class_2 = classLoader.loadClass(provider.getClassName());
            }
        }
        catch (Exception exception) {
            try {
                class_2 = Class.forName(provider.getClassName());
            }
            catch (Exception exception2) {
                if (!this.debug) throw new NoSuchProviderException(provider.getProtocol());
                exception2.printStackTrace(this.getDebugOut());
                throw new NoSuchProviderException(provider.getProtocol());
            }
        }
        try {
            return class_2.getConstructor(Session.class, URLName.class).newInstance(this, uRLName);
        }
        catch (Exception exception) {
            if (!this.debug) throw new NoSuchProviderException(provider.getProtocol());
            exception.printStackTrace(this.getDebugOut());
            throw new NoSuchProviderException(provider.getProtocol());
        }
    }

    private Store getStore(Provider object, URLName uRLName) throws NoSuchProviderException {
        if (object == null) throw new NoSuchProviderException("invalid provider");
        if (((Provider)object).getType() != Provider.Type.STORE) throw new NoSuchProviderException("invalid provider");
        try {
            return (Store)this.getService((Provider)object, uRLName);
        }
        catch (ClassCastException classCastException) {
            throw new NoSuchProviderException("incorrect class");
        }
    }

    private static URL[] getSystemResources(String string2) {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                URL[] arruRL;
                Object object = arruRL = (URL[])null;
                try {
                    object = arruRL;
                    Vector<URL> vector = new Vector<URL>();
                    object = arruRL;
                    Object object2 = ClassLoader.getSystemResources(String.this);
                    while (object2 != null) {
                        object = arruRL;
                        if (!object2.hasMoreElements()) break;
                        object = arruRL;
                        URL uRL = (URL)object2.nextElement();
                        if (uRL == null) continue;
                        object = arruRL;
                        vector.addElement(uRL);
                    }
                    object2 = arruRL;
                    object = arruRL;
                    if (vector.size() <= 0) return object2;
                    object = arruRL;
                    object2 = new URL[vector.size()];
                    object = object2;
                    vector.copyInto((Object[])object2);
                    return object2;
                }
                catch (IOException | SecurityException exception) {
                    return object;
                }
            }
        });
    }

    private Transport getTransport(Provider object, URLName uRLName) throws NoSuchProviderException {
        if (object == null) throw new NoSuchProviderException("invalid provider");
        if (((Provider)object).getType() != Provider.Type.TRANSPORT) throw new NoSuchProviderException("invalid provider");
        try {
            return (Transport)this.getService((Provider)object, uRLName);
        }
        catch (ClassCastException classCastException) {
            throw new NoSuchProviderException("incorrect class");
        }
    }

    private void loadAddressMap(Class serializable) {
        block3 : {
            Object object = new StreamLoader(){

                @Override
                public void load(InputStream inputStream2) throws IOException {
                    Session.this.addressMap.load(inputStream2);
                }
            };
            this.loadResource("/META-INF/javamail.default.address.map", (Class)serializable, (StreamLoader)object);
            this.loadAllResources("META-INF/javamail.address.map", (Class)serializable, (StreamLoader)object);
            try {
                serializable = new StringBuilder(String.valueOf(System.getProperty("java.home")));
                ((StringBuilder)serializable).append(File.separator);
                ((StringBuilder)serializable).append("lib");
                ((StringBuilder)serializable).append(File.separator);
                ((StringBuilder)serializable).append("javamail.address.map");
                this.loadFile(((StringBuilder)serializable).toString(), (StreamLoader)object);
            }
            catch (SecurityException securityException) {
                if (!this.debug) break block3;
                object = new StringBuilder("DEBUG: can't get java.home: ");
                ((StringBuilder)object).append(securityException);
                this.pr(((StringBuilder)object).toString());
            }
        }
        if (!this.addressMap.isEmpty()) return;
        if (this.debug) {
            this.pr("DEBUG: failed to load address map, using defaults");
        }
        this.addressMap.put("rfc822", "smtp");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void loadAllResources(String var1_1, Class var2_2, StreamLoader var3_3) {
        block32 : {
            var4_4 = false;
            var5_5 = false;
            var6_6 = 0;
            try {
                var8_9 = var7_7 = Session.getContextClassLoader();
                if (var7_7 == null) {
                    var8_9 = var2_2.getClassLoader();
                }
                if ((var9_16 = var8_9 != null ? Session.getResources((ClassLoader)var8_9, var1_1) : Session.getSystemResources(var1_1)) == null) break block32;
                var4_4 = false;
            }
            catch (Exception var8_12) {
                // empty catch block
                ** GOTO lbl168
            }
            do {
                block40 : {
                    block38 : {
                        block35 : {
                            block37 : {
                                block36 : {
                                    block34 : {
                                        block33 : {
                                            var10_17 = var4_4;
                                            if (var6_6 >= var9_16.length) {
                                                var5_5 = var4_4;
                                                break;
                                            }
                                            var11_18 = var9_16[var6_6];
                                            var12_19 = null;
                                            var13_27 = null;
                                            var7_7 = null;
                                            var10_17 = var4_4;
                                            if (this.debug) {
                                                var10_17 = var4_4;
                                                var10_17 = var4_4;
                                                var8_9 = new StringBuilder("DEBUG: URL ");
                                                var10_17 = var4_4;
                                                var8_9.append(var11_18);
                                                var10_17 = var4_4;
                                                this.pr(var8_9.toString());
                                            }
                                            var10_17 = true;
                                            var5_5 = true;
                                            var8_9 = Session.openStream(var11_18);
                                            if (var8_9 == null) break block33;
                                            var7_7 = var8_9;
                                            var12_19 = var8_9;
                                            var13_27 = var8_9;
                                            var3_3.load((InputStream)var8_9);
                                            try {
                                                if (this.debug) {
                                                    var7_7 = new StringBuilder("DEBUG: successfully loaded resource: ");
                                                    var7_7.append(var11_18);
                                                    this.pr(var7_7.toString());
                                                }
                                                var5_5 = true;
                                                break block34;
                                            }
                                            catch (Throwable var12_20) {
                                                var4_4 = true;
                                                break block35;
                                            }
                                            catch (SecurityException var12_21) {
                                                var4_4 = var5_5;
                                                break block36;
                                            }
                                            catch (IOException var12_22) {
                                                var4_4 = var10_17;
                                                ** GOTO lbl121
                                            }
                                        }
                                        var5_5 = var4_4;
                                        var7_7 = var8_9;
                                        var12_19 = var8_9;
                                        var13_27 = var8_9;
                                        if (!this.debug) break block34;
                                        var7_7 = var8_9;
                                        var12_19 = var8_9;
                                        var13_27 = var8_9;
                                        var7_7 = var8_9;
                                        var12_19 = var8_9;
                                        var13_27 = var8_9;
                                        var14_28 = new StringBuilder("DEBUG: not loading resource: ");
                                        var7_7 = var8_9;
                                        var12_19 = var8_9;
                                        var13_27 = var8_9;
                                        var14_28.append(var11_18);
                                        var7_7 = var8_9;
                                        var12_19 = var8_9;
                                        var13_27 = var8_9;
                                        this.pr(var14_28.toString());
                                        var5_5 = var4_4;
                                    }
                                    var4_4 = var5_5;
                                    if (var8_9 == null) break block40;
                                    var10_17 = var5_5;
                                    var8_9.close();
                                    var4_4 = var5_5;
                                    catch (Throwable var12_23) {
                                        var8_9 = var7_7;
                                        break block35;
                                    }
                                    catch (SecurityException var7_8) {
                                        var8_9 = var12_19;
                                        var12_19 = var7_8;
                                    }
                                }
                                var7_7 = var8_9;
                                var5_5 = var4_4;
                                if (this.debug) {
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var13_27 = new StringBuilder("DEBUG: ");
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var13_27.append(var12_19);
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    this.pr(var13_27.toString());
                                }
                                var5_5 = var4_4;
                                if (var8_9 != null) {
                                    break block37;
                                }
                                ** GOTO lbl163
                                catch (IOException var12_24) {
                                    var8_9 = var13_27;
                                }
lbl121: // 2 sources:
                                var7_7 = var8_9;
                                var5_5 = var4_4;
                                if (this.debug) {
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var13_27 = new StringBuilder("DEBUG: ");
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    var13_27.append(var12_19);
                                    var7_7 = var8_9;
                                    var5_5 = var4_4;
                                    this.pr(var13_27.toString());
                                }
                                var5_5 = var4_4;
                                if (var8_9 == null) ** GOTO lbl163
                            }
                            try {
                                var8_9.close();
                                var5_5 = var4_4;
                                ** GOTO lbl163
                            }
                            catch (Exception var8_10) {
                                break block38;
                            }
                            {
                            }
                            catch (Throwable var12_25) {
                                var4_4 = var5_5;
                                var8_9 = var7_7;
                            }
                        }
                        if (var8_9 == null) ** GOTO lbl160
                        var10_17 = var4_4;
                        try {
                            block41 : {
                                block39 : {
                                    try {
                                        var8_9.close();
                                        break block39;
                                    }
                                    catch (IOException var8_15) {}
                                    catch (IOException var8_14) {
                                        var5_5 = var4_4;
                                    }
                                    break block41;
                                }
                                var10_17 = var4_4;
                                throw var12_26;
                            }
                            var4_4 = var5_5;
                            break block40;
                        }
                        catch (Exception var8_11) {
                            var4_4 = var10_17;
                        }
                    }
                    var5_5 = var4_4;
                    if (!this.debug) break;
                    var7_7 = new StringBuilder("DEBUG: ");
                    var7_7.append(var8_9);
                    this.pr(var7_7.toString());
                    var5_5 = var4_4;
                    break;
                    catch (IOException var8_13) {
                        var4_4 = var5_5;
                    }
                }
                ++var6_6;
            } while (true);
        }
        if (var5_5 != false) return;
        if (this.debug) {
            this.pr("DEBUG: !anyLoaded");
        }
        var8_9 = new StringBuilder("/");
        var8_9.append(var1_1);
        this.loadResource(var8_9.toString(), var2_2, var3_3);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private void loadFile(String charSequence, StreamLoader object) {
        void var1_4;
        Object object2;
        block18 : {
            block21 : {
                StringBuilder stringBuilder;
                Object object3;
                block20 : {
                    Object var4_9;
                    block19 : {
                        block17 : {
                            Object streamLoader;
                            stringBuilder = null;
                            var4_9 = null;
                            object2 = streamLoader = null;
                            object2 = streamLoader;
                            object2 = streamLoader;
                            FileInputStream fileInputStream = new FileInputStream((String)charSequence);
                            object2 = streamLoader;
                            object3 = new BufferedInputStream(fileInputStream);
                            object.load((InputStream)object3);
                            if (!this.debug) break block17;
                            object = new StringBuilder("DEBUG: successfully loaded file: ");
                            ((StringBuilder)object).append((String)charSequence);
                            this.pr(((StringBuilder)object).toString());
                        }
                        try {
                            ((InputStream)object3).close();
                            return;
                        }
                        catch (IOException iOException) {
                            return;
                        }
                        catch (Throwable throwable) {
                            object2 = object3;
                            break block18;
                        }
                        catch (SecurityException securityException) {
                            object = object3;
                            object3 = securityException;
                            break block19;
                        }
                        catch (IOException iOException) {
                            object = object3;
                            object3 = iOException;
                            break block20;
                        }
                        catch (Throwable throwable) {
                            break block18;
                        }
                        catch (SecurityException securityException) {
                            object = stringBuilder;
                        }
                    }
                    object2 = object;
                    {
                        if (this.debug) {
                            object2 = object;
                            object2 = object;
                            stringBuilder = new StringBuilder("DEBUG: not loading file: ");
                            object2 = object;
                            stringBuilder.append((String)charSequence);
                            object2 = object;
                            this.pr(stringBuilder.toString());
                            object2 = object;
                            object2 = object;
                            charSequence = new StringBuilder("DEBUG: ");
                            object2 = object;
                            ((StringBuilder)charSequence).append(object3);
                            object2 = object;
                            this.pr(((StringBuilder)charSequence).toString());
                        }
                        if (object == null) return;
                        break block21;
                    }
                    catch (IOException iOException) {
                        object = var4_9;
                    }
                }
                object2 = object;
                {
                    if (this.debug) {
                        object2 = object;
                        object2 = object;
                        stringBuilder = new StringBuilder("DEBUG: not loading file: ");
                        object2 = object;
                        stringBuilder.append((String)charSequence);
                        object2 = object;
                        this.pr(stringBuilder.toString());
                        object2 = object;
                        object2 = object;
                        charSequence = new StringBuilder("DEBUG: ");
                        object2 = object;
                        ((StringBuilder)charSequence).append(object3);
                        object2 = object;
                        this.pr(((StringBuilder)charSequence).toString());
                    }
                    if (object == null) return;
                }
            }
            ((InputStream)object).close();
            return;
        }
        if (object2 == null) throw var1_4;
        try {
            ((InputStream)object2).close();
        }
        catch (IOException iOException) {
            throw var1_4;
        }
        throw var1_4;
    }

    private void loadProviders(Class serializable) {
        StreamLoader streamLoader;
        block4 : {
            streamLoader = new StreamLoader(){

                @Override
                public void load(InputStream inputStream2) throws IOException {
                    Session.this.loadProvidersFromStream(inputStream2);
                }
            };
            try {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(System.getProperty("java.home")));
                stringBuilder.append(File.separator);
                stringBuilder.append("lib");
                stringBuilder.append(File.separator);
                stringBuilder.append("javamail.providers");
                this.loadFile(stringBuilder.toString(), streamLoader);
            }
            catch (SecurityException securityException) {
                if (!this.debug) break block4;
                StringBuilder stringBuilder = new StringBuilder("DEBUG: can't get java.home: ");
                stringBuilder.append(securityException);
                this.pr(stringBuilder.toString());
            }
        }
        this.loadAllResources("META-INF/javamail.providers", (Class)serializable, streamLoader);
        this.loadResource("/META-INF/javamail.default.providers", (Class)serializable, streamLoader);
        if (this.providers.size() == 0) {
            if (this.debug) {
                this.pr("DEBUG: failed to load any providers, using defaults");
            }
            this.addProvider(new Provider(Provider.Type.STORE, "imap", "com.sun.mail.imap.IMAPStore", "Sun Microsystems, Inc.", "1.4.1"));
            this.addProvider(new Provider(Provider.Type.STORE, "imaps", "com.sun.mail.imap.IMAPSSLStore", "Sun Microsystems, Inc.", "1.4.1"));
            this.addProvider(new Provider(Provider.Type.STORE, "pop3", "com.sun.mail.pop3.POP3Store", "Sun Microsystems, Inc.", "1.4.1"));
            this.addProvider(new Provider(Provider.Type.STORE, "pop3s", "com.sun.mail.pop3.POP3SSLStore", "Sun Microsystems, Inc.", "1.4.1"));
            this.addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPTransport", "Sun Microsystems, Inc.", "1.4.1"));
            this.addProvider(new Provider(Provider.Type.TRANSPORT, "smtps", "com.sun.mail.smtp.SMTPSSLTransport", "Sun Microsystems, Inc.", "1.4.1"));
        }
        if (!this.debug) return;
        this.pr("DEBUG: Tables of loaded providers");
        serializable = new StringBuilder("DEBUG: Providers Listed By Class Name: ");
        ((StringBuilder)serializable).append(this.providersByClassName.toString());
        this.pr(((StringBuilder)serializable).toString());
        serializable = new StringBuilder("DEBUG: Providers Listed By Protocol: ");
        ((StringBuilder)serializable).append(this.providersByProtocol.toString());
        this.pr(((StringBuilder)serializable).toString());
    }

    private void loadProvidersFromStream(InputStream object) throws IOException {
        if (object == null) return;
        LineInputStream lineInputStream = new LineInputStream((InputStream)object);
        String string2;
        block0 : while ((string2 = lineInputStream.readLine()) != null) {
            Object object2;
            Object object3;
            if (string2.startsWith("#")) continue;
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ";");
            String string3 = null;
            Object object4 = object2 = (object3 = (object = string3));
            Object object5 = object;
            object = string3;
            do {
                if (!stringTokenizer.hasMoreTokens()) {
                    if (object != null && object5 != null && object3 != null && ((String)object5).length() > 0 && ((String)object3).length() > 0) {
                        this.addProvider(new Provider((Provider.Type)object, (String)object5, (String)object3, (String)object2, (String)object4));
                        continue block0;
                    }
                    if (!this.debug) continue block0;
                    object = new StringBuilder("DEBUG: Bad provider entry: ");
                    ((StringBuilder)object).append(string2);
                    this.pr(((StringBuilder)object).toString());
                    continue block0;
                }
                string3 = stringTokenizer.nextToken().trim();
                int n = string3.indexOf("=");
                if (string3.startsWith("protocol=")) {
                    object5 = string3.substring(n + 1);
                    continue;
                }
                if (string3.startsWith("type=")) {
                    if ((string3 = string3.substring(n + 1)).equalsIgnoreCase("store")) {
                        object = Provider.Type.STORE;
                        continue;
                    }
                    if (!string3.equalsIgnoreCase("transport")) continue;
                    object = Provider.Type.TRANSPORT;
                    continue;
                }
                if (string3.startsWith("class=")) {
                    object3 = string3.substring(n + 1);
                    continue;
                }
                if (string3.startsWith("vendor=")) {
                    object2 = string3.substring(n + 1);
                    continue;
                }
                if (!string3.startsWith("version=")) continue;
                object4 = string3.substring(n + 1);
            } while (true);
            break;
        }
        return;
    }

    /*
     * Exception decompiling
     */
    private void loadResource(String var1_1, Class var2_5, StreamLoader var3_8) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
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

    private static InputStream openStream(URL object) throws IOException {
        try {
            PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction(){

                public Object run() throws IOException {
                    return URL.this.openStream();
                }
            };
            return (InputStream)AccessController.doPrivileged(privilegedExceptionAction);
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getException();
        }
    }

    private void pr(String string2) {
        this.getDebugOut().println(string2);
    }

    public void addProvider(Provider provider) {
        synchronized (this) {
            this.providers.addElement(provider);
            this.providersByClassName.put(provider.getClassName(), provider);
            if (this.providersByProtocol.containsKey(provider.getProtocol())) return;
            this.providersByProtocol.put(provider.getProtocol(), provider);
            return;
        }
    }

    public boolean getDebug() {
        synchronized (this) {
            return this.debug;
        }
    }

    public PrintStream getDebugOut() {
        synchronized (this) {
            if (this.out != null) return this.out;
            return System.out;
        }
    }

    public Folder getFolder(URLName uRLName) throws MessagingException {
        Store store = this.getStore(uRLName);
        store.connect();
        return store.getFolder(uRLName);
    }

    public PasswordAuthentication getPasswordAuthentication(URLName uRLName) {
        return (PasswordAuthentication)this.authTable.get(uRLName);
    }

    public Properties getProperties() {
        return this.props;
    }

    public String getProperty(String string2) {
        return this.props.getProperty(string2);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public Provider getProvider(String object) throws NoSuchProviderException {
        synchronized (this) {
            if (object != null && ((String)object).length() > 0) {
                Object object2 = null;
                Properties properties = this.props;
                Object object3 = new StringBuilder("mail.");
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append(".class");
                object3 = properties.getProperty(((StringBuilder)object3).toString());
                if (object3 != null) {
                    if (this.debug) {
                        object2 = new StringBuilder("DEBUG: mail.");
                        ((StringBuilder)object2).append((String)object);
                        ((StringBuilder)object2).append(".class property exists and points to ");
                        ((StringBuilder)object2).append((String)object3);
                        this.pr(((StringBuilder)object2).toString());
                    }
                    object2 = (Provider)this.providersByClassName.get(object3);
                }
                if (object2 != null) {
                    return object2;
                }
                object2 = (Provider)this.providersByProtocol.get(object);
                if (object2 != null) {
                    if (!this.debug) return object2;
                    object = new StringBuilder("DEBUG: getProvider() returning ");
                    ((StringBuilder)object).append(((Provider)object2).toString());
                    this.pr(((StringBuilder)object).toString());
                    return object2;
                }
                object2 = new StringBuilder("No provider for ");
                ((StringBuilder)object2).append((String)object);
                object3 = new NoSuchProviderException(((StringBuilder)object2).toString());
                throw object3;
            }
            object = new NoSuchProviderException("Invalid protocol: null");
            throw object;
        }
    }

    public Provider[] getProviders() {
        synchronized (this) {
            Object[] arrobject = new Provider[this.providers.size()];
            this.providers.copyInto(arrobject);
            return arrobject;
        }
    }

    public Store getStore() throws NoSuchProviderException {
        return this.getStore(this.getProperty("mail.store.protocol"));
    }

    public Store getStore(String string2) throws NoSuchProviderException {
        return this.getStore(new URLName(string2, null, -1, null, null, null));
    }

    public Store getStore(Provider provider) throws NoSuchProviderException {
        return this.getStore(provider, null);
    }

    public Store getStore(URLName uRLName) throws NoSuchProviderException {
        return this.getStore(this.getProvider(uRLName.getProtocol()), uRLName);
    }

    public Transport getTransport() throws NoSuchProviderException {
        return this.getTransport(this.getProperty("mail.transport.protocol"));
    }

    public Transport getTransport(String string2) throws NoSuchProviderException {
        return this.getTransport(new URLName(string2, null, -1, null, null, null));
    }

    public Transport getTransport(Address address) throws NoSuchProviderException {
        CharSequence charSequence = (String)this.addressMap.get(address.getType());
        if (charSequence != null) {
            return this.getTransport((String)charSequence);
        }
        charSequence = new StringBuilder("No provider for Address type: ");
        ((StringBuilder)charSequence).append(address.getType());
        throw new NoSuchProviderException(((StringBuilder)charSequence).toString());
    }

    public Transport getTransport(Provider provider) throws NoSuchProviderException {
        return this.getTransport(provider, null);
    }

    public Transport getTransport(URLName uRLName) throws NoSuchProviderException {
        return this.getTransport(this.getProvider(uRLName.getProtocol()), uRLName);
    }

    public PasswordAuthentication requestPasswordAuthentication(InetAddress inetAddress, int n, String string2, String string3, String string4) {
        Authenticator authenticator = this.authenticator;
        if (authenticator == null) return null;
        return authenticator.requestPasswordAuthentication(inetAddress, n, string2, string3, string4);
    }

    public void setDebug(boolean bl) {
        synchronized (this) {
            this.debug = bl;
            if (!bl) return;
            this.pr("DEBUG: setDebug: JavaMail version 1.4.1");
            return;
        }
    }

    public void setDebugOut(PrintStream printStream) {
        synchronized (this) {
            this.out = printStream;
            return;
        }
    }

    public void setPasswordAuthentication(URLName uRLName, PasswordAuthentication passwordAuthentication) {
        if (passwordAuthentication == null) {
            this.authTable.remove(uRLName);
            return;
        }
        this.authTable.put(uRLName, passwordAuthentication);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public void setProtocolForAddress(String string2, String string3) {
        synchronized (this) {
            void var2_2;
            if (var2_2 == null) {
                this.addressMap.remove(string2);
            } else {
                this.addressMap.put(string2, var2_2);
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void setProvider(Provider var1_1) throws NoSuchProviderException {
        // MONITORENTER : this
        if (var1_1 == null) ** GOTO lbl16
        this.providersByProtocol.put(var1_1.getProtocol(), var1_1);
        var2_3 = this.props;
        var3_4 = new StringBuilder("mail.");
        var3_4.append(var1_1.getProtocol());
        var3_4.append(".class");
        var2_3.put(var3_4.toString(), var1_1.getClassName());
        // MONITOREXIT : this
        return;
lbl16: // 1 sources:
        var1_1 = new NoSuchProviderException("Can't set null provider");
        throw var1_1;
    }

}

