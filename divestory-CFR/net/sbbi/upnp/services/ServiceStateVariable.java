/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.services;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Set;
import net.sbbi.upnp.messages.StateVariableMessage;
import net.sbbi.upnp.messages.StateVariableResponse;
import net.sbbi.upnp.messages.UPNPMessageFactory;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.ISO8601Date;
import net.sbbi.upnp.services.ServiceStateVariableTypes;
import net.sbbi.upnp.services.UPNPService;

public class ServiceStateVariable
implements ServiceStateVariableTypes {
    protected Set allowedvalues;
    protected String dataType;
    protected String defaultValue;
    protected String maximumRangeValue;
    protected String minimumRangeValue;
    protected String name;
    protected UPNPService parent;
    protected boolean sendEvents;
    private StateVariableMessage stateVarMsg = null;
    protected String stepRangeValue;

    protected ServiceStateVariable() {
    }

    public static Object UPNPToJavaObject(String string2, String charSequence) throws Throwable {
        if (charSequence == null) throw new Exception("null value");
        if (string2 == null) throw new Exception("null dataType");
        int n = string2.hashCode();
        if (n == UI1_INT) {
            return new Short((String)charSequence);
        }
        if (n == UI2_INT) {
            return new Integer((String)charSequence);
        }
        if (n == UI4_INT) {
            return new Long((String)charSequence);
        }
        if (n == I1_INT) {
            return new Byte((String)charSequence);
        }
        if (n == I2_INT) {
            return new Short((String)charSequence);
        }
        if (n == I4_INT) {
            return new Integer((String)charSequence);
        }
        if (n == INT_INT) {
            return new Integer((String)charSequence);
        }
        if (n == R4_INT) {
            return new Float((String)charSequence);
        }
        if (n == R8_INT) {
            return new Double((String)charSequence);
        }
        if (n == NUMBER_INT) {
            return new Double((String)charSequence);
        }
        if (n == FIXED_14_4_INT) {
            return new Double((String)charSequence);
        }
        if (n == FLOAT_INT) {
            return new Float((String)charSequence);
        }
        if (n == CHAR_INT) {
            return new Character(((String)charSequence).charAt(0));
        }
        if (n == STRING_INT) {
            return charSequence;
        }
        if (n == DATE_INT) {
            return ISO8601Date.parse((String)charSequence);
        }
        if (n == DATETIME_INT) {
            return ISO8601Date.parse((String)charSequence);
        }
        if (n == DATETIME_TZ_INT) {
            return ISO8601Date.parse((String)charSequence);
        }
        if (n == TIME_INT) {
            return ISO8601Date.parse((String)charSequence);
        }
        if (n == TIME_TZ_INT) {
            return ISO8601Date.parse((String)charSequence);
        }
        if (n == BOOLEAN_INT) {
            if (((String)charSequence).equals("1")) return Boolean.TRUE;
            if (((String)charSequence).equalsIgnoreCase("yes")) return Boolean.TRUE;
            if (!((String)charSequence).equals("true")) return Boolean.FALSE;
            return Boolean.TRUE;
        }
        if (n == BIN_BASE64_INT) {
            return charSequence;
        }
        if (n == BIN_HEX_INT) {
            return charSequence;
        }
        if (n == URI_INT) {
            return new URI((String)charSequence);
        }
        if (n == UUID_INT) {
            return charSequence;
        }
        charSequence = new StringBuilder("Unhandled data type ");
        ((StringBuilder)charSequence).append(string2);
        throw new Exception(((StringBuilder)charSequence).toString());
    }

    public static Class getDataTypeClassMapping(String string2) {
        int n = string2.hashCode();
        if (n == UI1_INT) {
            return Short.class;
        }
        if (n == UI2_INT) {
            return Integer.class;
        }
        if (n == UI4_INT) {
            return Long.class;
        }
        if (n == I1_INT) {
            return Byte.class;
        }
        if (n == I2_INT) {
            return Short.class;
        }
        if (n == I4_INT) {
            return Integer.class;
        }
        if (n == INT_INT) {
            return Integer.class;
        }
        if (n == R4_INT) {
            return Float.class;
        }
        if (n == R8_INT) {
            return Double.class;
        }
        if (n == NUMBER_INT) {
            return Double.class;
        }
        if (n == FIXED_14_4_INT) {
            return Double.class;
        }
        if (n == FLOAT_INT) {
            return Float.class;
        }
        if (n == CHAR_INT) {
            return Character.class;
        }
        if (n == STRING_INT) {
            return String.class;
        }
        if (n == DATE_INT) {
            return Date.class;
        }
        if (n == DATETIME_INT) {
            return Date.class;
        }
        if (n == DATETIME_TZ_INT) {
            return Date.class;
        }
        if (n == TIME_INT) {
            return Date.class;
        }
        if (n == TIME_TZ_INT) {
            return Date.class;
        }
        if (n == BOOLEAN_INT) {
            return Boolean.class;
        }
        if (n == BIN_BASE64_INT) {
            return String.class;
        }
        if (n == BIN_HEX_INT) {
            return String.class;
        }
        if (n == URI_INT) {
            return URI.class;
        }
        if (n != UUID_INT) return null;
        return String.class;
    }

    public static String getUPNPDataTypeMapping(String string2) {
        String string3;
        if (string2.equals(Short.class.getName())) return "i2";
        if (string2.equals("short")) {
            return "i2";
        }
        if (string2.equals(Byte.class.getName())) return "i1";
        if (string2.equals("byte")) {
            return "i1";
        }
        if (string2.equals(Integer.class.getName())) return "int";
        if (string2.equals("int")) {
            return "int";
        }
        if (string2.equals(Long.class.getName())) return "ui4";
        if (string2.equals("long")) {
            return "ui4";
        }
        if (string2.equals(Float.class.getName())) return "float";
        if (string2.equals("float")) {
            return "float";
        }
        if (string2.equals(Double.class.getName())) return "number";
        if (string2.equals("double")) {
            return "number";
        }
        boolean bl = string2.equals(Character.class.getName());
        String string4 = string3 = "char";
        if (bl) return string4;
        if (string2.equals("char")) {
            return string3;
        }
        bl = string2.equals(String.class.getName());
        string4 = string3 = "string";
        if (bl) return string4;
        if (string2.equals("string")) {
            return string3;
        }
        if (string2.equals(Date.class.getName())) {
            return "dateTime";
        }
        bl = string2.equals(Boolean.class.getName());
        string4 = string3 = "boolean";
        if (bl) return string4;
        if (string2.equals("boolean")) {
            return string3;
        }
        if (!string2.equals(URI.class.getName())) return null;
        return "uri";
    }

    public Set getAllowedvalues() {
        return this.allowedvalues;
    }

    public String getDataType() {
        return this.dataType;
    }

    public Class getDataTypeAsClass() {
        return ServiceStateVariable.getDataTypeClassMapping(this.dataType);
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getMaximumRangeValue() {
        return this.maximumRangeValue;
    }

    public String getMinimumRangeValue() {
        return this.minimumRangeValue;
    }

    public String getName() {
        return this.name;
    }

    public UPNPService getParent() {
        return this.parent;
    }

    public String getStepRangeValue() {
        return this.stepRangeValue;
    }

    public String getValue() throws UPNPResponseException, IOException {
        if (this.stateVarMsg != null) return this.stateVarMsg.service().getStateVariableValue();
        synchronized (this) {
            if (this.stateVarMsg != null) return this.stateVarMsg.service().getStateVariableValue();
            this.stateVarMsg = UPNPMessageFactory.getNewInstance(this.parent).getStateVariableMessage(this.name);
            return this.stateVarMsg.service().getStateVariableValue();
        }
    }

    public boolean isSendEvents() {
        return this.sendEvents;
    }
}

