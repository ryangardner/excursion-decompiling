/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.net.InetAddress;
import javax.mail.PasswordAuthentication;

public abstract class Authenticator {
    private int requestingPort;
    private String requestingPrompt;
    private String requestingProtocol;
    private InetAddress requestingSite;
    private String requestingUserName;

    private void reset() {
        this.requestingSite = null;
        this.requestingPort = -1;
        this.requestingProtocol = null;
        this.requestingPrompt = null;
        this.requestingUserName = null;
    }

    protected final String getDefaultUserName() {
        return this.requestingUserName;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return null;
    }

    protected final int getRequestingPort() {
        return this.requestingPort;
    }

    protected final String getRequestingPrompt() {
        return this.requestingPrompt;
    }

    protected final String getRequestingProtocol() {
        return this.requestingProtocol;
    }

    protected final InetAddress getRequestingSite() {
        return this.requestingSite;
    }

    final PasswordAuthentication requestPasswordAuthentication(InetAddress inetAddress, int n, String string2, String string3, String string4) {
        this.reset();
        this.requestingSite = inetAddress;
        this.requestingPort = n;
        this.requestingProtocol = string2;
        this.requestingPrompt = string3;
        this.requestingUserName = string4;
        return this.getPasswordAuthentication();
    }
}

