package org.apache.http.params;

public abstract class AbstractHttpParams implements HttpParams {
   protected AbstractHttpParams() {
   }

   public boolean getBooleanParameter(String var1, boolean var2) {
      Object var3 = this.getParameter(var1);
      return var3 == null ? var2 : (Boolean)var3;
   }

   public double getDoubleParameter(String var1, double var2) {
      Object var4 = this.getParameter(var1);
      return var4 == null ? var2 : (Double)var4;
   }

   public int getIntParameter(String var1, int var2) {
      Object var3 = this.getParameter(var1);
      return var3 == null ? var2 : (Integer)var3;
   }

   public long getLongParameter(String var1, long var2) {
      Object var4 = this.getParameter(var1);
      return var4 == null ? var2 : (Long)var4;
   }

   public boolean isParameterFalse(String var1) {
      return this.getBooleanParameter(var1, false) ^ true;
   }

   public boolean isParameterTrue(String var1) {
      return this.getBooleanParameter(var1, false);
   }

   public HttpParams setBooleanParameter(String var1, boolean var2) {
      Boolean var3;
      if (var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      this.setParameter(var1, var3);
      return this;
   }

   public HttpParams setDoubleParameter(String var1, double var2) {
      this.setParameter(var1, new Double(var2));
      return this;
   }

   public HttpParams setIntParameter(String var1, int var2) {
      this.setParameter(var1, new Integer(var2));
      return this;
   }

   public HttpParams setLongParameter(String var1, long var2) {
      this.setParameter(var1, new Long(var2));
      return this;
   }
}
