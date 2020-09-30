package javax.mail.event;

import javax.mail.Store;

public class StoreEvent extends MailEvent {
   public static final int ALERT = 1;
   public static final int NOTICE = 2;
   private static final long serialVersionUID = 1938704919992515330L;
   protected String message;
   protected int type;

   public StoreEvent(Store var1, int var2, String var3) {
      super(var1);
      this.type = var2;
      this.message = var3;
   }

   public void dispatch(Object var1) {
      ((StoreListener)var1).notification(this);
   }

   public String getMessage() {
      return this.message;
   }

   public int getMessageType() {
      return this.type;
   }
}
