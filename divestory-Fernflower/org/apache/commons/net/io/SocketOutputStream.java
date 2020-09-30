package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOutputStream extends FilterOutputStream {
   private final Socket __socket;

   public SocketOutputStream(Socket var1, OutputStream var2) {
      super(var2);
      this.__socket = var1;
   }

   public void close() throws IOException {
      super.close();
      this.__socket.close();
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }
}
