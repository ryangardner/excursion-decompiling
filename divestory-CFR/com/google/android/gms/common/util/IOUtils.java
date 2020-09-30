/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

@Deprecated
public final class IOUtils {
    private IOUtils() {
    }

    public static void closeQuietly(@Nullable ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) return;
        try {
            parcelFileDescriptor.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @Deprecated
    public static long copyStream(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        return IOUtils.zza(inputStream2, outputStream2, false);
    }

    @Deprecated
    public static long copyStream(InputStream inputStream2, OutputStream outputStream2, boolean bl, int n) throws IOException {
        byte[] arrby = new byte[n];
        long l = 0L;
        try {
            int n2;
            while ((n2 = inputStream2.read(arrby, 0, n)) != -1) {
                l += (long)n2;
                outputStream2.write(arrby, 0, n2);
            }
            return l;
        }
        finally {
            if (bl) {
                IOUtils.closeQuietly(inputStream2);
                IOUtils.closeQuietly(outputStream2);
            }
        }
    }

    public static boolean isGzipByteBuffer(byte[] arrby) {
        if (arrby.length <= 1) return false;
        byte by = arrby[0];
        if (((arrby[1] & 255) << 8 | by & 255) != 35615) return false;
        return true;
    }

    @Deprecated
    public static byte[] readInputStreamFully(InputStream inputStream2) throws IOException {
        return IOUtils.readInputStreamFully(inputStream2, true);
    }

    @Deprecated
    public static byte[] readInputStreamFully(InputStream inputStream2, boolean bl) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.zza(inputStream2, byteArrayOutputStream, bl);
        return byteArrayOutputStream.toByteArray();
    }

    @Deprecated
    public static byte[] toByteArray(InputStream inputStream2) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(byteArrayOutputStream);
        byte[] arrby = new byte[4096];
        while ((n = inputStream2.read(arrby)) != -1) {
            ((OutputStream)byteArrayOutputStream).write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Deprecated
    private static long zza(InputStream inputStream2, OutputStream outputStream2, boolean bl) throws IOException {
        return IOUtils.copyStream(inputStream2, outputStream2, bl, 1024);
    }
}

