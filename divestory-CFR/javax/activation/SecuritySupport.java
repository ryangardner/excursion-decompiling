/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;

class SecuritySupport {
    private SecuritySupport() {
    }

    public static ClassLoader getContextClassLoader() {
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

    public static InputStream getResourceAsStream(Class object, final String string2) throws IOException {
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

    public static URL[] getResources(ClassLoader classLoader, final String string2) {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                URL[] arruRL;
                URL[] arruRL2 = arruRL = (URL[])null;
                try {
                    Object object;
                    arruRL2 = arruRL;
                    ArrayList<URL[]> arrayList = new ArrayList<URL[]>();
                    arruRL2 = arruRL;
                    Enumeration<URL> enumeration = ClassLoader.this.getResources(string2);
                    while (enumeration != null) {
                        arruRL2 = arruRL;
                        if (!enumeration.hasMoreElements()) break;
                        arruRL2 = arruRL;
                        object = enumeration.nextElement();
                        if (object == null) continue;
                        arruRL2 = arruRL;
                        arrayList.add((URL[])object);
                    }
                    object = arruRL;
                    arruRL2 = arruRL;
                    if (arrayList.size() <= 0) return object;
                    arruRL2 = arruRL;
                    arruRL2 = object = new URL[arrayList.size()];
                    return arrayList.toArray((T[])object);
                }
                catch (IOException | SecurityException exception) {
                    return arruRL2;
                }
            }
        });
    }

    public static URL[] getSystemResources(String string2) {
        return (URL[])AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                URL[] arruRL;
                URL[] arruRL2 = arruRL = (URL[])null;
                try {
                    Object object;
                    arruRL2 = arruRL;
                    ArrayList<URL[]> arrayList = new ArrayList<URL[]>();
                    arruRL2 = arruRL;
                    Enumeration<URL> enumeration = ClassLoader.getSystemResources(String.this);
                    while (enumeration != null) {
                        arruRL2 = arruRL;
                        if (!enumeration.hasMoreElements()) break;
                        arruRL2 = arruRL;
                        object = enumeration.nextElement();
                        if (object == null) continue;
                        arruRL2 = arruRL;
                        arrayList.add((URL[])object);
                    }
                    object = arruRL;
                    arruRL2 = arruRL;
                    if (arrayList.size() <= 0) return object;
                    arruRL2 = arruRL;
                    arruRL2 = object = new URL[arrayList.size()];
                    return arrayList.toArray((T[])object);
                }
                catch (IOException | SecurityException exception) {
                    return arruRL2;
                }
            }
        });
    }

    public static InputStream openStream(URL object) throws IOException {
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

}

