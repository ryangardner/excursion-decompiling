/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.fonts.FontVariationAxis
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompatApi21Impl;
import androidx.core.provider.FontsContractCompat;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl
extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class<?> mFontFamily;
    protected final Constructor<?> mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Method method;
        GenericDeclaration genericDeclaration;
        GenericDeclaration genericDeclaration2;
        Method method2;
        GenericDeclaration genericDeclaration3;
        GenericDeclaration genericDeclaration4;
        Class<?> class_;
        block3 : {
            Object var1_1 = null;
            try {
                class_ = this.obtainFontFamily();
                genericDeclaration2 = this.obtainFontFamilyCtor(class_);
                genericDeclaration = this.obtainAddFontFromAssetManagerMethod(class_);
                genericDeclaration4 = this.obtainAddFontFromBufferMethod(class_);
                method2 = this.obtainFreezeMethod(class_);
                method = this.obtainAbortCreationMethod(class_);
                genericDeclaration3 = this.obtainCreateFromFamiliesWithDefaultMethod(class_);
                break block3;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            genericDeclaration3 = new StringBuilder();
            ((StringBuilder)((Object)genericDeclaration3)).append("Unable to collect necessary methods for class ");
            ((StringBuilder)((Object)genericDeclaration3)).append(genericDeclaration2.getClass().getName());
            Log.e((String)TAG, (String)((StringBuilder)((Object)genericDeclaration3)).toString(), genericDeclaration2);
            class_ = null;
            genericDeclaration3 = genericDeclaration2 = class_;
            method = genericDeclaration = (genericDeclaration4 = genericDeclaration3);
            method2 = genericDeclaration;
            genericDeclaration = genericDeclaration3;
            genericDeclaration3 = class_;
            class_ = var1_1;
        }
        this.mFontFamily = class_;
        this.mFontFamilyCtor = genericDeclaration2;
        this.mAddFontFromAssetManager = genericDeclaration;
        this.mAddFontFromBuffer = genericDeclaration4;
        this.mFreeze = method2;
        this.mAbortCreation = method;
        this.mCreateFromFamiliesWithDefault = genericDeclaration3;
    }

    private void abortCreation(Object object) {
        try {
            this.mAbortCreation.invoke(object, new Object[0]);
            return;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return;
        }
    }

    private boolean addFontFromAssetManager(Context context, Object object, String string2, int n, int n2, int n3, FontVariationAxis[] arrfontVariationAxis) {
        try {
            return (Boolean)this.mAddFontFromAssetManager.invoke(object, new Object[]{context.getAssets(), string2, 0, false, n, n2, n3, arrfontVariationAxis});
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    private boolean addFontFromBuffer(Object object, ByteBuffer byteBuffer, int n, int n2, int n3) {
        try {
            return (Boolean)this.mAddFontFromBuffer.invoke(object, byteBuffer, n, null, n2, n3);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    private boolean freeze(Object object) {
        try {
            return (Boolean)this.mFreeze.invoke(object, new Object[0]);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w((String)TAG, (String)"Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        if (this.mAddFontFromAssetManager == null) return false;
        return true;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    protected Typeface createFromFamiliesWithDefault(Object object) {
        try {
            Object object2 = Array.newInstance(this.mFontFamily, 1);
            Array.set(object2, 0, object);
            return (Typeface)this.mCreateFromFamiliesWithDefault.invoke(null, object2, -1, -1);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry object, Resources object2, int n) {
        if (!this.isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry)object, (Resources)object2, n);
        }
        object2 = this.newFamily();
        if (object2 == null) {
            return null;
        }
        FontResourcesParserCompat.FontFileResourceEntry[] arrfontFileResourceEntry = ((FontResourcesParserCompat.FontFamilyFilesResourceEntry)object).getEntries();
        int n2 = arrfontFileResourceEntry.length;
        n = 0;
        do {
            if (n >= n2) {
                if (this.freeze(object2)) return this.createFromFamiliesWithDefault(object2);
                return null;
            }
            object = arrfontFileResourceEntry[n];
            if (!this.addFontFromAssetManager(context, object2, ((FontResourcesParserCompat.FontFileResourceEntry)object).getFileName(), ((FontResourcesParserCompat.FontFileResourceEntry)object).getTtcIndex(), ((FontResourcesParserCompat.FontFileResourceEntry)object).getWeight(), (int)((FontResourcesParserCompat.FontFileResourceEntry)object).isItalic(), FontVariationAxis.fromFontVariationSettings((String)((FontResourcesParserCompat.FontFileResourceEntry)object).getVariationSettings()))) {
                this.abortCreation(object2);
                return null;
            }
            ++n;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Typeface createFromFontInfo(Context var1_1, CancellationSignal var2_4, FontsContractCompat.FontInfo[] var3_6, int var4_7) {
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

    @Override
    public Typeface createFromResourcesFontFile(Context context, Resources object, int n, String string2, int n2) {
        if (!this.isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, (Resources)object, n, string2, n2);
        }
        object = this.newFamily();
        if (object == null) {
            return null;
        }
        if (!this.addFontFromAssetManager(context, object, string2, 0, -1, -1, null)) {
            this.abortCreation(object);
            return null;
        }
        if (this.freeze(object)) return this.createFromFamiliesWithDefault(object);
        return null;
    }

    protected Method obtainAbortCreationMethod(Class<?> class_) throws NoSuchMethodException {
        return class_.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    protected Method obtainAddFontFromAssetManagerMethod(Class<?> class_) throws NoSuchMethodException {
        return class_.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
    }

    protected Method obtainAddFontFromBufferMethod(Class<?> class_) throws NoSuchMethodException {
        return class_.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
    }

    protected Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> genericDeclaration) throws NoSuchMethodException {
        genericDeclaration = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(genericDeclaration, 1).getClass(), Integer.TYPE, Integer.TYPE);
        ((Method)genericDeclaration).setAccessible(true);
        return genericDeclaration;
    }

    protected Class<?> obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    protected Constructor<?> obtainFontFamilyCtor(Class<?> class_) throws NoSuchMethodException {
        return class_.getConstructor(new Class[0]);
    }

    protected Method obtainFreezeMethod(Class<?> class_) throws NoSuchMethodException {
        return class_.getMethod(FREEZE_METHOD, new Class[0]);
    }
}

