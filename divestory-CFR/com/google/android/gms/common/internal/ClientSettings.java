/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import androidx.collection.ArraySet;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public final class ClientSettings {
    @Nullable
    private final Account zaa;
    private final Set<Scope> zab;
    private final Set<Scope> zac;
    private final Map<Api<?>, zaa> zad;
    private final int zae;
    private final View zaf;
    private final String zag;
    private final String zah;
    private final SignInOptions zai;
    private final boolean zaj;
    private Integer zak;

    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, zaa> map, int n, View view, String string2, String string3, SignInOptions signInOptions) {
        this(account, set, map, n, view, string2, string3, signInOptions, false);
    }

    public ClientSettings(@Nullable Account object, Set<Scope> set, Map<Api<?>, zaa> map, int n, View view, String string2, String string3, SignInOptions signInOptions, boolean bl) {
        this.zaa = object;
        object = set == null ? Collections.emptySet() : Collections.unmodifiableSet(set);
        this.zab = object;
        object = map;
        if (map == null) {
            object = Collections.emptyMap();
        }
        this.zad = object;
        this.zaf = view;
        this.zae = n;
        this.zag = string2;
        this.zah = string3;
        this.zai = signInOptions;
        this.zaj = false;
        set = new HashSet<Scope>(this.zab);
        object = this.zad.values().iterator();
        do {
            if (!object.hasNext()) {
                this.zac = Collections.unmodifiableSet(set);
                return;
            }
            set.addAll(((zaa)object.next()).zaa);
        } while (true);
    }

    public static ClientSettings createDefault(Context context) {
        return new GoogleApiClient.Builder(context).buildClientSettings();
    }

    public final Account getAccount() {
        return this.zaa;
    }

    @Deprecated
    public final String getAccountName() {
        Account account = this.zaa;
        if (account == null) return null;
        return account.name;
    }

    public final Account getAccountOrDefault() {
        Account account = this.zaa;
        if (account == null) return new Account("<<default account>>", "com.google");
        return account;
    }

    public final Set<Scope> getAllRequestedScopes() {
        return this.zac;
    }

    public final Set<Scope> getApplicableScopes(Api<?> object) {
        zaa zaa2 = this.zad.get(object);
        if (zaa2 == null) return this.zab;
        if (zaa2.zaa.isEmpty()) {
            return this.zab;
        }
        object = new HashSet<Scope>(this.zab);
        object.addAll(zaa2.zaa);
        return object;
    }

    public final int getGravityForPopups() {
        return this.zae;
    }

    public final String getRealClientPackageName() {
        return this.zag;
    }

    public final Set<Scope> getRequiredScopes() {
        return this.zab;
    }

    public final View getViewForPopups() {
        return this.zaf;
    }

    public final Map<Api<?>, zaa> zaa() {
        return this.zad;
    }

    public final void zaa(Integer n) {
        this.zak = n;
    }

    public final String zab() {
        return this.zah;
    }

    public final SignInOptions zac() {
        return this.zai;
    }

    public final Integer zad() {
        return this.zak;
    }

    public static final class Builder {
        @Nullable
        private Account zaa;
        private ArraySet<Scope> zab;
        private int zac = 0;
        private String zad;
        private String zae;
        private SignInOptions zaf = SignInOptions.zaa;

        public final ClientSettings build() {
            return new ClientSettings(this.zaa, this.zab, null, 0, null, this.zad, this.zae, this.zaf, false);
        }

        public final Builder setRealClientPackageName(String string2) {
            this.zad = string2;
            return this;
        }

        public final Builder zaa(@Nullable Account account) {
            this.zaa = account;
            return this;
        }

        public final Builder zaa(String string2) {
            this.zae = string2;
            return this;
        }

        public final Builder zaa(Collection<Scope> collection) {
            if (this.zab == null) {
                this.zab = new ArraySet();
            }
            this.zab.addAll(collection);
            return this;
        }
    }

    public static final class zaa {
        public final Set<Scope> zaa;

        public zaa(Set<Scope> set) {
            Preconditions.checkNotNull(set);
            this.zaa = Collections.unmodifiableSet(set);
        }
    }

}

