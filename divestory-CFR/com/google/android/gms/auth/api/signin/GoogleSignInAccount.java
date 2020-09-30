/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.net.Uri
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
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.zaa;
import com.google.android.gms.auth.api.signin.zab;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zab();
    private static Clock zaa = DefaultClock.getInstance();
    private final int zab;
    private String zac;
    private String zad;
    private String zae;
    private String zaf;
    private Uri zag;
    private String zah;
    private long zai;
    private String zaj;
    private List<Scope> zak;
    private String zal;
    private String zam;
    private Set<Scope> zan = new HashSet<Scope>();

    GoogleSignInAccount(int n, String string2, String string3, String string4, String string5, Uri uri, String string6, long l, String string7, List<Scope> list, String string8, String string9) {
        this.zab = n;
        this.zac = string2;
        this.zad = string3;
        this.zae = string4;
        this.zaf = string5;
        this.zag = uri;
        this.zah = string6;
        this.zai = l;
        this.zaj = string7;
        this.zak = list;
        this.zal = string8;
        this.zam = string9;
    }

    public static GoogleSignInAccount createDefault() {
        Account account = new Account("<<default account>>", "com.google");
        HashSet<Scope> hashSet = new HashSet<Scope>();
        return GoogleSignInAccount.zaa(null, null, account.name, null, null, null, null, 0L, account.name, hashSet);
    }

    static final /* synthetic */ int zaa(Scope scope, Scope scope2) {
        return scope.getScopeUri().compareTo(scope2.getScopeUri());
    }

    public static GoogleSignInAccount zaa(String string2) throws JSONException {
        boolean bl = TextUtils.isEmpty((CharSequence)string2);
        Object var2_2 = null;
        if (bl) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string2);
        string2 = jSONObject.optString("photoUrl");
        string2 = !TextUtils.isEmpty((CharSequence)string2) ? Uri.parse((String)string2) : null;
        long l = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet<Scope> hashSet = new HashSet<Scope>();
        Object object = jSONObject.getJSONArray("grantedScopes");
        int n = object.length();
        for (int i = 0; i < n; ++i) {
            hashSet.add(new Scope(object.getString(i)));
        }
        String string3 = jSONObject.optString("id");
        object = jSONObject.has("tokenId") ? jSONObject.optString("tokenId") : null;
        String string4 = jSONObject.has("email") ? jSONObject.optString("email") : null;
        String string5 = jSONObject.has("displayName") ? jSONObject.optString("displayName") : null;
        String string6 = jSONObject.has("givenName") ? jSONObject.optString("givenName") : null;
        String string7 = jSONObject.has("familyName") ? jSONObject.optString("familyName") : null;
        object = GoogleSignInAccount.zaa(string3, (String)object, string4, string5, string6, string7, (Uri)string2, l, jSONObject.getString("obfuscatedIdentifier"), hashSet);
        string2 = var2_2;
        if (jSONObject.has("serverAuthCode")) {
            string2 = jSONObject.optString("serverAuthCode");
        }
        object.zah = string2;
        return object;
    }

    private static GoogleSignInAccount zaa(String string2, String string3, String string4, String string5, String string6, String string7, Uri uri, Long l, String string8, Set<Scope> set) {
        if (l != null) return new GoogleSignInAccount(3, string2, string3, string4, string5, uri, null, l, Preconditions.checkNotEmpty(string8), new ArrayList<Scope>((Collection)Preconditions.checkNotNull(set)), string6, string7);
        l = zaa.currentTimeMillis() / 1000L;
        return new GoogleSignInAccount(3, string2, string3, string4, string5, uri, null, l, Preconditions.checkNotEmpty(string8), new ArrayList<Scope>((Collection)Preconditions.checkNotNull(set)), string6, string7);
    }

    private final JSONObject zac() {
        JSONObject jSONObject = new JSONObject();
        try {
            Scope[] arrscope;
            if (this.getId() != null) {
                jSONObject.put("id", (Object)this.getId());
            }
            if (this.getIdToken() != null) {
                jSONObject.put("tokenId", (Object)this.getIdToken());
            }
            if (this.getEmail() != null) {
                jSONObject.put("email", (Object)this.getEmail());
            }
            if (this.getDisplayName() != null) {
                jSONObject.put("displayName", (Object)this.getDisplayName());
            }
            if (this.getGivenName() != null) {
                jSONObject.put("givenName", (Object)this.getGivenName());
            }
            if (this.getFamilyName() != null) {
                jSONObject.put("familyName", (Object)this.getFamilyName());
            }
            if ((arrscope = this.getPhotoUrl()) != null) {
                jSONObject.put("photoUrl", (Object)arrscope.toString());
            }
            if (this.getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", (Object)this.getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zai);
            jSONObject.put("obfuscatedIdentifier", (Object)this.zaj);
            JSONArray jSONArray = new JSONArray();
            arrscope = this.zak.toArray(new Scope[this.zak.size()]);
            Arrays.sort(arrscope, zaa.zaa);
            int n = arrscope.length;
            for (int i = 0; i < n; ++i) {
                jSONArray.put((Object)arrscope[i].getScopeUri());
            }
            jSONObject.put("grantedScopes", (Object)jSONArray);
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
        if (object == this) {
            return true;
        }
        if (!(object instanceof GoogleSignInAccount)) {
            return false;
        }
        object = (GoogleSignInAccount)object;
        if (!((GoogleSignInAccount)object).zaj.equals(this.zaj)) return false;
        if (!((GoogleSignInAccount)object).getRequestedScopes().equals(this.getRequestedScopes())) return false;
        return true;
    }

    public Account getAccount() {
        if (this.zae != null) return new Account(this.zae, "com.google");
        return null;
    }

    public String getDisplayName() {
        return this.zaf;
    }

    public String getEmail() {
        return this.zae;
    }

    public String getFamilyName() {
        return this.zam;
    }

    public String getGivenName() {
        return this.zal;
    }

    public Set<Scope> getGrantedScopes() {
        return new HashSet<Scope>(this.zak);
    }

    public String getId() {
        return this.zac;
    }

    public String getIdToken() {
        return this.zad;
    }

    public Uri getPhotoUrl() {
        return this.zag;
    }

    public Set<Scope> getRequestedScopes() {
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zak);
        hashSet.addAll(this.zan);
        return hashSet;
    }

    public String getServerAuthCode() {
        return this.zah;
    }

    public int hashCode() {
        return (this.zaj.hashCode() + 527) * 31 + this.getRequestedScopes().hashCode();
    }

    public boolean isExpired() {
        if (zaa.currentTimeMillis() / 1000L < this.zai - 300L) return false;
        return true;
    }

    public GoogleSignInAccount requestExtraScopes(Scope ... arrscope) {
        if (arrscope == null) return this;
        Collections.addAll(this.zan, arrscope);
        return this;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zab);
        SafeParcelWriter.writeString(parcel, 2, this.getId(), false);
        SafeParcelWriter.writeString(parcel, 3, this.getIdToken(), false);
        SafeParcelWriter.writeString(parcel, 4, this.getEmail(), false);
        SafeParcelWriter.writeString(parcel, 5, this.getDisplayName(), false);
        SafeParcelWriter.writeParcelable(parcel, 6, (Parcelable)this.getPhotoUrl(), n, false);
        SafeParcelWriter.writeString(parcel, 7, this.getServerAuthCode(), false);
        SafeParcelWriter.writeLong(parcel, 8, this.zai);
        SafeParcelWriter.writeString(parcel, 9, this.zaj, false);
        SafeParcelWriter.writeTypedList(parcel, 10, this.zak, false);
        SafeParcelWriter.writeString(parcel, 11, this.getGivenName(), false);
        SafeParcelWriter.writeString(parcel, 12, this.getFamilyName(), false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final String zaa() {
        return this.zaj;
    }

    public final String zab() {
        JSONObject jSONObject = this.zac();
        jSONObject.remove("serverAuthCode");
        return jSONObject.toString();
    }
}

