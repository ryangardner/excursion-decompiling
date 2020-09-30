package org.apache.commons.net.io;

import java.util.EventListener;
import java.util.Iterator;
import org.apache.commons.net.util.ListenerList;

public class CopyStreamAdapter implements CopyStreamListener {
   private final ListenerList internalListeners = new ListenerList();

   public void addCopyStreamListener(CopyStreamListener var1) {
      this.internalListeners.addListener(var1);
   }

   public void bytesTransferred(long var1, int var3, long var4) {
      Iterator var6 = this.internalListeners.iterator();

      while(var6.hasNext()) {
         ((CopyStreamListener)((EventListener)var6.next())).bytesTransferred(var1, var3, var4);
      }

   }

   public void bytesTransferred(CopyStreamEvent var1) {
      Iterator var2 = this.internalListeners.iterator();

      while(var2.hasNext()) {
         ((CopyStreamListener)((EventListener)var2.next())).bytesTransferred(var1);
      }

   }

   public void removeCopyStreamListener(CopyStreamListener var1) {
      this.internalListeners.removeListener(var1);
   }
}
