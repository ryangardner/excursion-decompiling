/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.content.Intent
 *  com.google.android.gms.auth.GoogleAuthException
 *  com.google.android.gms.auth.GoogleAuthUtil
 *  com.google.android.gms.auth.GooglePlayServicesAvailabilityException
 *  com.google.android.gms.auth.UserRecoverableAuthException
 */
package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
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
import java.util.Collection;
import java.util.Iterator;

public class GoogleAccountCredential
implements HttpRequestInitializer {
    private final GoogleAccountManager accountManager;
    private String accountName;
    private BackOff backOff;
    final Context context;
    final String scope;
    private Account selectedAccount;
    private Sleeper sleeper = Sleeper.DEFAULT;

    public GoogleAccountCredential(Context context, String string2) {
        this.accountManager = new GoogleAccountManager(context);
        this.context = context;
        this.scope = string2;
    }

    public static GoogleAccountCredential usingAudience(Context context, String string2) {
        boolean bl = string2.length() != 0;
        Preconditions.checkArgument(bl);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("audience:");
        stringBuilder.append(string2);
        return new GoogleAccountCredential(context, stringBuilder.toString());
    }

    public static GoogleAccountCredential usingOAuth2(Context context, Collection<String> collection) {
        boolean bl = collection != null && collection.iterator().hasNext();
        Preconditions.checkArgument(bl);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("oauth2: ");
        stringBuilder.append(Joiner.on(' ').join(collection));
        return new GoogleAccountCredential(context, stringBuilder.toString());
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
        BackOff backOff = this.backOff;
        if (backOff != null) {
            backOff.reset();
        }
        do {
            try {
                return GoogleAuthUtil.getToken((Context)this.context, (String)this.accountName, (String)this.scope);
            }
            catch (IOException iOException) {
                try {
                    if (this.backOff == null) throw iOException;
                    if (!BackOffUtils.next(this.sleeper, this.backOff)) throw iOException;
                }
                catch (InterruptedException interruptedException) {
                }
                continue;
            }
            break;
        } while (true);
    }

    @Override
    public void initialize(HttpRequest httpRequest) {
        RequestHandler requestHandler = new RequestHandler();
        httpRequest.setInterceptor(requestHandler);
        httpRequest.setUnsuccessfulResponseHandler(requestHandler);
    }

    public final Intent newChooseAccountIntent() {
        return AccountPicker.newChooseAccountIntent(this.selectedAccount, null, new String[]{"com.google"}, true, null, null, null, null);
    }

    public GoogleAccountCredential setBackOff(BackOff backOff) {
        this.backOff = backOff;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccount(Account object) {
        this.selectedAccount = object;
        object = object == null ? null : object.name;
        this.accountName = object;
        return this;
    }

    public final GoogleAccountCredential setSelectedAccountName(String string2) {
        Account account;
        this.selectedAccount = account = this.accountManager.getAccountByName(string2);
        if (account == null) {
            string2 = null;
        }
        this.accountName = string2;
        return this;
    }

    public final GoogleAccountCredential setSleeper(Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }

    class RequestHandler
    implements HttpExecuteInterceptor,
    HttpUnsuccessfulResponseHandler {
        boolean received401;
        String token;

        RequestHandler() {
        }

        @Override
        public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean bl) throws IOException {
            try {
                if (httpResponse.getStatusCode() != 401) return false;
                if (this.received401) return false;
                this.received401 = true;
                GoogleAuthUtil.clearToken((Context)GoogleAccountCredential.this.context, (String)this.token);
                return true;
            }
            catch (GoogleAuthException googleAuthException) {
                throw new GoogleAuthIOException(googleAuthException);
            }
        }

        @Override
        public void intercept(HttpRequest object) throws IOException {
            try {
                this.token = GoogleAccountCredential.this.getToken();
                HttpHeaders httpHeaders = ((HttpRequest)object).getHeaders();
                object = new StringBuilder();
                ((StringBuilder)object).append("Bearer ");
                ((StringBuilder)object).append(this.token);
                httpHeaders.setAuthorization(((StringBuilder)object).toString());
                return;
            }
            catch (GoogleAuthException googleAuthException) {
                throw new GoogleAuthIOException(googleAuthException);
            }
            catch (UserRecoverableAuthException userRecoverableAuthException) {
                throw new UserRecoverableAuthIOException(userRecoverableAuthException);
            }
            catch (GooglePlayServicesAvailabilityException googlePlayServicesAvailabilityException) {
                throw new GooglePlayServicesAvailabilityIOException(googlePlayServicesAvailabilityException);
            }
        }
    }

}

