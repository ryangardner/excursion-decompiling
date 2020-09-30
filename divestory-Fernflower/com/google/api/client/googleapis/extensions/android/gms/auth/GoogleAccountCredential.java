package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class GoogleAccountCredential implements HttpRequestInitializer {
   private final GoogleAccountManager accountManager;
   private String accountName;
   private BackOff backOff;
   final Context context;
   final String scope;
   private Account selectedAccount;
   private Sleeper sleeper;

   public GoogleAccountCredential(Context var1, String var2) {
      this.sleeper = Sleeper.DEFAULT;
      this.accountManager = new GoogleAccountManager(var1);
      this.context = var1;
      this.scope = var2;
   }

   public static GoogleAccountCredential usingAudience(Context var0, String var1) {
      boolean var2;
      if (var1.length() != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      StringBuilder var3 = new StringBuilder();
      var3.append("audience:");
      var3.append(var1);
      return new GoogleAccountCredential(var0, var3.toString());
   }

   public static GoogleAccountCredential usingOAuth2(Context var0, Collection<String> var1) {
      boolean var2;
      if (var1 != null && var1.iterator().hasNext()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      StringBuilder var3 = new StringBuilder();
      var3.append("oauth2: ");
      var3.append(Joiner.on(' ').join(var1));
      return new GoogleAccountCredential(var0, var3.toString());
   }

   public final Account[] getAllAccounts() {
      return this.accountManager.getAccounts();
   }

   public BackOff getBackOff() {
      return this.backOff;
   }

   public final Context getContext() {
      return this.context;
   }

   public final GoogleAccountManager getGoogleAccountManager() {
      return this.accountManager;
   }

   public final String getScope() {
      return this.scope;
   }

   public final Account getSelectedAccount() {
      return this.selectedAccount;
   }

   public final String getSelectedAccountName() {
      return this.accountName;
   }

   public final Sleeper getSleeper() {
      return this.sleeper;
   }

   public String getToken() throws IOException, GoogleAuthException {
      BackOff var1 = this.backOff;
      if (var1 != null) {
         var1.reset();
      }

      while(true) {
         try {
            String var5 = GoogleAuthUtil.getToken(this.context, this.accountName, this.scope);
            return var5;
         } catch (IOException var3) {
            IOException var4 = var3;

            try {
               if (this.backOff == null || !BackOffUtils.next(this.sleeper, this.backOff)) {
                  throw var4;
               }
            } catch (InterruptedException var2) {
            }
         }
      }
   }

   public void initialize(HttpRequest var1) {
      GoogleAccountCredential.RequestHandler var2 = new GoogleAccountCredential.RequestHandler();
      var1.setInterceptor(var2);
      var1.setUnsuccessfulResponseHandler(var2);
   }

   public final Intent newChooseAccountIntent() {
      return AccountPicker.newChooseAccountIntent(this.selectedAccount, (ArrayList)null, new String[]{"com.google"}, true, (String)null, (String)null, (String[])null, (Bundle)null);
   }

   public GoogleAccountCredential setBackOff(BackOff var1) {
      this.backOff = var1;
      return this;
   }

   public final GoogleAccountCredential setSelectedAccount(Account var1) {
      this.selectedAccount = var1;
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.name;
      }

      this.accountName = var2;
      return this;
   }

   public final GoogleAccountCredential setSelectedAccountName(String var1) {
      Account var2 = this.accountManager.getAccountByName(var1);
      this.selectedAccount = var2;
      if (var2 == null) {
         var1 = null;
      }

      this.accountName = var1;
      return this;
   }

   public final GoogleAccountCredential setSleeper(Sleeper var1) {
      this.sleeper = (Sleeper)Preconditions.checkNotNull(var1);
      return this;
   }

   class RequestHandler implements HttpExecuteInterceptor, HttpUnsuccessfulResponseHandler {
      boolean received401;
      String token;

      public boolean handleResponse(HttpRequest var1, HttpResponse var2, boolean var3) throws IOException {
         try {
            if (var2.getStatusCode() == 401 && !this.received401) {
               this.received401 = true;
               GoogleAuthUtil.clearToken(GoogleAccountCredential.this.context, this.token);
               return true;
            } else {
               return false;
            }
         } catch (GoogleAuthException var4) {
            throw new GoogleAuthIOException(var4);
         }
      }

      public void intercept(HttpRequest var1) throws IOException {
         try {
            this.token = GoogleAccountCredential.this.getToken();
            HttpHeaders var2 = var1.getHeaders();
            StringBuilder var6 = new StringBuilder();
            var6.append("Bearer ");
            var6.append(this.token);
            var2.setAuthorization(var6.toString());
         } catch (GooglePlayServicesAvailabilityException var3) {
            throw new GooglePlayServicesAvailabilityIOException(var3);
         } catch (UserRecoverableAuthException var4) {
            throw new UserRecoverableAuthIOException(var4);
         } catch (GoogleAuthException var5) {
            throw new GoogleAuthIOException(var5);
         }
      }
   }
}
