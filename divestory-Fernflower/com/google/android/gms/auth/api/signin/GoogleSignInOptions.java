package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

public class GoogleSignInOptions extends AbstractSafeParcelable implements Api.ApiOptions.Optional, ReflectedParcelable {
   public static final Creator<GoogleSignInOptions> CREATOR;
   public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN;
   public static final GoogleSignInOptions DEFAULT_SIGN_IN = (new GoogleSignInOptions.Builder()).requestId().requestProfile().build();
   public static final Scope zaa = new Scope("profile");
   public static final Scope zab = new Scope("email");
   public static final Scope zac = new Scope("openid");
   public static final Scope zad = new Scope("https://www.googleapis.com/auth/games_lite");
   public static final Scope zae = new Scope("https://www.googleapis.com/auth/games");
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
      DEFAULT_GAMES_SIGN_IN = (new GoogleSignInOptions.Builder()).requestScopes(zad).build();
      CREATOR = new zad();
      zaq = new zac();
   }

   GoogleSignInOptions(int var1, ArrayList<Scope> var2, Account var3, boolean var4, boolean var5, boolean var6, String var7, String var8, ArrayList<GoogleSignInOptionsExtensionParcelable> var9, String var10) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, zab((List)var9), var10);
   }

   private GoogleSignInOptions(int var1, ArrayList<Scope> var2, Account var3, boolean var4, boolean var5, boolean var6, String var7, String var8, Map<Integer, GoogleSignInOptionsExtensionParcelable> var9, String var10) {
      this.zaf = var1;
      this.zag = var2;
      this.zah = var3;
      this.zai = var4;
      this.zaj = var5;
      this.zak = var6;
      this.zal = var7;
      this.zam = var8;
      this.zan = new ArrayList(var9.values());
      this.zap = var9;
      this.zao = var10;
   }

   // $FF: synthetic method
   GoogleSignInOptions(int var1, ArrayList var2, Account var3, boolean var4, boolean var5, boolean var6, String var7, String var8, Map var9, String var10, zac var11) {
      this(3, var2, var3, var4, var5, var6, var7, var8, (Map)var9, var10);
   }

   public static GoogleSignInOptions zaa(String var0) throws JSONException {
      boolean var1 = TextUtils.isEmpty(var0);
      String var2 = null;
      if (var1) {
         return null;
      } else {
         JSONObject var3 = new JSONObject(var0);
         HashSet var4 = new HashSet();
         JSONArray var10 = var3.getJSONArray("scopes");
         int var5 = var10.length();

         for(int var6 = 0; var6 < var5; ++var6) {
            var4.add(new Scope(var10.getString(var6)));
         }

         if (var3.has("accountName")) {
            var0 = var3.optString("accountName");
         } else {
            var0 = null;
         }

         Account var11;
         if (!TextUtils.isEmpty(var0)) {
            var11 = new Account(var0, "com.google");
         } else {
            var11 = null;
         }

         ArrayList var7 = new ArrayList(var4);
         boolean var8 = var3.getBoolean("idTokenRequested");
         var1 = var3.getBoolean("serverAuthRequested");
         boolean var9 = var3.getBoolean("forceCodeForRefreshToken");
         String var12;
         if (var3.has("serverClientId")) {
            var12 = var3.optString("serverClientId");
         } else {
            var12 = null;
         }

         if (var3.has("hostedDomain")) {
            var2 = var3.optString("hostedDomain");
         }

         return new GoogleSignInOptions(3, var7, var11, var8, var1, var9, var12, var2, new HashMap(), (String)null);
      }
   }

   private static Map<Integer, GoogleSignInOptionsExtensionParcelable> zab(List<GoogleSignInOptionsExtensionParcelable> var0) {
      HashMap var1 = new HashMap();
      if (var0 == null) {
         return var1;
      } else {
         Iterator var3 = var0.iterator();

         while(var3.hasNext()) {
            GoogleSignInOptionsExtensionParcelable var2 = (GoogleSignInOptionsExtensionParcelable)var3.next();
            var1.put(var2.getType(), var2);
         }

         return var1;
      }
   }

   private final JSONObject zab() {
      JSONObject var1 = new JSONObject();

      JSONException var10000;
      label65: {
         JSONArray var2;
         ArrayList var3;
         int var4;
         boolean var10001;
         try {
            var2 = new JSONArray();
            Collections.sort(this.zag, zaq);
            var3 = (ArrayList)this.zag;
            var4 = var3.size();
         } catch (JSONException var12) {
            var10000 = var12;
            var10001 = false;
            break label65;
         }

         int var5 = 0;

         while(var5 < var4) {
            Object var6;
            try {
               var6 = var3.get(var5);
            } catch (JSONException var11) {
               var10000 = var11;
               var10001 = false;
               break label65;
            }

            ++var5;

            try {
               var2.put(((Scope)var6).getScopeUri());
            } catch (JSONException var10) {
               var10000 = var10;
               var10001 = false;
               break label65;
            }
         }

         try {
            var1.put("scopes", var2);
            if (this.zah != null) {
               var1.put("accountName", this.zah.name);
            }
         } catch (JSONException var9) {
            var10000 = var9;
            var10001 = false;
            break label65;
         }

         try {
            var1.put("idTokenRequested", this.zai);
            var1.put("forceCodeForRefreshToken", this.zak);
            var1.put("serverAuthRequested", this.zaj);
            if (!TextUtils.isEmpty(this.zal)) {
               var1.put("serverClientId", this.zal);
            }
         } catch (JSONException var8) {
            var10000 = var8;
            var10001 = false;
            break label65;
         }

         try {
            if (!TextUtils.isEmpty(this.zam)) {
               var1.put("hostedDomain", this.zam);
            }

            return var1;
         } catch (JSONException var7) {
            var10000 = var7;
            var10001 = false;
         }
      }

      JSONException var13 = var10000;
      throw new RuntimeException(var13);
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else {
         boolean var10001;
         GoogleSignInOptions var10;
         try {
            var10 = (GoogleSignInOptions)var1;
            if (this.zan.size() > 0 || var10.zan.size() > 0) {
               return false;
            }
         } catch (ClassCastException var9) {
            var10001 = false;
            return false;
         }

         try {
            if (this.zag.size() != var10.getScopes().size() || !this.zag.containsAll(var10.getScopes())) {
               return false;
            }
         } catch (ClassCastException var8) {
            var10001 = false;
            return false;
         }

         label83: {
            try {
               if (this.zah == null) {
                  if (var10.getAccount() != null) {
                     return false;
                  }
                  break label83;
               }
            } catch (ClassCastException var7) {
               var10001 = false;
               return false;
            }

            try {
               if (!this.zah.equals(var10.getAccount())) {
                  return false;
               }
            } catch (ClassCastException var6) {
               var10001 = false;
               return false;
            }
         }

         label103: {
            try {
               if (TextUtils.isEmpty(this.zal)) {
                  if (!TextUtils.isEmpty(var10.getServerClientId())) {
                     return false;
                  }
                  break label103;
               }
            } catch (ClassCastException var5) {
               var10001 = false;
               return false;
            }

            try {
               if (!this.zal.equals(var10.getServerClientId())) {
                  return false;
               }
            } catch (ClassCastException var4) {
               var10001 = false;
               return false;
            }
         }

         boolean var2;
         try {
            if (this.zak != var10.isForceCodeForRefreshToken() || this.zai != var10.isIdTokenRequested() || this.zaj != var10.isServerAuthCodeRequested()) {
               return false;
            }

            var2 = TextUtils.equals(this.zao, var10.getLogSessionId());
         } catch (ClassCastException var3) {
            var10001 = false;
            return false;
         }

         if (var2) {
            return true;
         } else {
            return false;
         }
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
      ArrayList var1 = this.zag;
      return (Scope[])var1.toArray(new Scope[var1.size()]);
   }

   public ArrayList<Scope> getScopes() {
      return new ArrayList(this.zag);
   }

   public String getServerClientId() {
      return this.zal;
   }

   public int hashCode() {
      ArrayList var1 = new ArrayList();
      ArrayList var2 = (ArrayList)this.zag;
      int var3 = var2.size();
      int var4 = 0;

      while(var4 < var3) {
         Object var5 = var2.get(var4);
         ++var4;
         var1.add(((Scope)var5).getScopeUri());
      }

      Collections.sort(var1);
      return (new HashAccumulator()).addObject(var1).addObject(this.zah).addObject(this.zal).zaa(this.zak).zaa(this.zai).zaa(this.zaj).addObject(this.zao).hash();
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

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaf);
      SafeParcelWriter.writeTypedList(var1, 2, this.getScopes(), false);
      SafeParcelWriter.writeParcelable(var1, 3, this.getAccount(), var2, false);
      SafeParcelWriter.writeBoolean(var1, 4, this.isIdTokenRequested());
      SafeParcelWriter.writeBoolean(var1, 5, this.isServerAuthCodeRequested());
      SafeParcelWriter.writeBoolean(var1, 6, this.isForceCodeForRefreshToken());
      SafeParcelWriter.writeString(var1, 7, this.getServerClientId(), false);
      SafeParcelWriter.writeString(var1, 8, this.zam, false);
      SafeParcelWriter.writeTypedList(var1, 9, this.getExtensions(), false);
      SafeParcelWriter.writeString(var1, 10, this.getLogSessionId(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final String zaa() {
      return this.zab().toString();
   }

   public static final class Builder {
      private Set<Scope> zaa = new HashSet();
      private boolean zab;
      private boolean zac;
      private boolean zad;
      private String zae;
      private Account zaf;
      private String zag;
      private Map<Integer, GoogleSignInOptionsExtensionParcelable> zah = new HashMap();
      private String zai;

      public Builder() {
      }

      public Builder(GoogleSignInOptions var1) {
         Preconditions.checkNotNull(var1);
         this.zaa = new HashSet(var1.zag);
         this.zab = var1.zaj;
         this.zac = var1.zak;
         this.zad = var1.zai;
         this.zae = var1.zal;
         this.zaf = var1.zah;
         this.zag = var1.zam;
         this.zah = GoogleSignInOptions.zab((List)var1.zan);
         this.zai = var1.zao;
      }

      private final String zaa(String var1) {
         Preconditions.checkNotEmpty(var1);
         String var2 = this.zae;
         boolean var3;
         if (var2 != null && !var2.equals(var1)) {
            var3 = false;
         } else {
            var3 = true;
         }

         Preconditions.checkArgument(var3, "two different server client ids provided");
         return var1;
      }

      public final GoogleSignInOptions.Builder addExtension(GoogleSignInOptionsExtension var1) {
         if (!this.zah.containsKey(var1.getExtensionType())) {
            List var2 = var1.getImpliedScopes();
            if (var2 != null) {
               this.zaa.addAll(var2);
            }

            this.zah.put(var1.getExtensionType(), new GoogleSignInOptionsExtensionParcelable(var1));
            return this;
         } else {
            throw new IllegalStateException("Only one extension per type may be added");
         }
      }

      public final GoogleSignInOptions build() {
         if (this.zaa.contains(GoogleSignInOptions.zae) && this.zaa.contains(GoogleSignInOptions.zad)) {
            this.zaa.remove(GoogleSignInOptions.zad);
         }

         if (this.zad && (this.zaf == null || !this.zaa.isEmpty())) {
            this.requestId();
         }

         return new GoogleSignInOptions(3, new ArrayList(this.zaa), this.zaf, this.zad, this.zab, this.zac, this.zae, this.zag, this.zah, this.zai, (zac)null);
      }

      public final GoogleSignInOptions.Builder requestEmail() {
         this.zaa.add(GoogleSignInOptions.zab);
         return this;
      }

      public final GoogleSignInOptions.Builder requestId() {
         this.zaa.add(GoogleSignInOptions.zac);
         return this;
      }

      public final GoogleSignInOptions.Builder requestIdToken(String var1) {
         this.zad = true;
         this.zae = this.zaa(var1);
         return this;
      }

      public final GoogleSignInOptions.Builder requestProfile() {
         this.zaa.add(GoogleSignInOptions.zaa);
         return this;
      }

      public final GoogleSignInOptions.Builder requestScopes(Scope var1, Scope... var2) {
         this.zaa.add(var1);
         this.zaa.addAll(Arrays.asList(var2));
         return this;
      }

      public final GoogleSignInOptions.Builder requestServerAuthCode(String var1) {
         return this.requestServerAuthCode(var1, false);
      }

      public final GoogleSignInOptions.Builder requestServerAuthCode(String var1, boolean var2) {
         this.zab = true;
         this.zae = this.zaa(var1);
         this.zac = var2;
         return this;
      }

      public final GoogleSignInOptions.Builder setAccountName(String var1) {
         this.zaf = new Account(Preconditions.checkNotEmpty(var1), "com.google");
         return this;
      }

      public final GoogleSignInOptions.Builder setHostedDomain(String var1) {
         this.zag = Preconditions.checkNotEmpty(var1);
         return this;
      }

      public final GoogleSignInOptions.Builder setLogSessionId(String var1) {
         this.zai = var1;
         return this;
      }
   }
}
