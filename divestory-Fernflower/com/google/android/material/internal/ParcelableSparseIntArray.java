package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseIntArray;

public class ParcelableSparseIntArray extends SparseIntArray implements Parcelable {
   public static final Creator<ParcelableSparseIntArray> CREATOR = new Creator<ParcelableSparseIntArray>() {
      public ParcelableSparseIntArray createFromParcel(Parcel var1) {
         int var2 = var1.readInt();
         ParcelableSparseIntArray var3 = new ParcelableSparseIntArray(var2);
         int[] var4 = new int[var2];
         int[] var5 = new int[var2];
         var1.readIntArray(var4);
         var1.readIntArray(var5);

         for(int var6 = 0; var6 < var2; ++var6) {
            var3.put(var4[var6], var5[var6]);
         }

         return var3;
      }

      public ParcelableSparseIntArray[] newArray(int var1) {
         return new ParcelableSparseIntArray[var1];
      }
   };

   public ParcelableSparseIntArray() {
   }

   public ParcelableSparseIntArray(int var1) {
      super(var1);
   }

   public ParcelableSparseIntArray(SparseIntArray var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.put(var1.keyAt(var2), var1.valueAt(var2));
      }

   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int[] var3 = new int[this.size()];
      int[] var4 = new int[this.size()];

      for(var2 = 0; var2 < this.size(); ++var2) {
         var3[var2] = this.keyAt(var2);
         var4[var2] = this.valueAt(var2);
      }

      var1.writeInt(this.size());
      var1.writeIntArray(var3);
      var1.writeIntArray(var4);
   }
}
