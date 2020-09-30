package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.LangUtils;

public class RouteSpecificPool {
   protected final ConnPerRoute connPerRoute;
   protected final LinkedList<BasicPoolEntry> freeEntries;
   private final Log log = LogFactory.getLog(this.getClass());
   @Deprecated
   protected final int maxEntries;
   protected int numEntries;
   protected final HttpRoute route;
   protected final Queue<WaitingThread> waitingThreads;

   @Deprecated
   public RouteSpecificPool(HttpRoute var1, int var2) {
      this.route = var1;
      this.maxEntries = var2;
      this.connPerRoute = new ConnPerRoute() {
         public int getMaxForRoute(HttpRoute var1) {
            return RouteSpecificPool.this.maxEntries;
         }
      };
      this.freeEntries = new LinkedList();
      this.waitingThreads = new LinkedList();
      this.numEntries = 0;
   }

   public RouteSpecificPool(HttpRoute var1, ConnPerRoute var2) {
      this.route = var1;
      this.connPerRoute = var2;
      this.maxEntries = var2.getMaxForRoute(var1);
      this.freeEntries = new LinkedList();
      this.waitingThreads = new LinkedList();
      this.numEntries = 0;
   }

   public BasicPoolEntry allocEntry(Object var1) {
      if (!this.freeEntries.isEmpty()) {
         LinkedList var2 = this.freeEntries;
         ListIterator var6 = var2.listIterator(var2.size());

         while(var6.hasPrevious()) {
            BasicPoolEntry var3 = (BasicPoolEntry)var6.previous();
            if (var3.getState() == null || LangUtils.equals(var1, var3.getState())) {
               var6.remove();
               return var3;
            }
         }
      }

      if (this.getCapacity() == 0 && !this.freeEntries.isEmpty()) {
         BasicPoolEntry var5 = (BasicPoolEntry)this.freeEntries.remove();
         var5.shutdownEntry();
         OperatedClientConnection var7 = var5.getConnection();

         try {
            var7.close();
         } catch (IOException var4) {
            this.log.debug("I/O error closing connection", var4);
         }

         return var5;
      } else {
         return null;
      }
   }

   public void createdEntry(BasicPoolEntry var1) {
      if (this.route.equals(var1.getPlannedRoute())) {
         ++this.numEntries;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Entry not planned for this pool.\npool: ");
         var2.append(this.route);
         var2.append("\nplan: ");
         var2.append(var1.getPlannedRoute());
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public boolean deleteEntry(BasicPoolEntry var1) {
      boolean var2 = this.freeEntries.remove(var1);
      if (var2) {
         --this.numEntries;
      }

      return var2;
   }

   public void dropEntry() {
      int var1 = this.numEntries;
      if (var1 >= 1) {
         this.numEntries = var1 - 1;
      } else {
         throw new IllegalStateException("There is no entry that could be dropped.");
      }
   }

   public void freeEntry(BasicPoolEntry var1) {
      int var2 = this.numEntries;
      StringBuilder var3;
      if (var2 >= 1) {
         if (var2 > this.freeEntries.size()) {
            this.freeEntries.add(var1);
         } else {
            var3 = new StringBuilder();
            var3.append("No entry allocated from this pool. ");
            var3.append(this.route);
            throw new IllegalStateException(var3.toString());
         }
      } else {
         var3 = new StringBuilder();
         var3.append("No entry created for this pool. ");
         var3.append(this.route);
         throw new IllegalStateException(var3.toString());
      }
   }

   public int getCapacity() {
      return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
   }

   public final int getEntryCount() {
      return this.numEntries;
   }

   public final int getMaxEntries() {
      return this.maxEntries;
   }

   public final HttpRoute getRoute() {
      return this.route;
   }

   public boolean hasThread() {
      return this.waitingThreads.isEmpty() ^ true;
   }

   public boolean isUnused() {
      int var1 = this.numEntries;
      boolean var2 = true;
      if (var1 >= 1 || !this.waitingThreads.isEmpty()) {
         var2 = false;
      }

      return var2;
   }

   public WaitingThread nextThread() {
      return (WaitingThread)this.waitingThreads.peek();
   }

   public void queueThread(WaitingThread var1) {
      if (var1 != null) {
         this.waitingThreads.add(var1);
      } else {
         throw new IllegalArgumentException("Waiting thread must not be null.");
      }
   }

   public void removeThread(WaitingThread var1) {
      if (var1 != null) {
         this.waitingThreads.remove(var1);
      }
   }
}
