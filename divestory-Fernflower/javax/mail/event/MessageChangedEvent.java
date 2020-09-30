package javax.mail.event;

import javax.mail.Message;

public class MessageChangedEvent extends MailEvent {
   public static final int ENVELOPE_CHANGED = 2;
   public static final int FLAGS_CHANGED = 1;
   private static final long serialVersionUID = -4974972972105535108L;
   protected transient Message msg;
   protected int type;

   public MessageChangedEvent(Object var1, int var2, Message var3) {
      super(var1);
      this.msg = var3;
      this.type = var2;
   }

   public void dispatch(Object var1) {
      ((MessageChangedListener)var1).messageChanged(this);
   }

   public Message getMessage() {
      return this.msg;
   }

   public int getMessageChangeType() {
      return this.type;
   }
}
