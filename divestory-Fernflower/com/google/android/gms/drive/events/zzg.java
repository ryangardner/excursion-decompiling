package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.List;

public final class zzg implements Creator<CompletionEvent> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DriveId var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      Object var8 = var3;
      Object var9 = var3;
      int var10 = 0;

      while(var1.dataPosition() < var2) {
         int var11 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var11)) {
         case 2:
            var3 = (DriveId)SafeParcelReader.createParcelable(var1, var11, DriveId.CREATOR);
            break;
         case 3:
            var4 = SafeParcelReader.createString(var1, var11);
            break;
         case 4:
            var5 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var11, ParcelFileDescriptor.CREATOR);
            break;
         case 5:
            var6 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var11, ParcelFileDescriptor.CREATOR);
            break;
         case 6:
            var7 = (MetadataBundle)SafeParcelReader.createParcelable(var1, var11, MetadataBundle.CREATOR);
            break;
         case 7:
            var8 = SafeParcelReader.createStringList(var1, var11);
            break;
         case 8:
            var10 = SafeParcelReader.readInt(var1, var11);
            break;
         case 9:
            var9 = SafeParcelReader.readIBinder(var1, var11);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var11);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new CompletionEvent(var3, (String)var4, (ParcelFileDescriptor)var5, (ParcelFileDescriptor)var6, (MetadataBundle)var7, (List)var8, var10, (IBinder)var9);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new CompletionEvent[var1];
   }
}
