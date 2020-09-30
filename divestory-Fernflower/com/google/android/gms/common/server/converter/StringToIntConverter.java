package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class StringToIntConverter extends AbstractSafeParcelable implements FastJsonResponse.FieldConverter<String, Integer> {
   public static final Creator<StringToIntConverter> CREATOR = new zac();
   private final int zaa;
   private final HashMap<String, Integer> zab;
   private final SparseArray<String> zac;
   private final ArrayList<StringToIntConverter.zaa> zad;

   public StringToIntConverter() {
      this.zaa = 1;
      this.zab = new HashMap();
      this.zac = new SparseArray();
      this.zad = null;
   }

   StringToIntConverter(int var1, ArrayList<StringToIntConverter.zaa> var2) {
      this.zaa = var1;
      this.zab = new HashMap();
      this.zac = new SparseArray();
      this.zad = null;
      var2 = (ArrayList)var2;
      int var3 = var2.size();
      var1 = 0;

      while(var1 < var3) {
         Object var4 = var2.get(var1);
         ++var1;
         StringToIntConverter.zaa var5 = (StringToIntConverter.zaa)var4;
         this.add(var5.zaa, var5.zab);
      }

   }

   public final StringToIntConverter add(String var1, int var2) {
      this.zab.put(var1, var2);
      this.zac.put(var2, var1);
      return this;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.zab.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var3.add(new StringToIntConverter.zaa(var5, (Integer)this.zab.get(var5)));
      }

      SafeParcelWriter.writeTypedList(var1, 2, var3, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final int zaa() {
      return 7;
   }

   // $FF: synthetic method
   public final Object zaa(Object var1) {
      Integer var2 = (Integer)var1;
      String var3 = (String)this.zac.get(var2);
      return var3 == null && this.zab.containsKey("gms_unknown") ? "gms_unknown" : var3;
   }

   public final int zab() {
      return 0;
   }

   // $FF: synthetic method
   public final Object zab(Object var1) {
      String var3 = (String)var1;
      Integer var2 = (Integer)this.zab.get(var3);
      Integer var4 = var2;
      if (var2 == null) {
         var4 = (Integer)this.zab.get("gms_unknown");
      }

      return var4;
   }

   public static final class zaa extends AbstractSafeParcelable {
      public static final Creator<StringToIntConverter.zaa> CREATOR = new zad();
      final String zaa;
      final int zab;
      private final int zac;

      zaa(int var1, String var2, int var3) {
         this.zac = var1;
         this.zaa = var2;
         this.zab = var3;
      }

      zaa(String var1, int var2) {
         this.zac = 1;
         this.zaa = var1;
         this.zab = var2;
      }

      public final void writeToParcel(Parcel var1, int var2) {
         var2 = SafeParcelWriter.beginObjectHeader(var1);
         SafeParcelWriter.writeInt(var1, 1, this.zac);
         SafeParcelWriter.writeString(var1, 2, this.zaa, false);
         SafeParcelWriter.writeInt(var1, 3, this.zab);
         SafeParcelWriter.finishObjectHeader(var1, var2);
      }
   }
}
