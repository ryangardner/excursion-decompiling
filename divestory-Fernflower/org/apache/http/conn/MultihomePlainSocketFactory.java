package org.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@Deprecated
public final class MultihomePlainSocketFactory implements SocketFactory {
   private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();

   private MultihomePlainSocketFactory() {
   }

   public static MultihomePlainSocketFactory getSocketFactory() {
      return DEFAULT_FACTORY;
   }

   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException {
      if (var2 == null) {
         throw new IllegalArgumentException("Target host may not be null.");
      } else if (var6 != null) {
         Socket var7 = var1;
         if (var1 == null) {
            var7 = this.createSocket();
         }

         if (var4 != null || var5 > 0) {
            int var8 = var5;
            if (var5 < 0) {
               var8 = 0;
            }

            var7.bind(new InetSocketAddress(var4, var8));
         }

         var5 = HttpConnectionParams.getConnectionTimeout(var6);
         InetAddress[] var11 = InetAddress.getAllByName(var2);
         ArrayList var13 = new ArrayList(var11.length);
         var13.addAll(Arrays.asList(var11));
         Collections.shuffle(var13);
         IOException var12 = null;
         Iterator var15 = var13.iterator();

         while(var15.hasNext()) {
            var4 = (InetAddress)var15.next();

            try {
               InetSocketAddress var16 = new InetSocketAddress(var4, var3);
               var7.connect(var16, var5);
               break;
            } catch (SocketTimeoutException var9) {
               StringBuilder var14 = new StringBuilder();
               var14.append("Connect to ");
               var14.append(var4);
               var14.append(" timed out");
               throw new ConnectTimeoutException(var14.toString());
            } catch (IOException var10) {
               var12 = var10;
               var7 = new Socket();
            }
         }

         if (var12 == null) {
            return var7;
         } else {
            throw var12;
         }
      } else {
         throw new IllegalArgumentException("Parameters may not be null.");
      }
   }

   public Socket createSocket() {
      return new Socket();
   }

   public final boolean isSecure(Socket var1) throws IllegalArgumentException {
      if (var1 != null) {
         if (var1.getClass() == Socket.class) {
            if (!var1.isClosed()) {
               return false;
            } else {
               throw new IllegalArgumentException("Socket is closed.");
            }
         } else {
            throw new IllegalArgumentException("Socket not created by this factory.");
         }
      } else {
         throw new IllegalArgumentException("Socket may not be null.");
      }
   }
}
