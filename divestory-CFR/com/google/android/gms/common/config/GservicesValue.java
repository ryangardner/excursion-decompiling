/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.google.android.gms.common.config;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.config.zzb;
import com.google.android.gms.common.config.zzc;
import com.google.android.gms.common.config.zzd;
import com.google.android.gms.common.config.zze;
import java.util.Set;

public abstract class GservicesValue<T> {
    private static final Object zzc = new Object();
    private static zza zzd = null;
    private static int zze = 0;
    private static Context zzf;
    private static Set<String> zzg;
    protected final String zza;
    protected final T zzb;
    private T zzh = null;

    protected GservicesValue(String string2, T t) {
        this.zza = string2;
        this.zzb = t;
    }

    public static boolean isInitialized() {
        Object object = zzc;
        synchronized (object) {
            return false;
        }
    }

    public static GservicesValue<Float> value(String string2, Float f) {
        return new zzc(string2, f);
    }

    public static GservicesValue<Integer> value(String string2, Integer n) {
        return new zzd(string2, n);
    }

    public static GservicesValue<Long> value(String string2, Long l) {
        return new com.google.android.gms.common.config.zza(string2, l);
    }

    public static GservicesValue<String> value(String string2, String string3) {
        return new zze(string2, string3);
    }

    public static GservicesValue<Boolean> value(String string2, boolean bl) {
        return new zzb(string2, bl);
    }

    private static boolean zza() {
        Object object = zzc;
        synchronized (object) {
            return false;
        }
    }

    /*
     * Exception decompiling
     */
    public final T get() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    @Deprecated
    public final T getBinderSafe() {
        return this.get();
    }

    public void override(T object) {
        Log.w((String)"GservicesValue", (String)"GservicesValue.override(): test should probably call initForTests() first");
        this.zzh = object;
        object = zzc;
        synchronized (object) {
            GservicesValue.zza();
            return;
        }
    }

    public void resetOverride() {
        this.zzh = null;
    }

    protected abstract T zza(String var1);

    private static interface zza {
        public Boolean zza(String var1, Boolean var2);

        public Float zza(String var1, Float var2);

        public Integer zza(String var1, Integer var2);

        public Long zza(String var1, Long var2);

        public String zza(String var1, String var2);
    }

}

