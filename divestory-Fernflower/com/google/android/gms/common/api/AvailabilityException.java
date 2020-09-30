package com.google.android.gms.common.api;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;

public class AvailabilityException extends Exception {
   private final ArrayMap<ApiKey<?>, ConnectionResult> zaa;

   public AvailabilityException(ArrayMap<ApiKey<?>, ConnectionResult> var1) {
      this.zaa = var1;
   }

   public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> var1) {
      ApiKey var2 = var1.getApiKey();
      boolean var3;
      if (this.zaa.get(var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      String var4 = var2.getApiName();
      StringBuilder var5 = new StringBuilder(String.valueOf(var4).length() + 58);
      var5.append("The given API (");
      var5.append(var4);
      var5.append(") was not part of the availability request.");
      Preconditions.checkArgument(var3, var5.toString());
      return (ConnectionResult)Preconditions.checkNotNull((ConnectionResult)this.zaa.get(var2));
   }

   public ConnectionResult getConnectionResult(HasApiKey<? extends Api.ApiOptions> var1) {
      ApiKey var2 = var1.getApiKey();
      boolean var3;
      if (this.zaa.get(var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      String var4 = var2.getApiName();
      StringBuilder var5 = new StringBuilder(String.valueOf(var4).length() + 58);
      var5.append("The given API (");
      var5.append(var4);
      var5.append(") was not part of the availability request.");
      Preconditions.checkArgument(var3, var5.toString());
      return (ConnectionResult)Preconditions.checkNotNull((ConnectionResult)this.zaa.get(var2));
   }

   public String getMessage() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.zaa.keySet().iterator();
      boolean var3 = true;

      while(var2.hasNext()) {
         ApiKey var4 = (ApiKey)var2.next();
         ConnectionResult var5 = (ConnectionResult)Preconditions.checkNotNull((ConnectionResult)this.zaa.get(var4));
         if (var5.isSuccess()) {
            var3 = false;
         }

         String var8 = var4.getApiName();
         String var6 = String.valueOf(var5);
         StringBuilder var9 = new StringBuilder(String.valueOf(var8).length() + 2 + String.valueOf(var6).length());
         var9.append(var8);
         var9.append(": ");
         var9.append(var6);
         var1.add(var9.toString());
      }

      StringBuilder var7 = new StringBuilder();
      if (var3) {
         var7.append("None of the queried APIs are available. ");
      } else {
         var7.append("Some of the queried APIs are unavailable. ");
      }

      var7.append(TextUtils.join("; ", var1));
      return var7.toString();
   }
}
