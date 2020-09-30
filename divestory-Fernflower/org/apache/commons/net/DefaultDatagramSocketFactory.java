package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DefaultDatagramSocketFactory implements DatagramSocketFactory {
   public DatagramSocket createDatagramSocket() throws SocketException {
      return new DatagramSocket();
   }

   public DatagramSocket createDatagramSocket(int var1) throws SocketException {
      return new DatagramSocket(var1);
   }

   public DatagramSocket createDatagramSocket(int var1, InetAddress var2) throws SocketException {
      return new DatagramSocket(var1, var2);
   }
}
