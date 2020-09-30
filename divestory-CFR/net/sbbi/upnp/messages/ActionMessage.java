/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.ISO8601Date;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceActionArgument;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;

public class ActionMessage {
    private static final Map XML_ENTITIES;
    private static final Logger log;
    private List inputParameters;
    private UPNPService service;
    private ServiceAction serviceAction;

    static {
        HashMap<Character, String> hashMap;
        log = Logger.getLogger(ActionMessage.class.getName());
        XML_ENTITIES = hashMap = new HashMap<Character, String>();
        hashMap.put(Character.valueOf('\"'), "quot");
        XML_ENTITIES.put(Character.valueOf('&'), "amp");
        XML_ENTITIES.put(Character.valueOf('<'), "lt");
        XML_ENTITIES.put(Character.valueOf('>'), "gt");
        XML_ENTITIES.put(Character.valueOf('\''), "apos");
    }

    protected ActionMessage(UPNPService uPNPService, ServiceAction serviceAction) {
        this.service = uPNPService;
        this.serviceAction = serviceAction;
        if (serviceAction.getInputActionArguments() == null) return;
        this.inputParameters = new ArrayList();
    }

    private String getResponseBody(InputStream inputStream2) throws IOException {
        int n;
        byte[] arrby = new byte[256];
        StringBuffer stringBuffer = new StringBuffer(256);
        do {
            if ((n = inputStream2.read(arrby)) == -1) break;
            stringBuffer.append(new String(arrby, 0, n, "UTF-8"));
        } while (true);
        n = stringBuffer.length();
        while (stringBuffer.charAt(n - 1) == '\u0000') {
            stringBuffer.setLength(--n);
        }
        return stringBuffer.toString().trim();
    }

    public void clearInputParameters() {
        this.inputParameters.clear();
    }

    public String escape(String string2) {
        StringBuffer stringBuffer = new StringBuffer(string2.length() * 2);
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            String string3 = (String)XML_ENTITIES.get(Character.valueOf(c));
            if (string3 == null) {
                if (c > '') {
                    stringBuffer.append("&#");
                    stringBuffer.append((int)c);
                    stringBuffer.append(';');
                } else {
                    stringBuffer.append(c);
                }
            } else {
                stringBuffer.append('&');
                stringBuffer.append(string3);
                stringBuffer.append(';');
            }
            ++n;
        }
        return stringBuffer.toString();
    }

    public List getInputParameterNames() {
        return this.serviceAction.getInputActionArgumentsNames();
    }

    public List getOutputParameterNames() {
        return this.serviceAction.getOutputActionArgumentsNames();
    }

    /*
     * Exception decompiling
     */
    public ActionResponse service() throws IOException, UPNPResponseException {
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

    public ActionMessage setInputParameter(String string2, byte by) throws IllegalArgumentException {
        return this.setInputParameter(string2, Byte.toString(by));
    }

    public ActionMessage setInputParameter(String string2, double d) throws IllegalArgumentException {
        return this.setInputParameter(string2, Double.toString(d));
    }

    public ActionMessage setInputParameter(String string2, float f) throws IllegalArgumentException {
        return this.setInputParameter(string2, Float.toString(f));
    }

    public ActionMessage setInputParameter(String string2, int n) throws IllegalArgumentException {
        return this.setInputParameter(string2, Integer.toString(n));
    }

    public ActionMessage setInputParameter(String string2, long l) throws IllegalArgumentException {
        return this.setInputParameter(string2, Long.toString(l));
    }

    public ActionMessage setInputParameter(String string2, Object object) throws IllegalArgumentException {
        if (object == null) {
            return this.setInputParameter(string2, "");
        }
        if (object instanceof Date) {
            return this.setInputParameter(string2, (Date)object);
        }
        if (object instanceof Boolean) {
            return this.setInputParameter(string2, (Boolean)object);
        }
        if (object instanceof Integer) {
            return this.setInputParameter(string2, (Integer)object);
        }
        if (object instanceof Byte) {
            return this.setInputParameter(string2, (Byte)object);
        }
        if (object instanceof Short) {
            return this.setInputParameter(string2, (Short)object);
        }
        if (object instanceof Float) {
            return this.setInputParameter(string2, ((Float)object).floatValue());
        }
        if (object instanceof Double) {
            return this.setInputParameter(string2, (Double)object);
        }
        if (!(object instanceof Long)) return this.setInputParameter(string2, object.toString());
        return this.setInputParameter(string2, (Long)object);
    }

    public ActionMessage setInputParameter(String string2, String charSequence) throws IllegalArgumentException {
        InputParamContainer inputParamContainer;
        if (this.serviceAction.getInputActionArguments() == null) throw new IllegalArgumentException("No input parameters required for this message");
        if (this.serviceAction.getInputActionArgument(string2) == null) {
            charSequence = new StringBuilder("Wrong input argument name for this action:");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" available parameters are : ");
            ((StringBuilder)charSequence).append(this.getInputParameterNames());
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence = this.escape((String)charSequence);
        Iterator iterator2 = this.inputParameters.iterator();
        do {
            if (iterator2.hasNext()) continue;
            inputParamContainer = new InputParamContainer();
            InputParamContainer.access$4(inputParamContainer, string2);
            InputParamContainer.access$2(inputParamContainer, (String)charSequence);
            this.inputParameters.add(inputParamContainer);
            return this;
        } while (!(inputParamContainer = (InputParamContainer)iterator2.next()).name.equals(string2));
        InputParamContainer.access$2(inputParamContainer, (String)charSequence);
        return this;
    }

    public ActionMessage setInputParameter(String charSequence, Date comparable) throws IllegalArgumentException {
        if (this.serviceAction.getInputActionArguments() == null) throw new IllegalArgumentException("No input parameters required for this message");
        Object object = this.serviceAction.getInputActionArgument((String)charSequence);
        if (object == null) {
            comparable = new StringBuilder("Wrong input argument name for this action:");
            ((StringBuilder)comparable).append((String)charSequence);
            ((StringBuilder)comparable).append(" available parameters are : ");
            ((StringBuilder)comparable).append(this.getInputParameterNames());
            throw new IllegalArgumentException(((StringBuilder)comparable).toString());
        }
        if (((ServiceStateVariable)(object = ((ServiceActionArgument)object).getRelatedStateVariable())).getDataType().equals("time")) {
            return this.setInputParameter((String)charSequence, ISO8601Date.getIsoTime(comparable));
        }
        if (((ServiceStateVariable)object).getDataType().equals("time.tz")) {
            return this.setInputParameter((String)charSequence, ISO8601Date.getIsoTimeZone(comparable));
        }
        if (((ServiceStateVariable)object).getDataType().equals("date")) {
            return this.setInputParameter((String)charSequence, ISO8601Date.getIsoDate(comparable));
        }
        if (((ServiceStateVariable)object).getDataType().equals("dateTime")) {
            return this.setInputParameter((String)charSequence, ISO8601Date.getIsoDateTime(comparable));
        }
        if (((ServiceStateVariable)object).getDataType().equals("dateTime.tz")) {
            return this.setInputParameter((String)charSequence, ISO8601Date.getIsoDateTimeZone(comparable));
        }
        charSequence = new StringBuilder("Related input state variable ");
        ((StringBuilder)charSequence).append(((ServiceStateVariable)object).getName());
        ((StringBuilder)charSequence).append(" is not of an date type");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public ActionMessage setInputParameter(String string2, short s) throws IllegalArgumentException {
        return this.setInputParameter(string2, Short.toString(s));
    }

    public ActionMessage setInputParameter(String string2, boolean bl) throws IllegalArgumentException {
        String string3;
        if (bl) {
            string3 = "1";
            return this.setInputParameter(string2, string3);
        }
        string3 = "0";
        return this.setInputParameter(string2, string3);
    }

    private class InputParamContainer {
        private String name;
        private String value;

        private InputParamContainer() {
        }

        static /* synthetic */ String access$1(InputParamContainer inputParamContainer) {
            return inputParamContainer.value;
        }

        static /* synthetic */ void access$2(InputParamContainer inputParamContainer, String string2) {
            inputParamContainer.value = string2;
        }

        static /* synthetic */ void access$4(InputParamContainer inputParamContainer, String string2) {
            inputParamContainer.name = string2;
        }
    }

}

