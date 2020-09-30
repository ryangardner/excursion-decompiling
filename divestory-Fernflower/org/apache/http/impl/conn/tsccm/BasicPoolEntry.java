package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;

public class BasicPoolEntry extends AbstractPoolEntry {
   private final long created;
   private long expiry;
   private long updated;
   private long validUntil;

   public BasicPoolEntry(ClientConnectionOperator var1, HttpRoute var2) {
      this(var1, var2, -1L, TimeUnit.MILLISECONDS);
   }

   public BasicPoolEntry(ClientConnectionOperator var1, HttpRoute var2, long var3, TimeUnit var5) {
      super(var1, var2);
      if (var2 != null) {
         long var6 = System.currentTimeMillis();
         this.created = var6;
         if (var3 > 0L) {
            this.validUntil = var6 + var5.toMillis(var3);
         } else {
            this.validUntil = Long.MAX_VALUE;
         }

         this.expiry = this.validUntil;
      } else {
         throw new IllegalArgumentException("HTTP route may not be null");
      }
   }

   @Deprecated
   public BasicPoolEntry(ClientConnectionOperator var1, HttpRoute var2, ReferenceQueue<Object> var3) {
      super(var1, var2);
      if (var2 != null) {
         this.created = System.currentTimeMillis();
         this.validUntil = Long.MAX_VALUE;
         this.expiry = Long.MAX_VALUE;
      } else {
         throw new IllegalArgumentException("HTTP route may not be null");
      }
   }

   protected final OperatedClientConnection getConnection() {
      return super.connection;
   }

   public long getCreated() {
      return this.created;
   }

   public long getExpiry() {
      return this.expiry;
   }

   protected final HttpRoute getPlannedRoute() {
      return super.route;
   }

   public long getUpdated() {
      return this.updated;
   }

   public long getValidUntil() {
      return this.validUntil;
   }

   @Deprecated
   protected final BasicPoolEntryRef getWeakRef() {
      return null;
   }

   public boolean isExpired(long var1) {
      boolean var3;
      if (var1 >= this.expiry) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   protected void shutdownEntry() {
      super.shutdownEntry();
   }

   public void updateExpiry(long var1, TimeUnit var3) {
      long var4 = System.currentTimeMillis();
      this.updated = var4;
      if (var1 > 0L) {
         var1 = var4 + var3.toMillis(var1);
      } else {
         var1 = Long.MAX_VALUE;
      }

      this.expiry = Math.min(this.validUntil, var1);
   }
}
