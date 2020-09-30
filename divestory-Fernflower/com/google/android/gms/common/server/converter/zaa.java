package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;

public final class zaa extends AbstractSafeParcelable {
   public static final Creator<zaa> CREATOR = new zab();
   private final int zaa;
   private final StringToIntConverter zab;

   zaa(int var1, StringToIntConverter var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   private zaa(StringToIntConverter var1) {
      this.zaa = 1;
      this.zab = var1;
   }

   public static zaa zaa(FastJsonResponse.FieldConverter<?, ?> var0) {
      if (var0 instanceof StringToIntConverter) {
         return new zaa((StringToIntConverter)var0);
      } else {
         throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.zab, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final FastJsonResponse.FieldConverter<?, ?> zaa() {
      StringToIntConverter var1 = this.zab;
      if (var1 != null) {
         return var1;
      } else {
         throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
      }
   }
}
