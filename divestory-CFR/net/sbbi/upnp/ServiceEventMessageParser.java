/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ServiceEventMessageParser
extends DefaultHandler {
    private Map changedStateVars = new HashMap();
    private String currentPropName = null;
    private boolean readPropertyName = false;

    protected ServiceEventMessageParser() {
    }

    @Override
    public void characters(char[] object, int n, int n2) {
        String string2 = this.currentPropName;
        if (string2 == null) return;
        CharSequence charSequence = (String)this.changedStateVars.get(string2);
        String string3 = new String((char[])object, n, n2);
        if (charSequence == null) {
            this.changedStateVars.put(this.currentPropName, string3);
            return;
        }
        object = this.changedStateVars;
        string2 = this.currentPropName;
        charSequence = new StringBuilder(String.valueOf(charSequence));
        ((StringBuilder)charSequence).append(string3);
        object.put(string2, ((StringBuilder)charSequence).toString());
    }

    @Override
    public void endElement(String string2, String string3, String string4) {
        string2 = this.currentPropName;
        if (string2 == null) return;
        if (!string3.equals(string2)) return;
        this.readPropertyName = false;
        this.currentPropName = null;
    }

    public Map getChangedStateVars() {
        return this.changedStateVars;
    }

    @Override
    public void startElement(String string2, String string3, String string4, Attributes attributes) {
        if (string3.equals("property")) {
            this.readPropertyName = true;
            return;
        }
        if (!this.readPropertyName) return;
        this.currentPropName = string3;
    }
}

