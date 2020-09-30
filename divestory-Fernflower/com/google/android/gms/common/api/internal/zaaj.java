package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

final class zaaj extends zaan {
   private final ArrayList<Api.Client> zaa;
   // $FF: synthetic field
   private final zaad zab;

   public zaaj(zaad var1, ArrayList var2) {
      super(var1, (zaag)null);
      this.zab = var1;
      this.zaa = var2;
   }

   public final void zaa() {
      zaad.zad(this.zab).zad.zac = zaad.zag(this.zab);
      ArrayList var1 = (ArrayList)this.zaa;
      int var2 = var1.size();
      int var3 = 0;

      while(var3 < var2) {
         Object var4 = var1.get(var3);
         ++var3;
         ((Api.Client)var4).getRemoteService(zaad.zah(this.zab), zaad.zad(this.zab).zad.zac);
      }

   }
}
