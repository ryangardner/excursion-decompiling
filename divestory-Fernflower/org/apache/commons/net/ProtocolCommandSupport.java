package org.apache.commons.net;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Iterator;
import org.apache.commons.net.util.ListenerList;

public class ProtocolCommandSupport implements Serializable {
   private static final long serialVersionUID = -8017692739988399978L;
   private final ListenerList __listeners = new ListenerList();
   private final Object __source;

   public ProtocolCommandSupport(Object var1) {
      this.__source = var1;
   }

   public void addProtocolCommandListener(ProtocolCommandListener var1) {
      this.__listeners.addListener(var1);
   }

   public void fireCommandSent(String var1, String var2) {
      ProtocolCommandEvent var4 = new ProtocolCommandEvent(this.__source, var1, var2);
      Iterator var3 = this.__listeners.iterator();

      while(var3.hasNext()) {
         ((ProtocolCommandListener)((EventListener)var3.next())).protocolCommandSent(var4);
      }

   }

   public void fireReplyReceived(int var1, String var2) {
      ProtocolCommandEvent var4 = new ProtocolCommandEvent(this.__source, var1, var2);
      Iterator var3 = this.__listeners.iterator();

      while(var3.hasNext()) {
         ((ProtocolCommandListener)((EventListener)var3.next())).protocolReplyReceived(var4);
      }

   }

   public int getListenerCount() {
      return this.__listeners.getListenerCount();
   }

   public void removeProtocolCommandListener(ProtocolCommandListener var1) {
      this.__listeners.removeListener(var1);
   }
}
