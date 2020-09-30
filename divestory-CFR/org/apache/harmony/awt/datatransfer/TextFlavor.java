/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class TextFlavor {
    public static final Class[] charsetTextClasses;
    public static final Class[] unicodeTextClasses;

    static {
        unicodeTextClasses = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};
        charsetTextClasses = new Class[]{InputStream.class, ByteBuffer.class, byte[].class};
    }

    public static void addCharsetClasses(SystemFlavorMap systemFlavorMap, String string2, String string3, String string4) {
        int n = 0;
        while (n < charsetTextClasses.length) {
            Object object = new StringBuilder("text/");
            ((StringBuilder)object).append(string3);
            object = ((StringBuilder)object).toString();
            CharSequence charSequence = new StringBuilder(";class=\"");
            charSequence.append(charsetTextClasses[n].getName());
            charSequence.append("\"");
            charSequence.append(";charset=\"");
            charSequence.append(string4);
            charSequence.append("\"");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(object));
            stringBuilder.append((String)charSequence);
            object = new DataFlavor(stringBuilder.toString(), (String)object);
            systemFlavorMap.addFlavorForUnencodedNative(string2, (DataFlavor)object);
            systemFlavorMap.addUnencodedNativeForFlavor((DataFlavor)object, string2);
            ++n;
        }
        return;
    }

    public static void addUnicodeClasses(SystemFlavorMap systemFlavorMap, String string2, String string3) {
        int n = 0;
        while (n < unicodeTextClasses.length) {
            Object object = new StringBuilder("text/");
            ((StringBuilder)object).append(string3);
            object = ((StringBuilder)object).toString();
            CharSequence charSequence = new StringBuilder(";class=\"");
            charSequence.append(unicodeTextClasses[n].getName());
            charSequence.append("\"");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(object));
            stringBuilder.append((String)charSequence);
            object = new DataFlavor(stringBuilder.toString(), (String)object);
            systemFlavorMap.addFlavorForUnencodedNative(string2, (DataFlavor)object);
            systemFlavorMap.addUnencodedNativeForFlavor((DataFlavor)object, string2);
            ++n;
        }
        return;
    }
}

