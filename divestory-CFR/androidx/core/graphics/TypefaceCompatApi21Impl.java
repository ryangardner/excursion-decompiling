/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.CancellationSignal
 *  android.os.ParcelFileDescriptor
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  android.util.Log
 */
package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompatBaseImpl;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TypefaceCompatApi21Impl
extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi21Impl";
    private static Method sAddFontWeightStyle;
    private static Method sCreateFromFamiliesWithDefault;
    private static Class<?> sFontFamily;
    private static Constructor<?> sFontFamilyCtor;
    private static boolean sHasInitBeenCalled = false;

    TypefaceCompatApi21Impl() {
    }

    /*
     * WARNING - void declaration
     */
    private static boolean addFontWeightStyle(Object object, String string2, int n, boolean bl) {
        void var0_3;
        TypefaceCompatApi21Impl.init();
        try {
            return (Boolean)sAddFontWeightStyle.invoke(object, string2, n, bl);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_3);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        throw new RuntimeException((Throwable)var0_3);
    }

    /*
     * WARNING - void declaration
     */
    private static Typeface createFromFamiliesWithDefault(Object object) {
        void var0_3;
        TypefaceCompatApi21Impl.init();
        try {
            Object object2 = Array.newInstance(sFontFamily, 1);
            Array.set(object2, 0, object);
            return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_3);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        throw new RuntimeException((Throwable)var0_3);
    }

    private File getFile(ParcelFileDescriptor object) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/proc/self/fd/");
            stringBuilder.append(object.getFd());
            object = Os.readlink((String)stringBuilder.toString());
            if (!OsConstants.S_ISREG((int)Os.stat((String)object).st_mode)) return null;
            return new File((String)object);
        }
        catch (ErrnoException errnoException) {
            return null;
        }
    }

    private static void init() {
        Method method;
        Class<?> class_;
        GenericDeclaration genericDeclaration;
        Constructor<?> constructor;
        block4 : {
            if (sHasInitBeenCalled) {
                return;
            }
            sHasInitBeenCalled = true;
            constructor = null;
            try {
                class_ = Class.forName(FONT_FAMILY_CLASS);
                Constructor<?> constructor2 = class_.getConstructor(new Class[0]);
                genericDeclaration = class_.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, String.class, Integer.TYPE, Boolean.TYPE);
                method = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(class_, 1).getClass());
                constructor = constructor2;
                break block4;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            Log.e((String)TAG, (String)class_.getClass().getName(), class_);
            method = null;
            genericDeclaration = class_ = method;
        }
        sFontFamilyCtor = constructor;
        sFontFamily = class_;
        sAddFontWeightStyle = genericDeclaration;
        sCreateFromFamiliesWithDefault = method;
    }

    /*
     * WARNING - void declaration
     */
    private static Object newFamily() {
        void var0_4;
        TypefaceCompatApi21Impl.init();
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_4);
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException((Throwable)var0_4);
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        throw new RuntimeException((Throwable)var0_4);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry object, Resources resources, int n) {
        Object object2 = TypefaceCompatApi21Impl.newFamily();
        FontResourcesParserCompat.FontFileResourceEntry[] arrfontFileResourceEntry = ((FontResourcesParserCompat.FontFamilyFilesResourceEntry)object).getEntries();
        int n2 = arrfontFileResourceEntry.length;
        n = 0;
        while (n < n2) {
            FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrfontFileResourceEntry[n];
            object = TypefaceCompatUtil.getTempFile(context);
            if (object == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, resources, fontFileResourceEntry.getResourceId());
            if (!bl) {
                return null;
            }
            bl = TypefaceCompatApi21Impl.addFontWeightStyle(object2, ((File)object).getPath(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic());
            if (!bl) {
                return null;
            }
            ++n;
        }
        return TypefaceCompatApi21Impl.createFromFamiliesWithDefault(object2);
        catch (RuntimeException runtimeException) {
            return null;
        }
        finally {
            ((File)object).delete();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Typeface createFromFontInfo(Context var1_1, CancellationSignal var2_5, FontsContractCompat.FontInfo[] var3_7, int var4_9) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
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
}

