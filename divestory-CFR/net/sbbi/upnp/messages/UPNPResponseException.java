/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

public class UPNPResponseException
extends Exception {
    private static final long serialVersionUID = 8313107558129180594L;
    protected int detailErrorCode;
    protected String detailErrorDescription;
    protected String faultCode;
    protected String faultString;

    public UPNPResponseException() {
    }

    public UPNPResponseException(int n, String string2) {
        this.detailErrorCode = n;
        this.detailErrorDescription = string2;
    }

    public int getDetailErrorCode() {
        return this.detailErrorCode;
    }

    public String getDetailErrorDescription() {
        return this.detailErrorDescription;
    }

    public String getFaultCode() {
        String string2;
        String string3 = string2 = this.faultCode;
        if (string2 != null) return string3;
        return "Client";
    }

    public String getFaultString() {
        String string2;
        String string3 = string2 = this.faultString;
        if (string2 != null) return string3;
        return "UPnPError";
    }

    @Override
    public String getLocalizedMessage() {
        return this.getMessage();
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder("Detailed error code :");
        stringBuilder.append(this.detailErrorCode);
        stringBuilder.append(", Detailed error description :");
        stringBuilder.append(this.detailErrorDescription);
        return stringBuilder.toString();
    }
}

