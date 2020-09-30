package javax.mail;

import java.util.Vector;
import javax.mail.event.MailEvent;

class EventQueue implements Runnable {
   private EventQueue.QueueElement head = null;
   private Thread qThread;
   private EventQueue.QueueElement tail = null;

   public EventQueue() {
      Thread var1 = new Thread(this, "JavaMail-EventQueue");
      this.qThread = var1;
      var1.setDaemon(true);
      this.qThread.start();
   }

   private EventQueue.QueueElement dequeue() throws InterruptedException {
      synchronized(this){}

      Throwable var10000;
      while(true) {
         boolean var10001;
         label286: {
            EventQueue.QueueElement var1;
            EventQueue.QueueElement var2;
            try {
               if (this.tail == null) {
                  break label286;
               }

               var1 = this.tail;
               var2 = var1.prev;
               this.tail = var2;
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break;
            }

            if (var2 == null) {
               try {
                  this.head = null;
               } catch (Throwable var30) {
                  var10000 = var30;
                  var10001 = false;
                  break;
               }
            } else {
               try {
                  var2.next = null;
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  break;
               }
            }

            try {
               var1.next = null;
               var1.prev = null;
               return var1;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break;
            }
         }

         try {
            this.wait();
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break;
         }
      }

      Throwable var33 = var10000;
      throw var33;
   }

   public void enqueue(MailEvent var1, Vector var2) {
      synchronized(this){}

      Throwable var10000;
      label133: {
         boolean var10001;
         label126: {
            EventQueue.QueueElement var3;
            try {
               var3 = new EventQueue.QueueElement(var1, var2);
               if (this.head == null) {
                  this.head = var3;
                  this.tail = var3;
                  break label126;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label133;
            }

            try {
               var3.next = this.head;
               this.head.prev = var3;
               this.head = var3;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label133;
            }
         }

         label117:
         try {
            this.notifyAll();
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label117;
         }
      }

      Throwable var16 = var10000;
      throw var16;
   }

   public void run() {
      while(true) {
         EventQueue.QueueElement var1;
         boolean var10001;
         try {
            var1 = this.dequeue();
         } catch (InterruptedException var16) {
            var10001 = false;
            break;
         }

         if (var1 == null) {
            break;
         }

         Vector var17;
         MailEvent var2;
         try {
            var2 = var1.event;
            var17 = var1.vector;
         } catch (InterruptedException var15) {
            var10001 = false;
            break;
         }

         int var3 = 0;

         while(true) {
            int var4;
            try {
               var4 = var17.size();
            } catch (InterruptedException var13) {
               var10001 = false;
               return;
            }

            if (var3 >= var4) {
               break;
            }

            try {
               var2.dispatch(var17.elementAt(var3));
            } catch (Throwable var14) {
               label108: {
                  Throwable var5 = var14;

                  boolean var6;
                  try {
                     var6 = var5 instanceof InterruptedException;
                  } catch (InterruptedException var12) {
                     var10001 = false;
                     return;
                  }

                  if (var6) {
                     return;
                  }
                  break label108;
               }
            }

            ++var3;
         }
      }

   }

   void stop() {
      Thread var1 = this.qThread;
      if (var1 != null) {
         var1.interrupt();
         this.qThread = null;
      }

   }

   static class QueueElement {
      MailEvent event = null;
      EventQueue.QueueElement next = null;
      EventQueue.QueueElement prev = null;
      Vector vector = null;

      QueueElement(MailEvent var1, Vector var2) {
         this.event = var1;
         this.vector = var2;
      }
   }
}
