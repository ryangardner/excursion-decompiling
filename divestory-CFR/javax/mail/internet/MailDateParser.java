/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.text.ParseException;

class MailDateParser {
    int index = 0;
    char[] orig = null;

    public MailDateParser(char[] arrc) {
        this.orig = arrc;
    }

    int getIndex() {
        return this.index;
    }

    /*
     * Exception decompiling
     */
    public int parseAlphaTimeZone() throws ParseException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CASE]], but top level block is 1[TRYBLOCK]
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
    public int parseMonth() throws ParseException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:328)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:466)
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
     * Unable to fully structure code
     */
    public int parseNumber() throws ParseException {
        var1_1 = this.orig.length;
        var2_2 = false;
        var3_3 = 0;
        do {
            if ((var4_4 = ++this.index) >= var1_1) {
                if (var2_2 == false) throw new ParseException("No Number found", this.index);
                return var3_3;
            }
            switch (this.orig[var4_4]) {
                default: {
                    if (var2_2 == false) throw new ParseException("No Number found", this.index);
                    return var3_3;
                }
                case '9': {
                    var3_3 = var3_3 * 10 + 9;
                    ** break;
                }
                case '8': {
                    var3_3 = var3_3 * 10 + 8;
                    ** break;
                }
                case '7': {
                    var3_3 = var3_3 * 10 + 7;
                    ** break;
                }
                case '6': {
                    var3_3 = var3_3 * 10 + 6;
                    ** break;
                }
                case '5': {
                    var3_3 = var3_3 * 10 + 5;
                    ** break;
                }
                case '4': {
                    var3_3 = var3_3 * 10 + 4;
                    ** break;
                }
                case '3': {
                    var3_3 = var3_3 * 10 + 3;
                    ** break;
                }
                case '2': {
                    var3_3 = var3_3 * 10 + 2;
                    ** break;
                }
                case '1': {
                    var3_3 = var3_3 * 10 + 1;
                    ** break;
                }
                case '0': 
            }
            var3_3 *= 10;
lbl41: // 10 sources:
            var2_2 = true;
        } while (true);
    }

    public int parseNumericTimeZone() throws ParseException {
        char[] arrc = this.orig;
        int n = this.index;
        this.index = n + 1;
        if ((n = arrc[n]) == 43) {
            n = 1;
        } else {
            if (n != 45) throw new ParseException("Bad Numeric TimeZone", this.index);
            n = 0;
        }
        int n2 = this.parseNumber();
        n2 = n2 / 100 * 60 + n2 % 100;
        if (n == 0) return n2;
        return -n2;
    }

    public int parseTimeZone() throws ParseException {
        int n = this.index;
        char[] arrc = this.orig;
        if (n >= arrc.length) throw new ParseException("No more characters", this.index);
        if ((n = arrc[n]) == 43) return this.parseNumericTimeZone();
        if (n != 45) return this.parseAlphaTimeZone();
        return this.parseNumericTimeZone();
    }

    public int peekChar() throws ParseException {
        int n = this.index;
        char[] arrc = this.orig;
        if (n >= arrc.length) throw new ParseException("No more characters", this.index);
        return arrc[n];
    }

    public void skipChar(char c) throws ParseException {
        int n = this.index;
        char[] arrc = this.orig;
        if (n >= arrc.length) throw new ParseException("No more characters", this.index);
        if (arrc[n] != c) throw new ParseException("Wrong char", this.index);
        this.index = n + 1;
    }

    public boolean skipIfChar(char c) throws ParseException {
        int n = this.index;
        char[] arrc = this.orig;
        if (n >= arrc.length) throw new ParseException("No more characters", this.index);
        if (arrc[n] != c) return false;
        this.index = n + 1;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void skipUntilNumber() throws ParseException {
        try {
            do {
                switch (this.orig[this.index]) {
                    default: {
                        var1_1 = this.index;
                        ** break;
                    }
                    case '0': 
                    case '1': 
                    case '2': 
                    case '3': 
                    case '4': 
                    case '5': 
                    case '6': 
                    case '7': 
                    case '8': 
                    case '9': 
                }
                return;
lbl9: // 1 sources:
                this.index = var1_1 + 1;
            } while (true);
        }
        catch (ArrayIndexOutOfBoundsException var2_2) {
            throw new ParseException("No Number Found", this.index);
        }
    }

    public void skipWhiteSpace() {
        int n = this.orig.length;
        int n2;
        while ((n2 = this.index) < n) {
            if ((n2 = this.orig[n2]) != 9 && n2 != 10 && n2 != 13 && n2 != 32) {
                return;
            }
            ++this.index;
        }
        return;
    }
}

