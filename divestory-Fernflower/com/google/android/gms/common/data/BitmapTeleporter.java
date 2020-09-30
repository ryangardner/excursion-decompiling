package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapTeleporter extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<BitmapTeleporter> CREATOR = new zaa();
   private final int zaa;
   private ParcelFileDescriptor zab;
   private final int zac;
   private Bitmap zad;
   private boolean zae;
   private File zaf;

   BitmapTeleporter(int var1, ParcelFileDescriptor var2, int var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = null;
      this.zae = false;
   }

   public BitmapTeleporter(Bitmap var1) {
      this.zaa = 1;
      this.zab = null;
      this.zac = 0;
      this.zad = var1;
      this.zae = true;
   }

   private final FileOutputStream zaa() {
      File var1 = this.zaf;
      if (var1 != null) {
         try {
            var1 = File.createTempFile("teleporter", ".tmp", var1);
         } catch (IOException var4) {
            throw new IllegalStateException("Could not create temporary file", var4);
         }

         FileOutputStream var2;
         try {
            var2 = new FileOutputStream(var1);
            this.zab = ParcelFileDescriptor.open(var1, 268435456);
         } catch (FileNotFoundException var3) {
            throw new IllegalStateException("Temporary file is somehow already deleted");
         }

         var1.delete();
         return var2;
      } else {
         throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
      }
   }

   private static void zaa(Closeable var0) {
      try {
         var0.close();
      } catch (IOException var1) {
         Log.w("BitmapTeleporter", "Could not close stream", var1);
      }
   }

   public Bitmap get() {
      if (!this.zae) {
         DataInputStream var1 = new DataInputStream(new AutoCloseInputStream((ParcelFileDescriptor)Preconditions.checkNotNull(this.zab)));

         byte[] var2;
         int var3;
         int var4;
         Config var11;
         try {
            var2 = new byte[var1.readInt()];
            var3 = var1.readInt();
            var4 = var1.readInt();
            var11 = Config.valueOf(var1.readUTF());
            var1.read(var2);
         } catch (IOException var8) {
            IllegalStateException var5 = new IllegalStateException("Could not read from parcel file descriptor", var8);
            throw var5;
         } finally {
            zaa(var1);
         }

         ByteBuffer var10 = ByteBuffer.wrap(var2);
         Bitmap var12 = Bitmap.createBitmap(var3, var4, var11);
         var12.copyPixelsFromBuffer(var10);
         this.zad = var12;
         this.zae = true;
      }

      return this.zad;
   }

   public void release() {
      if (!this.zae) {
         try {
            ((ParcelFileDescriptor)Preconditions.checkNotNull(this.zab)).close();
            return;
         } catch (IOException var2) {
            Log.w("BitmapTeleporter", "Could not close PFD", var2);
         }
      }

   }

   public void setTempDir(File var1) {
      if (var1 != null) {
         this.zaf = var1;
      } else {
         throw new NullPointerException("Cannot set null temp directory");
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      if (this.zab == null) {
         Bitmap var3 = (Bitmap)Preconditions.checkNotNull(this.zad);
         ByteBuffer var4 = ByteBuffer.allocate(var3.getRowBytes() * var3.getHeight());
         var3.copyPixelsToBuffer(var4);
         byte[] var5 = var4.array();
         DataOutputStream var12 = new DataOutputStream(new BufferedOutputStream(this.zaa()));

         try {
            var12.writeInt(var5.length);
            var12.writeInt(var3.getWidth());
            var12.writeInt(var3.getHeight());
            var12.writeUTF(var3.getConfig().toString());
            var12.write(var5);
         } catch (IOException var9) {
            IllegalStateException var11 = new IllegalStateException("Could not write into unlinked file", var9);
            throw var11;
         } finally {
            zaa(var12);
         }
      }

      int var6 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.zab, var2 | 1, false);
      SafeParcelWriter.writeInt(var1, 3, this.zac);
      SafeParcelWriter.finishObjectHeader(var1, var6);
      this.zab = null;
   }
}
