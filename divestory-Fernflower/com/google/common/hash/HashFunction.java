package com.google.common.hash;

import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Immutable
public interface HashFunction {
   int bits();

   HashCode hashBytes(ByteBuffer var1);

   HashCode hashBytes(byte[] var1);

   HashCode hashBytes(byte[] var1, int var2, int var3);

   HashCode hashInt(int var1);

   HashCode hashLong(long var1);

   <T> HashCode hashObject(T var1, Funnel<? super T> var2);

   HashCode hashString(CharSequence var1, Charset var2);

   HashCode hashUnencodedChars(CharSequence var1);

   Hasher newHasher();

   Hasher newHasher(int var1);
}
