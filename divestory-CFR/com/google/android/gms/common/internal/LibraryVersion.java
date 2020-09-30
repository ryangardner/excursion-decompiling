/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.IOUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryVersion {
    private static final GmsLogger zza = new GmsLogger("LibraryVersion", "");
    private static LibraryVersion zzb = new LibraryVersion();
    private ConcurrentHashMap<String, String> zzc = new ConcurrentHashMap();

    protected LibraryVersion() {
    }

    public static LibraryVersion getInstance() {
        return zzb;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public String getVersion(String var1_1) {
        block15 : {
            block14 : {
                block17 : {
                    block16 : {
                        Preconditions.checkNotEmpty(var1_1, "Please provide a valid libraryName");
                        if (this.zzc.containsKey(var1_1)) {
                            return this.zzc.get(var1_1);
                        }
                        var2_5 = new Properties();
                        var3_6 = null;
                        var4_7 = null;
                        var5_8 = null;
                        var6_9 = null;
                        var7_13 = LibraryVersion.class.getResourceAsStream(String.format("/%s.properties", new Object[]{var1_1}));
                        if (var7_13 == null) ** GOTO lbl38
                        var4_7 = var3_6;
                        try {
                            block18 : {
                                var2_5.load((InputStream)var7_13);
                                var4_7 = var3_6;
                                var4_7 = var5_8 = var2_5.getProperty("version", null);
                                var6_9 = LibraryVersion.zza;
                                var4_7 = var5_8;
                                var8_14 = String.valueOf(var1_1).length();
                                var4_7 = var5_8;
                                var9_15 = String.valueOf(var5_8).length();
                                var4_7 = var5_8;
                                var4_7 = var5_8;
                                var3_6 = new StringBuilder(var8_14 + 12 + var9_15);
                                var4_7 = var5_8;
                                var3_6.append(var1_1);
                                var4_7 = var5_8;
                                var3_6.append(" version is ");
                                var4_7 = var5_8;
                                var3_6.append((String)var5_8);
                                var4_7 = var5_8;
                                var6_9.v("LibraryVersion", var3_6.toString());
                                break block18;
lbl38: // 1 sources:
                                var4_7 = var3_6;
                                var2_5 = LibraryVersion.zza;
                                var4_7 = var3_6;
                                var5_8 = String.valueOf(var1_1);
                                var4_7 = var3_6;
                                if (var5_8.length() != 0) {
                                    var4_7 = var3_6;
                                    var5_8 = "Failed to get app version for libraryName: ".concat((String)var5_8);
                                } else {
                                    var4_7 = var3_6;
                                    var5_8 = new String("Failed to get app version for libraryName: ");
                                }
                                var4_7 = var3_6;
                                var2_5.w("LibraryVersion", (String)var5_8);
                                var5_8 = var6_9;
                            }
                            var4_7 = var5_8;
                            ** if (var7_13 == null) goto lbl-1000
                        }
                        catch (Throwable var1_2) {
                            var4_7 = var7_13;
                            break block15;
                        }
                        catch (IOException var6_10) {
                            var5_8 = var7_13;
                            var7_13 = var4_7;
                            break block16;
                        }
lbl-1000: // 1 sources:
                        {
                            IOUtils.closeQuietly((Closeable)var7_13);
                            var4_7 = var5_8;
                        }
lbl-1000: // 2 sources:
                        {
                            break block14;
                        }
                        catch (Throwable var1_3) {
                            break block15;
                        }
                        catch (IOException var6_11) {
                            var7_13 = null;
                        }
                    }
                    var4_7 = var5_8;
                    {
                        var2_5 = LibraryVersion.zza;
                        var4_7 = var5_8;
                        var3_6 = String.valueOf(var1_1);
                        var4_7 = var5_8;
                        if (var3_6.length() != 0) {
                            var4_7 = var5_8;
                            var3_6 = "Failed to get app version for libraryName: ".concat((String)var3_6);
                        } else {
                            var4_7 = var5_8;
                            var3_6 = new String("Failed to get app version for libraryName: ");
                        }
                        var4_7 = var5_8;
                        var2_5.e("LibraryVersion", (String)var3_6, (Throwable)var6_12);
                        if (var5_8 == null) break block17;
                    }
                    IOUtils.closeQuietly((Closeable)var5_8);
                }
                var4_7 = var7_13;
            }
            var5_8 = var4_7;
            if (var4_7 == null) {
                LibraryVersion.zza.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version is expected during Google internal testing where locally-built libraries are used");
                var5_8 = "UNKNOWN";
            }
            this.zzc.put(var1_1, (String)var5_8);
            return var5_8;
        }
        if (var4_7 == null) throw var1_4;
        IOUtils.closeQuietly(var4_7);
        throw var1_4;
    }
}

