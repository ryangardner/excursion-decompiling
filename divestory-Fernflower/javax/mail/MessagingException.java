package javax.mail;

public class MessagingException extends Exception {
   private static final long serialVersionUID = -7569192289819959253L;
   private Exception next;

   public MessagingException() {
      this.initCause((Throwable)null);
   }

   public MessagingException(String var1) {
      super(var1);
      this.initCause((Throwable)null);
   }

   public MessagingException(String var1, Exception var2) {
      super(var1);
      this.next = var2;
      this.initCause((Throwable)null);
   }

   private final String superToString() {
      return super.toString();
   }

   public Throwable getCause() {
      synchronized(this){}

      Exception var1;
      try {
         var1 = this.next;
      } finally {
         ;
      }

      return var1;
   }

   public Exception getNextException() {
      synchronized(this){}

      Exception var1;
      try {
         var1 = this.next;
      } finally {
         ;
      }

      return var1;
   }

   public boolean setNextException(Exception var1) {
      synchronized(this){}
      Object var2 = this;

      Throwable var10000;
      while(true) {
         boolean var10001;
         label189: {
            try {
               if (var2 instanceof MessagingException && ((MessagingException)var2).next != null) {
                  break label189;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break;
            }

            try {
               if (var2 instanceof MessagingException) {
                  ((MessagingException)var2).next = var1;
                  return true;
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break;
            }

            return false;
         }

         try {
            var2 = ((MessagingException)var2).next;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break;
         }
      }

      Throwable var15 = var10000;
      throw var15;
   }

   public String toString() {
      synchronized(this){}

      Throwable var10000;
      label450: {
         String var1;
         Exception var2;
         boolean var10001;
         try {
            var1 = super.toString();
            var2 = this.next;
         } catch (Throwable var46) {
            var10000 = var46;
            var10001 = false;
            break label450;
         }

         if (var2 == null) {
            return var1;
         }

         StringBuffer var3;
         try {
            var3 = new StringBuffer;
         } catch (Throwable var45) {
            var10000 = var45;
            var10001 = false;
            break label450;
         }

         String var4 = var1;
         if (var1 == null) {
            var4 = "";
         }

         try {
            var3.<init>(var4);
         } catch (Throwable var44) {
            var10000 = var44;
            var10001 = false;
            break label450;
         }

         Exception var47 = var2;

         label431:
         while(true) {
            while(true) {
               if (var47 == null) {
                  try {
                     var4 = var3.toString();
                  } catch (Throwable var41) {
                     var10000 = var41;
                     var10001 = false;
                     break label431;
                  }

                  return var4;
               }

               try {
                  var3.append(";\n  nested exception is:\n\t");
                  if (!(var47 instanceof MessagingException)) {
                     break;
                  }

                  MessagingException var48 = (MessagingException)var47;
                  var3.append(var48.superToString());
                  var47 = var48.next;
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break label431;
               }
            }

            try {
               var3.append(var47.toString());
            } catch (Throwable var42) {
               var10000 = var42;
               var10001 = false;
               break;
            }

            var47 = null;
         }
      }

      Throwable var49 = var10000;
      throw var49;
   }
}
