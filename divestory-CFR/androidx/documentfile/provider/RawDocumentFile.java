/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 *  android.webkit.MimeTypeMap
 */
package androidx.documentfile.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.documentfile.provider.DocumentFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile
extends DocumentFile {
    private File mFile;

    RawDocumentFile(DocumentFile documentFile, File file) {
        super(documentFile);
        this.mFile = file;
    }

    private static boolean deleteContents(File arrfile) {
        arrfile = arrfile.listFiles();
        boolean bl = true;
        boolean bl2 = true;
        if (arrfile == null) return bl;
        int n = arrfile.length;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) return bl;
            File file = arrfile[n2];
            bl = bl2;
            if (file.isDirectory()) {
                bl = bl2 & RawDocumentFile.deleteContents(file);
            }
            bl2 = bl;
            if (!file.delete()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to delete ");
                stringBuilder.append(file);
                Log.w((String)"DocumentFile", (String)stringBuilder.toString());
                bl2 = false;
            }
            ++n2;
        } while (true);
    }

    private static String getTypeForName(String string2) {
        int n = string2.lastIndexOf(46);
        if (n < 0) return "application/octet-stream";
        string2 = string2.substring(n + 1).toLowerCase();
        string2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string2);
        if (string2 == null) return "application/octet-stream";
        return string2;
    }

    @Override
    public boolean canRead() {
        return this.mFile.canRead();
    }

    @Override
    public boolean canWrite() {
        return this.mFile.canWrite();
    }

    @Override
    public DocumentFile createDirectory(String object) {
        if (((File)(object = new File(this.mFile, (String)object))).isDirectory()) return new RawDocumentFile(this, (File)object);
        if (!((File)object).mkdir()) return null;
        return new RawDocumentFile(this, (File)object);
    }

    @Override
    public DocumentFile createFile(String object, String string2) {
        String string3 = MimeTypeMap.getSingleton().getExtensionFromMimeType((String)object);
        object = string2;
        if (string3 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(".");
            ((StringBuilder)object).append(string3);
            object = ((StringBuilder)object).toString();
        }
        object = new File(this.mFile, (String)object);
        try {
            ((File)object).createNewFile();
            return new RawDocumentFile(this, (File)object);
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to createFile: ");
            ((StringBuilder)object).append(iOException);
            Log.w((String)"DocumentFile", (String)((StringBuilder)object).toString());
            return null;
        }
    }

    @Override
    public boolean delete() {
        RawDocumentFile.deleteContents(this.mFile);
        return this.mFile.delete();
    }

    @Override
    public boolean exists() {
        return this.mFile.exists();
    }

    @Override
    public String getName() {
        return this.mFile.getName();
    }

    @Override
    public String getType() {
        if (!this.mFile.isDirectory()) return RawDocumentFile.getTypeForName(this.mFile.getName());
        return null;
    }

    @Override
    public Uri getUri() {
        return Uri.fromFile((File)this.mFile);
    }

    @Override
    public boolean isDirectory() {
        return this.mFile.isDirectory();
    }

    @Override
    public boolean isFile() {
        return this.mFile.isFile();
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public long lastModified() {
        return this.mFile.lastModified();
    }

    @Override
    public long length() {
        return this.mFile.length();
    }

    @Override
    public DocumentFile[] listFiles() {
        ArrayList<RawDocumentFile> arrayList = new ArrayList<RawDocumentFile>();
        File[] arrfile = this.mFile.listFiles();
        if (arrfile == null) return arrayList.toArray(new DocumentFile[arrayList.size()]);
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(new RawDocumentFile(this, arrfile[n2]));
            ++n2;
        }
        return arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    @Override
    public boolean renameTo(String object) {
        object = new File(this.mFile.getParentFile(), (String)object);
        if (!this.mFile.renameTo((File)object)) return false;
        this.mFile = object;
        return true;
    }
}

