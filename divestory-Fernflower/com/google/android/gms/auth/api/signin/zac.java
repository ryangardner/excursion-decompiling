package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.Scope;
import java.util.Comparator;

final class zac implements Comparator<Scope> {
   // $FF: synthetic method
   public final int compare(Object var1, Object var2) {
      Scope var3 = (Scope)var1;
      Scope var4 = (Scope)var2;
      return var3.getScopeUri().compareTo(var4.getScopeUri());
   }
}
