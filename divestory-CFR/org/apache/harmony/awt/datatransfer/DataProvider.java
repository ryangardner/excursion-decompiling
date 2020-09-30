/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public interface DataProvider {
    public static final String FORMAT_FILE_LIST = "application/x-java-file-list";
    public static final String FORMAT_HTML = "text/html";
    public static final String FORMAT_IMAGE = "image/x-java-image";
    public static final String FORMAT_TEXT = "text/plain";
    public static final String FORMAT_URL = "application/x-java-url";
    public static final String TYPE_FILELIST = "application/x-java-file-list";
    public static final String TYPE_HTML = "text/html";
    public static final String TYPE_IMAGE = "image/x-java-image";
    public static final String TYPE_PLAINTEXT = "text/plain";
    public static final String TYPE_SERIALIZED = "application/x-java-serialized-object";
    public static final String TYPE_TEXTENCODING = "application/x-java-text-encoding";
    public static final String TYPE_URILIST = "text/uri-list";
    public static final String TYPE_URL = "application/x-java-url";
    public static final DataFlavor uriFlavor;
    public static final DataFlavor urlFlavor;

    static {
        urlFlavor = new DataFlavor("application/x-java-url;class=java.net.URL", "URL");
        uriFlavor = new DataFlavor(TYPE_URILIST, "URI");
    }

    public String[] getFileList();

    public String getHTML();

    public String[] getNativeFormats();

    public RawBitmap getRawBitmap();

    public byte[] getSerializedObject(Class<?> var1);

    public String getText();

    public String getURL();

    public boolean isNativeFormatAvailable(String var1);
}

