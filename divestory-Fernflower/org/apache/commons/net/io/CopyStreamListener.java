package org.apache.commons.net.io;

import java.util.EventListener;

public interface CopyStreamListener extends EventListener {
   void bytesTransferred(long var1, int var3, long var4);

   void bytesTransferred(CopyStreamEvent var1);
}
