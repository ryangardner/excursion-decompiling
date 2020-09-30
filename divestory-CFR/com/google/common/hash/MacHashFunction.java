/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractByteHasher;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

@Immutable
final class MacHashFunction
extends AbstractHashFunction {
    private final int bits;
    private final Key key;
    private final Mac prototype;
    private final boolean supportsClone;
    private final String toString;

    MacHashFunction(String string2, Key key, String string3) {
        this.prototype = MacHashFunction.getMac(string2, key);
        this.key = Preconditions.checkNotNull(key);
        this.toString = Preconditions.checkNotNull(string3);
        this.bits = this.prototype.getMacLength() * 8;
        this.supportsClone = MacHashFunction.supportsClone(this.prototype);
    }

    private static Mac getMac(String object, Key key) {
        try {
            object = Mac.getInstance((String)object);
            ((Mac)object).init(key);
            return object;
        }
        catch (InvalidKeyException invalidKeyException) {
            throw new IllegalArgumentException(invalidKeyException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new IllegalStateException(noSuchAlgorithmException);
        }
    }

    private static boolean supportsClone(Mac mac) {
        try {
            mac.clone();
            return true;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return false;
        }
    }

    @Override
    public int bits() {
        return this.bits;
    }

    @Override
    public Hasher newHasher() {
        if (!this.supportsClone) return new MacHasher(MacHashFunction.getMac(this.prototype.getAlgorithm(), this.key));
        try {
            return new MacHasher((Mac)this.prototype.clone());
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return new MacHasher(MacHashFunction.getMac(this.prototype.getAlgorithm(), this.key));
        }
    }

    public String toString() {
        return this.toString;
    }

    private static final class MacHasher
    extends AbstractByteHasher {
        private boolean done;
        private final Mac mac;

        private MacHasher(Mac mac) {
            this.mac = mac;
        }

        private void checkNotDone() {
            Preconditions.checkState(this.done ^ true, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override
        public HashCode hash() {
            this.checkNotDone();
            this.done = true;
            return HashCode.fromBytesNoCopy(this.mac.doFinal());
        }

        @Override
        protected void update(byte by) {
            this.checkNotDone();
            this.mac.update(by);
        }

        @Override
        protected void update(ByteBuffer byteBuffer) {
            this.checkNotDone();
            Preconditions.checkNotNull(byteBuffer);
            this.mac.update(byteBuffer);
        }

        @Override
        protected void update(byte[] arrby) {
            this.checkNotDone();
            this.mac.update(arrby);
        }

        @Override
        protected void update(byte[] arrby, int n, int n2) {
            this.checkNotDone();
            this.mac.update(arrby, n, n2);
        }
    }

}

