package com.google.android.gms.internal.drive;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzbi implements DriveContents {
   private static final GmsLogger zzbz = new GmsLogger("DriveContentsImpl", "");
   private boolean closed = false;
   private final Contents zzes;
   private boolean zzet = false;
   private boolean zzeu = false;

   public zzbi(Contents var1) {
      this.zzes = (Contents)Preconditions.checkNotNull(var1);
   }

   private final PendingResult<Status> zza(GoogleApiClient var1, MetadataChangeSet var2, com.google.android.gms.drive.zzn var3) {
      com.google.android.gms.drive.zzn var4 = var3;
      if (var3 == null) {
         var4 = (com.google.android.gms.drive.zzn)(new com.google.android.gms.drive.zzp()).build();
      }

      if (this.zzes.getMode() != 268435456) {
         if (ExecutionOptions.zza(var4.zzn()) && !this.zzes.zzb()) {
            throw new IllegalStateException("DriveContents must be valid for conflict detection.");
         } else {
            var4.zza(var1);
            if (!this.closed) {
               if (this.getDriveId() != null) {
                  if (var2 == null) {
                     var2 = MetadataChangeSet.zzax;
                  }

                  this.zzj();
                  return var1.execute(new zzbk(this, var1, var2, var4));
               } else {
                  throw new IllegalStateException("Only DriveContents obtained through DriveFile.open can be committed.");
               }
            } else {
               throw new IllegalStateException("DriveContents already closed.");
            }
         }
      } else {
         throw new IllegalStateException("Cannot commit contents opened with MODE_READ_ONLY");
      }
   }

   // $FF: synthetic method
   static Contents zza(zzbi var0) {
      return var0.zzes;
   }

   // $FF: synthetic method
   static GmsLogger zzx() {
      return zzbz;
   }

   public final PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2) {
      return this.zza(var1, var2, (com.google.android.gms.drive.zzn)null);
   }

   public final PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2, ExecutionOptions var3) {
      com.google.android.gms.drive.zzn var4;
      if (var3 == null) {
         var4 = null;
      } else {
         var4 = com.google.android.gms.drive.zzn.zza(var3);
      }

      return this.zza(var1, var2, var4);
   }

   public final void discard(GoogleApiClient var1) {
      if (!this.closed) {
         this.zzj();
         ((zzbm)var1.execute(new zzbm(this, var1))).setResultCallback(new zzbl(this));
      } else {
         throw new IllegalStateException("DriveContents already closed.");
      }
   }

   public final DriveId getDriveId() {
      return this.zzes.getDriveId();
   }

   public final InputStream getInputStream() {
      if (!this.closed) {
         if (this.zzes.getMode() == 268435456) {
            if (!this.zzet) {
               this.zzet = true;
               return this.zzes.getInputStream();
            } else {
               throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
            }
         } else {
            throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
         }
      } else {
         throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
      }
   }

   public final int getMode() {
      return this.zzes.getMode();
   }

   public final OutputStream getOutputStream() {
      if (!this.closed) {
         if (this.zzes.getMode() == 536870912) {
            if (!this.zzeu) {
               this.zzeu = true;
               return this.zzes.getOutputStream();
            } else {
               throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
            }
         } else {
            throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
         }
      } else {
         throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
      }
   }

   public final ParcelFileDescriptor getParcelFileDescriptor() {
      if (!this.closed) {
         return this.zzes.getParcelFileDescriptor();
      } else {
         throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
      }
   }

   public final PendingResult<DriveApi.DriveContentsResult> reopenForWrite(GoogleApiClient var1) {
      if (!this.closed) {
         if (this.zzes.getMode() == 268435456) {
            this.zzj();
            return var1.enqueue(new zzbj(this, var1));
         } else {
            throw new IllegalStateException("reopenForWrite can only be used with DriveContents opened with MODE_READ_ONLY.");
         }
      } else {
         throw new IllegalStateException("DriveContents already closed.");
      }
   }

   public final Contents zzi() {
      return this.zzes;
   }

   public final void zzj() {
      IOUtils.closeQuietly(this.zzes.getParcelFileDescriptor());
      this.closed = true;
   }

   public final boolean zzk() {
      return this.closed;
   }
}
