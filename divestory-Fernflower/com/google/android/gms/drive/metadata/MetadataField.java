package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public interface MetadataField<T> {
   String getName();

   T zza(Bundle var1);

   T zza(DataHolder var1, int var2, int var3);

   void zza(DataHolder var1, MetadataBundle var2, int var3, int var4);

   void zza(T var1, Bundle var2);
}
