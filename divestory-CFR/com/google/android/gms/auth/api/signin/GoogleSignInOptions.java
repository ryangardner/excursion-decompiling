/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.auth.api.signin.zac;
import com.google.android.gms.auth.api.signin.zad;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions
extends AbstractSafeParcelable
implements Api.ApiOptions.Optional,
ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    public static final Scope zaa;
    public static final Scope zab;
    public static final Scope zac;
    public static final Scope zad;
    public static final Scope zae;
    private static Comparator<Scope> zaq;
    private final int zaf;
    private final ArrayList<Scope> zag;
    private Account zah;
    private boolean zai;
    private final boolean zaj;
    private final boolean zak;
    private String zal;
    private String zam;
    private ArrayList<GoogleSignInOptionsExtensionParcelable> zan;
    private String zao;
    private Map<Integer, GoogleSignInOptionsExtensionParcelable> zap;

    static {
        zaa = new Scope("profile");
        zab = new Scope("email");
        zac = new Scope("openid");
        zad = new Scope("https://www.googleapis.com/auth/games_lite");
        zae = new Scope("https://www.googleapis.com/auth/games");
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(zad, new Scope[0]).build();
        CREATOR = new zad();
        zaq = new zac();
    }

    GoogleSignInOptions(int n, ArrayList<Scope> arrayList, Account account, boolean bl, boolean bl2, boolean bl3, String string2, String string3, ArrayList<GoogleSignInOptionsExtensionParcelable> arrayList2, String string4) {
        this(n, arrayList, account, bl, bl2, bl3, string2, string3, GoogleSignInOptions.zab(arrayList2), string4);
    }

    private GoogleSignInOptions(int n, ArrayList<Scope> arrayList, Account account, boolean bl, boolean bl2, boolean bl3, String string2, String string3, Map<Integer, GoogleSignInOptionsExtensionParcelable> map, String string4) {
        this.zaf = n;
        this.zag = arrayList;
        this.zah = account;
        this.zai = bl;
        this.zaj = bl2;
        this.zak = bl3;
        this.zal = string2;
        this.zam = string3;
        this.zan = new ArrayList<GoogleSignInOptionsExtensionParcelable>(map.values());
        this.zap = map;
        this.zao = string4;
    }

    /* synthetic */ GoogleSignInOptions(int n, ArrayList arrayList, Account account, boolean bl, boolean bl2, boolean bl3, String string2, String string3, Map map, String string4, zac zac2) {
        this(3, (ArrayList<Scope>)arrayList, account, bl, bl2, bl3, string2, string3, map, string4);
    }

    public static GoogleSignInOptions zaa(String string2) throws JSONException {
        boolean bl = TextUtils.isEmpty((CharSequence)string2);
        String string3 = null;
        if (bl) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string2);
        Object object = new HashSet<Scope>();
        string2 = jSONObject.getJSONArray("scopes");
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            object.add(new Scope(string2.getString(i)));
        }
        string2 = jSONObject.has("accountName") ? jSONObject.optString("accountName") : null;
        string2 = !TextUtils.isEmpty((CharSequence)string2) ? new Account(string2, "com.google") : null;
        ArrayList<Scope> arrayList = new ArrayList<Scope>((Collection<Scope>)object);
        boolean bl2 = jSONObject.getBoolean("idTokenRequested");
        bl = jSONObject.getBoolean("serverAuthRequested");
        boolean bl3 = jSONObject.getBoolean("forceCodeForRefreshToken");
        object = jSONObject.has("serverClientId") ? jSONObject.optString("serverClientId") : null;
        if (!jSONObject.has("hostedDomain")) return new GoogleSignInOptions(3, arrayList, (Account)string2, bl2, bl, bl3, (String)object, string3, new HashMap<Integer, GoogleSignInOptionsExtensionParcelable>(), null);
        string3 = jSONObject.optString("hostedDomain");
        return new GoogleSignInOptions(3, arrayList, (Account)string2, bl2, bl, bl3, (String)object, string3, new HashMap<Integer, GoogleSignInOptionsExtensionParcelable>(), null);
    }

    private static Map<Integer, GoogleSignInOptionsExtensionParcelable> zab(List<GoogleSignInOptionsExtensionParcelable> object) {
        HashMap<Integer, GoogleSignInOptionsExtensionParcelable> hashMap = new HashMap<Integer, GoogleSignInOptionsExtensionParcelable>();
        if (object == null) {
            return hashMap;
        }
        object = object.iterator();
        while (object.hasNext()) {
            GoogleSignInOptionsExtensionParcelable googleSignInOptionsExtensionParcelable = (GoogleSignInOptionsExtensionParcelable)object.next();
            hashMap.put(googleSignInOptionsExtensionParcelable.getType(), googleSignInOptionsExtensionParcelable);
        }
        return hashMap;
    }

    private final JSONObject zab() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zag, zaq);
            ArrayList<Scope> arrayList = this.zag;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Scope scope = arrayList.get(i);
                jSONArray.put((Object)scope.getScopeUri());
            }
            jSONObject.put("scopes", (Object)jSONArray);
            if (this.zah != null) {
                jSONObject.put("accountName", (Object)this.zah.name);
            }
            jSONObject.put("idTokenRequested", this.zai);
            jSONObject.put("forceCodeForRefreshToken", this.zak);
            jSONObject.put("serverAuthRequested", this.zaj);
            if (!TextUtils.isEmpty((CharSequence)this.zal)) {
                jSONObject.put("serverClientId", (Object)this.zal);
            }
            if (TextUtils.isEmpty((CharSequence)this.zam)) return jSONObject;
            jSONObject.put("hostedDomain", (Object)this.zam);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            object = (GoogleSignInOptions)object;
            if (this.zan.size() > 0) return false;
            if (((GoogleSignInOptions)object).zan.size() > 0) {
                return false;
            }
            if (this.zag.size() != ((GoogleSignInOptions)object).getScopes().size()) return false;
            if (!this.zag.containsAll(((GoogleSignInOptions)object).getScopes())) {
                return false;
            }
            if (this.zah == null) {
                if (((GoogleSignInOptions)object).getAccount() != null) return false;
            } else if (!this.zah.equals((Object)((GoogleSignInOptions)object).getAccount())) return false;
            if (TextUtils.isEmpty((CharSequence)this.zal)) {
                if (!TextUtils.isEmpty((CharSequence)((GoogleSignInOptions)object).getServerClientId())) return false;
            } else if (!this.zal.equals(((GoogleSignInOptions)object).getServerClientId())) return false;
            if (this.zak != ((GoogleSignInOptions)object).isForceCodeForRefreshToken()) return false;
            if (this.zai != ((GoogleSignInOptions)object).isIdTokenRequested()) return false;
            if (this.zaj != ((GoogleSignInOptions)object).isServerAuthCodeRequested()) return false;
            boolean bl = TextUtils.equals((CharSequence)this.zao, (CharSequence)((GoogleSignInOptions)object).getLogSessionId());
            if (!bl) return false;
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public Account getAccount() {
        return this.zah;
    }

    public ArrayList<GoogleSignInOptionsExtensionParcelable> getExtensions() {
        return this.zan;
    }

    public String getLogSessionId() {
        return this.zao;
    }

    public Scope[] getScopeArray() {
        ArrayList<Scope> arrayList = this.zag;
        return arrayList.toArray(new Scope[arrayList.size()]);
    }

    public ArrayList<Scope> getScopes() {
        return new ArrayList<Scope>(this.zag);
    }

    public String getServerClientId() {
        return this.zal;
    }

    public int hashCode() {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Scope> arrayList2 = this.zag;
        int n = arrayList2.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                Collections.sort(arrayList);
                return new HashAccumulator().addObject(arrayList).addObject((Object)this.zah).addObject(this.zal).zaa(this.zak).zaa(this.zai).zaa(this.zaj).addObject(this.zao).hash();
            }
            Scope scope = arrayList2.get(n2);
            ++n2;
            arrayList.add(scope.getScopeUri());
        } while (true);
    }

    public boolean isForceCodeForRefreshToken() {
        return this.zak;
    }

    public boolean isIdTokenRequested() {
        return this.zai;
    }

    public boolean isServerAuthCodeRequested() {
        return this.zaj;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaf);
        SafeParcelWriter.writeTypedList(parcel, 2, this.getScopes(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, (Parcelable)this.getAccount(), n, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.isIdTokenRequested());
        SafeParcelWriter.writeBoolean(parcel, 5, this.isServerAuthCodeRequested());
        SafeParcelWriter.writeBoolean(parcel, 6, this.isForceCodeForRefreshToken());
        SafeParcelWriter.writeString(parcel, 7, this.getServerClientId(), false);
        SafeParcelWriter.writeString(parcel, 8, this.zam, false);
        SafeParcelWriter.writeTypedList(parcel, 9, this.getExtensions(), false);
        SafeParcelWriter.writeString(parcel, 10, this.getLogSessionId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final String zaa() {
        return this.zab().toString();
    }

    public static final class Builder {
        private Set<Scope> zaa = new HashSet<Scope>();
        private boolean zab;
        private boolean zac;
        private boolean zad;
        private String zae;
        private Account zaf;
        private String zag;
        private Map<Integer, GoogleSignInOptionsExtensionParcelable> zah = new HashMap<Integer, GoogleSignInOptionsExtensionParcelable>();
        private String zai;

        public Builder() {
        }

        public Builder(GoogleSignInOptions googleSignInOptions) {
            Preconditions.checkNotNull(googleSignInOptions);
            this.zaa = new HashSet<Scope>(googleSignInOptions.zag);
            this.zab = googleSignInOptions.zaj;
            this.zac = googleSignInOptions.zak;
            this.zad = googleSignInOptions.zai;
            this.zae = googleSignInOptions.zal;
            this.zaf = googleSignInOptions.zah;
            this.zag = googleSignInOptions.zam;
            this.zah = GoogleSignInOptions.zab(googleSignInOptions.zan);
            this.zai = googleSignInOptions.zao;
        }

        private final String zaa(String string2) {
            Preconditions.checkNotEmpty(string2);
            String string3 = this.zae;
            boolean bl = string3 == null || string3.equals(string2);
            Preconditions.checkArgument(bl, "two different server client ids provided");
            return string2;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zah.containsKey(googleSignInOptionsExtension.getExtensionType())) throw new IllegalStateException("Only one extension per type may be added");
            List<Scope> list = googleSignInOptionsExtension.getImpliedScopes();
            if (list != null) {
                this.zaa.addAll(list);
            }
            this.zah.put(googleSignInOptionsExtension.getExtensionType(), new GoogleSignInOptionsExtensionParcelable(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.zaa.contains(zae) && this.zaa.contains(zad)) {
                this.zaa.remove(zad);
            }
            if (!this.zad) return new GoogleSignInOptions(3, new ArrayList<Scope>(this.zaa), this.zaf, this.zad, this.zab, this.zac, this.zae, this.zag, this.zah, this.zai, null);
            if (this.zaf != null) {
                if (this.zaa.isEmpty()) return new GoogleSignInOptions(3, new ArrayList<Scope>(this.zaa), this.zaf, this.zad, this.zab, this.zac, this.zae, this.zag, this.zah, this.zai, null);
            }
            this.requestId();
            return new GoogleSignInOptions(3, new ArrayList<Scope>(this.zaa), this.zaf, this.zad, this.zab, this.zac, this.zae, this.zag, this.zah, this.zai, null);
        }

        public final Builder requestEmail() {
            this.zaa.add(zab);
            return this;
        }

        public final Builder requestId() {
            this.zaa.add(zac);
            return this;
        }

        public final Builder requestIdToken(String string2) {
            this.zad = true;
            this.zae = this.zaa(string2);
            return this;
        }

        public final Builder requestProfile() {
            this.zaa.add(zaa);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope ... arrscope) {
            this.zaa.add(scope);
            this.zaa.addAll(Arrays.asList(arrscope));
            return this;
        }

        public final Builder requestServerAuthCode(String string2) {
            return this.requestServerAuthCode(string2, false);
        }

        public final Builder requestServerAuthCode(String string2, boolean bl) {
            this.zab = true;
            this.zae = this.zaa(string2);
            this.zac = bl;
            return this;
        }

        public final Builder setAccountName(String string2) {
            this.zaf = new Account(Preconditions.checkNotEmpty(string2), "com.google");
            return this;
        }

        public final Builder setHostedDomain(String string2) {
            this.zag = Preconditions.checkNotEmpty(string2);
            return this;
        }

        public final Builder setLogSessionId(String string2) {
            this.zai = string2;
            return this;
        }
    }

}

