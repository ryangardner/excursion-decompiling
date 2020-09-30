/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

final class zzkg
extends Enum<zzkg> {
    public static final /* enum */ zzkg zzrg;
    public static final /* enum */ zzkg zzrh;
    public static final /* enum */ zzkg zzri;
    public static final /* enum */ zzkg zzrj;
    private static final /* synthetic */ zzkg[] zzrl;
    private final boolean zzrk;

    static {
        zzkg zzkg2;
        zzrg = new zzkg(false);
        zzrh = new zzkg(true);
        zzri = new zzkg(true);
        zzrj = zzkg2 = new zzkg(false);
        zzrl = new zzkg[]{zzrg, zzrh, zzri, zzkg2};
    }

    private zzkg(boolean bl) {
        this.zzrk = bl;
    }

    public static zzkg[] values() {
        return (zzkg[])zzrl.clone();
    }
}

