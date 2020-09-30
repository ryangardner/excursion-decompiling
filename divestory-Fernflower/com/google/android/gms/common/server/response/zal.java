package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zal extends AbstractSafeParcelable {
   public static final Creator<zal> CREATOR = new zam();
   private final int zaa;
   private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zab;
   private final ArrayList<zak> zac;
   private final String zad;

   zal(int var1, ArrayList<zak> var2, String var3) {
      this.zaa = var1;
      this.zac = null;
      HashMap var4 = new HashMap();
      int var5 = var2.size();

      for(var1 = 0; var1 < var5; ++var1) {
         zak var6 = (zak)var2.get(var1);
         String var7 = var6.zaa;
         HashMap var8 = new HashMap();
         int var9 = ((ArrayList)Preconditions.checkNotNull(var6.zab)).size();

         for(int var10 = 0; var10 < var9; ++var10) {
            zan var11 = (zan)var6.zab.get(var10);
            var8.put(var11.zaa, var11.zab);
         }

         var4.put(var7, var8);
      }

      this.zab = var4;
      this.zad = (String)Preconditions.checkNotNull(var3);
      this.zaa();
   }

   public zal(java.lang.Class<? extends FastJsonResponse> var1) {
      this.zaa = 1;
      this.zac = null;
      this.zab = new HashMap();
      this.zad = (String)Preconditions.checkNotNull(var1.getCanonicalName());
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.zab.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.append(var3);
         var1.append(":\n");
         Map var4 = (Map)this.zab.get(var3);
         Iterator var6 = var4.keySet().iterator();

         while(var6.hasNext()) {
            String var5 = (String)var6.next();
            var1.append("  ");
            var1.append(var5);
            var1.append(": ");
            var1.append(var4.get(var5));
         }
      }

      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.zab.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var3.add(new zak(var5, (Map)this.zab.get(var5)));
      }

      SafeParcelWriter.writeTypedList(var1, 2, var3, false);
      SafeParcelWriter.writeString(var1, 3, this.zad, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final Map<String, FastJsonResponse.Field<?, ?>> zaa(String var1) {
      return (Map)this.zab.get(var1);
   }

   public final void zaa() {
      Iterator var1 = this.zab.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Map var3 = (Map)this.zab.get(var2);
         Iterator var4 = var3.keySet().iterator();

         while(var4.hasNext()) {
            ((FastJsonResponse.Field)var3.get((String)var4.next())).zaa(this);
         }
      }

   }

   public final void zaa(java.lang.Class<? extends FastJsonResponse> var1, Map<String, FastJsonResponse.Field<?, ?>> var2) {
      this.zab.put((String)Preconditions.checkNotNull(var1.getCanonicalName()), var2);
   }

   public final boolean zaa(java.lang.Class<? extends FastJsonResponse> var1) {
      return this.zab.containsKey(Preconditions.checkNotNull(var1.getCanonicalName()));
   }

   public final void zab() {
      Iterator var1 = this.zab.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Map var3 = (Map)this.zab.get(var2);
         HashMap var4 = new HashMap();
         Iterator var5 = var3.keySet().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var4.put(var6, ((FastJsonResponse.Field)var3.get(var6)).zaa());
         }

         this.zab.put(var2, var4);
      }

   }

   public final String zac() {
      return this.zad;
   }
}
