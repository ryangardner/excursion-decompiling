package com.google.android.gms.drive;

import android.graphics.Bitmap;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public final class MetadataChangeSet {
   public static final int CUSTOM_PROPERTY_SIZE_LIMIT_BYTES = 124;
   public static final int INDEXABLE_TEXT_SIZE_LIMIT_BYTES = 131072;
   public static final int MAX_PRIVATE_PROPERTIES_PER_RESOURCE_PER_APP = 30;
   public static final int MAX_PUBLIC_PROPERTIES_PER_RESOURCE = 30;
   public static final int MAX_TOTAL_PROPERTIES_PER_RESOURCE = 100;
   public static final MetadataChangeSet zzax = new MetadataChangeSet(MetadataBundle.zzbe());
   private final MetadataBundle zzay;

   public MetadataChangeSet(MetadataBundle var1) {
      this.zzay = var1.zzbf();
   }

   public final Map<CustomPropertyKey, String> getCustomPropertyChangeMap() {
      AppVisibleCustomProperties var1 = (AppVisibleCustomProperties)this.zzay.zza((MetadataField)zzhs.zzjn);
      return var1 == null ? Collections.emptyMap() : var1.zzba();
   }

   public final String getDescription() {
      return (String)this.zzay.zza(zzhs.zzjo);
   }

   public final String getIndexableText() {
      return (String)this.zzay.zza(zzhs.zzju);
   }

   public final Date getLastViewedByMeDate() {
      return (Date)this.zzay.zza((MetadataField)zzif.zzle);
   }

   public final String getMimeType() {
      return (String)this.zzay.zza((MetadataField)zzhs.zzki);
   }

   public final Bitmap getThumbnail() {
      BitmapTeleporter var1 = (BitmapTeleporter)this.zzay.zza(zzhs.zzkq);
      return var1 == null ? null : var1.get();
   }

   public final String getTitle() {
      return (String)this.zzay.zza((MetadataField)zzhs.zzkr);
   }

   public final Boolean isPinned() {
      return (Boolean)this.zzay.zza((MetadataField)zzhs.zzka);
   }

   public final Boolean isStarred() {
      return (Boolean)this.zzay.zza((MetadataField)zzhs.zzkp);
   }

   public final Boolean isViewed() {
      return (Boolean)this.zzay.zza(zzhs.zzkh);
   }

   public final MetadataBundle zzq() {
      return this.zzay;
   }

   public static class Builder {
      private final MetadataBundle zzay = MetadataBundle.zzbe();
      private AppVisibleCustomProperties.zza zzaz;

      private static void zza(String var0, int var1, int var2) {
         boolean var3;
         if (var2 <= var1) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, String.format(Locale.US, "%s must be no more than %d bytes, but is %d bytes.", var0, var1, var2));
      }

      private static int zzb(String var0) {
         return var0 == null ? 0 : var0.getBytes().length;
      }

      private final AppVisibleCustomProperties.zza zzr() {
         if (this.zzaz == null) {
            this.zzaz = new AppVisibleCustomProperties.zza();
         }

         return this.zzaz;
      }

      public MetadataChangeSet build() {
         if (this.zzaz != null) {
            this.zzay.zzb(zzhs.zzjn, this.zzaz.zzbb());
         }

         return new MetadataChangeSet(this.zzay);
      }

      public MetadataChangeSet.Builder deleteCustomProperty(CustomPropertyKey var1) {
         Preconditions.checkNotNull(var1, "key");
         this.zzr().zza(var1, (String)null);
         return this;
      }

      public MetadataChangeSet.Builder setCustomProperty(CustomPropertyKey var1, String var2) {
         Preconditions.checkNotNull(var1, "key");
         Preconditions.checkNotNull(var2, "value");
         zza("The total size of key string and value string of a custom property", 124, zzb(var1.getKey()) + zzb(var2));
         this.zzr().zza(var1, var2);
         return this;
      }

      public MetadataChangeSet.Builder setDescription(String var1) {
         this.zzay.zzb(zzhs.zzjo, var1);
         return this;
      }

      public MetadataChangeSet.Builder setIndexableText(String var1) {
         zza("Indexable text size", 131072, zzb(var1));
         this.zzay.zzb(zzhs.zzju, var1);
         return this;
      }

      public MetadataChangeSet.Builder setLastViewedByMeDate(Date var1) {
         this.zzay.zzb(zzif.zzle, var1);
         return this;
      }

      public MetadataChangeSet.Builder setMimeType(String var1) {
         Preconditions.checkNotNull(var1);
         this.zzay.zzb(zzhs.zzki, var1);
         return this;
      }

      public MetadataChangeSet.Builder setPinned(boolean var1) {
         this.zzay.zzb(zzhs.zzka, var1);
         return this;
      }

      public MetadataChangeSet.Builder setStarred(boolean var1) {
         this.zzay.zzb(zzhs.zzkp, var1);
         return this;
      }

      public MetadataChangeSet.Builder setTitle(String var1) {
         Preconditions.checkNotNull(var1, "Title cannot be null.");
         this.zzay.zzb(zzhs.zzkr, var1);
         return this;
      }

      public MetadataChangeSet.Builder setViewed() {
         this.zzay.zzb(zzhs.zzkh, true);
         return this;
      }

      @Deprecated
      public MetadataChangeSet.Builder setViewed(boolean var1) {
         if (var1) {
            this.zzay.zzb(zzhs.zzkh, true);
         } else if (this.zzay.zzd(zzhs.zzkh)) {
            this.zzay.zzc(zzhs.zzkh);
         }

         return this;
      }
   }
}
