/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

public abstract class FileTypeMap {
    private static FileTypeMap defaultMap;

    public static FileTypeMap getDefaultFileTypeMap() {
        if (defaultMap != null) return defaultMap;
        defaultMap = new MimetypesFileTypeMap();
        return defaultMap;
    }

    public static void setDefaultFileTypeMap(FileTypeMap fileTypeMap) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkSetFactory();
            }
            catch (SecurityException securityException) {
                if (FileTypeMap.class.getClassLoader() != fileTypeMap.getClass().getClassLoader()) throw securityException;
            }
        }
        defaultMap = fileTypeMap;
    }

    public abstract String getContentType(File var1);

    public abstract String getContentType(String var1);
}

