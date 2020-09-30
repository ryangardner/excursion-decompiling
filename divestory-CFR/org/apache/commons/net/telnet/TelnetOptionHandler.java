/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

public abstract class TelnetOptionHandler {
    private boolean acceptLocal = false;
    private boolean acceptRemote = false;
    private boolean doFlag = false;
    private boolean initialLocal = false;
    private boolean initialRemote = false;
    private int optionCode = -1;
    private boolean willFlag = false;

    public TelnetOptionHandler(int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.optionCode = n;
        this.initialLocal = bl;
        this.initialRemote = bl2;
        this.acceptLocal = bl3;
        this.acceptRemote = bl4;
    }

    public int[] answerSubnegotiation(int[] arrn, int n) {
        return null;
    }

    public boolean getAcceptLocal() {
        return this.acceptLocal;
    }

    public boolean getAcceptRemote() {
        return this.acceptRemote;
    }

    boolean getDo() {
        return this.doFlag;
    }

    public boolean getInitLocal() {
        return this.initialLocal;
    }

    public boolean getInitRemote() {
        return this.initialRemote;
    }

    public int getOptionCode() {
        return this.optionCode;
    }

    boolean getWill() {
        return this.willFlag;
    }

    public void setAcceptLocal(boolean bl) {
        this.acceptLocal = bl;
    }

    public void setAcceptRemote(boolean bl) {
        this.acceptRemote = bl;
    }

    void setDo(boolean bl) {
        this.doFlag = bl;
    }

    public void setInitLocal(boolean bl) {
        this.initialLocal = bl;
    }

    public void setInitRemote(boolean bl) {
        this.initialRemote = bl;
    }

    void setWill(boolean bl) {
        this.willFlag = bl;
    }

    public int[] startSubnegotiationLocal() {
        return null;
    }

    public int[] startSubnegotiationRemote() {
        return null;
    }
}

