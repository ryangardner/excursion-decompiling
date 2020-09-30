/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.security.Principal;

public interface Credentials {
    public String getPassword();

    public Principal getUserPrincipal();
}

