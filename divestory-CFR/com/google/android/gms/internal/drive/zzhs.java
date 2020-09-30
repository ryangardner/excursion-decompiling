/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.zzb;
import com.google.android.gms.drive.metadata.internal.zzi;
import com.google.android.gms.drive.metadata.internal.zzo;
import com.google.android.gms.drive.metadata.internal.zzs;
import com.google.android.gms.drive.metadata.internal.zzt;
import com.google.android.gms.drive.metadata.internal.zzu;
import com.google.android.gms.internal.drive.zzht;
import com.google.android.gms.internal.drive.zzhu;
import com.google.android.gms.internal.drive.zzhv;
import com.google.android.gms.internal.drive.zzhw;
import com.google.android.gms.internal.drive.zzhx;
import com.google.android.gms.internal.drive.zzhy;
import com.google.android.gms.internal.drive.zzhz;
import com.google.android.gms.internal.drive.zzia;
import com.google.android.gms.internal.drive.zzib;
import com.google.android.gms.internal.drive.zzic;
import com.google.android.gms.internal.drive.zzim;
import java.util.Collection;
import java.util.Collections;

public final class zzhs {
    public static final MetadataField<DriveId> zzjl = zzim.zzlj;
    public static final MetadataField<String> zzjm = new zzt("alternateLink", 4300000);
    public static final zzhv zzjn = new zzhv(5000000);
    public static final MetadataField<String> zzjo = new zzt("description", 4300000);
    public static final MetadataField<String> zzjp = new zzt("embedLink", 4300000);
    public static final MetadataField<String> zzjq = new zzt("fileExtension", 4300000);
    public static final MetadataField<Long> zzjr = new zzi("fileSize", 4300000);
    public static final MetadataField<String> zzjs = new zzt("folderColorRgb", 7500000);
    public static final MetadataField<Boolean> zzjt = new zzb("hasThumbnail", 4300000);
    public static final MetadataField<String> zzju = new zzt("indexableText", 4300000);
    public static final MetadataField<Boolean> zzjv = new zzb("isAppData", 4300000);
    public static final MetadataField<Boolean> zzjw = new zzb("isCopyable", 4300000);
    public static final MetadataField<Boolean> zzjx = new zzb("isEditable", 4100000);
    public static final MetadataField<Boolean> zzjy = new zzht("isExplicitlyTrashed", Collections.singleton("trashed"), Collections.emptySet(), 7000000);
    public static final MetadataField<Boolean> zzjz = new zzb("isLocalContentUpToDate", 7800000);
    public static final zzhw zzka = new zzhw("isPinned", 4100000);
    public static final MetadataField<Boolean> zzkb = new zzb("isOpenable", 7200000);
    public static final MetadataField<Boolean> zzkc = new zzb("isRestricted", 4300000);
    public static final MetadataField<Boolean> zzkd = new zzb("isShared", 4300000);
    public static final MetadataField<Boolean> zzke = new zzb("isGooglePhotosFolder", 7000000);
    public static final MetadataField<Boolean> zzkf = new zzb("isGooglePhotosRootFolder", 7000000);
    public static final MetadataField<Boolean> zzkg = new zzb("isTrashable", 4400000);
    public static final MetadataField<Boolean> zzkh = new zzb("isViewed", 4300000);
    public static final zzhx zzki = new zzhx(4100000);
    public static final MetadataField<String> zzkj = new zzt("originalFilename", 4300000);
    public static final com.google.android.gms.drive.metadata.zzb<String> zzkk = new zzs("ownerNames", 4300000);
    public static final zzu zzkl = new zzu("lastModifyingUser", 6000000);
    public static final zzu zzkm = new zzu("sharingUser", 6000000);
    public static final zzo zzkn = new zzo(4100000);
    public static final zzhy zzko = new zzhy("quotaBytesUsed", 4300000);
    public static final zzia zzkp = new zzia("starred", 4100000);
    public static final MetadataField<BitmapTeleporter> zzkq = new zzhu("thumbnail", Collections.emptySet(), Collections.emptySet(), 4400000);
    public static final zzib zzkr = new zzib("title", 4100000);
    public static final zzic zzks = new zzic("trashed", 4100000);
    public static final MetadataField<String> zzkt = new zzt("webContentLink", 4300000);
    public static final MetadataField<String> zzku = new zzt("webViewLink", 4300000);
    public static final MetadataField<String> zzkv = new zzt("uniqueIdentifier", 5000000);
    public static final zzb zzkw = new zzb("writersCanShare", 6000000);
    public static final MetadataField<String> zzkx = new zzt("role", 6000000);
    public static final MetadataField<String> zzky = new zzt("md5Checksum", 7000000);
    public static final zzhz zzkz = new zzhz(7000000);
    public static final MetadataField<String> zzla = new zzt("recencyReason", 8000000);
    public static final MetadataField<Boolean> zzlb = new zzb("subscribed", 8000000);
}

