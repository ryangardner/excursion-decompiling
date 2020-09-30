/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractCompositeHashFunction;
import com.google.common.hash.ChecksumHashFunction;
import com.google.common.hash.Crc32cHashFunction;
import com.google.common.hash.FarmHashFingerprint64;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.ImmutableSupplier;
import com.google.common.hash.MacHashFunction;
import com.google.common.hash.MessageDigestHashFunction;
import com.google.common.hash.Murmur3_128HashFunction;
import com.google.common.hash.Murmur3_32HashFunction;
import com.google.common.hash.SipHashFunction;
import com.google.errorprone.annotations.Immutable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.crypto.spec.SecretKeySpec;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Hashing {
    static final int GOOD_FAST_HASH_SEED = (int)System.currentTimeMillis();

    private Hashing() {
    }

    public static HashFunction adler32() {
        return ChecksumType.ADLER_32.hashFunction;
    }

    static int checkPositiveAndMakeMultipleOf32(int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Number of bits must be positive");
        return n + 31 & -32;
    }

    public static HashCode combineOrdered(Iterable<HashCode> object) {
        byte[] arrby = object.iterator();
        Preconditions.checkArgument(arrby.hasNext(), "Must be at least 1 hash code to combine.");
        int n = arrby.next().bits() / 8;
        arrby = new byte[n];
        object = object.iterator();
        block0 : while (object.hasNext()) {
            byte[] arrby2 = ((HashCode)object.next()).asBytes();
            int n2 = arrby2.length;
            int n3 = 0;
            boolean bl = n2 == n;
            Preconditions.checkArgument(bl, "All hashcodes must have the same bit length.");
            do {
                if (n3 >= arrby2.length) continue block0;
                arrby[n3] = (byte)(arrby[n3] * 37 ^ arrby2[n3]);
                ++n3;
            } while (true);
            break;
        }
        return HashCode.fromBytesNoCopy(arrby);
    }

    public static HashCode combineUnordered(Iterable<HashCode> object) {
        byte[] arrby = object.iterator();
        Preconditions.checkArgument(arrby.hasNext(), "Must be at least 1 hash code to combine.");
        int n = arrby.next().bits() / 8;
        arrby = new byte[n];
        object = object.iterator();
        block0 : while (object.hasNext()) {
            byte[] arrby2 = ((HashCode)object.next()).asBytes();
            int n2 = arrby2.length;
            int n3 = 0;
            boolean bl = n2 == n;
            Preconditions.checkArgument(bl, "All hashcodes must have the same bit length.");
            do {
                if (n3 >= arrby2.length) continue block0;
                arrby[n3] = (byte)(arrby[n3] + arrby2[n3]);
                ++n3;
            } while (true);
            break;
        }
        return HashCode.fromBytesNoCopy(arrby);
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction ... arrhashFunction) {
        ArrayList<HashFunction> arrayList = new ArrayList<HashFunction>();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        arrayList.addAll(Arrays.asList(arrhashFunction));
        return new ConcatenatedHashFunction(arrayList.toArray(new HashFunction[0]));
    }

    public static HashFunction concatenating(Iterable<HashFunction> object) {
        Preconditions.checkNotNull(object);
        ArrayList<HashFunction> arrayList = new ArrayList<HashFunction>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add((HashFunction)object.next());
        }
        boolean bl = arrayList.size() > 0;
        Preconditions.checkArgument(bl, "number of hash functions (%s) must be > 0", arrayList.size());
        return new ConcatenatedHashFunction(arrayList.toArray(new HashFunction[0]));
    }

    public static int consistentHash(long l, int n) {
        int n2;
        int n3 = 0;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "buckets must be positive: %s", n);
        LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(l);
        while ((n2 = (int)((double)(n3 + 1) / linearCongruentialGenerator.nextDouble())) >= 0) {
            if (n2 >= n) return n3;
            n3 = n2;
        }
        return n3;
    }

    public static int consistentHash(HashCode hashCode, int n) {
        return Hashing.consistentHash(hashCode.padToLong(), n);
    }

    public static HashFunction crc32() {
        return ChecksumType.CRC_32.hashFunction;
    }

    public static HashFunction crc32c() {
        return Crc32cHashFunction.CRC_32_C;
    }

    public static HashFunction farmHashFingerprint64() {
        return FarmHashFingerprint64.FARMHASH_FINGERPRINT_64;
    }

    public static HashFunction goodFastHash(int n) {
        if ((n = Hashing.checkPositiveAndMakeMultipleOf32(n)) == 32) {
            return Murmur3_32HashFunction.GOOD_FAST_HASH_32;
        }
        if (n <= 128) {
            return Murmur3_128HashFunction.GOOD_FAST_HASH_128;
        }
        int n2 = (n + 127) / 128;
        HashFunction[] arrhashFunction = new HashFunction[n2];
        arrhashFunction[0] = Murmur3_128HashFunction.GOOD_FAST_HASH_128;
        int n3 = GOOD_FAST_HASH_SEED;
        n = 1;
        while (n < n2) {
            arrhashFunction[n] = Hashing.murmur3_128(n3 += 1500450271);
            ++n;
        }
        return new ConcatenatedHashFunction(arrhashFunction);
    }

    public static HashFunction hmacMd5(Key key) {
        return new MacHashFunction("HmacMD5", key, Hashing.hmacToString("hmacMd5", key));
    }

    public static HashFunction hmacMd5(byte[] arrby) {
        return Hashing.hmacMd5(new SecretKeySpec(Preconditions.checkNotNull(arrby), "HmacMD5"));
    }

    public static HashFunction hmacSha1(Key key) {
        return new MacHashFunction("HmacSHA1", key, Hashing.hmacToString("hmacSha1", key));
    }

    public static HashFunction hmacSha1(byte[] arrby) {
        return Hashing.hmacSha1(new SecretKeySpec(Preconditions.checkNotNull(arrby), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(Key key) {
        return new MacHashFunction("HmacSHA256", key, Hashing.hmacToString("hmacSha256", key));
    }

    public static HashFunction hmacSha256(byte[] arrby) {
        return Hashing.hmacSha256(new SecretKeySpec(Preconditions.checkNotNull(arrby), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(Key key) {
        return new MacHashFunction("HmacSHA512", key, Hashing.hmacToString("hmacSha512", key));
    }

    public static HashFunction hmacSha512(byte[] arrby) {
        return Hashing.hmacSha512(new SecretKeySpec(Preconditions.checkNotNull(arrby), "HmacSHA512"));
    }

    private static String hmacToString(String string2, Key key) {
        return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", string2, key.getAlgorithm(), key.getFormat());
    }

    @Deprecated
    public static HashFunction md5() {
        return Md5Holder.MD5;
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128HashFunction.MURMUR3_128;
    }

    public static HashFunction murmur3_128(int n) {
        return new Murmur3_128HashFunction(n);
    }

    public static HashFunction murmur3_32() {
        return Murmur3_32HashFunction.MURMUR3_32;
    }

    public static HashFunction murmur3_32(int n) {
        return new Murmur3_32HashFunction(n);
    }

    @Deprecated
    public static HashFunction sha1() {
        return Sha1Holder.SHA_1;
    }

    public static HashFunction sha256() {
        return Sha256Holder.SHA_256;
    }

    public static HashFunction sha384() {
        return Sha384Holder.SHA_384;
    }

    public static HashFunction sha512() {
        return Sha512Holder.SHA_512;
    }

    public static HashFunction sipHash24() {
        return SipHashFunction.SIP_HASH_24;
    }

    public static HashFunction sipHash24(long l, long l2) {
        return new SipHashFunction(2, 4, l, l2);
    }

    @Immutable
    static abstract class ChecksumType
    extends Enum<ChecksumType>
    implements ImmutableSupplier<Checksum> {
        private static final /* synthetic */ ChecksumType[] $VALUES;
        public static final /* enum */ ChecksumType ADLER_32;
        public static final /* enum */ ChecksumType CRC_32;
        public final HashFunction hashFunction;

        static {
            ChecksumType checksumType;
            CRC_32 = new ChecksumType("Hashing.crc32()"){

                @Override
                public Checksum get() {
                    return new CRC32();
                }
            };
            ADLER_32 = checksumType = new ChecksumType("Hashing.adler32()"){

                @Override
                public Checksum get() {
                    return new Adler32();
                }
            };
            $VALUES = new ChecksumType[]{CRC_32, checksumType};
        }

        private ChecksumType(String string3) {
            this.hashFunction = new ChecksumHashFunction(this, 32, string3);
        }

        public static ChecksumType valueOf(String string2) {
            return Enum.valueOf(ChecksumType.class, string2);
        }

        public static ChecksumType[] values() {
            return (ChecksumType[])$VALUES.clone();
        }

    }

    private static final class ConcatenatedHashFunction
    extends AbstractCompositeHashFunction {
        private ConcatenatedHashFunction(HashFunction ... arrhashFunction) {
            super(arrhashFunction);
            int n = arrhashFunction.length;
            int n2 = 0;
            while (n2 < n) {
                HashFunction hashFunction = arrhashFunction[n2];
                boolean bl = hashFunction.bits() % 8 == 0;
                Preconditions.checkArgument(bl, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object)hashFunction);
                ++n2;
            }
        }

        @Override
        public int bits() {
            HashFunction[] arrhashFunction = this.functions;
            int n = arrhashFunction.length;
            int n2 = 0;
            int n3 = 0;
            while (n2 < n) {
                n3 += arrhashFunction[n2].bits();
                ++n2;
            }
            return n3;
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof ConcatenatedHashFunction)) return false;
            object = (ConcatenatedHashFunction)object;
            return Arrays.equals(this.functions, ((ConcatenatedHashFunction)object).functions);
        }

        public int hashCode() {
            return Arrays.hashCode(this.functions);
        }

        @Override
        HashCode makeHash(Hasher[] arrhasher) {
            byte[] arrby = new byte[this.bits() / 8];
            int n = arrhasher.length;
            int n2 = 0;
            int n3 = 0;
            while (n2 < n) {
                HashCode hashCode = arrhasher[n2].hash();
                n3 += hashCode.writeBytesTo(arrby, n3, hashCode.bits() / 8);
                ++n2;
            }
            return HashCode.fromBytesNoCopy(arrby);
        }
    }

    private static final class LinearCongruentialGenerator {
        private long state;

        public LinearCongruentialGenerator(long l) {
            this.state = l;
        }

        public double nextDouble() {
            long l;
            this.state = l = this.state * 2862933555777941757L + 1L;
            return (double)((int)(l >>> 33) + 1) / 2.147483648E9;
        }
    }

    private static class Md5Holder {
        static final HashFunction MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");

        private Md5Holder() {
        }
    }

    private static class Sha1Holder {
        static final HashFunction SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");

        private Sha1Holder() {
        }
    }

    private static class Sha256Holder {
        static final HashFunction SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");

        private Sha256Holder() {
        }
    }

    private static class Sha384Holder {
        static final HashFunction SHA_384 = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");

        private Sha384Holder() {
        }
    }

    private static class Sha512Holder {
        static final HashFunction SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");

        private Sha512Holder() {
        }
    }

}

