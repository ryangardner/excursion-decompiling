/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.IOUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.annotation.Nullable;

public class ProcessUtils {
    @Nullable
    private static String zza;
    private static int zzb;

    private ProcessUtils() {
    }

    public static String getMyProcessName() {
        if (zza != null) return zza;
        if (zzb == 0) {
            zzb = Process.myPid();
        }
        zza = ProcessUtils.zza(zzb);
        return zza;
    }

    private static BufferedReader zza(String object) throws IOException {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskReads();
        try {
            FileReader fileReader = new FileReader((String)object);
            object = new BufferedReader(fileReader);
            return object;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Nullable
    private static String zza(int n) {
        Object object;
        Object var1_1;
        void var1_4;
        block7 : {
            String string2;
            var1_1 = null;
            if (n <= 0) {
                return null;
            }
            object = new StringBuilder(25);
            ((StringBuilder)object).append("/proc/");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("/cmdline");
            object = ProcessUtils.zza(((StringBuilder)object).toString());
            try {
                string2 = Preconditions.checkNotNull(((BufferedReader)object).readLine()).trim();
            }
            catch (Throwable throwable) {
                break block7;
            }
            IOUtils.closeQuietly((Closeable)object);
            return string2;
            catch (Throwable throwable) {
                object = null;
            }
        }
        IOUtils.closeQuietly((Closeable)object);
        throw var1_4;
        catch (IOException iOException) {
            block8 : {
                object = null;
                break block8;
                catch (IOException iOException2) {}
            }
            IOUtils.closeQuietly((Closeable)object);
            return var1_1;
        }
    }
}

