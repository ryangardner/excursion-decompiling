package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class Storage {
   private static final Lock zaa = new ReentrantLock();
   private static Storage zab;
   private final Lock zac = new ReentrantLock();
   private final SharedPreferences zad;

   private Storage(Context var1) {
      this.zad = var1.getSharedPreferences("com.google.android.gms.signin", 0);
   }

   public static Storage getInstance(Context var0) {
      Preconditions.checkNotNull(var0);
      zaa.lock();

      Storage var4;
      try {
         if (zab == null) {
            Storage var1 = new Storage(var0.getApplicationContext());
            zab = var1;
         }

         var4 = zab;
      } finally {
         zaa.unlock();
      }

      return var4;
   }

   private final GoogleSignInAccount zaa(String var1) {
      if (TextUtils.isEmpty(var1)) {
         return null;
      } else {
         var1 = this.zac(zab("googleSignInAccount", var1));
         if (var1 != null) {
            try {
               GoogleSignInAccount var3 = GoogleSignInAccount.zaa(var1);
               return var3;
            } catch (JSONException var2) {
            }
         }

         return null;
      }
   }

   private final void zaa(String var1, String var2) {
      this.zac.lock();

      try {
         this.zad.edit().putString(var1, var2).apply();
      } finally {
         this.zac.unlock();
      }

   }

   private final GoogleSignInOptions zab(String var1) {
      if (TextUtils.isEmpty(var1)) {
         return null;
      } else {
         var1 = this.zac(zab("googleSignInOptions", var1));
         if (var1 != null) {
            try {
               GoogleSignInOptions var3 = GoogleSignInOptions.zaa(var1);
               return var3;
            } catch (JSONException var2) {
            }
         }

         return null;
      }
   }

   private static String zab(String var0, String var1) {
      StringBuilder var2 = new StringBuilder(String.valueOf(var0).length() + 1 + String.valueOf(var1).length());
      var2.append(var0);
      var2.append(":");
      var2.append(var1);
      return var2.toString();
   }

   private final String zac(String var1) {
      this.zac.lock();

      try {
         var1 = this.zad.getString(var1, (String)null);
      } finally {
         this.zac.unlock();
      }

      return var1;
   }

   private final void zad(String var1) {
      this.zac.lock();

      try {
         this.zad.edit().remove(var1).apply();
      } finally {
         this.zac.unlock();
      }

   }

   public void clear() {
      this.zac.lock();

      try {
         this.zad.edit().clear().apply();
      } finally {
         this.zac.unlock();
      }

   }

   public GoogleSignInAccount getSavedDefaultGoogleSignInAccount() {
      return this.zaa(this.zac("defaultGoogleSignInAccount"));
   }

   public GoogleSignInOptions getSavedDefaultGoogleSignInOptions() {
      return this.zab(this.zac("defaultGoogleSignInAccount"));
   }

   public String getSavedRefreshToken() {
      return this.zac("refreshToken");
   }

   public void saveDefaultGoogleSignInAccount(GoogleSignInAccount var1, GoogleSignInOptions var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      this.zaa("defaultGoogleSignInAccount", var1.zaa());
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      String var3 = var1.zaa();
      this.zaa(zab("googleSignInAccount", var3), var1.zab());
      this.zaa(zab("googleSignInOptions", var3), var2.zaa());
   }

   public final void zaa() {
      String var1 = this.zac("defaultGoogleSignInAccount");
      this.zad("defaultGoogleSignInAccount");
      if (!TextUtils.isEmpty(var1)) {
         this.zad(zab("googleSignInAccount", var1));
         this.zad(zab("googleSignInOptions", var1));
      }

   }
}
