/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

public interface TelnetNotificationHandler {
    public static final int RECEIVED_COMMAND = 5;
    public static final int RECEIVED_DO = 1;
    public static final int RECEIVED_DONT = 2;
    public static final int RECEIVED_WILL = 3;
    public static final int RECEIVED_WONT = 4;

    public void receivedNegotiation(int var1, int var2);
}

