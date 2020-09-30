/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 */
package com.google.android.gms.internal.drive;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.zzc;
import com.google.android.gms.drive.metadata.internal.zzg;
import com.google.android.gms.drive.metadata.internal.zzm;
import com.google.android.gms.internal.drive.zzie;
import java.util.Arrays;
import java.util.Collection;

public class zzid
extends zzm<AppVisibleCustomProperties> {
    public static final zzg zzlc = new zzie();

    public zzid(int n) {
        super("customProperties", Arrays.asList("hasCustomProperties", "sqlId"), Arrays.asList("customPropertiesExtra", "customPropertiesExtraHolder"), 5000000);
    }

    private static void zzc(DataHolder dataHolder) {
        Bundle bundle = dataHolder.getMetadata();
        if (bundle == null) {
            return;
        }
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder)bundle.getParcelable("customPropertiesExtraHolder");
            if (dataHolder2 == null) return;
            dataHolder2.close();
            bundle.remove("customPropertiesExtraHolder");
            return;
        }
    }

    static /* synthetic */ void zzd(DataHolder dataHolder) {
        zzid.zzc(dataHolder);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private static AppVisibleCustomProperties zzf(DataHolder dataHolder, int n, int n2) {
        Object object2;
        Object object;
        block15 : {
            Bundle bundle = dataHolder.getMetadata();
            object2 = bundle.getSparseParcelableArray("customPropertiesExtra");
            object = object2;
            if (object2 != null) return (AppVisibleCustomProperties)object.get(n, (Object)AppVisibleCustomProperties.zzjb);
            if (bundle.getParcelable("customPropertiesExtraHolder") != null) {
                // MONITORENTER : dataHolder
                DataHolder dataHolder2 = (DataHolder)dataHolder.getMetadata().getParcelable("customPropertiesExtraHolder");
                if (dataHolder2 == null) {
                    // MONITOREXIT : dataHolder
                } else {
                    object2 = dataHolder2.getMetadata();
                    String string2 = object2.getString("entryIdColumn");
                    String string3 = object2.getString("keyColumn");
                    String string4 = object2.getString("visibilityColumn");
                    String string5 = object2.getString("valueColumn");
                    LongSparseArray<SparseArray> longSparseArray = new LongSparseArray<SparseArray>();
                    for (n2 = 0; n2 < dataHolder2.getCount(); ++n2) {
                        int n3 = dataHolder2.getWindowIndex(n2);
                        long l = dataHolder2.getLong(string2, n2, n3);
                        String string6 = dataHolder2.getString(string3, n2, n3);
                        int n4 = dataHolder2.getInteger(string4, n2, n3);
                        object2 = dataHolder2.getString(string5, n2, n3);
                        object = new CustomPropertyKey(string6, n4);
                        zzc zzc2 = new zzc((CustomPropertyKey)object, (String)object2);
                        object = (AppVisibleCustomProperties.zza)longSparseArray.get(l);
                        object2 = object;
                        if (object == null) {
                            object2 = new AppVisibleCustomProperties.zza();
                            longSparseArray.put(l, (SparseArray)object2);
                        }
                        object2.zza(zzc2);
                    }
                    object2 = new SparseArray();
                    for (n2 = 0; n2 < dataHolder.getCount(); ++n2) {
                        object = (AppVisibleCustomProperties.zza)longSparseArray.get(dataHolder.getLong("sqlId", n2, dataHolder.getWindowIndex(n2)));
                        if (object == null) continue;
                        object2.append(n2, (Object)((AppVisibleCustomProperties.zza)object).zzbb());
                    }
                    dataHolder.getMetadata().putSparseParcelableArray("customPropertiesExtra", object2);
                    // MONITOREXIT : dataHolder
                }
                object2 = bundle.getSparseParcelableArray("customPropertiesExtra");
                break block15;
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                    dataHolder2.close();
                    dataHolder.getMetadata().remove("customPropertiesExtraHolder");
                }
            }
        }
        object = object2;
        if (object2 != null) return (AppVisibleCustomProperties)object.get(n, (Object)AppVisibleCustomProperties.zzjb);
        return AppVisibleCustomProperties.zzjb;
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return zzid.zzf(dataHolder, n, n2);
    }
}

