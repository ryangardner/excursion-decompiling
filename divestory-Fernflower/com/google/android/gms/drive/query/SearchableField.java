package com.google.android.gms.drive.query;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import java.util.Date;

public class SearchableField {
   public static final SearchableMetadataField<Boolean> IS_PINNED;
   public static final SearchableOrderedMetadataField<Date> LAST_VIEWED_BY_ME;
   public static final SearchableMetadataField<String> MIME_TYPE;
   public static final SearchableOrderedMetadataField<Date> MODIFIED_DATE;
   public static final SearchableCollectionMetadataField<DriveId> PARENTS;
   public static final SearchableMetadataField<Boolean> STARRED;
   public static final SearchableMetadataField<String> TITLE;
   public static final SearchableMetadataField<Boolean> TRASHED;
   public static final SearchableOrderedMetadataField<Date> zzlu;
   public static final SearchableMetadataField<AppVisibleCustomProperties> zzlv;

   static {
      TITLE = zzhs.zzkr;
      MIME_TYPE = zzhs.zzki;
      TRASHED = zzhs.zzks;
      PARENTS = zzhs.zzkn;
      zzlu = zzif.zzlh;
      STARRED = zzhs.zzkp;
      MODIFIED_DATE = zzif.zzlf;
      LAST_VIEWED_BY_ME = zzif.zzle;
      IS_PINNED = zzhs.zzka;
      zzlv = zzhs.zzjn;
   }
}
