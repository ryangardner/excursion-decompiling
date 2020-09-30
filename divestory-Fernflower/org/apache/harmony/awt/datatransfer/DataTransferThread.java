package org.apache.harmony.awt.datatransfer;

public class DataTransferThread extends Thread {
   private final DTK dtk;

   public DataTransferThread(DTK var1) {
      super("AWT-DataTransferThread");
      this.setDaemon(true);
      this.dtk = var1;
   }

   public void run() {
      synchronized(this){}

      try {
         this.dtk.initDragAndDrop();
      } finally {
         try {
            this.notifyAll();
         } catch (Throwable var19) {
            Throwable var10000 = var19;
            boolean var10001 = false;

            while(true) {
               Throwable var1 = var10000;

               try {
                  throw var1;
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  continue;
               }
            }
         }
      }

      this.dtk.runEventLoop();
   }

   public void start() {
      // $FF: Couldn't be decompiled
   }
}
