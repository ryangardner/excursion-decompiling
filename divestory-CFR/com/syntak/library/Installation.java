/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.syntak.library;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {
    private static final String INSTALLATION = "INSTALLATION";
    private static String sID;

    public static String id(Context object) {
        synchronized (Installation.class) {
            if (sID == null) {
                File file = new File(object.getFilesDir(), INSTALLATION);
                try {
                    if (!file.exists()) {
                        Installation.writeInstallationFile(file);
                    }
                    sID = Installation.readInstallationFile(file);
                }
                catch (Exception exception) {
                    object = new RuntimeException(exception);
                    throw object;
                }
            }
            object = sID;
            return object;
        }
    }

    private static String readInstallationFile(File arrby) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile((File)arrby, "r");
        arrby = new byte[(int)randomAccessFile.length()];
        randomAccessFile.readFully(arrby);
        randomAccessFile.close();
        return new String(arrby);
    }

    private static void writeInstallationFile(File object) throws IOException {
        object = new FileOutputStream((File)object);
        ((FileOutputStream)object).write(UUID.randomUUID().toString().getBytes());
        ((FileOutputStream)object).close();
    }
}

