package org.apache.http.protocol;

public class SyncBasicHttpContext extends BasicHttpContext {
   public SyncBasicHttpContext(HttpContext var1) {
      super(var1);
   }

   public Object getAttribute(String var1) {
      synchronized(this){}

      Object var4;
      try {
         var4 = super.getAttribute(var1);
      } finally {
         ;
      }

      return var4;
   }

   public Object removeAttribute(String var1) {
      synchronized(this){}

      Object var4;
      try {
         var4 = super.removeAttribute(var1);
      } finally {
         ;
      }

      return var4;
   }

   public void setAttribute(String var1, Object var2) {
      synchronized(this){}

      try {
         super.setAttribute(var1, var2);
      } finally {
         ;
      }

   }
}
