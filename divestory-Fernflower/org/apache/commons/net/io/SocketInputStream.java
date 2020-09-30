package org.apache.commons.net.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketInputStream extends FilterInputStream {
   private final Socket __socket;

   public SocketInputStream(Socket var1, InputStream var2) {
      super(var2);
      this.__socket = var1;
   }

   public void close() throws IOException {
      super.close();
      this.__socket.close();
   }
}
