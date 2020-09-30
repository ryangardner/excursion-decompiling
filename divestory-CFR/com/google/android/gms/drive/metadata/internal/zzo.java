/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.ParentDriveIdSet;
import com.google.android.gms.drive.metadata.internal.zzg;
import com.google.android.gms.drive.metadata.internal.zzl;
import com.google.android.gms.drive.metadata.internal.zzp;
import com.google.android.gms.drive.metadata.internal.zzq;
import com.google.android.gms.drive.metadata.zzb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class zzo
extends zzl<DriveId>
implements SearchableCollectionMetadataField<DriveId> {
    public static final zzg zzjk = new zzp();

    public zzo(int n) {
        super("parents", Collections.<String>emptySet(), Arrays.asList("parentsExtra", "dbInstanceId", "parentsExtraHolder"), 4100000);
    }

    private static void zzc(DataHolder dataHolder) {
        Bundle bundle = dataHolder.getMetadata();
        if (bundle == null) {
            return;
        }
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder)bundle.getParcelable("parentsExtraHolder");
            if (dataHolder2 == null) return;
            dataHolder2.close();
            bundle.remove("parentsExtraHolder");
            return;
        }
    }

    static /* synthetic */ void zzd(DataHolder dataHolder) {
        zzo.zzc(dataHolder);
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return ((zzl)this).zzc(bundle);
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return ((zzb)this).zzd(dataHolder, n, n2);
    }

    @Override
    protected final Collection<DriveId> zzc(Bundle object) {
        if ((object = super.zzc((Bundle)object)) != null) return new HashSet<DriveId>((Collection<DriveId>)object);
        return null;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    protected final Collection<DriveId> zzd(DataHolder object, int n, int n2) {
        Bundle bundle = ((DataHolder)((Object)object)).getMetadata();
        Object object2 = bundle.getParcelableArrayList("parentsExtra");
        HashMap<Long, Object> hashMap = object2;
        if (object2 == null) {
            block17 : {
                if (bundle.getParcelable("parentsExtraHolder") != null) {
                    // MONITORENTER : object
                    object2 = (DataHolder)((DataHolder)((Object)object)).getMetadata().getParcelable("parentsExtraHolder");
                    if (object2 == null) {
                        // MONITOREXIT : object
                    } else {
                        Object object3;
                        int n3 = ((DataHolder)((Object)object)).getCount();
                        ArrayList<ParentDriveIdSet> arrayList = new ArrayList<ParentDriveIdSet>(n3);
                        hashMap = new HashMap<Long, Object>(n3);
                        int n4 = 0;
                        for (n2 = 0; n2 < n3; ++n2) {
                            int n5 = ((DataHolder)((Object)object)).getWindowIndex(n2);
                            object3 = new ParentDriveIdSet();
                            arrayList.add((ParentDriveIdSet)object3);
                            hashMap.put(((DataHolder)((Object)object)).getLong("sqlId", n2, n5), object3);
                        }
                        Object object4 = ((DataHolder)object2).getMetadata();
                        String string2 = object4.getString("childSqlIdColumn");
                        object3 = object4.getString("parentSqlIdColumn");
                        object4 = object4.getString("parentResIdColumn");
                        n3 = ((DataHolder)object2).getCount();
                        for (n2 = n4; n2 < n3; ++n2) {
                            n4 = ((DataHolder)object2).getWindowIndex(n2);
                            ParentDriveIdSet parentDriveIdSet = (ParentDriveIdSet)hashMap.get(((DataHolder)object2).getLong(string2, n2, n4));
                            zzq zzq2 = new zzq(((DataHolder)object2).getString((String)object4, n2, n4), ((DataHolder)object2).getLong((String)object3, n2, n4), 1);
                            parentDriveIdSet.zzjj.add(zzq2);
                        }
                        ((DataHolder)((Object)object)).getMetadata().putParcelableArrayList("parentsExtra", arrayList);
                        // MONITOREXIT : object
                    }
                    object2 = bundle.getParcelableArrayList("parentsExtra");
                    break block17;
                    catch (Throwable hashMap2) {
                        throw hashMap2;
                    }
                    finally {
                        ((DataHolder)object2).close();
                        ((DataHolder)((Object)object)).getMetadata().remove("parentsExtraHolder");
                    }
                }
            }
            hashMap = object2;
            if (object2 == null) {
                return null;
            }
        }
        long l = bundle.getLong("dbInstanceId");
        object2 = (ParentDriveIdSet)hashMap.get(n);
        object = new HashSet();
        object2 = ((ParentDriveIdSet)object2).zzjj.iterator();
        while (object2.hasNext()) {
            hashMap = (zzq)object2.next();
            object.add(new DriveId(((zzq)hashMap).zzad, ((zzq)hashMap).zzae, l, ((zzq)hashMap).zzaf));
        }
        return object;
    }
}

