/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class VersionInfo {
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    private final String infoClassloader;
    private final String infoModule;
    private final String infoPackage;
    private final String infoRelease;
    private final String infoTimestamp;

    protected VersionInfo(String string2, String string3, String string4, String string5, String string6) {
        if (string2 == null) throw new IllegalArgumentException("Package identifier must not be null.");
        this.infoPackage = string2;
        if (string3 == null) {
            string3 = UNAVAILABLE;
        }
        this.infoModule = string3;
        if (string4 == null) {
            string4 = UNAVAILABLE;
        }
        this.infoRelease = string4;
        if (string5 == null) {
            string5 = UNAVAILABLE;
        }
        this.infoTimestamp = string5;
        if (string6 == null) {
            string6 = UNAVAILABLE;
        }
        this.infoClassloader = string6;
    }

    protected static final VersionInfo fromMap(String string2, Map object, ClassLoader classLoader) {
        Object object2;
        Object object3;
        Object object4;
        Object var3_3;
        block9 : {
            block6 : {
                block7 : {
                    block8 : {
                        if (string2 == null) throw new IllegalArgumentException("Package identifier must not be null.");
                        var3_3 = null;
                        if (object == null) break block6;
                        object4 = object2 = (String)object.get(PROPERTY_MODULE);
                        if (object2 != null) {
                            object4 = object2;
                            if (((String)object2).length() < 1) {
                                object4 = null;
                            }
                        }
                        object2 = object3 = (String)object.get(PROPERTY_RELEASE);
                        if (object3 == null) break block7;
                        if (((String)object3).length() < 1) break block8;
                        object2 = object3;
                        if (!((String)object3).equals("${pom.version}")) break block7;
                    }
                    object2 = null;
                }
                if ((object = (String)object.get(PROPERTY_TIMESTAMP)) != null && (((String)object).length() < 1 || ((String)object).equals("${mvn.timestamp}"))) {
                    object = null;
                }
                object3 = object4;
                object4 = object2;
                object2 = object3;
                object3 = object;
                break block9;
            }
            object3 = object = (object2 = null);
            object4 = object;
        }
        object = var3_3;
        if (classLoader == null) return new VersionInfo(string2, (String)object2, (String)object4, (String)object3, (String)object);
        object = classLoader.toString();
        return new VersionInfo(string2, (String)object2, (String)object4, (String)object3, (String)object);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static final VersionInfo loadVersionInfo(String string2, ClassLoader object) {
        VersionInfo versionInfo;
        Object classLoader;
        block9 : {
            block8 : {
                if (string2 == null) throw new IllegalArgumentException("Package identifier must not be null.");
                classLoader = object;
                if (object == null) {
                    classLoader = Thread.currentThread().getContextClassLoader();
                }
                versionInfo = null;
                object = new StringBuffer();
                ((StringBuffer)object).append(string2.replace('.', '/'));
                ((StringBuffer)object).append("/");
                ((StringBuffer)object).append(VERSION_PROPERTY_FILE);
                InputStream inputStream2 = ((ClassLoader)classLoader).getResourceAsStream(((StringBuffer)object).toString());
                if (inputStream2 == null) break block8;
                {
                    catch (IOException iOException) {}
                }
                object = new Properties();
                ((Properties)object).load(inputStream2);
                try {
                    inputStream2.close();
                }
                catch (IOException iOException) {}
                break block9;
                catch (Throwable throwable) {
                    inputStream2.close();
                    throw throwable;
                }
            }
            object = null;
        }
        if (object == null) return versionInfo;
        return VersionInfo.fromMap(string2, (Map)object, (ClassLoader)classLoader);
    }

    public static final VersionInfo[] loadVersionInfo(String[] arrstring, ClassLoader classLoader) {
        if (arrstring == null) throw new IllegalArgumentException("Package identifier list must not be null.");
        ArrayList<VersionInfo> arrayList = new ArrayList<VersionInfo>(arrstring.length);
        int n = 0;
        while (n < arrstring.length) {
            VersionInfo versionInfo = VersionInfo.loadVersionInfo(arrstring[n], classLoader);
            if (versionInfo != null) {
                arrayList.add(versionInfo);
            }
            ++n;
        }
        return arrayList.toArray(new VersionInfo[arrayList.size()]);
    }

    public final String getClassloader() {
        return this.infoClassloader;
    }

    public final String getModule() {
        return this.infoModule;
    }

    public final String getPackage() {
        return this.infoPackage;
    }

    public final String getRelease() {
        return this.infoRelease;
    }

    public final String getTimestamp() {
        return this.infoTimestamp;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.infoPackage.length() + 20 + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        stringBuffer.append("VersionInfo(");
        stringBuffer.append(this.infoPackage);
        stringBuffer.append(':');
        stringBuffer.append(this.infoModule);
        if (!UNAVAILABLE.equals(this.infoRelease)) {
            stringBuffer.append(':');
            stringBuffer.append(this.infoRelease);
        }
        if (!UNAVAILABLE.equals(this.infoTimestamp)) {
            stringBuffer.append(':');
            stringBuffer.append(this.infoTimestamp);
        }
        stringBuffer.append(')');
        if (UNAVAILABLE.equals(this.infoClassloader)) return stringBuffer.toString();
        stringBuffer.append('@');
        stringBuffer.append(this.infoClassloader);
        return stringBuffer.toString();
    }
}

