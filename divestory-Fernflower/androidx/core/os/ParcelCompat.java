package androidx.core.os;

import android.os.Parcel;

public final class ParcelCompat {
   private ParcelCompat() {
   }

   public static boolean readBoolean(Parcel var0) {
      boolean var1;
      if (var0.readInt() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void writeBoolean(Parcel var0, boolean var1) {
      var0.writeInt(var1);
   }
}
