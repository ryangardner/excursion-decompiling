/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountManager
 *  android.content.Context
 */
package com.google.api.client.googleapis.extensions.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.google.api.client.util.Preconditions;

public final class GoogleAccountManager {
    public static final String ACCOUNT_TYPE = "com.google";
    private final AccountManager manager;

    public GoogleAccountManager(AccountManager accountManager) {
        this.manager = Preconditions.checkNotNull(accountManager);
    }

    public GoogleAccountManager(Context context) {
        this(AccountManager.get((Context)context));
    }

    public Account getAccountByName(String string2) {
        if (string2 == null) return null;
        Account[] arraccount = this.getAccounts();
        int n = arraccount.length;
        int n2 = 0;
        while (n2 < n) {
            Account account = arraccount[n2];
            if (string2.equals(account.name)) {
                return account;
            }
            ++n2;
        }
        return null;
    }

    public AccountManager getAccountManager() {
        return this.manager;
    }

    public Account[] getAccounts() {
        return this.manager.getAccountsByType(ACCOUNT_TYPE);
    }

    public void invalidateAuthToken(String string2) {
        this.manager.invalidateAuthToken(ACCOUNT_TYPE, string2);
    }
}

