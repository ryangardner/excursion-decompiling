/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractByteHasher;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.ImmutableSupplier;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.zip.Checksum;

@Immutable
final class ChecksumHashFunction
extends AbstractHashFunction
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final int bits;
    private final ImmutableSupplier<? extends Checksum> checksumSupplier;
    private final String toString;

    ChecksumHashFunction(ImmutableSupplier<? extends Checksum> immutableSupplier, int n, String string2) {
        this.checksumSupplier = Preconditions.checkNotNull(immutableSupplier);
        boolean bl = n == 32 || n == 64;
        Preconditions.checkArgument(bl, "bits (%s) must be either 32 or 64", n);
        this.bits = n;
        this.toString = Preconditions.checkNotNull(string2);
    }

    @Override
    public int bits() {
        return this.bits;
    }

    @Override
    public Hasher newHasher() {
        return new ChecksumHasher((Checksum)this.checksumSupplier.get());
    }

    public String toString() {
        return this.toString;
    }

    private final class ChecksumHasher
    extends AbstractByteHasher {
        private final Checksum checksum;

        private ChecksumHasher(Checksum checksum) {
            this.checksum = Preconditions.checkNotNull(checksum);
        }

        @Override
        public HashCode hash() {
            long l = this.checksum.getValue();
            if (ChecksumHashFunction.this.bits != 32) return HashCode.fromLong(l);
            return HashCode.fromInt((int)l);
        }

        @Override
        protected void update(byte by) {
            this.checksum.update(by);
        }

        @Override
        protected void update(byte[] arrby, int n, int n2) {
            this.checksum.update(arrby, n, n2);
        }
    }

}

