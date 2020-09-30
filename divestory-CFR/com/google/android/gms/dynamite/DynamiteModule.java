/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.zzc;
import com.google.android.gms.dynamite.zzd;
import com.google.android.gms.dynamite.zze;
import com.google.android.gms.dynamite.zzf;
import com.google.android.gms.dynamite.zzg;
import com.google.android.gms.dynamite.zzh;
import com.google.android.gms.dynamite.zzj;
import com.google.android.gms.dynamite.zzk;
import com.google.android.gms.dynamite.zzl;
import com.google.android.gms.dynamite.zzm;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class DynamiteModule {
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION;
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING;
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION;
    public static final VersionPolicy PREFER_LOCAL;
    public static final VersionPolicy PREFER_REMOTE;
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING;
    private static Boolean zza;
    private static zzk zzb;
    private static zzm zzc;
    private static String zzd;
    private static int zze = -1;
    private static final ThreadLocal<zza> zzf;
    private static final VersionPolicy.zzb zzg;
    private static final VersionPolicy zzh;
    private final Context zzi;

    static {
        zzf = new ThreadLocal();
        zzg = new com.google.android.gms.dynamite.zzb();
        PREFER_REMOTE = new com.google.android.gms.dynamite.zza();
        PREFER_LOCAL = new zzd();
        PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzc();
        PREFER_HIGHEST_OR_LOCAL_VERSION = new zzf();
        PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zze();
        PREFER_HIGHEST_OR_REMOTE_VERSION = new zzh();
        zzh = new zzg();
    }

    private DynamiteModule(Context context) {
        this.zzi = Preconditions.checkNotNull(context);
    }

    public static int getLocalVersion(Context object, String string2) {
        try {
            Object object2 = object.getApplicationContext().getClassLoader();
            int n = String.valueOf(string2).length();
            object = new StringBuilder(n + 61);
            ((StringBuilder)object).append("com.google.android.gms.dynamite.descriptors.");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(".ModuleDescriptor");
            object2 = ((ClassLoader)object2).loadClass(((StringBuilder)object).toString());
            object = ((Class)object2).getDeclaredField("MODULE_ID");
            object2 = ((Class)object2).getDeclaredField("MODULE_VERSION");
            if (Objects.equal(((Field)object).get(null), string2)) return ((Field)object2).getInt(null);
            object = String.valueOf(((Field)object).get(null));
            int n2 = String.valueOf(object).length();
            n = String.valueOf(string2).length();
            object2 = new StringBuilder(n2 + 51 + n);
            ((StringBuilder)object2).append("Module descriptor id '");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("' didn't match expected id '");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("'");
            Log.e((String)"DynamiteModule", (String)((StringBuilder)object2).toString());
            return 0;
        }
        catch (Exception exception) {
            String string3 = String.valueOf(exception.getMessage());
            string3 = string3.length() != 0 ? "Failed to load module descriptor class: ".concat(string3) : new String("Failed to load module descriptor class: ");
            Log.e((String)"DynamiteModule", (String)string3);
            return 0;
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 45);
            stringBuilder.append("Local module descriptor class for ");
            stringBuilder.append(string2);
            stringBuilder.append(" not found.");
            Log.w((String)"DynamiteModule", (String)stringBuilder.toString());
        }
        return 0;
    }

    public static int getRemoteVersion(Context context, String string2) {
        return DynamiteModule.zza(context, string2, false);
    }

    /*
     * Exception decompiling
     */
    public static DynamiteModule load(Context var0, VersionPolicy var1_2, String var2_3) throws LoadingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    /*
     * Exception decompiling
     */
    public static int zza(Context var0, String var1_1, boolean var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 25[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    private static DynamiteModule zza(Context context, String string2) {
        string2 = (string2 = String.valueOf(string2)).length() != 0 ? "Selected local version of ".concat(string2) : new String("Selected local version of ");
        Log.i((String)"DynamiteModule", (String)string2);
        return new DynamiteModule(context.getApplicationContext());
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private static DynamiteModule zza(Context var0, String var1_3, int var2_5) throws LoadingException {
        var3_6 = DynamiteModule.zza;
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        if (var3_6 == null) ** GOTO lbl43
        try {
            if (var3_6.booleanValue()) {
                return DynamiteModule.zzb(var0, (String)var1_3, var2_5);
            }
            var4_7 = String.valueOf(var1_3).length();
            var3_6 = new StringBuilder(var4_7 + 51);
            var3_6.append("Selected remote version of ");
            var3_6.append((String)var1_3);
            var3_6.append(", version >= ");
            var3_6.append(var2_5);
            Log.i((String)"DynamiteModule", (String)var3_6.toString());
            var3_6 = DynamiteModule.zza(var0);
            if (var3_6 == null) {
                var1_3 = new LoadingException("Failed to create IDynamiteLoader.", null);
                throw var1_3;
            }
            var4_7 = var3_6.zzb();
            if (var4_7 >= 3) {
                var5_8 = DynamiteModule.zzf.get();
                if (var5_8 == null) {
                    var1_3 = new LoadingException("No cached result cursor holder", null);
                    throw var1_3;
                }
                var1_3 = var3_6.zza(ObjectWrapper.wrap(var0), (String)var1_3, var2_5, ObjectWrapper.wrap(var5_8.zza));
            } else if (var4_7 == 2) {
                Log.w((String)"DynamiteModule", (String)"IDynamite loader version = 2");
                var1_3 = var3_6.zzb(ObjectWrapper.wrap(var0), (String)var1_3, var2_5);
            } else {
                Log.w((String)"DynamiteModule", (String)"Dynamite loader version < 2, falling back to createModuleContext");
                var1_3 = var3_6.zza(ObjectWrapper.wrap(var0), (String)var1_3, var2_5);
            }
            if (ObjectWrapper.unwrap((IObjectWrapper)var1_3) != null) {
                return new DynamiteModule((Context)ObjectWrapper.unwrap((IObjectWrapper)var1_3));
            }
            var1_3 = new LoadingException("Failed to load remote module.", null);
            throw var1_3;
lbl43: // 1 sources:
            var1_3 = new LoadingException("Failed to determine which loading route to use.", null);
            throw var1_3;
        }
        catch (Throwable var1_4) {
            CrashUtils.addDynamiteErrorToDropBox(var0, var1_4);
            throw new LoadingException("Failed to load remote module.", var1_4, null);
        }
        catch (LoadingException var0_1) {
            throw var0_1;
        }
        catch (RemoteException var0_2) {
            throw new LoadingException("Failed to load remote module.", var0_2, null);
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private static zzk zza(Context object) {
        block5 : {
            // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
            if (zzb != null) {
                object = zzb;
                // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                return object;
            }
            try {
                IInterface iInterface;
                object = (IBinder)object.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                object = object == null ? null : ((iInterface = object.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader")) instanceof zzk ? (zzk)iInterface : new zzj((IBinder)object));
                if (object == null) break block5;
                zzb = object;
            }
            catch (Exception exception) {
                String string2 = String.valueOf(exception.getMessage());
                string2 = string2.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(string2) : new String("Failed to load IDynamiteLoader from GmsCore: ");
                Log.e((String)"DynamiteModule", (String)string2);
            }
            // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
            return object;
        }
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        return null;
    }

    private static Boolean zza() {
        synchronized (DynamiteModule.class) {
            boolean bl = zze >= 2;
            return bl;
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void zza(ClassLoader object) throws LoadingException {
        void var0_6;
        try {
            IInterface iInterface;
            object = (IBinder)((ClassLoader)object).loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            object = object == null ? null : ((iInterface = object.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2")) instanceof zzm ? (zzm)iInterface : new zzl((IBinder)object));
            zzc = object;
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)var0_6, null);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)var0_6, null);
        }
        catch (InstantiationException instantiationException) {
            throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)var0_6, null);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)var0_6, null);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)var0_6, null);
    }

    private static boolean zza(Cursor cursor) {
        zza zza2 = zzf.get();
        if (zza2 == null) return false;
        if (zza2.zza != null) return false;
        zza2.zza = cursor;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static int zzb(Context var0, String var1_2, boolean var2_7) {
        block15 : {
            block16 : {
                block13 : {
                    block14 : {
                        var3_8 = DynamiteModule.zza(var0 /* !! */ );
                        if (var3_8 == null) {
                            return 0;
                        }
                        var4_9 = null;
                        var5_10 = null;
                        var6_11 = null;
                        var7_12 /* !! */  = var4_9;
                        var8_13 = var3_8.zzb();
                        if (var8_13 < 3) break block13;
                        var7_12 /* !! */  = var4_9;
                        if ((var0 /* !! */  = (Cursor)ObjectWrapper.unwrap(var3_8.zzc(ObjectWrapper.wrap(var0 /* !! */ ), var1_2, var2_7))) == null) break block14;
                        if (!var0 /* !! */ .moveToFirst()) break block14;
                        var8_13 = var0 /* !! */ .getInt(0);
                        if (var8_13 > 0 && (var2_7 = DynamiteModule.zza((Cursor)var0 /* !! */ ))) {
                            var0 /* !! */  = var6_11;
                        }
                        if (var0 /* !! */  == null) return var8_13;
                        var0 /* !! */ .close();
                        return var8_13;
                    }
                    try {
                        Log.w((String)"DynamiteModule", (String)"Failed to retrieve remote module version.");
                        if (var0 /* !! */  == null) return 0;
                    }
                    catch (Throwable var1_3) {
                        var7_12 /* !! */  = var0 /* !! */ ;
                        var0 /* !! */  = var1_3;
                        break block15;
                    }
                    catch (RemoteException var1_4) {
                        break block16;
                    }
                    var0 /* !! */ .close();
                    return 0;
                }
                if (var8_13 != 2) ** GOTO lbl44
                var7_12 /* !! */  = var4_9;
                try {
                    Log.w((String)"DynamiteModule", (String)"IDynamite loader version = 2, no high precision latency measurement.");
                    var7_12 /* !! */  = var4_9;
                    return var3_8.zzb(ObjectWrapper.wrap(var0 /* !! */ ), var1_2, var2_7);
lbl44: // 1 sources:
                    var7_12 /* !! */  = var4_9;
                    Log.w((String)"DynamiteModule", (String)"IDynamite loader version < 2, falling back to getModuleVersion2");
                    var7_12 /* !! */  = var4_9;
                    return var3_8.zza(ObjectWrapper.wrap(var0 /* !! */ ), var1_2, var2_7);
                }
                catch (Throwable var0_1) {
                    break block15;
                }
                catch (RemoteException var1_5) {
                    var0 /* !! */  = var5_10;
                }
            }
            var7_12 /* !! */  = var0 /* !! */ ;
            var1_6 = String.valueOf(var1_6.getMessage());
            var7_12 /* !! */  = var0 /* !! */ ;
            if (var1_6.length() != 0) {
                var7_12 /* !! */  = var0 /* !! */ ;
                var1_6 = "Failed to retrieve remote module version: ".concat(var1_6);
            } else {
                var7_12 /* !! */  = var0 /* !! */ ;
                var1_6 = new String("Failed to retrieve remote module version: ");
            }
            var7_12 /* !! */  = var0 /* !! */ ;
            Log.w((String)"DynamiteModule", (String)var1_6);
            if (var0 /* !! */  == null) return 0;
            var0 /* !! */ .close();
            return 0;
        }
        if (var7_12 /* !! */  == null) throw var0 /* !! */ ;
        var7_12 /* !! */ .close();
        throw var0 /* !! */ ;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private static DynamiteModule zzb(Context object, String string2, int n) throws LoadingException, RemoteException {
        Object object2 = new StringBuilder(String.valueOf(string2).length() + 51);
        ((StringBuilder)object2).append("Selected remote version of ");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(", version >= ");
        ((StringBuilder)object2).append(n);
        Log.i((String)"DynamiteModule", (String)((StringBuilder)object2).toString());
        synchronized (DynamiteModule.class) {
            object2 = zzc;
        }
        if (object2 == null) throw new LoadingException("DynamiteLoaderV2 was not cached.", null);
        zza zza2 = zzf.get();
        if (zza2 == null) throw new LoadingException("No result cursor", null);
        if (zza2.zza == null) throw new LoadingException("No result cursor", null);
        object = object.getApplicationContext();
        zza2 = zza2.zza;
        ObjectWrapper.wrap(null);
        if (DynamiteModule.zza().booleanValue()) {
            Log.v((String)"DynamiteModule", (String)"Dynamite loader version >= 2, using loadModule2NoCrashUtils");
            object = object2.zzb(ObjectWrapper.wrap(object), string2, n, ObjectWrapper.wrap(zza2));
        } else {
            Log.w((String)"DynamiteModule", (String)"Dynamite loader version < 2, falling back to loadModule2");
            object = object2.zza(ObjectWrapper.wrap(object), string2, n, ObjectWrapper.wrap(zza2));
        }
        object = (Context)ObjectWrapper.unwrap((IObjectWrapper)object);
        if (object == null) throw new LoadingException("Failed to get module context", null);
        return new DynamiteModule((Context)object);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private static int zzc(Context object, String object2, boolean bl) throws LoadingException {
        void var1_8;
        block19 : {
            LoadingException loadingException;
            block17 : {
                int n;
                block18 : {
                    loadingException = null;
                    Object var4_11 = null;
                    ContentResolver contentResolver = object.getContentResolver();
                    object = bl ? "api_force_staging" : "api";
                    n = ((String)object).length();
                    int n2 = String.valueOf(object2).length();
                    StringBuilder stringBuilder = new StringBuilder(n + 42 + n2);
                    stringBuilder.append("content://com.google.android.gms.chimera/");
                    stringBuilder.append((String)object);
                    stringBuilder.append("/");
                    stringBuilder.append((String)object2);
                    object = contentResolver.query(Uri.parse((String)stringBuilder.toString()), null, null, null, null);
                    if (object == null) break block17;
                    if (!object.moveToFirst()) break block17;
                    n = object.getInt(0);
                    if (n <= 0) break block18;
                    // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
                    zzd = object.getString(2);
                    n2 = object.getColumnIndex("loaderVersion");
                    if (n2 >= 0) {
                        zze = object.getInt(n2);
                    }
                    // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                    bl = DynamiteModule.zza((Cursor)object);
                    if (!bl) break block18;
                    object = var4_11;
                }
                if (object == null) return n;
                object.close();
                return n;
            }
            try {
                Log.w((String)"DynamiteModule", (String)"Failed to retrieve remote module version.");
                object2 = new LoadingException("Failed to connect to dynamite module ContentResolver.", null);
                throw object2;
            }
            catch (Throwable throwable) {}
            catch (Exception exception) {}
            finally {
                break block19;
            }
            catch (Throwable throwable) {
                object = loadingException;
                break block19;
            }
            catch (Exception exception) {
                object = null;
            }
            try {
                void var1_6;
                if (var1_6 instanceof LoadingException) {
                    throw var1_6;
                }
                loadingException = new LoadingException("V2 version check failed", (Throwable)var1_6, null);
                throw loadingException;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (object == null) throw var1_8;
        object.close();
        throw var1_8;
    }

    public final Context getModuleContext() {
        return this.zzi;
    }

    /*
     * WARNING - void declaration
     */
    public final IBinder instantiate(String string2) throws LoadingException {
        void var2_6;
        try {
            return (IBinder)this.zzi.getClassLoader().loadClass(string2).newInstance();
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InstantiationException instantiationException) {
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        string2 = String.valueOf(string2);
        if (string2.length() != 0) {
            string2 = "Failed to instantiate module class: ".concat(string2);
            throw new LoadingException(string2, (Throwable)var2_6, null);
        }
        string2 = new String("Failed to instantiate module class: ");
        throw new LoadingException(string2, (Throwable)var2_6, null);
    }

    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    public static class LoadingException
    extends Exception {
        private LoadingException(String string2) {
            super(string2);
        }

        /* synthetic */ LoadingException(String string2, com.google.android.gms.dynamite.zzb zzb2) {
            this(string2);
        }

        private LoadingException(String string2, Throwable throwable) {
            super(string2, throwable);
        }

        /* synthetic */ LoadingException(String string2, Throwable throwable, com.google.android.gms.dynamite.zzb zzb2) {
            this(string2, throwable);
        }
    }

    public static interface VersionPolicy {
        public zza zza(Context var1, String var2, zzb var3) throws LoadingException;

        public static final class zza {
            public int zza = 0;
            public int zzb = 0;
            public int zzc = 0;
        }

        public static interface zzb {
            public int zza(Context var1, String var2);

            public int zza(Context var1, String var2, boolean var3) throws LoadingException;
        }

    }

    private static final class zza {
        public Cursor zza;

        private zza() {
        }

        /* synthetic */ zza(com.google.android.gms.dynamite.zzb zzb2) {
            this();
        }
    }

    private static final class zzb
    implements VersionPolicy.zzb {
        private final int zza;
        private final int zzb;

        public zzb(int n, int n2) {
            this.zza = n;
            this.zzb = 0;
        }

        @Override
        public final int zza(Context context, String string2) {
            return this.zza;
        }

        @Override
        public final int zza(Context context, String string2, boolean bl) {
            return 0;
        }
    }

}

