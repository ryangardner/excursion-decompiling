package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<GoogleSignInAccount> CREATOR = new zab();
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
   private Set<Scope> zan = new HashSet();

   GoogleSignInAccount(int var1, String var2, String var3, String var4, String var5, Uri var6, String var7, long var8, String var10, List<Scope> var11, String var12, String var13) {
      this.zab = var1;
      this.zac = var2;
      this.zad = var3;
      this.zae = var4;
      this.zaf = var5;
      this.zag = var6;
      this.zah = var7;
      this.zai = var8;
      this.zaj = var10;
      this.zak = var11;
      this.zal = var12;
      this.zam = var13;
   }

   public static GoogleSignInAccount createDefault() {
      Account var0 = new Account("<<default account>>", "com.google");
      HashSet var1 = new HashSet();
      return zaa((String)null, (String)null, var0.name, (String)null, (String)null, (String)null, (Uri)null, 0L, var0.name, var1);
   }

   // $FF: synthetic method
   static final int zaa(Scope var0, Scope var1) {
      return var0.getScopeUri().compareTo(var1.getScopeUri());
   }

   public static GoogleSignInAccount zaa(String var0) throws JSONException {
      boolean var1 = TextUtils.isEmpty(var0);
      Object var2 = null;
      if (var1) {
         return null;
      } else {
         JSONObject var3 = new JSONObject(var0);
         var0 = var3.optString("photoUrl");
         Uri var15;
         if (!TextUtils.isEmpty(var0)) {
            var15 = Uri.parse(var0);
         } else {
            var15 = null;
         }

         long var4 = Long.parseLong(var3.getString("expirationTime"));
         HashSet var6 = new HashSet();
         JSONArray var7 = var3.getJSONArray("grantedScopes");
         int var8 = var7.length();

         for(int var9 = 0; var9 < var8; ++var9) {
            var6.add(new Scope(var7.getString(var9)));
         }

         String var10 = var3.optString("id");
         String var16;
         if (var3.has("tokenId")) {
            var16 = var3.optString("tokenId");
         } else {
            var16 = null;
         }

         String var11;
         if (var3.has("email")) {
            var11 = var3.optString("email");
         } else {
            var11 = null;
         }

         String var12;
         if (var3.has("displayName")) {
            var12 = var3.optString("displayName");
         } else {
            var12 = null;
         }

         String var13;
         if (var3.has("givenName")) {
            var13 = var3.optString("givenName");
         } else {
            var13 = null;
         }

         String var14;
         if (var3.has("familyName")) {
            var14 = var3.optString("familyName");
         } else {
            var14 = null;
         }

         GoogleSignInAccount var17 = zaa(var10, var16, var11, var12, var13, var14, var15, var4, var3.getString("obfuscatedIdentifier"), var6);
         var0 = (String)var2;
         if (var3.has("serverAuthCode")) {
            var0 = var3.optString("serverAuthCode");
         }

         var17.zah = var0;
         return var17;
      }
   }

   private static GoogleSignInAccount zaa(String var0, String var1, String var2, String var3, String var4, String var5, Uri var6, Long var7, String var8, Set<Scope> var9) {
      if (var7 == null) {
         var7 = zaa.currentTimeMillis() / 1000L;
      }

      return new GoogleSignInAccount(3, var0, var1, var2, var3, var6, (String)null, var7, Preconditions.checkNotEmpty(var8), new ArrayList((Collection)Preconditions.checkNotNull(var9)), var4, var5);
   }

   private final JSONObject zac() {
      JSONObject var1 = new JSONObject();

      JSONException var10000;
      label116: {
         boolean var10001;
         try {
            if (this.getId() != null) {
               var1.put("id", this.getId());
            }
         } catch (JSONException var17) {
            var10000 = var17;
            var10001 = false;
            break label116;
         }

         try {
            if (this.getIdToken() != null) {
               var1.put("tokenId", this.getIdToken());
            }
         } catch (JSONException var16) {
            var10000 = var16;
            var10001 = false;
            break label116;
         }

         try {
            if (this.getEmail() != null) {
               var1.put("email", this.getEmail());
            }
         } catch (JSONException var15) {
            var10000 = var15;
            var10001 = false;
            break label116;
         }

         try {
            if (this.getDisplayName() != null) {
               var1.put("displayName", this.getDisplayName());
            }
         } catch (JSONException var14) {
            var10000 = var14;
            var10001 = false;
            break label116;
         }

         try {
            if (this.getGivenName() != null) {
               var1.put("givenName", this.getGivenName());
            }
         } catch (JSONException var13) {
            var10000 = var13;
            var10001 = false;
            break label116;
         }

         try {
            if (this.getFamilyName() != null) {
               var1.put("familyName", this.getFamilyName());
            }
         } catch (JSONException var12) {
            var10000 = var12;
            var10001 = false;
            break label116;
         }

         Uri var2;
         try {
            var2 = this.getPhotoUrl();
         } catch (JSONException var11) {
            var10000 = var11;
            var10001 = false;
            break label116;
         }

         if (var2 != null) {
            try {
               var1.put("photoUrl", var2.toString());
            } catch (JSONException var10) {
               var10000 = var10;
               var10001 = false;
               break label116;
            }
         }

         try {
            if (this.getServerAuthCode() != null) {
               var1.put("serverAuthCode", this.getServerAuthCode());
            }
         } catch (JSONException var9) {
            var10000 = var9;
            var10001 = false;
            break label116;
         }

         JSONArray var3;
         int var4;
         Scope[] var19;
         try {
            var1.put("expirationTime", this.zai);
            var1.put("obfuscatedIdentifier", this.zaj);
            var3 = new JSONArray();
            var19 = (Scope[])this.zak.toArray(new Scope[this.zak.size()]);
            Arrays.sort(var19, com.google.android.gms.auth.api.signin.zaa.zaa);
            var4 = var19.length;
         } catch (JSONException var8) {
            var10000 = var8;
            var10001 = false;
            break label116;
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            try {
               var3.put(var19[var5].getScopeUri());
            } catch (JSONException var7) {
               var10000 = var7;
               var10001 = false;
               break label116;
            }
         }

         try {
            var1.put("grantedScopes", var3);
            return var1;
         } catch (JSONException var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      JSONException var18 = var10000;
      throw new RuntimeException(var18);
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof GoogleSignInAccount)) {
         return false;
      } else {
         GoogleSignInAccount var2 = (GoogleSignInAccount)var1;
         return var2.zaj.equals(this.zaj) && var2.getRequestedScopes().equals(this.getRequestedScopes());
      }
   }

   public Account getAccount() {
      return this.zae == null ? null : new Account(this.zae, "com.google");
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
      return new HashSet(this.zak);
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
      HashSet var1 = new HashSet(this.zak);
      var1.addAll(this.zan);
      return var1;
   }

   public String getServerAuthCode() {
      return this.zah;
   }

   public int hashCode() {
      return (this.zaj.hashCode() + 527) * 31 + this.getRequestedScopes().hashCode();
   }

   public boolean isExpired() {
      return zaa.currentTimeMillis() / 1000L >= this.zai - 300L;
   }

   public GoogleSignInAccount requestExtraScopes(Scope... var1) {
      if (var1 != null) {
         Collections.addAll(this.zan, var1);
      }

      return this;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zab);
      SafeParcelWriter.writeString(var1, 2, this.getId(), false);
      SafeParcelWriter.writeString(var1, 3, this.getIdToken(), false);
      SafeParcelWriter.writeString(var1, 4, this.getEmail(), false);
      SafeParcelWriter.writeString(var1, 5, this.getDisplayName(), false);
      SafeParcelWriter.writeParcelable(var1, 6, this.getPhotoUrl(), var2, false);
      SafeParcelWriter.writeString(var1, 7, this.getServerAuthCode(), false);
      SafeParcelWriter.writeLong(var1, 8, this.zai);
      SafeParcelWriter.writeString(var1, 9, this.zaj, false);
      SafeParcelWriter.writeTypedList(var1, 10, this.zak, false);
      SafeParcelWriter.writeString(var1, 11, this.getGivenName(), false);
      SafeParcelWriter.writeString(var1, 12, this.getFamilyName(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final String zaa() {
      return this.zaj;
   }

   public final String zab() {
      JSONObject var1 = this.zac();
      var1.remove("serverAuthCode");
      return var1.toString();
   }
}
