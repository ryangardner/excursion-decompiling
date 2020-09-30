package com.google.common.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface PrimitiveSink {
   PrimitiveSink putBoolean(boolean var1);

   PrimitiveSink putByte(byte var1);

   PrimitiveSink putBytes(ByteBuffer var1);

   PrimitiveSink putBytes(byte[] var1);

   PrimitiveSink putBytes(byte[] var1, int var2, int var3);

   PrimitiveSink putChar(char var1);

   PrimitiveSink putDouble(double var1);

   PrimitiveSink putFloat(float var1);

   PrimitiveSink putInt(int var1);

   PrimitiveSink putLong(long var1);

   PrimitiveSink putShort(short var1);

   PrimitiveSink putString(CharSequence var1, Charset var2);

   PrimitiveSink putUnencodedChars(CharSequence var1);
}
