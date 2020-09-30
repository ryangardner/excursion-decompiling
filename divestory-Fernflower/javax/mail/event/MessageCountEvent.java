package javax.mail.event;

import javax.mail.Folder;
import javax.mail.Message;

public class MessageCountEvent extends MailEvent {
   public static final int ADDED = 1;
   public static final int REMOVED = 2;
   private static final long serialVersionUID = -7447022340837897369L;
   protected transient Message[] msgs;
   protected boolean removed;
   protected int type;

   public MessageCountEvent(Folder var1, int var2, boolean var3, Message[] var4) {
      super(var1);
      this.type = var2;
      this.removed = var3;
      this.msgs = var4;
   }

   public void dispatch(Object var1) {
      if (this.type == 1) {
         ((MessageCountListener)var1).messagesAdded(this);
      } else {
         ((MessageCountListener)var1).messagesRemoved(this);
      }

   }

   public Message[] getMessages() {
      return this.msgs;
   }

   public int getType() {
      return this.type;
   }

   public boolean isRemoved() {
      return this.removed;
   }
}
