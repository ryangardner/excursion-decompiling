/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.webkit.MimeTypeMap
 */
package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS = new String[]{"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
    private static final String TAG_EXTERNAL_FILES = "external-files-path";
    private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    private static HashMap<String, PathStrategy> sCache = new HashMap();
    private PathStrategy mStrategy;

    private static File buildPath(File file, String ... arrstring) {
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            File file2 = file;
            if (string2 != null) {
                file2 = new File(file, string2);
            }
            ++n2;
            file = file2;
        }
        return file;
    }

    private static Object[] copyOf(Object[] arrobject, int n) {
        Object[] arrobject2 = new Object[n];
        System.arraycopy(arrobject, 0, arrobject2, 0, n);
        return arrobject2;
    }

    private static String[] copyOf(String[] arrstring, int n) {
        String[] arrstring2 = new String[n];
        System.arraycopy(arrstring, 0, arrstring2, 0, n);
        return arrstring2;
    }

    private static PathStrategy getPathStrategy(Context context, String object) {
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            PathStrategy pathStrategy;
            PathStrategy pathStrategy2 = pathStrategy = sCache.get(object);
            if (pathStrategy != null) return pathStrategy2;
            try {
                pathStrategy2 = FileProvider.parsePathStrategy(context, (String)object);
                sCache.put((String)object, pathStrategy2);
            }
            catch (XmlPullParserException xmlPullParserException) {
                object = new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", xmlPullParserException);
                throw object;
            }
            catch (IOException iOException) {
                object = new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
                throw object;
            }
            return pathStrategy2;
        }
    }

    public static Uri getUriForFile(Context context, String string2, File file) {
        return FileProvider.getPathStrategy(context, string2).getUriForFile(file);
    }

    private static int modeToMode(String string2) {
        if ("r".equals(string2)) {
            return 268435456;
        }
        if ("w".equals(string2)) return 738197504;
        if ("wt".equals(string2)) return 738197504;
        if ("wa".equals(string2)) {
            return 704643072;
        }
        if ("rw".equals(string2)) {
            return 939524096;
        }
        if ("rwt".equals(string2)) {
            return 1006632960;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid mode: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static PathStrategy parsePathStrategy(Context object, String object2) throws IOException, XmlPullParserException {
        int n;
        SimplePathStrategy simplePathStrategy = new SimplePathStrategy((String)object2);
        ProviderInfo providerInfo = object.getPackageManager().resolveContentProvider((String)object2, 128);
        if (providerInfo == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't find meta-data for provider with authority ");
            ((StringBuilder)object).append((String)object2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        XmlResourceParser xmlResourceParser = providerInfo.loadXmlMetaData(object.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
        if (xmlResourceParser == null) throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        while ((n = xmlResourceParser.next()) != 1) {
            if (n != 2) continue;
            File[] arrfile = xmlResourceParser.getName();
            providerInfo = null;
            String string2 = xmlResourceParser.getAttributeValue(null, ATTR_NAME);
            String string3 = xmlResourceParser.getAttributeValue(null, ATTR_PATH);
            if (TAG_ROOT_PATH.equals(arrfile)) {
                object2 = DEVICE_ROOT;
            } else if (TAG_FILES_PATH.equals(arrfile)) {
                object2 = object.getFilesDir();
            } else if (TAG_CACHE_PATH.equals(arrfile)) {
                object2 = object.getCacheDir();
            } else if (TAG_EXTERNAL.equals(arrfile)) {
                object2 = Environment.getExternalStorageDirectory();
            } else if (TAG_EXTERNAL_FILES.equals(arrfile)) {
                arrfile = ContextCompat.getExternalFilesDirs((Context)object, null);
                object2 = providerInfo;
                if (arrfile.length > 0) {
                    object2 = arrfile[0];
                }
            } else if (TAG_EXTERNAL_CACHE.equals(arrfile)) {
                arrfile = ContextCompat.getExternalCacheDirs((Context)object);
                object2 = providerInfo;
                if (arrfile.length > 0) {
                    object2 = arrfile[0];
                }
            } else {
                object2 = providerInfo;
                if (Build.VERSION.SDK_INT >= 21) {
                    object2 = providerInfo;
                    if (TAG_EXTERNAL_MEDIA.equals(arrfile)) {
                        arrfile = object.getExternalMediaDirs();
                        object2 = providerInfo;
                        if (arrfile.length > 0) {
                            object2 = arrfile[0];
                        }
                    }
                }
            }
            if (object2 == null) continue;
            simplePathStrategy.addRoot(string2, FileProvider.buildPath((File)object2, string3));
        }
        return simplePathStrategy;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) throw new SecurityException("Provider must not be exported");
        if (!providerInfo.grantUriPermissions) throw new SecurityException("Provider must grant uri permissions");
        this.mStrategy = FileProvider.getPathStrategy(context, providerInfo.authority);
    }

    public int delete(Uri uri, String string2, String[] arrstring) {
        return (int)this.mStrategy.getFileForUri(uri).delete();
    }

    public String getType(Uri object) {
        int n = ((File)(object = this.mStrategy.getFileForUri((Uri)object))).getName().lastIndexOf(46);
        if (n < 0) return "application/octet-stream";
        object = ((File)object).getName().substring(n + 1);
        object = MimeTypeMap.getSingleton().getMimeTypeFromExtension((String)object);
        if (object == null) return "application/octet-stream";
        return object;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string2) throws FileNotFoundException {
        return ParcelFileDescriptor.open((File)this.mStrategy.getFileForUri(uri), (int)FileProvider.modeToMode(string2));
    }

    public Cursor query(Uri arrobject, String[] matrixCursor, String arrstring, String[] arrstring2, String string2) {
        arrstring = this.mStrategy.getFileForUri((Uri)arrobject);
        arrobject = matrixCursor;
        if (matrixCursor == null) {
            arrobject = COLUMNS;
        }
        arrstring2 = new String[arrobject.length];
        matrixCursor = new Object[arrobject.length];
        int n = arrobject.length;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            block8 : {
                block7 : {
                    block6 : {
                        if (n2 >= n) {
                            arrstring = FileProvider.copyOf(arrstring2, n3);
                            arrobject = FileProvider.copyOf((Object[])matrixCursor, n3);
                            matrixCursor = new MatrixCursor(arrstring, 1);
                            matrixCursor.addRow(arrobject);
                            return matrixCursor;
                        }
                        string2 = arrobject[n2];
                        if (!"_display_name".equals(string2)) break block6;
                        arrstring2[n3] = "_display_name";
                        n4 = n3 + 1;
                        matrixCursor[n3] = arrstring.getName();
                        n3 = n4;
                        break block7;
                    }
                    n4 = n3;
                    if (!"_size".equals(string2)) break block8;
                    arrstring2[n3] = "_size";
                    n4 = n3 + 1;
                    matrixCursor[n3] = Long.valueOf(arrstring.length());
                    n3 = n4;
                }
                n4 = n3;
            }
            ++n2;
            n3 = n4;
        } while (true);
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("No external updates");
    }

    static interface PathStrategy {
        public File getFileForUri(Uri var1);

        public Uri getUriForFile(File var1);
    }

    static class SimplePathStrategy
    implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap();

        SimplePathStrategy(String string2) {
            this.mAuthority = string2;
        }

        void addRoot(String charSequence, File file) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) throw new IllegalArgumentException("Name must not be empty");
            try {
                File file2 = file.getCanonicalFile();
                this.mRoots.put((String)charSequence, file2);
            }
            catch (IOException iOException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Failed to resolve canonical path for ");
                ((StringBuilder)charSequence).append(file);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString(), iOException);
            }
        }

        @Override
        public File getFileForUri(Uri object) {
            Object object2 = object.getEncodedPath();
            int n = ((String)object2).indexOf(47, 1);
            Object object3 = Uri.decode((String)((String)object2).substring(1, n));
            object2 = Uri.decode((String)((String)object2).substring(n + 1));
            if ((object3 = this.mRoots.get(object3)) == null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Unable to find configured root for ");
                ((StringBuilder)object3).append(object);
                throw new IllegalArgumentException(((StringBuilder)object3).toString());
            }
            object = new File((File)object3, (String)object2);
            try {
                object2 = ((File)object).getCanonicalFile();
                if (!((File)object2).getPath().startsWith(((File)object3).getPath())) throw new SecurityException("Resolved path jumped beyond configured root");
                return object2;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve canonical path for ");
                stringBuilder.append(object);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Uri getUriForFile(File object) {
            CharSequence charSequence;
            void var4_8;
            try {
                charSequence = ((File)object).getCanonicalPath();
                object = null;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve canonical path for ");
                stringBuilder.append(object);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            for (Map.Entry<String, File> object22 : this.mRoots.entrySet()) {
                String string2 = object22.getValue().getPath();
                if (!((String)charSequence).startsWith(string2) || object != null && string2.length() <= object.getValue().getPath().length()) continue;
                object = object22;
            }
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to find configured root that contains ");
                ((StringBuilder)object).append((String)charSequence);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            String iOException = ((File)object.getValue()).getPath();
            if (iOException.endsWith("/")) {
                String stringBuilder = ((String)charSequence).substring(iOException.length());
            } else {
                String string3 = ((String)charSequence).substring(iOException.length() + 1);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Uri.encode((String)object.getKey()));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Uri.encode((String)var4_8, (String)"/"));
            object = ((StringBuilder)charSequence).toString();
            return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath((String)object).build();
        }
    }

}

