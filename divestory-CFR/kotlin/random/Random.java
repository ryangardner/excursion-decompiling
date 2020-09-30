/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementations;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.PlatformRandomKt;
import kotlin.random.RandomKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b'\u0018\u0000 \u00182\u00020\u0001:\u0002\u0017\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016\u00a8\u0006\u0019"}, d2={"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Companion", "Default", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class Random {
    public static final Companion Companion;
    public static final Default Default;
    private static final Random defaultRandom;

    static {
        Default = new Default(null);
        defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
        Companion = Companion.INSTANCE;
    }

    public static /* synthetic */ byte[] nextBytes$default(Random random, byte[] arrby, int n, int n2, int n3, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return random.nextBytes(arrby, n, n2);
        n2 = arrby.length;
        return random.nextBytes(arrby, n, n2);
    }

    public abstract int nextBits(int var1);

    public boolean nextBoolean() {
        boolean bl = true;
        if (this.nextBits(1) == 0) return false;
        return bl;
    }

    public byte[] nextBytes(int n) {
        return this.nextBytes(new byte[n]);
    }

    public byte[] nextBytes(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "array");
        return this.nextBytes(arrby, 0, arrby.length);
    }

    /*
     * Unable to fully structure code
     */
    public byte[] nextBytes(byte[] var1_1, int var2_2, int var3_3) {
        Intrinsics.checkParameterIsNotNull(var1_1, "array");
        var4_4 = ((Object)var1_1).length;
        var5_5 = 0;
        var6_6 = 1;
        if (var2_2 < 0 || var4_4 < var2_2) ** GOTO lbl-1000
        var4_4 = ((Object)var1_1).length;
        if (var3_3 >= 0 && var4_4 >= var3_3) {
            var4_4 = 1;
        } else lbl-1000: // 2 sources:
        {
            var4_4 = 0;
        }
        if (var4_4 == 0) {
            var8_8 = new StringBuilder();
            var8_8.append("fromIndex (");
            var8_8.append(var2_2);
            var8_8.append(") or toIndex (");
            var8_8.append(var3_3);
            var8_8.append(") are out of range: 0..");
            var8_8.append(((Object)var1_1).length);
            var8_8.append('.');
            throw (Throwable)new IllegalArgumentException(var8_8.toString().toString());
        }
        var4_4 = var2_2 <= var3_3 ? var6_6 : 0;
        if (var4_4 == 0) {
            var1_1 = new StringBuilder();
            var1_1.append("fromIndex (");
            var1_1.append(var2_2);
            var1_1.append(") must be not greater than toIndex (");
            var1_1.append(var3_3);
            var1_1.append(").");
            throw (Throwable)new IllegalArgumentException(var1_1.toString().toString());
        }
        var6_6 = (var3_3 - var2_2) / 4;
        for (var4_4 = 0; var4_4 < var6_6; var2_2 += 4, ++var4_4) {
            var7_7 = this.nextInt();
            var1_1[var2_2] = (byte)var7_7;
            var1_1[var2_2 + 1] = (byte)(var7_7 >>> 8);
            var1_1[var2_2 + 2] = (byte)(var7_7 >>> 16);
            var1_1[var2_2 + 3] = (byte)(var7_7 >>> 24);
        }
        var4_4 = var3_3 - var2_2;
        var6_6 = this.nextBits(var4_4 * 8);
        var3_3 = var5_5;
        while (var3_3 < var4_4) {
            var1_1[var2_2 + var3_3] = (byte)(var6_6 >>> var3_3 * 8);
            ++var3_3;
        }
        return var1_1;
    }

    public double nextDouble() {
        return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
    }

    public double nextDouble(double d) {
        return this.nextDouble(0.0, d);
    }

    /*
     * Unable to fully structure code
     */
    public double nextDouble(double var1_1, double var3_2) {
        RandomKt.checkRangeBounds(var1_1, var3_2);
        var5_3 = var3_2 - var1_1;
        if (!Double.isInfinite(var5_3)) ** GOTO lbl-1000
        var7_4 = Double.isInfinite(var1_1);
        var8_5 = true;
        var9_6 = var7_4 == false && Double.isNaN(var1_1) == false;
        if (var9_6 && (var9_6 = Double.isInfinite(var3_2) == false && Double.isNaN(var3_2) == false ? var8_5 : false)) {
            var5_3 = this.nextDouble();
            var10_7 = 2;
            var5_3 *= var3_2 / var10_7 - var1_1 / var10_7;
            var1_1 = var1_1 + var5_3 + var5_3;
        } else lbl-1000: // 2 sources:
        {
            var1_1 += this.nextDouble() * var5_3;
        }
        var5_3 = var1_1;
        if (!(var1_1 >= var3_2)) return var5_3;
        return Math.nextAfter(var3_2, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    public float nextFloat() {
        return (float)this.nextBits(24) / (float)16777216;
    }

    public int nextInt() {
        return this.nextBits(32);
    }

    public int nextInt(int n) {
        return this.nextInt(0, n);
    }

    public int nextInt(int n, int n2) {
        RandomKt.checkRangeBounds(n, n2);
        int n3 = n2 - n;
        if (n3 > 0 || n3 == Integer.MIN_VALUE) {
            int n4;
            if ((-n3 & n3) == n3) {
                n2 = this.nextBits(RandomKt.fastLog2(n3));
                return n + n2;
            }
            while ((n4 = this.nextInt() >>> 1) - (n2 = n4 % n3) + (n3 - 1) < 0) {
            }
            return n + n2;
        }
        while (n > (n3 = this.nextInt()) || n2 <= n3) {
        }
        return n3;
    }

    public long nextLong() {
        return ((long)this.nextInt() << 32) + (long)this.nextInt();
    }

    public long nextLong(long l) {
        return this.nextLong(0L, l);
    }

    public long nextLong(long l, long l2) {
        RandomKt.checkRangeBounds(l, l2);
        long l3 = l2 - l;
        if (l3 > 0L) {
            long l4;
            if ((-l3 & l3) == l3) {
                int n = (int)l3;
                int n2 = (int)(l3 >>> 32);
                if (n != 0) {
                    n2 = this.nextBits(RandomKt.fastLog2(n));
                } else {
                    if (n2 != 1) {
                        l2 = ((long)this.nextBits(RandomKt.fastLog2(n2)) << 32) + (long)this.nextInt();
                        return l + l2;
                    }
                    n2 = this.nextInt();
                }
                l2 = (long)n2 & 0xFFFFFFFFL;
                return l + l2;
            }
            while ((l4 = this.nextLong() >>> 1) - (l2 = l4 % l3) + (l3 - 1L) < 0L) {
            }
            return l + l2;
        }
        while (l > (l3 = this.nextLong()) || l2 <= l3) {
        }
        return l3;
    }

    @Deprecated(level=DeprecationLevel.HIDDEN, message="Use Default companion object instead")
    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2={"Lkotlin/random/Random$Companion;", "Lkotlin/random/Random;", "()V", "nextBits", "", "bitCount", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion
    extends Random {
        public static final Companion INSTANCE = new Companion();

        private Companion() {
        }

        @Override
        public int nextBits(int n) {
            return Default.nextBits(n);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "()V", "Companion", "Lkotlin/random/Random$Companion;", "Companion$annotations", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Default
    extends Random {
        private Default() {
        }

        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(level=DeprecationLevel.HIDDEN, message="Use Default companion object instead")
        public static /* synthetic */ void Companion$annotations() {
        }

        @Override
        public int nextBits(int n) {
            return defaultRandom.nextBits(n);
        }

        @Override
        public boolean nextBoolean() {
            return defaultRandom.nextBoolean();
        }

        @Override
        public byte[] nextBytes(int n) {
            return defaultRandom.nextBytes(n);
        }

        @Override
        public byte[] nextBytes(byte[] arrby) {
            Intrinsics.checkParameterIsNotNull(arrby, "array");
            return defaultRandom.nextBytes(arrby);
        }

        @Override
        public byte[] nextBytes(byte[] arrby, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrby, "array");
            return defaultRandom.nextBytes(arrby, n, n2);
        }

        @Override
        public double nextDouble() {
            return defaultRandom.nextDouble();
        }

        @Override
        public double nextDouble(double d) {
            return defaultRandom.nextDouble(d);
        }

        @Override
        public double nextDouble(double d, double d2) {
            return defaultRandom.nextDouble(d, d2);
        }

        @Override
        public float nextFloat() {
            return defaultRandom.nextFloat();
        }

        @Override
        public int nextInt() {
            return defaultRandom.nextInt();
        }

        @Override
        public int nextInt(int n) {
            return defaultRandom.nextInt(n);
        }

        @Override
        public int nextInt(int n, int n2) {
            return defaultRandom.nextInt(n, n2);
        }

        @Override
        public long nextLong() {
            return defaultRandom.nextLong();
        }

        @Override
        public long nextLong(long l) {
            return defaultRandom.nextLong(l);
        }

        @Override
        public long nextLong(long l, long l2) {
            return defaultRandom.nextLong(l, l2);
        }
    }

}

