/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.webkit.MimeTypeMap
 */
package com.syntak.library;

import android.util.Log;
import android.webkit.MimeTypeMap;
import com.syntak.library.CountingRequestBody;
import com.syntak.library.FileOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpsUploadFile {
    public static final String HTTPS_ERROR = "HTTPS ERROR";
    public static final String HTTPS_OK = "HTTPS OK";
    String folder = null;
    String key_file = "uploaded_file";
    String key_folder = null;
    String php;
    String result = "HTTPS OK";
    String source_path;
    String tag_for_key_file = null;
    String target_name;

    public HttpsUploadFile(String string2, String string3, String string4) {
        this.php = string2;
        this.source_path = string3;
        this.target_name = string4;
        this.init();
    }

    public HttpsUploadFile(String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        this.php = string2;
        this.source_path = string3;
        this.target_name = string4;
        this.tag_for_key_file = string5;
        this.key_file = string6;
        this.key_folder = string7;
        this.folder = string8;
        this.init();
    }

    private void init() {
        Object object = new File(this.source_path);
        Object object2 = FileOp.getFileExtension(this.source_path);
        object2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(((String)object2).toLowerCase());
        object = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(this.key_file, this.target_name, RequestBody.create((File)object, MediaType.parse((String)object2)));
        object2 = this.tag_for_key_file;
        if (object2 != null) {
            ((MultipartBody.Builder)object).addFormDataPart((String)object2, this.key_file);
        }
        if ((object2 = this.key_folder) != null) {
            ((MultipartBody.Builder)object).addFormDataPart((String)object2, this.folder);
        }
        object2 = ((MultipartBody.Builder)object).build();
        object = new CountingRequestBody.Listener(){

            @Override
            public void onRequestProgress(long l, long l2) {
                HttpsUploadFile.this.OnProgress(l, l2);
            }
        };
        new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor((CountingRequestBody.Listener)object){
            final /* synthetic */ CountingRequestBody.Listener val$progressListener;
            {
                this.val$progressListener = listener;
            }

            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                if (request.body() != null) return chain.proceed(request.newBuilder().method(request.method(), new CountingRequestBody(request.body(), this.val$progressListener)).build());
                return chain.proceed(request);
            }
        }).connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).followRedirects(false).followSslRedirects(false).build().newCall(new Request.Builder().url(this.php).post((RequestBody)object2).build()).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException iOException) {
                HttpsUploadFile.this.OnResult(iOException.getMessage());
                Log.e((String)"failure Response", (String)iOException.getMessage().toString());
            }

            @Override
            public void onResponse(Call object, Response response) throws IOException {
                if (response.isSuccessful()) {
                    HttpsUploadFile.this.result = HttpsUploadFile.HTTPS_OK;
                } else if (response.code() != 302) {
                    HttpsUploadFile.this.result = response.message();
                }
                object = HttpsUploadFile.this;
                ((HttpsUploadFile)object).OnResult(((HttpsUploadFile)object).result);
            }
        });
    }

    public void OnProgress(long l, long l2) {
    }

    public void OnResult(String string2) {
    }

}

