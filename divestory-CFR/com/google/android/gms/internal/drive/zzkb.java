/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzkc;
import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkn;
import com.google.android.gms.internal.drive.zzkt;
import com.google.android.gms.internal.drive.zzkw;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlr;
import com.google.android.gms.internal.drive.zzlx;
import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zznm;
import com.google.android.gms.internal.drive.zznr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzkb<FieldDescriptorType extends zzkd<FieldDescriptorType>> {
    private static final zzkb zzov = new zzkb<FieldDescriptorType>(true);
    final zzmi<FieldDescriptorType, Object> zzos;
    private boolean zzot;
    private boolean zzou = false;

    private zzkb() {
        this.zzos = zzmi.zzav(16);
    }

    private zzkb(boolean bl) {
        this.zzos = zzmi.zzav(0);
        this.zzbp();
    }

    static int zza(zznm zznm2, int n, Object object) {
        int n2;
        n = n2 = zzjr.zzab(n);
        if (zznm2 != zznm.zzxd) return n + zzkb.zzb(zznm2, object);
        zzkm.zzf((zzlq)object);
        n = n2 << 1;
        return n + zzkb.zzb(zznm2, object);
    }

    private final Object zza(FieldDescriptorType object) {
        Object object2 = this.zzos.get(object);
        object = object2;
        if (!(object2 instanceof zzkt)) return object;
        return zzkt.zzdp();
    }

    static void zza(zzjr zzjr2, zznm object, int n, Object object2) throws IOException {
        if (object == zznm.zzxd) {
            object = (zzlq)object2;
            zzkm.zzf((zzlq)object);
            zzjr2.zzb(n, 3);
            object.zzb(zzjr2);
            zzjr2.zzb(n, 4);
            return;
        }
        zzjr2.zzb(n, ((zznm)((Object)object)).zzfk());
        switch (zzkc.zzox[((Enum)object).ordinal()]) {
            default: {
                return;
            }
            case 18: {
                if (object2 instanceof zzkn) {
                    zzjr2.zzx(((zzkn)object2).zzcp());
                    return;
                }
                zzjr2.zzx((Integer)object2);
                return;
            }
            case 17: {
                zzjr2.zzm((Long)object2);
                return;
            }
            case 16: {
                zzjr2.zzz((Integer)object2);
                return;
            }
            case 15: {
                zzjr2.zzn((Long)object2);
                return;
            }
            case 14: {
                zzjr2.zzaa((Integer)object2);
                return;
            }
            case 13: {
                zzjr2.zzy((Integer)object2);
                return;
            }
            case 12: {
                if (object2 instanceof zzjc) {
                    zzjr2.zza((zzjc)object2);
                    return;
                }
                object = (byte[])object2;
                zzjr2.zzd((byte[])object, 0, ((Object)object).length);
                return;
            }
            case 11: {
                if (object2 instanceof zzjc) {
                    zzjr2.zza((zzjc)object2);
                    return;
                }
                zzjr2.zzl((String)object2);
                return;
            }
            case 10: {
                zzjr2.zzb((zzlq)object2);
                return;
            }
            case 9: {
                ((zzlq)object2).zzb(zzjr2);
                return;
            }
            case 8: {
                zzjr2.zzc((Boolean)object2);
                return;
            }
            case 7: {
                zzjr2.zzaa((Integer)object2);
                return;
            }
            case 6: {
                zzjr2.zzn((Long)object2);
                return;
            }
            case 5: {
                zzjr2.zzx((Integer)object2);
                return;
            }
            case 4: {
                zzjr2.zzl((Long)object2);
                return;
            }
            case 3: {
                zzjr2.zzl((Long)object2);
                return;
            }
            case 2: {
                zzjr2.zza(((Float)object2).floatValue());
                return;
            }
            case 1: 
        }
        zzjr2.zza((Double)object2);
    }

    private final void zza(FieldDescriptorType FieldDescriptorType, Object arrayList) {
        if (FieldDescriptorType.zzcs()) {
            if (!(arrayList instanceof List)) throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(arrayList);
            arrayList = arrayList2;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Object e = arrayList.get(i);
                zzkb.zza(FieldDescriptorType.zzcq(), e);
            }
            arrayList = arrayList2;
        } else {
            zzkb.zza(FieldDescriptorType.zzcq(), arrayList);
        }
        if (arrayList instanceof zzkt) {
            this.zzou = true;
        }
        this.zzos.zza(FieldDescriptorType, arrayList);
    }

    /*
     * Unable to fully structure code
     */
    private static void zza(zznm var0, Object var1_1) {
        block15 : {
            zzkm.checkNotNull(var1_1);
            var2_2 = zzkc.zzow[var0.zzfj().ordinal()];
            var3_3 = true;
            var4_4 = false;
            switch (var2_2) {
                default: {
                    ** break;
                }
                case 9: {
                    var4_4 = var3_3;
                    if (var1_1 instanceof zzlq) break block15;
                    if (var1_1 instanceof zzkt) {
                        var4_4 = var3_3;
                        ** break;
                    }
                    ** GOTO lbl30
                }
                case 8: {
                    var4_4 = var3_3;
                    if (var1_1 instanceof Integer) break block15;
                    if (var1_1 instanceof zzkn) {
                        var4_4 = var3_3;
                        ** break;
                    }
                    ** GOTO lbl30
                }
                case 7: {
                    var4_4 = var3_3;
                    if (!(var1_1 instanceof zzjc)) {
                        if (var1_1 instanceof byte[]) {
                            var4_4 = var3_3;
                            ** break;
                        } else {
                            ** GOTO lbl30
                        }
                    }
                    break block15;
lbl30: // 4 sources:
                    var4_4 = false;
                    ** break;
                }
                case 6: {
                    var4_4 = var1_1 instanceof String;
                    ** break;
                }
                case 5: {
                    var4_4 = var1_1 instanceof Boolean;
                    ** break;
                }
                case 4: {
                    var4_4 = var1_1 instanceof Double;
                    ** break;
                }
                case 3: {
                    var4_4 = var1_1 instanceof Float;
                    ** break;
                }
                case 2: {
                    var4_4 = var1_1 instanceof Long;
                    ** break;
                }
                case 1: 
            }
            var4_4 = var1_1 instanceof Integer;
        }
        if (var4_4 == false) throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    public static int zzb(zzkd<?> iterator2, Object object) {
        zznm zznm2 = iterator2.zzcq();
        int n = iterator2.zzcp();
        if (!iterator2.zzcs()) return zzkb.zza(zznm2, n, object);
        boolean bl = iterator2.zzct();
        int n2 = 0;
        int n3 = 0;
        if (bl) {
            iterator2 = ((List)object).iterator();
            while (iterator2.hasNext()) {
                n3 += zzkb.zzb(zznm2, iterator2.next());
            }
            return zzjr.zzab(n) + n3 + zzjr.zzaj(n3);
        }
        iterator2 = ((List)object).iterator();
        n3 = n2;
        while (iterator2.hasNext()) {
            n3 += zzkb.zza(zznm2, n, iterator2.next());
        }
        return n3;
    }

    private static int zzb(zznm zznm2, Object object) {
        switch (zzkc.zzox[zznm2.ordinal()]) {
            default: {
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
            }
            case 18: {
                if (!(object instanceof zzkn)) return zzjr.zzah((Integer)object);
                return zzjr.zzah(((zzkn)object).zzcp());
            }
            case 17: {
                return zzjr.zzq((Long)object);
            }
            case 16: {
                return zzjr.zzae((Integer)object);
            }
            case 15: {
                return zzjr.zzs((Long)object);
            }
            case 14: {
                return zzjr.zzag((Integer)object);
            }
            case 13: {
                return zzjr.zzad((Integer)object);
            }
            case 12: {
                if (!(object instanceof zzjc)) return zzjr.zzc((byte[])object);
                return zzjr.zzb((zzjc)object);
            }
            case 11: {
                if (!(object instanceof zzjc)) return zzjr.zzm((String)object);
                return zzjr.zzb((zzjc)object);
            }
            case 10: {
                if (!(object instanceof zzkt)) return zzjr.zzc((zzlq)object);
                return zzjr.zza((zzkt)object);
            }
            case 9: {
                return zzjr.zzd((zzlq)object);
            }
            case 8: {
                return zzjr.zzd((Boolean)object);
            }
            case 7: {
                return zzjr.zzaf((Integer)object);
            }
            case 6: {
                return zzjr.zzr((Long)object);
            }
            case 5: {
                return zzjr.zzac((Integer)object);
            }
            case 4: {
                return zzjr.zzp((Long)object);
            }
            case 3: {
                return zzjr.zzo((Long)object);
            }
            case 2: {
                return zzjr.zzb(((Float)object).floatValue());
            }
            case 1: 
        }
        return zzjr.zzb((Double)object);
    }

    private static boolean zzb(Map.Entry<FieldDescriptorType, Object> iterator2) {
        zzkd zzkd2 = (zzkd)iterator2.getKey();
        if (zzkd2.zzcr() != zznr.zzxx) return true;
        if (zzkd2.zzcs()) {
            iterator2 = ((List)iterator2.getValue()).iterator();
            do {
                if (!iterator2.hasNext()) return true;
            } while (((zzlq)iterator2.next()).isInitialized());
            return false;
        }
        if ((iterator2 = iterator2.getValue()) instanceof zzlq) {
            if (((zzlq)((Object)iterator2)).isInitialized()) return true;
            return false;
        }
        if (!(iterator2 instanceof zzkt)) throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        return true;
    }

    private final void zzc(Map.Entry<FieldDescriptorType, Object> object) {
        zzkd zzkd2 = (zzkd)object.getKey();
        Object object2 = object.getValue();
        object = object2;
        if (object2 instanceof zzkt) {
            object = zzkt.zzdp();
        }
        if (zzkd2.zzcs()) {
            Object object3 = this.zza(zzkd2);
            object2 = object3;
            if (object3 == null) {
                object2 = new ArrayList();
            }
            object = ((List)object).iterator();
            do {
                if (!object.hasNext()) {
                    this.zzos.zza(zzkd2, object2);
                    return;
                }
                object3 = object.next();
                ((List)object2).add(zzkb.zze(object3));
            } while (true);
        }
        if (zzkd2.zzcr() != zznr.zzxx) {
            this.zzos.zza(zzkd2, zzkb.zze(object));
            return;
        }
        object2 = this.zza(zzkd2);
        if (object2 == null) {
            this.zzos.zza(zzkd2, zzkb.zze(object));
            return;
        }
        object = object2 instanceof zzlx ? zzkd2.zza((zzlx)object2, (zzlx)object) : zzkd2.zza(((zzlq)object2).zzcy(), (zzlq)object).zzdf();
        this.zzos.zza(zzkd2, object);
    }

    public static <T extends zzkd<T>> zzkb<T> zzcn() {
        return zzov;
    }

    private static int zzd(Map.Entry<FieldDescriptorType, Object> entry) {
        zzkd zzkd2 = (zzkd)entry.getKey();
        Object object = entry.getValue();
        if (zzkd2.zzcr() != zznr.zzxx) return zzkb.zzb(zzkd2, object);
        if (zzkd2.zzcs()) return zzkb.zzb(zzkd2, object);
        if (zzkd2.zzct()) return zzkb.zzb(zzkd2, object);
        if (!(object instanceof zzkt)) return zzjr.zzb(((zzkd)entry.getKey()).zzcp(), (zzlq)object);
        return zzjr.zzb(((zzkd)entry.getKey()).zzcp(), (zzkt)object);
    }

    private static Object zze(Object arrby) {
        if (arrby instanceof zzlx) {
            return ((zzlx)arrby).zzef();
        }
        if (!(arrby instanceof byte[])) return arrby;
        arrby = arrby;
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby2;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        Map.Entry<FieldDescriptorType, Object> entry;
        zzkb<FieldDescriptorType> zzkb2 = new zzkb<FieldDescriptorType>();
        for (int i = 0; i < this.zzos.zzer(); ++i) {
            entry = this.zzos.zzaw(i);
            zzkb.super.zza((zzkd)entry.getKey(), entry.getValue());
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator2 = this.zzos.zzes().iterator();
        do {
            if (!iterator2.hasNext()) {
                zzkb2.zzou = this.zzou;
                return zzkb2;
            }
            entry = iterator2.next();
            zzkb.super.zza((zzkd)entry.getKey(), entry.getValue());
        } while (true);
    }

    final Iterator<Map.Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (!this.zzou) return this.zzos.zzet().iterator();
        return new zzkw(this.zzos.zzet().iterator());
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzkb)) {
            return false;
        }
        object = (zzkb)object;
        return this.zzos.equals(((zzkb)object).zzos);
    }

    public final int hashCode() {
        return this.zzos.hashCode();
    }

    public final boolean isImmutable() {
        return this.zzot;
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzos.zzer(); ++i) {
            if (zzkb.zzb(this.zzos.zzaw(i))) continue;
            return false;
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator2 = this.zzos.zzes().iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (zzkb.zzb(iterator2.next()));
        return false;
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (!this.zzou) return this.zzos.entrySet().iterator();
        return new zzkw(this.zzos.entrySet().iterator());
    }

    public final void zza(zzkb<FieldDescriptorType> object) {
        for (int i = 0; i < ((zzkb)object).zzos.zzer(); ++i) {
            this.zzc(((zzkb)object).zzos.zzaw(i));
        }
        object = ((zzkb)object).zzos.zzes().iterator();
        while (object.hasNext()) {
            this.zzc((Map.Entry)object.next());
        }
    }

    public final void zzbp() {
        if (this.zzot) {
            return;
        }
        this.zzos.zzbp();
        this.zzot = true;
    }

    public final int zzco() {
        int n = 0;
        for (int i = 0; i < this.zzos.zzer(); n += zzkb.zzd(this.zzos.zzaw((int)i)), ++i) {
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator2 = this.zzos.zzes().iterator();
        while (iterator2.hasNext()) {
            n += zzkb.zzd(iterator2.next());
        }
        return n;
    }
}

