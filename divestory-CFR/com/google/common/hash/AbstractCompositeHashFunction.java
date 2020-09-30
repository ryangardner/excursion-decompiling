/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.Immutable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Immutable
abstract class AbstractCompositeHashFunction
extends AbstractHashFunction {
    private static final long serialVersionUID = 0L;
    final HashFunction[] functions;

    AbstractCompositeHashFunction(HashFunction ... arrhashFunction) {
        int n = arrhashFunction.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.functions = arrhashFunction;
                return;
            }
            Preconditions.checkNotNull(arrhashFunction[n2]);
            ++n2;
        } while (true);
    }

    private Hasher fromHashers(final Hasher[] arrhasher) {
        return new Hasher(){

            @Override
            public HashCode hash() {
                return AbstractCompositeHashFunction.this.makeHash(arrhasher);
            }

            @Override
            public Hasher putBoolean(boolean bl) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putBoolean(bl);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putByte(byte by) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putByte(by);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putBytes(ByteBuffer byteBuffer) {
                int n = byteBuffer.position();
                Hasher[] arrhasher2 = arrhasher;
                int n2 = arrhasher2.length;
                int n3 = 0;
                while (n3 < n2) {
                    Hasher hasher = arrhasher2[n3];
                    byteBuffer.position(n);
                    hasher.putBytes(byteBuffer);
                    ++n3;
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] arrby) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putBytes(arrby);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] arrby, int n, int n2) {
                Hasher[] arrhasher2 = arrhasher;
                int n3 = arrhasher2.length;
                int n4 = 0;
                while (n4 < n3) {
                    arrhasher2[n4].putBytes(arrby, n, n2);
                    ++n4;
                }
                return this;
            }

            @Override
            public Hasher putChar(char c) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putChar(c);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putDouble(double d) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putDouble(d);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putFloat(float f) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putFloat(f);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putInt(int n) {
                Hasher[] arrhasher2 = arrhasher;
                int n2 = arrhasher2.length;
                int n3 = 0;
                while (n3 < n2) {
                    arrhasher2[n3].putInt(n);
                    ++n3;
                }
                return this;
            }

            @Override
            public Hasher putLong(long l) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putLong(l);
                    ++n2;
                }
                return this;
            }

            @Override
            public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putObject(t, funnel);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putShort(short s) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putShort(s);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putString(CharSequence charSequence, Charset charset) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putString(charSequence, charset);
                    ++n2;
                }
                return this;
            }

            @Override
            public Hasher putUnencodedChars(CharSequence charSequence) {
                Hasher[] arrhasher2 = arrhasher;
                int n = arrhasher2.length;
                int n2 = 0;
                while (n2 < n) {
                    arrhasher2[n2].putUnencodedChars(charSequence);
                    ++n2;
                }
                return this;
            }
        };
    }

    abstract HashCode makeHash(Hasher[] var1);

    @Override
    public Hasher newHasher() {
        int n = this.functions.length;
        Hasher[] arrhasher = new Hasher[n];
        int n2 = 0;
        while (n2 < n) {
            arrhasher[n2] = this.functions[n2].newHasher();
            ++n2;
        }
        return this.fromHashers(arrhasher);
    }

    @Override
    public Hasher newHasher(int n) {
        int n2 = 0;
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        int n3 = this.functions.length;
        Hasher[] arrhasher = new Hasher[n3];
        while (n2 < n3) {
            arrhasher[n2] = this.functions[n2].newHasher(n);
            ++n2;
        }
        return this.fromHashers(arrhasher);
    }

}

