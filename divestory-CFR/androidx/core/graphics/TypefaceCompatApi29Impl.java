/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.Typeface$CustomFallbackBuilder
 *  android.graphics.fonts.Font
 *  android.graphics.fonts.Font$Builder
 *  android.graphics.fonts.FontFamily
 *  android.graphics.fonts.FontFamily$Builder
 *  android.graphics.fonts.FontStyle
 *  android.os.CancellationSignal
 */
package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompatBaseImpl;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl
extends TypefaceCompatBaseImpl {
    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context var1_1, FontResourcesParserCompat.FontFamilyFilesResourceEntry var2_2, Resources var3_4, int var4_5) {
        var5_9 = var2_5.getEntries();
        var6_10 = var5_9.length;
        var7_11 = 0;
        var1_2 = null;
        var8_12 = 0;
        block2 : do {
            var9_13 = 1;
            if (var8_12 >= var6_10) ** GOTO lbl22
            var2_5 = var5_9[var8_12];
            try {
                block9 : {
                    var10_14 = new Font.Builder((Resources)var3_7, var2_5.getResourceId());
                    var10_14 = var10_14.setWeight(var2_5.getWeight());
                    if (!var2_5.isItalic()) {
                        var9_13 = 0;
                    }
                    var10_14 = var10_14.setSlant(var9_13).setTtcIndex(var2_5.getTtcIndex()).setFontVariationSettings(var2_5.getVariationSettings()).build();
                    if (var1_3 == null) {
                        var1_4 = var2_5 = new FontFamily.Builder((Font)var10_14);
                    } else {
                        var1_3.addFont((Font)var10_14);
                    }
                    break block9;
lbl22: // 1 sources:
                    if (var1_3 == null) {
                        return null;
                    }
                    var8_12 = (var4_8 & true) != 0 ? 700 : 400;
                    var9_13 = var7_11;
                    if ((var4_8 & 2) != 0) {
                        var9_13 = 1;
                    }
                    var2_5 = new FontStyle(var8_12, var9_13);
                    return new Typeface.CustomFallbackBuilder(var1_3.build()).setStyle((FontStyle)var2_5).build();
                }
lbl31: // 2 sources:
                do {
                    ++var8_12;
                    continue block2;
                    break;
                } while (true);
            }
            catch (IOException var2_6) {
                ** continue;
            }
        } while (true);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Typeface createFromFontInfo(Context var1_1, CancellationSignal var2_3, FontsContractCompat.FontInfo[] var3_4, int var4_5) {
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
    protected Typeface createFromInputStream(Context context, InputStream inputStream2) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int n, String string2, int n2) {
        try {
            context = new Font.Builder(resources, n);
            context = context.build();
            resources = new FontFamily.Builder((Font)context);
            resources = resources.build();
        }
        catch (IOException iOException) {
            return null;
        }
        return new Typeface.CustomFallbackBuilder((FontFamily)resources).setStyle(context.getStyle()).build();
    }

    @Override
    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }
}

