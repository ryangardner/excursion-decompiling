package javax.mail;

import java.util.Vector;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

public abstract class Service {
   private boolean connected = false;
   private Vector connectionListeners = null;
   protected boolean debug = false;
   private EventQueue q;
   private Object qLock = new Object();
   protected Session session;
   protected URLName url = null;

   protected Service(Session var1, URLName var2) {
      this.session = var1;
      this.url = var2;
      this.debug = var1.getDebug();
   }

   private void terminateQueue() {
      Object var1 = this.qLock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.q != null) {
               Vector var2 = new Vector();
               var2.setSize(1);
               EventQueue var3 = this.q;
               Service.TerminatorEvent var4 = new Service.TerminatorEvent();
               var3.enqueue(var4, var2);
               this.q = null;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var17 = var10000;

         try {
            throw var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }

   public void addConnectionListener(ConnectionListener var1) {
      synchronized(this){}

      try {
         if (this.connectionListeners == null) {
            Vector var2 = new Vector();
            this.connectionListeners = var2;
         }

         this.connectionListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public void close() throws MessagingException {
      synchronized(this){}

      try {
         this.setConnected(false);
         this.notifyConnectionListeners(3);
      } finally {
         ;
      }

   }

   public void connect() throws MessagingException {
      this.connect((String)null, (String)null, (String)null);
   }

   public void connect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void connect(String var1, String var2) throws MessagingException {
      this.connect((String)null, var1, var2);
   }

   public void connect(String var1, String var2, String var3) throws MessagingException {
      this.connect(var1, -1, var2, var3);
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.terminateQueue();
   }

   public URLName getURLName() {
      synchronized(this){}

      URLName var1;
      try {
         if (this.url != null && (this.url.getPassword() != null || this.url.getFile() != null)) {
            var1 = new URLName(this.url.getProtocol(), this.url.getHost(), this.url.getPort(), (String)null, this.url.getUsername(), (String)null);
            return var1;
         }

         var1 = this.url;
      } finally {
         ;
      }

      return var1;
   }

   public boolean isConnected() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.connected;
      } finally {
         ;
      }

      return var1;
   }

   protected void notifyConnectionListeners(int var1) {
      synchronized(this){}

      Throwable var10000;
      label81: {
         boolean var10001;
         try {
            if (this.connectionListeners != null) {
               ConnectionEvent var2 = new ConnectionEvent(this, var1);
               this.queueEvent(var2, this.connectionListeners);
            }
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label81;
         }

         if (var1 != 3) {
            return;
         }

         label72:
         try {
            this.terminateQueue();
            return;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label72;
         }
      }

      Throwable var9 = var10000;
      throw var9;
   }

   protected boolean protocolConnect(String var1, int var2, String var3, String var4) throws MessagingException {
      return false;
   }

   protected void queueEvent(MailEvent var1, Vector var2) {
      Object var3 = this.qLock;
      synchronized(var3){}

      label136: {
         Throwable var10000;
         boolean var10001;
         label131: {
            try {
               if (this.q == null) {
                  EventQueue var4 = new EventQueue();
                  this.q = var4;
               }
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label131;
            }

            label128:
            try {
               break label136;
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label128;
            }
         }

         while(true) {
            Throwable var17 = var10000;

            try {
               throw var17;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               continue;
            }
         }
      }

      var2 = (Vector)var2.clone();
      this.q.enqueue(var1, var2);
   }

   public void removeConnectionListener(ConnectionListener var1) {
      synchronized(this){}

      try {
         if (this.connectionListeners != null) {
            this.connectionListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   protected void setConnected(boolean var1) {
      synchronized(this){}

      try {
         this.connected = var1;
      } finally {
         ;
      }

   }

   protected void setURLName(URLName var1) {
      synchronized(this){}

      try {
         this.url = var1;
      } finally {
         ;
      }

   }

   public String toString() {
      URLName var1 = this.getURLName();
      return var1 != null ? var1.toString() : super.toString();
   }

   static class TerminatorEvent extends MailEvent {
      private static final long serialVersionUID = 5542172141759168416L;

      TerminatorEvent() {
         super(new Object());
      }

      public void dispatch(Object var1) {
         Thread.currentThread().interrupt();
      }
   }
}
