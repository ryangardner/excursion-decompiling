/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.net.ssl.SSLSocket;

public class SSLSocketUtils {
    private SSLSocketUtils() {
    }

    public static boolean enableEndpointNameVerification(SSLSocket sSLSocket) {
        try {
            GenericDeclaration genericDeclaration = Class.forName("javax.net.ssl.SSLParameters");
            Method method = ((Class)genericDeclaration).getDeclaredMethod("setEndpointIdentificationAlgorithm", String.class);
            Object object = SSLSocket.class.getDeclaredMethod("getSSLParameters", new Class[0]);
            genericDeclaration = SSLSocket.class.getDeclaredMethod("setSSLParameters", new Class[]{genericDeclaration});
            if (method == null) return false;
            if (object == null) return false;
            if (genericDeclaration == null) return false;
            if ((object = ((Method)object).invoke(sSLSocket, new Object[0])) == null) return false;
            method.invoke(object, "HTTPS");
            ((Method)genericDeclaration).invoke(sSLSocket, object);
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException exception) {
            return false;
        }
    }
}

