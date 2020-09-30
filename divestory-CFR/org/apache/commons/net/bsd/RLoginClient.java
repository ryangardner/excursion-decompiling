/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.bsd;

import java.io.IOException;
import org.apache.commons.net.bsd.RCommandClient;

public class RLoginClient
extends RCommandClient {
    public static final int DEFAULT_PORT = 513;

    public RLoginClient() {
        this.setDefaultPort(513);
    }

    public void rlogin(String string2, String string3, String string4) throws IOException {
        this.rexec(string2, string3, string4, false);
    }

    public void rlogin(String string2, String string3, String string4, int n) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string4);
        stringBuilder.append("/");
        stringBuilder.append(n);
        this.rexec(string2, string3, stringBuilder.toString(), false);
    }
}

