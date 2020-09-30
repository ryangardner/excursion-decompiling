/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.protocol.HttpContext;

public class ResponseProcessCookies
implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    /*
     * Exception decompiling
     */
    private void processCookies(HeaderIterator var1_1, CookieSpec var2_2, CookieOrigin var3_3, CookieStore var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[UNCONDITIONALDOLOOP]
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

    @Override
    public void process(HttpResponse httpResponse, HttpContext object) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        CookieSpec cookieSpec = (CookieSpec)object.getAttribute("http.cookie-spec");
        if (cookieSpec == null) {
            this.log.debug((Object)"Cookie spec not specified in HTTP context");
            return;
        }
        CookieStore cookieStore = (CookieStore)object.getAttribute("http.cookie-store");
        if (cookieStore == null) {
            this.log.debug((Object)"Cookie store not specified in HTTP context");
            return;
        }
        if ((object = (CookieOrigin)object.getAttribute("http.cookie-origin")) == null) {
            this.log.debug((Object)"Cookie origin not specified in HTTP context");
            return;
        }
        this.processCookies(httpResponse.headerIterator("Set-Cookie"), cookieSpec, (CookieOrigin)object, cookieStore);
        if (cookieSpec.getVersion() <= 0) return;
        this.processCookies(httpResponse.headerIterator("Set-Cookie2"), cookieSpec, (CookieOrigin)object, cookieStore);
    }
}

