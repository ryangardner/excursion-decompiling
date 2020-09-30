/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
    private static final Pattern V_SEP = Pattern.compile("[-_./;:]");

    protected VersionUtil() {
    }

    private static final void _close(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @Deprecated
    public static Version mavenVersionFor(ClassLoader object, String object2, String string2) {
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("META-INF/maven/");
        ((StringBuilder)serializable).append(((String)object2).replaceAll("\\.", "/"));
        ((StringBuilder)serializable).append("/");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append("/pom.properties");
        object = ((ClassLoader)object).getResourceAsStream(((StringBuilder)serializable).toString());
        if (object == null) return Version.unknownVersion();
        try {
            serializable = new Properties();
            ((Properties)serializable).load((InputStream)object);
            object2 = ((Properties)serializable).getProperty("version");
            string2 = ((Properties)serializable).getProperty("artifactId");
            object2 = VersionUtil.parseVersion((String)object2, ((Properties)serializable).getProperty("groupId"), string2);
            return object2;
        }
        catch (IOException iOException) {
            VersionUtil._close((Closeable)object);
            return Version.unknownVersion();
        }
        finally {
            VersionUtil._close((Closeable)object);
        }
    }

    public static Version packageVersionFor(Class<?> serializable) {
        Serializable serializable2 = new StringBuilder();
        ((StringBuilder)serializable2).append(serializable.getPackage().getName());
        ((StringBuilder)serializable2).append(".PackageVersion");
        serializable2 = Class.forName(((StringBuilder)serializable2).toString(), true, serializable.getClassLoader());
        try {
            serializable = ((Versioned)((Class)serializable2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).version();
        }
        catch (Exception exception) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to get Versioned out of ");
                stringBuilder.append(serializable2);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            catch (Exception exception2) {
                serializable = null;
            }
        }
        serializable2 = serializable;
        if (serializable != null) return serializable2;
        return Version.unknownVersion();
    }

    public static Version parseVersion(String object, String string2, String string3) {
        if (object == null) return Version.unknownVersion();
        if ((object = object.trim()).length() <= 0) return Version.unknownVersion();
        object = V_SEP.split((CharSequence)object);
        int n = VersionUtil.parseVersionPart(object[0]);
        int n2 = ((String[])object).length > 1 ? VersionUtil.parseVersionPart(object[1]) : 0;
        int n3 = ((String[])object).length > 2 ? VersionUtil.parseVersionPart(object[2]) : 0;
        if (((String[])object).length > 3) {
            object = object[3];
            return new Version(n, n2, n3, (String)object, string2, string3);
        }
        object = null;
        return new Version(n, n2, n3, (String)object, string2, string3);
    }

    protected static int parseVersionPart(String string2) {
        int n = string2.length();
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c > '9') return n3;
            if (c < '0') {
                return n3;
            }
            n3 = n3 * 10 + (c - 48);
            ++n2;
        }
        return n3;
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    public static Version versionFor(Class<?> serializable) {
        Version version = VersionUtil.packageVersionFor(serializable);
        serializable = version;
        if (version != null) return serializable;
        return Version.unknownVersion();
    }

    @Deprecated
    public Version version() {
        return Version.unknownVersion();
    }
}

