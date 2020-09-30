/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package androidx.core.util;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    public AtomicFile(File file) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
    }

    private static boolean sync(FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.getFD().sync();
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) return;
        AtomicFile.sync(fileOutputStream);
        try {
            fileOutputStream.close();
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"AtomicFile", (String)"failWrite: Got exception:", (Throwable)iOException);
        }
    }

    public void finishWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) return;
        AtomicFile.sync(fileOutputStream);
        try {
            fileOutputStream.close();
            this.mBackupName.delete();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"AtomicFile", (String)"finishWrite: Got exception:", (Throwable)iOException);
        }
    }

    public File getBaseFile() {
        return this.mBaseName;
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (!this.mBackupName.exists()) return new FileInputStream(this.mBaseName);
        this.mBaseName.delete();
        this.mBackupName.renameTo(this.mBaseName);
        return new FileInputStream(this.mBaseName);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public byte[] readFully() throws IOException {
        var1_1 = this.openRead();
        var2_2 = new byte[var1_1.available()];
        var3_4 = 0;
        ** while ((var4_5 = var1_1.read((byte[])var2_2, (int)var3_4, (int)(var2_2.length - var3_4))) > 0)
        {
            catch (Throwable var2_3) {
                var1_1.close();
                throw var2_3;
            }
lbl9: // 1 sources:
            var4_5 = var3_4 + var4_5;
            var5_6 = var1_1.available();
            var3_4 = var4_5;
            if (var5_6 <= var2_2.length - var4_5) continue;
            var6_7 = new byte[var5_6 + var4_5];
            System.arraycopy(var2_2, 0, var6_7, 0, var4_5);
            var2_2 = var6_7;
            var3_4 = var4_5;
            continue;
        }
lbl19: // 1 sources:
        var1_1.close();
        return var2_2;
    }

    public FileOutputStream startWrite() throws IOException {
        block8 : {
            if (this.mBaseName.exists()) {
                if (!this.mBackupName.exists()) {
                    if (!this.mBaseName.renameTo(this.mBackupName)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Couldn't rename file ");
                        stringBuilder.append(this.mBaseName);
                        stringBuilder.append(" to backup file ");
                        stringBuilder.append(this.mBackupName);
                        Log.w((String)"AtomicFile", (String)stringBuilder.toString());
                    }
                } else {
                    this.mBaseName.delete();
                }
            }
            try {
                return new FileOutputStream(this.mBaseName);
            }
            catch (FileNotFoundException fileNotFoundException) {
                if (!this.mBaseName.getParentFile().mkdirs()) break block8;
                try {
                    return new FileOutputStream(this.mBaseName);
                }
                catch (FileNotFoundException fileNotFoundException2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't create ");
                    stringBuilder.append(this.mBaseName);
                    throw new IOException(stringBuilder.toString());
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't create directory ");
        stringBuilder.append(this.mBaseName);
        throw new IOException(stringBuilder.toString());
    }
}

