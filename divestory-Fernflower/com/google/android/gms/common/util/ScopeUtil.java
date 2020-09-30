package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class ScopeUtil {
   private ScopeUtil() {
   }

   public static Set<Scope> fromScopeString(Collection<String> var0) {
      Preconditions.checkNotNull(var0, "scopeStrings can't be null.");
      return fromScopeString((String[])var0.toArray(new String[var0.size()]));
   }

   public static Set<Scope> fromScopeString(String... var0) {
      Preconditions.checkNotNull(var0, "scopeStrings can't be null.");
      HashSet var1 = new HashSet(var0.length);
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var0[var3];
         if (!TextUtils.isEmpty(var4)) {
            var1.add(new Scope(var4));
         }
      }

      return var1;
   }

   public static String[] toScopeString(Set<Scope> var0) {
      Preconditions.checkNotNull(var0, "scopes can't be null.");
      return toScopeString((Scope[])var0.toArray(new Scope[var0.size()]));
   }

   public static String[] toScopeString(Scope[] var0) {
      Preconditions.checkNotNull(var0, "scopes can't be null.");
      String[] var1 = new String[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = var0[var2].getScopeUri();
      }

      return var1;
   }
}
