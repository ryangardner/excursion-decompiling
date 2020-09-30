/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzhy;
import com.google.android.gms.internal.drive.zzib;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzig;
import com.google.android.gms.internal.drive.zzih;
import com.google.android.gms.internal.drive.zzii;
import com.google.android.gms.internal.drive.zzij;
import com.google.android.gms.internal.drive.zzik;
import com.google.android.gms.internal.drive.zzil;
import java.util.Date;

public class SortableField {
    public static final SortableMetadataField<Date> CREATED_DATE;
    public static final SortableMetadataField<Date> LAST_VIEWED_BY_ME;
    public static final SortableMetadataField<Date> MODIFIED_BY_ME_DATE;
    public static final SortableMetadataField<Date> MODIFIED_DATE;
    public static final SortableMetadataField<Long> QUOTA_USED;
    public static final SortableMetadataField<Date> SHARED_WITH_ME_DATE;
    public static final SortableMetadataField<String> TITLE;
    private static final SortableMetadataField<Date> zzly;

    static {
        TITLE = zzhs.zzkr;
        CREATED_DATE = zzif.zzld;
        MODIFIED_DATE = zzif.zzlf;
        MODIFIED_BY_ME_DATE = zzif.zzlg;
        LAST_VIEWED_BY_ME = zzif.zzle;
        SHARED_WITH_ME_DATE = zzif.zzlh;
        QUOTA_USED = zzhs.zzko;
        zzly = zzif.zzli;
    }
}

