package org.apache.http.params;

public interface HttpParams {
   HttpParams copy();

   boolean getBooleanParameter(String var1, boolean var2);

   double getDoubleParameter(String var1, double var2);

   int getIntParameter(String var1, int var2);

   long getLongParameter(String var1, long var2);

   Object getParameter(String var1);

   boolean isParameterFalse(String var1);

   boolean isParameterTrue(String var1);

   boolean removeParameter(String var1);

   HttpParams setBooleanParameter(String var1, boolean var2);

   HttpParams setDoubleParameter(String var1, double var2);

   HttpParams setIntParameter(String var1, int var2);

   HttpParams setLongParameter(String var1, long var2);

   HttpParams setParameter(String var1, Object var2);
}
