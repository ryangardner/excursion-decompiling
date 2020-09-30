/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.util.Log
 */
package com.syntak.library;

import android.os.Process;
import android.util.Log;
import com.syntak.library.CipherOp;
import com.syntak.library.FileOp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpOp {
    private static final String CRLF = "\r\n";
    public static final String HTTP_ERROR = "HTTP ERROR";
    public static final String HTTP_ERROR_CONNECTION = "HTTP Error Connection!";
    public static final String HTTP_ERROR_SOURCE_FILE = "HTTP Error Source File Not Found!";
    public static final String HTTP_ERROR_TARGET_FILE = "HTTP Error Target File Cannot Open!";
    public static final String HTTP_OK = "HTTP OK";
    public static final String HTTP_URL = "HTTP_URL";
    private static final int TASK_DOWNLOAD = 2;
    private static final int TASK_PHP = 0;
    private static final int TASK_UPLOAD = 1;
    public static final String TRANSMISSION_CHECK = "TRANSMISSION_CHECK";
    private static final String tag_folder_switch = "folder_switch";
    private static final String tag_uploaded_file = "uploaded_file";

    public static String Connection_IO(URLConnection object, String object2) {
        try {
            ((URLConnection)object).setDoInput(true);
            ((URLConnection)object).setDoOutput(true);
            ((URLConnection)object).setUseCaches(false);
            ((URLConnection)object).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Object object3 = new DataOutputStream(((URLConnection)object).getOutputStream());
            Object object4 = new DataOutputStream((OutputStream)object3);
            object3 = new OutputStreamWriter((OutputStream)object4, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter((Writer)object3);
            bufferedWriter.write((String)object2);
            bufferedWriter.close();
            bufferedWriter.close();
            object = ((URLConnection)object).getInputStream();
            object4 = new InputStreamReader((InputStream)object, "UTF-8");
            object2 = new BufferedReader((Reader)object4);
            object = "";
            do {
                if ((object4 = ((BufferedReader)object2).readLine()) == null) {
                    ((BufferedReader)object2).close();
                    return object;
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append((String)object4);
                object = ((StringBuilder)object3).toString();
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return HTTP_ERROR_CONNECTION;
        }
    }

    public static String HttpClientGet(String object) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        object = new HttpGet((String)object);
        try {
            return EntityUtils.toString(defaultHttpClient.execute((HttpUriRequest)object).getEntity());
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static String HttpDeleteFile(String object) {
        Object object2;
        block8 : {
            block11 : {
                block10 : {
                    block9 : {
                        object2 = new URL((String)object);
                        object = (HttpURLConnection)((URL)object2).openConnection();
                        try {
                            ((URLConnection)object).setDoOutput(true);
                            ((URLConnection)object).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            ((HttpURLConnection)object).setRequestMethod("DELETE");
                            ((URLConnection)object).connect();
                            object2 = HTTP_OK;
                            break block8;
                        }
                        catch (IOException iOException) {
                            break block9;
                        }
                        catch (ProtocolException protocolException) {
                            break block10;
                        }
                        catch (MalformedURLException malformedURLException) {
                            break block11;
                        }
                        catch (IOException iOException) {
                            object = null;
                        }
                    }
                    object2 = ((Throwable)object2).getMessage();
                    break block8;
                    catch (ProtocolException protocolException) {
                        object = null;
                    }
                }
                object2 = ((Throwable)object2).getMessage();
                break block8;
                catch (MalformedURLException malformedURLException) {
                    object = null;
                }
            }
            object2 = ((Throwable)object2).getMessage();
        }
        if (object == null) return object2;
        ((HttpURLConnection)object).disconnect();
        return object2;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static String HttpDownloadFile(String object, String object2) {
        block7 : {
            block9 : {
                byte[] arrby;
                block8 : {
                    arrby = null;
                    InputStream inputStream2 = null;
                    File file = new File((String)object2);
                    object2 = new URL((String)object);
                    object = (HttpURLConnection)((URL)object2).openConnection();
                    try {
                        int n;
                        ((HttpURLConnection)object).getResponseCode();
                        ((URLConnection)object).connect();
                        int n2 = ((URLConnection)object).getContentLength();
                        inputStream2 = ((URLConnection)object).getInputStream();
                        object2 = new FileOutputStream(file);
                        n2 = Math.min(n2, 1048576);
                        arrby = new byte[n2];
                        while ((n = inputStream2.read(arrby, 0, n2)) > 0) {
                            ((OutputStream)object2).write(arrby, 0, n);
                        }
                        ((OutputStream)object2).flush();
                        ((OutputStream)object2).close();
                        inputStream2.close();
                        ((HttpURLConnection)object).disconnect();
                        object2 = HTTP_OK;
                        break block7;
                    }
                    catch (IOException iOException) {
                        break block8;
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        break block9;
                    }
                    catch (IOException iOException) {
                        object = inputStream2;
                    }
                }
                object2 = ((Throwable)object2).getMessage();
                break block7;
                catch (FileNotFoundException fileNotFoundException) {
                    object = arrby;
                }
            }
            object2 = ((Throwable)object2).getMessage();
        }
        if (object == null) return object2;
        ((HttpURLConnection)object).disconnect();
        return object2;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static String HttpDownloadFileCipher(String var0, String var1_2, String var2_8, String var3_10) {
        block14 : {
            block13 : {
                block12 : {
                    var4_11 = "HTTP OK";
                    var5_12 = null;
                    var6_13 = null;
                    var7_14 = null;
                    var8_15 = new File(var1_2);
                    var9_16 = var0.replaceAll(" ", "%20");
                    var0 = new URL(var9_16);
                    var0 = (HttpURLConnection)var0.openConnection();
                    try {
                        block11 : {
                            var5_12 = var0.getHeaderField("Last-Modified");
                            var7_14 = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                            var5_12 = var7_14.parse((String)var5_12);
                            var0.getResponseCode();
                            var0.connect();
                            var10_17 = var0.getContentLength();
                            var6_13 = var0.getInputStream();
                            var7_14 = new FileOutputStream((File)var8_15);
                            var10_17 = Math.min(var10_17, 1048576);
                            var8_15 = new byte[var10_17];
                            var2_8 = CipherOp.readMapFile((String)var2_8);
                            if (var3_10 == null) ** GOTO lbl26
                            try {
                                var7_14.write(var3_10.getBytes(), 0, var3_10.length());
lbl26: // 3 sources:
                                while ((var11_18 = var6_13.read(var8_15, 0, var10_17)) > 0) {
                                    var7_14.write(CipherOp.mappingData(var2_8, var8_15, var11_18), 0, var11_18);
                                }
                                break block11;
                            }
                            catch (Exception var2_9) {}
                            catch (IOException var1_6) {
                                var0 = var7_14;
                                break block12;
                            }
                            catch (FileNotFoundException var1_7) {
                                var0 = var5_12;
                                break block13;
                            }
                            catch (ParseException var0_1) {
                                var1_2 = var4_11;
                                var0 = var6_13;
                                break block14;
                            }
                        }
                        var7_14.flush();
                        var7_14.close();
                        var6_13.close();
                        var0.disconnect();
                        FileOp.setFileLastModified(var1_2, var5_12.getTime());
                        var1_2 = var4_11;
                        break block14;
                    }
                    catch (ParseException var1_3) {
                        var1_2 = var4_11;
                        break block14;
                    }
                    catch (IOException var1_4) {
                    }
                    catch (FileNotFoundException var1_5) {
                        break block13;
                    }
                }
                var1_2 = var1_2.getMessage();
                break block14;
            }
            var1_2 = var1_2.getMessage();
        }
        if (var0 == null) return var1_2;
        var0.disconnect();
        return var1_2;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static long HttpGetLastModified(String object) {
        long l;
        block6 : {
            void var3_8;
            block8 : {
                void var3_6;
                block7 : {
                    l = 0L;
                    URL uRL = new URL((String)object);
                    object = (HttpURLConnection)uRL.openConnection();
                    try {
                        long l2;
                        l = l2 = ((URLConnection)object).getLastModified();
                        break block6;
                    }
                    catch (IOException iOException) {
                        break block7;
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        break block8;
                    }
                    catch (IOException iOException) {
                        object = null;
                    }
                }
                var3_6.getMessage();
                break block6;
                catch (FileNotFoundException fileNotFoundException) {
                    object = null;
                }
            }
            var3_8.getMessage();
        }
        if (object == null) return l;
        ((HttpURLConnection)object).disconnect();
        return l;
    }

    public static String HttpPost(String object, String string2, int n) {
        try {
            URL uRL = new URL((String)object);
            object = uRL.openConnection();
            if (n < 0) return HttpOp.Connection_IO((URLConnection)object, string2);
            ((URLConnection)object).setConnectTimeout(n);
            ((URLConnection)object).setReadTimeout(n);
            return HttpOp.Connection_IO((URLConnection)object, string2);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return HTTP_ERROR_CONNECTION;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static String[] HttpSocketPhp(String object, String object2, int n, String string2) {
        Object object3;
        String[] arrstring;
        block5 : {
            void var1_4;
            block6 : {
                BufferedWriter bufferedWriter = null;
                arrstring = new String[]{null, null, null, null};
                object3 = new Socket((String)object, n);
                try {
                    Appendable appendable = new OutputStreamWriter(((Socket)object3).getOutputStream());
                    bufferedWriter = new BufferedWriter((Writer)appendable);
                    appendable = new StringBuilder();
                    ((StringBuilder)appendable).append("GET ");
                    ((StringBuilder)appendable).append((String)object2);
                    ((StringBuilder)appendable).append("?");
                    ((StringBuilder)appendable).append(string2);
                    ((StringBuilder)appendable).append(" HTTP/1.1");
                    ((StringBuilder)appendable).append(CRLF);
                    bufferedWriter.write(((StringBuilder)appendable).toString());
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Host: ");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(CRLF);
                    bufferedWriter.write(((StringBuilder)object2).toString());
                    bufferedWriter.write(CRLF);
                    bufferedWriter.flush();
                    object2 = new InputStreamReader(((Socket)object3).getInputStream());
                    object = new BufferedReader((Reader)object2);
                    while ((string2 = ((BufferedReader)object).readLine()) != null) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(arrstring[0]);
                        ((StringBuilder)object2).append(string2);
                        arrstring[0] = ((StringBuilder)object2).toString();
                    }
                    ((BufferedReader)object).close();
                    break block5;
                }
                catch (IOException iOException) {
                    object = object3;
                    break block6;
                }
                catch (IOException iOException) {
                    object = bufferedWriter;
                }
            }
            var1_4.printStackTrace();
            arrstring[0] = HTTP_ERROR_CONNECTION;
            object3 = object;
        }
        if (object3 == null) return arrstring;
        arrstring[1] = ((Socket)object3).getLocalAddress().toString();
        arrstring[2] = String.valueOf(((Socket)object3).getLocalPort());
        arrstring[3] = ((Socket)object3).getInetAddress().getHostAddress();
        return arrstring;
    }

    public static String HttpUploadFile(String string2, String string3, String string4) {
        return HttpOp.HttpUploadFile(string2, string3, string4, "tag", tag_uploaded_file, "folder", "../file/");
    }

    public static String HttpUploadFile(String charSequence, String object, String charSequence2, String object2, String string2, String string3, String string4) {
        Object object3 = new File((String)object);
        if (!((File)object3).isFile()) {
            return HTTP_ERROR_SOURCE_FILE;
        }
        try {
            object = new FileInputStream((File)object3);
            object3 = new URL((String)charSequence);
            object3 = (HttpURLConnection)((URL)object3).openConnection();
            ((URLConnection)object3).setDoInput(true);
            ((URLConnection)object3).setDoOutput(true);
            ((URLConnection)object3).setUseCaches(false);
            ((HttpURLConnection)object3).setRequestMethod("POST");
            ((URLConnection)object3).setRequestProperty("Connection", "Keep-Alive");
            ((URLConnection)object3).setRequestProperty("ENCTYPE", "multipart/form-data");
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("multipart/form-data;boundary=");
            ((StringBuilder)charSequence).append("*****");
            ((URLConnection)object3).setRequestProperty("Content-Type", ((StringBuilder)charSequence).toString());
            ((URLConnection)object3).setRequestProperty((String)object2, string2);
            ((URLConnection)object3).setRequestProperty(string3, string4);
            object2 = new DataOutputStream(((URLConnection)object3).getOutputStream());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("--");
            ((StringBuilder)charSequence).append("*****");
            ((StringBuilder)charSequence).append(CRLF);
            ((DataOutputStream)object2).writeBytes(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Content-Disposition: form-data; name=\"");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("\";filename=\"");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append('\"');
            ((StringBuilder)charSequence).append(CRLF);
            ((DataOutputStream)object2).writeBytes(((StringBuilder)charSequence).toString());
            ((DataOutputStream)object2).writeBytes(CRLF);
            int n = Math.min(((FileInputStream)object).available(), 1048576);
            charSequence = new byte[n];
            int n2 = ((FileInputStream)object).read((byte[])charSequence, 0, n);
            while (n2 > 0) {
                ((DataOutputStream)object2).write((byte[])charSequence, 0, n);
                n = Math.min(((FileInputStream)object).available(), 1048576);
                n2 = ((FileInputStream)object).read((byte[])charSequence, 0, n);
            }
            ((DataOutputStream)object2).writeBytes(CRLF);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("--");
            ((StringBuilder)charSequence).append("*****");
            ((StringBuilder)charSequence).append("--");
            ((StringBuilder)charSequence).append(CRLF);
            ((DataOutputStream)object2).writeBytes(((StringBuilder)charSequence).toString());
            n = ((HttpURLConnection)object3).getResponseCode();
            charSequence = ((HttpURLConnection)object3).getResponseMessage();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("HTTP Response is : ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(": ");
            ((StringBuilder)charSequence2).append(n);
            Log.i((String)"uploadFile", (String)((StringBuilder)charSequence2).toString());
            ((FileInputStream)object).close();
            ((DataOutputStream)object2).flush();
            ((FilterOutputStream)object2).close();
            object = new DataInputStream(((URLConnection)object3).getInputStream());
            do {
                if ((object2 = ((DataInputStream)object).readLine()) == null) {
                    ((FilterInputStream)object).close();
                    return HTTP_OK;
                }
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append((String)object2);
                charSequence = ((StringBuilder)charSequence2).toString();
            } while (true);
        }
        catch (Exception exception) {
            charSequence = exception.getMessage();
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception : ");
            ((StringBuilder)object).append((String)charSequence);
            Log.e((String)"Upload file ", (String)((StringBuilder)object).toString(), (Throwable)exception);
            return charSequence;
        }
        catch (MalformedURLException malformedURLException) {
            charSequence = malformedURLException.getMessage();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("error: ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            Log.e((String)"Upload file ", (String)((StringBuilder)charSequence2).toString(), (Throwable)malformedURLException);
        }
        return charSequence;
    }

    public static long getUrlLastModifiedTime(String string2) {
        try {
            string2 = string2.replaceAll(" ", "%20");
            Serializable serializable = new URL(string2);
            string2 = ((URL)serializable).openConnection().getHeaderField("Last-Modified");
            serializable = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            return ((DateFormat)serializable).parse(string2).getTime();
        }
        catch (IOException | MalformedURLException | ParseException exception) {
            return 0L;
        }
    }

    public static class AsyncHttpDownloadFile {
        String source;
        String target;

        public AsyncHttpDownloadFile(String ... arrstring) {
            this.source = arrstring[0];
            this.target = arrstring[1];
            new background_thread().start();
        }

        public String OnBackgroundResponse(String string2) {
            return string2;
        }

        class background_thread
        extends Thread {
            background_thread() {
            }

            @Override
            public void run() {
                String string2 = HttpOp.HttpDownloadFile(AsyncHttpDownloadFile.this.source, AsyncHttpDownloadFile.this.target);
                AsyncHttpDownloadFile.this.OnBackgroundResponse(string2.trim());
            }
        }

    }

    public static class AsyncHttpDownloadFileCipher {
        long downloaded = 0L;
        String id = null;
        String map_file;
        String source;
        String target;
        String timestamp;

        public AsyncHttpDownloadFileCipher(String ... arrstring) {
            Process.setThreadPriority((int)11);
            this.source = arrstring[0];
            this.target = arrstring[1];
            this.map_file = arrstring[2];
            if (arrstring.length > 3 && arrstring[3] != null) {
                this.timestamp = arrstring[3];
            }
            if (arrstring.length > 4 && arrstring[4] != null) {
                this.downloaded = Long.parseLong(arrstring[4]);
            }
            if (arrstring.length > 5 && arrstring[5] != null) {
                this.id = arrstring[5];
            }
            new background_thread().start();
        }

        public String OnBackgroundResponse(String string2) {
            return string2;
        }

        public String OnBackgroundResponse(String string2, String string3) {
            return string3;
        }

        public void OnDownloaded(int n, byte[] arrby) {
        }

        public void OnDownloaded(String string2, int n, byte[] arrby) {
        }

        class background_thread
        extends Thread {
            background_thread() {
            }

            /*
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            @Override
            public void run() {
                Object object3;
                Object object2;
                Object object;
                block21 : {
                    String string2;
                    block20 : {
                        block19 : {
                            block18 : {
                                string2 = HttpOp.HTTP_OK;
                                boolean bl = AsyncHttpDownloadFileCipher.this.downloaded > 0L;
                                Object object4 = null;
                                object = null;
                                object3 = null;
                                byte[] arrby = null;
                                byte[] arrby2 = new File(AsyncHttpDownloadFileCipher.this.target);
                                String string3 = AsyncHttpDownloadFileCipher.this.source.replaceAll(" ", "%20");
                                object2 = new URL(string3);
                                object2 = (HttpURLConnection)((URL)object2).openConnection();
                                try {
                                    block17 : {
                                        object = ((URLConnection)object2).getHeaderField("Last-Modified");
                                        object4 = arrby;
                                        if (object != null) {
                                            object4 = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                                            object4 = ((DateFormat)object4).parse((String)object);
                                        }
                                        ((HttpURLConnection)object2).getResponseCode();
                                        ((URLConnection)object2).connect();
                                        int n = ((URLConnection)object2).getContentLength();
                                        object3 = ((URLConnection)object2).getInputStream();
                                        object = new FileOutputStream((File)arrby2, bl);
                                        int n2 = Math.min(n, 1048576);
                                        arrby2 = new byte[n2];
                                        arrby = CipherOp.readMapFile(AsyncHttpDownloadFileCipher.this.map_file);
                                        try {
                                            if (AsyncHttpDownloadFileCipher.this.downloaded > 0L) {
                                                ((InputStream)object3).skip(AsyncHttpDownloadFileCipher.this.downloaded);
                                            } else if (AsyncHttpDownloadFileCipher.this.timestamp.length() > 0) {
                                                ((OutputStream)object).write(AsyncHttpDownloadFileCipher.this.timestamp.getBytes(), 0, AsyncHttpDownloadFileCipher.this.timestamp.length());
                                            }
                                            while ((n = ((InputStream)object3).read(arrby2, 0, n2)) > 0) {
                                                ((OutputStream)object).write(CipherOp.mappingData(arrby, arrby2, n), 0, n);
                                                AsyncHttpDownloadFileCipher.this.OnDownloaded(n, arrby2);
                                                AsyncHttpDownloadFileCipher.this.OnDownloaded(AsyncHttpDownloadFileCipher.this.id, n, arrby2);
                                            }
                                            break block17;
                                        }
                                        catch (Exception exception) {}
                                        catch (IOException iOException) {
                                            object2 = object4;
                                            break block18;
                                        }
                                        catch (FileNotFoundException fileNotFoundException) {
                                            object2 = object;
                                            break block19;
                                        }
                                        catch (ParseException parseException) {
                                            object2 = object3;
                                            break block20;
                                        }
                                    }
                                    ((OutputStream)object).flush();
                                    ((OutputStream)object).close();
                                    ((InputStream)object3).close();
                                    ((HttpURLConnection)object2).disconnect();
                                    object = string2;
                                    object3 = object2;
                                    if (object4 != null) {
                                        FileOp.setFileLastModified(AsyncHttpDownloadFileCipher.this.target, ((Date)object4).getTime());
                                        object = string2;
                                        object3 = object2;
                                    }
                                    break block21;
                                }
                                catch (ParseException parseException) {
                                    break block20;
                                }
                                catch (IOException iOException) {
                                }
                                catch (FileNotFoundException fileNotFoundException) {
                                    break block19;
                                }
                            }
                            string2 = ((Throwable)((Object)string2)).getMessage();
                            break block20;
                        }
                        string2 = ((Throwable)((Object)string2)).getMessage();
                    }
                    object3 = object2;
                    object = string2;
                }
                if (object3 != null) {
                    ((HttpURLConnection)object3).disconnect();
                }
                AsyncHttpDownloadFileCipher.this.OnBackgroundResponse(((String)object).trim());
                object2 = AsyncHttpDownloadFileCipher.this;
                ((AsyncHttpDownloadFileCipher)object2).OnBackgroundResponse(((AsyncHttpDownloadFileCipher)object2).id, ((String)object).trim());
            }
        }

    }

    public static class HttpPostAsync {
        HashMap<String, String> hashMap;
        String request = "";
        String url;

        public HttpPostAsync(HashMap<String, String> hashMap) {
            this.hashMap = hashMap;
            this.url = hashMap.get(HttpOp.HTTP_URL);
            Iterator<String> iterator2 = hashMap.keySet().iterator();
            int n = 0;
            do {
                StringBuilder stringBuilder;
                if (!iterator2.hasNext()) {
                    new background_thread().start();
                    return;
                }
                String string2 = iterator2.next();
                if (HttpOp.HTTP_URL.equals(string2)) continue;
                if (n > 0) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.request);
                    stringBuilder.append("&");
                    this.request = stringBuilder.toString();
                }
                if (hashMap.get(string2) != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.request);
                    stringBuilder.append(string2);
                    stringBuilder.append("=");
                    stringBuilder.append(hashMap.get(string2));
                    this.request = stringBuilder.toString();
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.request);
                    stringBuilder.append(string2);
                    stringBuilder.append("=");
                    this.request = stringBuilder.toString();
                }
                ++n;
            } while (true);
        }

        public String OnBackgroundResponse(String string2) {
            return string2;
        }

        class background_thread
        extends Thread {
            background_thread() {
            }

            @Override
            public void run() {
                String string2 = HttpOp.HttpPost(HttpPostAsync.this.url, HttpPostAsync.this.request, 0);
                HttpPostAsync.this.OnBackgroundResponse(string2.trim());
            }
        }

    }

    public static class HttpUploadFileAsync {
        String php;
        String source;
        String target;

        public HttpUploadFileAsync(String ... arrstring) {
            this.php = arrstring[0];
            this.source = arrstring[1];
            this.target = arrstring[2];
            new background_thread().start();
        }

        public String OnBackgroundResponse(String string2) {
            return string2;
        }

        class background_thread
        extends Thread {
            background_thread() {
            }

            @Override
            public void run() {
                String string2 = HttpOp.HttpUploadFile(HttpUploadFileAsync.this.php, HttpUploadFileAsync.this.source, HttpUploadFileAsync.this.target);
                HttpUploadFileAsync.this.OnBackgroundResponse(string2.trim());
            }
        }

    }

}

