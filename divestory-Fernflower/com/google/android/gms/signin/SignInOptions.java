package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

public final class SignInOptions implements Api.ApiOptions.Optional {
   public static final SignInOptions zaa;
   private final boolean zab = false;
   private final boolean zac = false;
   private final String zad = null;
   private final boolean zae = false;
   private final String zaf = null;
   private final String zag = null;
   private final boolean zah = false;
   private final Long zai = null;
   private final Long zaj = null;

   static {
      new SignInOptions.zaa();
      zaa = new SignInOptions(false, false, (String)null, false, (String)null, (String)null, false, (Long)null, (Long)null);
   }

   private SignInOptions(boolean var1, boolean var2, String var3, boolean var4, String var5, String var6, boolean var7, Long var8, Long var9) {
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SignInOptions)) {
         return false;
      } else {
         SignInOptions var2 = (SignInOptions)var1;
         return Objects.equal((Object)null, (Object)null) && Objects.equal((Object)null, (Object)null) && Objects.equal((Object)null, (Object)null) && Objects.equal((Object)null, (Object)null) && Objects.equal((Object)null, (Object)null);
      }
   }

   public final int hashCode() {
      Boolean var1 = false;
      return Objects.hashCode(var1, var1, null, var1, var1, null, null, null, null);
   }

   public static final class zaa {
   }
}
