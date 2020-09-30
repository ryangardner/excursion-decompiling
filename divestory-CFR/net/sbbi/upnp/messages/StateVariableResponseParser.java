/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import java.util.logging.Logger;
import net.sbbi.upnp.messages.StateVariableResponse;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.ServiceStateVariable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StateVariableResponseParser
extends DefaultHandler {
    private static final String SOAP_FAULT_EL = "Fault";
    private static final Logger log = Logger.getLogger(StateVariableResponseParser.class.getName());
    private boolean faultResponse = false;
    private UPNPResponseException msgEx;
    private boolean parseStateVar = false;
    private boolean readErrorCode = false;
    private boolean readErrorDescription = false;
    private boolean readFaultCode = false;
    private boolean readFaultString = false;
    private StateVariableResponse result;
    private ServiceStateVariable stateVar;

    protected StateVariableResponseParser(ServiceStateVariable serviceStateVariable) {
        this.stateVar = serviceStateVariable;
    }

    @Override
    public void characters(char[] object, int n, int n2) {
        if (this.parseStateVar) {
            CharSequence charSequence = this.result.stateVariableValue;
            object = new String((char[])object, n, n2);
            if (charSequence == null) {
                this.result.stateVariableValue = object;
                return;
            }
            StateVariableResponse stateVariableResponse = this.result;
            charSequence = new StringBuilder(String.valueOf(charSequence));
            ((StringBuilder)charSequence).append((String)object);
            stateVariableResponse.stateVariableValue = ((StringBuilder)charSequence).toString();
            return;
        }
        if (this.readFaultCode) {
            this.msgEx.faultCode = new String((char[])object, n, n2);
            this.readFaultCode = false;
            return;
        }
        if (this.readFaultString) {
            this.msgEx.faultString = new String((char[])object, n, n2);
            this.readFaultString = false;
            return;
        }
        if (!this.readErrorCode) {
            if (!this.readErrorDescription) return;
            this.msgEx.detailErrorDescription = new String((char[])object, n, n2);
            this.readErrorDescription = false;
            return;
        }
        object = new String((char[])object, n, n2);
        try {
            this.msgEx.detailErrorCode = Integer.parseInt((String)object);
        }
        catch (Throwable throwable) {
            Logger logger = log;
            StringBuilder stringBuilder = new StringBuilder("Error during returned error code ");
            stringBuilder.append((String)object);
            stringBuilder.append(" parsing");
            logger.fine(stringBuilder.toString());
        }
        this.readErrorCode = false;
    }

    @Override
    public void endElement(String string2, String string3, String string4) throws SAXException {
        if (!string3.equals("return")) {
            if (!string3.equals("varName")) return;
        }
        this.parseStateVar = false;
    }

    protected StateVariableResponse getStateVariableResponse() {
        return this.result;
    }

    protected UPNPResponseException getUPNPResponseException() {
        return this.msgEx;
    }

    @Override
    public void startElement(String object, String string2, String string3, Attributes attributes) {
        if (this.faultResponse) {
            if (string2.equals("faultcode")) {
                this.readFaultCode = true;
                return;
            }
            if (string2.equals("faultstring")) {
                this.readFaultString = true;
                return;
            }
            if (string2.equals("errorCode")) {
                this.readErrorCode = true;
                return;
            }
            if (!string2.equals("errorDescription")) return;
            this.readErrorDescription = true;
            return;
        }
        if (string2.equals(SOAP_FAULT_EL)) {
            this.msgEx = new UPNPResponseException();
            this.faultResponse = true;
            return;
        }
        if (!string2.equals("return")) {
            if (!string2.equals("varName")) return;
        }
        this.parseStateVar = true;
        this.result = object = new StateVariableResponse();
        ((StateVariableResponse)object).stateVar = this.stateVar;
    }
}

