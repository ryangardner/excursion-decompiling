package com.fasterxml.jackson.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface SerializableString {
   int appendQuoted(char[] var1, int var2);

   int appendQuotedUTF8(byte[] var1, int var2);

   int appendUnquoted(char[] var1, int var2);

   int appendUnquotedUTF8(byte[] var1, int var2);

   char[] asQuotedChars();

   byte[] asQuotedUTF8();

   byte[] asUnquotedUTF8();

   int charLength();

   String getValue();

   int putQuotedUTF8(ByteBuffer var1) throws IOException;

   int putUnquotedUTF8(ByteBuffer var1) throws IOException;

   int writeQuotedUTF8(OutputStream var1) throws IOException;

   int writeUnquotedUTF8(OutputStream var1) throws IOException;
}
