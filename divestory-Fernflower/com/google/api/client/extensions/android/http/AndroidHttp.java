package com.google.api.client.extensions.android.http;

import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

@Deprecated
public class AndroidHttp {
   private AndroidHttp() {
   }

   public static HttpTransport newCompatibleTransport() {
      Object var0;
      if (AndroidUtils.isMinimumSdkLevel(9)) {
         var0 = new NetHttpTransport();
      } else {
         var0 = new ApacheHttpTransport();
      }

      return (HttpTransport)var0;
   }
}
