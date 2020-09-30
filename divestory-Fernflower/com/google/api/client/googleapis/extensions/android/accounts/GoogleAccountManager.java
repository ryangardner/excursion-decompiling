package com.google.api.client.googleapis.extensions.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.google.api.client.util.Preconditions;

public final class GoogleAccountManager {
   public static final String ACCOUNT_TYPE = "com.google";
   private final AccountManager manager;

   public GoogleAccountManager(AccountManager var1) {
      this.manager = (AccountManager)Preconditions.checkNotNull(var1);
   }

   public GoogleAccountManager(Context var1) {
      this(AccountManager.get(var1));
   }

   public Account getAccountByName(String var1) {
      if (var1 != null) {
         Account[] var2 = this.getAccounts();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Account var5 = var2[var4];
            if (var1.equals(var5.name)) {
               return var5;
            }
         }
      }

      return null;
   }

   public AccountManager getAccountManager() {
      return this.manager;
   }

   public Account[] getAccounts() {
      return this.manager.getAccountsByType("com.google");
   }

   public void invalidateAuthToken(String var1) {
      this.manager.invalidateAuthToken("com.google", var1);
   }
}
