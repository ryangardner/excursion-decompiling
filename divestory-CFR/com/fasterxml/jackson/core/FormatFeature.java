/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

public interface FormatFeature {
    public boolean enabledByDefault();

    public boolean enabledIn(int var1);

    public int getMask();
}

