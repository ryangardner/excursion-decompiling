package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class zak extends AbstractSafeParcelable {
   public static final Creator<zak> CREATOR = new zao();
   final String zaa;
   final ArrayList<zan> zab;
   private final int zac;

   zak(int var1, String var2, ArrayList<zan> var3) {
      this.zac = var1;
      this.zaa = var2;
      this.zab = var3;
   }

   zak(String var1, Map<String, FastJsonResponse.Field<?, ?>> var2) {
      this.zac = 1;
      this.zaa = var1;
      ArrayList var5;
      if (var2 == null) {
         var5 = null;
      } else {
         ArrayList var3 = new ArrayList();
         Iterator var4 = var2.keySet().iterator();

         while(true) {
            var5 = var3;
            if (!var4.hasNext()) {
               break;
            }

            var1 = (String)var4.next();
            var3.add(new zan(var1, (FastJsonResponse.Field)var2.get(var1)));
         }
      }

      this.zab = var5;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zac);
      SafeParcelWriter.writeString(var1, 2, this.zaa, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.zab, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
