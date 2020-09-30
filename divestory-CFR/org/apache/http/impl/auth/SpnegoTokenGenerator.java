/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import java.io.IOException;

public interface SpnegoTokenGenerator {
    public byte[] generateSpnegoDERObject(byte[] var1) throws IOException;
}

