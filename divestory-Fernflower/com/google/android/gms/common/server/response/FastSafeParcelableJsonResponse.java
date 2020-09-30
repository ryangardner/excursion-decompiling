package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;

public abstract class FastSafeParcelableJsonResponse extends FastJsonResponse implements SafeParcelable {
   public final int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (this == var1) {
         return true;
      } else if (!this.getClass().isInstance(var1)) {
         return false;
      } else {
         FastJsonResponse var2 = (FastJsonResponse)var1;
         Iterator var3 = this.getFieldMappings().values().iterator();

         FastJsonResponse.Field var4;
         label37:
         do {
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               var4 = (FastJsonResponse.Field)var3.next();
               if (this.isFieldSet(var4)) {
                  continue label37;
               }
            } while(!var2.isFieldSet(var4));

            return false;
         } while(var2.isFieldSet(var4) && Objects.equal(this.getFieldValue(var4), var2.getFieldValue(var4)));

         return false;
      }
   }

   public Object getValueObject(String var1) {
      return null;
   }

   public int hashCode() {
      Iterator var1 = this.getFieldMappings().values().iterator();
      int var2 = 0;

      while(var1.hasNext()) {
         FastJsonResponse.Field var3 = (FastJsonResponse.Field)var1.next();
         if (this.isFieldSet(var3)) {
            var2 = var2 * 31 + Preconditions.checkNotNull(this.getFieldValue(var3)).hashCode();
         }
      }

      return var2;
   }

   public boolean isPrimitiveFieldSet(String var1) {
      return false;
   }

   public byte[] toByteArray() {
      Parcel var1 = Parcel.obtain();
      this.writeToParcel(var1, 0);
      byte[] var2 = var1.marshall();
      var1.recycle();
      return var2;
   }
}
