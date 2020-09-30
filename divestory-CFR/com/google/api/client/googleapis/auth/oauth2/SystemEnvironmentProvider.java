/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

class SystemEnvironmentProvider {
    static final SystemEnvironmentProvider INSTANCE = new SystemEnvironmentProvider();

    SystemEnvironmentProvider() {
    }

    String getEnv(String string2) {
        return System.getenv(string2);
    }

    boolean getEnvEquals(String string2, String string3) {
        if (!System.getenv().containsKey(string2)) return false;
        if (!System.getenv(string2).equals(string3)) return false;
        return true;
    }
}

