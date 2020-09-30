/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import net.sbbi.upnp.messages.StateVariableResponse;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;

public class StateVariableMessage {
    private static final Logger log = Logger.getLogger(StateVariableMessage.class.getName());
    private UPNPService service;
    private ServiceStateVariable serviceStateVar;

    protected StateVariableMessage(UPNPService uPNPService, ServiceStateVariable serviceStateVariable) {
        this.service = uPNPService;
        this.serviceStateVar = serviceStateVariable;
    }

    private String getResponseBody(InputStream inputStream2) throws IOException {
        int n;
        byte[] arrby = new byte[256];
        StringBuffer stringBuffer = new StringBuffer(256);
        do {
            if ((n = inputStream2.read(arrby)) == -1) break;
            stringBuffer.append(new String(arrby, 0, n));
        } while (true);
        n = stringBuffer.length();
        while (stringBuffer.charAt(n - 1) == '\u0000') {
            stringBuffer.setLength(--n);
        }
        return stringBuffer.toString().trim();
    }

    /*
     * Exception decompiling
     */
    public StateVariableResponse service() throws IOException, UPNPResponseException {
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
}

