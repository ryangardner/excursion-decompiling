/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.data.zaa;
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
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BitmapTeleporter
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zaa();
    private final int zaa;
    private ParcelFileDescriptor zab;
    private final int zac;
    private Bitmap zad;
    private boolean zae;
    private File zaf;

    BitmapTeleporter(int n, ParcelFileDescriptor parcelFileDescriptor, int n2) {
        this.zaa = n;
        this.zab = parcelFileDescriptor;
        this.zac = n2;
        this.zad = null;
        this.zae = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.zaa = 1;
        this.zab = null;
        this.zac = 0;
        this.zad = bitmap;
        this.zae = true;
    }

    private final FileOutputStream zaa() {
        FileOutputStream fileOutputStream;
        File file = this.zaf;
        if (file == null) throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        try {
            file = File.createTempFile("teleporter", ".tmp", file);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Could not create temporary file", iOException);
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            this.zab = ParcelFileDescriptor.open((File)file, (int)268435456);
            file.delete();
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new IllegalStateException("Temporary file is somehow already deleted");
        }
        return fileOutputStream;
    }

    private static void zaa(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"BitmapTeleporter", (String)"Could not close stream", (Throwable)iOException);
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public Bitmap get() {
        Throwable throwable2222;
        if (this.zae) return this.zad;
        Object object = new DataInputStream((InputStream)new ParcelFileDescriptor.AutoCloseInputStream(Preconditions.checkNotNull(this.zab)));
        byte[] arrby = new byte[((DataInputStream)object).readInt()];
        int n = ((DataInputStream)object).readInt();
        int n2 = ((DataInputStream)object).readInt();
        Bitmap.Config config = Bitmap.Config.valueOf((String)((DataInputStream)object).readUTF());
        ((DataInputStream)object).read(arrby);
        BitmapTeleporter.zaa((Closeable)object);
        object = ByteBuffer.wrap(arrby);
        config = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)config);
        config.copyPixelsFromBuffer((Buffer)object);
        this.zad = config;
        this.zae = true;
        return this.zad;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                IllegalStateException illegalStateException = new IllegalStateException("Could not read from parcel file descriptor", iOException);
                throw illegalStateException;
            }
        }
        BitmapTeleporter.zaa((Closeable)object);
        throw throwable2222;
    }

    public void release() {
        if (this.zae) return;
        try {
            Preconditions.checkNotNull(this.zab).close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"BitmapTeleporter", (String)"Could not close PFD", (Throwable)iOException);
        }
    }

    public void setTempDir(File file) {
        if (file == null) throw new NullPointerException("Cannot set null temp directory");
        this.zaf = file;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public void writeToParcel(Parcel parcel, int n) {
        Throwable throwable2222;
        Object object2;
        Object object;
        if (this.zab == null) {
            object2 = Preconditions.checkNotNull(this.zad);
            object = ByteBuffer.allocate(object2.getRowBytes() * object2.getHeight());
            object2.copyPixelsToBuffer((Buffer)object);
            byte[] arrby = ((ByteBuffer)object).array();
            object = new DataOutputStream(new BufferedOutputStream(this.zaa()));
            ((DataOutputStream)object).writeInt(arrby.length);
            ((DataOutputStream)object).writeInt(object2.getWidth());
            ((DataOutputStream)object).writeInt(object2.getHeight());
            ((DataOutputStream)object).writeUTF(object2.getConfig().toString());
            ((FilterOutputStream)object).write(arrby);
            BitmapTeleporter.zaa((Closeable)object);
        }
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeParcelable(parcel, 2, (Parcelable)this.zab, n | 1, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zac);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
        this.zab = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                object2 = new IllegalStateException("Could not write into unlinked file", iOException);
                throw object2;
            }
        }
        BitmapTeleporter.zaa((Closeable)object);
        throw throwable2222;
    }
}

