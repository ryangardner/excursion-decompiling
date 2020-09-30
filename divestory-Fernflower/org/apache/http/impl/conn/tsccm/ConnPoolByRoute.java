package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

public class ConnPoolByRoute extends AbstractConnPool {
   protected final ConnPerRoute connPerRoute;
   private final long connTTL;
   private final TimeUnit connTTLTimeUnit;
   protected final Queue<BasicPoolEntry> freeConnections;
   protected final Set<BasicPoolEntry> leasedConnections;
   private final Log log;
   protected volatile int maxTotalConnections;
   protected volatile int numConnections;
   protected final ClientConnectionOperator operator;
   private final Lock poolLock;
   protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
   protected volatile boolean shutdown;
   protected final Queue<WaitingThread> waitingThreads;

   public ConnPoolByRoute(ClientConnectionOperator var1, ConnPerRoute var2, int var3) {
      this(var1, var2, var3, -1L, TimeUnit.MILLISECONDS);
   }

   public ConnPoolByRoute(ClientConnectionOperator var1, ConnPerRoute var2, int var3, long var4, TimeUnit var6) {
      this.log = LogFactory.getLog(this.getClass());
      if (var1 != null) {
         if (var2 != null) {
            this.poolLock = super.poolLock;
            this.leasedConnections = super.leasedConnections;
            this.operator = var1;
            this.connPerRoute = var2;
            this.maxTotalConnections = var3;
            this.freeConnections = this.createFreeConnQueue();
            this.waitingThreads = this.createWaitingThreadQueue();
            this.routeToPool = this.createRouteToPoolMap();
            this.connTTL = var4;
            this.connTTLTimeUnit = var6;
         } else {
            throw new IllegalArgumentException("Connections per route may not be null");
         }
      } else {
         throw new IllegalArgumentException("Connection operator may not be null");
      }
   }

   @Deprecated
   public ConnPoolByRoute(ClientConnectionOperator var1, HttpParams var2) {
      this(var1, ConnManagerParams.getMaxConnectionsPerRoute(var2), ConnManagerParams.getMaxTotalConnections(var2));
   }

   private void closeConnection(BasicPoolEntry var1) {
      OperatedClientConnection var3 = var1.getConnection();
      if (var3 != null) {
         try {
            var3.close();
         } catch (IOException var2) {
            this.log.debug("I/O error closing connection", var2);
         }
      }

   }

   public void closeExpiredConnections() {
      this.log.debug("Closing expired connections");
      long var1 = System.currentTimeMillis();
      this.poolLock.lock();

      label163: {
         Throwable var10000;
         label162: {
            Iterator var3;
            boolean var10001;
            try {
               var3 = this.freeConnections.iterator();
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break label162;
            }

            while(true) {
               BasicPoolEntry var4;
               try {
                  do {
                     if (!var3.hasNext()) {
                        break label163;
                     }

                     var4 = (BasicPoolEntry)var3.next();
                  } while(!var4.isExpired(var1));

                  if (this.log.isDebugEnabled()) {
                     Log var5 = this.log;
                     StringBuilder var6 = new StringBuilder();
                     var6.append("Closing connection expired @ ");
                     Date var7 = new Date(var4.getExpiry());
                     var6.append(var7);
                     var5.debug(var6.toString());
                  }
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  break;
               }

               try {
                  var3.remove();
                  this.deleteEntry(var4);
               } catch (Throwable var17) {
                  var10000 = var17;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var20 = var10000;
         this.poolLock.unlock();
         throw var20;
      }

      this.poolLock.unlock();
   }

   public void closeIdleConnections(long var1, TimeUnit var3) {
      if (var3 != null) {
         long var4 = var1;
         if (var1 < 0L) {
            var4 = 0L;
         }

         if (this.log.isDebugEnabled()) {
            Log var6 = this.log;
            StringBuilder var7 = new StringBuilder();
            var7.append("Closing connections idle longer than ");
            var7.append(var4);
            var7.append(" ");
            var7.append(var3);
            var6.debug(var7.toString());
         }

         var1 = System.currentTimeMillis();
         var4 = var3.toMillis(var4);
         this.poolLock.lock();

         label206: {
            Throwable var10000;
            label205: {
               Iterator var8;
               boolean var10001;
               try {
                  var8 = this.freeConnections.iterator();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label205;
               }

               while(true) {
                  BasicPoolEntry var24;
                  try {
                     do {
                        if (!var8.hasNext()) {
                           break label206;
                        }

                        var24 = (BasicPoolEntry)var8.next();
                     } while(var24.getUpdated() > var1 - var4);

                     if (this.log.isDebugEnabled()) {
                        Log var25 = this.log;
                        StringBuilder var9 = new StringBuilder();
                        var9.append("Closing connection last used @ ");
                        Date var22 = new Date(var24.getUpdated());
                        var9.append(var22);
                        var25.debug(var9.toString());
                     }
                  } catch (Throwable var20) {
                     var10000 = var20;
                     var10001 = false;
                     break;
                  }

                  try {
                     var8.remove();
                     this.deleteEntry(var24);
                  } catch (Throwable var19) {
                     var10000 = var19;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var23 = var10000;
            this.poolLock.unlock();
            throw var23;
         }

         this.poolLock.unlock();
      } else {
         throw new IllegalArgumentException("Time unit must not be null.");
      }
   }

   protected BasicPoolEntry createEntry(RouteSpecificPool var1, ClientConnectionOperator var2) {
      if (this.log.isDebugEnabled()) {
         Log var3 = this.log;
         StringBuilder var4 = new StringBuilder();
         var4.append("Creating new connection [");
         var4.append(var1.getRoute());
         var4.append("]");
         var3.debug(var4.toString());
      }

      BasicPoolEntry var7 = new BasicPoolEntry(var2, var1.getRoute(), this.connTTL, this.connTTLTimeUnit);
      this.poolLock.lock();

      try {
         var1.createdEntry(var7);
         ++this.numConnections;
         this.leasedConnections.add(var7);
      } finally {
         this.poolLock.unlock();
      }

      return var7;
   }

   protected Queue<BasicPoolEntry> createFreeConnQueue() {
      return new LinkedList();
   }

   protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
      return new HashMap();
   }

   protected Queue<WaitingThread> createWaitingThreadQueue() {
      return new LinkedList();
   }

   public void deleteClosedConnections() {
      this.poolLock.lock();

      label93: {
         Throwable var10000;
         label92: {
            Iterator var1;
            boolean var10001;
            try {
               var1 = this.freeConnections.iterator();
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label92;
            }

            while(true) {
               try {
                  BasicPoolEntry var2;
                  do {
                     if (!var1.hasNext()) {
                        break label93;
                     }

                     var2 = (BasicPoolEntry)var1.next();
                  } while(var2.getConnection().isOpen());

                  var1.remove();
                  this.deleteEntry(var2);
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var9 = var10000;
         this.poolLock.unlock();
         throw var9;
      }

      this.poolLock.unlock();
   }

   protected void deleteEntry(BasicPoolEntry var1) {
      HttpRoute var2 = var1.getPlannedRoute();
      if (this.log.isDebugEnabled()) {
         Log var3 = this.log;
         StringBuilder var4 = new StringBuilder();
         var4.append("Deleting connection [");
         var4.append(var2);
         var4.append("][");
         var4.append(var1.getState());
         var4.append("]");
         var3.debug(var4.toString());
      }

      this.poolLock.lock();

      try {
         this.closeConnection(var1);
         RouteSpecificPool var7 = this.getRoutePool(var2, true);
         var7.deleteEntry(var1);
         --this.numConnections;
         if (var7.isUnused()) {
            this.routeToPool.remove(var2);
         }
      } finally {
         this.poolLock.unlock();
      }

   }

   protected void deleteLeastUsedEntry() {
      this.poolLock.lock();

      label114: {
         Throwable var10000;
         label113: {
            BasicPoolEntry var1;
            boolean var10001;
            try {
               var1 = (BasicPoolEntry)this.freeConnections.remove();
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label113;
            }

            if (var1 != null) {
               label107:
               try {
                  this.deleteEntry(var1);
               } catch (Throwable var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label107;
               }
            } else {
               label109:
               try {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("No free connection to delete");
                  }
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label109;
               }
            }
            break label114;
         }

         Throwable var14 = var10000;
         this.poolLock.unlock();
         throw var14;
      }

      this.poolLock.unlock();
   }

   public void freeEntry(BasicPoolEntry var1, boolean var2, long var3, TimeUnit var5) {
      HttpRoute var6 = var1.getPlannedRoute();
      StringBuilder var8;
      if (this.log.isDebugEnabled()) {
         Log var7 = this.log;
         var8 = new StringBuilder();
         var8.append("Releasing connection [");
         var8.append(var6);
         var8.append("][");
         var8.append(var1.getState());
         var8.append("]");
         var7.debug(var8.toString());
      }

      this.poolLock.lock();

      label631: {
         Throwable var10000;
         label637: {
            boolean var10001;
            try {
               if (this.shutdown) {
                  this.closeConnection(var1);
                  break label631;
               }
            } catch (Throwable var82) {
               var10000 = var82;
               var10001 = false;
               break label637;
            }

            RouteSpecificPool var84;
            try {
               this.leasedConnections.remove(var1);
               var84 = this.getRoutePool(var6, true);
            } catch (Throwable var81) {
               var10000 = var81;
               var10001 = false;
               break label637;
            }

            if (var2) {
               label639: {
                  try {
                     if (!this.log.isDebugEnabled()) {
                        break label639;
                     }
                  } catch (Throwable var80) {
                     var10000 = var80;
                     var10001 = false;
                     break label637;
                  }

                  String var85;
                  if (var3 > 0L) {
                     try {
                        var8 = new StringBuilder();
                        var8.append("for ");
                        var8.append(var3);
                        var8.append(" ");
                        var8.append(var5);
                        var85 = var8.toString();
                     } catch (Throwable var79) {
                        var10000 = var79;
                        var10001 = false;
                        break label637;
                     }
                  } else {
                     var85 = "indefinitely";
                  }

                  try {
                     Log var9 = this.log;
                     StringBuilder var10 = new StringBuilder();
                     var10.append("Pooling connection [");
                     var10.append(var6);
                     var10.append("][");
                     var10.append(var1.getState());
                     var10.append("]; keep alive ");
                     var10.append(var85);
                     var9.debug(var10.toString());
                  } catch (Throwable var78) {
                     var10000 = var78;
                     var10001 = false;
                     break label637;
                  }
               }

               try {
                  var84.freeEntry(var1);
                  var1.updateExpiry(var3, var5);
                  this.freeConnections.add(var1);
               } catch (Throwable var77) {
                  var10000 = var77;
                  var10001 = false;
                  break label637;
               }
            } else {
               try {
                  var84.dropEntry();
                  --this.numConnections;
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label637;
               }
            }

            try {
               this.notifyWaitingThread(var84);
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label637;
            }

            this.poolLock.unlock();
            return;
         }

         Throwable var83 = var10000;
         this.poolLock.unlock();
         throw var83;
      }

      this.poolLock.unlock();
   }

   public int getConnectionsInPool() {
      this.poolLock.lock();

      int var1;
      try {
         var1 = this.numConnections;
      } finally {
         this.poolLock.unlock();
      }

      return var1;
   }

   public int getConnectionsInPool(HttpRoute var1) {
      this.poolLock.lock();
      int var2 = 0;

      label71: {
         Throwable var10000;
         label75: {
            boolean var10001;
            RouteSpecificPool var9;
            try {
               var9 = this.getRoutePool(var1, false);
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label75;
            }

            if (var9 == null) {
               break label71;
            }

            label66:
            try {
               var2 = var9.getEntryCount();
               break label71;
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label66;
            }
         }

         Throwable var10 = var10000;
         this.poolLock.unlock();
         throw var10;
      }

      this.poolLock.unlock();
      return var2;
   }

   protected BasicPoolEntry getEntryBlocking(HttpRoute var1, Object var2, long var3, TimeUnit var5, WaitingThreadAborter var6) throws ConnectionPoolTimeoutException, InterruptedException {
      BasicPoolEntry var7 = null;
      Date var8;
      if (var3 > 0L) {
         var8 = new Date(System.currentTimeMillis() + var5.toMillis(var3));
      } else {
         var8 = null;
      }

      this.poolLock.lock();

      label2487: {
         Throwable var10000;
         label2491: {
            RouteSpecificPool var9;
            boolean var10001;
            try {
               var9 = this.getRoutePool(var1, true);
            } catch (Throwable var302) {
               var10000 = var302;
               var10001 = false;
               break label2491;
            }

            WaitingThread var10 = null;
            BasicPoolEntry var311 = var7;

            while(true) {
               var7 = var311;
               if (var311 != null) {
                  break label2487;
               }

               label2493: {
                  boolean var11;
                  try {
                     if (this.shutdown) {
                        break label2493;
                     }

                     var11 = this.log.isDebugEnabled();
                  } catch (Throwable var307) {
                     var10000 = var307;
                     var10001 = false;
                     break;
                  }

                  if (var11) {
                     try {
                        Log var314 = this.log;
                        StringBuilder var312 = new StringBuilder();
                        var312.append("[");
                        var312.append(var1);
                        var312.append("] total kept alive: ");
                        var312.append(this.freeConnections.size());
                        var312.append(", total issued: ");
                        var312.append(this.leasedConnections.size());
                        var312.append(", total allocated: ");
                        var312.append(this.numConnections);
                        var312.append(" out of ");
                        var312.append(this.maxTotalConnections);
                        var314.debug(var312.toString());
                     } catch (Throwable var301) {
                        var10000 = var301;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var7 = this.getFreeEntry(var9, var2);
                  } catch (Throwable var300) {
                     var10000 = var300;
                     var10001 = false;
                     break;
                  }

                  if (var7 != null) {
                     break label2487;
                  }

                  boolean var12;
                  label2472: {
                     label2471: {
                        try {
                           if (var9.getCapacity() > 0) {
                              break label2471;
                           }
                        } catch (Throwable var306) {
                           var10000 = var306;
                           var10001 = false;
                           break;
                        }

                        var12 = false;
                        break label2472;
                     }

                     var12 = true;
                  }

                  try {
                     var11 = this.log.isDebugEnabled();
                  } catch (Throwable var299) {
                     var10000 = var299;
                     var10001 = false;
                     break;
                  }

                  StringBuilder var13;
                  Log var313;
                  if (var11) {
                     try {
                        var313 = this.log;
                        var13 = new StringBuilder();
                        var13.append("Available capacity: ");
                        var13.append(var9.getCapacity());
                        var13.append(" out of ");
                        var13.append(var9.getMaxEntries());
                        var13.append(" [");
                        var13.append(var1);
                        var13.append("][");
                        var13.append(var2);
                        var13.append("]");
                        var313.debug(var13.toString());
                     } catch (Throwable var298) {
                        var10000 = var298;
                        var10001 = false;
                        break;
                     }
                  }

                  if (var12) {
                     try {
                        if (this.numConnections < this.maxTotalConnections) {
                           var311 = this.createEntry(var9, this.operator);
                           continue;
                        }
                     } catch (Throwable var304) {
                        var10000 = var304;
                        var10001 = false;
                        break;
                     }
                  }

                  if (var12) {
                     try {
                        if (!this.freeConnections.isEmpty()) {
                           this.deleteLeastUsedEntry();
                           var9 = this.getRoutePool(var1, true);
                           var311 = this.createEntry(var9, this.operator);
                           continue;
                        }
                     } catch (Throwable var303) {
                        var10000 = var303;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     if (this.log.isDebugEnabled()) {
                        var313 = this.log;
                        var13 = new StringBuilder();
                        var13.append("Need to wait for connection [");
                        var13.append(var1);
                        var13.append("][");
                        var13.append(var2);
                        var13.append("]");
                        var313.debug(var13.toString());
                     }
                  } catch (Throwable var297) {
                     var10000 = var297;
                     var10001 = false;
                     break;
                  }

                  WaitingThread var315 = var10;
                  if (var10 == null) {
                     try {
                        var315 = this.newWaitingThread(this.poolLock.newCondition(), var9);
                        var6.setWaitingThread(var315);
                     } catch (Throwable var296) {
                        var10000 = var296;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var9.queueThread(var315);
                     this.waitingThreads.add(var315);
                     var11 = var315.await(var8);
                  } finally {
                     try {
                        var9.removeThread(var315);
                        this.waitingThreads.remove(var315);
                     } catch (Throwable var292) {
                        var10000 = var292;
                        var10001 = false;
                        break;
                     }
                  }

                  var311 = var7;
                  var10 = var315;
                  if (var11) {
                     continue;
                  }

                  var311 = var7;
                  var10 = var315;
                  if (var8 == null) {
                     continue;
                  }

                  label2458: {
                     try {
                        if (var8.getTime() > System.currentTimeMillis()) {
                           break label2458;
                        }
                     } catch (Throwable var305) {
                        var10000 = var305;
                        var10001 = false;
                        break;
                     }

                     try {
                        ConnectionPoolTimeoutException var308 = new ConnectionPoolTimeoutException("Timeout waiting for connection");
                        throw var308;
                     } catch (Throwable var293) {
                        var10000 = var293;
                        var10001 = false;
                        break;
                     }
                  }

                  var311 = var7;
                  var10 = var315;
                  continue;
               }

               try {
                  IllegalStateException var310 = new IllegalStateException("Connection pool shut down");
                  throw var310;
               } catch (Throwable var294) {
                  var10000 = var294;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var309 = var10000;
         this.poolLock.unlock();
         throw var309;
      }

      this.poolLock.unlock();
      return var7;
   }

   protected BasicPoolEntry getFreeEntry(RouteSpecificPool var1, Object var2) {
      this.poolLock.lock();
      boolean var3 = false;
      BasicPoolEntry var4 = null;

      while(!var3) {
         label449: {
            Throwable var10000;
            label448: {
               boolean var10001;
               try {
                  var4 = var1.allocEntry(var2);
               } catch (Throwable var48) {
                  var10000 = var48;
                  var10001 = false;
                  break label448;
               }

               Log var5;
               StringBuilder var6;
               if (var4 != null) {
                  label457: {
                     try {
                        if (this.log.isDebugEnabled()) {
                           var5 = this.log;
                           var6 = new StringBuilder();
                           var6.append("Getting free connection [");
                           var6.append(var1.getRoute());
                           var6.append("][");
                           var6.append(var2);
                           var6.append("]");
                           var5.debug(var6.toString());
                        }
                     } catch (Throwable var46) {
                        var10000 = var46;
                        var10001 = false;
                        break label457;
                     }

                     label438: {
                        try {
                           this.freeConnections.remove(var4);
                           if (!var4.isExpired(System.currentTimeMillis())) {
                              break label438;
                           }

                           if (this.log.isDebugEnabled()) {
                              var5 = this.log;
                              var6 = new StringBuilder();
                              var6.append("Closing expired free connection [");
                              var6.append(var1.getRoute());
                              var6.append("][");
                              var6.append(var2);
                              var6.append("]");
                              var5.debug(var6.toString());
                           }
                        } catch (Throwable var45) {
                           var10000 = var45;
                           var10001 = false;
                           break label457;
                        }

                        try {
                           this.closeConnection(var4);
                           var1.dropEntry();
                           --this.numConnections;
                           continue;
                        } catch (Throwable var43) {
                           var10000 = var43;
                           var10001 = false;
                           break label457;
                        }
                     }

                     label432:
                     try {
                        this.leasedConnections.add(var4);
                     } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break label432;
                     }
                  }
               } else {
                  label444:
                  try {
                     if (this.log.isDebugEnabled()) {
                        var5 = this.log;
                        var6 = new StringBuilder();
                        var6.append("No free connections [");
                        var6.append(var1.getRoute());
                        var6.append("][");
                        var6.append(var2);
                        var6.append("]");
                        var5.debug(var6.toString());
                     }
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break label444;
                  }
               }
               break label449;
            }

            Throwable var49 = var10000;
            this.poolLock.unlock();
            throw var49;
         }

         var3 = true;
      }

      this.poolLock.unlock();
      return var4;
   }

   protected Lock getLock() {
      return this.poolLock;
   }

   public int getMaxTotalConnections() {
      return this.maxTotalConnections;
   }

   protected RouteSpecificPool getRoutePool(HttpRoute var1, boolean var2) {
      this.poolLock.lock();

      RouteSpecificPool var4;
      label80: {
         Throwable var10000;
         label84: {
            boolean var10001;
            RouteSpecificPool var3;
            try {
               var3 = (RouteSpecificPool)this.routeToPool.get(var1);
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break label84;
            }

            var4 = var3;
            if (var3 != null) {
               break label80;
            }

            var4 = var3;
            if (!var2) {
               break label80;
            }

            label75:
            try {
               var4 = this.newRouteSpecificPool(var1);
               this.routeToPool.put(var1, var4);
               break label80;
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label75;
            }
         }

         Throwable var11 = var10000;
         this.poolLock.unlock();
         throw var11;
      }

      this.poolLock.unlock();
      return var4;
   }

   protected void handleLostEntry(HttpRoute var1) {
      this.poolLock.lock();

      try {
         RouteSpecificPool var2 = this.getRoutePool(var1, true);
         var2.dropEntry();
         if (var2.isUnused()) {
            this.routeToPool.remove(var1);
         }

         --this.numConnections;
         this.notifyWaitingThread(var2);
      } finally {
         this.poolLock.unlock();
      }

   }

   protected RouteSpecificPool newRouteSpecificPool(HttpRoute var1) {
      return new RouteSpecificPool(var1, this.connPerRoute);
   }

   protected WaitingThread newWaitingThread(Condition var1, RouteSpecificPool var2) {
      return new WaitingThread(var1, var2);
   }

   protected void notifyWaitingThread(RouteSpecificPool var1) {
      label486: {
         Throwable var10000;
         label488: {
            WaitingThread var46;
            boolean var10001;
            label484: {
               label489: {
                  this.poolLock.lock();
                  if (var1 != null) {
                     try {
                        if (var1.hasThread()) {
                           if (this.log.isDebugEnabled()) {
                              Log var2 = this.log;
                              StringBuilder var3 = new StringBuilder();
                              var3.append("Notifying thread waiting on pool [");
                              var3.append(var1.getRoute());
                              var3.append("]");
                              var2.debug(var3.toString());
                           }
                           break label489;
                        }
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label488;
                     }
                  }

                  label477: {
                     try {
                        if (this.waitingThreads.isEmpty()) {
                           break label477;
                        }

                        if (this.log.isDebugEnabled()) {
                           this.log.debug("Notifying thread waiting on any pool");
                        }
                     } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break label488;
                     }

                     try {
                        var46 = (WaitingThread)this.waitingThreads.remove();
                        break label484;
                     } catch (Throwable var41) {
                        var10000 = var41;
                        var10001 = false;
                        break label488;
                     }
                  }

                  try {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("Notifying no-one, there are no waiting threads");
                     }
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break label488;
                  }

                  var46 = null;
                  break label484;
               }

               try {
                  var46 = var1.nextThread();
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break label488;
               }
            }

            if (var46 == null) {
               break label486;
            }

            label460:
            try {
               var46.wakeup();
               break label486;
            } catch (Throwable var40) {
               var10000 = var40;
               var10001 = false;
               break label460;
            }
         }

         Throwable var47 = var10000;
         this.poolLock.unlock();
         throw var47;
      }

      this.poolLock.unlock();
   }

   public PoolEntryRequest requestPoolEntry(final HttpRoute var1, final Object var2) {
      return new PoolEntryRequest(new WaitingThreadAborter()) {
         // $FF: synthetic field
         final WaitingThreadAborter val$aborter;

         {
            this.val$aborter = var2x;
         }

         public void abortRequest() {
            ConnPoolByRoute.this.poolLock.lock();

            try {
               this.val$aborter.abort();
            } finally {
               ConnPoolByRoute.this.poolLock.unlock();
            }

         }

         public BasicPoolEntry getPoolEntry(long var1x, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException {
            return ConnPoolByRoute.this.getEntryBlocking(var1, var2, var1x, var3, this.val$aborter);
         }
      };
   }

   public void setMaxTotalConnections(int var1) {
      this.poolLock.lock();

      try {
         this.maxTotalConnections = var1;
      } finally {
         this.poolLock.unlock();
      }

   }

   public void shutdown() {
      this.poolLock.lock();

      Throwable var10000;
      label838: {
         boolean var1;
         boolean var10001;
         try {
            var1 = this.shutdown;
         } catch (Throwable var95) {
            var10000 = var95;
            var10001 = false;
            break label838;
         }

         if (var1) {
            this.poolLock.unlock();
            return;
         }

         Iterator var2;
         try {
            this.shutdown = true;
            var2 = this.leasedConnections.iterator();
         } catch (Throwable var93) {
            var10000 = var93;
            var10001 = false;
            break label838;
         }

         BasicPoolEntry var3;
         while(true) {
            try {
               if (!var2.hasNext()) {
                  break;
               }

               var3 = (BasicPoolEntry)var2.next();
               var2.remove();
               this.closeConnection(var3);
            } catch (Throwable var94) {
               var10000 = var94;
               var10001 = false;
               break label838;
            }
         }

         Iterator var4;
         try {
            var4 = this.freeConnections.iterator();
         } catch (Throwable var91) {
            var10000 = var91;
            var10001 = false;
            break label838;
         }

         while(true) {
            try {
               if (!var4.hasNext()) {
                  break;
               }

               var3 = (BasicPoolEntry)var4.next();
               var4.remove();
               if (this.log.isDebugEnabled()) {
                  Log var5 = this.log;
                  StringBuilder var96 = new StringBuilder();
                  var96.append("Closing connection [");
                  var96.append(var3.getPlannedRoute());
                  var96.append("][");
                  var96.append(var3.getState());
                  var96.append("]");
                  var5.debug(var96.toString());
               }
            } catch (Throwable var92) {
               var10000 = var92;
               var10001 = false;
               break label838;
            }

            try {
               this.closeConnection(var3);
            } catch (Throwable var90) {
               var10000 = var90;
               var10001 = false;
               break label838;
            }
         }

         try {
            var2 = this.waitingThreads.iterator();
         } catch (Throwable var88) {
            var10000 = var88;
            var10001 = false;
            break label838;
         }

         while(true) {
            try {
               if (!var2.hasNext()) {
                  break;
               }

               WaitingThread var98 = (WaitingThread)var2.next();
               var2.remove();
               var98.wakeup();
            } catch (Throwable var89) {
               var10000 = var89;
               var10001 = false;
               break label838;
            }
         }

         try {
            this.routeToPool.clear();
         } catch (Throwable var87) {
            var10000 = var87;
            var10001 = false;
            break label838;
         }

         this.poolLock.unlock();
         return;
      }

      Throwable var97 = var10000;
      this.poolLock.unlock();
      throw var97;
   }
}
