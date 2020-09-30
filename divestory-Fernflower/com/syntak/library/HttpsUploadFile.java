package com.syntak.library;

import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

   public HttpsUploadFile(String var1, String var2, String var3) {
      this.php = var1;
      this.source_path = var2;
      this.target_name = var3;
      this.init();
   }

   public HttpsUploadFile(String var1, String var2, String var3, String var4, String var5, String var6, String var7) {
      this.php = var1;
      this.source_path = var2;
      this.target_name = var3;
      this.tag_for_key_file = var4;
      this.key_file = var5;
      this.key_folder = var6;
      this.folder = var7;
      this.init();
   }

   private void init() {
      File var1 = new File(this.source_path);
      String var2 = FileOp.getFileExtension(this.source_path);
      var2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var2.toLowerCase());
      MultipartBody.Builder var3 = (new MultipartBody.Builder()).setType(MultipartBody.FORM).addFormDataPart(this.key_file, this.target_name, RequestBody.create(var1, MediaType.parse(var2)));
      var2 = this.tag_for_key_file;
      if (var2 != null) {
         var3.addFormDataPart(var2, this.key_file);
      }

      var2 = this.key_folder;
      if (var2 != null) {
         var3.addFormDataPart(var2, this.folder);
      }

      MultipartBody var5 = var3.build();
      final CountingRequestBody.Listener var4 = new CountingRequestBody.Listener() {
         public void onRequestProgress(long var1, long var3) {
            HttpsUploadFile.this.OnProgress(var1, var3);
         }
      };
      (new OkHttpClient.Builder()).addNetworkInterceptor(new Interceptor() {
         public Response intercept(Interceptor.Chain var1) throws IOException {
            Request var2 = var1.request();
            return var2.body() == null ? var1.proceed(var2) : var1.proceed(var2.newBuilder().method(var2.method(), new CountingRequestBody(var2.body(), var4)).build());
         }
      }).connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).followRedirects(false).followSslRedirects(false).build().newCall((new Request.Builder()).url(this.php).post(var5).build()).enqueue(new Callback() {
         public void onFailure(Call var1, IOException var2) {
            HttpsUploadFile.this.OnResult(var2.getMessage());
            Log.e("failure Response", var2.getMessage().toString());
         }

         public void onResponse(Call var1, Response var2) throws IOException {
            if (var2.isSuccessful()) {
               HttpsUploadFile.this.result = "HTTPS OK";
            } else if (var2.code() != 302) {
               HttpsUploadFile.this.result = var2.message();
            }

            HttpsUploadFile var3 = HttpsUploadFile.this;
            var3.OnResult(var3.result);
         }
      });
   }

   public void OnProgress(long var1, long var3) {
   }

   public void OnResult(String var1) {
   }
}
