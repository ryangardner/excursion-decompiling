/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Environment
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Images$Thumbnails
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.CheckBox
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.syntak.library;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.syntak.library.CipherOp;
import com.syntak.library.HttpOp;
import com.syntak.library.MediaOp;
import com.syntak.library.R;
import com.syntak.library.TimeOp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipOutputStream;

public class FileOp {
    static final String EXT_M3U8 = "m3u8";
    public static final String MIME_SQLITE = "application/x-sqlite3";

    public static String[] BufferedReaderToArray(BufferedReader bufferedReader) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            String string2;
            if ((string2 = bufferedReader.readLine()) == null) {
                bufferedReader.close();
                return arrayList.toArray(new String[arrayList.size()]);
            }
            arrayList.add(string2);
        } while (true);
    }

    public static void DownloadFileFromWeb(String object, String string2) {
        try {
            Object object2 = new URL((String)object);
            object2 = (FileInputStream)((URL)object2).getContent();
            object = new FileOutputStream(string2);
            FileOp.copyStream((InputStream)object2, (OutputStream)object);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static String[] FileToArray(String string2) throws IOException {
        return FileOp.BufferedReaderToArray(new BufferedReader(new FileReader(string2)));
    }

    public static boolean IsHostavailable(String string2) {
        try {
            InetAddress.getByName(string2);
            return true;
        }
        catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace();
            return false;
        }
    }

    public static Drawable LoadImageFromWeb(String string2) {
        try {
            URL uRL = new URL(string2);
            return Drawable.createFromStream((InputStream)uRL.openStream(), (String)"src name");
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static boolean NewIsHostavailable(String object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("ping ");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" -c 1");
        object = ((StringBuilder)object2).toString();
        object2 = Runtime.getRuntime();
        boolean bl = false;
        try {
            object = ((Runtime)object2).exec((String)object);
            try {
                ((Process)object).waitFor();
                if (((Process)object).exitValue() != 0) return bl;
                return true;
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return false;
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public static void SortFilesInfo(ArrayList<FileInfo> arrayList) {
        Collections.sort(arrayList, new Comparator<FileInfo>(){

            @Override
            public int compare(FileInfo fileInfo, FileInfo fileInfo2) {
                if (fileInfo.file_type == fileInfo2.file_type) return fileInfo.name.compareToIgnoreCase(fileInfo2.name);
                return fileInfo.file_type - fileInfo2.file_type;
            }
        });
    }

    public static String[] UrlFileToArray(String string2) throws IOException {
        return FileOp.BufferedReaderToArray(new BufferedReader(new InputStreamReader((InputStream)new URL(string2).getContent())));
    }

    public static String[][] UrlFileToArray2D(String arrstring) throws IOException {
        arrstring = FileOp.UrlFileToArray((String)arrstring);
        String[][] arrstring2 = new String[arrstring.length][];
        int n = 0;
        while (n < arrstring.length) {
            arrstring2[n] = arrstring[n].split(",");
            ++n;
        }
        return arrstring2;
    }

    public static boolean appendTextFile(String object, String string2) {
        Object object2 = new File((String)object);
        try {
            if (!((File)object2).exists()) {
                ((File)object2).createNewFile();
            }
            object = new FileOutputStream((File)object2, true);
            object2 = new OutputStreamWriter((OutputStream)object);
            BufferedWriter bufferedWriter = new BufferedWriter((Writer)object2);
            bufferedWriter.write(string2);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            ((FileOutputStream)object).close();
            return true;
        }
        catch (FileNotFoundException | IOException iOException) {
            return false;
        }
    }

    public static void buildFolderChain(String string2) {
        if (string2 == null) {
            return;
        }
        int n = string2.indexOf("/", 1);
        if (n > 0) {
            int n2;
            do {
                String string3;
                if (!FileOp.checkDirExist(string3 = string2.substring(0, n))) {
                    FileOp.makeDir(string3);
                }
                if ((n2 = string2.indexOf("/", n + 1)) <= 0) break;
                n = n2;
            } while (n2 <= string2.length());
        }
        if (FileOp.checkDirExist(string2)) return;
        FileOp.makeDir(string2);
    }

    public static boolean checkDirExist(String object) {
        if (!((File)(object = new File((String)object))).exists()) return false;
        if (!((File)object).isDirectory()) return false;
        return true;
    }

    public static boolean checkFileExist(String object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = new File((String)object);
        boolean bl2 = bl;
        if (!((File)object).exists()) return bl2;
        bl2 = bl;
        if (!((File)object).isFile()) return bl2;
        return true;
    }

    public static boolean checkUriExist(Uri uri) {
        return FileOp.checkFileExist(uri.getPath());
    }

    public static String combinePath(String string2, String string3) {
        if (string2 == null) {
            return string3;
        }
        if (string3 == null) {
            return string2;
        }
        if (string2.lastIndexOf("/") == string2.length() - 1) {
            if (string3.indexOf("/") == 0) {
                string3 = string3.substring(1);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(string3);
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }
        if (string3.indexOf("/") == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("/");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    public static String combinePath(String ... arrstring) {
        int n = arrstring.length;
        int n2 = 0;
        String string2 = null;
        int n3 = 0;
        while (n2 < n) {
            CharSequence charSequence = arrstring[n2];
            int n4 = n3;
            CharSequence charSequence2 = string2;
            if (charSequence != null) {
                if (n3 == 0) {
                    string2 = charSequence;
                } else if (string2.lastIndexOf("/") == string2.length() - 1) {
                    if (((String)charSequence).indexOf("/") == 0) {
                        charSequence2 = ((String)charSequence).substring(1);
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string2);
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        string2 = ((StringBuilder)charSequence).toString();
                    } else {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append(string2);
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        string2 = ((StringBuilder)charSequence2).toString();
                    }
                } else if (((String)charSequence).indexOf("/") == 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(string2);
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    string2 = ((StringBuilder)charSequence2).toString();
                } else {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(string2);
                    ((StringBuilder)charSequence2).append("/");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    string2 = ((StringBuilder)charSequence2).toString();
                }
                n4 = n3 + 1;
                charSequence2 = string2;
            }
            ++n2;
            n3 = n4;
            string2 = charSequence2;
        }
        return string2;
    }

    public static void copyFile(String object, String string2) {
        try {
            FileInputStream fileInputStream = new FileInputStream((String)object);
            object = new FileOutputStream(string2);
            FileOp.copyStream(fileInputStream, (OutputStream)object);
            ((OutputStream)object).flush();
            ((OutputStream)object).close();
            ((InputStream)fileInputStream).close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void copyStream(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        int n;
        byte[] arrby = new byte[1024];
        while ((n = inputStream2.read(arrby)) > 0) {
            outputStream2.write(arrby, 0, n);
        }
    }

    public static String decriptFile(String object, String string2, String arrby, int n) {
        byte[] arrby2 = new byte[1048576];
        arrby = CipherOp.readMapFile((String)arrby);
        try {
            FileInputStream fileInputStream = new FileInputStream((String)object);
            object = new FileOutputStream(string2);
            if (n > 0) {
                ((InputStream)fileInputStream).read(arrby2, 0, n);
            }
            do {
                if ((n = ((InputStream)fileInputStream).read(arrby2, 0, 1048576)) <= 0) {
                    ((OutputStream)object).flush();
                    ((OutputStream)object).close();
                    ((InputStream)fileInputStream).close();
                    return string2;
                }
                ((OutputStream)object).write(CipherOp.mappingData(arrby, arrby2, n), 0, n);
            } while (true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void deleteFile(String string2) {
        if (!FileOp.checkFileExist(string2)) {
            return;
        }
        new File(string2).delete();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static boolean dumpByteArrayToTextFile(byte[] var0, String var1_2, int var2_3) {
        var4_5 = var3_4 = false;
        if (var0 == null) return var4_5;
        var4_5 = var3_4;
        if (var0.length == 0) return var4_5;
        var4_5 = var3_4;
        if (var1_2 == null) return var4_5;
        if (var1_2.length() == 0) {
            return var3_4;
        }
        var5_6 = var2_3;
        if (var2_3 == 0) {
            var5_6 = 10;
        }
        var6_7 = new File(var1_2);
        try {
            if (var6_7.exists()) {
                FileOp.deleteFile(var1_2);
            }
            var6_7.createNewFile();
            var7_8 = new FileOutputStream((File)var6_7, true);
            var6_7 = new OutputStreamWriter(var7_8);
            var8_9 = new BufferedWriter((Writer)var6_7);
            var8_9.write(var1_2);
            var8_9.newLine();
            var8_9.write("      ");
            for (var2_3 = 0; var2_3 < var5_6; ++var2_3) {
                var8_9.write(String.format(Locale.ENGLISH, "%2d ", new Object[]{var2_3}));
            }
            var8_9.newLine();
            var8_9.write("      ");
            for (var2_3 = 0; var2_3 < var5_6; ++var2_3) {
                var8_9.write("---");
            }
            var8_9.newLine();
            var10_11 = 0;
            var2_3 = 0;
            var11_12 = 0;
            for (var9_10 = 0; var9_10 < var0.length; ++var9_10) {
                if (var10_11 % var5_6 != 0) ** GOTO lbl42
            }
        }
        catch (IOException var0_1) {
            return var3_4;
        }
        {
            block14 : {
                block13 : {
                    var8_9.write(String.format(Locale.ENGLISH, "%04d| ", new Object[]{var2_3}));
lbl42: // 2 sources:
                    var8_9.write(String.format("%02x ", new Object[]{var0[var11_12]}));
                    var12_13 = var11_12 + 1;
                    if (var10_11 % var5_6 != var5_6 - 1) break block13;
                    var8_9.newLine();
                    var11_12 = var2_3 + 1;
                    var2_3 = 0;
                    break block14;
                }
                var11_12 = var2_3;
                var2_3 = ++var10_11;
            }
            var10_11 = var2_3;
            var2_3 = var11_12;
            var11_12 = var12_13;
            continue;
        }
        var8_9.flush();
        var8_9.close();
        var7_8.close();
        return true;
    }

    public static void emptyFolder(String object) {
        if (!((File)(object = new File((String)object))).isDirectory()) return;
        String[] arrstring = ((File)object).list();
        int n = 0;
        while (n < arrstring.length) {
            new File((File)object, arrstring[n]).delete();
            ++n;
        }
    }

    public static String getAnyFilePathInFolder(String arrfile) {
        if ((arrfile = new File((String)arrfile).listFiles()) == null) return null;
        if (arrfile.length <= 0) return null;
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File file = arrfile[n2];
            if (!file.isDirectory()) {
                return file.getPath();
            }
            ++n2;
        }
        return null;
    }

    public static String getFileExtension(String string2) {
        if (!string2.contains(".")) return null;
        return string2.substring(string2.lastIndexOf(46) + 1);
    }

    public static Date getFileModifiedDate(String object) {
        if (object == null) {
            return null;
        }
        if (!((File)(object = new File((String)object))).exists()) return null;
        return new Date(((File)object).lastModified());
    }

    public static long getFileModifiedTime(String object) {
        long l = 0L;
        if (object == null) {
            return 0L;
        }
        if (!((File)(object = new File((String)object))).exists()) return l;
        return ((File)object).lastModified();
    }

    public static long getFileSize(String object) {
        long l = 0L;
        if (object == null) {
            return 0L;
        }
        if (!((File)(object = new File((String)object))).exists()) return l;
        return ((File)object).length();
    }

    public static String getFilenameFromPath(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.lastIndexOf(File.separator);
        String string3 = string2;
        if (n <= 0) return string3;
        return string2.substring(n + 1);
    }

    public static String[] getFilenames(String string2) {
        return new File(string2).list();
    }

    public static File[] getFiles(String string2) {
        return new File(string2).listFiles();
    }

    public static String[] getFilesByPrimary(String arrfile, String string2) {
        arrfile = new File((String)arrfile).listFiles();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (arrfile == null) {
            return null;
        }
        int n = 0;
        do {
            String string3;
            if (n >= arrfile.length) {
                if (arrayList.size() <= 0) return null;
                return arrayList.toArray(new String[arrayList.size()]);
            }
            if (arrfile[n].isFile() && string2.equals(FileOp.getPrimaryFilename(string3 = arrfile[n].getName()))) {
                arrayList.add(string3);
            }
            ++n;
        } while (true);
    }

    public static ArrayList<FileInfo> getFilesInfo(String arrfile) {
        ArrayList<FileInfo> arrayList = new ArrayList<FileInfo>();
        arrfile = new File((String)arrfile).listFiles();
        int n = arrfile.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                FileOp.SortFilesInfo(arrayList);
                return arrayList;
            }
            File[] arrfile2 = arrfile[n2];
            FileInfo fileInfo = new FileInfo();
            fileInfo.path = arrfile2.getPath();
            fileInfo.name = arrfile2.getName();
            fileInfo.file = arrfile2;
            fileInfo.time_last_modified = arrfile2.lastModified();
            if (arrfile2.isDirectory()) {
                fileInfo.file_type = 0;
                if ((arrfile2 = arrfile2.listFiles()) != null) {
                    fileInfo.num_childs = arrfile2.length;
                }
            } else {
                fileInfo.file_type = 1;
                fileInfo.length = arrfile2.length();
                fileInfo.checkable = true;
            }
            arrayList.add(fileInfo);
            ++n2;
        } while (true);
    }

    public static String getFirstFolderChainOfNextLevel(String object) {
        object = new File((String)object).listFiles();
        int n = 0;
        while (n < ((File[])object).length) {
            if (object[n].isDirectory()) {
                return object[n].getPath();
            }
            ++n;
        }
        return null;
    }

    public static String getFolderChainFromPath(String string2) {
        Object var1_1 = null;
        if (string2 == null) {
            return null;
        }
        int n = string2.lastIndexOf(File.separator);
        if (n > 0) {
            return string2.substring(0, n);
        }
        string2 = var1_1;
        if (n != 0) return string2;
        return "/";
    }

    public static String getFolderFromPath(String string2) {
        Object var1_1 = null;
        if (string2 == null) {
            return null;
        }
        int n = string2.lastIndexOf(File.separator);
        if (n > 0) {
            int n2 = n - 1;
            String string3 = string2.substring(0, n2);
            n = string3.lastIndexOf(File.separator);
            string2 = var1_1;
            if (n < 0) return string2;
            return string3.substring(n, n2);
        }
        string2 = var1_1;
        if (n != 0) return string2;
        return "/";
    }

    public static String getImagePathFromID(Context object, long l) {
        Cursor cursor = object.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data"}, "_id = ?", new String[]{String.valueOf(l)}, null);
        int n = cursor.getColumnIndex("_data");
        cursor.moveToFirst();
        object = null;
        do {
            if (cursor.isAfterLast()) {
                cursor.close();
                return object;
            }
            object = cursor.getString(n);
            cursor.moveToNext();
        } while (true);
    }

    public static String getImagePathFromThumbPath(Context context, String string2) {
        if (string2 == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%");
        stringBuilder.append(string2);
        string2 = stringBuilder.toString();
        string2 = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"image_id"}, "_data LIKE ?", new String[]{string2}, null);
        if (string2 != null) {
            int n = string2.getColumnIndex("image_id");
            string2.moveToFirst();
            long l = -1L;
            while (!string2.isAfterLast()) {
                l = Long.parseLong(string2.getString(n));
                string2.moveToNext();
            }
            if (l != -1L) {
                FileOp.getImagePathFromID(context, l);
            }
        }
        string2.close();
        return null;
    }

    public static int getListsFromM3U8(String arrstring, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        arrstring = arrstring.split(" ");
        int n = 0;
        int n2 = 0;
        while (n < arrstring.length) {
            if (!arrstring[n].substring(0, 1).equals("#")) {
                if (EXT_M3U8.equals(FileOp.getFileExtension(arrstring[n]).toLowerCase())) {
                    arrayList.add(arrstring[n]);
                    ++n2;
                } else {
                    arrayList2.add(arrstring[n]);
                }
            }
            ++n;
        }
        return n2;
    }

    public static String getPathAfterFolder(String string2, String string3) {
        String string4;
        String string5 = string4 = null;
        if (string2 == null) return string5;
        if (string3 == null) {
            return string4;
        }
        string5 = string4;
        if (!string2.contains(string3)) return string5;
        return string2.substring(string2.indexOf(string3) + string3.length());
    }

    public static String getPathFromURI(Context context, Uri object) {
        String string2 = object.getScheme();
        if (string2 == null) return object.getPath();
        if (!string2.equals("content")) return object.getPath();
        string2 = null;
        try {
            context = context.getContentResolver().query(object, new String[]{"_data"}, null, null, null);
            string2 = context;
            int n = context.getColumnIndexOrThrow("_data");
            string2 = context;
            context.moveToFirst();
            string2 = context;
            object = context.getString(n);
            if (context == null) return object;
        }
        catch (Throwable throwable) {
            if (string2 == null) throw throwable;
            string2.close();
            throw throwable;
        }
        context.close();
        return object;
    }

    public static String[] getPathsFromURIs(Context context, ArrayList<Uri> arrayList) {
        int n = arrayList.size();
        String[] arrstring = new String[n];
        int n2 = 0;
        while (n2 < n) {
            arrstring[n2] = FileOp.getPathFromURI(context, arrayList.get(n2));
            ++n2;
        }
        return arrstring;
    }

    public static String getPrimaryFilename(String string2) {
        String string3 = string2;
        if (!string2.contains(".")) return string3;
        return string2.substring(0, string2.lastIndexOf(46));
    }

    public static String getPrimaryFilenameFromPath(String string2) {
        return FileOp.getPrimaryFilename(FileOp.getFilenameFromPath(string2));
    }

    public static String getThumbPathFromID(Context object, long l) {
        Cursor cursor = object.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"_data"}, "image_id = ?", new String[]{String.valueOf(l)}, null);
        int n = cursor.getColumnIndex("_data");
        cursor.moveToFirst();
        object = null;
        do {
            if (cursor.isAfterLast()) {
                cursor.close();
                return object;
            }
            object = cursor.getString(n);
            cursor.moveToNext();
        } while (true);
    }

    public static List<String> get_List_of_Filepath_with_Extension(String string2, String string3) {
        ArrayList<String> arrayList = new ArrayList<String>();
        File[] arrfile = new File(string2).listFiles();
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File file = arrfile[n2];
            string2 = file.getPath();
            if (file.isFile() && string2.endsWith(string3)) {
                arrayList.add(string2);
            }
            ++n2;
        }
        return arrayList;
    }

    public static boolean isExternalStorageAvailable() {
        if (!Environment.getExternalStorageState().equals("mounted")) return false;
        return true;
    }

    public static boolean makeDir(String string2) {
        return new File(string2).mkdir();
    }

    public static void moveFile(String string2, String string3) {
        FileOp.copyFile(string2, string3);
        new File(string2).delete();
    }

    public static String readTextFile(String charSequence) {
        File file = new File((String)charSequence);
        charSequence = new StringBuilder();
        try {
            Object object = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader((Reader)object);
            do {
                if ((object = bufferedReader.readLine()) == null) {
                    bufferedReader.close();
                    return ((StringBuilder)charSequence).toString();
                }
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append('\n');
            } while (true);
        }
        catch (IOException iOException) {
            return ((StringBuilder)charSequence).toString();
        }
    }

    public static String removeLastSlash(String string2) {
        String string3 = string2;
        if (string2.lastIndexOf("/") != string2.length() - 1) return string3;
        return string2.substring(0, string2.length() - 1);
    }

    public static void rename(String string2, String string3, String string4) {
        new File(string2, string3).renameTo(new File(string2, string4));
    }

    public static boolean setFileLastModified(String object, long l) {
        if (object == null) {
            return false;
        }
        if (!((File)(object = new File((String)object))).exists()) return true;
        if (!((File)object).isFile()) return true;
        ((File)object).setLastModified(l);
        return true;
    }

    public static void writeDataOutputStreamShorts(DataOutputStream dataOutputStream, short[] arrs, int n) throws IOException {
        int n2 = 0;
        while (n2 < n) {
            dataOutputStream.writeShort(arrs[n2]);
            ++n2;
        }
    }

    public static String zip(String object, String string2) {
        Object object2 = FileOp.getFilenameFromPath((String)object);
        byte[] arrby = new StringBuilder();
        arrby.append(string2);
        arrby.append(FileOp.getPrimaryFilename((String)object2));
        arrby.append(".zip");
        string2 = arrby.toString();
        arrby = new byte[1048576];
        try {
            object2 = new FileInputStream((String)object);
            object = new BufferedInputStream((InputStream)object2);
            FileOutputStream fileOutputStream = new FileOutputStream(string2);
            object2 = new BufferedOutputStream(fileOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)object2);
            do {
                int n;
                if ((n = ((BufferedInputStream)object).read(arrby, 0, 1048576)) <= 0) {
                    zipOutputStream.flush();
                    zipOutputStream.close();
                    ((BufferedInputStream)object).close();
                    return string2;
                }
                zipOutputStream.write(arrby, 0, n);
            } while (true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static class AsyncCopyFile
    extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String ... arrstring) {
            FileOp.copyFile(arrstring[0], arrstring[1]);
            this.onBackgroundResponse();
            return null;
        }

        public void onBackgroundResponse() {
        }

        public void onPost() {
        }

        protected void onPostExecute() {
            this.onPost();
        }
    }

    public static class AsyncMappingFile
    extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String ... object) {
            String string2 = object[0];
            Object object2 = object[1];
            String string3 = object[2];
            byte[] arrby = object[3];
            int n = (object = object[4]) != null ? ((String)object).length() : 0;
            byte[] arrby2 = new byte[1048576];
            arrby = CipherOp.readMapFile((String)arrby);
            try {
                FileInputStream fileInputStream = new FileInputStream((String)object2);
                object2 = new FileOutputStream(string3);
                if (n > 0) {
                    if (string2 == "encription") {
                        ((OutputStream)object2).write(((String)object).getBytes(), 0, ((String)object).length());
                    } else {
                        ((InputStream)fileInputStream).read(arrby2, 0, n);
                    }
                }
                do {
                    if ((n = ((InputStream)fileInputStream).read(arrby2, 0, 1048576)) <= 0) {
                        ((OutputStream)object2).flush();
                        ((OutputStream)object2).close();
                        ((InputStream)fileInputStream).close();
                        this.onBackgroundResult(true);
                        return null;
                    }
                    ((OutputStream)object2).write(CipherOp.mappingData(arrby, arrby2, n), 0, n);
                    this.onRead(n);
                } while (true);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }

        public void onBackgroundResult(boolean bl) {
        }

        public void onPost() {
        }

        protected void onPostExecute() {
            this.onPost();
        }

        public void onRead(int n) {
        }
    }

    public static class DownloadCachedUrlImage
    extends AsyncTask<String, Void, String> {
        Bitmap bitmap;
        boolean flag_local_existed = false;
        boolean flag_max_priority = false;
        boolean flag_to_download = false;
        String local;
        String url;

        public DownloadCachedUrlImage(String string2, String string3) {
            this.url = string2;
            this.local = string3;
            if (!FileOp.checkFileExist(string3)) {
                this.flag_to_download = true;
                return;
            }
            string2 = MediaOp.getBitmapFromPath(string3);
            this.bitmap = string2;
            if (string2 == null) {
                this.flag_to_download = true;
                return;
            }
            this.flag_local_existed = true;
        }

        public void OnDownloaded(String string2) {
        }

        protected String doInBackground(String ... arrstring) {
            if (this.flag_max_priority) {
                Thread.currentThread().setPriority(10);
            }
            long l = HttpOp.HttpGetLastModified(this.url);
            if (this.flag_local_existed && new File(this.local).lastModified() != l) {
                this.flag_to_download = true;
            }
            if (!this.flag_to_download) return this.local;
            if ("HTTP OK".equals(HttpOp.HttpDownloadFile(this.url, this.local))) {
                FileOp.setFileLastModified(this.local, l);
                return this.local;
            }
            this.local = null;
            return this.local;
        }

        protected void onPostExecute(String string2) {
            this.OnDownloaded(string2);
        }

        public DownloadCachedUrlImage setMaxPriority() {
            this.flag_max_priority = true;
            return this;
        }
    }

    public static class FileInfo {
        public static final int TYPE_FILE = 1;
        public static final int TYPE_FOLDER = 0;
        public static final int TYPE_PARENT = 2;
        public static final int TYPE_ROOT = 3;
        public boolean checkable = false;
        public File file;
        public int file_type;
        public boolean isChecked = false;
        public long length;
        public String mime_type;
        public String name = null;
        public int num_childs = 0;
        public String path = null;
        public long time_last_modified = 0L;
        private ViewGroup viewGroup = null;
    }

    public static class FileList {
        private static ArrayList<FileInfo> files_info;
        private static FileListAdapter listAdapter;
        private static ListView listView;
        private static String parent_folder;
        private Context context;
        private boolean flag_show_index = false;
        private int index = 0;
        boolean multiple_selection = false;
        ArrayList<String> preSelectedPaths = null;

        public FileList(Context object, ListView listView, String string2, boolean bl) {
            this.context = object;
            FileList.listView = listView;
            this.multiple_selection = bl;
            if (files_info == null) {
                files_info = new ArrayList();
            }
            object = new FileListAdapter();
            listAdapter = object;
            FileList.listView.setAdapter((ListAdapter)object);
            FileList.fillFilesInfo(string2);
        }

        public static void clearList() {
            files_info.clear();
            files_info.trimToSize();
            FileList.notifyDataSetChanged();
        }

        public static void fillFilesInfo(String string2) {
            files_info = null;
            files_info = FileOp.getFilesInfo(string2);
            parent_folder = new File(string2).getParent();
            FileList.notifyDataSetChanged();
        }

        public static FileInfo getItem(int n) {
            return files_info.get(n);
        }

        public static int getListSize() {
            return files_info.size();
        }

        public static String getParentFolder() {
            return parent_folder;
        }

        private void logChecked(View view) {
            FileInfo fileInfo = (FileInfo)view.getTag();
            fileInfo.isChecked = ((CheckBox)view).isChecked();
            if (fileInfo.isChecked) {
                this.onCheckedChanged(1);
                return;
            }
            this.onCheckedChanged(-1);
        }

        public static void notifyDataSetChanged() {
            listAdapter.notifyDataSetChanged();
        }

        private void show_row_view(FileInfo fileInfo) {
            Object object = (ImageView)fileInfo.viewGroup.findViewById(R.id.icon);
            TextView textView = (TextView)fileInfo.viewGroup.findViewById(R.id.name);
            TextView textView2 = (TextView)fileInfo.viewGroup.findViewById(R.id.time_last_modifed);
            TextView textView3 = (TextView)fileInfo.viewGroup.findViewById(R.id.size);
            CheckBox checkBox = (CheckBox)fileInfo.viewGroup.findViewById(R.id.checkBox);
            RelativeLayout relativeLayout = (RelativeLayout)fileInfo.viewGroup.findViewById(R.id.row_file_list);
            object.setVisibility(0);
            textView.setVisibility(0);
            textView2.setVisibility(0);
            textView3.setVisibility(0);
            int n = fileInfo.file_type;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            object.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.up));
                            textView.setText((CharSequence)"/");
                            textView2.setVisibility(8);
                            textView3.setVisibility(8);
                        }
                    } else {
                        textView.setText((CharSequence)"..");
                        object.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.up));
                        textView2.setVisibility(8);
                        textView3.setVisibility(8);
                    }
                } else {
                    object.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.file));
                    textView.setText((CharSequence)fileInfo.name);
                    textView2.setText((CharSequence)TimeOp.MsToHourMinuteYearMonthDayVariable(fileInfo.time_last_modified));
                    if (fileInfo.length > 1000000L) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(String.valueOf(fileInfo.length / 1000000L));
                        ((StringBuilder)object).append(" MB");
                        object = ((StringBuilder)object).toString();
                    } else if (fileInfo.length > 1000L) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(String.valueOf(fileInfo.length / 1000L));
                        ((StringBuilder)object).append(" KB");
                        object = ((StringBuilder)object).toString();
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(String.valueOf(fileInfo.length));
                        ((StringBuilder)object).append(" Bytes");
                        object = ((StringBuilder)object).toString();
                    }
                    textView3.setText((CharSequence)object);
                }
            } else {
                textView.setText((CharSequence)fileInfo.name);
                textView2.setText((CharSequence)TimeOp.MsToHourMinuteYearMonthDayVariable(fileInfo.time_last_modified));
                object.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.folder_blue));
                object = new StringBuilder();
                ((StringBuilder)object).append(String.valueOf(fileInfo.num_childs));
                ((StringBuilder)object).append(" items");
                textView3.setText((CharSequence)((StringBuilder)object).toString());
            }
            if (fileInfo.checkable && this.multiple_selection) {
                object = this.preSelectedPaths;
                if (object != null && ((ArrayList)object).contains(fileInfo.path)) {
                    checkBox.setChecked(true);
                    checkBox.setEnabled(false);
                } else {
                    checkBox.setChecked(false);
                    checkBox.setEnabled(true);
                    checkBox.setTag((Object)fileInfo);
                    checkBox.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            FileList.this.logChecked(view);
                        }
                    });
                }
                checkBox.setVisibility(0);
            } else {
                checkBox.setVisibility(8);
            }
            relativeLayout.setTag((Object)fileInfo);
            relativeLayout.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    FileList.this.onRowClicked((FileInfo)view.getTag());
                }
            });
        }

        public void onCheckedChanged(int n) {
        }

        public void onRowClicked(FileInfo fileInfo) {
        }

        public class FileListAdapter
        extends BaseAdapter {
            LayoutInflater inflater;

            public FileListAdapter() {
                this.inflater = (LayoutInflater)FileList.this.context.getSystemService("layout_inflater");
            }

            public int getCount() {
                return files_info.size();
            }

            public Object getItem(int n) {
                return files_info.get(n);
            }

            public long getItemId(int n) {
                return 0L;
            }

            public View getView(int n, View object, ViewGroup viewGroup) {
                object = (FileInfo)files_info.get(n);
                if (((FileInfo)object).viewGroup != null) return ((FileInfo)object).viewGroup;
                ((FileInfo)object).viewGroup = (ViewGroup)this.inflater.inflate(R.layout.file_list, null);
                FileList.this.show_row_view((FileInfo)object);
                return ((FileInfo)object).viewGroup;
            }
        }

    }

    public static class ReadTextFromWebFile {
        public ReadTextFromWebFile(final String string2) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Object object = new URL(string2);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)object.openConnection();
                        httpURLConnection.getResponseCode();
                        httpURLConnection.connect();
                        int n = httpURLConnection.getContentLength();
                        object = new byte[n + 1];
                        InputStream inputStream2 = httpURLConnection.getInputStream();
                        inputStream2.read((byte[])object, 0, n);
                        inputStream2.close();
                        httpURLConnection.disconnect();
                        object = object.toString();
                        ReadTextFromWebFile.this.OnTextRead((String)object);
                        return;
                    }
                    catch (IOException iOException) {
                        Log.d((String)"WebFile", (String)iOException.toString());
                    }
                }
            }).start();
        }

        public void OnTextRead(String string2) {
        }

    }

}

