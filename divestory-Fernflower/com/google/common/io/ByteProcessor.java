package com.google.common.io;

import com.google.errorprone.annotations.DoNotMock;
import java.io.IOException;

@DoNotMock("Implement it normally")
public interface ByteProcessor<T> {
   T getResult();

   boolean processBytes(byte[] var1, int var2, int var3) throws IOException;
}
