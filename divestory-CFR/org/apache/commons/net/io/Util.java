/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import org.apache.commons.net.io.CopyStreamException;
import org.apache.commons.net.io.CopyStreamListener;

public final class Util {
    public static final int DEFAULT_COPY_BUFFER_SIZE = 1024;

    private Util() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket == null) return;
        try {
            socket.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static final long copyReader(Reader reader, Writer writer) throws CopyStreamException {
        return Util.copyReader(reader, writer, 1024);
    }

    public static final long copyReader(Reader reader, Writer writer, int n) throws CopyStreamException {
        return Util.copyReader(reader, writer, n, -1L, null);
    }

    public static final long copyReader(Reader reader, Writer writer, int n, long l, CopyStreamListener copyStreamListener) throws CopyStreamException {
        if (n <= 0) {
            n = 1024;
        }
        char[] arrc = new char[n];
        long l2 = 0L;
        do {
            long l3 = l2;
            try {
                long l4;
                n = reader.read(arrc);
                if (n == -1) return l2;
                if (n == 0) {
                    l3 = l2;
                    n = reader.read();
                    if (n < 0) {
                        return l2;
                    }
                    l3 = l2;
                    writer.write(n);
                    l3 = l2;
                    writer.flush();
                    l2 = l4 = l2 + 1L;
                    if (copyStreamListener == null) continue;
                    l3 = l4;
                    copyStreamListener.bytesTransferred(l4, 1, l);
                    l2 = l4;
                    continue;
                }
                l3 = l2;
                writer.write(arrc, 0, n);
                l3 = l2;
                writer.flush();
                l2 = l4 = l2 + (long)n;
                if (copyStreamListener == null) continue;
                l3 = l4;
                copyStreamListener.bytesTransferred(l4, n, l);
                l2 = l4;
            }
            catch (IOException iOException) {
                throw new CopyStreamException("IOException caught while copying.", l3, iOException);
            }
        } while (true);
    }

    public static final long copyStream(InputStream inputStream2, OutputStream outputStream2) throws CopyStreamException {
        return Util.copyStream(inputStream2, outputStream2, 1024);
    }

    public static final long copyStream(InputStream inputStream2, OutputStream outputStream2, int n) throws CopyStreamException {
        return Util.copyStream(inputStream2, outputStream2, n, -1L, null);
    }

    public static final long copyStream(InputStream inputStream2, OutputStream outputStream2, int n, long l, CopyStreamListener copyStreamListener) throws CopyStreamException {
        return Util.copyStream(inputStream2, outputStream2, n, l, copyStreamListener, true);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static final long copyStream(InputStream var0, OutputStream var1_4, int var2_5, long var3_6, CopyStreamListener var5_7, boolean var6_8) throws CopyStreamException {
        if (var2_5 <= 0) {
            var2_5 = 1024;
        }
        var7_9 = new byte[var2_5];
        var8_10 = 0L;
        do {
            do {
                var2_5 = var0.read(var7_9);
                if (var2_5 == -1) return var8_10;
                if (var2_5 != 0) ** break block11
                var2_5 = var0.read();
                if (var2_5 < 0) {
                    return var8_10;
                }
                var1_4.write(var2_5);
                if (var6_8) {
                    var1_4.flush();
                }
                var8_10 = var10_11 = var8_10 + 1L;
            } while (var5_7 == null);
            var8_10 = var10_11;
            var5_7.bytesTransferred(var10_11, 1, var3_6);
            var8_10 = var10_11;
            break;
        } while (true);
        catch (IOException var0_1) {
            throw new CopyStreamException("IOException caught while copying.", var8_10, (IOException)var0_3);
        }
        {
            block12 : {
                var1_4.write(var7_9, 0, var2_5);
                if (!var6_8) break block12;
                var1_4.flush();
            }
            var8_10 = var10_11 = var8_10 + (long)var2_5;
            if (var5_7 == null) continue;
            var8_10 = var10_11;
            var5_7.bytesTransferred(var10_11, var2_5, var3_6);
            var8_10 = var10_11;
            continue;
        }
        catch (IOException var0_2) {
            // empty catch block
        }
        throw new CopyStreamException("IOException caught while copying.", var8_10, (IOException)var0_3);
    }
}

