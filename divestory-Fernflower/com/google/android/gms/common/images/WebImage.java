package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends AbstractSafeParcelable {
   public static final Creator<WebImage> CREATOR = new zae();
   private final int zaa;
   private final Uri zab;
   private final int zac;
   private final int zad;

   WebImage(int var1, Uri var2, int var3, int var4) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = var4;
   }

   public WebImage(Uri var1) throws IllegalArgumentException {
      this(var1, 0, 0);
   }

   public WebImage(Uri var1, int var2, int var3) throws IllegalArgumentException {
      this(1, var1, var2, var3);
      if (var1 != null) {
         if (var2 < 0 || var3 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
         }
      } else {
         throw new IllegalArgumentException("url cannot be null");
      }
   }

   public WebImage(JSONObject var1) throws IllegalArgumentException {
      this(zaa(var1), var1.optInt("width", 0), var1.optInt("height", 0));
   }

   private static Uri zaa(JSONObject var0) {
      Uri var1 = Uri.EMPTY;
      Uri var2 = var1;
      if (var0.has("url")) {
         try {
            var2 = Uri.parse(var0.getString("url"));
         } catch (JSONException var3) {
            var2 = var1;
         }
      }

      return var2;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && var1 instanceof WebImage) {
            WebImage var2 = (WebImage)var1;
            if (Objects.equal(this.zab, var2.zab) && this.zac == var2.zac && this.zad == var2.zad) {
               return true;
            }
         }

         return false;
      }
   }

   public final int getHeight() {
      return this.zad;
   }

   public final Uri getUrl() {
      return this.zab;
   }

   public final int getWidth() {
      return this.zac;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zab, this.zac, this.zad);
   }

   public final JSONObject toJson() {
      JSONObject var1 = new JSONObject();

      try {
         var1.put("url", this.zab.toString());
         var1.put("width", this.zac);
         var1.put("height", this.zad);
      } catch (JSONException var3) {
      }

      return var1;
   }

   public final String toString() {
      return String.format(Locale.US, "Image %dx%d %s", this.zac, this.zad, this.zab.toString());
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.getUrl(), var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.getWidth());
      SafeParcelWriter.writeInt(var1, 4, this.getHeight());
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
