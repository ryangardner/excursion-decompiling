package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzin;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public abstract class Metadata implements Freezable<Metadata> {
   public static final int CONTENT_AVAILABLE_LOCALLY = 1;
   public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

   public String getAlternateLink() {
      return (String)this.zza(zzhs.zzjm);
   }

   public int getContentAvailability() {
      Integer var1 = (Integer)this.zza(zzin.zzlk);
      return var1 == null ? 0 : var1;
   }

   public Date getCreatedDate() {
      return (Date)this.zza(zzif.zzld);
   }

   public Map<CustomPropertyKey, String> getCustomProperties() {
      AppVisibleCustomProperties var1 = (AppVisibleCustomProperties)this.zza(zzhs.zzjn);
      return var1 == null ? Collections.emptyMap() : var1.zzba();
   }

   public String getDescription() {
      return (String)this.zza(zzhs.zzjo);
   }

   public DriveId getDriveId() {
      return (DriveId)this.zza(zzhs.zzjl);
   }

   public String getEmbedLink() {
      return (String)this.zza(zzhs.zzjp);
   }

   public String getFileExtension() {
      return (String)this.zza(zzhs.zzjq);
   }

   public long getFileSize() {
      return (Long)this.zza(zzhs.zzjr);
   }

   public Date getLastViewedByMeDate() {
      return (Date)this.zza(zzif.zzle);
   }

   public String getMimeType() {
      return (String)this.zza(zzhs.zzki);
   }

   public Date getModifiedByMeDate() {
      return (Date)this.zza(zzif.zzlg);
   }

   public Date getModifiedDate() {
      return (Date)this.zza(zzif.zzlf);
   }

   public String getOriginalFilename() {
      return (String)this.zza(zzhs.zzkj);
   }

   public long getQuotaBytesUsed() {
      return (Long)this.zza(zzhs.zzko);
   }

   public Date getSharedWithMeDate() {
      return (Date)this.zza(zzif.zzlh);
   }

   public String getTitle() {
      return (String)this.zza(zzhs.zzkr);
   }

   public String getWebContentLink() {
      return (String)this.zza(zzhs.zzkt);
   }

   public String getWebViewLink() {
      return (String)this.zza(zzhs.zzku);
   }

   public boolean isEditable() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzjx);
      return var1 == null ? false : var1;
   }

   public boolean isExplicitlyTrashed() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzjy);
      return var1 == null ? false : var1;
   }

   public boolean isFolder() {
      return "application/vnd.google-apps.folder".equals(this.getMimeType());
   }

   public boolean isInAppFolder() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzjv);
      return var1 == null ? false : var1;
   }

   public boolean isPinnable() {
      Boolean var1 = (Boolean)this.zza(zzin.zzll);
      return var1 == null ? false : var1;
   }

   public boolean isPinned() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzka);
      return var1 == null ? false : var1;
   }

   public boolean isRestricted() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzkc);
      return var1 == null ? false : var1;
   }

   public boolean isShared() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzkd);
      return var1 == null ? false : var1;
   }

   public boolean isStarred() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzkp);
      return var1 == null ? false : var1;
   }

   public boolean isTrashable() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzkg);
      return var1 == null || var1;
   }

   public boolean isTrashed() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzks);
      return var1 == null ? false : var1;
   }

   public boolean isViewed() {
      Boolean var1 = (Boolean)this.zza(zzhs.zzkh);
      return var1 == null ? false : var1;
   }

   public abstract <T> T zza(MetadataField<T> var1);
}
