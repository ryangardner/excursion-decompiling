package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzt;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Query extends AbstractSafeParcelable {
   public static final Creator<Query> CREATOR = new zzb();
   private final List<DriveSpace> zzby;
   private final zzr zzlm;
   private final String zzln;
   private final SortOrder zzlo;
   final List<String> zzlp;
   final boolean zzlq;
   final boolean zzlr;

   Query(zzr var1, String var2, SortOrder var3, List<String> var4, boolean var5, List<DriveSpace> var6, boolean var7) {
      this.zzlm = var1;
      this.zzln = var2;
      this.zzlo = var3;
      this.zzlp = var4;
      this.zzlq = var5;
      this.zzby = var6;
      this.zzlr = var7;
   }

   private Query(zzr var1, String var2, SortOrder var3, List<String> var4, boolean var5, Set<DriveSpace> var6, boolean var7) {
      this(var1, var2, var3, var4, var5, (List)(new ArrayList(var6)), var7);
   }

   // $FF: synthetic method
   Query(zzr var1, String var2, SortOrder var3, List var4, boolean var5, Set var6, boolean var7, zza var8) {
      this(var1, var2, var3, var4, var5, var6, var7);
   }

   public Filter getFilter() {
      return this.zzlm;
   }

   @Deprecated
   public String getPageToken() {
      return this.zzln;
   }

   public SortOrder getSortOrder() {
      return this.zzlo;
   }

   public String toString() {
      return String.format(Locale.US, "Query[%s,%s,PageToken=%s,Spaces=%s]", this.zzlm, this.zzlo, this.zzln, this.zzby);
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzlm, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.zzln, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzlo, var2, false);
      SafeParcelWriter.writeStringList(var1, 5, this.zzlp, false);
      SafeParcelWriter.writeBoolean(var1, 6, this.zzlq);
      SafeParcelWriter.writeTypedList(var1, 7, this.zzby, false);
      SafeParcelWriter.writeBoolean(var1, 8, this.zzlr);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final Set<DriveSpace> zzbi() {
      return this.zzby == null ? new HashSet() : new HashSet(this.zzby);
   }

   public static class Builder {
      private String zzln;
      private SortOrder zzlo;
      private List<String> zzlp = Collections.emptyList();
      private boolean zzlq;
      private boolean zzlr;
      private final List<Filter> zzls = new ArrayList();
      private Set<DriveSpace> zzlt = Collections.emptySet();

      public Builder() {
      }

      public Builder(Query var1) {
         this.zzls.add(var1.getFilter());
         this.zzln = var1.getPageToken();
         this.zzlo = var1.getSortOrder();
         this.zzlp = var1.zzlp;
         this.zzlq = var1.zzlq;
         var1.zzbi();
         this.zzlt = var1.zzbi();
         this.zzlr = var1.zzlr;
      }

      public Query.Builder addFilter(Filter var1) {
         Preconditions.checkNotNull(var1, "Filter may not be null.");
         if (!(var1 instanceof zzt)) {
            this.zzls.add(var1);
         }

         return this;
      }

      public Query build() {
         return new Query(new zzr(zzx.zzmv, this.zzls), this.zzln, this.zzlo, this.zzlp, this.zzlq, this.zzlt, this.zzlr, (zza)null);
      }

      @Deprecated
      public Query.Builder setPageToken(String var1) {
         this.zzln = var1;
         return this;
      }

      public Query.Builder setSortOrder(SortOrder var1) {
         this.zzlo = var1;
         return this;
      }
   }
}
