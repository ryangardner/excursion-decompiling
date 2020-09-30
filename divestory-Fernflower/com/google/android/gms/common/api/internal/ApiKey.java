package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

public final class ApiKey<O extends Api.ApiOptions> {
   private final boolean zaa = false;
   private final int zab;
   private final Api<O> zac;
   private final O zad;

   private ApiKey(Api<O> var1, O var2) {
      this.zac = var1;
      this.zad = var2;
      this.zab = Objects.hashCode(var1, var2);
   }

   public static <O extends Api.ApiOptions> ApiKey<O> getSharedApiKey(Api<O> var0, O var1) {
      return new ApiKey(var0, var1);
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ApiKey)) {
         return false;
      } else {
         ApiKey var2 = (ApiKey)var1;
         return Objects.equal(this.zac, var2.zac) && Objects.equal(this.zad, var2.zad);
      }
   }

   public final String getApiName() {
      return this.zac.zad();
   }

   public final Api.AnyClientKey<?> getClientKey() {
      return this.zac.zac();
   }

   public final int hashCode() {
      return this.zab;
   }

   public final boolean isUnique() {
      return false;
   }
}
