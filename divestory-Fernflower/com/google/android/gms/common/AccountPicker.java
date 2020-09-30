package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;

public final class AccountPicker {
   private AccountPicker() {
   }

   @Deprecated
   public static Intent newChooseAccountIntent(Account var0, ArrayList<Account> var1, String[] var2, boolean var3, String var4, String var5, String[] var6, Bundle var7) {
      Intent var8 = new Intent();
      Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
      var8.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
      var8.setPackage("com.google.android.gms");
      var8.putExtra("allowableAccounts", var1);
      var8.putExtra("allowableAccountTypes", var2);
      var8.putExtra("addAccountOptions", var7);
      var8.putExtra("selectedAccount", var0);
      var8.putExtra("alwaysPromptForAccount", var3);
      var8.putExtra("descriptionTextOverride", var4);
      var8.putExtra("authTokenType", var5);
      var8.putExtra("addAccountRequiredFeatures", var6);
      var8.putExtra("setGmsCoreAccount", false);
      var8.putExtra("overrideTheme", 0);
      var8.putExtra("overrideCustomTheme", 0);
      var8.putExtra("hostedDomainFilter", (String)null);
      return var8;
   }

   public static Intent newChooseAccountIntent(AccountPicker.AccountChooserOptions var0) {
      Intent var1 = new Intent();
      Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
      Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
      Preconditions.checkArgument(true, "Making the selected account non-clickable is only supported for the theme THEME_DAY_NIGHT_GOOGLE_MATERIAL2");
      var1.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
      var1.setPackage("com.google.android.gms");
      var1.putExtra("allowableAccounts", var0.zzc);
      if (var0.zzd != null) {
         var1.putExtra("allowableAccountTypes", (String[])var0.zzd.toArray(new String[0]));
      }

      var1.putExtra("addAccountOptions", var0.zzg);
      var1.putExtra("selectedAccount", var0.zza);
      var1.putExtra("selectedAccountIsNotClickable", false);
      var1.putExtra("alwaysPromptForAccount", var0.zze);
      var1.putExtra("descriptionTextOverride", var0.zzf);
      var1.putExtra("setGmsCoreAccount", false);
      var1.putExtra("realClientPackage", (String)null);
      var1.putExtra("overrideTheme", 0);
      var1.putExtra("overrideCustomTheme", 0);
      var1.putExtra("hostedDomainFilter", (String)null);
      Bundle var2 = new Bundle();
      if (!var2.isEmpty()) {
         var1.putExtra("first_party_options_bundle", var2);
      }

      return var1;
   }

   public static class AccountChooserOptions {
      private Account zza;
      private boolean zzb;
      private ArrayList<Account> zzc;
      private ArrayList<String> zzd;
      private boolean zze;
      private String zzf;
      private Bundle zzg;
      private boolean zzh;
      private int zzi;
      private String zzj;
      private boolean zzk;
      private AccountPicker.AccountChooserOptions.zza zzl;
      private String zzm;
      private boolean zzn;

      // $FF: synthetic method
      static int zza(AccountPicker.AccountChooserOptions var0, int var1) {
         var0.zzi = 0;
         return 0;
      }

      // $FF: synthetic method
      static AccountPicker.AccountChooserOptions.zza zza(AccountPicker.AccountChooserOptions var0, AccountPicker.AccountChooserOptions.zza var1) {
         var0.zzl = null;
         return null;
      }

      // $FF: synthetic method
      static String zza(AccountPicker.AccountChooserOptions var0, String var1) {
         var0.zzj = null;
         return null;
      }

      // $FF: synthetic method
      static String zzb(AccountPicker.AccountChooserOptions var0, String var1) {
         var0.zzm = null;
         return null;
      }

      // $FF: synthetic method
      static boolean zzb(AccountPicker.AccountChooserOptions var0, boolean var1) {
         var0.zzb = false;
         return false;
      }

      // $FF: synthetic method
      static boolean zzc(AccountPicker.AccountChooserOptions var0, boolean var1) {
         var0.zzh = false;
         return false;
      }

      // $FF: synthetic method
      static boolean zzd(AccountPicker.AccountChooserOptions var0, boolean var1) {
         var0.zzk = false;
         return false;
      }

      // $FF: synthetic method
      static boolean zze(AccountPicker.AccountChooserOptions var0, boolean var1) {
         var0.zzn = false;
         return false;
      }

      public static class Builder {
         private Account zza;
         private ArrayList<Account> zzb;
         private ArrayList<String> zzc;
         private boolean zzd = false;
         private String zze;
         private Bundle zzf;
         private boolean zzg = false;
         private int zzh = 0;
         private boolean zzi = false;

         public AccountPicker.AccountChooserOptions build() {
            Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
            Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
            AccountPicker.AccountChooserOptions var1 = new AccountPicker.AccountChooserOptions();
            var1.zzd = this.zzc;
            var1.zzc = this.zzb;
            var1.zze = this.zzd;
            AccountPicker.AccountChooserOptions.zza(var1, (AccountPicker.AccountChooserOptions.zza)null);
            AccountPicker.AccountChooserOptions.zza(var1, (String)null);
            var1.zzg = this.zzf;
            var1.zza = this.zza;
            AccountPicker.AccountChooserOptions.zzb(var1, false);
            AccountPicker.AccountChooserOptions.zzc(var1, false);
            AccountPicker.AccountChooserOptions.zzb(var1, (String)null);
            AccountPicker.AccountChooserOptions.zza(var1, 0);
            var1.zzf = this.zze;
            AccountPicker.AccountChooserOptions.zzd(var1, false);
            AccountPicker.AccountChooserOptions.zze(var1, false);
            return var1;
         }

         public AccountPicker.AccountChooserOptions.Builder setAllowableAccounts(List<Account> var1) {
            ArrayList var2;
            if (var1 == null) {
               var2 = null;
            } else {
               var2 = new ArrayList(var1);
            }

            this.zzb = var2;
            return this;
         }

         public AccountPicker.AccountChooserOptions.Builder setAllowableAccountsTypes(List<String> var1) {
            ArrayList var2;
            if (var1 == null) {
               var2 = null;
            } else {
               var2 = new ArrayList(var1);
            }

            this.zzc = var2;
            return this;
         }

         public AccountPicker.AccountChooserOptions.Builder setAlwaysShowAccountPicker(boolean var1) {
            this.zzd = var1;
            return this;
         }

         public AccountPicker.AccountChooserOptions.Builder setOptionsForAddingAccount(Bundle var1) {
            this.zzf = var1;
            return this;
         }

         public AccountPicker.AccountChooserOptions.Builder setSelectedAccount(Account var1) {
            this.zza = var1;
            return this;
         }

         public AccountPicker.AccountChooserOptions.Builder setTitleOverrideText(String var1) {
            this.zze = var1;
            return this;
         }
      }

      public static final class zza {
      }
   }
}
