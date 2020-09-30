package com.google.common.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface Hasher extends PrimitiveSink {
   HashCode hash();

   @Deprecated
   int hashCode();

   Hasher putBoolean(boolean var1);

   Hasher putByte(byte var1);

   Hasher putBytes(ByteBuffer var1);

   Hasher putBytes(byte[] var1);

   Hasher putBytes(byte[] var1, int var2, int var3);

   Hasher putChar(char var1);

   Hasher putDouble(double var1);

   Hasher putFloat(float var1);

   Hasher putInt(int var1);

   Hasher putLong(long var1);

   <T> Hasher putObject(T var1, Funnel<? super T> var2);

   Hasher putShort(short var1);

   Hasher putString(CharSequence var1, Charset var2);

   Hasher putUnencodedChars(CharSequence var1);
}
