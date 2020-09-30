package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import java.util.ArrayList;
import java.util.Iterator;

public final class SafeParcelableSerializer {
   public static <T extends SafeParcelable> T deserializeFromBytes(byte[] var0, Creator<T> var1) {
      Preconditions.checkNotNull(var1);
      Parcel var2 = Parcel.obtain();
      var2.unmarshall(var0, 0, var0.length);
      var2.setDataPosition(0);
      SafeParcelable var3 = (SafeParcelable)var1.createFromParcel(var2);
      var2.recycle();
      return var3;
   }

   public static <T extends SafeParcelable> T deserializeFromIntentExtra(Intent var0, String var1, Creator<T> var2) {
      byte[] var3 = var0.getByteArrayExtra(var1);
      return var3 == null ? null : deserializeFromBytes(var3, var2);
   }

   public static <T extends SafeParcelable> T deserializeFromString(String var0, Creator<T> var1) {
      return deserializeFromBytes(Base64Utils.decodeUrlSafe(var0), var1);
   }

   public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromBundle(Bundle var0, String var1, Creator<T> var2) {
      ArrayList var7 = (ArrayList)var0.getSerializable(var1);
      if (var7 == null) {
         return null;
      } else {
         ArrayList var6 = new ArrayList(var7.size());
         var7 = (ArrayList)var7;
         int var3 = var7.size();
         int var4 = 0;

         while(var4 < var3) {
            Object var5 = var7.get(var4);
            ++var4;
            var6.add(deserializeFromBytes((byte[])var5, var2));
         }

         return var6;
      }
   }

   public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromIntentExtra(Intent var0, String var1, Creator<T> var2) {
      ArrayList var7 = (ArrayList)var0.getSerializableExtra(var1);
      if (var7 == null) {
         return null;
      } else {
         ArrayList var6 = new ArrayList(var7.size());
         ArrayList var3 = (ArrayList)var7;
         int var4 = var3.size();
         int var5 = 0;

         while(var5 < var4) {
            Object var8 = var3.get(var5);
            ++var5;
            var6.add(deserializeFromBytes((byte[])var8, var2));
         }

         return var6;
      }
   }

   public static <T extends SafeParcelable> void serializeIterableToBundle(Iterable<T> var0, Bundle var1, String var2) {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         var3.add(serializeToBytes((SafeParcelable)var4.next()));
      }

      var1.putSerializable(var2, var3);
   }

   public static <T extends SafeParcelable> void serializeIterableToIntentExtra(Iterable<T> var0, Intent var1, String var2) {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         var3.add(serializeToBytes((SafeParcelable)var4.next()));
      }

      var1.putExtra(var2, var3);
   }

   public static <T extends SafeParcelable> byte[] serializeToBytes(T var0) {
      Parcel var1 = Parcel.obtain();
      var0.writeToParcel(var1, 0);
      byte[] var2 = var1.marshall();
      var1.recycle();
      return var2;
   }

   public static <T extends SafeParcelable> void serializeToIntentExtra(T var0, Intent var1, String var2) {
      var1.putExtra(var2, serializeToBytes(var0));
   }

   public static <T extends SafeParcelable> String serializeToString(T var0) {
      return Base64Utils.encodeUrlSafe(serializeToBytes(var0));
   }
}
