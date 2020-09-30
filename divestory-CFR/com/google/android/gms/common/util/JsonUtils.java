/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.util.zzc;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonUtils {
    private static final Pattern zza = Pattern.compile("\\\\.");
    private static final Pattern zzb = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    private JsonUtils() {
    }

    /*
     * Exception decompiling
     */
    public static boolean areJsonValuesEquivalent(Object var0, Object var1_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    public static String escapeString(String string2) {
        String string3 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) return string3;
        Matcher matcher = zzb.matcher(string2);
        string3 = null;
        block5 : while (matcher.find()) {
            char c;
            CharSequence charSequence = string3;
            if (string3 == null) {
                charSequence = new StringBuffer();
            }
            if ((c = matcher.group().charAt(0)) != '\f') {
                if (c != '\r') {
                    if (c != '\"') {
                        if (c != '/') {
                            if (c != '\\') {
                                switch (c) {
                                    default: {
                                        string3 = charSequence;
                                        continue block5;
                                    }
                                    case '\n': {
                                        matcher.appendReplacement((StringBuffer)charSequence, "\\\\n");
                                        string3 = charSequence;
                                        continue block5;
                                    }
                                    case '\t': {
                                        matcher.appendReplacement((StringBuffer)charSequence, "\\\\t");
                                        string3 = charSequence;
                                        continue block5;
                                    }
                                    case '\b': 
                                }
                                matcher.appendReplacement((StringBuffer)charSequence, "\\\\b");
                                string3 = charSequence;
                                continue;
                            }
                            matcher.appendReplacement((StringBuffer)charSequence, "\\\\\\\\");
                            string3 = charSequence;
                            continue;
                        }
                        matcher.appendReplacement((StringBuffer)charSequence, "\\\\/");
                        string3 = charSequence;
                        continue;
                    }
                    matcher.appendReplacement((StringBuffer)charSequence, "\\\\\\\"");
                    string3 = charSequence;
                    continue;
                }
                matcher.appendReplacement((StringBuffer)charSequence, "\\\\r");
                string3 = charSequence;
                continue;
            }
            matcher.appendReplacement((StringBuffer)charSequence, "\\\\f");
            string3 = charSequence;
        }
        if (string3 == null) {
            return string2;
        }
        matcher.appendTail((StringBuffer)((Object)string3));
        return ((StringBuffer)((Object)string3)).toString();
    }

    public static String unescapeString(String string2) {
        CharSequence charSequence = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) return charSequence;
        String string3 = zzc.zza(string2);
        Matcher matcher = zza.matcher(string3);
        string2 = null;
        while (matcher.find()) {
            char c;
            charSequence = string2;
            if (string2 == null) {
                charSequence = new StringBuffer();
            }
            if ((c = matcher.group().charAt(1)) != '\"') {
                if (c != '/') {
                    if (c != '\\') {
                        if (c != 'b') {
                            if (c != 'f') {
                                if (c != 'n') {
                                    if (c != 'r') {
                                        if (c != 't') throw new IllegalStateException("Found an escaped character that should never be.");
                                        matcher.appendReplacement((StringBuffer)charSequence, "\t");
                                        string2 = charSequence;
                                        continue;
                                    }
                                    matcher.appendReplacement((StringBuffer)charSequence, "\r");
                                    string2 = charSequence;
                                    continue;
                                }
                                matcher.appendReplacement((StringBuffer)charSequence, "\n");
                                string2 = charSequence;
                                continue;
                            }
                            matcher.appendReplacement((StringBuffer)charSequence, "\f");
                            string2 = charSequence;
                            continue;
                        }
                        matcher.appendReplacement((StringBuffer)charSequence, "\b");
                        string2 = charSequence;
                        continue;
                    }
                    matcher.appendReplacement((StringBuffer)charSequence, "\\\\");
                    string2 = charSequence;
                    continue;
                }
                matcher.appendReplacement((StringBuffer)charSequence, "/");
                string2 = charSequence;
                continue;
            }
            matcher.appendReplacement((StringBuffer)charSequence, "\"");
            string2 = charSequence;
        }
        if (string2 == null) {
            return string3;
        }
        matcher.appendTail((StringBuffer)((Object)string2));
        return ((StringBuffer)((Object)string2)).toString();
    }
}

