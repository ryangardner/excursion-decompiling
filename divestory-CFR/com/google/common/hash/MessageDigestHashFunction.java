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
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Immutable
final class MessageDigestHashFunction
extends AbstractHashFunction
implements Serializable {
    private final int bytes;
    private final MessageDigest prototype;
    private final boolean supportsClone;
    private final String toString;

    MessageDigestHashFunction(String object, int n, String string2) {
        this.toString = Preconditions.checkNotNull(string2);
        this.prototype = object = MessageDigestHashFunction.getMessageDigest((String)object);
        int n2 = ((MessageDigest)object).getDigestLength();
        boolean bl = n >= 4 && n <= n2;
        Preconditions.checkArgument(bl, "bytes (%s) must be >= 4 and < %s", n, n2);
        this.bytes = n;
        this.supportsClone = MessageDigestHashFunction.supportsClone(this.prototype);
    }

    MessageDigestHashFunction(String object, String string2) {
        this.prototype = object = MessageDigestHashFunction.getMessageDigest((String)object);
        this.bytes = ((MessageDigest)object).getDigestLength();
        this.toString = Preconditions.checkNotNull(string2);
        this.supportsClone = MessageDigestHashFunction.supportsClone(this.prototype);
    }

    private static MessageDigest getMessageDigest(String object) {
        try {
            return MessageDigest.getInstance((String)object);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    private static boolean supportsClone(MessageDigest messageDigest) {
        try {
            messageDigest.clone();
            return true;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return false;
        }
    }

    @Override
    public int bits() {
        return this.bytes * 8;
    }

    @Override
    public Hasher newHasher() {
        if (!this.supportsClone) return new MessageDigestHasher(MessageDigestHashFunction.getMessageDigest(this.prototype.getAlgorithm()), this.bytes);
        try {
            return new MessageDigestHasher((MessageDigest)this.prototype.clone(), this.bytes);
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return new MessageDigestHasher(MessageDigestHashFunction.getMessageDigest(this.prototype.getAlgorithm()), this.bytes);
        }
    }

    public String toString() {
        return this.toString;
    }

    Object writeReplace() {
        return new SerializedForm(this.prototype.getAlgorithm(), this.bytes, this.toString);
    }

    private static final class MessageDigestHasher
    extends AbstractByteHasher {
        private final int bytes;
        private final MessageDigest digest;
        private boolean done;

        private MessageDigestHasher(MessageDigest messageDigest, int n) {
            this.digest = messageDigest;
            this.bytes = n;
        }

        private void checkNotDone() {
            Preconditions.checkState(this.done ^ true, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override
        public HashCode hash() {
            this.checkNotDone();
            this.done = true;
            if (this.bytes != this.digest.getDigestLength()) return HashCode.fromBytesNoCopy(Arrays.copyOf(this.digest.digest(), this.bytes));
            return HashCode.fromBytesNoCopy(this.digest.digest());
        }

        @Override
        protected void update(byte by) {
            this.checkNotDone();
            this.digest.update(by);
        }

        @Override
        protected void update(ByteBuffer byteBuffer) {
            this.checkNotDone();
            this.digest.update(byteBuffer);
        }

        @Override
        protected void update(byte[] arrby, int n, int n2) {
            this.checkNotDone();
            this.digest.update(arrby, n, n2);
        }
    }

    private static final class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final String algorithmName;
        private final int bytes;
        private final String toString;

        private SerializedForm(String string2, int n, String string3) {
            this.algorithmName = string2;
            this.bytes = n;
            this.toString = string3;
        }

        private Object readResolve() {
            return new MessageDigestHashFunction(this.algorithmName, this.bytes, this.toString);
        }
    }

}

