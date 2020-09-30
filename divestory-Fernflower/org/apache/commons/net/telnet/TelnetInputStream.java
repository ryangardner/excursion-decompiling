package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

final class TelnetInputStream extends BufferedInputStream implements Runnable {
   private static final int EOF = -1;
   private static final int WOULD_BLOCK = -2;
   static final int _STATE_CR = 8;
   static final int _STATE_DATA = 0;
   static final int _STATE_DO = 4;
   static final int _STATE_DONT = 5;
   static final int _STATE_IAC = 1;
   static final int _STATE_IAC_SB = 9;
   static final int _STATE_SB = 6;
   static final int _STATE_SE = 7;
   static final int _STATE_WILL = 2;
   static final int _STATE_WONT = 3;
   private int __bytesAvailable;
   private final TelnetClient __client;
   private boolean __hasReachedEOF;
   private IOException __ioException;
   private volatile boolean __isClosed;
   private final int[] __queue;
   private int __queueHead;
   private int __queueTail;
   private boolean __readIsWaiting;
   private int __receiveState;
   private final int[] __suboption;
   private int __suboption_count;
   private final Thread __thread;
   private volatile boolean __threaded;

   TelnetInputStream(InputStream var1, TelnetClient var2) {
      this(var1, var2, true);
   }

   TelnetInputStream(InputStream var1, TelnetClient var2, boolean var3) {
      super(var1);
      this.__suboption = new int[512];
      this.__suboption_count = 0;
      this.__client = var2;
      this.__receiveState = 0;
      this.__isClosed = true;
      this.__hasReachedEOF = false;
      this.__queue = new int[2049];
      this.__queueHead = 0;
      this.__queueTail = 0;
      this.__bytesAvailable = 0;
      this.__ioException = null;
      this.__readIsWaiting = false;
      this.__threaded = false;
      if (var3) {
         this.__thread = new Thread(this);
      } else {
         this.__thread = null;
      }

   }

   private boolean __processChar(int param1) throws InterruptedException {
      // $FF: Couldn't be decompiled
   }

   private int __read(boolean param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   void _start() {
      if (this.__thread != null) {
         this.__isClosed = false;
         int var1 = Thread.currentThread().getPriority() + 1;
         int var2 = var1;
         if (var1 > 10) {
            var2 = 10;
         }

         this.__thread.setPriority(var2);
         this.__thread.setDaemon(true);
         this.__thread.start();
         this.__threaded = true;
      }
   }

   public int available() throws IOException {
      int[] var1 = this.__queue;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label137: {
         int var2;
         try {
            if (this.__threaded) {
               var2 = this.__bytesAvailable;
               return var2;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label137;
         }

         int var3;
         try {
            var3 = this.__bytesAvailable;
            var2 = super.available();
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label137;
         }

         return var3 + var2;
      }

      while(true) {
         Throwable var4 = var10000;

         try {
            throw var4;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }

   public void close() throws IOException {
      super.close();
      int[] var1 = this.__queue;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label130: {
         try {
            this.__hasReachedEOF = true;
            this.__isClosed = true;
            if (this.__thread != null && this.__thread.isAlive()) {
               this.__thread.interrupt();
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label130;
         }

         label127:
         try {
            this.__queue.notifyAll();
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label127;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (var3 < 1) {
         return 0;
      } else {
         int[] var4 = this.__queue;
         synchronized(var4){}
         int var5 = var3;

         label220: {
            Throwable var10000;
            boolean var10001;
            label213: {
               try {
                  if (var3 > this.__bytesAvailable) {
                     var5 = this.__bytesAvailable;
                  }
               } catch (Throwable var19) {
                  var10000 = var19;
                  var10001 = false;
                  break label213;
               }

               label210:
               try {
                  break label220;
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label210;
               }
            }

            while(true) {
               Throwable var20 = var10000;

               try {
                  throw var20;
               } catch (Throwable var17) {
                  var10000 = var17;
                  var10001 = false;
                  continue;
               }
            }
         }

         int var6 = this.read();
         if (var6 == -1) {
            return -1;
         } else {
            int var7 = var5;
            var5 = var2;
            var3 = var6;

            while(true) {
               var6 = var5 + 1;
               var1[var5] = (byte)((byte)var3);
               --var7;
               if (var7 <= 0) {
                  break;
               }

               var3 = this.read();
               if (var3 == -1) {
                  break;
               }

               var5 = var6;
            }

            return var6 - var2;
         }
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
