/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0004H\u0004J\u001d\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u0000H\u0004\u00a2\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0004*\u00028\u0000H$\u00a2\u0006\u0002\u0010\u0018R\u001a\u0010\u0006\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000bX\u0082\u0004\u00a2\u0006\n\n\u0002\u0010\u000e\u0012\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2={"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "spreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class PrimitiveSpreadBuilder<T> {
    private int position;
    private final int size;
    private final T[] spreads;

    public PrimitiveSpreadBuilder(int n) {
        this.size = n;
        this.spreads = new Object[n];
    }

    private static /* synthetic */ void spreads$annotations() {
    }

    public final void addSpread(T t) {
        Intrinsics.checkParameterIsNotNull(t, "spreadArgument");
        T[] arrT = this.spreads;
        int n = this.position;
        this.position = n + 1;
        arrT[n] = t;
    }

    protected final int getPosition() {
        return this.position;
    }

    protected abstract int getSize(T var1);

    protected final void setPosition(int n) {
        this.position = n;
    }

    /*
     * Exception decompiling
     */
    protected final int size() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.NullPointerException
        // org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueAssignmentAndAliasCondenser$ExpressionStatement.access$200(LValueAssignmentAndAliasCondenser.java:283)
        // org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueAssignmentAndAliasCondenser$MutationRewriterSecondPass.getLValueReplacement(LValueAssignmentAndAliasCondenser.java:579)
        // org.benf.cfr.reader.bytecode.analysis.parse.expression.LValueExpression.replaceSingleUsageLValues(LValueExpression.java:59)
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.ReturnValueStatement.replaceSingleUsageLValues(ReturnValueStatement.java:50)
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.IfExitingStatement.replaceSingleUsageLValues(IfExitingStatement.java:40)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.condense(Op03SimpleStatement.java:428)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LValueProp.condenseLValues(LValueProp.java:47)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:504)
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

    protected final T toArray(T t, T t2) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(t, "values");
        Intrinsics.checkParameterIsNotNull(t2, "result");
        int n3 = this.size - 1;
        int n4 = 0;
        if (n3 >= 0) {
            int n5 = 0;
            int n6 = 0;
            n4 = 0;
            do {
                T t3 = this.spreads[n5];
                n2 = n6;
                n = n4;
                if (t3 != null) {
                    n = n4;
                    if (n6 < n5) {
                        n = n5 - n6;
                        System.arraycopy(t, n6, t2, n4, n);
                        n = n4 + n;
                    }
                    n4 = this.getSize(t3);
                    System.arraycopy(t3, 0, t2, n, n4);
                    n += n4;
                    n2 = n5 + 1;
                }
                if (n5 == n3) break;
                ++n5;
                n6 = n2;
                n4 = n;
            } while (true);
            n4 = n2;
        } else {
            n = 0;
        }
        n2 = this.size;
        if (n4 >= n2) return t2;
        System.arraycopy(t, n4, t2, n, n2 - n4);
        return t2;
    }
}

