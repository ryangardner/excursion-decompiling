package com.sun.mail.util;

import java.io.OutputStream;

public class BEncoderStream extends BASE64EncoderStream {
   public BEncoderStream(OutputStream var1) {
      super(var1, Integer.MAX_VALUE);
   }

   public static int encodedLength(byte[] var0) {
      return (var0.length + 2) / 3 * 4;
   }
}
