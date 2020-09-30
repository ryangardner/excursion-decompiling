package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import androidx.collection.ArraySet;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
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
   private final Map<Api<?>, ClientSettings.zaa> zad;
   private final int zae;
   private final View zaf;
   private final String zag;
   private final String zah;
   private final SignInOptions zai;
   private final boolean zaj;
   private Integer zak;

   public ClientSettings(Account var1, Set<Scope> var2, Map<Api<?>, ClientSettings.zaa> var3, int var4, View var5, String var6, String var7, SignInOptions var8) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, false);
   }

   public ClientSettings(@Nullable Account var1, Set<Scope> var2, Map<Api<?>, ClientSettings.zaa> var3, int var4, View var5, String var6, String var7, SignInOptions var8, boolean var9) {
      this.zaa = var1;
      Set var10;
      if (var2 == null) {
         var10 = Collections.emptySet();
      } else {
         var10 = Collections.unmodifiableSet(var2);
      }

      this.zab = var10;
      Map var11 = var3;
      if (var3 == null) {
         var11 = Collections.emptyMap();
      }

      this.zad = var11;
      this.zaf = var5;
      this.zae = var4;
      this.zag = var6;
      this.zah = var7;
      this.zai = var8;
      this.zaj = false;
      HashSet var12 = new HashSet(this.zab);
      Iterator var13 = this.zad.values().iterator();

      while(var13.hasNext()) {
         var12.addAll(((ClientSettings.zaa)var13.next()).zaa);
      }

      this.zac = Collections.unmodifiableSet(var12);
   }

   public static ClientSettings createDefault(Context var0) {
      return (new GoogleApiClient.Builder(var0)).buildClientSettings();
   }

   public final Account getAccount() {
      return this.zaa;
   }

   @Deprecated
   public final String getAccountName() {
      Account var1 = this.zaa;
      return var1 != null ? var1.name : null;
   }

   public final Account getAccountOrDefault() {
      Account var1 = this.zaa;
      return var1 != null ? var1 : new Account("<<default account>>", "com.google");
   }

   public final Set<Scope> getAllRequestedScopes() {
      return this.zac;
   }

   public final Set<Scope> getApplicableScopes(Api<?> var1) {
      ClientSettings.zaa var2 = (ClientSettings.zaa)this.zad.get(var1);
      if (var2 != null && !var2.zaa.isEmpty()) {
         HashSet var3 = new HashSet(this.zab);
         var3.addAll(var2.zaa);
         return var3;
      } else {
         return this.zab;
      }
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

   public final Map<Api<?>, ClientSettings.zaa> zaa() {
      return this.zad;
   }

   public final void zaa(Integer var1) {
      this.zak = var1;
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
      private SignInOptions zaf;

      public Builder() {
         this.zaf = SignInOptions.zaa;
      }

      public final ClientSettings build() {
         return new ClientSettings(this.zaa, this.zab, (Map)null, 0, (View)null, this.zad, this.zae, this.zaf, false);
      }

      public final ClientSettings.Builder setRealClientPackageName(String var1) {
         this.zad = var1;
         return this;
      }

      public final ClientSettings.Builder zaa(@Nullable Account var1) {
         this.zaa = var1;
         return this;
      }

      public final ClientSettings.Builder zaa(String var1) {
         this.zae = var1;
         return this;
      }

      public final ClientSettings.Builder zaa(Collection<Scope> var1) {
         if (this.zab == null) {
            this.zab = new ArraySet();
         }

         this.zab.addAll(var1);
         return this;
      }
   }

   public static final class zaa {
      public final Set<Scope> zaa;

      public zaa(Set<Scope> var1) {
         Preconditions.checkNotNull(var1);
         this.zaa = Collections.unmodifiableSet(var1);
      }
   }
}
