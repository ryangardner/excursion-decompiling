package com.google.android.gms.drive.metadata;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomPropertyKey extends AbstractSafeParcelable {
   public static final Creator<CustomPropertyKey> CREATOR = new zzc();
   public static final int PRIVATE = 1;
   public static final int PUBLIC = 0;
   private static final Pattern zzja = Pattern.compile("[\\w.!@$%^&*()/-]+");
   private final int visibility;
   private final String zziz;

   public CustomPropertyKey(String var1, int var2) {
      Preconditions.checkNotNull(var1, "key");
      Preconditions.checkArgument(zzja.matcher(var1).matches(), "key name characters must be alphanumeric or one of .!@$%^&*()-_/");
      boolean var3 = true;
      boolean var4 = var3;
      if (var2 != 0) {
         if (var2 == 1) {
            var4 = var3;
         } else {
            var4 = false;
         }
      }

      Preconditions.checkArgument(var4, "visibility must be either PUBLIC or PRIVATE");
      this.zziz = var1;
      this.visibility = var2;
   }

   public static CustomPropertyKey fromJson(JSONObject var0) throws JSONException {
      return new CustomPropertyKey(var0.getString("key"), var0.getInt("visibility"));
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         if (var1 != null && var1.getClass() == this.getClass()) {
            CustomPropertyKey var2 = (CustomPropertyKey)var1;
            if (var2.getKey().equals(this.zziz) && var2.getVisibility() == this.visibility) {
               return true;
            }
         }

         return false;
      }
   }

   public String getKey() {
      return this.zziz;
   }

   public int getVisibility() {
      return this.visibility;
   }

   public int hashCode() {
      String var1 = this.zziz;
      int var2 = this.visibility;
      StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 11);
      var3.append(var1);
      var3.append(var2);
      return var3.toString().hashCode();
   }

   public JSONObject toJson() throws JSONException {
      JSONObject var1 = new JSONObject();
      var1.put("key", this.getKey());
      var1.put("visibility", this.getVisibility());
      return var1;
   }

   public String toString() {
      String var1 = this.zziz;
      int var2 = this.visibility;
      StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 31);
      var3.append("CustomPropertyKey(");
      var3.append(var1);
      var3.append(",");
      var3.append(var2);
      var3.append(")");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.zziz, false);
      SafeParcelWriter.writeInt(var1, 3, this.visibility);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
