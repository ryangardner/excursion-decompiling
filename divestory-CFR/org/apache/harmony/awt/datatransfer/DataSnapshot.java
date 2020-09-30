/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public class DataSnapshot
implements DataProvider {
    private final String[] fileList;
    private final String html;
    private final String[] nativeFormats;
    private final RawBitmap rawBitmap;
    private final Map<Class<?>, byte[]> serializedObjects;
    private final String text;
    private final String url;

    public DataSnapshot(DataProvider dataProvider) {
        this.nativeFormats = dataProvider.getNativeFormats();
        this.text = dataProvider.getText();
        this.fileList = dataProvider.getFileList();
        this.url = dataProvider.getURL();
        this.html = dataProvider.getHTML();
        this.rawBitmap = dataProvider.getRawBitmap();
        this.serializedObjects = Collections.synchronizedMap(new HashMap());
        int n = 0;
        Object object;
        while (n < ((String[])(object = this.nativeFormats)).length) {
            Object object2 = null;
            try {
                object2 = object = SystemFlavorMap.decodeDataFlavor(object[n]);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            if (object2 != null) {
                object = object2.getRepresentationClass();
                object2 = dataProvider.getSerializedObject((Class<?>)object);
                if (object2 != null) {
                    this.serializedObjects.put((Class<?>)object, (byte[])object2);
                }
            }
            ++n;
        }
        return;
    }

    @Override
    public String[] getFileList() {
        return this.fileList;
    }

    @Override
    public String getHTML() {
        return this.html;
    }

    @Override
    public String[] getNativeFormats() {
        return this.nativeFormats;
    }

    @Override
    public RawBitmap getRawBitmap() {
        return this.rawBitmap;
    }

    public short[] getRawBitmapBuffer16() {
        short[] arrs = this.rawBitmap;
        if (arrs == null) return null;
        if (!(arrs.buffer instanceof short[])) return null;
        return (short[])this.rawBitmap.buffer;
    }

    public int[] getRawBitmapBuffer32() {
        int[] arrn = this.rawBitmap;
        if (arrn == null) return null;
        if (!(arrn.buffer instanceof int[])) return null;
        return (int[])this.rawBitmap.buffer;
    }

    public byte[] getRawBitmapBuffer8() {
        byte[] arrby = this.rawBitmap;
        if (arrby == null) return null;
        if (!(arrby.buffer instanceof byte[])) return null;
        return (byte[])this.rawBitmap.buffer;
    }

    public int[] getRawBitmapHeader() {
        int[] arrn = this.rawBitmap;
        if (arrn == null) return null;
        return arrn.getHeader();
    }

    @Override
    public byte[] getSerializedObject(Class<?> class_) {
        return this.serializedObjects.get(class_);
    }

    public byte[] getSerializedObject(String arrby) {
        try {
            return this.getSerializedObject(SystemFlavorMap.decodeDataFlavor((String)arrby).getRepresentationClass());
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public boolean isNativeFormatAvailable(String object) {
        if (object == null) {
            return false;
        }
        if (((String)object).equals("text/plain")) {
            if (this.text == null) return false;
            return true;
        }
        if (((String)object).equals("application/x-java-file-list")) {
            if (this.fileList == null) return false;
            return true;
        }
        if (((String)object).equals("application/x-java-url")) {
            if (this.url == null) return false;
            return true;
        }
        if (((String)object).equals("text/html")) {
            if (this.html == null) return false;
            return true;
        }
        if (((String)object).equals("image/x-java-image")) {
            if (this.rawBitmap == null) return false;
            return true;
        }
        try {
            object = SystemFlavorMap.decodeDataFlavor((String)object);
            return this.serializedObjects.containsKey(((DataFlavor)object).getRepresentationClass());
        }
        catch (Exception exception) {
            return false;
        }
    }
}

