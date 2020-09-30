/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.zzb;
import com.google.android.gms.drive.metadata.internal.zzg;
import com.google.android.gms.drive.metadata.internal.zzo;
import com.google.android.gms.drive.metadata.internal.zzu;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzhv;
import com.google.android.gms.internal.drive.zzhw;
import com.google.android.gms.internal.drive.zzhx;
import com.google.android.gms.internal.drive.zzhy;
import com.google.android.gms.internal.drive.zzhz;
import com.google.android.gms.internal.drive.zzia;
import com.google.android.gms.internal.drive.zzib;
import com.google.android.gms.internal.drive.zzic;
import com.google.android.gms.internal.drive.zzid;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzig;
import com.google.android.gms.internal.drive.zzih;
import com.google.android.gms.internal.drive.zzii;
import com.google.android.gms.internal.drive.zzij;
import com.google.android.gms.internal.drive.zzik;
import com.google.android.gms.internal.drive.zzil;
import com.google.android.gms.internal.drive.zzin;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzf {
    private static final Map<String, MetadataField<?>> zzjf = new HashMap();
    private static final Map<String, zzg> zzjg = new HashMap<String, zzg>();

    static {
        zzf.zzb(zzhs.zzjl);
        zzf.zzb(zzhs.zzkr);
        zzf.zzb(zzhs.zzki);
        zzf.zzb(zzhs.zzkp);
        zzf.zzb(zzhs.zzks);
        zzf.zzb(zzhs.zzjy);
        zzf.zzb(zzhs.zzjx);
        zzf.zzb(zzhs.zzjz);
        zzf.zzb(zzhs.zzka);
        zzf.zzb(zzhs.zzkb);
        zzf.zzb(zzhs.zzjv);
        zzf.zzb(zzhs.zzkd);
        zzf.zzb(zzhs.zzke);
        zzf.zzb(zzhs.zzkf);
        zzf.zzb(zzhs.zzkn);
        zzf.zzb(zzhs.zzjm);
        zzf.zzb(zzhs.zzkk);
        zzf.zzb(zzhs.zzjo);
        zzf.zzb(zzhs.zzjw);
        zzf.zzb(zzhs.zzjp);
        zzf.zzb(zzhs.zzjq);
        zzf.zzb(zzhs.zzjr);
        zzf.zzb(zzhs.zzjs);
        zzf.zzb(zzhs.zzkh);
        zzf.zzb(zzhs.zzkc);
        zzf.zzb(zzhs.zzkj);
        zzf.zzb(zzhs.zzkl);
        zzf.zzb(zzhs.zzkm);
        zzf.zzb(zzhs.zzko);
        zzf.zzb(zzhs.zzkt);
        zzf.zzb(zzhs.zzku);
        zzf.zzb(zzhs.zzju);
        zzf.zzb(zzhs.zzjt);
        zzf.zzb(zzhs.zzkq);
        zzf.zzb(zzhs.zzkg);
        zzf.zzb(zzhs.zzjn);
        zzf.zzb(zzhs.zzkv);
        zzf.zzb(zzhs.zzkw);
        zzf.zzb(zzhs.zzkx);
        zzf.zzb(zzhs.zzky);
        zzf.zzb(zzhs.zzkz);
        zzf.zzb(zzhs.zzla);
        zzf.zzb(zzhs.zzlb);
        zzf.zzb(zzif.zzld);
        zzf.zzb(zzif.zzlf);
        zzf.zzb(zzif.zzlg);
        zzf.zzb(zzif.zzlh);
        zzf.zzb(zzif.zzle);
        zzf.zzb(zzif.zzli);
        zzf.zzb(zzin.zzlk);
        zzf.zzb(zzin.zzll);
        zzf.zza(zzo.zzjk);
        zzf.zza(zzid.zzlc);
    }

    public static void zza(DataHolder dataHolder) {
        Iterator<zzg> iterator2 = zzjg.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().zzb(dataHolder);
        }
    }

    private static void zza(zzg object) {
        if (zzjg.put(object.zzbd(), (zzg)object) == null) {
            return;
        }
        String string2 = object.zzbd();
        object = new StringBuilder(String.valueOf(string2).length() + 46);
        ((StringBuilder)object).append("A cleaner for key ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" has already been registered");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private static void zzb(MetadataField<?> object) {
        if (!zzjf.containsKey(object.getName())) {
            zzjf.put(object.getName(), (MetadataField<?>)object);
            return;
        }
        if (((String)(object = String.valueOf(object.getName()))).length() != 0) {
            object = "Duplicate field name registered: ".concat((String)object);
            throw new IllegalArgumentException((String)object);
        }
        object = new String("Duplicate field name registered: ");
        throw new IllegalArgumentException((String)object);
    }

    public static Collection<MetadataField<?>> zzbc() {
        return Collections.unmodifiableCollection(zzjf.values());
    }

    public static MetadataField<?> zzf(String string2) {
        return zzjf.get(string2);
    }
}

