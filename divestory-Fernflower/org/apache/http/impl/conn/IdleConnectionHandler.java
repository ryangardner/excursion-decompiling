package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

@Deprecated
public class IdleConnectionHandler {
   private final Map<HttpConnection, IdleConnectionHandler.TimeValues> connectionToTimes = new HashMap();
   private final Log log = LogFactory.getLog(this.getClass());

   public void add(HttpConnection var1, long var2, TimeUnit var4) {
      long var5 = System.currentTimeMillis();
      if (this.log.isDebugEnabled()) {
         Log var7 = this.log;
         StringBuilder var8 = new StringBuilder();
         var8.append("Adding connection at: ");
         var8.append(var5);
         var7.debug(var8.toString());
      }

      this.connectionToTimes.put(var1, new IdleConnectionHandler.TimeValues(var5, var2, var4));
   }

   public void closeExpiredConnections() {
      long var1 = System.currentTimeMillis();
      if (this.log.isDebugEnabled()) {
         Log var3 = this.log;
         StringBuilder var4 = new StringBuilder();
         var4.append("Checking for expired connections, now: ");
         var4.append(var1);
         var3.debug(var4.toString());
      }

      Iterator var10 = this.connectionToTimes.entrySet().iterator();

      while(var10.hasNext()) {
         Entry var5 = (Entry)var10.next();
         HttpConnection var9 = (HttpConnection)var5.getKey();
         IdleConnectionHandler.TimeValues var6 = (IdleConnectionHandler.TimeValues)var5.getValue();
         if (var6.timeExpires <= var1) {
            if (this.log.isDebugEnabled()) {
               Log var7 = this.log;
               StringBuilder var11 = new StringBuilder();
               var11.append("Closing connection, expired @: ");
               var11.append(var6.timeExpires);
               var7.debug(var11.toString());
            }

            try {
               var9.close();
            } catch (IOException var8) {
               this.log.debug("I/O error closing connection", var8);
            }
         }
      }

   }

   public void closeIdleConnections(long var1) {
      var1 = System.currentTimeMillis() - var1;
      if (this.log.isDebugEnabled()) {
         Log var3 = this.log;
         StringBuilder var4 = new StringBuilder();
         var4.append("Checking for connections, idle timeout: ");
         var4.append(var1);
         var3.debug(var4.toString());
      }

      Iterator var10 = this.connectionToTimes.entrySet().iterator();

      while(var10.hasNext()) {
         Entry var5 = (Entry)var10.next();
         HttpConnection var11 = (HttpConnection)var5.getKey();
         long var6 = ((IdleConnectionHandler.TimeValues)var5.getValue()).timeAdded;
         if (var6 <= var1) {
            if (this.log.isDebugEnabled()) {
               Log var12 = this.log;
               StringBuilder var8 = new StringBuilder();
               var8.append("Closing idle connection, connection time: ");
               var8.append(var6);
               var12.debug(var8.toString());
            }

            try {
               var11.close();
            } catch (IOException var9) {
               this.log.debug("I/O error closing connection", var9);
            }
         }
      }

   }

   public boolean remove(HttpConnection var1) {
      IdleConnectionHandler.TimeValues var3 = (IdleConnectionHandler.TimeValues)this.connectionToTimes.remove(var1);
      boolean var2 = true;
      if (var3 == null) {
         this.log.warn("Removing a connection that never existed!");
         return true;
      } else {
         if (System.currentTimeMillis() > var3.timeExpires) {
            var2 = false;
         }

         return var2;
      }
   }

   public void removeAll() {
      this.connectionToTimes.clear();
   }

   private static class TimeValues {
      private final long timeAdded;
      private final long timeExpires;

      TimeValues(long var1, long var3, TimeUnit var5) {
         this.timeAdded = var1;
         if (var3 > 0L) {
            this.timeExpires = var1 + var5.toMillis(var3);
         } else {
            this.timeExpires = Long.MAX_VALUE;
         }

      }
   }
}
