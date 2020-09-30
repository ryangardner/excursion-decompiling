package org.apache.http.params;

public class SyncBasicHttpParams extends BasicHttpParams {
   private static final long serialVersionUID = 5387834869062660642L;

   public void clear() {
      synchronized(this){}

      try {
         super.clear();
      } finally {
         ;
      }

   }

   public Object clone() throws CloneNotSupportedException {
      synchronized(this){}

      Object var1;
      try {
         var1 = super.clone();
      } finally {
         ;
      }

      return var1;
   }

   public Object getParameter(String var1) {
      synchronized(this){}

      Object var4;
      try {
         var4 = super.getParameter(var1);
      } finally {
         ;
      }

      return var4;
   }

   public boolean isParameterSet(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         var2 = super.isParameterSet(var1);
      } finally {
         ;
      }

      return var2;
   }

   public boolean isParameterSetLocally(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         var2 = super.isParameterSetLocally(var1);
      } finally {
         ;
      }

      return var2;
   }

   public boolean removeParameter(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         var2 = super.removeParameter(var1);
      } finally {
         ;
      }

      return var2;
   }

   public HttpParams setParameter(String var1, Object var2) {
      synchronized(this){}

      HttpParams var5;
      try {
         var5 = super.setParameter(var1, var2);
      } finally {
         ;
      }

      return var5;
   }

   public void setParameters(String[] var1, Object var2) {
      synchronized(this){}

      try {
         super.setParameters(var1, var2);
      } finally {
         ;
      }

   }
}
