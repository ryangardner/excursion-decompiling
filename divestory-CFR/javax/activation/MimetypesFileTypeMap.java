/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;
import javax.activation.FileTypeMap;
import javax.activation.SecuritySupport;

public class MimetypesFileTypeMap
extends FileTypeMap {
    private static final int PROG = 0;
    private static MimeTypeFile defDB;
    private static String defaultType = "application/octet-stream";
    private MimeTypeFile[] DB;

    /*
     * Enabled unnecessary exception pruning
     */
    public MimetypesFileTypeMap() {
        Object[] arrobject;
        Vector<Object[]> vector = new Vector<Object[]>(5);
        vector.addElement(null);
        LogSupport.log("MimetypesFileTypeMap: load HOME");
        try {
            arrobject = System.getProperty("user.home");
            if (arrobject != null) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(arrobject));
                stringBuilder.append(File.separator);
                stringBuilder.append(".mime.types");
                arrobject = this.loadFile(stringBuilder.toString());
                if (arrobject != null) {
                    vector.addElement(arrobject);
                }
            }
        }
        catch (SecurityException securityException) {}
        LogSupport.log("MimetypesFileTypeMap: load SYS");
        try {
            super(String.valueOf(System.getProperty("java.home")));
            arrobject.append(File.separator);
            arrobject.append("lib");
            arrobject.append(File.separator);
            arrobject.append("mime.types");
            arrobject = this.loadFile(arrobject.toString());
            if (arrobject != null) {
                vector.addElement(arrobject);
            }
        }
        catch (SecurityException securityException) {}
        LogSupport.log("MimetypesFileTypeMap: load JAR");
        this.loadAllResources(vector, "mime.types");
        LogSupport.log("MimetypesFileTypeMap: load DEF");
        synchronized (MimetypesFileTypeMap.class) {
            if (defDB == null) {
                defDB = this.loadResource("/mimetypes.default");
            }
        }
        arrobject = defDB;
        if (arrobject != null) {
            vector.addElement(arrobject);
        }
        arrobject = new MimeTypeFile[vector.size()];
        this.DB = arrobject;
        vector.copyInto(arrobject);
    }

    public MimetypesFileTypeMap(InputStream inputStream2) {
        this();
        try {
            MimeTypeFile mimeTypeFile;
            MimeTypeFile[] arrmimeTypeFile = this.DB;
            arrmimeTypeFile[0] = mimeTypeFile = new MimeTypeFile(inputStream2);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public MimetypesFileTypeMap(String string2) throws IOException {
        this();
        this.DB[0] = new MimeTypeFile(string2);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void loadAllResources(Vector var1_1, String var2_2) {
        block32 : {
            var3_3 = false;
            var4_4 = false;
            var5_5 = 0;
            try {
                var7_8 = var6_6 = SecuritySupport.getContextClassLoader();
                if (var6_6 == null) {
                    var7_8 = this.getClass().getClassLoader();
                }
                if ((var8_15 = var7_8 != null ? SecuritySupport.getResources((ClassLoader)var7_8, (String)var2_2) : SecuritySupport.getSystemResources((String)var2_2)) == null) break block32;
                if (LogSupport.isLoggable()) {
                    LogSupport.log("MimetypesFileTypeMap: getResources");
                }
                var3_3 = false;
            }
            catch (Exception var7_11) {
                // empty catch block
                ** GOTO lbl177
            }
            do {
                block40 : {
                    block38 : {
                        block35 : {
                            block37 : {
                                block36 : {
                                    block34 : {
                                        block33 : {
                                            var9_16 = var3_3;
                                            if (var5_5 >= var8_15.length) {
                                                var4_4 = var3_3;
                                                break;
                                            }
                                            var10_17 = var8_15[var5_5];
                                            var11_18 = null;
                                            var12_26 = null;
                                            var6_6 = null;
                                            var9_16 = var3_3;
                                            if (LogSupport.isLoggable()) {
                                                var9_16 = var3_3;
                                                var9_16 = var3_3;
                                                var7_8 = new StringBuilder("MimetypesFileTypeMap: URL ");
                                                var9_16 = var3_3;
                                                var7_8.append(var10_17);
                                                var9_16 = var3_3;
                                                LogSupport.log(var7_8.toString());
                                            }
                                            var9_16 = true;
                                            var4_4 = true;
                                            var7_8 = SecuritySupport.openStream(var10_17);
                                            if (var7_8 == null) break block33;
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var13_27 = new MimeTypeFile((InputStream)var7_8);
                                            var6_6 = var7_8;
                                            var11_18 = var7_8;
                                            var12_26 = var7_8;
                                            var1_1.addElement(var13_27);
                                            try {
                                                if (LogSupport.isLoggable()) {
                                                    var6_6 = new StringBuilder("MimetypesFileTypeMap: successfully loaded mime types from URL: ");
                                                    var6_6.append(var10_17);
                                                    LogSupport.log(var6_6.toString());
                                                }
                                                var4_4 = true;
                                                break block34;
                                            }
                                            catch (Throwable var11_19) {
                                                var3_3 = true;
                                                break block35;
                                            }
                                            catch (SecurityException var11_20) {
                                                var3_3 = var4_4;
                                                break block36;
                                            }
                                            catch (IOException var11_21) {
                                                var3_3 = var9_16;
                                                ** GOTO lbl130
                                            }
                                        }
                                        var4_4 = var3_3;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        if (!LogSupport.isLoggable()) break block34;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var13_27 = new StringBuilder("MimetypesFileTypeMap: not loading mime types from URL: ");
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        var13_27.append(var10_17);
                                        var6_6 = var7_8;
                                        var11_18 = var7_8;
                                        var12_26 = var7_8;
                                        LogSupport.log(var13_27.toString());
                                        var4_4 = var3_3;
                                    }
                                    var3_3 = var4_4;
                                    if (var7_8 == null) break block40;
                                    var9_16 = var4_4;
                                    var7_8.close();
                                    var3_3 = var4_4;
                                    catch (Throwable var11_22) {
                                        var7_8 = var6_6;
                                        break block35;
                                    }
                                    catch (SecurityException var6_7) {
                                        var7_8 = var11_18;
                                        var11_18 = var6_7;
                                    }
                                }
                                var6_6 = var7_8;
                                var4_4 = var3_3;
                                if (LogSupport.isLoggable()) {
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26 = new StringBuilder("MimetypesFileTypeMap: can't load ");
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26.append(var10_17);
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    LogSupport.log(var12_26.toString(), (Throwable)var11_18);
                                }
                                var4_4 = var3_3;
                                if (var7_8 != null) {
                                    break block37;
                                }
                                ** GOTO lbl172
                                catch (IOException var11_23) {
                                    var7_8 = var12_26;
                                }
lbl130: // 2 sources:
                                var6_6 = var7_8;
                                var4_4 = var3_3;
                                if (LogSupport.isLoggable()) {
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26 = new StringBuilder("MimetypesFileTypeMap: can't load ");
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    var12_26.append(var10_17);
                                    var6_6 = var7_8;
                                    var4_4 = var3_3;
                                    LogSupport.log(var12_26.toString(), (Throwable)var11_18);
                                }
                                var4_4 = var3_3;
                                if (var7_8 == null) ** GOTO lbl172
                            }
                            try {
                                var7_8.close();
                                var4_4 = var3_3;
                                ** GOTO lbl172
                            }
                            catch (Exception var7_9) {
                                break block38;
                            }
                            {
                            }
                            catch (Throwable var11_24) {
                                var3_3 = var4_4;
                                var7_8 = var6_6;
                            }
                        }
                        if (var7_8 == null) ** GOTO lbl169
                        var9_16 = var3_3;
                        try {
                            block41 : {
                                block39 : {
                                    try {
                                        var7_8.close();
                                        break block39;
                                    }
                                    catch (IOException var7_14) {}
                                    catch (IOException var7_13) {
                                        var4_4 = var3_3;
                                    }
                                    break block41;
                                }
                                var9_16 = var3_3;
                                throw var11_25;
                            }
                            var3_3 = var4_4;
                            break block40;
                        }
                        catch (Exception var7_10) {
                            var3_3 = var9_16;
                        }
                    }
                    var4_4 = var3_3;
                    if (!LogSupport.isLoggable()) break;
                    var6_6 = new StringBuilder("MimetypesFileTypeMap: can't load ");
                    var6_6.append((String)var2_2);
                    LogSupport.log(var6_6.toString(), (Throwable)var7_8);
                    var4_4 = var3_3;
                    break;
                    catch (IOException var7_12) {
                        var3_3 = var4_4;
                    }
                }
                ++var5_5;
            } while (true);
        }
        if (var4_4 != false) return;
        LogSupport.log("MimetypesFileTypeMap: !anyLoaded");
        var7_8 = new StringBuilder("/");
        var7_8.append((String)var2_2);
        var2_2 = this.loadResource(var7_8.toString());
        if (var2_2 == null) return;
        var1_1.addElement(var2_2);
    }

    private MimeTypeFile loadFile(String object) {
        try {
            MimeTypeFile mimeTypeFile = new MimeTypeFile((String)object);
            return mimeTypeFile;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private MimeTypeFile loadResource(String var1_1) {
        block23 : {
            block21 : {
                block24 : {
                    block22 : {
                        block20 : {
                            var2_7 = null;
                            var3_9 = SecuritySupport.getResourceAsStream(this.getClass(), var1_1);
                            if (var3_9 == null) break block20;
                            var2_7 = var3_9;
                            var2_7 = var3_9;
                            var4_10 = new MimeTypeFile(var3_9);
                            var2_7 = var3_9;
                            if (LogSupport.isLoggable()) {
                                var2_7 = var3_9;
                                var2_7 = var3_9;
                                var5_18 = new StringBuilder("MimetypesFileTypeMap: successfully loaded mime types file: ");
                                var2_7 = var3_9;
                                var5_18.append(var1_1);
                                var2_7 = var3_9;
                                LogSupport.log(var5_18.toString());
                            }
                            if (var3_9 == null) return var4_10;
                            try {
                                var3_9.close();
                                return var4_10;
                            }
                            catch (IOException var1_5) {
                                return var4_10;
                            }
                        }
                        var2_7 = var3_9;
                        try {
                            if (LogSupport.isLoggable()) {
                                var2_7 = var3_9;
                                var2_7 = var3_9;
                                var4_11 = new StringBuilder("MimetypesFileTypeMap: not loading mime types file: ");
                                var2_7 = var3_9;
                                var4_11.append(var1_1);
                                var2_7 = var3_9;
                                LogSupport.log(var4_11.toString());
                            }
                            if (var3_9 == null) return null;
                            break block21;
                        }
                        catch (SecurityException var4_12) {
                            break block22;
                        }
                        catch (IOException var4_13) {
                            ** GOTO lbl62
                        }
                        catch (Throwable var1_2) {
                            break block23;
                        }
                        catch (SecurityException var4_14) {
                            var3_9 = null;
                        }
                    }
                    var2_7 = var3_9;
                    if (LogSupport.isLoggable()) {
                        var2_7 = var3_9;
                        var2_7 = var3_9;
                        var5_19 = new StringBuilder("MimetypesFileTypeMap: can't load ");
                        var2_7 = var3_9;
                        var5_19.append(var1_1);
                        var2_7 = var3_9;
                        LogSupport.log(var5_19.toString(), (Throwable)var4_15);
                    }
                    break block24;
                    catch (IOException var4_16) {
                        var3_9 = null;
                    }
lbl62: // 2 sources:
                    var2_7 = var3_9;
                    if (LogSupport.isLoggable()) {
                        var2_7 = var3_9;
                        var2_7 = var3_9;
                        var5_20 = new StringBuilder("MimetypesFileTypeMap: can't load ");
                        var2_7 = var3_9;
                        var5_20.append(var1_1);
                        var2_7 = var3_9;
                        LogSupport.log(var5_20.toString(), (Throwable)var4_17);
                    }
                    if (var3_9 == null) return null;
                }
                if (var3_9 == null) return null;
            }
            try {
                var3_9.close();
                return null;
            }
            catch (IOException var1_6) {
                return null;
            }
            catch (Throwable var1_3) {
                // empty catch block
            }
        }
        if (var2_7 == null) throw var1_4;
        try {
            var2_7.close();
        }
        catch (IOException var2_8) {
            throw var1_4;
        }
        throw var1_4;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public void addMimeTypes(String string2) {
        synchronized (this) {
            if (this.DB[0] == null) {
                MimeTypeFile mimeTypeFile;
                MimeTypeFile[] arrmimeTypeFile = this.DB;
                arrmimeTypeFile[0] = mimeTypeFile = new MimeTypeFile();
            }
            this.DB[0].appendToRegistry(string2);
            return;
        }
    }

    @Override
    public String getContentType(File file) {
        return this.getContentType(file.getName());
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public String getContentType(String string2) {
        synchronized (this) {
            int n = string2.lastIndexOf(".");
            if (n < 0) {
                return defaultType;
            }
            String string3 = string2.substring(n + 1);
            if (string3.length() == 0) {
                return defaultType;
            }
            n = 0;
            while (n < this.DB.length) {
                if (this.DB[n] != null && (string2 = this.DB[n].getMIMETypeString(string3)) != null) {
                    return string2;
                }
                ++n;
            }
            return defaultType;
        }
    }
}

