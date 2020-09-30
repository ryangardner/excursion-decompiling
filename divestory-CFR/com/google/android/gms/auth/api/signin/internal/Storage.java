/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  org.json.JSONException
 */
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

    private Storage(Context context) {
        this.zad = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static Storage getInstance(Context object) {
        Preconditions.checkNotNull(object);
        zaa.lock();
        try {
            if (zab == null) {
                Storage storage;
                zab = storage = new Storage(object.getApplicationContext());
            }
            object = zab;
            return object;
        }
        finally {
            zaa.unlock();
        }
    }

    private final GoogleSignInAccount zaa(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zac(Storage.zab("googleSignInAccount", (String)object))) == null) return null;
        try {
            return GoogleSignInAccount.zaa((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    private final void zaa(String string2, String string3) {
        this.zac.lock();
        try {
            this.zad.edit().putString(string2, string3).apply();
            return;
        }
        finally {
            this.zac.unlock();
        }
    }

    private final GoogleSignInOptions zab(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zac(Storage.zab("googleSignInOptions", (String)object))) == null) return null;
        try {
            return GoogleSignInOptions.zaa((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    private static String zab(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(string3).length());
        stringBuilder.append(string2);
        stringBuilder.append(":");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    private final String zac(String string2) {
        this.zac.lock();
        try {
            string2 = this.zad.getString(string2, null);
            return string2;
        }
        finally {
            this.zac.unlock();
        }
    }

    private final void zad(String string2) {
        this.zac.lock();
        try {
            this.zad.edit().remove(string2).apply();
            return;
        }
        finally {
            this.zac.unlock();
        }
    }

    public void clear() {
        this.zac.lock();
        try {
            this.zad.edit().clear().apply();
            return;
        }
        finally {
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

    public void saveDefaultGoogleSignInAccount(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        Preconditions.checkNotNull(googleSignInAccount);
        Preconditions.checkNotNull(googleSignInOptions);
        this.zaa("defaultGoogleSignInAccount", googleSignInAccount.zaa());
        Preconditions.checkNotNull(googleSignInAccount);
        Preconditions.checkNotNull(googleSignInOptions);
        String string2 = googleSignInAccount.zaa();
        this.zaa(Storage.zab("googleSignInAccount", string2), googleSignInAccount.zab());
        this.zaa(Storage.zab("googleSignInOptions", string2), googleSignInOptions.zaa());
    }

    public final void zaa() {
        String string2 = this.zac("defaultGoogleSignInAccount");
        this.zad("defaultGoogleSignInAccount");
        if (TextUtils.isEmpty((CharSequence)string2)) return;
        this.zad(Storage.zab("googleSignInAccount", string2));
        this.zad(Storage.zab("googleSignInOptions", string2));
    }
}

