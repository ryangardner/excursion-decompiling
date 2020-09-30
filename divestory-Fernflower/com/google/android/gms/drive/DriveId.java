package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.drive.zzbn;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzfb;
import com.google.android.gms.internal.drive.zzfd;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkq;

public class DriveId extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<DriveId> CREATOR = new zzk();
   public static final int RESOURCE_TYPE_FILE = 0;
   public static final int RESOURCE_TYPE_FOLDER = 1;
   public static final int RESOURCE_TYPE_UNKNOWN = -1;
   private final String zzad;
   private final long zzae;
   private final int zzaf;
   private volatile String zzag = null;
   private final long zzf;
   private volatile String zzh = null;

   public DriveId(String var1, long var2, long var4, int var6) {
      this.zzad = var1;
      boolean var7 = "".equals(var1);
      boolean var8 = true;
      Preconditions.checkArgument(var7 ^ true);
      var7 = var8;
      if (var1 == null) {
         if (var2 != -1L) {
            var7 = var8;
         } else {
            var7 = false;
         }
      }

      Preconditions.checkArgument(var7);
      this.zzae = var2;
      this.zzf = var4;
      this.zzaf = var6;
   }

   public static DriveId decodeFromString(String var0) {
      boolean var1 = var0.startsWith("DriveId:");
      String var2 = String.valueOf(var0);
      if (var2.length() != 0) {
         var2 = "Invalid DriveId: ".concat(var2);
      } else {
         var2 = new String("Invalid DriveId: ");
      }

      Preconditions.checkArgument(var1, var2);
      return zza(Base64.decode(var0.substring(8), 10));
   }

   public static DriveId zza(String var0) {
      Preconditions.checkNotNull(var0);
      return new DriveId(var0, -1L, -1L, -1);
   }

   private static DriveId zza(byte[] var0) {
      zzfb var1;
      try {
         var1 = zzfb.zza(var0, zzjx.zzcj());
      } catch (zzkq var2) {
         throw new IllegalArgumentException();
      }

      String var3;
      if ("".equals(var1.getResourceId())) {
         var3 = null;
      } else {
         var3 = var1.getResourceId();
      }

      return new DriveId(var3, var1.zzal(), var1.zzam(), var1.getResourceType());
   }

   public DriveFile asDriveFile() {
      if (this.zzaf != 1) {
         return new zzbn(this);
      } else {
         throw new IllegalStateException("This DriveId corresponds to a folder. Call asDriveFolder instead.");
      }
   }

   public DriveFolder asDriveFolder() {
      if (this.zzaf != 0) {
         return new zzbs(this);
      } else {
         throw new IllegalStateException("This DriveId corresponds to a file. Call asDriveFile instead.");
      }
   }

   public DriveResource asDriveResource() {
      int var1 = this.zzaf;
      if (var1 == 1) {
         return this.asDriveFolder();
      } else {
         return (DriveResource)(var1 == 0 ? this.asDriveFile() : new zzdp(this));
      }
   }

   public final String encodeToString() {
      if (this.zzh == null) {
         zzfb.zza var1 = zzfb.zzan().zzm(1);
         String var2 = this.zzad;
         String var3 = var2;
         if (var2 == null) {
            var3 = "";
         }

         var3 = String.valueOf(Base64.encodeToString(((zzfb)((zzkk)var1.zze(var3).zzg(this.zzae).zzh(this.zzf).zzn(this.zzaf).zzdf())).toByteArray(), 10));
         if (var3.length() != 0) {
            var3 = "DriveId:".concat(var3);
         } else {
            var3 = new String("DriveId:");
         }

         this.zzh = var3;
      }

      return this.zzh;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == DriveId.class) {
         DriveId var4 = (DriveId)var1;
         if (var4.zzf != this.zzf) {
            return false;
         }

         if (var4.zzae == -1L && this.zzae == -1L) {
            return var4.zzad.equals(this.zzad);
         }

         String var2 = this.zzad;
         if (var2 != null) {
            String var3 = var4.zzad;
            if (var3 != null) {
               if (var4.zzae == this.zzae && var3.equals(var2)) {
                  return true;
               }

               return false;
            }
         }

         if (var4.zzae == this.zzae) {
            return true;
         }
      }

      return false;
   }

   public String getResourceId() {
      return this.zzad;
   }

   public int getResourceType() {
      return this.zzaf;
   }

   public int hashCode() {
      if (this.zzae == -1L) {
         return this.zzad.hashCode();
      } else {
         String var1 = String.valueOf(String.valueOf(this.zzf));
         String var2 = String.valueOf(String.valueOf(this.zzae));
         if (var2.length() != 0) {
            var1 = var1.concat(var2);
         } else {
            var1 = new String(var1);
         }

         return var1.hashCode();
      }
   }

   public final String toInvariantString() {
      if (this.zzag == null) {
         this.zzag = Base64.encodeToString(((zzfd)((zzkk)zzfd.zzap().zzi(this.zzae).zzj(this.zzf).zzdf())).toByteArray(), 10);
      }

      return this.zzag;
   }

   public String toString() {
      return this.encodeToString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.zzad, false);
      SafeParcelWriter.writeLong(var1, 3, this.zzae);
      SafeParcelWriter.writeLong(var1, 4, this.zzf);
      SafeParcelWriter.writeInt(var1, 5, this.zzaf);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
