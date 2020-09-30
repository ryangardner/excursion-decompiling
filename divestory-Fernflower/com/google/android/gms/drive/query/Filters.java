package com.google.android.gms.drive.query;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.query.internal.zzn;
import com.google.android.gms.drive.query.internal.zzp;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzv;
import com.google.android.gms.drive.query.internal.zzx;
import com.google.android.gms.drive.query.internal.zzz;

public class Filters {
   public static Filter and(Filter var0, Filter... var1) {
      Preconditions.checkNotNull(var0, "Filter may not be null.");
      Preconditions.checkNotNull(var1, "Additional filters may not be null.");
      return new zzr(zzx.zzmv, var0, var1);
   }

   public static Filter and(Iterable<Filter> var0) {
      Preconditions.checkNotNull(var0, "Filters may not be null");
      return new zzr(zzx.zzmv, var0);
   }

   public static Filter contains(SearchableMetadataField<String> var0, String var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzmy, var0, var1);
   }

   public static Filter eq(CustomPropertyKey var0, String var1) {
      Preconditions.checkNotNull(var0, "Custom property key may not be null.");
      Preconditions.checkNotNull(var1, "Custom property value may not be null.");
      return new zzn(SearchableField.zzlv, (new AppVisibleCustomProperties.zza()).zza(var0, var1).zzbb());
   }

   public static <T> Filter eq(SearchableMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzmq, var0, var1);
   }

   public static <T extends Comparable<T>> Filter greaterThan(SearchableOrderedMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzmt, var0, var1);
   }

   public static <T extends Comparable<T>> Filter greaterThanEquals(SearchableOrderedMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzmu, var0, var1);
   }

   public static <T> Filter in(SearchableCollectionMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new zzp(var0, var1);
   }

   public static <T extends Comparable<T>> Filter lessThan(SearchableOrderedMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzmr, var0, var1);
   }

   public static <T extends Comparable<T>> Filter lessThanEquals(SearchableOrderedMetadataField<T> var0, T var1) {
      Preconditions.checkNotNull(var0, "Field may not be null.");
      Preconditions.checkNotNull(var1, "Value may not be null.");
      return new com.google.android.gms.drive.query.internal.zzb(zzx.zzms, var0, var1);
   }

   public static Filter not(Filter var0) {
      Preconditions.checkNotNull(var0, "Filter may not be null");
      return new zzv(var0);
   }

   public static Filter openedByMe() {
      return new com.google.android.gms.drive.query.internal.zzd(SearchableField.LAST_VIEWED_BY_ME);
   }

   public static Filter or(Filter var0, Filter... var1) {
      Preconditions.checkNotNull(var0, "Filter may not be null.");
      Preconditions.checkNotNull(var1, "Additional filters may not be null.");
      return new zzr(zzx.zzmw, var0, var1);
   }

   public static Filter or(Iterable<Filter> var0) {
      Preconditions.checkNotNull(var0, "Filters may not be null");
      return new zzr(zzx.zzmw, var0);
   }

   public static Filter ownedByMe() {
      return new zzz();
   }

   public static Filter sharedWithMe() {
      return new com.google.android.gms.drive.query.internal.zzd(SearchableField.zzlu);
   }
}
