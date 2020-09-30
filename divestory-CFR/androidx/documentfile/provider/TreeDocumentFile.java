/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.DocumentsContract
 *  android.util.Log
 */
package androidx.documentfile.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import androidx.documentfile.provider.DocumentsContractApi19;
import java.util.ArrayList;

class TreeDocumentFile
extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.mContext = context;
        this.mUri = uri;
    }

    private static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable == null) return;
        try {
            autoCloseable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static Uri createFile(Context context, Uri uri, String string2, String string3) {
        try {
            return DocumentsContract.createDocument((ContentResolver)context.getContentResolver(), (Uri)uri, (String)string2, (String)string3);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    @Override
    public boolean canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    @Override
    public DocumentFile createDirectory(String object) {
        if ((object = TreeDocumentFile.createFile(this.mContext, this.mUri, "vnd.android.document/directory", (String)object)) == null) return null;
        return new TreeDocumentFile(this, this.mContext, (Uri)object);
    }

    @Override
    public DocumentFile createFile(String object, String string2) {
        if ((object = TreeDocumentFile.createFile(this.mContext, this.mUri, (String)object, string2)) == null) return null;
        return new TreeDocumentFile(this, this.mContext, (Uri)object);
    }

    @Override
    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument((ContentResolver)this.mContext.getContentResolver(), (Uri)this.mUri);
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    @Override
    public String getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    @Override
    public String getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    @Override
    public Uri getUri() {
        return this.mUri;
    }

    @Override
    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    @Override
    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    @Override
    public boolean isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
    }

    @Override
    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    @Override
    public long length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public DocumentFile[] listFiles() {
        var1_1 = this.mContext.getContentResolver();
        var2_2 = this.mUri;
        var3_4 /* !! */  = DocumentsContract.buildChildDocumentsUriUsingTree((Uri)var2_2, (String)DocumentsContract.getDocumentId((Uri)var2_2));
        var4_6 = new ArrayList<Uri>();
        var5_7 = 0;
        var2_2 = null;
        var6_8 = null;
        var1_1 = var1_1.query(var3_4 /* !! */ , new String[]{"document_id"}, null, null, null);
        do {
            var3_4 /* !! */  = var1_1;
            var6_8 = var1_1;
            var2_2 = var1_1;
            if (!var1_1.moveToNext()) break;
            var6_8 = var1_1;
            var2_2 = var1_1;
            var3_4 /* !! */  = var1_1.getString(0);
            var6_8 = var1_1;
            var2_2 = var1_1;
            var4_6.add(DocumentsContract.buildDocumentUriUsingTree((Uri)this.mUri, (String)var3_4 /* !! */ ));
        } while (true);
lbl23: // 2 sources:
        do {
            continue;
            break;
        } while (true);
        {
            catch (Throwable var2_3) {
            }
            catch (Exception var3_5) {}
            var6_8 = var2_2;
            {
                var6_8 = var2_2;
                var1_1 = new StringBuilder();
                var6_8 = var2_2;
                var1_1.append("Failed query: ");
                var6_8 = var2_2;
                var1_1.append(var3_5);
                var6_8 = var2_2;
                Log.w((String)"DocumentFile", (String)var1_1.toString());
                var3_4 /* !! */  = var2_2;
                ** continue;
            }
        }
        TreeDocumentFile.closeQuietly((AutoCloseable)var6_8);
        throw var2_3;
        TreeDocumentFile.closeQuietly((AutoCloseable)var3_4 /* !! */ );
        var6_8 = var4_6.toArray((T[])new Uri[var4_6.size()]);
        var2_2 = new DocumentFile[var6_8.length];
        while (var5_7 < var6_8.length) {
            var2_2[var5_7] = new TreeDocumentFile(this, this.mContext, var6_8[var5_7]);
            ++var5_7;
        }
        return var2_2;
    }

    @Override
    public boolean renameTo(String string2) {
        try {
            string2 = DocumentsContract.renameDocument((ContentResolver)this.mContext.getContentResolver(), (Uri)this.mUri, (String)string2);
            if (string2 == null) return false;
            this.mUri = string2;
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

