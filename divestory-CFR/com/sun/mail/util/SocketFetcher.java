/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketFetcher {
    private SocketFetcher() {
    }

    private static void configureSSLSocket(Socket socket, Properties object, String charSequence) {
        if (!(socket instanceof SSLSocket)) {
            return;
        }
        socket = (SSLSocket)socket;
        CharSequence charSequence2 = new StringBuilder(String.valueOf(charSequence));
        charSequence2.append(".ssl.protocols");
        charSequence2 = ((Properties)object).getProperty(charSequence2.toString(), null);
        if (charSequence2 != null) {
            ((SSLSocket)socket).setEnabledProtocols(SocketFetcher.stringArray((String)charSequence2));
        } else {
            ((SSLSocket)socket).setEnabledProtocols(new String[]{"TLSv1"});
        }
        charSequence = new StringBuilder(String.valueOf(charSequence));
        ((StringBuilder)charSequence).append(".ssl.ciphersuites");
        object = ((Properties)object).getProperty(((StringBuilder)charSequence).toString(), null);
        if (object == null) return;
        ((SSLSocket)socket).setEnabledCipherSuites(SocketFetcher.stringArray((String)object));
    }

    private static Socket createSocket(InetAddress inetAddress, int n, String string2, int n2, int n3, SocketFactory object, boolean bl) throws IOException {
        object = object != null ? ((SocketFactory)object).createSocket() : (bl ? SSLSocketFactory.getDefault().createSocket() : new Socket());
        if (inetAddress != null) {
            ((Socket)object).bind(new InetSocketAddress(inetAddress, n));
        }
        if (n3 >= 0) {
            ((Socket)object).connect(new InetSocketAddress(string2, n2), n3);
            return object;
        }
        ((Socket)object).connect(new InetSocketAddress(string2, n2));
        return object;
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

    public static Socket getSocket(String string2, int n, Properties properties, String string3) throws IOException {
        return SocketFetcher.getSocket(string2, n, properties, string3, false);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static Socket getSocket(String object, int n, Properties object2, String object3, boolean bl) throws IOException {
        int n4;
        int n3;
        Object object5;
        int n2;
        String string2;
        InetAddress inetAddress;
        Object object4;
        block31 : {
            block27 : {
                boolean bl2;
                int n5;
                block32 : {
                    block29 : {
                        block28 : {
                            block30 : {
                                block26 : {
                                    block25 : {
                                        if (object3 == null) {
                                            object3 = "socket";
                                        }
                                        object4 = object2 == null ? new Properties() : object2;
                                        object2 = new StringBuilder(String.valueOf(object3));
                                        ((StringBuilder)object2).append(".connectiontimeout");
                                        object2 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                        n4 = -1;
                                        if (object2 != null) {
                                            try {
                                                n3 = Integer.parseInt((String)object2);
                                                break block25;
                                            }
                                            catch (NumberFormatException numberFormatException) {}
                                        }
                                        n3 = -1;
                                    }
                                    object2 = new StringBuilder(String.valueOf(object3));
                                    ((StringBuilder)object2).append(".timeout");
                                    string2 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                    object2 = new StringBuilder(String.valueOf(object3));
                                    ((StringBuilder)object2).append(".localaddress");
                                    object2 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                    inetAddress = object2 != null ? InetAddress.getByName((String)object2) : null;
                                    object2 = new StringBuilder(String.valueOf(object3));
                                    ((StringBuilder)object2).append(".localport");
                                    object2 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                    if (object2 != null) {
                                        try {
                                            n2 = Integer.parseInt((String)object2);
                                            break block26;
                                        }
                                        catch (NumberFormatException numberFormatException) {}
                                    }
                                    n2 = 0;
                                }
                                object2 = new StringBuilder(String.valueOf(object3));
                                ((StringBuilder)object2).append(".socketFactory.fallback");
                                object2 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                bl2 = object2 == null || !((String)object2).equalsIgnoreCase("false");
                                object2 = new StringBuilder(String.valueOf(object3));
                                ((StringBuilder)object2).append(".socketFactory.class");
                                object5 = ((Properties)object4).getProperty(((StringBuilder)object2).toString(), null);
                                object2 = SocketFetcher.getSocketFactory((String)object5);
                                if (object2 == null) break block27;
                                CharSequence charSequence = new StringBuilder(String.valueOf(object3));
                                charSequence.append(".socketFactory.port");
                                charSequence = ((Properties)object4).getProperty(charSequence.toString(), null);
                                if (charSequence == null) break block28;
                                try {
                                    n5 = Integer.parseInt((String)charSequence);
                                    break block29;
                                }
                                catch (Exception exception) {
                                    break block30;
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                            }
                            n5 = -1;
                            break block32;
                            catch (NumberFormatException numberFormatException) {}
                        }
                        n5 = -1;
                    }
                    if (n5 == -1) {
                        n5 = n;
                    }
                    try {
                        object2 = SocketFetcher.createSocket(inetAddress, n2, (String)object, n5, n3, (SocketFactory)object2, bl);
                        break block31;
                    }
                    catch (Exception exception) {}
                }
                if (bl2) break block27;
                object3 = object2;
                if (object2 instanceof InvocationTargetException) {
                    object4 = ((InvocationTargetException)object2).getTargetException();
                    object3 = object2;
                    if (object4 instanceof Exception) {
                        object3 = (Exception)object4;
                    }
                }
                if (object3 instanceof IOException) {
                    throw (IOException)object3;
                }
                object2 = new StringBuilder("Couldn't connect using \"");
                ((StringBuilder)object2).append((String)object5);
                ((StringBuilder)object2).append("\" socket factory to host, port: ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(", ");
                ((StringBuilder)object2).append(n5);
                ((StringBuilder)object2).append("; Exception: ");
                ((StringBuilder)object2).append(object3);
                object = new IOException(((StringBuilder)object2).toString());
                ((Throwable)object).initCause((Throwable)object3);
                throw object;
            }
            object2 = null;
        }
        object5 = object2;
        if (object2 == null) {
            object5 = SocketFetcher.createSocket(inetAddress, n2, (String)object, n, n3, null, bl);
        }
        n = n4;
        if (string2 != null) {
            try {
                n = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                n = n4;
            }
        }
        if (n >= 0) {
            ((Socket)object5).setSoTimeout(n);
        }
        SocketFetcher.configureSSLSocket((Socket)object5, (Properties)object4, (String)object3);
        return object5;
    }

    private static SocketFactory getSocketFactory(String string2) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> class_ = null;
        if (string2 == null) return null;
        if (string2.length() == 0) {
            return null;
        }
        ClassLoader classLoader = SocketFetcher.getContextClassLoader();
        Class<?> class_2 = class_;
        if (classLoader != null) {
            try {
                class_2 = classLoader.loadClass(string2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                class_2 = class_;
            }
        }
        class_ = class_2;
        if (class_2 != null) return (SocketFactory)class_.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
        class_ = Class.forName(string2);
        return (SocketFactory)class_.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
    }

    public static Socket startTLS(Socket socket) throws IOException {
        return SocketFetcher.startTLS(socket, new Properties(), "socket");
    }

    public static Socket startTLS(Socket object, Properties properties, String object2) throws IOException {
        String string2 = ((Socket)object).getInetAddress().getHostName();
        int n = ((Socket)object).getPort();
        try {
            Object object3 = new StringBuilder(String.valueOf(object2));
            ((StringBuilder)object3).append(".socketFactory.class");
            object3 = SocketFetcher.getSocketFactory(properties.getProperty(((StringBuilder)object3).toString(), null));
            object3 = object3 != null && object3 instanceof SSLSocketFactory ? (SSLSocketFactory)object3 : (SSLSocketFactory)SSLSocketFactory.getDefault();
            object = ((SSLSocketFactory)object3).createSocket((Socket)object, string2, n, true);
            SocketFetcher.configureSSLSocket((Socket)object, properties, (String)object2);
            return object;
        }
        catch (Exception exception) {
            object = exception;
            if (exception instanceof InvocationTargetException) {
                object2 = ((InvocationTargetException)exception).getTargetException();
                object = exception;
                if (object2 instanceof Exception) {
                    object = (Exception)object2;
                }
            }
            if (object instanceof IOException) {
                throw (IOException)object;
            }
            Serializable serializable = new StringBuilder("Exception in startTLS: host ");
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append(", port ");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append("; Exception: ");
            ((StringBuilder)serializable).append(object);
            serializable = new IOException(((StringBuilder)serializable).toString());
            ((Throwable)serializable).initCause((Throwable)object);
            throw serializable;
        }
    }

    private static String[] stringArray(String object) {
        object = new StringTokenizer((String)object);
        ArrayList<String> arrayList = new ArrayList<String>();
        while (((StringTokenizer)object).hasMoreTokens()) {
            arrayList.add(((StringTokenizer)object).nextToken());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

}

