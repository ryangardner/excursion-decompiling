package javax.mail.event;

public class ConnectionEvent extends MailEvent {
   public static final int CLOSED = 3;
   public static final int DISCONNECTED = 2;
   public static final int OPENED = 1;
   private static final long serialVersionUID = -1855480171284792957L;
   protected int type;

   public ConnectionEvent(Object var1, int var2) {
      super(var1);
      this.type = var2;
   }

   public void dispatch(Object var1) {
      int var2 = this.type;
      if (var2 == 1) {
         ((ConnectionListener)var1).opened(this);
      } else if (var2 == 2) {
         ((ConnectionListener)var1).disconnected(this);
      } else if (var2 == 3) {
         ((ConnectionListener)var1).closed(this);
      }

   }

   public int getType() {
      return this.type;
   }
}
