/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import java.util.logging.Logger;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceActionArgument;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ActionMessageResponseParser
extends DefaultHandler {
    private static final String SOAP_FAULT_EL = "Fault";
    private static final Logger log = Logger.getLogger(ActionMessageResponseParser.class.getName());
    private String bodyElementName;
    private boolean faultResponse = false;
    private UPNPResponseException msgEx;
    private boolean parseOutputParams = false;
    private StringBuffer parsedResultBuilder = new StringBuffer();
    private ServiceActionArgument parsedResultOutArg;
    private boolean readErrorCode = false;
    private boolean readErrorDescription = false;
    private boolean readFaultCode = false;
    private boolean readFaultString = false;
    private ActionResponse result;
    private ServiceAction serviceAction;

    protected ActionMessageResponseParser(ServiceAction object) {
        this.serviceAction = object;
        object = new StringBuilder(String.valueOf(((ServiceAction)object).getName()));
        ((StringBuilder)object).append("Response");
        this.bodyElementName = ((StringBuilder)object).toString();
    }

    @Override
    public void characters(char[] object, int n, int n2) {
        if (this.parseOutputParams) {
            if (this.parsedResultOutArg == null) return;
            this.parsedResultBuilder.append((char[])object, n, n2);
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
    public void endElement(String object, String string2, String string3) {
        object = this.parsedResultOutArg;
        if (object != null && ((ServiceActionArgument)object).getName().equals(string2)) {
            this.result.addResult(this.parsedResultOutArg, this.parsedResultBuilder.toString());
            this.parsedResultOutArg = null;
            this.parsedResultBuilder = new StringBuffer();
            return;
        }
        if (!string2.equals(this.bodyElementName)) return;
        this.parseOutputParams = false;
    }

    protected ActionResponse getActionResponse() {
        return this.result;
    }

    protected UPNPResponseException getUPNPResponseException() {
        return this.msgEx;
    }

    @Override
    public void startElement(String object, String string2, String string3, Attributes attributes) {
        if (this.parseOutputParams) {
            object = this.serviceAction.getActionArgument(string2);
            if (object != null && ((ServiceActionArgument)object).getDirection() == "out") {
                this.parsedResultOutArg = object;
                this.result.addResult((ServiceActionArgument)object, null);
                return;
            }
            this.parsedResultOutArg = null;
            return;
        }
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
        if (!string2.equals(this.bodyElementName)) return;
        this.parseOutputParams = true;
        this.result = new ActionResponse();
    }
}

