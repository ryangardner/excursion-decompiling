package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class DataBufferSafeParcelable<T extends SafeParcelable> extends AbstractDataBuffer<T> {
   private static final String[] zaa = new String[]{"data"};
   private final Creator<T> zab;

   public DataBufferSafeParcelable(DataHolder var1, Creator<T> var2) {
      super(var1);
      this.zab = var2;
   }

   public static <T extends SafeParcelable> void addValue(DataHolder.Builder var0, T var1) {
      Parcel var2 = Parcel.obtain();
      var1.writeToParcel(var2, 0);
      ContentValues var3 = new ContentValues();
      var3.put("data", var2.marshall());
      var0.withRow(var3);
      var2.recycle();
   }

   public static DataHolder.Builder buildDataHolder() {
      return DataHolder.builder(zaa);
   }

   public T get(int var1) {
      DataHolder var2 = (DataHolder)Preconditions.checkNotNull(this.mDataHolder);
      byte[] var3 = var2.getByteArray("data", var1, var2.getWindowIndex(var1));
      Parcel var4 = Parcel.obtain();
      var4.unmarshall(var3, 0, var3.length);
      var4.setDataPosition(0);
      SafeParcelable var5 = (SafeParcelable)this.zab.createFromParcel(var4);
      var4.recycle();
      return var5;
   }
}
